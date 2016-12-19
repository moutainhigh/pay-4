package com.pay.poss.personmanager.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.constants.OperateStatusConstants;
import com.pay.poss.base.enums.CommonEnum;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.operatelogmanager.model.PossOperate;
import com.pay.poss.personmanager.dao.AcctDAO;
import com.pay.poss.personmanager.dao.PersonMangerDAO;
import com.pay.poss.personmanager.dao.PersonMemberDAO;
import com.pay.poss.personmanager.dto.AcctDto;
import com.pay.poss.personmanager.dto.PersonMemberInfoDto;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctAssociateDto;
import com.pay.poss.personmanager.dto.PersonalAcctBalanceDto;
import com.pay.poss.personmanager.dto.PersonalAcctInfoSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctTradeTotalDto;
import com.pay.poss.personmanager.dto.PersonalLoginHistoryDto;
import com.pay.poss.personmanager.formbean.PersonSearchFormBean;
import com.pay.poss.personmanager.service.PersonMemberService;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
/**
 * @Description
 * @project poss-membermanager
 * @file PersonMemberServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *  Date Author Changes 
 *  2010-9-30 jim_chen Create
 */
public class PersonMemberServiceImpl implements PersonMemberService {
	
	private Log log = LogFactory.getLog(PersonMemberServiceImpl.class);
	private PersonMemberDAO personMemberDAO;
	
	private PersonMangerDAO postPersonManagerDao;
	private AcctDAO possAcctDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalAcctInfoSearchDto>  selectPersonalLoginHistoryList(Map paramMap,Page<PersonalAcctInfoSearchDto> page){
		  Long memberCode = null;//注意如果此处没有输入值可能为0
		  Integer pageStartRow;// 起始行
		  Integer pageEndRow;// 结束行
		  String paramMemberCode = (String)paramMap.get("memberCode");
		  //类型判断
		  memberCode = this.getLongValue(paramMemberCode);
		  paramMap.put("memberCode",memberCode);
		  if (null == page) {
			  return null;
		  }
		  int totalCount = personMemberDAO.countPersonalLoginHistory(paramMap);
		  page.setTotalCount(totalCount);
		  if (totalCount == 0) {
			  return null;
		  }
		  pageEndRow = page.getPageNo() * page.getPageSize();
		  if ((page.getPageNo() - 1) == 0) {
			  pageStartRow = 0;
		  }else {
			  pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		  }
		  paramMap.put("pageStartRow", pageStartRow);
		  paramMap.put("pageEndRow", pageEndRow);
		  List<PersonalAcctInfoSearchDto> list = personMemberDAO.queryPersonalLoginHistoryList(paramMap);
		  return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalAcctInfoSearchDto> selectPersonalAcctInfoList(Map paramMap,Page<PersonalAcctInfoSearchDto> page) {
		  Long memberCode = null;//注意如果此处没有输入值可能为0
		  Integer pageStartRow;// 起始行
		  Integer pageEndRow;// 结束行
		  String paramMemberCode = (String)paramMap.get("memberCode");
		  memberCode = this.getLongValue(paramMemberCode);
		  paramMap.put("memberCode",memberCode);
		  if (null == page) {
			  return null;
		  }
		  int totalCount = personMemberDAO.countPersonalAcctInfo(paramMap);
		  page.setTotalCount(totalCount);
		  if (totalCount == 0) {
			  return null;
		  }
		  pageEndRow = page.getPageNo() * page.getPageSize();
		  if ((page.getPageNo() - 1) == 0) {
			  pageStartRow = 0;
		  }else {
			  pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		  }
		  paramMap.put("pageStartRow", pageStartRow);
		  paramMap.put("pageEndRow", pageEndRow);
		  List<PersonalAcctInfoSearchDto> list = personMemberDAO.queryPersonalAcctInfoList(paramMap);
		  return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	 public List<PersonMemberInfoDto> selectPersonMemberInfoList(PersonSearchFormBean personSearchFormBean,Page<PersonMemberInfoDto> page) {
	  Map paramMap = new HashMap();
	  Long memberCode = null;//注意如果此处没有输入值可能为0
	  Integer pageStartRow;// 起始行
	  Integer pageEndRow;// 结束行
	  //类型判断
	  memberCode = this.getLongValue(personSearchFormBean.getMemberCode());
	  paramMap.put("loginName", personSearchFormBean.getLoginName());
	  paramMap.put("memberCode",memberCode);
	  paramMap.put("userName", personSearchFormBean.getUserName());
	  paramMap.put("status", personSearchFormBean.getStatus());
	  if (null == page) {
		  return null;
	  }
	  int totalCount = personMemberDAO.countPersonInfo(paramMap);
	  page.setTotalCount(totalCount);
	  if (totalCount == 0) {
		  return null;
	  }
	  pageEndRow = page.getPageNo() * page.getPageSize();
	  if ((page.getPageNo() - 1) == 0) {
		  pageStartRow = 0;
	  }else {
		  pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
	  }
	  paramMap.put("pageStartRow", pageStartRow);
	  paramMap.put("pageEndRow", pageEndRow);
	  List<PersonMemberInfoDto> list = personMemberDAO.queryPersonInfoList(paramMap);
	  return list;
	 }
	

	@SuppressWarnings("unchecked")
	@Override
	 public List<PersonalAcctAssociateDto> queryPersonalAcctAssociatelist(PersonSearchFormBean personSearchFormBean,Page<PersonalAcctAssociateDto> page) {
	  Map paramMap = new HashMap();
	  Long memberCode = null;//注意如果此处没有输入值可能为0
	  Integer pageStartRow;// 起始行
	  Integer pageEndRow;// 结束行
	  //类型判断
	  memberCode = this.getLongValue(personSearchFormBean.getMemberCode());
	  paramMap.put("loginName", personSearchFormBean.getLoginName());
	  paramMap.put("memberCode",personSearchFormBean.getLoginName() == null?null:memberCode);
	  paramMap.put("userName", personSearchFormBean.getUserName());
	  paramMap.put("loginIp", personSearchFormBean.getLoginIp());
	  if (null == page) {
		  return null;
	  }
	  int totalCount = personMemberDAO.countqueryPersonalAcctAssociate(paramMap);
	  page.setTotalCount(totalCount);
	  if (totalCount == 0) {
		  return null;
	  }
	  pageEndRow = page.getPageNo() * page.getPageSize();
	  if ((page.getPageNo() - 1) == 0) {
		  pageStartRow = 0;
	  }else {
		  pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
	  }
	  paramMap.put("pageStartRow", pageStartRow);
	  paramMap.put("pageEndRow", pageEndRow);
	  List<PersonalAcctAssociateDto> list = personMemberDAO.queryPersonalAcctAssociatelist(paramMap);
	  return list;
	 }
	
	@SuppressWarnings("unchecked")
	public List<PersonalAcctBalanceDto> queryPersonalAcctBalanceList(Map paramMap,Page<PersonalAcctBalanceDto> page) {
		  Long memberCode = null;//注意如果此处没有输入值可能为0
		  Integer pageStartRow;// 起始行
		  Integer pageEndRow;// 结束行
		  String paramMemberCode = (String)paramMap.get("memberCode");
		  //类型判断
		  memberCode = this.getLongValue(paramMemberCode);
		  paramMap.put("memberCode",memberCode);
		  if (null == page) {
			  return null;
		  }
		  int totalCount = personMemberDAO.countPersonalAcctBalanceList(paramMap);
		  page.setTotalCount(totalCount);
		  if (totalCount == 0) {
			  return null;
		  }
		  pageEndRow = page.getPageNo() * page.getPageSize();
		  if ((page.getPageNo() - 1) == 0) {
			  pageStartRow = 0;
		  }else {
			  pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		  }
		  if("yes".equals(paramMap.get("exportExcel"))){
			  paramMap.put("pageStartRow", 0);
			  paramMap.put("pageEndRow", 65530);
		  }else{
			  paramMap.put("pageStartRow", pageStartRow);
			  paramMap.put("pageEndRow", pageEndRow);
		  }
		 
		  List<PersonalAcctBalanceDto> list = personMemberDAO.queryPersonalAcctBalanceList(paramMap);
		  return list;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean updatePersonalMemberOperate(Map paramMap){
		Boolean isSuccess = true;
		try{	
			personMemberDAO.updatePersonalMemberOperate(paramMap);
		}catch(Exception e){
			log.error("PersonMemberServiceImpl.updatePersonalMemberOperate is error...");
			e.printStackTrace();
			isSuccess = false;
		}
	return isSuccess;
	}
	
	
	public void updateMemberStatusTrans(Map<String,Object> paramMap) throws PossException{
		String operateStatus = (String)paramMap.get("operate");
		try{
			//1:更新个人会员t_member表中的STATUS
			this.updatePersonalMemberOperate(paramMap);
			//2:如果页面传值为冻结(frozen)
			if("frozen".equals(operateStatus)){
				//2-1在日志表中插入冻结状态的记录
				PossOperate operate = new PossOperate();
				operate.setObjectCode((String)paramMap.get("memberCode")); 
				operate.setLoginName((String)paramMap.get("loginName"));
				operate.setLoginIp((String)paramMap.get("loginIp"));
				operate.setActionUrl(CommonEnum.PERSONAL_MEMBER_FROZEN.getDescription());
				operate.setUpdateOperator("loginName");
				operate.setType(1);
				operate.setStatus(1);
				personMemberDAO.insertPossOperater(operate);
			}
			//3:如果页面传值为解冻(unFrozen)
			if("unFrozen".equals(operateStatus)){
				//3-1 在日志表中插入解冻的记录
				PossOperate operate = new PossOperate();
				operate.setObjectCode((String)paramMap.get("memberCode")); 
				operate.setLoginName((String)paramMap.get("loginName"));
				operate.setLoginIp((String)paramMap.get("loginIp"));
				operate.setActionUrl(CommonEnum.PERSONAL_MEMBER_UNFROZEN.getDescription());
				operate.setType(2);
				operate.setStatus(2);
				personMemberDAO.insertPossOperater(operate);
				//3-1更新该会员冻结状态记录为解冻
				paramMap.put("newStatus",2);
				paramMap.put("oldStatus",1);
				paramMap.put("objectCode",(String)paramMap.get("memberCode"));
				personMemberDAO.updatePossOperater(paramMap);
				
				//added by wangtq 2011-1-12  修改t_member_operate中的fail_time为0
				paramMap.put("memberCode", (String)paramMap.get("memberCode"));
				paramMap.put("type", 0);
				personMemberDAO.updateMemberOperateByCondition(paramMap);
			}
		}catch(Exception e){
			log.error("PersonMemberServiceImpl.updateMemberStatusTrans is error...");
			e.printStackTrace();
			throw new PossException("操作会员状态失败(冻结,解冻)!", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Boolean updatePersonalMemberAcctOperate(Map paramMap){
		Boolean isSuccess = true;
		try{	
			personMemberDAO.updatePersonalMemberAcctOperate(paramMap);
		}catch(Exception e){
			log.error("PersonMemberServiceImpl.updatePersonalMemberAcctOperate is error...");
			e.printStackTrace();
			isSuccess = false;
		}
	return isSuccess;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean updateAcctStatusTrans(Map paramMap) throws PossException{
		Boolean isSuess = false;
		String operateStatus = (String)paramMap.get("operate");
		String acctCode  = (String)paramMap.get("acctCode");
		//查询该账户是否冻结,止入或者止出,因为冻结的同时要对该账户进行止入和止出,
		//如果该账户已经做过止入或者止出则插入操作记录时排除该操作
		AcctAttribDto attr = personMemberDAO.selectAcctAttrib(paramMap);  
		if(null == attr) return isSuess; 
		try{
			PossOperate operate = new PossOperate();
			operate.setObjectCode(acctCode); 
			operate.setLoginName((String)paramMap.get("loginName"));
			operate.setLoginIp((String)paramMap.get("loginIp"));
			//1:操作操作状态(冻结,止入,止出)
			this.blockAcctOpeate(paramMap,operateStatus,acctCode,attr,operate);
			//2解除操作状态(解冻,解除止入,解除止出)
			this.unBlockAcctOpeate(paramMap, operateStatus, acctCode, attr, operate);
			isSuess = true;
		}catch(Exception e){
			log.error("PersonMemberServiceImpl.updateAcctStatusTrans is error...");
			e.printStackTrace();
			throw new PossException("操作个人会员账户状态失败!", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return isSuess;
	}
	
	@SuppressWarnings("unchecked")
	private  void blockAcctOpeate(Map paramMap,String operateStatus,String acctCode,AcctAttribDto attr,PossOperate operate){
		//1:如果controller传值为冻结,止入，止出
		if(OperateStatusConstants.OPERATE_FROZEN.equals(operateStatus)||
				OperateStatusConstants.OPERATE_ALLOWIN.equals(operateStatus) || 
				OperateStatusConstants.OPERATE_ALLOWOUT.equals(operateStatus)){
			//1-1如果操作为冻结,则要对该账户进行止入和止出
			if(OperateStatusConstants.OPERATE_FROZEN.equals(operateStatus)){
				//1-1-1判断该账户是否已经止入,如果无则止入
				if(!"0".equals(attr.getAllowIn())){
					paramMap.put("operate", OperateStatusConstants.OPERATE_ALLOWIN);
					operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_ALLOWIN);
					operate.setType(3);
					operate.setStatus(3);
					this.updatePersonalMemberAcctOperate(paramMap);
					personMemberDAO.insertPossOperater(operate);
				}
				//1-1-2判断该账户是否已经止出,如果无则止出
				if(!"0".equals(attr.getAllowOut())){
					paramMap.put("operate", OperateStatusConstants.OPERATE_ALLOWOUT);
					operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_ALLOWOUT);
					operate.setType(5);
					operate.setStatus(5);
					this.updatePersonalMemberAcctOperate(paramMap);
					personMemberDAO.insertPossOperater(operate);
				}
				//1-1-3 进行冻结操作
				paramMap.put("operate", OperateStatusConstants.OPERATE_FROZEN);
				operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_FROZEN);
				operate.setType(1);
				operate.setStatus(1);
				this.updatePersonalMemberAcctOperate(paramMap);
				personMemberDAO.insertPossOperater(operate);
			}else if(OperateStatusConstants.OPERATE_ALLOWIN.equals(operateStatus)){
				//1-2如果操作为止入,则要对该账户进行止入
				operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_ALLOWIN);
				operate.setType(3);
				operate.setStatus(3);
				this.updatePersonalMemberAcctOperate(paramMap);
				personMemberDAO.insertPossOperater(operate);
			}else if(OperateStatusConstants.OPERATE_ALLOWOUT.equals(operateStatus)){
				//1-2如果操作为止出,则要对该账户进行止出
				operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_ALLOWOUT);
				operate.setType(5);
				operate.setStatus(5);
				this.updatePersonalMemberAcctOperate(paramMap);
				personMemberDAO.insertPossOperater(operate);
			}
			
		}
	}
	@SuppressWarnings("unchecked")
	private  void unBlockAcctOpeate(Map paramMap,String operateStatus,String acctCode,AcctAttribDto attr,PossOperate operate){
		//2-1:如果controller传值为解冻,解除止出,解除止入
		if(OperateStatusConstants.OPERATE_UNFROZEN.equals(operateStatus)||
				OperateStatusConstants.OPERATE_UNALLOWIN.equals(operateStatus) || 
				OperateStatusConstants.OPERATE_UNALLOWOUT.equals(operateStatus)){
			//2-2如果传值为解冻
			if(OperateStatusConstants.OPERATE_UNFROZEN.equals(operateStatus)){
				//2-2-1找出冻结时的记录(时间)
				paramMap.put("oldStatus", 1);
				List<PossOperate>  listOpeDate = personMemberDAO.selectAcctFrozenOperateByAcctCode(paramMap);
				if(listOpeDate!=null && listOpeDate.size()>0){
					PossOperate	opeDate = (PossOperate)listOpeDate.get(0);
					paramMap.put("paramcreateDate", opeDate.getCreateDate());
					paramMap.remove("oldStatus");
					//2-2-2拿当初冻结时的时间找出有止入和止出的日志记录
					List<PossOperate>  listOpeate = personMemberDAO.selectAcctFrozenOperateByAcctCode(paramMap);
					for(PossOperate ope: listOpeate){
						//2-2-3如果冻结时并止入,则要解除止入,恢复止入记录为解除止入,并插入解除止入记录 
						if(3 == ope.getType()){
							operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_UNALLOWIN);
							operate.setType(4);
							operate.setStatus(4);
							paramMap.put("newStatus",4);
							paramMap.put("oldStatus",3);
							paramMap.put("objectCode", acctCode);
							
							paramMap.put("operate", OperateStatusConstants.OPERATE_UNALLOWIN);
							this.updatePersonalMemberAcctOperate(paramMap);
							personMemberDAO.insertPossOperater(operate);
							personMemberDAO.updatePossOperater(paramMap);
						}
						//2-2-4如果冻结时并止出,则要解除止出,恢复止出记录为解除止出,并插入解除止出记录 
						if(5 == ope.getType()){
							operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_UNALLOWOUT);
							operate.setType(6);
							operate.setStatus(6);
							paramMap.put("newStatus",6);
							paramMap.put("oldStatus",5);
							paramMap.put("objectCode", acctCode);
							paramMap.put("operate", OperateStatusConstants.OPERATE_UNALLOWOUT);
							this.updatePersonalMemberAcctOperate(paramMap);
							personMemberDAO.insertPossOperater(operate);
							personMemberDAO.updatePossOperater(paramMap);
						}
						//2-2-5如果是冻结记录,则进行解冻,并插入解冻记录同时更新当初的冻结记录
						if(1 == ope.getType()){
							operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_UNFROZEN);
							operate.setType(2);
							operate.setStatus(2);
							paramMap.put("newStatus",2);
							paramMap.put("oldStatus",1);
							paramMap.put("objectCode", acctCode);
							paramMap.put("operate", OperateStatusConstants.OPERATE_UNFROZEN);
							this.updatePersonalMemberAcctOperate(paramMap);
							personMemberDAO.insertPossOperater(operate);
							personMemberDAO.updatePossOperater(paramMap);
							
							//added by wangtq 2011-1-12  修改t_member_operate中的fail_time为0
							paramMap.put("memberCode", attr.getMemberCode());
							paramMap.put("type", 1);
							paramMap.put("acctType", 10);
							personMemberDAO.updateMemberOperateByCondition(paramMap);
						}
					}
				}
			}else if(OperateStatusConstants.OPERATE_UNALLOWIN.equals(operateStatus)){
				//2-3如果传值为解除止入
				operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_UNALLOWIN);
				operate.setType(4);
				operate.setStatus(4);
				paramMap.put("newStatus",4);
				paramMap.put("oldStatus",3);
				paramMap.put("objectCode", acctCode);
				
				this.updatePersonalMemberAcctOperate(paramMap);
				personMemberDAO.insertPossOperater(operate);
				personMemberDAO.updatePossOperater(paramMap);
			}else if(OperateStatusConstants.OPERATE_UNALLOWOUT.equals(operateStatus)){
				//2-4如果传值为解除止出
				operate.setActionUrl(OperateStatusConstants.PERSONAL_ACCT_UNALLOWOUT);
				operate.setType(6);
				operate.setStatus(6);
				paramMap.put("newStatus",6);
				paramMap.put("oldStatus",5);
				paramMap.put("objectCode", acctCode);
				this.updatePersonalMemberAcctOperate(paramMap);
				personMemberDAO.insertPossOperater(operate);
				personMemberDAO.updatePossOperater(paramMap);
				
				//added by wangtq 2011-1-12  修改t_member_operate中的fail_time为0
				paramMap.put("memberCode", attr.getMemberCode());
				paramMap.put("type", 1);
				paramMap.put("acctType", 10);
				personMemberDAO.updateMemberOperateByCondition(paramMap);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public List<PersonalAcctTradeTotalDto> queryPeraonalAcctTradeTotal(Map paramMap) {
		 List<PersonalAcctTradeTotalDto> list = personMemberDAO.queryPeraonalAcctTradeTotal(paramMap);
		 return list;
	}
	@Override
	public AcctDto selectAcctDtoByMemberCode(Long memberCode) {
		if (null == memberCode) {
			return null;
		}
		return possAcctDAO.selectAcctByMemberCode(memberCode);
	}

	@Override
	public PersonMemberSearchDto selectPersonMemberSearchDto(Long memberCode) {
		return postPersonManagerDao.selectPersonMember(memberCode);
	}

	@Override
	public List<PersonMemberSearchDto> selectPersonMemberList(PersonMemberSearchDto personSearchDto, Page<PersonMemberSearchDto> page) {
		if (null == page) {
			return null;
		}
		int totalCount = postPersonManagerDao.countPersonMember(personSearchDto);
		page.setTotalCount(totalCount);
		if (totalCount == 0) {
			return null;
		}
		personSearchDto.setPageEndRow((page.getPageNo() * page.getPageSize()));
		if ((page.getPageNo() - 1) == 0) {
			personSearchDto.setPageStartRow(0);
		}
		else {
			personSearchDto.setPageStartRow((page.getPageNo() - 1) * page.getPageSize());
		}
		List<PersonMemberSearchDto> list = postPersonManagerDao.queryPersonList(personSearchDto);
		page.setResult(list);
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PersonMemberInfoDto selectPersonlMemberInfoDetail(String paramMemberCode){
		Long memberCode = null;
		  //类型判断
		memberCode = this.getLongValue(paramMemberCode);
		Map paramMap = new HashMap(1);
		paramMap.put("memberCode", memberCode);
		return personMemberDAO.selectPersonlMemberInfoDetail(paramMap);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PersonalLoginHistoryDto selectMemberLoginIp(String paramMemberCode){
		Long memberCode = null;
		  //类型判断
		memberCode = this.getLongValue(paramMemberCode);
		Map paramMap = new HashMap(1);
		paramMap.put("memberCode", memberCode);
		return personMemberDAO.selectMemberLoginIp(paramMap);
	}
	@SuppressWarnings("unchecked")
	public PersonMemberInfoDto selectPersonlMemberIsPaperFile (String paramMemberCode){
		Long memberCode = null;
		  //类型判断
		memberCode = this.getLongValue(paramMemberCode);
		Map paramMap = new HashMap(1);
		paramMap.put("memberCode", memberCode);
		return personMemberDAO.selectPersonlMemberIsPaperFile(memberCode);
	}
	

	@SuppressWarnings("unchecked")
	public PersonMemberInfoDto selectMemberBankAcct(String paramMemberCode){
		Long memberCode = null;
		memberCode = this.getLongValue(paramMemberCode);
		Map paramMap = new HashMap(1);
		paramMap.put("memberCode", memberCode);
		return personMemberDAO.selectMemberBankAcct(paramMap);
	}
	@SuppressWarnings("unchecked")
	public Integer countBindMatrixCard(String paramMemberCode){
		Long memberCode = null;
		memberCode = this.getLongValue(paramMemberCode);
		Map paramMap = new HashMap(1);
		paramMap.put("memberCode", memberCode);
		return personMemberDAO.countBindMatrixCard(paramMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PersonalAcctInfoSearchDto> queryPersonalMemberAcctMessage(Map paramMap,Page<PersonalAcctInfoSearchDto> page) {
		  Long memberCode = null;//注意如果此处没有输入值可能为0
		  Integer pageStartRow;// 起始行
		  Integer pageEndRow;// 结束行
		  String paramMemberCode = (String)paramMap.get("memberCode");
		  //类型判断
		  memberCode = this.getLongValue(paramMemberCode);
		  paramMap.put("memberCode",memberCode);
		  if (null == page) {
			  return null;
		  }
		  int totalCount = personMemberDAO.countPersonalMemberAcctMessage(paramMap);
		  page.setTotalCount(totalCount);
		  if (totalCount == 0) {
			  return null;
		  }
		  pageEndRow = page.getPageNo() * page.getPageSize();
		  if ((page.getPageNo() - 1) == 0) {
			  pageStartRow = 0;
		  }else {
			  pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		  }
		  paramMap.put("pageStartRow", pageStartRow);
		  paramMap.put("pageEndRow", pageEndRow);
		  List<PersonalAcctInfoSearchDto> list = personMemberDAO.queryPersonalMemberAcctMessage(paramMap);
		  return list;
	}
	
	@SuppressWarnings("unchecked")
	public PersonMemberInfoDto selectMemberByMemberCode(Map paramMap){
		Long memberCode = null;
		String paramMemberCode = (String)paramMap.get("memberCode");
		memberCode = this.getLongValue(paramMemberCode);
		paramMap.put("memberCode",memberCode);
		return personMemberDAO.selectMemberByMemberCode(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public String queryBalance(Map paramMap) {
		return personMemberDAO.queryBalance(paramMap);
	}
	
	private Long getLongValue (String paramMemberCode){
		Long code = null;
		//类型判断
		 if(null != paramMemberCode && !"".equals(paramMemberCode)){
			  if(!this.isNumber(paramMemberCode)){
				  code = new Long(0);
			  }else{
				  BigDecimal  num = new BigDecimal (paramMemberCode.trim());
				  code = num.longValue();
			  }
		  }
		 return code;
	}
	
	private boolean isNumber(String value){
		try {
			new BigDecimal (value.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		} 
	}
	
	public void setPersonMemberDAO(PersonMemberDAO personMemberDAO) {
		this.personMemberDAO = personMemberDAO;
	}
	
	
	public void setPostPersonManagerDao(PersonMangerDAO postPersonManagerDao) {
		this.postPersonManagerDao = postPersonManagerDao;
	}

	public void setPossAcctDAO(AcctDAO possAcctDAO) {
		this.possAcctDAO = possAcctDAO;
	}


}
