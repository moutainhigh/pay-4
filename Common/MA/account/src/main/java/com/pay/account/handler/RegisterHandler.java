/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.acc.exception.MaMemberException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberIdentityDto;
import com.pay.acc.member.dto.MemberInfoDto;
import com.pay.acc.member.exception.ErrorExceptionEnum;
import com.pay.acc.member.service.MemberIdentityService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.member.service.RegisterService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.account.bean.EnterpriseRegisterCommand;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * @author xiaodai.Rainy
 *
 * @data 2015年11月28日下午2:37:28 注册调用服务
 * @param
 */

public class RegisterHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(RegisterHandler.class);
	private IMessageDigest iMessageDigest;
	private MemberService memberService;
	private MemberIdentityService memberIdentityService;
	private RegisterService registerService;

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberIdentityService(
			MemberIdentityService memberIdentityService) {
		this.memberIdentityService = memberIdentityService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, Object>().getClass());
			// 获取请求参数
			String loginName = (String) paraMap.get("loginName");
			String loginPwd = (String) paraMap.get("loginPwd");
			String loginPwdConfirm = (String) paraMap.get("loginPwdConfirm");
			String legalName = (String) paraMap.get("legalName");
			String email = (String) paraMap.get("email");
			String legalLink = (String) paraMap.get("legalLink");
			String tel = (String) paraMap.get("tel");
			String zhName = (String) paraMap.get("zhName");
			String region = (String) paraMap.get("region");
			String city = (String) paraMap.get("city");
			String address = (String) paraMap.get("address");
			String regionBank = (String) paraMap.get("regionBank");
			String cityBank = (String) paraMap.get("cityBank");
			String branchBankId = (String) paraMap.get("branchBankId");
			String bigBankName = (String) paraMap.get("bigBankName");
			String bankId = (String) paraMap.get("bankId");
			String acctName = (String) paraMap.get("acctName");
			String memberType = String.valueOf(paraMap.get("type"));
			String merchantCodePrefix = (String) paraMap
					.get("merchantCodePrefix");
			String zip = (String) paraMap.get("zip");
			merchantCodePrefix = "500";
			// 参数验证
			if (StringUtil.isEmpty(loginName) || StringUtil.isEmpty(loginPwd)
					|| StringUtil.isEmpty(memberType)) {
				result.put("responseCode",
						ErrorExceptionEnum.INVAILD_PARAMETER.getErrorCode());
				result.put("responseDesc",
						ErrorExceptionEnum.INVAILD_PARAMETER.getMessage());
				return JSonUtil.toJSonString(result);
			}

			// 判断是否已存在
			MemberIdentityDto memberIdentityDto = memberIdentityService
					.getMemberIdentity(loginName);
			MemberDto member = memberService.queryMemberByLoginName(loginName);
			if (null != memberIdentityDto || null != member) {
				result.put("responseCode",
						ErrorExceptionEnum.MEMBER_EXIST_ERROR.getErrorCode());
				result.put("responseDesc",
						ErrorExceptionEnum.MEMBER_EXIST_ERROR.getMessage());
				return JSonUtil.toJSonString(result);
			}
			String passWord = iMessageDigest.genMessageDigest(loginPwd
					.getBytes());
			String loginPwdConfirm1 = iMessageDigest.genMessageDigest(loginPwd
					.getBytes());

			try {
				// 个人注册
				if (memberType.equals("1")) {
					MemberDto memberDto = new MemberDto();

					memberDto.setLoginName(loginName);
					memberDto.setLoginPwd(passWord);
					memberDto.setType(Integer.valueOf(memberType));
					Long MemberDto1 = memberService.createMember(memberDto);

					if (MemberDto1 != null) {
						result.put("MemberDto1", MemberDto1);
						result.put("responseCode",
								ResponseCodeEnum.SUCCESS.getCode());
						result.put("responseDesc",
								ResponseCodeEnum.SUCCESS.getDesc());
						return JSonUtil.toJSonString(result);
					}

				} else {
					// 企业注册
					if (memberType.equals("2")) {

						MemberInfoDto memberInfoDto = new MemberInfoDto();
						EnterpriseRegisterCommand registerCmd = new EnterpriseRegisterCommand();

						BeanUtils.copyProperties(registerCmd, memberInfoDto);
						memberInfoDto.setLoginName(loginName);
						memberInfoDto.setLoginPwd(passWord);
						memberInfoDto.setConformPwd(loginPwdConfirm1);
						memberInfoDto.setCityBank(cityBank);
						memberInfoDto.setAddress(address);
						memberInfoDto.setTel(tel);
						memberInfoDto.setZhName(zhName);
						memberInfoDto.setRegion(region);
						memberInfoDto.setCity(city);
						memberInfoDto.setRegionBank(regionBank);
						memberInfoDto.setCityBank(cityBank);
						memberInfoDto.setBranchBankId(branchBankId);
						memberInfoDto.setBigBankName(bigBankName);
						memberInfoDto.setBankId(bankId);
						memberInfoDto.setAcctName(acctName);
						memberInfoDto.setEmail(loginName);
						memberInfoDto.setLegalName(legalName);
						memberInfoDto.setLegalLink(legalLink);
						memberInfoDto.setZip(zip);
						memberInfoDto
								.setType(MemberTypeEnum.MERCHANT.getCode());
						memberInfoDto
								.setEnterpriseType(MerchantTypeEnum.CROSSPAY_ENTERPRISEMERCHANT
										.getCode() + "");
						memberInfoDto.setMerchantCodePrefix(merchantCodePrefix);
						// 企业注册
						Long registerRdTx = registerService
								.registerRdTx(memberInfoDto);
						if (registerRdTx != null) {
							result.put("registerRdTx", registerRdTx);
							result.put("responseCode",
									ResponseCodeEnum.SUCCESS.getCode());
							result.put("responseDesc",
									ResponseCodeEnum.SUCCESS.getDesc());
							return JSonUtil.toJSonString(result);
						}

					}
				}

			} catch (Exception e) {
				logger.error("register insert error:", e);

			}
		} catch (MaMemberException e) {
			logger.error("parse request error:", e);
			result.put("responseCode", e.getErrorEnum().getErrorCode());
			result.put("responseDesc", e.getErrorEnum().getMessage());
			return JSonUtil.toJSonString(result);
		} catch (Exception e) {
			logger.error("parse request error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return JSonUtil.toJSonString(result);
		}

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

}
