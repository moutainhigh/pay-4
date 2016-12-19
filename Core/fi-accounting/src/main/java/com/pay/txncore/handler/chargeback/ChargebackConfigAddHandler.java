/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.chargeback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.ChargeBackConfig;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 获取可用渠道
 * 
 * @author chma
 */
public class ChargebackConfigAddHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChargebackConfigAddHandler.class);
	private BaseDAO chargeBackConfigDAO;

	public void setChargeBackConfigDAO(BaseDAO chargeBackConfigDAO) {
		this.chargeBackConfigDAO = chargeBackConfigDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		Map<String, String> paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		ChargeBackConfig config = MapUtil.map2Object(ChargeBackConfig.class,
				paraMap);
		config.setCreateDate(new Date());
		config.setUpdateDate(new Date());

		ChargeBackConfig query = new ChargeBackConfig();
		query.setMemberCode(config.getMemberCode());
		ChargeBackConfig queryConfig = (ChargeBackConfig) chargeBackConfigDAO
				.findObjectByCriteria("findByCriteria", query);
		if (null == queryConfig) {
			Integer id = (Integer) chargeBackConfigDAO.create(config);
			if (null != id) {
				resultMap.put("responseCode",
						ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc",
						ResponseCodeEnum.SUCCESS.getDesc());
			} else {
				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc());
			}
		} else {
			queryConfig.setFirstCost(config.getFirstCost());
			queryConfig.setFirstPercent(config.getFirstPercent());
			queryConfig.setFourCost(config.getFourCost());
			queryConfig.setFourPercent(config.getFourPercent());
			queryConfig.setSecondCost(config.getSecondCost());
			queryConfig.setSecondPercent(config.getSecondPercent());
			queryConfig.setThirdPercent(config.getThirdPercent());
			boolean b = chargeBackConfigDAO.update(queryConfig);
			if (b) {
				resultMap.put("responseCode",
						ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc",
						ResponseCodeEnum.SUCCESS.getDesc());
			} else {
				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc());
			}
		}

		return JSonUtil.toJSonString(resultMap);
	}

}
