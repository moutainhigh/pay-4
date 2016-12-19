
/**
 * @author single_zhang
 * @version
 * @data 2010-12-31
 */

package com.pay.base.service.appeal;

import com.pay.base.dto.AppealDto;
import com.pay.inf.exception.AppException;



public interface AppealService {
	
	 /**
     * 创建信息
     *
     * @param appeal
     * @return
     */
    public Long createAppeal(AppealDto appealDto);
    
    /**
     * 得到eppealCode
     * @return
     */
    public String getAppealCode();
    
    /**
     * 创建   appeal记录的同时也创建了appealHistory记录
     * @param appealDto
     * @param appealHistoryDto
     * @return
     */
    public Long createAppealHistoryRnTx(AppealDto appealDto)throws AppException;
}
