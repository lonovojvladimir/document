//package ru.com.document.document.report.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RestController;
//import ru.com.document.dict.report.api.DocumentReportApi;
//import ru.com.document.document.report.helper.ExportWrapper;
//import ru.com.document.document.report.helper.ReportType;
//import ru.com.document.document.report.service.DocumentReportService;
//
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//@RequiredArgsConstructor
//public class DocumentReportController implements DocumentReportApi {
//    private final DocumentReportService reportService;
//
//    @Override
//    public void getDocumentReport(HttpServletResponse response, long idDocument) throws Exception {
//        ReportType reportType = ReportType.DOCX;
////        BOMTitleReportEntity bomTitleReportEntity = bomTitleReportRepository.getTitleForReport(id_parent);
//        ExportWrapper.wrap(response, reportService.getDocumentReport(idDocument), reportType, "WordDocumentReport"+ "-" + idDocument);
//    }
//}
