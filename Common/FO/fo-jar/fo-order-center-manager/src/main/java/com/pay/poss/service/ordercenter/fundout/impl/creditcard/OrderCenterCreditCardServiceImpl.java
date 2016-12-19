package com.pay.poss.service.ordercenter.fundout.impl.creditcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.creditcard.OrderCenterCreditCardDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.fundout.impl.withdraw.OrderCenterWithdrawServiceImpl;

/**
 * 信用卡还款
 * <p></p>
 * @author Volcano.Wu
 * @since 2010-11-26
 * @see
 */
public class OrderCenterCreditCardServiceImpl extends AbstractOrderCenterCommonService{

	private static Log logger = LogFactory.getLog(OrderCenterWithdrawServiceImpl.class);
	private  OrderCenterCreditCardDao orderCenterCreditCardDao;
	
	public void setOrderCenterCreditCardDao(
			OrderCenterCreditCardDao orderCenterCreditCardDao) {
		this.orderCenterCreditCardDao = orderCenterCreditCardDao;
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {
		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		Long memberCode = Long.valueOf(StringUtils.isEmpty(queryDto.getPayerMemberCode()) ? "0" : queryDto.getPayerMemberCode());
		Integer accountType = Integer.valueOf(StringUtils.isEmpty(queryDto.getPayerAccType()) ? "10" : queryDto.getPayerAccType());
		OrderDetailDTO orderDetailDTO = null;
		MemberBaseInfoBO memberBaseInfoBO = null;
		AcctAttribDto acctAttribDto = null;
		try {
			orderDetailDTO = orderCenterCreditCardDao.queryCreditCardDetail(orderKy);
			memberBaseInfoBO =  memberQueryService.queryMemberBaseInfoByMemberCode(memberCode);
			acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(memberCode,accountType);
			
			orderDetailDTO.setPayerKy(queryDto.getPayerMemberCode()); //付款人用户号
			orderDetailDTO.setPayerName(memberBaseInfoBO.getName()); //付款人用户 姓名
			orderDetailDTO.setPayerLevel(String.valueOf(memberBaseInfoBO.getServiceLevelCode()));//付款人用户等级
			orderDetailDTO.setPayerId(memberBaseInfoBO.getLoginName());//付款人用户标识
			orderDetailDTO.setPayerAcct(acctAttribDto.getAcctCode());//付款人账户
			
		} catch (MaMemberQueryException e) {
			logger.error(e.getMessage(), e);
		}catch (MaAccountQueryUntxException e) {
			logger.error(e.getMessage(), e);
		}
		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("relationList", orderCenterCreditCardDao.queryCreditCardList(queryDto));
		return map;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String, Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterCreditCardDao.queryCreditCard(page, dto));
		return map;
	}

}
