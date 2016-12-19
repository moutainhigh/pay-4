package com.pay.rm.blacklistcheck.dao;


import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.blacklistcheck.dto.BlackChecklistDTO;




public interface BlackChecklistDAO {
	public Long addBlackChecklist(BlackChecklistDTO dto);

	public Page<BlackChecklistDTO> queryBlacklistCheck(
			Page<BlackChecklistDTO> page, Map map);

	public boolean updateBlacklistCheckStatus(BlackChecklistDTO blackChecklist);


}
