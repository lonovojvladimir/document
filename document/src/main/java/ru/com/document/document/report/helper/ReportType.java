//package ru.com.document.document.report.helper;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//
//public enum ReportType {
//    PDF("application/pdf", "pdf"),
//    CSV("text/csv", "csv"),
//    XLS("application/vnd.ms-excel", "xls"),
//    XLSX("application/vnd.ms-excel", "xlsx"),
//    HTML("application/html", "html"),
//    DOCX("application/msword", "docx");
//
//    /**
//     * Content type header
//     */
//    private String contentType;
//    /**
//     * Report file extension
//     */
//    private String extension;
//
//    /**
//     * Constructor
//     *
//     * @param contentType - String: type of content;
//     * @param extension - String: file extension;
//     */
//    ReportType(String contentType, String extension) {
//        this.contentType = contentType;
//        this.extension = extension;
//    }
//
//    /**
//     * Gets enum value by given file extension.
//     *
//     * @param extension - String: file extension;
//     * @return ReportType: enum value
//     */
//    @JsonCreator
//    public static ReportType byExtension(String extension) {
//        for (ReportType value : values()) {
//            if (value.extension.equalsIgnoreCase(extension)) {
//                return value;
//            }
//        }
//
//        throw new IllegalArgumentException("The incoming value of enum is not registered.");
//    }
//
//    public String getContentType() {
//        return contentType;
//    }
//
//    public String getExtension() {
//        return extension;
//    }
//}
