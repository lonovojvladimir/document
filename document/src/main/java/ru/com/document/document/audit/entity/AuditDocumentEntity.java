package ru.com.document.document.audit.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "AUDIT_DOCUMENT")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditDocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_document_sequence")
    @SequenceGenerator(name = "audit_document_sequence", sequenceName = "audit_document_sequence",
            allocationSize = 1)
    @Column(name = "ID_AUDIT_DOCUMENT")
    private Long idAuditDocument;
    @Column(name = "OPERATION")
    private String operation;
    @Column(name = "CODE")
    private String code;
    @Column(name = "PROPERTY")
    private String property;
    @Column(name = "OLD_VALUE")
    private String oldValue;
    @Column(name = "NEW_VALUE")
    private String newValue;
    @Column(name = "MODIFIER")
    private String modifier;
    @Column(name = "MODIFIED")
    private LocalDateTime modified;


}
