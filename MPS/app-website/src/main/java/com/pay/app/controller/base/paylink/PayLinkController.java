/**
 * 
 */
package com.pay.app.controller.base.paylink;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.fi.chain.condition.paylink.PayLinkCondition;
import com.pay.fi.chain.model.LogoPicture;
import com.pay.fi.chain.model.PayLink;
import com.pay.fi.chain.model.PayLinkAttrib;
import com.pay.fi.chain.model.PayLinkBaseInfo;
import com.pay.fi.chain.service.LogoPicService;
import com.pay.fi.chain.service.PayLinkService;
import com.pay.fi.chain.service.ShoptermFileService;
import com.pay.fi.chain.util.FeeEnum;
import com.pay.fi.chain.util.O2oEnum;
import com.pay.fi.chain.util.PayLinkUtil;
import com.pay.fi.chain.util.PaylinkCountryEnum;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.inf.dao.Page;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.MD5Util;
import com.pay.util.StringUtil;
import com.pay.util.StringUtils;

/**
 * 支付链
 * @author PengJiangbo
 *
 */
public class PayLinkController extends MultiActionController {
	
	private static final Log logger = LogFactory.getLog(PayLinkController.class) ;
	private String indexView ;
	private String queryView ;
	private String basicInfo ;
	private String shopTermView ;
	private String contactView ;
	private String payLinkInvalidView ;
	private String previewView ;
	private String payLinkDetailView ;
	private String cloneView ;
	private String excelView ;
	
	private PayLinkService payLinkService ;
	private ShoptermFileService shoptermFileService ;
	private LogoPicService logoPicService ;
	
	private static final String FILE_TYPE_ERROR = "文件类型不正确，只允许上传pdf文件！" ;
	private static final double FILE_SIZE = 1024 * 1024 * 5 ;					//文件允许的大小 ，单位M
	private static final String FILE_SIZE_ERROR_TIP = "文件大小不允许超过5M" ;
	private static final String PAY_LINK_NULL = "请选择一条支付链接" ;
	private static final String SHOP_TERM = "shopterm/" ;
	
	private String basePath ;
	//阿里云存储密钥
	private String ossKey;
	//阿里云存储子目录
	private String ossSubDir;
	//阿里云存储根目录
	private String ossRootDir;
	/**
	 * 默认页面，支付链生成页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response){
		String generated = StringUtil.null2String(request.getParameter("generated")) ;
		String errMsg = StringUtil.null2String(request.getParameter("errMsg")) ;
		CurrencyCodeEnum[] values = CurrencyCodeEnum.values() ;
		//加载购物条款信息
		PayLinkBaseInfo baseInfo = loadBaseInfo() ;
		String shoptermsName = baseInfo.getShoptermsName() ;
		String pathTail = SHOP_TERM + baseInfo.getMemberCode() + "/" + baseInfo.getShoptermsName() ;
		//加载商户logo信息
		List<LogoPicture> logoPictures = this.logoPicService.findPicByMemberCode(Long.valueOf(SessionHelper.getMemeberCodeBySession())) ;
		this.convertOssURL(logoPictures);
		//其他费用费用类型
		FeeEnum[] fees = FeeEnum.values() ;
		if(!org.apache.commons.lang.StringUtils.isBlank(generated)){
			String payChainLink = this.basePath + generated ; //PayLinkUtil.generatePayLinkName(request) + "/" + generated ;
			return new ModelAndView(indexView).addObject("currencyCodes", values)
					.addObject("payChainLink", payChainLink)
					.addObject("shoptermsName", shoptermsName)
					.addObject("pathTail", pathTail)
					.addObject("logoPictures", logoPictures)
					.addObject("fees", fees)
					.addObject("errMsg", errMsg);
		}
		return new ModelAndView(indexView).addObject("currencyCodes", values)
				.addObject("shoptermsName", shoptermsName)
				.addObject("pathTail", pathTail)
				.addObject("logoPictures", logoPictures)
				.addObject("fees", fees);
	}
	
	/**
	 * 支付链生成处理
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView payLinkGenerate(final HttpServletRequest request, final HttpServletResponse response){
//		String jsonStr = "[{'productName':'名剪', 'productSpec':'一等品', 'productNum':28, 'price':10, 'currencyCode':'CNY', 'site':'happymall.com', 'productAmount':280},"
//				+ "{'productName':'飞科剃刀', 'productSpec':'二等品', 'productNum':28, 'price':10, 'currencyCode':'CNY', 'site':'happymall.com', 'productAmount':280}]" ;
		String attribsStr = null;
		String errMsg = "" ;
		StringBuilder sb = new StringBuilder() ;
		try {
			request.setCharacterEncoding("UTF-8");
			attribsStr = URLDecoder.decode(URLDecoder.decode(StringUtil.null2String(request.getParameter("jsonStr")), "UTF-8") ,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("URL解码出错了，不支持的编码！");
			sb.append("URL解码出错了，不支持的编码！") ;
			e.printStackTrace();
		}
		JSONArray jsonArray = null;
		List<PayLinkAttrib> payLinkAttribs = null ;
		try {
			jsonArray = JSONArray.fromObject(attribsStr);
			payLinkAttribs = JSONArray.toList(jsonArray, PayLinkAttrib.class) ;
		} catch (Exception e1) {
			logger.info("json数据转换出错!");
			sb.append("|json数据转换出错!") ;
			e1.printStackTrace();
		}
		//@SuppressWarnings("unchecked")
		System.out.println(payLinkAttribs);
		PayLink payLink = ConstructVo(request);
		try {
			Long saveResult = this.payLinkService.payLinkSave(payLink) ;
			try {
				if(null != saveResult){
					for(PayLinkAttrib payLinkAttrib : payLinkAttribs){
						payLinkAttrib.setPayLinkNo(saveResult);
					}
					//生成支付链商品属性
					this.payLinkService.payLinkAttribBatchSave(payLinkAttribs) ;
				}
			} catch (Exception e) {
				logger.error("生成支付链商品属性出错："+e.getMessage());
				sb.append("|生成支付链商品属性出错!") ;
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("生成支付链出错了："+e.getMessage());
			sb.append("|生成支付链出错了!") ;
			e.printStackTrace();
		}
		String payLinkName = payLink.getPayLinkName() ;
		errMsg = sb.toString() ;
		return new ModelAndView("redirect:/corp/paylink.htm?generated=" + payLinkName +"&errMsg=" + errMsg) ;
	}
	/**
	 * 构建对象
	 * @param request
	 * @return
	 */
	private PayLink ConstructVo(final HttpServletRequest request) {
		String isClone = StringUtil.null2String(request.getParameter("isClone")) ;
		String otherFeeName = StringUtil.null2String(request.getParameter("otherFeeName")) ;
		String logoPictureId = StringUtil.null2String(request.getParameter("logoPicture")) ;
		LogoPicture logoPic=this.logoPicService.queryPicListById(Long.parseLong(logoPictureId));
		//Long fee = StringUtils.stringToLong(request.getParameter("fee")) ;
		String fee = StringUtil.null2String(request.getParameter("fee")) ;
		String feeCurrencyCode = StringUtil.null2String(request.getParameter("otehrFeeCurrencyCode")) ;
		//Long totalAmount = StringUtils.stringToLong(request.getParameter("totalAmount")) ;
		String totalAmount = StringUtil.null2String(request.getParameter("totalAmount")) ;
		//
		int invalidTimeLong = StringUtils.StringToInt(request.getParameter("invalidTimeLong")) ;
		String logoPath = logoPic.getPicturePath();//StringUtil.null2String(request.getParameter("logoPath")) ;
		//Long productAmount = StringUtils.stringToLong(request.getParameter("productAmount")) ;
		String productAmount = StringUtil.null2String(request.getParameter("productAmount")) ;
		String merchantSite = StringUtil.null2String(request.getParameter("merchantSite")) ;
		String productNameDesc = StringUtil.null2String(request.getParameter("productNameDesc")) ;
		PayLink payLink = new PayLink() ;
		payLink.setCreateTime(new Timestamp(new Date().getTime()));
		if(org.apache.commons.lang.StringUtils.isNotEmpty(fee)){
			//;
			payLink.setFee(Long.valueOf(strNum(fee, isClone)));
		}
		payLink.setInvalidTime(PayLinkUtil.genrateInvalidTime(invalidTimeLong));
		payLink.setInvalidTimeLong(invalidTimeLong); 
		payLink.setLogoPath("");
		payLink.setMemberCode(Long.valueOf(SessionHelper.getMemeberCodeBySession()));
		payLink.setOtherFeeName(otherFeeName);
		//支付链访问尾缀action
		payLink.setPayLinkName(AppConf.get(AppConf.payLinkUrl) + "?n=" + MD5Util.md5Hex(UUID.randomUUID().toString())); 
		payLink.setPaymentLogoFlg(0);
		payLink.setStatus(O2oEnum.payLinkEffective.getStatus()); //生效
		if(org.apache.commons.lang.StringUtils.isNotEmpty(totalAmount)){
			payLink.setTotalAmount(Long.valueOf(strNum(totalAmount, isClone)));
		}
		payLink.setFeeCurrencyCode(feeCurrencyCode); 
		payLink.setProductAmount(0L);
		payLink.setLogoPath(logoPath);
		if(org.apache.commons.lang.StringUtils.isNotEmpty(productAmount)){
			payLink.setProductAmount(Long.valueOf(strNum(productAmount, isClone)));
		}
		payLink.setMerchantSite(merchantSite); 
		payLink.setProductNameDesc(productNameDesc);
		return payLink;
	}
	/**
	 * @param fee
	 * @param payLink
	 */
	private String strNum(String strNum, String isClone) {
		if(org.apache.commons.lang.StringUtils.isNotEmpty(isClone)){
			if(strNum.equals("0.00")){
				strNum = "0" ;
			}
			if(strNum.contains(",")){
				strNum = strNum.replace(",", "");
			}
			if(strNum.contains(".")){
				strNum = strNum.replace(".", "") ;
				//payLink.setFee(Long.valueOf(strNum) * 100);
			}else{
				//payLink.setFee(Long.valueOf(strNum) * 100);
				strNum = strNum + "00" ;
			}
			return strNum ;
		}else
			return strNum ;
		
	}
	
	/**
	 * 支付链查询 | 下载 －> 共用方法
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView payLinkQuery(final HttpServletRequest request, final HttpServletResponse response){
		if(logger.isInfoEnabled()){
			logger.info("paylink basePath:" + this.basePath);
		}
		String excel = StringUtil.null2String(request.getParameter("excel")) ;
		String toPage = StringUtil.null2String(request.getParameter("toPage")) ;
		if(org.apache.commons.lang.StringUtils.isBlank(toPage)){
			String createTimeStart = DateUtil.formatDateTime("yyyy-MM-dd",DateUtil.skipDateTime(new Date(),-2)) ;
			String createTimeEnd = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil.skipDateTime(new Date(), 1)) ;
			String invalidTimeStart = DateUtil.formatDateTime("yyyy-MM-dd",DateUtil.skipDateTime(new Date(),0)) ;
			String invalidTimeEnd = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil.skipDateTime(new Date(), 4)) ;
			return new ModelAndView(queryView).addObject("createTimeStart", createTimeStart)
					.addObject("createTimeEnd", createTimeEnd)
					.addObject("invalidTimeStart", invalidTimeStart)
					.addObject("invalidTimeEnd", invalidTimeEnd) ;
		}
		//String toPage
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		String payLinkName = StringUtil.null2String(request.getParameter("payLinkName")) ;
		String createTimeStart = StringUtil.null2String(request.getParameter("createTimeStart")) ;
		String createTimeEnd = StringUtil.null2String(request.getParameter("createTimeEnd")) ;
		String invalidTimeStart = StringUtil.null2String(request.getParameter("invalidTimeStart")) ;
		String invalidTimeEnd = StringUtil.null2String(request.getParameter("invalidTimeEnd")) ;
		String productName = StringUtil.null2String(request.getParameter("productName")) ;
		//String productNamePara = "" ;
		/*if(org.apache.commons.lang.StringUtils.isNotEmpty(productName)){
			String[] productNames = productName.split(",") ;
			for(String p : productNames){
				productNamePara = productNamePara + "'" + p + "'" + "," ;
			}
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(productNamePara)){
			productNamePara = productNamePara.substring(0, productNamePara.lastIndexOf(",")) ;      
		}*/
		String statusStr = StringUtil.null2String(request.getParameter("status")) ;
		PayLinkCondition payLinkCondition = new PayLinkCondition() ;
		if(null != statusStr && !"".equals(statusStr) && !"999999".equals(statusStr)){
			payLinkCondition.setStatus(Integer.valueOf(statusStr));
			payLinkCondition.setInvalidTime(PayLinkUtil.date2Str(new Timestamp(new Date().getTime())));
		}
		if(null != payLinkNo && !"".equals(payLinkNo) && payLinkNo.replace(" ", "").matches("[0-9]+")){
			payLinkCondition.setPayLinkNo(Long.valueOf(payLinkNo.replace(" ", "")));
		}
		payLinkCondition.setMemberCode(Long.valueOf(SessionHelper.getMemeberCodeBySession()));
		payLinkCondition.setPayLinkName(payLinkName);
		payLinkCondition.setCreateTimeStart(createTimeStart);
		payLinkCondition.setCreateTimeEnd(createTimeEnd);
		payLinkCondition.setInvalidTimeStart(invalidTimeStart);
		payLinkCondition.setInvalidTimeEnd(invalidTimeEnd);
		//payLinkCondition.setProductName(productNamePara);
		payLinkCondition.setProductName(productName);
		if((null != createTimeStart && !"".equals(createTimeStart)) && (null != createTimeEnd && !"".equals(createTimeEnd))){
			payLinkCondition.setCreateTimeBetween("true");
		}
		if((null != invalidTimeStart && !"".equals(invalidTimeStart)) && (null != invalidTimeEnd && !"".equals(invalidTimeEnd))){
			payLinkCondition.setInvalidTimeBetween("true");
			
		}
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 10; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码
		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 10;// 此处为避免分页计算时出现被0除的情况
		}
		@SuppressWarnings("rawtypes")
		Page<?> page = new Page() ;
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		List<PayLink> payLinks = null ;
		if(org.apache.commons.lang.StringUtils.isBlank(excel)){
			payLinks = this.payLinkService.queryPayLinkByCondition(payLinkCondition, page) ;
		}else{
			payLinks = this.payLinkService.queryPayLinkByCondition(payLinkCondition) ;
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Transfer-Encoding", "binary");
		    response.setHeader("Cache-Control",
		     "must-revalidate, post-check=0, pre-check=0");
		    response.setHeader("Pragma", "public");
		    //String agent = request.getHeader("User-Agent");
		    response.setHeader("Content-Disposition", "attachment;filename=paylink.xls");
			return new ModelAndView(excelView).addObject("payLinks", payLinks)
					.addObject("nowDate", new Date());
		}
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;
		Map<String, Object> model = new HashMap<String, Object>() ;
		model.put("payLinkName", payLinkName) ;
		model.put("createTimeStart", createTimeStart) ;
		model.put("createTimeEnd", createTimeEnd) ;
		model.put("invalidTimeStart", invalidTimeStart) ;
		model.put("invalidTimeEnd", invalidTimeEnd) ;
		model.put("status", statusStr) ;
		model.put("productName", productName);
		model.put("payLinkNo", payLinkNo) ;
		//String basePath = PayLinkUtil.generatePayLinkName(request) + "/" ;
		String basePath = this.basePath; //AppConf.get(AppConf.payLinkBasePath) ;
		model.put("basePath", basePath) ;
		return new ModelAndView(queryView).addObject("payLinks", payLinks)
				.addObject("pu", pu )
				.addAllObjects(model);
	}	
	
	/**
	 * 支付链基本信息维护（商户logo）
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView payLinkBasicInfoMaintain(final HttpServletRequest request, final HttpServletResponse response){
		//加载商户logo图片路径
		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession()) ;
		List<LogoPicture> logoPictures = this.logoPicService.findPicByMemberCode(memberCode) ;
		this.convertOssURL(logoPictures);
		return new ModelAndView(basicInfo).addObject("logoPictures", logoPictures) ;
	}
	/**
	 * 商户 logo图片上的网址添加
	 * @param request
	 * @return
	 */
	public ModelAndView merchantSiteAdd(final HttpServletRequest request, final HttpServletResponse response){
		String merchantSite = StringUtil.null2String(request.getParameter("merchantSite")) ;
		String pictureId = StringUtil.null2String(request.getParameter("pictureId")) ;
		if(null == pictureId || "".equals(pictureId)){
			logger.info("logo pictureId不能为空！");
			return null;
		}
		LogoPicture logoPicture = new LogoPicture() ;
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			if(logger.isInfoEnabled()){
				logger.info("response.getWrite异常!");
			}
			e.printStackTrace();
		}
		if(null == pictureId || "".equals(pictureId)){
			writer.write("请选择相应logo!");
			writer.flush();
			writer.close();
			return null ;
		}
		logoPicture.setMerchantSite(merchantSite);
		logoPicture.setPictureId(Long.valueOf(pictureId));
		int updateResult = this.logoPicService.updatePicByParam(logoPicture) ;
		if(updateResult> 0){
			writer.write("添加logo图网址成功!");
			writer.flush();
			writer.close(); 
		}
		return null ;
	}
	/**
	 * 到达购物条款页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView payShopTerm(final HttpServletRequest request, final HttpServletResponse response){
		PayLinkBaseInfo payLinkBaseInfo = loadBaseInfo() ;
		String shoptermsName = payLinkBaseInfo.getShoptermsName() ;
		String pathTail = SHOP_TERM + payLinkBaseInfo.getMemberCode() + "/" + payLinkBaseInfo.getShoptermsName() ;
		return new ModelAndView(shopTermView).addObject("baseInfo", payLinkBaseInfo)
				.addObject("shoptermsName", shoptermsName)
				.addObject("pathTail", pathTail);
	}
	
	/**
	 * 到达联系方式页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView contact(final HttpServletRequest request, final HttpServletResponse response){
		PayLinkBaseInfo payLinkBaseInfo = loadBaseInfo() ;
		return new ModelAndView(contactView).addObject("baseInfo", payLinkBaseInfo);
	}
	
	//加载基本信息
	public PayLinkBaseInfo loadBaseInfo(){
		PayLinkBaseInfo payLinkBaseInfo = null ;
		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession()) ;
		payLinkBaseInfo = this.payLinkService.payLinkBaseInfoQuery(memberCode) ;
		if(null == payLinkBaseInfo)
			payLinkBaseInfo = new PayLinkBaseInfo() ;
		return payLinkBaseInfo ;
	}
	//购物条款新增或修改
	public ModelAndView doPayShopTermUorS(final HttpServletRequest request, final HttpServletResponse response){
		String suFlag = StringUtil.null2String(request.getParameter("doUpdate")) ;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
		MultipartFile file = multipartRequest.getFile("shopTermFile") ;
		String shoptermName = file.getOriginalFilename() ;
		PayLinkBaseInfo info = new PayLinkBaseInfo() ;
		if(file!=null && org.apache.commons.lang.StringUtils.isNotBlank(file.getName()) && file.getSize()>0){
			//校验文件类型和大小
			if(!this.shoptermFileService.validateFileType(file)){
				return new ModelAndView(shopTermView).addObject("FILE_TYPE_ERROR", FILE_TYPE_ERROR)
						.addObject("baseInfo", info);
			}else if(this.shoptermFileService.getFileSizeAtM(file) > FILE_SIZE){
				return new ModelAndView(shopTermView).addObject("FILE_SIZE_ERROR_TIP", FILE_SIZE_ERROR_TIP)
						.addObject("baseInfo", info);
			}
		}
		else{
			return new ModelAndView(shopTermView).addObject("noFile", "请选择要上传的文件")
					.addObject("baseInfo", info);
		}
		//校验通过，进行文件上传
		try {
			String memberCode = SessionHelper.getMemeberCodeBySession() ;
			String shoptermUrl = this.shoptermFileService.upload(file, file.getOriginalFilename(), memberCode) ;
			if(null != shoptermUrl){
				//判断是新增还是修改
				//修改
				if(!"".equals(suFlag) && suFlag.equals("doUpdate")){
					Long infoId = StringUtils.stringToLong(request.getParameter("infoId")) ;
					Timestamp updateTime = new Timestamp(new Date().getTime()) ;
					this.payLinkService.updateShopTermById(infoId, updateTime, shoptermName, shoptermUrl) ;
				}else{//新增
					PayLinkBaseInfo payLinkBaseInfo = new PayLinkBaseInfo() ;
					payLinkBaseInfo.setCreateTime(new Timestamp(new Date().getTime()));
					payLinkBaseInfo.setMemberCode(Long.valueOf(memberCode));
					payLinkBaseInfo.setShoptermsName(shoptermName); 
					payLinkBaseInfo.setShoptermsUrl(shoptermUrl);
					this.payLinkService.shoptermSave(payLinkBaseInfo) ;
				}
			}
		} catch (Exception e) {
			logger.error("文件上专异常！"+ e.getMessage());
			e.printStackTrace();
			return new ModelAndView(shopTermView).addObject("uploadExction", "文件上传异常！")
					.addObject("baseInfo", info);
		}
		PayLinkBaseInfo baseInfo = loadBaseInfo() ;
		String shoptermsName = baseInfo.getShoptermsName() ;
		String pathTail = SHOP_TERM + baseInfo.getMemberCode() + "/" + baseInfo.getShoptermsName() ;
		return new ModelAndView(shopTermView).addObject("baseInfo", baseInfo)
				.addObject("shoptermsName", shoptermsName)
				.addObject("pathTail", pathTail);
	}
	//售后联系方式新增或修改
	public ModelAndView doContactUorS(final HttpServletRequest request, final HttpServletResponse response){
		String suFlag = StringUtil.null2String(request.getParameter("doUpdate")) ;
		String contact = StringUtil.null2String(request.getParameter("contact")) ;
		//
		if(!"".equals(suFlag) && "doUpdate".equals(suFlag)){
			Long infoId = StringUtils.stringToLong(request.getParameter("infoId")) ;
			Timestamp updateDate = new Timestamp(new Date().getTime()) ;
			this.payLinkService.updateContact(infoId, updateDate, contact) ;
		}else{
			PayLinkBaseInfo payLinkBaseInfo = new PayLinkBaseInfo() ;
			payLinkBaseInfo.setContact(contact);
			payLinkBaseInfo.setMemberCode(Long.valueOf(SessionHelper.getMemeberCodeBySession()));
			payLinkBaseInfo.setCreateTime(new Timestamp(new Date().getTime()));
			this.payLinkService.baseInfoSave(payLinkBaseInfo) ;
		}
		PayLinkBaseInfo baseInfo = loadBaseInfo() ;
		return new ModelAndView(contactView).addObject("baseInfo", baseInfo) ;
	}
	
	/**
	 * 支付链失效处理
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView payLinkInvalid(final HttpServletRequest request, final HttpServletResponse response){
		String toPage = StringUtil.null2String(request.getParameter("toPage")) ;
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		if(null == payLinkNo || "".equals(payLinkNo)){
			return new ModelAndView(queryView).addObject("payLinkNull", "请选择要置为失效的支付链接！");
		}
		//到达支付链失效页面
		if(org.apache.commons.lang.StringUtils.isBlank(toPage)){
			PayLink payLink = this.payLinkService.findPayLinkByPayLinkNo(Long.valueOf(payLinkNo)) ;
			List<PayLinkAttrib> payLinkAttribs = this.payLinkService.findPayLinkAttribsByPayLinkNo(Long.valueOf(payLinkNo)) ;
			//加载购物条款
			PayLinkBaseInfo baseInfo = loadBaseInfo() ;
			String shoptermsName = baseInfo.getShoptermsName() ;
			String pathTail = SHOP_TERM + baseInfo.getMemberCode() + "/" + baseInfo.getShoptermsName() ;
			String basePath = this.basePath; //AppConf.get(AppConf.payLinkBasePath) ;
			return new ModelAndView(payLinkInvalidView)
					.addObject("payLinkNo", payLinkNo)
					.addObject("payLink", payLink)
					.addObject("payLinkAttribs", payLinkAttribs)
					.addObject("shoptermsName", shoptermsName)
					.addObject("pathTail", pathTail)
					.addObject("basePath", basePath);
		}
		Timestamp invalidTime = new Timestamp(new Date().getTime()) ;
		int updateResult = this.payLinkService.updateStatusByNo(Long.valueOf(payLinkNo), O2oEnum.payLinkInvalid.getStatus(), invalidTime) ;
		if(updateResult > 0){
			return new ModelAndView(queryView).addObject("updateSuccess", "状态更新成功") ;
		}else
			return new ModelAndView(queryView).addObject("updateFail", "状态更新失败！") ;
	}
	/**
	 * 支付链预览
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView preview(final HttpServletRequest request, final HttpServletResponse response){
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		if(org.apache.commons.lang.StringUtils.isBlank(payLinkNo)){
			return new ModelAndView(payLinkInvalidView).addObject(PAY_LINK_NULL, PAY_LINK_NULL) ;
		}
		PayLink payLink = this.payLinkService.findPayLinkByPayLinkNo(Long.valueOf(payLinkNo)) ;
		List<PayLinkAttrib> payLinkAttribs = this.payLinkService.findPayLinkAttribsByPayLinkNo(Long.valueOf(payLinkNo)) ;
		//加载购物条款
		PayLinkBaseInfo baseInfo = loadBaseInfo() ;
		String shoptermsName = baseInfo.getShoptermsName() ;
		String pathTail = SHOP_TERM + baseInfo.getMemberCode() + "/" + baseInfo.getShoptermsName() ;
		//String basePath = AppConf.get(AppConf.payLinkBasePath) ;
		PaylinkCountryEnum[] countries = PaylinkCountryEnum.values() ;
		return new ModelAndView(previewView).addObject("payLink", payLink)
				.addObject("payLinkAttribs", payLinkAttribs)
				.addObject("shoptermsName", shoptermsName)
				.addObject("pathTail", pathTail)
				.addObject("countries", countries);
	}
	/**
	 * 支付链详情
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView querySingleDetail(final HttpServletRequest request, final HttpServletResponse response){
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		if(null == payLinkNo || "".equals(payLinkNo)){
			return new ModelAndView(queryView).addObject(PAY_LINK_NULL, PAY_LINK_NULL) ;
		}
		PayLink payLink = this.payLinkService.findPayLinkByPayLinkNo(Long.valueOf(payLinkNo)) ;
		List<PayLinkAttrib> payLinkAttribs = this.payLinkService.findPayLinkAttribsByPayLinkNo(Long.valueOf(payLinkNo)) ;
		//加载购物条款
		PayLinkBaseInfo baseInfo = loadBaseInfo() ;
		String shoptermsName = baseInfo.getShoptermsName() ;
		String pathTail = SHOP_TERM + baseInfo.getMemberCode() + "/" + baseInfo.getShoptermsName() ;
		return new ModelAndView(payLinkDetailView)
			.addObject("payLink", payLink)
			.addObject("payLinkAttribs", payLinkAttribs)
			.addObject("shoptermsName", shoptermsName)
			.addObject("pathTail", pathTail);
	}
	/**
	 * 支付链克隆处理
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView payLinkClone(final HttpServletRequest request, final HttpServletResponse response){
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		if(null == payLinkNo || "".equals(payLinkNo)){
			return new ModelAndView(queryView).addObject(PAY_LINK_NULL, PAY_LINK_NULL) ;
		}
		PayLink payLink = this.payLinkService.findPayLinkByPayLinkNo(Long.valueOf(payLinkNo)) ;
		List<PayLinkAttrib> payLinkAttribs = this.payLinkService.findPayLinkAttribsByPayLinkNo(Long.valueOf(payLinkNo)) ;
		//页面币种显示处理
		for(PayLinkAttrib payLinkAttrib : payLinkAttribs){
			for(CurrencyCodeEnum item : CurrencyCodeEnum.values()){
				if(item.getCode().equals(payLinkAttrib.getCurrencyCode())){
					payLinkAttrib.setCurrencyCodeDes(item.getDesc());
				}
			}
		}
		CurrencyCodeEnum[] values = CurrencyCodeEnum.values();
		//加载购物条款
		PayLinkBaseInfo baseInfo = loadBaseInfo() ;
		String shoptermsName = baseInfo.getShoptermsName() ;
		String pathTail = SHOP_TERM + baseInfo.getMemberCode() + "/" + baseInfo.getShoptermsName() ;
		List<LogoPicture> logoPictures = this.logoPicService.findPicByMemberCode(Long.valueOf(SessionHelper.getMemeberCodeBySession())) ;
		FeeEnum[] fees = FeeEnum.values() ;
		return new ModelAndView(cloneView).addObject("payLink", payLink)
				.addObject("payLinkAttribs", payLinkAttribs)
				.addObject("currencyCodes", values)
				.addObject("shoptermsName", shoptermsName)
				.addObject("pathTail", pathTail)
				.addObject("logoPictures", logoPictures)
				.addObject("fees", fees);
	}
	private void convertOssURL(List<LogoPicture> logoPictures){
		MyOSS oss = new MyOSS(ossKey);
		JSONObject rawToken=null;
		OSSClient client=null;
		try {
			rawToken = oss.init(ossSubDir);
			client = oss.getOSSClient();
		} catch (UnsupportedOperationException e) {
			logger.error("oss error:"+e.getMessage());
		} catch (MyOSSException e) {
			logger.error("oss error:"+e.getMessage());
		} catch (IOException e) {
			logger.error("oss error:"+e.getMessage());
		}
		Date date=new Date(System.currentTimeMillis()+3600*1000*5);
		java.net.URL url=null;
		for(LogoPicture logo:logoPictures){
			if(client!=null&&rawToken!=null&&org.apache.commons.lang.StringUtils.isNotBlank(logo.getPicturePath())){
				url=client.generatePresignedUrl(rawToken.getString("bucket"), ossRootDir+logo.getPicturePath(), date);
				logo.setPicturePath(url.toString());
			}
		}
	}
	//--------------------------setter-----------------------------------------------------------------------------------
	public void setIndexView(final String indexView) {
		this.indexView = indexView;
	}
	public void setQueryView(final String queryView) {
		this.queryView = queryView;
	}
	public void setBasicInfo(final String basicInfo) {
		this.basicInfo = basicInfo;
	}
	public void setShopTermView(final String shopTermView) {
		this.shopTermView = shopTermView;
	}
	public void setContactView(final String contactView) {
		this.contactView = contactView;
	}
	public void setPayLinkInvalidView(final String payLinkInvalidView) {
		this.payLinkInvalidView = payLinkInvalidView;
	}
	public void setPayLinkService(final PayLinkService payLinkService) {
		this.payLinkService = payLinkService;
	}
	public void setShoptermFileService(final ShoptermFileService shoptermFileService) {
		this.shoptermFileService = shoptermFileService;
	}
	public void setPreviewView(final String previewView) {
		this.previewView = previewView;
	}
	public void setPayLinkDetailView(final String payLinkDetailView) {
		this.payLinkDetailView = payLinkDetailView;
	}
	public void setCloneView(final String cloneView) {
		this.cloneView = cloneView;
	}
	public void setExcelView(final String excelView) {
		this.excelView = excelView;
	}
	public void setLogoPicService(final LogoPicService logoPicService) {
		this.logoPicService = logoPicService;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getOssKey() {
		return ossKey;
	}
	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}
	public String getOssSubDir() {
		return ossSubDir;
	}
	public void setOssSubDir(String ossSubDir) {
		this.ossSubDir = ossSubDir;
	}
	public String getOssRootDir() {
		return ossRootDir;
	}
	public void setOssRootDir(String ossRootDir) {
		this.ossRootDir = ossRootDir;
	}
	
	
}
