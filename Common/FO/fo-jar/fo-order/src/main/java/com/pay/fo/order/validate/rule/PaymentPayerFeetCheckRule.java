/**
 *
 */
package com.pay.fo.order.validate.rule;

import org.apache.commons.lang.StringUtils;

import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.fo.order.validate.BatchPaymentRequest;
import com.pay.inf.rule.MessageRule;

/**
 * 检测是否配置了商户批量付款手续费
 * @author PengJiangbo
 *
 */
public class PaymentPayerFeetCheckRule extends MessageRule {
	
	private EnterpriseBaseService enterpriseBaseService ;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(final Object validateBean) throws Exception {

		BatchPaymentRequest detailRequest = (BatchPaymentRequest) validateBean;
		
		Long memberCode = detailRequest.getMemberCode() ;
		WithdrawUnionBatchpayFee unionFee = this.enterpriseBaseService.queryWithdrawUnionBatchByCode(memberCode) ;
		if(null != unionFee){
			Long batchpayFee = unionFee.getBatchpayFee() ;
			String batchpayFeeCurrency = unionFee.getBatchpayFeeCurrency() ;
			if(StringUtils.isNotEmpty(batchpayFeeCurrency) && (null != batchpayFee && 0 != batchpayFee)){
				return true ;
			}else{
				detailRequest.getPaymentResponse().setErrorMsg(getMessageId());
				return false ;
			}
		}else{
			detailRequest.getBatchPaymentResponse().setErrorMsg(getMessageId());
			return false ;
		}
		
	}

	public void setEnterpriseBaseService(final EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	
}
