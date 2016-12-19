/**
 * 
 */
package com.pay.wechat.dao.sysusermapper.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.wechat.dao.sysusermapper.SysUserMapperDao;

/**
 * @author PengJiangbo
 *
 */
public class SysUserMapperDaoImpl extends BaseDAOImpl implements
		SysUserMapperDao {

	
	@Override
	public int updateSysUserMapLoginDpwd(String dpwd) {
		return this.getSqlMapClientTemplate().update(getNamespace().concat("updateSysUserMapLoginDpwd"), dpwd) ;
	}

	@Override
	public int deleteSysUserMapperByMemberCode(String openID) {

		return this.getSqlMapClientTemplate().delete(getNamespace().concat("deleteSysUserMapperByMemberCode"), openID);
	}

}
