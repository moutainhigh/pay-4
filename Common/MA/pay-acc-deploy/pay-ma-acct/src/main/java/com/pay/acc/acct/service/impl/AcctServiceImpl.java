/**
 * 
 */
package com.pay.acc.acct.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.acct.dao.AcctDAO;
import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.dto.MemberAcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.model.Acct;
import com.pay.acc.acct.model.AcctWithdrawFee;
import com.pay.acc.acct.model.PseudoAcct;
import com.pay.acc.acct.model.VouchAcct;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.balancelog.dto.FrozenAmountDto;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;
import com.pay.acc.balancelog.service.FrozenOperatorLogService;
import com.pay.pe.helper.CRDRType;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */
public class AcctServiceImpl implements AcctService {

	private final Log log = LogFactory.getLog(this.getClass());
	private AcctDAO acctDAO;
	private FrozenOperatorLogService frozenOperatorLogService;

	public void setFrozenOperatorLogService(
			final FrozenOperatorLogService frozenOperatorLogService) {
		this.frozenOperatorLogService = frozenOperatorLogService;
	}

	@Override
	public Long createAcct(final AcctDto acctDto) {

		return (Long) acctDAO.create(BeanConvertUtil.convert(Acct.class,
				acctDto));
	}

	@Override
	public AcctDto queryAcctWithAcctCode(final String acctCode)
			throws AcctServiceException, AcctServiceUnkownException {
		if (acctCode == null || !StringUtils.hasText(acctCode)) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		Acct acct = null;
		try {
			acct = this.acctDAO.queryAcctWithAcctCode(acctCode);
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);

		}
		return BeanConvertUtil.convert(AcctDto.class, acct);

	}

	@Override
	public AcctDto queryAcctByAcctCode(final String acctCode)
			throws AcctServiceException, AcctServiceUnkownException {
		if (acctCode == null || !StringUtils.hasText(acctCode)) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		Acct acct = null;
		try {
			acct = acctDAO.findObjectByTemplate(
					"queryAccountByAcctCode", acctCode);
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);
		}
		return BeanConvertUtil.convert(AcctDto.class, acct);
	}

	/**
	 * 更新余额，不做负数限制
	 * 
	 * @param acctCode
	 * @param amount
	 * @param debitAmount
	 * @param creditAmount
	 * @return
	 */
	@Override
	public boolean updateBalance(final String acctCode, final Long amount,
			final Long debitAmount, final Long creditAmount) {
		return acctDAO.updateBalance(acctCode, amount, debitAmount,
				creditAmount);
	}

	/**
	 * 更新余额，做负数限制
	 * 
	 * @param acctCode
	 * @param amount
	 * @param debitAmount
	 * @param creditAmount
	 * @return
	 */
	@Override
	public boolean updateBalanceCheckNegative(final String acctCode,
			final Long amount, final Long debitAmount, final Long creditAmount) {
		return acctDAO.updateBalanceCheckNegative(acctCode, amount,
				debitAmount, creditAmount);
	}

	@Override
	public boolean updateAcctCreditBalanceWithAcctCode(final Long amount,
			final Long creditAmount, final String acctCode, final boolean isReduce)
			throws AcctServiceException, AcctServiceUnkownException {
		if (acctCode == null || !StringUtils.hasText(acctCode)
				|| amount == null || creditAmount == null) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		try {
			if (isReduce) {
				return this.acctDAO.updateCreditReduceBalanceWithAcctCode(
						amount, creditAmount, acctCode);
			} else {
				return this.acctDAO.updateAcctCreditBalanceWithAcctCode(amount,
						creditAmount, acctCode);
			}
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);
		}

	}

	@Override
	public boolean updateNegativeBalanceWithAcctCode(final Long amount,
			final Long debitAmount, final String acctCode, final Integer crdr)
			throws AcctServiceException {
		if (acctCode == null || !StringUtils.hasText(acctCode)
				|| amount == null || debitAmount == null) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		try {
			if (CRDRType.DEBIT.getValue() == crdr) {// 借x
				return this.acctDAO.updateCreditNegativeBalanceWithAcctCode(
						amount, debitAmount, acctCode);
			} else {// 贷
				return this.acctDAO.updateDebitNegativeBalanceWithAcctCode(
						amount, debitAmount, acctCode);
			}
		} catch (Exception e) {
			throw new AcctServiceException(e);
		}
	}

	@Override
	public boolean updateFrozenAmountWithAcctCode(final String acctCode,
			final Long frozenAmount, final String orderId, final Integer dealCode)
			throws AcctServiceException {
		if (acctCode == null || !StringUtils.hasText(acctCode)
				|| frozenAmount == null) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		try {
			// if(null==orderId){
			// return this.acctDAO.updateFrozenAmountWithAcctCode(acctCode,
			// frozenAmount);
			// }else{
			// return this.acctDAO.updateUnFrozenAmountWithAcctCode(acctCode,
			// frozenAmount, orderId, dealCode);
			// }
			return this.acctDAO.updateFrozenAmountWithAcctCode(acctCode,
					frozenAmount);

		} catch (Exception e) {
			throw new AcctServiceException(e);
		}
	}

	@Override
	public boolean updateAcctDebitBalanceWithAcctCode(final Long amount,
			final Long debitAmount, final String acctCode, final boolean isAdd)
			throws AcctServiceException, AcctServiceUnkownException {

		if (acctCode == null || !StringUtils.hasText(acctCode)
				|| amount == null || debitAmount == null) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		try {
			if (isAdd) {
				return this.acctDAO.updateDebitAddBalanceWithAcctCode(amount,
						debitAmount, acctCode);
			} else {
				return this.acctDAO.updateAcctDebitBalanceWithAcctCode(amount,
						debitAmount, acctCode);
			}
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);
		}

	}

	@Override
	public AcctDto queryAcctByMemberCodeAndAcctTypeId(final Long memberCode,
			final Integer acctTypeId) throws AcctServiceException,
			AcctServiceUnkownException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			throw new AcctServiceException("memberCode输入的参数" + memberCode
					+ "，请核对");
		} else if (null == acctTypeId || acctTypeId.intValue() <= 0) {
			throw new AcctServiceException("acctTypeId输入的参数" + acctTypeId
					+ "，请核对");
		}

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberCode", memberCode);
		paraMap.put("acctType", acctTypeId);

		Acct acct = null;
		try {
			acct = acctDAO.findObjectByTemplate(
					"queryAcctByMemberCodeAndAcctTypeId", paraMap);
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);
		}
		return BeanConvertUtil.convert(AcctDto.class, acct);
	}

	public AcctDAO getAcctDAO() {
		return acctDAO;
	}

	public void setAcctDAO(final AcctDAO acctDAO) {
		this.acctDAO = acctDAO;
	}

	@Override
	public boolean updateAcctFreezeStatusWithAcctCode(final String acctCode,
			final Integer status) throws AcctServiceException,
			AcctServiceUnkownException {
		if (!StringUtils.hasText(acctCode)) {
			throw new AcctServiceException("请正确输入您的参数");
		}
		try {
			return this.acctDAO.updateAcctStatusWithAcctCode(acctCode, status);
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);
		}

	}

	@Override
	public Integer updateAcctCreditBalanceWithVer(final Long amount,
			final Long creditAmount, final String acctCode, final Long ver)
			throws AcctServiceException, AcctServiceUnkownException {
		if (acctCode == null || !StringUtils.hasText(acctCode)
				|| amount == null || creditAmount == null) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		try {
			return this.acctDAO.updateAcctCreditBalanceWithVer(amount,
					creditAmount, acctCode, ver);
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);
		}

	}

	@Override
	public Integer updateAcctDebitBalanceWithVer(final Long amount, final Long debitAmount,
			final String acctCode, final Long ver) throws AcctServiceException,
			AcctServiceUnkownException {
		if (acctCode == null || !StringUtils.hasText(acctCode)
				|| amount == null || debitAmount == null) {
			throw new AcctServiceException("输入的参数" + acctCode + "，请核对");
		}
		try {
			return this.acctDAO.updateAcctDebitBalanceWithVer(amount,
					debitAmount, acctCode, ver);
		} catch (Exception e) {
			throw new AcctServiceUnkownException(e);
		}
	}

	@Override
	public List<AcctDto> queryAcctByMemberCode(final Long memberCode)
			throws AcctServiceException, AcctServiceUnkownException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			throw new AcctServiceException("memberCode输入的参数" + memberCode
					+ "，请核对");
		}

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberCode", memberCode);

		List<Acct> accts = acctDAO.queryAcctByMemberCode(memberCode);

		List<AcctDto> r = null;

		if (accts != null) {
			r = new ArrayList<AcctDto>(accts.size());
		}

		for (Acct acct : accts) {
			r.add(BeanConvertUtil.convert(AcctDto.class, acct));
		}

		return r;
	}

	@Override
	public boolean batchAddFrozenAmount(final List<FrozenAmountDto> frozenAmountDtos) {
		int len = frozenAmountDtos.size();
		log.info("batchAddFrozenAmount...批量冻结开始..");
		int updateCount = 0;
		for (FrozenAmountDto faDto : frozenAmountDtos) {
			boolean isFrozen = addFrozenAmount(faDto);
			if (isFrozen) {
				updateCount++;
			} else {
				log.info("冻结" + faDto + "时中断，第" + (updateCount + 1)
						+ ".....break");
				break;
			}
		}

		log.info("batchAddFrozenAmount...批量冻结结束..");
		log.info("开始冻结条数：" + len + "\t" + "冻结条数：" + updateCount + "将返回"
				+ (updateCount == len));
		return updateCount == len;
	}

	@Override
	public boolean batchUnFrozenAmount(
			final List<UnFrozenAmountDto> unFrozenAmountDtos) {
		int len = unFrozenAmountDtos.size();
		log.info("batchAddFrozenAmount...批量解冻结开始..");
		int updateCount = 0;
		for (UnFrozenAmountDto ufDto : unFrozenAmountDtos) {
			boolean isFrozen = unFrozenAmount(ufDto);
			if (isFrozen) {
				updateCount++;
			} else {
				log.info("批量解冻" + ufDto + "时中断，第" + (updateCount + 1)
						+ ".....break");
				break;
			}
		}

		log.info("batchAddFrozenAmount...批量解冻结束..");
		log.info("开始冻结条数：" + len + "\t" + "冻结条数：" + updateCount + "将返回"
				+ (updateCount == len));
		return updateCount == len;
	}

	@Override
	public boolean addFrozenAmount(final FrozenAmountDto faDto) {

		if (faDto.getFrozenAmount().longValue() < (0.01 * 1000)) {
			String error = "解冻金额" + faDto.getFrozenAmount() + "必须大于等于1分钱(10)";
			log.error(error);
			throw new IllegalArgumentException(error);
		}
		Acct acct = this.acctDAO.queryAcctWithAcctCode(faDto.getAcctCode());
		if (!faDto.getMemberCode().equals(acct.getMemberCode().longValue())) {
			String error = "memberCode不匹配,参数异常,账户" + faDto.getAcctCode()
					+ "的mebmerCode是" + acct.getMemberCode();
			error += "，实际参数是" + faDto.getMemberCode();
			log.error(error);
			throw new IllegalArgumentException(error);
		}
		// 如果账户上的可用余额少于当前需要冻结的金额
		if (acct.getBalance() < faDto.getFrozenAmount().longValue()) {
			String error = ("可用金额小于实际要冻结的金额,实际" + acct.getBalance() + "要冻结的信息：" + faDto);
			log.error(error);
			throw new IllegalArgumentException(error);
		}
		boolean isFrozen = acctDAO.addFrozenAmount(faDto);
		long id = frozenOperatorLogService.addFrozenLog(faDto,
				BigDecimal.valueOf(acct.getBalance()));
		if (id <= 0) {// 如果新增日志失败直接跳出
			log.error("增加冻结日志信息失败" + faDto);
			return false;
		}
		log.info("成功增加冻结日志，id:" + id + ",冻结信息：" + faDto);
		return isFrozen;
	}

	@Override
	public boolean unFrozenAmount(final UnFrozenAmountDto ufDto) {

		if (ufDto.getUnFrozenAmount().longValue() < (0.01 * 1000)) {
			String error = "解冻金额" + ufDto.getUnFrozenAmount() + "必须大于等于1分钱(10)";
			log.error(error);
			throw new IllegalArgumentException(error);
		}
		Acct acct = this.acctDAO.queryAcctWithAcctCode(ufDto.getAcctCode());
		if (!ufDto.getMemberCode().equals(acct.getMemberCode().longValue())) {
			String error = "memberCode不匹配,参数异常,账户" + ufDto.getAcctCode()
					+ "的mebmerCode是" + acct.getMemberCode();
			error += "，实际参数是" + ufDto.getMemberCode();
			log.error(error);
			throw new IllegalArgumentException(error);
		}
		// 判断当前的冻结金额是否大于要解冻的金额（扣掉后台冻结）
		BigDecimal possFrozen = acctDAO.getHasFrozenAmountOfPoss(ufDto
				.getMemberCode());

		if (ufDto.getUnFrozenAmount().longValue() > (acct.getFrozeAmount()
				.longValue() - possFrozen.longValue())) {
			String error = ("解冻金额大于当前已冻结金额,当前已冻结总金额" + acct.getFrozeAmount()
					+ "-poss人工冻结" + possFrozen + "="
					+ (acct.getFrozeAmount() - possFrozen.longValue())
					+ ">将解冻结金额" + ufDto.getUnFrozenAmount() + "，解冻金额信息：" + ufDto);
			log.error(error);
			throw new IllegalArgumentException(error);
		}
		boolean isUnFrozen = acctDAO.unFrozenAmount(ufDto);
		long id = frozenOperatorLogService.addUnFrozenLog(ufDto,
				BigDecimal.valueOf(acct.getBalance()));
		if (id <= 0) {// 如果新增日志失败直接跳出
			log.error("增加冻结日志信息失败" + ufDto);
			return false;
		}
		log.info("成功增加解冻日志，id:" + id + ",解冻信息：" + ufDto);
		return isUnFrozen;
	}

	@Override
	public MemberAcctDto queryMemberAcctByMemberCode(final long memberCode) {
		log.info("通过会员号查询账户信息......开始");
		MemberAcctDto maDto = new MemberAcctDto();
		maDto.setMemberCode(memberCode);
		maDto.setAcctType(10);
		List<MemberAcctDto> list = this.getAcctDAO().queryMemberAcctDto(maDto);
		log.info("通过会员号查询账户信息......结果：" + list.size() + "条");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	@Override
	public MemberAcctDto queryMemberAcctByloginName(final String loginName) {
		log.info("通过登录名" + loginName + "查询账户信息......开始");
		MemberAcctDto maDto = new MemberAcctDto();
		maDto.setLoginName(loginName);
		maDto.setAcctType(10);
		List<MemberAcctDto> list = this.getAcctDAO().queryMemberAcctDto(maDto);
		log.info("通过登录名" + loginName + "查询账户信息......结果：" + list.size() + "条");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public BigDecimal getPossFrozenAmount(final long memberCode) {
		return acctDAO.getHasFrozenAmountOfPoss(memberCode);

	}

	@Override
	public List<AcctDto> queryAcctByMemberCode(final Long memberCode,
			final Integer acctTypeId) throws AcctServiceException,
			AcctServiceUnkownException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			throw new AcctServiceException("memberCode输入的参数" + memberCode
					+ "，请核对");
		}

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberCode", memberCode);

		List<Acct> accts = acctDAO.queryAcctsByMemberCode(memberCode,acctTypeId);

		List<AcctDto> r = null;

		if (accts != null) {
			r = new ArrayList<AcctDto>(accts.size());
		}

		for (Acct acct : accts) {
			r.add(BeanConvertUtil.convert(AcctDto.class, acct));
		}

		return r;
	}

	@Override
	public boolean updateAcctWithdrawFee(final List<AcctWithdrawFee> acctWithdrawFees) {
		return this.acctDAO.updateAcctWithdrawFee(acctWithdrawFees) ;
	}

	@Override
	public List<Acct> queryAcctWithFeeByMemberCode(final Long memberCode) {
		return this.acctDAO.queryAcctWithFeeByMemberCode(memberCode) ;
	}

	@Override
	public Acct queryAcctWithFeeByMemberCodeAndCurrencyCode(final Long memberCode,
			final String currencyCode) {
		return this.acctDAO.queryAcctWithFeeByMemberCodeAndCurrencyCode(memberCode, currencyCode) ;
	}

	/* (non-Javadoc)
	 * @see com.pay.acc.acct.service.AcctService#queryAcctByMemberCodeAndCurrency(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<PseudoAcct> queryAcctByMemberCodeAndCurrency(final Long memberCode,
			final String currency) {
		return this.acctDAO.queryAcctByMemberCodeAndCurrency(memberCode, currency) ;
	}
	@Override
	public BigDecimal queryFrozenAmountByMemberCodeAndCurrency(final Long memberCode,
			final String currency,final String acctType,final String dealCode) {
		return this.acctDAO.queryFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,dealCode) ;
	}
	@Override
	public BigDecimal queryUnFrozenAmountByMemberCodeAndCurrency(final Long memberCode,
			final String currency,final String acctType,final String dealCode) {
		return this.acctDAO.queryUnFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,dealCode) ;
	}

	@Override
	public boolean updateFrozenAmountByMemberCodeAndCurrency(
			Long memberCode, String currency,String acctType,BigDecimal frozenAmount) {
		return this.acctDAO.updateFrozenAmountByMemberCodeAndCurrency(memberCode, currency,acctType,frozenAmount) ;
	}
	@Override
	public List<Acct> queryAcctCodeForAcctType(String acctType) {
		return this.acctDAO.queryAcctCodeForAcctType(acctType);
	}
	@Override
	public List<Acct> queryFrozenAmountByacctCode(List<Acct> accts, String dealCode)
	{
		return this.acctDAO.queryFrozenAmountByacctCode(accts,dealCode);
	}
	
	@Override
	public List<VouchAcct> queryBasicRepairAmount()
	{
		return this.acctDAO.queryBasicRepairAmount();
	}
	@Override
	public List<VouchAcct> queryGuaranteeRepairAmount()
	{
		return this.acctDAO.queryGuaranteeRepairAmount();
	}

	@Override
	public List<Acct> queryUnFrozenAmountByacctCode(List<Acct> accts, String dealCode)
	{
		return this.acctDAO.queryUnFrozenAmountByacctCode(accts,dealCode);
	}
	
	@Override
	public boolean updateFrozenAmount(
			final String acctCode,final BigDecimal frozenAmount) {
		return this.acctDAO.updateFrozenAmount(acctCode,frozenAmount) ;
	}
	@Override
	public Integer updateFrozenAmountBatch(List<Acct> paramList){
		return this.acctDAO.updateFrozenAmountBatch(paramList) ;
	}

/***
	 * 查询拒付罚款可配置的币种  delin
	 * @param valueOf
	 * @return
	 */
	@Override
	public List<Map> queryAcctAttribCurCode(Long memberCode) {
		return this.acctDAO.queryAcctAttribCurCode(memberCode);
	}

	@Override
	public Map<String, Object> queryAcctBycurCodeAndmenberCode(Map<String, Object> params) {
		return acctDAO.queryAcctBycurCodeAndmenberCode(params);
	}
}