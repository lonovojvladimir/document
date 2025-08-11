package com.lonovojvladimir.document.service;

import com.lonovojvladimir.document.audit.service.AuditDocumentService;
import com.lonovojvladimir.document.document.dto.DocumentCreateDto;
import com.lonovojvladimir.document.document.dto.DocumentDto;
import com.lonovojvladimir.document.document.dto.DocumentUpdateDto;
import com.lonovojvladimir.document.document.entity.DocumentEntity;
import com.lonovojvladimir.document.document.filter.DocumentFilter;
import com.lonovojvladimir.document.document.mappper.DocumentMapper;
import com.lonovojvladimir.document.document.repository.DocumentRepository;
import com.lonovojvladimir.document.document.service.DocumentService;
import com.lonovojvladimir.document.document.validator.DocumentValidator;
import com.lonovojvladimir.document.minio.service.MinioService;
import com.lonovojvladimir.document.user.entity.UserEntity;
import com.lonovojvladimir.document.user.service.UserServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private DocumentMapper documentMapper;
    @Mock
    private DocumentValidator documentValidator;
    @Mock
    private MinioService minioService;
    @Mock
    private List<DocumentFilter> documentFilters;
    @Mock
    private AuditDocumentService auditDocumentService;
    @Mock
    private UserServiceClient userServiceClient;
    @InjectMocks
    private DocumentService documentService;

    Long documentId = 1L;
    DocumentEntity entity = new DocumentEntity();
    DocumentDto dto = new DocumentDto();

    @Test
    void getDocument_shouldReturnDto() {
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(entity));
        when(documentMapper.toDto(entity)).thenReturn(dto);
        DocumentDto result = documentService.getDocument(documentId);
        assertNotNull(result);
        verify(documentRepository).findById(documentId);
        verify(documentMapper).toDto(entity);
    }

    @Test
    void getAllDocuments_shouldReturnPageOfDtos() {
        int page = 1, size = 10;
        String term = "test";
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "idDocument"));
        List<DocumentEntity> entities = List.of(new DocumentEntity());
        Page<DocumentEntity> entityPage = new PageImpl<>(entities, pageable, entities.size());
        when(documentRepository.findDocumentEntitiesBySearchString(anyString(), eq(pageable))).thenReturn(entityPage);
        when(documentMapper.toDto(any())).thenReturn(dto);
        Page<DocumentDto> result = documentService.getAllDocuments(page, size, term);
        assertEquals(1, result.getContent().size());
        assertNotNull(result.getContent());
        verify(documentRepository).findDocumentEntitiesBySearchString(anyString(), eq(pageable));
        verify(documentMapper).toDto(any());
    }

    @Test
    void createDocument_shouldReturnDto() {
        DocumentCreateDto createDto = new DocumentCreateDto();
        UserEntity userEntity = new UserEntity();
        when(documentMapper.toCreateEntity(createDto)).thenReturn(entity);
        when(userServiceClient.getUserByToken()).thenReturn(userEntity);
        when(documentRepository.save(any())).thenReturn(entity);
        when(documentMapper.toDto(entity)).thenReturn(dto);
        doNothing().when(auditDocumentService).audit(eq("CREATE"), isNull(), any());
        DocumentDto result = documentService.createDocument(createDto);
        assertNotNull(result);
        verify(documentMapper).toCreateEntity(createDto);
        verify(documentRepository).save(entity);
        verify(documentValidator).validateCreateDocument(createDto);
        verify(auditDocumentService).audit(eq("CREATE"), isNull(), any());
    }

    @Test
    void updateDocument_shouldReturnDto() {
        DocumentUpdateDto updateDto = new DocumentUpdateDto();
        updateDto.setCode("test.docx");
        updateDto.setTitle("test");
        updateDto.setComment("test");
        UserEntity userEntity = new UserEntity();
        doNothing().when(auditDocumentService).audit(eq("UPDATE"), eq(entity), any(DocumentEntity.class));
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(entity));
        when(userServiceClient.getUserByToken()).thenReturn(userEntity);
        when(documentRepository.save(any())).thenReturn(entity);
        when(documentMapper.toDto(entity)).thenReturn(dto);
        DocumentDto result = documentService.updateDocument(documentId, updateDto);
        assertNotNull(result);
        verify(documentValidator).validateUpdateDocument(updateDto);
        verify(auditDocumentService).audit(eq("UPDATE"), any(), any());
        verify(documentRepository).save(entity);
        verify(documentMapper).toDto(entity);
    }

    @Test
    void uploadDocument_shouldUploadIfExists() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.docx");
        when(documentRepository.findDistinctByCode("test.docx")).thenReturn(new DocumentEntity());
        ResponseEntity<String> response = documentService.uploadDocument(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(minioService).uploadDocx(file);
        verify(documentRepository).findDistinctByCode("test.docx");
    }

    @Test
    void uploadDocument_shouldReturnNotModifiedIfNotExists() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.docx");
        when(documentRepository.findDistinctByCode("test.docx")).thenReturn(null);
        ResponseEntity<String> response = documentService.uploadDocument(file);
        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        verify(minioService, never()).uploadDocx(file);
        verify(documentRepository).findDistinctByCode("test.docx");
    }

    @Test
    void downloadFile_shouldReturnResource() {
        String fileName = "test.docx";
        Resource resource = mock(Resource.class);
        when(minioService.downloadFile(fileName)).thenReturn(resource);
        ResponseEntity<Resource> response = documentService.downloadFile(fileName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resource, response.getBody());
        verify(minioService).downloadFile(fileName);
    }

    @Test
    void deleteDocument_shouldAuditAndDelete() {
        doNothing().when(documentRepository).deleteById(documentId);
        doNothing().when(auditDocumentService).deleteHistory(documentId);
        ResponseEntity<String> response = documentService.deleteDocument(documentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(documentRepository).deleteById(documentId);
        verify(auditDocumentService).deleteHistory(documentId);
    }

    @Test
    void deleteFile_shouldCallMinio() throws Exception {
        String fileName = "test.docx";
        doNothing().when(minioService).deleteFile(fileName);
        ResponseEntity<String> response = documentService.deleteFile(fileName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(minioService).deleteFile(fileName);
    }


}
