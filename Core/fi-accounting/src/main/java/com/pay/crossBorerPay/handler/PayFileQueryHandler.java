package com.pay.crossBorerPay.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.crossBorerPay.service.KfPayResourceService;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayResource;
import com.pay.txncore.model.KfPayTrade;
import com.pay.txncore.model.KfPayTradeDetail;
import com.pay.txncore.service.PayFileService;
import com.pay.txncore.service.RemitFailTypingService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
/**
 * 跨境付款文件下载
 * @author delin
 *@date 2016年8月31日14:46:08
 */
public class PayFileQueryHandler implements EventHandler{

	private static Logger logger = LoggerFactory.getLogger(PayFileQueryHandler.class);

	private PayFileService payFileService;
	
	private KfPayResourceService kfPayResourceService;
	
	private RemitFailTypingService remitFailTypingService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		List<KfPayTrade> kfPayTrades = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map<String, Object> KfPayTradeMap=(Map<String,Object>)paraMap.get("crossOrder");
			KfPayTrade kfPayTrade = MapUtil.map2Object(KfPayTrade.class,
					KfPayTradeMap);
			Map pageMap = (Map) paraMap.get("page");
			if(pageMap == null){
				kfPayTrades=payFileService.findKfPayTrade(kfPayTrade);
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap);
				kfPayTrades=payFileService.findKfPayTrade(kfPayTrade,page);
				resultMap.put("page", page);
			}
			for (KfPayTrade kfPayTrade2 : kfPayTrades) {
				String status = "-1";                              //0处理中 1处理成功 2处理失败 3部分成功    出款状态 0:待审核 1：审核通过 2：审核拒绝 3：出款成功 4：出款失败
				KfPayTradeDetail kfPayTradeDetail=new KfPayTradeDetail();
				kfPayTradeDetail.setBatchNo(kfPayTrade2.getBatchNo());
				List<KfPayTradeDetail> KfPayTradeDetail=remitFailTypingService.findKfPayTradeDetail(kfPayTradeDetail); //判断状态
			 	int successCount = 0;
				int pendingCount = 0;
				int errorConut=0;
				for (KfPayTradeDetail kfPayTradeDetail2 : KfPayTradeDetail) {
						if(kfPayTradeDetail2.getOutStatus().equals("3")){
							successCount ++;
						}else if(kfPayTradeDetail2.getOutStatus().equals("0")||kfPayTradeDetail2.getOutStatus().equals("1")){
							pendingCount ++;
						}else if(kfPayTradeDetail2.getOutStatus().equals("4")||kfPayTradeDetail2.getOutStatus().equals("2")){
							errorConut++;
						}
				}
				if(pendingCount >0){
					status = "0";
				}else if(successCount  == KfPayTradeDetail.size()){
					status = "1";
				}else if(errorConut==KfPayTradeDetail.size()){
					status = "2";
				}else if(successCount  >0&&successCount<KfPayTradeDetail.size()){
					status = "3";
				}
				kfPayTrade2.setStatus(status);
				kfPayTrade2.setCompleteDate(new Date());
				payFileService.update(kfPayTrade2);
				Map<String, Object> para=new HashMap<String, Object>(); //查询对应的文件
				para.put("batchNo", kfPayTrade2.getBatchNo());
				para.put("resourceNo", "0");
				para.put("fileType", "0");	
				List<KfPayResource> kfPayResources = kfPayResourceService.findDownloadUrl(para);
				kfPayTrade2.setKfPayResources(kfPayResources);
			}
			resultMap.put("list", kfPayTrades);
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setPayFileService(PayFileService payFileService) {
		this.payFileService = payFileService;
	}

	public void setRemitFailTypingService(
			RemitFailTypingService remitFailTypingService) {
		this.remitFailTypingService = remitFailTypingService;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PayFileQueryHandler.logger = logger;
	}

	public void setKfPayResourceService(KfPayResourceService kfPayResourceService) {
		this.kfPayResourceService = kfPayResourceService;
	}
	
}
