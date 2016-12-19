
package com.pay.pe.controller;

import java.sql.Date;
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

import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.pricingstrategy.dto.PricingStrategyDTO;
import com.pay.pricingstrategy.helper.EFFECTIVEON;
import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.util.MfDate;
import com.pay.util.StringUtil;



public final class ChangePricingStrategyController extends
MultiActionController {
 
	private Log logger = LogFactory.getLog(getClass());
 
    private PricingStrategyService pricingStrategyService;
 
    private PaymentServiceService paymentServiceService;
    
    private String indexView ;
    private String successView ;
    
 
    
    
    
    
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws Exception {
//		formBackingObject(request);
		
		String priceStrategyCode = request.getParameter("paymentservicecode");
        PricingStrategyCommand pricingStrategyCmd = new PricingStrategyCommand();
        PricingStrategyDTO pricingStetagyDto = this.pricingStrategyService.getPricingStrategy(new Long(priceStrategyCode));
        Assert.notNull(pricingStetagyDto, "pricingStetagyDto must be not null");
        pricingStrategyCmd.getEffectiveonMap().remove(EFFECTIVEON.SPECIFIDESERVERLEVEL.getValue());//exclude spe level
        pricingStrategyCmd.setPaymentservicecode(pricingStetagyDto.getPaymentServiceCode());
        
        pricingStrategyCmd.setPricestrategycode(pricingStetagyDto.getPriceStrategyCode());
        pricingStrategyCmd.setPricestrategyname(pricingStetagyDto.getPriceStrategyName());
        pricingStrategyCmd.setCaculatemethod(pricingStetagyDto.getCaculateMethod());
        pricingStrategyCmd.setPricestrategytype(""+PRICESTRATEGYTYPE.getPRICESTRATEGYTYPEMAPKey(pricingStetagyDto.getPriceStrategyType()).getValue());
        
        pricingStrategyCmd.setValiddate(new MfDate(pricingStetagyDto.getValidDate()).toString());
        pricingStrategyCmd.setInvaliddate(new MfDate(pricingStetagyDto.getInvalidDate())        
            .equals(new MfDate(1900, 1, 1)) ? null : new MfDate(pricingStetagyDto.getInvalidDate()).toString());
        
        pricingStrategyCmd.setEffectiveon(EFFECTIVEON.getEFFECTIVEONMAPKey(pricingStetagyDto.getEffectiveOn()).getValue());
        pricingStrategyCmd.setServicelevelcode(pricingStetagyDto.getServiceLevelCode());        
        pricingStrategyCmd.setMembercode(pricingStetagyDto.getMemberCode());
        
        pricingStrategyCmd.setStatus(pricingStetagyDto.getStatus());
//        pricingStrategyCmd.setPricestrategyseq(pricingStetagyDto.getPriceStrategySeq().toString());
		return new ModelAndView(indexView).addObject("pricingStrategyCmd", pricingStrategyCmd);
 }

   
    
    public ModelAndView submit(final HttpServletRequest request,
            final HttpServletResponse response, final PricingStrategyCommand cmd
           ) throws Exception {
    	try{
        PricingStrategyCommand pricingStrategyCmd =(PricingStrategyCommand) cmd;
        PricingStrategyDTO dto = new PricingStrategyDTO();
        
        dto.setPaymentServiceCode(pricingStrategyCmd.getPaymentservicecode());
        dto.setPriceStrategyCode(pricingStrategyCmd.getPricestrategycode());
        dto.setPriceStrategyName(pricingStrategyCmd.getPricestrategyname());
        
        dto.setPriceStrategyType(Integer.parseInt(pricingStrategyCmd.getPricestrategytype()));
        dto.setCaculateMethod(pricingStrategyCmd.getCaculatemethod());
        
        dto.setEffectiveOn(pricingStrategyCmd.getEffectiveon());
        dto.setMemberCode(pricingStrategyCmd.getMembercode());
        dto.setServiceLevelCode(pricingStrategyCmd.getServicelevelcode());
        
        dto.setValidDate(new Timestamp(Date.valueOf(
            pricingStrategyCmd.getValiddate()).getTime()));
        
        if (StringUtil.isNull(pricingStrategyCmd.getInvaliddate())) {
            dto.setInvalidDate(new Timestamp((new MfDate(1900, 1, 1)
                .getTime()).getTime()));
        } else {
            dto.setInvalidDate(new Timestamp((new MfDate(
                pricingStrategyCmd.getInvaliddate()).getTime()).getTime()));
        }
        
        dto.setStatus(pricingStrategyCmd.getStatus());
        
//      dto.setPriceStrategySeq(new Integer(pricingStrategyCmd.getPricestrategyseq()));

        this.pricingStrategyService.changePricingStrategy(dto);
        
        request.getSession().setAttribute("paymentservicecode",dto.getPaymentServiceCode());
        
        return new ModelAndView(this.getSuccessView());
    	 }catch(Exception e){
          	e.printStackTrace();
          	Map  refData = new HashMap();
          	refData.put("error",  e.getMessage());
          	return new ModelAndView(indexView, refData).addObject("pricingStrategyCmd", cmd);
    	  }
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




}
