/**
 * 
 */
package com.pay.app.dao.bankacct.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.app.dao.bankacct.BankAcctDAO;
import com.pay.app.model.BankAcct;
import com.pay.base.model.LiquidateInfo;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class BankAcctDAOImpl extends BaseDAOImpl implements BankAcctDAO {

	public void addBankAcct(BankAcct bankAcct) {
		getSqlMapClientTemplate().insert(namespace.concat("InsertBankAcct"),
				bankAcct);
	}

	public int getUserBankAcctNum(String memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getMemberBankNum"), memberCode);
	}

	public Class getModelClass() {
		return BankAcct.class;
	}

	public int checkBankCardExits(String bankAcctId) {

		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("checkBankCardExits"), bankAcctId);
	}
	//	
	public List<BankAcct> getBankAcctByCode(Long memberCode) {
		List<BankAcct> bankAcctList = getSqlMapClientTemplate().queryForList(
				namespace.concat("queryBankAcctByCode"), memberCode);
		return bankAcctList;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public List<LiquidateInfo> queryBankAcctByMemberCode(Long memberCode) {
		List<LiquidateInfo> liquidateInfoList =getSqlMapClientTemplate().queryForList(namespace.concat("queryBankAcctByMemberCode"), memberCode);
		return liquidateInfoList;
	}
	
	
	

	public BankAcct getBankAcctById(Long id) {
		return (BankAcct) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("selectBankAcctById"), id);
	}

	public int removeBankAcctById(Long id) {
		return this.getSqlMapClientTemplate().delete(
				namespace.concat("deleteBankAcctById"), id);
	}

	public int editBankAcct(BankAcct bankAcct) {
		return this.getSqlMapClientTemplate().update(
				namespace.concat("updateBankAcct"), bankAcct);
	}

	public BankAcct queryBankAcct(BankAcct bankAcct) {
		return (BankAcct) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("selectBankAcct"), bankAcct);
	}

	@Override
	public int updateStatus(String memberCode, Long liquidateId, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (liquidateId != null && liquidateId > 0) {
			map.put("id", liquidateId);
		}
		map.put("memberCode", new Long(memberCode));
		map.put("isPrimaryBankacct", status);
		return getSqlMapClientTemplate().update(
				namespace.concat("updateStatus"), map);
	}

	@Override
	public int updateStatus(String memberCode, Integer status) {
		return updateStatus(memberCode, null, status);
	}

	

}
