package com.pay.base.dao.acct;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pay.base.model.Acct;
import com.pay.base.model.AcctInfo;

public interface AcctDAO {

    public abstract Long createAcct(Acct acct);

    public abstract Long createAcctAttrib(Long memberCode);

    public String getAccCodeById(Long acctAttrId);
    
    /**
     * 根据会员号获取账户信息
     * @author wangzhi
     * @param memberCode
     * @param acctTypeId
     * @return
     */
    public List<Acct> getByMemberCode(long memberCode);
    
    /**
     * 根据会员号获取账户信息
     * @author peiyu.yang
     * @param memberCode
     * @return
     */
    public List<AcctInfo> getAcctInfoByMemberCode(long memberCode);
    
    /**
     * 根据账户编号获取账户信息
     * @author peiyu.yang
     * @param acctCode
     * @return
     */
    public AcctInfo getAcctInfoByAcctCode(String acctCode);
    
    public Acct getByMemberCode(long memberCode,Integer acctType);
    
    /**
     * 根据父会员获取子交易商账户信息
     * @param fatherMemberCode
     * @param acctTypeId
     * @param sonMemberName
     * @return
     */
    public List<Acct> getSonAcctByParentMember(long fatherMemberCode, int acctTypeId,String sonMemberName);
    
    /**
     * 根据会员号和账户类型更新会员账户状态
     *
     * @param memberCode
     * @param acctTypeId
     * @param status
     * @return
     */
    public int updateAcctStatus(String acctCode, int status);
    
    public void createMemberProduct(Long memberCode) throws Exception;
    
    @SuppressWarnings("rawtypes")
	public abstract Class getModelClass();
    
    /**
     * 多条数据插入提交
     * @author Sunny Ying
     * @param acctList
     * @throws DataAccessException
     * @return void
     */
    public abstract void batchCreate(final List<Acct> acctList) throws DataAccessException;
    /**
     * 根据会员号和货币类型获取账户信息
     * @param memberCode
     * @param currency
     * @return
     */
    List<Acct> queryAcctByMemberCodeAndCurrency(Long memberCode, String currency) ;

}