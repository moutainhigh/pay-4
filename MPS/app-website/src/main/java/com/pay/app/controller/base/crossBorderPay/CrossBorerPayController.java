package com.pay.app.controller.base.crossBorderPay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.pay.acc.acct.service.AcctService;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.SettleCoreClientService;
import com.pay.app.validator.CrossBorerPayValidator;
import com.pay.execlUtil.PoiExcel2k3Helper;
import com.pay.fi.dto.KfPayResource;
import com.pay.fi.dto.KfPayTrade;
import com.pay.fi.dto.KfPayTradeDetail;
import com.pay.fi.service.CrossBorerPayService;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.ZipUtils;

@Controller
public class CrossBorerPayController {
	
	Log logger = LogFactory.getLog(CrossBorerPayController.class);
	@Autowired
	@Qualifier(value = "acc-acctService")
	private AcctService acctService;

	@Autowired
	@Qualifier(value = "ossUtils")
	private MyOSS myoss;
	@Autowired
	@Qualifier(value = "fo-order-paymentValidateService")
	private PaymentValidateService paymentValidateService;

	@Autowired
	@Qualifier(value = "fi-crossBorerPayServiceImpl")
	private CrossBorerPayService crossBorerPayServiceImpl;
	
	@Autowired
	@Qualifier(value = "settleCoreClientService")
	private SettleCoreClientService settleCoreClientService;
	
	@Autowired
	@Qualifier(value="fi-crossBorerPayValidator")
	private CrossBorerPayValidator crossBorerPayValidator;
	/**
	 * 跨境付款into
	 * 
	 * @param result
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/corp/CrossBorerPayController/into.do")
	public String into(Map<String, Object> result, HttpSession session) {
		initAcctount(result, session);
		return "/base/crossBorerPay/crossBorerPay";
	}

	/**
	 * 跨境付款支付
	 */
	@RequestMapping(value = "/CrossBorerPayController/crossBorerPay.do")
	@ResponseBody
	public Map<String, Object> crossBorerPay(final HttpServletRequest request, HttpSession session,
			String payPassword,String batchNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			long partnerId=Long.valueOf(session.getAttribute("memberCode").toString());
			String message = paymentValidateService.validatePaymentPassword(partnerId, 101, loginSession.getOperatorId(),
					payPassword);
			if (StringUtil.isNull(message)) {
				Map<String, Object> applyparaMap=new HashMap<String, Object>();
				KfPayTradeDetail kfPayTradeDetail=new KfPayTradeDetail();
				kfPayTradeDetail.setBatchNo(batchNo);
				kfPayTradeDetail.setPartnerId(partnerId);
				applyparaMap.put("entity", kfPayTradeDetail);
				settleCoreClientService.kfCrossBorerPayApply(applyparaMap);
				Map<String, Object> updateparaMap=new HashMap<String, Object>();
				updateparaMap.put("batchNo", batchNo);
				settleCoreClientService.KfPayTrandUpdate(updateparaMap);
				result.put("repCode", 0);
			} else {
				result.put("repCode", -2);
				result.put("message", message);
			}
		} catch (Exception e) {
			logger.error("跨境付款支付错误", e);
		}
		return result;

	}

	/**
	 * 文件解析
	 * 
	 * @param payFile
	 * @param type
	 * @return
	 * @throws InterruptedException
	 */
	private Map<String, Object> fileAnalysis(MultipartHttpServletRequest multipartRequest, HttpSession session,HttpServletRequest request,KfPayTrade kfPayTrade,Map<String, Object> result) {
		Map<String, Object> analysisMap=null;
		int repCode = 0;
		try {
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			String partnerId=session.getAttribute("memberCode").toString();
			kfPayTrade.setPartnerId(Long.valueOf(partnerId));
			kfPayTrade.setOperator(loginSession.getOperatorIdentity());
			MultipartFile payFileMultipart=multipartRequest.getFile("payFile");
			InputStream inputStream =multipartRequest.getFile("payFile").getInputStream();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			byte[] buffer = new byte[1024];  
			int len;  
			while ((len = inputStream.read(buffer)) > -1 ) {  
			    baos.write(buffer, 0, len);  
			}  
			baos.flush();                
			InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());  
			InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());  
			PoiExcel2k3Helper poiExcel2k3Helper = new PoiExcel2k3Helper();
			String bucket = myoss.init("/CrossBorerPay").getString("bucket");
			OSSClient client = myoss.getOSSClient();
			File accessoryFile=null;
			File payFile=null;
			File outFileZip=null;
			try {
				// 获取导入成功模版
				InputStream successTemplate = getSuccessTemplate(Integer.valueOf(kfPayTrade.getType()), client, bucket);
				// 获取上传文件excel数据
				ArrayList<ArrayList<String>> verify = poiExcel2k3Helper.readExcel(stream1, 0, "3-");
				// 文件解析
				analysisMap = crossBorerPayServiceImpl.fileAnalysis(verify,successTemplate,kfPayTrade);
				//获取文件路径
				String key=createKey(session, analysisMap.get("batchNo").toString());
				String fullPath=myoss.getFullPath(key);
				String typeName="";
				String type=kfPayTrade.getType();
				if(type.equals("1")){
					typeName="跨境付款";
				}else if(type.equals("2")){
					typeName="机票旅游"; 
				}else if(type.equals("3")){
					typeName="酒店住宿";
				}else if(type.equals("4")){
					typeName="留学教育";
				}
				//文件保存到数据库并且上传到文件服务器
				MultipartFile accessoryMultipart=multipartRequest.getFile("accessoryFile");
				String batchNo=analysisMap.get("batchNo").toString();
				
				if(accessoryMultipart.getSize()>0){
					accessoryFile=new File(accessoryMultipart.getOriginalFilename());
					payFile=new File(payFileMultipart.getOriginalFilename());
					File[] files=new File[]{accessoryFile,payFile};
					outFileZip=new File(typeName+"付款文件.zip");
					accessoryMultipart.transferTo(accessoryFile);
					payFileMultipart.transferTo(payFile);
					ZipUtils.compress(files, outFileZip);
					client.putObject(bucket,key+typeName+"付款文件.zip",outFileZip);
					crossBorerPayServiceImpl.savePayFileByKfResource(batchNo,key+typeName+"付款文件.zip",fullPath,typeName,batchNo+typeName+"付款文件.zip");
				}else{
					client.putObject(bucket,key+typeName+"付款文件.xls",stream2);
					crossBorerPayServiceImpl.savePayFileByKfResource(batchNo,key+typeName+"付款文件.xls",fullPath,typeName,batchNo+typeName+"付款文件.xls");
				}
				// 文件上传到文件服务器
				crossBorerPayServiceImpl.savePaySuccessFileByKfResource(analysisMap.get("batchNo").toString(),key+typeName+"付款明细文件.xls",fullPath,typeName);
				client.putObject(bucket,key+typeName+"付款明细文件.xls",(InputStream) analysisMap.get("successFile"));
				result.put("analysisResult", analysisMap.get("analysisResult"));
				result.put("batchNo", analysisMap.get("batchNo"));
				result.put("saveTradeOrderDetailList", analysisMap.get("saveTradeOrderDetailList"));
				result.put("payAmount",analysisMap.get("payAmount"));
				result.put("successFilePath", myoss.signURL(fullPath+key+typeName+"付款明细文件.xls"));
			} catch (IOException e) {
				logger.error("文件解析异常", e);
				repCode = -1;
			}finally{
				if(outFileZip!=null){
					deleteFile(payFile);
					deleteFile(accessoryFile);
					deleteFile(outFileZip);	
				}
			}
		} catch (MyOSSException e) {
			logger.error("文件服务异常", e);
			repCode = -2;
		} catch (Exception e1) {
			logger.error("系统服务异常", e1);
			repCode = -3;
		}
		result.put("repCode", repCode);
		return result;
	}
	
	private static void deleteFile(File file) {
		if (file == null || !file.exists()) {
			return;
		}
		// 单文件
		if (!file.isDirectory()) {
			boolean delFlag = file.delete();
			if (!delFlag) {
				throw new RuntimeException(file.getPath() + "删除失败！");
			} else {
				return;
			}
		}
		// 删除子目录
		for (File child : file.listFiles()) {
			deleteFile(child);
		}
		// 删除自己
		file.delete();
	}
	
	/**
	 * 创建文件上传key
	 * 
	 * @param session
	 * @param fileName
	 * @return
	 */
	private String createKey(HttpSession session, String fileName) {
		StringBuffer key = new StringBuffer("mps/CrossBorerPay/").append(session.getAttribute("memberCode").toString())
				.append("/").append(DateUtil.getNowDate("yyyyMMdd")).append("/").append(fileName);
		return key.toString();
	}

	/**
	 * 得到导入成功模版
	 * 
	 * @param type
	 * @param client
	 * @param bucket
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private InputStream getSuccessTemplate(int type, OSSClient client, String bucket) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("batchNo", type + 4);
		Map<String, Object> rpcMap = settleCoreClientService.kfPayResourceDownload(paraMap);
		KfPayResource kfPayResource = MapUtil.map2Object(KfPayResource.class,
				(HashMap<String, Object>) rpcMap.get("result"));
		OSSObject obj = client.getObject(bucket, kfPayResource.getFilePath());
		return obj.getObjectContent();
	}

	/**
	 * 文件验证
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/CrossBorerPayController/fileVerify.do")
	@ResponseBody
	public Map<String, Object> fileVerify(MultipartHttpServletRequest multipartRequest, int type,HttpSession session,HttpServletRequest request,KfPayTrade kfPayTrade) throws InterruptedException {
		Map<String, Object> result = new HashMap<String, Object>();
		int repCode = -1;
		try {
			MultipartFile accessoryMultipart=multipartRequest.getFile("accessoryFile");
			if(accessoryMultipart != null){
					if(accessoryMultipart.getSize()>0){
						if (accessoryMultipart.getSize()/(1024*1024)>10) {
							repCode=-3;
						}
						String uploadFileName=accessoryMultipart.getOriginalFilename();
						String fileSuffix=uploadFileName.substring(uploadFileName.lastIndexOf(46) + 1).toLowerCase();
						if(!crossBorerPayValidator.verifyFileSuffix(fileSuffix)){
							repCode=-4;
						}
					}
			}
			InputStream inputStream = multipartRequest.getFile("payFile").getInputStream();
			PoiExcel2k3Helper poiExcel2k3Helper = new PoiExcel2k3Helper();
			try {
				if(repCode==-1){
					repCode = 1;
					ArrayList<ArrayList<String>> verify = poiExcel2k3Helper.readExcel(inputStream, 0, "1-");
					if(verify.size()>102){
						repCode=-5;
					}else{
						repCode = crossBorerPayServiceImpl.fileVerify(verify, String.valueOf(type));
					}
					if(repCode==0){
						fileAnalysis(multipartRequest,session,request,kfPayTrade,result);
					}
				}
			} catch (Exception e) {
				logger.error("上传数据错误", e);
				repCode = -1;
			}
		} catch (Exception e) {
			logger.error("文件解析异常", e);
			repCode = -2;
		}
		result.put("repCode", repCode);
		return result;
	}

	/**
	 * 下载模版文件
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/CrossBorerPayController/downloadModel.do")
	@ResponseBody
	public Map<String, Object> downloadModel(String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("batchNo", type);
			Map<String, Object> rpcMap = settleCoreClientService.kfPayResourceDownload(paraMap);
			KfPayResource kfPayResource = MapUtil.map2Object(KfPayResource.class,
					(HashMap<String, Object>) rpcMap.get("result"));
			result.put("fileName", kfPayResource.getFileName());
			String url = myoss.signURL(kfPayResource.getUrl());
			result.put("downloadUrl", url);
		} catch (Exception e) {
			logger.error("下载模版文件错误", e);
		}
		return result;
	}

	/**
	 * 初始化账号
	 * 
	 * @param result
	 * @param session
	 * @param params
	 */
	private void initAcctount(Map<String, Object> result, HttpSession session) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", session.getAttribute("memberCode"));
		params.put("curCode", "CNY");
		Map<String, Object> acct = acctService.queryAcctBycurCodeAndmenberCode(params);
		result.put("data", acct);
	}
}
