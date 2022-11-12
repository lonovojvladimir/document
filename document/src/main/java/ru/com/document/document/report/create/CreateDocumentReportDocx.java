//package ru.com.document.document.report.create;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.poi.xwpf.usermodel.*;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//@Service
//public class CreateDocumentReportDocx {
//
//
//    public ByteArrayOutputStream createDocumentReportDocx(long idDocument) throws IOException {
//        String fileName = "reports/Report.docx";
//        InputStream is = Model.class.getClassLoader().getResourceAsStream(fileName);
//
//        XWPFDocument document = new XWPFDocument(is);
////        XWPFTable table = document.getTables().get(0);
////        RoomReportAssetEntity asset = reportAssetRepository.getRoomReportAssetEntitiesBy(id_repo);
////        copyRow(table,1,0,asset.getCol1()==null ? "" : asset.getCol1().toString());
////        for(RoomReportPositionEntity p : reportPositionRepository.getRoomReportPositionEntitiesBy(id_repo)){
////            copyRow(table,1,0,p.getObjTag()==null ? "" : p.getObjTag().toString());
////            Long id_repo_obj = p.getIdObj();
////
////            for (RoomReportLevelEntity l : reportLevelRepository.getRoomReportLevelEntitiesBy(id_repo_obj)){
////                copyRow(table,1,0,l.getObjTag()==null ? "" : l.getObjTag().toString());
////                Long id_repo_room = l.getIdObj();
////
////                for(RoomReportEntity r : reportRepository.getRoomReportEntitiesBy(id_repo_room)){
////
////                    CTRow ctRow = CTRow.Factory.newInstance();
////                    XWPFTableRow row = table.getRow(2);
////                    ctRow.set(row.getCtRow());
////                    XWPFTableRow row2 = new XWPFTableRow(ctRow, table);
////
////                    XWPFTableCell cell1 = row2.getCell(0);
////                    XWPFParagraph par = cell1.getParagraphs().get(0);
////                    XWPFRun ru = par.createRun();
////                    ru.setText(r.getObjTag()==null ? "" : r.getObjTag().toString());
////                    ru.setFontFamily("Times New Roman");
////                    ru.setFontSize(12);
////
////                    XWPFTableCell cell2 = row2.getCell(1);
////                    XWPFParagraph par2 = cell2.getParagraphs().get(0);
////                    XWPFRun ru2 = par2.createRun();
////                    ru2.setText(r.getCol1()==null ? "" : r.getCol1().toString());
////                    ru2.setFontFamily("Times New Roman");
////                    ru2.setFontSize(12);
////
////                    XWPFTableCell cell3 = row2.getCell(2);
////                    XWPFParagraph par3 = cell3.getParagraphs().get(0);
////                    XWPFRun ru3 = par3.createRun();
////                    ru3.setText(r.getCol2()==null ? "" : r.getCol2().toString());
////                    ru3.setFontFamily("Times New Roman");
////                    ru3.setFontSize(12);
////
////                    XWPFTableCell cell4 = row2.getCell(3);
////                    XWPFParagraph par4 = cell4.getParagraphs().get(0);
////                    XWPFRun ru4 = par4.createRun();
////                    ru4.setText(r.getCol3()==null ? "" : r.getCol3().toString());
////                    ru4.setFontFamily("Times New Roman");
////                    ru4.setFontSize(12);
////
////                    table.addRow(row2);
////                }
////            }
////        }
////        table.removeRow(1);
////        table.removeRow(1);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        document.write(byteArrayOutputStream);
//        return byteArrayOutputStream;
//    }
//
//
//
//}
