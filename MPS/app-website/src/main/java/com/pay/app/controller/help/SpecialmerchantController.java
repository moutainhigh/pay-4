package com.pay.app.controller.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.common.util.IpAddress;
import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.base.model.CardType;
import com.pay.base.model.SpEnumInfo;
import com.pay.base.model.SpecialMerchant;
import com.pay.base.model.SpecialMerchantCity;
import com.pay.base.service.specialmerchant.SpecialmerchantService;
import com.pay.inf.dto.CityDTO;
import com.pay.util.WebUtil;

public class SpecialmerchantController extends MultiActionController{
	
	public static final String specialname = "请输入商户名称";
	private Log log = LogFactory.getLog(this.getClass().getName());
	private String introprepaidcardViewPrefix;
	private String introprepaidcardIndex;
	
	private SpecialmerchantService specialmerchantService;
	private BankAcctServiceFacade bankAcctService;

	public ModelAndView indexspecialmerchant(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/**
		 *根据IP获得所在的区域 
		 */
		String ip = WebUtil.getIp(request);
		//ip = "116.235.55.251";
		IpAddress ipAddr = IpAddress.getInstance();
		String countrys=ipAddr.IpStringToAddress(ip); 
		if(log.isInfoEnabled()){	
			log.info("ip="+ip);
			log.info("countrys="+countrys);
		}	
		
		String pageView = ServletRequestUtils.getStringParameter(request, "view",introprepaidcardIndex);
		List<SpEnumInfo> cardTypes = specialmerchantService.getSpEnumInfoList(1);
		List<SpEnumInfo> products = specialmerchantService.getSpEnumInfoList(2);
		List<SpecialMerchantCity> SpecialMerchantCitys = specialmerchantService.getSpecialMerchantCityCodeList();
		List<CityDTO> citys = new ArrayList<CityDTO>();
		for (SpecialMerchantCity specialMerchantCity : SpecialMerchantCitys) {
			CityDTO citydto = new CityDTO();
			String cityname = bankAcctService.getCityById(Integer.valueOf(specialMerchantCity.getProvince_code()));
			citydto.setCitycode(Integer.valueOf(specialMerchantCity.getProvince_code()));
			citydto.setCityname(cityname);
			citys.add(citydto);
		}
		
		int cityCode = 0;
		for(CityDTO cityDTO:citys){		
			if(countrys.contains(cityDTO.getCityname())){
				cityCode = cityDTO.getCitycode();
				break;
			}
		}
		
		SpecialMerchant dto = new SpecialMerchant();
		int pager_offset=1;  //当前页码
		int page_size=5;     //每页条数
		pager_offset=request.getParameter("pager_offset")==null?1:Integer.parseInt(request.getParameter("pager_offset")); //获取页面传过来的页码	
		//查询条件
		String sp_merchant_name = ServletRequestUtils.getStringParameter(request, "specialname", "");
		String province_code = ServletRequestUtils.getStringParameter(request, "province_code", "");
		Long range_id = ServletRequestUtils.getLongParameter(request, "range_id",0L);
		String card_type_id = ServletRequestUtils.getStringParameter(request, "card_type_id", "");
		
		
		if(cityCode==0){
			if(province_code.equals("all")){
				dto.setProvince_code("");
			}else{
				dto.setProvince_code(province_code);
			}
		}else{
			if(StringUtils.isEmpty(province_code)){
				dto.setProvince_code(String.valueOf(cityCode));	
			}else if(province_code.equals("all")){
				dto.setProvince_code("");
			}else{
				dto.setProvince_code(province_code);
			}
		}
		
		dto.setRange_id(range_id);
		dto.setCard_type_id(card_type_id);
		
		if(sp_merchant_name.equals(specialname)||StringUtils.isEmpty(sp_merchant_name)){
			sp_merchant_name = "";
		}else {
			dto.setProvince_code("");
		}
		
		dto.setSp_merchant_name(sp_merchant_name);
		
		//查询总记录数
		int  sum = specialmerchantService.querySpecialmerchantSum(dto);
		
		PageUtil pu = null;
		pu=new PageUtil(pager_offset,page_size,sum);//分页处理
		
		//查询记录数
		List<SpecialMerchant> specialMerchantList = specialmerchantService.querySpecialMerchantList(pager_offset, page_size,dto);
		
		for(SpecialMerchant specialMerchant : specialMerchantList){
					
			if(!StringUtils.isEmpty(specialMerchant.getMerchant_intro())&&specialMerchant.getMerchant_intro().length()>240){
				specialMerchant.setMerchant_intro(specialMerchant.getMerchant_intro().substring(0, 240)+"......");
			}
		
			CardType cardType = new CardType();
			cardType.setSp_merchant_id(specialMerchant.getSp_merchant_id());
			List<CardType> cardTypeList = specialmerchantService.queryCardTypeList(cardType);
			specialMerchant.setCardTypeList(cardTypeList);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardTypes", cardTypes);
		map.put("products", products);
		map.put("citys", citys);
		map.put("sum", sum);
		map.put("specialMerchantList", specialMerchantList);
		map.put("specialmerchantdto", dto);
		map.put("pu", pu);
		return new ModelAndView(introprepaidcardViewPrefix+pageView,map).addObject("view",pageView);
	}

	public ModelAndView viewdetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",introprepaidcardIndex);
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long sp_merchant_id = ServletRequestUtils.getLongParameter(request,"sp_merchant_id",0L);
		SpecialMerchant specialMerchant = specialmerchantService.getSpecialMerchantdetail(sp_merchant_id);
		
		String sp_merchant_logo = specialMerchant.getSp_merchant_logo();
		
		if(!StringUtils.isEmpty(sp_merchant_logo)){
			specialMerchant.setSp_merchant_logo(sp_merchant_logo.replace(".", "_big."));
		}
		
		
		CardType cardType = new CardType();
		cardType.setSp_merchant_id(specialMerchant.getSp_merchant_id());
		List<CardType> cardTypeList = specialmerchantService.queryCardTypeList(cardType);
		specialMerchant.setCardTypeList(cardTypeList);
		
		map.put("specialMerchant", specialMerchant);
		
		return new ModelAndView(introprepaidcardViewPrefix+pageView,map).addObject("view",pageView);
		
	}
	public void setSpecialmerchantService(
			SpecialmerchantService specialmerchantService) {
		this.specialmerchantService = specialmerchantService;
	}
	
	public void setBankAcctService(BankAcctServiceFacade bankAcctService) {
		this.bankAcctService = bankAcctService;
	}

	public void setIntroprepaidcardViewPrefix(String introprepaidcardViewPrefix) {
		this.introprepaidcardViewPrefix = introprepaidcardViewPrefix;
	}

	public void setIntroprepaidcardIndex(String introprepaidcardIndex) {
		this.introprepaidcardIndex = introprepaidcardIndex;
	}
	
}



