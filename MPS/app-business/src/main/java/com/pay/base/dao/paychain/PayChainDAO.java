package com.pay.base.dao.paychain;

import java.util.Date;

import com.pay.base.dto.PayChainDetailDto;
import com.pay.base.dto.PayChainPaymentParamDto;
import com.pay.base.dto.PayChainStatDto;
import com.pay.base.dto.PayChainStatParamDto;
import com.pay.base.model.PayChain;

public interface PayChainDAO {

public Long create(PayChain payChain);
	
	public Long generateChainNum();
	
  	public Date getOverDate(int day);
  	
  	public PayChain getPayChin(String payChainNumber);
  	
	/**
	 * 查询统计支付链的结果列表
	 * @author DDR
	 * @param payChainStatParamDto 参数如果没有可以不设置
	 * @return List<PayChainStatRecordDto> 
	 */
	public PayChainStatDto queryPayChainRecords(
			PayChainStatParamDto payChainStatParamDto);
	
	/**
	 * 统计当前条件的总记录数和总金额
	 * @author DDR
	 * @param payChainStatParamDto
	 * @return PayChainStatDto
	 * @author DDR
	 */
	public PayChainStatDto  queryPayChainCountSum(PayChainStatParamDto payChainStatParamDto);
	
	/**
	 * 查询支付链总记录数
	 * @param payChainStatParamDto
	 * @return 记录条数
	 * @author DDR 
	 */
	public int queryPayChainRecordsCount(PayChainStatParamDto payChainStatParamDto);
	
	/**
	 * 通过payChainNum得到支付成功的记录数
	 * @param payChainNum 支付链的号  必须
	 * @return  返回记录数
	 * @author DDR
	 */
	public int queryPayChainPaymentCount(String  payChainNum);
	/**
	 * 通过payChainNum得到支付成功的记录数，分页查询所以需要设计 分页的参数
	 * @param payChainPaymentParamDto 分页查询的条件，都是必须的参数
	 * @return 返回记录结果集
	 * @author DDR
	 */
	public PayChainDetailDto queryPayChainDetail(PayChainPaymentParamDto payChainPaymentParamDto);
	
	
	
  	
	public String getMaxChainNum();
	
	/**
	 * 根据payChainNumber更新状态 
	 * @param payChainNumber
	 * @param status 可以是1和2的这些值
	 * @return 更新的个数 
	 * @author DDR
	 */
	public int updatePayChainStatus(String payChainNumber,int status);
	
	
	/**
	 * 修改支付链接有效时长
	 * @param payNum 
	 * @param effectiveType
	 * @return true
	 * @author DDR
	 */
	public int updateEffDate(String payNum, int effectiveType);
}
