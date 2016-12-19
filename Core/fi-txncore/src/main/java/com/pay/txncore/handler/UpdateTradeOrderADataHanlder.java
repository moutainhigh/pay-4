package com.pay.txncore.handler;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.TradeDataDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class UpdateTradeOrderADataHanlder implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private TradeOrderService tradeOrderService;

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = null;
        Map resultMap = new HashMap();

        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, Object>().getClass());

            String tradeOrderNo = (String)paraMap.get("tradeOrderNo");
            String status = (String)paraMap.get("status");
            String oldStatus = (String)paraMap.get("oldStatus");
            String resultCode = (String)paraMap.get("resultCode");
            String resultMsg = (String)paraMap.get("resultMsg");
            String doingBouncedAmount = (String)paraMap.get("doingBouncedAmount");
            Map<String,String> data = (Map<String,String>)paraMap.get("data");

            TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();

            if(!StringUtil.isEmpty(tradeOrderNo)){
                tradeOrderDTO.setTradeOrderNo(Long.valueOf(tradeOrderNo));
            }

            if(!StringUtil.isEmpty(status)){
                tradeOrderDTO.setStatus(Integer.valueOf(status));
            }
            if(!StringUtil.isEmpty(resultMsg)){
                tradeOrderDTO.setRespMsg(resultMsg);
            }
            if(!StringUtil.isEmpty(resultCode)){
                tradeOrderDTO.setRespCode(resultCode);
            }
            if(!StringUtil.isEmpty(doingBouncedAmount)){
                tradeOrderDTO.setDoingBouncedAmount(Long.parseLong(doingBouncedAmount));
            }

            tradeOrderDTO.setCompleteDate(new Date());

            TradeDataDTO dto = new TradeDataDTO();
            dto.setTradeOrderDTO(tradeOrderDTO);
            dto.setData(data);
            boolean result = false;

            if(StringUtil.isEmpty(oldStatus)){
                result = tradeOrderService.updateTradeDataRnTx(dto);
            }else{
                result = tradeOrderService.updateTradeDataRnTx
                        (dto,Integer.valueOf(oldStatus));
            }

            if(result){
                resultMap.put("responseCode","0000");
                resultMap.put("responseDesc","交易订单更新成功");
            }else{
                resultMap.put("responseCode","9999");
                resultMap.put("responseDesc","交易订单更新失败");
            }

        } catch (BusinessException e) {
            logger.error("CrosspayCashierPayHandler error:", e);
            ExceptionCodeEnum error = e.getCode();
            resultMap.put("responseCode", error.getCode());
            resultMap.put("responseDesc", error.getDescription());
        } catch (Exception e) {
            logger.error("api pay error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",
                    ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
        }

        return JSonUtil.toJSonString(resultMap);
    }

    public void setTradeOrderService(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

}
