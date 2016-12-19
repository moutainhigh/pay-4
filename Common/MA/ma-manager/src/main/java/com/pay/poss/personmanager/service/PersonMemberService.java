package com.pay.poss.personmanager.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.personmanager.dto.AcctDto;
import com.pay.poss.personmanager.dto.PersonMemberInfoDto;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctAssociateDto;
import com.pay.poss.personmanager.dto.PersonalAcctBalanceDto;
import com.pay.poss.personmanager.dto.PersonalAcctInfoSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctTradeTotalDto;
import com.pay.poss.personmanager.dto.PersonalLoginHistoryDto;
import com.pay.poss.personmanager.formbean.PersonSearchFormBean;

public interface PersonMemberService {

	/**
	 * 个人会员登录历史记录查询
	 * 
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<PersonalAcctInfoSearchDto> selectPersonalLoginHistoryList(
			Map paramMap, Page<PersonalAcctInfoSearchDto> page);

	/**
	 * 会员账户信息查询
	 * 
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<PersonalAcctInfoSearchDto> selectPersonalAcctInfoList(Map paramMap,
			Page<PersonalAcctInfoSearchDto> page);

	/**
	 * 会员基本信息查询
	 * 
	 * @param memberCode
	 * @return
	 */
	List<PersonMemberInfoDto> selectPersonMemberInfoList(
			PersonSearchFormBean PersonSearchFormBean,
			Page<PersonMemberInfoDto> page);

	/**
	 * 个人会员查询
	 * 
	 * @param personSearchDto
	 * @param page
	 * @return
	 */
	List<PersonMemberSearchDto> selectPersonMemberList(
			PersonMemberSearchDto personSearchDto,
			Page<PersonMemberSearchDto> page);

	/**
	 * 根据会员号查询会员账户信息
	 * 
	 * @param memberCode
	 * @return
	 */
	AcctDto selectAcctDtoByMemberCode(Long memberCode);

	/**
	 * 根据会员号得到会员查询信息
	 * 
	 * @param memberCode
	 * @return
	 */
	PersonMemberSearchDto selectPersonMemberSearchDto(Long memberCode);

	/**
	 * 查询疑似IP关联账户list
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<PersonalAcctAssociateDto> queryPersonalAcctAssociatelist(
			PersonSearchFormBean personSearchFormBean,
			Page<PersonalAcctAssociateDto> page);

	/**
	 * 查询个人账户余额ist
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalAcctBalanceDto> queryPersonalAcctBalanceList(
			Map paramMap, Page<PersonalAcctBalanceDto> page);

	/**
	 * 查询个人账户交易统计金额
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalAcctTradeTotalDto> queryPeraonalAcctTradeTotal(
			Map paramMap);

	/**
	 * 更新会员账号可操作属性
	 * 
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Boolean updatePersonalMemberAcctOperate(Map paramMap);

	/**
	 * 更新会员可操作属性
	 * 
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Boolean updatePersonalMemberOperate(Map paramMap);

	/**
	 * 查询个人会员详细信息
	 * 
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PersonMemberInfoDto selectPersonlMemberInfoDetail(String memberCode);

	/**
	 * 个人会员最近一次登录信息
	 * 
	 * @param paramMemberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PersonalLoginHistoryDto selectMemberLoginIp(String paramMemberCode);

	/**
	 * 查询会员银行账户
	 * 
	 * @param paramMemberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PersonMemberInfoDto selectMemberBankAcct(String paramMemberCode);

	/**
	 * 查询个人会员信息
	 * 
	 * @param paramMemberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalAcctInfoSearchDto> queryPersonalMemberAcctMessage(
			Map paramMap, Page<PersonalAcctInfoSearchDto> page);

	/**
	 * 会员属性操作(冻结,解冻)
	 * 
	 * @param paramMap
	 * @return
	 */
	public void updateMemberStatusTrans(Map<String, Object> paramMap)
			throws PossException;

	/**
	 * 会员账户属性操作(冻结,解冻)
	 * 
	 * @param paramMap
	 * @return
	 */
	public Boolean updateAcctStatusTrans(Map paramMap) throws PossException;

	/**
	 * 查询个人身份证状态
	 * 
	 * @param paramMemberCode
	 * @return verifyStatus
	 */
	public PersonMemberInfoDto selectPersonlMemberIsPaperFile(
			String paramMemberCode);

	/**
	 * 查询该会员是否绑定口令卡
	 * 
	 * @param paramMap
	 * @return int
	 */
	public Integer countBindMatrixCard(String paramMemberCode);

	/**
	 * 由交易流水号和acctCode查询余额
	 * 
	 * @param paramMap
	 * @return String
	 */
	public String queryBalance(Map paramMap);

	/**
	 * 由memberCode查询会员t_member表信息
	 * 
	 * @param paramMap
	 * @return PersonMemberInfoDto
	 */
	public PersonMemberInfoDto selectMemberByMemberCode(Map paramMap);
}
