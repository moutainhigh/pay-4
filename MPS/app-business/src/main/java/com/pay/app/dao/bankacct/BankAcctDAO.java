/**
 * 
 */
package com.pay.app.dao.bankacct;

import java.util.List;

import com.pay.app.model.BankAcct;
import com.pay.base.model.LiquidateInfo;


/**
 * @author Sunny.Ying
 *
 */
public interface BankAcctDAO {

	/**
	 * 添加一条 银行卡绑定 信息
	 * @param bankAcct
	 * @return int
	 */
	public void addBankAcct(BankAcct bankAcct);
	
	/**
	 * 查询 会员已经	绑定了多少张银行卡
	 * @param memberCode 会员 code
	 * @return int
	 */
	public int getUserBankAcctNum(String memberCode);
	
	/**
	 * 查询银行卡是否已经存在
	 * @author Sunny Ying
	 * @description 查询银行卡是否已经存在
	 * @param  bankAcctId
	 * @throw null
	 * @return int
	 */
	public int checkBankCardExits(String bankAcctId);
	
	/**
	 * 根据会员代号查询所有绑定的银行卡信息
	 * @author Sunny Ying
	 * @description TODO
	 * @param memberCode
	 * @throw null
	 * @return List<BankAcct>
	 */
	public List<BankAcct> getBankAcctByCode(Long memberCode);
	
	
	//查询所有银行卡信息
	public List<LiquidateInfo>queryBankAcctByMemberCode(Long memberCode);
	
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
	 * 
	 * @author Sunny Ying
	 * @param id
	 * @throw null
	 * @return int
	 */
	public int editBankAcct(BankAcct bankAcct);
	
	/**
	 * 查询 BankAcct
	 * @author Sunny Ying
	 * @param bankAcct
	 * @throw null
	 * @return BankAcct
	 */
	public BankAcct queryBankAcct(BankAcct bankAcct);
	
	/**
	 * 根据用户号和id号来更新状态
	 * @param memberCode
	 * @param liquidateId 可以为空，只更新条件为memberCode的
	 * @param status
	 * @return 更新的个数
	 */
	public int updateStatus(String memberCode,Long liquidateId,Integer status);
	/**
	 * 根据用户号来更新状态
	 * @param memberCode
	 * @param status
	 * @return 更新的个数
	 */
	public int updateStatus(String memberCode,Integer status);
	
	
}
