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
import com.pay.txncore.crosspay.dao.TokenPayInfoDAO;
import com.pay.txncore.model.TokenPayInfo;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 批量状态查询
 * @author mmzhang
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class TokenPayInfoQueryHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(TokenPayInfoQueryHandler.class);
	//注入
	private TokenPayInfoDAO tokenPayInfoDAO ;
	

	public TokenPayInfoDAO getTokenPayInfoDAO() {
		return tokenPayInfoDAO;
	}


	public void setTokenPayInfoDAO(TokenPayInfoDAO tokenPayInfoDAO) {
		this.tokenPayInfoDAO = tokenPayInfoDAO;
	}


	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			List<TokenPayInfo> cardBindOrders = this.tokenPayInfoDAO.findByCriteria(paraMap, page);
//			page.setResult(cardBindOrders);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			resultMap.put("list", cardBindOrders);
			resultMap.put("page", page);
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}


}
