package com.pay.poss.personmanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.personmanager.dto.UserLogDto;

public interface UserLogDAO  extends BaseDAO<UserLogDto>{
	
	/**查询日志列表
	 * @param userLogDto
	 * @return
	 */
	public List<UserLogDto> selectUserLogList(UserLogDto userLogDto);

}
