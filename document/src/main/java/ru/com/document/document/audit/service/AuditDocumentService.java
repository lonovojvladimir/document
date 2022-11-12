package ru.com.document.document.audit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.com.document.document.audit.entity.AuditDocumentEntity;
import ru.com.document.document.audit.repository.AuditDocumentRepository;
import ru.com.document.document.entity.DocumentEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuditDocumentService {

    private static final List<String> AUDIT_FIELDS = Arrays.asList("title","comment");
    private final AuditDocumentRepository repository;

    public void audit(String operation, DocumentEntity oldEntity, DocumentEntity newEntity) {
        List<AuditDocumentEntity> entities = new ArrayList<>();
        for (String fieldName : AUDIT_FIELDS) {
            AuditDocumentEntity entity = event(operation, fieldName, oldEntity, newEntity);
            if (entity != null) {
                entities.add(entity);
            }
        }
        repository.saveAll(entities);
    }

    private AuditDocumentEntity event(String operation, String fieldName, DocumentEntity oldEntity, DocumentEntity newEntity) {

        AuditDocumentEntity entity = new AuditDocumentEntity();
        entity.setOperation(operation);
        entity.setModifier(newEntity.getUpdater());
        entity.setModified(newEntity.getUpdated());
        entity.setCode(newEntity.getCode());
        entity.setProperty(StringUtils.capitalize(fieldName));
        if (oldEntity == null) {
            entity.setOldValue(null);
            entity.setNewValue(getField(fieldName, newEntity));
            return entity;
        } else if (!Objects.equals(getField(fieldName, oldEntity), getField(fieldName, newEntity))) {
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
//            if (value instanceof Dict) {
//                return "code : " + ((Dict) value).getC();
//            }
            return (String) value;
        } catch (Exception e) {
            return null;
        }
    }





}
