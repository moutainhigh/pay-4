package com.pay.pe.account.service;

import java.util.List;

import com.pay.pe.account.dto.SubjectDTO;
import com.pay.pe.account.model.Subject;

public interface SubjectService {

	List<SubjectDTO> selectSubjectList();
	
	/**
	* @Title: countSubjectByAcctCodeAndAcctName
	* @Description: 根据账户，名称验证科目表中是否存在相应记录
	* @param @param acctCode　科目账户　
	* @param @param acctName	　科目名称
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	*/ 
	public boolean countSubjectByAcctCodeAndAcctName(String acctCode,String acctName);
}
