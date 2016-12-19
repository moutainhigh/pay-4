package com.pay.pe.controller;

import java.sql.Date;
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
import com.pay.pricingstrategy.helper.EFFECTIVEON;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.util.MfDate;
import com.pay.util.StringUtil;

public final class AddPricingStrategyController extends MultiActionController {
    
    private PricingStrategyService pricingStrategyService;

    private PaymentServiceService paymentServiceService;
  
    private String indexView ;
    private String successView ;
    
    
    public Map refData(HttpServletRequest request){
    	Map  refData = new HashMap();
        String paymentServiceCode = request.getParameter("paymentservicecode");
        Assert.notNull(paymentServiceCode);
        
        PaymentServiceDTO paymentServiceDto = paymentServiceService.getPaymentService(Integer.valueOf(paymentServiceCode));
        refData.put("paymentServiceDto" , paymentServiceDto);
        
//        String pricestrategycode = request.getParameter("pricestrategycode");
        refData.put("paymentservicecode" , paymentServiceCode);
        
        PricingStrategyCommand cmd = new PricingStrategyCommand();
        cmd.getEffectiveonMap().remove(EFFECTIVEON.SPECIFIDESERVERLEVEL.getValue());//exclude spe level
        refData.put("pricingStrategyCmd" , cmd);

//        refData.put("defaultvalidate" , new MfDate().excursionDays(1).toString());
        cmd.setValiddate( new MfDate().excursionDays(1).toString());
        return refData ;
    }
 
    
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws Exception {
			return new ModelAndView(indexView).addAllObjects(refData(request));
	}
    

  
    public ModelAndView submit(final HttpServletRequest request,
            final HttpServletResponse response, final PricingStrategyCommand cmd
           ) throws Exception {
    	try{
	        PricingStrategyCommand pricingStrategyCmd = (PricingStrategyCommand) cmd;
	        
	        PricingStrategyDTO dto = new PricingStrategyDTO();
	        dto.setPaymentServiceCode(pricingStrategyCmd.getPaymentservicecode());
	        
	        dto.setPriceStrategyCode(pricingStrategyCmd.getPricestrategycode());
	        dto.setPriceStrategyName(pricingStrategyCmd.getPricestrategyname());
	        dto.setPriceStrategyType(Integer.parseInt(pricingStrategyCmd.getPricestrategytype()));
	        dto.setCaculateMethod(pricingStrategyCmd.getCaculatemethod());
	        
	        dto.setEffectiveOn(pricingStrategyCmd.getEffectiveon());
	        dto.setMemberCode(pricingStrategyCmd.getMembercode());
	        dto.setServiceLevelCode(pricingStrategyCmd.getServicelevelcode());
	        
	//        dto.setStatus(new Integer(request.getParameter("status")));
	        dto.setStatus(new Integer(0));
	        
	        if (!StringUtil.isEmpty(request.getParameter("validdate"))) {
	                dto.setValidDate(new Timestamp(Date.valueOf(
	                    request.getParameter("validdate")).getTime()));
	        }
	        
	        if (StringUtil.isNull(pricingStrategyCmd.getInvaliddate())) {
	            dto.setInvalidDate(new Timestamp((new MfDate(1900, 1, 1).getTime()).getTime()));
	        } else {
	            dto.setInvalidDate(new Timestamp((new MfDate(
	                pricingStrategyCmd.getInvaliddate()).getTime()).getTime()));
	        }
	        this.pricingStrategyService.addPricingStrategy(dto);
	        request.getSession().setAttribute("paymentservicecode",pricingStrategyCmd.getPaymentservicecode());
	        String  url = response.encodeRedirectURL(this.getSuccessView());
	        return new ModelAndView(url);
        }catch(Exception e){
        	e.printStackTrace();
        	Map  refData = refData(request);
        	refData.put("error",  e.getMessage());
        	return new ModelAndView(indexView, refData);
        }
    }


	public void setPricingStrategyService(
			PricingStrategyService pricingStrategyService) {
		this.pricingStrategyService = pricingStrategyService;
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

	public PricingStrategyService getPricingStrategyService() {
		return pricingStrategyService;
	}

	public PaymentServiceService getPaymentServiceService() {
		return paymentServiceService;
	}

   

}
