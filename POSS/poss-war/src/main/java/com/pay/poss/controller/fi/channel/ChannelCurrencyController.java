package com.pay.poss.controller.fi.channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.cardbin.model.CountryCurrency;
import com.pay.cardbin.service.CountryCurrencyService;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.ChannelCurrency;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.MapUtil;

/**
 * 渠道币种配置
 * @author delin.dong
 *	@date 2016年6月20日11:31:38
 */
public class ChannelCurrencyController extends MultiActionController {

	private String index;
	
	private String list;
	
	private String add;
	
	private String update;
	
	private ChannelClientService channelClientService;
	
	private CountryCurrencyService countryCurrencyService;


	private MemberService memberService;
	/**
	 * 查询渠道币种配置
	 * @param request
	 * @param response   
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String orgCode=request.getParameter("orgCode");
		String prdtCode=request.getParameter("prdtCode");
		String  payType=request.getParameter("payType");
		String  partnerId=request.getParameter("partnerId");
		String  cardCurrencyCode=request.getParameter("cardCurrencyCode");
		String  currencyCode=request.getParameter("currencyCode");
		Page<Map> page = PageUtils.getPage(request);
		Map paraMap=new HashMap();
		paraMap.put("orgCode", orgCode);
		paraMap.put("prdtCode", prdtCode);
		paraMap.put("payType", payType);
		paraMap.put("cardCurrencyCode", cardCurrencyCode);
		paraMap.put("currencyCode", currencyCode);
		if(StringUtil.isNumber(partnerId)){
			paraMap.put("partnerId", Long.parseLong(partnerId));
		}
		paraMap.put("page", page);
		Map resultMap=channelClientService.queryChannelCurrency(paraMap);
		List<Map> list1= (List<Map>) resultMap.get("result");
		List<Map> result=new ArrayList();
		if(list!=null){
			for(Map map:list1) {
				result.add(map);
			}
		}
		ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(list).addObject("result", result).addObject("channelItems", values).addObject("page",page);
    } 
	/**
	 * 删除渠道币种配置
	 * @param request
	 * @param response
	 * @return
	 */
	public void deleteChannelCurrency(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		String operator = SessionUserHolderUtil.getLoginId();
		Map paraMap=new HashMap();	
		paraMap.put("id", id);
		Map queryChannelCurrency = channelClientService.queryChannelCurrency(paraMap);
		paraMap.put("status", "0");//待审核
		boolean failure =false;
		String errorMsg  = null;
		Map<String,Object> reviewedChannelCurrency=channelClientService.queryReviewedChannelCurrency(paraMap);
		List<Map> results= (List<Map>) reviewedChannelCurrency.get("result");
		if(results != null && results.size()>0){ //判断是否有 已添加进复核的数据
			errorMsg = "删除配置申请已提交！";
			failure = true;
		}
		List<Map> result= (List<Map>) queryChannelCurrency.get("result");
		for (Map map : result) {
			map.put("operator", operator);
			map.put("status", "0");//待审核
			map.put("flag", "3");//删除
			map.put("channelCurrencyId", id);//删除
		}
		Map resultMap = channelClientService.addChannelCurrencyReviewed(result);
		if(!resultMap.get("responseCode").equals("0000")){
			errorMsg = "删除失败！！！！";
			failure = true;
		}
		WithdrawJSON json = WithdrawJSON.JsonBuilder();
		json.setSuccess(!failure);
		json.setReason(errorMsg);
		SpringControllerUtils.renderText(response, json.toString());
	}
	/**
	 * 修改渠道币种配置
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateChannelCurrency(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view=new ModelAndView(index);
		ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
		String orgCode=request.getParameter("orgCode");
		String[]	prdtCodes=request.getParameterValues("prdtCode");
		String currencyCode=request.getParameter("currencyCode");
		String cardCurrencyCode=request.getParameter("cardCurrencyCode");
		String channelCurrencyCode=request.getParameter("channelCurrencyCode");
		String payType=request.getParameter("payType");
		String id=request.getParameter("id");
		String operator = SessionUserHolderUtil.getLoginId();
		String prdtCode="";
		if(prdtCodes==null || prdtCodes.length<=0){
			view.addObject("msg", "请选择产品编号！！！");
			return view.addObject("channelItems", values);
		}
		for (String s : prdtCodes) {
			if(s.equals("CUSTOM_HIDDEN_DCC")){
				if(currencyCode.equals("CNY")){
					view.addObject("msg", "交易币种选择错误！！！");
					return view.addObject("channelItems", values);
				}
			}
			prdtCode+=s+",";
		}
		if(payType.equals("DCC")){
			if(!cardCurrencyCode.equals("*")){
			boolean existCurrencyCode = countryCurrencyService.isExistCurrencyCode(cardCurrencyCode);
			if(!existCurrencyCode ){
				view.addObject("msg", "卡本币输入错误！");
				return view.addObject("channelItems", values);
				}
			}
		}		
		List<Map<String,String>> paras=new ArrayList<Map<String,String>>();
		Map<String,String> paraMap=new HashMap<String,String>();
		paraMap.put("orgCode", orgCode);
		paraMap.put("prdtCode", prdtCode.substring(0, prdtCode.length() - 1));
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("cardCurrencyCode", cardCurrencyCode);
		paraMap.put("channelCurrencyCode", channelCurrencyCode);
		paraMap.put("payType", payType);
		//查看数据是否已存在
		Map<String,Object> queryChannelCurrency = channelClientService.queryChannelCurrency(paraMap);
		List<Map> result= (List<Map>) queryChannelCurrency.get("result");
		if(result != null && result.size()>0){
			view.addObject("msg", "该币种配置已存在！");
			return view.addObject("channelItems", values);
		}
		paraMap.put("status", "0");//待审核
		Map<String,Object> reviewedChannelCurrency=channelClientService.queryReviewedChannelCurrency(paraMap);
		List<Map> results= (List<Map>) reviewedChannelCurrency.get("result");
		if(results != null && results.size()>0){
			view.addObject("msg", "配置申请已提交！");
			return view.addObject("channelItems", values);
		}
		paraMap.put("operator", operator);
		paraMap.put("flag", "2");//表示update
		paraMap.put("channelCurrencyId", id);//表示update
		paras.add(paraMap);
		Map resultMap=channelClientService.addChannelCurrencyReviewed(paras);
		if(resultMap.get("responseCode").equals("0000")){
			view.addObject("msg", "修改成功！！！待审核");
		}
		return view.addObject("channelItems", values);
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	public void addChannelCurrency(HttpServletRequest request,HttpServletResponse response){

		String id = request.getParameter("id");
		String orgCode=request.getParameter("orgCode");
		String partnerId=request.getParameter("partnerId");
		String currencyCode=request.getParameter("currencyCode");
		String cardCurrencyCode=request.getParameter("cardCurrencyCode");
		String channelCurrencyCode=request.getParameter("channelCurrencyCode");
		String payType=request.getParameter("payType");
		String operator = SessionUserHolderUtil.getLoginId();
		String errorMsg = null;
		boolean failure =false;
		if(StringUtils.isBlank(partnerId) || !StringUtils.isNumeric(partnerId)){
			errorMsg = "会员号只能是数字";
			failure = true;
			WithdrawJSON json = WithdrawJSON.JsonBuilder();
			json.setSuccess(!failure);
			json.setReason(errorMsg);
			SpringControllerUtils.renderText(response, json.toString());
			return ;
		}else if(!"0".equals(partnerId)){
			MemberDto member = memberService.queryMemberByMemberCode(Long
					.valueOf(partnerId));
			if(member == null){
				errorMsg = "会员号不存在";
				failure = true;
				WithdrawJSON json = WithdrawJSON.JsonBuilder();
				json.setSuccess(!failure);
				json.setReason(errorMsg);
				SpringControllerUtils.renderText(response, json.toString());
				return;
			}
		}
		if(!cardCurrencyCode.equals("*") && "DCC".equals(payType)){
			boolean existCurrencyCode = countryCurrencyService.isExistCurrencyCode(cardCurrencyCode);
			if(!existCurrencyCode ){
				errorMsg = "卡本币输入错误！";
				failure = true;
				WithdrawJSON json = WithdrawJSON.JsonBuilder();
				json.setSuccess(!failure);
				json.setReason(errorMsg);
				SpringControllerUtils.renderText(response, json.toString());
				return;
			}
		}
		Map<String,Object> paraMap=new HashMap<String,Object>();
		paraMap.put("partnerId", Long.parseLong(partnerId));
		paraMap.put("orgCode", orgCode);
		paraMap.put("currencyCode", currencyCode);
		paraMap.put("cardCurrencyCode", cardCurrencyCode);
		paraMap.put("channelCurrencyCode", channelCurrencyCode);
		paraMap.put("payType", payType);
		paraMap.put("operator", operator);
		paraMap.put("id", id);
		paraMap.put("status", "0");//待审核

		Map resultMap=channelClientService.channelCurrencyProcess(paraMap);
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
			errorMsg = (String) resultMap.get("responseDesc");
			failure = true;
		}
		WithdrawJSON json = WithdrawJSON.JsonBuilder();
		json.setSuccess(!failure);
		json.setReason(errorMsg);
		SpringControllerUtils.renderText(response, json.toString());
	}
	/**
	 * 批量新增
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView batchAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String operator = SessionUserHolderUtil.getLoginId();
		ModelAndView model = new ModelAndView(index);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;
		MultipartFile multiPartFile = dmRequest.getFile("addChannelCurrencyFile");
		if (multiPartFile != null) {
			Workbook book = Workbook.getWorkbook(multiPartFile.getInputStream()) ;
			@SuppressWarnings("unchecked")
			List<ChannelCurrency> 	channelCurrencys = ExcelUtils.getListByReadShell(book.getSheet(0), 1, 0, 6,
					new String[] { "orgCode", "prdtCode","currencyCode","cardCurrencyCode","channelCurrencyCode", "payType"}, ChannelCurrency.class);
			for (ChannelCurrency channelCurrency : channelCurrencys) {
				channelCurrency.setOperator(operator);
				if(StringUtils.isEmpty(channelCurrency.getOrgCode())){
					model.addObject("msg", "上传失败，渠道不能为空！！！");
					return model;
				}
				if(StringUtils.isEmpty(channelCurrency.getPrdtCode())){
					model.addObject("msg", "上传失败，产品编号不能为空！！！");
					return model;
				}
				if(StringUtils.isEmpty(channelCurrency.getCurrencyCode())){
					model.addObject("msg", "上传失败，交易币种不能为空！！！");
					return model;
				}
				if(StringUtils.isEmpty(channelCurrency.getCardCurrencyCode())){
					model.addObject("msg", "上传失败，卡本币不能为空！！！");
					return model;
				}
				if(StringUtils.isEmpty(channelCurrency.getChannelCurrencyCode())){
					model.addObject("msg", "上传失败，渠道币种不能为空！！！");
					return model;
				}
				if(StringUtils.isEmpty(channelCurrency.getPayType())){
					model.addObject("msg", "上传失败，支付类型不能为空！！！");
					return model;
				}
			}
			Map resultMap=channelClientService.addChannelCurrency(channelCurrencys);
			if(resultMap.get("responseCode").equals("0000")){
				model.addObject("msg", "添加成功！！！");
			}
		}
			return model;
	}
	/***
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView(index);
		ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
		return view.addObject("channelItems", values);
	 }
	/**
	 * 添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
		ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
		return new ModelAndView(add).addObject("channelItems", values);
	}
	/**
	 * 修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initUpdate(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		Map paraMap=new HashMap();
		paraMap.put("id", id);
		Map resultMap = channelClientService.queryChannelCurrency(paraMap);
		List<Map> result= (List<Map>) resultMap.get("result");
		Map map = null;
		if(result!=null && !result.isEmpty()){
			 map = result.get(0);
		}
		PaymentChannelItemDto paymentChannelItemDto = new PaymentChannelItemDto();
		ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
		return new ModelAndView(update).addObject("map", map).addObject("channelItems",values);
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

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
}
