package com.lonovojvladimir.document.validator;

import com.lonovojvladimir.document.document.dto.DocumentCreateDto;
import com.lonovojvladimir.document.document.dto.DocumentUpdateDto;
import com.lonovojvladimir.document.document.exception.DataValidationException;
import com.lonovojvladimir.document.document.validator.DocumentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DocumentValidatrorTest {

    private DocumentValidator documentValidator;

    @BeforeEach
    void setUp() {
        documentValidator = new DocumentValidator();
    }

    @Test
    void validateCreateDocument_shouldThrowIfNull() {
        DataValidationException exception = assertThrows(
                DataValidationException.class,
                () -> documentValidator.validateCreateDocument(null)
        );
        assertEquals("Document is null", exception.getMessage());
    }

    @Test
    void validateCreateDocument_shouldThrowIfCodeIsNull() {
        DocumentCreateDto dto = new DocumentCreateDto();
        dto.setCode(null);
        DataValidationException exception = assertThrows(
                DataValidationException.class,
                () -> documentValidator.validateCreateDocument(dto)
        );
        assertEquals("Code is null", exception.getMessage());
    }

    @Test
    void validateCreateDocument_shouldPassWithValidDto() {
        DocumentCreateDto dto = new DocumentCreateDto();
        dto.setCode("test.docx");
        assertDoesNotThrow(() -> documentValidator.validateCreateDocument(dto));
    }

    @Test
    void validateUpdateDocument_shouldThrowIfNull() {
        DataValidationException exception = assertThrows(
                DataValidationException.class,
                () -> documentValidator.validateUpdateDocument(null)
        );
        assertEquals("Document is null", exception.getMessage());
    }

    @Test
    void validateUpdateDocument_shouldThrowIfCodeIsNull() {
        DocumentUpdateDto dto = new DocumentUpdateDto();
        dto.setCode(null);
        DataValidationException exception = assertThrows(
                DataValidationException.class,
                () -> documentValidator.validateUpdateDocument(dto)
        );
        assertEquals("Code is null", exception.getMessage());
    }

    @Test
    void validateUpdateDocument_shouldPassWithValidDto() {
        DocumentUpdateDto dto = new DocumentUpdateDto();
        dto.setCode("test.docx");
        assertDoesNotThrow(() -> documentValidator.validateUpdateDocument(dto));
    }
}
