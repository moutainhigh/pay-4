/**
 * 
 */
package com.pay.wechat.dao.jsapiticket;

import com.pay.wechat.model.JsapiTicket;

/**
 * 微信js-sdk接口获取jsapi_ticket DAO
 * @author PengJiangbo
 *
 */
public interface JsapiTicketDao {
	//
	public JsapiTicket findJsapiTicketOnlyOne() ;
	
}
