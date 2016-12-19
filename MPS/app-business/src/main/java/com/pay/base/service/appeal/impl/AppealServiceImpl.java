
/**
 * @author single_zhang
 * @version
 * @data 2010-12-31
 */

package com.pay.base.service.appeal.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.appeal.AppealDAO;
import com.pay.base.dao.appeal.AppealHistoryDAO;
import com.pay.base.dto.AppealDto;
import com.pay.base.model.Appeal;
import com.pay.base.model.AppealHistory;
import com.pay.base.service.appeal.AppealService;
import com.pay.base.service.suggest.impl.SuggestServiceImpl;
import com.pay.inf.exception.AppException;
import com.pay.util.BeanConvertUtil;

public class AppealServiceImpl implements AppealService{

	    private Log logger = LogFactory.getLog(SuggestServiceImpl.class);
	    private AppealDAO appealDao;
	    private AppealHistoryDAO appealHistoryDao;
	    private final String APPEALSTATUS = "1";

	    @Override
		public Long createAppeal(AppealDto appealDto) {
			// TODO Auto-generated method stub
			try {
				Appeal appeal = BeanConvertUtil.convert(Appeal.class,appealDto);
				return appealDao.createAppeal(appeal);
			} catch (Exception e) {
				logger.error("AppealServiceImpl.createAppeal throws error", e);
				return null;
			}
		}

		public Long createAppealHistoryRnTx(AppealDto appealDto) throws AppException{
			Long result;
			try{
				Appeal appeal = BeanConvertUtil.convert(Appeal.class,appealDto);
				appealDao.createAppeal(appeal);
				AppealHistory appealHistory = new AppealHistory();
				appealHistory.setAppealId(appeal.getAppealId());
				appealHistory.setAppealStatusCode(APPEALSTATUS);
				result = appealHistoryDao.createAppealHistory(appealHistory);
			} catch(Exception ex){
				logger.error("AppealServiceImpl.createAppealHistoryRnTx",ex);
				throw new AppException();
			}
			return result;
		}
		
		@Override
		public String getAppealCode() {
			// TODO Auto-generated method stub
			try {
				String appealCode = appealDao.getAppealCode();
				return appealCode;
			} catch (Exception e) {
				logger.error("AppealServiceImpl.getAppealCode throws error", e);
				return null;
			}
		}

		public void setAppealHistoryDao(AppealHistoryDAO appealHistoryDao) {
			this.appealHistoryDao = appealHistoryDao;
		}

		public void setLogger(Log logger) {
			this.logger = logger;
		}

		public void setAppealDao(AppealDAO appealDao) {
			this.appealDao = appealDao;
		}
		
}
