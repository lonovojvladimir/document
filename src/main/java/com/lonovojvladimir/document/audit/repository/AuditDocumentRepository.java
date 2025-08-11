package com.lonovojvladimir.document.audit.repository;

import com.lonovojvladimir.document.audit.entity.AuditDocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditDocumentRepository extends CrudRepository<AuditDocumentEntity, Long> {

    @Query("SELECT a FROM AuditDocumentEntity a " +
            "where UPPER(a.code) like UPPER(:code)")
    Page<AuditDocumentEntity> findAuditDocumentEntitiesByCode(String code, Pageable pageable);

    @Query("SELECT a.idAuditDocument FROM AuditDocumentEntity a " +
            "where UPPER(a.code) like UPPER(:code)")
    Iterable<Long> findAuditDocumentEntitiesByCodeList(String code);

}
