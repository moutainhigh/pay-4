package com.pay.pe.account.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.account.model.Subject;

public interface SubjectDao extends  BaseDAO<Object>{
	
	/**查询所有的科目列表
	 * @return
	 */
	public List<Subject> selectSubjectList();
	
	/**
	* @Title: countSubjectByAcctCodeAndAcctName
	* @Description: 根据账户，名称验证是否存在相应记录
	* @param @param subject
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	*/ 
	public boolean countSubjectByAcctCodeAndAcctName(Subject subject);
	
}
