package com.pay.poss.conreg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.conreg.dto.TConregOrder;

/**
 * @Description
 * @project ma-manager
 * @file ConregController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 woyo Corporation. All rights reserved. Date
 *          Author Changes 2013-3-25 liwei Create
 */
public class ConregController extends MultiActionController {
	private BaseDAO<TConregOrder> baseDao;
	private String indexView;
	private String listView;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, TConregOrder dto) {
		return new ModelAndView(indexView);
	}

	public ModelAndView searchConreg(HttpServletRequest request,
			HttpServletResponse response, TConregOrder dto) {
		Page<TConregOrder> paramPage = PageUtils.getPage(request);
		Page<TConregOrder> page = baseDao.findByQuery(
				"tConregOrder.selectSelective", paramPage, dto);
		List<TConregOrder> orders = page.getResult();
		if (orders != null && orders.size() > 0) {
			for (TConregOrder order : orders) {
				order.setIdNumber(replaceSubString(order.getIdNumber(), 6, 14));
				order.setRealName(replaceSubString(order.getRealName(), 1, 2));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return new ModelAndView(listView, map);
	}

	private String replaceSubString(String str, int ns, int ne) {
		String sub1 = "";
		String sub2 = "";
		String sub = "";
		try {
			sub1 = str.substring(0, ns);
			sub2 = str.substring(ne);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ne - ns; i++) {
				sb = sb.append("*");
			}
			sub = sub1 + sb.toString() + sub2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sub;
	}

	/*
	 * public static void main(String[] args){ String a = "422322198207093517";
	 * System.out.println(replaceSubString(a,6,14)); String b = "李威"; String c =
	 * "杨育林"; System.out.println(replaceSubString(b,1,2));
	 * System.out.println(replaceSubString(c,1,2)); }
	 */

	public void setBaseDao(BaseDAO<TConregOrder> baseDao) {
		this.baseDao = baseDao;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}
}
