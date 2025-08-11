package com.lonovojvladimir.document.document.dto;

import com.lonovojvladimir.document.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    private Long idDocument;
    private String code;
    private String title;
    private String comment;
    private UserDto creator;
    private LocalDateTime created;
    private UserDto updater;
    private LocalDateTime updated;
}
