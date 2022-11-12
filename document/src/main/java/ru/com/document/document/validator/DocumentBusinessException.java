package ru.com.document.document.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.com.document.dict.exception.DocumentExceptionDto;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class DocumentBusinessException extends RuntimeException{

    private List<DocumentExceptionDto> errors;

    public DocumentBusinessException(DocumentExceptionDto error) {
        this.errors = Collections.singletonList(error);
    }
}
