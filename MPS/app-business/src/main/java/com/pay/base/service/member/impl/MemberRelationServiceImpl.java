/**
 * 
 */
package com.pay.base.service.member.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.pay.base.dao.member.MemberRelationDAO;
import com.pay.base.dto.MemberRelationDto;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.Operator;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.member.MemberRelationService;
import com.pay.base.service.operator.OperatorServiceFacade;

/**
 * @author 戴德荣
 * date  2011-6-27 上午11:26:46
 */
public class MemberRelationServiceImpl implements MemberRelationService {

	private Log log = LogFactory.getFactory().getLog("MemberRelationServiceImpl");
	private MemberRelationDAO memberRelationDAO;
	private OperatorServiceFacade operatorServiceFacade;
	private EnterpriseBaseService enterpriseBaseService; 
	
	/**
	 * @param memberRelationDAO the memberRelationDAO to set
	 */
	public void setMemberRelationDAO(MemberRelationDAO memberRelationDAO) {
		this.memberRelationDAO = memberRelationDAO;
	}
	
	

	/**
	 * @param operatorServiceFacade the operatorServiceFacade to set
	 */
	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}



	/**
	 * @param enterpriseBaseService the enterpriseBaseService to set
	 */
	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}



	/* (non-Javadoc)
	 * @see com.pay.base.service.member.MemberRelationService#createMemberRelation(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public Long createMemberRelation(Long currMemberCode, Long usteelId,
			String usteelName, Long fatherMemberCode) {
		
		MemberRelationDto mrDto = new MemberRelationDto();
		{
			EnterpriseBase base = enterpriseBaseService.findByMemberCode(currMemberCode);
			Operator currOperate = operatorServiceFacade.getAdminOperator(currMemberCode);
			log.info("当前的membercode:"+currMemberCode);
			Long sonMemberCode = base.getMemberCode();
			Long sonMerchantCode = base.getMerchantCode();
			Long operatorId = currOperate.getOperatorId();
			mrDto.setSonMemberCode(sonMemberCode);
			mrDto.setSonMerchantCode(sonMerchantCode);
			mrDto.setSonUsteelId(Long.valueOf(usteelId));
			mrDto.setSonUsteelName(usteelName);
			mrDto.setSonOperatorId(operatorId);
			mrDto.setSonZhName(base.getZhName());
			mrDto.setSonEnName(base.getEnName());
		}
		{
			EnterpriseBase baseFather = enterpriseBaseService.findByMemberCode(fatherMemberCode);
			Operator fatherOperate = operatorServiceFacade.getAdminOperator(baseFather.getMemberCode());
			Long fachthOperatorId = fatherOperate.getOperatorId();
			log.info("父的membercode:"+fatherMemberCode);
			mrDto.setFatherMemberCode(fatherMemberCode);
			mrDto.setFatherOperatorId(fachthOperatorId);
		}
		
		Date date = new Date();
		mrDto.setCreateDate(date);
		mrDto.setUpdateDate(date);
		//设置上一级的membercode
		return (Long) memberRelationDAO.create(mrDto);
	}
	
	public MemberRelationDto getMemberRelationBySonMemberCode(Long sonMemberCode){
		return memberRelationDAO.getMemberRelationBySonMemberCode(sonMemberCode);
	}


	/* (non-Javadoc)
	 * @see com.pay.base.service.member.MemberRelationService#isFatherAndSon(java.lang.Long, java.lang.Long)
	*/
	@Override
	public boolean isFatherAndSon(Long sonMemberCode, Long fatherMemberCode) {
		return memberRelationDAO.isFatherAndSon(sonMemberCode, fatherMemberCode);
	}



	/* (non-Javadoc)
	 * @see com.pay.base.service.member.MemberRelationService#isExistsSonEnName(java.lang.String)
	 */
	@Override
	public boolean isExistsSonEnName(String enName) {
		MemberRelationDto dto = new MemberRelationDto();
		dto.setSonEnName(enName);
		List list  = memberRelationDAO.findBySelective(dto);
		return  ! CollectionUtils.isEmpty(list);
	}



	/* (non-Javadoc)
	 * @see com.pay.base.service.member.MemberRelationService#isExistsSonZhName(java.lang.String)
	 */
	@Override
	public boolean isExistsSonZhName(String zhName) {
		MemberRelationDto dto = new MemberRelationDto();
		dto.setSonZhName(zhName);
		List list  = memberRelationDAO.findBySelective(dto);
		return  ! CollectionUtils.isEmpty(list);
	}
	

	/* (non-Javadoc)
	 * @see com.pay.base.service.member.MemberRelationService#isExistsOriginCode(java.lang.Long)
	 */
	@Override
	public boolean isExistsOriginCode(Long originCode) {
		EnterpriseBase base = enterpriseBaseService.findByMemberCode(originCode);
		return base != null;
	}
	
}
