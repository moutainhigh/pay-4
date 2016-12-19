package com.pay.poss.controller.fi.buysettlepoundage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.CapitalPoolManageService;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 *  资金池管理
 * @author delin
 * @date 2016年8月8日20:35:03
 */
public class CapitalPoolManageController extends MultiActionController{

	 private String  capitalIndex;
	 
	 private CapitalPoolManageService capitalPoolManageService;
	 
	 public void setCapitalPoolManageService(
			CapitalPoolManageService capitalPoolManageService) {
		this.capitalPoolManageService = capitalPoolManageService;
	}

	public void setCapitalIndex(String capitalIndex) {
		this.capitalIndex = capitalIndex;
	}

	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap=capitalPoolManageService.queryCapitalPoolManage();
		List<Map> result= (List<Map>) resultMap.get("list");
		 return new ModelAndView(capitalIndex).addObject("result", result);
	 }
	
	/**
	 * 修改预警线金额
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		String precautiousLineAmount=request.getParameter("precautiousLineAmount");
		String status=request.getParameter("status");
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("id", id);
		param.put("status", status);
		param.put("precautiousLineAmount", precautiousLineAmount);
		capitalPoolManageService.updatePrecautiousLineAmount(param);
		return index(request, response);
	}
	
	/**
	 * 工作面板
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView workPanels(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap= capitalPoolManageService.queryCount();
		String pendingCount=String.valueOf(resultMap.get("count"));
		try {
			response.getWriter().print(pendingCount);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
	/**
	 * 调拨头寸申请
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView addAudit(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
				ModelAndView index = index(request, response);
				String callOutAccount=request.getParameter("callOutAccountDesc");
			    String callInAccount=request.getParameter("callInAccountDesc");
			    String callOutCurrencyCode=request.getParameter("callOutAccount");
			    String callInCurrencyCode=request.getParameter("callInAccount");
				String callOutAmount=request.getParameter("callOutAmount");
				String callInAmount=request.getParameter("callInAmount");
				String operator = SessionUserHolderUtil.getLoginId();
				String callinBuyAmount=null;
				String	calloutBuyAmount=null;
				Map<String,Object> resultMap=capitalPoolManageService.queryCapitalPoolManage();
				List<Map> result= (List<Map>) resultMap.get("list");
				for (Map map : result) {
					if(callOutCurrencyCode.equals(map.get("currencyCode"))){
						calloutBuyAmount=new BigDecimal(String.valueOf(map.get("buyAmount"))).multiply(new BigDecimal(1000)).toString() ;
					}
					if(callInCurrencyCode.equals(map.get("currencyCode"))){
						 callinBuyAmount=new BigDecimal(String.valueOf(map.get("buyAmount"))).multiply(new BigDecimal(1000)).toString() ;
					}
				}
				Map<String,Object> paraMap=new HashMap<String, Object>();
				paraMap.put("callOutAccount", callOutAccount);
				paraMap.put("callInAccount", callInAccount);
				paraMap.put("callOutCurrencyCode",callOutCurrencyCode);
				paraMap.put("callInCurrencyCode", callInCurrencyCode);
				paraMap.put("callOutAmount", callOutAmount);
				paraMap.put("callInAmount", callInAmount);
				paraMap.put("operator", operator);
				paraMap.put("callinBuyAmount", callinBuyAmount);
				paraMap.put("calloutBuyAmount", calloutBuyAmount);
				paraMap.put("status", "0");
				Map<String,Object> resultM=capitalPoolManageService.addAudit(paraMap);
				String responseCode=String.valueOf(resultM.get("responseCode"));
				String responseDesc=String.valueOf(resultM.get("responseDesc"));
				if(responseCode.equals("0000")){
					index.addObject("info", "调拨头寸申请成功！！！");
				}else{
					index.addObject("info", responseDesc);
				}
			return index;
	}
	
}
