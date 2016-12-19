package com.pay.acc.service.member.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.common.MaConstant;
import com.pay.acc.common.config.VerifyPasswordConfig;
import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.dto.IndividualInfoDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberMessageBeanDto;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.member.service.IndividualInfoService;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.operatelog.model.OperateLog;
import com.pay.acc.operatelog.service.OperateLogService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.NotifyFacadeService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

public class MemberQueryServiceImpl implements MemberQueryService {

	private final Log log = LogFactory.getLog(MemberQueryServiceImpl.class);
	private MemberService memberService;

	private EnterpriseBaseService enterpriseBaseService;

	private IndividualInfoService individualInfoService;

	private MemberOperateService memberOperateService;

	private IMessageDigest iMessageDigest;

	private OperateLogService operateLogService;

	private final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/** website登录密码输入失败前三次发异步消息接口 */
	private NotifyFacadeService notifyFacadeService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMemberOperateService(
			MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public MemberInfoDto doQueryMemberInfoNsTx(String loginName,
			Long memberCode, Integer memberType, Integer acctType)
			throws MaMemberQueryException {
		if (memberCode == null && loginName == null) {
			log.error("memberCode and loginName are null");
			throw new MaMemberQueryException(
					ErrorExceptionEnum.MEMBER_CODE_LOGINNAME_ERROR,
					ErrorExceptionEnum.MEMBER_CODE_LOGINNAME_ERROR.getMessage());
		}

		MemberMessageBeanDto member = null;
		if (loginName != null)
			loginName = loginName.toLowerCase();
		try {
			member = memberService.queryMemberInfo(loginName, memberCode,
					memberType, acctType);
		} catch (MemberUnknowException e) {
			log.error("未知异常", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR,
					"未知异常", e);
		}

		MemberInfoDto memberInfoDto = null;
		// 设置会员名
		if (member != null) {
			memberInfoDto = BeanConvertUtil
					.convert(MemberInfoDto.class, member);
			if (member.getMemberType().equals(MaConstant.MEMBER_TYPE_PERSON)
					|| member.getMemberType().equals(
							MaConstant.MERCHANT_TYPE_PERSON)) {
				IndividualInfoDto individualInfoDto = individualInfoService
						.queryIndividualInfoByMemberCode(member.getMemberCode());
				if (individualInfoDto != null) {
					memberInfoDto.setMemberName(individualInfoDto.getName());
					memberInfoDto.setName(individualInfoDto.getName());
					memberInfoDto.setMobile(individualInfoDto.getMobile());
					memberInfoDto.setEmail(individualInfoDto.getEmail());
					memberInfoDto.setNickname(individualInfoDto.getNickname());
					memberInfoDto.setLevelCode(100);
				}
			} else if (member.getMemberType().equals(
					MaConstant.MEMBER_TYPE_MERCHANT)) {
				EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
						.queryEnterpriseBaseByMemberCode(member.getMemberCode());
				if (enterpriseBaseDto != null) {
					memberInfoDto.setMemberName(enterpriseBaseDto.getZhName());
					if (!StringUtil
							.isEmpty(enterpriseBaseDto.getRiskLeveCode())) {
						memberInfoDto.setLevelCode(Integer
								.valueOf(enterpriseBaseDto.getRiskLeveCode()));
					}

				}
			}
		}
		return memberInfoDto;
	}

	@Override
	public MaResultDto doVerifyLoginPassword(String loginName, String passWord) {
		return this.doVerifyLoginPassword(loginName, passWord, false);
	}

	@Override
	public MaResultDto doLogin(String loginName, String passWord) {
		return this.doVerifyLoginPassword(loginName, passWord, true);
	}

	@Override
	public String queryLastLoginTime(Long memberCode) {
		MemberOperateDto mo = memberOperateService.queryMemberOperate(
				memberCode, MemberOperateTypeEnum.LOGIN_PWD.getCode());
		if (mo == null) {
			return null;
		}
		if (mo.getLastLoginTime() == null) {
			return null;
		}
		return sdf.format(mo.getLastLoginTime());
	}

	public MemberBaseInfoBO queryMemberBaseInfoByMemberCode(Long memberCode)
			throws MaMemberQueryException {

		MemberDto member = null;
		try {
			member = memberService.queryMemberWithMemberCode(memberCode);
		} catch (Exception e) {
			log.error("查询会员号为：[" + memberCode + "]出现未知异常");
			throw new MaMemberQueryException(
					ErrorExceptionEnum.MEMBER_QUERY_ERROR, "查询会员号为：["
							+ memberCode + "]出现未知异常");
		}

		if (member == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaMemberQueryException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为["
							+ memberCode + "]的会员");
		}
		MemberBaseInfoBO memberBaseInfoBO = new MemberBaseInfoBO();
		memberBaseInfoBO.setUpdateDate(member.getUpdateDate());
		memberBaseInfoBO.setStatus(member.getStatus());
		memberBaseInfoBO.setServiceLevelCode(member.getServiceLevelCode());
		if (member.getSecurityQuestion() != null) {
			memberBaseInfoBO.setSecurityQuestion(member.getSecurityQuestion()
					.toString());
		}
		memberBaseInfoBO.setSecurityAnswer(member.getSecurityAnswer());
		if (member.getLoginType() != null) {
			memberBaseInfoBO.setRegType(member.getLoginType().toString());
		}
		memberBaseInfoBO.setParnterUserid(member.getSsoUserId());
		memberBaseInfoBO.setMemberType(member.getType());
		memberBaseInfoBO.setCreationDate(member.getCreateDate());
		memberBaseInfoBO.setGreeting(member.getGreeting());
		memberBaseInfoBO.setLoginName(member.getLoginName());
		memberBaseInfoBO.setMemberCode(member.getMemberCode());

		// 设置会员名
		if (member != null) {
			if (member.getType().equals(MaConstant.MEMBER_TYPE_PERSON)
					|| member.getType().equals(MaConstant.MERCHANT_TYPE_PERSON)) {
				IndividualInfoDto individualInfoDto = null;
				try {
					individualInfoDto = individualInfoService
							.queryIndividualInfoByMemberCode(member
									.getMemberCode());
				} catch (Exception e) {
					log.error("查询会员号为：[" + memberCode + "]出现未知异常");
					throw new MaMemberQueryException(
							ErrorExceptionEnum.MEMBER_QUERY_ERROR, "查询会员号为：["
									+ memberCode + "]出现未知异常");
				}
				if (individualInfoDto != null) {
					memberBaseInfoBO.setName(individualInfoDto.getName());
				}
			} else if (member.getType().equals(MaConstant.MEMBER_TYPE_MERCHANT)) {
				EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
						.queryEnterpriseBaseByMemberCode(member.getMemberCode());
				if (enterpriseBaseDto != null) {
					memberBaseInfoBO.setName(enterpriseBaseDto.getZhName());
					memberBaseInfoBO
							.setShortName(enterpriseBaseDto.getEnName());
				}
			}
		}
		return memberBaseInfoBO;
	}

	public MemberBaseInfoBO queryMemberBaseInfo(String userId)
			throws MaMemberQueryException {
		if (null == userId || !StringUtils.hasText(userId)) {
			log.error(" invaild parameter , userId is null or  invaild! ");
			throw new MaMemberQueryException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , userId is null or  invaild! ");
		}
		MemberDto memberDto = null;
		try {
			memberDto = memberService.queryMemberWithSsoUserId(userId);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberQueryException(
					ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter",
					e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR,
					"unknow error", e);
		}

		if (memberDto == null) {
			log.error("不存在userID为[" + userId + "]的会员");
			throw new MaMemberQueryException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为["
							+ userId + "]的会员");
		}
		MemberBaseInfoBO memberBaseInfoBO = new MemberBaseInfoBO();
		memberBaseInfoBO.setUpdateDate(memberDto.getUpdateDate());
		memberBaseInfoBO.setStatus(memberDto.getStatus());
		memberBaseInfoBO.setServiceLevelCode(memberDto.getServiceLevelCode());
		if (memberDto.getSecurityQuestion() != null) {
			memberBaseInfoBO.setSecurityQuestion(memberDto
					.getSecurityQuestion().toString());
		}
		memberBaseInfoBO.setSecurityAnswer(memberDto.getSecurityAnswer());
		if (memberDto.getLoginType() != null) {
			memberBaseInfoBO.setRegType(memberDto.getLoginType().toString());
		}
		memberBaseInfoBO.setParnterUserid(memberDto.getSsoUserId());
		memberBaseInfoBO.setMemberType(memberDto.getType());
		memberBaseInfoBO.setCreationDate(memberDto.getCreateDate());
		memberBaseInfoBO.setGreeting(memberDto.getGreeting());
		memberBaseInfoBO.setLoginName(memberDto.getLoginName());
		memberBaseInfoBO.setMemberCode(memberDto.getMemberCode());

		// 设置会员名
		if (memberDto != null && memberDto.getType() != null) {
			if (memberDto.getType().equals(MaConstant.MEMBER_TYPE_PERSON)
					|| memberDto.getType().equals(
							MaConstant.MERCHANT_TYPE_PERSON)) {
				IndividualInfoDto individualInfoDto = null;
				try {
					individualInfoDto = individualInfoService
							.queryIndividualInfoByMemberCode(memberDto
									.getMemberCode());
				} catch (Exception e) {
					log.error(" unknow error");
					throw new MaMemberQueryException(
							ErrorExceptionEnum.UNKNOW_ERROR, "unknow error");
				}
				if (individualInfoDto != null) {
					memberBaseInfoBO.setName(individualInfoDto.getName());
				}
			} else if (memberDto.getType().equals(
					MaConstant.MEMBER_TYPE_MERCHANT)) {
				EnterpriseBaseDto enterpriseBaseDto = null;
				try {
					enterpriseBaseDto = enterpriseBaseService
							.queryEnterpriseBaseByMemberCode(memberDto
									.getMemberCode());
				} catch (Exception e) {
					log.error(" unknow error");
					throw new MaMemberQueryException(
							ErrorExceptionEnum.UNKNOW_ERROR, "unknow error");
				}
				if (enterpriseBaseDto != null) {
					memberBaseInfoBO.setName(enterpriseBaseDto.getZhName());
				}
			}
		}
		return memberBaseInfoBO;
	}

	@Override
	public List<Long> queryMemberByName(String name) {
		return memberService.queryMemberByName(name);
	}

	@Override
	public Long queryEnterpriseRiskLeveCode(Long memberCode) {
		return enterpriseBaseService.queryEnterpriseRiskLeveCode(memberCode);
	}

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	public Long queryEnterpriseMccCode(Long memberCode) {
		return enterpriseBaseService.queryEnterpriseMccCode(memberCode);
	}

	private void addOperateLog(Long type, String objectCode, String actionUrl)
			throws MaMemberQueryException {
		try {
			OperateLog operateLog = new OperateLog();
			operateLog.setObjectCode(objectCode);
			// 登陆名，登陆IP现在默认
			operateLog.setLoginName("system");
			operateLog.setLoginIp("127.0.0.1");
			operateLog.setType(type);
			operateLog.setActionUrl(actionUrl);
			operateLog.setBrowserVer(null);
			operateLogService.insertOperateLog(operateLog);
		} catch (Exception e) {
			throw new MaMemberQueryException(e);
		}
	}

	/**
	 * 验证登录密码
	 * 
	 * @param loginName
	 *            登录名
	 * @param passWord
	 *            密码
	 * @param isLogin
	 *            是否属于登录操作（记录登录时间）
	 * @return
	 */
	private MaResultDto doVerifyLoginPassword(String loginName,
			String passWord, boolean isLogin) {
		MaResultDto result = new MaResultDto();

		if (loginName == null) {
			log.info("登录密码验证---会员不存在:loginName:" + loginName);
			result.setResultStatus(MaConstant.MEMBER_NOT_EXISTS);
			return result;
		}

		try {
			MemberDto member = memberService.queryMemberByLoginName(loginName);

			// 如果会员不存在
			if (member == null) {
				log.info("登录密码验证---会员不存在:loginName:" + loginName);
				result.setResultStatus(MaConstant.MEMBER_NOT_EXISTS);
				return result;
			}

			MemberOperateDto mo = memberOperateService.queryMemberOperate(
					member.getMemberCode(),
					MemberOperateTypeEnum.LOGIN_PWD.getCode());

			if (mo != null) {
				// 清零
				memberOperateService.cleanFailTime(mo, VerifyPasswordConfig
						.getInstance().getLoginFrozenMinute());
				// 解冻
				if (member.getStatus() == MemberInfoStatusEnum.FREEZE.getCode()) {
					int doUnlock = memberService.unlockMember(mo,
							VerifyPasswordConfig.getInstance()
									.getLoginFrozenMinute());// 解锁
					if (doUnlock > 0) {
						member.setStatus(MemberInfoStatusEnum.NORMAL.getCode());
						this.addOperateLog(
								new Long(OperateTypeEnum.UNFREEZE.getCode())
										.longValue(), String.valueOf(member
										.getMemberCode()),
								OperateTypeEnum.UNFREEZE.getMessage());
						log.info("登录密码验证---会员已经锁定，解锁 memberCode:"
								+ member.getMemberCode() + "成功");
					} else if (doUnlock == -1) {
						int minutes = VerifyPasswordConfig.getInstance()
								.getLoginFrozenMinute()
								- (int) (System.currentTimeMillis() - mo
										.getUpdateTime().getTime()) / 60000;
						log.info("登录密码验证失败---会员已经锁定，尚未到解锁时间。剩余" + minutes
								+ "分钟解锁，memberCode:" + member.getMemberCode());
						result.setResultStatus(MaConstant.ERROR_AND_LOCK);
						result.setObject(new VerifyResultDto()
								.setLeavingMinute(minutes));
						return result;
					} else {
						log.info("登录密码验证---会员已经锁定，解锁 memberCode:"
								+ member.getMemberCode() + "失败");
					}
				}

			}

			// 密码加密
			String verPassWord = iMessageDigest.genMessageDigest(passWord
					.getBytes());

			// 如果记录不存在，则创建操作记录
			if (mo == null) {
				log.info("登录密码验证---member_operate数据不存在，开始初始化数据。memberCode:"
						+ member.getMemberCode());
				mo = new MemberOperateDto();
				mo.setMemberCode(member.getMemberCode());
				mo.setUpdateTime(new Date());
				mo.setCreateTime(new Date());
				mo.setFailTime(0L);
				mo.setType(new Long(MemberOperateTypeEnum.LOGIN_PWD.getCode()));
				memberOperateService.insert(mo);
				log.info("登录密码验证---member_operate数据初始化结束。memberCode:"
						+ member.getMemberCode());
			}
			// 添加上一次登录时间
			if (mo.getLastLoginTime() == null) {
				member.setLastLoginTime(null);
			} else {
				member.setLastLoginTime(sdf.format(mo.getLastLoginTime()));
			}
			// 验证密码正确
			if (member.getLoginPwd().equals(verPassWord)) {

				// 用户已经锁定
				if (member.getStatus() == MemberInfoStatusEnum.FREEZE.getCode()) {
					log.info("登录密码验证---会员已经锁定，准备解锁会员。memberCode:"
							+ member.getMemberCode());
					int unlockSuccess = memberService.unlockMember(mo,
							VerifyPasswordConfig.getInstance()
									.getLoginFrozenMinute());// 解锁
					// 如果解锁成功
					if (unlockSuccess > 0) {
						log.info("登录密码验证---验证成功，会员解锁成功。memberCode:"
								+ member.getMemberCode());
						if (isLogin) {
							mo.setLastLoginTime(new Date());// 最后登录时间
						}
						mo.setFailTime(0L);// 错误数字清零
						mo.setUpdateTime(new Date());
						member.setStatus(MemberStatusEnum.NORMAL.getCode());
						memberOperateService.update(mo);
						result.setResultStatus(MaConstant.SECCESS);
						result.setObject(member);
						return result;
					} else {
						int minutes = VerifyPasswordConfig.getInstance()
								.getLoginFrozenMinute()
								- ((int) (System.currentTimeMillis() - mo
										.getUpdateTime().getTime()) / 60000);
						log.info("登录密码验证---验证失败，会员解锁失败。剩余" + minutes
								+ "分钟以后解锁，memberCode:" + member.getMemberCode());
						result.setResultStatus(MaConstant.ERROR_AND_LOCK);
						result.setObject(new VerifyResultDto()
								.setLeavingMinute(minutes));
						return result;
					}
				} else if (member.getStatus() == MemberInfoStatusEnum.NORMAL
						.getCode()) {
					// 用户状态正常
					if (isLogin) {
						mo.setLastLoginTime(new Date());// 最后登录时间
					}
					mo.setFailTime(0L);// 错误数字清零
					mo.setUpdateTime(new Date());
					memberOperateService.update(mo);
					result.setResultStatus(MaConstant.SECCESS);
					result.setObject(member);
					log.info("登录密码验证---验证成功，会员状态正常，登录错误次数重置。memberCode:"
							+ member.getMemberCode());
					return result;
				} else {
					// 用户状态不正常
					MemberStatusEnum mse = MemberStatusEnum.getByCode(member
							.getStatus());
					log.info("登录密码验证---验证成功，memberCode:"
							+ member.getMemberCode()
							+ "，会员状态:"
							+ (mse == null ? member.getStatus() : mse
									.getMessage()));
					result.setResultStatus(MaConstant.SUCCESS_MEMBER_STATUS_EXCEPTION);
					result.setErrorCode(String.valueOf(member.getStatus()));
					result.setErrorMsg(mse == null ? "会员状态描述不存在" : mse
							.getMessage());
					result.setObject(member);
					return result;
				}
			} else {
				// 如果账户未被锁定
				if (member.getStatus() == MemberStatusEnum.NORMAL.getCode()) {

					mo.setFailTime(mo.getFailTime() + 1);

					// modified by
					// meng.li，对于商户情况，密码输入1，2，3次情况都发消息记录登录失败信息，再多不计，防止恶意攻击
					if (mo.getFailTime() <= VerifyPasswordConfig.getInstance()
							.getLoginTotalCount()) {
						HashMap<String, Object> request = new HashMap<String, Object>();
						request.put(MaConstant.MEMBER_CODE,
								member.getMemberCode());
						request.put(MaConstant.ERROR_TIME, new Date());
						notifyFacadeService.sendRequest(
								MaConstant.FAIL_LOGIN_QUEUE_NAME, request);
					}
					// end of modify

					// 如果错误次数小于最大错误次数
					if (mo.getFailTime() < VerifyPasswordConfig.getInstance()
							.getLoginTotalCount()) {
						log.info("登录密码验证---验证失败，memberCode:"
								+ member.getMemberCode() + "更新登录错误次数："
								+ mo.getFailTime() + "次");
						// 更新记录
						memberOperateService.update(mo);
						result.setResultStatus(MaConstant.ERROR_NOT_LOCK);
						result.setObject(new VerifyResultDto()
								.setLeavingMinute(
										VerifyPasswordConfig.getInstance()
												.getLoginFrozenMinute())
								.setLeavingTime(
										VerifyPasswordConfig.getInstance()
												.getLoginTotalCount()
												- mo.getFailTime().intValue())
								.setTotalTime(
										VerifyPasswordConfig.getInstance()
												.getLoginTotalCount()));
						return result;

					} else if (mo.getFailTime() >= VerifyPasswordConfig
							.getInstance().getLoginTotalCount()) {
						int minutes = VerifyPasswordConfig.getInstance()
								.getLoginFrozenMinute()
								- ((int) (System.currentTimeMillis() - mo
										.getUpdateTime().getTime()) / 60000);
						log.info("登录密码验证---验证失败，memberCode:"
								+ member.getMemberCode() + "登录错误连续满"
								+ mo.getFailTime() + "次，锁定账户");
						memberOperateService.update(mo);
						// 锁定账户
						memberService.lockMember(member.getMemberCode());
						this.addOperateLog(
								new Long(OperateTypeEnum.FREEZE.getCode())
										.longValue(), String.valueOf(member
										.getMemberCode()),
								OperateTypeEnum.FREEZE.getMessage());
						// 如果错误次数大于等于总错误次数
						result.setResultStatus(MaConstant.ERROR_AND_LOCK);
						result.setObject(new VerifyResultDto()
								.setLeavingMinute(minutes));
						return result;
					}
				} else if (member.getStatus() == MemberStatusEnum.FROZEEN
						.getCode()) {
					int minutes = VerifyPasswordConfig.getInstance()
							.getLoginFrozenMinute()
							- ((int) (System.currentTimeMillis() - mo
									.getUpdateTime().getTime()) / 60000);
					// 如果账户已经被锁定
					log.info("登录密码验证---验证失败，memberCode:"
							+ member.getMemberCode() + "，账户已经锁定");
					result.setResultStatus(MaConstant.ERROR_AND_LOCK);
					result.setObject(new VerifyResultDto()
							.setLeavingMinute(minutes));
					return result;
				} else {
					MemberStatusEnum mse = MemberStatusEnum.getByCode(member
							.getStatus());
					log.info("登录密码验证---验证失败，memberCode:"
							+ member.getMemberCode()
							+ "，会员状态:"
							+ (mse == null ? member.getStatus() : mse
									.getMessage()) + "，状态异常");
					result.setResultStatus(MaConstant.FAILED_MEMBER_STATUS_EXCEPTION);
					result.setErrorCode(String.valueOf(member.getStatus()));
					result.setErrorMsg(mse == null ? "会员状态描述不存在" : mse
							.getMessage());
					result.setObject(member);
					return result;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatus(MaConstant.RUNTIME_EXCEPTION);
			result.setErrorMsg(e.getMessage());
			return result;
		}
		result.setResultStatus(MaConstant.OTHER);
		log.error("系统错误");
		return result;
	}

}
