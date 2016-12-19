/**
 * 
 */
package com.pay.wechat.dao.questionbind.impl;

import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.wechat.dao.questionbind.QuestionBindDao;
import com.pay.wechat.model.QuestionBind;
import com.pay.wechat.model.SysUserMapper;

/**
 * @author PengJiangbo
 *
 */
public class QuestionBindDaoImpl extends BaseDAOImpl implements QuestionBindDao {

	@Override
	public long addQuestionBindInfo(QuestionBind questionBind) {
		long result = (Long) getSqlMapClientTemplate().insert(getNamespace().concat("addQuestionBindInfo"), questionBind) ;
		return result ;
	}

	@Override
	public QuestionBind findQuestionBindByMemberCode(String openID) {
		QuestionBind questionBind = (QuestionBind) this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("findQuestionBindByMemberCode"), openID) ;
		return questionBind;
	}

	@Override
	public int deletelQuestionBindByOpenID(String openID) {
		return this.getSqlMapClientTemplate().delete(getNamespace().concat("deletelQuestionBindByOpenID"), openID) ;
	}


}
