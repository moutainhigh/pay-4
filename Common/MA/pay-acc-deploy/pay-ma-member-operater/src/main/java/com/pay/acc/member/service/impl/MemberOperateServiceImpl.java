package com.pay.acc.member.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dao.MemberOperateDAO;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.dto.OperatorDto;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;
import com.pay.acc.member.model.MemberOperate;
import com.pay.acc.member.model.Operator;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.util.BeanConvertUtil;

public class MemberOperateServiceImpl implements MemberOperateService {
	
	private Log log = LogFactory.getLog(MemberOperateServiceImpl.class);
	
	private MemberOperateDAO memberOperateDAO;

	public void setMemberOperateDAO(MemberOperateDAO memberOperateDAO) {
		this.memberOperateDAO = memberOperateDAO;
	}

	@Override
	public void insert(MemberOperateDto memberOperateDto) {
		MemberOperate memberOperate = BeanConvertUtil.convert(MemberOperate.class, memberOperateDto);
		this.memberOperateDAO.create(memberOperate);
	}

	@Override
	public void update(MemberOperateDto memberOperateDto) {
		MemberOperate memberOperate = BeanConvertUtil.convert(MemberOperate.class, memberOperateDto);
		this.memberOperateDAO.update(memberOperate);
	}

	@Override
	public MemberOperateDto queryMemberOperate(Long memberCode, int type) {
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("memberCode", memberCode);
		paramMap.put("type", type);
		List<MemberOperate> list = this.memberOperateDAO.queryMemberOperateByMap(paramMap);
		if (null != list && list.size() > 0) {
			MemberOperate memberOperate = list.get(0);
			MemberOperateDto memberOperateDto = BeanConvertUtil.convert(MemberOperateDto.class, memberOperate);
			return memberOperateDto;
		}
		return null;
	}

	@Override
	public MemberOperateDto queryMemberOperate(Long memberCode, int type, Integer accType) {
		Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("memberCode", memberCode);
		paramMap.put("type", type);
		paramMap.put("accType", accType);
		List<MemberOperate> list = this.memberOperateDAO.queryMemberOperateByMap(paramMap);
		if (null != list && list.size() > 0) {
			MemberOperate memberOperate = list.get(0);
			MemberOperateDto memberOperateDto = BeanConvertUtil.convert(MemberOperateDto.class, memberOperate);
			return memberOperateDto;
		}
		return null;
	}
	
	@Override
	public List<MemberOperateDto> queryAccountMemberOperate(Long memberCode, int type) {
		Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("memberCode", memberCode);
		paramMap.put("type", type);
		List<MemberOperate> list = this.memberOperateDAO.queryAccountMemberOperateByMap(paramMap);
		List<MemberOperateDto> memberOperateDtos = (List<MemberOperateDto>) BeanConvertUtil.convert(MemberOperateDto.class, list);
		return memberOperateDtos;
	}

	@Override
	public boolean cleanFailTime(MemberOperateDto memberOperate, int outTime) {
		try {
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Calendar calTmp = java.util.Calendar.getInstance();
			cal.setTime(memberOperate.getUpdateTime());
			cal.add(java.util.Calendar.MINUTE, outTime);
			// 当前时间大于更新时间+outTime，则对错误次数进行清0
			if (calTmp.after(cal)) {
				memberOperate.setFailTime(0L);
				this.update(memberOperate);
				return true;
			}
			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public MemberOperateDto createMemberOperate(Long memberCode, Long type, Integer accType) {
		MemberOperate memberOperate = new MemberOperate();
		memberOperate.setMemberCode(memberCode);
		memberOperate.setType(type);
		memberOperate.setAccType(accType);
		memberOperate.setFailTime(1L);
		MemberOperateDto memberOperateDto = BeanConvertUtil.convert(MemberOperateDto.class, memberOperate);
		return memberOperateDto;
	}
	
	@Override
	public MemberOperateDto getMemberOperate(long memberCode,MemberOperateTypeEnum mote){
		MemberOperateDto mo = queryMemberOperate(memberCode, mote.getCode());
		if(mo==null){
			//如果记录不存在，则创建操作记录
			log.info(mote.getMessage()+"---member_operate数据不存在，开始初始化数据。memberCode:"+memberCode);
			mo = new MemberOperateDto();
			mo.setMemberCode(memberCode);
			mo.setUpdateTime(new Date());
			mo.setCreateTime(new Date());
			mo.setFailTime(0L);
			mo.setType(new Long(MemberOperateTypeEnum.LOGIN_PWD.getCode()));
			insert(mo);
			log.info(mote.getMessage()+"---member_operate数据初始化结束。memberCode:"+memberCode);
		}
		return mo;
	}
	
	public OperatorDto queryOperatorByOperatorId(Long operatorId) {
		if (operatorId == null || operatorId <= 0)
			return null;
		
		Operator operator = this.memberOperateDAO.queryOperatorByOperatorId(operatorId);
		return BeanConvertUtil.convert(OperatorDto.class, operator);
	}
	
	public OperatorDto queryOperatorByMemberCode(Long memberCode,String identity){
		if(memberCode == null || identity == null){
			return null;
		}
		Operator operator = this.memberOperateDAO.queryOperatorByMemberCode(memberCode, identity);
		return BeanConvertUtil.convert(OperatorDto.class, operator);
	}

	
	public boolean isErrLoginWarning(Long memberCode){
		int errCount=this.memberOperateDAO.queryCountByErrLogin(memberCode);
		return (errCount>=3);
	}
}
