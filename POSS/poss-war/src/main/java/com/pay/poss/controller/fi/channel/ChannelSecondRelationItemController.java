package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.JSonUtil;
import com.pay.util.SimpleExcelGenerator;

/**
 * 入款通道管理(新)
 * 
 * @author sandy
 *
 */
public class ChannelSecondRelationItemController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(ChannelSecondRelationItemController.class);
	private ChannelClientService channelClientService;
	private MemberService memberService;
	private String indexView;
	private String resultView;
	private String addView;
	private String channelConfigView;
	private String batchAddView;

	/**
	 * 获取通道
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		return new ModelAndView(indexView).addObject("channelItems", result);
	}

	/**
	 * 查询商户通道
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) {
		String queryType = request.getParameter("queryType");

		if("0".equals(queryType)){
			String memberCode = request.getParameter("memberCode");
			if(memberCode != null) {memberCode.trim();memberCode.replaceAll(" ","");}
			String paymentChannelItemId = request.getParameter("orgCode");
			String orgMerchantCode = request.getParameter("orgMerchantCode");
			List resultList = channelClientService.querySecondChannelItem(
					memberCode, paymentChannelItemId, orgMerchantCode);
			List channelItems = channelClientService
					.queryChannelItem(new PaymentChannelItemDto());
			ModelAndView view = new ModelAndView(resultView);
			view.addObject("channelItems", channelItems);
			view.addObject("resultList", resultList);
			return view.addObject("memberCode", memberCode).addObject("orgMerchantCode", orgMerchantCode);
		}else{
			String memberCode = request.getParameter("memberCode");
			if(memberCode != null) {memberCode.trim();memberCode.replaceAll("\\s","");}
			String paymentChannelItemId = request.getParameter("orgCode");
			String orgMerchantCode = request.getParameter("orgMerchantCode");
			List resultList = channelClientService.queryMemberConChannelConfigHandler(
					memberCode, paymentChannelItemId, orgMerchantCode,null);
			List channelItems = channelClientService
					.queryChannelItem(new PaymentChannelItemDto());
			ModelAndView view = new ModelAndView("channel/channelsecondrelation/resultListAuto");
			view.addObject("channelItems", channelItems);
			view.addObject("resultList", resultList);
			return view.addObject("memberCode", memberCode).addObject("orgMerchantCode", orgMerchantCode);
		}
	}

	/**
	 * 配置二级商户渠道
	 * 
	 * @param request
	 * @param response
	 * @param
	 * @return
	 */
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) {
		String mcc = request.getParameter("mcc");
		String currencyCode = request.getParameter("currencyCode");
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		Map resultMap  = channelClientService.queryChannelConfig(null, null,
				null, null, mcc, currencyCode,null,null,null,null);
		List results= (List) resultMap.get("channelConfigs");
		ModelAndView view = new ModelAndView(addView);
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		view.addObject("currencyCodeEnum", currencyCodeEnum);
		return view.addObject("channelItems", result).addObject("itemConfigs",
				results);
	}

	/**
	 * 配置二级商户渠道
	 * 
	 * @param request
	 * @param response
	 * @param
	 * @return
	 * @throws IOException
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String memberCode = request.getParameter("memberCode").trim();
		String orgCode = request.getParameter("orgCode");
		String orgMerchantCodeStr = request.getParameter("orgMerchantCode").trim();
		String[]  strArr = StringUtils.split(orgMerchantCodeStr,",");
		String channelConfigId = strArr[0];
		String orgMerchantCode =orgMerchantCodeStr.replace(channelConfigId + ",","");
		String transType = request.getParameter("transType");
		String mcc = request.getParameter("mcc");
		String cardOrg = request.getParameter("cardOrg");
		String currencyCode = request.getParameter("currencyCode");
		String payChannelItemId = request.getParameter("payChannelItemId");
		ModelAndView view = new ModelAndView(addView);

		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		Map resultMap = channelClientService.queryChannelConfig(null, null,
				null, null, mcc, currencyCode,null,null,null,null);
		List itemConfigs= (List) resultMap.get("channelConfigs");
		MemberDto member = memberService.queryMemberByMemberCode(Long
				.valueOf(memberCode));
		if (null == member) {

			return view.addObject("errorMsg", "商户不存在!")
					.addObject("channelItems", result)
					.addObject("itemConfigs", itemConfigs);
		}

		String operator = SessionUserHolderUtil.getLoginId();
		String addResult = channelClientService.configSecondMemberChannelItem(
				memberCode, orgCode, orgMerchantCode, operator, transType,
				currencyCode,payChannelItemId,null,channelConfigId);
		if (null == addResult) {
			view.addObject("errorMsg", "添加成功！");
		} else {
			logger.error("response str:" + addResult);
			view.addObject("errorMsg", addResult);
		}

		view.addObject("channelItems", result);
		view.addObject("itemConfigs", itemConfigs);
		return view;
	}

	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String id = request.getParameter("id");
		String cardOrg = request.getParameter("cardOrg");
		String orgMerchantCode = request.getParameter("orgMerchantCode");
		String orgCode = request.getParameter("orgCode");
		String operator = SessionUserHolderUtil.getLoginId();

		logger.info("del:" + id + " by :" + operator);
		
		if(id==null){
			return null;
		}

		String str = channelClientService.delSecondMemberChannelItem(id, orgCode,
				orgMerchantCode,cardOrg);
		if (null == str) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(str);
		}
		return null;
	}

	public ModelAndView delCurCon(HttpServletRequest request,
							HttpServletResponse response) throws IOException {

		String id = request.getParameter("id");
		boolean result = channelClientService.delMemberConChannelConfigHandler(id);
		if (result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print("删除失败");
		}
		return null;
	}

	public ModelAndView queryChannelConfigItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("utf-8");
		String orgCode = request.getParameter("orgCode");
		String res = null;
		if(!StringUtils.isEmpty(orgCode )){ //orgCode为空的时候不需要查询出二级商户号  2016年5月19日 17:14:56 delin.dong
			Map resultMap = channelClientService.queryChannelConfig(null,
					orgCode, null, null, null, null,null,null,null,null);
			List itemConfigs= (List) resultMap.get("channelConfigs");
			
			if(null != itemConfigs && !itemConfigs.isEmpty()){
				res = JSonUtil.toJSonString(itemConfigs);
			}
			response.getWriter().print(res);
		}else
		response.getWriter().print(res);
		return null;
	}
	
	/***
	 * 初始化批量关联二级商户号页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initBatchAdd(HttpServletRequest request,HttpServletResponse response){
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		return new ModelAndView(batchAddView).addObject("channelItems", result);
	}
	/***
	 * 批量关联二级商户号查询
	 * @param request
	 * @param response
	 * @return
	 */
	public  ModelAndView batchQuery(HttpServletRequest request,HttpServletResponse response){
			String memberCode=request.getParameter("memberCode");
			String orgCodes=	request.getParameter("orgCode");
			String startTime=	request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String type=request.getParameter("type");
			String fitMerchantType=request.getParameter("fitMerchantType");
			String orgMerchantCode=request.getParameter("orgMerchantCode");
			String orgCode = orgCodes.split("-")[0];
			String paymentCategoryCode = null;
			if(!StringUtils.isEmpty(orgCodes)){
			 paymentCategoryCode=orgCodes.split("-")[2];
			}
			Map<String,String> param=new HashMap<String, String>();
			param.put("memberCode", memberCode);
			param.put("orgCode",orgCode);
			param.put("startTime", startTime);
			param.put("endTime", endTime);
			param.put("type", type);
			param.put("fitMerchantType", fitMerchantType);
			param.put("orgMerchantCode", orgMerchantCode);
			param.put("paymentCategoryCode", paymentCategoryCode);
			Map resultMap=channelClientService.batchQuery(param);
			List<Map> list=(List<Map>)resultMap.get("relations");
			PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
			List result = channelClientService.queryChannelItem(paymentChannelItem);
		return new ModelAndView(batchAddView).addObject("list", list).addObject("param", param).addObject("channelItems", result).addObject("orgCode", orgCode).addObject("paymentCategoryCode", paymentCategoryCode);
	}
	/**
	 * 批量关联二级商户号
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView batchAdd(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String orgCodes=request.getParameter("orgCode"); //orgCode - paymentChannelItemId
		String orgMerchantCode=request.getParameter("orgMerchantCode");
		String channelConfigIds = request.getParameter("channelConfigIds");
		String[]  split= orgCodes.split("-");
		String	 orgCode =split[0];
		String 	payChannelItemId=split[1];
		String 	cardOrg=split[2];
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		ModelAndView view = new ModelAndView(batchAddView);
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		MemberDto member = memberService.queryMemberByMemberCode(Long
				.valueOf(memberCode));
		if (null == member) {
			return view.addObject("errorMsg", "商户不存在!")
					.addObject("channelItems", result);
		}
		String operator = SessionUserHolderUtil.getLoginId();
		String addResult = channelClientService.configSecondMemberChannelItem(
				memberCode, orgCode, orgMerchantCode, operator, null,
				null,payChannelItemId,cardOrg,channelConfigIds);
		if (null == addResult) {
			view.addObject("errorMsg", "添加成功！");
		} else {
			logger.error("response str:" + addResult);
			view.addObject("errorMsg", addResult);
		}
		view.addObject("channelItems", result);
		return view;
	}
	/***
	 * 下载
	 * @return
	 */
	public ModelAndView download(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String orgCodes=	request.getParameter("orgCode");
		String startTime=	request.getParameter("startTime");
		String fitMerchantType=request.getParameter("fitMerchantType");
		String orgMerchantCode=request.getParameter("orgMerchantCode");
		String endTime=request.getParameter("endTime"); 	
		String type=request.getParameter("type");
		 String orgCode = orgCodes.split("-")[0];
		Map<String,String> param=new HashMap<String, String>();
		param.put("memberCode", memberCode);
		param.put("orgCode",orgCode);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("type", type);
		param.put("fitMerchantType", fitMerchantType);
		param.put("orgMerchantCode", orgMerchantCode);
		Map resultMap=channelClientService.batchQuery(param);
		List<Map> list=(List<Map>)resultMap.get("relations");
		for (Map map : list) {
		      Long creDate= (Long) map.get("createRelateDate");
			   Long canDate= (Long) map.get("cancelRelateDate");
			   if(creDate!=null){
				   Date date = new Date(creDate);
				   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   map.put("createRelateDate", sd.format(date));
			   }
			   if(canDate!=null){
				   Date dates = new Date(canDate);
				   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   map.put("cancelRelateDate", sdf.format(dates));
			   }
		}
		try {
			String[] headers = new String[] {"会员号", "通道名称", "二级商户号",
					"交易类型","模式","本月拒付比例","本月交易笔数","商户类型(500/800:ipaylinks/GC)","添加时间","删除时间"};
			String[] fields = new String[] { "memberCode", "paymentItemName", "orgMerchantCode",
					"transType", "pattern","cptPercent","tradeCnt","fitMerchantType","createRelateDate","cancelRelateDate"};
			Workbook grid = SimpleExcelGenerator.generateGrid("通道二级商户号添加",
					headers, fields, list);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setBatchAddView(String batchAddView) {
		this.batchAddView = batchAddView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setChannelConfigView(String channelConfigView) {
		this.channelConfigView = channelConfigView;
	}

	public static void main(String[] args) {
		String orgMerchantCode="aaa";
		String orgMerchantCode1="aaa,aaa,bbb,";
		String[] split = orgMerchantCode.split(",");
		for (String string : split) {
			System.out.println(string);
		}
		String[] split2 = orgMerchantCode1.split(",");
		for (String string : split2) {
			System.out.println(string);
		}						
	
	}
}
