/**
 * @(#)WRegionAction.java	07/21/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-07-21
 */
package cn.app.action.region;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.app118.action.common.BaseAction;
import cn.app118.model.Region;
import cn.app118.service.region.IRegionService;

/**
 * 全国省市区控制类
 * 
 * @author wRitchie
 *
 */
@Controller
@RequestMapping("wRegionAction")
public class WRegionAction extends BaseAction {
	
	@Resource 
	private IRegionService regionService;
	
	/**
	 * 根据父结点Id获取省市区县
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/listRegion")
	@ResponseBody
	public Map<String, Object> listRegion(String parentId) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<Region> list=regionService.selectByParentId(parentId);
		jsonMap.put("list", list);
		return jsonMap;
	}

}
