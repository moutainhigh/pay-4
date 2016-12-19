/**
 *
 */
package com.pay.credit.service.creditorder.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.dao.creditorder.ICreditOrderDao;
import com.pay.credit.dto.creditorder.CreditOrderDTO;
import com.pay.credit.model.creditcurrency.PartnerCreditCurrencyCode;
import com.pay.credit.model.creditorder.CreditOrder;
import com.pay.credit.model.creditorder.CreditOrderDetailParam;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.credit.service.creditorder.ICreditOrderService;
import com.pay.inf.dao.Page;
import com.pay.util.BeanConvertUtil;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public class CreditOrderServiceImpl implements ICreditOrderService {

	private ICreditOrderDao creditOrderDao;

	/* (non-Javadoc)
	 * @see com.pay.credit.service.creditorder.ICreditOrderService#saveCreditOrderDto(com.pay.credit.dto.creditorder.CreditOrderDTO)
	 */
	@Override
	public Long saveCreditOrderDto(final CreditOrderDTO creditOrderDto) {

		final CreditOrder	creditOrder = BeanConvertUtil.convert(CreditOrder.class, creditOrderDto);
		String creditOrderId = (String) creditOrderDao.create(creditOrder);
		return Long.valueOf(creditOrderId) ;
	}

	/* (non-Javadoc)
	 * @see com.pay.credit.service.creditorder.ICreditOrderService#queryCreditOrderList(com.pay.credit.conditon.creditorder.CreditOrderQueryConditon)
	 */
	@Override
	public List<CreditOrderDTO> queryCreditOrderList(
			final CreditOrderQueryConditon queryConditon) {

		final List<CreditOrder> creditOrderList = 	creditOrderDao.queryCreditOrderByConditon(queryConditon);
		return (List<CreditOrderDTO>) BeanConvertUtil.convert(CreditOrderDTO.class, creditOrderList);
	}

	/**
	 * @param creditOrderDao
	 */
	public void setCreditOrderDao(final ICreditOrderDao creditOrderDao) {
		this.creditOrderDao = creditOrderDao;
	}

	@Override
	public List<CreditOrderDTO> queryCreditOrderList(
			final CreditOrderQueryConditon queryConditon, final Page page) {

		final List<CreditOrder> creditOrderList = 	creditOrderDao.queryCreditOrderByConditon(queryConditon ,page);
		return (List<CreditOrderDTO>) BeanConvertUtil.convert(CreditOrderDTO.class, creditOrderList);
	}

	@Override
	public CreditOrder queryCreditOrderById(final String creditOrderId) {
		return  creditOrderDao.queryCreditOrderById(creditOrderId);
	}

	@Override
	public List<CreditOrderDetailed> queryCreditOrderDetailById(
			final String creditOrderId) {
		return creditOrderDao.queryCreditOrderDetailById(creditOrderId);
	}

	@Override
	public void subCreditCheck(final CreditOrderDetailed creditOrder) {
		Timestamp d=new Timestamp(System.currentTimeMillis());
		creditOrder.setApproveTime(d);
		creditOrder.setUpdateTime(d);
		creditOrderDao.updateCreditOrder(creditOrder);
		creditOrderDao.updateCreditOrderDetailed(creditOrder);
	}

	@Override
	public void subCreditCheck(final List<CreditOrderDetailed> list) {
			Timestamp d=new Timestamp(System.currentTimeMillis());
			String status="";
		for (CreditOrderDetailed creditOrderDetailed : list) {
			if(creditOrderDetailed.getStatus().equals("审核通过")){
				creditOrderDetailed.setStatus("A");
				status="A";
			}else if(creditOrderDetailed.getStatus().equals("审核拒绝")){
				creditOrderDetailed.setStatus("R");
			}
			creditOrderDao.updateCreditOrderDetail(creditOrderDetailed);
		}
			CreditOrder creditOrder=new CreditOrder();
			creditOrder.setApproveTime(d);
			creditOrder.setUpdateTime(d);
			if(StringUtils.isNotEmpty(status)){
				creditOrder.setStatus(status);
			}else{
				creditOrder.setStatus("R");
			}
			String creditOrderId="";
			if(list!=null&& !list.isEmpty()){
				creditOrderId = list.get(0).getCreditOrderId();
			}
			creditOrder.setCreditOrderId(creditOrderId);
			creditOrderDao.updateCreditOrders(creditOrder);
	}
	
	@Override
	public boolean saveCreditOrderDetail(final List<CreditOrderDetailParam> list) {
		return this.creditOrderDao.saveCreditOrderDetail(list);
	}

	@Override
	public void saveCreditOrderDetail2(final Map<String, Object> hMap) {
		this.creditOrderDao.saveCreditOrderDetail2(hMap);
		
	}

	@Override
	public List<PartnerCreditCurrencyCode> queryPartnerCreditCurrency(
			final Long memberCode) {
		return this.creditOrderDao.queryPartnerCreditCurrency(memberCode) ;
	}

	@Override
	public void updateCreditOrderPassCount(CreditOrder creditOrder) {
		creditOrderDao.updateCreditOrderPassCount(creditOrder);
	}

}
