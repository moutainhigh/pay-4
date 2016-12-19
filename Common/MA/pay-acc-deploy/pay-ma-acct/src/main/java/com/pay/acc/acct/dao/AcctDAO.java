/**
 * 
 */
package com.pay.acc.acct.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pay.acc.acct.dto.MemberAcctDto;
import com.pay.acc.acct.model.Acct;
import com.pay.acc.acct.model.AcctWithdrawFee;
import com.pay.acc.acct.model.PseudoAcct;
import com.pay.acc.acct.model.VouchAcct;
import com.pay.acc.balancelog.dto.FrozenAmountDto;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Administrator
 *
 */
public interface AcctDAO extends BaseDAO<Acct> {
	
	/**
	 * 根据账户号查询  
	 * @param params
	 * @return
	 */
	public Map<String,Object> queryAcctBycurCodeAndmenberCode(Map<String, Object> params);
	/**
	 * 根据账号查询账户信息
	 * 
	 * @param acctCode
	 * @return
	 */
	public Acct queryAcctWithAcctCode(String acctCode);

	/**
	 * 根据membecode查询账户信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public List<Acct> queryAcctByMemberCode(Long memberCode);

	/**
	 * 更新余额，不做负数限制
	 * 
	 * @param acctCode
	 * @param amount
	 * @param debitAmount
	 * @param creditAmount
	 * @return
	 */
	public boolean updateBalance(final String acctCode, Long amount,
			Long debitAmount, Long creditAmount);

	/**
	 * 更新余额，做负数限制
	 * 
	 * @param acctCode
	 * @param amount
	 * @param debitAmount
	 * @param creditAmount
	 * @return
	 */
	public boolean updateBalanceCheckNegative(final String acctCode,
			Long amount, Long debitAmount, Long creditAmount);

	/**
	 * 根据账号更新的借方发生额
	 * 
	 * @param amount
	 *            金额
	 * @param acctCode
	 *            账户
	 * @return
	 */
	public boolean updateAcctCreditBalanceWithAcctCode(Long amount,
			Long creditAmount, String acctCode);

	/**
	 * 更新借方余额，借方余额方向为减
	 * 
	 * @param amount
	 * @param creditAmount
	 * @param acctCode
	 * @return
	 */
	public boolean updateCreditReduceBalanceWithAcctCode(Long amount,
			Long creditAmount, String acctCode);

	/**
	 * 根据账号更新的贷方发生额
	 * 
	 * @param amount
	 *            金额
	 * @param acctCode
	 *            账户
	 * @return
	 */
	public boolean updateAcctDebitBalanceWithAcctCode(Long amount,
			Long debitAmount, String acctCode);

	/**
	 * 更新贷方余额,贷方余额方向为加
	 * 
	 * @param amount
	 * @param creditAmount
	 * @param acctCode
	 * @return
	 */
	public boolean updateDebitAddBalanceWithAcctCode(Long amount,
			Long debitAmount, String acctCode);

	/**
	 * 根据version更新的借方发生额
	 * 
	 * @param amount
	 *            金额
	 * @param acctCode
	 *            账户
	 * @return
	 */
	public Integer updateAcctCreditBalanceWithVer(Long amount,
			Long creditAmount, String acctCode, Long ver);

	/**
	 * 根据version更新的贷方发生额
	 * 
	 * @param amount
	 *            金额
	 * @param acctCode
	 *            账户
	 * @return
	 */
	public Integer updateAcctDebitBalanceWithVer(Long amount, Long debitAmount,
			String acctCode, Long ver);

	/**
	 * 更新用户状态
	 * 
	 * @param acctCode
	 *            账户号
	 * @param status
	 *            状态
	 * @return 更新是否成功 true成功
	 */
	public boolean updateAcctStatusWithAcctCode(String acctCode, Integer status);

	/**
	 * 更新贷方余额,余额可为负数（账户属性可透支为1）
	 * 
	 * @param acctCode
	 *            账户号
	 * @param status
	 *            状态
	 * @return 更新是否成功 true成功
	 * @return
	 */
	public boolean updateDebitNegativeBalanceWithAcctCode(Long amount,
			Long debitAmount, String acctCode);

	/**
	 * 更新借方余额，余额可为负数 （账户属性可透支为1）
	 * 
	 * @param amount
	 * @param debitAmount
	 * @param acctCode
	 * @return
	 */
	public boolean updateCreditNegativeBalanceWithAcctCode(Long amount,
			Long creditAmount, String acctCode);

	/**
	 * 更新冻结金额
	 * 
	 * @param acctCode
	 * @param frozenAmount
	 * @return
	 */
	public boolean updateFrozenAmountWithAcctCode(String acctCode,
			Long frozenAmount);

	/**
	 * 更新冻结金额
	 * 
	 * @param acctCode
	 * @param frozenAmount
	 * @return
	 */
	public boolean updateUnFrozenAmountWithAcctCode(String acctCode,
			Long frozenAmount, String orderId, Long dealCode);

	/**
	 * 批量增加更新冻结金额
	 * 
	 * @param frozenAmountDtos
	 * @return 冻结个数
	 * @author ddr
	 */
	public int batchAddFrozen(List<FrozenAmountDto> frozenAmountDtos)
			throws SQLException;

	/**
	 * 批量解冻冻结金额的钱
	 * 
	 * @param frozenAmountDtos
	 * @return 解冻个数
	 * @author ddr
	 */
	public int batchUnFrozen(List<UnFrozenAmountDto> unFrozenAmountDtos)
			throws SQLException;

	/**
	 * 批量增加更新冻结金额
	 * 
	 * @param frozenAmountDto
	 * @return 冻结该条的id号
	 * @author ddr
	 */
	public boolean addFrozenAmount(FrozenAmountDto frozenAmountDto);

	/**
	 * 查询通过poss冻结了多少金额单位是厘
	 * 
	 * @param memberCode
	 * @return poss后台人工冻结的金额 单位是厘
	 */
	public BigDecimal getHasFrozenAmountOfPoss(long memberCode);

	/**
	 * 批量增加更新冻结金额
	 * 
	 * @param unFrozenAmountDto
	 * @return 冻结该条的id号
	 * @author ddr
	 */
	public boolean unFrozenAmount(UnFrozenAmountDto unFrozenAmountDto);

	/**
	 * 根据MemberAcctDto去查询用户-账户信息
	 * 
	 * @param memberAcctDto
	 * @return List<MemberAcctDto>
	 */
	public List<MemberAcctDto> queryMemberAcctDto(MemberAcctDto memberAcctDto);
	
	public List<Acct> queryAcctsByMemberCode(Long memberCode,
			Integer acctTypeId);
	
	/**
	 * 更新提现账户手续费
	 * @param acctWithdrawFees
	 * @return
	 */
	public boolean updateAcctWithdrawFee(List<AcctWithdrawFee> acctWithdrawFees) ;
	
	/**
	 * 根据会员号查询设有提现手续费的账户列表
	 * @param memberCode
	 * @return
	 */
	public List<Acct> queryAcctWithFeeByMemberCode(Long memberCode) ;
	
	/**
	 * 根据会员号和提现币种查询对应的提现账户信息
	 * @param memberCode
	 * @param currencyCode
	 * @return
	 */
	public Acct queryAcctWithFeeByMemberCodeAndCurrencyCode(Long memberCode, String currencyCode) ;
	
	/**
	 * 根据会员号和货币代码查询账户信息
	 * @param memberCode
	 * @param currencyCode
	 * @return
	 */
	List<PseudoAcct> queryAcctByMemberCodeAndCurrency(Long memberCode, String currency) ;
	/**
	 * 根据会员号和货币代码查询账户信息冻结金额
	 * @param memberCode
	 * @param currencyCode
	 * @return
	 */
	BigDecimal queryFrozenAmountByMemberCodeAndCurrency(Long memberCode, String currency,String acctType,String dealCode) ;

	/**
	 * 根据会员号和货币代码查询账户信息解冻金额
	 * @param memberCode
	 * @param currencyCode
	 * @return
	 */
	BigDecimal queryUnFrozenAmountByMemberCodeAndCurrency(Long memberCode,String currency,String acctType,String dealCode);
	/**
	 * 更新冻结信息
	 * @param frozenAmountDto
	 * @return
	 * 2016年7月16日   mmzhang     add
	 */
	Boolean updateFrozenAmountByMemberCodeAndCurrency(Long memberCode, String currency,String acctType,BigDecimal frozenAmount);
	
	/**
	 * 查询保证金或基本户帐号
	 * @param acctType
	 * @return
	 * 2016年7月18日   mmzhang     add
	 */
	public List<Acct> queryAcctCodeForAcctType(String acctType);

	List<Acct> queryFrozenAmountByacctCode(List<Acct> accts, String acctType);
	
	public List<VouchAcct> queryBasicRepairAmount();
	public List<VouchAcct> queryGuaranteeRepairAmount();

	List<Acct> queryUnFrozenAmountByacctCode(List<Acct> accts, String dealCode);
	Boolean updateFrozenAmount(String acctCode,BigDecimal frozenAmount);

	Integer updateFrozenAmountBatch(List<Acct> paramList);
	/***
	 * 查询拒付罚款可配置的币种  delin
	 * @param valueOf
	 * @return
	 */
	public List<Map> queryAcctAttribCurCode(Long memberCode);
}

	
