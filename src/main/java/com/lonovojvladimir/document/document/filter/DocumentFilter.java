package com.lonovojvladimir.document.document.filter;

import com.lonovojvladimir.document.document.dto.DocumentFilterDto;
import com.lonovojvladimir.document.document.entity.DocumentEntity;

import java.util.stream.Stream;

public interface DocumentFilter {
    boolean isApplicable(DocumentFilterDto filterDto);
    Stream<DocumentEntity> apply(Stream<DocumentEntity> documentEntityStream, DocumentFilterDto filterDto);
}
