/**
 * @(#)UserAction.java	09/19/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-19
 */
package cn.app118.framework.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.app118.service.code.ICodeService;

/**
 * 充值卡查询页Excel导入导出工具类
 * 
 * @author wRitchie
 * 
 */
public class CardExcelUtil {
	@Resource
	private static ICodeService codeService;// 代码管理服务类
	
	/** 
     * 将数据写入到Excel文件 
     * @param filePath 文件路径 
     * @param sheetName 工作表名称 
     * @param title 工作表标题栏 
     * @param data 工作表数据 
     * @throws FileNotFoundException 文件不存在异常 
     * @throws IOException IO异常 
     */
    public static void writeToFile(String filePath,String[] sheetName,List<? extends Object[]> title,List<? extends List<? extends Object[]>> data) throws FileNotFoundException, IOException{ 
        //创建并获取工作簿对象 
        Workbook wb=getWorkBook( sheetName, title, data); 
        //写入到文件 
        FileOutputStream out=new FileOutputStream(filePath); 
        wb.write(out); 
        out.close(); 
    } 
      
    /** 
     * 创建工作簿对象<br> 
     * <font color="red">工作表名称，工作表标题，工作表数据最好能够对应起来</font><br> 
     * 比如三个不同或相同的工作表名称，三组不同或相同的工作表标题，三组不同或相同的工作表数据<br> 
     * <b> 
     * 注意：<br> 
     * 需要为每个工作表指定<font color="red">工作表名称，工作表标题，工作表数据</font><br> 
     * 如果工作表的数目大于工作表数据的集合，那么首先会根据顺序一一创建对应的工作表名称和数据集合，然后创建的工作表里面是没有数据的<br> 
     * 如果工作表的数目小于工作表数据的集合，那么多余的数据将不会写入工作表中 
     * </b> 
     * @param sheetName 工作表名称的数组 
     * @param title 每个工作表名称的数组集合 
     * @param data 每个工作表数据的集合的集合 
     * @return Workbook工作簿 
     * @throws FileNotFoundException 文件不存在异常 
     * @throws IOException IO异常 
     */
    public static Workbook getWorkBook(String[] sheetName,List<? extends Object[]> title,List<? extends List<? extends Object[]>> data) throws FileNotFoundException, IOException{ 
        //创建工作簿，支持2007及以后的文档格式 
        Workbook wb = new XSSFWorkbook(); 
        //创建一个工作表sheet 
        Sheet sheet = null; 
        //申明行 
        Row row = null; 
        //申明单元格 
        Cell cell = null; 
        //单元格样式 
        CellStyle titleStyle=wb.createCellStyle(); 
        CellStyle cellStyle=wb.createCellStyle(); 
        //字体样式 
        Font font=wb.createFont(); 
        //粗体 
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); 
        titleStyle.setFont(font); 
        //水平居中   
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER); 
        //垂直居中   
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
        //水平居中   
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); 
        //垂直居中   
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); 
        //标题数据 
        Object[] title_temp=null; 
        //行数据 
        Object[] rowData=null; 
        //工作表数据 
        List<? extends Object[]> sheetData=null; 
        //遍历sheet 
        for(int sheetNumber=0;sheetNumber<sheetName.length;sheetNumber++){ 
            //创建工作表 
            sheet=wb.createSheet(); 
            //设置工作表名称 
            wb.setSheetName(sheetNumber, sheetName[sheetNumber]); 
            //设置标题 
            title_temp=title.get(sheetNumber); 
            row=sheet.createRow(0); 
  
            //写入标题 
            for(int i=0;i<title_temp.length;i++){ 
                cell=row.createCell(i); 
                cell.setCellStyle(titleStyle); 
                cell.setCellValue(title_temp[i].toString());
            } 
              
            try { 
                sheetData=data.get(sheetNumber); 
            } catch (Exception e) { 
                continue; 
            } 
            //写入行数据 
            for(int rowNumber=0;rowNumber<sheetData.size();rowNumber++){ 
            	//sheet.autoSizeColumn(rowNumber);  //只能解决英文、数字列宽自适应，如果该列为中文，会出现列宽不足现象。 
                //如果没有标题栏，起始行就是0，如果有标题栏，行号就应该为1 
                row=sheet.createRow(title_temp==null?rowNumber:(rowNumber+1)); 
                rowData=sheetData.get(rowNumber); 
                for(int columnNumber=0;columnNumber<rowData.length;columnNumber++){ 
                    cell=row.createCell(columnNumber); 
                    cell.setCellStyle(cellStyle); 
                    String cellValue = rowData[columnNumber].toString();
					if(columnNumber==3){
                    	if(StringUtil.isEmpty(cellValue)){
                    		cellValue="0";
                    	}
                    	cell.setCellValue(Double.parseDouble(StringUtil.trimNull(cellValue)));
                    }else{
                    	int cellLength=cellValue.getBytes().length;//首先通过cellValue.getBytes().length方法对比找到该列数据最大长度
                    	sheet.setColumnWidth(columnNumber,cellLength*2*256);//手动设置列宽。长度乘以2是为了解决纯数字列宽度不足会显示科学计数法问题，乘以256得到的数据才是excel真实列宽

                    	cell.setCellValue(cellValue); 
                    }
                } 
            } 
        } 
        return wb; 
    } 
}
