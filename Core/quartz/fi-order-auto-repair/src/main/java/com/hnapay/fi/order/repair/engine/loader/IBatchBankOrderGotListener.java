/**
 * IBatchBankOrderGotListener.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader;

import com.hnapay.fi.dto.batchrepair.api.BatchBankOrderDTO;

/**
 * 批次银行订单已获取监听器
 * latest modified time : 2011-8-27 下午12:15:32
 * @author bigknife
 */
public interface IBatchBankOrderGotListener {
	void onBatchBankOrderGot(BatchBankOrderDTO batchBankOrder);
}
