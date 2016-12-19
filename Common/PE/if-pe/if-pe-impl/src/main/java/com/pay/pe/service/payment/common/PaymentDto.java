package com.pay.pe.service.payment.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pay.pe.dto.AccountEntryDTO;

/**
 * 
 */
public final class PaymentDto implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1245687677536344417L;
	
    /**
     * 帐目分录.
     */
    private List < AccountEntryDTO > resultEntries = new ArrayList <AccountEntryDTO>();

    /**
     * 支付事件.
     */
    private List < AccountingEvent > resultEvents = new ArrayList <AccountingEvent>();

    /**
     * 支付请求参数.
     */
    private PaymentRequest paymentRequest;
    
    /**
     * 支付返回对象.
     */
    private PaymentResponse paymentResponse;

    /**
     * Default constructor.
     *
     */
    public PaymentDto() {
    }
    
    /**
     * 清除变量.
     *
     */
    public void clean() {
    	paymentRequest = null;
    	paymentResponse = null;
    	resultEntries = new ArrayList <AccountEntryDTO>();
    	resultEvents = new ArrayList <AccountingEvent>();
    }
    
	/**
	 * @return Returns the paymentRequest.
	 */
	public PaymentRequest getPaymentRequest() {
		return paymentRequest;
	}

	/**
	 * @param paymentRequest The paymentRequest to set.
	 */
	public void setPaymentRequest(final PaymentRequest paymentRequest) {
		this.paymentRequest = paymentRequest;
	}

	/**
	 * @return Returns the paymentResponse.
	 */
	public PaymentResponse getPaymentResponse() {
		return paymentResponse;
	}

	/**
	 * @param paymentResponse The paymentResponse to set.
	 */
	public void setPaymentResponse(PaymentResponse paymentResponse) {
		this.paymentResponse = paymentResponse;
	}

	/**
	 * @return Returns the resultEntries.
	 */
	public List<AccountEntryDTO> getResultEntries() {
		return resultEntries;
	}

	/**
	 * @param resultEntries The resultEntries to set.
	 */
	public void setResultEntries(final List<AccountEntryDTO> resultEntries) {
		this.resultEntries = resultEntries;
	}
	
	public void addResultEntry(final AccountEntryDTO entry) {
		this.resultEntries.add(entry);
	}

	/**
	 * @return Returns the resultEvents.
	 */
	public List<AccountingEvent> getResultEvents() {
		return resultEvents;
	}

	/**
	 * @param resultEvents The resultEvents to set.
	 */
	public void setResultEvents(final List<AccountingEvent> resultEvents) {
		this.resultEvents = resultEvents;
	}
	
	public void addResultEvent(final AccountingEvent event) {
		this.resultEvents.add(event);
	}
}
