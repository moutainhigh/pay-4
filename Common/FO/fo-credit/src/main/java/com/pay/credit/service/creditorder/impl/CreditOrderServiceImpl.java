/**
 *
 */
package com.pay.credit.service.creditorder.impl;

import java.util.List;

import com.pay.credit.conditon.creditorder.CreditOrderQueryConditon;
import com.pay.credit.dao.creditorder.ICreditOrderDao;
import com.pay.credit.dto.creditorder.CreditOrderDTO;
import com.pay.credit.model.creditorder.CreditOrder;
import com.pay.credit.service.creditorder.ICreditOrderService;
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
	public void saveCreditOrderDto(final CreditOrderDTO creditOrderDto) {

		final CreditOrder	creditOrder = BeanConvertUtil.convert(CreditOrder.class, creditOrderDto);
		creditOrderDao.create(creditOrder);
	}

	/* (non-Javadoc)
	 * @see com.pay.credit.service.creditorder.ICreditOrderService#queryCreditOrderList(com.pay.credit.conditon.creditorder.CreditOrderQueryConditon)
	 */
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

}
