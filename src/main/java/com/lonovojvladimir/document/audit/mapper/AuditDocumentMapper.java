package com.lonovojvladimir.document.audit.mapper;

import com.lonovojvladimir.document.audit.dto.AuditDocumentDto;
import com.lonovojvladimir.document.audit.entity.AuditDocumentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditDocumentMapper {

    AuditDocumentDto toDto (AuditDocumentEntity entity);
    AuditDocumentEntity toEntity (AuditDocumentDto dto);

}
