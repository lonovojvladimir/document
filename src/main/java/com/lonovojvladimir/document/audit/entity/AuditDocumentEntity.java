package com.lonovojvladimir.document.audit.entity;

import com.lonovojvladimir.document.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id_audit_document")
    private Long idAuditDocument;
    @Column(name = "operation")
    private String operation;
    @Column(name = "code")
    private String code;
    @Column(name = "property")
    private String property;
    @Column(name = "old_value")
    private String oldValue;
    @Column(name = "new_value")
    private String newValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier")
    private UserEntity modifier;
    @Column(name = "modified")
    private LocalDateTime modified;


}
