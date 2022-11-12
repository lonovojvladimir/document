package ru.com.document.document.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.com.document.document.entity.DocumentEntity;

public interface DocumentRepository extends PagingAndSortingRepository<DocumentEntity, Long> {

    @Query("select d from DocumentEntity d\n" +
            "    where UPPER(d.code) like UPPER(:searchString)\n" +
            "    or UPPER(d.title) like UPPER(:searchString)\n" +
            "    or UPPER(d.comment) like UPPER(:searchString)")
    Page<DocumentEntity> searchDocument(String searchString, Pageable pageable);

}
