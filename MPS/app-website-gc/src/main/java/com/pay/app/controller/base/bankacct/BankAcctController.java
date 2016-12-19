package com.pay.app.controller.base.bankacct;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.app.facade.dto.Relation;
import com.pay.app.model.BankAcct;
import com.pay.app.service.bankacct.BankAcctService;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.BankBrancheInfoService;

/**
 * 会员银行卡绑定Controller
 * 
 * @author Sunny.Ying
 * 
 */
public class BankAcctController extends MultiActionController {
	protected static final Log log = LogFactory.getLog(BankAcctController.class);
	/**
	 * 实名认证服务
	 */
	private CidVerify2GovServiceFacade cidVerify2GovService;
	private String addBankAcctPage; // 添加 银行卡绑定页面
	private String bankAcctNumErrorPage; // 进入提示 已经有10张银行卡的信息页面
	private String notCheckErrorPage;// /进入提示没有进行实名认证的信息\
	private String success;// 添加银行卡成功页面
	private String fail;// 添加银行卡失败页面
	private String bankCodeFail;// 银行卡号与银行代码不匹配
	private String bankCardExitsFail;// 银行卡绑定失败，银行卡已经存在
	BankAcctServiceFacade bankAcctService;
	private BankAcctService bankAcctServiceBussiness;
	private BankBrancheInfoService bankBrancheInfoService ;
	private String editBankAcctPage;
	private String editSuccess;
	private String editFail;
	private String removeFail;
	private String removeForward;
	private String editCardExits;
	private String bankListFO;
	private String searchBanksFO;
	private String bankSelect;
	/**
	 * 创建失败
	 */
	private static final int CREATE_FAIL = 0;
	
	private static final int edit_success = 1;
	/**
	 * 银行卡已经存在
	 */
	private static final int CREATE_FAIL_BAKD_ACCT_IS_EXIST = 2;
	
	/**
	 * 银行卡超过10张
	 */
	private static final int CREATE_FAIL_MEMBERCODE_CARD_NUMBER_COUNT_ERROR = 3;
	
	/**
	 * 是否为主银行账户   0为主账户 
	 */
	private static final Integer  default_Account= 0;
	
	/**
	 *  验证状态标识    0、未验证;1、已验证;2、验证中（未打款）  ;3.验证中 ; 4 鉴权验证中
	 */
	private static final Integer  default_status= 1;
	
	public void queryBanksSelect(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
	}
	
	public ModelAndView queryBanksFO(HttpServletRequest request,HttpServletResponse response){
		String keyWord = request.getParameter("strs") == null ?"":request.getParameter("strs");
		String bankName = request.getParameter("bank") == null ?"":request.getParameter("bank");
		String provinceName = request.getParameter("province") == null ?"":request.getParameter("province");
		String cityName = request.getParameter("city") == null ?"":request.getParameter("city");
		String defaultBankNo = ServletRequestUtils.getStringParameter(request, "defaultBankNo", "");
		BankBrancheInfoDto bankBrancheInfoDto = new BankBrancheInfoDto() ;
		bankBrancheInfoDto.setBankName(bankName);
		bankBrancheInfoDto.setProvince(provinceName);
		bankBrancheInfoDto.setCity(cityName);
		bankBrancheInfoDto.setBankKaihu(keyWord);
		if(!"".equals(keyWord) &&!"".equals(bankName) && !"".equals(provinceName)&& !"".equals(cityName)){
			//List<SearchResultInfoDTO> bankList = bankAcctServiceBussiness.getBankAcctFO(bankName, provinceName, cityName, keyWord);
			List<BankBrancheInfoDto> bankList = this.bankBrancheInfoService.findByCondition(bankBrancheInfoDto) ;
			Map map = new HashMap();
			map.put("bankList", bankList);
			map.put("defaultBankNo", defaultBankNo);
			return new ModelAndView(bankSelect,map);
		}
		return null;
	}
	public ModelAndView readioBanksFO(HttpServletRequest request,HttpServletResponse response){
		String bank = request.getParameter("bank") == null ?"":request.getParameter("bank");
		String province = request.getParameter("province") == null ?"":request.getParameter("province");
		String city = request.getParameter("city") == null ?"":request.getParameter("city");
		String defaultBankNo = ServletRequestUtils.getStringParameter(request, "defaultBankNo", "");
		BankBrancheInfoDto bankBrancheInfoDto = new BankBrancheInfoDto() ;
		bankBrancheInfoDto.setBankName(bank);
		bankBrancheInfoDto.setProvince(province);
		bankBrancheInfoDto.setCity(city);
		if(!"".equals(bank) && !"".equals(province) && !"".equals(city)){
			Map map = new HashMap();
			//List<SearchResultInfoDTO> bankList = bankAcctServiceBussiness.getBankAcctFO(bank, province, city, "");
			List<BankBrancheInfoDto> bankList = this.bankBrancheInfoService.findByCondition(bankBrancheInfoDto) ;
			map.put("bankList", bankList);
			map.put("defaultBankNo", defaultBankNo);
			return new ModelAndView(bankSelect,map);
		}
		return null;
	}
	
	public ModelAndView removeBankCard(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String errorMsg = "";
		boolean resultBool = false;
		String memberCode = request.getSession().getAttribute("memberCode").toString();
		if(!bankAcctServiceBussiness.getMemberVerifyState(memberCode)){
			errorMsg = "未实名认证，请先实名";
		}else{
			String id = ServletRequestUtils.getStringParameter(request, "id", "");
			if(!"".equals(id)){
				try{
					resultBool = bankAcctServiceBussiness.removeBankAcctById(Long.parseLong(id)) == 1;
					if(! resultBool )
					{
						errorMsg = "删除银行卡不成功，该卡不存在或已被删除";
					}
				}catch(NumberFormatException e){
					errorMsg = "删除银行卡的id号不正确";
				}
				
			}
		}
		
		response.setContentType("text/plain;charset=UTF-8");
		String result = resultBool ? "S" : errorMsg;
		response.getWriter().write(result);
		return null;
	}
	

	public void checkBankNo(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String bankCode = request.getParameter("bankId") == null ? ""
				: request.getParameter("bankId");
		String bankId = request.getParameter("bankAcctId") == null ? "" : request
				.getParameter("bankAcctId");
		//boolean flag=false;
		int reuslt = 0;
        PrintWriter out = null;
        out = response.getWriter();
        if(!"".equals(bankCode) && !"".equals(bankId)){
        	reuslt = bankAcctService.validCardNo(bankCode, bankId);
        	//flag = bankAcctService.isValidCardBin(bankCode, bankId);
        }
		out.print(reuslt);
        out.flush();
        out.close();		
	}
	
//	public ModelAndView editBankCard(HttpServletRequest request,
//			HttpServletResponse response) {
////		String memberCode = request.getSession().getAttribute("memberCode").toString();
////		if(!bankAcctServiceBussiness.getMemberVerifyState(memberCode)){
////			return new ModelAndView(notCheckErrorPage);
////		}
//		String bankcardId=request.getParameter("bankcardId")== null ? "" : request.getParameter("bankcardId");
//		BankAcct bankAcct = null;
//		try{
//			bankAcct=bankAcctServiceBussiness.getBankAcctById(Long.parseLong(bankcardId));
//		}catch(NumberFormatException e){
//			log.error("BankAcctController editBankCard NumberFormatException!",e);
//			return new ModelAndView(editFail);
//		}
//		if(bankAcct != null)
//		{
//			String bankAcctId = "";
//			try{
//				 bankAcctId = DESUtil.decrypt(bankAcct.getBankAcctId());
//			}catch(Exception e){
//				log.error("BankAcctController editBankCard Exception!",e);
//				return new ModelAndView(editFail);
//			}
//			bankAcct.setBankAcctId(bankAcctId);
//			String memberName = "";// 会员姓名
//			memberName=request.getSession().getAttribute("verifyName").toString();
//			List<ProvinceDTO> provinceList=bankAcctService.getAllProvince();
//			List<Relation> relationList = new ArrayList<Relation>();
//			if(provinceList != null && !provinceList.isEmpty())
//			{
//			for (int i = 0; i < provinceList.size(); i++) {
//				ProvinceDTO province = (ProvinceDTO) provinceList.get(i);
//				List<CityDTO> cityList = bankAcctService.getAllCity(province.getProvincecode());
//				for (int j = 0; j < cityList.size(); j++) {
//					CityDTO city = (CityDTO) cityList.get(j);
//					Relation relation = new Relation();
//					relation.setFatherCode(province.getProvincecode().toString());
//					relation.setCode(city.getCitycode().toString());
//					relation.setName(city.getCityname());
//					relationList.add(relation);
//				}
//			}
//			}
//			String param="bankacct";
//			List<Bank> bankList = bankAcctService.getBindAllBankList(param);
//			return new ModelAndView(editBankAcctPage).addObject("provinceList",provinceList).addObject("relationList",relationList).
//			addObject("bankList",bankList).addObject("memberName",memberName).addObject("bankNum",bankList.size()).addObject("bankAcct",bankAcct);
//		}
//		else
//		{
//			return new ModelAndView();//页面不存在
//		}
//	}
	/**
	 * 验证会员的身份信息,账户信息,银行卡信息
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView checkBankCard(HttpServletRequest request,
			HttpServletResponse response) {

		String memberCode = "";
		int bankNum = 0;// 银行卡绑定张数s
		int maxBankNum = 10;// 银行卡最多绑定10张
		String memberName = "";// 会员姓名
		memberCode = request.getSession().getAttribute("memberCode").toString();

		memberName = request.getSession().getAttribute("verifyName").toString();
		if(!bankAcctServiceBussiness.getMemberVerifyState(memberCode)){
			return new ModelAndView(notCheckErrorPage);
		}
		Long id = ServletRequestUtils.getLongParameter(request, "id",-1);
		BankAcct  defaultBankAcctInfo = null;
		if(id != -1){
			defaultBankAcctInfo = bankAcctServiceBussiness.getBankAcctById(id);
		}
		//查询是否有默认的卡号
		bankNum =bankAcctServiceBussiness.getUserBankAcctNum(memberCode);
		if (bankNum >= maxBankNum && defaultBankAcctInfo==null) {//新增加人时候判断是否大于10张
			return new ModelAndView(bankAcctNumErrorPage).addObject("bankNum",bankNum);
		}
		
		List<ProvinceDTO> provinceList=bankAcctService.getAllProvince();
		List<Relation> relationList = new ArrayList<Relation>();
		if(provinceList != null && !provinceList.isEmpty())
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
		
		return new ModelAndView(addBankAcctPage).addObject("provinceList",provinceList).addObject("relationList",relationList).
		addObject("bankList",bankList).addObject("memberName",memberName).addObject("bankNum",bankList.size())
		.addObject("defaultInfo", defaultBankAcctInfo);
	}

	/**
	 * 添加会员绑定银行卡信息，并且更新会员银行卡绑定状态属性
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView addBankCard(HttpServletRequest request,
			HttpServletResponse response,BankAcct bankAcct) {
		String member = request.getSession().getAttribute("memberCode").toString();
		if(!bankAcctServiceBussiness.getMemberVerifyState(member)){
			return new ModelAndView(notCheckErrorPage);
		}
//		BankAcct bankAcct = new BankAcct();
		
//		String bankName = request.getParameter("bankName") == null ? ""
//				: request.getParameter("bankName");
//		String bankCode = request.getParameter("bankCode") == null ? ""
//				: request.getParameter("bankCode");
//		String bankId = request.getParameter("bankId") == null ? "" : request
//				.getParameter("bankId");
//		String bankNo =bankId; //未加密的银行卡号
		
		String memberName = ServletRequestUtils.getStringParameter(request, "memberName","");
//		String province = request.getParameter("province") == null ? ""
//				: request.getParameter("province");
//		String city = request.getParameter("city") == null ? "" : request
//				.getParameter("city");
		String bankAcctId = bankAcct.getBankAcctId();
		if(StringUtils.isBlank(bankAcctId) || bankAcctId.trim().length() < 8 || bankAcctId.trim().length()> 32){
			
			return new ModelAndView(bankAcctNumErrorPage).addObject("errorMsg",ErrorCodeEnum.BANK_CARD_LENGTH_ERROR.getMessage());
		}
		int reuslt = bankAcctService.validCardNo(String.valueOf(bankAcct.getBankId()), bankAcct.getBankAcctId());
		
		if(reuslt>0){
		    String errorMsg="";
		    if(reuslt==1){
		        errorMsg= ErrorCodeEnum.BANK_CARD_NO.getMessage();
		    }else{
		        errorMsg= ErrorCodeEnum.BANK_CARD_ERROR.getMessage();
		    }
		    return new ModelAndView(bankAcctNumErrorPage).addObject("errorMsg",errorMsg);
		}
		try{
			Long memberCode = Long.parseLong(request.getSession().getAttribute(
					"memberCode").toString());
			
			
			bankAcct.setCreationDate(new Date());
			bankAcct.setStatus(default_status);
			bankAcct.setName(memberName);
//			bankAcct.setCity(Long.parseLong(city));
//			bankAcct.setProvince(Long.parseLong(province));
//			bankAcct.setName(memberName);
//			bankAcct.setBranchBankName(bankName);
//	
//			bankAcct.setBankId(Long.parseLong(bankCode));// 通过Card Bin拿到 银行 Code
			bankAcct.setMemberCode(memberCode);
			bankAcct.setIsPrimaryBankacct(default_Account);
//			bankAcct.setBankAcctId(DESUtil.encrypt(bankId));//对银行卡号进行加密
			
		}catch(Exception e){
			log.error("BankAcctController addBankCard Exception!",e);
			return new ModelAndView(fail);
		}
		boolean isUpdate = false;
		if(bankAcct.getId()!=null && bankAcct.getId()>0){
			isUpdate = true;
		}
		Integer addResult = bankAcctServiceBussiness.addOrUpdateBankAcct(bankAcct,request.getSession().getAttribute("memberCode").toString());//bankAcctSerbce.addBankAcct(bankAcct);
	
		if (addResult == CREATE_FAIL) {
			return new ModelAndView(fail);
		}
		if (addResult == CREATE_FAIL_BAKD_ACCT_IS_EXIST) {
			return new ModelAndView(bankCardExitsFail);
		}
		if (addResult == CREATE_FAIL_MEMBERCODE_CARD_NUMBER_COUNT_ERROR) {
			return new ModelAndView(bankAcctNumErrorPage);
		}
		String view = isUpdate ? editSuccess : success;
		return new ModelAndView(view);
	}
	
	
	
//	public ModelAndView editSubmitBankCard(HttpServletRequest request,
//			HttpServletResponse response) {
//		
//		String id = request.getParameter("id") == null ? ""
//				: request.getParameter("id");
//		BankAcct bankAcct = new BankAcct();
//		String bankName = request.getParameter("bankName") == null ? ""
//				: request.getParameter("bankName");
//		String bankCode = request.getParameter("bankCode") == null ? ""
//				: request.getParameter("bankCode");
//		String bankId = request.getParameter("bankId") == null ? "" : request
//				.getParameter("bankId");
//		String memberName = request.getParameter("memberName") == null ? ""
//				: request.getParameter("memberName");
//		String province = request.getParameter("province") == null ? ""
//				: request.getParameter("province");
//		String city = request.getParameter("city") == null ? "" : request
//				.getParameter("city");
//		try{
//			Long memberCode = Long.parseLong(request.getSession().getAttribute(
//					"memberCode").toString());
//			bankAcct.setId(Long.parseLong(id));//可能需要解密
//			bankAcct.setCreationDate(new Date());
//			bankAcct.setStatus(default_status);
//			bankAcct.setCity(Long.parseLong(city));
//			bankAcct.setProvince(Long.parseLong(province));
//			bankAcct.setName(memberName);
//			bankAcct.setBranchBankName(bankName);
//	
//			bankAcct.setBankId(Long.parseLong(bankCode));// 通过Card Bin拿到 银行 Code
//			bankAcct.setMemberCode(memberCode);
//			bankAcct.setIsPrimaryBankacct(default_Account);
//			bankAcct.setBankAcctId(DESUtil.encrypt(bankId));
//		}catch(Exception e){
//			log.error("BankAcctController editSubmitBankCard Exception!",e);
//			return new ModelAndView(editFail);
//		}
//		int editResult=bankAcctServiceBussiness.editBankAcct(bankAcct,request.getSession().getAttribute("memberCode").toString());
//		if(editResult == edit_success)
//		{
//			return new ModelAndView(editSuccess);//修改成功页面
//		}
//		if (editResult == CREATE_FAIL_BAKD_ACCT_IS_EXIST) {
//			return new ModelAndView(editCardExits);
//		}
//		return new ModelAndView(editFail);//修改失败页面
//	}
//	
	/**
	 * 设置默认卡片(AJAX) 成功打印S,失败打印F
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView setDefaultBankCard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = request.getSession().getAttribute("memberCode").toString();
		Long id  = ServletRequestUtils.getLongParameter(request, "id");
		boolean resultBool =  bankAcctServiceBussiness.setDefaultAcctRnTx(memberCode, id);
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
	public ModelAndView setNotDefaultBankCard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = request.getSession().getAttribute("memberCode").toString();
		Long id  = ServletRequestUtils.getLongParameter(request, "id");
		boolean resultBool =  bankAcctServiceBussiness.setNotDefaultAcctRnTx(memberCode,id);
		String result = resultBool ? "S" : "F";
		response.getWriter().write(result);
		return null;
		//return new ModelAndView(removeBantCardSuccessView);
	}
	public void setSuccess(String success) {
		this.success = success;
	}

	public void setBankCardExitsFail(String bankCardExitsFail) {
		this.bankCardExitsFail = bankCardExitsFail;
	}

	public void setAddBankAcctPage(String addBankAcctPage) {
		this.addBankAcctPage = addBankAcctPage;
	}

	public void setBankAcctNumErrorPage(String bankAcctNumErrorPage) {
		this.bankAcctNumErrorPage = bankAcctNumErrorPage;
	}

	public void setNotCheckErrorPage(String notCheckErrorPage) {
		this.notCheckErrorPage = notCheckErrorPage;
	}

	public void setBankAcctService(BankAcctServiceFacade bankAcctService) {
		this.bankAcctService = bankAcctService;
	}

	public void setBankCodeFail(String bankCodeFail) {
		this.bankCodeFail = bankCodeFail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public void setBankAcctServiceBussiness(BankAcctService bankAcctServiceBussiness) {
		this.bankAcctServiceBussiness = bankAcctServiceBussiness;
	}

	public void setCidVerify2GovService(
			CidVerify2GovServiceFacade cidVerify2GovService) {
		this.cidVerify2GovService = cidVerify2GovService;
	}
	public void setEditBankAcctPage(String editBankAcctPage) {
		this.editBankAcctPage = editBankAcctPage;
	}
	public void setEditSuccess(String editSuccess) {
		this.editSuccess = editSuccess;
	}
	public void setEditFail(String editFail) {
		this.editFail = editFail;
	}


	public void setRemoveFail(String removeFail) {
		this.removeFail = removeFail;
	}


	public void setSearchBanksFO(String searchBanksFO) {
		this.searchBanksFO = searchBanksFO;
	}
	public void setBankListFO(String bankListFO) {
		this.bankListFO = bankListFO;
	}

	public void setRemoveForward(String removeForward) {
		this.removeForward = removeForward;
	}


	public void setBankSelect(String bankSelect) {
		this.bankSelect = bankSelect;
	}

	public void setEditCardExits(String editCardExits) {
		this.editCardExits = editCardExits;
	}

	public void setBankBrancheInfoService(
			BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}
	
}
