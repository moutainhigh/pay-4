package com.pay.txncore.handler.bounced;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/*****
 * 拒付罚款查询
 * 
 * @author delin
 * @date 2016年7月21日10:35:33
 */
public class BouncedFineQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(BouncedFineQueryHandler.class);

	private BaseDAO chargeBackOrderDAO;

	public void setChargeBackOrderDAO(BaseDAO chargeBackOrderDAO) {
		this.chargeBackOrderDAO = chargeBackOrderDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page");
			List<Map<String,Object>> map=null;
			if(pageMap!=null){
			Page page = MapUtil.map2Object(Page.class, pageMap);
			map = chargeBackOrderDAO
					.findByCriteria("queryBouncedFine", paraMap, page);
			resultMap.put("page", page);
			}else{
				map = chargeBackOrderDAO
						.findByCriteria("queryBouncedFine", paraMap);
			}
			resultMap.put("result", map);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("BouncedOrderQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}
		return JSonUtil.toJSonString(resultMap);
	}
}
