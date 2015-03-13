/**
 * @(#)UserAction.java	09/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-09-15
 */
package cn.app118.action.code;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.app118.action.common.BaseAction;
import cn.app118.constants.SystemConstant;
import cn.app118.framework.util.DateUtil;
import cn.app118.framework.util.StringUtil;
import cn.app118.model.Code;
import cn.app118.service.code.ICodeService;

/**
 * 通用代码管理控制类
 * 
 * @author wRitchie
 * 
 */
@Controller
@RequestMapping("codeAction")
public class CodeAction extends BaseAction {

	@Resource
	private ICodeService codeService;// 代码管理服务类

	/**
	 * 新增代码管理初始
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("toAddCode")
	public ModelAndView toAddModelCode(String type) {
		ModelAndView mv = new ModelAndView("/pages/code/addCode.jsp");
		Map<String, String> statusMap = SystemConstant.getCodeStatusMap();
		statusMap.remove("4");// 去掉已删除
		mv.addObject("statusMap", statusMap);

		Map param = new HashMap();
		if (!StringUtil.isEmpty(type)) {
			param.put("type", type);
		}
		Map<String, String> codeMap = new TreeMap<String, String>();// 可供下拉选择的
																	// 父代码类别标识、名称对
		List<Map> list = codeService.selectBySelective(param);
		for (Map oneMap : list) {
			codeMap.put(oneMap.get("codeId") + "", oneMap.get("codeName") + "");
		}

		Map<String, String> typeMap = SystemConstant.getCodeTypeMap();// 所属种类

		mv.addObject("typeMap", typeMap);
		mv.addObject("codeMap", codeMap);
		mv.addObject("type", type);
		return mv;
	}

	/**
	 * 保存代码
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping("/saveCode")
	public ModelAndView saveCode(Code code) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/code/addCode.jsp");
		code.setCreateTime(new Date());
		code.setSort(10000);
		try {
			int result = codeService.insert(code);
			if (result > 0) {
				mv.addObject("message", "success");
				mv.addObject("info", "增加代码成功。");
			} else {
				mv.addObject("message", "error");
				mv.addObject("info", "增加代码失败。");
			}
		} catch (Exception e) {
			mv.addObject("message", "error");
			mv.addObject("info", "增加代码异常。");
			log.info("增加代码异常:" + e);
		}
		return mv;
	}

	/**
	 * 代码管理列表初始页
	 * 
	 * @return
	 */
	@RequestMapping("initCode")
	public ModelAndView initCode() {
		ModelAndView mv = new ModelAndView("/pages/code/listCode.jsp");
		Map<String, String> map = SystemConstant.getCodeTypeMap();// 所属种类
		map.remove("2");//过滤器品牌
		map.remove("4");//过滤器型号
		mv.addObject("typeMap", map);
		return mv;
	}

	/**
	 * 代码管理列表分页查询
	 * 
	 * @param curNo
	 * @param curSize
	 * @param codeName
	 * @param type
	 * @param fromCreateTime
	 * @param toCreateTime
	 * @return
	 */
	@RequestMapping("listCode")
	@ResponseBody
	public Map<String, Object> listCode(String curNo, String curSize,String sortname,String sortorder,
			String type, String codeName, String fromCreateTime,
			String toCreateTime) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		/************* 分页处理 ****************/
		int skip;
		int max;
		if (curNo == null || "".equals(curNo))
			curNo = "0";
		if (curSize == null || "".equals(curSize))
			curSize = SystemConstant.PAGER_CURSIZE;
		skip = Integer.parseInt(curNo);
		max = Integer.parseInt(curSize);
		int start = (skip - 1) * max;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("len", max);
		/************* 分页处理 ****************/

		if (!StringUtil.isEmpty(type)) {
			map.put("type", type);
		}
		if (!StringUtil.isEmpty(codeName)) {
			map.put("codeName", codeName);
		}

		if (!StringUtil.isEmpty(fromCreateTime)) {
			map.put("fromCreateTime", fromCreateTime);
		}
		if (!StringUtil.isEmpty(toCreateTime)) {
			map.put("toCreateTime", toCreateTime);
		}
		map.put("noHepatype", "and type !=2 and type!=4");//去掉过滤器品牌、型号
		String orderbyStr = null;
		if (!StringUtil.isEmpty(sortname)) {
			if ("codeId".equals(sortname)) {
				orderbyStr = "order by codeId " + sortorder;
			} else if ("typeCn".equals(sortname)) {
				orderbyStr = "order by type " + sortorder;
			} else if ("codeName".equals(sortname)) {
				orderbyStr = "order by code_name " + sortorder;
			} else if ("codeValue".equals(sortname)) {
				orderbyStr = "order by code_value " + sortorder;
			} else if ("pCodeName".equals(sortname)) {
				orderbyStr = "order by p_code " + sortorder;
			}else if ("status".equals(sortname)) {
				orderbyStr = "order by status " + sortorder;
			}else if ("createTime".equals(sortname)) {
				orderbyStr = "order by create_time " + sortorder;
			}else if ("descr".equals(sortname)) {
				orderbyStr = "order by descr " + sortorder;
			}
		} else {
			orderbyStr = "order by code_name asc";
		}
		map.put("orderBy", orderbyStr);
		
		List<Map> list = new ArrayList<Map>();
		list = codeService.selectByPager(map);
		for (Map oneMap : list) {
			Date d = (Date) oneMap.get("createTime");
			if (null != d) {
				oneMap.put("createTime", DateUtil.getFormatDate(d, ""));// 创建时间
			}

			oneMap.put("status",
					SystemConstant.getCodeStatusMap().get(oneMap.get("status")));// 状态

			oneMap.put("typeCn",
					SystemConstant.getCodeTypeMap().get(oneMap.get("type")));// 所属种类

			String pCode = oneMap.get("pCode") + "";
			if (!StringUtil.isEmpty(pCode)) {
				Code code = codeService.selectByPrimaryKey(Integer.valueOf(pCode));
				if(code!=null)
				oneMap.put("pCodeName", code.getCodeName());
			}
		}

		int allSize = codeService.selectByPagerCount(map);

		jsonMap.put("codeName", codeName);
		jsonMap.put("type", type);
		jsonMap.put("fromCreateTime", fromCreateTime);
		jsonMap.put("toCreateTime", toCreateTime);

		jsonMap.put("Rows", list);
		jsonMap.put("Total", allSize);
		return jsonMap;
	}

	/**
	 * 修改代码管理初始页
	 * 
	 * @return
	 */
	@RequestMapping("toUpdCode")
	public ModelAndView toUpdCode(Integer codeId, String type) {
		ModelAndView mv = new ModelAndView("/pages/code/updCode.jsp");
		Map<String, String> statusMap = SystemConstant.getCodeStatusMap();
		statusMap.remove("4");// 去掉已删除
		Code code = codeService.selectByPrimaryKey(codeId);

		Map param = new HashMap();
		if (!StringUtil.isEmpty(type)) {
			param.put("type", type);
		}
		Map<String, String> codeMap = new TreeMap<String, String>();// 可供下拉选择的
																	// 父代码类别标识、名称对
		List<Map> list = codeService.selectBySelective(param);
		for (Map oneMap : list) {
			codeMap.put(oneMap.get("codeId") + "", oneMap.get("codeName") + "");
		}

		Map<String, String> typeMap = SystemConstant.getCodeTypeMap();// 所属种类

		mv.addObject("codeMap", codeMap);
		mv.addObject("statusMap", statusMap);
		mv.addObject("typeMap", typeMap);
		mv.addObject("code", code);
		mv.addObject("type", type);// 所属种类 类型
		return mv;
	}

	/**
	 * 保存代码管理
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping("/updCode")
	public ModelAndView updCode(Code code) {
		// 变量声明
		ModelAndView mv = new ModelAndView("/pages/code/updCode.jsp");
		try {
			int result = codeService.updateByPrimaryKeySelective(code);
			if (result > 0) {
				mv.addObject("message", "success");
				mv.addObject("info", "编辑代码成功。");
			} else {
				mv.addObject("message", "error");
				mv.addObject("info", "编辑代码失败。");
			}
		} catch (Exception e) {
			mv.addObject("message", "error");
			mv.addObject("info", "编辑代码异常。");
			log.info("编辑代码异常:" + e);
		}
		return mv;
	}

	/**
	 * 删除代码管理
	 * 
	 */
	@RequestMapping("/delCode")
	@ResponseBody
	public Map<String, Object> delCode(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {
			if (!StringUtil.isEmpty(ids)) {
				System.out.println(ids);
				String[] idArray = ids.split(",");
				for (String id : idArray) {
					int result = codeService.deleteByPrimaryKey(Integer
							.valueOf(id));
					if (result > 0) {
						flag = result;// 1 表示删除成功
					}
				}
			}
		} catch (Exception e) {
			log.info("删除异常：" + e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	/**
	 * 检验某种类代码名称的唯一性
	 * @param codeName
	 * @param type
	 * @return
	 */
	@RequestMapping("/checkCodeNameUnique")
	@ResponseBody
	public Map<String,Object> checkCodeNameUnique(String codeName,String type){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map param = new HashMap();
		if (!StringUtil.isEmpty(type)) {
			param.put("type", type);
		}
		if(!StringUtil.isEmpty(codeName)){
			param.put("codeName", codeName);
		}
		Map codeMap=new HashMap();
		List<Map> list = codeService.selectBySelective(param);
		if(list.size()>0){
			codeMap=list.get(0);
			codeMap.put("message", "1");
		}else{
			codeMap.put("message", "0");
		}
		resultMap.put("codeMap", codeMap);
		return resultMap;
	}

	/**
	 * 导入代码
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/importCode")
	@ResponseBody
	public Map<String, Object> importCode(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int flag = 0;// 删除失败
		try {

			// 读取文件
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					"E:\\brand.xls"));
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
						if (list.size() > 0) {
							System.out.println(cellValue + "存在");
						} else {
							Code code = new Code();
							code.setCodeName(cellValue);
							code.setStatus("1");
							//code.setpCode(6);//父结点id
							code.setType("8");//车型
							code.setCreateTime(new Date());
							code.setSort(10000);
							codeService.insert(code);
						}
					}

				}

			}
		} catch (Exception e) {
			log.info("删除异常：" + e);
			flag = 0;
		}
		resultMap.put("flag", flag);
		return resultMap;
	}

	/**
	 * 批量导入行政区域名称
	 * 
	 * @return
	 */
	@Deprecated
	@RequestMapping("/insertCodeByBatch")
	@ResponseBody
	public Map<String, Object> insertCodeByBatch() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		codeService.insertCodeByBatch();
		resultMap.put("flag", "1");
		return resultMap;
	}
}
