package com.pay.base.service.suggest.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.util.BeanConvertUtil;
import com.pay.base.dao.suggest.SuggestDAO;
import com.pay.base.dto.SuggestDto;
import com.pay.base.model.Suggest;
import com.pay.base.service.suggest.SuggestService;

public class SuggestServiceImpl implements SuggestService {
	
    private Log logger = LogFactory.getLog(SuggestServiceImpl.class);
    private SuggestDAO suggestDAO;
    
    
	/* (non-Javadoc)
	 * @see com.pay.base.service.suggest.SuggestService#createAcct(com.pay.base.model.Suggest)
	 */
	public Long createSuggest(SuggestDto suggestDto) {
		try {
			Suggest suggest = BeanConvertUtil.convert(Suggest.class, suggestDto);
			return suggestDAO.createSuggest(suggest);
		} catch (Exception e) {
			logger.error("SuggestServiceImpl.createSuggest throws error", e);
			return null;
		}
	}
	

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public SuggestDAO getSuggestDAO() {
		return suggestDAO;
	}

	public void setSuggestDAO(SuggestDAO suggestDAO) {
		this.suggestDAO = suggestDAO;
	}
	

}
