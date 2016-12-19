package com.pay.pe.account.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.dao.Page;
import com.pay.pe.account.controller.SubjectBalanceQueryCommand;
import com.pay.pe.account.dao.EntryQueryDao;
import com.pay.pe.account.dto.QueryBalanceDTO;
import com.pay.pe.account.dto.QueryEntryDTO;
import com.pay.pe.account.dto.SubjectBalanceDTO;
import com.pay.pe.account.dto.SubjectBalanceQueryDto;
import com.pay.pe.account.enums.SubjectLevelEnum;
import com.pay.pe.account.service.EntryQueryService;
import com.pay.util.MfDate;

public class EntryQueryServiceImpl implements EntryQueryService {
	
	
	private EntryQueryDao entryQueryDao ;
	private MemberQueryService memberQueryService;
	private static final Log log = LogFactory.getLog(EntryQueryServiceImpl.class);




	@Override
	public QueryBalanceDTO getBalanceInfo(String acctCode, MfDate beginDate, MfDate endDate) {
		 QueryBalanceDTO balanceInfo = entryQueryDao.getBalanceInfo(acctCode, beginDate, endDate);
		return balanceInfo ;
	}
	
	
	public EntryQueryDao getEntryQueryDao() {
		return entryQueryDao;
	}

	public void setEntryQueryDao(EntryQueryDao entryQueryDao) {
		this.entryQueryDao = entryQueryDao;
	}

	@Override
	public List<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate) {
		return entryQueryDao.getAllEntries(acctCode, beginDate, endDate);
	}

	@Override
	public Page<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate, Page<QueryEntryDTO> page) {
		return entryQueryDao.getAllEntries(acctCode, beginDate, endDate, page);		
	}


	@Override
	public List<SubjectBalanceDTO> getSubjectBalanceList(SubjectBalanceQueryCommand queryCommand,Page page) {
		int subjectLevel=Integer.parseInt(queryCommand.getSubjectLevel());
		
		List<SubjectBalanceDTO> dtoList=null;
		MfDate beginDateMfDate = new MfDate(queryCommand.getBeginDate());
		MfDate endDateMfDate = new MfDate(queryCommand.getEndDate()) ;		
		String beginDateStr = beginDateMfDate.toString("yyyyMMdd");
		String endDateStr = endDateMfDate.toString("yyyyMMdd");
				
		SubjectBalanceQueryDto dto=convertSubjectBalanceQueryDto(queryCommand);
		dto.setBeginDate(beginDateStr);
		dto.setEndDate(endDateStr);
		
		//五级科目
		if(SubjectLevelEnum.FIVE_LEVER.getCode()==subjectLevel){			
			dtoList=getSubjectBalanceListFiveLevel(dto,page);
			setAcctName(dtoList);
		}else{
			dtoList=getSubjectBalanceListLevel(dto,page);
			setCrDrBalance(dtoList);
		}
		return dtoList;
	}
	
	
	private List<SubjectBalanceDTO> getSubjectBalanceListLevel(SubjectBalanceQueryDto queryCommand,Page page){
		
		Integer count=entryQueryDao.countSubjectBalanceListLevel(queryCommand);
		SubjectBalanceQueryDto dto = setPage(queryCommand,page,count);
		if(null == dto) return null;		
		return entryQueryDao.getSubjectBalanceList(dto);
	}
	
	private List<SubjectBalanceDTO> getSubjectBalanceListFiveLevel(SubjectBalanceQueryDto queryCommand,Page page){		
		Integer count=entryQueryDao.countSubjectBalanceListFiveLevel(queryCommand);
		SubjectBalanceQueryDto dto = setPage(queryCommand,page,count);
		if(null == dto) return null;		
		return entryQueryDao.getSubjectBalanceListFiveLevel(dto);
	}
	

	private SubjectBalanceQueryDto convertSubjectBalanceQueryDto(SubjectBalanceQueryCommand command){
		SubjectBalanceQueryDto dto=new SubjectBalanceQueryDto();
		dto.setAcctCode(command.getAcctCode());
		dto.setBeginDate(command.getBeginDate());
		dto.setEndDate(command.getEndDate());
		dto.setSubjectLevel(command.getSubjectLevel());
		return dto;
	}
	
	@Override
	public List<SubjectBalanceDTO> getAllSubjectBalanceList(
			SubjectBalanceQueryCommand queryCommand,Integer count) {
		int subjectLevel=Integer.parseInt(queryCommand.getSubjectLevel());
		List<SubjectBalanceDTO> dtoList=null;
		MfDate beginDateMfDate = new MfDate(queryCommand.getBeginDate());
		MfDate endDateMfDate = new MfDate(queryCommand.getEndDate()) ;		
		String beginDateStr = beginDateMfDate.toString("yyyyMMdd");
		String endDateStr = endDateMfDate.toString("yyyyMMdd");
				
		SubjectBalanceQueryDto dto=convertSubjectBalanceQueryDto(queryCommand);
		dto.setBeginDate(beginDateStr);
		dto.setEndDate(endDateStr);
		
		dto.setPageStartRow(0);
		dto.setPageEndRow(count);
		//五级科目
		if(SubjectLevelEnum.FIVE_LEVER.getCode()==subjectLevel){			
			dtoList=entryQueryDao.getSubjectBalanceListFiveLevel(dto);
			setAcctName(dtoList);
		}else{
			dtoList=entryQueryDao.getSubjectBalanceList(dto);
			setCrDrBalance(dtoList);
		}
		return dtoList;
	}
	
	//设置五级科目的名称
	private void setAcctName(List<SubjectBalanceDTO> dtoList){
		if(null!=dtoList)
			for(SubjectBalanceDTO subjectDto:dtoList){				
				MemberInfoDto infoDto;
				try {
					infoDto = memberQueryService.doQueryMemberInfoNsTx(null, getMemberByAcctCode(subjectDto.getAcctCode()), null, null);
					if(null!=infoDto){
						subjectDto.setAcctName(infoDto.getMemberName());
					}
				} catch (Exception e) {
					log.error("取会员信息异常:  "+e);
				}
				if(subjectDto.getBeginningDr().signum() >= 0){
					subjectDto.setBeginningCrBalance(new BigDecimal(0));
				}else{
					//设置期初借方余额(元) 期初贷方余额(元)
					subjectDto.setBeginningCrBalance(subjectDto.getBeginningDr().negate());
					subjectDto.setBeginningDrBalance(new BigDecimal(0));
				}
				
				if(subjectDto.getEndingDr().signum() >= 0){
					subjectDto.setEndingCrBalance(new BigDecimal(0));
				}else{
					//设置期末借方余额(元) 期末贷方余额(元)
					subjectDto.setEndingCrBalance(subjectDto.getEndingDr().negate());
					subjectDto.setEndingDrBalance(new BigDecimal(0));
				}
			}
	}
	
	private void setCrDrBalance(List<SubjectBalanceDTO> dtoList){
		if(null!=dtoList)
			for(SubjectBalanceDTO subjectDto:dtoList){				
				if(subjectDto.getBeginningDr().signum() >= 0){
					subjectDto.setBeginningCrBalance(new BigDecimal(0));
				}else{
					//设置期初借方余额(元) 期初贷方余额(元)
					subjectDto.setBeginningCrBalance(subjectDto.getBeginningDr().negate());
					subjectDto.setBeginningDrBalance(new BigDecimal(0));
				}
				
				if(subjectDto.getEndingDr().signum() >= 0){
					subjectDto.setEndingCrBalance(new BigDecimal(0));
				}else{
					//设置期末借方余额(元) 期末贷方余额(元)
					subjectDto.setEndingCrBalance(subjectDto.getEndingDr().negate());
					subjectDto.setEndingDrBalance(new BigDecimal(0));
				}
			}
	}
	
	private Long getMemberByAcctCode(String acctCode){
		String code=null;
		if(null!=acctCode && acctCode.length()==25){
			code=acctCode.substring(11, 22);
		}		
		return Long.parseLong(code);
	}
	

	private SubjectBalanceQueryDto  setPage(SubjectBalanceQueryDto dto,Page  page,Integer totalCount){
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
        if (null == page || totalCount == 0) {
			  return null;
        }
        page.setTotalCount(totalCount);
        pageEndRow = page.getPageNo() * page.getPageSize();
        if ((page.getPageNo() - 1) == 0) {
        	pageStartRow = 0;
        }else {
        	pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
        }
        dto.setPageStartRow(pageStartRow);
        dto.setPageEndRow(pageEndRow);
		return dto;
	}
	

	@Override
	public List<SubjectBalanceDTO> getSubjectBalanceList(String acctCode,
			MfDate beginDate, MfDate endDate) {
		return entryQueryDao.getSubjectBalanceList(acctCode, beginDate, endDate);
	}

	@Override
	public List<QueryEntryDTO> getEntrieListByOrderId(String orderId) {
		return entryQueryDao.getEntrieListByOrderId(orderId);
	}


	@Override
	public SubjectBalanceDTO getSubjectBalance(String acctCode,
			MfDate beginDate, MfDate endDate) {
		return entryQueryDao.getSubjectBalance(acctCode, beginDate, endDate);
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}


	@Override
	public Integer countSubjectBalanceListFiveLevel(
			SubjectBalanceQueryCommand queryCommand) {	
		MfDate beginDateMfDate = new MfDate(queryCommand.getBeginDate());
		MfDate endDateMfDate = new MfDate(queryCommand.getEndDate()) ;		
		String beginDateStr = beginDateMfDate.toString("yyyyMMdd");
		String endDateStr = endDateMfDate.toString("yyyyMMdd");				
		SubjectBalanceQueryDto dto=convertSubjectBalanceQueryDto(queryCommand);
		dto.setBeginDate(beginDateStr);
		dto.setEndDate(endDateStr);
		return entryQueryDao.countSubjectBalanceListFiveLevel(dto);
	}

	@Override
	public Integer countSubjectBalanceListLevel(
			SubjectBalanceQueryCommand queryCommand) {
		MfDate beginDateMfDate = new MfDate(queryCommand.getBeginDate());
		MfDate endDateMfDate = new MfDate(queryCommand.getEndDate()) ;		
		String beginDateStr = beginDateMfDate.toString("yyyyMMdd");
		String endDateStr = endDateMfDate.toString("yyyyMMdd");				
		SubjectBalanceQueryDto dto=convertSubjectBalanceQueryDto(queryCommand);
		dto.setBeginDate(beginDateStr);
		dto.setEndDate(endDateStr);
		return entryQueryDao.countSubjectBalanceListLevel(dto);
	}


	

}
