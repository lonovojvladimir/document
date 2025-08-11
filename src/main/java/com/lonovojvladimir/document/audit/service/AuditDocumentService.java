package com.lonovojvladimir.document.audit.service;

import com.lonovojvladimir.document.audit.dto.AuditDocumentDto;
import com.lonovojvladimir.document.audit.entity.AuditDocumentEntity;
import com.lonovojvladimir.document.audit.mapper.AuditDocumentMapper;
import com.lonovojvladimir.document.audit.repository.AuditDocumentRepository;
import com.lonovojvladimir.document.document.dto.DocumentDto;
import com.lonovojvladimir.document.document.entity.DocumentEntity;
import com.lonovojvladimir.document.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditDocumentService {
    private static final List<String> AUDIT_FIELDS = Arrays.asList("title", "comment");
    private final AuditDocumentRepository auditDocumentRepository;
    private final DocumentService documentService;
    private final AuditDocumentMapper auditDocumentMapper;

    public void audit(String operation, DocumentEntity oldEntity, DocumentEntity newEntity) {
        List<AuditDocumentEntity> entities = new ArrayList<>();
        for (String fieldName : AUDIT_FIELDS) {
            AuditDocumentEntity entity = event(operation, fieldName, oldEntity, newEntity);
            if (entity != null) {
                entities.add(entity);
            }
        }
        auditDocumentRepository.saveAll(entities);
    }

    private AuditDocumentEntity event(String operation, String fieldName, DocumentEntity oldEntity, DocumentEntity newEntity) {
        AuditDocumentEntity entity = new AuditDocumentEntity();
            entity.setOperation(operation);
            entity.setModifier(newEntity.getUpdater());
            entity.setModified(newEntity.getUpdated());
            entity.setCode(newEntity.getCode());
            entity.setProperty(fieldName);

        if (oldEntity == null) {
            entity.setOldValue(null);
            entity.setNewValue(getField(fieldName, newEntity));
            return entity;
        } else if (oldEntity != null && newEntity != null) {
            entity.setOldValue(getField(fieldName, oldEntity));
            entity.setNewValue(getField(fieldName, newEntity));
            return entity;
        }
        return null;
    }

    private String getField(String property, DocumentEntity entity) {
        try {
            Field field = entity.getClass().getDeclaredField(property);
            field.setAccessible(true);
            Object value = field.get(entity);
            return (String) value;
        } catch (Exception e) {
            return null;
        }
    }

    public Page<AuditDocumentDto> getHistory(Long idDocument, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "idAuditDocument"));
        DocumentDto documentDto  = documentService.getDocument(idDocument);
        String code = documentDto.getCode();
        Page<AuditDocumentEntity> auditDocumentEntityPage = auditDocumentRepository.findAuditDocumentEntitiesByCode(code, pageable);
        return auditDocumentEntityPage.map(auditDocumentMapper :: toDto);
    }

    public void deleteHistory(Long idDocument){
        String code = documentService.getDocument(idDocument).getCode();
        Iterable<Long> auditDocumentEntityList = auditDocumentRepository.findAuditDocumentEntitiesByCodeList(code);
        auditDocumentRepository.deleteAllById(auditDocumentEntityList);
    }
}
