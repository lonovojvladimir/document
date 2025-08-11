package com.lonovojvladimir.document.controller;

import com.lonovojvladimir.document.audit.dto.AuditDocumentDto;
import com.lonovojvladimir.document.audit.service.AuditDocumentService;
import com.lonovojvladimir.document.document.controller.DocumentController;
import com.lonovojvladimir.document.document.dto.DocumentCreateDto;
import com.lonovojvladimir.document.document.dto.DocumentDto;
import com.lonovojvladimir.document.document.dto.DocumentFilterDto;
import com.lonovojvladimir.document.document.dto.DocumentUpdateDto;
import com.lonovojvladimir.document.document.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentControllerTest {
    @Mock
    private DocumentService documentService;
    @Mock
    private AuditDocumentService auditDocumentService;
    @InjectMocks
    private DocumentController documentController;

    Long documentId = 1L;
    DocumentDto expectedDto = new DocumentDto();

    @Test
    void getDocument_ReturnsDocumentDto() {
        expectedDto.setIdDocument(documentId);
        expectedDto.setTitle("Test Title");
        when(documentService.getDocument(documentId)).thenReturn(expectedDto);
        DocumentDto result = documentController.getDocument(documentId);
        assertNotNull(result);
        assertEquals(documentId, result.getIdDocument());
        assertEquals("Test Title", result.getTitle());
        verify(documentService).getDocument(documentId);
    }

    @Test
    void testGetAllDocuments() {
        int page = 0, size = 10;
        String term = "example";
        expectedDto.setIdDocument(documentId);
        expectedDto.setTitle("Test Title");
        Page<DocumentDto> mockPage = new PageImpl<>(List.of(expectedDto));
        when(documentService.getAllDocuments(page, size, term)).thenReturn(mockPage);
        Page<DocumentDto> result = documentController.getAllDocuments(page, size, term);
        assertEquals(documentId, result.getContent().size());
        assertNotNull(result.getContent());
        verify(documentService).getAllDocuments(page, size, term);
    }

    @Test
    void testGetAllDocumentsByFilter() {
        DocumentFilterDto filter = new DocumentFilterDto();
        expectedDto.setIdDocument(documentId);
        expectedDto.setTitle("Test Title");
        List<DocumentDto> expected = List.of(expectedDto);
        Mockito.when(documentService.getAllDocumentsByFilter(filter)).thenReturn(expected);
        List<DocumentDto> result = documentController.getAllDocumentsByFilter(filter);
        assertEquals(expected, result);
        assertNotNull(result);
        verify(documentService).getAllDocumentsByFilter(filter);
    }

    @Test
    void testCreateDocument() throws Exception {
        expectedDto.setIdDocument(documentId);
        expectedDto.setTitle("Test Title");
        DocumentCreateDto dto = new DocumentCreateDto(); // заполни при необходимости
        when(documentService.createDocument(dto)).thenReturn(expectedDto);
        DocumentDto result = documentController.createDocument(dto);
        assertEquals(expectedDto, result);
        assertNotNull(result);
        verify(documentService).createDocument(dto);
    }

    @Test
    void testUpdateDocument() {
        expectedDto.setIdDocument(documentId);
        expectedDto.setTitle("Test Title");
        DocumentUpdateDto dto = new DocumentUpdateDto(); // заполни при необходимости
        when(documentService.updateDocument(documentId, dto)).thenReturn(expectedDto);
        DocumentDto result = documentController.updateDocument(documentId, dto);
        assertEquals(expectedDto, result);
        assertNotNull(result);
        verify(documentService).updateDocument(documentId, dto);
    }

    @Test
    void testUploadDocument() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        ResponseEntity<String> expected = ResponseEntity.ok("Uploaded");
        when(documentService.uploadDocument(file)).thenReturn(expected);
        ResponseEntity<String> result = documentController.uploadDocument(file);
        assertEquals("Uploaded", result.getBody());
        assertNotNull(result);
        verify(documentService).uploadDocument(file);
    }

    @Test
    void testDownloadFile() {
        String fileName = "doc.pdf";
        Resource resource = mock(Resource.class);
        ResponseEntity<Resource> expected = ResponseEntity.ok(resource);
        when(documentService.downloadFile(fileName)).thenReturn(expected);
        ResponseEntity<Resource> result = documentController.downloadFile(fileName);
        assertEquals(resource, result.getBody());
        assertNotNull(result);
        verify(documentService).downloadFile(fileName);
    }

    @Test
    void testDeleteDocument() {
        ResponseEntity<String> expected = ResponseEntity.ok("Deleted");
        when(documentService.deleteDocument(documentId)).thenReturn(expected);
        ResponseEntity<String> result = documentController.deleteDocument(documentId);
        assertEquals("Deleted", result.getBody());
        assertNotNull(result);
        verify(documentService).deleteDocument(documentId);
    }

    @Test
    void testDeleteFile() throws Exception {
        String fileName = "file.docx";
        ResponseEntity<String> expected = ResponseEntity.ok("File Deleted");
        when(documentService.deleteFile(fileName)).thenReturn(expected);
        ResponseEntity<String> result = documentController.deleteFile(fileName);
        assertEquals("File Deleted", result.getBody());
        assertNotNull(result);
        verify(documentService).deleteFile(fileName);
    }

    @Test
    void testGetAllDocumentHistorys() {
        int page = 0, size = 10;
        AuditDocumentDto expectedHistoryDto = new AuditDocumentDto();
        Page<AuditDocumentDto> mockPage = new PageImpl<>(List.of(expectedHistoryDto));
        when(auditDocumentService.getHistory(documentId, page, size)).thenReturn(mockPage);
        Page<AuditDocumentDto> result = auditDocumentService.getHistory(documentId, page, size);
        assertEquals(documentId, result.getContent().size());
        assertNotNull(result.getContent());
        verify(auditDocumentService).getHistory(documentId, page, size);
    }

}
