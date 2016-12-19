package com.pay.poss.memberrelation.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.model.MemberRelation;
import com.pay.poss.memberrelation.util.MemberRelationTemplate;

/**
 * @Title: MemberRelationService.java
 * @Package com.pay.poss.memberrelation.service
 * @Description: 保存关联信息
 * @author cf
 * @date 2011-9-23 下午04:15:28
 * @version V1.0
 */
public interface MemberRelationService {

	public void insertMemberRelation(List<RelationDataDto> dataDtoList);

	/**
	 * 得到模板对象
	 * 
	 * @return
	 */
	public MemberRelationTemplate getVouchDataTemplate();

	List<MemberRelation> queryMemberRelationByCondition(
			MemberRelation mebmerRelation, Page page);

}
