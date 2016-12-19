package com.pay.poss.controller.risk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.redis.RedisClientTemplate;
import com.pay.rm.orderthrehold.dto.OrderThreholdRuleDTO;
import com.pay.rm.orderthrehold.service.OrderThreholdService;
import com.pay.rm.orderthrehold.service.RiskTradeReportService;
import com.pay.util.SimpleExcelGenerator;
import com.pay.util.StringUtil;

/**
 * 风控交易监控预警日报表
 * 
 */
public class OrderRiskMonitorController extends MultiActionController{
	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String setrule;
	private String resultList;
	public String getSetrule() {
		return setrule;
	}

	public void setResultList(String resultList) {
		this.resultList = resultList;
	}

	public void setSetrule(String setrule) {
		this.setrule = setrule;
	}

	private OrderThreholdService orderThreholdService;
	
	private RiskTradeReportService riskTradeReportService;
	
	private RedisClientTemplate redisClient;
	
	private final String threholdKey = "orderThrehold";

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}
	
	public void setOrderThreholdService(OrderThreholdService orderThreholdService) {
		this.orderThreholdService = orderThreholdService;
	}

	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}
	public void setRiskTradeReportService(
			RiskTradeReportService riskTradeReportService) {
		this.riskTradeReportService = riskTradeReportService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			//当前日期减一天
		   Calendar calendar=Calendar.getInstance();  
		   calendar.setTime(new Date());		  
		   calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);//让日期加1  
		
		return new ModelAndView(queryInit)
				.addObject("dateStr",calendar.getTime())
				;
	}
	
	
	/**
	 * 查询监控预警情况
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,HttpServletResponse response){
		String monitorType=request.getParameter("businessType");
		String monitorDate=request.getParameter("startDate");
		Page page = PageUtils.getPage(request);
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("monitorType",monitorType);
		paraMap.put("monitorDate",monitorDate);
		paraMap.put("page",page);
		List<Map> resList=riskTradeReportService.queryRiskTradeRep(paraMap,page);
		for (Map map : resList) {
			String merchantId =String.valueOf(map.get("merchantId"));
			if(merchantId.startsWith("5")){
				map.put("merchantId", "IPAY");
			}else if(merchantId.startsWith("8")){
				map.put("merchantId", "GC");
			}
		}
		return new ModelAndView(resultList).addObject("resList", resList).addObject("paraMap", paraMap).addObject("page", page);
	}
	
	/**
	 * 查询监控预警情况下载
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView download(HttpServletRequest request,HttpServletResponse response){
		String monitorType=request.getParameter("businessType");
		String monitorDate=request.getParameter("startDate");
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("monitorType",monitorType);
		paraMap.put("monitorDate",monitorDate);
		List<Map> resList=riskTradeReportService.queryRiskTradeRep(paraMap);
		for (Map map : resList) {
		String monitortype=String.valueOf(map.get("monitorType"));
			if(monitortype.equals("1")){
				map.put("monitorType", "时段内失败交易数超限");	
			}else if(monitortype.equals("2")){
				map.put("monitorType", "同一Email累计交易次数超限");		
			}else if(monitortype.equals("3")){
				map.put("monitorType", "账单地址不符");		
			}else if(monitortype.equals("4")){
				map.put("monitorType", "账单国与收货国不符");		
			}else if(monitortype.equals("5")){
				map.put("monitorType", "单笔交易金额超限");		
			}
		String tradeStatus=String.valueOf(map.get("tradeStatus"));
			if(tradeStatus.equals("3")){
				map.put("tradeStatus", "是");	
			}else{
				map.put("tradeStatus", "否");	
			}
		}
		try {
			String[] headers = new String[] { "网关订单号", "触碰规则类型", "会员号", "商户名称",
					"商户类型","商户订单号","交易金额","交易币种","是否退款","创建时间","交易完成时间","渠道支付结果","渠道返回原因","交易网址",
					"商品名称","下单IP地址","卡号","发卡国家","账单姓名","账单国家","账单州","账单城市",
					"账单街道","账单地址","账单邮编","账单邮箱","账单电话","收货姓名","收货国家","收货州","收货城市","收货街道","收货地址",
					"收货邮编","收货邮箱","收货电话"};
			String[] fields = new String[] { "tradeOrderNo", "monitorType", "partnerId",
					"merchantName", "merchantId","orderId","orderAmount","currencyCode","tradeStatus","monitorDate","completeDate","channelRespCode",
					"channelRespMsg","siteId","goodsName","coustomerIp","cardId","cardCountry","billName","billCountry","billState","billCity",
					"billStreet","billAddress","billPostalCode","billEmail","billPhone","shippingName","shippingCountry","shippingState","shippingCity","shippingStreet","shippingAddress",
					"shippingPostalCode","shippingEmail","shippingPhone"};
			Workbook grid = SimpleExcelGenerator.generateGrid("监 控 预 警 情 况",
					headers, fields, resList);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 设置规则
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView setrule(HttpServletRequest request,HttpServletResponse response){
		 String memberduration ="60";
		 String menbertimes ="7";
		 String emailduration ="12";
		 String emailtimes ="5"; 
		if(redisClient.exists("orderriskmoniter.rule")){
			Map<String, String> redisMap=redisClient.hgetAll("orderriskmoniter.rule");
			memberduration=redisMap.get("memberduration");
			menbertimes=redisMap.get("menbertimes");
			emailduration=redisMap.get("emailduration");
			emailtimes=redisMap.get("emailtimes");
			}else{
				Map<String, String> redisMap=new HashMap<String, String>();
				redisMap.put("memberduration","60");
				redisMap.put("menbertimes","7");
				redisMap.put("emailduration","12");
				redisMap.put("emailtimes","5");
				redisClient.hmset("orderriskmoniter.rule", redisMap);
			};
		
		return new ModelAndView(setrule).addObject("memberduration", memberduration).addObject("menbertimes", menbertimes).addObject("emailduration", emailduration).addObject("emailtimes", emailtimes);
	
	}
	
	/**
	 * 修改规则
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView modyrule(HttpServletRequest request,HttpServletResponse response){
		String memberduration =request.getParameter("memberduration");
		 String menbertimes =request.getParameter("menbertimes");
		 String emailduration =request.getParameter("emailduration");
		 String emailtimes =request.getParameter("emailtimes");
		 Map<String, String> redisMap=new HashMap<String, String>();
			redisMap.put("memberduration",memberduration);
			redisMap.put("menbertimes",menbertimes);
			redisMap.put("emailduration",emailduration);
			redisMap.put("emailtimes",emailtimes);
			redisClient.hmset("orderriskmoniter.rule", redisMap);
		
			//当前日期减一天
			   Calendar calendar=Calendar.getInstance();  
			   calendar.setTime(new Date());		  
			   calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);//让日期加1  
			
			return new ModelAndView(queryInit)
					.addObject("dateStr",calendar.getTime())
					;
			}
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String description = StringUtil.null2String(request
				.getParameter("description"));
		int duration = this.getPositiveInteger(request
				.getParameter("duration"));
		int threhold = this.getPositiveInteger(request
				.getParameter("threhold"));

		ModelAndView mv = new ModelAndView(queryInit)
				.addObject("description", description)
				.addObject("duration", request.getParameter("duration"))
				.addObject("threhold", request.getParameter("threhold"));
		
		if (description.trim().length() == 0) {
			mv.addObject("responseDesc", "描述不能为空");
		} else if (description.length() > 60) {
			mv.addObject("responseDesc", "描述不能超过60个字符");
		} else if (duration <= 0) {
			mv.addObject("responseDesc", "时间间隔必须为正整数！");
		} else if (threhold <= 0) {
			mv.addObject("responseDesc", "次数阈值必须为正整数！");
		} else {
			try {
				redisClient.set(threholdKey + "_duration", duration + "");
				redisClient.set(threholdKey + "_threhold", threhold + "");
			} catch (Exception ex) {
				mv.addObject("responseDesc", "redis写入异常！" + ex.getMessage());
			}
			OrderThreholdRuleDTO threholdDTO = new OrderThreholdRuleDTO();
			threholdDTO.setType(threholdKey);
			threholdDTO.setDescription(description);
			threholdDTO.setDuration(duration);
			threholdDTO.setThrehold(threhold);
			this.orderThreholdService.updateOrderThreholdRule(threholdDTO);
			mv.addObject("responseDesc", "更新成功！");
		}

		return mv;
	}
	
	// 将字符串转成正整数，如果出错则返回-1
	private  int getPositiveInteger(String str) {
		int ret = -1;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			ret = -1;
		}
		return ret;
	}
}
