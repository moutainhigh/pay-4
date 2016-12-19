package com.pay.fundout.withdraw.service.autorisk.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fundout.withdraw.dao.autorisk.AutoRiskInterfaceDAO;
import com.pay.fundout.withdraw.dao.autorisk.LoginFailLogDAO;
import com.pay.fundout.withdraw.dto.autorisk.IpDto;
import com.pay.fundout.withdraw.dto.autorisk.MemBerDto;
import com.pay.fundout.withdraw.dto.autorisk.TransSumDto;
import com.pay.fundout.withdraw.dto.autorisk.TransWebsiteDto;
import com.pay.fundout.withdraw.service.autorisk.AutoRiskInterfaceService;
import com.pay.inf.dao.Page;

public class AutoRiskInterfaceServiceImpl implements AutoRiskInterfaceService {

	private AutoRiskInterfaceDAO autoRiskInterfaceDAO;
	
	private LoginFailLogDAO loginFailLogDAO;
	
	public void setAutoRiskInterfaceDAO(AutoRiskInterfaceDAO autoRiskInterfaceDAO){
		this.autoRiskInterfaceDAO = autoRiskInterfaceDAO;
	}
	
	public void setLoginFailLogDAO(LoginFailLogDAO loginFailLogDAO){
		this.loginFailLogDAO = loginFailLogDAO;
	}
	
	@Override
	public boolean isIpConnected(Long memberCode, Date beginDate, Date endDate) {
		return this.autoRiskInterfaceDAO.isIpConnected(memberCode, beginDate, endDate);
	}

	@Override
	public BigDecimal queryFundInAmount(Long memberCode, Date beginDate, Date endDate) {
		return this.autoRiskInterfaceDAO.queryFundInAmount(memberCode, beginDate, endDate);
	}

	@Override
	public BigDecimal queryFundInMaxAmount(Long memberCode, Date beginDate, Date endDate) {
		return this.autoRiskInterfaceDAO.queryFundInMaxAmount(memberCode, beginDate, endDate);
	}

	@Override
	public int queryFundInNums(Long memberCode, Date beginDate, Date endDate) {
		return this.autoRiskInterfaceDAO.queryFundInNums(memberCode, beginDate, endDate);
	}

	@Override
	public int queryFundOutTimes(Long memberCode) {
		return this.autoRiskInterfaceDAO.queryFundOutTimes(memberCode);
	}
	
	@Override
	public int queryBatchFundOutTimes(Long memberCode, Long parentOrderid) {
		return this.autoRiskInterfaceDAO.queryBatchFundOutTimes(memberCode, parentOrderid);
	}

	@Override
	public int queryLoginFailNum(Long memberCode, Date beginDate, Date endDate) {
		return this.loginFailLogDAO.queryLoginFailNum(memberCode, beginDate, endDate);
	}
	
	@Override
	public MemBerDto queryIndividualInfo(Long memberCode) {
		return this.autoRiskInterfaceDAO.queryIndividualInfo(memberCode);
	}
	
	@Override
	public MemBerDto queryEnterpriseInfo(Long memberCode) {
		return this.autoRiskInterfaceDAO.queryEnterpriseInfo(memberCode);
	}
	
	@Override
	public Page<IpDto> queryIpInfo(Page<IpDto> page, Long memberCode) {
		Page<IpDto> resultPage = this.autoRiskInterfaceDAO.queryIpInfo(page, memberCode);
		if (resultPage != null && resultPage.getResult() != null && resultPage.getResult().size() > 0) {
			MemBerDto member;
			for (IpDto dto : resultPage.getResult()) {
				if(MemberTypeEnum.INDIVIDUL.getCode() == dto.getMemberType()){
					member = this.queryIndividualInfo(dto.getMemberCode());
				}else{
					member = this.queryEnterpriseInfo(dto.getMemberCode());
				}
				if (member != null) {
					dto.setMemberName(member.getMemberName());
				}
			}
		}
		return resultPage;
	}
	
	@Override
	public List<TransSumDto> queryTransSumInfo(Long memberCode) {
		return this.autoRiskInterfaceDAO.queryTransSumInfo(memberCode);
	}
	
	@Override
	public List<TransWebsiteDto> queryTransWebsiteInfo(Long memberCode) {
		return this.autoRiskInterfaceDAO.queryTransWebsiteInfo(memberCode);
	}

}
