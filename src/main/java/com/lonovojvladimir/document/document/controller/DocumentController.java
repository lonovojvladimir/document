package com.lonovojvladimir.document.document.controller;

import com.lonovojvladimir.document.audit.dto.AuditDocumentDto;
import com.lonovojvladimir.document.audit.service.AuditDocumentService;
import com.lonovojvladimir.document.document.dto.*;
import com.lonovojvladimir.document.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;
    private final AuditDocumentService auditDocumentService;

    @GetMapping("{idDocument}")
    public DocumentDto getDocument(@PathVariable Long idDocument) {
        return documentService.getDocument(idDocument);
    }

    @GetMapping("/all")
    public Page<DocumentDto> getAllDocuments(int page, int size, String term) {
        return documentService.getAllDocuments(page, size, term);
    }

    @PostMapping("/allByFilter")
    public List<DocumentDto> getAllDocumentsByFilter(@RequestBody DocumentFilterDto documentFilterDto) {
        return documentService.getAllDocumentsByFilter(documentFilterDto);
    }

    @PostMapping("/create")
    public DocumentDto createDocument(@RequestBody DocumentCreateDto documentCreateDto) throws Exception {
        return documentService.createDocument(documentCreateDto);
    }

    @PutMapping("/update/{idDocument}")
    public DocumentDto updateDocument(@PathVariable Long idDocument, @RequestBody DocumentUpdateDto documentUpdateDto) {
        return documentService.updateDocument(idDocument, documentUpdateDto);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) throws Exception {
        return documentService.uploadDocument(file);
    }

    @GetMapping("/donwload/{fileName}")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        return documentService.downloadFile(fileName);
    }

    @DeleteMapping("/{idDocument}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long idDocument){
        return documentService.deleteDocument(idDocument);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) throws Exception {
        return documentService.deleteFile(fileName);
    }

    @GetMapping("/history/{idDocument}")
    public Page<AuditDocumentDto> getHistory(@PathVariable Long idDocument, int page, int size){
        return auditDocumentService.getHistory(idDocument, page, size);
    }

}
