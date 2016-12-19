/**
 * 
 */
package com.pay.gateway.service.impl;

import com.pay.gateway.dao.GatewayResponseDAO;
import com.pay.gateway.model.GatewayResponse;
import com.pay.gateway.service.GatewayResponseService;

/**
 * @author chaoyue
 *
 */
public class GatewayResponseServiceImpl implements GatewayResponseService {

	private GatewayResponseDAO gatewayResponseDAO;

	public void setGatewayResponseDAO(GatewayResponseDAO gatewayResponseDAO) {
		this.gatewayResponseDAO = gatewayResponseDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.gateway.service.GatewayRequestService#saveGatewayRequest(com.
	 * pay.gateway.model.GatewayRequest)
	 */
	@Override
	public Long saveGatewayResponse(GatewayResponse request) {
		return (Long) gatewayResponseDAO.create(request);
	}

}
