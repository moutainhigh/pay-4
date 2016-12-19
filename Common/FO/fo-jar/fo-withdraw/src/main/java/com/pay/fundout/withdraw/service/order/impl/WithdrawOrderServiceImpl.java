/**
 *  File: WithdrawOrderServiceImpl.java
 *  Description:提现订单相关Service实现
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.withdraw.service.order.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.dao.order.WithdrawOrderDao;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppParaDTO;
import com.pay.fundout.withdraw.dto.withdraworder.WithDrawOrderQueryDTO;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryParam;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryResult;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.ma.member.WithdrawMemberFacadeService;

/**
 * 提现订单相关Service实现
 * @author Sandy_Yang
 */
public class WithdrawOrderServiceImpl implements WithdrawOrderService {
	
	private WithdrawOrderDao withdrawOrderDao;
	private WithdrawMemberFacadeService withdrawMemberService;
	
	public void setWithdrawMemberService(
			WithdrawMemberFacadeService withdrawMemberService) {
		this.withdrawMemberService = withdrawMemberService;
	}

	@Override
	public Long createWithdrawOrderRnTx(WithdrawOrderAppDTO withdrawOrderAppDTO) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		//目的银行编号
		map.put("targetBankId", withdrawOrderAppDTO.getBankKy());
		//出款方式
		map.put("foMode", "1");//暂时写死为1手工,以后直连接入在进行修改
		//出款业务
		map.put("fobusiness", String.valueOf(withdrawOrderAppDTO.getBusiType()));//业务类型
		
		String outBankCode = configBankService.queryFundOutBank2Withdraw(map);

		withdrawOrderAppDTO.setWithdrawBankCode(outBankCode);		
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		BeanUtils.copyProperties(withdrawOrderAppDTO, withdrawOrder);
		
		/**
		 *	added by jonathen ni 2011-01-04
		 *	According to the ouyang's require,
		 *	insert memberName field to the withdrawOrder object.
		 */
		MemberInfoDto member = loadMemberInfo(withdrawOrder.getMemberCode());
		String memberName = "";
		if(member!=null){
			memberName = member.getMemberName()==null?"":member.getMemberName();
		}
		
		withdrawOrder.setMemberName(memberName);
		Long withdrawId = withdrawOrderDao.createWithdrawOrder(withdrawOrder);
		if (withdrawId != null) {
			return withdrawId;
		}else {
			return null;
		}
	}
	
	private MemberInfoDto loadMemberInfo(Long memberCode){
		try{
			if(memberCode!=null && !memberCode.equals("")){
				return this.withdrawMemberService.qyeryMember(memberCode);
			}
		}catch(Exception e){
			LogUtil.error(WithdrawOrderServiceImpl.class,"装载会员信息异常",OPSTATUS.FAIL,
					"","[memberCode=" + memberCode ,e.getMessage(),"",e.getMessage());
		}
		return null;
	}

	@Override
	public Long dayMoneyTotal(Long memberCode) {
		return withdrawOrderDao.dayMoneyTotal(memberCode,WithdrawOrderBusiType.WITHDRAW.getCode());
	}

	@Override
	public int dayTimesTotal(Long memberCode) {
		return withdrawOrderDao.dayTimesTotal(memberCode,WithdrawOrderBusiType.WITHDRAW.getCode());
	}

	@Override
	public Long monthMoneyTotal(Long memberCode) {
		return withdrawOrderDao.monthMoneyTotal(memberCode,WithdrawOrderBusiType.WITHDRAW.getCode());
	}

	@Override
	public Page<WithdrawOrderAppDTO> queryWithdrawOrder(WithdrawOrderAppParaDTO withdrawOrderAppParaDTO,
			Integer pageNo, Integer pageSize) {
		return withdrawOrderDao.queryWithdrawOrder(withdrawOrderAppParaDTO, pageNo, pageSize);
	}
	
	public void setWithdrawOrderDao(WithdrawOrderDao withdrawOrderDao) {
		this.withdrawOrderDao = withdrawOrderDao;
	}

	private ConfigBankService configBankService;
	
	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	/**
	 * start by jason_li
	 */
	@Override
	public WithDrawOrderQueryResult queryWithDrawOrderResult(
			WithDrawOrderQueryParam queryParam) {
		WithDrawOrderQueryDTO queryDto = new WithDrawOrderQueryDTO();
		BeanUtils.copyProperties(queryParam, queryDto);
		return withdrawOrderDao.queryWithDrawOrderResult(queryDto);
	}

	@Override
	public boolean updateWithdrawOrder(WithdrawOrderAppDTO withdrawOrderAppDTO) {
		if(withdrawOrderAppDTO==null){
			return false;
		}
		WithdrawOrder order = new WithdrawOrder();
		BeanUtils.copyProperties(withdrawOrderAppDTO, order);
		return withdrawOrderDao.updateWithDrawOrder(order);
	}
	/**
	 * end by jason_li
	 */
	@Override
	public WithdrawOrderAppDTO getWithdrawOrder(Long id) {
		return withdrawOrderDao.getWithdrawOrder(id);
	}

	@Override
	public Long dayMoneyTotal(Long memberCode, int busiType) {
		return withdrawOrderDao.dayMoneyTotal(memberCode,busiType);
	}

	@Override
	public List<WithdrawOrderAppDTO> getWithdrawOrderListByMassOrderSeq(
			Long massOrderSeq) {
		return withdrawOrderDao.getWithdrawOrderListByMassOrderSeq(massOrderSeq);
	}

	@Override
	public int dayTimesTotal(Long memberCode, int busiType) {
		return withdrawOrderDao.dayTimesTotal(memberCode, busiType);
	}

	@Override
	public Long monthMoneyTotal(Long memberCode, int busiType) {
		return withdrawOrderDao.monthMoneyTotal(memberCode, busiType);	
	}

	@Override
	public Integer monthTimesTotal(Long memberCode, int busiType) {
		return withdrawOrderDao.monthTimesTotal(memberCode, busiType);	
	}

	/**
     * 查询付款到银行成功金额
     * @param massOrderSeq
     * @return
     */
	@Override
	public Long getMassOutOrderPayAmount(Long massOrderSeq) {
		return withdrawOrderDao.getMassOutOrderPayAmount(massOrderSeq);
	}
	@Override
	public Long dayMoneyTotal(Long memberCode, int[] busiType) {
		return withdrawOrderDao.dayMoneyTotal(memberCode, busiType);
	}

	@Override
	public int dayTimesTotal(Long memberCode, int[] busiType) {
		return withdrawOrderDao.dayTimesTotal(memberCode, busiType);
	}

	@Override
	public Long monthMoneyTotal(Long memberCode, int[] busiType) {
		return withdrawOrderDao.monthMoneyTotal(memberCode, busiType);
	}

	@Override
	public Integer monthTimesTotal(Long memberCode, int[] busiType) {
		return withdrawOrderDao.monthTimesTotal(memberCode, busiType);
	}

	/**
	* 查询状态为112但没有生成退款订单
	* @param map
	* @return
	*/
	@Override
	public List<WithdrawOrderAppDTO> getNoFundMentOrderList(
			Map<String, Object> map) {
		return withdrawOrderDao.getNoFundMentOrderList(map);
	}

	@Override
	public Page<WithdrawOrderAppDTO> queryWithdrawOrderList(
			Map<String,Object> map, Integer pageNo,
			Integer pageSize) {
		return withdrawOrderDao.queryWithdrawOrderList(map, pageNo, pageSize);
	}

	@Override
	public WithdrawOrderAppDTO getSingleWithdrawOrder(Map<String, Object> map) {
		return withdrawOrderDao.getSingleWithdrawOrder(map);
	}



}
