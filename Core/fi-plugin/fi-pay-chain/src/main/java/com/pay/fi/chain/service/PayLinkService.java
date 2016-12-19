/**
 * 
 */
package com.pay.fi.chain.service;


import java.sql.Timestamp;
import java.util.List;

import com.pay.fi.chain.condition.paylink.PayLinkCondition;
import com.pay.fi.chain.model.PayLink;
import com.pay.fi.chain.model.PayLinkAttrib;
import com.pay.fi.chain.model.PayLinkBaseInfo;
import com.pay.inf.dao.Page;

/**
 * 支付链服务
 * @author PengJiangbo
 *
 */
public interface PayLinkService {

	/**
	 * 购物条款｜联系方式
	 * @param payLinkBaseInfo
	 * @return
	 */
	Long baseInfoSave(PayLinkBaseInfo payLinkBaseInfo) ;
	/**
	 * 基本信息查询
	 * @return
	 */
	PayLinkBaseInfo payLinkBaseInfoQuery(Long memberCode) ;
	/**
	 * 更新售后联系方式
	 * @param infoId
	 * @param updateDate
	 * @param contact
	 * @return
	 */
	int updateContact(Long infoId, Timestamp updateDate, String contact) ;
	/**
	 * 购物条款
	 * @param payLinkBaseInfo
	 * @return
	 */
	Long shoptermSave(PayLinkBaseInfo payLinkBaseInfo) ;
	/**
	 * 更新购物条款
	 * @param infoId
	 * @param updateTime
	 * @param shoptermName
	 * @param shoptermUrl
	 * @return
	 */
	int updateShopTermById(Long infoId, Timestamp updateTime, String shoptermName, String shoptermUrl) ;
	/**
	 * 支付链生成
	 * @param payLink
	 * @return
	 */
	Long payLinkSave(PayLink payLink) ;
	/**
	 * 支付链商品属性保存
	 * @return
	 */
	Long payLinkAttribSave(PayLinkAttrib payLinkAttrib) ;
	/**
	 * 支付链商品属性批量保存
	 * @param payLinkAttribs
	 * @return
	 */
	boolean payLinkAttribBatchSave(final List<PayLinkAttrib> payLinkAttribs) ;
	/**
	 * 支付链条件查询
	 * @param payLinkCondition
	 * @return
	 */
	List<PayLink> queryPayLinkByCondition(PayLinkCondition payLinkCondition, Page<?> page) ;
	/**
	 * 支付链条件查询,不分页
	 * @param payLinkCondition
	 * @return
	 */
	List<PayLink> queryPayLinkByCondition(PayLinkCondition payLinkCondition) ;
	/**
	 * 更新状态[生效｜失效]
	 * @param payLinkNo
	 * @return
	 */
	int updateStatusByNo(Long payLinkNo, int status, Timestamp invalidTime) ;
	/**
	 * 根据支付链ID查询
	 * @param payLinkNo
	 * @return
	 */
	PayLink findPayLinkByPayLinkNo(Long payLinkNo) ;
	/**
	 * 根据支付链ID查询商品属性
	 * @param payLinkNo
	 * @return
	 */
	List<PayLinkAttrib> findPayLinkAttribsByPayLinkNo(Long payLinkNo) ;
	/**
	 * 根据支付链名称查找
	 * @param payLinkName
	 * @return
	 */
	PayLink findPayLinkByName(String payLinkName) ;
}
