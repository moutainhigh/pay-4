package com.pay.poss.personmanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.personmanager.dao.BankAcctDAO;
import com.pay.poss.personmanager.dao.IndividualDAO;
import com.pay.poss.personmanager.dao.PersonMangerDAO;
import com.pay.poss.personmanager.dao.UserLogDAO;
import com.pay.poss.personmanager.dto.BankAcctDto;
import com.pay.poss.personmanager.dto.IndividualDto;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.dto.UserLogDto;
import com.pay.poss.personmanager.service.IndividualService;

public class IndividualServiceImpl implements IndividualService {
	private UserLogDAO possUserLogDAO;
	private IndividualDAO possIndividualDAO;
	private BankAcctDAO possBankAcctDAO;
	private PersonMangerDAO postPersonManagerDao;

	@Override
	public Map<String, Object> queryIndividualByMemberCode(Long memberCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		IndividualDto dto = this.selectIndividualDtoByMemberCode(memberCode);
		resultMap.put("individualDto", dto);
		BankAcctDto bankAcctDto = new BankAcctDto();
		bankAcctDto.setMemberCode(memberCode);
		List<BankAcctDto> list = possBankAcctDAO.selectBankAcctDtoList(bankAcctDto);
		resultMap.put("bankAcctList", list);
		PersonMemberSearchDto postDao = postPersonManagerDao.selectPersonMember(memberCode);
		resultMap.put("memberSearchDto", postDao);
		return resultMap;
	}

	@Override
	public IndividualDto selectIndividualDtoByMemberCode(Long memberCode) {
		IndividualDto dto = this.possIndividualDAO.selectIndividualDtoByMemberCode(memberCode);
		if (null != dto) {// 取最近一条登陆的日志,将登陆IP，日期加入对象
			UserLogDto userLogDto = new UserLogDto();
			userLogDto.setPageEndRow(1);
			userLogDto.setPageStartRow(0);
			userLogDto.setMemberCode(memberCode);
			List<UserLogDto> list = this.possUserLogDAO.selectUserLogList(userLogDto);
			if (null != list && list.size() > 0) {
				UserLogDto dtoTmp = list.get(0);
				dto.setLastLoginDate(dtoTmp.getCreateDate());
				dto.setLastLoginIp(dtoTmp.getLoginIp());
			}
		}
		return dto;
	}

	public void setPossBankAcctDAO(BankAcctDAO possBankAcctDAO) {
		this.possBankAcctDAO = possBankAcctDAO;
	}

	public void setPossUserLogDAO(UserLogDAO possUserLogDAO) {
		this.possUserLogDAO = possUserLogDAO;
	}

	public void setPossIndividualDAO(IndividualDAO possIndividualDAO) {
		this.possIndividualDAO = possIndividualDAO;
	}

	public void setPostPersonManagerDao(PersonMangerDAO postPersonManagerDao) {
		this.postPersonManagerDao = postPersonManagerDao;
	}

}
