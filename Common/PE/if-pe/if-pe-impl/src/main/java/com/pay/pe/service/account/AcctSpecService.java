
package com.pay.pe.service.account;

import com.pay.pe.dto.AcctSpecDTO;
import com.pay.pe.exception.account.AccountValidationBalaceException;
import com.pay.pe.exception.coa.OrgTypeMatchException;
import com.pay.pe.exception.coa.SummarizeAccountNotFoundException;


/**
 * 
 * 
 *         <p>
 *         AcctSpecService.java
 *         </p>
 * 
 */
public interface AcctSpecService {
    
    /**
     * Retrieve <code>AcctSpecDTO</code>s from the datastore by last
     * accttype(账户类型),acctlevel(科目级别),acctcode(账户类型),acctname(账户名称),acctstatus(账户生成状态)
     * 
     * @param acctSpecDTO
     * @return a <code>List</code> of matching <code>AcctSpecDTO</code>s     
     */
//    List getAcctSpecsByFilter(AcctSpecDTO acctSpecDTO);

    /**
     * 判断科目是否存在
     * 
     * @param acctcodeOfAcctSpec
     * @return boolean
     */
    boolean isExist(String acctcodeOfAcctSpec);

    /**
     * 校验机构类型是否匹配
     * 
     * @param acctSpecDTO
     * @throws OrgTypeMatchException
     *             如果机构类型不匹配
     * @throws SummarizeAccountNotFoundException
     *             如果汇总账户不存在
     * 
     */
    void validateOrgType(AcctSpecDTO acctSpecDTO)
            throws OrgTypeMatchException, SummarizeAccountNotFoundException;

    /**
     * Retrieve an <code>AcctSpecDTO</code> from the datastore by acctcode.
     * 
     * @param acctcode
     *            the acctcode to search for
     * @return the <code>AcctSpecDTO</code> if found
     * @throws AccountValidationBalaceException
     *             如果该账户的余额，借方余额，贷方余额不为0
     */
    AcctSpecDTO requestModificationAcctSpec(String acctcode)
            throws AccountValidationBalaceException;

    /**
     * Retrieve an <code>AcctSpecDTO</code> from the datastore by acctcode.
     * 
     * @param acctcode
     *            the acctcode to search for
     * @return the <code>AcctSpecDTO</code> if found
     */
    AcctSpecDTO getAcctSpec(String acctCode) ;

   

    /**
     * 校验该账户的余额，借方余额，贷方余额是否为0
     * 
     * @param acctcode
     *            the acctcode to valiation
     * @throws AccountValidationBalaceException
     *             如果该账户的余额，借方余额，贷方余额不为0
     */
    void validateAccount(String acctcode)
            throws AccountValidationBalaceException;



   
    /**
     * 得到删除失败的acctcode，该dao不能是singleton.
     * @return String
     */
    String getRemoveAcctSpecErrorCode();
    /**
     * 设置删除失败的acctcode，该dao不能是singleton.
     * @param code long
     */
    void setRemoveAcctSpecErrorCode(String code);
}
