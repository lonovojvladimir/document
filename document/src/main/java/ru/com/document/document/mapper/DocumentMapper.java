package ru.com.document.document.mapper;

import org.mapstruct.Mapper;
import ru.com.document.dict.dto.DocumentCreateDto;
import ru.com.document.dict.dto.DocumentDto;
import ru.com.document.document.entity.DocumentEntity;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDto toDocumentDto (DocumentEntity entity);
    DocumentEntity toDocumentCreateEntity(DocumentCreateDto dto);

//    DocumentEntity toDocumentDto(DocumentCreateDto dto);
}
