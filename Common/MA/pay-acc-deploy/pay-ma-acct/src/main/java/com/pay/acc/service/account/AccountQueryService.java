/**
 * 
 */
package com.pay.acc.service.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.model.PseudoAcct;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.constantenum.AcctAttribEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.account.dto.MaResultDto;

public interface AccountQueryService {

	/**
	 * 查询余额 包括：可用余额，可提现余额，冻结余额
	 * 
	 * @param memberCode
	 *            账户号
	 * @param accountType
	 *            账户类型
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	BalancesDto doQueryBalancesNsTx(Long memberCode, Integer accountType)
			throws MaAccountQueryUntxException;

	/**
	 * 查询会员下所有账户余额信息
	 * 
	 * @param memberCode
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	List<BalancesDto> doQueryBalancesNsTx(Long memberCode)
			throws MaAccountQueryUntxException;

	/**
	 * 查询会员下所有基本结算账户信息
	 * 
	 * @param memberCode
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	List<BalancesDto> doQueryBalancesBasicNsTx(Long memberCode)
			throws MaAccountQueryUntxException;

	/**
	 * 查询账户属性
	 * 
	 * @param memberCode
	 *            会员号
	 * @param accountType
	 *            账户类型
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	AcctAttribDto doQueryAcctAttribNsTx(Long memberCode, Integer accountType)
			throws MaAccountQueryUntxException;

	/**
	 * @Title: countIsAllowAcctribByMemberCode
	 * @Description: 根据会员号，账户属性判断是否有权限
	 * @param @param memberCode
	 * @param @param acctEnum
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	boolean countIsAllowAcctribByMemberCode(Long memberCode,
			AcctAttribEnum acctEnum);

	/**
	 * 根据账户号，账户属性判断是否有权限
	 * 
	 * @param acctCode
	 *            会员账号
	 * @param AcctAttribEnum
	 *            账户属性中的属性
	 * @return boolean true:false
	 * @throws MaAccountQueryUntxException
	 */
	boolean isAllowAcctrib(String acctCode, AcctAttribEnum acctEnum);

	/**
	 * 查询会员交易额度
	 * 
	 * @param memberCode
	 *            ：Long 会员号
	 * @param acctType
	 *            ：Integer 账户类型
	 * @param amount
	 *            ：Long 额度（单位：人民币元）
	 * @param days
	 *            ：Integer 时段（天数）
	 * @return true:小于等于交易额度，false:大于交易额度
	 * @throws MaAccountQueryUntxException
	 */
	boolean doQueryBalanceEntryForTransAmountRntx(Long memberCode,
			Integer acctType, Long amount, Integer days)
			throws MaAccountQueryUntxException;

	/**
	 * 查询会员一段时间内借贷总额
	 * 
	 * @param memberCode
	 *            ：Long 会员号
	 * @param acctType
	 *            ：Integer 账户类型
	 * @param startAt
	 *            ：String 开始时间
	 * @param endAt
	 *            ：String 结束时间
	 * @return MaResultDto:resultBool:true/false
	 * @see com.pay.acc.service.account.dto.BorrowBalanceDto
	 * 
	 * 
	 */
	MaResultDto doQueryCrdrSumByMemberCodeRntx(Long memberCode,
			Integer accountType, String startAt, String endAt);

	/**
	 * 查询 时间距离本日最近的余额
	 * 
	 * @param memberCode
	 *            ：Long 会员号
	 * @param acctType
	 *            ：Integer 账户类型
	 * @return MaResultDto:resultBool:true/false
	 * @see com.pay.acc.service.account.dto.BorrowBalanceDto
	 * 
	 * 
	 */
	Long doQueryBalanceByEntryRntx(Long memberCode, Integer accountType);

	/**
	 * 根据某个时间点账户余额查询
	 * 
	 * @param acctCode账户号
	 * @param date日期
	 * @return
	 */
	Long selectBalanceByAcctCodeAndDate(String acctCode, Date date);

	/**
	 * @Title: queryBalanceByAcctCode
	 * @Description: 根据账户号查询账户余额
	 * @param @param acctCode
	 * @param @return 设定文件
	 * @return Long 返回类型
	 * @throws
	 */
	Long queryBalanceByAcctCode(String acctCode);

	/**
	 * 查询账户属性
	 * 
	 * @param memberCode
	 *            会员号
	 * @param accountType
	 *            账户类型
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	AcctAttribDto doQueryDefaultAcctAttribNsTx(Long memberCode);
	
	
	/**
	 * 查询账户属性
	 * 
	 * @param memberCode
	 *            会员号
	 * @param accountType
	 *            账户类型
	 * @return
	 * @throws Exception 
	 * @throws MaAccountQueryUntxException
	 */
	List<AcctDto> doQueryAcctDtoList(Long memberCode,Integer acctType) throws Exception;

	/**
	 * 根据币种查询商户结算账户
	 * 
	 * @param memberCode
	 * @param currencyCode
	 * @return
	 */
	List<AcctAttribDto> doQueryAcctAttribByMemberCodeAndCurrencyCodeNsTx(
			Long memberCode, String currencyCode);
	
	/**
	 * 查询账户余额
	 * @param memberCode
	 * @param currency
	 * @return
	 * @throws MaAccountQueryUntxException
	 */
	Map<String, PseudoAcct> doQueryAcctsNsTx(Long memberCode, String currency)
			throws MaAccountQueryUntxException;
	
	/**
	 * 
	 * @param memberCode
	 * @param currency
	 * @throws MaAccountQueryUntxException
	 * 2016年7月16日   mmzhang     add
	 */
	BigDecimal doUpdateAcctsNsTx(Long memberCode, String currency,String acctType)
			throws MaAccountQueryUntxException;
	
}
