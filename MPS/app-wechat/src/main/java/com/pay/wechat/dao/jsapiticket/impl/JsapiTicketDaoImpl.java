/**
 * 
 */
package com.pay.wechat.dao.jsapiticket.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.wechat.dao.jsapiticket.JsapiTicketDao;
import com.pay.wechat.model.JsapiTicket;

/**
 * @author PengJiangbo
 *
 */
public class JsapiTicketDaoImpl extends BaseDAOImpl implements JsapiTicketDao {

	@Override
	public JsapiTicket findJsapiTicketOnlyOne() {
		return (JsapiTicket) this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("findJsapiTicketOnlyOne"));
	}

}
