package com.pay.poss.memberrelation.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.memberrelation.dao.MemberRelationDao;
import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.model.MemberRelation;
import com.pay.poss.memberrelation.service.MemberRelationService;
import com.pay.poss.memberrelation.util.MemberRelationTemplate;

public class MemberRelationServiceImpl implements MemberRelationService {
	
	private MemberRelationDao memberRelationDao;
	private static final Log LOG = LogFactory.getLog(MemberRelationServiceImpl.class);

	private MemberRelation geterateMemberRelation(RelationDataDto dto){
		MemberRelation relation=new MemberRelation();
		relation.setFatherMemberCode(new Long(dto.getFatherMemberCode()));
		relation.setSonMemberCode(dto.getSunMemberCode());
		relation.setSonZhName(dto.getSunMemberCode().toString());
		relation.setValue1(dto.getRelationStoreNumbers());
		relation.setValue2(dto.getRelationStoreAddress());
		relation.setStatus(1);
		relation.setRelationType("2");
		relation.setSonMerchantCode(1L); 
		return relation;
	}

	@Override
	public void insertMemberRelation(List<RelationDataDto> dataDtoList) {
		for(RelationDataDto dto:dataDtoList){
			MemberRelation relation=geterateMemberRelation(dto);
			try{
				memberRelationDao.create(relation);	
			}catch(Exception e){
				LOG.error("关联会员号"+dto.getSunMemberCode()+"失败 "+e);
			}
		}
	}

	
	public void setMemberRelationDao(MemberRelationDao memberRelationDao) {
		this.memberRelationDao = memberRelationDao;
	}

	@Override
	public MemberRelationTemplate getVouchDataTemplate() {
		return MemberRelationTemplate.getInstance();
	}

	@Override
	public List<MemberRelation> queryMemberRelationByCondition(
			MemberRelation mebmerRelation,Page  page) {
		int totalCount=memberRelationDao.countMemberRelationByCondition(mebmerRelation);
		LOG.info("totalCount  :  "+totalCount);
		MemberRelation dto=setPage(mebmerRelation, page, totalCount);
		if(totalCount==0)
			return null;		
		return memberRelationDao.queryMemberRelationByCondition(dto);
	}

	private MemberRelation  setPage(MemberRelation dto,Page  page,Integer totalCount){
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
	

}
