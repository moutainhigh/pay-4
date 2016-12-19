package com.pay.acc.service.member;

import com.pay.acc.service.member.dto.LaResultDto;

/** 
* @ClassName: LaMemberQueryService 
* @Description: 利安余额查询接口
* @author cf
* @date 2011-9-26 上午09:25:35 
*  
*/

public interface LaMemberQueryService {
	
	/**查询名单已关联的余额，
	 * @param memberCode
	 * @param login_Name
	 * @return
	 */
	public LaResultDto doQueryMemberByRelation(String memberCode,String login_Name);

}
