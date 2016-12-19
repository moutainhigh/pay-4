 /** @Description 
 * @project 	poss-reconcile
 * @file 		ParserReconcileFactory.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-2		Henry.Zeng			Create 
*/
package com.pay.poss.refund.common.parser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.dataprovide.DataProvideManager;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.ParamsFileParser;
import com.pay.poss.base.tool.ObjectUtils;

/**
 * <p>产生不同的解析器</p>
 * @author Henry.Zeng
 * @since 2010-8-2
 * @see 
 */
public class ParserReconcileFactory {
	
	private final static transient Log log = LogFactory.getLog(ParserReconcileFactory.class);
	
	public static ParserReconcile createParserReconcile(String key ) throws PossUntxException {
		
		if(key == null) throw new PossUntxException("没有找到对应的解析类",ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		
		ParamsFileParser paramsFileParser =  DataProvideManager.getDataProvideObject().getParamsFileParserByBankCode(key);
		
		log.debug("paramsFileParser:"+paramsFileParser);
		
		ParserReconcile parserReconcile = null;
		
		try{
			Object instance = ObjectUtils.instanceByClass(paramsFileParser.getParserClass());
				
			if(instance instanceof ParserReconcile ){
				parserReconcile = (ParserReconcile)instance;
			}
		}catch (Exception e) {
			log.error("工厂实例化解析类失败"+e.getMessage());
			throw new PossUntxException("工厂实例化解析类失败"+e.getMessage(),ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return parserReconcile;
		
	}
	
}
