package com.pay.poss.controller.fi.currencyrate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;















import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.model.IpCountryInfo;
import com.pay.poss.client.CurrencyRateService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.MapUtil;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * 商户汇率浮动
 * 
 * @Description
 * @file 
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 . All rights reserved.
 *          Date
 */
public class PartnerRateFloatController extends MultiActionController{
	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String addView;
	private String recordList;
	private CurrencyRateService currencyRateService;
	private MemberQueryService memberQueryService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(queryInit);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(addView);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        
		String partnerIdStr = request.getParameter("partnerId");
		String startPoint = request.getParameter("startPoint");
		String endPoint = request.getParameter("endPoint");
		String operator = SessionUserHolderUtil.getLoginId();
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("operator", operator);

		Long partnerId = 0L;
		Map<String,String> respMap = new HashMap<String, String>();
		respMap.put("startPoint",startPoint);
		respMap.put("endPoint",endPoint);
		respMap.put("partnerId",partnerIdStr);
		
		if(StringUtil.isEmpty(partnerIdStr)){
			return new ModelAndView(addView, null)
			.addObject("respMap",respMap)
			.addObject("responseDesc","请输入正确的会员号");
		}else{
			try{
				partnerId = Long.valueOf(partnerIdStr.trim());
			}catch(Exception e){
				logger.info("err: "+e.getMessage());
				return new ModelAndView(addView, null)
				.addObject("respMap",respMap)
				.addObject("responseDesc","会员号输入不正确！");
			}
			if(partnerId.longValue()!=0){
				//--检查会员号是否存在
				MemberBaseInfoBO memberBaseInfoBO = memberQueryService
						.queryMemberBaseInfoByMemberCode(Long.valueOf(partnerId));
				if(memberBaseInfoBO==null){
					return new ModelAndView(addView, null)
					.addObject("respMap",respMap)
					.addObject("responseDesc","商户会员号不存在！");
				}
			}
			param.put("partnerId",String.valueOf(partnerId));
		}
		
        if(!StringUtil.isEmpty(startPoint)&&this.isZfNumber(startPoint)){
        	param.put("startPoint",startPoint);
        }else{
        		return new ModelAndView(addView, null)
        		.addObject("respMap",respMap)
    			.addObject("responseDesc","浮动起始点输入不正确");
        }
        
        if(!StringUtil.isEmpty(endPoint)&&this.isZfNumber(endPoint)){
        	param.put("endPoint",endPoint);
        }else{
        		return new ModelAndView(addView, null)
        		.addObject("respMap",respMap)
    			.addObject("responseDesc","浮动截止点输入不正确");
        }
        
        Double spt = Double.valueOf(startPoint);
        Double ept = Double.valueOf(endPoint);
        
        if(ept.doubleValue()<=spt.doubleValue()){
        	return new ModelAndView(addView, null)
        	.addObject("respMap",respMap)
			.addObject("responseDesc","浮动截止点不能小于或等于浮动起始点");
        }
        
        //判断当前是不是已经添加了		
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("partnerId",String.valueOf(partnerId));
		
		
		Map<String,Object> resultMapQuery = currencyRateService.partnerRateFloatQuery(paraMap);
		
		List<Map> returnMap=null;
		returnMap = (List<Map>) resultMapQuery.get("list");
		
		if(returnMap!=null&&!returnMap.isEmpty()){
        	return new ModelAndView(addView, null)
        	.addObject("respMap",respMap)
			.addObject("responseDesc","该会员的汇率浮动已存在！");
		}
        
        
		Map<String, Object> resultMap = currencyRateService.partnerRateFloatAdd(param);

		boolean result = (Boolean) resultMap.get("result");
		
		String responseDesc="";
		if(result){
			responseDesc="添加成功";
		}else{
			responseDesc="添加失败";
		}
	
		return new ModelAndView(queryInit, null).addObject("responseDesc",
				responseDesc);

	}
	
	// 检查字符串是否为数字
	public  boolean isZfNumber(String str) {
		if (str == null || str == "")
			return false;
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		Pattern pattern = Pattern.compile(regex); 
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 检查数据是否存在
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public ModelAndView checkData(HttpServletRequest request,
			                                HttpServletResponse response) 
			                                		{
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(!StringUtil.isEmpty(memberCode)){
			Long partnerId = 0L;
			try{
				partnerId = Long.valueOf(memberCode);
			}catch(Exception e){
				out.write("会员号格式不正确!");
			}
			if(partnerId.longValue()!=0){
				try{
				//--检查会员号是否存在
				MemberBaseInfoBO memberBaseInfoBO = memberQueryService
						.queryMemberBaseInfoByMemberCode(Long.valueOf(partnerId));
				  if(memberBaseInfoBO==null){
					  out.write("会员号不存在!");
				  }
				}catch(MaMemberQueryException e){
					out.write(e.getMessage());
				}
			}
		}
		
		out.flush();
		out.close();
		return null;
		
	}

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String memberCode = StringUtil.null2String(request.getParameter("partnerId"));

		Page page = PageUtils.getPage(request);
		
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("partnerId",memberCode);
		paraMap.put("page", page);
		
		
		Map<String,Object> resultMap = currencyRateService.partnerRateFloatQuery(paraMap);
		
		List<Map> returnMap=null;
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		returnMap = (List<Map>) resultMap.get("list");
			
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
			
		return new ModelAndView(recordList).addObject("rateList", returnMap)
				                           .addObject("page",page)
				                           .addObject("partnerId",memberCode);
	}
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sequenceId = request.getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id",sequenceId);
		Map<String,Object> resultMap = currencyRateService.partnerRateFloatDelete(map);
		boolean result = (Boolean) resultMap.get("result");
		String desc = "";
		if (result) {
			desc = "商户汇率浮动删除成功";
		} else {
			desc="商户汇率浮动删除成功,请您联系管理员!";
		}
		return new ModelAndView(queryInit, null)
				   .addObject("responseDesc",desc);
	}

}
