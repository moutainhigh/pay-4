package com.pay.fi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.fi.dto.BatchRefundParamDTO;
import com.pay.fi.dto.ExpressTracking;
import com.pay.fi.dto.PartnerWebsiteConfig;
import com.pay.fi.hession.ExpressTrackingService;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.MapUtil;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * 运单管理
 * 
 * @author Steven Lee
 *
 */
public class TrackingMgrController extends MultiActionController {

	private final Log log = LogFactory.getLog(TrackingMgrController.class);
	private String queryView;
	private String exportinDetailCorp;
	private String batchErrorDetailView ;
	public String getExportinDetailCorp() {
		return exportinDetailCorp;
	}

	public void setExportinDetailCorp(String exportinDetailCorp) {
		this.exportinDetailCorp = exportinDetailCorp;
	}

	private ExpressTrackingService expressTrackingService;

	public void setQueryView(final String queryView) {
		this.queryView = queryView;
	}

	public void setExpressTrackingService(
			final ExpressTrackingService expressTrackingService) {
		this.expressTrackingService = expressTrackingService;
	}

	/**
	 * 默认进index
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		ModelAndView view = new ModelAndView(queryView);
		view.addObject("beginTime", new SimpleDateFormat("yyyy-MM-dd")
				.format(DateUtil.skipDateTime(new Date(), -2)));
		view.addObject("endTime", new SimpleDateFormat("yyyy-MM-dd")
				.format(DateUtil.skipDateTime(new Date(), 1)));
		view.addObject("uploadBeginTime", new SimpleDateFormat("yyyy-MM-dd 00:00")
		.format(DateUtil.skipDateTime(new Date(), -2)));
		view.addObject("uploadEndTime", new SimpleDateFormat("yyyy-MM-dd 00:00")
		.format(DateUtil.skipDateTime(new Date(), 1)));
		return view;
	}

	/**	发货单据查询
	 * 
	 * @param request
	 * @param response
	 * @param expressTracking
	 * @return 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final ExpressTracking expressTracking)
			throws IllegalAccessException, InvocationTargetException {
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		ModelAndView modelView = new ModelAndView(queryView);
		//导出标志
		String export = request.getParameter("export");
        //交易号
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		//运单号
		String trackingNo = request.getParameter("trackingNo");
		String checkStartTime = request.getParameter("checkStartTime");
		String checkEndTime = request.getParameter("checkEndTime");
		String uploadBeginTime = request.getParameter("uploadBeginTime") ;
		String uploadEndTime = request.getParameter("uploadEndTime") ;
		//新增两个字段
		String expressCom = request.getParameter("expressCom");
	    String queryUrl = request.getParameter("queryUrl");	
	    String auditTime = request.getParameter("auditTime");
	    String orderId =request.getParameter("orderId");
		String status = StringUtil.null2String(request.getParameter("status")) ;
		if(trackingNo!=null&&trackingNo.length()>0)
		       expressTracking.setTrackingNo(trackingNo);
		if(tradeOrderNo!=null&&tradeOrderNo.length()>0)
		       expressTracking.setTradeOrderNo(Long.valueOf(tradeOrderNo));
		if(beginTime!=null&&beginTime.length()>0)
		       expressTracking.setBeginTime(beginTime);
		if(endTime!=null&&endTime.length()>0)
			   expressTracking.setEndTime(endTime);
		if(checkStartTime!=null&&checkStartTime.length()>0)
			   expressTracking.setCheckStartTime(checkStartTime);
		if(checkEndTime!=null&&checkEndTime.length()>0)
			   expressTracking.setCheckEndTime(checkEndTime);
		if(expressCom!=null&&expressCom.length()>0)
				expressTracking.setExpressCom(expressCom);
		if(queryUrl!=null&&queryUrl.length()>0)
				expressTracking.setQueryUrl(queryUrl);
		if(status!=null&&status.length()>0)
		       expressTracking.setStatus(status);
		
		if(!StringUtil.isEmpty(tradeOrderNo)){
			expressTracking.setTradeOrderNo(Long.valueOf(tradeOrderNo));
		}
		if(StringUtils.isNotEmpty(uploadBeginTime)){
			expressTracking.setUploadBeginTime(uploadBeginTime);
		}
		if(StringUtils.isNotEmpty(uploadEndTime)){
			expressTracking.setUploadEndTime(uploadEndTime);
		}
		expressTracking.setPartnerId(memberCode);
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 20; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码
		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 20;//
		}	

		Page page = new Page();
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		
		try {
			@SuppressWarnings("rawtypes")
			Map resultMap = null ;
			if (StringUtils.isNotBlank(export)) {
				resultMap = expressTrackingService.expressTrackingQuery(expressTracking, null);
			}else{
				resultMap = expressTrackingService.expressTrackingQuery(expressTracking, page);
			}
				
			List<Map> returnMap = (List<Map>) resultMap.get("list");
			Map pageMap = (Map) resultMap.get("page");
			page = MapUtil.map2Object(Page.class, pageMap);
			
			List<ExpressTracking> list = MapUtil.map2List(ExpressTracking.class,
				returnMap);
			if(list!=null){
				for(int i=0;i<list.size();i++){
					ExpressTracking tracking = list.get(i);
					String amount=tracking.getOrderAmount();
					BigDecimal tmp = new BigDecimal(amount);
					
					BigDecimal tmp2 = tmp.multiply(new BigDecimal("0.001"));
					tracking.setOrderAmount(tmp2.toString());
					list.set(i, tracking);
				}
			}
			PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;
			resultMap.put("resultList", list); 
			if (StringUtils.isNotBlank(export)) {
				return downLoadFile2(request, response, list);
			}
			return new ModelAndView(queryView, resultMap).addObject("pu", pu)
					.addObject("status", status)
					.addObject("tradeOrderNo", tradeOrderNo)
					.addObject("trackingNo", trackingNo)
					.addObject("beginTime", beginTime)
					.addObject("endTime", endTime)
					.addObject("checkStartTime", checkStartTime)
					.addObject("checkEndTime", checkEndTime)
					.addObject("uploadBeginTime", uploadBeginTime)
					.addObject("uploadEndTime", uploadEndTime)
					.addObject("orderId", orderId);
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = "";
			modelView.addObject("errorMsg", e.getMessage() + errorCode);
			PageUtil pu = new PageUtil(0, 0, 0);// 分页处理
			modelView.addObject("pu", pu);
			return modelView;
		}
	}

	public ModelAndView SaveTracking(final HttpServletRequest request,
			final HttpServletResponse response, final ExpressTracking expressTracking)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		expressTracking.setStatus("1");
		JSONObject json = new JSONObject();
		List<ExpressTracking> expressTrackings = new ArrayList<ExpressTracking>();
		expressTrackings.add(expressTracking);
		if (!expressTrackingService.expressTrackingUpdate(expressTrackings)) {
			json.put("result", "error");
			json.put("message", "更新失败");
		} else {
			json.put("result", "success");
			json.put("message", "保存成功");
		}
		SpringControllerUtils.renderText(response, json.toString());
		return null;
	}
	/**
	 * 导出查询结果(excel)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportResult(final HttpServletRequest request,
			final HttpServletResponse response, final Object dateList) throws Exception {
		setResonseHeader(request, response);
		return new ModelAndView(exportinDetailCorp).addObject(
				"resultList", dateList).addObject("nowDate", new Date());
	}
	
	/**
	 * 设置文件下载响应
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void setResonseHeader(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("运单查询明细列表.xls").getBytes("UTF-8"),
						"ISO8859_1"));
	}
	public ModelAndView EditTracking(final HttpServletRequest request,
			final HttpServletResponse response, final ExpressTracking expressTracking)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		expressTracking.setStatus("1");
		JSONObject json = new JSONObject();
		List<ExpressTracking> expressTrackings = new ArrayList<ExpressTracking>();
		expressTrackings.add(expressTracking);
		if (!expressTrackingService.expressTrackingUpdate(expressTrackings)) {
			json.put("result", "error");
			json.put("message", "运单号已存在");
		} else {
			json.put("result", "success");
			json.put("message", "修改成功");
		}
		SpringControllerUtils.renderText(response, json.toString());
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param websiteConfig
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downLoadFile(final HttpServletRequest request,
			final HttpServletResponse response, final PartnerWebsiteConfig websiteConfig)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		// MultiUploadForm multiUploadForm = (MultiUploadForm) form;
		String path = getServletContext().getRealPath(
				"/ftl/crosspay/trackingmgr/Template");

		String fileName = request.getParameter("fileName");
		FileInputStream fi = new FileInputStream(path + "//" + fileName);
		byte[] bt = new byte[fi.available()];
		fi.read(bt);
		response.setContentType("application/msdownload;charset=UTF-8");

		// System.out.print(response.getContentType());
		response.setCharacterEncoding("UTF-8");
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null;
	}
	public ModelAndView downLoadFile2(final HttpServletRequest request,
			final HttpServletResponse response, List<ExpressTracking> list)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		// MultiUploadForm multiUploadForm = (MultiUploadForm) form;
		String path = getServletContext().getRealPath(
				"/ftl/crosspay/trackingmgr/Template");

		String fileName = request.getParameter("fileName");
		String fullpath=path + "//" + "Express_Template.xls";
		FileInputStream fi = new FileInputStream(fullpath);
		//writeXlsx(list, fullpath);
		writeXls(list, fullpath);
		
		byte[] bt = new byte[fi.available()];
		fi.read(bt);
		response.setContentType("application/msdownload;charset=UTF-8");

		// System.out.print(response.getContentType());
		response.setCharacterEncoding("UTF-8");
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null;
	}
	public void writeXlsx(List<ExpressTracking> list, String path) throws Exception {
        if (list == null) {
            return;
        }
        //XSSFWorkbook
        int countColumnNum = list.size();
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("运单查询明细列表");
        // option at first row.
        XSSFRow firstRow = sheet.createRow(0);
        XSSFCell[] firstCells = new XSSFCell[countColumnNum];
        String[] options = { "商户订单号", "所属网站", "交易时间", "运单号", "上传时间", "运单状态", "运单查询网站" , "快点公司名称" };
        for (int j = 0; j < options.length; j++) {
            firstCells[j] = firstRow.createCell(j);
            firstCells[j].setCellValue(new XSSFRichTextString(options[j]));
        }
        //
        for (int i = 0; i < countColumnNum; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            ExpressTracking expressTracking = list.get(i);
            for (int column = 0; column < options.length; column++) {
                XSSFCell orderId = row.createCell(0);
                XSSFCell siteId = row.createCell(1);
                XSSFCell createDate = row.createCell(2);
                XSSFCell trackingNo = row.createCell(3);
                XSSFCell createDate1 = row.createCell(4);
                XSSFCell status = row.createCell(5);
                XSSFCell queryUrl = row.createCell(6);
                XSSFCell expressCom = row.createCell(7);
                orderId.setCellValue(expressTracking.getOrderId());
                siteId.setCellValue(expressTracking.getSiteId());
                createDate.setCellValue(expressTracking.getCreateDate());
                trackingNo.setCellValue(expressTracking.getTrackingNo());
                createDate1.setCellValue(expressTracking.getCreateDate());
				if ("0".equals(expressTracking.getStatus())) {
					expressTracking.setStatus("未上传");
				} else if ("1".equals(expressTracking.getStatus())) {
					expressTracking.setStatus("已上传");
				} else if ("2".equals(expressTracking.getStatus())) {
					expressTracking.setStatus("已审核");
				} else if ("3".equals(expressTracking.getStatus())) {
					expressTracking.setStatus("审核未通过");
				} else {
					expressTracking.setStatus("未上传");
				}
                status.setCellValue(expressTracking.getStatus());
                queryUrl.setCellValue(expressTracking.getQueryUrl());
                expressCom.setCellValue(expressTracking.getExpressCom());
            }
        }
        File file = new File(path);
        OutputStream os = new FileOutputStream(file);
        book.write(os);
        os.close();
    }
	public void writeXls(List<ExpressTracking> list, String path) throws Exception {
        if (list == null) {
            return;
        }
        int countColumnNum = list.size();
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("运单查询明细列表");
        // option at first row.
        
        
        String[] options = { "商户订单号", "所属网站", "交易时间", "运单号", "上传时间", "运单状态", "运单查询网站" , "快点公司名称" };
        HSSFRow row0 = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        HSSFCell titleCell1=row0.createCell(0);
        HSSFCell titleCell2=row1.createCell(0);
        String title="下载时间 ："+DateUtil.formatDateTime("yyyy/MM/dd",new Date());
        titleCell1.setCellValue(new HSSFRichTextString("==============运单查询明细列表=============="));
        titleCell2.setCellValue(title);
        HSSFRow firstRow = sheet.createRow(2);
        HSSFCell[] firstCells = new HSSFCell[options.length];
        for (int j = 0; j < options.length; j++) {
            firstCells[j] = firstRow.createCell(j);
            firstCells[j].setCellValue(new HSSFRichTextString(options[j]));
        }
        //
        for (int i = 0; i < countColumnNum; i++) {
            HSSFRow row = sheet.createRow(i + 3);
            ExpressTracking expressTracking = list.get(i);
            if ("0".equals(expressTracking.getStatus())) {
				expressTracking.setStatus("未上传");
			} else if ("1".equals(expressTracking.getStatus())) {
				expressTracking.setStatus("已上传");
			} else if ("2".equals(expressTracking.getStatus())) {
				expressTracking.setStatus("已审核");
			} else if ("3".equals(expressTracking.getStatus())) {
				expressTracking.setStatus("审核未通过");
			} else {
				expressTracking.setStatus("未上传");
			}
            for (int column = 0; column < options.length; column++) {
            	HSSFCell orderId = row.createCell(0);
            	HSSFCell siteId = row.createCell(1);
            	HSSFCell createDate = row.createCell(2);
            	HSSFCell trackingNo = row.createCell(3);
            	HSSFCell uploadeDate = row.createCell(4);
            	HSSFCell status = row.createCell(5);
            	HSSFCell queryUrl = row.createCell(6);
            	HSSFCell expressCom = row.createCell(7);
                orderId.setCellValue(expressTracking.getOrderId());
                siteId.setCellValue(expressTracking.getSiteId());
                String date1 = DateUtil.formatDateTime("yyyy/MM/dd",expressTracking.getCreateDate()); 
                //上传文件取当前日期
                String uploadeDates = DateUtil.formatDateTime("yyyy/MM/dd",new Date()); 
                createDate.setCellValue(date1);
                trackingNo.setCellValue(expressTracking.getTrackingNo());
                uploadeDate.setCellValue(uploadeDates);
                status.setCellValue(expressTracking.getStatus());
                queryUrl.setCellValue(expressTracking.getQueryUrl());
                expressCom.setCellValue(expressTracking.getExpressCom());
            }
        }
        File file = new File(path);
        OutputStream os = new FileOutputStream(file);
        book.write(os);
        os.close();
    }
	/**
	 * 需求更改运单批量上传[xls形式， 原cvs中交易号改为商户订单号]
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView submitUploade(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
			String memberCode = (String) request.getSession().getAttribute("memberCode");
			try {
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;
				MultipartFile multiPartFile = dmRequest.getFile("file_trackinguploade");
				PrintWriter outPrintWriter = response.getWriter();
				if (multiPartFile != null) {
					Workbook book = Workbook.getWorkbook(multiPartFile.getInputStream()) ;
					@SuppressWarnings("unchecked")
					List<ExpressTracking> expressTrackings = ExcelUtils.getListByReadShell(book.getSheet(0), 3, 0, 8,
							new String[] { "orderId", "siteId","createDate","trackingNo","createDate", "status","queryUrl",
									"expressCom" }, ExpressTracking.class);
					List<ExpressTracking> targetList = new ArrayList<ExpressTracking>() ;
					List<ExpressTracking> errorList = new ArrayList<ExpressTracking>() ;
					if(CollectionUtils.isNotEmpty(expressTrackings)){
						/*for(ExpressTracking expressTracking : expressTrackings){
							expressTracking.setStatus("1");
							expressTracking.setUploadeDate(new Date());
							expressTracking.setPartnerId(memberCode);
						}*/
						//运单上传逻辑修改
						for(ExpressTracking expressTracking : expressTrackings){
							//商户订单号
							String orderId = expressTracking.getOrderId() ;
							//运单号
							String trackingNo = expressTracking.getTrackingNo() ;
							//运单查询网站
							String queryUrl = expressTracking.getQueryUrl() ;
							//快递公司
							String expressCom = expressTracking.getExpressCom() ;
							if(StringUtils.isNotBlank(orderId)){
								//当商户订单号不为空时，判断商户订单号是否在系统存在
								ExpressTracking expressTrackingForQuery = new ExpressTracking() ;
								expressTrackingForQuery.setOrderId(orderId);
								expressTrackingForQuery.setPartnerId(memberCode);
								Map resultMap = this.expressTrackingService.expressTrackingQuery(expressTrackingForQuery, null);
								List<Map> returnMap = (List<Map>) resultMap.get("list");
								//匹配到有该笔交易
								if(CollectionUtils.isNotEmpty(returnMap)){
									if(StringUtils.isNotBlank(trackingNo)){
										if(StringUtils.isNotBlank(queryUrl)){
											if(StringUtils.isNotBlank(expressCom)){
												//补全合规的运单记录
												expressTracking.setStatus("1");
												expressTracking.setUploadeDate(new Date());
												expressTracking.setPartnerId(memberCode);
												targetList.add(expressTracking) ;
											}else{
												expressTracking.setUploadFailRemark("快递公司名称不能为空");
												errorList.add(expressTracking) ;
											}
										}else{
											expressTracking.setUploadFailRemark("运单查询网站不能为空");
											errorList.add(expressTracking) ;
										}
									}else{
										expressTracking.setUploadFailRemark("运单号不能为空");
										errorList.add(expressTracking) ;
									}	
								}else{
									expressTracking.setUploadFailRemark("商户订单号不存在");
									errorList.add(expressTracking) ;
								}
							}else{
								expressTracking.setUploadFailRemark("商户订单号不能为空");
								errorList.add(expressTracking) ;
							}
						}
					}else{
						String errormsg = "上传数据不能为空！" ;
						outPrintWriter.println("<script>parent.callback('N|"
								+ errormsg + "')</script>");
						return null ;
					}
					//上传失败
					if (CollectionUtils.isNotEmpty(targetList) &&  !expressTrackingService
							.expressTrackingUpdate(targetList)) { 
						String errormsg = "运单上传失败,请重试！";
						outPrintWriter.println("<script>parent.callback('N|"
								+ errormsg + "')</script>");
					}else {
						//上传处理上功
						if(CollectionUtils.isEmpty(errorList)){
							response.getWriter().println("<script>parent.callback('Y|上传处理完成')</script>");
						}else{
							//存在批处理失败, 需要展示详情
							int successSize = targetList.size() ;
							JSONArray jsonArray = JSONArray.fromObject(errorList) ;
							String jStr = jsonArray.toString() ;
							response.getWriter().println("<script>parent.callback('D|"+ jStr +"|"+ successSize +"')</script>");
						}
					}
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				String errormsg = "运单批量上传失败，请按照下载模板填写数据上传!";
				response.getWriter().println("<script>parent.callback('N|"
						+ errormsg + "')</script>");
			} 
		return null ;
	}
	
	/**
	 * 运单批量上传失败详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBatchErrorDetail(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(batchErrorDetailView);
		try {
			String success = StringUtil.null2String(request.getParameter("success")) ;
			String batchErrorDetail = "[]";
			try {
				batchErrorDetail = URLDecoder.decode(URLDecoder.decode(StringUtil.null2String(request.getParameter("batchErrorDetail")), "UTF-8"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = JSONArray.fromObject(batchErrorDetail) ;
			@SuppressWarnings("unchecked")
			List<ExpressTracking> list = JSONArray.toList(jsonArray, ExpressTracking.class) ;
			mv.addObject("success", success).addObject("list", list) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv ;
	}
	/**
	 * 原运单批量上传上，csv方式
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView submitUploade_old(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;

			MultipartFile multiPartFile = dmRequest
					.getFile("file_trackinguploade");
			if (multiPartFile != null) {
				PrintWriter outPrintWriter = response.getWriter();
				InputStream fis = null;
				InputStreamReader isw = null;
				BufferedReader br = null;
				try {
					// 处理数据
					fis = multiPartFile.getInputStream();
					isw = new InputStreamReader(fis, "GBK");
					br = new BufferedReader(isw);

					// 读取直到最后一行
					String line = "";
					int successCount = 0;// 成功处理数目
					int totalLine = 0;// 总数目
					List<ExpressTracking> expressTrackings = new ArrayList<ExpressTracking>();
					ExpressTracking expressTracking;
					// JSONObject json = new JSONObject();
					while ((line = br.readLine()) != null) {
						if (line.trim().isEmpty()) {
							continue;
						}
						totalLine++;
						System.out.print(totalLine + " " + line);
						String[] arrStrings = line.split(",");

						if (arrStrings.length != 4) {
							String errormsg = "第" + totalLine + "行数据格式不正确";
							outPrintWriter
									.println("<script>parent.callback('N|"
											+ errormsg + "')</script>");
							return null;
						} else if (arrStrings[0] == null
								|| arrStrings[0].trim().isEmpty()) {
							String errormsg = "第" + totalLine + "行交易号为空。";
							outPrintWriter
									.println("<script>parent.callback('N|"
											+ errormsg + "')</script>");
							return null;
						} else if (arrStrings[0].contains("交易号")) {
							totalLine = totalLine - 1;
							continue;
						} else if (arrStrings.length == 4
								&& !arrStrings[0].isEmpty()) {
							expressTracking = new ExpressTracking();
							expressTracking.setTradeOrderNo(Long.valueOf(arrStrings[0]));
//							expressTracking.setTradeOrderNo(arrStrings[0]);
							expressTracking.setTrackingNo(arrStrings[1]);
							expressTracking.setExpressCom(arrStrings[2]);
							expressTracking.setQueryUrl(arrStrings[3]);
							expressTracking.setStatus("1");
							expressTracking.setUploadeDate(new Date());
							expressTracking.setPartnerId(memberCode);
							expressTrackings.add(expressTracking);
						}
					}

					if (!expressTrackingService
							.expressTrackingUpdate(expressTrackings)) {
						String errormsg = "交易运单号上传失败";
						outPrintWriter.println("<script>parent.callback('N|"
								+ errormsg + "')</script>");
						return null;
					} else {
						outPrintWriter
								.println("<script>parent.callback('Y|上传处理完成')</script>");
					}
					if (successCount == totalLine) {
						outPrintWriter
								.println("<script>parent.callback('Y|上传处理完成')</script>");
					} else {
						String msg = "成功上传" + successCount + "条数据，余"
								+ (totalLine - successCount) + "条数据未处理";
						outPrintWriter.println("<script>parent.callback('Y|"
								+ msg + "')</script>");
					}
				} catch (Exception k) {
					k.printStackTrace();
				} finally {
					outPrintWriter.flush();
					outPrintWriter.close();
					br.close();
					isw.close();
					fis.close();
				}
			}
		} catch (Exception k) {
			k.printStackTrace();
		}
		return null;
	}

	public void setBatchErrorDetailView(String batchErrorDetailView) {
		this.batchErrorDetailView = batchErrorDetailView;
	}
	
	
	
}







