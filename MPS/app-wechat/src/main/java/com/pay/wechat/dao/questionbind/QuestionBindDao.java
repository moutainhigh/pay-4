/**
 * 
 */
package com.pay.wechat.dao.questionbind;

import com.pay.wechat.model.QuestionBind;

/**
 * 安全问题绑定
 * @author PengJiangbo
 *
 */
public interface QuestionBindDao {
	
	/**
	 * 绑定验证问题
	 * @param questionBind
	 * @return
	 */
	public long addQuestionBindInfo(QuestionBind questionBind) ;
	
	/**
	 * 根据商户号查询绑定问题
	 * @param questionBind
	 * @return
	 */
	public QuestionBind findQuestionBindByMemberCode(String openID) ;
	
	/**
	 * 根据openID删除安全问题绑定
	 * @param openID
	 * @return
	 */
	public int deletelQuestionBindByOpenID(String openID) ;
	
}
