/**
 * ABCB2CMap2StringTransform.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.abc.conn.requestbuilder;

import java.util.Map;

import com.hnapay.fi.order.repair.conn.base.StringRequestBuilder.Map2StringTransform;

/**
 * latest modified time : 2011-8-26 上午11:04:20
 * @author bigknife
 */
public class ABCB2CMap2StringTransform implements Map2StringTransform {

	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.conn.base.StringRequestBuilder.Map2StringTransform#map2String(java.util.Map)
	 */
	@Override
	public String map2String(Map<String, String> data) {
		if(data == null || !data.containsKey("MSG")){
			throw new RuntimeException("农行报文数据必须包含MSG");
		}
		
		String msg = data.get("MSG");
		return msg;
	}

}
