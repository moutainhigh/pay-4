/**
 *  File: WithdrawOrderDaoImpl.java
 *  Description:提现订单DAO实现
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.withdraw.dao.order.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.order.WithdrawOrderDao;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppParaDTO;
import com.pay.fundout.withdraw.dto.withdraworder.WithDrawOrderQueryDTO;
import com.pay.fundout.withdraw.model.order.WithdrawOrder;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryResult;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 提现订单DAO实现
 * 
 * @author Sandy_Yang
 */
public class WithdrawOrderDaoImpl extends BaseDAOImpl implements
		WithdrawOrderDao {

	/** 分页参数-size **/
	@SuppressWarnings("unused")
	private int pageSize;

	@Override
	public Long createWithdrawOrder(WithdrawOrder withdrawOrder) {
		withdrawOrder.setWithdrawType((short) 1);
		return (Long) super.create(withdrawOrder);
	}

	@Override
	public int dayTimesTotal(Long memberCode, int busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiType(new Long(busiType));
		Object times = getSqlMapClientTemplate().queryForObject(
				namespace.concat("dayTimesTotal"), withdrawOrder);
		if (times == null) {
			return 0;
		} else {
			return (Integer) times;
		}
	}

	@Override
	public Long monthMoneyTotal(Long memberCode, int busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiType(new Long(busiType));
		Long monthMoneyTotal = (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("monthMoneyTotal"), withdrawOrder);
		if (monthMoneyTotal == null) {
			return 0L;
		} else {
			return monthMoneyTotal;
		}
	}

	@Override
	public Page<WithdrawOrderAppDTO> queryWithdrawOrder(
			WithdrawOrderAppParaDTO withdrawOrderAppParaDTO, Integer pageNo,
			Integer pageSize) {
		Page<WithdrawOrderAppDTO> page = new Page<WithdrawOrderAppDTO>(pageSize);
		page.setPageNo(pageNo);
		return (Page<WithdrawOrderAppDTO>) super.findByQuery("queryWithdrawOrder", page,
				withdrawOrderAppParaDTO);
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * START BY JASON_LI
	 */
	@Override
	public WithDrawOrderQueryResult queryWithDrawOrderResult(
			WithDrawOrderQueryDTO queryDto) {
		return (WithDrawOrderQueryResult) super.findObjectByCriteria("appQueryApi", queryDto);
	}

	@Override
	public boolean updateWithDrawOrder(WithdrawOrder withdrawOrder) {

		return getSqlMapClientTemplate().update(namespace.concat("update"),
				withdrawOrder) == 1;

	}

	/**
	 * END BY JASON_LI
	 */

	@Override
	public WithdrawOrderAppDTO getWithdrawOrder(Long id) {
		WithdrawOrderAppDTO withdrawOrderAppDTO = (WithdrawOrderAppDTO) super
				.findById(id);
		return withdrawOrderAppDTO;
	}

	@Override
	public Long dayMoneyTotal(Long memberCode, int busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiType(new Long(busiType));
		Long dayMoneyTotal = (Long) super.findObjectByTemplate("dayMoneyTotal",
				withdrawOrder);
		if (dayMoneyTotal == null) {
			return 0L;
		} else {
			return dayMoneyTotal;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WithdrawOrderAppDTO> getWithdrawOrderListByMassOrderSeq(
			Long massOrderSeq) {
		return getSqlMapClientTemplate().queryForList(
				this.namespace.concat("findByMassOrderSeq"), massOrderSeq);
	}

	@Override
	public Integer monthTimesTotal(Long memberCode, int busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiType(new Long(busiType));
		Object times = getSqlMapClientTemplate().queryForObject(
				namespace.concat("monthTimesTotal"), withdrawOrder);
		if (times == null) {
			return 0;
		} else {
			return (Integer) times;
		}
	}

	/**
	 * 查询付款到银行成功金额
	 * 
	 * @param massOrderSeq
	 * @return
	 */
	@Override
	public Long getMassOutOrderPayAmount(Long massOrderSeq) {
		Object amount = getSqlMapClientTemplate()
				.queryForObject(
						this.namespace.concat("getMassOutOrderPayAmount"),
						massOrderSeq);
		if (amount == null) {
			return 0L;
		}
		return (Long) amount;
	}

	@Override
	public Long dayMoneyTotal(Long memberCode, int[] busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiTypes(busiType);
		Long dayMoneyTotal = (Long) super.findObjectByTemplate(
				"dayMoneyTotals", withdrawOrder);
		if (dayMoneyTotal == null) {
			return 0L;
		} else {
			return dayMoneyTotal;
		}
	}

	@Override
	public int dayTimesTotal(Long memberCode, int[] busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiTypes(busiType);
		Object times = getSqlMapClientTemplate().queryForObject(
				namespace.concat("dayTimesTotals"), withdrawOrder);
		if (times == null) {
			return 0;
		} else {
			return (Integer) times;
		}
	}

	@Override
	public Long monthMoneyTotal(Long memberCode, int[] busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiTypes(busiType);
		Long monthMoneyTotal = (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("monthMoneyTotals"), withdrawOrder);
		if (monthMoneyTotal == null) {
			return 0L;
		} else {
			return monthMoneyTotal;
		}
	}

	@Override
	public Integer monthTimesTotal(Long memberCode, int[] busiType) {
		WithdrawOrder withdrawOrder = new WithdrawOrder();
		withdrawOrder.setMemberCode(memberCode);
		withdrawOrder.setBusiTypes(busiType);
		Object times = getSqlMapClientTemplate().queryForObject(
				namespace.concat("monthTimesTotals"), withdrawOrder);
		if (times == null) {
			return 0;
		} else {
			return (Integer) times;
		}
	}

	/**
	 * 查询所有状态没112 但没有退款订单的数据
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawOrderAppDTO> getNoFundMentOrderList(
			Map<String, Object> map) {
		return getSqlMapClientTemplate().queryForList(
				this.namespace.concat("findNoFundMentOrderList"), map);
	}

	@Override
	public Page<WithdrawOrderAppDTO> queryWithdrawOrderList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Page<WithdrawOrderAppDTO> page = new Page<WithdrawOrderAppDTO>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryWithdrawOrderList",
				page, map);
	}

	@Override
	public WithdrawOrderAppDTO getSingleWithdrawOrder(Map<String, Object> map) {
		return (WithdrawOrderAppDTO) super.findObjectByCriteria("getSingleWithdrawOrder", map);
	}

}
