package com.pay.txncore.service.impl;

/* MIGS 
* checkRefundOrderMatch(....)  函数来匹配  refundOrder和 请求参数 
*  （1）根据参考号先找到 渠道订单号和网关订单号
*  （2）然后根据网关订单号找到退款订单号，根据CompleteTime 和 金额 找到真正的退款订单号，匹配函数使用
*  
*  
* 这里有两个方法：
* 方法1： poss这端每次传递1个订单过来，每次查找一个对应的refundorder。 如果连续多个同样的订单，找到的，可能都会是同一个退款订单。  
* 方法2： poss这端每次传递1个数组过来，先根据ReferNumber和金额 形成一个数组，读取对应的refundorder数组，形成一一匹配的情况.
* 方法3： poss这端每次传递1个数组过来，先根据ReferNumber和金额 形成一个数组，读取对应的refundorder数组，形成一一匹配的情况.
*       参考号的意义在于退款订单，而不是渠道订单。
* 由于方法2/3 相对比较正确，所以，建议使用方法2/3,前提是 groovy 文件要配合 
*

如果传递过来的是 多条 记录，则返回的记录条目数，必须=0， 或者想等。 否则没法一一核对 , 或者里面有一个编号，是可以一一对应的
针对方法2： 实际上 poss可以把所有的记录传递过来， 让本程序进行排序， 并且进行合理安排和计算,最后返回一个总的结果。
*/

/* CTV 目前的方法,参考MIGS 方法1, 将来可能会变化
 * 可以使用 2 ,比较直观一点
 */


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.handler.orderquery.RefundOrderQueryHandler;
import com.pay.txncore.model.CTVRefundOrderQueryModel;
import com.pay.txncore.model.MIGSRefundOrderQueryModel;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.RefundOrderMatchService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

public class RefundOrderMatchServiceImpl implements RefundOrderMatchService{

	public final Log logger = LogFactory.getLog(RefundOrderMatchServiceImpl.class);
	
	private RefundOrderService refundOrderService;
	private ChannelOrderService channelOrderService;
	
	private final String ErrorRefundOrderNo = "1060000000000000000";
	private final int MinuteDeltaIsValid = 30;		//30 分钟之内的差距算是合法的
	
	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}
	
	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}
	
	/*
	 * 匹配卡号是否相等,这是个通用函数 
	 * 输入参数： duizhangCardNo  从对账单里来的卡号条目
	 * channelCardNo: channelOrderDTO 里来的 
	 * 返回值： true   表示匹配成功， 包括 cCardNO 为空的情况
	 *       false: 两个字段都有值，但是匹配不成功 
	 */
	private boolean checkMatchCardNo(String duizhangCardNo,String channelCardNo){
		if(StringUtil.isEmpty(channelCardNo)) {
			return true;
		}
		
		if(duizhangCardNo.length() != channelCardNo.length()){
			return false;
		}
		
		try {
			byte[] migsBytes  = duizhangCardNo.getBytes("UTF-8"); 		//utf
			byte[] cCardBytes = channelCardNo.getBytes("UTF-8");	
	    
			if( migsBytes.length != cCardBytes.length){
				return false;				
			}
			
			for(int i=0;i<migsBytes.length;i++){
				byte c = migsBytes[i];
				if(c=='*') {
					continue;
				}
				if(c != cCardBytes[i]) {
					return false;
				}
			}
			
			return true;
		}
		catch( UnsupportedEncodingException e){
			e.printStackTrace();
		}
		
		return false;
	}
	/*
	 * 根据参考号和授权码获得渠道订单列表,找不到，或者找到多条，都认为是错误的
	 * 最好再比较一下 付款卡号什么的 
	 */
	private ChannelOrderDTO getChannelOrders(String referenceNo,String authorisation,String cardNo){
		
		//(1) 查找渠道订单 
		if(referenceNo==null || referenceNo.length()==0){
			return null;
		}
		
		List<ChannelOrderDTO> channelOrders = null;

		{
			ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
			channelOrderDTO.setReferenceNo(referenceNo);
			if(authorisation != null)
				channelOrderDTO.setAuthorisation(authorisation);
		
			channelOrders = channelOrderService
				.queryChannelOrder(channelOrderDTO);
		}
		
		if(null == channelOrders || channelOrders.isEmpty()){
			return null;
		}
		
		//(2) 匹配卡号 
		//匹配一下卡号: 把ChanleOrders 中卡号不匹配的元素删除掉
		/*
		if((cardNo != null) && (cardNo.length()>0)){			
			for(int i=0;i<channelOrders.size();i++){				
				//匹配卡号				
				ChannelOrderDTO channelOrderDto = channelOrders.get(i);
				String channelCardNo = channelOrderDto.getCardNumber();  //这个字段是null，所以没有意义
							
				logger.info("cardNo=[" + cardNo +"] channelCardNo=[" + channelCardNo + "]");
				
				if(checkMatchCardNo(cardNo,channelCardNo)==false){
					logger.info("卡号不匹配 cardNo=[" + cardNo + "] channelCardNo=[" 
							+ channelCardNo + "]");
					channelOrders.remove(i);
					i--;		//注意，一定要i--
				}	
				else {
					logger.info("card match success");
				}
			}		
		}
		else {
			logger.info("channel order no card no");
		}
		*/
	
		if(channelOrders.size() >1){
			return null;
		}
		
		return channelOrders.get(0);
	}
	
	
	/*
	 * 根据参考号和授权码获得渠道订单列表,找不到，或者找到多条，都认为是错误的
	 * 最好再比较一下 付款卡号什么的 
	 */
	private ChannelOrderDTO getChannelOrderByChannelOrderNO(String channelOrderNo,String cardNo){
		
		//(1) 查找渠道订单 
		if(channelOrderNo==null || channelOrderNo.length()==0){
			return null;
		}
		
		List<ChannelOrderDTO> channelOrders = null;

		{
			ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
			channelOrderDTO.setChannelOrderNo( Long.valueOf(channelOrderNo));
			
			channelOrders = channelOrderService
				.queryChannelOrder(channelOrderDTO);
		}
		
		if(null == channelOrders || channelOrders.isEmpty()){
			return null;
		}
		
		//最好  匹配一下卡号 
		
		if(channelOrders.size() >1){
			return null;
		}
		
		return channelOrders.get(0);
	}
	
	/*
	 * 检查 退款订单 是否匹配 refundAmount 和 refundDate 
	 * 返回值：true 表示匹配成功， false 表示匹配失败 
	 * 输出参数： outDiffResult
	 *         Long 类型不能作为输出参数，传递出来，所以，搞了一个 map 表，把结果传递出来 
	 * 匹配逻辑是： 时间相差在半个小时之内，金额匹配 
	 */
	private boolean checkRefundOrderMatch(RefundOrderDTO refundOrderDTO,
			Long refundAmount,Date refundDate,Map outDiffResult){
				
		Integer iRefundStatus =Integer.valueOf(refundOrderDTO.getStatus());
		Long outDiffDate=Long.valueOf(0);
		
		//只判断成功的订单
		if((RefundStatusEnum.SUCCESS.getCode()==iRefundStatus) )
		{
			java.util.Date orderDate=refundOrderDTO.getComplateDate();	//订单里的时间 
			Long  orderTransAmount = refundOrderDTO.getTransferAmount();					
			
			// 金额符合，并且时间符合，则加入到列表中去
			long diffDate = refundDate.getTime() - orderDate.getTime();  //这样得到的差值是豪秒级别
			long diffTransAmount = refundAmount - orderTransAmount;
					
			// 半小时之内的，算是合理
			if( (diffDate > 1000*60* MinuteDeltaIsValid ) || (diffDate < -1000*60*MinuteDeltaIsValid) ){
				return false;
			}
			
			if(diffTransAmount >10 || diffTransAmount < -10){
				return false;
			}
			
			//outDiffDate = Long.valueOf(Math.abs(diffDate));
			if(outDiffResult != null){
				outDiffDate = Math.abs(diffDate);
				outDiffResult.put("resultDiffDate", outDiffDate.toString());
			}
			
			logger.info("找到一个合适的RefundOrder refundOrderNo=[" + refundOrderDTO.getRefundOrderNo()   
					+ " transAmount=[" + orderTransAmount + "]" 
					+ " compleateDate=[" + orderDate + "]" 
					+ " diffDate=[" + diffDate + "]" 
					+ " outDifDate=[ +" + outDiffDate + "]");
			
			return true;
		}
		return false;
	}
	
	
	/*
	 * 检查 退款订单 是否匹配 refundAmount , 检查是否已经对过账了 
	 * 返回值：true 表示匹配成功， false 表示匹配失败 
	 * 匹配逻辑是： 时
	 */
	private boolean checkRefundOrderMatch(RefundOrderDTO refundOrderDTO,
			Long refundAmount){
				
		//已经对帐的,我们去掉 
		if(refundOrderDTO.getReconciliationFlg()==1){
			return false;
		}
		
		Integer iRefundStatus =Integer.valueOf(refundOrderDTO.getStatus());
		Long outDiffDate=Long.valueOf(0);
		
		//只判断成功的订单 并且还没有对帐的订单  
		if((RefundStatusEnum.SUCCESS.getCode()==iRefundStatus) )
		{
			java.util.Date orderDate=refundOrderDTO.getComplateDate();	//订单里的时间 
			Long  orderTransAmount = refundOrderDTO.getTransferAmount();					
			
			long diffTransAmount = refundAmount - orderTransAmount;					
						
			if(diffTransAmount >10 || diffTransAmount < -10){
				return false;
			}
						
			logger.info("找到一个合适的RefundOrder refundOrderNo=[" + refundOrderDTO.getRefundOrderNo()   
					+ " transAmount=[" + orderTransAmount + "]" 
					+ " compleateDate=[" + orderDate + "]" );
			
			return true;
		}
		
		return false;
	}
	
	/*
	 * 返回退款订单,只返回唯一的一个订单号
	 */
	private RefundOrderDTO getRefundOrder_MIGS_Method1(ChannelOrderDTO channelOrderDTO,
			MIGSRefundOrderQueryModel migsROQM){
		
		if(null == channelOrderDTO){
			return null;
		}
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
		
		//数据库 channel_order 中并没有 tradeOrderNo 字段，channelOrderDTO 里是如何找到这个东东的 
		List<RefundOrderDTO> refundOrders = refundOrderService.findByTradeOrderNo(channelOrderDTO.getTradeOrderNo());	
		if(null == refundOrders || refundOrders.isEmpty() ){
			logger.info("find no refundOrders tradeOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
			return null;
		}
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]" + "refundOrderNums=[" + 
					 refundOrders.size() + "]");
		
		List<RefundOrderDTO>  refundOrderResult = new  ArrayList<RefundOrderDTO>();	
		BigDecimal refundAmount_d = new BigDecimal(migsROQM.getRefundAmount()); 
		
		//扣款金额
		Long refundAmount = refundAmount_d.multiply(new BigDecimal(1000)).longValue();		//需要乘以 1000;
		if(refundAmount<0){
			refundAmount = -refundAmount;		//数据库中是正数，所以需要调整
		}
		//扣款时间		
		Date refundDate = getMigsDate(migsROQM);
		
		logger.info("开始查找   RefundAmount=[" + refundAmount + "]" + " refundDate=[" + refundDate + "]");
		
		//遍历这个refundOrders ，查找符合条件的refundOrders;
		for (RefundOrderDTO refundOrderDTO : refundOrders) {						
				if(checkRefundOrderMatch(refundOrderDTO,refundAmount,refundDate,null)){			
					refundOrderResult.add(refundOrderDTO);
				}
					
		}
		
		if(refundOrderResult.isEmpty()) {
			logger.info("找不到对应的RefundOrder");
			return null;
		}
		
		if(refundOrderResult.size() > 1) {
			logger.info("找到多条记录，不匹配");
			return null;
		}
				
		return refundOrderResult.get(0);
	}
	
	/*
	 * 获取 RefundOrderNO 方法1 
	 */
	private void do_MIGS_Method_1(MIGSRefundOrderQueryModel migsROQM){
		//金额-日期-时间  不能为空 
		if((migsROQM.getRefundAmount() == null) 
				|| (migsROQM.getDealDate()== null ) 
				|| (migsROQM.getDealTime()== null )) {
			
			logger.info("金额-日期-时间  不能为空 ");
			migsROQM.setRefundOrderNo(ErrorRefundOrderNo);  //106 订单全部为1 
			return ;
		}
		
		//(1)查找渠道订单  
		ChannelOrderDTO channelOrderDTO = getChannelOrders(
				migsROQM.getRefeNumber(),migsROQM.getAuthorisation(),migsROQM.getCardNo());
		
		if(channelOrderDTO == null){
			migsROQM.setRefundOrderNo(ErrorRefundOrderNo);  
			return ;
		}
		
		//(2) 根据 网关订单号找到 退款订单号
		RefundOrderDTO refundOrderDTO = getRefundOrder_MIGS_Method1(channelOrderDTO,migsROQM);
		if(refundOrderDTO != null){
			migsROQM.setRefundOrderNo(refundOrderDTO.getRefundOrderNo().toString());
		}
		else {
			migsROQM.setRefundOrderNo(ErrorRefundOrderNo);   
		}
		
		return ;
	}
	
	@Override
	public void call_MIGS_Method_1(List<MIGSRefundOrderQueryModel> migsROQMList) {
		for (MIGSRefundOrderQueryModel migsROQM : migsROQMList) {
			logger.info("migsROQM" + migsROQM.toString());
			do_MIGS_Method_1(migsROQM);
		}
	}
	
	/*
	 * 根据 2个字符串 天+时间， 获得一个日期 
	 */
	private Date getMigsDate(MIGSRefundOrderQueryModel migsROQM){
			
		Date refundDate = new Date();
		try{		
			String dateStr = migsROQM.getDealDate();		//20160506
			String timeStr = migsROQM.getDealTime(); 		//002000
			
			if(dateStr == null || dateStr.length()<8){
				return refundDate;
			}
			if(timeStr== null || timeStr.length() < 6){
				return refundDate;
			}
			
			String formatDateStr = dateStr.substring(0,4) + "-" 
			                     + dateStr.substring(4,6) + "-"
			                     + dateStr.substring(6,8) + " "
			                     + timeStr.substring(0, 2) + ":"
			                     + timeStr.substring(2, 4) + ":" 
			                     + timeStr.substring(4, 6);
			             
			refundDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateStr);
			logger.info("refundDate=[" + refundDate +"]" + "FormatDateStr=[" + formatDateStr + "]");
		}
		catch (ParseException e) {
			logger.error("日期处理失败", e);
		}
		
		return refundDate;
	}
	
	//根据一个数组: 来获得每一个数组中的元素对应的退款订单
	public void do_MIGS_Method_2(List<MIGSRefundOrderQueryModel> migsROQMList){
		
		logger.info("doMethod_2 开始"); 
		if(migsROQMList == null || migsROQMList.isEmpty()){
			return ;
		}
		
		//如果只有 1个元素 ,没有必要走 doMethod_1 ，下面的逻辑更加顺畅 
		//if(migsROQMList.size()==1){
		//	doMethod_1(migsROQMList.get(0));  
		//	return ;
		//}
		
		//多个元素 
		MIGSRefundOrderQueryModel migsROQM_1 = migsROQMList.get(0);	//第一个元素
		
		ChannelOrderDTO channelOrderDTO = getChannelOrders(
				migsROQM_1.getRefeNumber(),migsROQM_1.getAuthorisation(),migsROQM_1.getCardNo());
		
		if(channelOrderDTO == null){
			logger.info("找不到ChannelOrderDTO");
			for(MIGSRefundOrderQueryModel migsROQM :  migsROQMList){
				logger.info("migsROQM" + migsROQM.toString());
				migsROQM.setRefundOrderNo(ErrorRefundOrderNo);	
			}
			
			return ;
		}
		
		//
		List<RefundOrderDTO> refundOrders = refundOrderService.findByTradeOrderNo(channelOrderDTO.getTradeOrderNo());	
		if(null == refundOrders || refundOrders.isEmpty() ){
			logger.info("find no refundOrders tradeOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
			return ;
		}
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]" + "refundOrderNums=[" + 
					 refundOrders.size() + "]");
		
		//去掉 不成功的 以及 已经对账成功的订单(否则同一个订单可能会出现反复对账成功？)
		//暂时先不实现这个代码，因为 对账成功的订单是否可以反复对账？ 
		
		
		
		//两个数组进行排序 之后进行对比 
		Map outDiffDateMap = new HashMap();
		
		for(MIGSRefundOrderQueryModel migsROQM :  migsROQMList){
		
			//List<RefundOrderDTO>  refundOrderResult = new  ArrayList<RefundOrderDTO>();	
			BigDecimal refundAmount_d = new BigDecimal(migsROQM.getRefundAmount()); 
		
			//扣款金额
			Long refundAmount = refundAmount_d.multiply(new BigDecimal(1000)).longValue();		//需要乘以 1000;
			if(refundAmount<0){
				refundAmount = -refundAmount;		//数据库中是正数，所以需要调整
			}
			
			//扣款时间		
			Date refundDate = getMigsDate(migsROQM);
		
			logger.info("开始查找   RefundAmount=[" + refundAmount + "]" + " refundDate=[" + refundDate + "]");
		
			//遍历这个refundOrders ，查找符合条件的refundOrder,找到时间最为匹配的
			int findIndex = -1;
			long minDiffDate_l = 1000000000;		//取一个很大的值
			for(int i=0;i< refundOrders.size();i++){
				
				RefundOrderDTO refundOrderDTO = refundOrders.get(i);			
				
				if(checkRefundOrderMatch(refundOrderDTO, refundAmount,refundDate ,outDiffDateMap)){						
					
					String strOutDateMap  = (String)outDiffDateMap.get("resultDiffDate");
					
					if(strOutDateMap != null){
						Long outDiffDate_L = Long.valueOf(strOutDateMap);
					
						//logger.info("outDiffDate_L = [" + outDiffDate_L + "]" + "minDiffDate_l=" + minDiffDate_l);
						//refundOrderResult.add(refundOrderDTO);							
						if((findIndex==-1) || (outDiffDate_L.longValue() < minDiffDate_l)){
							minDiffDate_l = outDiffDate_L.longValue();	 
							findIndex = i;
						
							//logger.info("set findIndex=["+ i +"]outDiffDate_L = [" + outDiffDate_L + "]" + "minDiffDate_l=" + minDiffDate_l);
						}
					}
				}
			}
			
			//如果找到，则从RefundOrderDTO 中删除元素 	
			if(findIndex != -1){
				RefundOrderDTO refundOrderDTO = refundOrders.get(findIndex);
				migsROQM.setRefundOrderNo( refundOrderDTO.getRefundOrderNo().toString() );
				
				//查找结果
				logger.info("refundOrderDTO refundOrderNo=[" + refundOrderDTO.getRefundOrderNo() + "]" 
						+ " findIndex=[" + findIndex + "]");
				
				//从RefundOrderDTO 中删除该元素
				refundOrders.remove(findIndex);
			}
			else {
				logger.info("找不到 该条信息");
				migsROQM.setRefundOrderNo( ErrorRefundOrderNo );
			}
			//refundOrderResult.clear();
		}
		
	}
	
	
	//根据一个数组: 来获得每一个数组中的元素对应的退款订单
		public void do_MIGS_Method_3(List<MIGSRefundOrderQueryModel> migsROQMList){
			
			logger.info("doMethod_3 开始"); 
			if(migsROQMList == null || migsROQMList.isEmpty()){
				return ;
			}
			
			//如果只有 1个元素 ,没有必要走 doMethod_1 ，下面的逻辑更加顺畅 
			//if(migsROQMList.size()==1){
			//	doMethod_1(migsROQMList.get(0));  
			//	return ;
			//}
			
			//多个元素 
			MIGSRefundOrderQueryModel migsROQM_1 = migsROQMList.get(0);	//第一个元素

			RefundOrderDTO refundOrderQueryDto = new RefundOrderDTO();
			refundOrderQueryDto.setChannelReturnNo(migsROQM_1.getRefeNumber());
			refundOrderQueryDto.setRefundOrgCode(ChannelItemOrgCodeEnum.BOCI.getCode());	//MIGS 
			
			//
			List<RefundOrderDTO> refundOrders = refundOrderService.findRefundOrderDTOs(refundOrderQueryDto);
			if(null == refundOrders || refundOrders.isEmpty() ){
				logger.info("find no refundOrders refer_num=[" + migsROQM_1.getRefeNumber() + "]");
				return ;
			}
			
			logger.info(" migsROQM.RefeNumber =[" + migsROQM_1.getRefeNumber() + "]" + "refundOrderNums=[" + 
						 refundOrders.size() + "]");
			
			//去掉 不成功的 以及 已经对账成功的订单(否则同一个订单可能会出现反复对账成功？)
			//暂时先不实现这个代码，因为 对账成功的订单是否可以反复对账？ 
			
			//两个数组进行排序 之后进行对比 
			Map outDiffDateMap = new HashMap();
			
			for(MIGSRefundOrderQueryModel migsROQM :  migsROQMList){
			
				//List<RefundOrderDTO>  refundOrderResult = new  ArrayList<RefundOrderDTO>();	
				BigDecimal refundAmount_d = new BigDecimal(migsROQM.getRefundAmount()); 
			
				//扣款金额
				Long refundAmount = refundAmount_d.multiply(new BigDecimal(1000)).longValue();		//需要乘以 1000;
				if(refundAmount<0){
					refundAmount = -refundAmount;		//数据库中是正数，所以需要调整
				}
				
				//扣款时间		
				Date refundDate = getMigsDate(migsROQM);
			
				logger.info("开始查找   RefundAmount=[" + refundAmount + "]" + " refundDate=[" + refundDate + "]");
			
				//遍历这个refundOrders ，查找符合条件的refundOrder,找到时间最为匹配的
				int findIndex = -1;
				long minDiffDate_l = 1000000000;		//取一个很大的值
				for(int i=0;i< refundOrders.size();i++){
					
					RefundOrderDTO refundOrderDTO = refundOrders.get(i);			
					
					if(checkRefundOrderMatch(refundOrderDTO, refundAmount,refundDate ,outDiffDateMap)){						
						
						String strOutDateMap  = (String)outDiffDateMap.get("resultDiffDate");
						
						if(strOutDateMap != null){
							Long outDiffDate_L = Long.valueOf(strOutDateMap);
						
							//logger.info("outDiffDate_L = [" + outDiffDate_L + "]" + "minDiffDate_l=" + minDiffDate_l);
							//refundOrderResult.add(refundOrderDTO);							
							if((findIndex==-1) || (outDiffDate_L.longValue() < minDiffDate_l)){
								minDiffDate_l = outDiffDate_L.longValue();	 
								findIndex = i;
							
								//logger.info("set findIndex=["+ i +"]outDiffDate_L = [" + outDiffDate_L + "]" + "minDiffDate_l=" + minDiffDate_l);
							}
						}
					}
				}
				
				//如果找到，则从RefundOrderDTO 中删除元素 	
				if(findIndex != -1){
					RefundOrderDTO refundOrderDTO = refundOrders.get(findIndex);
					migsROQM.setRefundOrderNo( refundOrderDTO.getRefundOrderNo().toString() );
					
					//查找结果
					logger.info("refundOrderDTO refundOrderNo=[" + refundOrderDTO.getRefundOrderNo() + "]" 
							+ " findIndex=[" + findIndex + "]");
					
					//从RefundOrderDTO 中删除该元素
					refundOrders.remove(findIndex);
				}
				else {
					logger.info("找不到 该条信息");
					migsROQM.setRefundOrderNo( ErrorRefundOrderNo );
				}
				//refundOrderResult.clear();
			}
			
		}
		
	/*
	 * 方法2,3 都使用这个流程, 重新构造了一下
	 */
	public void call_MIGS_Method_2_3(List<MIGSRefundOrderQueryModel> migsROQMList,int nMethod){
		//(1) (参考号 + 退款金额  ) 作为 key ,构造一个 has 表.  value 是一个list，里面是相同  参考号 和 退款金额的条目  
				// 如果这样有问题，比如有的时候，两笔单子  12.33 12.34 是在两个 key ，我们可以把key 取到精确到  0.1 ，而不是 0.01 
				Map<String , List<MIGSRefundOrderQueryModel>> map = new HashMap<String , List<MIGSRefundOrderQueryModel>>();
				
				boolean hasDoubleRefeNum = false;		//是否有重复的RefuNum;
				for(MIGSRefundOrderQueryModel migsROQM :  migsROQMList){
					
					if((migsROQM.getRefundAmount() == null) 
							|| (migsROQM.getDealDate()== null ) 
							|| (migsROQM.getDealTime()== null )) {
						
						logger.info("金额-日期-时间  不能为空 ");
						migsROQM.setRefundOrderNo(ErrorRefundOrderNo);  //106 订单全部为1 
						continue ;
					}
					
					String key = migsROQM.getRefeNumber() + "_" + migsROQM.getRefundAmount();
					
					List<MIGSRefundOrderQueryModel> hashValue = (List<MIGSRefundOrderQueryModel> ) map.get(key);
					if(hashValue==null){
						List<MIGSRefundOrderQueryModel> list= new ArrayList<MIGSRefundOrderQueryModel>();
						list.add(migsROQM);
						map.put(key,list);
					}
					else {
						hasDoubleRefeNum = true;
						hashValue.add(migsROQM);
						
						//需要重新设置回去么？  				
					}
				}
				
				//没有重复的，我们直接调用 方法1 来完成
				
				if(hasDoubleRefeNum==false){
					logger.info("method 2 , 但是没有重复的记录，所以，我们循环调用方法2");
					
					/*for(MIGSRefundOrderQueryModel migsROQM :  migsROQMList){
						logger.info("migsROQM" + migsROQM.toString());
						doMethod_1(migsROQM);
					}
					
					map.clear();  //加速内存清理 
					return ;
					*/
				}
				
				
				//(2) 针对 hash 表中的每一个 元素(list) ，我们执行 doMethod_2(list)  

				Iterator<Map.Entry<String,List<MIGSRefundOrderQueryModel>>> iterator = map.entrySet().iterator();
				
				while (iterator.hasNext()) {
					Map.Entry<String, List<MIGSRefundOrderQueryModel>> entry = iterator.next();
					String key = entry.getKey();
					List<MIGSRefundOrderQueryModel> list= entry.getValue();
					
					logger.info("key=[" + key + "]" + "size=[" + list.size() + "]" );
					if(nMethod==2){
						do_MIGS_Method_2(list);
					}
					else{
						do_MIGS_Method_3(list);
					}
				}
					
				map.clear();
				
				//(3) for debug 打印结果
				/*
				logger.info("Call Method 2");
				for(MIGSRefundOrderQueryModel migsROQM :  migsROQMList){			
					String migsROQM2Str = migsROQM.toString();
					logger.info(migsROQM2Str + "refundOrderNo=[" + migsROQM.getRefundOrderNo() + "]");			
				}
				*/
				
				return ;
	}
	
	/*
	 * 获取 RefundOrderNO 方法2 
	 * 输出参数： 会修改 migsROQMList 中每一个元素的 refundOrderNo, 如果找不到，就会设置为 ErrorRefundOrderNo
	 */
	@Override
	public void call_MIGS_Method_2(List<MIGSRefundOrderQueryModel> migsROQMList){
		
		call_MIGS_Method_2_3(migsROQMList,2);
	}
	
	@Override
	public void call_MIGS_Method_3(List<MIGSRefundOrderQueryModel> migsROQMList){
		call_MIGS_Method_2_3(migsROQMList,3);
	}
	
	/* 
	 * CTV  
	 */
	
	/*
	 * 返回退款订单,只返回唯一的一个订单号,
	 * 根据网关订单进行查找,这个方法不推荐  
	 */
	private RefundOrderDTO getRefundOrder_CTV_Method1(ChannelOrderDTO channelOrderDTO,
			CTVRefundOrderQueryModel ctvROQM){
		
		if(null == channelOrderDTO){
			return null;
		}
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
		
		//数据库 channel_order 中并没有 tradeOrderNo 字段，channelOrderDTO 里是如何找到这个东东的 
		List<RefundOrderDTO> refundOrders = refundOrderService.findByTradeOrderNo(channelOrderDTO.getTradeOrderNo());	
		if(null == refundOrders || refundOrders.isEmpty() ){
			logger.info("find no refundOrders tradeOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
			return null;
		}
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]" + "refundOrderNums=[" + 
					 refundOrders.size() + "]");
		
		List<RefundOrderDTO>  refundOrderResult = new  ArrayList<RefundOrderDTO>();	
		BigDecimal refundAmount_d = new BigDecimal(ctvROQM.getRefundAmount()); 
		
		//扣款金额
		Long refundAmount = refundAmount_d.multiply(new BigDecimal(1000)).longValue();		//需要乘以 1000;
		if(refundAmount<0){
			refundAmount = -refundAmount;		//数据库中是正数，所以需要调整
		}
		//扣款时间		
		//Date refundDate = getMigsDate(ctvROQM);
		
		logger.info("开始查找   RefundAmount=[" + refundAmount + "]" );
		
		//遍历这个refundOrders ，查找符合条件的refundOrders;
		for (RefundOrderDTO refundOrderDTO : refundOrders) {						
				if(checkRefundOrderMatch(refundOrderDTO,refundAmount)){			
					refundOrderResult.add(refundOrderDTO);
				}
		}
		
		if(refundOrderResult.isEmpty()) {
			logger.info("找不到对应的RefundOrder");
			return null;
		}
		
		/*
		if(refundOrderResult.size() > 1) {
			logger.info("找到多条记录，不匹配");
			return null;
		}
		*/
		
		return refundOrderResult.get(0);
	}
	
	/*
	 * 返回退款订单,只返回唯一的一个订单号,根据渠道订单号和返回码 
	 */
	private RefundOrderDTO getRefundOrder_CTV_Method2(ChannelOrderDTO channelOrderDTO,
			CTVRefundOrderQueryModel ctvROQM){
		
		if(null == channelOrderDTO){
			return null;
		}
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
		
		//数据库 channel_order 中并没有 tradeOrderNo 字段，channelOrderDTO 里是如何找到这个东东的
		RefundOrderDTO refundQueryDTO = new RefundOrderDTO();
		refundQueryDTO.setPaymentOrderNo(channelOrderDTO.getPaymentOrderNo());//
		//refundQueryDTO.setRequestSerialId(ctvROQM.getRefundRequestId());	//
		refundQueryDTO.setChannelReturnNo(ctvROQM.getRefundRequestId());	//modify by nico.shao 2016-07-25
		
		List<RefundOrderDTO> refundOrders = refundOrderService.findRefundOrderDTOs(refundQueryDTO);	 
		if(null == refundOrders || refundOrders.isEmpty() ){
			logger.info("find no refundOrders tradeOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
			return null;
		}
		
		logger.info(" paymentOrderNo=[" + channelOrderDTO.getPaymentOrderNo() + "]" + ",refundOrderNums=[" + 
					 refundOrders.size() + "]");
		
		//如果只有一个订单,则直接返回这个订单 
		if(refundOrders.size()==1){
			return refundOrders.get(0);
		}
		
		//进行金额匹配  
		List<RefundOrderDTO>  refundOrderResult = new  ArrayList<RefundOrderDTO>();	
		BigDecimal refundAmount_d = new BigDecimal(ctvROQM.getRefundAmount()); 
		
		//扣款金额
		Long refundAmount = refundAmount_d.multiply(new BigDecimal(1000)).longValue();		//需要乘以 1000;
		if(refundAmount<0){
			refundAmount = -refundAmount;		//数据库中是正数，所以需要调整
		}
		//扣款时间		
		//Date refundDate = getMigsDate(ctvROQM);
		
		logger.info("开始查找   RefundAmount=[" + refundAmount + "]" );
		
		//遍历这个refundOrders ，查找符合条件的refundOrders;
		for (RefundOrderDTO refundOrderDTO : refundOrders) {						
				if(checkRefundOrderMatch(refundOrderDTO,refundAmount)){			
					refundOrderResult.add(refundOrderDTO);
				}
		}
		
		if(refundOrderResult.isEmpty()) {
			logger.info("找不到对应的RefundOrder");
			return null;
		}
		
		/*
		if(refundOrderResult.size() > 1) {
			logger.info("找到多条记录，不匹配");
			return null;
		}
		*/
		
		return refundOrderResult.get(0);
	}
	/*
	 * 获取 RefundOrderNO 
	 */
	private void do_CTV_Method(CTVRefundOrderQueryModel ctvROQM,int nMethod){
		//金额-日期-时间  不能为空 
		if(ctvROQM.getRefundAmount() == null) {			
			logger.info("金额-日期-时间  不能为空 ");
			ctvROQM.setRefundOrderNo(ErrorRefundOrderNo);  //106 订单全部为1 
			return ;
		}
		
		//(1)查找渠道订单  
		ChannelOrderDTO channelOrderDTO = getChannelOrderByChannelOrderNO(
				ctvROQM.getChannelOrderNo(),ctvROQM.getCardNo());
		
		if(channelOrderDTO == null){
			ctvROQM.setRefundOrderNo(ErrorRefundOrderNo);  
			return ;
		}
		
		//(2) 根据 网关订单号找到 退款订单号
		if(nMethod==1)
		{
			RefundOrderDTO refundOrderDTO = getRefundOrder_CTV_Method1(channelOrderDTO,ctvROQM);
			if(refundOrderDTO != null){
				ctvROQM.setRefundOrderNo(refundOrderDTO.getRefundOrderNo().toString());
			}
			else {
				ctvROQM.setRefundOrderNo(ErrorRefundOrderNo);   
			}
		}else if(nMethod==2){
			RefundOrderDTO refundOrderDTO = getRefundOrder_CTV_Method2(channelOrderDTO,ctvROQM);
			if(refundOrderDTO != null){
				ctvROQM.setRefundOrderNo(refundOrderDTO.getRefundOrderNo().toString());
			}
			else {
				ctvROQM.setRefundOrderNo(ErrorRefundOrderNo);   
			}
		}
		else
		{
			ctvROQM.setRefundOrderNo(ErrorRefundOrderNo);   
		}
		
		return ;
	}
	
	//(2)CTV　匹配，匹配的是单条记录 
	@Override 
	public void call_CTV_Method_1(List<CTVRefundOrderQueryModel> ctvROQMList){
		
		for (CTVRefundOrderQueryModel ctvROQM : ctvROQMList) {
				logger.info("ctvROQM" + ctvROQM.toString());
				do_CTV_Method(ctvROQM,1);
		}
	}
	
	
	//(2)CTV　方法2和方法1 ,流程是一样的,但是采取的配方方法不一样 .
	//   ctvROQMList 中可以包含一个或多个元素,由客户端来触发进行优化 
	@Override
	public void call_CTV_Method_2(List<CTVRefundOrderQueryModel> ctvROQMList) {
		for (CTVRefundOrderQueryModel ctvROQM : ctvROQMList) {
			logger.info("ctvROQM" + ctvROQM.toString());
			do_CTV_Method(ctvROQM, 2);
		}
	}
}
