/**
 * 
 */
package com.pay.poss.controller.risk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.jms.sender.JmsSender;
import com.pay.poss.client.FiExceptionCardDetailService;
import com.pay.rm.exceptioncard.dto.AweekCount;
import com.pay.rm.exceptioncard.dto.ExceptionCardDTO;
import com.pay.rm.exceptioncard.service.ExceptionCardService;
import com.pay.util.DateUtil;
import com.pay.util.SimpleExcelGenerator;
import com.pay.util.StringUtil;

/**
 * @author Jiangbo.peng
 *
 */
public class ExceptionCardMgrController extends MultiActionController {
	
	private static Log logger = LogFactory.getLog(ExceptionCardMgrController.class) ;

	private String initView ;
	private String listView ;
	
	private ExceptionCardService exceptionCardService ;
	private FiExceptionCardDetailService fiExceptionCardDetailService ;
	private JmsSender jmsSender;
	
	/**
	 * 异常卡首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(initView) ;
		
		return mv ;
	}

	/**
	 * 异常卡查询列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(listView) ;
		String memberCode = StringUtil.null2String(request.getParameter("memberCode")) ;
		String time = StringUtil.null2String(request.getParameter("time")) ;
		if(StringUtils.isEmpty(memberCode)){
			return mv.addObject("errorMsg", "会员号不能为空") ;
		}
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("time", time) ;
		hMap.put("memberCode", memberCode) ;
		List<ExceptionCardDTO> resultList = this.exceptionCardService.queryExceptionCardList(hMap) ;
		//今日总计
		String dayStart1 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), 0)) ;
		String dayEnd1 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), 1)) ;
		AweekCount dayCount1 = this.extractDayStatics(memberCode, dayStart1, dayEnd1);
		mv.addObject("dayCount1", dayCount1) ;
		//统计前七日
		String dayStart7 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), -7)) ;
		String dayEnd7 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), 0)) ;
		AweekCount dayCount7 = this.extractDayStatics(memberCode, dayStart7, dayEnd7);
		mv.addObject("dayCount7", dayCount7) ;
		return mv.addObject("list", resultList).addObject("memberCode", memberCode).addObject("time", time) ;
	}

	private Date timeStr2Date(String time){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd") ;
			Date date = df.parse(time) ;
			if(StringUtils.isNotEmpty(time)){
				return date ;
			}else{
				return new Date() ;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date() ;
		}
			
	}
	
	/**
	 * 按天统计
	 * @param memberCode
	 */
	private AweekCount extractDayStatics(String memberCode, String dayStart, String dayEnd) {
		Map<String, Object> aweekCountMap1 = new HashMap<String, Object>() ;
		aweekCountMap1.put("dayStart", dayStart) ;
		aweekCountMap1.put("dayEnd", dayEnd) ;
		aweekCountMap1.put("memberCode", memberCode) ;
		AweekCount dayCount = this.exceptionCardService.queryAweekCount(aweekCountMap1) ;
		Long aweekExceptionCardCount = null == dayCount.getAweekExceptionCardCount() ? 0L :dayCount.getAweekExceptionCardCount() ;
		Long aweekFailOrderCount = dayCount.getAweekFailOrderCount() ;
		String dayPercent = "" ;
		if(null == aweekFailOrderCount || 0 == aweekFailOrderCount){
			dayPercent = "0.00%" ;
		}else{
			//计算前今日日比例
			BigDecimal divideAweekResult = new BigDecimal(aweekExceptionCardCount).divide(new BigDecimal(aweekFailOrderCount), 4, BigDecimal.ROUND_HALF_EVEN) ;
			//计算前今日比例
			DecimalFormat df = new DecimalFormat("0.00%") ;
			dayPercent = df.format(divideAweekResult) ;
		}
		//mv.addObject("dayPercent", dayPercent) ;
		dayCount.setPercent(dayPercent);
		return dayCount ;
	}
	
	/**
	 * 查询列表下载
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downLoadList(HttpServletRequest request, HttpServletResponse response){
		String memberCode = StringUtil.null2String(request.getParameter("memberCode")) ;
		String time = StringUtil.null2String(request.getParameter("time")) ;
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("time", time) ;
		hMap.put("memberCode", memberCode) ;
		List<ExceptionCardDTO> resultList = this.exceptionCardService.queryExceptionCardList(hMap) ;
		try {
			return this.downLoadFile2(request, response, resultList, memberCode, time) ;
//			String[] headers = new String[] { "会员号", "时间", "异常卡笔数", "失败订单数","异常卡比例","前一周比例","状态"};
//			String[] fields = new String[] { "memberCode", "timeZone", "exceptionCardCount",
//					"failOrderCount", "exceptionCardPercent","aweekAgoTimeZonePercent","status"};
//			Workbook grid = SimpleExcelGenerator.generateGrid("异常卡情况",
//					headers, fields, resultList);
//			Date myDate = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//			String dd = sdf.format(myDate);
//			response.setContentType("application/force-download");
//			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
//			response.setHeader("Content-Disposition", "attachment;filename="
//					+ "Abnormalcard_" + dd + ".xls");
//			grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.info("异常卡下载出错了！");
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
	 * 
	 * @param list
	 * @param path
	 * @throws Exception
	 */
	public void writeXls(List<ExceptionCardDTO> list, String path, String memberCode2, String time) throws Exception {
        if (list == null) {
            return;
        }
        int countColumnNum = list.size();
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("异常卡");
        // option at first row.
        String[] options = { "会员号", "时间", "异常卡笔数", "失败订单数", "异常卡比例", "前一周比例", "状态"};
        //今日总计
  		String dayStart1 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), 0)) ;
  		String dayEnd1 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), 1)) ;
  		AweekCount dayCount1 = this.extractDayStatics(memberCode2, dayStart1, dayEnd1);
  		
  		//统计前七日
		String dayStart7 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), -7)) ;
		String dayEnd7 = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(this.timeStr2Date(time)), 0)) ;
		AweekCount dayCount7 = this.extractDayStatics(memberCode2, dayStart7, dayEnd7);
  			
        HSSFRow firstRow = sheet.createRow(0);
        HSSFCell[] firstCells = new HSSFCell[options.length];
        for (int j = 0; j < options.length; j++) {
            firstCells[j] = firstRow.createCell(j);
            firstCells[j].setCellValue(new HSSFRichTextString(options[j]));
        }
        //
        for (int i = 0; i < countColumnNum; i++) {
            HSSFRow row = sheet.createRow(i + 1);
            ExceptionCardDTO exceptionCardDTO = list.get(i);
            for (int column = 0; column < options.length; column++) {
            	HSSFCell memberCode = row.createCell(0);
            	HSSFCell timeZone = row.createCell(1);
            	HSSFCell exceptionCardCount = row.createCell(2);
            	HSSFCell failOrderCount = row.createCell(3);
            	HSSFCell exceptionCardPercent = row.createCell(4);
            	HSSFCell aweekAgoTimeZonePercent = row.createCell(5);
            	HSSFCell status = row.createCell(6);
            	//HSSFCell expressCom = row.createCell(7);
            	memberCode.setCellValue(exceptionCardDTO.getMemberCode());
            	timeZone.setCellValue(exceptionCardDTO.getTimeZone());
            	exceptionCardCount.setCellValue(exceptionCardDTO.getExceptionCardCount());
            	failOrderCount.setCellValue(exceptionCardDTO.getFailOrderCount());
            	exceptionCardPercent.setCellValue(exceptionCardDTO.getExceptionCardPercent());
            	aweekAgoTimeZonePercent.setCellValue(exceptionCardDTO.getAweekAgoTimeZonePercent());
                status.setCellValue(exceptionCardDTO.getStatus());
            }
        }
        HSSFRow rowTail = sheet.createRow(countColumnNum + 1);
        HSSFCell tailCell=rowTail.createCell(0);
        //日总计：会员号：10000003671 时间：2016-06-17 异常卡笔数：5 失败订单数：17 异常卡比例：29.41% 前一周比例：70.27%
		tailCell.setCellValue(new HSSFRichTextString("日总计：会员号：" + memberCode2
				+ " 时间：" + time + " 异常卡笔数"
				+ dayCount1.getAweekExceptionCardCount() + " 失败订单笔数"
				+ dayCount1.getAweekFailOrderCount() + " 异常卡比例："
				+ dayCount1.getPercent() + " 前七日比例：" + dayCount7.getPercent()));
        File file = new File(path);
        OutputStream os = new FileOutputStream(file);
        book.write(os);
        os.close();
    }
	
	public ModelAndView downLoadFile2(final HttpServletRequest request,
			final HttpServletResponse response, List<ExceptionCardDTO> list, String memberCode, String time)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
		// MultiUploadForm multiUploadForm = (MultiUploadForm) form;
		String path = getServletContext().getRealPath(
				"/WEB-INF/jsp/crosspay/exceptioncard");

		String fileName = "Abnormal card_" + sdf.format(new Date()) + ".xls" ; //request.getParameter("fileName");
		String fullpath=path + "//" + "template.xls";
		FileInputStream fi = new FileInputStream(fullpath);
		//writeXlsx(list, fullpath);
		writeXls(list, fullpath, memberCode, time);
		
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
	/**
	 * 异常卡明细下载
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downDetail(HttpServletRequest request, HttpServletResponse response){
		String memberCode = StringUtil.null2String(request.getParameter("memberCode")) ;
		String time = StringUtil.null2String(request.getParameter("time")) ;
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		//异常卡范围编码
		//"202","205","33","41","54","75","34","43","59","67","78","79","81","62","37","38","35","44","9904","9907","9933","9934","9941","9943","9954","9959
		String[] channelRespCodes = new String[]{"202","205","33","41","54","75","34","43","59","67","78","79","81","62","37","38","35","44","9904","9907","9933","9934","9941","9943","9954","9959"} ;
		String[] channels = new String[]{"10002001","10079001","10075001","10076001"} ;
		hMap.put("time", time) ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("channelRespCodes", channelRespCodes) ;
		hMap.put("channels", channels) ;
		Map resultMap = this.fiExceptionCardDetailService.queryExceptionCardDetail(hMap) ;
		List<Map> details = (List<Map>) resultMap.get("result") ;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		for(Map m : details){
			 if(m.get("createTime")!=null){
				 String[] careateTimeSp = dateFormat.format(m.get("createTime")).split(" ") ;
				 m.put("createTime", careateTimeSp[0]);
				 m.put("hms", careateTimeSp[1]) ;
			 }
		}
		try {
			String[] headers = new String[] { "商户号", "商户订单号", "网关订单号", "支付订单号","渠道订单号","渠道号","交易金额","交易币种","交易汇率","渠道结果","网关结果","日期","时间","支付金额",
					"支付币种","渠道二级号","网址","卡号","姓名","IP","邮箱","收货人地址","CARDHOLDER_MOBILE","商户返回码","商户返回原因","渠道错误代码","银行返回原因"};
			String[] fields = new String[] { "memberCode", "orderId", "tradeOrderNo","paymentOrderNo","channelOrderNo","orgCode","orderAmount","currencyCode","currencyRate",
					"channelResult","tradeResult","createTime","hms","payAmount","payCurrencyCode","merchantNo","site","cardNo","cardHolderName","ip","email",
					"shippingAddress","cardHolderMobile",
					"tradeRespCode","tradeRespDesc","channelRespCode","channelRespDesc"};
			Workbook grid = SimpleExcelGenerator.generateGrid("异常卡明细",
					headers, fields, details);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("明细下载".getBytes("UTF-8"), "ISO-8859-1") + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			logger.info("异常卡明细下载出错了！");
			e.printStackTrace();
		}
		return null ;
	}
	
//	/**
//	 * 测试微信报警
//	 * @param request
//	 * @param response
//	 */
//	public void testAlert(HttpServletRequest request, HttpServletResponse response){
//		Map<String,String> data = new HashMap<String, String>();
//
//		data.put("first","商户异常卡订单比例超限，请确认是否有刷单情况，member code:测试");
//		data.put("keyword1","异常卡报警提醒");
//		SimpleDateFormat formatter = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss");
//        data.put("keyword2",formatter.format(new Date()));
//        data.put("remark","请尽快检查！");
//		
//		this.sendAlertMsg(data);
//		
//	}
//	
//	private void sendAlertMsg(Map<String,String> data){
//		WeiXinNotifyRequest request = new WeiXinNotifyRequest();
//        request.setBizCode("0015");
//        request.setOpenId("0000");
//        request.setType(RequestType.WEIXIN);
//		
//		request.setData(data);
//		jmsSender.send(request);
//	}
	
	/**
	 * @param initView the initView to set
	 */
	public void setInitView(String initView) {
		this.initView = initView;
	}

	/**
	 * @param listView the listView to set
	 */
	public void setListView(String listView) {
		this.listView = listView;
	}

	/**
	 * @param exceptionCardService the exceptionCardService to set
	 */
	public void setExceptionCardService(ExceptionCardService exceptionCardService) {
		this.exceptionCardService = exceptionCardService;
	}

	/**
	 * @param fiExceptionCardDetailService the fiExceptionCardDetailService to set
	 */
	public void setFiExceptionCardDetailService(
			FiExceptionCardDetailService fiExceptionCardDetailService) {
		this.fiExceptionCardDetailService = fiExceptionCardDetailService;
	}

	/**
	 * @param jmsSender the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	
}
