package com.pay.base.dao.appeal;

/**
 * @author single_zhang
 * @version
 * @data 2010-12-31
 */

import com.pay.base.model.AppealHistory;


public interface AppealHistoryDAO {
	  
		/**
	    * 创建一个投诉，建议，举报的历史记录
	    * @param appeal
	    * @return
	    */
	    public Long createAppealHistory(AppealHistory appealHistory);
	    
	    
	    
}
