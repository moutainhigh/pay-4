/**
 *  File: AppOrderService.java
 *  Description:提现订单相关Service接口
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-14   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.withdraw.service.order;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppParaDTO;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryParam;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryResult;
import com.pay.inf.dao.Page;

/**
 * 提现订单相关Service接口
 * @author Sandy_Yang
 */
public interface WithdrawOrderService {
	
	/**
	 * 创建提现订单
	 * @param map
	 * @return 提现订单Id
	 */
	public Long createWithdrawOrderRnTx(WithdrawOrderAppDTO withdrawOrderAppDTO);

	
	/**
	 * 查询用户当日提现次数
	 * @param memberCode
	 * @return 当日提现次数
	 */
	public int dayTimesTotal(Long memberCode);
	
		
	/**
	 * 查询用户当日提现金额
	 * @param memberCode
	 * @return 当日提现金额
	 */
	public Long dayMoneyTotal(Long memberCode);
	
		
	/**
	 * 查询用户当月提现金额
	 * @param memberCode
	 * @return 当月提现金额
	 */
	public Long monthMoneyTotal(Long memberCode);
	
	/**
	 * 查询用户当日提现次数
	 * @param memberCode
	 * @param busiType    业务类型
	 * @return 当日提现次数
	 */
	public int dayTimesTotal(Long memberCode,int busiType);
	/**
	 * 查询用户当日提现次数
	 * @param memberCode
	 * @param busiType    业务类型
	 * @return 当日提现次数
	 */
	public int dayTimesTotal(Long memberCode,int busiType[]);
	

	/**
	 * 查询用户指定业务类型付款金额
	 * @param memberCode  会员号
	 * @param busiType    业务类型
	 * @return 当日指定业务类型付款金额
	 */
	public Long dayMoneyTotal(Long memberCode,int busiType);
	/**
	 * 查询用户指定业务类型付款金额
	 * @param memberCode  会员号
	 * @param busiType    业务类型
	 * @return 当日指定业务类型付款金额
	 */
	public Long dayMoneyTotal(Long memberCode,int busiType[]);
	
	/**
	 * 查询用户当月提现金额
	 * @param memberCode
	 * @param busiType    业务类型
	 * @return 当月提现金额
	 */
	public Long monthMoneyTotal(Long memberCode,int busiType);
	/**
	 * 查询用户当月提现金额
	 * @param memberCode
	 * @param busiType    业务类型
	 * @return 当月提现金额
	 */
	public Long monthMoneyTotal(Long memberCode,int busiType[]);
	
	/**
	 * 查询用户当月付款次数
	 * @param memberCode
	 * @param busiType    业务类型
	 * @return 当月提现金额
	 */
	public Integer monthTimesTotal(Long memberCode,int busiType);
	/**
	 * 查询用户当月付款次数
	 * @param memberCode
	 * @param busiType    业务类型
	 * @return 当月提现金额
	 */
	public Integer monthTimesTotal(Long memberCode,int busiType[]);
	/**
	 * 查询提现订单
	 * @param withdrawOrderAppParaDTO
	 * @param pageNo
	 * @param pageSize
	 */
	public Page<WithdrawOrderAppDTO> queryWithdrawOrder(WithdrawOrderAppParaDTO withdrawOrderAppParaDTO,Integer pageNo,Integer pageSize);
	
	/**
	 * start by jason_li 
	 */
	/**
	 * 根据外系统提供的查询参数返回相对应的查询结果
	 * @param queryParam                  出款订单查询参数
	 * @return withdrawOrderQueryResult   出款订单查询结果参数
	 */
	public WithDrawOrderQueryResult queryWithDrawOrderResult(WithDrawOrderQueryParam queryParam);
	/**
	 * 
	 * @param withDrawOrder
	 * @return
	 */
	public boolean updateWithdrawOrder(WithdrawOrderAppDTO withdrawOrderAppDTO);
	/**
	 * end by jason_li 
	 */
	
	/**
     * 得到特定的订单order
     */
    public  WithdrawOrderAppDTO getWithdrawOrder(Long id);
    
    /**
     * 根据批量订单号获取批量付款到银行明细订单信息
     * @param massOrderSeq
     * @return
     */
    List<WithdrawOrderAppDTO> getWithdrawOrderListByMassOrderSeq(Long massOrderSeq);
    
    /**
     * 查询付款到银行成功金额
     * @param massOrderSeq
     * @return
     */
    Long getMassOutOrderPayAmount(Long massOrderSeq);
    
    /**
     * 查询状态为112但没有生成退款订单
     * @param map
     * @return
     */
    public List<WithdrawOrderAppDTO> getNoFundMentOrderList(Map<String, Object> map);
    
    /**
     * 查询提现列表,包括业务类型(推荐使用此方法)
     * @param withdrawOrderAppParaDTO
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<WithdrawOrderAppDTO> queryWithdrawOrderList(Map<String,Object> map,
			Integer pageNo, Integer pageSize);
    
    /**
     * 通过memberCode和sequenceId 获取提现订单
     * @param map
     * @return
     */
    public WithdrawOrderAppDTO getSingleWithdrawOrder(Map<String,Object> map);
}
