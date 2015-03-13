/**
 * @(#)UserAction.java	08/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-08-21
 */
package cn.app118.service.code;

import java.util.List;
import java.util.Map;

import cn.app118.model.Code;

/**
 * 代码管理服务接口
 * 
 * @author wRitchie
 * 
 */
public interface ICodeService {
	int deleteByPrimaryKey(Integer codeId);

	int insert(Code record);
	
	int updateByPrimaryKeySelective(Code record);
	
	Code selectByPrimaryKey(Integer codeId);
	
	List<Map> selectByPager(Map record);
	
	List<Map> selectBySelective(Map record);
	
	int selectByPagerCount(Map record);
	
	@Deprecated
	void insertCodeByBatch();

}
