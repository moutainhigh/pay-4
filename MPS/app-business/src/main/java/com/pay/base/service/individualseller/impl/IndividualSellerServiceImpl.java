/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 上午11:42:03 2010-11-10
 */
package com.pay.base.service.individualseller.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.dao.acct.AcctDAO;
import com.pay.base.dao.acctattrib.AcctAttribDAO;
import com.pay.base.dto.MemberDto;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctAttrib;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.base.service.individualseller.IndividualSellerService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.memberaccttemplate.MemberAcctTemplateService;


/**
 * @author Sunny Ying
 * @description TODO
 * @version 上午11:42:03 & 2010-11-10
 */

public class IndividualSellerServiceImpl implements IndividualSellerService{

	private static final Log log = LogFactory.getLog(IndividualSellerServiceImpl.class);
	private MemberVerifyService memberVerifyService;
	private MemberAcctTemplateService memberAcctTemplateService;
	private MemberService memberService;
	private AcctService acctService;
	private AcctAttribService acctAttribService;
	private AcctAttribDAO acctAttribDAO;
	private AcctDAO acctDAO;
	
	/** 更新失败 **/
	private static final int updateFail = 0;
		
	/** 移动点账户代码 **/
	private static final Long acctTypeId = 24L;
	/** 移动点内部影子账户代码 **/
	private static final Long internal_acctTypeId = 20L;	
	/** 电信点账户代码 **/
	private static final Long dx_acctTypeId = 25L;
	/** 电信点内部影子账户代码 **/
	private static final Long internal_dx_acctTypeId = 21L;
	/** 联通点账户代码 **/
	private static final Long lt_acctTypeId = 26L;
	/** 联通点内部影子账户代码 **/
	private static final Long internal_lt_acctTypeId = 22L;	
	/**骏网点账户代码 **/
	private static final Long jw_acctTypeId = 27L;
	/** 骏网点内部影子账户代码 **/
	private static final Long internal_jw_acctTypeId = 23L;
			
	/** 默认账户余额 **/
	private static final Double balance = 0d;
	/** 默认账户状态  1 有效 **/
	private static final int status = 1;
	/** 冻结余额 **/
	private static final Double frozenAmount = 0d;
	/** 借方交易额 **/
	private static final Double creditBalance = 0d;
	/** 贷方交易额 **/
	private static final Double debitBalance = 0d;
	/** 账户类型**/
	private static final String memberType = "3";

	
	/** 只开通了个人卖家身份 **/
	private static final int justSellerPurview = 1;

	/** 开通了个人卖家身份并且开通了 点账户 **/
	private static final int sellerAccountPurview = 2;
	
	/** 1为个人账户 **/
	private static final Integer personalAccount = 1;
	/** 10为人民币账户**/
	private static final Integer cnyAccount = 10;
	/** 3为个人卖家身份**/
	private static final int personalSeller = 3;
	
	//acctAttribute
	
	/** 1允许充值**/
	private static final Integer allowDeposit = 1;
	/** 1允许提现 **/
	private static final Integer allowWithdrawal = 1;
	/** 1允许转账入 **/
	private static final Integer allowTransferIn = 1;
	/** 1允许转账出 **/
	private static final Integer allowTransferOut = 1;
	/** 是否止入  1正常 **/
	private static final Integer allowIn = 1;
	/** 是否止出  1正常 **/
	private static final Integer allowOut = 1;
	
	/** 描述 **/
	private static final String szx_description = "个人移动点账户";
	private static final String internal_szx_description = "移动影子账户";
	private static final String dx_description = "个人电信点账户";
	private static final String internal_dx_description = "电信影子账户";	
	private static final String lt_description = "个人联通点账户";
	private static final String internal_lt_description = "联通影子账户";
	private static final String jw_description = "个人骏网点账户";
	private static final String internal_jw_description = "骏网影子账户";
	
	/** 是否冻结  1正常 **/
	private static final Integer frozen =1 ;
	/** 是否默认收账  1默认**/
	private static final Integer defRecAcct = 1;
	/** 货币类型 默认是 CNY**/
	private static final String curCode = "CNY";
	/** 科目级别  4默认**/
	private static final Integer acctLevel = 4;
	/** 余额方向  1是借**/
	private static final Integer balanceBy = 1;
	/** 是否允许支付  1是允许**/
	private static final Integer payAble = 1;
	/** 是否允许透支  0是不允许**/
	private static final Integer allowOverdraft = 0;
	/** 密码保护  1是**/
	private static final Integer needProtect = 1;
	/** 会员管理  1是允许**/
	private static final Integer managerable = 1;
	/**  3是个人卖家**/
	private static final Integer type = 3;
	/** 是否计息 0为不计息**/
	private static final Integer bearInterest = 0;

	public AcctAttribDto getAcctAttribDtoByAcctCode(String acctCode){
		if(acctCode != null && !"".equals(acctCode)){
			AcctAttribDto  acctAttribDto = acctAttribService.queryAcctAttribByAcctCode(acctCode);
			return acctAttribDto;
		}
		return null;
	}
	
	public Acct getAcct(String memberCode,Integer acctTypeId){
		if(!"".equals(memberCode) && acctTypeId != null){
			Acct acct = acctDAO.getByMemberCode(Long.parseLong(memberCode), acctTypeId);
			return acct;
		}
		return null;
	}
	
	public Map isExitsCardAccount(Map<String,String> map){
		if(map != null){
			Map resultMap = new HashMap();
			String szxAccount = map.get("szxAccount");
			String dxAccount = map.get("dxAccount");
			String ltAccount = map.get("ltAccount");
			String jwAccount = map.get("jwAccount");
			String memberCode = map.get("memberCode");
			
			String subjectCode = "";
			String internal_subjectCode = "";
			String acctCode = "";
			String internal_acctCode = "";
			if(!"".equals(szxAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_acctTypeId.toString();
				
				Acct acct =this.getAcct(memberCode, acctTypeId.intValue());
				Acct internal_acct =this.getAcct(memberCode, internal_acctTypeId.intValue());
				AcctAttribDto  acctAttribDto = this.getAcctAttribDtoByAcctCode(acctCode);
				AcctAttribDto  internal_acctAttribDto = this.getAcctAttribDtoByAcctCode(internal_acctCode);
				if(acct != null && internal_acct != null && acctAttribDto != null && internal_acctAttribDto !=null){
					resultMap.put("szx_acct", acct);
				}
			}
			if(!"".equals(dxAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", dx_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_dx_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + dx_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_dx_acctTypeId.toString();
				
				Acct acct =this.getAcct(memberCode, dx_acctTypeId.intValue());
				Acct internal_acct =this.getAcct(memberCode, internal_dx_acctTypeId.intValue());
				AcctAttribDto  acctAttribDto = this.getAcctAttribDtoByAcctCode(acctCode);
				AcctAttribDto  internal_acctAttribDto = this.getAcctAttribDtoByAcctCode(internal_acctCode);
				if(acct != null && internal_acct != null && acctAttribDto != null && internal_acctAttribDto !=null){
					resultMap.put("dx_acct", acct);
				}
			}
			if(!"".equals(ltAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", lt_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_lt_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + lt_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_lt_acctTypeId.toString();
				
				Acct acct =this.getAcct(memberCode, lt_acctTypeId.intValue());
				Acct internal_acct =this.getAcct(memberCode, internal_lt_acctTypeId.intValue());
				AcctAttribDto  acctAttribDto = this.getAcctAttribDtoByAcctCode(acctCode);
				AcctAttribDto  internal_acctAttribDto = this.getAcctAttribDtoByAcctCode(internal_acctCode);
				if(acct != null && internal_acct != null && acctAttribDto != null && internal_acctAttribDto !=null){
					resultMap.put("lt_acct", acct);
				}
			}
			if(!"".equals(jwAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", jw_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_jw_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + jw_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_jw_acctTypeId.toString();
				
				Acct acct =this.getAcct(memberCode, jw_acctTypeId.intValue());
				Acct internal_acct =this.getAcct(memberCode, internal_jw_acctTypeId.intValue());
				AcctAttribDto  acctAttribDto = this.getAcctAttribDtoByAcctCode(acctCode);
				AcctAttribDto  internal_acctAttribDto = this.getAcctAttribDtoByAcctCode(internal_acctCode);
				if(acct != null && internal_acct != null && acctAttribDto != null && internal_acctAttribDto !=null){
					resultMap.put("jw_acct", acct);
				}
			}
			return resultMap;
		}	
		return null;
	}
	
	public String getSubjectCode(Map map) {
		if(map != null){
			return memberAcctTemplateService.getSubjectCode(map);
		}
		return "";
	}
	
	public AcctAttrib setAcctAttrib(String acctCode,String description,String memberCode,String payPwd,Long memberAcctCode, String subjectCode){
		AcctAttrib acctAttrib = new AcctAttrib();
		
		acctAttrib.setAcctCode(acctCode);
		acctAttrib.setAllowDeposit(allowDeposit);
		acctAttrib.setAllowWithdrawal(allowWithdrawal);
		acctAttrib.setAllowTransferIn(allowTransferIn);
		acctAttrib.setAllowTransferOut(allowTransferOut);
		acctAttrib.setAllowIn(allowIn);
		acctAttrib.setAllowOut(allowOut);
		acctAttrib.setDescription(description);
		acctAttrib.setFrozen(frozen);
		acctAttrib.setMemberCode(Long.parseLong(memberCode));
		acctAttrib.setDefRecAcct(defRecAcct);
		acctAttrib.setCurCode(curCode);
		acctAttrib.setPayPwd(payPwd);
		acctAttrib.setCreateDate(new Date());
		acctAttrib.setUpdateDate(new Date());
		acctAttrib.setAcctLevel(acctLevel);
		acctAttrib.setBalanceBy(balanceBy);
		acctAttrib.setPayAble(payAble);
		acctAttrib.setAllowOverdraft(allowOverdraft);
		acctAttrib.setNeedProtect(needProtect);
		acctAttrib.setManagerable(managerable);
		//acctAttrib.setAcctType();
		acctAttrib.setMemberAcctCode(memberAcctCode);
		acctAttrib.setSubjectCode(subjectCode);
		acctAttrib.setBearInterest(bearInterest);
		return acctAttrib;
	}
	
	public List<AcctAttrib> setAcctAttribList(Map<String,String> map){
		List<AcctAttrib> acctAttribList = new ArrayList<AcctAttrib>();
		if(map != null){
			String szxAccount = map.get("szxAccount");
			String dxAccount = map.get("dxAccount");
			String ltAccount = map.get("ltAccount");
			String jwAccount = map.get("jwAccount");
			String memberCode = map.get("memberCode");
			Map cny_model = new HashMap();				
			cny_model.put("type",personalAccount);
			cny_model.put("acctTypeId", cnyAccount);
			String payPwd= "";
			String payPwd_subject = memberAcctTemplateService.getSubjectCode(cny_model);
			String payPwd_acctCode = payPwd_subject + memberCode + cnyAccount.toString();
			AcctAttribDto acctAttribDto =acctAttribService.queryAcctAttribByAcctCode(payPwd_acctCode);
			if(acctAttribDto != null){
				payPwd = acctAttribDto.getPayPwd();
			}
			Long memberAcctCode = 0L; 
			Long internal_memberAcctCode = 0L;
			String subjectCode = "";
			String internal_subjectCode = "";
			String acctCode = "";
			String internal_acctCode = "";		
			if(!"".equals(szxAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_acctTypeId);
				
				memberAcctCode = Long.parseLong(memberCode + acctTypeId.toString());
				internal_memberAcctCode = Long.parseLong(memberCode + internal_acctTypeId.toString());
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_acctTypeId.toString();
				
				acctAttribList.add(this.setAcctAttrib(acctCode, szx_description, memberCode,payPwd,memberAcctCode,subjectCode));
				acctAttribList.add(this.setAcctAttrib(internal_acctCode, internal_szx_description, memberCode,payPwd,internal_memberAcctCode,internal_subjectCode));
			}
			if(!"".equals(dxAccount)){
				
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", dx_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_dx_acctTypeId);
				
				memberAcctCode = Long.parseLong(memberCode + dx_acctTypeId.toString());
				internal_memberAcctCode = Long.parseLong(memberCode + internal_dx_acctTypeId.toString());
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + dx_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_dx_acctTypeId.toString();
				
				acctAttribList.add(this.setAcctAttrib(acctCode, dx_description, memberCode,payPwd,memberAcctCode,subjectCode));
				acctAttribList.add(this.setAcctAttrib(internal_acctCode, internal_dx_description, memberCode,payPwd,internal_memberAcctCode,internal_subjectCode));
			}
			if(!"".equals(ltAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", lt_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_lt_acctTypeId);
				
				memberAcctCode = Long.parseLong(memberCode + lt_acctTypeId.toString());
				internal_memberAcctCode = Long.parseLong(memberCode + internal_lt_acctTypeId.toString());
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + lt_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_lt_acctTypeId.toString();
				
				acctAttribList.add(this.setAcctAttrib(acctCode, lt_description, memberCode,payPwd,memberAcctCode,subjectCode));
				acctAttribList.add(this.setAcctAttrib(internal_acctCode, internal_lt_description, memberCode,payPwd,internal_memberAcctCode,internal_subjectCode));
			}
			if(!"".equals(jwAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", jw_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_jw_acctTypeId);
				
				memberAcctCode = Long.parseLong(memberCode + jw_acctTypeId.toString());
				internal_memberAcctCode = Long.parseLong(memberCode + internal_jw_acctTypeId.toString());
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + jw_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_jw_acctTypeId.toString();
				
				acctAttribList.add(this.setAcctAttrib(acctCode, jw_description, memberCode,payPwd,memberAcctCode,subjectCode));
				acctAttribList.add(this.setAcctAttrib(internal_acctCode, internal_jw_description, memberCode,payPwd,internal_memberAcctCode,internal_subjectCode));
			}
			
		}
		return acctAttribList;
	}
	
	/**
	 * 更新用户为 个人卖家身份
	 * @author Sunny Ying
	 * @param memberCode
	 * @throw null
	 * @return int
	 */
	public int editMemberTypeByMemberCode(String memberCode) {
		if(memberCode != null && !memberCode.equals(""))
		{
			MemberDto memberDto = memberService.findByMemberCode(Long.parseLong(memberCode));
			if(memberDto != null){
				Map map= new HashMap();
				map.put("memberCode", Long.parseLong(memberCode));
				map.put("type", memberType);
				int updateResult = memberService.editMemberTypeByMemberCode(map);
				return updateResult;
			}
		}
		return updateFail;
	}
	

	
	public int editMemberTypeByMemberCode(Integer sellerCode,
			String memberCode) {
		if(sellerCode != null && memberCode !=null){
			ServiceLevelEnum serviceLevel = ServiceLevelEnum.getServiceLevelEnum(sellerCode);
			return memberService.updateServiceLevelByMemCode(serviceLevel, Long.parseLong(memberCode));
		}
		return 0;
	}




	public int openUserAccountRdTx(Map<String,String> map) {
		int returnResult = justSellerPurview;
		if(map != null){
			String memberCode = map.get("memberCode");
			MemberDto memberDto = memberService.findByMemberCode(Long.parseLong(memberCode));
			if(memberDto != null){
				if(memberDto.getType() == personalSeller){
					List<Acct> acctList = this.setAcctList(map);
					acctDAO.batchCreate(acctList);
					List<AcctAttrib> acctAttribList = this.setAcctAttribList(map);
					acctAttribDAO.createBatchAcctAttrib(acctAttribList);
					returnResult = sellerAccountPurview;
				}
			}
		}
		return returnResult;
	}
	
	public List<Acct> setAcctList(Map<String,String> map){
		List<Acct> acctList = new ArrayList<Acct>();
		if(map != null){
			String szxAccount = map.get("szxAccount");
			String dxAccount = map.get("dxAccount");
			String ltAccount = map.get("ltAccount");
			String jwAccount = map.get("jwAccount");
			String memberCode = map.get("memberCode");
			
			String subjectCode = "";
			String internal_subjectCode = "";
			String acctCode = "";
			String internal_acctCode = "";
			if(!"".equals(szxAccount)){
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_acctTypeId.toString();
				
				acctList.add(this.setAcct(acctCode, acctTypeId, memberCode));
				acctList.add(this.setAcct(internal_acctCode, internal_acctTypeId, memberCode));
			}
			if(!"".equals(dxAccount)){
				
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", dx_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_dx_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + dx_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_dx_acctTypeId.toString();
				
				acctList.add(this.setAcct(acctCode, dx_acctTypeId, memberCode));
				acctList.add(this.setAcct(internal_acctCode, internal_dx_acctTypeId, memberCode));
			}
			if(!"".equals(ltAccount)){
				
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", lt_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_lt_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + lt_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_lt_acctTypeId.toString();
				
				acctList.add(this.setAcct(acctCode, lt_acctTypeId, memberCode));
				acctList.add(this.setAcct(internal_acctCode, internal_lt_acctTypeId, memberCode));
			}
			if(!"".equals(jwAccount)){
				
				Map model = new HashMap();				
				model.put("type", type);
				model.put("acctTypeId", jw_acctTypeId);
				Map internal_model = new HashMap();
				internal_model.put("type", type);
				internal_model.put("acctTypeId", internal_jw_acctTypeId);
				
				subjectCode=memberAcctTemplateService.getSubjectCode(model);
				internal_subjectCode = memberAcctTemplateService.getSubjectCode(internal_model);
				acctCode = subjectCode + memberCode + jw_acctTypeId.toString();
				internal_acctCode = internal_subjectCode + memberCode + internal_jw_acctTypeId.toString();
				
				acctList.add(this.setAcct(acctCode, jw_acctTypeId, memberCode));
				acctList.add(this.setAcct(internal_acctCode, internal_jw_acctTypeId, memberCode));
			}
			return acctList;
		}		
		return null;
	}
	
	/**
	 * 
	 * @author Sunny Ying
	 * @param acctCode
	 * @param acctTypeId
	 * @param memberCode
	 * @throw null
	 * @return Acct
	 */
	public Acct setAcct(String acctCode,Long acctTypeId,String memberCode){
		Acct acct =new Acct();
		acct.setAcctCode(acctCode);
		acct.setMemberCode(Long.parseLong(memberCode));		
		//acct.setBalance(new BigDecimal(0));
		acct.setStatus(status);
		acct.setFrozenAmount(new BigDecimal(0));
		acct.setCreateDate(new Date());
		acct.setUpdateDate(new Date());
		acct.setCreditBalance(new BigDecimal(0));
		acct.setDebitBalance(new BigDecimal(0));
		return acct;
	}
	
	
	
	public boolean checkUserCidVerify(String memberCode) {
		try {
			return memberVerifyService.doQueryRealNameVerifyNsTx(Long.parseLong(memberCode));
		} catch (NumberFormatException e) {
			log.error("IndividualSellerServiceImpl MemberVerifyService throws NumberFormatException", e);
			return false;
		} catch (Exception e) {
			log.error("IndividualSellerServiceImpl MemberVerifyService throws Exception", e);
			return false;
		}
	}
	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}


	public void setAcctAttribDAO(AcctAttribDAO acctAttribDAO) {
		this.acctAttribDAO = acctAttribDAO;
	}


	public void setAcctDAO(AcctDAO acctDAO) {
		this.acctDAO = acctDAO;
	}

	public void setMemberAcctTemplateService(
			MemberAcctTemplateService memberAcctTemplateService) {
		this.memberAcctTemplateService = memberAcctTemplateService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	
}
