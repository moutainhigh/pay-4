/**
 * 
 */
package com.pay.wechat.service.sysusermapper;

/**
 * @author PengJiangbo
 *
 */
public interface SysUserMapperService {
	
	/**
	 * 重置手势密码
	 */
	public int updateSysUserMapLoginDpwd(String dpwd) ;
	
	/**
	 * 根据memberCode删除绑定用户
	 * @param memeberCode
	 * @return
	 */
	public int deleteSysUserMapperByMemberCode(String openID) ;
}
