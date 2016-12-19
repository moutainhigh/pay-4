package com.pay.poss.service.ordercenter.fundout.impl.backfundout;

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
import com.pay.poss.dao.ordercenter.fundout.backfundout.OrderCenterBackFundoutDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * 出款失败
 * <p>
 * </p>
 * 
 * @author Volcano.Wu
 * @since 2010-12-3
 * @see
 */
public class OrderCenterBackFundoutServiceImpl extends AbstractOrderCenterCommonService {

	private static Log logger = LogFactory.getLog(OrderCenterBackFundoutServiceImpl.class);
	private OrderCenterBackFundoutDao orderCenterBackFundoutDao;
	private Map<String, String> busiType2Table = new HashMap<String, String>();
	private Map<String, String> repository = new HashMap<String, String>(30);
	

	public void setOrderCenterBackFundoutDao(OrderCenterBackFundoutDao orderCenterBackFundoutDao) {
		this.orderCenterBackFundoutDao = orderCenterBackFundoutDao;
	}

	public void setBusiType2Table(Map<String, String> busiType2Table) {
		this.busiType2Table = busiType2Table;
		for (String busiType : busiType2Table.keySet()) {
			String sqlId=busiType2Table.get(busiType);
			String[] arryBusiType=busiType.split("[,]");
			for (int i = 0; i < arryBusiType.length; i++) {
				String signalBusiType = arryBusiType[i];
				repository.put(signalBusiType, sqlId);
			}
		}
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {
		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		String busiType = queryDto.getBusiType();
		Long payerMember = Long.valueOf(StringUtils.isEmpty(queryDto.getPayerMemberCode()) ? "0" : queryDto.getPayerMemberCode());
		Integer payerAcctType = Integer.valueOf(StringUtils.isEmpty(queryDto.getPayerAccType()) ? "10" : queryDto.getPayerAccType());

		OrderDetailDTO orderDetailDTO = null;
		MemberBaseInfoBO payerMemberInfo = null;
		AcctAttribDto payerAcctInfo = null;
		try {
			orderDetailDTO = orderCenterBackFundoutDao.queryOrderDetail(orderKy);
			payerMemberInfo = memberQueryService.queryMemberBaseInfoByMemberCode(payerMember);
			payerAcctInfo = accountQueryService.doQueryAcctAttribNsTx(payerMember, payerAcctType);
			orderDetailDTO.setPayerKy(queryDto.getPayerMemberCode()); // 付款人用户号
			orderDetailDTO.setPayerName(payerMemberInfo.getName()); // 付款人用户 姓名
			orderDetailDTO.setPayerLevel(String.valueOf(payerMemberInfo.getServiceLevelCode()));// 付款人用户等级
			orderDetailDTO.setPayerId(payerMemberInfo.getLoginName());// 付款人用户标识
			orderDetailDTO.setPayerAcct(payerAcctInfo.getAcctCode());// 付款人账户
		} catch (MaMemberQueryException e) {
			logger.error("查询付款会员信息错误 [" + payerMember + "]", e);
		} catch (MaAccountQueryUntxException e) {
			logger.error("查询付款账户信息错误 [" + payerMember + "," + payerAcctType + "]", e);
		}

		Long payeeMember = 0L;
		Integer payeeAcctType = 10;
		try {
			if (StringUtils.isNotEmpty(queryDto.getPayeeMemberCode())) {
				payeeMember = Long.valueOf(queryDto.getPayeeMemberCode());
				payeeAcctType = Integer.valueOf(StringUtils.isEmpty(queryDto.getPayerAccType()) ? "10" : queryDto.getPayeeAccType());
				MemberBaseInfoBO payeeMemberInfo = memberQueryService.queryMemberBaseInfoByMemberCode(payeeMember);
				AcctAttribDto payeeAcctInfo = accountQueryService.doQueryAcctAttribNsTx(payeeMember, payeeAcctType);
				orderDetailDTO.setPayeeKy(queryDto.getPayeeMemberCode()); // 收款人用户号
				orderDetailDTO.setPayeeName(payeeMemberInfo.getName()); // 收款人用户
				// 姓名
				orderDetailDTO.setPayeeLevel(String.valueOf(payeeMemberInfo.getServiceLevelCode()));// 收款人用户等级
				orderDetailDTO.setPayeeId(payeeMemberInfo.getLoginName());// 收款人用户标识
				orderDetailDTO.setPayeeAcct(payeeAcctInfo.getAcctCode());// 收款人账户
			}
		} catch (MaMemberQueryException e) {
			logger.error("查询收款款会员信息错误 [" + payeeMember + "]", e);
		} catch (MaAccountQueryUntxException e) {
			logger.error("查询收款账户信息错误 [" + payeeMember + "," + payeeAcctType + "]", e);
		}

		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		String busiType=queryDto.getBusiType();
		String sqlId = repository.get(busiType);
		if(null != sqlId){
			map.put("relationList", orderCenterBackFundoutDao.queryBackFundoutRelationList(sqlId, queryDto));
		}
		
		return map;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String, Page<OrderCenterResultDTO>> map = new HashMap<String, Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterBackFundoutDao.queryBackFundout(page, dto));
		return map;
	}

}
