/**
 * 
 */
package com.pay.acc.acct.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.dto.MemberAcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.model.Acct;
import com.pay.acc.acct.model.AcctWithdrawFee;
import com.pay.acc.acct.model.PseudoAcct;
import com.pay.acc.acct.model.VouchAcct;
import com.pay.acc.balancelog.dto.FrozenAmountDto;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;

/**
 * @author Administrator
 * 
 */
public interface AcctService {

	/**
	 * 
	 * @param acctDto
	 * @return
	 */
	public Long createAcct(AcctDto acctDto);

	/**
	 * 根据memberCode查询账户
	 * 
	 * @param memberCode
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public List<AcctDto> queryAcctByMemberCode(Long memberCode)
			throws AcctServiceException, AcctServiceUnkownException;
	
	/**
	 * 根据memberCode查询账户
	 * 
	 * @param memberCode
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public List<AcctDto> queryAcctByMemberCode(Long memberCode,Integer acctTypeId)
			throws AcctServiceException, AcctServiceUnkownException;
	
	
	

	/**
	 * 根据账号查询账户
	 * 
	 * @param acctCode
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public AcctDto queryAcctWithAcctCode(String acctCode)
			throws AcctServiceException, AcctServiceUnkownException;

	/**
	 * 根据会员号、账户类型 查询账户信息
	 * 
	 * @param memberCode
	 *            会员号
	 * @param acctTypeId
	 *            账户类型
	 * @return AcctDto 账户信息
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public AcctDto queryAcctByMemberCodeAndAcctTypeId(Long memberCode,
			Integer acctTypeId) throws AcctServiceException,
			AcctServiceUnkownException;
	
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
	 * 根据账号更新余额和借方发生额
	 * 
	 * @param amount
	 * @param acctCode
	 * @param isReduce
	 *            true 为减
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public boolean updateAcctCreditBalanceWithAcctCode(Long amount,
			Long creditAmount, String acctCode, boolean isReduce)
			throws AcctServiceException, AcctServiceUnkownException;

	/**
	 * 根据账号更新余额和贷方发生额
	 * 
	 * @param amount
	 * @param acctCode
	 * @param isAdd
	 *            true 为增加
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public boolean updateAcctDebitBalanceWithAcctCode(Long amount,
			Long debitAmount, String acctCode, boolean isAdd)
			throws AcctServiceException, AcctServiceUnkownException;

	/**
	 * 更新中间账户余额（余额可为负,账户属性可透支状态为1）
	 * 
	 * @param amount
	 *            金额
	 * @param debitAmount借贷发生额
	 * @param acctCode
	 *            账户号
	 * @param crdr借贷方向
	 * @return
	 * @throws AcctServiceException
	 */
	public boolean updateNegativeBalanceWithAcctCode(Long amount,
			Long debitAmount, String acctCode, Integer crdr)
			throws AcctServiceException;

	/**
	 * 更新冻结金额
	 * 
	 * @param acctCode
	 * @param frozenAmount
	 * @return
	 * @throws AcctServiceException
	 */
	public boolean updateFrozenAmountWithAcctCode(String acctCode,
			Long frozenAmount, String orderId, Integer dealCode)
			throws AcctServiceException;

	/**
	 * 根据版本号version更新余额和借方发生额
	 * 
	 * @param amount
	 * @param acctCode
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public Integer updateAcctCreditBalanceWithVer(Long amount,
			Long creditAmount, String acctCode, Long ver)
			throws AcctServiceException, AcctServiceUnkownException;

	/**
	 * 根据版本号更新余额和贷方发生额
	 * 
	 * @param amount
	 * @param acctCode
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public Integer updateAcctDebitBalanceWithVer(Long amount, Long debitAmount,
			String acctCode, Long ver) throws AcctServiceException,
			AcctServiceUnkownException;

	/**
	 * 查询账户
	 * 
	 * @param acctCode
	 * @return
	 */
	public AcctDto queryAcctByAcctCode(String acctCode)
			throws AcctServiceException, AcctServiceUnkownException;

	/**
	 * 冻结或者解冻账户
	 * 
	 * @param acctCode
	 * @param status
	 * @return
	 * @throws AcctServiceException
	 * @throws AcctServiceUnkownException
	 */
	public boolean updateAcctFreezeStatusWithAcctCode(String acctCode,
			Integer status) throws AcctServiceException,
			AcctServiceUnkownException;

	/**
	 * 批量冻结用户资金
	 * 
	 * @param frozenAmountDtos
	 *            详见 FrozenAmountDto
	 * @return true/false
	 * @author ddr
	 */
	public boolean batchAddFrozenAmount(List<FrozenAmountDto> frozenAmountDtos);

	/**
	 * 批量解冻用户资金
	 * 
	 * @param unFrozenAmountDtos
	 * @return true/false
	 */
	public boolean batchUnFrozenAmount(
			List<UnFrozenAmountDto> unFrozenAmountDtos);

	/**
	 * 单个更新冻结资金
	 * 
	 * @param frozenAmountDto
	 * @return true/false
	 */
	public boolean addFrozenAmount(FrozenAmountDto frozenAmountDto);
	
	/**
	 * 单个解冻冻结资金
	 * 
	 * @param unFrozenAmountDto
	 * @return true/false
	 */
	public boolean unFrozenAmount(UnFrozenAmountDto unFrozenAmountDto);

	/**
	 * 根据memberCode查询用户账户信息,暂时只查人民币账户
	 * 
	 * @param memberCode
	 * @return @see<code>com.pay.acc.acct.dto.MemberAcctDto</code>
	 * @author ddr 2012-6-12
	 */
	public MemberAcctDto queryMemberAcctByMemberCode(long memberCode);

	/**
	 * 根据登录名查询用户账户信息,暂时只查人民币账户
	 * 
	 * @param memberCode
	 * @return MemberAcctDto @see<code>com.pay.acc.acct.dto.MemberAcctDto</code>
	 * @author ddr 2012-6-12
	 */
	public MemberAcctDto queryMemberAcctByloginName(String loginName);

	/**
	 * 通过后台poss的冻结金额
	 * 
	 * @param memberCode
	 * @return 金额以分为单位
	 */
	public BigDecimal getPossFrozenAmount(long memberCode);
	
	/**
	 * 更新提现账户手续费
	 * @param acctWithdrawFees
	 * @return
	 */
	public boolean updateAcctWithdrawFee(List<AcctWithdrawFee> acctWithdrawFees) ;
	
	/**
	 * 根据会员号查询设有提现手续费的账户
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

	BigDecimal queryFrozenAmountByMemberCodeAndCurrency(Long memberCode,
			String currency,String acctType,String dealCode);

	BigDecimal queryUnFrozenAmountByMemberCodeAndCurrency(Long memberCode,
			String currency,String acctType,String dealCode); 
	/**
	 * 更新冻结资金
	 * 
	 * @param frozenAmountDto
	 * @return true/false
	 */
	public boolean updateFrozenAmountByMemberCodeAndCurrency(Long memberCode, String currency,String acctType,BigDecimal frozenAmount);
	
	/**
	 * 查询基本户或保证金额帐号
	 * @param acctType
	 * @return
	 * 2016年7月18日   mmzhang     add
	 */
	public List<Acct> queryAcctCodeForAcctType(String acctType);

	List<Acct> queryFrozenAmountByacctCode(List<Acct> accts, String dealCode);

	List<Acct> queryUnFrozenAmountByacctCode(List<Acct> accts, String dealCode);

	boolean updateFrozenAmount(String acctCode, BigDecimal frozenAmount);
	
	Integer updateFrozenAmountBatch(List<Acct> paramList);

/***
	 * 查询拒付罚款可配置的币种 
	 * @param valueOf
	 * @return
	 */
    List<Map> queryAcctAttribCurCode(Long memberCode);

List<VouchAcct> queryBasicRepairAmount();

List<VouchAcct> queryGuaranteeRepairAmount();

Map<String, Object> queryAcctBycurCodeAndmenberCode(Map<String, Object> params); 
}
