package ru.com.document.document.audit.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.com.document.document.audit.entity.AuditDocumentEntity;

public interface AuditDocumentRepository extends PagingAndSortingRepository<AuditDocumentEntity, Long> {
}
