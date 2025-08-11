package com.lonovojvladimir.document.document.repository;

import com.lonovojvladimir.document.document.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<DocumentEntity, Long> {

    @Query("SELECT d FROM DocumentEntity d " +
            "where UPPER(d.code) like UPPER(:term)" +
            "and UPPER(d.title) like UPPER(:term)" +
            "and UPPER(d.comment) like UPPER(:term)")
    Page<DocumentEntity> findDocumentEntitiesBySearchString(@Param("term") String term, Pageable pageable);

    @Query("SELECT d FROM DocumentEntity d " +
            "where UPPER(d.code) like UPPER(:fileName)")
    DocumentEntity findDistinctByCode(String fileName);
}
