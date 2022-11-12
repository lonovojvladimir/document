//package ru.com.document.document.report.helper;
//
//
//import org.apache.commons.io.IOUtils;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//
//
//public final class ExportWrapper {
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
//
//    /**
//     * Default constructor disabled.
//     */
//    private ExportWrapper() {
//    }
//
//    /**
//     * Performs the wrapping of report export data into {@link HttpServletResponse}.
//     * <p />Method also sets 'Content-Disposition', 'Content-Type' and 'Content-Length' response headers.
//     *
//     * @param response     current instance of {@link HttpServletResponse}.
//     * @param baos         stream with data.
//     * @param type         report type.
//     * @param fileName Desired filename without an extension.
//     * @throws IOException In case of errors during stream copying.
//     */
//    public static void wrap(HttpServletResponse response, ByteArrayOutputStream baos, ReportType type,
//                            String fileName) throws IOException {
//        String filename = fileName + "." + type.getExtension();
//        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//        response.setContentType(type.getContentType());
//        response.setContentLength(baos.size());
//        IOUtils.write(baos.toByteArray(), response.getOutputStream());
//    }
//}
