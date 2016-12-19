package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.cardbin.service.CountryCurrencyService;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.ChannelCurrency;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 渠道币种配置
 * @author cuber
 *	@date 2016年6月29日11:31:38
 */
public class ChannelItemRCurrencyController extends MultiActionController {

	private String index;
	
	private String list;
	
	private String add;
	
	private String update;
	
	private ChannelClientService channelClientService;
	
	private CountryCurrencyService countryCurrencyService;
	/**
	 * 查询渠道支持币种配置
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String channelItemCode=request.getParameter("channelItemCode");
		String currencyCode=request.getParameter("currencyCode");
		Page<Map> page = PageUtils.getPage(request);
		Map paraMap=new HashMap();
		paraMap.put("channelItemCode", channelItemCode);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("page", page);
		Map resultMap=channelClientService.queryChannelItemRCurrency(paraMap);
		List<Map> result= (List<Map>) resultMap.get("result");
		if(result != null && result.size() > 0){
			for (Map map : result) {
				channelItemCode = (String)map.get("channelItemCode");
				map.put("channelItemName", ChannelItemOrgCodeEnum.getChannelItemByCode(channelItemCode).getDesc());
			}
		}
		ChannelItemOrgCodeEnum[] channelItems= ChannelItemOrgCodeEnum.values();
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(list).addObject("result", result).addObject("channelItems", channelItems).addObject("page",page);
    } 
	/**
	 * 失效渠道币种配置
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteChannelCurrency(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("relateId");
		ModelAndView modelAndView = new ModelAndView(index);
		Map paraMap=new HashMap();
		paraMap.put("relateId", id);
		String operator = SessionUserHolderUtil.getLoginId();
		paraMap.put("updateOperator", operator);
		Map resultMap=channelClientService.validChannelItemRCurrency(paraMap);
		if(resultMap.get("responseCode").equals("0000")){
			modelAndView.addObject("msg", "删除成功！！！");
		}
		ChannelItemOrgCodeEnum[] channelItems= ChannelItemOrgCodeEnum.values();
		modelAndView.addObject("channelItems", channelItems);
		return modelAndView;
	}
	/**
	 * 添加渠道币种
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addChannelCurrency(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view=new ModelAndView(index);
		ModelAndView viewadd = new ModelAndView(add);
		String channelItemCode=request.getParameter("channelItemCode");
		String currencyCode=request.getParameter("currencyCode");
		String operator = SessionUserHolderUtil.getLoginId();
		ChannelItemOrgCodeEnum[] channelItems= ChannelItemOrgCodeEnum.values();
		boolean existCurrencyCode = countryCurrencyService.isExistCurrencyCode(currencyCode);
		if(!existCurrencyCode){
			view.addObject("msg", "请输入正确的币种！！！");
			view.addObject("channelItems", channelItems);
			return view;
		}
		List<Map> paras=new ArrayList<Map>();
		Map paraMap=new HashMap();
		paraMap.put("channelItemCode", channelItemCode);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("createOperator", operator);
		paras.add(paraMap);
		Map resultMap=channelClientService.addChannelItemRCurrency(paraMap);
		if(resultMap.get("responseCode").equals("0000")){
			view.addObject("msg", "添加成功！！！");
			view.addObject("channelItems", channelItems);
			
			return view;
		}else{
			viewadd.addObject("channelItems", channelItems);
			viewadd.addObject("selectChannelItemCode", channelItemCode);
			viewadd.addObject("inputCurreneyCode",currencyCode);
			viewadd.addObject("msg", resultMap.get("responseDesc"));
			return viewadd;
		}
	}
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView(index);
		ChannelItemOrgCodeEnum[] channelItems= ChannelItemOrgCodeEnum.values();
		return view.addObject("channelItems", channelItems);
	 }
	/**
	 * 添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
		ChannelItemOrgCodeEnum[] channelItems= ChannelItemOrgCodeEnum.values();
		return new ModelAndView(add).addObject("channelItems", channelItems);
	}
	
	/**
	 * 获取渠道支持币种
	 * @param request
	 * @param response
	 * @return
	 */
	public void getChannelItemCurrencyCode(HttpServletRequest request,HttpServletResponse response){
		String channelItemCode=request.getParameter("channelItemCode");
		String currencyCode=request.getParameter("currencyCode");
		Page<Map> page = PageUtils.getPage(request);
		page.setPageSize(300);
		Map paraMap=new HashMap();
		paraMap.put("channelItemCode", channelItemCode);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("page", page);
		Map resultMap=channelClientService.queryChannelItemRCurrency(paraMap);
		List<Map> result= (List<Map>) resultMap.get("result");
		try {
			String reqMsg = JSonUtil.toJSonString(result);
			response.getWriter().print(reqMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	public void setList(String list) {
		this.list = list;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public void setCountryCurrencyService(
			CountryCurrencyService countryCurrencyService) {
		this.countryCurrencyService = countryCurrencyService;
	}
	
}
