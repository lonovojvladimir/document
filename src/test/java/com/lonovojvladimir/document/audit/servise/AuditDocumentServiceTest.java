package com.lonovojvladimir.document.audit.servise;

import com.lonovojvladimir.document.audit.dto.AuditDocumentDto;
import com.lonovojvladimir.document.audit.entity.AuditDocumentEntity;
import com.lonovojvladimir.document.audit.mapper.AuditDocumentMapper;
import com.lonovojvladimir.document.audit.repository.AuditDocumentRepository;
import com.lonovojvladimir.document.audit.service.AuditDocumentService;
import com.lonovojvladimir.document.document.dto.DocumentDto;
import com.lonovojvladimir.document.document.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuditDocumentServiceTest {
    @Mock
    private AuditDocumentRepository auditDocumentRepository;

    @Mock
    private AuditDocumentMapper auditDocumentMapper;

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private AuditDocumentService auditDocumentService;




    @Test
    void testGetHistory_shouldReturnMappedPage() {
        int page = 1, size = 10;
        Long documentId = 1L;
        DocumentDto documentDto = new DocumentDto();
        documentDto.setCode("test.docx");
        AuditDocumentDto auditDocumentDto = new AuditDocumentDto();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "idAuditDocument"));
        List<AuditDocumentEntity> auditDocumentEntityList = List.of(new AuditDocumentEntity());
        Page<AuditDocumentEntity> entitiesPage = new PageImpl<>(auditDocumentEntityList, pageable, auditDocumentEntityList.size());
        when(documentService.getDocument(documentId)).thenReturn(documentDto);
        when(auditDocumentRepository.findAuditDocumentEntitiesByCode(eq("test.docx"), any(Pageable.class))).thenReturn(entitiesPage);
        when(auditDocumentMapper.toDto(new AuditDocumentEntity())).thenReturn(auditDocumentDto);
        Page<AuditDocumentDto> result = auditDocumentService.getHistory(documentId, 1, 10);
        assertEquals(1, result.getContent().size());
        verify(documentService).getDocument(documentId);
        verify(auditDocumentRepository).findAuditDocumentEntitiesByCode(any(),  eq(pageable));
    }


    @Test
    void testDeleteHistory_shouldCallRepository() {
        Long documentId = 1L;
        DocumentDto documentDto = new DocumentDto();
        documentDto.setCode("test.docx");
        Iterable<Long> ids = List.of(1L, 2L, 3L);
        when(documentService.getDocument(documentId)).thenReturn(documentDto);
        when(auditDocumentRepository.findAuditDocumentEntitiesByCodeList("test.docx")).thenReturn(ids);
        auditDocumentService.deleteHistory(documentId);
        verify(auditDocumentRepository).deleteAllById(ids);
    }
}
