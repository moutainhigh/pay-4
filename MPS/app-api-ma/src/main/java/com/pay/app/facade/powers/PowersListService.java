package com.pay.app.facade.powers;

import java.util.List;

import com.pay.app.facade.dto.PowersModel;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-7-23 下午04:17:15
 * 权限菜单URL服务
 */
public interface PowersListService {
	/**
	 * 返回权限对象列表
	 */
	List<PowersModel> getPowersList(List<String>list);
}
