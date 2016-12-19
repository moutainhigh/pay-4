package com.pay.poss.platformmembers.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.platformmembers.dao.PlatformMembersDao;
import com.pay.poss.platformmembers.dto.PlatformMembersDTO;
import com.pay.poss.platformmembers.model.PlatformMembers;
import com.pay.poss.platformmembers.service.PlatformMembersService;

public class PlatformMembersServiceImpl implements PlatformMembersService {
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private PlatformMembersDao platformMembersDao;
	private static final Log LOG = LogFactory.getLog(PlatformMembersServiceImpl.class);

	private PlatformMembers geterateMemberRelation(PlatformMembersDTO dto){
		PlatformMembers platformMembers=new PlatformMembers();
		if(dto.getFatherMemberCode() != null && !dto.getFatherMemberCode().toString().trim().equals("")){
			platformMembers.setFatherMemberCode(new Long(dto.getFatherMemberCode()));
		}
		
		platformMembers.setSonMemberCode(dto.getSonMemberCode());
//		platformMembers.setCreateDate(dto.getCreateDate());
		platformMembers.setFatherOperatorId(dto.getFatherOperatorId());
		platformMembers.setSonOperatorId(dto.getSonOperatorId());
		platformMembers.setStatus(dto.getStatus());
//		platformMembers.setUpdateDate(dto.getUpdateDate());
		platformMembers.setId(dto.getId());
		return platformMembers;
	}
	
	private PlatformMembersDTO geterateMemberRelationDTO(PlatformMembers model){
		PlatformMembersDTO platformMembers=new PlatformMembersDTO();
		platformMembers.setFatherMemberCode(new Long(model.getFatherMemberCode()));
		platformMembers.setSonMemberCode(model.getSonMemberCode());
		platformMembers.setCreateDate(model.getCreateDate() != null ? simpleDateFormat.format(model.getCreateDate()) : "");
		platformMembers.setFatherOperatorId(model.getFatherOperatorId());
		platformMembers.setSonOperatorId(model.getSonOperatorId());
		platformMembers.setStatus(model.getStatus());
		platformMembers.setUpdateDate(model.getUpdateDate() != null ? simpleDateFormat.format(model.getUpdateDate()) : "");
		platformMembers.setFatherZhName(model.getFatherZhName());
		platformMembers.setSonZhName(model.getSonZhName());
		platformMembers.setSonOperator(model.getSonOperator());
		platformMembers.setFatherOperator(model.getFatherOperator());
		platformMembers.setId(model.getId());
		return platformMembers;
	}

	@Override
	public void insertPlatformMembers(List<PlatformMembersDTO> dataDtoList) {
		for(PlatformMembersDTO dto:dataDtoList){
			PlatformMembers relation = geterateMemberRelation(dto);
			try{
				platformMembersDao.create(relation);	
			}catch(Exception e){
				LOG.error("关联会员号"+dto.getSonMemberCode()+"失败 "+e);
			}
		}
	}

	public Map<String, String> updatePlatformMembers(String id, String srcStatus, String targetStatus, Page page) {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put("id", id);
		criteria.put("status", srcStatus);
		List<PlatformMembersDTO> platformMembers = findByCriteria(criteria, page);
		if(platformMembers == null || platformMembers.isEmpty()) {
			result.put("resultCode", "9999");
			result.put("resultMsg", "审核状态已改变，请刷新页面查看");
			return result;
		}
		
		PlatformMembers pltmembers = new PlatformMembers();
		pltmembers.setId(Long.valueOf(id));
		pltmembers.setStatus(Integer.valueOf(targetStatus));
		
		boolean success = this.platformMembersDao.update(pltmembers);
		if(success) {
			result.put("resultCode", "0000");
		} else {
			result.put("resultCode", "9999");
			result.put("resultMsg", "更新审核状态失败");
		}
		return result;		
	}
	
	public List<PlatformMembersDTO> findByCriteria(
			Map<String, String> criteria, Page  page) {
		List<PlatformMembers> platformMembers = platformMembersDao.findByCriteria(criteria, page);
		
		List<PlatformMembersDTO> platformMembersDTOs = new ArrayList<PlatformMembersDTO>();
		for(PlatformMembers platformMember : platformMembers){
			PlatformMembersDTO platformDTO = geterateMemberRelationDTO(platformMember);
			platformMembersDTOs.add(platformDTO);
		}
		return platformMembersDTOs;
	}
	
	public List<PlatformMembersDTO> findByCriteria(Map<String, String> criteria) {
		List<PlatformMembers> platformMembers = platformMembersDao.findByCriteria(criteria);
		
		List<PlatformMembersDTO> platformMembersDTOs = new ArrayList<PlatformMembersDTO>();
		for(PlatformMembers platformMember : platformMembers){
			PlatformMembersDTO platformDTO = geterateMemberRelationDTO(platformMember);
			platformMembersDTOs.add(platformDTO);
		}
		return platformMembersDTOs;
	}

	public PlatformMembersDao getPlatformMembersDao() {
		return platformMembersDao;
	}

	public void setPlatformMembersDao(PlatformMembersDao platformMembersDao) {
		this.platformMembersDao = platformMembersDao;
	}
	
}
