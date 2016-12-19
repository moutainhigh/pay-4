package com.pay.app.controller.base.crossBorderPay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pay.acc.acct.service.AcctService;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.client.SettleCoreClientService;
import com.pay.app.controller.base.vo.MapVO;
import com.pay.fi.dto.KfPayTrade;
import com.pay.fi.dto.KfPayTradeByDownload;
import com.pay.fi.dto.KfPayTradeDetail;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.inf.dao.Page;
import com.pay.util.MapUtil;

@Controller
public class CrossBorerQueryController {

	Log logger = LogFactory.getLog(CrossBorerQueryController.class);
	
	@Autowired
	@Qualifier(value = "settleCoreClientService")
	private SettleCoreClientService settleCoreClientService;

	@Autowired
	@Qualifier(value = "ossUtils")
	private MyOSS myoss;
	
	@Autowired
	@Qualifier(value = "acc-acctService")
	private AcctService acctService;

	
	@RequestMapping(value="/corp/CrossBorerQueryController/into.do")
	public String into(){
		return "/base/crossBorerPay/crossBorerQuery";
	}
	
	@RequestMapping(value="/CrossBorerQueryController/query.do")
	public String query(MapVO mapVO, HttpServletRequest request, Map<String, Object> result,
			HttpSession session){
		
		try {
			String pageCurrent = request.getParameter("pager_offset");
			int pageSizeNum = 20;
			int pageCurrentNum = 1;
			if (StringUtils.isNotBlank(pageCurrent)) {
				pageCurrentNum = Integer.valueOf(pageCurrent);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			Page<KfPayTrade> page = new Page<KfPayTrade>();
			page.setPageNo(pageCurrentNum);
			page.setPageSize(pageSizeNum);
			params.put("page", page);
			Map<String, String> mapvo=mapVO.getData();
			mapvo.put("partnerId", session.getAttribute("memberCode").toString());
			mapvo.put("status",  "'0','1','2','3'");
			params.put("crossOrder", mapvo);
			params.put("partnerId", session.getAttribute("memberCode"));
			Map<String, Object> kfPayTradeOrderMap = settleCoreClientService.findCrossBorerOrderByMap(params);
			Page<Object> newPage = MapUtil.map2Object(Page.class, (Map<?, ?>) kfPayTradeOrderMap.get("page"));
			List<Map<String, Object>> infoList = (List<Map<String, Object>>) kfPayTradeOrderMap.get("list");
			for (Map<String, Object> map : infoList) {
				   List<Map> kfPayResources=(List<Map>)map.get("kfPayResources");
				   for (Map map2 : kfPayResources) {
					   String url=String.valueOf(map2.get("url"));
					   String signURL = myoss.signURL(url);
					   map2.put("url", signURL);
				}
				   Long comDate= (Long) map.get("createDate");
				   Date date = new Date(comDate);
			   	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				   map.put("createDate", sdf.format(date));
			}
			PageUtil pu = new PageUtil(pageCurrentNum, pageSizeNum, newPage.getTotalCount());// 分页处理
			result.put("infoList", infoList);
			result.put("pu", pu);
			} catch (Exception e) {
			logger.error("跨境付款查询", e);
		}
		return "/base/crossBorerPay/crossBorerQuery";
	}
	
	
	/**
	 * 导出查询结果(excel)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/CrossBorerQueryController/crossBorerDownload.do")
	public String exportResults(Map<String, Object> resultMap, HttpServletRequest request, HttpServletResponse response,
			MapVO mapVO,HttpSession session) throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			mapVO.getData().put("status",  "'0','1','2','3'");
			params.put("crossOrder", mapVO.getData());
			params.put("partnerId", session.getAttribute("memberCode"));
			Map<String, Object> kfPayTradeOrderMap = settleCoreClientService.findCrossBorerOrderByMap(params);
			List<Map<String, Object>> infoList = (List<Map<String, Object>>) kfPayTradeOrderMap.get("list");
			List<KfPayTradeByDownload> kfPayTradeOrders = new ArrayList<KfPayTradeByDownload>();
			for (Map<String, Object> map : infoList) {
				kfPayTradeOrders.add(MapUtil.map2Object(KfPayTradeByDownload.class, map));
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("跨境付款下载.xls").getBytes("UTF-8"), "ISO8859_1"));
			resultMap.put("incomeDetailList", kfPayTradeOrders);
			resultMap.put("nowDate", new Date());
		} catch (Exception e) {
			logger.error("跨境付款下载错误", e);
		}
		return "/base/crossBorerPay/crossBorerQueryDownload";
	}
}
