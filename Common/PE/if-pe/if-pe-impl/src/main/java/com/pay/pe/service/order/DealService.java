package com.pay.pe.service.order;

import java.util.List;

import com.pay.inf.dto.MutableDto;
import com.pay.inf.service.BaseService;
import com.pay.pe.dto.DealDto;
import com.pay.pe.helper.DealStatus;
import com.pay.pe.service.CalFeeRequest;

/**
 * 
 */
public interface DealService extends BaseService {
	/**
	 * 根据交易信息计算出交易的费用 <li>根据交易取得其对应的计费支付服务 <li>如果计费支付服务不为空，则根据交易和支付服务信息构造计费参数
	 * <li>根据计费参数进行计费 <li>根据计费支付服务的作用方，把费用设置交易的付款方或者收款方
	 * 
	 * @param deal
	 *            交易
	 * @return 计价后的交易
	 * @throws Exception
	 */
	// DealDto caculatePrice(final DealDto deal) throws Exception;

	/**
	 * 按订单号查找该订单下所有交易
	 * 
	 * @param orderSeqId
	 *            订单号
	 * @return 交易列表
	 */
	List<DealDto> findByOrderSeqId(Long orderSeqId);

	/**
	 * 根据传过来的参数，产生DealDto对象
	 * 
	 * @param calFeeRequest
	 * @return
	 */
	DealDto generateDealDto(CalFeeRequest calFeeRequest);

	/**
	 * 根据交易号查找交易. <li>跟调用此方法的方法在同一个事务内
	 * 
	 * @param dealId
	 *            交易号
	 * @return 交易
	 */
	DealDto findDealInSameTx(String dealId);

	/**
	 * 保存交易. <li>此方法在独立的事务内执行
	 * 
	 * @param deal
	 *            要保存的交易
	 * @return DealDto 保存成功的交易
	 */
	DealDto saveDealInIsolatedTx(DealDto deal) throws Exception;

	/**
	 * 保存交易.
	 * 
	 * @param deal
	 *            要保存的交易
	 * @return Deal 保存成功的交易
	 */
	DealDto create(DealDto deal);

	/**
	 * 保存交易.
	 * 
	 * @param deal
	 *            要保存的交易
	 * @return 保存成功的交易
	 */
	DealDto modify(DealDto deal);

	/**
	 * 根据交易号查询交易状态.
	 * 
	 * @param dealId
	 *            交易
	 * @return 状态
	 */
	int getDealStatus(String dealId);

	/**
	 * 根据交易号得到该交易的同步令牌. <li>如果返回一个大于0的值，则表示可以对此交易号对应的交易进行记帐.否则不能记帐.
	 * 
	 * @param dealId
	 *            交易号
	 * @return long 令牌号
	 */
	long generateDealAccountingToken(String dealId);

	/**
	 * 根据凭证号查询deal
	 * 
	 * @param voucherNO
	 * @return
	 */
	DealDto getDealByVoucherNo(Long voucherNO);

	/**
	 * 根据交易号以悲观锁的形式获得交易
	 * 
	 * @param dealId
	 *            交易号
	 * @return 获得的交易
	 */
	DealDto getDealAndLocked(String dealId);

	/**
	 * 根据交易号以悲观锁的形式获得交易
	 * 
	 * @param dealId
	 *            交易号
	 * @param status
	 *            状态
	 * @return 获得的交易
	 */
	DealDto getDealAndLocked(String dealId, DealStatus status);

	/**
	 * 根据交易号以悲观锁的形式获得交易
	 * 
	 * @param dealId
	 *            交易号
	 * @param waitSeconds
	 *            等待时间
	 * @return 获得的交易
	 */
	DealDto getDealAndLocked(String dealId, long waitSeconds);

	/**
	 * 根据交易号以悲观锁的形式获得交易
	 * 
	 * @param dealId
	 *            交易号
	 * @param waitSeconds
	 *            等待时间
	 * @param status
	 *            状态条件
	 * @return 获得的交易
	 */
	DealDto getDealAndLocked(String dealId, long waitSeconds, DealStatus status);

	/**
	 * 根据交易号更新交易的状态
	 * 
	 * @param dealId
	 *            交易号
	 * @param oldStatus
	 *            旧状态
	 * @param newStatus
	 *            新状态
	 */
	void changeDealStatus(String dealId, int oldStatus, int newStatus);

	/**
	 * 根据交易号更新交易的状态
	 * 
	 * @param dealId
	 *            交易号
	 * @param oldStatus
	 *            旧状态
	 * @param newStatus
	 *            新状态
	 */
	void changeDealStatus(String dealId, DealStatus oldStatus,
			DealStatus newStatus);

	/**
	 * 更新交易
	 * 
	 * @param dto
	 *            要更新的交易
	 * @return 更新好的交易
	 */
	MutableDto update(final MutableDto dto);
}
