/**
 * 
 */
package com.pay.app.service.bsp;

import com.pay.app.dto.UserPhoneDto;

/**
 * @author 戴德荣
 * date  2011-6-29 上午11:12:03
 */
public interface UpdateUserPhoneService {
		
	/**
	 * 根据memberCode取得用户
	 * @param memberCode memberCode
	 * @return UserPhoneDto
	 */
	public UserPhoneDto getUserPhoneByMemberCode(Long memberCode);
	
	/**
	 * 更新用户的联系电话
	 * @param userPhoneDto
	 * @return true
	 */
	public boolean updateUserPhone(UserPhoneDto userPhoneDto);
	
	/**
	 * 判断是否有权限修改
	 * @param sessionMebmerCode
	 * @param paramMemberCode
	 * @return true/false;
	 */
	boolean hasPromisson(Long sessionMebmerCode,Long paramMemberCode);
}
