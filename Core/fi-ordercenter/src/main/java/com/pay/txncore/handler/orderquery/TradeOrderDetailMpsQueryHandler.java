package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.TradeOrderDetailMpsDTO;
import com.pay.txncore.service.TradeOrderDetailService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class TradeOrderDetailMpsQueryHandler implements EventHandler {
	
	private final Log logger = LogFactory.getLog(TradeOrderDetailQueryHandler.class);
	
	private TradeOrderDetailService tradeOrderDetailService;
	
	private CardBinInfoService  cardBinInfoService; 
	
	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}
	public void setTradeOrderDetailService(
			TradeOrderDetailService tradeOrderDetailService) {
		this.tradeOrderDetailService = tradeOrderDetailService;
	}
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		TradeOrderDetailMpsDTO tradeOrderDetail = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			logger.info("TradeOrderDetailQueryHandler dataMsg : "+JSonUtil.toJSonString(paraMap));
			TradeOrderDetailMpsDTO detailMpsDTO = MapUtil.map2Object(
					TradeOrderDetailMpsDTO.class, paraMap);
			
			tradeOrderDetail=tradeOrderDetailService.getTradeOrderDetailMps(detailMpsDTO);
			if(tradeOrderDetail==null)
				return JSonUtil.toJSonString(resultMap);
			//解析报文中的账单信息
			String tradeRequest = tradeOrderDetail.getRequestContext();
			if(StringUtils.isNotEmpty(tradeRequest)){
				String[] data = tradeRequest.split("&");
				for(String parameter:data){
					String[] kv = StringUtils.split(parameter, "=", 2);
					if(kv.length > 1) {
						resultMap.put(kv[0], kv[1]);
					}else{   
						if(kv.length == 1 && StringUtils.isNotEmpty(kv[0]))
							resultMap.put(kv[0], null);
					}
				}
			}
			String cardNo = tradeOrderDetail.getCardNo();
			CardBinInfo cardBinInfo = cardBinInfoService.getCardBinInfo(cardNo.substring(0, 6));
			String goodsDesc= resultMap.get("goodsDesc")+"";
	
			tradeOrderDetail.setGoodsDesc(goodsDesc);
			if(cardBinInfo!=null){
				tradeOrderDetail.setCardType(cardBinInfo.getCardOrg());
				tradeOrderDetail.setCountryCode2(cardBinInfo.getCountryCode2());
			}
			tradeOrderDetail.setGoodsDesc(goodsDesc);
		} catch (Exception e) {
			logger.error("api pay error:", e);
		}
		Map map = MapUtil.bean2map(tradeOrderDetail);
		return JSonUtil.toJSonString(map);
	}

}
	