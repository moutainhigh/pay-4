package com.pay.fundout.withdraw.dao.autorisk;

import java.util.Date;

import com.pay.fundout.withdraw.dto.autorisk.LoginFailLogDto;
import com.pay.inf.dao.BaseDAO;

public interface LoginFailLogDAO extends BaseDAO<LoginFailLogDto>{
	
	/**
	 * 创建日志
	 * @param dto 日志实体
	 * @return
	 */
	public Long createLoginFailLog(LoginFailLogDto dto);
	
	/**
	 * 根据会员号、起止时间查询登录失败次数
	 * @param memberCode 会员号
	 * @param beginDate 起始时间
	 * @param endDate 结束时间
	 * @return 登录失败次数
	 */
	public int queryLoginFailNum(Long memberCode, Date beginDate, Date endDate);
	
}
