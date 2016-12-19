package com.pay.fo.order.service.pay2bank;

import java.util.List;

import com.pay.fo.order.dto.RecentPayeeDTO;
import com.pay.inf.dao.BaseDAO;

public class Pay2bankUtils {

	protected BaseDAO iBaseDao;// 数据库操作

	public List<RecentPayeeDTO> queryRecentPayee(String payerMembercode) {
		return iBaseDao.findByQuery(
				"FO_FUNDOUT_PayToBankUtils.queryRecentPayee", payerMembercode);
	}

	public void setiBaseDao(BaseDAO<Object> iBaseDao) {
		this.iBaseDao = iBaseDao;
	}

}
