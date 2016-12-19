package com.pay.pe.template.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.exception.AppException;
import com.pay.pe.controller.PricingStrategyCommand;
import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.pe.template.dto.PrStgyTemDTO;
import com.pay.pe.template.service.PrStgyTemService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.pricingstrategy.dto.PricingStrategyDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.util.MfDate;

public class PrStgyTemController extends MultiActionController{
	private Log logger = LogFactory.getLog(PrStgyTemController.class);
	private PrStgyTemService prStgyTemService;
	private PricingStrategyService pricingStrategyService;
	private PaymentServiceService paymentServiceService;
	
	/**
	 * 價格策略模板新增視圖
	 */
	private String prstgytemAddView;
	/**
	 * 價格策略模板更新視圖
	 */
	private String prstgytemEditView;
	/**
	 * 價格策略模板查詢視圖
	 */
	private String prstgytemInitView;
	/**
	 * 價格策略模板查詢顯示視圖
	 */
	private String prstgytemListView;
	/**
	 * 添加價格策略明顯跳轉
	 */
	private String prstgytemAddSelView;
	private String prstgytemEditSelView;
	//支付服務碼，價格策略碼
	/**
	 * 價格策略模板新增請求
	 */
	public ModelAndView prstgytemAdd(HttpServletRequest request,HttpServletResponse response){
		String priceStrategyCode = request.getParameter("priceStrategyCode");
		Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
		String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
		if(priceStrategyDetailCode == null || "".equals(priceStrategyDetailCode)){
			priceStrategyDetailCode = "";
		}
		return new ModelAndView(prstgytemAddView)
		.addObject("pricingStrategyCmd",new PricingStrategyCommand())
				.addObject("priceStrategyCode", priceStrategyCode)
				.addObject("pricestrategydetailcode", priceStrategyDetailCode);
	}
	/**
	 * 價格策略模板新增結果
	 */
	public ModelAndView prstgytemAddResult(HttpServletRequest request,HttpServletResponse response){
		String priceStrategyCode = request.getParameter("priceStrategyCode");
		Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
		String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
		if(priceStrategyDetailCode == null || "".equals(priceStrategyDetailCode)){
			priceStrategyDetailCode = "";
		}
		PrStgyTemDTO prStgyTemDTO = getFormObject(request);
		try {
			prStgyTemService.createPrStgyTem(prStgyTemDTO);
			return new ModelAndView(prstgytemInitView).addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode)
					.addObject("info", "添加價格策略模板成功!");
		} catch (AppException e) {
			logger.error(e.getMessage(), e);
			return new ModelAndView(prstgytemInitView).addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode)
					.addObject("info", "添加價格策略模板失敗!");
		}
	}
	/**
	 * 價格策略模板更新請求
	 * @throws AppException 
	 */
	public ModelAndView prstgytemEdit(HttpServletRequest request,HttpServletResponse response) {
		String priceStrategyCode = request.getParameter("priceStrategyCode");
		Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
		String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
		if(priceStrategyDetailCode == null || "".equals(priceStrategyDetailCode)){
			priceStrategyDetailCode = "";
		}
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		PrStgyTemDTO prStgyTemDTO = prStgyTemService.findTemById(id);
		if(prStgyTemDTO != null && prStgyTemDTO instanceof PrStgyTemDTO){
			return new ModelAndView(prstgytemEditView).addObject("pricingStrategyCmd",new PricingStrategyCommand())
					.addObject("prStgyTemDTO", prStgyTemDTO)
					.addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode);
		}
		else{
			return new ModelAndView(prstgytemInitView).addObject("pricingStrategyCmd",new PricingStrategyCommand())
					.addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode);
		}
	}
	/**
	 * 價格策略模板刪除請求
	 * @throws AppException 
	 */
	public ModelAndView prstgytemDel(HttpServletRequest request,HttpServletResponse response) {
		String priceStrategyCode = request.getParameter("priceStrategyCode");
		Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
		String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
		if(priceStrategyDetailCode == null || "".equals(priceStrategyDetailCode)){
			priceStrategyDetailCode = "";
		}
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		if(prStgyTemService.deleteTemById(id)){
			return new ModelAndView(prstgytemInitView).addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode)
					.addObject("info", "刪除價格策略模板成功!");
		}else{
			return new ModelAndView(prstgytemInitView).addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode)
					.addObject("info", "刪除價格策略模板失敗!");
		}
	}
		/**
		 * 價格策略模板選擇請求
		 * @throws AppException 
		 */
		public ModelAndView prstgytemSel(HttpServletRequest request,HttpServletResponse response) {
			String priceStrategyCode = request.getParameter("priceStrategyCode");
			Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
			String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
			if(priceStrategyDetailCode == null || "".equals(priceStrategyDetailCode)){
				priceStrategyDetailCode = "";
			}
			String idStr = request.getParameter("id");
			Long id = Long.parseLong(idStr);
			PrStgyTemDTO prStgyTemDTO = prStgyTemService.findTemById(id);
			PricingStrategyCommand pricingStrategyCmd = new PricingStrategyCommand();
			if(prStgyTemDTO != null && prStgyTemDTO instanceof PrStgyTemDTO){
				if(prStgyTemDTO.getFixedCharge() != null){
					pricingStrategyCmd.setFixedcharge(prStgyTemDTO.getFixedCharge().toString());
				}
				if(prStgyTemDTO.getChargeRate() != null){
					pricingStrategyCmd.setChargerate(prStgyTemDTO.getChargeRate());
				}
				if(prStgyTemDTO.getRangFrom() != null){
					pricingStrategyCmd.setRangfrom(prStgyTemDTO.getRangFrom().toString());
				}
				if(prStgyTemDTO.getRangTo() != null){
					pricingStrategyCmd.setRangto(prStgyTemDTO.getRangTo().toString());
				}
				if(prStgyTemDTO.getMaxCharge() != null){
					pricingStrategyCmd.setMaxcharge(prStgyTemDTO.getMaxCharge().toString());
				}
				if(prStgyTemDTO.getMinCharge() != null){
					pricingStrategyCmd.setMincharge(prStgyTemDTO.getMinCharge().toString());
				}
				//System.out.println("setFixedcharge="+prStgyTemDTO.getFixedCharge()+"setChargerate="+prStgyTemDTO.getChargeRate()+"setRangfrom="+prStgyTemDTO.getRangFrom()+"setRangto"+prStgyTemDTO.getRangTo()+"setMaxcharge="+prStgyTemDTO.getMaxCharge()+"setMincharge="+prStgyTemDTO.getMinCharge());
				//System.out.println("setFixedcharge="+pricingStrategyCmd.getFixedcharge()+"setChargerate="+pricingStrategyCmd.getChargerate()+"setRangfrom="+pricingStrategyCmd.getRangfrom()+"setRangto"+pricingStrategyCmd.getRangto()+"setMaxcharge="+pricingStrategyCmd.getMaxcharge()+"setMincharge="+pricingStrategyCmd.getMincharge());
			}
			
			PricingStrategyDTO pricingStetagyDto = this.pricingStrategyService
		            .getPricingStrategy(new Long(priceStrategyCode));
			PaymentServiceDTO paymentServiceDto =paymentServiceService.getPaymentService(pricingStetagyDto.getPaymentServiceCode());
			Map<String, Object>  refData = new HashMap<String, Object>();
			
			refData.put("paymentServiceDto", paymentServiceDto);
		    refData.put("pricingStetagyDto" , pricingStetagyDto);
		    System.out.println("prstgytemAddSelView="+prstgytemAddSelView+"priceStrategyDetailCode="+priceStrategyDetailCode);
			if(null == priceStrategyDetailCode || "".equals(priceStrategyDetailCode)){//add
				refData.put("pricingStrategyCmd", pricingStrategyCmd);
				String validdate = new MfDate(pricingStetagyDto.getValidDate()).toString();
		        String invaliddate = new MfDate(pricingStetagyDto.getInvalidDate())        
		        .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(pricingStetagyDto.getInvalidDate()).toString();
		        refData.put("validdate", validdate);
		        refData.put("invaliddate", invaliddate);
				return new ModelAndView(prstgytemAddSelView).addAllObjects(refData);
			}else{//edit
				PricingStrategyDetailDTO strategyDetailDto = this.pricingStrategyService.getPricingStrategyDetail(new Long(priceStrategyDetailCode));
				pricingStrategyCmd.setPaymentservicecode(paymentServiceDto.getPaymentservicecode());
		        pricingStrategyCmd.setPaymentservicename(paymentServiceDto.getPaymentservicename());
		        
		        pricingStrategyCmd.setPricestrategycode(pricingStetagyDto.getPriceStrategyCode());
		        pricingStrategyCmd.setPricestrategyname(pricingStetagyDto.getPriceStrategyName());
		        
		        pricingStrategyCmd.setCaculatemethod(pricingStetagyDto.getCaculateMethod());
		        pricingStrategyCmd.setPricestrategytype(pricingStetagyDto.getPriceStrategyType().toString());
		        
		        pricingStrategyCmd.setValiddate(new MfDate(pricingStetagyDto.getValidDate()).toString());
		        pricingStrategyCmd.setInvaliddate(new MfDate(pricingStetagyDto.getInvalidDate())        
		            .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(pricingStetagyDto.getInvalidDate()).toString());
		        
//		        pricingStrategyCmd.setEffectivefrom(new MfTime(strategyDetailDto.getEffectiveFrom()).toString());
//		        pricingStrategyCmd.setEffectiveto(new MfTime(strategyDetailDto.getEffectiveTo()).toString());
		        pricingStrategyCmd.setEffectivefrom(new MfDate(strategyDetailDto.getEffectiveFrom())        
		        .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(strategyDetailDto.getEffectiveFrom()).toString());
		        pricingStrategyCmd.setEffectiveto(new MfDate(strategyDetailDto.getEffectiveTo())        
		        .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(strategyDetailDto.getEffectiveTo()).toString());
		        
		        pricingStrategyCmd.setTerminltype(strategyDetailDto.getTerminalTypeCode());
		        pricingStrategyCmd.setReservedCode(strategyDetailDto.getReservedCode());
		        pricingStrategyCmd.setReservedName(strategyDetailDto.getReservedName());
		        if (strategyDetailDto.getRangBy() != null) {
		            pricingStrategyCmd.setRangby(strategyDetailDto.getRangBy());
		        }
		        
		        //pricingStrategyCmd.setRangfrom(strategyDetailDto.getRangFrom().toString());
		        //pricingStrategyCmd.setRangto(strategyDetailDto.getRangTo().toString());
		        
		        //pricingStrategyCmd.setChargerate(strategyDetailDto.getChargeRate());
		        //pricingStrategyCmd.setFixedcharge(strategyDetailDto.getFixedCharge().toString());
		        //pricingStrategyCmd.setMaxcharge(strategyDetailDto.getMaxCharge().toString());
		        //pricingStrategyCmd.setMincharge(strategyDetailDto.getMinCharge().toString());
		        pricingStrategyCmd.setPricestrategydetailcode(strategyDetailDto.getPriceStrategyDetailCode());
		       
		        pricingStrategyCmd.setStatus(pricingStetagyDto.getStatus());
		        refData.put("pricingStrategyCmd", pricingStrategyCmd);
		        return new ModelAndView(prstgytemEditSelView).addAllObjects(refData);
			}
		}
	/**
	 * 價格策略模板更新結果
	 */
	public ModelAndView prstgytemEditResult(HttpServletRequest request,HttpServletResponse response){
		String priceStrategyCode = request.getParameter("priceStrategyCode");
		Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
		String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
		if(priceStrategyDetailCode == null ||"".equals(priceStrategyDetailCode)){
			priceStrategyDetailCode = "";
		}
		PrStgyTemDTO prStgyTemDTO = getFormObject(request);
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		prStgyTemDTO.setId(id);
		try {
			prStgyTemService.updatePrStgyTem(prStgyTemDTO);
			return new ModelAndView(prstgytemInitView).addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode)
					.addObject("info", "更新價格策略模板成功!");
		} catch (AppException e) {
			logger.error(e.getMessage(), e);
			return new ModelAndView(prstgytemInitView).addObject("priceStrategyCode", priceStrategyCode)
					.addObject("pricestrategydetailcode", priceStrategyDetailCode)
					.addObject("info", "更新價格策略模板失敗!");
		}
	}
	/**
	 * 價格策略模板查詢請求
	 */
	public ModelAndView prstgytemInit(HttpServletRequest request,HttpServletResponse response){
		String priceStrategyCode = request.getParameter("priceStrategyCode");
		Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
		String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
		if(priceStrategyDetailCode == null || "".equals(priceStrategyDetailCode)){
			priceStrategyDetailCode = "";
		}
		return new ModelAndView(prstgytemInitView).addObject("priceStrategyCode", priceStrategyCode)
				.addObject("pricestrategydetailcode", priceStrategyDetailCode);
	}
	/**
	 * 價格策略模板查詢結果
	 */
	public ModelAndView prstgytemList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String priceStrategyCode = request.getParameter("priceStrategyCode");
		Assert.notNull(priceStrategyCode, " pricestrategycode must be not null");
		String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
		if(priceStrategyDetailCode == null || "".equals(priceStrategyDetailCode)){
			priceStrategyDetailCode = "";
		}
		PricingStrategyDTO pricingStetagyDto = this.pricingStrategyService.getPricingStrategy(Long.parseLong(priceStrategyCode));
		Integer priceStrategyType = pricingStetagyDto.getPriceStrategyType();
		
		PrStgyTemDTO prStgyTemDTO = new PrStgyTemDTO();
		if(null != priceStrategyType){
			prStgyTemDTO.setPriceStrategyType(priceStrategyType);
		}
//		String templateName = request.getParameter("templateName");
//		if(null != templateName && !templateName.trim().equals("")){
//			prStgyTemDTO.setTemplateName(templateName);
//		}
		Page<PrStgyTemDTO> page = PageUtils.getPage(request);
		int pageEnd = page.getPageNo()*page.getPageSize();
		int pageStart = 0;
		if((page.getPageNo()-1)==0){
			pageStart = 0;
		}else{
			pageStart = (page.getPageNo()-1)*page.getPageSize();
		}
		List<PrStgyTemDTO> templateList = prStgyTemService.queryByPage(prStgyTemDTO, pageStart, pageEnd);
		Integer templateCount = prStgyTemService.queryCount(prStgyTemDTO);
		
		page.setResult(templateList);
		page.setTotalCount(templateCount);
		
		Map<Integer, String> pricestrategytypeMap = new HashMap<Integer, String>();
		for(PRICESTRATEGYTYPE aaa :PRICESTRATEGYTYPE.values()){
    		pricestrategytypeMap.put(aaa.getValue(), aaa.getPRICESTRATEGYTYPEDesc(aaa.getValue()));
    	}
		return new ModelAndView(prstgytemListView).addObject("page", page)
				.addObject("priceStrategyCode", priceStrategyCode)
				.addObject("pricestrategydetailcode", priceStrategyDetailCode)
				.addObject("pricestrategytypeMap", pricestrategytypeMap);
	}
	
	/*
	 * 从页面表单获得PrStgyTemDTO
	 */
	private PrStgyTemDTO getFormObject(HttpServletRequest request){
		PrStgyTemDTO prStgyTemDTO = new PrStgyTemDTO();
		String templateName = request.getParameter("templateName");
		String fixedCharge = request.getParameter("fixedCharge");
		String priceStrategyType = request.getParameter("priceStrategyType");
		String chargeRate = request.getParameter("chargeRate");
		String rangFrom = request.getParameter("rangFrom");
		String rangTo = request.getParameter("rangTo");
		String maxChange = request.getParameter("maxChange");
		String minCharge = request.getParameter("minCharge");
		if(templateName != null && !templateName.trim().equals("")){
			prStgyTemDTO.setTemplateName(templateName);
		}
		if(fixedCharge != null && !fixedCharge.trim().equals("")){
			prStgyTemDTO.setFixedCharge(Long.parseLong(fixedCharge));
		}
		if(chargeRate != null && !chargeRate.trim().equals("")){
			prStgyTemDTO.setChargeRate(Long.parseLong(chargeRate));
		}
		if(rangFrom != null && !rangFrom.trim().equals("")){
			prStgyTemDTO.setRangFrom(Long.parseLong(rangFrom));
		}
		if(rangTo != null && !rangTo.trim().equals("")){
			prStgyTemDTO.setRangTo(Long.parseLong(rangTo));
		}
		if(maxChange != null && !maxChange.trim().equals("")){
			prStgyTemDTO.setMaxCharge(Long.parseLong(maxChange));
		}
		if(minCharge != null && !minCharge.trim().equals("")){
			prStgyTemDTO.setMinCharge(Long.parseLong(minCharge));
		}	
		if(priceStrategyType != null && !priceStrategyType.trim().equals("")){
			prStgyTemDTO.setPriceStrategyType(Integer.parseInt(priceStrategyType));
		}
		prStgyTemDTO.setCreateDate(new Date());
		prStgyTemDTO.setUpdateDate(new Date());
		prStgyTemDTO.setCreatedBy(SessionUserHolderUtil.getLoginId());
		prStgyTemDTO.setModifiedBy(SessionUserHolderUtil.getLoginId());
		
		return prStgyTemDTO;
	}
	
	public PrStgyTemService getPrStgyTemService() {
		return prStgyTemService;
	}
	public void setPrStgyTemService(PrStgyTemService prStgyTemService) {
		this.prStgyTemService = prStgyTemService;
	}
	public String getPrstgytemAddView() {
		return prstgytemAddView;
	}
	public void setPrstgytemAddView(String prstgytemAddView) {
		this.prstgytemAddView = prstgytemAddView;
	}
	public String getPrstgytemEditView() {
		return prstgytemEditView;
	}
	public void setPrstgytemEditView(String prstgytemEditView) {
		this.prstgytemEditView = prstgytemEditView;
	}
	public String getPrstgytemInitView() {
		return prstgytemInitView;
	}
	public void setPrstgytemInitView(String prstgytemInitView) {
		this.prstgytemInitView = prstgytemInitView;
	}
	public String getPrstgytemListView() {
		return prstgytemListView;
	}
	public void setPrstgytemListView(String prstgytemListView) {
		this.prstgytemListView = prstgytemListView;
	}
	public PricingStrategyService getPricingStrategyService() {
		return pricingStrategyService;
	}
	public void setPricingStrategyService(
			PricingStrategyService pricingStrategyService) {
		this.pricingStrategyService = pricingStrategyService;
	}

	public PaymentServiceService getPaymentServiceService() {
		return paymentServiceService;
	}
	public void setPaymentServiceService(PaymentServiceService paymentServiceService) {
		this.paymentServiceService = paymentServiceService;
	}
	public String getPrstgytemAddSelView() {
		return prstgytemAddSelView;
	}
	public void setPrstgytemAddSelView(String prstgytemAddSelView) {
		this.prstgytemAddSelView = prstgytemAddSelView;
	}
	public String getPrstgytemEditSelView() {
		return prstgytemEditSelView;
	}
	public void setPrstgytemEditSelView(String prstgytemEditSelView) {
		this.prstgytemEditSelView = prstgytemEditSelView;
	}
}
