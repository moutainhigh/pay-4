/**
 * 
 */
package com.pay.jms.listener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.axis.transport.jms.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.jms.notification.request.NotifyRequest;
import com.pay.util.JSonUtil;

/**
 * @author yanshichuan
 *
 */
public class CancelPreAuthMessageListener implements MessageListener {
    private final Log log = LogFactory.getLog(CancelPreAuthMessageListener.class);
    private HessianInvokeService invokeService; 
    /**
     * 失败重试次数
     */
    private final int retryTimes=2;
	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;

            NotifyRequest request;
            try {
                request = (NotifyRequest) om.getObject();
                log.info("数据 request ="+ request.getData());
                Map<String, String> requestData = request.getData();
                int times=0;
                while(times<retryTimes){
                	log.info("第"+(++times)+"次撤销预授权");
                	if(cancelPreAuth(requestData)){
                		break;
                	}
                }
            } catch (JMSException e) {
                log.error(e);
            }
        }
	}
	private boolean cancelPreAuth(Map paraMap) {
		boolean success=false;
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		log.info("CancelPreAuthMessageListener cancelPreAuth");
		String result = invokeService.invoke(
				"txncore.cancelPreAuthHandler", sysTraceNo,
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());
		if(ExceptionCodeEnum.PRE_AUTH_VOID_SUCCESS.getCode().equals(resultMap.get("responseCode"))){
			success=true;
		}
		return success;
	}
	public HessianInvokeService getInvokeService() {
		return invokeService;
	}
	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}
	
}
