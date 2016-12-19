package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.CapitalPoolManageDAO;
import com.pay.txncore.model.CapitalPoolManage;
import com.pay.txncore.model.PositionAllocaAudit;

public class CapitalPoolManageDAOImpl extends BaseDAOImpl implements CapitalPoolManageDAO{

	@Override
	public List<CapitalPoolManage> queryCapitalPool(Map<String, Object> paraMap) {
		return super.findByCriteria(paraMap);
	}

	@Override
	public void update(Map<String, Object> paraMap) {
		super.updateByMap("update", paraMap);
	}

	@Override
	public Integer count() {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(this.namespace.concat("count"));
	}

	@Override
	public Long createAudit(Map<String, Object> paraMap) {
		Long allotSequence = (Long)this.getSqlMapClientTemplate().insert(this.namespace.concat("create"), paraMap);
		return allotSequence;
	}
	@Override
	public List<PositionAllocaAudit> queryPositionAllocaAudit(
			PositionAllocaAudit positionAllocaAudit, Page page) {
		return this.findByCriteria("queryPositionAllocaAudit", positionAllocaAudit, page);
	}

	@Override
	public boolean updateStatus(PositionAllocaAudit positionAllocaAudit) {
		boolean update = this.update("updateStatus", positionAllocaAudit);
		return update;
	}

	@Override
	public PositionAllocaAudit findPositionById(
			PositionAllocaAudit positionAllocaAudit) {
		return (PositionAllocaAudit) this.findById(positionAllocaAudit);
	}

	@Override
	public List<CapitalPoolManage> queryByStatus(String status) {
		return  this.getSqlMapClientTemplate().queryForList(this.namespace.concat("queryBystatus"));
	}

}
