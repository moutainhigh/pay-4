package com.pay.app.controller.base.bankacct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.pay.app.base.api.common.enums.BspIdentityEnum;
import com.pay.app.base.exception.LoginTimeOutException;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.app.facade.dto.Relation;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.enterprise.LiquidateInfoService;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.fundout.autofundout.service.AutoFundoutConfigService;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.exception.AppException;

/**
 * 功能：企业增加银行卡号，用于提现等功能的银行账户列表
 * @author 戴德荣
 * 
 */
public class CorpBankAcctController extends MultiActionController {
	protected static final Log log = LogFactory.getLog(CorpBankAcctController.class);
	//service
	//
	private BankAcctServiceFacade bankAcctService;
	private LiquidateInfoService liquidateInfoService;
	private EnterpriseBaseService enterpriseBaseService;
    //查询是否有绑定自动提现功能的接口
	private AutoFundoutConfigService autoFundoutConfigService;
	//页面View
	protected String editBantCardView ;
	protected String addBankCardSuccessView ;
	protected String editBankCardFailedView ;
	protected String bindCardNumFailedView ;
	protected String cardExistsErrorView ;
	protected String corpCardErrorView;
	protected String bankCardListView;
	//上传授权委托书模板保存路径配置部分
	//private static final String authTemplateSavePath = "/file/authTemplate/";
	private String authTemplateSavePath ;
	//阿里云存储密钥
	private String ossKey;
	//阿里云存储子目录
	private String ossSubDir;
	//阿里云存储根目录
	private String ossRootDir;
	/**
	 * 银行卡已经存在
	 */
	private static final int FAIL_BAKD_ACCT_IS_EXIST = 2;

	

	/**
	 * 查询已绑定的银行卡列表
	 * @return
	 */
	public ModelAndView  queryBankCard(final HttpServletRequest request,final HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		List<LiquidateInfo> liquidateInfos=liquidateInfoService.queryBankCardList(memberCode);//银行卡列表
		return new ModelAndView(bankCardListView).addObject("liquidateInfos",liquidateInfos).addObject("bankCardNumber",liquidateInfos.size());
	}
	/**
	 * 显示银行卡号列表
	 * @param request
	 * @param response
	 * @return 
	 * @author 戴德荣
	 * @throws LoginTimeOutException 
	 */
	public ModelAndView editBankCard(final HttpServletRequest request,
			final HttpServletResponse response)  {
	
		LoginSession loginSession = SessionHelper.getLoginSession();
		//如果是交易商，不让走编辑 
		if(BspIdentityEnum.CORP_TRADING.getIdentity() ==   loginSession.getBspIdentity()){
			return new ModelAndView("redirect:/error.htm?method=noFeature");
		}
		
		String memberName = "";// 会员姓名
		//查询是否有修改的卡号
		Long liquidateId = ServletRequestUtils.getLongParameter(request, "liquidateId",-1);
		LiquidateInfo defaultLiquidateInfo = null;
		if(liquidateId != -1){
			defaultLiquidateInfo = liquidateInfoService.getById(liquidateId);
			if(defaultLiquidateInfo != null ){
				//判断权限
				if(! liquidateInfoService.hasPromisson(new Long(loginSession.getMemberCode()), defaultLiquidateInfo.getMemberCode())){
					return new ModelAndView("redirect:/error.htm?method=noFeature"); 
				}
				
				
				boolean isNotBindAutoFo =   autoFundoutConfigService.findByMemberCodeAndBankCard(Long.valueOf(defaultLiquidateInfo.getMemberCode()), defaultLiquidateInfo.getBankAcct(), String.valueOf(defaultLiquidateInfo.getBankId()));
				if(! isNotBindAutoFo){
					String errorMsg = "该卡已设定委托提现，若要修改必须先取消委托提现";
					return new ModelAndView(editBankCardFailedView).addObject("errorMsg", errorMsg);
				}
				memberName = defaultLiquidateInfo.getAcctName();
			}
			
		}
		if(liquidateId == -1 || defaultLiquidateInfo==null){//如果是新增加 ，则判定卡片是否大于10张卡
			int countCard = liquidateInfoService.getCountByMemberCode(Long.valueOf(loginSession.getMemberCode()));
			if(countCard >= 10)
				return new ModelAndView(bindCardNumFailedView);
		}
		
		
		
		List<ProvinceDTO> provinceList = bankAcctService.getAllProvince();
		List<Relation> relationList = new ArrayList<Relation>();
		if (provinceList != null && !provinceList.isEmpty())
		{
			for (int i = 0; i < provinceList.size(); i++) {
				ProvinceDTO province = provinceList.get(i);
				List<CityDTO> cityList = bankAcctService.getAllCity(province.getProvincecode());
				for (int j = 0; j < cityList.size(); j++) {
					CityDTO city = cityList.get(j);
					Relation relation = new Relation();
					relation.setFatherCode(province.getProvincecode().toString());
					relation.setCode(city.getCitycode().toString());
					relation.setName(city.getCityname());
					relationList.add(relation);
				}
			}
		}
		String param="bankacct";
		List<Bank> bankList = bankAcctService.getBindAllBankList(param);
		//查询是否有默认的卡号
//		Long liquidateId = ServletRequestUtils.getLongParameter(request, "liquidateId",-1);
//		LiquidateInfo defaultLiquidateInfo = null;
//		if(liquidateId != -1){
//			defaultLiquidateInfo = liquidateInfoService.getByMemberCodeAndId(memberCode, liquidateId);
//		}
		//查询中文开户信息
		if(memberName==null || memberName.length()==0){
			EnterpriseBase ebase = enterpriseBaseService.findByMemberCode(Long.valueOf(loginSession.getMemberCode()));
			memberName = ebase.getZhName();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("provinceList",provinceList);
		model.put("relationList",relationList);
		model.put("bankList",bankList);
		model.put("memberName",memberName);
		model.put("bankNum",bankList.size());
		model.put("defaultInfo", defaultLiquidateInfo);
		return new ModelAndView(editBantCardView,model);
	}
	
	/**
	 * 添加银行卡号
	 * @param request
	 * @param response
	 * @return
	 * @author 戴德荣
	 * @throws Exception 
	 */
	public ModelAndView addBankCard(final HttpServletRequest request,
			final HttpServletResponse response, final LiquidateInfo corpBankAcc) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
	    CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file") ;
	    String dbRelativePath = "" ;
		if(null != file && file.getSize() > 0){
			Map<String,String> map = this.uploadAuthTemplate(request,response);
			String value = map.get("0000");
			dbRelativePath = map.get("dbRelativePath") ;
			if(StringUtils.isEmpty(value)){
				Set<Entry<String,String>> entrySet = map.entrySet();
				Iterator<Entry<String, String>> iterator = entrySet.iterator();
				while(iterator.hasNext()) {
					Entry<String, String> next = iterator.next();
					new ModelAndView(editBantCardView).addObject(next.getKey(),next.getValue());
				}
			}
		}
		corpBankAcc.setDbRelativePath(dbRelativePath);
		String allowForPrivate = request.getParameter("allowForPrivate") ;
		String memberName = request.getParameter("memberName") ;
		//如果是交易商
		LoginSession loginSession = SessionHelper.getLoginSession();
		//如果是交易商，不让走编辑 
		if(BspIdentityEnum.CORP_TRADING.getIdentity() ==   loginSession.getBspIdentity()){
			return new ModelAndView("redirect:/error.htm?method=noFeature");
		}
		
		String memberCode = request.getSession().getAttribute("memberCode").toString();
		Map<String, Object> model = new HashMap<String, Object>();
		String bankAcctId =  corpBankAcc.getBankAcct();
		if(StringUtils.isBlank(bankAcctId) || bankAcctId.trim().length() < 8 || bankAcctId.trim().length()> 32){
			
			model.put("errorMsg",ErrorCodeEnum.BANK_CARD_LENGTH_ERROR.getMessage());
			return new ModelAndView(corpCardErrorView,model);
		}
		boolean isUpdate  = ( corpBankAcc.getLiquidateId()!=null && corpBankAcc.getLiquidateId()>0) ?  true : false;
		//如果是更新
		if(isUpdate){
			boolean isNotBindAutoFo = false;
			boolean resultBool = false;
			String errorMsg = "";
			//先判断是否有绑定提现 ,如果是，则卡号不能修改
			LiquidateInfo liquidateInfo =  liquidateInfoService.getById(corpBankAcc.getLiquidateId());
			corpBankAcc.setAcctName(liquidateInfo.getAcctName());
			if(liquidateInfo != null ){
				//判断是否修改的是卡号
				if( ! liquidateInfo.getBankAcct().equals(corpBankAcc.getBankAcct())){
					isNotBindAutoFo =   autoFundoutConfigService.findByMemberCodeAndBankCard(Long.valueOf(memberCode), liquidateInfo.getBankAcct(), String.valueOf(liquidateInfo.getBankId()));
					if(isNotBindAutoFo){
						resultBool =  (liquidateInfoService.addOrUpdateCorpBankAcctRnTx(corpBankAcc,new Long( memberCode), corpBankAcc.getBankAcct()) == 1);
					}else{
						errorMsg = "该卡已设定委托提现，不能修改卡号，如需修改，必须取消委托提现";
						model.put("errorMsg",errorMsg);
						return new ModelAndView(editBankCardFailedView,model);
					}
				}else{
					int updateValue = liquidateInfoService.addOrUpdateCorpBankAcctRnTx(corpBankAcc,new Long( memberCode), corpBankAcc.getBankAcct());
					if( updateValue == FAIL_BAKD_ACCT_IS_EXIST){
						return new ModelAndView(cardExistsErrorView);
					}
					resultBool =  (updateValue == 1);
				}
			}else{
				errorMsg = "该卡不存在或已被删除了";
			}
			if(resultBool){
				model.put("isUpdate",isUpdate);
				model.put("memberCode",liquidateInfo.getMemberCode());
				return new ModelAndView(addBankCardSuccessView,model);
			}
			else{
				errorMsg = "卡号已存在，不能重复添加同一卡号";
				model.put("errorMsg",errorMsg);
				return new ModelAndView(editBankCardFailedView,model);
			}
		}
		
		//设置用户名
		//查询中文开户信息
		if(StringUtils.isEmpty(allowForPrivate)){//不允许对私提现
			EnterpriseBase ebase = enterpriseBaseService.findByMemberCode(Long.valueOf(memberCode));
			//String memberName = ebase.getZhName();
			memberName = ebase.getZhName() ;
			corpBankAcc.setAcctName(memberName);
		}else{
			corpBankAcc.setAcctName(memberName);
		}
		try{
			int updateOrSaveCount = liquidateInfoService.addOrUpdateCorpBankAcctRnTx(corpBankAcc,new Long( memberCode), corpBankAcc.getBankAcct());
			if(updateOrSaveCount == 2){
				return new ModelAndView(cardExistsErrorView);
			}
		}catch (AppException e) {
			return new ModelAndView(bindCardNumFailedView);
		}
		
		return new ModelAndView(addBankCardSuccessView).addObject("isUpdate", isUpdate);
	}
	/**
	 * 删除卡片(AJAX) 成功打印S,失败打印F
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView removeBankCard(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String memberCode = request.getSession().getAttribute("memberCode").toString();
		
		int cardCount = liquidateInfoService.getCountByMemberCode(Long.valueOf(memberCode));
		boolean isNotBindAutoFo = false;
		boolean resultBool = false;
		String errorMsg = "";
		if (cardCount != 1) { 
			Long id  = ServletRequestUtils.getLongParameter(request, "liquidateId");
			//先判断是否有绑定提现
			LiquidateInfo liquidateInfo =  liquidateInfoService.getById(id);
			if(liquidateInfo != null ){
				isNotBindAutoFo =   autoFundoutConfigService.findByMemberCodeAndBankCard(Long.valueOf(memberCode), liquidateInfo.getBankAcct(), String.valueOf(liquidateInfo.getBankId()));
				if(isNotBindAutoFo){
					resultBool =  liquidateInfoService.removeCorpAcctByMemberCodeAndId(memberCode, id);
				}else{
					errorMsg = "该卡已绑定,不能删除";
				}
			}else{
				errorMsg = "该卡不存在或已经被删除了";
			}
		}else{
			errorMsg = "删除失败，至少需要保留一张银行卡！";
		}
		
		
		response.setContentType("text/plain;charset=UTF-8");
		String result = resultBool ? "S" : errorMsg;
		response.getWriter().write(result);
		return null;
		//return new ModelAndView(removeBantCardSuccessView);
	}
	
	/**
	 * 设置默认卡片(AJAX) 成功打印S,失败打印F
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView setDefaultBankCard(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String memberCode = request.getSession().getAttribute("memberCode").toString();
		Long id  = ServletRequestUtils.getLongParameter(request, "liquidateId");
		boolean resultBool =  liquidateInfoService.setDefaultCordAcctRnTx(memberCode, id);
		String result = resultBool ? "S" : "F";
		response.getWriter().write(result);
		return null;
		//return new ModelAndView(removeBantCardSuccessView);
	}
	
	/**
	 * 取消默认卡片(AJAX) 成功打印S,失败打印F
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView setNotDefaultBankCard(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String memberCode = request.getSession().getAttribute("memberCode").toString();
		Long id  = ServletRequestUtils.getLongParameter(request, "liquidateId");
		boolean resultBool =  liquidateInfoService.setNotDefaultCordAcctRnTx(memberCode,id);
		String result = resultBool ? "S" : "F";
		response.getWriter().write(result);
		return null;
		//return new ModelAndView(removeBantCardSuccessView);
	}
	/**
	 * 下载委托授权书模板
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downAuthTemplate(final HttpServletRequest request, final HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		String path = getServletContext().getRealPath("/ftl/base/bankcard/corp") ;
		String fileName = request.getParameter("fileName") ;
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
	 * 商户授权委托书上传
	 * @param request
	 * @param response
	 * @return
	 */
	public Map uploadAuthTemplate(HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
	    CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file") ;
	    String dbRelativePath = "" ;
	    if(null != file && file.getSize() > 0){
	    	try {
				//String contentType = file.getContentType() ;
	    		long size = file.getSize() ;
				String originalFileName = file.getOriginalFilename() ;
				String extFileName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length()) ;
				if(!extFileName.equals(".doc") && !extFileName.equals(".docx")){
					Map<String,String> map = new HashMap<String,String>();
					map.put("fileTypeError", "只能上传.doc或docx后缀的文档！");
					return map;
				}
				if (size > 2 * 1024 *1024) {
					Map<String,String> map = new HashMap<String,String>();
					map.put("moreThanMaxSize", "文件大小不能超过2M!");
					return map ;
				}
				String memberCode = SessionHelper.getMemeberCodeBySession() ;
				String newFileName = this.renameFileName(extFileName) ;
				dbRelativePath = memberCode + "/" + newFileName ;
				String name = authTemplateSavePath + dbRelativePath ;
				File dest = new File(name) ;
				if(!dest.getParentFile().exists()){
					dest.getParentFile().mkdirs() ;
					dest.createNewFile() ;
				}
				file.transferTo(dest);
				//added by yanshichuan stored file on aliyun
				MyOSS oss = new MyOSS(ossKey);
				JSONObject rawToken;
				try {
					rawToken = oss.init(ossSubDir);
					OSSClient client = oss.getOSSClient();
					PutObjectResult result = client.putObject(rawToken.getString("bucket"), ossRootDir+"/"+ossSubDir+"/"+dbRelativePath, dest);
				} catch (UnsupportedOperationException e) {
					e.printStackTrace();
				} catch (MyOSSException e) {
					e.printStackTrace();
				}
				if(dest.exists()){
					dest.delete();
				}
			
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }else{
	    	Map<String,String> map = new HashMap<String,String>();
			map.put("fileNotNull", "上传文件不能为空，请选择！");
	    	return map;
	    }
		    Map<String,String> map = new HashMap<String,String>();
			map.put("0000", "上传成功！");
			map.put("dbRelativePath", dbRelativePath) ;
		return map;
	}
	//重构文件名
	private String renameFileName(String extFileName){
		String newFileName = "" ;
		newFileName = UUID.randomUUID() + extFileName ;
		return newFileName ;
	}
	//------------------------setter 
	public AutoFundoutConfigService getAutoFundoutConfigService() {
		return autoFundoutConfigService;
	}

	public void setAutoFundoutConfigService(
			final AutoFundoutConfigService autoFundoutConfigService) {
		this.autoFundoutConfigService = autoFundoutConfigService;
	}

	public void setBankAcctService(final BankAcctServiceFacade bankAcctService) {
		this.bankAcctService = bankAcctService;
	}

	public void setLiquidateInfoService(final LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}

	public void setEnterpriseBaseService(final EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setEditBantCardView(final String editBantCardView) {
		this.editBantCardView = editBantCardView;
	}

	public void setAddBankCardSuccessView(final String addBankCardSuccessView) {
		this.addBankCardSuccessView = addBankCardSuccessView;
	}

	public void setBindCardNumFailedView(final String bindCardNumFailedView) {
		this.bindCardNumFailedView = bindCardNumFailedView;
	}

	public void setCardExistsErrorView(final String cardExistsErrorView) {
		this.cardExistsErrorView = cardExistsErrorView;
	}

	public void setEditBankCardFailedView(final String editBankCardFailedView) {
		this.editBankCardFailedView = editBankCardFailedView;
	}

	public void setCorpCardErrorView(final String corpCardErrorView) {
		this.corpCardErrorView = corpCardErrorView;
	}

	public void setAuthTemplateSavePath(String authTemplateSavePath) {
		this.authTemplateSavePath = authTemplateSavePath;
	}
	public void setBankCardListView(String bankCardListView) {
		this.bankCardListView = bankCardListView;
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
