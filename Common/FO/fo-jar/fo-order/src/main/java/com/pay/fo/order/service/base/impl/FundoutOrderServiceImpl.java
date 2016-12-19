/**
 * 
 */
package com.pay.fo.order.service.base.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.EntryPriseRiskControlService;
import com.pay.acc.service.account.constantenum.AcctAttribEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.fo.order.dao.base.FundoutOrderDAO;
import com.pay.fo.order.dao.batchpayment.BatchPayToBankQueryDetailDAO;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.base.FundoutOrderExample;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.inf.dao.Page;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class FundoutOrderServiceImpl implements FundoutOrderService {
	
	private FundoutOrderDAO fundoutOrderDAO;
	private BatchPayToBankQueryDetailDAO batchPayToBankQueryDetailDAO;
	private EntryPriseRiskControlService entryPriseRiskControlService;
	private MemberQueryService memberQueryService;
	private AccountQueryService accountQueryService;

	public void setBatchPayToBankQueryDetailDAO(BatchPayToBankQueryDetailDAO batchPayToBankQueryDetailDAO) {
		this.batchPayToBankQueryDetailDAO = batchPayToBankQueryDetailDAO;
	}

	/**
	 * @param entryPriseRiskControlService the entryPriseRiskControlService to set
	 */
	public void setEntryPriseRiskControlService(
			EntryPriseRiskControlService entryPriseRiskControlService) {
		this.entryPriseRiskControlService = entryPriseRiskControlService;
	}

	/**
	 * @param memberQueryService the memberQueryService to set
	 */
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	/**
	 * @param fundoutOrderDAO the fundoutOrderDAO to set
	 */
	public void setFundoutOrderDAO(FundoutOrderDAO fundoutOrderDAO) {
		this.fundoutOrderDAO = fundoutOrderDAO;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.OrderService#createOrder(com.pay.fo.order.dto.Order)
	 */
	@Override
	public Long createOrder(Order order) {
		
		if(order instanceof FundoutOrderDTO){
			FundoutOrderDTO dto = (FundoutOrderDTO)order;
			FundoutOrder model = new FundoutOrder();
			BeanUtils.copyProperties(dto, model);
			String riskLeveCode = String.valueOf(memberQueryService.queryEnterpriseRiskLeveCode(dto.getPayerMemberCode()));
			String accountMode = String.valueOf(entryPriseRiskControlService.queryEnterPriseSettlePeriod(dto.getPayerMemberCode()));
			Integer isLoaning = 0;
			if (accountQueryService.countIsAllowAcctribByMemberCode(dto.getPayerMemberCode(), AcctAttribEnum.ALLOW_ADVANCE_MONEY)) {
				isLoaning = 1;
			}
			model.setIsLoaning(isLoaning);
			if(!StringUtil.isEmpty(riskLeveCode)){
				model.setRiskLeveCode(riskLeveCode);
			}
			if(!StringUtil.isEmpty(accountMode)){
				model.setAccountMode(accountMode);
			}
			return fundoutOrderDAO.insert(model);
		}
		return null;
	}

	@Override
	public boolean updateOrder(Order upOrder) {
		if(upOrder instanceof FundoutOrderDTO){
			FundoutOrderDTO dto = (FundoutOrderDTO)upOrder;
			FundoutOrder model = new FundoutOrder();
			BeanUtils.copyProperties(dto, model);
			
			return fundoutOrderDAO.updateByPrimaryKeySelective(model)==1?true:false;
			
		}
		return false;
	}
	
	@Override
	public boolean updateOrderStatus(Order upOrder, int oldStatus) {
		
		if(upOrder instanceof FundoutOrderDTO){
			FundoutOrderDTO dto = (FundoutOrderDTO)upOrder;
			FundoutOrder model = new FundoutOrder();
			model.setOrderStatus(dto.getOrderStatus());
			model.setUpdateDate(dto.getUpdateDate());
			model.setFailedReason(dto.getFailedReason());
			
			FundoutOrderExample example = new FundoutOrderExample();
			  example.createCriteria()
			  .andOrderIdEqualTo(dto.getOrderId())
			  .andOrderStatusEqualTo(oldStatus);

			return fundoutOrderDAO.updateByExampleSelective(model, example)==1?true:false;
			
		}
		  
		return false;
	}


	@Override
	public Order getOrder(Long orderId) {
		 FundoutOrder model = fundoutOrderDAO.selectByPrimaryKey(orderId);
		 if(model!=null){
			 FundoutOrderDTO dto = new FundoutOrderDTO();
			 BeanUtils.copyProperties(model, dto);
			 return dto;
		 }
		return null;
	}

	@Override
	public Long sumCurrentDayPaymentAmount(Integer orderType,
			Long payerMemberCode) {
		FundoutOrder record = new FundoutOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		
		return fundoutOrderDAO.sumCurrentDayPaymentAmount(record);
	}

	@Override
	public Long sumCurrentMonthPaymentAmount(Integer orderType,
			Long payerMemberCode) {
		FundoutOrder record = new FundoutOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		
		return fundoutOrderDAO.sumCurrentMonthPaymentAmount(record);
	}

	@Override
	public Integer countCurrentDayPaymentTimes(Integer orderType,
			Long payerMemberCode) {
		FundoutOrder record = new FundoutOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		return fundoutOrderDAO.countCurrentDayPaymentTimes(record);
	}

	@Override
	public Integer countCurrentMonthPaymentTimes(Integer orderType,
			Long payerMemberCode) {
		FundoutOrder record = new FundoutOrder();
		record.setPayerMemberCode(payerMemberCode);
		record.setOrderType(orderType);
		return fundoutOrderDAO.countCurrentMonthPaymentTimes(record);
	}

	@Override
	public List<FundoutOrderDTO> getChildFundoutOrderList(Long parentOrderId,Long payerMemberCode) {
		List<FundoutOrderDTO> results = new ArrayList<FundoutOrderDTO>();
		FundoutOrderDTO dto = null;
		FundoutOrderExample example = new FundoutOrderExample();
		  example.createCriteria()
		  .andParentOrderIdEqualTo(parentOrderId)
		  .andPayerMemberCodeEqualTo(payerMemberCode);
		List models = fundoutOrderDAO.selectByExample(example);
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			FundoutOrder model = (FundoutOrder)iterator.next();
			if(model!=null){
				dto = new FundoutOrderDTO();
				BeanUtils.copyProperties(model, dto);
				results.add(dto);
			}
		}
		return results;
	}

	public Page<FundoutOrderDTO> queryPayToBankBatchDetail(Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return batchPayToBankQueryDetailDAO.queryPayToBankBatchDetail(map, pageNo, pageSize);
	}

	public Page<OrderInfoDTO> queryPayToAcctBatchDetail(Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return batchPayToBankQueryDetailDAO.queryPayToAcctBatchDetail(map, pageNo, pageSize);
	}
	
	/**
	 * @param accountQueryService the accountQueryService to set
	 */
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

}
