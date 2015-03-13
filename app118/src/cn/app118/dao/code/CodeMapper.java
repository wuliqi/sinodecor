/**
 * @(#)UserAction.java	08/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-08-21
 */
package cn.app118.dao.code;

import java.util.List;
import java.util.Map;

import cn.app118.model.Code;

/**
 * 代码管理dao * @author wRitchie
 * 
 */
public interface CodeMapper {
	
	int deleteByPrimaryKey(Integer codeId);

	int insert(Code record);

	int insertSelective(Code record);

	Code selectByPrimaryKey(Integer codeId);

	int updateByPrimaryKeySelective(Code record);

	int updateByPrimaryKey(Code record);

	List<Map> selectByPager(Map record);

	int selectByPagerCount(Map record);
	
	List<Map> selectBySelective(Map record);
}