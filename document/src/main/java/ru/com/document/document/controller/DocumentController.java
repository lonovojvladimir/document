package ru.com.document.document.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.com.document.dict.api.DocumentApi;
import ru.com.document.dict.dto.*;
import ru.com.document.document.service.DocumentService;
import ru.com.document.document.validator.DocumentExceptionHandler;

@RestController
@AllArgsConstructor
@DocumentExceptionHandler
public class DocumentController implements DocumentApi {

    private final DocumentService service;

    @Override
    public ResponseEntity<DocumentResponseDto<DocumentDto>> createDocument(DocumentCreateDto createDto) {
        return response(service.createDocument(createDto));
    }

    @Override
    public ResponseEntity<DocumentResponseDto<DocumentDto>> updateDocument(long idDocument, DocumentUpdateDto updateDto) {
        return response(service.updateDocument(idDocument, updateDto));
    }

    @Override
    public ResponseEntity<DocumentResponseDto<DocumentDto>> getDocument(long idDocument) {
        return response(service.getDocument(idDocument));
    }

    @Override
    public ResponseEntity<DocumentResponseDto<DocumentListPageDto<DocumentDto>>> getAllDocument(int page, int size, String term) {
        return response(service.getAllDocument( page, size, term));
    }


    private <Dto> ResponseEntity<DocumentResponseDto<Dto>> response(Dto dto) {
        DocumentResponseDto<Dto> response = new DocumentResponseDto<>();
        response.setData(dto);
        response.setError(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
