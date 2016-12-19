 /** @Description 
 * @project 	poss-reconcile
 * @file 		AbstractParserReconcile.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-3		Henry.Zeng			Create 
*/
package com.pay.poss.refund.common.parser.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.refund.common.parser.ParserReconcile;
import com.pay.poss.refund.model.RefundImportRecord;
import com.pay.poss.refund.model.WebRefundUploadDTO;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-8-3
 * @see 
 */
abstract public class AbstractParserReconcile implements ParserReconcile {
	protected transient Log log = LogFactory.getLog(getClass());
	
	protected String dataForamt = "yyyyMMddHHmmss";
	
	@Override
	abstract public List<RefundImportRecord> parserFile(
			WebRefundUploadDTO webRefundUploadDTO)
			throws PossUntxException ;
	/**
	 * 通过反射调用子类的方法
	 * @param methodName 方法名字(方法名字必须遵循parserFile+文件后缀)
	 * @param parserReconcile 当前调用改方法的对象一般传入this
	 * @param parameters 入参的数据
	 * @param parameterTypes 入参的类型
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author Henry.Zeng
	 * @see
	 */
	public Object invoke(String methodName , ParserReconcile parserReconcile , Object [] parameters , Class<?>... parameterTypes  ) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		Object obj = null;
		
		Method method = parserReconcile.getClass().getMethod(methodName, parameterTypes);
		
		obj = method.invoke(parserReconcile ,parameters);

		return obj;
	}
	
	
	
	
}
