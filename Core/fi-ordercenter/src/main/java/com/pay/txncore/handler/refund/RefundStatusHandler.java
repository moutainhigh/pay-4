package com.pay.txncore.handler.refund;
/*modify history
 * 2016-04-22 sch: 增加了日志，人工处理状态只能由初始状态触发出来
 */
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.txncore.service.refund.RefundService;
import com.pay.util.JSonUtil;

/**
 * 根据业务需要更改退款订单状态
 * @author Bobby Guo
 * @date 2015年11月12日
 */
public class RefundStatusHandler implements EventHandler{
	
public final Log logger = LogFactory.getLog(RefundStatusHandler.class);
	private RefundService refundService;
	
	private RefundOrderService refundOrderService;
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			logger.info("SetRefundStatus paraMap : "+paraMap);
			
			Long refundOrderNo = Long.valueOf(paraMap.get("refundOrderNo")+"");
			Integer refundStatus = Integer.valueOf(paraMap.get("refundStatus")+"");
			RefundOrderDTO refundOrderDTO = refundOrderService.queryByPk(refundOrderNo);
			
			//判断
			if((refundOrderDTO ==null) || (refundOrderDTO.getStatus() == null)){
				resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
				return JSonUtil.toJSonString(resultMap);
			}			
			//订单中的状态
			logger.info("SetRefundStatus refundOrderDTO.status=" + refundOrderDTO.getStatus());
			int doResult = 0;		//是否有处理
			
			if( RefundStatusEnum.FINAL_FAIL.getCode()!=refundStatus){
				//设置为人工处理
				//根据4月12号的会议纪要，  状态3和4 做成最终方案，不允许进一步的人工处理,也就是说只有进行中的状态，才能处理成为人工处理
				//2016-06-25 4 可以处理为人工处理装填 
				if((RefundStatusEnum.INIT.getCode()==Integer.valueOf(refundOrderDTO.getStatus()))
					|| (RefundStatusEnum.TIMEOUT.getCode()==Integer.valueOf(refundOrderDTO.getStatus()))
					|| (RefundStatusEnum.REFUNDING.getCode()==Integer.valueOf(refundOrderDTO.getStatus()))) {					  
					logger.info("Set RefundStatusHandle can set manual  ");
					refundOrderDTO.setStatus(RefundStatusEnum.MANUAL.getCode()+"");
					refundOrderDTO.setUpdateDate(Calendar.getInstance().getTime());
					refundOrderService.updateRefundOrderByPk(refundOrderDTO);
					doResult = 1;
				}
				else{
					logger.info("Set SetRefundStatus cannot set manual");
				}
			}else if(RefundStatusEnum.MANUAL.getCode()==Integer.valueOf(refundOrderDTO.getStatus())){
				//设置置为失败
				ChannelPaymentResult refundResult = new ChannelPaymentResult();
				
				//add by sch 2016-05-24, 这里还是明确一点，传入失败，比较好。 必须设置为失败才行
				refundResult.setErrorCode(ResponseCodeEnum.FAIL.getCode());
				refundResult.setErrorMsg(ResponseCodeEnum.FAIL.getDesc());
				//
				refundService.refundHandle(String.valueOf(refundOrderNo), refundResult);
				
				//logger.info("after refund hanle");
				//add by sch 2016-05-24
				//上面的函数里，已经更新了这个 refundOrder,这里用对象更新，不太合适吧？ 
				//最好是前面传递一个参数到 refundHandle里，让refundHandle里面更新这个状态为 FINAL_FAIL 
				refundOrderService.updateRefundOrderState(refundOrderNo , RefundStatusEnum.FINAL_FAIL,null);
				
				//logger.info("after update RefundOrderState ");
				doResult=1;
			}else{
				logger.info("Set SetRefundStatus  failed");
			}
			
			if(doResult==1){
				resultMap.put("responseCode",ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc",ResponseCodeEnum.SUCCESS.getDesc());
			}else{
				resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			}
				
		} catch (Exception e) {
			logger.error("置为人工处理失败", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}
	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}
	
	
}
