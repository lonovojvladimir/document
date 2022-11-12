package ru.com.document.document.entity;

import lombok.*;

import javax.persistence.*;
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
    @Column(name = "CREATOR")
    private String creator;
    @Column(name = "CREATED")
    private LocalDateTime created;
    @Column(name = "UPDATER")
    private String updater;
    @Column(name = "UPDATED")
    private LocalDateTime updated;
}
