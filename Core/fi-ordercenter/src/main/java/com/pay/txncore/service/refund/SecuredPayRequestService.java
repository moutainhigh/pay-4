package com.pay.txncore.service.refund;

import com.pay.txncore.dto.PaymentNoticeDTO;
import com.pay.txncore.dto.ResponseNotify;
import com.pay.txncore.dto.refund.RefundResultNoticeDTO;



/**
 * 商家调用支付后台服务接口
 * 
 * @author Administrator
 * 
 */
public interface SecuredPayRequestService {
	
	//支付通知接口
	public void paymentResultNotify(PaymentNoticeDTO noticeDto);
	
	public boolean noiftyRefundResult(RefundResultNoticeDTO resultDTO);
	
	/**
	 * 统一发送通知方法 
	 * @param response
	 * @return
	 */
	public boolean notify(ResponseNotify response);

	public void sharingNotice(String content, String url);

	/**
	 * 确认收货接口
	 * @throws Exception 
	 */
//	public void confirmReceived(ConfirmPaymentDTO confirmRecievedDTO) throws Exception;

	/**
	 * 退款接口
	 */
//	public RefundTransactionServiceResultDTO refund(RefundDTO refundDTO);
	
	/**
	 * 构建错误退款信息
	 * @param dataMap
	 * @return
	 */
//	public String buildFailRefundResultStr(Map<String,String> dataMap);

	/**
	 * 订单修改
	 */
//	public ChangeDTO change(ChangeDTO changeDTO) throws Exception;

	/**
	 * 提交网银通知商城
	 */
//	public void sentToBankNotifty(SentToBankDTO sentToBankDTO);
	
	/**
	 * 订单查询
	 */
//	public StatusQueryDTO query(StatusQueryDTO statusQueryDTO);

	/**
	 * 查询订单明细
	 * 
	 * // TODO 看具体需求要不要这个接口 public TradeInfo queryDetail(Map<String, String>
	 * tradeMap);
	 */
	/**
	 * 激活卖家帐号
	 */
//	public void activeSeller(ActiveSellerDTO activeSellerDTO);
	
	/**
	 * 支付完成后通知商城接口
	 */
//	public void payResultNotify(SentToBankDTO sentToBankDTO);
	
	/**
	 * 支付完成后通知商城接口（合并付款）
	 */
//	public void payResultNotify(List<TradeOrderInfo> orderInfoList);
	
	/**
	 * 网银支付通知商城（合并付款）
	 */
//	public void bankPayResultNotify(List<TradeOrderInfo> orderInfoList);

	/**
	 * 网关支付前台页面通知参数生成
	 * @param baseInfo
	 * @param orderList
	 * @return
	 */
//	public Map createPageNotify(Long tradeBaseNo);
//
//	public Map generateNotifyParam(TradeBaseInfo baseInfo,
//			List<TradeOrderInfo> orderList);

	/**
	 * 发送退款通知
	 * @param resultDTO
	 * @throws Exception
	 */
//	public Map<String, String> noiftyRefundResult(RefundTransactionServiceResultDTO resultDTO) throws Exception ;
}
