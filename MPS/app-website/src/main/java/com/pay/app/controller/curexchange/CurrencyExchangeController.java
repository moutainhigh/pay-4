package com.pay.app.controller.curexchange;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

//import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.memberaccttemplate.dto.MemberAcctTemplateDto;
import com.pay.acc.memberaccttemplate.service.MemberAcctTemplateService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.client.TxncoreClientService;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctInfo;
import com.pay.base.service.acct.AcctService;
//import com.pay.base.service.acctatrrib.AcctAttribService;
//import com.pay.base.service.createacc.*;
import com.pay.ce.model.CurrencyExchangeOrder;
import com.pay.ce.service.CurrencyExchangeService;
import com.pay.fi.hession.SettlementOrderService;
import com.pay.inf.service.IMessageDigest;
import com.pay.pe.dto.CurrencyDTO;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.util.CurrencyCodeEnum;







/**
 * 货币兑换 
 * @author peiyu.yang
 *
 */
public class CurrencyExchangeController extends MultiActionController{
	private Logger logger = LoggerFactory.getLogger(getClass());
    private String index;
    private String jumpfail;
    private String success;
	private CurrencyService currencyService;
    /** 账户信息服务 */
	private AcctService acctService;
	
	private AcctAttribService acctAttribService;
	
	private CurrencyExchangeService currencyExchangeService;
	
	//
	private SettlementOrderService settlementOrderService;
	
	/**取汇率 */
	private TxncoreClientService txncoreClientService;
	
	//
	//private CreateAccService createAccService;
	
	private IMessageDigest iMessageDigest;
	
	private MemberAcctTemplateService memberAcctTemplateService;
	private MemberService memberService;
	
	
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		
		
		
		ModelAndView singleView = new ModelAndView(index);
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		
		Map<String,List<AcctInfo>> acctInfoMap = 
	            acctService.getAcctInfByMemberCode(Long.valueOf(memberCode));
		 
		List<AcctInfo> acctList = acctInfoMap.get("all");
		
		
		List<AcctInfo> infoList = new ArrayList<AcctInfo>();
		
		
		
		for(AcctInfo info:acctList){
			if(CurrencyCodeEnum.isExists(info.getCurCode())
					&&info.getAcctType()<200){
				String code = info.getCurCode();
				info.setAcctName(CurrencyCodeEnum.getCurrencyNameByCode(code));
                infoList.add(info);
			}
		}
		
	
		CurrencyDTO currencyDTO = new CurrencyDTO();
		currencyDTO.setFlag("2");//1:交易支持货币，2：兑换支持货币  3:其他
		
        List<CurrencyDTO> currencyList = currencyService.findByMapper(currencyDTO);

        
         
        /*MemberAcctTemplateDto memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_CNY.getCode());
        Assert.notNull(memberAcctTemplateDto, "memberAcctTemplateDto must be not null");
        StringBuilder memberAcctCode = new StringBuilder();
        memberAcctCode.append(memberCode);
        memberAcctCode.append(memberAcctTemplateDto.getAcctType());
        AcctAttribDto acctAttrib = new AcctAttribDto();
        BeanUtils.copyProperties(memberAcctTemplateDto, acctAttrib);
        String acctCode = (new StringBuilder()).append(memberAcctTemplateDto.getSubjectCode()).append(memberCode).append(memberAcctTemplateDto.getAcctType()).toString();
        acctAttrib.setAcctCode(acctCode);
        acctAttrib.setMemberCode(memberCode);
        acctAttrib.setCurCode(memberAcctTemplateDto.getCurCode());
        acctAttrib.setMemberAcctCode(Long.valueOf(memberAcctCode.toString()));
        acctAttrib.setCreateDate(new Date());
        acctAttrib.setUpdateDate(new Date());
        acctAttribService.createAcctAttrib(acctAttrib);
        AcctDto acct = new AcctDto();
        acct.setAcctCode(acctCode);
        acct.setMemberCode(memberCode);
        acct.setCreateDate(new Date());
        acct.setUpdateDate(new Date());
        acctService.createAcct(acct);*/
   
        
        
        
        
//        Acct a = new Acct();
//        System.out.print(acctService.createAcct(a)+"!!!!!!!!!!!!!!!!!!!!");
        
        
    ////
   
   
        
		return singleView.addObject("currencyList",currencyList).addObject("infoList",infoList);
	}
		
	BigDecimal fixedCharge = new BigDecimal(10);
	BigDecimal flexibleCharge = new BigDecimal(0.0001);
	
	public ModelAndView chargeCount( HttpServletRequest request,
			 HttpServletResponse response)
					throws Exception {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		BigDecimal balance =new BigDecimal(request.getParameter("balance"));
		//BigDecimal inputbalance =new BigDecimal(request.getParameter("inputbalance"));
		JSONObject json = new JSONObject();		
		BigDecimal totalCharge = (balance.multiply(flexibleCharge)).add(fixedCharge);
		//BigDecimal totalinputCharge = (inputbalance.multiply(flexibleCharge)).add(fixedCharge);
		totalCharge=totalCharge.setScale(2, BigDecimal.ROUND_HALF_UP); 
		//totalinputCharge=totalinputCharge.setScale(2, BigDecimal.ROUND_HALF_UP); 
		json.put("charge", totalCharge);
		//json.put("inputcharge",totalinputCharge);
		json.put("maxCurrencyPurchase",(balance.subtract(totalCharge)).setScale(2, BigDecimal.ROUND_HALF_UP));
		response.getWriter().write(json.toString());
		return null;
	}

	public ModelAndView chargeRealCount( HttpServletRequest request,
			 HttpServletResponse response)
					throws Exception {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	
		BigDecimal inputbalance =new BigDecimal(request.getParameter("inputbalance"));

		JSONObject json = new JSONObject();		
	
		BigDecimal totalinputCharge = (inputbalance.multiply(flexibleCharge)).add(fixedCharge);
		totalinputCharge=totalinputCharge.setScale(2, BigDecimal.ROUND_HALF_UP); 
		json.put("inputcharge",totalinputCharge);
		json.put("afterbalance",(inputbalance.subtract(totalinputCharge)).setScale(2, BigDecimal.ROUND_HALF_UP));
		response.getWriter().write(json.toString());
		return null;
	}
	
	
	
	public ModelAndView getRate (HttpServletRequest request,
	 HttpServletResponse response)
				throws Exception {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		JSONObject json = new JSONObject();
		String currency = request.getParameter("currency");
		String targetCurrency = request.getParameter("targetCurrency");
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		Map  map = txncoreClientService.getTransactionRate(currency, targetCurrency, memberCode,"1");
	try{
		if(map.containsKey("exchangeRate")){
			String exchangeRate = (String)map.get("exchangeRate");
			json.put("exchangeRate",exchangeRate);
		}
		else{
			BigDecimal exchangeRate = new BigDecimal(0);
			json.put("exchangeRate",exchangeRate);
		}
		}catch(Exception e){
			
		}
		
//		Map  map = txncoreClientService.getTransactionRate("USD", "JPY", memberCode,"1");
//		System.out.println("!!!!!!!!!!!!"+map);
		response.getWriter().write(json.toString());
		return null;
	
				}
	
	
	
	public ModelAndView submit( HttpServletRequest request,
			 HttpServletResponse response)
					throws Exception {
		ModelAndView mv = new ModelAndView();
		String loginName = (String) request.getSession().getAttribute(
				"loginName");
		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);

		
		
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String tmpbalance = request.getParameter("currencyin");
		if(tmpbalance.equals("")){
			mv.setViewName(jumpfail);
			mv.addObject("errmsg","请选择转出币种");
			return mv;
		}
		String targetBalance = request.getParameter("finalbalance");
		String acctCode = request.getParameter("currencyin").split("_")[0];
		String acctBalance = request.getParameter("currencyin").split("_")[1];
		String CurCode = request.getParameter("currencyout");
		String realCharge = request.getParameter("realCharge1");
		String PuchaseCurr = request.getParameter("PuchaseCurr");
		String inCode = request.getParameter("inCode");
		String outCode = request.getParameter("outCode");
		String rate = request.getParameter("rate");
		String fee = request.getParameter("realcharge1");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		Date createDate = df.parse(date);
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		
		if(rate.equals("")||rate.equals("0")){
			mv.setViewName(jumpfail);
			mv.addObject("errmsg","汇率出错");
			return mv;
		}
		
		if(PuchaseCurr.equals("")){
			mv.setViewName(jumpfail);
			mv.addObject("errmsg","请购汇金额不能为空");
			return mv;
		}
		
		
		BigDecimal afterBalance = new BigDecimal(request.getParameter("afterbalance"));
		BigDecimal canPurchase = new BigDecimal(request.getParameter("maxCurrencyPurchase"));

		if(afterBalance.compareTo(canPurchase)==1){
			
			
			mv.setViewName(jumpfail);
			mv.addObject("errmsg","你输入的申请购汇金额有误");
			return mv;
		}
		
		
		
	
		
		if(outCode.equals("")){
			mv.setViewName(jumpfail);
			mv.addObject("errmsg","请选择转入币种");
			return mv;
		}
		
		//判断是否有币种账户，没有就创建
		Map<String,List<AcctInfo>> acctInfoMap = 
	            acctService.getAcctInfByMemberCode(Long.valueOf(memberCode));
		 
		List<AcctInfo> acctList = acctInfoMap.get("all");
		List<AcctInfo> infoList = new ArrayList<AcctInfo>();

		for(AcctInfo info:acctList){
			if(CurrencyCodeEnum.isExists(info.getCurCode())
					&&info.getAcctType()<200){
				String code = info.getCurCode();
				info.setAcctName(CurrencyCodeEnum.getCurrencyNameByCode(code));
                infoList.add(info);
			}
		}
		
//infoList.get(2);
		//
		List<String> tmpList = new ArrayList<String>();
		for (AcctInfo a : infoList) {
			tmpList.add(a.getCurCode());
		}
		if(!tmpList.contains(outCode)){
			createacc(memberDto, outCode);
		}

		CurrencyExchangeOrder currencyExchangeOrder = new CurrencyExchangeOrder();
		currencyExchangeOrder.setCurrency(inCode);
		currencyExchangeOrder.setCeOutAmount(PuchaseCurr);
		currencyExchangeOrder.setTargetCurrency(outCode);
		currencyExchangeOrder.setCeAmount(targetBalance);
		currencyExchangeOrder.setRate(rate);
		currencyExchangeOrder.setFee(fee);
		currencyExchangeOrder.setCreateDate(new Date());
		currencyExchangeOrder.setPartnerId(memberCode);
		currencyExchangeOrder.setStatus("0");
		
		
		currencyExchangeService.insertCurrencyExchange(currencyExchangeOrder);
		mv.setViewName(success);
		return mv;
		
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	
	public void setJumpfail(String jumpfail){
		this.jumpfail=jumpfail;
	}

	
	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setCurrencyExchangeService(
			CurrencyExchangeService currencyExchangeService) {
		this.currencyExchangeService = currencyExchangeService;
	}

	/*public void setCreateAccService(CreateAccService createAccService) {
		this.createAccService = createAccService;
	}*/
	
	
	
	public Long createacc(MemberDto memberDto,String acccode) throws Exception{
	
    MemberAcctTemplateDto memberAcctTemplateDto;
    Integer memberType =memberDto.getType();
    long memberCode = memberDto.getMemberCode();
    
  if(acccode.equals("CNY"))
    {
	   memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_CNY.getCode());
    }
  else if(acccode.equals("USD"))
  {
	   memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_USD.getCode());
  }
  else if(acccode.equals("EUR"))
  {
	   memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_EUR.getCode());
  }
else if(acccode.equals("GBP")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_GBP.getCode());
  }
else if(acccode.equals("HKD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_HKD.getCode());
}
else if(acccode.equals("TWD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_TWD.getCode());
}
else if(acccode.equals("AUD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_AUD.getCode());
}
else if(acccode.equals("CAD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_CAD.getCode());
}
else if(acccode.equals("INR")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_INR.getCode());
}
else if(acccode.equals("JPY")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_JPY.getCode());
}
else if(acccode.equals("KRW")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_KRW.getCode());
}
else if(acccode.equals("MOP")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_MOP.getCode());
}
else if(acccode.equals("MYR")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_MYR.getCode());
}
  
else if(acccode.equals("NZD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_NZD.getCode());
}
else if(acccode.equals("RUB")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_RUB.getCode());
}
else if(acccode.equals("SGD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_SGD.getCode());
}
else if(acccode.equals("CHF")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_CHF.getCode());
}

else if(acccode.equals("THB")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_THB.getCode());
}
else if(acccode.equals("PHP")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_PHP.getCode());
}
else if(acccode.equals("SEK")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_SEK.getCode());
}
else if(acccode.equals("TRL")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_TRL.getCode());
}
else if(acccode.equals("ILS")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_ILS.getCode());
}
else if(acccode.equals("DKK")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_DKK.getCode());
}
else if(acccode.equals("AED")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_AED.getCode());
}
else if(acccode.equals("NOK")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_NOK.getCode());
}
else if(acccode.equals("KWD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_KWD.getCode());
}
  
else if(acccode.equals("BHD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_BHD.getCode());
}
else if(acccode.equals("OMR")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_OMR.getCode());
}
else if(acccode.equals("JOD")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_JOD.getCode());
}
else if(acccode.equals("QAR")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_QAR.getCode());
}
else if(acccode.equals("KSA")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_KSA.getCode());
}
else if(acccode.equals("CZK")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_CZK.getCode());
}

else if(acccode.equals("MXN")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_MXN.getCode());
}

else if(acccode.equals("PLN")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_PLN.getCode());
}
else if(acccode.equals("ZAR")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_ZAR.getCode());
}
else if(acccode.equals("BRL")){
 memberAcctTemplateDto = memberAcctTemplateService.queryByMemberTypeAndAcctType(memberType, AcctTypeEnum.BASIC_BRL.getCode());
}
else{
System.out.print("*******error log:code is wrong********");
return null;
}
   
    Assert.notNull(memberAcctTemplateDto, "memberAcctTemplateDto must be not null");
    StringBuilder memberAcctCode = new StringBuilder();
    memberAcctCode.append(memberCode);
   memberAcctCode.append(memberAcctTemplateDto.getAcctType());
    AcctAttribDto acctAttrib = new AcctAttribDto();
    BeanUtils.copyProperties(memberAcctTemplateDto, acctAttrib);
    String acctCode = (new StringBuilder()).append(memberAcctTemplateDto.getSubjectCode()).append(memberCode).append(memberAcctTemplateDto.getAcctType()).toString();
    acctAttrib.setAcctCode(acctCode);

    acctAttrib.setCurCode(memberAcctTemplateDto.getCurCode());
    acctAttrib.setMemberAcctCode(Long.valueOf(memberAcctCode.toString()));
    acctAttrib.setCreateDate(new Date());
    acctAttrib.setUpdateDate(new Date());
    acctAttribService.createAcctAttrib(acctAttrib);
    Acct acct = new Acct();
    acct.setAcctCode(acctCode);
    acct.setMemberCode(memberCode);
    acct.setCreateDate(new Date());
    acct.setUpdateDate(new Date());
    acctService.createAcct(acct);
    return null;
	}


	
	public MemberAcctTemplateService getMemberAcctTemplateService() {
		return memberAcctTemplateService;
	}

	public void setMemberAcctTemplateService(
			MemberAcctTemplateService memberAcctTemplateService) {
		this.memberAcctTemplateService = memberAcctTemplateService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setSettlementOrderService(
			SettlementOrderService settlementOrderService) {
		this.settlementOrderService = settlementOrderService;
	}

	public void setFixedCharge(BigDecimal fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public void setFlexibleCharge(BigDecimal flexibleCharge) {
		this.flexibleCharge = flexibleCharge;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	
	
}
	



