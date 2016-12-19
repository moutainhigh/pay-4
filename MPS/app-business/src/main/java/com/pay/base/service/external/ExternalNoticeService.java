/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.service.external;


/**
 * 外部交易通知服务
 * @author fjl
 * @date 2011-9-20
 */
public interface ExternalNoticeService {
	
	/**
	 * 外部交易完成后，发送通知
	 * @param externalId
	 * @return
	 */
	public boolean sendNotice(Long externalId);
	
}
