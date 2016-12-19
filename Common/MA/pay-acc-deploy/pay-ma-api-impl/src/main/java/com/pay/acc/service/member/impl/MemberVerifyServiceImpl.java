package com.pay.acc.service.member.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.exception.MaMemberVerifyException;
import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberVerifyResultDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.service.IdcVerifyBaseService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.cidverify.IdcVerifyGovService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.inf.service.IMessageDigest;

public class MemberVerifyServiceImpl implements MemberVerifyService {

	private IdcVerifyBaseService idcVerifyBaseService;
	private IdcVerifyGovService idcVerifyGovService;
	private MemberService memberService;
	private IMessageDigest iMessageDigest;
	private Log log = LogFactory.getLog(MemberVerifyServiceImpl.class);

	/**
	 * 根据memberCode查询实名认证信息
	 * 
	 * @param memberCode
	 * @return MemberVerifyResult
	 * @exception Exception
	 * @author lei.jiangl
	 */
	public MemberVerifyResult QueryMemberVerifyByMemberCode(Long memberCode) throws Exception {
		MemberVerifyResult result = null;
		if (null != memberCode) {
			log.info("Start QueryMemberLevelByMemberCode.");
			MemberVerifyResultDto dto = idcVerifyBaseService.QueryMemberVerifyByMemberCode(memberCode);
			result = new MemberVerifyResult();
			result.setVerify(dto.isVerify());
			result.setMemberLevel(dto.getMemberLevel());
			log.info("End QueryMemberLevelByMemberCode.");
		} else {
			log.error("memberCode is null");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.service.member.MemberVerifyService#doQueryRealNameVerifyNsTx
	 * (java.lang.Long)
	 */
	public boolean doQueryRealNameVerifyNsTx(Long memberCode) throws MaMemberVerifyException {
		// 验证
		this.checkQueryParameter(memberCode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		log.debug(" query membercode  verify , membercode is " + memberCode);
		boolean bool = false;
		// 查询数据库：是否存在成功的实名认证记录
		try {
			bool = idcVerifyBaseService.queryVerifySuccess(map);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberVerifyException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		log.debug("query  queryverifysuccesscount success");
		return bool;
	}

	public MemberVerifyDto doQueryMemberVerifyInfoNsTx(Long memberCode) throws MaMemberVerifyException {
		// 验证
		this.checkQueryParameter(memberCode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		log.debug(" query membercode  verify , membercode is " + memberCode);

		IdcVerifyDto idcVerifyDto = null; // 认证成功记录
		// 查询数据库：是否存在成功的实名认证记录
		try {
			idcVerifyDto = idcVerifyBaseService.queryVerifyInfo(map);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberVerifyException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		log.debug("query  queryVerifyInfo success");

		MemberVerifyDto memberVerifyDto = null;// 没有认证成功的记录返回空
		// 有成功的实名认证记录
		if (null != idcVerifyDto) {
			memberVerifyDto = new MemberVerifyDto();
			memberVerifyDto.setErrorCode(idcVerifyDto.getErrorCode());
			memberVerifyDto.setCreatedDate(idcVerifyDto.getCreatedDate());
			memberVerifyDto.setErrorMsg(idcVerifyDto.getErrorMsg());
			memberVerifyDto.setId(idcVerifyDto.getId());
			memberVerifyDto.setIsPaperFile(idcVerifyDto.getIsPaperFile());
			memberVerifyDto.setLinkNo(idcVerifyDto.getLinkNo());
			memberVerifyDto.setMemberCode(idcVerifyDto.getMemberCode());
			memberVerifyDto.setName(idcVerifyDto.getName());
			memberVerifyDto.setPaperNo(idcVerifyDto.getPaperNo());
			memberVerifyDto.setPaperType(idcVerifyDto.getPaperType());
			memberVerifyDto.setPhoto(idcVerifyDto.getPhoto());
			memberVerifyDto.setStatus(idcVerifyDto.getStatus());
			memberVerifyDto.setVerifyFlag(idcVerifyDto.getVerifyFlag());
		}
		return memberVerifyDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.member.MemberVerifyService#
	 * doVerifyMemberSecurityQuestionNsTx(long, int, java.lang.String)
	 */
	public boolean doVerifyMemberSecurityQuestionNsTx(Long memberCode, Integer securQuestionId, String answer)
			throws MaMemberVerifyException {

		// 验证会员信息
		MemberDto member = this.checkQueryParameter(memberCode);

		// 验证参数
		this.checkQueryParameter(securQuestionId, answer);

		// 安全问题加密
		try {
			answer = iMessageDigest.genMessageDigest(answer.getBytes());
		} catch (Exception e) {
			log.error("SHAMessageDigest is error");
			throw new MaMemberVerifyException(ErrorExceptionEnum.SHA_MESSAGE_DIGEST_ERROR, "SHAMessageDigest is error", e);
		}
		boolean result = VERIFY_FAIL; // 返回值默认false

		try {
			result = answer.equals(member.getSecurityAnswer()) && securQuestionId.equals(member.getSecurityQuestion());
		} catch (Exception e) {
			log.error("member verify security question error");
			throw new MaMemberVerifyException(ErrorExceptionEnum.MEMBER_VERIFY_QUESTION_ERROR, "member verify security question error");
		}

		return result;
	}

	// 验证参数
	private boolean checkQueryParameter(Integer securQuestionId, String answer) throws MaMemberVerifyException {
		if (null == securQuestionId || securQuestionId.intValue() <= 0) {
			log.error(" invaild parameter , securQuestionId is null ! ");
			throw new MaMemberVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , securQuestionId is null ! ");
		} else if (null == answer || !StringUtils.hasText(answer)) {
			log.error(" invaild parameter , answer is null ! ");
			throw new MaMemberVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , answer is null ! ");
		}
		return true;

	}

	// 会员验证参数
	@SuppressWarnings("deprecation")
	private MemberDto checkQueryParameter(Long memberCode) throws MaMemberVerifyException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null ! ");
			throw new MaMemberVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null ! ");
		}

		// else if (memberCode < MaConstant.CHECK_MEMBER_CODE) {
		// log.error("member code error");
		// throw new MaMemberVerifyException(
		// ErrorExceptionEnum.MEMBERCODE_ERROR, "member code error");
		// }
		// 验证会员是否激活
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberVerifyException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaMemberVerifyException(ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为[" + memberCode + "]的会员");
		}
		if (null == memberDto.getStatus() || memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL.getCode()) {
			log.error("会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());
			throw new MaMemberVerifyException(ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode + "]处于非"
					+ MemberInfoStatusEnum.NORMAL.getDescription());
		}
		return memberDto;

	}
	
	public boolean active(Long memberCode){
		if(memberCode!=null)
			return memberService.active(memberCode);
		return false;
	}
	
	
	public boolean updateLoginPwd(Long memberCode,String loginPwd){
		if(memberCode!=null && loginPwd!=null)
			try {
				return memberService.updateLoginPwd(memberCode, iMessageDigest.genMessageDigest(loginPwd.getBytes()));
			} catch (Exception e) {
				log.error(e);
			}
		return false;
	}

	public IdcVerifyBaseService getIdcVerifyBaseService() {
		return idcVerifyBaseService;
	}

	public void setIdcVerifyBaseService(IdcVerifyBaseService idcVerifyBaseService) {
		this.idcVerifyBaseService = idcVerifyBaseService;
	}

	public IdcVerifyGovService getIdcVerifyGovService() {
		return idcVerifyGovService;
	}

	public void setIdcVerifyGovService(IdcVerifyGovService idcVerifyGovService) {
		this.idcVerifyGovService = idcVerifyGovService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

}
