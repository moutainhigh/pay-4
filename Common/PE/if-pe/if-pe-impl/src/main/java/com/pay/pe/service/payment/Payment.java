package com.pay.pe.service.payment;

import com.pay.pe.dao.BankOrgCodeMappingDAO;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.exception.PEBisnessRuntimeException;
import com.pay.pe.service.payment.common.AccountingEvent;
import com.pay.pe.service.payment.common.PaymentRequest;
import com.pay.pe.service.payment.common.PaymentResponse;
import com.pay.pe.service.paymentservice.PaymentServiceService;

public interface Payment {
	/**
	 * 增加一个支付处理事件.
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 */
	void addAccountingEvent(AccountingEvent accountingEvent);

	/**
	 * 增加一个帐目分录.
	 * 
	 * @param entry
	 *            <code>AccountEntry</code> object.
	 */
	void addAccountingEntry(AccountEntryDTO entry);

	/**
	 * 得到支付请求对象.
	 * 
	 * @return PaymentRequest
	 */
	PaymentRequest getPaymentRequest();

	/**
	 * 得到支付返回对象.
	 * 
	 * @return PaymentResponse
	 */
	PaymentResponse getPaymentResponse();

	/**
	 * 设置支付服务的服务层对象.
	 * 
	 * @return <code>PaymentServiceService</code> object.
	 */
	PaymentServiceService getPaymentService();

	/**
	 * 设置支付服务的服务对象.
	 * 
	 * @param service
	 *            <code>PaymentServiceService</code> object.
	 */
	void setPaymentService(PaymentServiceService service);

	/**
	 * 处理支付.
	 * 
	 * @param paymentRequest
	 *            the <code>PaymentRequest</code> object. <br>
	 *            paymentRequest中包含支付方，收款方，金额，支付所采用的服务类型.
	 */
	// PaymentResponse process(PaymentRequest paymentRequest) throws
	// PaymentException;
	// public List<AccountEntryDTO> generateEntries(final DealDto deal, final
	// PaymentRequest request) throws PEBisnessRuntimeException;

	public PaymentResponse generateEntries(final PaymentRequest request)
			throws PEBisnessRuntimeException;

	public BankOrgCodeMappingDAO getBankOrgCodeMappingDAO();
}
