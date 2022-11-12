package ru.com.document.document.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.com.document.application.service.UserService;
import ru.com.document.dict.dto.DocumentCreateDto;
import ru.com.document.dict.dto.DocumentListPageDto;
import ru.com.document.dict.dto.DocumentUpdateDto;
import ru.com.document.document.audit.service.AuditDocumentService;
import ru.com.document.document.mapper.DocumentMapper;
import ru.com.document.dict.dto.DocumentDto;
import ru.com.document.document.entity.DocumentEntity;
import ru.com.document.document.repository.DocumentRepository;
import ru.com.document.document.validator.DocumentValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocumentService {

    private final DocumentRepository repository;
    private final DocumentValidator validator;
    private final DocumentMapper mapper;
    private final UserService userService;
    private final AuditDocumentService auditDocumentService;

    public DocumentDto createDocument(DocumentCreateDto createDto) {
        String user = userService.getCustomUserDetails().getUsername();
//        validator.validate(createDto);
        LocalDateTime now = LocalDateTime.now();
        DocumentEntity entity = mapper.toDocumentCreateEntity(createDto);
        entity.setCreator(user);
        entity.setUpdater(user);
        entity.setCreated(now);
        entity.setUpdated(now);
        auditDocumentService.audit("CREATE", null, entity);
        return mapper.toDocumentDto(repository.save(entity));
    }

    public DocumentDto updateDocument(long idDocument, DocumentUpdateDto updateDto) {
        String user = userService.getCustomUserDetails().getUsername();
//        validator.validate(updateDto);
        LocalDateTime now = LocalDateTime.now();
        Optional<DocumentEntity> entityOptional = repository.findById(idDocument);
        if (entityOptional.isPresent()) {
            DocumentEntity entity = entityOptional.get();
            DocumentEntity oldEntity = DocumentEntity.builder()
                    .title(entity.getTitle())
                    .comment(entity.getComment())
                    .build();
            entity.setTitle(updateDto.getTitle());
            entity.setComment(updateDto.getComment());
            entity.setUpdater(user);
            entity.setUpdated(now);
            auditDocumentService.audit("UPDATE", oldEntity, entity);
            return mapper.toDocumentDto(repository.save(entity));
        }
        return null;
    }

    public DocumentDto getDocument(long idDocument) {
        Optional<DocumentEntity> entityOptional  =  repository.findById(idDocument);
        return entityOptional.map(mapper::toDocumentDto).orElse(null);
    }

    public DocumentListPageDto<DocumentDto> getAllDocument(int page, int size, String term) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DocumentEntity> entities;
        if (term == null || term.isEmpty()){
            entities = repository.findAll(pageable);
        } else {
            entities = repository.searchDocument("%" + term + "%", pageable);
        }
        return  DocumentListPageDto.<DocumentDto>builder()
                .content(entities.stream().map(mapper::toDocumentDto)
                        .collect(Collectors.toList()))
                .totalElements(entities.getTotalElements())
                .build();
    }

}
