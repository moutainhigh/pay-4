/**
 * 
 */
package com.pay.wechat.service.jsapiticket.impl;

import com.pay.wechat.dao.jsapiticket.JsapiTicketDao;
import com.pay.wechat.model.JsapiTicket;
import com.pay.wechat.service.jsapiticket.JsapiTicketService;

/**
 * @author PengJiangbo
 *
 */
public class JsapiTicketServiceImpl implements JsapiTicketService {

	//注入
	private JsapiTicketDao jsapiTicketDao ;
	
	public void setJsapiTicketDao(JsapiTicketDao jsapiTicketDao) {
		this.jsapiTicketDao = jsapiTicketDao;
	}


	@Override
	public JsapiTicket findJsapiTicketOnlyOne() {
		return this.jsapiTicketDao.findJsapiTicketOnlyOne() ;
	}

}
