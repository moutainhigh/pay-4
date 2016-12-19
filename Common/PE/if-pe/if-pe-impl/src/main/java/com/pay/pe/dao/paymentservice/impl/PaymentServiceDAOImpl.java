
package com.pay.pe.dao.paymentservice.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.paymentservice.PaymentServiceDAO;
import com.pay.pe.model.PaymentService;
import com.pay.pe.model.PaymentServicePKG;
import com.pay.util.StringUtil;


public final class PaymentServiceDAOImpl extends IbatisDAOSupport implements
        PaymentServiceDAO {

  
    public PaymentService addPaymentService(
            final PaymentService paymentService) {

        return (PaymentService) super.saveObject(paymentService);
    }

   
    public PaymentService changePaymentService(
            final PaymentService paymentService) {
    	 super.updateObject(paymentService);
    	 return paymentService ;
    }


    public void removePaymentService(final Integer paymentServiceCode) {
        super.deleteObjectById( paymentServiceCode);
    }

  
    public List getAllPaymentService() {
    	return super.findBySelective(null);
    }

  
    public PaymentService changePaymentService() {
        // TODO Auto-generated method stub
        return null;
    }

  
    public PaymentService getPaymentService(
            final Integer paymentServiceCode) {
        return (PaymentService) super.findObjectById(paymentServiceCode);
    }

   
    public List < PaymentService > getAllPaymentServiceByName(
            final String paymentServiceName) {
    	PaymentService paymentService = new PaymentService();
    	paymentService.setPaymentServiceName(paymentServiceName);
    	return super.findBySelective(paymentService);
    }


    public List < PaymentServicePKG > getAllAssignedToPaymentServicePKG(
            final Integer paymentServiceCode) {
        return null;
    }


    public List < PaymentService > getAllPaymentServiceByDTO(
            final Map map) {
    	PaymentService paymentService = new PaymentService();
    	if (!StringUtil.isEmpty((String) map
                .get("paymentservicename"))) {
        	paymentService.setPaymentServiceName((String)map.get("paymentservicename"));
        }
        if (!StringUtil.isNull(map
                .get("paymentservicecode"))) {
        	paymentService.setPaymentServiceCode((Integer)map.get("paymentservicecode"));
            
        }
        if (!StringUtil.isNull(map
                .get("paymentServiceType"))) {
        	paymentService.setPaymentServiceType((Integer)map.get("paymentServiceType"));
            
        }
        return super.findBySelective(paymentService);
    }

	/**
	 * 查询在某一支付组paymentServicePkgCode下，某个支付服务类型的所有支付服务。
	 * 
	 * @param paymentServicePkgCode
	 *            支付组
	 * @param paymentServiceType
	 *            支付服务类型
	 * @return 所以支付服务
	 */
	public List<PaymentService> getPaymentServices(
			final Integer paymentServicePkgCode,
			final Integer paymentServiceType) {
		
		
	 	PaymentService paymentService = new PaymentService();
    	return super.findBySelective(paymentService);
		
	}

}
