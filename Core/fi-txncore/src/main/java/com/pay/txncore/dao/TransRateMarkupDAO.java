package com.pay.txncore.dao;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.TransRateMarkup;


/**
 * 汇率多维度的
 * @author peiyu.yang
 *
 */
public interface TransRateMarkupDAO 
                extends BaseDAO<TransRateMarkup> {
	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	TransRateMarkup findTransRateMarkup(Map<String,Object> paramMap);
}
