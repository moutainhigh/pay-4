package com.pay.app.facade.powers.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.app.facade.dto.PowersModel;
import com.pay.app.facade.powers.PowersListService;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-7-23 下午04:19:24
 */
public class PowersListServiceImpl implements PowersListService {

	@Override
	public List<PowersModel> getPowersList(List<String> list) {
		List<PowersModel> pmList = new ArrayList<PowersModel>();
		//入参原始形态(ID|菜单名称|URL|父级ID)解析成PowersModel
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				PowersModel pm = new PowersModel();
				String baseStr = list.get(i);
				String [] str = baseStr.split("\\|");
				pm.setId(str[0]);
				pm.setMenuName(str[1]);
				pm.setMenuUrl(str[2]);
				pm.setParentId(str[3]);
				pmList.add(pm);
			}
		}
		return pmList;
	}

}
