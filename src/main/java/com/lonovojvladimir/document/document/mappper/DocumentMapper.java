package com.lonovojvladimir.document.document.mappper;

import com.lonovojvladimir.document.document.dto.DocumentCreateDto;
import com.lonovojvladimir.document.document.dto.DocumentDto;
import com.lonovojvladimir.document.document.entity.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {

    DocumentDto toDto(DocumentEntity documentEntity);
    DocumentEntity toEntity(DocumentDto documentDto);
    DocumentEntity toCreateEntity(DocumentCreateDto documentCreateDto);

}
