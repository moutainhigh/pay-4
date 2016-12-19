/**
 * 
 */
package com.pay.wechat.service.questionbind;

import java.util.Map;

import com.pay.wechat.model.QuestionBind;
import com.pay.wechat.model.SysUserMapper;

/**
 * @author PengJiangbo
 *
 */
public interface QuestionBindService {

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
