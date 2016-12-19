package com.pay.txncore.handler.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dao.ReportDAO;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 
 * 商户退款比例报表
 * @date 2016年7月7日17:27:50
 * @author delin
 */
public class MerchantRefundPercntHandler  implements EventHandler{

	public final Log logger = LogFactory.getLog(MerchantRefundPercntHandler.class);
	private ReportDAO reportDAO;

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> paraMap = null;
		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			List<Map> resultList=null;
			List<Map>  totalResultList=null;
			Map pageMap = (Map) paraMap.get("page");
			if(pageMap!=null){
				Page page = MapUtil.map2Object(Page.class, pageMap);
				 resultList = reportDAO.queryRefundPercntReports(paraMap,page);
				 resultMap.put("page", page);				
			}else{
				 resultList = reportDAO.queryRefundPercntReports(paraMap);
			}
			String memberCode = (String)paraMap.get("memberCode");
			if(StringUtils.isEmpty(memberCode )){
				totalResultList= reportDAO.queryTotalRefundPercnt(paraMap);
			}
			resultMap.put("resultList", resultList);
			resultMap.put("totalResultList", totalResultList);
			resultMap.put("responseCode",
					ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		return JSonUtil.toJSonString(resultMap);
	}

}
