/**
 * 
 */
package com.pay.ma.chargeup.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.ma.chargeup.dao.BalanceDealDAO;
import com.pay.ma.chargeup.dto.BalanceDealDto;
import com.pay.ma.chargeup.model.BalanceDeal;
import com.pay.ma.chargeup.service.BalanceDealService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */
public class BalanceDealServiceImpl implements BalanceDealService {

	private BalanceDealDAO balanceDealDAO;

	@Override
	public boolean callUpdateBalanceDealStatus(String serialNo,
			Integer dealCode, Integer chargeUpStatus) {
		return this.balanceDealDAO.updateChargeUpStatus(serialNo, dealCode,
				chargeUpStatus);

	}

	@Override
	public List<BalanceDealDto> callQueryBalanceChargeup(Integer status) {
		// NO_CHARGEUP(0, "未记账"), SUCCESS(1, "记账成功"), FAIL(2, "记账失败"); 这里取成功的反集
		// ，
		List<BalanceDeal> balanceDeals = this.balanceDealDAO
				.queryChargeUpInfo(status);
		List<BalanceDealDto> list = new ArrayList<BalanceDealDto>();
		for (BalanceDeal balanceDeal : balanceDeals) {
			list.add(BeanConvertUtil.convert(BalanceDealDto.class, balanceDeal));
		}
		return list;
	}

	/**
	 * @param balanceDealDAO
	 *            the balanceDealDAO to set
	 */
	public void setBalanceDealDAO(BalanceDealDAO balanceDealDAO) {
		this.balanceDealDAO = balanceDealDAO;
	}

	@Override
	public List<BalanceDealDto> callQueryBalanceDealInfo(String serialNo,
			Integer dealCode, Long amount) {
		return (List<BalanceDealDto>) BeanConvertUtil.convert(BalanceDealDto.class,
				this.balanceDealDAO.queryBalanceDealInfo(serialNo, dealCode,
						amount));
	}

	@Override
	public List<BalanceDealDto> callQueryBalanceDealInfoByVo(Long voucherCode) {
		return (List<BalanceDealDto>) BeanConvertUtil.convert(
				BalanceDealDto.class,
				this.balanceDealDAO.queryBalanceDealInfoByVo(voucherCode));
	}

}
