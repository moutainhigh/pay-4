package com.pay.app.controller.base.cidverify;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.acc.service.cidverify.IdCardVerifyService;
import com.pay.acc.service.cidverify.dto.Cid2GovDTO;
import com.pay.acc.service.cidverify.dto.CidResult;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.cidverify.CidVerifyEnum;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.app.facade.dto.Relation;
import com.pay.app.filter.SafeControllerWrapper;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.login.LoginUtil;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.util.CheckUtil;
import com.pay.util.DESUtil;
import com.pay.util.FTPUtil;
import com.pay.util.FormatNumber;
import com.pay.util.RSAHelper;
/**
 * 实名认证
 * @author sunny.ying
 * @version 1.0
 * @data 2010-9-13 下午01:43:46
 */
public class CidVerifyController extends MultiActionController{
	protected static final Log log = LogFactory.getLog(CidVerifyController.class);
	private IdCardVerifyService idCardVerifyService;
	private CidVerify2GovServiceFacade cidVerify2GovService;	
	private CheckCodeService checkCodeService;	
	private BankAcctServiceFacade bankAcctService;

	
	private String certificationindexpage;// 实名认证首页	
	private String certificationmodepage;// 实名认证选择认证方式页面
	private String certificationuserinfopage;// 实名认证填写个人信息页面	
	private String certificationbankbindpage;// 实名认证填写银行卡信息页面	
	private String certificationresultpage;// 实名认证确认提交页面	
	private String success;// 实名认证结果	
	private String calFeeFail;	
	private String accountBalanceFail;
	private String bankVerifyResultPage; //银行卡鉴权认证结果页面
	private String verfityExitsPage; //已经做了实名认证
	private String accountLocked;	
	private String endpage;//已做过实名认证	
	private String accountExceptionPage;//账户状态不正常页面
	private String notEnoughBalancePage;//账户余额不够引导页面
	private String noDataInGov; //公安网查无此人
	private String verifingPage; //认证中页面
	private String isFree;
	private String fee;
	private String govAccount;
	
	private String uploadUrl;
	private String uploadPort;
	private String uploadUsername;
	private String uploadPassword;
	private String uploadPath;
	
	private final static Integer rmb_account = Integer.parseInt(MessageConvertFactory.getMessage("rmb_account"));//10;//人民币账户
	private final static Integer min_money = Integer.parseInt(MessageConvertFactory.getMessage("gov_amount"));//5000;//实名认证最少的费用 5000 厘(5元)
	/**
	 * 跳转至实名认证首页
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView cidverifyIndex(HttpServletRequest request,
			HttpServletResponse response) {
		LoginSession loginSession = SessionHelper.getLoginSession();
		int result = idCardVerifyService.getMemberVerifyState(Long.valueOf(loginSession.getMemberCode()));
		ModelAndView mv = new ModelAndView(certificationindexpage);
		if(result == CidVerifyEnum.verifing){
			mv= new ModelAndView(verifingPage);
		}
		if(result == CidVerifyEnum.result_gov){
			mv= new ModelAndView(endpage);
		}
		return mv;
	}

	/**
	 * 跳转至实名认证选择认证方式页面
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView cidverifyMode(HttpServletRequest request,
			HttpServletResponse response) {	
		LoginSession loginSession = SessionHelper.getLoginSession();
		int forward_result = idCardVerifyService.getMemberVerifyState(Long.valueOf(loginSession.getMemberCode()));
		if(forward_result == CidVerifyEnum.verifing){
			return new ModelAndView(verifingPage);
		}
		if(forward_result == CidVerifyEnum.result_gov){
			return new ModelAndView(endpage);
		}
		String checkBoxValue=request.getParameter("cbox") == null ?"":request.getParameter("cbox");
		if(!"checked".equals(checkBoxValue)){
			return new ModelAndView(certificationindexpage);
		}
		String 	memberCode = request.getSession().getAttribute("memberCode").toString();
		Long balance=cidVerify2GovService.getAccountBalance(Long.parseLong(memberCode), rmb_account);
		String hasBalance="";
		if(balance!=null && balance >= min_money){
			hasBalance="y";
		}
		return new ModelAndView(certificationmodepage).addObject("hasBalance", hasBalance);
	}

	/**
	 * 跳转至填写个人信息页面
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView cidverifyUserInfo(HttpServletRequest request,
			HttpServletResponse response) {	

		Long gov_fee = Long.parseLong(fee);
		double viewMoney = FormatNumber.round(gov_fee / 1000.00);
		LoginSession loginSession = SessionHelper.getLoginSession();
		int forward_result = idCardVerifyService.getMemberVerifyState(Long.valueOf(loginSession.getMemberCode()));
		if(forward_result == CidVerifyEnum.verifing){
			return new ModelAndView(verifingPage);
		}
		if(loginSession.getScaleType() != CidVerifyEnum.memberType){
			return new ModelAndView("redirect:/error.htm?method=individual");
		}
		if(forward_result == CidVerifyEnum.result_gov){
			return new ModelAndView(endpage);
		}
		String memberCode = loginSession.getMemberCode();
		boolean result=cidVerify2GovService.getAccountState(Long.parseLong(memberCode), rmb_account);
		if(!result){
			return new ModelAndView(accountLocked).addObject("message",ErrorCodeEnum.ACCT_PAYPWD_Lock.getMessage());
		}		
		if(!CidVerifyEnum.gov_model.equals(isFree)){			
			Long banlace=cidVerify2GovService.getAccountBalance(Long.parseLong(memberCode), rmb_account);//校验账户余额是否足够做一笔公安网实名认证
			if(banlace!=null && banlace < Integer.parseInt(fee)){
				double banlace_double=FormatNumber.round(banlace / 1000.00);
				return new ModelAndView(notEnoughBalancePage).addObject("banlace",banlace_double).addObject("min_money",viewMoney);
			}
		}

		String loginName = loginSession.getLoginName();//登陆账户名
		String name = loginSession.getVerifyName();
		String idc = "";//idc 为用户身份证，为空让用户自己输入
		//String verifyFlag = request.getParameter("verifyflag")==null? CidVerifyEnum.gov_validate :request.getParameter("verifyflag");//认证方式 默认为公安网
		String verifyFlag = CidVerifyEnum.gov_validate+"_upload";
		ModelAndView mv = new ModelAndView();
		if(!"".equals(verifyFlag)){
			mv = new ModelAndView(certificationuserinfopage);
			mv.addObject("ln",loginName);
			mv.addObject("vf",verifyFlag);
			mv.addObject("vn",name);
			mv.addObject("idcno",idc);
			mv.addObject("viewMoney",viewMoney);
			mv.addObject("isFree",isFree);
		}
		else{
			mv = new ModelAndView(certificationindexpage);
		}
		return mv;
	}
	
	/**
	 * 查询用户余额控制器
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public void confirmAccountBalance(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        out = response.getWriter();
        boolean flag=false;
		Long balance=1L;
		String 	memberCode = request.getSession().getAttribute("memberCode").toString();
		balance=cidVerify2GovService.getAccountBalance(Long.parseLong(memberCode), rmb_account);
		if(balance!=null && balance >= Integer.parseInt(fee)){
			flag=true;
		}		
		out.print(flag);
        out.flush();
        out.close();		
	}
	
	/**
	 * 上传身份证正反面 ajax请求
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public void uploadAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception{ 	
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        out = response.getWriter();
        String result ="上传成功";
        
        String idcno = request.getParameter("idcno") == null ? "":request.getParameter("idcno");
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
		MultipartFile imgFile1  =  multipartRequest.getFile("img1"); 
		MultipartFile imgFile2  =  multipartRequest.getFile("img2");
		
		long n1=imgFile1.getSize();
		long n2=imgFile2.getSize();
		boolean b1=isImageFile(imgFile1.getOriginalFilename());
		boolean b2 = isImageFile(imgFile2.getOriginalFilename());
		
		out.print(result);
        out.flush();
        out.close();
	}
	
	/**
	 * 上传身份证正反面 form表单提交请求
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView uploadForm(HttpServletRequest request,
			HttpServletResponse response) {
		LoginSession floginSession = SessionHelper.getLoginSession();
		Long gov_fee = Long.parseLong(fee);
		double viewMoney = FormatNumber.round(gov_fee / 1000.00);
		String loginName = floginSession.getLoginName();
		String name = floginSession.getVerifyName();
		//个人信息		
		String vf = "".equals(request.getParameter("vf")) ? CidVerifyEnum.gov_validate :request.getParameter("vf");
		String vn = request.getParameter("vn") == null ? name:request.getParameter("vn");
		String ln = request.getParameter("ln") == null ? loginName :request.getParameter("ln");
		String idcno = request.getParameter("idcno") == null ? "":request.getParameter("idcno");
		String inname = request.getParameter("inname") == null ?"":request.getParameter("inname");
		ModelAndView mv = new ModelAndView(certificationuserinfopage);
		if(vf.equals(CidVerifyEnum.gov_validate+"_upload"))//上传
		{
			String uploadMsg = "上传成功";
			String uploadFlag = "1";
			MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
			MultipartFile imgFile1  =  multipartRequest.getFile("img1"); 
			MultipartFile imgFile2  =  multipartRequest.getFile("img2");
			
			long n1=imgFile1.getSize();
			long n2=imgFile2.getSize();
			boolean b1=isImageFile(imgFile1.getOriginalFilename());
			boolean b2 = isImageFile(imgFile2.getOriginalFilename());
			double fileMaxSize = 1.5*1024*1024;//上传图片最大限制1.5M
			
			if(n1<=0 || n2<=0){
				uploadMsg = "需要同时上传正反面";
				uploadFlag ="2";
			}else if(n1>fileMaxSize || n2>fileMaxSize){
				uploadMsg = "文件大小不能超过1.5M";
				uploadFlag ="2";
			}else if(!b1 || !b2){
				uploadMsg = "文件格式不正确";
				uploadFlag ="2";
			}else {
				boolean flag1=false;
				boolean flag2=false;
				try {
					String fileSuff1=imgFile1.getOriginalFilename().substring(imgFile1.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
					String fileSuff2=imgFile2.getOriginalFilename().substring(imgFile2.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
					flag1 = FTPUtil.uploadFile(uploadUrl, uploadPort, uploadUsername, uploadPassword, uploadPath, idcno+"_1."+fileSuff1, imgFile1.getInputStream());
					flag2 = FTPUtil.uploadFile(uploadUrl, uploadPort, uploadUsername, uploadPassword, uploadPath, idcno+"_2."+fileSuff2, imgFile2.getInputStream());
				} catch (IOException e) {
					log.error("CidVerifyController uploadForm Exception!",e);
					uploadMsg = "上传失败";
					uploadFlag ="2";
				}
				
				if(!flag1 || !flag2){
					uploadMsg = "上传失败";
					uploadFlag ="2";
				}
			}
			
			
			mv.addObject("uploadMsg",uploadMsg);
			mv.addObject("uploadFlag",uploadFlag);
			if("1".equals(uploadFlag)){
				mv.addObject("vf",CidVerifyEnum.gov_validate);
			}else{
				mv.addObject("vf",CidVerifyEnum.gov_validate+"_upload");
			}
		}
		mv.addObject("ln",ln);
		mv.addObject("inname",inname);
		mv.addObject("idcno",idcno);
		mv.addObject("vn",vn);
		mv.addObject("viewMoney",viewMoney);
		mv.addObject("isFree",isFree);
		return mv;
	}
	
	private boolean isImageFile(String fileName){
		if(! fileName.endsWith(".")){
			String fileSuff = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			return  ("jpg,gif,jpeg,bmp,png").indexOf(fileSuff) >= 0; 
		}
		return false;
	}
	
	/**
	 * 填写银行卡信息
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView cidverifyBankBind(HttpServletRequest request,
			HttpServletResponse response) {
		LoginSession floginSession = SessionHelper.getLoginSession();
		int forward_result = idCardVerifyService.getMemberVerifyState(Long.valueOf(floginSession.getMemberCode()));
		if(forward_result == CidVerifyEnum.verifing){
			return new ModelAndView(verifingPage);
		}
		if(forward_result == CidVerifyEnum.result_gov){
			return new ModelAndView(endpage);
		}
		String vf = request.getParameter("vf") == null ? "":request.getParameter("vf");
		String vn = request.getParameter("vn") == null ? "":request.getParameter("vn");
		String ln = request.getParameter("ln") == null ? "":request.getParameter("ln");
		String idcno = request.getParameter("idcno") == null ? "":request.getParameter("idcno");
		String inname = request.getParameter("inname") == null ? "":request.getParameter("inname");
		if(vf == null || vf.equals("") || vn.equals("")){
			return new ModelAndView(certificationindexpage);
		}
		String randCode =request.getSession().getAttribute("rand")== null ? CidVerifyEnum.err_code : request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
		request.getSession().removeAttribute("rand");
		ModelAndView mv = new ModelAndView();		
		if (!code.equalsIgnoreCase(randCode)){
			mv = new ModelAndView(certificationuserinfopage);
			mv.addObject("err","randcode");
		}
		else{
			List<ProvinceDTO> provinceList=bankAcctService.getAllProvince();
			List<Relation> relationList = new ArrayList<Relation>();
			if(provinceList != null && !provinceList.isEmpty())
			{
				for (int i = 0; i < provinceList.size(); i++) {
					ProvinceDTO province = (ProvinceDTO) provinceList.get(i);
					List<CityDTO> cityList = bankAcctService.getAllCity(province.getProvincecode());
					for (int j = 0; j < cityList.size(); j++) {
						CityDTO city = (CityDTO) cityList.get(j);
						Relation relation = new Relation();
						relation.setFatherCode(province.getProvincecode().toString());
						relation.setCode(city.getCitycode().toString());
						relation.setName(city.getCityname());
						relationList.add(relation);
					}
				}
			}
			if("".equals(vn)){
				LoginSession loginSession = SessionHelper.getLoginSession();
				vn = loginSession.getUserName();
			}
			String param="cidverify";
			List<Bank> bankList = bankAcctService.getBindAllBankList(param);		
			mv = new ModelAndView(certificationbankbindpage).addObject("provinceList",provinceList).
			addObject("relationList",relationList).addObject("bankList",bankList);		
			mv.addObject("err","null");
		}
		mv.addObject("ln",ln);
		mv.addObject("vf",vf);
		mv.addObject("vn",vn);
		mv.addObject("idcno",idcno);
		mv.addObject("inname",inname);
		return mv;
	}
	
	/**
	 * 确认提交信息页面
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView confirmInfo(HttpServletRequest request,
			HttpServletResponse response) {
		LoginSession floginSession = SessionHelper.getLoginSession();
		int forward_result = idCardVerifyService.getMemberVerifyState(Long.valueOf(floginSession.getMemberCode()));
		if(forward_result == CidVerifyEnum.verifing){
			return new ModelAndView(verifingPage);
		}
		if(forward_result == CidVerifyEnum.result_gov){
			return new ModelAndView(endpage);
		}
		Long gov_fee = Long.parseLong(fee);
		double viewMoney = FormatNumber.round(gov_fee / 1000.00);
		String loginName = floginSession.getLoginName();
		String name = floginSession.getVerifyName();
		//个人信息		
		String vf = "".equals(request.getParameter("vf")) ? CidVerifyEnum.gov_validate :request.getParameter("vf");
		String vn = request.getParameter("vn") == null ? name:request.getParameter("vn");
		String ln = request.getParameter("ln") == null ? loginName :request.getParameter("ln");
		String idcno = request.getParameter("idcno") == null ? "":request.getParameter("idcno");
		String inname = request.getParameter("inname") == null ?"":request.getParameter("inname");
		//银行信息
		String bankCode = request.getParameter("bankCode") == null ? "": request.getParameter("bankCode");//银行机构代码
		String bankId = request.getParameter("bankId") == null ? "" : request.getParameter("bankId");  //银行卡号
		String bankName = request.getParameter("bankName") == null ? "" : request.getParameter("bankName"); //开户行名称
		String province = request.getParameter("province") == null ? "" : request.getParameter("province"); //开户省份
		String city = request.getParameter("city") == null ? "" : request.getParameter("city");//开户城市
		if(vf == null || vf.equals("") || vn ==null || vn.equals("") || inname.length() < CidVerifyEnum.min_name_length || inname.length() > CidVerifyEnum.max_name_length){
			return new ModelAndView(certificationuserinfopage).addObject("vn",vn).addObject("ln",ln).addObject("vf",vf).addObject("viewMoney",viewMoney).addObject("isFree",isFree);
		}		
		ModelAndView mv = new ModelAndView();
		if(vf.equals(CidVerifyEnum.bank_validate) )//银行卡鉴权入口
		{			
			mv = new ModelAndView(certificationresultpage);
			mv.addObject("bankName",bankName);
			mv.addObject("provinceCode",province);
			mv.addObject("bankCode",bankCode);
			mv.addObject("bankId",bankId);
			mv.addObject("cityCode",city);
			try{
			mv.addObject("city", bankAcctService.getCityById(Integer.parseInt(city)));
			mv.addObject("province", bankAcctService.getProvinceById(Integer.parseInt(province)));			
			mv.addObject("bankOrgName",bankAcctService.getBankNameByCode(bankCode));
			}catch(Exception e){
				log.error("CidVerifyController confirmInfo Exception!",e);
				return new ModelAndView(certificationindexpage);
			}
		}		
		if(vf.equals(CidVerifyEnum.gov_validate))//公安网认证入口
		{
			String randCode = request.getSession().getAttribute("rand")== null ? CidVerifyEnum.err_code :request.getSession().getAttribute("rand").toString();
			String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
			request.getSession().removeAttribute("rand");
			if (code.equalsIgnoreCase(randCode)){ 
				mv = new ModelAndView(certificationresultpage);
				mv.addObject("vn",vn);
			}
			else{
				mv = new ModelAndView(certificationuserinfopage);
				mv.addObject("vn",vn);
				mv.addObject("err","randcode");
			}
		}
		mv.addObject("ln",ln);
		mv.addObject("vf",vf);
		mv.addObject("inname",inname);
		mv.addObject("idcno",idcno);
		return mv;
	}
	/**
	 *通过公安网进行实名认证 
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 * @throws Exception 
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView cid2Gov(HttpServletRequest req,
			HttpServletResponse response) throws NumberFormatException, SQLException  {
		SafeControllerWrapper request = new SafeControllerWrapper(req,new String[]{"payPwd"});
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		int forward_result = idCardVerifyService.getMemberVerifyState(Long.valueOf(loginSession.getMemberCode()));
		if(forward_result == CidVerifyEnum.verifing){
			return new ModelAndView(verifingPage);
		}
		if(forward_result == CidVerifyEnum.result_gov){
			return new ModelAndView(endpage);
		}
		Long gov_fee = Long.parseLong(fee);
		double viewMoney = FormatNumber.round(gov_fee / 1000.00);
		String vf=request.getParameter("validateMethod") == null ? CidVerifyEnum.gov_validate :request.getParameter("validateMethod");
		String ln = request.getParameter("ln")== null ?loginSession.getLoginName(): request.getParameter("ln");//账户名
		boolean result=false;
		result=cidVerify2GovService.getAccountState(Long.parseLong(memberCode), rmb_account);
		if(!result){
			return new ModelAndView(accountLocked).addObject("message",ErrorCodeEnum.ACCT_PAYPWD_Lock.getMessage());
		}
		Long banlace=1L;
		if(!CidVerifyEnum.gov_model.equals(isFree)){
			banlace=cidVerify2GovService.getAccountBalance(Long.parseLong(memberCode), rmb_account);//校验账户余额是否足够做一笔公安网实名认证
			if(banlace!=null && banlace < Integer.parseInt(fee)){
				double banlace_double=FormatNumber.round(banlace / 1000.00);
				return new ModelAndView(notEnoughBalancePage).addObject("banlace", banlace_double).addObject("min_money",viewMoney);
			}
		}
		String name = request.getParameter("vname") == null ?loginSession.getVerifyName():request.getParameter("vname");
		String no = request.getParameter("vidc")== null ? "": request.getParameter("vidc").trim();	
		String pwdMessage = "";//"支付密码不正确";
		if(ln ==null || name == null || no == null ||"".equals(no) ||"".equals(vf)){
			return new ModelAndView(certificationuserinfopage).addObject("ln",ln).addObject("vn",name).addObject("viewMoney",viewMoney).addObject("isFree",isFree);
		}
		String payPwd=request.getParameter("payPwd")==null?"":request.getParameter("payPwd");
		MaResultDto maResultDto = cidVerify2GovService.getPayPwdResultDto(memberCode,rmb_account,payPwd,SessionHelper.getOperatorIdBySession());
		VerifyResultDto verifyResultDto  = null;
		ModelAndView mv = null;
		switch(maResultDto.getResultStatus()){
		
		case 2://验证失败，账户未锁定
			verifyResultDto =(VerifyResultDto)maResultDto.getObject();
			if(verifyResultDto.getLeavingTime()==0){
				verifyResultDto =(VerifyResultDto)maResultDto.getObject();
				pwdMessage = MessageFormat.format(ErrorCodeEnum.ACCT_PAYPWD_Lock.getMessage(),verifyResultDto.getLeavingMinute());
				mv = new ModelAndView(accountLocked);
				return mv.addObject("message",pwdMessage);
			}
			pwdMessage =MessageFormat.format(ErrorCodeEnum.ACCT_PAYPWD_FAILNUM.getMessage(),new Object[]{verifyResultDto.getLeavingTime(),verifyResultDto.getTotalTime(),verifyResultDto.getLeavingMinute()});
			mv = new ModelAndView(certificationresultpage);
			return mv.addObject("pwdResult", CidVerifyEnum.pay_password_validate.toString())
				.addObject("message",pwdMessage).addObject("inname", name).addObject("idcno", no).addObject("ln", ln).addObject("vf", vf);
		case 3://验证失败，账户已锁定
			verifyResultDto =(VerifyResultDto)maResultDto.getObject();
			pwdMessage = MessageFormat.format(ErrorCodeEnum.ACCT_PAYPWD_Lock.getMessage(),verifyResultDto.getLeavingMinute());
			mv = new ModelAndView(accountLocked);
			return mv.addObject("message",pwdMessage);
		case 4://验证失败，账户异常
			mv = new ModelAndView(accountLocked);
			pwdMessage = ErrorCodeEnum.ACCT_PAYPWD_Lock.getMessage();
			return mv.addObject("message",pwdMessage);
		}			
		
		String mainOrderId = checkCodeService.getOrderId();
		String recvAcct = CidVerifyEnum.dealer;
		String payAcct = CidVerifyEnum.dealer;
		Integer balance_result = 0;//调用更新余额的结果 
//		AcctAttribDto acctAttribDto = cidVerify2GovService.getAccountAcctAttrib(Long.parseLong(memberCode), rmb_account);
		String order_fee = "0";
		if(!CidVerifyEnum.gov_model.equals(isFree)){
			order_fee= fee;
			//CalFeeRequest calFeeRequest = cidVerify2GovService.setCaculateFee(acctAttribDto, memberCode, mainOrderId);
			//CalFeeReponse	calFeeRespone = cidVerify2GovService.caculateFee(calFeeRequest);//PE计费接口
//			if(null != calFeeRespone){
//				boolean dealResult=calFeeRespone.isHasCaculatedPrice();//计费是否成功
//			if(!dealResult){
//				return new ModelAndView(calFeeFail);//计费不成功页面，提示用户系统错误
//			}			
//			CalFeeReponseDto calFeeReponseDto = cidVerify2GovService.setCalFeeReponseDto(calFeeRespone);		
//			balance_result = cidVerify2GovService.updateAccountBalance(calFeeReponseDto,CidVerifyEnum.dealTypeIn);
//			List<CalFeeDetailDto> list_calFeeDetailDto = calFeeReponseDto.getCalFeeDetailDtos();
//			for(CalFeeDetailDto calFeeDetailDto : list_calFeeDetailDto){
//				if(calFeeDetailDto.getCrdr() == CidVerifyEnum.cr){
//					payAcct = calFeeDetailDto.getAcctcode();
//				}else{
//					recvAcct = calFeeDetailDto.getAcctcode();
//				}
//			}		
//			if(balance_result != CidVerifyEnum.balanceResult){
//				return new ModelAndView(calFeeFail);//扣费不成功的页面提示
//				}
//			}
//			else{
//				return new ModelAndView(calFeeFail);//认证失败，系统计费不成功,调整到系统忙，请稍后重试
//			}
		}
		if(idCardVerifyService.addTransLog(mainOrderId, CidVerifyEnum.dealTypeIn, balance_result, recvAcct, payAcct, CidVerifyEnum.order_empty,null,order_fee) != null){
			log.info("-------实名认证  TransLog 交易日志表 记录成功-----");
		}else{
			log.error("-------实名认证  TransLog 交易日志表 记录失败-----");
		}
		Cid2GovDTO dto = new Cid2GovDTO();
		dto.setNo(no);
		dto.setName(name);
		Long idcId = idCardVerifyService.addCidVerifyDefaultState(memberCode, name, no);
		if(idCardVerifyService.editTransLogForLinkId(mainOrderId, idcId) != CidVerifyEnum.update_success){
			log.error("TransLog流水号为:"+mainOrderId+"的交易记录关联实名认证id:"+idcId+"失败 ！");
		}
		Map backMap = new HashMap();
		String back_orderId ="";
		if(idcId == null){
			if(!CidVerifyEnum.gov_model.equals(isFree)){
//				back_orderId = checkCodeService.getOrderId();
//				backMap = cidVerify2GovService.refundMember(acctAttribDto, memberCode, back_orderId);
//				if(backMap != null){
//					idCardVerifyService.addTransLog(back_orderId, (Integer)backMap.get("payType"), (Integer)backMap.get("updateResult"), (String)backMap.get("recvAcct"),(String)backMap.get("payAcct"), mainOrderId,idcId,fee);
//				}else{
//					log.error("会员："+memberCode+" 公安网退费 更新余额不成功");	
//					return new ModelAndView(accountBalanceFail);
//				}
			}
			return new ModelAndView(calFeeFail);//认证失败，跳转到系统忙，请稍后重试
		}
		try{
			mv = new ModelAndView(success);
			CidResult cr =idCardVerifyService.cid2Gov(dto);
			if(cr.isNoCid()){//查无此人 走退款流程
//				if(!CidVerifyEnum.gov_model.equals(isFree)){
//					back_orderId = checkCodeService.getOrderId();
//					backMap = cidVerify2GovService.refundMember(acctAttribDto, memberCode, back_orderId);
//					if(backMap != null){
//						idCardVerifyService.addTransLog(back_orderId, (Integer)backMap.get("payType"), (Integer)backMap.get("updateResult"), (String)backMap.get("recvAcct"),(String)backMap.get("payAcct"), mainOrderId,idcId,fee);
//					}else{
//						log.error("会员："+memberCode+" 公安网退费 更新余额不成功");
//						return new ModelAndView(accountBalanceFail);
//					}
//				}
				idCardVerifyService.updateMemberVerifyStatus(idcId, CidVerifyEnum.verifyFail);
				return new ModelAndView("redirect:/app/cidverifyUserInfo.htm?method=redirectNoDataInGov");
			}
			if(cr.getStateResult() == CidVerifyEnum.result_gov || cr.getStateResult() == CidVerifyEnum.verifyFail){
				Map map = new HashMap();
				if(!CidVerifyEnum.gov_model.equals(isFree)){
					map = cidVerify2GovService.doFeeToBusinessAccount(mainOrderId);
					if(map != null){
						idCardVerifyService.addTransLog(mainOrderId, (Integer)map.get("payType"), (Integer)map.get("updateResult"), (String)map.get("recvAcct"),(String)map.get("payAcct"), CidVerifyEnum.order_empty,idcId,fee);
					}else{
						log.error("实名认证中间科目账户 2181012010101： 更新余额不成功");
					}
				}
				
				idCardVerifyService.addTransLog(mainOrderId, (Integer)map.get("payType"), (Integer)map.get("updateResult"), (String)map.get("recvAcct"),(String)map.get("payAcct"), CidVerifyEnum.order_empty,idcId,govAccount);
				//map = cidVerify2GovService.doFeeToGovAccount(mainOrderId);
				
			}
			if(cr.getStateResult() == CidVerifyEnum.result_gov){
				cr.setStateResult(1);
			}
			if(cr.getStateResult() == CidVerifyEnum.verifyFail){//实名认证失败时，返回姓名与身份证号输入页面
				idCardVerifyService.updateMemberVerifyStatus(idcId, CidVerifyEnum.verifyFail);
				mv = new ModelAndView(certificationuserinfopage);
				//个人信息		
				String vn = request.getParameter("vn") == null ? name:request.getParameter("vn");
				mv.addObject("vn",vn);
				mv.addObject("ln",ln);
				mv.addObject("vf",vf);
				mv.addObject("inname",name);
				mv.addObject("idcno",no);
				mv.addObject("validate", "failed");
				return mv;
			}
			int result_boolean = idCardVerifyService.editIdcVerify(idcId, cr.getStateResult(), cr.getGovResult());
			
			/*if(result_boolean == 4){
				loginSession.setVerifyName(name);
				request.getSession().setAttribute("userSession", loginSession);
				request.getSession().setAttribute("verifyName", name);
				mv = new ModelAndView("redirect:/app/cidverifyUserInfo.htm?method=redirectSuccess");
			}
			if(result_boolean == CidVerifyEnum.verifing){
				mv = new ModelAndView("redirect:/app/cidverifyUserInfo.htm?method=redirectVerify");
			}
			if(result_boolean == CidVerifyEnum.verifyFail){
				mv = new ModelAndView("redirect:/app/cidverifyUserInfo.htm?method=redirectFail");
			}*/
			mv = new ModelAndView("redirect:/app/cidverifyUserInfo.htm?method=redirectSuccess");
			return mv;
		}catch(Exception e){		
			return new ModelAndView("redirect:/app/cidverifyUserInfo.htm?method=redirectVerify");
		}				
}
	
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView redirectSuccess(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(success).addObject("res",true);
	}
	
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView redirectFail(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(success).addObject("res",false);
	}
	
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView redirectVerify(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(verifingPage);
	}
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView redirectNoDataInGov(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(noDataInGov);
	}
	
	/**
	 * 通过银行卡鉴权进行实名认证
	 * @throws Exception 
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView cid2Bank(HttpServletRequest req,HttpServletResponse response) throws Exception {
		SafeControllerWrapper request = new SafeControllerWrapper(req,new String[]{"payPwd"});
		//我要哪些参数 (交行那边需要  哪些参数)
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		boolean result_bool=true;
		result_bool= cidVerify2GovService.checkQueryCidVerify(loginSession.getMemberCode());
		if(result_bool)
		{
			return new ModelAndView(verfityExitsPage);
		}
		Integer pwd_result=0;
		String vf=request.getParameter("validateMethod") == null ?"":request.getParameter("validateMethod");
		String ln = request.getParameter("ln");//支付账户名
		String name = request.getParameter("vname");
		String no = request.getParameter("vidc").trim();		
		
		String validateMethod=request.getParameter("validateMethod") == null ?"":request.getParameter("validateMethod");
		String bankId=request.getParameter("bankId") == null ?"":request.getParameter("bankId"); //银行卡号
		String idCard=request.getParameter("idCard") == null ?"":request.getParameter("idCard");//身份证号
		String trueName=request.getParameter("inname") == null ?"":request.getParameter("inname");//开户人姓名	
		String accountName=request.getParameter("accountName") == null ?"":request.getParameter("accountName");//账户名称
		String bankName=request.getParameter("bankName") == null ?"":request.getParameter("bankName");//开户行名称
		String bankCode=request.getParameter("bankCode") == null ?"":request.getParameter("bankCode");//银行机构代码
		String provinceCode=request.getParameter("provinceCode") == null ?"":request.getParameter("provinceCode");
		String cityCode=request.getParameter("cityCode") == null ?"":request.getParameter("cityCode");
		String bankArea=request.getParameter("bankArea") == null ?"":request.getParameter("bankArea");
		String bankOrgName=request.getParameter("bankOrgName") == null ?"":request.getParameter("bankOrgName");
		if(ln ==null || name == null || no == null ||"".equals(vf))
		{
			return new ModelAndView(certificationindexpage);
		}		
		LoginUtil loginUtil = new LoginUtil();
		int loginFailNum=0;
		AcctAttribDto acctAttirib=cidVerify2GovService.getAccountAcctAttrib(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
        if(acctAttirib==null || acctAttirib.getAllowOut().equals(0)){
            return new ModelAndView(accountLocked);
        }		
		loginFailNum=loginUtil.getLoginFailNum(memberCode, "", "");
		if(loginFailNum > CidVerifyEnum.lockNum)
		{
			cidVerify2GovService.doHandlerAccountLockRnTx(memberCode);  //账户锁定操作
			return new ModelAndView(accountLocked);//跳转到账户锁定的页面
		}
		String payPwd=request.getParameter("payPwd")==null?"":request.getParameter("payPwd");
		if("".equals(payPwd))
		{
			loginUtil.setLoginErrorNumLog(memberCode, "", "");
			Map map=new HashMap();
			map.put("bankCode", bankCode);map.put("province", bankArea);map.put("vf", vf);map.put("ln", ln);map.put("inname", name);map.put("idcno", no);map.put("validateMethod", validateMethod);map.put("bankId", bankId);map.put("idCard", idCard);
			map.put("trueName", trueName);map.put("accountName", accountName);map.put("bankName", bankName);map.put("bankOrgName", bankOrgName);map.put("provinceCode", provinceCode);map.put("cityCode", cityCode);
			return new ModelAndView(certificationresultpage,map).addObject("message", MessageFormat.format(MessageConvertFactory.getMessage("frozenapaypwdempty"),CidVerifyEnum.MAX_FAIL_NUM)).addObject("pwdResult", CidVerifyEnum.pay_password_validate.toString());
		}
		CheckUtil checkUtil = new CheckUtil();		
		String explorer_type=request.getParameter("relationlogin")==null?"":request.getParameter("relationlogin");
		if(!"".equals(explorer_type))
		{
			RSAHelper rsa=new RSAHelper();
			payPwd=rsa.getRSAString(payPwd);
			if(!checkUtil.checkPayPwd(payPwd))
			{
				loginUtil.setLoginErrorNumLog(memberCode, "", "");
				Map map=new HashMap();
				map.put("bankCode", bankCode);map.put("province", bankArea);map.put("vf", vf);map.put("ln", ln);map.put("inname", name);map.put("idcno", no);map.put("validateMethod", validateMethod);map.put("bankId", bankId);map.put("idCard", idCard);
				map.put("trueName", trueName);map.put("accountName", accountName);map.put("bankName", bankName);map.put("bankOrgName", bankOrgName);map.put("provinceCode", provinceCode);map.put("cityCode", cityCode);
				return new ModelAndView(certificationresultpage,map).addObject("message", MessageFormat.format(MessageConvertFactory.getMessage("frozenapaypwderror"),CidVerifyEnum.MAX_FAIL_NUM)).addObject("pwdResult", CidVerifyEnum.pay_password_validate.toString());
			}
		}
		else{
			if(!checkUtil.checkPayPwd(payPwd))
			{
				loginUtil.setLoginErrorNumLog(memberCode, "", "");
				Map map=new HashMap();
				map.put("bankCode", bankCode);map.put("province", bankArea);map.put("vf", vf);map.put("ln", ln);map.put("inname", name);map.put("idcno", no);map.put("validateMethod", validateMethod);map.put("bankId", bankId);map.put("idCard", idCard);
				map.put("trueName", trueName);map.put("accountName", accountName);map.put("bankName", bankName);map.put("bankOrgName", bankOrgName);map.put("provinceCode", provinceCode);map.put("cityCode", cityCode);
				return new ModelAndView(certificationresultpage,map).addObject("message", MessageFormat.format(MessageConvertFactory.getMessage("frozenapaypwderror"),CidVerifyEnum.MAX_FAIL_NUM)).addObject("pwdResult", CidVerifyEnum.pay_password_validate.toString());
			}
		}
		pwd_result = cidVerify2GovService.checkPayPassword(Long.parseLong(memberCode), rmb_account, payPwd);
		if(pwd_result == CidVerifyEnum.pay_password_validate)
		{
			loginUtil.setLoginErrorNumLog(memberCode, "", "");
			Map map=new HashMap();
			map.put("bankCode", bankCode);map.put("province", bankArea);map.put("vf", vf);map.put("ln", ln);map.put("inname", name);map.put("idcno", no);map.put("validateMethod", validateMethod);map.put("bankId", bankId);map.put("idCard", idCard);
			map.put("trueName", trueName);map.put("accountName", accountName);map.put("bankName", bankName);map.put("bankOrgName", bankOrgName);map.put("provinceCode", provinceCode);map.put("cityCode", cityCode);
			return new ModelAndView(certificationresultpage,map).addObject("pwdResult", CidVerifyEnum.pay_password_validate.toString()).addObject("message", MessageFormat.format(MessageConvertFactory.getMessage("frozenapaypwderror"),CidVerifyEnum.MAX_FAIL_NUM));
		}		
		boolean cidBank_result=false;
		boolean result = false;	
		if(bankId.equals(CidVerifyEnum.test_pass_id)){//暂时用交行的测试 id  和 银行卡号		
			idCard="9999999999";//交行连调测试数据
		}
		cidBank_result=idCardVerifyService.cidVerifyByBank(bankId, trueName, idCard);
		if(!cidBank_result){
			return new ModelAndView(bankVerifyResultPage).addObject("res", result);
		}		
		if(bankCode.equals("")){
			bankCode="200";//交通银行 代码  测试数据
		}
		MemberVerifyDto mvd =  new MemberVerifyDto();
		mvd.setMemberCode(new Long(memberCode));
		mvd.setName(name);
		mvd.setPaperNo(DESUtil.encrypt(no));//身份证号码加密 DESUtil.encrypt(no)
		mvd.setVerifyFlag(CidVerifyEnum.verifyFlag_bank);
		mvd.setPaperType(CidVerifyEnum.paperType);
		mvd.setIsPaperFile(CidVerifyEnum.isPaperFile);
		mvd.setPhoto("null");
		mvd.setErrorCode("null");
		mvd.setErrorMsg("null");	
		mvd.setBankAcctId(DESUtil.encrypt(bankId));
		mvd.setBankId(bankCode);
		mvd.setBranchBankName(bankName);
		mvd.setBranchBankId(Long.parseLong(bankCode));
		mvd.setProvince(Long.parseLong(provinceCode));
		mvd.setCity(Long.parseLong(cityCode));				
		if(cidBank_result){
			mvd.setStatus(1);
		}
		else
			mvd.setStatus(0);
		if(idCardVerifyService.saveCidVerify2Bank(mvd)!=null)
		    result=true;
		
		if(result){
					loginSession.setVerifyName(name);
					request.getSession().setAttribute("userSession", loginSession);
					request.getSession().setAttribute("verifyName", name);
		}
		return new ModelAndView(bankVerifyResultPage).addObject("res", result);
	}
	/**
	 * 实名认证结果
	 */
	@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
	public ModelAndView certification(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(success);
	}
	
	public void setCidVerify2GovService(
			CidVerify2GovServiceFacade cidVerify2GovService) {
		this.cidVerify2GovService = cidVerify2GovService;
	}
	
	public void setIdCardVerifyService(IdCardVerifyService idCardVerifyService) {
		this.idCardVerifyService = idCardVerifyService;
	}
	
	public void setCertificationindexpage(String certificationindexpage) {
		this.certificationindexpage = certificationindexpage;
	}

	public void setCertificationmodepage(String certificationmodepage) {
		this.certificationmodepage = certificationmodepage;
	}

	public void setCertificationuserinfopage(String certificationuserinfopage) {
		this.certificationuserinfopage = certificationuserinfopage;
	}

	public void setCertificationbankbindpage(String certificationbankbindpage) {
		this.certificationbankbindpage = certificationbankbindpage;
	}

	public void setCertificationresultpage(String certificationresultpage) {
		this.certificationresultpage = certificationresultpage;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	public void setEndpage(String endpage) {
		this.endpage = endpage;
	}

	public void setAccountExceptionPage(String accountExceptionPage) {
		this.accountExceptionPage = accountExceptionPage;
	}
	public void setNotEnoughBalancePage(String notEnoughBalancePage) {
		this.notEnoughBalancePage = notEnoughBalancePage;
	}
	public void setBankAcctService(BankAcctServiceFacade bankAcctService) {
		this.bankAcctService = bankAcctService;
	}
	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}
	public void setBankVerifyResultPage(String bankVerifyResultPage) {
		this.bankVerifyResultPage = bankVerifyResultPage;
	}
	public void setVerfityExitsPage(String verfityExitsPage) {
		this.verfityExitsPage = verfityExitsPage;
	}
	public void setCalFeeFail(String calFeeFail) {
		this.calFeeFail = calFeeFail;
	}
	public void setAccountBalanceFail(String accountBalanceFail) {
		this.accountBalanceFail = accountBalanceFail;
	}
	public void setNoDataInGov(String noDataInGov) {
		this.noDataInGov = noDataInGov;
	}

	public void setAccountLocked(String accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public void setVerifingPage(String verifingPage) {
		this.verifingPage = verifingPage;
	}

	public void setGovAccount(String govAccount) {
		this.govAccount = govAccount;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public void setUploadPort(String uploadPort) {
		this.uploadPort = uploadPort;
	}

	public void setUploadUsername(String uploadUsername) {
		this.uploadUsername = uploadUsername;
	}

	public void setUploadPassword(String uploadPassword) {
		this.uploadPassword = uploadPassword;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
}
