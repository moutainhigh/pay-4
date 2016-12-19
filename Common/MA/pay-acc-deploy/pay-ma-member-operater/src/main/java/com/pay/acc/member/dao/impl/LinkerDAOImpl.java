/**
 * 
 */
package com.pay.acc.member.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.member.dao.LinkerDAO;
import com.pay.acc.member.model.Linker;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Administrator
 *
 */
public class LinkerDAOImpl extends BaseDAOImpl<Linker> implements LinkerDAO {

	/* (non-Javadoc)
	 * @see com.pay.acc.member.dao.LinkerDAO#queryMyLinkerWithMemberCode(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Linker queryMyLinkerWithMemberCode(Long orgMemberCode, Long linkMemberCode) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("orgMemberCode", String.valueOf(orgMemberCode));
		map.put("linkMemberCode", linkMemberCode);	
		return (Linker) this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("queryMyLinkerWithMemberCode"), map);
	}

}
