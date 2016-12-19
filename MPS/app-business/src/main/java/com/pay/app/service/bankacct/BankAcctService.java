/**
 * 
 */
package com.pay.app.service.bankacct;

import java.util.List;

import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.app.model.BankAcct;
import com.pay.base.model.LiquidateInfo;

/**
 * @author sunny.ying
 *
 */
public interface BankAcctService {
	
	/**
	 * 添加/更新一条 银行卡绑定 信息
	 * @param bankAcct
	 * @return null
	 */
	public Integer addOrUpdateBankAcct(BankAcct bankAcct,String memberCode);
	
	/**
	 * 查询 会员已经	绑定了多少张银行卡
	 * @param memberCode 会员 code
	 * @return int
	 */
	public int getUserBankAcctNum(String memberCode);
	
	/**
	 * 根据会员代号查询所有绑定的银行卡信息
	 * @author Sunny Ying
	 * 
	 * @param memberCode
	 * @throw null
	 * @return List<BankAcct>
	 */
	
	
	public List<BankAcct> getBankAcctByCode(String memberCode);
	
	
	//查询所有银行卡的信息
	public List<LiquidateInfo> queryBankAcctByMemberCode(String memberCode);
	/**
	 * 查询 BankAcct
	 * @author Sunny Ying 
	 * @param bankAcct
	 * @throw null
	 * @return BankAcct
	 */
	public BankAcct queryBankAcct(BankAcct bankAcct);
	
	/**
	 * 更新bankacct
	 * @author Sunny Ying
	 * @param id
	 * @throw null
	 * @return int
	 */
	public int editBankAcct(BankAcct bankAcct,String memberCode);
	
	/**
	 * 通过id得到 bankAcct
	 * @author Sunny Ying
	 * @param id
	 * @throw null
	 * @return BankAcct
	 */
	public BankAcct getBankAcctById(Long id);
	
	/**
	 * 根据id删除数据
	 * @author Sunny Ying
	 * @param id
	 * @throw null
	 * @return int
	 */
	public int removeBankAcctById(Long id);
	
	/**
	 * 查询会员的实名认证信息
	 * @author Sunny Ying
	 * @param memberCode
	 * @throw null
	 * @return int
	 */
	public boolean getMemberVerifyState(String memberCode);
	
	/**
	 * 开户行名称列表 (FO)
	 * @author Sunny Ying
	 * @param bankName
	 * @param provinceName
	 * @param cityName
	 * @param keyWord
	 * @throw null
	 * @return List<SearchResultInfoDTO>
	 */
	public List<SearchResultInfoDTO> getBankAcctFO(String bankName,String provinceName,String cityName,String keyWord);
	
	/**
	 * 判断 卡片是否可以修改
	 * @param memberCode 要修改卡的用户
	 * @param id 要修改的id号
	 * @param cardNo 将要改为的卡号
	 * @return 0为可以修改，1为有重复的，不能再修改
	 * @author 戴德荣
	 */
	public int  validateEditCard(String memberCode,Long id,String cardNo);
	
	/**
	 * 设置默认的账户卡号
	 * @param memberCode
	 * @param id 
	 * @return true
	 */
	public boolean setDefaultAcctRnTx(String memberCode,Long id);
	
	/**
	 * 取消默认的账户卡号
	 * @param memberCode
	 * @param id 
	 * @return true
	 */
	public boolean setNotDefaultAcctRnTx(String memberCode,Long id);
}
