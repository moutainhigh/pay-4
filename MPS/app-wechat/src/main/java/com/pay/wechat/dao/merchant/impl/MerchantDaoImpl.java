/**
 * 
 */
package com.pay.wechat.dao.merchant.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.wechat.dao.merchant.MerchantDao;
import com.pay.wechat.model.MerchantCooperation;
import com.pay.wechat.model.SysUserMapper;

/**
 * @author PengJiangbo
 *
 */
public class MerchantDaoImpl extends BaseDAOImpl implements MerchantDao {

	/**
	 * 添加游客填写的了解合作信息
	 */
	public void addMerchantCooperationInfo(
			MerchantCooperation merchantCooperation) {
		getSqlMapClientTemplate().insert(getNamespace().concat("addMerchantCooperationInfo"), merchantCooperation) ;
		
	}
	
	/**
	 * 存储系统商户与公众号绑定信息
	 */
	public long addSysUserMapperInfo(SysUserMapper sysUserMapper) {
		return (Long) getSqlMapClientTemplate().insert(getNamespace().concat("addSysUserMapperInfo"), sysUserMapper) ;
	}

	/**
	 * @param opengId
	 * @return
	 */
	@Override
	public SysUserMapper findSysUserMapperByOpenID(String openId) {
		SysUserMapper sysUserMapper = (SysUserMapper) getSqlMapClientTemplate().queryForObject(getNamespace().concat("findSysUserMapperByOpenID"), openId) ;
		return sysUserMapper;
	}
	
	@Override
	public SysUserMapper findSysUserMapByMemCodeAndOpenID(Map<String, Object> map) {
		SysUserMapper sysUserMapper = (SysUserMapper) this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("findSysUserMapByMemCodeAndOpenID"), map);
		return sysUserMapper;
	}


}
