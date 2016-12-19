package com.pay.pe.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


public final class AddPricingStrategyDetailController extends MultiActionController {
	
	private PricingStrategyService pricingStrategyService;
    
    private PaymentServiceService paymentServiceService;
    
    
    private String redirectView;
    
    private String indexView ;
    
    private String successView ;
    
  
    public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws Exception {
        Map<String, Object>  refData = new HashMap<String, Object>();
        String pricestrategycode = request.getParameter("pricestrategycode");
        Assert.notNull(pricestrategycode, " pricestrategycode must be not null");
        PricingStrategyDTO pricingStetagyDto = this.pricingStrategyService
            .getPricingStrategy(new Long(pricestrategycode));
        
        PaymentServiceDTO paymentServiceDto =paymentServiceService.getPaymentService(pricingStetagyDto.getPaymentServiceCode());
        
        String validdate = new MfDate(pricingStetagyDto.getValidDate()).toString();
        String invaliddate = new MfDate(pricingStetagyDto.getInvalidDate())        
        .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(pricingStetagyDto.getInvalidDate()).toString();
        
        refData.put("paymentServiceDto", paymentServiceDto);
        refData.put("pricingStetagyDto" , pricingStetagyDto);
        refData.put("validdate", validdate);
        refData.put("invaliddate", invaliddate);
        return new ModelAndView(indexView).addAllObjects(refData);
    }

   
    public ModelAndView submit(final HttpServletRequest request,final HttpServletResponse response, 
    		final PricingStrategyCommand cmd) throws Exception {
    	
        PricingStrategyCommand pricingStrategyCmd = (PricingStrategyCommand) cmd;
        request.getSession().setAttribute("pricingstrategycode",pricingStrategyCmd.getPricestrategycode());
        
        PricingStrategyDetailDTO dto = new PricingStrategyDetailDTO();
        String  url = response.encodeRedirectURL(this.getSuccessView());
        dto.setChargeRate(pricingStrategyCmd.getChargerate());
        
        if (!StringUtil.isNull(pricingStrategyCmd.getFixedcharge())) {
            dto.setFixedCharge(new Long(pricingStrategyCmd.getFixedcharge()));
        }
        if (!StringUtil.isNull(pricingStrategyCmd.getMaxcharge())) {
            dto.setMaxCharge(new Long(pricingStrategyCmd.getMaxcharge()));
        }
        if (!StringUtil.isNull(pricingStrategyCmd.getMincharge())) {
            dto.setMinCharge(new Long(pricingStrategyCmd.getMincharge()));
        }
        
        if (!StringUtil.isNull(pricingStrategyCmd.getRangfrom())) {
            dto.setRangFrom(new Long(pricingStrategyCmd.getRangfrom()));
        }
        if (!StringUtil.isNull(pricingStrategyCmd.getRangto())) {
            dto.setRangTo(new Long(pricingStrategyCmd.getRangto()));
        }       
        
		dto.setPriceStrategyCode(pricingStrategyCmd.getPricestrategycode());
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
			this.pricingStrategyService.addPricingStrategyDetail(dto);
		} catch (Exception e) {
			PricingStrategyDTO pricingStetagyDto = this.pricingStrategyService.getPricingStrategy(pricingStrategyCmd.getPricestrategycode());
			PaymentServiceDTO paymentServiceDto =paymentServiceService.getPaymentService(pricingStetagyDto.getPaymentServiceCode());
			logger.info("price Strategy Detail add error " + e.getMessage()); 
			return new ModelAndView(indexView).addObject("pricingstrategycode", pricingStrategyCmd.getPricestrategycode())
			.addObject("paymentServiceDto", paymentServiceDto)
			.addObject("pricingStetagyDto", pricingStetagyDto)
			.addObject("pricingStrategyCmd", pricingStrategyCmd)
			.addObject("error", e.getMessage());
		}
		request.getSession().setAttribute("pricingstrategycode",pricingStrategyCmd.getPricestrategycode());
		return new ModelAndView(url).addObject("pricingstrategycode", pricingStrategyCmd.getPricestrategycode());
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

	public String getRedirectView() {
		return redirectView;
	}

	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
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