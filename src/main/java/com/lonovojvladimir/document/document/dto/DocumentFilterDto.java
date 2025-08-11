package com.lonovojvladimir.document.document.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentFilterDto {
    public String codePattern;
    private String titlePattern;
    private String commentPattern;
    private long creatorPattern;
    private LocalDateTime createdPattern;
    private long updaterPattern;
    private LocalDateTime updatedPattern;
}
