package com.pay.poss.base.dataservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.dataprovide.DataProvideManager;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.model.ParamsFileParser;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.util.ObjectUtil;

public class DateServiceFactory {

	private final static transient Log log = LogFactory.getLog(DateServiceFactory.class);

	public static IDateService createDateService(String key)throws PossUntxException {
		
		if (key == null) throw new PossUntxException("没有找到对应得解析类", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		ParamsFileParser paramsFileParser = DataProvideManager.getDataProvideObject().getParamsFileParserByBankCode(key);
		log.debug("paramsFileParser:" + paramsFileParser);
		IDateService iDateService = null;
		try {
			Object instance = ObjectUtil.instanceByClass(paramsFileParser.getParserClass());
			if (instance instanceof IDateService) {
				iDateService = (IDateService) instance;
			}
		} catch (Exception e) {
			log.equals("工厂实例化解析类失败" + e.getMessage());
			throw new PossUntxException("工厂实例化解析类失败" + e.getMessage(),ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return iDateService;
	}
}
