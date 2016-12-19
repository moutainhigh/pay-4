package com.pay.txncore.handler;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dao.AuthChaneOrderDAO;
import com.pay.txncore.service.TokenPayInfoService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber on 2016/9/20.
 */
public class TokenPayValidationHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private AuthChaneOrderDAO authChaneOrderDAO;
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, String> paraMap = null;
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            String registerUserId = paraMap.get("registerUserId");
            String partnerId = paraMap.get("partnerId");
            String token = paraMap.get("token");

            Map<String,Object> params = new HashMap<String, Object>();
            if(!StringUtil.isEmpty(token)){
                params.put("token",token);
            }
            params.put("registerUserId",registerUserId);
            params.put("partnerId",partnerId);

            int validateNumber = authChaneOrderDAO.findByValidate(params);

            resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
            resultMap.put("validateNumber", validateNumber);
            resultMap.put("responseDesc",ResponseCodeEnum.SUCCESS.getDesc());
        } catch (BusinessException e) {
            logger.error("CrosspayCashierPayHandler error:", e);
            ExceptionCodeEnum error = e.getCode();
            resultMap.put("responseCode", error.getCode());
            resultMap.put("responseDesc", error.getDescription());
        } catch (Exception e) {
            logger.error("card bind error:", e);
            resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
        }
        return JSonUtil.toJSonString(resultMap);
    }


    public void setAuthChaneOrderDAO(AuthChaneOrderDAO authChaneOrderDAO) {
        this.authChaneOrderDAO = authChaneOrderDAO;
    }
}
