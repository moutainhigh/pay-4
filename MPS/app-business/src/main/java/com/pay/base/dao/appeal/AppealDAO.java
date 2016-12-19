package com.pay.base.dao.appeal;

import com.pay.base.model.Appeal;


/**
 * @author single_zhang
 * @version
 * @data 2010-12-31
 */

public interface AppealDAO {

	   /**
	    * 创建一个投诉，建议，举报
	    * @param appeal
	    * @return
	    */
       public Long createAppeal(Appeal appeal);
       
       /**
        * 得到eppealCode
        * @return
        */
       public String getAppealCode();
       
}
