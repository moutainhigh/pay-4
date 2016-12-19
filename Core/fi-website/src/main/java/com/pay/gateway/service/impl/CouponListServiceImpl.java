/**
 * 
 */
package com.pay.gateway.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.gateway.dao.CouponListDAO;
import com.pay.gateway.model.CouponList;
import com.pay.gateway.service.CouponListService;

/**
 * @author chaoyue
 *
 */
public class CouponListServiceImpl implements CouponListService {

	private CouponListDAO couponListDAO;

	public void setCouponListDAO(CouponListDAO couponListDAO) {
		this.couponListDAO = couponListDAO;
	}

	@Override
	public Long createCoupon(CouponList coupon) {
		// TODO Auto-generated method stub
		return (Long) couponListDAO.create(coupon);
	}

	@Override
	public boolean delCoupon(Long id) {
		// TODO Auto-generated method stub
		return couponListDAO.delete(id);
	}

	@Override
	public boolean updateCoupon(CouponList coupon) {
		// TODO Auto-generated method stub
		return couponListDAO.update(coupon);
	}

	@Override
	public List<CouponList> queryCoupon(CouponList coupon) {
		// TODO Auto-generated method stub
		return couponListDAO.findByCriteria(coupon);
	}

	@Override
	public boolean checkCoupon(String orgCode, String couponNumber) {

		Map<String, String> paraMap = new HashMap();
		paraMap.put("orgCode", orgCode);
		paraMap.put("couponNumber", couponNumber);
		List<CouponList> couponList = couponListDAO.findByCriteria(
				"checkCoupon", paraMap);
		return null != couponList && !couponList.isEmpty();
	}

	@Override
	public CouponList findById(Long id) {
		// TODO Auto-generated method stub
		return (CouponList) couponListDAO.findById(id);
	}

	@Override
	public CouponList queryCoupon(String orgCode, String couponNumber) {
		Map<String, String> paraMap = new HashMap();
		paraMap.put("orgCode", orgCode);
		paraMap.put("couponNumber", couponNumber);
		List<CouponList> couponList = couponListDAO.findByCriteria(
				"checkCoupon", paraMap);
		if (null != couponList) {
			return couponList.get(0);
		} else {
			return null;
		}
	}
}
