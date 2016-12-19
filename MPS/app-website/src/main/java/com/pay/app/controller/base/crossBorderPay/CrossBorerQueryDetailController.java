package com.pay.app.controller.base.crossBorderPay;

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
import com.pay.fi.dto.KfPayTradeDetail;
import com.pay.inf.dao.Page;
import com.pay.util.MapUtil;

@Controller
public class CrossBorerQueryDetailController {

	Log logger = LogFactory.getLog(CrossBorerQueryDetailController.class);

	@Autowired
	@Qualifier(value = "settleCoreClientService")
	private SettleCoreClientService settleCoreClientService;

	@Autowired
	@Qualifier(value = "acc-acctService")
	private AcctService acctService;

	@RequestMapping(value = "/corp/CrossBorerQueryDetailController/into.do")
	public String into() {
		return "/base/crossBorerPay/crossBorerQueryDetail";
	}

	@RequestMapping(value = "/CrossBorerQueryDetailController/query.do")
	public String query(MapVO mapVO, HttpServletRequest request,
			Map<String, Object> result, HttpSession session) {

		try {
			String pageCurrent = request.getParameter("pager_offset");
			int pageSizeNum = 20;
			int pageCurrentNum = 1;
			if (StringUtils.isNotBlank(pageCurrent)) {
				pageCurrentNum = Integer.valueOf(pageCurrent);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			Page<KfPayTradeDetail> page = new Page<KfPayTradeDetail>();
			page.setPageNo(pageCurrentNum);
			page.setPageSize(pageSizeNum);
			params.put("page", page);
			Map<String, String> mapVo=mapVO.getData();
			mapVo.put("partnerId", session.getAttribute("memberCode").toString());
			mapVo.put("outStatus", "'0','1','2','3','4'");			
			params.put("crossOrder", mapVo);
			Map<String, Object> kfPayTradeDetailOrderMap = settleCoreClientService
					.findCrossBorerDetailOrderByMap(params);
			Page<Object> newPage = MapUtil.map2Object(Page.class,
					(Map<?, ?>) kfPayTradeDetailOrderMap.get("page"));
			List<Map<String, Object>> infoList = (List<Map<String, Object>>) kfPayTradeDetailOrderMap
					.get("list");
			List<KfPayTradeDetail> kfPayTradeDetailOrders = new ArrayList<KfPayTradeDetail>();
			for (Map<String, Object> map : infoList) {
				kfPayTradeDetailOrders.add(MapUtil.map2Object(KfPayTradeDetail.class, map));
			}
			PageUtil pu = new PageUtil(pageCurrentNum, pageSizeNum,
					newPage.getTotalCount());// 分页处理
			result.put("infoList", kfPayTradeDetailOrders);
			result.put("pu", pu);
		} catch (Exception e) {
			logger.error("跨境付款明细查询", e);
		}
		return "/base/crossBorerPay/crossBorerQueryDetail";
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
	@RequestMapping(value = "/CrossBorerQueryDetailController/crossBorerDownload.do")
	public String exportResults(Map<String, Object> resultMap, HttpServletRequest request, HttpServletResponse response,
			MapVO mapVO,HttpSession session) throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			mapVO.getData().put("outStatus", "'0','1','2','3','4'");			
			params.put("crossOrder", mapVO.getData());
			params.put("partnerId", session.getAttribute("memberCode"));
			Map<String, Object> crossBorerOrderMap = settleCoreClientService.findCrossBorerDetailOrderByMap(params);
			List<Map<String, Object>> infoList = (List<Map<String, Object>>) crossBorerOrderMap.get("list");
			List<KfPayTradeDetail> crossBorerOrders = new ArrayList<KfPayTradeDetail>();
			for (Map<String, Object> map : infoList) {
				crossBorerOrders.add(MapUtil.map2Object(KfPayTradeDetail.class, map));
			}
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("跨境付款明细下载.xls").getBytes("UTF-8"), "ISO8859_1"));
			resultMap.put("incomeDetailList", crossBorerOrders);
			resultMap.put("nowDate", new Date());
		} catch (Exception e) {
			logger.error("跨境付款明细下载错误", e);
		}
		return "/base/crossBorerPay/crossBorerQueryDetailDownload";
	}
}
