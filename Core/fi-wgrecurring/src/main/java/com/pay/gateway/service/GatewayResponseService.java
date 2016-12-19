/**
 * 
 */
package com.pay.gateway.service;

import com.pay.gateway.model.GatewayResponse;

/**
 * @author chaoyue
 *
 */
public interface GatewayResponseService {

	/**
	 * 保存网关请求
	 * 
	 * @param request
	 * @return
	 */
	Long saveGatewayResponse(GatewayResponse response);
}
