package com.pay.pe.service.account.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.pe.dao.account.AcctSpecDAO;
import com.pay.pe.dto.AcctSpecDTO;
import com.pay.pe.dto.AcctSpecDTOUtil;
import com.pay.pe.exception.account.AccountValidationBalaceException;
import com.pay.pe.exception.coa.OrgTypeMatchException;
import com.pay.pe.exception.coa.SummarizeAccountNotFoundException;
import com.pay.pe.exception.coa.SummarizeAccountNotNullException;
import com.pay.pe.exception.coa.SummarizeAccountRequiredException;
import com.pay.pe.helper.AcctLevel;
import com.pay.pe.helper.OrgType;
import com.pay.pe.model.AccountSpecification;
import com.pay.pe.service.account.AcctSpecService;
import com.pay.util.StringUtil;

/**
 * 
 * 
 *         <p>
 *         AcctSpecServiceImpl.java
 *         </p>
 * 此类的各项验证判断在并发环境下时要加上同步
 */
public class AcctSpecServiceImpl implements AcctSpecService {

	/**
	 * 日志. 
	 */
	private Log logger = LogFactory.getLog(getClass());

    /**
     * AcctSpectDAO的实例,用于数据读取与持久操做
     */
    private AcctSpecDAO acctSpecDAO;
    /**
     * AccountDAO的实例,用于Acount数据读取与持久操做
     */
//    private AccountDAO accountDAO;
    
    /**
     * errorcode.
     */
    private String errorCode;
   

    /*
     * (non-Javadoc)
     * 
     * 
     */
    public void changeAcctSpec(final AcctSpecDTO acctSpecDTO)
            throws AccountValidationBalaceException, OrgTypeMatchException,
            SummarizeAccountNotFoundException,
            SummarizeAccountNotNullException,
            SummarizeAccountRequiredException {
        Assert.notNull(acctSpecDTO, "acctSpecDTO must be not null");
        //校验汇总账户是否填写,除总账外,明细账和明细账子目均须填写
        validateSummarizeAccount(acctSpecDTO);
        //校验账户余额是否为0
        validateAccount(acctSpecDTO.getAcctCode());
        //校验机构类型
        validateOrgType(acctSpecDTO);
        //校验机构代码
        validateOrgCode(acctSpecDTO);
        acctSpecDAO.change(AcctSpecDTOUtil.convertDtoToAcctSpec(acctSpecDTO));

    }

    /*
     * (non-Javadoc)
     * 
     * 
     */
    public AcctSpecDTO requestModificationAcctSpec(final String acctcode)
            throws AccountValidationBalaceException{
        //校验账户余额
        validateAccount(acctcode);
        return getAcctSpec(acctcode);

    }

    /*
     * (non-Javadoc)
     * 
     * 
     */
    public AcctSpecDTO getAcctSpec(final String acctcode){
        AcctSpecDTO getAcctSpecDTO = AcctSpecDTOUtil
                .convertAcctSpecToDto(acctSpecDAO.get(acctcode));
        if (getAcctSpecDTO == null) {
            return null;
        }
//        System.out.println("------------"+getAcctSpecDTO.getAcctCode());
//        Account account = accountDAO.getAccountByCode(getAcctSpecDTO.getAcctCode());
//        //设置标识是否生成账户
//        if (null == account) {
//            getAcctSpecDTO.setAcctStatus("0");
//        } else {
//            getAcctSpecDTO.setAcctStatus("1");
//        }
        return getAcctSpecDTO;

    }

    /*
     * (non-Javadoc)
     * 
     * 
     */
//    public List getAcctSpecsByFilter(final AcctSpecDTO acctSpecDTO){
//        Assert.notNull(acctSpecDTO, "acctSpecDTO must be not null");
//        //组织查询filter
//        Map acctSpecfilter = new HashMap();
//        acctSpecfilter.put("acctcode", acctSpecDTO.getAcctcode());
//        acctSpecfilter.put("acctname", acctSpecDTO.getAcctname());
//        acctSpecfilter.put("accttype", acctSpecDTO.getAccttype());
//        acctSpecfilter.put("acctlevel", acctSpecDTO.getAcctlevel());
//        acctSpecfilter.put("acctStatus", acctSpecDTO.getAcctStatus());
//        //传递分页对象
//        acctSpecfilter.put("page",acctSpecDTO.getPagination());
//        List acctSpecDTOList = AcctSpecDTOUtil.convertAcctSpecToDto(acctSpecDAO
//                .getList(acctSpecfilter));
//        //查询不包括是否生成acct的的所有的subject
//        if (StringUtil.isNull(acctSpecDTO.getAcctStatus())) {
//            for (int i = 0; i < acctSpecDTOList.size(); i++) {
//                AcctSpecDTO getAcctSpecDTO = (AcctSpecDTO) acctSpecDTOList
//                    .get(i);
//                Account account = accountDAO.getAccountByCode(getAcctSpecDTO
//                    .getAcctcode());
//                    if (account == null) {
//                        getAcctSpecDTO.setAcctStatus("0");                       
//                    }
//                    if (account != null) {
//                        getAcctSpecDTO.setAcctStatus("1");                       
//                    }
//            }
//            return acctSpecDTOList;
//        }
//        // 数据库查询固定帐户状态
//        for (int i = 0; i < acctSpecDTOList.size(); i++) {
//            AcctSpecDTO getAcctSpecDTO = (AcctSpecDTO) acctSpecDTOList.get(i);            
//            getAcctSpecDTO.setAcctStatus(StringUtil.isNull(acctSpecDTO
//                .getAcctStatus()) ? null : acctSpecDTO.getAcctStatus());
//        } 
//        return acctSpecDTOList;
//    }

    /*
     * (non-Javadoc)
     * 
     * 
     */
    public boolean isExist(final String acctcodeOfAcctSpec) {
        //判断COA在插入时是否存在,存在返回true;
        if (null == acctSpecDAO.get(acctcodeOfAcctSpec)) {
            return false;
        } else {
            return true;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * 
     */
    public void validateAccount(final String acctcode)
            throws AccountValidationBalaceException {
        //跟据账户代码获取账户
//        Account account = accountDAO.getAccountByCode(acctcode);
//        if (null!=account) {
//            //校验账户余额是否为0,不为0 throw AccountValidationBalaceException
//            if (account.getBalance().longValue() != 0
//                    || account.getCreditBalance().longValue() != 0
//                    || account.getDebitBalance().longValue() != 0) {
//                throw new AccountValidationBalaceException();
//            }
//        }
    }

    /*
     * (non-Javadoc)
     * 
     * 
     */
    public void validateOrgType(final AcctSpecDTO acctSpecDTO)
            throws OrgTypeMatchException, SummarizeAccountNotFoundException {

        Assert.notNull(acctSpecDAO, "acctSpecDAO must be not null");
        Assert.notNull(acctSpecDTO, "acctSpecDTO must be not null");
        Assert.notNull(acctSpecDTO.getAcctCode(),
                "acctSpecDTO's acctcode must be not null");
//        Helper h = new Helper();
        // 判断是否是总账
        if (null!=acctSpecDTO.getAcctLevel() 
                && acctSpecDTO.getAcctLevel().intValue() != AcctLevel.GENERAL
                        .getValue()) {
            AccountSpecification ledger = acctSpecDAO.get(acctSpecDTO
                    .getSummarizeTo());
            if (ledger == null) {
                throw new SummarizeAccountNotFoundException();
            }
            // 子目是否由机构构成,1为true
            if (null!=ledger.getChildBeorg() 
                    && null!=acctSpecDTO.getOrgCode() 
                    && null!=ledger.getChildBeorg()
                    && ledger.getChildBeorg().intValue() == 1
                    && ledger.getChildOrgType().intValue() != acctSpecDTO.getOrgType().intValue()) {
                throw new OrgTypeMatchException();

            }
        }
    }

  
//    /**
//     * @return Returns the accountDAO.
//     */
//    public AccountDAO getAccountDAO() {
//        return accountDAO;
//    }
//
//    /**
//     * @param accountDAO
//     *            The accountDAO to set.
//     */
//    public void setAccountDAO(final AccountDAO accountDAO) {
//        this.accountDAO = accountDAO;
//    }

    /**
     * @return Returns the acctSpecDAO.
     */
    public AcctSpecDAO getAcctSpecDAO() {
        return acctSpecDAO;
    }

    /**
     * @param acctSpecDAO
     *            The acctSpecDAO to set.
     */
    public void setAcctSpecDAO(final AcctSpecDAO acctSpecDAO) {
        this.acctSpecDAO = acctSpecDAO;
    }

    /**
     * 校验汇总账户是否填写,除总账外,明细账和明细账子目均须填写
     * 
     * @param acctSpecDTO
     * @throws SummarizeAccountNotNullException   科目级别为非总账类型,汇总账户必须为空
     * @throws SummarizeAccountRequiredException  科目级别为总账类型,汇总账户必须填写
     */
    private void validateSummarizeAccount(final AcctSpecDTO acctSpecDTO)
            throws SummarizeAccountNotNullException,
            SummarizeAccountRequiredException {
        //科目级别为非总账类型,汇总账户必须为空    
        if (AcctLevel.GENERAL.getValue() ==acctSpecDTO.getAcctLevel().intValue() 
                && !StringUtil.isNull(acctSpecDTO.getSummarizeTo())) {
            throw new SummarizeAccountNotNullException();
        }
        //科目级别为总账类型,汇总账户必须填写
        if (AcctLevel.GENERAL.getValue() != acctSpecDTO.getAcctLevel().intValue()
                && StringUtil.isNull(acctSpecDTO.getSummarizeTo())) {
            throw new SummarizeAccountRequiredException();
        }
    }

    /**
     * 校验组织代码是否填写,银行必须填写
     * 
     * @param acctSpecDTO     
     * @throws RequiredException
     */
    private void validateOrgCode(final AcctSpecDTO acctSpecDTO)
           {
         
        if (null==acctSpecDTO.getOrgCode()&&OrgType.BANK.getValue()== acctSpecDTO.getOrgType().intValue()
                ) {
//            throw new Exception("校验组织代码是否填写,银行必须填写");
        } 

    }

    public String getRemoveAcctSpecErrorCode() {        
        return this.errorCode;
    }

    public void setRemoveAcctSpecErrorCode(String code) {
        this.errorCode = String.valueOf(code);
    }
}
