package com.pay.ma.unlock.service.impl;

import com.pay.ma.unlock.dao.UnLockDAO;
import com.pay.ma.unlock.service.UnlockService;

public class UnlockServiceImpl implements UnlockService {

	private UnLockDAO unLockDAO;

	/* (non-Javadoc)
	 * @see com.pay.ma.unlock.service.UnlockService#unlockMemberWithDateTime(java.util.Date, java.lang.Integer)
	 */
	@Override
	public boolean unlockMemberWithStatus(Integer status) {
		this.unLockDAO.updateMemberStatusWithDateTime(status);
		return true;
	}

	/**
	 * @param unLockDAO
	 *            the unLockDAO to set
	 */
	public void setUnLockDAO(UnLockDAO unLockDAO) {
		this.unLockDAO = unLockDAO;
	}

}
