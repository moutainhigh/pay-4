package com.pay.poss.controller.fi.buysettlepoundage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acct.buySettleParities.model.BuysettlePoundageConfig;
import com.pay.acct.buySettleParities.service.BuySettleForeignCurrencyService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.controller.fi.vo.MapVO;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.MapUtil;

/****
 * 购结汇 
 * @author delin
 * @date 2016年8月4日11:39:50
 */
public class BuySettleForeignCurrecnyController  extends MultiActionController{

	public String buySettleFeeConfIndex;

	public String buySettleFeeConfList;
	
	private BuySettleForeignCurrencyService buySettleForeignCurrencyService;

	public void setBuySettleForeignCurrencyService(
			BuySettleForeignCurrencyService buySettleForeignCurrencyService) {
		this.buySettleForeignCurrencyService = buySettleForeignCurrencyService;
	}
	public void setBuySettleFeeConfList(String buySettleFeeConfList) {
		this.buySettleFeeConfList = buySettleFeeConfList;
	}
	public void setBuySettleFeeConfIndex(String buySettleFeeConfIndex) {
		this.buySettleFeeConfIndex = buySettleFeeConfIndex;
	}
	/**
	 * 购结汇手续费配置页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		String status=request.getParameter("status");
		return new ModelAndView(buySettleFeeConfIndex).addObject("status", status);//1：购汇手续费  2：结汇手续费
	}
	
	/***
	 * 购结汇手续费配置查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String beginCreateDate=request.getParameter("beginCreateDate");
		String endCreateDate=request.getParameter("endCreateDate");
		String beginUpdateDate=request.getParameter("beginUpdateDate");
		String endUpdateDate=request.getParameter("endUpdateDate");
		String partnerId=request.getParameter("partnerId");
		String status=request.getParameter("status");
		Page<Object> page = PageUtils.getPage(request);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("beginCreateDate", beginCreateDate);
		param.put("endCreateDate",endCreateDate);
		param.put("beginUpdateDate",beginUpdateDate);
		param.put("endUpdateDate",endUpdateDate);
		param.put("partnerId",partnerId);
		param.put("status",status);
		param.put("page", page);
		List<BuysettlePoundageConfig> result=buySettleForeignCurrencyService.queryBuySettleForeignCurrency(param,page);
		List<MapVO> mapVos=new ArrayList<MapVO>();
		for (BuysettlePoundageConfig buysettlePoundageConfig : result) {
			MapVO mapvo=new MapVO();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date updateDate = buysettlePoundageConfig.getUpdateDate();
			Date createDate = buysettlePoundageConfig.getCreateDate();
			buysettlePoundageConfig.setCreateDateS(sdf.format(createDate));
			buysettlePoundageConfig.setUpdateDateS(sdf.format(updateDate));
			Map<String,String> bean2map = MapUtil.bean2map(buysettlePoundageConfig);
			mapvo.setData(bean2map);
			mapVos.add(mapvo);
		}
		return new ModelAndView(buySettleFeeConfList).addObject("result", mapVos).addObject("page", page);
	}
	
	/**
	 * 添加购结汇的手续费配置页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
			String  memberCode=request.getParameter("memberCode");
			String  minimum=request.getParameter("minimum");
			String  fixedFee=request.getParameter("fixedFee");
			String  percentageFee=request.getParameter("percentageFee");
			String  capValue=request.getParameter("capValue");
			String  status=request.getParameter("status");
			String operator = SessionUserHolderUtil.getLoginId();
			Page<Object> page = PageUtils.getPage(request);
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("partnerId", memberCode);
			param.put("status", status);
			List<BuysettlePoundageConfig> result=buySettleForeignCurrencyService.queryBuySettleForeignCurrency(param,page);
			if(result != null && result.size()>0){
				return new ModelAndView(buySettleFeeConfIndex).addObject("error", "该会员号配置已存在！！！").addObject("status",status);
			}
			param.put("minimum", minimum);
			param.put("fixedFee", fixedFee);
			param.put("percentageFee", percentageFee);
			param.put("capValue", capValue);
			param.put("operator", operator);
			if(status.equals("1")){
				param.put("currencyCode", "CNY");
			}else if(status.equals("2")){
				param.put("currencyCode", "USD");
			}
			buySettleForeignCurrencyService.addBuySettleForeignCurrency(param);
		return new ModelAndView(buySettleFeeConfIndex).addObject("status",status);
	}
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response){
		String  memberCode=request.getParameter("memberCode");
		String  minimum=request.getParameter("minimum");
		String  fixedFee=request.getParameter("fixedFee");
		String  percentageFee=request.getParameter("percentageFee");
		String  capValue=request.getParameter("capValue");
		String  status=request.getParameter("status");
		String  id=request.getParameter("id");
		String operator = SessionUserHolderUtil.getLoginId();
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("partnerId", memberCode);
		param.put("status", status);
		param.put("minimum", minimum);
		param.put("fixedFee", fixedFee);
		param.put("percentageFee", percentageFee);
		param.put("capValue", capValue);
		param.put("id", id);
		param.put("operator", operator);
		buySettleForeignCurrencyService.updateBuySettleForeignCurrency(param);
		return new ModelAndView(buySettleFeeConfIndex).addObject("status",status);
	}
	/**
	 *  删除购结汇的手续费配置页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		String status=request.getParameter("status");
		buySettleForeignCurrencyService.deleteBuySettleForeignCurrency(id);
		return new ModelAndView(buySettleFeeConfIndex).addObject("status", status);
	}

}	
