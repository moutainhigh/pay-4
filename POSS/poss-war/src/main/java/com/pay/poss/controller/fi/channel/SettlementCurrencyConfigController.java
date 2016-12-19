package com.pay.poss.controller.fi.channel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.enterprisemanager.model.AccountAttribute;
import com.pay.poss.enterprisemanager.model.BaseData;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.MapUtil;
import com.pay.util.SpringControllerUtils;
/**
 * 结算币种配置控制器
 * @author zhaoyang
 * 
 */
public class SettlementCurrencyConfigController extends MultiActionController {

	private final Logger logger = LoggerFactory.getLogger(SettlementCurrencyConfigController.class);
	
	private ChannelClientService channelClientService;
	private MemberService memberService;
	private IEnterpriseService enterpriseService;
	
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		//交易币种
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		return new ModelAndView("channel/settlementcurrencyconfig/index").addObject("currencyCodeEnum", currencyCodeEnum);
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
        String memberCode=request.getParameter("memberCodeQ");
        String payType=request.getParameter("payTypeQ");
        String tradeCurrencyCode=request.getParameter("tradeCurrencyCodeQ");
        String payCurrencyCode=request.getParameter("payCurrencyCodeQ");
        String settlementCurrencyCode=request.getParameter("settlementCurrencyCodeQ");
        Page<Map> page = PageUtils.getPage(request);
        Map<String,Object> paraMap=new HashMap<String,Object>();

        paraMap.put("page", page);
        paraMap.put("memberCode", memberCode);
        paraMap.put("payType", payType);
        paraMap.put("tradeCurrencyCode", tradeCurrencyCode);
        paraMap.put("payCurrencyCode", payCurrencyCode);
        paraMap.put("settlementCurrencyCode", settlementCurrencyCode);
        
        Map resultMap=channelClientService.querySettlementCurrencyConfig(paraMap);
        List<Map> result= (List<Map>) resultMap.get("result");
        Map pageMap = (Map) resultMap.get("page");
        page = MapUtil.map2Object(Page.class, pageMap);
        return new ModelAndView("channel/settlementcurrencyconfig/resultList").addObject("result", result).addObject("page",page);
    }
	
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String id=request.getParameter("configId");
        WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess(channelClientService.deleteSettlementCurrencyConfig(id));
        json.setReason("未知错误");
        SpringControllerUtils.renderText(response, json.toString());
        return null;
    }

	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String memberCode=request.getParameter("memberCode");
        String payType=request.getParameter("payType");
        String tradeCurrencyCode=request.getParameter("tradeCurrencyCode");
        String payCurrencyCode=request.getParameter("payCurrencyCode");
        String settlementCurrencyCode=request.getParameter("settlementCurrencyCode");
        String mark = request.getParameter("mark");
        String configId = request.getParameter("configId");
        String grade = request.getParameter("grade");
        String operator = SessionUserHolderUtil.getLoginId();
        Map<String,String> returnMap = new HashMap<String, String>();
        boolean failure = false;
        String errorMsg = null;
        if(StringUtils.isBlank(memberCode) || !StringUtils.isNumeric(memberCode)){
            errorMsg = "会员号只能是数字";
            failure = true;
        }else if(!"0".equals(memberCode)){
            MemberDto member = memberService.queryMemberByMemberCode(Long
                    .valueOf(memberCode));
            if(member == null){
                errorMsg = "会员号不存在";
                failure = true;
            }
        }
        Map<String,Object> paraMap=new HashMap<String,Object>();
        paraMap.put("memberCode", memberCode);
        paraMap.put("payType", payType);
        paraMap.put("tradeCurrencyCode", tradeCurrencyCode);
        paraMap.put("payCurrencyCode", payCurrencyCode);
        paraMap.put("settlementCurrencyCode", settlementCurrencyCode);
        paraMap.put("operator", operator);
        paraMap.put("mark", mark);
        paraMap.put("grade", grade);
        Map resultMap = null;
        if(!failure){
            if(StringUtils.isNotBlank(configId)){
            	paraMap.put("configId", configId);
                resultMap=channelClientService.updateSettlementCurrencyConfig(paraMap);
            }else{
                resultMap=channelClientService.addSettlementCurrencyConfig(paraMap);
            }
            if(!ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
                failure = true;
                errorMsg = (String)resultMap.get("responseDesc");
            }
        }
        WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess(!failure);
        json.setReason(errorMsg);
        SpringControllerUtils.renderText(response, json.toString());
        return null;
    }
	
	/**根据会员号获得对应开通的账户信息集合，再整理成结算币种在页面展示
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getSettlementCurrencyByMemberCode(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		//结算币种
		List<AccountAttribute> accountAttributeList = enterpriseService.queryAttributeByMemberCode(memberCode);
		
		Set<CurrencyCodeEnum> set = new HashSet<CurrencyCodeEnum>();
		for(AccountAttribute aa :accountAttributeList){
			set.add(CurrencyCodeEnum.getCurrencyByCode(aa.getCurCode()));
		}
		
		StringBuffer sb = new StringBuffer("<option value=");
		sb.append("\"");
		sb.append("\"");
		sb.append(" selected");
		sb.append(">---请选择---</option>");
		for(CurrencyCodeEnum ccEnum : set){
			sb.append("<option value=");
			sb.append("\"");
			sb.append(ccEnum.getCode());
			sb.append("\"");
			sb.append(">");
			sb.append(ccEnum.getDesc());
			sb.append("</option>");
		}
		WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess(true);
        json.setReason(sb.toString());
        SpringControllerUtils.renderText(response, json.toString());
        return null;
	}
	
	public ModelAndView getPayCurrencyByMemberCode(HttpServletRequest request,HttpServletResponse response){
		Map paraMap=new HashMap();
		Map resultMap=channelClientService.queryChannelItemRCurrency(paraMap);
		List<Map> result= (List<Map>) resultMap.get("result");
		Set<CurrencyCodeEnum> set = new HashSet<CurrencyCodeEnum>();
		if(resultMap.get("result") != null){
			for(Map map : result){
				CurrencyCodeEnum cce = CurrencyCodeEnum.getCurrencyByCode(map.get("currencyCode").toString());
				if(cce != null){
					set.add(cce);
				}else{
					logger.error("渠道支持币种-{}未在枚举类CurrencyCodeEnum中找到",map.get("currencyCode").toString());
				}
			}
		}
		StringBuffer sb = new StringBuffer("<option value=");
		sb.append("\"");
		sb.append("\"");
		sb.append(" selected");
		sb.append(">---请选择---</option>");
		sb.append("<option value=");
		sb.append("\"");
		sb.append("*");
		sb.append("\"");
		sb.append(">*</option>");
		for(CurrencyCodeEnum ccEnum : set){
			sb.append("<option value=");
			sb.append("\"");
			sb.append(ccEnum.getCode());
			sb.append("\"");
			sb.append(">");
			sb.append(ccEnum.getDesc());
			sb.append("</option>");
		}
		WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess(true);
        json.setReason(sb.toString());
		SpringControllerUtils.renderText(response, json.toString());
		return null;
	}
	
	/**根据用户号和交易类型查找支付币种
	 * @param request
	 * @param response
	 * @return
	 */
//	public ModelAndView getPayCurrencyByMemberCode(HttpServletRequest request,HttpServletResponse response){
//		String memberCode=request.getParameter("memberCode");
//		String payType=request.getParameter("payType");
//		/**
//		 * 1，根据用户号查找该商户配置的通道的集合，对应于POSS平台的商户通道管理-》检索功能
//		 * 2，遍历通道集合，根据通道的编号和交易类型查找该渠道配置的送渠道币种，对应于POSS
//		 * 	 平台的渠道币种配置-》检索功能
//		 */
//		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
//		paymentChannelItem.setMemberCode(memberCode);
//		List<Map> memberChannels = channelClientService.queryMemberChannelItem(paymentChannelItem);
//		Set<CurrencyCodeEnum> set = new HashSet<CurrencyCodeEnum>();
//		for(int i=0,len=memberChannels.size(); i<len-1; i++){
//			Map dto = memberChannels.get(i);
//			Map paraMap=new HashMap();
//			paraMap.put("orgCode", dto.get("orgCode"));
//			paraMap.put("payType", payType);
////			paraMap.put("page", page);
//			Map resultMap=channelClientService.queryChannelCurrency(paraMap);
//			List<Map> result= (List<Map>) resultMap.get("result");
//			for(int j=0,rlen=result.size(); j<rlen; j++){
//				Map map = result.get(j);
//				set.add(CurrencyCodeEnum.getCurrencyByCode(map.get("channelCurrencyCode").toString()));
//			}
//		}
//		StringBuffer sb = new StringBuffer("<option value=");
//		sb.append("\"");
//		sb.append("\"");
//		sb.append(" selected");
//		sb.append(">---请选择---</option>");
//		sb.append("<option value=");
//		sb.append("\"");
//		sb.append("*");
//		sb.append("\"");
//		sb.append(">*</option>");
//		for(CurrencyCodeEnum ccEnum : set){
//			sb.append("<option value=");
//			sb.append("\"");
//			sb.append(ccEnum.getCode());
//			sb.append("\"");
//			sb.append(">");
//			sb.append(ccEnum.getDesc());
//			sb.append("</option>");
//		}
//		WithdrawJSON json = WithdrawJSON.JsonBuilder();
//        json.setSuccess(true);
//        json.setReason(sb.toString());
//		SpringControllerUtils.renderText(response, json.toString());
//		return null;
//	}
	
	/**判断用户是否存在
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView isExsit(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		boolean failure = false;
        String errorMsg = null;
        if(StringUtils.isBlank(memberCode) || !StringUtils.isNumeric(memberCode)){
            errorMsg = "会员号只能是数字";
            failure = true;
        }else if(!"0".equals(memberCode)){
            MemberDto member = memberService.queryMemberByMemberCode(Long
                    .valueOf(memberCode));
            if(member == null){
                errorMsg = "会员号不存在";
                failure = true;
            }
        }
        WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess(!failure);
        json.setReason(errorMsg);
        SpringControllerUtils.renderText(response, json.toString());
        return null;
	}
	
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	
}
