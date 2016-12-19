package com.pay.app.controller.base.accountactive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.app.facade.dto.Relation;
import com.pay.app.service.bankacct.BankAcctService;
import com.pay.base.dto.EnterpriseRegisterInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.util.CheckUtil;
import com.pay.util.DESUtil;

/**
 * 企业激活－银行信息填写
 * @author jerry_jin
 *
 */
public class LiquidateInfoController extends MultiActionController {

	private EnterpriseRegisterService enterpriseRegisterService;
	
	private BankAcctService bankAcctServiceBussiness;
	
	private BankService bankService;
	
	private BankAcctServiceFacade bankAcctService;
	
	private String toInputLiquidateInfo = "";
	
	private String saveLiquidateInfoSuccess = "";
	
	private String invalidRequest = "";
	
	private String toRegister = "";
	
	private String bankListFO;
	
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView inputLiquidateInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{

		
		Object e = request.getSession().getAttribute("enterpriseRegisterInfo");
		
		EnterpriseRegisterInfo eri = null;
		
		if(e!=null && e instanceof EnterpriseRegisterInfo){
			eri = (EnterpriseRegisterInfo)e;
		}else{
			return new ModelAndView(invalidRequest);
		}
		//公司名称
		String companyName = replaceStr(eri.getZhName());
		List<Bank> bankList = bankService.getWithdrawBanks();
		List<ProvinceDTO> provinceList=bankAcctService.getAllProvince();
		List<Relation> relationList = new ArrayList<Relation>();
		if(provinceList != null && !provinceList.isEmpty()){
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
		return new ModelAndView(toInputLiquidateInfo).addObject("companyName", companyName).addObject("provinceList",provinceList).addObject("bankList", bankList).addObject("relationList",relationList);
	}
	 private String replaceStr(String str){
    	 String welcomeMsg = str;
    	 if(null != welcomeMsg && welcomeMsg.contains("&quot;")){
    		 welcomeMsg=welcomeMsg.replace("&quot;","\"");
    	 }
    	 if(null != welcomeMsg && welcomeMsg.contains("&#39;")){
    		 welcomeMsg=welcomeMsg.replace("&#39;","\'");
    	 }
    	 return welcomeMsg;
    }
	/**
	 * 保存提交
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveLiquidateInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Object e = request.getSession().getAttribute("enterpriseRegisterInfo");
		
		EnterpriseRegisterInfo eri = null;
		
		if(e!=null && e instanceof EnterpriseRegisterInfo){
			eri = (EnterpriseRegisterInfo)e;
		}else{
			return new ModelAndView(invalidRequest);
		}
		
		String companyName = eri.getZhName();
		
		String bankAcct1 = request.getParameter("bankAcct1");
		
		String bankAcct2 = request.getParameter("bankAcct2");
		
		String bankName = request.getParameter("bankName");
		
		String bankId = request.getParameter("bankId");
		
		String province = request.getParameter("province");
		
		String city = request.getParameter("city");
		
		boolean flag = true;
		/*********** 验证参数 开始 **********/
		
		//开户行为空
		if(StringUtils.isBlank(bankId)){
			flag = false;
//			result.put("errorTip","bankIdTip");
//			result.put("errMsg", ErrorCodeEnum.MEMBER_ENTERPRISE_LIQUIDATE_BANKID.getMessage());
		}
		
		//TODO 验证开户行格式

		if(!CheckUtil.isNumber(province) || !CheckUtil.isNumber(city) && flag==true){
			flag = false;
			//			result.put("errorTip","provinceTip");
//			result.put("errMsg", ErrorCodeEnum.MEMBER_ENTERPRISE_LIQUIDATE_PROVINCE.getMessage());
		}
		
		//开户行支行名称为空
		if(StringUtils.isBlank(bankName) && flag==true){
			flag = false;
//			result.put("errorTip","bankNameTip");
//			result.put("errMsg", ErrorCodeEnum.MEMBER_ENTERPRISE_LIQUIDATE_BANKNAME.getMessage());
		}
		
		//TODO 验证开户支行名称格式
		
		//公司银行账户为空
		if(StringUtils.isBlank(bankAcct1)|| StringUtils.isBlank(bankAcct2) && flag ==true){
			flag = false;
//			result.put("errorTip","bankAcct1Tip");
//			result.put("errMsg", ErrorCodeEnum.MEMBER_ENTERPRISE_LIQUIDATE_BANKACCT.getMessage());
		}
		
		//如果两次输入得银行账户不一致
		if(!bankAcct1.equals(bankAcct2) && flag ==true){
			flag = false;
//			result.put("errorTip","bankAcct1Tip");
//			result.put("errMsg", ErrorCodeEnum.MEMBER_ENTERPRISE_LIQUIDATE_BANKACCT_DIFFERENCE.getMessage());
		}
		
		//TODO 验证银行账户格式 

		
		if(flag==true){
			LiquidateInfo li = new LiquidateInfo();
			li.setBankAcct(DESUtil.encrypt(bankAcct1));
			li.setBankName(bankName);
			li.setCity(Integer.parseInt(city));
			li.setAcctName(companyName);
			li.setAccountMode(2);
			li.setProvince(Integer.parseInt(province));
			li.setBankId(bankId);
			li.setBankAddress(bankName);//默认银行卡信息为空
			/*********** 验证参数 结束 **********/
			
			//保存注册信息
			ResultDto rd = enterpriseRegisterService.doRegisterRdTx(eri,li);
			
			if(rd.isResultStatus()){
				return new ModelAndView(saveLiquidateInfoSuccess);
			}
			if(!rd.isResultStatus() && rd.getErrorCode()!=null){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("info", eri);
				m.put("msgStr", rd.getErrorMsg());
				return new ModelAndView(toRegister,m);
			}
		}
		
		
		return new ModelAndView(toInputLiquidateInfo).addObject("bankId",bankId).addObject("bankName",bankName).addObject("bankAcct1",bankAcct1)
		.addObject("bankAcct2", bankAcct2).addObject("province",province).addObject("city", city);
		
	}
	
	public ModelAndView queryBanksFO(HttpServletRequest request,HttpServletResponse response){
		String keyWord = request.getParameter("strs") == null ?"":request.getParameter("strs");
		String bankName = request.getParameter("bank") == null ?"":request.getParameter("bank");
		String provinceName = request.getParameter("province") == null ?"":request.getParameter("province");
		String cityName = request.getParameter("city") == null ?"":request.getParameter("city");
		if(!"".equals(keyWord)){
			List<SearchResultInfoDTO> bankList = bankAcctServiceBussiness.getBankAcctFO(bankName, provinceName, cityName, keyWord);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bankList", bankList);
			return new ModelAndView(bankListFO,map);
		}
		return null;
	}


	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setBankAcctService(BankAcctServiceFacade bankAcctService) {
		this.bankAcctService = bankAcctService;
	}

	public void setToInputLiquidateInfo(String toInputLiquidateInfo) {
		this.toInputLiquidateInfo = toInputLiquidateInfo;
	}

	public void setSaveLiquidateInfoSuccess(String saveLiquidateInfoSuccess) {
		this.saveLiquidateInfoSuccess = saveLiquidateInfoSuccess;
	}

	public void setInvalidRequest(String invalidRequest) {
		this.invalidRequest = invalidRequest;
	}

	public void setEnterpriseRegisterService(
			EnterpriseRegisterService enterpriseRegisterService) {
		this.enterpriseRegisterService = enterpriseRegisterService;
	}
	public void setToRegister(String toRegister) {
		this.toRegister = toRegister;
	}
	public void setBankAcctServiceBussiness(BankAcctService bankAcctServiceBussiness) {
		this.bankAcctServiceBussiness = bankAcctServiceBussiness;
	}
	public void setBankListFO(String bankListFO) {
		this.bankListFO = bankListFO;
	}
	
	
}
