package com.pay.app.controller.base.accountactive;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.app.model.BankAcct;
import com.pay.app.service.bankacct.BankAcctService;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.model.Acct;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.model.EnterpriseContract;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.enterprise.EnterpriseContactService;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.base.service.enterprise.LiquidateInfoService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.FormatNumber;


/**
 * 
 */
//@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
public class AccountInfoController extends MultiActionController {

	/**
	 * 修改账户信息的页面
	 */
	protected String updateAccountInfo;
	
	protected String corpUpdateAccountInfo;
	/**
	 * 登录
	 */
	protected String login;
	
	
	
	protected MemberService baseMemberService;
	
	/** 操作员服务 */
    protected OperatorServiceFacade operatorServiceFacade;
	
    /** 企业会员基本信息服务 */
    protected EnterpriseBaseService  enterpriseBaseService;
    /** 企业会员联系信息服务 */
    protected EnterpriseContactService enterpriseContactService;
    
    protected BankAcctService          bankAcctService;
    /** 企业会员结算信息服务 */
    protected LiquidateInfoService     liquidateInfoService;
    
    /**
     * 实名认证服务
     */
    protected CidVerify2GovServiceFacade verify2GovService;
    
    protected BankAcctServiceFacade provinceServiceFacade;
    
    //
    protected AcctService acctService;
    
    protected EnterpriseRegisterService enterpriseRegisterService;

	/**
	 * 到我的账户信息修改页面
	 */
    @OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_QUERY")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//登录的身份验证
		LoginSession loginSession = SessionHelper.getLoginSession();
		Long requestMemberCode = ServletRequestUtils.getLongParameter(request, "mbCode", -1L);
		Long memberCode = Long.valueOf((loginSession.getMemberCode()));
		MemberCriteria memberCriteria = baseMemberService.queryMemberCriteriaByMemberCodeNsTx(Long.valueOf(memberCode));
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("memberCriteria", memberCriteria);
		 // 会员是否通过实名认证
        boolean boolvfy = verify2GovService.checkQueryCidVerify(memberCode+"");
        if(boolvfy){//已实名认证
            paraMap.put("qdbool","true");
        }else{
            paraMap.put("qdbool","false");
        }
		paraMap.put("type",loginSession.getScaleType());
		Acct acct = acctService.getByMemberCode(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
        paraMap.put("acct", acct);
        
        /* balance  frozenAmount 显示为实现值 */
//        double balance = FormatNumber.round(acct == null ? 0L : acct.getBalance() / 1000.00);
//		double frozenAmount = FormatNumber.round(acct == null ? 0L : acct.getFrozenAmount() /1000.00);
       
		BigDecimal balance=(acct == null ? new BigDecimal(0)  :acct.getBalance());
		BigDecimal frozenAmount=(acct == null ? new BigDecimal(0) : acct.getFrozenAmount());

		
		
		paraMap.put("acct_balance", FormatNumber.decimalsRound(balance.divide(new BigDecimal(1000)), 2));
		paraMap.put("acct_frozenAmount", FormatNumber.decimalsRound(frozenAmount.divide(new BigDecimal(1000)), 2));
		
        AcctAttribDto attribDto =  verify2GovService.getAccountAcctAttrib(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
        paraMap.put("attribDto", attribDto);
		if(SessionHelper.isCorpLogin()){
		    long mCode = Long.valueOf(memberCode);
            // 获取操作员总数
            int operatorNum = operatorServiceFacade.queryCountByMemberCode(mCode);
            paraMap.put("operatorNum", operatorNum-1);
            String operatorIdentity = loginSession.getOperatorIdentity();
            paraMap.put("operatorIdentity", operatorIdentity);
            String loginName = loginSession.getLoginName();
            paraMap.put("loginName", loginName);
            // 获取企业会员基本信息
            EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(mCode);
            if(enterpriseBase != null){
                paraMap.put("enterpriseBase", enterpriseBase);
            }
            // 获取企业会员联系信息
            EnterpriseContact enterpriseContact = enterpriseContactService.findByMemberCode(mCode);
            if(enterpriseContact != null){
                paraMap.put("enterpriseContact", enterpriseContact);
            }
            StringBuffer address = new StringBuffer();
            String region = enterpriseBase.getRegion();
            String city = enterpriseBase.getCity();
            if(StringUtils.isNotBlank(region)){
                region = provinceServiceFacade.getProvinceById(Integer.valueOf(region));
                address.append(region).append("&nbsp;&nbsp;");
            }
            if(StringUtils.isNotBlank(city)){
                city = provinceServiceFacade.getCityById(Integer.valueOf(city));
                address.append(city).append("&nbsp;&nbsp;");
            }
            if(StringUtils.isNotEmpty(enterpriseContact.getAddress())){
                address.append(enterpriseContact.getAddress());
            }
            if(StringUtils.isNotEmpty(enterpriseContact.getZip())){
            	address.append("&nbsp;("+enterpriseContact.getZip()+")");
            }
            paraMap.put("address", address.toString());
            // 获取企业会员结算银行信息
            List<LiquidateInfo> liquidateInfoList = liquidateInfoService.getByMemberCode(mCode);
            paraMap.put("liquidateList", liquidateInfoList);

          //获取账户信息
            
            
			EnterpriseContract econtract = enterpriseRegisterService
					.getContractByMemberCode(mCode);
            paraMap.put("econtract", econtract);
            
            
            //查询当前绑定的手机 
           String bindMobile =  operatorServiceFacade.getBindMobileByMeberCodeOperatorId(mCode,loginSession.getOperatorId());
           paraMap.put("bindMobile", bindMobile!=null? bindMobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"):"");
           paraMap.put("isCertUser", SessionHelper.isCertUser()?"1":"0");
          
            return new ModelAndView(corpUpdateAccountInfo, paraMap);
        } else {
    		if(StringUtils.isNotBlank(memberCriteria.getCardNo())){
    		    String cardNoSuf =  memberCriteria.getCardNo();
    		    cardNoSuf = StringUtils.repeat("*", cardNoSuf.length()-4)+StringUtils.right(cardNoSuf, 4);
    		    paraMap.put("cardNoSuf",cardNoSuf);
    		}
    		StringBuffer address = new StringBuffer();
    		String province = memberCriteria.getProvince();
            String city = memberCriteria.getCity();
            String postCode = memberCriteria.getPostCode();
            if(StringUtils.isNotBlank(province)){
                province = provinceServiceFacade.getProvinceById(Integer.valueOf(province));
                address.append(province).append("&nbsp;&nbsp;");
            }
            if(StringUtils.isNotBlank(city)){
                city = provinceServiceFacade.getCityById(Integer.valueOf(city));
                address.append(city).append("&nbsp;&nbsp;");
            }
            if(StringUtils.isNotEmpty(memberCriteria.getAddress())){
                address.append(memberCriteria.getAddress());
            }
            if(StringUtils.isNotBlank(postCode)){
            	address.append("&nbsp;("+postCode+")");
            }
            paraMap.put("address", address.toString());
            
        }
		// 获取个人会员银行卡信息
        List<BankAcct> bankList = bankAcctService.getBankAcctByCode(memberCode+"");
        int bankNum=bankAcctService.getUserBankAcctNum(memberCode+"");
        paraMap.put("bankList", bankList);
        paraMap.put("bankNum", bankNum);
		return new ModelAndView(updateAccountInfo,paraMap);
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setUpdateAccountInfo(String updateAccountInfo) {
		this.updateAccountInfo = updateAccountInfo;
	}

    public void setCorpUpdateAccountInfo(String corpUpdateAccountInfo) {
        this.corpUpdateAccountInfo = corpUpdateAccountInfo;
    }

    public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
        this.operatorServiceFacade = operatorServiceFacade;
    }

    public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
        this.enterpriseBaseService = enterpriseBaseService;
    }

    public void setEnterpriseContactService(EnterpriseContactService enterpriseContactService) {
        this.enterpriseContactService = enterpriseContactService;
    }

    public void setBaseMemberService(MemberService baseMemberService) {
        this.baseMemberService = baseMemberService;
    }

	public void setBankAcctService(BankAcctService bankAcctService) {
		this.bankAcctService = bankAcctService;
	}

    public void setLiquidateInfoService(LiquidateInfoService liquidateInfoService) {
        this.liquidateInfoService = liquidateInfoService;
    }

    public void setVerify2GovService(CidVerify2GovServiceFacade verify2GovService) {
        this.verify2GovService = verify2GovService;
    }

    public void setProvinceServiceFacade(BankAcctServiceFacade provinceServiceFacade) {
        this.provinceServiceFacade = provinceServiceFacade;
    }

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setEnterpriseRegisterService(
			EnterpriseRegisterService enterpriseRegisterService) {
		this.enterpriseRegisterService = enterpriseRegisterService;
	}

	
}

