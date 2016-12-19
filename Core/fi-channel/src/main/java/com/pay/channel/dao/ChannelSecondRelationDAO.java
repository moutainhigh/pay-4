/**
 * 
 */
package com.pay.channel.dao;

import java.util.List;

import com.pay.channel.model.ChannelSecondRelation;
import com.pay.inf.dao.BaseDAO;

/**
 * @author chaoyue
 *
 */
public interface ChannelSecondRelationDAO extends BaseDAO {

	/**
	 * 
	 * @param memberCode
	 * @param orgCode
	 * @return
	 */
	List<ChannelSecondRelation> findRelation(String memberCode, String orgCode,
			String transType, String currencyCode, String mcc,Long paymentChannelItemId);

	/**
	 * 
	 * @param memberCode
	 * @param orgCode
	 * @param transType
	 * @return
	 */
	ChannelSecondRelation findRelation(String memberCode, String orgCode,
			String transType);

	/**
	 * 查询二级商户号配置
	 * 
	 * @param memberCode
	 * @param orgCode
	 * @param transType
	 * @param currencyCode
	 * @return
	 */
	ChannelSecondRelation findRelation(String memberCode, String orgCode,
			String transType, String currencyCode);

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	List<ChannelSecondRelation> findByMemberCode(String memberCode);

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	List<ChannelSecondRelation> findByMemberCode(String memberCode,
			String orgCode);

	/**
	 * 
	 * @param memberCode
	 * @param orgCode
	 * @param currencyCode
	 * @return
	 */
	ChannelSecondRelation findByOrgCode(String memberCode, String orgCode,
			String currencyCode);
	
	List<ChannelSecondRelation> findByChannelSecondRelation(ChannelSecondRelation csr);
}
