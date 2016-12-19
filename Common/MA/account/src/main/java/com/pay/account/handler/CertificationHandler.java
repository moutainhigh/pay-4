/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.dto.IndividualInfoDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.IndividualInfoService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.cidverify.IdCardVerifyService;
import com.pay.acc.service.cidverify.dto.Cid2GovDTO;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.DESUtil;
import com.pay.util.JSonUtil;
import com.pay.util.ValidateUtils;

/**
 * 实名认证
 * 
 * @author chma
 */
public class CertificationHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ModifyPasswordHandler.class);
	private MemberService memberService;
	private IdCardVerifyService idCardVerifyService;
	private IndividualInfoService individualInfoService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setIdCardVerifyService(IdCardVerifyService idCardVerifyService) {
		this.idCardVerifyService = idCardVerifyService;
	}

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");
		String name = paraMap.get("name");
		String idType = paraMap.get("idType");
		String idNo = paraMap.get("idNo");

		if (!ValidateUtils.isValidCardId(idNo)) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_CERT_NO_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_CERT_NO_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);
		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		MemberVerifyDto memberVerifyDto = new MemberVerifyDto();
		memberVerifyDto.setCreatedDate(new Date());
		memberVerifyDto.setMemberCode(memberDto.getMemberCode());
		memberVerifyDto.setName(name);
		memberVerifyDto.setStatus(3);
		memberVerifyDto.setPhoto("test");
		memberVerifyDto.setPaperNo(DESUtil.encrypt(idNo));
		memberVerifyDto.setVerifyFlag(3);
		memberVerifyDto.setPaperType(Integer.parseInt(idType));
		memberVerifyDto.setIsPaperFile(2);

		Cid2GovDTO dto = new Cid2GovDTO();
		dto.setName(name);
		dto.setNo(idNo);

		try {

			IdcVerifyDto idcVerifyDto = idCardVerifyService
					.queryVerifyInfo(memberDto.getMemberCode());

			if (null != idcVerifyDto && idcVerifyDto.getStatus() == 1) {
				result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
				return JSonUtil.toJSonString(result);
			}

			Long cId = idCardVerifyService.saveCidVerify2Gov(memberVerifyDto);

//			boolean cidResult = idCardVerifyService.validator(name, idNo);
			if (true) {
				idCardVerifyService.updateMemberVerifyStatus(cId, 1);

				// 更新实名信息
				IndividualInfoDto individualInfoDto = individualInfoService
						.queryIndividualInfoByMemberCode(memberDto
								.getMemberCode());

				individualInfoDto.setName(name);

				individualInfoService
						.updateIndividualInfoByMemberCode(individualInfoDto);

				result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
				return JSonUtil.toJSonString(result);
			} else {
				result.put("responseCode",
						ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				result.put("responseDesc",
						ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
				return JSonUtil.toJSonString(result);
			}
		} catch (Exception e) {
			logger.error("verify error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return JSonUtil.toJSonString(result);
		}

	}
}
