
package com.pay.pe.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.pricingstrategy.dto.PricingStrategyDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.util.MfDate;
import com.pay.util.StringUtil;

public final class ChangePricingStrategyDetailController extends
MultiActionController {
   
	private Log logger = LogFactory.getLog(getClass());
    
	private PricingStrategyService pricingStrategyService;
    
    private PaymentServiceService paymentServiceService;
    
    private String redirectView;
    
    private String indexView ;
    private String successView ;
    
    public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws Exception {
    	
        String priceStrategyDetailCode = request.getParameter("pricestrategydetailcode");
        
        PricingStrategyCommand pricingStrategyCmd = new PricingStrategyCommand();
        PricingStrategyDetailDTO strategyDetailDto = this.pricingStrategyService.getPricingStrategyDetail(new Long(priceStrategyDetailCode));
        Assert.notNull(strategyDetailDto,"PricingStrategyDetailDTO must be not null");
        
        PricingStrategyDTO pricingStetagyDto = this.pricingStrategyService.getPricingStrategy(strategyDetailDto.getPriceStrategyCode());
        PaymentServiceDTO paymentServiceDto =paymentServiceService.getPaymentService(pricingStetagyDto.getPaymentServiceCode());
        
        pricingStrategyCmd.setPaymentservicecode(paymentServiceDto.getPaymentservicecode());
        pricingStrategyCmd.setPaymentservicename(paymentServiceDto.getPaymentservicename());
        
        pricingStrategyCmd.setPricestrategycode(pricingStetagyDto.getPriceStrategyCode());
        pricingStrategyCmd.setPricestrategyname(pricingStetagyDto.getPriceStrategyName());
        
        pricingStrategyCmd.setCaculatemethod(pricingStetagyDto.getCaculateMethod());
        pricingStrategyCmd.setPricestrategytype(pricingStetagyDto.getPriceStrategyType().toString());
        
        pricingStrategyCmd.setValiddate(new MfDate(pricingStetagyDto.getValidDate()).toString());
        pricingStrategyCmd.setInvaliddate(new MfDate(pricingStetagyDto.getInvalidDate())        
            .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(pricingStetagyDto.getInvalidDate()).toString());
        
        pricingStrategyCmd.setEffectivefrom(new MfDate(strategyDetailDto.getEffectiveFrom()).toString());
        pricingStrategyCmd.setEffectiveto(new MfDate(strategyDetailDto.getEffectiveTo()).toString());
//        pricingStrategyCmd.setEffectivefrom(new MfDate(strategyDetailDto.getEffectiveFrom())        
//        .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(strategyDetailDto.getEffectiveFrom()).toString());
//        pricingStrategyCmd.setEffectiveto(new MfDate(strategyDetailDto.getEffectiveTo())        
//        .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(strategyDetailDto.getEffectiveTo()).toString());
        
        pricingStrategyCmd.setTerminltype(strategyDetailDto.getTerminalTypeCode());
        String reservedCode = strategyDetailDto.getReservedCode();
        if(reservedCode != null && !"".equals(reservedCode)){
        	int reservedCodeLength = reservedCode.length();
        	if(reservedCodeLength < 2){
        		pricingStrategyCmd.setReservedCode(strategyDetailDto.getReservedCode());
        	}else{
        		if("10".equals(reservedCode.substring(reservedCodeLength-2, reservedCodeLength))){
        			reservedCode = reservedCode.substring(0, reservedCodeLength-2);
        		}
        		pricingStrategyCmd.setReservedCode(reservedCode);
        	}
        }else{
        	pricingStrategyCmd.setReservedCode(strategyDetailDto.getReservedCode());
        }
//        pricingStrategyCmd.setReservedCode(strategyDetailDto.getReservedCode());
        pricingStrategyCmd.setReservedName(strategyDetailDto.getReservedName());
        if (strategyDetailDto.getRangBy() != null) {
            pricingStrategyCmd.setRangby(strategyDetailDto.getRangBy());
        }
        pricingStrategyCmd.setRangfrom(strategyDetailDto.getRangFrom().toString());
        pricingStrategyCmd.setRangto(strategyDetailDto.getRangTo().toString());
        
        pricingStrategyCmd.setChargerate(strategyDetailDto.getChargeRate());
        pricingStrategyCmd.setFixedcharge(strategyDetailDto.getFixedCharge()
            .toString());
        pricingStrategyCmd.setMaxcharge(strategyDetailDto.getMaxCharge()
            .toString());
        pricingStrategyCmd.setMincharge(strategyDetailDto.getMinCharge()
            .toString());
        pricingStrategyCmd.setPricestrategydetailcode(strategyDetailDto.getPriceStrategyDetailCode());
       
        pricingStrategyCmd.setStatus(pricingStetagyDto.getStatus());
        Map<String, Object>  refData = new HashMap<String, Object>();
        refData.put("paymentServiceDto", paymentServiceDto);
        refData.put("pricingStetagyDto" , pricingStetagyDto);
        return new ModelAndView(indexView).addObject("pricingStrategyCmd", pricingStrategyCmd).addAllObjects(refData);
    }
    
    

    
    public ModelAndView submit(final HttpServletRequest request,
            final HttpServletResponse response, final PricingStrategyCommand cmd
           ) throws Exception {
        String  url = response.encodeRedirectURL(this.getSuccessView());
        
        PricingStrategyCommand pricingStrategyCmd =(PricingStrategyCommand) cmd;
        PricingStrategyDetailDTO dto = new PricingStrategyDetailDTO();
        
        dto.setPriceStrategyCode(pricingStrategyCmd.getPricestrategycode());
        
       
        dto.setChargeRate(pricingStrategyCmd.getChargerate());
        dto.setFixedCharge(StringUtil.isNull(pricingStrategyCmd.getFixedcharge()) ? 0 : Long.parseLong(pricingStrategyCmd.getFixedcharge()));
        dto.setMaxCharge(StringUtil.isNull(pricingStrategyCmd.getMaxcharge()) ? 0 : Long.parseLong(pricingStrategyCmd.getMaxcharge()));
        dto.setMinCharge(StringUtil.isNull(pricingStrategyCmd.getMincharge()) ? 0 : Long.parseLong(pricingStrategyCmd.getMincharge()));
        dto.setPriceStrategyDetailCode(pricingStrategyCmd.getPricestrategydetailcode());
        
        if (!StringUtil.isNull(pricingStrategyCmd.getRangfrom())) {
            dto.setRangFrom(new Long(pricingStrategyCmd.getRangfrom()));
        }
        if (!StringUtil.isNull(pricingStrategyCmd.getRangto())) {
            dto.setRangTo(new Long(pricingStrategyCmd.getRangto()));
        }     
        
		dto.setEffectiveFrom(new Timestamp(new MfDate(1900, 1, 1).getTime().getTime()));
		dto.setEffectiveTo(new Timestamp(new MfDate(1900, 1, 1).getTime().getTime()));
		/*if(!StringUtil.isEmpty(pricingStrategyCmd.getEffectivefrom())){
			dto.setEffectiveFrom(new Timestamp(Date.valueOf(pricingStrategyCmd.getEffectivefrom()).getTime()));
		}else{
			dto.setEffectiveFrom(new Timestamp(new MfDate(1900, 1, 1).getTime().getTime()));
		}
		if(!StringUtil.isEmpty(pricingStrategyCmd.getEffectiveto())){
			dto.setEffectiveTo(new Timestamp(Date.valueOf(pricingStrategyCmd.getEffectiveto()).getTime()));
		}else{
			dto.setEffectiveTo(new Timestamp(new MfDate(1900, 1, 1).getTime().getTime()));
		}*/
		dto.setRangBy(1L);
		dto.setTerminalTypeCode(1);
		//dto.setReservedCode(null);
		if (!StringUtil.isNull(pricingStrategyCmd.getReservedCode())) {
			String reservedCode = pricingStrategyCmd.getReservedCode().trim() + "10";
			dto.setReservedCode(reservedCode);
			//dto.setReservedCode(pricingStrategyCmd.getReservedCode());
	    }
		dto.setReservedName(null);
		
		try {
			this.pricingStrategyService.changePricingStrategyDetail(dto);
		} catch (Exception e) {
			PricingStrategyDTO pricingStetagyDto = this.pricingStrategyService.getPricingStrategy(pricingStrategyCmd.getPricestrategycode());
			PaymentServiceDTO paymentServiceDto = paymentServiceService.getPaymentService(pricingStetagyDto.getPaymentServiceCode());
			logger.info("price Strategy Detail add error " + e.getMessage()); 
			return new ModelAndView(indexView).addObject("pricingstrategycode", pricingStrategyCmd.getPricestrategycode())
			.addObject("paymentServiceDto", paymentServiceDto)
			.addObject("pricingStetagyDto", pricingStetagyDto)
			.addObject("pricingStrategyCmd", pricingStrategyCmd)
			.addObject("error", e.getMessage());
		}
		
		request.getSession().setAttribute("pricingstrategycode",pricingStrategyCmd.getPricestrategycode());
		return new ModelAndView(url);
    }
    
    
    
    //
   
    public String getRedirectView() {
        return redirectView;
    }
    public void setRedirectView(String redirectView) {
        this.redirectView = redirectView;
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



	public String getIndexView() {
		return indexView;
	}



	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}



	public String getSuccessView() {
		return successView;
	}



	public void setSuccessView(String successView) {
		this.successView = successView;
	}
    
    
   
}
