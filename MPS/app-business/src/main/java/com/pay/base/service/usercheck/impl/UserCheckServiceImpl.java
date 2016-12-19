package com.pay.base.service.usercheck.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dao.member.MemberDAO;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Member;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.usercheck.UserCheckService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.StringUtil;


public class UserCheckServiceImpl implements UserCheckService{
	
	private final Log log = LogFactory.getLog(UserCheckServiceImpl.class);

	private MemberService memberService;
	
	private MemberDAO memberDAO;
	
	private IMessageDigest iMessageDigest;
	
	public boolean queryUserRelation(String userId) {
		boolean relBool = false;
		try {
			log.info("query member some information by member's partnerUserId " + userId );
			
			MemberDto memberDto = memberService.queryMemberBySsoUserid(userId);
			if(memberDto != null){
				relBool = true;
			}else{
				relBool = false;
			}
		} catch (Exception e) {
			log.error("unknow error", e);
		}
		return relBool;
	}
	
	public Integer updateUserRelation(Long memberCode, String userId){
		log.info("reset member relationRn " + memberCode);
		
		//默认返回0
		int flag=0;
		Boolean isSuccess=false;
		MemberDto member = null;
		MemberDto ssoMember = null;
		
		try {
			member = memberService.findByMemberCode(memberCode);
			ssoMember = memberService.queryMemberBySsoUserid(userId);
		} catch (Exception e) {
			log.error("unknow error !", e);
		}
		if(member != null){
			//操作自己
			if(ssoMember == null || (null != userId && memberCode.equals(member.getMemberCode()))){
				//建立关联
				member.setSsoUserId(userId);
				member.setUpdateDate(new Date());
				//是否成功
				try {
					isSuccess = memberService.updateMemberSsoUserid(memberCode, userId);
					flag=2; //关联成功  建立关联
				} catch (Exception e) {
					log.error("unknow error !", e);
				}
				if(!isSuccess){
					log.error("member code error !");
				}
				log.info("update Member message");
				//存在不同memberCode
			}else{
				log.error("parnterUserid is exist !");
			}

		}else{
			log.info("member non exist error !");
		}
		
		//判断是否关联
		//1：已激活，未关联，并且成功建立关联
		//2：未激活，未关联，建立关联，但未激活
		if(isSuccess && member.getStatus()==1){
			flag=1;
		}
		return flag;
	}
	
	
	public boolean validateMemberRelation(Long memberCode) {
		boolean isSuccess=false;
		MemberDto member = null;
		try {
			member = memberService.findByMemberCode(memberCode);
			if(member!=null && !StringUtil.isEmpty(member.getSsoUserId())){
				isSuccess=true;
			}
			return isSuccess;
		} catch (Exception e) {
			log.error("[ValidateMemberRelation]" + e);
		}
		return isSuccess;
	}
	
	public ResultDto updateUserRelationNew(Long memberCode, String userId){
		ResultDto resultDto = new ResultDto();
		boolean relNum = false;
		try {
			relNum = memberService.updateMemberSsoUserid(memberCode, userId);
			if(memberCode != null){
				resultDto.setMemberCode(memberCode.toString());
			}
			resultDto.setObject(relNum);
		} catch (Exception e) {
			resultDto.setErrorCode(ErrorCodeEnum.MEMBER_SSO_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorCodeEnum.MEMBER_SSO_ERROR.getMessage());
			resultDto.setErrorNum(ErrorCodeEnum.MEMBER_SSO_ERROR);
			log.error("[updateUserRelation]" + e);
		}
		return resultDto;
	}
	
	public Long checkUser(String loginName, String loginPwd) {
		Long membercode = null;
		Member member = null;
		if(loginName != null && loginPwd != null){
			try {
			    // 查询个人信息
				member = memberDAO.findMemberByLogin(loginName,new Integer(1));
				loginPwd = iMessageDigest.genMessageDigest(loginPwd.getBytes());
	            if(member != null && member.getLoginPwd().equals(loginPwd)){
	            	membercode = member.getMemberCode();
	            }
			} catch (Exception e) {
				log.error("[checkUser]" + e);
				membercode = null;
			}
		}
		return membercode;
	}

	public MemberDAO getMemberDAO() {
		return memberDAO;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}


}
