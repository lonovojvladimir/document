package com.lonovojvladimir.document.document.service;

import com.lonovojvladimir.document.audit.service.AuditDocumentService;
import com.lonovojvladimir.document.document.dto.*;
import com.lonovojvladimir.document.document.entity.DocumentEntity;
import com.lonovojvladimir.document.document.filter.DocumentFilter;
import com.lonovojvladimir.document.document.mappper.DocumentMapper;
import com.lonovojvladimir.document.minio.service.MinioService;
import com.lonovojvladimir.document.document.repository.DocumentRepository;
import com.lonovojvladimir.document.document.validator.DocumentValidator;
import com.lonovojvladimir.document.user.entity.UserEntity;
import com.lonovojvladimir.document.user.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final MinioService minioService;
    private final List<DocumentFilter> documentFilters;
    private final DocumentValidator documentValidator;
    private final UserServiceClient userServiceClient;
    private final AuditDocumentService auditDocumentService;

    public DocumentDto getDocument(Long idDocument) {
        DocumentEntity documentEntity = documentRepository.findById(idDocument).orElse(null);
        return documentMapper.toDto(documentEntity);
    }

    public Page<DocumentDto> getAllDocuments(int page, int size, String term) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "idDocument"));
        Page<DocumentEntity> documentEntityPage = documentRepository.findDocumentEntitiesBySearchString("%" + term + "%", pageable);
        return documentEntityPage.map(documentMapper::toDto);
    }

    public List<DocumentDto> getAllDocumentsByFilter(DocumentFilterDto documentFilterDto) {
        Stream<DocumentEntity> documentEntityStream = StreamSupport.stream(documentRepository.findAll().spliterator(), false);
        for (DocumentFilter documentFilter : documentFilters) {
            if (documentFilter.isApplicable(documentFilterDto)) {
                documentEntityStream = documentFilter.apply(documentEntityStream, documentFilterDto);
            }
        }
        return documentEntityStream.map(documentMapper::toDto).toList();
    }

    public DocumentDto createDocument(DocumentCreateDto documentCreateDto) {
        UserEntity userEntity = userServiceClient.getUserByToken();
        documentValidator.validateCreateDocument(documentCreateDto);
        LocalDateTime now = LocalDateTime.now();
        DocumentEntity documentEntity = documentMapper.toCreateEntity(documentCreateDto);
        documentEntity.setCreated(now);
        documentEntity.setUpdated(now);
        documentEntity.setCreator(userEntity);
        documentEntity.setUpdater(userEntity);
        auditDocumentService.audit("CREATE", null, documentEntity);
        return documentMapper.toDto(documentRepository.save(documentEntity));
    }

    public DocumentDto updateDocument(Long idDocument, DocumentUpdateDto documentUpdateDto) {
        documentValidator.validateUpdateDocument(documentUpdateDto);
        LocalDateTime now = LocalDateTime.now();
        DocumentEntity documentEntity = documentRepository.findById(idDocument).orElse(null);
        DocumentEntity documentEntityNew = new DocumentEntity();
        documentEntityNew.setCode(documentUpdateDto.getCode());
        documentEntityNew.setTitle(documentUpdateDto.getTitle());
        documentEntityNew.setComment(documentUpdateDto.getComment());
        documentEntityNew.setUpdated(now);
        documentEntityNew.setUpdater(userServiceClient.getUserByToken());
        auditDocumentService.audit("UPDATE", documentEntity, documentEntityNew);
        return documentMapper.toDto(documentRepository.save(documentEntity));
    }

    public ResponseEntity<String> uploadDocument(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        if (documentRepository.findDistinctByCode(originalFilename) != null) {
            minioService.uploadDocx(file);
            return ResponseEntity.status(HttpStatus.OK).body("file add minio");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("file not add minio");
        }
    }

    public ResponseEntity<Resource> downloadFile(String fileName) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(minioService.downloadFile(fileName));
    }

    public ResponseEntity<String> deleteDocument(Long idDocument) {
        auditDocumentService.deleteHistory(idDocument);
        documentRepository.deleteById(idDocument);
        return ResponseEntity.status(HttpStatus.OK).body("document deleted successfully");
    }

    public ResponseEntity<String> deleteFile(String fileName) throws Exception {
        minioService.deleteFile(fileName);
        return ResponseEntity.status(HttpStatus.OK).body("file " + fileName + " deleted successfully");
    }

}
