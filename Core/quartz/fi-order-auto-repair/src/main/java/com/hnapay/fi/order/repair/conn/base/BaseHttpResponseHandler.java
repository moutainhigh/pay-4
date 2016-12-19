/**
 * BaseHttpResponseHandler.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hnapay.fi.dto.batchrepair.api.BankOrderDTO;
import com.hnapay.fi.order.repair.conn.IHttpResponseHandler;
import com.hnapay.fi.order.repair.engine.exception.ConnectException;

/**
 * latest modified time : 2011-8-23 下午12:12:06
 * 
 * @author bigknife
 */
public class BaseHttpResponseHandler implements IHttpResponseHandler {
	protected Log log = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.fi.order.repair.conn.IHttpResponseHandler#isSuccessful(java
	 * .lang.String)
	 */
	@Override
	public boolean isSuccessful(String response) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.fi.order.repair.conn.IHttpResponseHandler#handleConnectException
	 * (java.lang.Exception)
	 */
	@Override
	public void handleConnectException(Exception e) throws ConnectException {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.fi.order.repair.conn.IHttpResponseHandler#handleOkResponse
	 * (java.lang.String)
	 */
	@Override
	public List<BankOrderDTO> handleOkResponse(String response) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.fi.order.repair.conn.IHttpResponseHandler#handleErrorResponse
	 * (java.lang.String)
	 */
	@Override
	public void handleErrorResponse(String response) {
		throw new UnsupportedOperationException();
	}
}
