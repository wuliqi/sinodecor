package cn.app118.framework.util;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPrintUtil {

	public static void main(String[] args) {
		try {
			Workbook wb = new XSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream("d:\\2014年9月26日空气卫士安装费用清单.xlsx");
			Sheet sheet1 = wb.createSheet("空气卫士安装费用清单");
		    
		    CreationHelper createHelper = wb.getCreationHelper();
		    
		    CellStyle borderStyle = wb.createCellStyle();
	        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
	        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
	        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		    
		    Row row = sheet1.createRow((short)0);
		    Cell cell = row.createCell(0);
	        cell.setCellValue("空气卫士－三里屯体验店");
	        sheet1.addMergedRegion(new CellRangeAddress(
	                1, //first row (0-based)
	                1, //last row  (0-based)
	                0, //first column (0-based)
	                6  //last column  (0-based)
	        ));
	        cell.setCellStyle(borderStyle);
		    

		    row = sheet1.createRow(1);
		    cell = row.createCell(0);
		    cell.setCellValue("吴理琪");
		    cell = row.createCell(1);
		    cell.setCellValue(new Date());
		    CellStyle cellStyle = wb.createCellStyle();
		    cellStyle.setDataFormat(
		        createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		    cell = row.createCell(2);
		    cell.setCellValue(new Date());
		    cell.setCellStyle(cellStyle);
		    cell = row.createCell(3);
		    cell.setCellValue(Calendar.getInstance());
		    cell.setCellStyle(cellStyle);


		    row = sheet1.createRow((short)2);
		    row.createCell(0).setCellValue(1.1);
		    row.createCell(1).setCellValue(new Date());
		    row.createCell(2).setCellValue(Calendar.getInstance());
		    row.createCell(3).setCellValue("a string");
		    row.createCell(4).setCellValue(true);
		    row.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);


		    row = sheet1.createRow((short) 3);
	        row.setHeightInPoints(30);

	        createCell(wb, row, (short) 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM);
	        createCell(wb, row, (short) 1, CellStyle.ALIGN_CENTER_SELECTION, CellStyle.VERTICAL_BOTTOM);
	        createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER);
	        createCell(wb, row, (short) 3, CellStyle.ALIGN_GENERAL, CellStyle.VERTICAL_CENTER);
	        createCell(wb, row, (short) 4, CellStyle.ALIGN_JUSTIFY, CellStyle.VERTICAL_JUSTIFY);
	        createCell(wb, row, (short) 5, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_TOP);
	        createCell(wb, row, (short) 6, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_TOP);

		    
	        row = sheet1.createRow(4);
	        cell = row.createCell(1);
	        cell.setCellValue(4);
	        CellStyle style = wb.createCellStyle();
	        style.setBorderBottom(CellStyle.BORDER_THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(CellStyle.BORDER_THIN);
	        style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
	        style.setBorderRight(CellStyle.BORDER_THIN);
	        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
	        style.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        cell.setCellStyle(style);
	        
	       /* row = sheet1.createRow(5);
	        cell = row.createCell(3);
	        cell.setCellValue("工单编号");
	        CellStyle borderStyle = wb.createCellStyle();
	        borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderRight(CellStyle.BORDER_THIN);
	        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderTop(CellStyle.BORDER_THIN);
	        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        cell.setCellStyle(borderStyle);*/

	        
	        row = sheet1.createRow((short) 6);
	        style = wb.createCellStyle();
	        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
	        style.setFillPattern(CellStyle.BIG_SPOTS);
	        cell = row.createCell((short) 1);
	        cell.setCellValue("X");
	        cell.setCellStyle(style);
	        
	        style = wb.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        cell = row.createCell((short) 2);
	        cell.setCellValue("X");
	        cell.setCellStyle(style);

	       

	        // Create a new font and alter it.
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)24);
	        font.setFontName("Courier New");
	        font.setItalic(true);
	        font.setStrikeout(true);
	        
	        row = sheet1.createRow((short)8);
	        CellStyle fontStyle = wb.createCellStyle();
	        fontStyle.setFont(font);
	        cell = row.createCell(1);
	        cell.setCellValue("This is a test of fonts");
	        cell.setCellStyle(fontStyle);

	        row = sheet1.createRow(9);
	        cell = row.createCell(2);
	        cell.setCellValue("Use  with word wrap \n on  to create a new line");
	        CellStyle cs = wb.createCellStyle();
	        cs.setWrapText(true);
	        cell.setCellStyle(cs);
	        row.setHeightInPoints((2*sheet1.getDefaultRowHeightInPoints()));
	        sheet1.autoSizeColumn((short)2);


	        DataFormat format = wb.createDataFormat();
	        row = sheet1.createRow(10);
	        cell = row.createCell(0);
	        cell.setCellValue(11111.25);
	        style = wb.createCellStyle();
	        style.setDataFormat(format.getFormat("0.0"));
	        cell.setCellStyle(style);

	        row = sheet1.createRow(11);
	        cell = row.createCell(0);
	        cell.setCellValue(11111.25);
	        style = wb.createCellStyle();
	        style.setDataFormat(format.getFormat("#,##0.0000"));
	        cell.setCellStyle(style);
	        
	        //适合于一页
	        PrintSetup ps = sheet1.getPrintSetup();
	        sheet1.setAutobreaks(true);
	        ps.setFitHeight((short)1);
	        ps.setFitWidth((short)1);

	        //打印区域
	        wb.setPrintArea(0, "$A$1:$C$2");
	        wb.setPrintArea(
	                0, //sheet index
	                0, //start column
	                6, //end column
	                0, //start row
	                12  //end row
	        );

	        //页码
	        Footer footer = sheet1.getFooter();
	        footer.setRight( "第" + HeaderFooter.page() + "页，共" + HeaderFooter.numPages() +"页");
	        
	    

	       


	      



		    
			
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb     the workbook
     * @param row    the row to create the cell in
     * @param column the column number to create the cell in
     * @param halign the horizontal alignment for the cell.
     */
    private static void createCell(Workbook wb, Row row, short column, short halign, short valign) {
        Cell cell = row.createCell(column);
        cell.setCellValue("Align It");
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }


}
