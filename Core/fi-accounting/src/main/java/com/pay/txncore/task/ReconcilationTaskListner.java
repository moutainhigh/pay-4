package com.pay.txncore.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.pay.txncore.service.ReconcileRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.jms.notification.request.AccountingReconcileRequest;
import com.pay.jms.notification.request.TranDailyReportNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.txncore.commons.ReconcileBatchStatusEnum;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.dto.ReconciliationDto;
import com.pay.txncore.dto.refund.RefundOrderDTO;
/*
 * 设计思路：收到一个消息，暂时就在这个Listner中进行处理，如果性能吃不消，可以采用线程池来进行处理。以提高并发性。
 * 不过，考虑到记账方面的并发性能，线程池未必能提高多少并发性能 
 */
import com.pay.txncore.model.RefundOrder;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.ChannelService;
import com.pay.txncore.service.reconcile.ReconcileBatchService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import org.springframework.beans.BeanUtils;

public class ReconcilationTaskListner implements MessageListener{
	
	private Log logger = LogFactory.getLog(ReconcilationTaskListner.class);
	private ChannelService channelService;
	private ChannelOrderService channelOrderService;
	private RefundOrderService refundOrderService;
	private ReconcileBatchService reconcileBatchService;
	private JmsSender jmsSender;
	private ReconcileRecordService reconcileRecordService;

	public void setReconcileRecordService(ReconcileRecordService reconcileRecordService) {
		this.reconcileRecordService = reconcileRecordService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
	
	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setReconcileBatchService(ReconcileBatchService reconcileBatchService){
		this.reconcileBatchService = reconcileBatchService;
	}

	/**
	 * @param jmsSender the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		
		logger.info("##################ReconcilationTaskListner 进行处理 start##################");
		
		if (message instanceof ObjectMessage) {
			logger.info("解析监听消息对象，消息对象为：[ " + message.toString() + "]");
			
			//(1) 从消息中解析出   dataMap 
			ObjectMessage objectMessage = (ObjectMessage) message;					
			Map<String, String> dataMap = null;	//从消息中解析出来的Map
			
			try {
				AccountingReconcileRequest accountingReconcileRequest = (AccountingReconcileRequest) objectMessage.getObject();
				if(accountingReconcileRequest==null){
					logger.error("从MQ里面取出信息[" + objectMessage + "] accountingReconcileRequest is null");
				}
				
				//获取批次信息
				dataMap = accountingReconcileRequest.getData();
				
				if(dataMap==null){
					logger.error("从MQ里面取出信息[" + objectMessage + "] dataMap is null");
					return ;
				}
				
			} catch (JMSException e) {
				logger.error("从MQ里面取出信息[" + objectMessage + "]序列化转换出错", e);
				return ;
			}
			
			//处理消息
			try{		
				//(2.1) 获取参数   
				String command = dataMap.get("command");
				String listStr = dataMap.get("list");
				String batchNo = dataMap.get("batchNo");
				
				List <ReconciliationDto> reconciliationDtoList= new ArrayList<ReconciliationDto>();
				Map map = new HashMap();
				map.put("batchNo", batchNo);

				List<ReconcileImportRecordDetailDto> batchDetailDTOs=reconcileRecordService.queryReconcileDetailBatch(map);
				if(null != batchDetailDTOs && batchDetailDTOs.size() > 0){
					for (ReconcileImportRecordDetailDto recordDetail:batchDetailDTOs
							) {
						ReconciliationDto reconciliationDto =  new ReconciliationDto();
						BeanUtils.copyProperties(recordDetail, reconciliationDto);
						reconciliationDtoList.add(reconciliationDto);
					}
				}

				//(2.2) 根据命令，来调用不同的代码 
				if(command.equals("pre")){
					
					//先做预处理
					this.doPre(batchNo,dataMap,reconciliationDtoList);
					this.doReconcile(batchNo,dataMap,reconciliationDtoList);
				
				}
				else if(command.equals("process")){
					this.doReconcile(batchNo,dataMap,reconciliationDtoList);
				}
				
				
			} catch (Exception e) {
				logger.error("batchNo end", e);
			}
		}
		
		logger.info("##################ReconcilationTaskListner 进行处理 end ##################");
	}
	
	/*
	 * 预处理
	 * 策略方法：     如果是migs ，完全可以从数据库中读取一段时间内的渠道订单，形成一个内存map，如果有用，然后查找。
	 *          这个内存map 可以按天 来读，然后放在 Redis 里，缓存起来，
	 *          在内存map 中找不到，或者时间差超过 1个星期的，可以按照从数据库中获取的方法，进行对账。
	 *          
	 *          如果用上面的方法，效率会很不错。 整体上可控
	 * 输入：reconciliationDtoList  
	 * 输出： reconciliationDtoList  
	 *          
	 */
	void doPre(String batch,Map<String, String> dataMap,List <ReconciliationDto> reconciliationDtoList){
		if(reconciliationDtoList==null || reconciliationDtoList.size()==0)
			return;
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = dataMap.get("startDate");		//2016-08-15
		String endDate = dataMap.get("endDate");			//
		String orgCode = dataMap.get("orgCode");
		String batchNo = dataMap.get("batchNo");
		Boolean bFindOrder = false;	//找到一个订单
		//根据开始时间和结束时间到数据库中获取渠道订单 TODO
		
		//最好是判断一下状态，状态不对的，就不接受处理 
		reconcileBatchService.updateBatchPreStatus(batchNo, 
				ReconcileBatchStatusEnum.PREPROCESS_START.getCode());
		
		try {
			ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();//MapUtil.map2Object(ChannelOrderDTO.class, paraMap);
			if(!StringUtil.isEmpty(startDate))
			{
				String beginTime = startDate + " 00:00:00";
				Calendar startTime = Calendar.getInstance();
				startTime.setTime(sd.parse(beginTime));
				startTime.add(Calendar.DAY_OF_MONTH, -1);//在页面所选择的时间上再提前一天
				startDate = sd.format(startTime.getTime());
				channelOrderDTO.setBeginTime(startDate);
				System.out.println("###startDate="+startDate);
			}
			if(!StringUtil.isEmpty(endDate))
			{
				String endTime = endDate + " 23:59:59";
				Calendar c_endTime = Calendar.getInstance();
				c_endTime.setTime(sd.parse(endTime));
				c_endTime.add(Calendar.DAY_OF_MONTH, 1);//在页面所选择的时间上再推后一天
				endDate = sd.format(c_endTime.getTime());
				channelOrderDTO.setEndTime(endDate);
				System.out.println("###endDate="+endDate);
			}
			
			channelOrderDTO.setStatus(1);
			if(!StringUtil.isEmpty(orgCode))
				channelOrderDTO.setOrgCode(orgCode);
			
			List<ChannelOrderDTO> coList = channelOrderService.queryChannelOrder(channelOrderDTO);
			//以"authorisation+referenceNo"为key，缓存指定渠道订单号。
			String authorisation = "";//授权码
			String referenceNo = "";//参考号
			String key = "";
			ConcurrentMap<String, Long> channelOrderCache = new ConcurrentHashMap<String, Long>();
			if(coList !=null){
				for(ChannelOrderDTO co : coList)
				{
					authorisation = co.getAuthorisation();
					referenceNo = co.getReferenceNo();
					if(authorisation==null && referenceNo==null)continue;
					if((authorisation!=null && "null".equalsIgnoreCase(authorisation)) && (referenceNo!=null && "null".equalsIgnoreCase(referenceNo)))continue;
					if(authorisation==null || "null".equalsIgnoreCase(authorisation)) authorisation = "";
					if(referenceNo==null || "null".equalsIgnoreCase(referenceNo)) referenceNo = "";
					key = authorisation + "_" + referenceNo;
					channelOrderCache.put(key, co.getChannelOrderNo());
					//System.out.println("数据库中的记录key=["+key+"] value=["+co.getChannelOrderNo()+"]");
				}
			}
			logger.info("channelOrderCache size=" + channelOrderCache.size() );
			
			int nFindInCache_payment = 0;
			int nFindInDB_payment = 0;
			int nNotFind_payment = 0;
			int nFindInDB_refund = 0;
			int nNotFind_refund = 0;
			
			Long channelOrderNo = 0L;
			refundOrderMap.clear();//在处理之前清空缓存
			for(ReconciliationDto rec : reconciliationDtoList)
			{
				//正向支付订单
				if("1".equalsIgnoreCase(rec.getTradeType()))
				{
					authorisation = rec.getAuthCode();
					referenceNo = rec.getRefeNumber();
					if(authorisation==null && referenceNo==null)continue;
					if((authorisation!=null && "null".equalsIgnoreCase(authorisation)) && (referenceNo!=null && "null".equalsIgnoreCase(referenceNo)))continue;
					if(authorisation==null || "null".equalsIgnoreCase(authorisation)) authorisation = "";
					if(referenceNo==null || "null".equalsIgnoreCase(referenceNo)) referenceNo = "";
					key = authorisation + "_" + referenceNo;
					channelOrderNo = channelOrderCache.get(key);
					if(channelOrderNo != null)
					{
						//找到对应值，进行赋值
						rec.setChannelOrderNo(channelOrderNo.toString());
						bFindOrder=true;
						nFindInCache_payment++;
						updateChannelOrderNo_Pre(batchNo,rec);
						//System.out.println("###已经缓存的Key="+key);
						//logger.info("###已经缓存的Key="+key);
					}
					else
					{
						//未找到对应值，根据“授权码”和“参考号”从数据库中查找指定数据
						channelOrderDTO = new ChannelOrderDTO();
						if(!StringUtil.isEmpty(authorisation))
							channelOrderDTO.setAuthorisation(authorisation);
						if(!StringUtil.isEmpty(referenceNo))
							channelOrderDTO.setReferenceNo(referenceNo);

						//System.out.println("$$$未缓存的Key="+key);
						//logger.info("$$$未缓存的Key="+key);
						coList = channelOrderService.queryChannelOrder(channelOrderDTO);
						if(coList!=null && !coList.isEmpty()){
							if(coList.size()>1)
							{
								logger.error("@@@ moto_rc 通过参考号【 " + referenceNo + "】查到多笔数据取第一条！");
							}											
							rec.setChannelOrderNo(String.valueOf(coList.get(0).getChannelOrderNo()));//TODO 就取第一条会不会有问题？要不要通过机构码等来匹配？remark by davis.guo
							bFindOrder = true;
							updateChannelOrderNo_Pre(batchNo,rec);
							nFindInDB_payment++;
							
						}else{
							//rec.setChannelOrderNo("100000000");  这句话没有必要，groovy 文件会设置这个订单号
							nNotFind_payment++;
						}
					}
				}
				//反向退款订单
				else if("2".equalsIgnoreCase(rec.getTradeType()))
				{
					String refundOrderNo = rec.getChannelOrderNo();
					// 106开头的订单为退款订单
					if (!StringUtil.isEmpty(refundOrderNo)
							&& refundOrderNo.length()==19 //1061608021608032681我们的订单号为19位
							&& refundOrderNo.startsWith("106")
							&& !refundOrderNo.equals("1060000000000000000")) {
						
						//不处理
					}
					else
					{
						//没有找到退款订单号，农行通过channelOrderNo查找退款订单；中银通过refeNumber,authorisation,channelReturnNo,refundOrgCode 查找退款订单。
						refundOrderNo = findRefundOrderNo(orgCode, rec);
						rec.setChannelOrderNo(refundOrderNo);//TODO 可能换成别的字段
						updateChannelOrderNo_Pre(batchNo,rec);
					}
				}
			}

			refundOrderMap.clear();//在处理完成后清空缓存

			
			logger.info("nFindInCache_payment=["+nFindInCache_payment
					+"],nFindInDB_payment=[" +nFindInDB_payment
					+"],nNotFind_payment=["+nNotFind_payment
					+"],nFindInDB_refund=["+nFindInDB_refund
					+"],nNotFind_refund=["+nNotFind_refund
					+"]");
		} catch (Exception e) {
			logger.error("query error:", e);
		}	
		
		reconcileBatchService.updateBatchPreStatus(batchNo, 
				ReconcileBatchStatusEnum.PREPROCESS_END.getCode());
		
	}
	
	
	//更新数据库的订单id 
	private void updateChannelOrderNo_Pre(String batchNo,ReconciliationDto rec){
		ReconcileImportRecordDetailDto rdd = new ReconcileImportRecordDetailDto();
		rdd.setBatchNoDetail(batchNo);
		rdd.setAcquirerRef(rec.getListIndex());
		rdd.setChannelOrderNo(Long.valueOf(rec.getChannelOrderNo())); 
		
		logger.info("update ChannelOrderNo=[" + rec.getChannelOrderNo() +"],RefNumber=[" 
		+ rec.getRefeNumber() + "],AuthCode="+ rec.getAuthCode()  
		+ ",] listIndex=[" + rec.getListIndex() +"]");
		
		this.reconcileBatchService.updateReconcileRecordDetailChannelOrderNo(rdd);
	}
	private final String ErrorRefundOrderNo = "1060000000000000000";
	
	/***
	 * 没有找到退款订单号，农行通过channelOrderNo,cardNo查找退款订单；
	 * 中银通过refeNumber,authorisation,cardNo 查找退款订单。
	 * @author Davis.guo at 2016-08-17
	 * @param orgCode
	 * @param rec
	 * @return
	 */
	private String findRefundOrderNo(String orgCode, final ReconciliationDto rec)
	{
		String refundOrderNo = "";
		if (ChannelItemOrgCodeEnum.BOCI.getCode().equals(orgCode)//中银MIGS
			|| ChannelItemOrgCodeEnum.BOCM.getCode().equals(orgCode)//中银MOTO
			|| ChannelItemOrgCodeEnum.BOCS.getCode().equals(orgCode)//中银卡司
		){
			if(rec == null){
				return ErrorRefundOrderNo;
			}
			String refundAmount = rec.getDealAmount();//TODO 可能换成别的字段
			//金额 不能为空 
			if(refundAmount == null || Float.valueOf(refundAmount) == 0) {//可能为负数
				logger.info("金额不能为空 ");
				return ErrorRefundOrderNo;
			}
			
			if(ChannelItemOrgCodeEnum.BOCI.getCode().equals(orgCode))//中银MIGS
			{
				System.out.println("###中银MIGS----------------refundAmount="+refundAmount);
				//根据渠道返回码和退款机构码查询退款单
				return do_BOCI(rec);//中银MIGS
			}
			else
			{
				System.out.println("###中银MOTO,中银卡司----------------refundAmount="+refundAmount);
				//根据参考号和授权码获取渠道订单，并依据退款金额识别的退款订单
				return do_BOCM_BOCS(rec);//中银MOTO,中银卡司
			}
		
		}
		else if (ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(orgCode)//农行CTV
			|| ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)//农业银行
		){//农行通过channelOrderNo,cardNo查找退款订单；
			String refundAmount = rec.getDealAmount();//TODO 可能换成别的字段
			String channelOrderNo = rec.getChannelCode();//TODO 可能换成别的字段
			String channelReturnNo = rec.getRefeNumber();//TODO getResultCode换成getRefeNumber字段，配合abc groovy文件中的字段
			System.out.println("###农业银行----------------refundAmount="+refundAmount+",channelOrderNo="+channelOrderNo+",channelReturnNo="+channelReturnNo);
			//金额 不能为空 
			if(refundAmount == null || Float.valueOf(refundAmount) <= 0) {
				return ErrorRefundOrderNo;
			}
			//渠道订单号或渠道返回码，不能为空 
			if( StringUtil.isEmpty(channelOrderNo) 
					&& StringUtil.isEmpty(channelReturnNo) ) {
				return ErrorRefundOrderNo;
			}
			//(1)查找渠道订单  
			ChannelOrderDTO channelOrderDTO = getChannelOrderByChannelOrderNO(channelOrderNo);
			if(channelOrderDTO == null){
				return ErrorRefundOrderNo;
			}

			//(2)根据 支付订单号和渠道返回码 查找退款订单号
			RefundOrderDTO refundOrderDTO = getRefundOrder_ABC_CYBSCTV(channelOrderDTO, channelReturnNo, refundAmount);
			if(refundOrderDTO != null){
				refundOrderNo = refundOrderDTO.getRefundOrderNo().toString();
			}
			else {
				refundOrderNo = ErrorRefundOrderNo;   
			}
			
		}
		return refundOrderNo;
	}
	
	//=====================================================MIGS begin=====================================================

	//网关订单关联的退款订单;“渠道返回码+退款机构码”关联的退款订单;
	private ConcurrentMap<String , List<RefundOrderDTO>> refundOrderMap = new ConcurrentHashMap<String, List<RefundOrderDTO>>();
	
	//根据参考号和授权码获取渠道订单，并依据退款金额识别的退款订单
	private String do_BOCM_BOCS(ReconciliationDto rec){
		logger.info("do_BOCM_BOCS 开始"); 

		String refeNumber = rec.getRefeNumber();//TODO 可能换成别的字段
		String authorisation = rec.getAuthCode();//TODO 可能换成别的字段
		String cardNo = rec.getCardHolderPhoneNumber();//TODO 可能换成别的字段
		String refundAmount = rec.getDealAmount();//TODO 可能换成别的字段
		
		ChannelOrderDTO channelOrderDTO = getChannelOrders(refeNumber, authorisation, cardNo);
		
		if(channelOrderDTO == null){
			logger.info("找不到ChannelOrderDTO");
			return ErrorRefundOrderNo;
		}
		
		//一个渠道订单可能对应多条部分退款订单，所以使用Map来缓存数据库中渠道订单对应的退款订单，避免重复查找。
		List<RefundOrderDTO> refundOrders = refundOrderMap.get(channelOrderDTO.getTradeOrderNo().toString());
		
		if(refundOrders==null || refundOrders.size()==0)
		{
			refundOrders = refundOrderService.findByTradeOrderNo(channelOrderDTO.getTradeOrderNo());
			if(null == refundOrders || refundOrders.isEmpty() ){
				logger.info("find no refundOrders tradeOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
				return ErrorRefundOrderNo;
			}
			refundOrderMap.put(channelOrderDTO.getTradeOrderNo().toString(), refundOrders);
		}
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]" + "refundOrderNums=[" + 
					 refundOrders.size() + "]");
		
		return checkRefundOrderMatch(refundOrders, refundAmount);
		
	}
	
	//通过退款金额匹配退款订单
	private String checkRefundOrderMatch(List<RefundOrderDTO> refundOrders, String refundAmountStr)
	{
		BigDecimal refundAmount_d = new BigDecimal(refundAmountStr); 
		//扣款金额
		Long refundAmount = refundAmount_d.multiply(new BigDecimal(1000)).longValue();		//需要乘以 1000;
		if(refundAmount<0){
			refundAmount = -refundAmount;		//数据库中是正数，所以需要调整
		}
		//通过时间去匹配太麻烦了，更何况不能唯一对应。这里直接根据金额去匹配，如果有多条金额是一样的，匹配一条删除一条，反正对账时只对金额。
		for(RefundOrderDTO refundOrder : refundOrders)
		{
			//已经对帐的,我们去掉 
			if(refundOrder.getReconciliationFlg()==1){
				continue;
			}
			//只判断成功的订单
			Integer iRefundStatus =Integer.valueOf(refundOrder.getStatus());
			if((RefundStatusEnum.SUCCESS.getCode()!=iRefundStatus) )
			{
				continue;
			}
			Long  orderTransAmount = refundOrder.getTransferAmount();
			int diffTransAmount = (int) (refundAmount - orderTransAmount);
			if(diffTransAmount >10 || diffTransAmount < -10){
				continue;
			}
			//查找到后，标识缓存中该记录已经匹配过，下次不再进行重新匹配。因是缓存数据，所以改状态不会有影响。
			refundOrder.setStatus("6");//设为不成功就行，以便上面进行过滤
			logger.info("找到一个合适的RefundOrder refundOrderNo=[" + refundOrder.getRefundOrderNo()   
					+ " transAmount=[" + orderTransAmount + "]");

			return refundOrder.getRefundOrderNo().toString();
		}
		logger.info("找不到该条信息");
		
		return ErrorRefundOrderNo;
	}

	/*
	 * 根据参考号和授权码获得渠道订单列表，找不到或者找到多条，都认为是错误的
	 * 最好再比较一下 付款卡号什么的 
	 */
	private ChannelOrderDTO getChannelOrders(String referenceNo,String authorisation,String cardNo){
		
		//(1) 查找渠道订单 
		if(StringUtil.isEmpty(referenceNo)){
			return null;
		}
		
		List<ChannelOrderDTO> channelOrders = null;

		ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
		channelOrderDTO.setReferenceNo(referenceNo);
		if(!StringUtil.isEmpty(authorisation))
			channelOrderDTO.setAuthorisation(authorisation);
	
		channelOrders = channelOrderService.queryChannelOrder(channelOrderDTO);
		if(null == channelOrders || channelOrders.isEmpty()){
			return null;
		}
		
		//(2) 匹配卡号 
		//匹配一下卡号: 把ChanleOrders 中卡号不匹配的元素删除掉
	
		if(channelOrders.size() >1){
			logger.info("###channelOrders.size()="+channelOrders.size());
			return null;
		}
		
		return channelOrders.get(0);
	}
	
	//根据渠道返回码和退款机构码查询退款单
	public String do_BOCI(ReconciliationDto rec){
		
		logger.info("do_BOCI 开始"); 

		String channelReturnNo = rec.getRefeNumber();// getResultCode换成getRefeNumber字段
		String refundAmount = rec.getDealAmount();//TODO 可能换成别的字段
		System.out.println("###农业银行----------------refundAmount="+refundAmount+",channelReturnNo="+channelReturnNo);
		
		RefundOrderDTO refundOrderQueryDto = new RefundOrderDTO();
		refundOrderQueryDto.setChannelReturnNo(channelReturnNo);
		refundOrderQueryDto.setRefundOrgCode(ChannelItemOrgCodeEnum.BOCI.getCode());	//MIGS 
		//TODO 在此添加查询条件
		//
		//一个渠道订单可能对应多条部分退款订单，所以使用Map来缓存数据库中渠道订单对应的退款订单，避免重复查找。
		String key = channelReturnNo+"_"+refundOrderQueryDto.getRefundOrgCode();
		List<RefundOrderDTO> refundOrders = refundOrderMap.get(key);
		
		if(refundOrders==null || refundOrders.size()==0)
		{
			refundOrders = refundOrderService.findRefundOrderDTOs(refundOrderQueryDto);
			if(null == refundOrders || refundOrders.isEmpty() ){
				logger.info("find no refundOrders channelReturnNo=[" + channelReturnNo + "]");
				return ErrorRefundOrderNo;
			}
			refundOrderMap.put(key, refundOrders);//通过“渠道返回码+退款机构码”来进行缓存，查询到的退款订单
		}
		
		logger.info(" channelReturnNo =[" + channelReturnNo + "]" + "refundOrderNums=[" + 
					 refundOrders.size() + "]");
		
		
		return checkRefundOrderMatch(refundOrders, refundAmount);
		
	}
	//=====================================================MIGS end=====================================================
	
	//=====================================================CTV begin=====================================================
	/*
	 * 根据参考号和授权码获得渠道订单列表,找不到，或者找到多条，都认为是错误的
	 * 最好再比较一下 付款卡号什么的 
	 */
	private ChannelOrderDTO getChannelOrderByChannelOrderNO(String channelOrderNo){
		
		//(1) 查找渠道订单 
		if(StringUtil.isEmpty(channelOrderNo)){
			return null;
		}
		
		List<ChannelOrderDTO> channelOrders = null;

		ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
		channelOrderDTO.setChannelOrderNo( Long.valueOf(channelOrderNo));
		
		channelOrders = channelOrderService.queryChannelOrder(channelOrderDTO);
		
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
	 * 返回退款订单,只返回唯一的一个订单号,根据渠道订单号和返回码 
	 */
	private RefundOrderDTO getRefundOrder_ABC_CYBSCTV(ChannelOrderDTO channelOrderDTO, String channelReturnNo, String refundAmountStr){
		
		logger.info(" traderOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
		
		RefundOrderDTO refundQueryDTO = new RefundOrderDTO();
		refundQueryDTO.setPaymentOrderNo(channelOrderDTO.getPaymentOrderNo());//
		if(!StringUtil.isEmpty(channelReturnNo))
		{
			refundQueryDTO.setChannelReturnNo(channelReturnNo);
		}
		
		List<RefundOrderDTO> refundOrders = refundOrderService.findRefundOrderDTOs(refundQueryDTO);	 
		if(null == refundOrders || refundOrders.isEmpty() ){
			logger.info("find no refundOrders tradeOrderNo=[" + channelOrderDTO.getTradeOrderNo() + "]");
			return null;
		}
		
		logger.info(" paymentOrderNo=[" + channelOrderDTO.getPaymentOrderNo() + "]" + ",refundOrderNums=[" + refundOrders.size() + "]");
		
		//如果只有一个订单,则直接返回这个订单 
		if(refundOrders.size()==1){
			return refundOrders.get(0);
		}
		
		//进行金额匹配  TODO
		List<RefundOrderDTO>  refundOrderResult = new  ArrayList<RefundOrderDTO>();	
		BigDecimal refundAmount_d = new BigDecimal(refundAmountStr); 
		
		//扣款金额
		Long refundAmount = refundAmount_d.multiply(new BigDecimal(1000)).longValue();		//需要乘以 1000;
		if(refundAmount<0){
			refundAmount = -refundAmount;		//数据库中是正数，所以需要调整
		}
				
		logger.info("开始查找   RefundAmount=[" + refundAmount + "]" );
		
		//遍历这个refundOrders ，查找符合条件的refundOrders;
		for (RefundOrderDTO refundOrderDTO : refundOrders) {
			//已经对帐的,我们去掉 
			if(refundOrderDTO.getReconciliationFlg()==1){
				continue;
			}
			
			Integer iRefundStatus =Integer.valueOf(refundOrderDTO.getStatus());
			
			//只判断成功的订单 并且还没有对帐的订单  
			if((RefundStatusEnum.SUCCESS.getCode()==iRefundStatus) )
			{
				java.util.Date orderDate=refundOrderDTO.getComplateDate();	//订单里的时间 
				Long  orderTransAmount = refundOrderDTO.getTransferAmount();					
				
				long diffTransAmount = refundAmount - orderTransAmount;					
							
				if(diffTransAmount >10 || diffTransAmount < -10){
					continue;
				}
							
				logger.info("找到一个合适的RefundOrder refundOrderNo=[" + refundOrderDTO.getRefundOrderNo()   
						+ " transAmount=[" + orderTransAmount + "]" 
						+ " compleateDate=[" + orderDate + "]" );
				
				refundOrderResult.add(refundOrderDTO);
			}
		}
		
		if(refundOrderResult.isEmpty()) {
			logger.info("找不到对应的RefundOrder");
			return null;
		}
		
		return refundOrderResult.get(0);
	}
	//=====================================================CTV end=====================================================
	
	//对账
	private void doReconcile(String batchNo,Map<String, String> dataMap,List <ReconciliationDto> reconciliationDtos){
		
		//判断一下需要对账的条目是否正确
		if(reconciliationDtos==null || reconciliationDtos.isEmpty()){
			logger.info("reconciliationDtos is null or is Empty");
			
			reconcileBatchService.updateBatchPreStatus(batchNo,
					ReconcileBatchStatusEnum.PROCESS_END.getCode());	
			return ;
		}
	
		List<ReconcileImportRecordDetailDto> importRecordDetailDtos=new ArrayList<ReconcileImportRecordDetailDto>(); 
		
		ReconcileImportRecordBatchDto importRecordBatch=new ReconcileImportRecordBatchDto();
			
		importRecordBatch.initValue(batchNo,ReconcileBatchStatusEnum.PROCESS_START.getCode());
		
		this.reconcileBatchService.updateReconcileRecordBatch(importRecordBatch);
		
		//几个输入参数
		String startDate = dataMap.get("startDate");
		String endDate =  dataMap.get("endDate");
		String operator = dataMap.get("operator");
		String settlmentCurrencyCodesString = dataMap.get("settlementCurrencyCodes");
		//转换数据
		List<String> settlementCurrencyCodesList = (List<String>) 
				JSonUtil.toObject(settlmentCurrencyCodesString, new ArrayList<String>().getClass()) ;
		
		String[] settlementCurrencyCodes =null;
		if( (settlementCurrencyCodesList != null) && (settlementCurrencyCodesList.size()>0) ) {
			settlementCurrencyCodes = new String[settlementCurrencyCodesList.size()] ;
			settlementCurrencyCodesList.toArray(settlementCurrencyCodes);
		}
		
		logger.info("settlementCurrencyCodesArray=[" + settlementCurrencyCodesList
				    +"],settlementCurrencyCodes=[" + settlementCurrencyCodes +"]");
		
		Date date = new Date();
		
		List<ChannelOrderDTO>	channelOrderDTOs = new ArrayList<ChannelOrderDTO>();
		List<RefundOrder>	    refundOrders = new ArrayList<RefundOrder>();
		List<ReconciliationDto>	ReportsReconcilianList = new ArrayList<ReconciliationDto>();
				
		for (ReconciliationDto reconciliationDto : reconciliationDtos) {
			
			if(reconciliationDto ==null || StringUtil.isEmpty(reconciliationDto.getChannelOrderNo())){
				logger.info("reconciliationDto is null or reconciliationDto.getChannelOrderNo is null ");
				continue;
			}

		
			// 106开头的订单问退款订单
			if (reconciliationDto.getChannelOrderNo().startsWith("106")) {
				RefundOrder refundOrder = null;
				try {			
						logger.info("退款对账开始，退款订单号:" + reconciliationDto.getChannelOrderNo());
						refundOrder = channelService.reconciliationRefundRnTx(startDate,endDate,reconciliationDto);
					
						// 回显对账结果
						refundOrder.setBacthNo(batchNo);
						refundOrder.setOperator(operator);
						refundOrder.setBacthCreateDate(date);
					} catch (BusinessException e) {
						logger.error("BusinessException:", e);
						refundOrder.setErrorMsg(e.getMessage());
					} catch (Exception e) {
						logger.error("Reconciliation error:", e);
						refundOrder.setErrorMsg("未知异常");
					}
					
					if (null != refundOrder) {
						
						reconcileBatchService.addResultList_RefundOrderDTO(importRecordBatch, importRecordDetailDtos,
								refundOrder, batchNo, reconciliationDto.getListIndex());
						
						refundOrders.add(refundOrder);
					}
				} else {
					
					/* 渠道对账 */
					ChannelOrderDTO channelOrderDTO = null;
					
					try {
						logger.info("原渠道对账开始，渠道订单号:" + reconciliationDto.getChannelOrderNo());
						channelOrderDTO = channelService.reconciliationRnTx(startDate, endDate,
								reconciliationDto,settlementCurrencyCodes);
						// 回显对账结果
						channelOrderDTO.setBacthNo(batchNo);
						channelOrderDTO.setOperator(operator);
						channelOrderDTO.setBacthCreateDate(date);
						
						//add by sch 2016-06-26 正确对账
						if(reconcileBatchService.checkNeedAddtoReportService(reconciliationDto,channelOrderDTO)){
							reconcileBatchService.normalize2ReportService(reconciliationDto);
							ReportsReconcilianList.add(reconciliationDto);
						}
						
					} catch (BusinessException e) {
						logger.error("BusinessException:", e);
						channelOrderDTO.setErrorMsg(e.getMessage());
					} catch (Exception e) {
						logger.error("Reconciliation error:", e);
						channelOrderDTO.setErrorMsg("未知异常");
						channelOrderDTO.setTransFee(reconciliationDto.getTransFee());
						channelOrderDTO.setSettAmount(reconciliationDto.getSettAmount());
						channelOrderDTO.setSettleDate(reconciliationDto.getSettleDate());
						channelOrderDTO.setChannelOrderNo(Long.valueOf(reconciliationDto
								.getChannelOrderNo()));
					}
					if (null != channelOrderDTO) {
						
						reconcileBatchService.addResultList_ChannelOrderDTO(importRecordBatch, importRecordDetailDtos, channelOrderDTO, batchNo, 
								reconciliationDto.getListIndex());
						
						channelOrderDTOs.add(channelOrderDTO);
					}
				}
			}
		
		//reconcileBatchService.updateBatchPreStatus(batchNo,
		//	ReconcileBatchStatusEnum.PROCESS_END.getCode());	
		
		//这个代码会更新importRecodBatch 
		updateReconcileRecord2(importRecordBatch, importRecordDetailDtos);
	    
	    //调用交易日报表，进行核对账目,2016-06-25
	    updateReportService(ReportsReconcilianList);  
	    
	    //发送邮件通知 
	    
	}
	
	/*
	 * 最后更新条目明细和批次数据
	 */
	
	public void updateReconcileRecord2(ReconcileImportRecordBatchDto importRecordBatch,
			List<ReconcileImportRecordDetailDto> detailDtos){
		
		if( (detailDtos !=null) && (detailDtos.size()>0)){
			this.reconcileBatchService.updateReconcileRecordDetail(detailDtos);
		}
	
		importRecordBatch.setBatchEndTime(new Date());
		importRecordBatch.setStatus(ReconcileBatchStatusEnum.PROCESS_END.getCode());
		if(importRecordBatch != null){
			//this.reconcileBatchService.updateReconcileRecordBatch(importRecordBatch);
			this.reconcileBatchService.updateReconcileRecordBatchProcess(importRecordBatch);
		}
	}
	
	/*
	 * 调用交易日报表，进行维护
	 */
	private void   updateReportService(List<ReconciliationDto> reportsReconcilianList){
		logger.info("update reportService");
		this.notifyTranDailyReport(reportsReconcilianList);
	}
	
	/**
	 * added by Jiangbo.Peng
	 * 交易日报表数据异步更新
	 * @param reportsReconcilianList
	 * 注意： 如果要改这里的代码，请同步修改 ReconcilationTaskListner.java 中的代码 
	 */
	private void notifyTranDailyReport(List<ReconciliationDto> reportsReconcilianList) { 
		logger.info("notifyTranDailyReport................");
		try {
			//发送mq消息到forpay
			String reqMsg = JSonUtil.toJSonString(reportsReconcilianList); // 转json发送保存的数据
			
			TranDailyReportNotifyRequest tranDailyReportNotifyRequest = new TranDailyReportNotifyRequest();
			Map<String, String> data = new HashMap<String, String>() ;
			data.put("list", reqMsg) ;
			tranDailyReportNotifyRequest.setData(data);
			jmsSender.send("notify.forpay",tranDailyReportNotifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
