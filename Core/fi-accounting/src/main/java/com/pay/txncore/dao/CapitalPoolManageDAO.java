package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.CapitalPoolManage;
import com.pay.txncore.model.PositionAllocaAudit;

public interface CapitalPoolManageDAO {

	List<CapitalPoolManage> queryCapitalPool(Map<String, Object> paraMap);

	void update(Map<String, Object> paraMap);

	Integer count();

	Long createAudit(Map<String, Object> paraMap);

	List<PositionAllocaAudit> queryPositionAllocaAudit(
			PositionAllocaAudit positionAllocaAudit, Page page);

	boolean updateStatus(PositionAllocaAudit positionAllocaAudit);

	PositionAllocaAudit findPositionById(PositionAllocaAudit positionAllocaAudit);
	List<CapitalPoolManage> queryByStatus(String status);
}
