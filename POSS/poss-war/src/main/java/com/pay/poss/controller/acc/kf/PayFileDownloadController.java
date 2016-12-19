package com.pay.poss.controller.acc.kf;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.KfPayService;
import com.pay.util.MapUtil;

/**
 * KF 支付文件下载
 * @date 2016年8月25日17:55:48
 * @author delin
 *
 */
public class PayFileDownloadController extends MultiActionController {
	private String index;

	private String list;
	
	private KfPayService kfPayService;

	private MyOSS myoss;
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
			Page<Object> page = null;
			List<Map<String, Object>> resultList = null;
			try {
				String partnerId=request.getParameter("partnerId");		
				String batchNo=request.getParameter("batchNo");		
				String type=request.getParameter("type");		
				String beginCreateDate=request.getParameter("beginCreateDate");		
				String endCreateDate=request.getParameter("endCreateDate");		
				String status=request.getParameter("status");		
				page = PageUtils.getPage(request);
				Map<String,Object> paraMap=new HashMap<String, Object>();
				Map<String, Object> crossOrderMap=new HashMap<String, Object>();
				if(StringUtils.isNotEmpty(partnerId)){
					crossOrderMap.put("partnerId", Long.valueOf(partnerId));
				}
				crossOrderMap.put("batchNo", batchNo);
				crossOrderMap.put("type", type);
				crossOrderMap.put("beginCreateDate", beginCreateDate);
				crossOrderMap.put("endCreateDate", endCreateDate);
				crossOrderMap.put("status",  "'0','1','2','3'");
				if(StringUtils.isNotEmpty(status)){
					crossOrderMap.put("status",  Long.valueOf(status));
				}
				paraMap.put("crossOrder", crossOrderMap);
				paraMap.put("page", page);
				Map<String,Object> resultMap=kfPayService.findPayFile(paraMap);
				resultList = (List<Map<String,Object>>) resultMap.get("list");
				for (Map<String, Object> map : resultList) {
					   Long comDate= (Long) map.get("createDate");
					   Date date = new Date(comDate);
				   	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					   map.put("createDate", sdf.format(date));
					   BigDecimal bigDecimal = new  BigDecimal(1000);
					   map.put("remitAmount",new BigDecimal(map.get("remitAmount").toString()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).toString());
					   map.put("payAmount", new BigDecimal(map.get("payAmount").toString()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).toString());
					   List<Map> kfPayResources=(List<Map>)map.get("kfPayResources");
					   for (Map map2 : kfPayResources) {
						   String url=String.valueOf(map2.get("url"));
						   String signURL = myoss.signURL(url);
						   map2.put("url", signURL);
					}
				}
				Map pageMap = (Map) resultMap.get("page");
				page = MapUtil.map2Object(Page.class, pageMap);
			} catch (Exception e) {
				e.printStackTrace();
			} 	
		return new ModelAndView(list).addObject("page", page).addObject("resultList", resultList);
	}
	
	
	public void setIndex(String index) {
		this.index = index;
	}

	public void setList(String list) {
		this.list = list;
	}
	
	public void setKfPayService(KfPayService kfPayService) {
		this.kfPayService = kfPayService;
	}

	public void setMyoss(MyOSS myoss) {
		this.myoss = myoss;
	}
	

}
