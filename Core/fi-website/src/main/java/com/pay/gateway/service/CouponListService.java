/**
 * 
 */
package com.pay.gateway.service;

import java.util.List;

import com.pay.gateway.model.CouponList;

/**
 * @author chaoyue
 *
 */
public interface CouponListService {

	/**
	 * 
	 * @param coupon
	 * @return
	 */
	Long createCoupon(CouponList coupon);

	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean delCoupon(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	CouponList findById(Long id);

	/**
	 * 
	 * @param coupon
	 * @return
	 */
	boolean updateCoupon(CouponList coupon);

	/**
	 * 
	 * @return
	 */
	List<CouponList> queryCoupon(CouponList coupon);

	/**
	 * 
	 * @param orgCode
	 * @param couponNumber
	 * @return
	 */
	CouponList queryCoupon(String orgCode, String couponNumber);

	/**
	 * 判断优惠券有效
	 * 
	 * @param couponNumber
	 * @return
	 */
	boolean checkCoupon(final String orgCode, final String couponNumber);
}
