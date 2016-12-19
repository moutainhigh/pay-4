/**
 * 
 */
package com.pay.wechat.dao.merchant;

import java.util.Map;

import com.pay.wechat.model.MerchantCooperation;
import com.pay.wechat.model.SysUserMapper;

/**
 * 商户DAO
 * @author PengJiangbo
 *
 */
public interface MerchantDao {
	
	/**
	 * 添加商户的了解合作信息
	 * @param merchantCooperation
	 */
	public void addMerchantCooperationInfo(MerchantCooperation merchantCooperation) ;
	
	/**
	 * 存储系统商户与公众号绑定信息
	 * @param sysUserMapper
	 */
	public long addSysUserMapperInfo(SysUserMapper sysUserMapper) ;
	
	/**
	 * 查询单个的系统用户（商户｜员工）对象
	 * @param opengId 微信公用户唯一标识
	 * @return
	 */
	public SysUserMapper findSysUserMapperByOpenID(String openId) ;
	
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public SysUserMapper findSysUserMapByMemCodeAndOpenID(Map<String, Object> map) ;
	
}
