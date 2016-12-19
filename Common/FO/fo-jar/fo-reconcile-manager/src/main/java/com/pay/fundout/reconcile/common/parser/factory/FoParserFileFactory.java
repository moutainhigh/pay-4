/** @Description 
 * @project 	poss-withdraw
 * @file 		FoParserFileFactory.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-23		Henry.Zeng			Create 
 */
package com.pay.fundout.reconcile.common.parser.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.reconcile.common.parser.FoParserFile;
import com.pay.poss.base.dataprovide.DataProvideManager;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.ParamsFileParser;
import com.pay.poss.base.tool.ObjectUtils;

/**
 * <p>
 * 实例化具体解析类
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-23
 * @see
 */
public class FoParserFileFactory {
	private final static transient Log log = LogFactory
			.getLog(FoParserFileFactory.class);

	/**
	 * 根据key创建文件解析实例对象
	 * @param key
	 * @return
	 * @throws PossUntxException
	 */
	public static FoParserFile createFoParserFile(String key)
			throws PossUntxException {

		if (key == null)
			throw new PossUntxException("没有找到对应得解析类",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);

		ParamsFileParser paramsFileParser = DataProvideManager
				.getDataProvideObject().getParamsFileParserByBankCode(key);

		log.debug("paramsFileParser:" + paramsFileParser);

		FoParserFile foParserFile = null;

		try {
			Object instance = ObjectUtils.instanceByClass(paramsFileParser
					.getParserClass());

			if (instance instanceof FoParserFile) {
				foParserFile = (FoParserFile) instance;
			}
		} catch (Exception e) {
			log.error("工厂实例化解析类失败" + e.getMessage(),e);
			throw new PossUntxException("工厂实例化解析类失败" + e.getMessage(),
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return foParserFile;

	}
}
