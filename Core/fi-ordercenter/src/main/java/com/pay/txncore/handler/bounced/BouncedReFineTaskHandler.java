package com.pay.txncore.handler.bounced;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.service.chargeback.BouncedFineTaskService;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.JSonUtil;

public class BouncedReFineTaskHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(BouncedReFineTaskHandler.class);

	private BouncedFineTaskService bouncedfineTaskService;

	private ChargeBackOrderService chargeBackOrderService;
	
	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}

	public void setBouncedfineTaskService(
			BouncedFineTaskService bouncedfineTaskService) {
		this.bouncedfineTaskService = bouncedfineTaskService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			Map<String,String> params=new HashMap<String, String>();
			params.put("status", "2");//查询拒付罚款失败的数据
			List<Map> bouncedFineList=bouncedfineTaskService.findBouncedFineOrder(params);
			for (Map map : bouncedFineList) {
				String bouncedCurrencyCode=(String)map.get("bouncedCurrencyCode");
				Long partnerId=Long.valueOf(map.get("partnerId").toString());
				Long settlementAmount=Long.valueOf(map.get("settlementAmount").toString());
				Boolean baseCheckBalance = chargeBackOrderService.baseCheckBalance(new BigDecimal(settlementAmount), partnerId, bouncedCurrencyCode, true);	// 比较商户的此币种的 基本户 是否够扣
				if(!baseCheckBalance){//不够扣则 拒付罚款表状态改为失败
					//map.put("status", "2");
					logger.info("余额不足，" + "settlementAmount:" + settlementAmount + ",partnerId:" + partnerId+",bouncedCurrencyCode"+bouncedCurrencyCode);
				}else{
					chargeBackOrderService.doFineAccounting(map);//够扣则 调用记账。
					map.put("status", "1");
				}
				bouncedfineTaskService.updateBouncedFine(map); // update 拒付罚款表
			}
		} catch (Exception e) {
			logger.error("bounced fine error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.FAIL.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.FAIL.getDesc());

		}
		
		return JSonUtil.toJSonString(resultMap);
	}

}
