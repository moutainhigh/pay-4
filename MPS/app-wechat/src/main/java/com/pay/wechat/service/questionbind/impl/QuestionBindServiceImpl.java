/**
 * 
 */
package com.pay.wechat.service.questionbind.impl;

import java.util.Map;

import com.pay.wechat.dao.questionbind.QuestionBindDao;
import com.pay.wechat.model.QuestionBind;
import com.pay.wechat.model.SysUserMapper;
import com.pay.wechat.service.questionbind.QuestionBindService;

/**
 * @author PengJiangbo
 *
 */
public class QuestionBindServiceImpl implements QuestionBindService {

	//注入
	private QuestionBindDao questionBindDao;
	//-------------------------------------------------------setter
	public void setQuestionBindDao(QuestionBindDao questionBindDao) {
		this.questionBindDao = questionBindDao;
	}
	
	@Override
	public long addQuestionBindInfo(QuestionBind questionBind) {
		long result = this.questionBindDao.addQuestionBindInfo(questionBind) ;
		return result;
	}

	@Override
	public QuestionBind findQuestionBindByMemberCode(String openID) {
		QuestionBind questionBind = this.questionBindDao.findQuestionBindByMemberCode(openID) ;
		return questionBind;
	}

	@Override
	public int deletelQuestionBindByOpenID(String openID) {
		return this.questionBindDao.deletelQuestionBindByOpenID(openID) ;
	}
	
}
