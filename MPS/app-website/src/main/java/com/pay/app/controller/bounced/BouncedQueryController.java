package com.pay.app.controller.bounced;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.TxncoreClientService;
import com.pay.base.fi.service.ChargeBackOrderService;
import com.pay.inf.dao.Page;
import com.pay.util.MapUtil;

/**
 * 拒付调单查询
 * @date 2016年5月25日15:13:39
 * @author delin.dong
 *
 */
public class BouncedQueryController extends MultiActionController{
	
	private TxncoreClientService txncoreClientService;
	
	private ChargeBackOrderService chargeBackOrderService ;
	
	private String downloadBounced;
	
	private String chargeBackSavePath ;
	
	private String bouncedFine;
	
	public void setBouncedFine(String bouncedFine) {
		this.bouncedFine = bouncedFine;
	}
	public void setDownloadBounced(String downloadBounced) {
		this.downloadBounced = downloadBounced;
	}
	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	private String index;
	
	private String bounced; 
	
	private String downBounced;

	public void setDownBounced(String downBounced) {
		this.downBounced = downBounced;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public void setBounced(String bounced) {
		this.bounced = bounced;
	}

	/**
	 * 调单查询页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ModelAndView  index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView exportResult=new ModelAndView(index);
		Map<String, Object> requetParam = this.requetParam(request);
		requetParam.put("mpscpTypes", "1,2");//0拒付1调单2内部调单
		String export=request.getParameter("export");//有值就下载
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd"); 
		if(StringUtils.isNotBlank(export)){
			Page<?> page = new Page() ;
			page.setPageNo(1);
			page.setPageSize(20000);
			requetParam.put("mpscpTypes", "1,2");
			requetParam.put("page", page);
			Map resultMap=txncoreClientService.queryBouncedOrder(requetParam);
			List<Map> result=(List<Map>) resultMap.get("result");
			if(result!=null&&!result.isEmpty()){
				for (Map map : result) {
					 if(map.get("tradeDate")!=null){
						 map.put("tradeDate", dateFormat.format(map.get("tradeDate")));
					 }
					 if(map.get("createDate")!=null){
						map.put("cpdDate", dateFormat.format(map.get("createDate"))) ;
					 }
					 if(map.get("latestAnswerDate")!=null){
						map.put("latestAnswerDate", dateFormat.format(map.get("latestAnswerDate")));
					 }
					 if(map.get("chargeBackAmount")!=null){
						map.put("chargeBackAmount",Long.valueOf((String)map.get("chargeBackAmount")));
					}
				}
				 exportResult = this.exportResult(request, response, result);
			}
			return exportResult.addObject("requetParam", requetParam);
		}		 
		Map resultMap=txncoreClientService.queryBouncedOrder(requetParam);
		List<Map> result=(List<Map>) resultMap.get("result");
		long nowDate = System.currentTimeMillis() ;
		if(result!=null&&!result.isEmpty()){
			for (Map map : result) {
				 if(map.get("tradeDate")!=null){
					 map.put("tradeDate", dateFormat.format(map.get("tradeDate")));
				 }
				 if(map.get("cpdDate")!=null){
					map.put("cpdDate", map.get("cpdDate")) ;
				 }
				 if(map.get("latestAnswerDate")!=null){
					map.put("latestAnswerDate", dateFormat.format(map.get("latestAnswerDate")));
					Date latestAnswerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("latestAnswerDate").toString() + " 18:00:00") ;
					long latestAnswerDateLong = latestAnswerDate.getTime() ;
					//appealFlag:申诉标志[1:可申诉2:不可申诉]
					if(nowDate<=latestAnswerDateLong ){
						map.put("appealFlag", "1") ;
					}else{
						map.put("appealFlag", "2") ;
					}
				 }
				 if(map.get("chargeBackAmount")!=null){
					map.put("chargeBackAmount",Long.valueOf((String)map.get("chargeBackAmount")));
				}
			}
		}
		if("".equals(requetParam.get("statusIn"))){
			if(requetParam.get("status")==""){
				requetParam.put("status", "8");
			}
		}else{
			requetParam.put("status", requetParam.get("statusIn"));
		}
		Map pageMap = (Map) resultMap.get("page");
		Page page = MapUtil.map2Object(Page.class, pageMap);	
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;

		return exportResult.addObject("requetParam", requetParam).addObject("resultList", result).addObject("pu", pu);
	}
	
	
	
	
	/**
	 * 拒付查询页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public ModelAndView bounced(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView exportResult=new ModelAndView(bounced);
		Map<String, Object> requetParam = this.requetParam(request);
		requetParam.put("bouncedFlag", "0");//拒付类型 0拒付 gc调单
		String export=request.getParameter("export");//有值就下载
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd"); 
		if(StringUtils.isNotBlank(export)){
			Page<?> page = new Page() ;
			page.setPageNo(1);
			page.setPageSize(20000);
			requetParam.put("bouncedFlag", "0");
			requetParam.put("page", page);
			Map resultMap=txncoreClientService.queryBouncedOrder(requetParam);
			List<Map> result=(List<Map>) resultMap.get("result");
			if(result!=null&&!result.isEmpty()){
				for (Map map : result) {
					 if(map.get("tradeDate")!=null){
						 map.put("tradeDate", dateFormat.format(map.get("tradeDate")));
					 }
					 if(map.get("createDate")!=null){
						map.put("cpdDate", dateFormat.format(map.get("createDate"))) ;
					 }
					 if(map.get("latestAnswerDate")!=null){
						map.put("latestAnswerDate", dateFormat.format(map.get("latestAnswerDate")));
					 }
					 if(map.get("chargeBackAmount")!=null){
						map.put("chargeBackAmount",Long.valueOf((String)map.get("chargeBackAmount")));
					}
				}
				 exportResult = this.exportResults(request, response, result);
			}
			return exportResult.addObject("requetParam", requetParam);
		}
		Map resultMap=txncoreClientService.queryBouncedOrder(requetParam);
		List<Map> result=(List<Map>) resultMap.get("result");
		long nowDate = System.currentTimeMillis() ;
		if(result!=null&&!result.isEmpty()){
			 for (Map map : result) {
				 if(map.get("tradeDate")!=null){
					 map.put("tradeDate", dateFormat.format(map.get("tradeDate")));
				 }
				 if(map.get("cpdDate")!=null){
					map.put("cpdDate", map.get("cpdDate")) ;
				 }
				 if(map.get("latestAnswerDate")!=null){
					map.put("latestAnswerDate", dateFormat.format(map.get("latestAnswerDate")));
					Date latestAnswerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("latestAnswerDate").toString() + " 18:00:00") ;
					long latestAnswerDateLong = latestAnswerDate.getTime() ;
					//appealFlag:申诉标志[1:可申诉2:不可申诉]
					if(nowDate<=latestAnswerDateLong ){
						map.put("appealFlag", "1") ;
					}else{
						map.put("appealFlag", "2") ;
					}
				 }
				 if(map.get("chargeBackAmount")!=null){
					map.put("chargeBackAmount",Long.valueOf((String)map.get("chargeBackAmount")));
				 }
			}
			
		}
		if("".equals(requetParam.get("statusIn"))){
			if(requetParam.get("status")==""){
				requetParam.put("status", "8");
			}
		}else{
			requetParam.put("status", requetParam.get("statusIn"));
		}
	 	Map pageMap = (Map) resultMap.get("page");
		Page page = MapUtil.map2Object(Page.class, pageMap);
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;
		return new ModelAndView(bounced).addObject("requetParam", requetParam).addObject("resultList", result).addObject("pu", pu);
		}
		
	/***
	 * 封装参数
	 * @param request
	 * @param map
	 * @return
	 */
	public Map<String, Object> requetParam(HttpServletRequest request){
		String orderNo= request.getParameter("orderNo");
		//审批状态：0未处理 1已上传资料2已递交银行3申诉失败待复核4申诉失败待复核5申诉失败6申诉成功7不申诉
		//MPS显示状态：0:未处理 1，2，3，4：处理中 5：申诉失败 6：申诉成功 7：不申诉
		String status= request.getParameter("status");
		String statusIn = "" ;
		if(StringUtils.isNotEmpty(status)){
			if(status.contains(",")){
				statusIn = status ;
				status = "" ;
			}
		}
		String tradeBeginTime= request.getParameter("tradeBeginTime");
		String tradeEndTime= request.getParameter("tradeEndTime");
		String createBeginTime= request.getParameter("createBeginTime");//
		String createEndTime= request.getParameter("createEndTime");
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		Page<?> page = this.packPage(request,1,20);
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("orderNo", orderNo);
		param.put("status", status);
		param.put("statusIn", statusIn) ;
		param.put("tradeBeginTime", tradeBeginTime);
		param.put("tradeEndTime", tradeEndTime);
		param.put("createBeginTime", createBeginTime);
		param.put("createEndTime", createEndTime);
		param.put("memberCode",memberCode);
		param.put("page",page);
	   return param;
   }

	/**
	 * 封装分页数据
	 * @param request
	 * @param pager_offset
	 * @param page_size
	 * @return
	 */
	public Page<?> packPage(HttpServletRequest request,int pager_offset ,int page_size){
		pager_offset = request.getParameter("pager_offset") == null ? 1: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码
		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 20;// 此处为避免分页计算时出现被0除的情况
		}
		@SuppressWarnings("rawtypes")
		Page<?> page = new Page() ;
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		return page;
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
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("调单查询明细.xls").getBytes("UTF-8"),
						"ISO8859_1"));
		return new ModelAndView(downloadBounced).addObject(
				"incomeDetailList", dateList).addObject("nowDate", new Date());
	}
	
	/**
	 * 导出查询结果(excel)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportResults(final HttpServletRequest request,
			final HttpServletResponse response, final Object dateList) throws Exception {
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("拒付查询明细.xls").getBytes("UTF-8"),
						"ISO8859_1"));
		return new ModelAndView(downBounced).addObject(
				"incomeDetailList", dateList).addObject("nowDate", new Date());
	}
	
	/**
	 * 批量不申诉
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void bacthNoAppeal(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String orderIds = request.getParameter("orderIds");
		String[] orderId= orderIds.split(",");
		List<Map> list=new ArrayList<Map>();
		for (String orderid: orderId) {
			Map map=new HashMap();	
			map.put("orderId",Long.valueOf(orderid));
			map.put("status", 7);
			map.put("cpType", 0);
			map.put("merchantCode", 8);
			list.add(map);
		}
		Map map=this.txncoreClientService.bacthNoAppeal(list,"true");
		String responseCode=map.get("responseCode")+"";
		if(responseCode.equals("0000")){
			response.getWriter().print("操作成功！！");
		}else{
			response.getWriter().print("操作失败！！");
		}
		
	}
	
		/**
	 * 下载拒付申诉资料模板
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downBouncedAppealTemplate(final HttpServletRequest request, final HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/ftl/bounced") ;
		String fileName = request.getParameter("fileName") ;
		@SuppressWarnings("resource")
		FileInputStream fis = new FileInputStream(path + "//" + fileName) ;
		byte[] bt = new byte[fis.available()] ;
		fis.read(bt) ;
		
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		fileName = URLEncoder.encode(fileName, "UTF-8") ;
		response.setHeader("Content-Disposition", "attactment;filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null ;
	}
	
	/**
	 * 上传申诉资料
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView uploadChargeFile(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null ;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			//拒付｜调单 订单号
			String orderId = request.getParameter("d_orderId") ;
			//类型
			String cpType = request.getParameter("d_cpType") ;
			//批次号
			String batchNo=request.getParameter("d_batchNo");
			//档号
			String refNo= request.getParameter("d_refNo");
			
			
			out = response.getWriter() ;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file") ;
			String dbRelativePath = "" ;
			if(null != file && file.getSize() > 0){
				try {
					long size = file.getSize() ;
					String originalFileName = file.getOriginalFilename() ;
					String extFileName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length()) ;
					if(!extFileName.equals(".doc") && !extFileName.equals(".docx")){
						out.println("<script>parent.callback('N|只能上传.doc或docx后缀的文档！')</script>");
						return null ;
					}
					if (size > 5 * 1024 *1024) {
						out.println("<script>parent.callback('N|文件大小不能超过5M!')</script>");
						return null ;
					}
					String memberCode = SessionHelper.getMemeberCodeBySession() ;
					String newFileName="";
					if(StringUtils.isNotEmpty(refNo)){
						 newFileName =memberCode+batchNo+refNo+ orderId+extFileName;
					}else{
						 if(StringUtils.isEmpty(batchNo)) batchNo = "" ;
						 newFileName =memberCode+batchNo +orderId+extFileName;
					}
					dbRelativePath = "chargeback" + "/" + memberCode + "/" + newFileName ;
					String name = this.chargeBackSavePath + dbRelativePath ;
					File dest = new File(name) ;
					if(!dest.getParentFile().exists()){
						dest.getParentFile().mkdirs() ;
						dest.createNewFile() ;
					}
					file.transferTo(dest);
					//申诉资料上传成功， 状态更改为申诉中（已上传资料）
					@SuppressWarnings("rawtypes")
					List<Map> list=new ArrayList<Map>();
					Map<String, Object> map = new HashMap<String, Object>() ;
					map.put("orderId", orderId) ;
					map.put("status", 1) ; //已上传资料
					map.put("appealDbRelativePath", dbRelativePath) ;
					map.put("operator", SessionHelper.getLoginSession().getVerifyName());
					list.add(map) ;
					@SuppressWarnings("rawtypes")
					Map resultMap=this.txncoreClientService.chargeBackOrderBatchUpdate(list);
					String responseCode=resultMap.get("responseCode")+"";
					if(responseCode.equals("0000")){
						//生成拒付操作记录
						List<Map> bouncedFlow=new ArrayList<Map>();
						bouncedFlow.add(map);
						Map bouncedFlowMap=this.txncoreClientService.bouncedFlowAddHandler(bouncedFlow);
						try {
							//创建关联
							Map<String, Object> relativeMap = new HashMap<String, Object>() ;
							relativeMap.put("memberCode", memberCode) ;
							relativeMap.put("orderId", orderId) ;
							relativeMap.put("cpType", cpType) ;
							this.chargeBackOrderService.createRealtionRnTx(relativeMap);
						} catch (Exception e) {
							logger.info("商户拒付处理标志关联关系创建失败：" + e);
						}
						out.println("<script>parent.callback('Y|资料提交成功，我们会尽快为你处理！')</script>");
					}else{
						out.println("<script>parent.callback('N|操作失败！')</script>");
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				out.println("<script>parent.callback('N|上传文件不能为空，请选择！')</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.close(); 
			}
		}
		return null ;
	}
	
	/**
	 * 拒付罚款查询
	 * @author delin
	 * @date 2016年7月29日11:32:20
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView bouncedFineQuery(HttpServletRequest request,HttpServletResponse response){
			String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
			String tradeBeginTime=request.getParameter("tradeBeginTime");
			String tradeEndTime = request.getParameter("tradeEndTime");
			Page<?> page = this.packPage(request,1,20);
			Map<String,Object> requetParam=new HashMap<String, Object>();
			requetParam.put("startTime", tradeBeginTime);
			requetParam.put("endTime", tradeEndTime);
			requetParam.put("memberCode", memberCode);
			requetParam.put("page", page);
			Map resultMap=txncoreClientService.bouncedFineQuery(requetParam);		
			List<Map> result= (List<Map>) resultMap.get("result");
			for (Map map2 : result) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
				  String bouncedDate=(String)map2.get("bouncedDate");
					 try {
						Date parse = sdf.parse(bouncedDate);
						map2.put("bouncedDate",sdf2.format(parse));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				 Long creDate= (Long) map2.get("createDate");
				 Long executeDate= (Long) map2.get("executeDate");//转为日期格式
				   if(creDate!=null){
					   Date d1 = new Date(creDate);
					   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					   map2.put("createDate", sd.format(d1));
				   }
				   if(executeDate!=null){
					   Date d2 = new Date(executeDate);
					   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					   map2.put("executeDate", sd.format(d2));
				   }
				   String bouncedRate=(String)map2.get("bouncedRate");
				   if(bouncedRate!=null){
					   if(bouncedRate.startsWith(".")){
						   BigDecimal bigDecimal = new BigDecimal(bouncedRate).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);	
						  map2.put("bouncedRate", bigDecimal.toString()+"%"); 
						 }   
				   }
				   String fraudRate=(String)map2.get("fraudRate");
				   if(fraudRate!=null){
				   if(fraudRate.startsWith(".")){
					   BigDecimal bigDecimal = new BigDecimal(fraudRate).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);	
					   map2.put("fraudRate", bigDecimal.toString()+"%"); 
				   }
				   } 
			}
			Map pageMap = (Map) resultMap.get("page");
			 page = MapUtil.map2Object(Page.class, pageMap);
			PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;
		return new ModelAndView(bouncedFine).addObject("requetParam", requetParam).addObject("resultList", result).addObject("pu", pu);
	}
	
	
	
/*	//重构文件名
	private String renameFileName(String extFileName){
		String newFileName = "" ;
		newFileName = UUID.randomUUID() + extFileName ;
		return newFileName ;
	}*/
	public void setChargeBackSavePath(String chargeBackSavePath) {
		this.chargeBackSavePath = chargeBackSavePath;
	}
	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}
	
	
}

