package com.pay.app.facade.usercheck.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.facade.dto.ResultDto;
import com.pay.app.facade.usercheck.UserCheckServiceFacade;


/**
 * @author lei.jiangl
 * @version
 * @data 2010-8-17 下午02:25:13
 */
public class UserCheckServiceFacadeImpl implements UserCheckServiceFacade {

	private final Log log = LogFactory.getLog(UserCheckServiceFacadeImpl.class);
	

	/**
	 * 社区用户忽略关联pay账户
	 */

	// --
	@Override
	public void ignoreRelation() {

	}

	@Override
	public Long checkUser(String loginName, String loginPwd) {
		Long membercode = null;
//		try {
//			membercode = memberValidateService.doValidateLoginNsTx(loginName,
//					loginPwd);
//		} catch (MaRegisterException e) {
//			log.error("[checkUser]" + e);
//			membercode = null;
//		}
		return membercode;
	}

	@Override
	public void createUserRelation() {
//		try {
//			memberRegisterService.doMemberRegisterRnTx(new MemberRegisterBO());
//		} catch (MaRegisterException e) {
//			log.error("[createUserRelation]" + e);
//		}
	}

	@Override
	public boolean queryUserRelation(String userId) {
		boolean relBool = false;
//		try {
//			relBool = memberQueryService.doQueryMemberRelationNsTx(userId);
//		} catch (MaMemberException e) {
//			log.error("[queryUserRelation]" + e);
//		}
		return relBool;
	}

	@Override
	public int updateUserRelation(Long memberCode, String userId) {
		int relNum = 0;
//		try {
//			relNum = memberUpdateService.doResetMemberRelationIgnoreRnTx(
//					memberCode, userId);
//		} catch (MaMemberException e) {
//			log.error("[updateUserRelation]" + e);
//		}
		return relNum;
	}
	
	@Override
	public ResultDto updateUserRelationNew(Long memberCode, String userId) {
		ResultDto resultDto = new ResultDto();
		int relNum = 0;
//		try {
//			relNum = memberUpdateService.doResetMemberRelationIgnoreRnTx(
//					memberCode, userId);
//		} catch (MaMemberException e) {
//			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
//			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
//			log.error("[updateUserRelation]" + e);
//		}
		return resultDto;
	}


	@Override
	public void userQualification(Long memberCode, String userId) {
//		try {
//			memberActiveService.doResetPartnerIdActiveMemberRnTx(memberCode,
//					userId);
//		} catch (MaActiveException e) {
//			log.error("[userQualification]" + e);
//		}
	}

	@Override
	public boolean ValidateMemberRelation(Long memberCode) {
//		try {
//			return memberValidateService
//					.doValidateMemberRelationNsTx(memberCode);
//		} catch (MaValidateException e) {
//			// TODO Auto-generated catch block
//			log.error("[ValidateMemberRelation]" + e);
//			return false;
//		}
		return false;
	}

	@Override
	public String QueryMemberRelation(Long memberCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/********** set ***************/

	
}
