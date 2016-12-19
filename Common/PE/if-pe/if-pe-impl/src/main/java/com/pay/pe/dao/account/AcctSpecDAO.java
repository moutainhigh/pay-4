package com.pay.pe.dao.account;


import java.util.List;

import com.pay.pe.model.AccountSpecification;


public interface AcctSpecDAO {
   
    AccountSpecification add(AccountSpecification acctSpec);

    
//    void remove(String acctcode) throws MatchingAccountIsExistException;

    
    AccountSpecification change(AccountSpecification acctSpec);

    
//    List getList(Map acctSpecfilter);

    
    AccountSpecification get(String acctcode);

    List getList(String parentAcctCode);
    
    void remove(String acctCode);

    
  
    
}
