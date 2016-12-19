package com.pay.pe.dao.account.impl;


import java.util.List;

import org.springframework.util.Assert;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.account.AcctSpecDAO;
import com.pay.pe.model.AccountSpecification;
/**
 *
 *g
 *<p>AcctSpecificationDAOImpl.java</p>
 *
 */
public class AcctSpecDAOImpl extends IbatisDAOSupport implements AcctSpecDAO {

    
    public AccountSpecification add(final AccountSpecification acctSpec) {    
        Assert.notNull(acctSpec, "AccountSpecification object must be not null");
        return (AccountSpecification) this.saveObject(acctSpec);
    }

    
    public AccountSpecification change(final AccountSpecification acctSpec) {
        Assert.notNull(acctSpec, "AccountSpecification object must be not null");
         this.updateObject(acctSpec);
         return acctSpec ;
//        return (AccountSpecification) this.updateObject(AccountSpecification.class,
//                acctSpec, acctSpec.getAcctcode());
    }

    
//    public void remove(final String acctcode) throws MatchingAccountIsExistException {
//        Assert.notNull(acctcode, "acctcode must be not null");                
//        this.deleteObjectById(AccountSpecification.class, acctcode);        
//    }

  
//    public List getList(final Map acctSpecfilter) {  
//        Assert.notNull(acctSpecfilter , "AcctSpecfilter must be not null"); 
//        List < ExpressionType > list = new ArrayList < ExpressionType >();
//        List < Expression > expList = new LinkedList < Expression > ();
//        //模糊查询账户号码
//        if (!StringUtil.isNull(acctSpecfilter.get("acctcode"))) {
//            ExpressionType expressionType = ExpressionTypeFactory.like(
//                    "acctcode" , "%" + acctSpecfilter.get("acctcode") + "%");
//            list.add(expressionType);
//            expressionType.setFieldDecorator(ExpressionTypeFactory.TO_CHAR);
//        }
//        //模糊查询账户查询
//        if (!StringUtil.isNull(acctSpecfilter.get("acctname"))) {
//            ExpressionType expressionType = ExpressionTypeFactory.like(
//                    "acctname" , "%" + acctSpecfilter.get("acctname") + "%");
//            list.add(expressionType);
//            expressionType.setFieldDecorator(ExpressionTypeFactory.TO_CHAR);
//        }
//        //精确查询账户类型
//        if (!StringUtil.isNull(acctSpecfilter.get("accttype"))) {
//            ExpressionType expressionType = ExpressionTypeFactory
//                    .equal("accttype",acctSpecfilter.get("accttype") );
//            //ExpressionTypeFactory.i
//            list.add(expressionType);
//        }
//        //精确查找账户级别
//        if (!StringUtil.isNull(acctSpecfilter.get("acctlevel"))) {
//            ExpressionType expressionType = ExpressionTypeFactory
//                    .equal("acctlevel",acctSpecfilter.get("acctlevel") );
//            list.add(expressionType);
//        }
//        Expression exp = null;
//       // Expression exp = emp.exists(subQuery);
//        if (!StringUtil.isNull(acctSpecfilter.get("acctStatus"))) {
//            ExpressionBuilder proj = new ExpressionBuilder(Account.class);
//            ExpressionBuilder emp = new ExpressionBuilder(AccountSpecification.class);
//            ReportQuery subQuery = new ReportQuery(Account.class, proj);
//            subQuery.addAttribute("acctcode");
//            subQuery.setSelectionCriteria(proj.get("acctcode").equal(
//                emp.get("acctcode")));
//            if (String.valueOf(acctSpecfilter.get("acctStatus")).equals("0")) {
//				list.add(ExpressionTypeFactory.notExist(subQuery));
//			} else {
//				list.add(ExpressionTypeFactory.exists(subQuery));
//			}
//        }
//        //排序
//        //list.add
//        ExpressionType  et = ExpressionTypeFactory.orderByAsc("acctcode");
//        et.setFieldDecorator(ExpressionTypeFactory.TO_CHAR);
//        list.add(et);
//        Page page=(Page)acctSpecfilter.get("page");
//        return super.findObjectByExpPagination(AccountSpecification.class , list
//              .toArray(new ExpressionType[list.size()]),page);
//    }

    /* (non-Javadoc)
     * 
     */
    public AccountSpecification get(String acctcode) {
        Assert.notNull(acctcode , "acctcode must be not null");
//        return (AccountSpecification) super.findObjectById(AccountSpecification.class, acctcode);
        
        return (AccountSpecification) super.findObjectById(acctcode);
    }

   /*
    * （非 Javadoc）
    * 
    */
    public List getList(String parentAcctCode) {
    	Assert.notNull(parentAcctCode , "parentAcctCode must be not null");
    	AccountSpecification accountSpecification = new AccountSpecification();
    	accountSpecification.setSummarizeTo(parentAcctCode);
    	return super.findBySelective(accountSpecification);
    	
        
//        ExpressionType expressionType = ExpressionTypeFactory.equal("summarizeTo", parentAcctCode);
//        return  super.findObjectsByExpType(AccountSpecification.class, expressionType);
    }


	@Override
	public void remove(String acctCode) {
		// TODO Auto-generated method stub
		
	}

}
