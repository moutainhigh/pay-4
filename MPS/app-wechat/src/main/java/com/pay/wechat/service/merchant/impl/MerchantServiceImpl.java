/**
 * 
 */
package com.pay.wechat.service.merchant.impl;

import java.util.Map;

import com.pay.wechat.dao.merchant.MerchantDao;
import com.pay.wechat.model.MerchantCooperation;
import com.pay.wechat.model.SysUserMapper;
import com.pay.wechat.service.merchant.MerchantService;

/**
 * @author PengJiangbo
 *
 */
public class MerchantServiceImpl implements MerchantService {
	
	//注入merchantDao
	private MerchantDao merchantDao ;
	
	//-------------------setter------------------------
	public void setMerchantDao(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}
	
	public void addMerchantCooperationInfo(
			MerchantCooperation merchantCooperation) {
		this.merchantDao.addMerchantCooperationInfo(merchantCooperation);
	}
	/**
	 * 存储系统商户绑定信息
	 */
	public long addSysUserMapperInfo(SysUserMapper sysUserMapper) {
		return this.merchantDao.addSysUserMapperInfo(sysUserMapper);
	}
	
	@Override
	public SysUserMapper findSysUserMapperByOpenID(String openId) {
		SysUserMapper sysUserMapper = this.merchantDao.findSysUserMapperByOpenID(openId) ;
		return sysUserMapper;
	}
	@Override
	public SysUserMapper findSysUserMapByMemCodeAndOpenID(Map<String, Object> map) {
		SysUserMapper sysUserMapper = this.merchantDao.findSysUserMapByMemCodeAndOpenID(map) ;
		return sysUserMapper;
	}
	
	
	
}
