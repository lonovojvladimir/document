package com.lonovojvladimir.document.document.filter;

import com.lonovojvladimir.document.document.dto.DocumentFilterDto;
import com.lonovojvladimir.document.document.entity.DocumentEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class DocumentTitlePatternFilter implements DocumentFilter {
    @Override
    public boolean isApplicable(DocumentFilterDto documentFilterDto) {
        return documentFilterDto.getTitlePattern()!=null;
    }

    @Override
    public Stream<DocumentEntity> apply(Stream<DocumentEntity> documentEntityStream, DocumentFilterDto documentFilterDto) {
        return documentEntityStream.filter(documentEntity -> documentEntity.getTitle().equals(documentFilterDto.getTitlePattern()));
    }
}
