package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.channel.model.ChannelCurrencyConf;
import com.pay.channel.service.ChannelCurrencyConfService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.CurrencyDTO;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.JSonUtil;

/****
 * 渠道币种规则管理
 * 
 * @author ddl
 *
 */
public class ChannelCurrencyManagementController extends MultiActionController {

	private String index;

	private CurrencyService currencyService;
	
	private ChannelClientService channelClientService;

	private ChannelCurrencyConfService channelCurrencyConfService;
	
	private String channelcurrencyList;
	

	public void setChannelcurrencyList(String channelcurrencyList) {
		this.channelcurrencyList = channelcurrencyList;
	}

	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setChannelCurrencyConfService(
			ChannelCurrencyConfService channelCurrencyConfService) {
		this.channelCurrencyConfService = channelCurrencyConfService;
	}



	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response ,PaymentChannelItemDto paymentChannelItem) {
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		return new ModelAndView(index).addObject("result",result);
	}

	/***
	 * 获取币种
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getTradeCurrency(HttpServletRequest request,
			HttpServletResponse response) {
		Map currency = getCurrency(request, response);
		String string = new String(JSonUtil.toJSonString(currency));

		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Map getCurrency(HttpServletRequest request,
			HttpServletResponse response){
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setFlag("4");
		Page page = PageUtils.getPage(request);
		page.setPageSize(200);
		Map<String,String> map=new HashMap<String, String>();
		List<CurrencyDTO> findByCriteria = currencyService.findByCriteria(
				currencyDTO, page);
		for (CurrencyDTO currencyDTO2 : findByCriteria) {
			map.put(currencyDTO2.getCurrencyCode(), currencyDTO2.getCurrencyName());
		}
		return map;
	}
	
	
	
		/**
		 * 
		 * 添加渠道币种管理
		 * 
		 * **/
	public ModelAndView AddChannelCurrency(HttpServletRequest request,
			HttpServletResponse response) {

		String tradeCurrency = request.getParameter("tradeCurrency2");// 交易币种

		String channelCurrency = request.getParameter("channelCurrency1");// 渠道币种	

		String mechanism = request.getParameter("mechanism");// 机构号
		Long  partnerId=Long.parseLong(request.getParameter("partnerId").isEmpty()?"0":request.getParameter("partnerId"));//会员号
		
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil.getSessionUserHolder();
	
		ChannelCurrencyConf cc=new ChannelCurrencyConf();
		cc.setOrgCurrencyCode(channelCurrency);
		cc.setOrgCode(mechanism);
		String[] split = tradeCurrency.split(" ");
			
		cc.setPartnerId(partnerId);
		cc.setStatus("1");
		cc.setUpdateDate(new Date());
		cc.setCreateDate(new Date());
		cc.setOperator(sessionUserHolder.getUsername());
		List<ChannelCurrencyConf> channelCurrencies=findChannelCurrency(cc,split);
		if (!channelCurrencies.isEmpty() || channelCurrencies.size() != 0) {
			return index( request,
					 response ,new PaymentChannelItemDto()).addObject("error",
							"不能添加相同的渠道币种规则");	
		}
		channelCurrencyConfService.addChannelCurrency(cc,split);
		return index( request,
				 response ,new PaymentChannelItemDto());
	}
	
	
	
	
	
	
	public  List<ChannelCurrencyConf> findChannelCurrency(ChannelCurrencyConf cc,String[] split){
		List<ChannelCurrencyConf> 	channelCurrencys=channelCurrencyConfService.findChannelCurrency(cc,split);
		return channelCurrencys;
	}
	
	
	public  ModelAndView findChannelCurrencyPage(HttpServletRequest request,HttpServletResponse response){
		String tradeCurrency = request.getParameter("tradeCurrency2");// 交易币种
		
		String channelCurrency = request.getParameter("channelCurrency1");// 渠道币种
		
		String mechanism = request.getParameter("mechanism");// 机构号
		
		String  partner=request.getParameter("partnerId");//会员号
		
		Long partnerId;
		if(partner.isEmpty()){
			partnerId=null;
		}else{
			partnerId=Long.parseLong(partner);
		}
		ChannelCurrencyConf cc=new ChannelCurrencyConf();
		
		cc.setOrgCurrencyCode(channelCurrency);
		
		cc.setOrgCode(mechanism);
		
		String[] split = tradeCurrency.split(" ");
		
		cc.setPartnerId(partnerId);
		
		Page page=PageUtils.getPage(request);	
		List<ChannelCurrencyConf> channelCurrencys=channelCurrencyConfService.findChannelCurrency(cc,split,page);
		List list = channelClientService.queryChannelItem(new PaymentChannelItemDto());
		ModelAndView view = new ModelAndView(channelcurrencyList);
		view.addObject("channelCurrencys",channelCurrencys);
		view.addObject("page", page);
		view.addObject("list", list);
		return view;
	}
	/*
	 * 删除
	 * **/
	public  ModelAndView  deleteChannel(HttpServletRequest request,HttpServletResponse response){
		String parameter = request.getParameter("deleteId");
		if(parameter.isEmpty()){
			return new ModelAndView(index);
		}
		String[] split = parameter.split(",");
		channelCurrencyConfService.deleteChannel(split);
		return index( request,
				 response ,new PaymentChannelItemDto());
		
	}
	
	
	
	
	
	public ModelAndView editChannel(HttpServletRequest request,HttpServletResponse response){
		
		String id=request.getParameter("id");
		String orgCurrencyCode=request.getParameter("orgCurrencyCode");
		if(id.isEmpty() || id.length()==0){
			return new ModelAndView(index).addObject("error", "修改失败");
		}
		if(orgCurrencyCode==null || orgCurrencyCode.length()==0){
			return new ModelAndView(index).addObject("error","修改失败");
		}
		
		Map currency = getCurrency(request, response);
		
		
		
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil.getSessionUserHolder();
		ChannelCurrencyConf cc=new ChannelCurrencyConf();
		cc.setId(Long.parseLong(id));
		cc.setOrgCurrencyCode(orgCurrencyCode);
		cc.setUpdateDate(new Date());
		cc.setOperator(sessionUserHolder.getUsername());
		channelCurrencyConfService.updateChannel(cc);
		return index( request,
				 response ,new PaymentChannelItemDto());
	}	
}
