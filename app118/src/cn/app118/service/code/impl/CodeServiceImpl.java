/**
 * @(#)UserAction.java	08/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-08-21
 */
package cn.app118.service.code.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app118.dao.code.CodeMapper;
import cn.app118.framework.util.JdomUtil;
import cn.app118.model.Code;
import cn.app118.service.code.ICodeService;

/**
 * 代码管理服务实现类
 * 
 * @author wRitchie
 * 
 */
@Service("codeService")
public class CodeServiceImpl implements ICodeService {

	@Resource
	private CodeMapper codeMapper;// 代码管理dao

	@Override
	public int deleteByPrimaryKey(Integer codeId) {
		return codeMapper.deleteByPrimaryKey(codeId);
	}

	@Override
	public int insert(Code record) {
		return codeMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Code record) {
		return codeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Code selectByPrimaryKey(Integer codeId) {
		return codeMapper.selectByPrimaryKey(codeId);
	}

	@Override
	public List<Map> selectByPager(Map record) {
		return codeMapper.selectByPager(record);
	}

	@Override
	public int selectByPagerCount(Map record) {
		return codeMapper.selectByPagerCount(record);
	}

	@Override
	public List<Map> selectBySelective(Map record) {
		return codeMapper.selectBySelective(record);
	}

	/**
	 * 批量导入行政区域名称
	 * 
	 */
	@Deprecated
	@Override
	public  void insertCodeByBatch(){
		try {
			Set<String> citySet=JdomUtil.parseCity();
			
			 for(String s:citySet){
				 System.out.println(s);
				 Map map=new HashMap();
				 map.put("type", "10000");
				 map.put("codeName", s);
				 List l=codeMapper.selectBySelective(map);
				 if(l.size()==0){
					 Code code=new Code();
					 code.setType("10000");
					 code.setStatus("1");
					 code.setCodeName(s);
					 code.setCreateTime(new Date());
				     codeMapper.insert(code);
				 }
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
