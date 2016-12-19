package com.pay.txncore.handler.crosspay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.dao.CardBindDAO;
import com.pay.txncore.model.CardBindOrder;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 批量状态查询
 * @author mmzhang
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CardOrderQueryHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(CardOrderQueryHandler.class);
	//注入
	private CardBindDAO cardBindDAO ;
	
	public CardBindDAO getCardBindDAO() {
		return cardBindDAO;
	}

	public void setCardBindDAO(CardBindDAO cardBindDAO) {
		this.cardBindDAO = cardBindDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			List<CardBindOrder> cardBindOrders = this.cardBindDAO.findByCriteria(paraMap, page);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			resultMap.put("cardBindOrders", cardBindOrders);
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}


}
