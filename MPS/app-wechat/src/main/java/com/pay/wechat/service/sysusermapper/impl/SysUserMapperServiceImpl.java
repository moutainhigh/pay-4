/**
 * 
 */
package com.pay.wechat.service.sysusermapper.impl;

import com.pay.wechat.dao.sysusermapper.SysUserMapperDao;
import com.pay.wechat.service.sysusermapper.SysUserMapperService;

/**
 * @author PengJiangbo
 *
 */
public class SysUserMapperServiceImpl implements SysUserMapperService {

	//注入
	private SysUserMapperDao sysUserMapperDao ;
	
	public void setSysUserMapperDao(SysUserMapperDao sysUserMapperDao) {
		this.sysUserMapperDao = sysUserMapperDao;
	}

	@Override
	public int updateSysUserMapLoginDpwd(String dpwd) {
		return this.sysUserMapperDao.updateSysUserMapLoginDpwd(dpwd);
	}

	@Override
	public int deleteSysUserMapperByMemberCode(String openID) {
		return this.sysUserMapperDao.deleteSysUserMapperByMemberCode(openID);
	}

}
