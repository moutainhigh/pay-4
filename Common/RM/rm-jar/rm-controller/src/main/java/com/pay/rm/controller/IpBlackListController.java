package com.pay.rm.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import au.com.bytecode.opencsv.CSVWriter;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.rm.service.model.GatewayIpBlacklist;
import com.pay.rm.service.service.IpBlackListService;
import com.pay.util.DateUtil;

public class IpBlackListController extends MultiActionController {

	private String viewName;
	private String detailView;
	private String addView;
	private String editView;
	private IpBlackListService ipBlackListService;
	private final Log logger = LogFactory.getLog(IpBlackListController.class);

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map resultMap = new HashMap();
		initSearchDate(resultMap);
		return new ModelAndView(viewName, resultMap);
	}

	private HashMap<String, String> getParameter(HttpServletRequest request) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String ipaddr = request.getParameter("ipaddr");
		String orderNo = request.getParameter("status");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("beginDate", startDate);
		params.put("endDate", endDate);
		params.put("ipaddr", ipaddr);
		params.put("status", orderNo);
		return params;
	}

	public ModelAndView checkIpAddr(HttpServletRequest request,
			HttpServletResponse response) {
		String ipaddr = request.getParameter("ipaddr");
		boolean isExist = ipBlackListService.isExistIpBlack(ipaddr);
		try {
			if (isExist)
				response.getWriter().print("ipRepeat");
			response.getWriter().print("yes");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ModelAndView createIpBlack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ipaddr = request.getParameter("ipaddr");
		String desc = request.getParameter("desc");
		String flag = request.getParameter("flag");
		Map resultMap = new HashMap();
		if ("1".equals(flag)) {
			boolean bret = ipBlackListService.saveIpBlack(ipaddr, desc);
			if (bret) {
				resultMap.put("message", "新增IP黑名单成功");
			} else {
				resultMap.put("message", "新增IP黑名单失败");
			}
			initSearchDate(resultMap);
			return new ModelAndView(viewName, resultMap);
		} else {
			return new ModelAndView(addView, resultMap);
		}
	}

	public ModelAndView editIpBlack(HttpServletRequest request,
			HttpServletResponse response) {
		String desc = request.getParameter("desc");
		String ipBlackNo = request.getParameter("ipBlackNo");
		String flag = request.getParameter("flag");
		Map resultMap = new HashMap();

		if ("1".equals(flag)) {
			boolean bret = ipBlackListService.editIpBlack(ipBlackNo, desc);
			if (bret) {
				resultMap.put("message", "修改IP黑名单成功");
			} else {
				resultMap.put("message", "修改IP黑名单失败");
			}
			initSearchDate(resultMap);
			return new ModelAndView(viewName, resultMap);
		} else if (!ipBlackNo.isEmpty()) {
			GatewayIpBlacklist ipBlack = ipBlackListService
					.queryGatewayIpBlacklist(ipBlackNo);
			resultMap.put("dto", ipBlack);
			return new ModelAndView(editView, resultMap);
		}
		return null;
	}

	public ModelAndView updateStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ipaddr = request.getParameter("ipBlackNo");
		String desc = request.getParameter("status");
		Map resultMap = new HashMap();

		boolean bret = ipBlackListService.updateStatus(ipaddr, desc);
		// if(bret){
		// resultMap.put("ret", "新增IP黑名单成功");
		// }else{
		// resultMap.put("ret", "新增IP黑名单失败");
		// }
		// initSearchDate(resultMap);
		return index(request, response);
	}

	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) {
		Page<GatewayIpBlacklist> page = PageUtils.getPage(request);
		HashMap<String, String> params = getParameter(request);
		List<GatewayIpBlacklist> listIpBlack = ipBlackListService
				.queryIpBlackList(params, page.getPageSize(), page.getPageNo());
		page.setResult(listIpBlack);
		page.setTotalCount(ipBlackListService.countIpBlackList(params));
		return new ModelAndView(detailView).addObject("page", page);
	}

	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Page page = PageUtils.getPage(request);
		HashMap params = getParameter(request);
		List<GatewayIpBlacklist> listSuspectOrder = ipBlackListService
				.queryIpBlackList(params);
		genreateFile(response, listSuspectOrder);
		return null;
	}

	private void genreateFile(HttpServletResponse response,
			List<GatewayIpBlacklist> listIpBlack) {

		logger.info("---@poss IpBalckListController.genreateFile 文件生成开始:size:-->"
				+ listIpBlack.size());

		ZipOutputStream zFile = null;
		String fileName = "IP黑名单";
		try {
			ServletOutputStream out = response.getOutputStream();
			zFile = new ZipOutputStream(out);
			zFile.setEncoding("gbk");
			zFile.putNextEntry(new ZipEntry(fileName + ".csv"));
			CSVWriter writer = new CSVWriter(new OutputStreamWriter(zFile,
					"gbk"));
			String[] title;
			String[] content = new String[15];
			// 第一先写入TITLE
			title = new String[] { "创建时间", "IP地址", "状态", "更新时间 ", "禁用次数",
					"激活次数" };

			writer.writeNext(title);
			writer.flush();

			// 循环写结果集
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			for (int i = 0; i < listIpBlack.size(); i++) {
				GatewayIpBlacklist dto = listIpBlack.get(i);
				content[0] = DateUtil.dateToStr(dto.getCreateDate(),
						"yyyy-MM-dd HH:mm:ss");
				content[1] = dto.getIpAddress();
				content[2] = getStatus(dto.getStatus());
				content[3] = DateUtil.dateToStr(dto.getUpdateDate(),
						"yyyy-MM-dd HH:mm:ss");
				content[4] = dto.getDisableCount().toString();
				content[5] = dto.getEnableCount().toString();
				writer.writeNext(content);
				writer.flush();
			}
			StringBuffer sb = new StringBuffer();

			sb.append("共计：");
			sb.append(listIpBlack.size());
			sb.append("记录");
			writer.writeNext(new String[] { sb.toString() });
			writer.flush();

			zFile.closeEntry();
			zFile.close();
			logger.info("---@poss ConstingSearchController.genreateFile 文件生成结束");
		} catch (IOException ex) {
			logger.error("---@poss ConstingSearchController.genreateFile 文件生成异常:--"
					+ ex);
			if (zFile != null) {
				try {
					zFile.close();
				} catch (IOException e) {
					logger.error("---@poss ConstingSearchController.genreateFile 文件生成异常:--"
							+ e);
				}
			}
		}
	}

	private void initSearchDate(Map map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("startDate", new SimpleDateFormat("yyyy-MM-dd").format(DateUtil
				.skipDateTime(new Date(), -3)));
		map.put("endDate", new SimpleDateFormat("yyyy-MM-dd").format(DateUtil
				.skipDateTime(new Date(), 0)));
	}

	private String getStatus(String status) {
		if (status.equals("1"))
			return "激活";
		else if (status.equals("2"))
			return "禁用";
		return "";
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setEditView(String editView) {
		this.editView = editView;
	}

	public void setIpBlackListService(IpBlackListService ipBlackListService) {
		this.ipBlackListService = ipBlackListService;
	}
}
