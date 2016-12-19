package com.pay.pe.account.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.account.dao.SubjectDao;
import com.pay.pe.account.model.Subject;

public class SubjectDaoImpl extends BaseDAOImpl<Object> implements SubjectDao {

	

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> selectSubjectList() {
		return super.getSqlMapClientTemplate().queryForList(this.namespace.concat("selectSubjectList"));
	}

	@Override
	public boolean countSubjectByAcctCodeAndAcctName(Subject subject) {
		Integer obj= (Integer) getSqlMapClientTemplate().queryForObject(this.namespace.concat("countSubjectByAcctCodeAndAcctName"),subject);
		if(null!=obj && obj>0){
			return true;
		}else{
			return false;
		}
	}

}
