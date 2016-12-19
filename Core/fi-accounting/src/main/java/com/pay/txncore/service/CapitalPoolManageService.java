package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.CapitalPoolManage;
import com.pay.txncore.model.PositionAllocaAudit;

public interface CapitalPoolManageService {

	List<CapitalPoolManage> queryCapitalPool(Map<String, Object> paraMap);

	void update(Map<String, Object> paraMap);

	Integer count();

	void createAudit(Map<String, Object> paraMap);

	List<PositionAllocaAudit> queryPositionAllocaAudit(
			PositionAllocaAudit positionAllocaAudit, Page page);

	void updateStatus(PositionAllocaAudit positionAllocaAudit);
	List<CapitalPoolManage> queryByStatus(String status);
}
