/**
 * 
 */
package com.pay.wechat.dao.sysusermapper;

/**
 * 系统商户｜用户绑定
 * @author PengJiangbo
 *
 */
public interface SysUserMapperDao {

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
