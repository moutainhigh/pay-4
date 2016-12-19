package com.pay.txncore.facade.impl;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.member.dto.OperatorDto;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.acc.service.MemberRelationQueryService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.LinkerQueryService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fi.exception.BusinessException;
import com.pay.txncore.dao.PartnerConfigDAO;
import com.pay.txncore.facade.MemberInfoServiceFacade;
import com.pay.txncore.facade.dto.MaOperatorDTO;
import com.pay.txncore.facade.dto.MemberCriteriaDto;
import com.pay.txncore.model.PartnerConfig;
import com.pay.util.StringUtil;

/**
 * File: MemberInfoServiceFacadeImpl.java Description: Copyright 2006-2011 HNA
 * Corporation. All rights reserved. Date 2010-8-18 Author zengjin Changes
 * Comment 会员信息相关
 */
public class MemberInfoServiceFacadeImpl implements MemberInfoServiceFacade {
	private final String PUBLICKEY = "code1";
	private final String PAY_RESULT_NOTIFYURL = "code2";
	private final String CONFIRM_PAY_NOTIFYURL = "code3";
	private final String REFUND_NOTIFYURL = "code4";
	private final String SENTTO_BANK_NOTIFYURL = "code5";
	private final String TRANSACTION_DETAILURL = "code6";
	private final String DIRECT_FLAG = "code8";
	private final String ASSIGN_PROFIT = "code9";
	private final String REFUND_DEADLINE = "code10";
	private final String ALLOW_REFUND_FEE = "code11";
	private final String ASYNC_NOTICE_PARAM = "code12";
	// private final String DIRECT_NOTICE_PARAM = "code13"; //直连下前台通知带参数
	private final String APSERVICE_PROFIT = "code14";// 防钓鱼服务权限
	private final String PARTNER_REFERER = "code15"; // 商户网站referer
	private final String APSERVICE_URL = "code16"; // 可疑订单通知URL

	private static final Log log = LogFactory
			.getLog(MemberInfoServiceFacadeImpl.class);

	private MemberQueryService memberQueryService;

	private LinkerQueryService linkerQueryService;

	// FI获取商户配置DAO
	private PartnerConfigDAO partnerConfigDAO;

	private MemberRelationQueryService memberRelationQueryService;
	private MemberOperateService memberOperateService;

	/**
	 * 根据商户ID和参数编码查询商户相关配置(2011-04-13 fred update")
	 * 
	 * @param partner
	 * @param paramCode
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private PartnerConfig queryPartnerConfigByPIDAndPCode(Long partnerID,
			String paramCode) throws Exception {
		if (partnerID <= 0 || StringUtil.isEmpty(paramCode)) {
			throw new BusinessException(
					"MemberInfoServiceFacadeImpl-queryPartnerConfigByPIDAndPCode-查询商户配置参数错误");
		}
		PartnerConfig modelConfig = partnerConfigDAO.findConfig(partnerID,
				paramCode);
		if (null == modelConfig) {
			throw new BusinessException(
					"MemberInfoServiceFacadeImpl-queryPartnerConfigByPIDAndPCode-查询商户配置返回异常");
		}
		return modelConfig;
	}

	/**
	 * 根据商户ID获取确认支付通知URL (2011-04-13 fred update)
	 */
	@Override
	public String queryConfirmPayNotifyURL(Long partner) throws Exception {
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, CONFIRM_PAY_NOTIFYURL);
		if (null != configModel)
			return configModel.getValue();
		return null;
	}

	/**
	 * 根据商户ID获取支付通知URL (2011-04-13 fred update)
	 */
	@Override
	public String queryPayResultNotifyURL(Long partner) throws Exception {
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, PAY_RESULT_NOTIFYURL);
		if (null != configModel)
			return configModel.getValue();
		return null;
	}

	/**
	 * 根据商户ID获取退款通知URL (2011-04-13 fred update)
	 */
	@Override
	public String queryRefundNotifyURL(Long partner) throws Exception {
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, REFUND_NOTIFYURL);
		if (null != configModel)
			return configModel.getValue();
		return null;
	}

	/**
	 * 根据商户ID获取接收网关已经发送交易信息到银行的通知 (2011-04-13 fred update)
	 */
	@Override
	public String querySentToBankNotifyURL(Long partner) throws Exception {
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, SENTTO_BANK_NOTIFYURL);
		if (null != configModel)
			return configModel.getValue();
		return null;
	}

	/**
	 * (2011-04-13 fred update)
	 */
	@Override
	public String queryTransactionDetailURL(Long partner) throws Exception {
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, TRANSACTION_DETAILURL);
		if (null != configModel)
			return configModel.getValue();
		return null;
	}

	/**
	 * (2011-04-13 fred update)
	 */
	@Override
	public String queryAssignProfitURL(Long partner) throws Exception {
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, ASSIGN_PROFIT);
		if (null != configModel)
			return configModel.getValue();
		return null;
	}

	/**
	 * (2011-04-13 fred update)
	 */
	@Override
	public String queryPublicKey(Long partner) throws Exception {
		// 查询商户的公钥，以BASE64编码方式存放在数据库中的， 注意这里的Base64解码的时候需要考虑换行 \r \n的情况
		String base64Data = "";
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, PUBLICKEY);
		if (null != configModel)
			return configModel.getValue();
		return null;
	}

	public MemberCriteriaDto queryMemberInfoByMemberCode(final Long memberCode) {
		// log.info("queryMemberInfoByMemberCode" + memberCode);
		MemberBaseInfoBO memberBaseInfoBO = null;
		MemberCriteriaDto memberInfo = null;
		try {
			memberBaseInfoBO = memberQueryService
					.queryMemberBaseInfoByMemberCode(memberCode);

			memberInfo = new MemberCriteriaDto();
			if (null != memberBaseInfoBO.getMemberCode()) {
				Long tempMemberCode = memberBaseInfoBO.getMemberCode();
				memberInfo.setMemberCode(tempMemberCode);
			}
			if (null != memberBaseInfoBO.getStatus()) {
				Integer status = memberBaseInfoBO.getStatus();
				memberInfo.setStatus(status);
			}
			memberInfo.setName(memberBaseInfoBO.getName());
			memberInfo.setSalutatory(memberBaseInfoBO.getGreeting());
			memberInfo.setGreeting(memberBaseInfoBO.getGreeting());
			memberInfo.setSecurityAnswer(memberBaseInfoBO.getSecurityAnswer());
			memberInfo.setSecurityQuestion(memberBaseInfoBO
					.getSecurityQuestion());
			memberInfo.setUpdateDate(memberBaseInfoBO.getUpdateDate());
			memberInfo.setRegType(memberBaseInfoBO.getRegType());
			memberInfo.setLoginName(memberBaseInfoBO.getLoginName());
			memberInfo.setMemberType(memberBaseInfoBO.getMemberType());
			memberInfo.setCreationDate(memberBaseInfoBO.getCreationDate());
			memberInfo.setShortName(memberBaseInfoBO.getShortName());
		} catch (MaMemberQueryException e) {
			log.error("queryMemberBaseInfoByMemberCode throws error"
					+ ArrayUtils.toString(e.getStackTrace()), e);
		}
		return memberInfo;
	}

	@Override
	public MemberCriteriaDto queryMemberBaseInfoByUserId(String userId) {
		log.info("queryMemberBaseInfoByUserId" + userId);
		MemberBaseInfoBO memberBaseInfoBO = null;
		MemberCriteriaDto memberInfo = null;
		try {
			memberBaseInfoBO = memberQueryService.queryMemberBaseInfo(userId);

			memberInfo = new MemberCriteriaDto();
			if (null != memberBaseInfoBO.getMemberCode()) {
				Long tempMemberCode = memberBaseInfoBO.getMemberCode();
				memberInfo.setMemberCode(tempMemberCode);
			}
			if (null != memberBaseInfoBO.getStatus()) {
				Integer status = memberBaseInfoBO.getStatus();
				memberInfo.setStatus(status);
			}
			memberInfo.setName(memberBaseInfoBO.getName());
			memberInfo.setSalutatory(memberBaseInfoBO.getGreeting());
			memberInfo.setGreeting(memberBaseInfoBO.getGreeting());
			memberInfo.setSecurityAnswer(memberBaseInfoBO.getSecurityAnswer());
			memberInfo.setSecurityQuestion(memberBaseInfoBO
					.getSecurityQuestion());
			memberInfo.setUpdateDate(memberBaseInfoBO.getUpdateDate());
			memberInfo.setRegType(memberBaseInfoBO.getRegType());
			memberInfo.setLoginName(memberBaseInfoBO.getLoginName());
			memberInfo.setMemberType(memberBaseInfoBO.getMemberType());
			memberInfo.setCreationDate(memberBaseInfoBO.getCreationDate());
		} catch (MaMemberQueryException e) {
			if (e.getErrorEnum()
					.getErrorCode()
					.equals(ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR
							.getErrorCode())) {
				log.info("不存在ssoid为" + userId + "的会员");
			} else {
				log.error("queryMemberBaseInfoByUserId throws error"
						+ ArrayUtils.toString(e.getStackTrace()), e);
			}
		}
		return memberInfo;
	}

	@Override
	public boolean isBePartOfTheBourse(Long platformID, Long dealer) {
		if (platformID == null || dealer == null) {
			log.info("查询交易方是否属于平台商体系：" + platformID + "; " + dealer);
			return false;
		} else {
			return memberRelationQueryService.isBePartOfTheBourse(
					platformID.toString(), dealer.toString());
		}
	}

	@Override
	public Boolean queryIsLinkerWithMemberCode(Long orgMemberCode,
			Long linkMemberCode) throws Exception {
		Boolean resoutBoolean;
		resoutBoolean = linkerQueryService.queryIsLinkerWithMemberCode(
				orgMemberCode, linkMemberCode);
		return resoutBoolean;
	}

	@Override
	public boolean partnerAllowRefundFee(Long partner) throws Exception {
		try {
			PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
					partner, this.ALLOW_REFUND_FEE);
			if (null != configModel) {
				return Integer.valueOf(configModel.getValue()) == 1 ? true
						: false;
			}
			return false;
		} catch (BusinessException e) {
			log.info("ALLOW_REFUND_FEE=" + partner + ",商户配置,查询出错.");
			return false;
		}
	}

	@Override
	public int queryPartnerRefundDeadLine(Long partner) throws Exception {
		PartnerConfig configModel = this.queryPartnerConfigByPIDAndPCode(
				partner, this.REFUND_DEADLINE);
		if (null != configModel) {
			return Integer.valueOf(configModel.getValue());
		}
		return 0;
	}

	/**
	 * 查询商户是否退手续费，如果商户不退则默认为平台退手续费
	 * 
	 * @throws Exception
	 */
	@Override
	public boolean isPlatformAllowRefundFee(Long partner) throws Exception {
		return !(this.partnerAllowRefundFee(partner));
	}

	@Override
	public boolean isPartnerHasDirectProfit(Long partner) {
		PartnerConfig configModel = new PartnerConfig();
		try {
			configModel = this.queryPartnerConfigByPIDAndPCode(partner,
					this.DIRECT_FLAG);
			if (null != configModel) {
				if (configModel.getValue().equals("1")) {// 开通直连权限
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			log.error("partner=" + partner + " 查询直连权限出错", e);
			return false;
		}
		return false;
	}

	@Override
	public Long selectMemberInfoDtoByBargainerName(Long memberCode, String name) {
		// TODO Auto-generated method stub
		MemberInfoDto memberInfoDto = null;
		try {
			memberInfoDto = memberRelationQueryService
					.selectMemberInfoDtoByBargainerName(memberCode, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (memberInfoDto != null) {
			return memberInfoDto.getMemberCode();
		} else {
			return 0L;
		}

	}

	@Override
	public boolean isPartnerNeedAsyncNoticeParam(Long partner) {
		PartnerConfig configModel = new PartnerConfig();
		try {
			configModel = this.queryPartnerConfigByPIDAndPCode(partner,
					this.ASYNC_NOTICE_PARAM);
			if (null != configModel) {
				if (configModel.getValue().equals("1")) {// 同步通知中需要带参数
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			log.error("query error:", e);
			log.warn("partner=" + partner + " 查询同步通知权限出错");
			return false;
		}
		return false;
	}

	@Override
	public boolean isPartnerNeedAsyncNoticeParam(Long partner, String code) {
		PartnerConfig configModel = new PartnerConfig();
		try {
			configModel = this.queryPartnerConfigByPIDAndPCode(partner, code);
			if (null != configModel) {
				if (configModel.getValue().equals("1")) {// 直连通知中需要带参数
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			log.warn("partner=" + partner + "code=" + code + " 通知权限出错");
			return false;
		}
		return false;
	}

	public MaOperatorDTO queryOperatorByMemberCode(Long memberCode,
			String identity) {
		MaOperatorDTO maOperatorDTO = null;
		try {
			OperatorDto operator = memberOperateService
					.queryOperatorByMemberCode(memberCode, identity);
			if (null == operator)
				return null;
			maOperatorDTO = new MaOperatorDTO();
			BeanUtils.copyProperties(operator, maOperatorDTO);
			return maOperatorDTO;
		} catch (Exception e) {
			log.info("由memberCode和操作员名称查询操作员信息异常！异常信息如下：");
			e.printStackTrace();
			return null;
		}
	}

	public void setMemberRelationQueryService(
			MemberRelationQueryService memberRelationQueryService) {
		this.memberRelationQueryService = memberRelationQueryService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public LinkerQueryService getLinkerQueryService() {
		return linkerQueryService;
	}

	public void setLinkerQueryService(LinkerQueryService linkerQueryService) {
		this.linkerQueryService = linkerQueryService;
	}

	public void setPartnerConfigDAO(PartnerConfigDAO partnerConfigDAO) {
		this.partnerConfigDAO = partnerConfigDAO;
	}

	public void setMemberOperateService(
			MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	@Override
	public boolean isPartnerHasAPService(Long partner) {
		PartnerConfig configModel = new PartnerConfig();
		try {
			configModel = this.queryPartnerConfigByPIDAndPCode(partner,
					this.APSERVICE_PROFIT);
			if (null != configModel) {
				if (configModel.getValue().equals("1")) {// 防钓鱼接口权限 1 ，有 0 ，没有
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			log.warn("partner=" + partner + "code=" + this.APSERVICE_PROFIT
					+ " 防钓鱼权限出错");
			return false;
		}
		return false;
	}

	@Override
	public String queryPartnerReferer(Long partner) {
		PartnerConfig configModel = new PartnerConfig();
		try {
			configModel = this.queryPartnerConfigByPIDAndPCode(partner,
					this.PARTNER_REFERER);
			if (null != configModel) {
				String referer = configModel.getValue();
				if (null != referer && !referer.equals("")) {// 商户网站referer
					return referer;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			log.warn("partner=" + partner + "code=" + this.PARTNER_REFERER
					+ " 商户网站referer配置出错");
			return null;
		}
		return null;
	}

	@Override
	public String queryAPServiceNoticeUrl(Long partner) {
		PartnerConfig configModel = new PartnerConfig();
		try {
			configModel = this.queryPartnerConfigByPIDAndPCode(partner,
					this.APSERVICE_URL);
			if (null != configModel) {
				String url = configModel.getValue();
				if (null != url && !url.equals("")) {// 商户网站可疑订单通知地址
					return url;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			log.warn("partner=" + partner + "code=" + this.APSERVICE_URL
					+ " 商户网站可疑订单通知接受URL配置出错");
			return null;
		}
		return null;
	}

}
