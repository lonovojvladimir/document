package com.lonovojvladimir.document.audit.dto;

import com.lonovojvladimir.document.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDocumentDto {
    private Long idAuditDocument;
    private String operation;
    private String code;
    private String property;
    private String oldValue;
    private String newValue;
    private UserEntity modifier;
    private LocalDateTime modified;


}
