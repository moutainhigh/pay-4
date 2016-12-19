package com.pay.forpay.client;

import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eva on 2016/8/18.
 */
public class ChannelClientService {
    private final Log logger = LogFactory.getLog(getClass());

    private HessianInvokeService invokeService;

    public void setInvokeService(HessianInvokeService invokeService) {
        this.invokeService = invokeService;
    }

    public Map<String, String> sendChannel(Map<String, String> requestData) {
        String reqMsg = JSonUtil.toJSonString(requestData);
        HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
        String sysTraceNo = SysTraceNoService
                .generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());
        String result = invokeService.invoke(
                "fi-channel.afterTradeHandler", sysTraceNo,
                SystemCodeEnum.TXNCORE.getCode(),
                SystemCodeEnum.CHANNEL.getCode(),
                SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
                param.getMsgCompress(), param.getDataMsg());

        param.parse(result);
        HessianInvokeHelper.processResponse(param);
        result = param.getDataMsg();

        Map resultMap = JSonUtil.toObject(result,
                new HashMap<String, Object>().getClass());
        return resultMap;
    }

}
