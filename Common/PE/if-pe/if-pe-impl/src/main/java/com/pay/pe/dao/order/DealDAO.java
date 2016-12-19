/**
 * Created on Wed Dec 06 18:09:11 CST 2006.
 */
package com.pay.pe.dao.order;

import java.util.List;

import com.pay.pe.helper.DealStatus;
import com.pay.pe.model.Deal;
import com.pay.pe.model.PaymentOrder;

/**
 * @Company: 
 * g
 * @version 1.0
 */
public interface DealDAO  {
    /**
     * 按PaymentOrder表中的sequence id查找对应的Deal
     * @param orderSeqId
     * @return
     */
    List<Deal> findByOrderSeqId(PaymentOrder order);
    
    /**根据DEAL的OrgOrderId查询DEAL.
	 * @param OrgOrderId
	 * @return
	 */
	List<Deal> findDealByOrgOrderId(final String orgOrderId);
    
    /**
     * 得到一个交易进行记帐的标识.
     * 如果返回一个大于0的值，则表示可以对此交易号对应的交易进行记帐.
     * 否则不能记帐.
     * @param dealId String
     * @return long
     */
    long getDealAccountingToken(String dealId);
    
    /**
     * 查询在一段时间内某一账户的某一订单类型的指定的交易状态的交易额.
     * @param dealStatus
     * 如果为空则查询在一段时间内某一账户的某一订单类型的交易总额,包括成功失败创建的所有交易.
     * @param orderCode int
     * 如果为空则查询在一段时间内某一账户全部的指定的交易状态的交易额.
     * @param memberAcctCode String
     * 如果会员账户为空则查询在一段时间内某一订单类型全部的指定的交易状态的交易额.
     * @param from  Date
     * @param end Date
     * 如果为空则默认为到当前系统时间.
     * @return long
     */
//    long countDealAmount(Integer dealStatus, Integer orderCode, String memberAcctCode, Date from, Date end);
    
    /**
     * voucherNO 记账凭证
     */
    public Deal getDealByVoucherNo(final Long voucherNo);
    /**
	 * 以悲观锁的形式获得deal
	 * @param dealId
	 * @return
	 */
	Deal getDealAndLocked(final String dealId);
	
    /**
	 * 以悲观锁的形式获得deal
	 * @param dealId
	 * @param status
	 * @return
	 */
	Deal getDealAndLocked(final String dealId, final DealStatus status);
	/**
	 * 以悲观锁的形式获得deal
	 * @param dealId
	 * @param waitSeconds 等待时间
	 * @return
	 */
	Deal getDealAndLocked(final String dealId,long waitSeconds);
	
	/**
	 * 以悲观锁的形式获得deal
	 * @param dealId
	 * @param waitSeconds 等待时间
	 * @param status 状态条件
	 * @return
	 */
	Deal getDealAndLocked(final String dealId,long waitSeconds,final DealStatus status);
}
