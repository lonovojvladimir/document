//package ru.com.document.document.report.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.com.document.document.report.create.CreateDocumentReportDocx;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//@Service
//public class DocumentReportService {
//
//    private final CreateDocumentReportDocx createDocumentReportDocx;
//
//    public ByteArrayOutputStream getDocumentReport(long idDocument) throws IOException {
//        return createDocumentReportDocx.createDocumentReportDocx(idDocument);
//    }
//}
