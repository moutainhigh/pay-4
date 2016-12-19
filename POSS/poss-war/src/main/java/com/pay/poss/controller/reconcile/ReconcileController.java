/**
 *  File: FoReconcileQueryController.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.controller.reconcile;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ReconcileService;
import com.pay.poss.controller.fi.dto.BouncedOrderVO;
import com.pay.poss.controller.fi.dto.ReconcileImportRecordDetailDto;
import com.pay.poss.dto.ReconcileBatchDTO;
import com.pay.util.MapUtil;
import com.pay.util.SimpleExcelGenerator;
import com.pay.util.StringUtil;

/**
 * 查询对账结果Controller
 * 
 * @author delin.dong
 */
public class ReconcileController extends MultiActionController {

	private String index;
	
	private ReconcileService reconcileService;
	
	private String reconcileList;
	
	private String reconcileListDetail;
	
	public void setReconcileListDetail(String reconcileListDetail) {
		this.reconcileListDetail = reconcileListDetail;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public void setReconcileService(ReconcileService reconcileService) {
		this.reconcileService = reconcileService;
	}
	public void setReconcileList(String reconcileList) {
		this.reconcileList = reconcileList;
	}
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}
	
	public ModelAndView queryReconcile(HttpServletRequest request,HttpServletResponse response){
		String operator =request.getParameter("operator");
		String batchNo 	=request.getParameter("batchNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		ReconcileBatchDTO batchDTO=new ReconcileBatchDTO();
		batchDTO.setOperator(operator);
		batchDTO.setBatchNo(batchNo);
		try {
			if(StringUtils.isNotEmpty(endTime)){
				batchDTO.setEndTime(sdf.parse(endTime));
			}
			if(StringUtils.isNotEmpty(startTime)){
				batchDTO.setStartTime(sdf.parse(startTime));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Page page = PageUtils.getPage(request);
		Map resultMap=reconcileService.queryReconcile(batchDTO,page);
		List<Map> listMap = (List<Map>) resultMap.get("batchDTOs");
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		if(listMap!=null &&listMap.size()>0){
			for (Map map : listMap) {
				//几个时间
				Long createDate=(Long) map.get("createDate");
				Date date = new Date(createDate);
				map.put("createDate", date);
				
				//add by nico.shao 2016-08-17 时间都需要转换一下  
				Long batchEndTime = (Long)map.get("batchEndTime");
				if(batchEndTime !=null){
					Date batchEndDate = new Date(batchEndTime);
					map.put("batchEndTime", batchEndDate);
				}

			}
		}
		return new ModelAndView(reconcileList).addObject("batchDTOs", listMap).addObject("page",page);
	}
	
	public ModelAndView queryReconcileDetail(HttpServletRequest request,HttpServletResponse response)throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		String batchNo=request.getParameter("batchNo");
		String status=StringUtil.null2String(request.getParameter("status"));//添加状态查询条件 add by davis.guo at 2016-08-11
		//Page page = PageUtils.getPage(request);
		Map resultMap=reconcileService.queryReconcileDetail(batchNo, status);
		List<Map> listMap = (List<Map>) resultMap.get("batchDetailDTOs");
		//Map pageMap = (Map) resultMap.get("page");
	//	page = MapUtil.map2Object(Page.class, pageMap);
		if(listMap!=null && listMap.size()>0){
			System.out.println("listMap.size()="+listMap.size());
		for (Map map : listMap) {
			Long createDate=(Long)map.get("createDate");
			Date date = new Date(createDate);
			map.put("createDate", date);
			}
		}
		return new ModelAndView(reconcileListDetail).addObject("batchDetails",listMap)
				.addObject("batchNo",batchNo);//add by davis.guo 2016-08-11
	}

	/**
	 * 订单对账结果详情 下载
	 * @author Davis.guo at 2016-08-11
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String batchNo = StringUtil.null2String(request.getParameter("batchNo"));
		String status = StringUtil.null2String(request.getParameter("status"));

		Map resultMap=reconcileService.queryReconcileDetail(batchNo, status);
		List<Map> listMap = (List<Map>) resultMap.get("batchDetailDTOs");

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(listMap!=null && listMap.size()>0){
			List<ReconcileImportRecordDetailDto> reconcileDetails = MapUtil.map2List(ReconcileImportRecordDetailDto.class, listMap);
			for (ReconcileImportRecordDetailDto reconcile : reconcileDetails) {

				Date createDate = reconcile.getCreateDate();
				if(createDate != null)reconcile.setCreateDateStr(sd.format(createDate));
				Date transactionDate = reconcile.getTransactionDate();
				if(transactionDate != null)reconcile.setTransactionDateStr(sd.format(transactionDate));
				Date postingDate = reconcile.getPostingDate();
				if(postingDate != null)reconcile.setPostingDateStr(sd.format(postingDate));
				switch(reconcile.getStatus())
				{
					case 0: reconcile.setStatusMsg("未对账");break;
					case 1: reconcile.setStatusMsg("对账成功");break;
					case 2: reconcile.setStatusMsg("对账失败");break;
					case 4: reconcile.setStatusMsg("对账成功，记账失败");break;
					default : reconcile.setStatusMsg(""+reconcile.getStatus());break;
				}

				if (reconcile.getTransAmount() != null && !reconcile.getTransAmount().equals("")) {
					reconcile.setTransAmount(new BigDecimal(reconcile
							.getTransAmount()).divide(new BigDecimal(1000)).toString());//new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP
				}
			}
			//对账批次号	渠道订单号		referNum	上传时间	操作员	对账状态	备注
			try {
				String[] headers = new String[] { "对账批次号", "渠道订单号", "参考号", "上传时间", "操作员", "对账状态", "备注" };
				String[] fields = new String[] { "batchNoDetail", "channelOrderNo", "merchTranRef", "createDateStr",
						"operator", "statusMsg", "remark" };
				Workbook grid = SimpleExcelGenerator
						.generateGrid("对账结果详情", headers, fields, reconcileDetails);
				Date myDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
				String dd = sdf.format(myDate);
				response.setContentType("application/force-download");
				response.setContentType("applicationnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + dd + ".xls");
				grid.write(response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
}
