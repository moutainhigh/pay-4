/**
 * 
 */
package com.pay.gateway.service.impl;

import com.pay.gateway.dao.GatewayRequestDAO;
import com.pay.gateway.model.GatewayRequest;
import com.pay.gateway.service.GatewayRequestService;

/**
 * @author chaoyue
 *
 */
public class GatewayRequestServiceImpl implements GatewayRequestService {

	private GatewayRequestDAO gatewayRequestDAO;

	public void setGatewayRequestDAO(GatewayRequestDAO gatewayRequestDAO) {
		this.gatewayRequestDAO = gatewayRequestDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.gateway.service.GatewayRequestService#saveGatewayRequest(com.
	 * pay.gateway.model.GatewayRequest)
	 */
	@Override
	public Long saveGatewayRequest(GatewayRequest request) {
		return (Long) gatewayRequestDAO.create(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.gateway.service.GatewayRequestService#findGatewayRequest(java
	 * .lang.String)
	 */
	@Override
	public GatewayRequest findGatewayRequest(Long memberCode, String orderId) {

		return gatewayRequestDAO.queryGatewayRequest(memberCode, orderId);
	}

}
