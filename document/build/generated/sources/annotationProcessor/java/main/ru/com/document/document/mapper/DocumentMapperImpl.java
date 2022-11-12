package ru.com.document.document.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.com.document.dict.dto.DocumentCreateDto;
import ru.com.document.dict.dto.DocumentDto;
import ru.com.document.dict.dto.DocumentDto.DocumentDtoBuilder;
import ru.com.document.document.entity.DocumentEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-12T22:52:31+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public DocumentDto toDocumentDto(DocumentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DocumentDtoBuilder documentDto = DocumentDto.builder();

        documentDto.idDocument( entity.getIdDocument() );
        documentDto.code( entity.getCode() );
        documentDto.title( entity.getTitle() );
        documentDto.comment( entity.getComment() );
        documentDto.creator( entity.getCreator() );
        documentDto.created( entity.getCreated() );
        documentDto.updater( entity.getUpdater() );
        documentDto.updated( entity.getUpdated() );

        return documentDto.build();
    }

    @Override
    public DocumentEntity toDocumentCreateEntity(DocumentCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        DocumentEntity documentEntity = new DocumentEntity();

        documentEntity.setCode( dto.getCode() );
        documentEntity.setTitle( dto.getTitle() );
        documentEntity.setComment( dto.getComment() );

        return documentEntity;
    }
}
