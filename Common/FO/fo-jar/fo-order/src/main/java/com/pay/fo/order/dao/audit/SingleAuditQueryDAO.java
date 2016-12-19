/**
 * 
 */
package com.pay.fo.order.dao.audit;

import com.pay.fo.order.model.audit.SingleAuditQueryInfo;
import com.pay.inf.dao.Page;

/**
 * @author NEW
 *
 */
public interface SingleAuditQueryDAO {
	
	/**
	 * 查询待审核列表
	 * @param page
	 * @param auditQueryOrder
	 */
	public Page<SingleAuditQueryInfo> queryAuditList(Page<SingleAuditQueryInfo> page ,SingleAuditQueryInfo info);

}
