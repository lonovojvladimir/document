package com.lonovojvladimir.document.document.validator;

import com.lonovojvladimir.document.document.dto.DocumentCreateDto;
import com.lonovojvladimir.document.document.dto.DocumentUpdateDto;
import com.lonovojvladimir.document.document.exception.DataValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentValidator {

    public void validateCreateDocument(DocumentCreateDto documentCreateDto) {
        if (documentCreateDto == null) {
            throw new DataValidationException("Document is null");
        }
        if (documentCreateDto.getCode() == null) {
            throw new DataValidationException("Code is null");
        }
    }

    public void validateUpdateDocument(DocumentUpdateDto documentUpdateDto) {
        if (documentUpdateDto == null) {
            throw new DataValidationException("Document is null");
        }
        if (documentUpdateDto.getCode() == null) {
            throw new DataValidationException("Code is null");
        }
    }
}
