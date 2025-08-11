package com.lonovojvladimir.document.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUpdateDto {
    private String code;
    private String title;
    private String comment;
}
