package com.pay.txncore.facade;

import com.pay.txncore.facade.dto.MaOperatorDTO;
import com.pay.txncore.facade.dto.MemberCriteriaDto;

/**
 * File: MemberInfoServiceFacade.java Description: Copyright 2006-2011 HNA
 * Corporation. All rights reserved. Date 2010-8-13 Author zengjin Changes
 * Comment
 */
public interface MemberInfoServiceFacade {

	/**
	 * 通过UserId获取会员基本信息
	 * 
	 * @param userId
	 * @return MemberCriteriaDto
	 */
	public MemberCriteriaDto queryMemberBaseInfoByUserId(final String userId);

	/**
	 * 通过memberCode获取会员基本信息
	 * 
	 * @param memberCode
	 * @return MemberCriteriaDto
	 */
	public MemberCriteriaDto queryMemberInfoByMemberCode(final Long memberCode);

	// MemberCriteriaDto queryMemberProfileByMemberCode(final Long memberCode);

	/**
	 * 查询商户公钥
	 * 
	 * @param partner
	 * @return
	 */
	public String queryPublicKey(Long partner) throws Exception;

	/**
	 * 支付结果URL
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public String queryPayResultNotifyURL(Long partner) throws Exception;

	/**
	 * 确认付款URL
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public String queryConfirmPayNotifyURL(Long partner) throws Exception;

	/**
	 * 退款通知URL
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public String queryRefundNotifyURL(Long partner) throws Exception;

	/**
	 * 发送银行消息URL
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public String querySentToBankNotifyURL(Long partner) throws Exception;

	/**
	 * 查询交易明细URL
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public String queryTransactionDetailURL(Long partner) throws Exception;

	/**
	 * 分润结果URL
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public String queryAssignProfitURL(Long partner) throws Exception;

	/**
	 * 根据memberCode判断对方是否为联系人
	 * 
	 * @param orgMemberCode
	 *            当前用户名memberCode
	 * @param linkMemberCode
	 *            交易对方的memberCode
	 * @return
	 * @throws Exception
	 */
	public Boolean queryIsLinkerWithMemberCode(Long orgMemberCode,
			Long linkMemberCode) throws Exception;

	/**
	 * 查询商户允许退手续费
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean partnerAllowRefundFee(Long partner) throws Exception;

	/**
	 * 查询支付平台是否退还手续费
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isPlatformAllowRefundFee(Long partner) throws Exception;

	/**
	 * 查询商户退款期限
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public int queryPartnerRefundDeadLine(Long partner) throws Exception;

	/**
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public boolean isPartnerHasDirectProfit(Long partner) throws Exception;

	/**
	 * 查询交易商是否是平台商体系中
	 * 
	 * @param platformID
	 * @param dealer
	 * @return
	 */
	public boolean isBePartOfTheBourse(Long platformID, Long dealer);

	/**
	 * 根据交易中心会员号，交易商名称查询交易商会员号
	 * 
	 * @param memberCode
	 * @param name
	 * @return
	 */
	public Long selectMemberInfoDtoByBargainerName(Long memberCode, String name);

	/**
	 * 判断商户是否需要同步通知中带参数，1-true，否则为false
	 * 
	 * @param partner
	 * @return
	 */
	public boolean isPartnerNeedAsyncNoticeParam(Long partner);

	/**
	 * 判断直连情况下商户是否需要通知带参数，1-true，否则为false
	 * 
	 * @param partner
	 * @return
	 */
	public boolean isPartnerNeedAsyncNoticeParam(Long partner, String code);

	/**
	 * 由memberCode和操作员名称查询操作员信息
	 * 
	 * @param memberCode
	 * @param identity
	 * @return MaOperatorDTO
	 */
	public MaOperatorDTO queryOperatorByMemberCode(Long memberCode,
			String identity);

	/**
	 * 由商户的会员code查询是否有防钓鱼接口权限
	 * 
	 * @param partner
	 */
	public boolean isPartnerHasAPService(Long partner);

	/**
	 * 由商户的会员code查询商户网站referer配置信息
	 * 
	 * @param partner
	 */
	public String queryPartnerReferer(Long partner);

	public String queryAPServiceNoticeUrl(Long partner);

}