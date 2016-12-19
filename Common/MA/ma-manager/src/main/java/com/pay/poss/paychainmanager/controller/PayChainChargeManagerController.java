package com.pay.poss.paychainmanager.controller;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.paychainmanager.dto.PayChainExternalLog;
import com.pay.poss.paychainmanager.dto.PayChainManagerDto;
import com.pay.poss.paychainmanager.dto.PayChainStatDto;
import com.pay.poss.paychainmanager.enums.EffectiveTypeEnum;
import com.pay.poss.paychainmanager.service.PayChainManagerService;
import com.pay.util.DateUtil;

/**
 * 
 * @Description 支付链接收款管理
 * @project ma-manager
 * @file PayChainChargeManagerController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2011-09-20 tianqing_wang Create
 */

public class PayChainChargeManagerController extends MultiActionController {

	private String queryView;
	private String listView;
	private String queryDetail;
	private String exportPayChainDetailPath;

	private PayChainManagerService payChainManagerService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 指定当前时间为最大值
		String maxDate = DateUtil.formatDateTime("yyyy-MM-dd HH:mm");
		String suffer = " 00:00";
		// 最大的结束时间，企业为maxDate + 1 天

		Map<String, Object> model = new HashMap<String, Object>();

		String startTime = DateUtil.skipDate(maxDate, -3) + suffer;
		String endTime = maxDate;

		model.put("startDate", startTime);
		model.put("endDate", endTime);

		// startDate
		// 此处处理日期显示

		return new ModelAndView(queryView, model);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView queryList(HttpServletRequest request,
			HttpServletResponse response, PayChainManagerDto dto)
			throws Exception {
		Page page = PageUtils.getPage(request);
		List<PayChainManagerDto> info = payChainManagerService
				.queryPayChainByCondition(dto, page);
		PayChainStatDto statDto = payChainManagerService.queryPayChainSum(dto);
		page.setResult(info);
		ModelAndView view = new ModelAndView(listView);
		view.addObject("page", page)
			.addObject("payChainStatDto", statDto)
			.addObject("EffectiveTypeEnums", EffectiveTypeEnum.values());		
		return view;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView operatePayChain(HttpServletRequest request,
			HttpServletResponse response, PayChainManagerDto dto)
			throws Exception {
		// 1：操作数据库修改T_PAY_CHAIN表的status
		Map paramMap = new HashMap();
		paramMap.put("operate", dto.getOperate());
		paramMap.put("payChainNumber", dto.getPayChainNumber());
		payChainManagerService.updatePayChainStatus(paramMap);
		// 2:操作完后回刷页面
		return new ModelAndView(queryView).addObject("payChainNumber",
				dto.getPayChainNumber());
	}
	
	public ModelAndView modifyPayChainDate(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		// 1：操作数据库修改T_PAY_CHAIN表的effectiveDate,overdueDate
		String payChainNumber = request.getParameter("payChainNumber");
		String strId = request.getParameter("id");
		Long id = Long.valueOf(strId);
		String effectiveDate = request.getParameter("effectiveType");
		int type = Integer.parseInt(effectiveDate);
		EffectiveTypeEnum typeEnum = EffectiveTypeEnum.getEffectiveEnum(type);
		
		payChainManagerService.modifyPayChainDate(id,typeEnum);
		// 2:操作完后回刷页面
		return new ModelAndView(queryView).addObject("payChainNumber",
				payChainNumber);
	}

	public ModelAndView queryDetail(HttpServletRequest request,
			HttpServletResponse response, PayChainManagerDto dto)
			throws Exception {

		PayChainManagerDto chainDto = payChainManagerService
				.queryPayChainManagerDto(dto);
        String addr=payChainManagerService.getEnterMemberAdress(chainDto.getRegions(), chainDto.getCity(), chainDto.getAddress(), chainDto.getZip());
        chainDto.setAddress(addr);
		Page page = PageUtils.getPage(request);
		PayChainExternalLog externalLog = new PayChainExternalLog();
		externalLog.setCardNo(dto.getPayChainNumber());
		externalLog.setProcessStatus(1);
		List<PayChainExternalLog> externalList = payChainManagerService
				.queryPayChainExternalLog(externalLog, page);
		// 2:操作完后回刷页面
		return new ModelAndView(queryDetail).addObject("chainDto", chainDto)
				.addObject("externalList", externalList)
				.addObject("page", page);
	}

	/**
	 * 下载excel
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downExcelPayChainDetail(HttpServletRequest request,
			HttpServletResponse response, PayChainManagerDto dto)
			throws Exception {

		PayChainExternalLog externalLog = new PayChainExternalLog();
		externalLog.setCardNo(dto.getPayChainNumber());
		externalLog.setProcessStatus(1);
		String fullPath = request.getSession().getServletContext()
				.getRealPath(exportPayChainDetailPath);
		HSSFWorkbook book;
		OutputStream os = null;
		try {
			// 创建workbook
			book = payChainManagerService.downExcelPayChainDetail(externalLog,
					dto, fullPath);
			// 设置格式
			// 输出
			response.setContentType("application/msexcel;charset=GBK");
			response.addHeader(
					"Content-Disposition",
					(new StringBuilder("attachment;   filename=\""))
							.append(new String("支付链收款记录.xls".getBytes("GB2312"),
									"ISO-8859-1")).append("\"").toString());
			os = response.getOutputStream();
			book.write(os);
			os.flush();
		} catch (FileNotFoundException e) {
			logger.error("写excel报错，模板文件不存在", e);
		} catch (Exception e) {
			logger.error("写excel报错IO", e);
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				logger.error("关闭流出错", e);
			}
		}
		return null;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public String getQueryView() {
		return queryView;
	}

	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}

	public void setQueryDetail(String queryDetail) {
		this.queryDetail = queryDetail;
	}

	public PayChainManagerService getPayChainManagerService() {
		return payChainManagerService;
	}

	public void setPayChainManagerService(
			PayChainManagerService payChainManagerService) {
		this.payChainManagerService = payChainManagerService;
	}

	public void setExportPayChainDetailPath(String exportPayChainDetailPath) {
		this.exportPayChainDetailPath = exportPayChainDetailPath;
	}

}
