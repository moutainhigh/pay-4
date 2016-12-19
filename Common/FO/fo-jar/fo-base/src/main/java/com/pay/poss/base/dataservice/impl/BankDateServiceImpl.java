package com.pay.poss.base.dataservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.base.dataservice.IDateService;

public class BankDateServiceImpl implements IDateService {

	@Override
	public List<Map<String, String>> getDatas() {
		List<Map<String,String>> list =  new ArrayList<Map<String,String>>();
		Map<String,String> map1 = new HashMap<String, String>();
		map1.put("text", "我是一");
		map1.put("value", "1");
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("text", "我是二");
		map2.put("value", "2");
		Map<String,String> map3 = new HashMap<String, String>();
		map3.put("text", "我是三");
		map3.put("value", "3");
		Map<String,String> map4 = new HashMap<String, String>();
		map4.put("text", "我是四");
		map4.put("value", "4");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		return list;
	}

}
