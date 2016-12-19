package com.pay.pe.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.pe.account.dao.SubjectDao;
import com.pay.pe.account.dto.SubjectDTO;
import com.pay.pe.account.model.Subject;
import com.pay.pe.account.service.SubjectService;
import com.pay.util.BeanConvertUtil;

public class SubjectServiceImpl implements SubjectService {
	
	private SubjectDao subjectDao;

	@Override
	public List<SubjectDTO> selectSubjectList() {
		List<Subject> list=this.subjectDao.selectSubjectList();
		if(null==list){
			return null;
		}
		 List<SubjectDTO> subjectDtoList=new ArrayList<SubjectDTO>();
		for(Subject subject : list){
			subjectDtoList.add(BeanConvertUtil.convert(SubjectDTO.class, subject));
		}
		return subjectDtoList;		
	}

	
	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}


	@Override
	public boolean countSubjectByAcctCodeAndAcctName(String acctCode,
			String acctName) {
		Subject subject=new Subject();
		
		subject.setAcctCode(acctCode);
		subject.setAcctName(acctName);		
		return subjectDao.countSubjectByAcctCodeAndAcctName(subject);
	}
}
