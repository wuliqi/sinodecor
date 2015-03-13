/**
 * @(#)UserAction.java	09/19/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-19
 */
package cn.app118.framework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.app118.model.Code;
import cn.app118.service.code.ICodeService;

/**
 * Excel导入导出工具类
 * 
 * @author wRitchie
 * 
 */
public class ExcelUtil {

	@Resource
	private static ICodeService codeService;// 代码管理服务类

	public static void readExcel(String fileName) {
		boolean isE2007 = false; // 判断是否是excel2007格式
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // 建立输入流
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			StringBuilder sb = new StringBuilder();
			while (rows.hasNext()) {
				Row row = rows.next(); // 获得行数据
				System.out.println("Row #" + row.getRowNum()); // 获得行号从0开始
				Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
				while (cells.hasNext()) {
					Cell cell = cells.next();
					System.out.println("Cell #" + cell.getColumnIndex());

					switch (cell.getCellType()) { // 根据cell中的类型来输出数据
					case HSSFCell.CELL_TYPE_NUMERIC:
						System.out.println(cell.getNumericCellValue());
						sb.append(cell.getNumericCellValue() + "\t");
						break;
					case HSSFCell.CELL_TYPE_STRING:
						System.out.println(cell.getStringCellValue());
						sb.append(cell.getStringCellValue() + "\t");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						System.out.println(cell.getBooleanCellValue());
						sb.append(cell.getBooleanCellValue() + "\t");
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						System.out.println(cell.getCellFormula());
						sb.append(cell.getCellFormula() + "\t");
						break;
					default:
						System.out.println("unsuported sell type");
						break;
					}
				}
			}
			System.out.println("##########:" + sb.toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void readExcelBrand(String fileName) {
		try {
			// 读取文件
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					fileName));
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			Row row = null;
			Cell cell_a = null;
			for (int i = firstRowNum; i <= lastRowNum; i++) {
				row = sheet.getRow(i); // 取得第i行
				if (row != null) {
					cell_a = row.getCell(0); // 取得i行的第一列
					String cellValue = cell_a.getStringCellValue().trim();
					if (!StringUtil.isEmpty(cellValue)) {

						System.out.println(cellValue);
						Map param = new HashMap();
						param.put("codeName", cellValue);
						List<Map> list = codeService.selectBySelective(param);
						if(list.size()>0){
							System.out.println(cellValue+"存在");
						}
						Code code = new Code();
						code.setCreateTime(new Date());
						code.setSort(10000);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
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
                   
					if(columnNumber==11||columnNumber==12||columnNumber==13){
//                    	System.out.println("####8-10###:"+rowData[columnNumber]);
                    	//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                    	DataFormat format = wb.createDataFormat();
//                    	cellStyle.setDataFormat(format.getFormat("#,##0.00"));
//                    	cell.setCellStyle(cellStyle);
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
  
      
    public static void main(String[] args) throws FileNotFoundException, IOException { 
    	//     
        String[] title1={"销售时间 ","所属组织机构","销售员","产品类型","车型", "产品型号","服务类型","数量","产品原价","实收金额","客户名称","客户电话","客户地址"}; 
//        String[] title2={"第一列d","第二列e","第三列f"}; 
//        String[] title3={"第一列h","第二列i","第三列j"}; 
          
        List<String[]> titles=new ArrayList<String[]>(); 
        titles.add(title1); 
//        titles.add(title2); 
//        titles.add(title3); 
          
        String []data1={"销售时间 ","所属组织机构","销售员","产品类型","车型", "产品型号","服务类型","数量","产品原价","实收金额","客户名称","客户电话","客户地址"}; 
        String []data2={"销售时间 ","所属组织机构","销售员","产品类型","车型", "产品型号","服务类型","数量","产品原价","实收金额","客户名称","客户电话","客户地址"}; 
        String []data3={"销售时间 ","所属组织机构","销售员","产品类型","车型", "产品型号","服务类型","数量","产品原价","实收金额","客户名称","客户电话","客户地址"}; 
          
        List<String[]> data=new ArrayList<String[]>(); 
        data.add(data1); 
        data.add(data2); 
        data.add(data3); 
        List<List<String[]>> data_=new ArrayList<List<String[]>>(); 
        data_.add(data); 
        String[] sheetName={"sheet1"}; //,"sheet2","sheet3"
        ExcelUtil.writeToFile("D:\\销售统计报表.xlsx", sheetName, titles, data_);
        System.out.println("导出成功。");
    } 


	/*public static void main(String[] args) {
		ExcelUtil.readExcelBrand("E:\\brand.xls");

	}*/

}
