package com.pay.channel.handler.paymentchannelitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.channel.dao.ChannelConfigTempDAO;
import com.pay.channel.model.ChannelConfigTemp;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 渠道配置批量添加
 * @date 2016年6月22日13:57:09
 * @author delin
 */
public class ChannelConfigBatchAddHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelConfigBatchAddHandler.class);
	private ChannelConfigTempDAO channelConfigTempDAO;

	public void setChannelConfigTempDAO(ChannelConfigTempDAO channelConfigTempDAO) {
		this.channelConfigTempDAO = channelConfigTempDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			 List<Map<String,String>> channelConfigs= (List<Map<String,String>>) paraMap .get("list");
			 List<ChannelConfigTemp> channels=new ArrayList<ChannelConfigTemp>();
			 for (Map<String,String> map: channelConfigs) {
				 ChannelConfigTemp configTemp=new ChannelConfigTemp();
				 configTemp.setOrgCode(map.get("orgCode").replaceAll(" ", " "));
				 configTemp.setOrgMerchantCode(map.get("orgMerchantCode").replaceAll(" ", " "));
				 configTemp.setMerchantBillName(map.get("merchantBillName").replaceAll(" ", " "));
				 configTemp.setTransType(map.get("transType").replaceAll(" ", " "));
				 configTemp.setPattern(map.get("pattern").replaceAll(" ", " "));
				 configTemp.setTerminalCode(map.get("terminalCode").replaceAll(" ", " "));
				 configTemp.setAccessCode(map.get("accessCode").replaceAll(" ", " "));
 				 configTemp.setCurrencyCode(map.get("currencyCode").replaceAll(" ", " "));
				 configTemp.setMcc(map.get("mcc").replaceAll(" ", " "));
				 configTemp.setOrgKey(map.get("orgKey").replaceAll(" ", " "));
				 configTemp.setSupportWebsite(map.get("supportWebsite").replaceAll(" ", " "));
				 configTemp.setBatchNo(map.get("batchNo").replaceAll(" ", " "));
				 configTemp.setFitMerchantType(map.get("fitMerchantType").replaceAll(" ", " "));
				 configTemp.setStatus(1);
				 configTemp.setOperator(map.get("operator").replaceAll(" ", " "));
				 channels.add(configTemp);
			}
			 channelConfigTempDAO.batchCreate(channels);
			 Map<String,String> map=new HashMap<String, String>();
			 map.put("p_batch_no", channels.get(0).getBatchNo());
			 map.put("p_operator", channels.get(0).getOperator());
			 channelConfigTempDAO.findObjectByCriteria("callChannelConfigValidationPr", map);
			 List<ChannelConfigTemp> channelConfigTemps = channelConfigTempDAO.findByCriteria(map);
			
			resultMap.put("list",channelConfigTemps);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
}
