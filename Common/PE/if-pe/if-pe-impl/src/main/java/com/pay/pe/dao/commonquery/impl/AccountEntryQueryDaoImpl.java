
package com.pay.pe.dao.commonquery.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.commonquery.AccountEntryQueryDao;
import com.pay.pe.model.AccountEntry;


public class AccountEntryQueryDaoImpl extends IbatisDAOSupport implements
		AccountEntryQueryDao {


	public List getList(Map map) {
	    
//		//创建开始时间
//		Timestamp createdateFrom = (Timestamp)map.get("createdateFrom");
//		//创建结束时间
//		Timestamp createdateTo = (Timestamp)map.get("createdateTo");
//		//过账开始时间
//		Timestamp postdateFrom = (Timestamp)map.get("postdateFrom");
//		//过账结束时间
//		Timestamp postdateTo = (Timestamp)map.get("postdateTo");
//		//帐户代码
//		Long acctCode = (Long)map.get("acctCode");
//		//凭证号
//		Long vocherCode = (Long)map.get("vocherCode");
//		//状态
//		Integer acctStatus = (Integer)map.get("acctStatus");
//		//交易号
//		String dealCode = (String)map.get("dealCode");
//		Page page = (Page)map.get("page");
//		List<ToplinkCriteria> list = new ArrayList<ToplinkCriteria>();
//		//条件对象.
//		ToplinkCriteria c = null;
//		//帐户代码不为空
//		if(null != acctCode){
//			c = ToplinkCriteriaFactory.equal("acctcode",acctCode.toString());
//			list.add(c);
//			
//		}
//		//凭证号不为空
//		if(null != vocherCode){
//			c = ToplinkCriteriaFactory.equal("vouchercode",vocherCode);
//			list.add(c);
//		}
//		//创建时间不为空
//		if(null != createdateFrom){
//			c = ToplinkCriteriaFactory.greaterThanEqual("createdate", createdateFrom);
//			list.add(c);	
//		}
//		if(null != createdateTo){
//			c = ToplinkCriteriaFactory.lessThanEqual("createdate", createdateTo);
//			list.add(c);
//		}
//		//过账时间不为空
//		if(null != postdateFrom){
//			c = ToplinkCriteriaFactory.greaterThanEqual("postDate", postdateFrom);
//			list.add(c);	
//		}
//		if(null != postdateTo){
//			c = ToplinkCriteriaFactory.lessThanEqual("postDate", postdateTo);
//			list.add(c);
//		}
//		//状态不为空
//		if(null != acctStatus){
//			c = ToplinkCriteriaFactory
//			.equal("status",acctStatus);
//			list.add(c);
//			
//		}
//		//交易号不为空
//		if(null != dealCode){
//			c = ToplinkCriteriaFactory.equal("dealId",dealCode);
//			list.add(c);
//		}
//		//根据修改时间排序.
//		c = ToplinkCriteriaFactory.orderByAsc("createdate");
//		list.add(c);
//		List result = new ArrayList();
//		if(null != page){
//			result = findModelsByCriteria(page, list);
//		}else{
//			result = findModelsByCriteria(list);
//		}
//		return result;
		return null ;
	}

	/* (non-Javadoc)
	 * 
	 */

}
