package com.pay.txncore.service;

import java.util.List;

import com.pay.txncore.model.MIGSRefundOrderQueryModel;
import com.pay.txncore.model.CTVRefundOrderQueryModel;

/*
 *  本文件用来匹配  退款订单，主要是根据各种具体业务来匹配
 *  
 */

public interface RefundOrderMatchService {
	//(1)MIGS 匹配, 1 已经不用了  用2 
	public void call_MIGS_Method_1(List<MIGSRefundOrderQueryModel> migsROQMList);
	public void call_MIGS_Method_2(List<MIGSRefundOrderQueryModel> migsROQMList);
	public void call_MIGS_Method_3(List<MIGSRefundOrderQueryModel> migsROQMList);
	
	//(2)CTV　匹配，匹配的是单条记录 
	public void call_CTV_Method_1(List<CTVRefundOrderQueryModel> ctvROQMList);	//这个采用金额进行匹配
	public void call_CTV_Method_2(List<CTVRefundOrderQueryModel> ctvROQMList);	//采用RequestId进行匹配
}
