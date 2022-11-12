//package ru.com.document.dict.report.api;
//
//import io.swagger.annotations.*;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//
//@Api(value = "API for Document report operations")
//@RequestMapping("/api/document/report")
//public interface DocumentReportApi {
//
//    @ApiOperation(value = "Download object msword report",
//            produces = "application/msword"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "")
//    })
//    @GetMapping(value = "/msword/{idDocument}")
//    void getDocumentReport(
//            HttpServletResponse response,
//            @ApiParam(value = "Repo identifier")
//            @PathVariable("idDocument") long idDocument
//    ) throws Exception;
//
//
//}
