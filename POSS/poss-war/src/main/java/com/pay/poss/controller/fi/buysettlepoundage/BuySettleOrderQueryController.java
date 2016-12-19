package com.pay.poss.controller.fi.buysettlepoundage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.CapitalPoolManageService;
import com.pay.util.MapUtil;

/**
 * 购结汇查询
 * 
 * @author delin
 * @Date 2016年8月12日16:38:12
 */
public class BuySettleOrderQueryController extends MultiActionController {
	private CapitalPoolManageService  capitalPoolManageService;

	private String index;

	private String list;
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}
	/**
	 * 查询结购汇 订单
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
			String partnerId=request.getParameter("partnerId");
			String exchangeNo=request.getParameter("exchangeNo");
			String type=request.getParameter("type");
			Page page = PageUtils.getPage(request);
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("type", type);
			if(StringUtils.isNotEmpty(exchangeNo)){
				param.put("exchangeNo", Long.valueOf(exchangeNo));
			}
			param.put("partnerId", partnerId);
			param.put("page", page);
			Map<String,Object> resultMap=capitalPoolManageService.findBuySettleOrder(param);
			List<Map> resultList=	(List<Map>) resultMap.get("result");
			for (Map map : resultList) {
				   Long comDate= (Long) map.get("createDate");
				   Date date = new Date(comDate);
				   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   map.put("createDate", sd.format(date));
			}
			Map pageMap = (Map) resultMap.get("page");
			page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(list).addObject("page", page).addObject("resultList",resultList);
	}

	public void setCapitalPoolManageService(
			CapitalPoolManageService capitalPoolManageService) {
		this.capitalPoolManageService = capitalPoolManageService;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public void setList(String list) {
		this.list = list;
	}
}
