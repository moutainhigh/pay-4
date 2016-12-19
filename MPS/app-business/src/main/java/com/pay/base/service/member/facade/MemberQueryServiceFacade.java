package com.pay.base.service.member.facade;

public interface MemberQueryServiceFacade {
	
	/**
	 * 查询上一次登录时间
	 * @param memberCode	会员号
	 * @return
	 */
	public String queryLastLoginTime(Long memberCode);
}
