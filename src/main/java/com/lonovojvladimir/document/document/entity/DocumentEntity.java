package com.lonovojvladimir.document.document.entity;

import com.lonovojvladimir.document.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "Document")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_sequence")
    @SequenceGenerator(name = "document_sequence", sequenceName = "document_sequence",
            allocationSize = 1)
    @Column(name = "id_document")
    private Long idDocument;
    @Column(name = "code")
    private String code;
    @Column(name = "title")
    private String title;
    @Column(name = "comment")
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private UserEntity creator;
    @Column(name = "created")
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updater")
    private UserEntity updater;
    @Column(name = "updated")
    private LocalDateTime updated;
}
