package com.pay.txncore.service;

import com.pay.txncore.dto.PreauthInfo;
import com.pay.txncore.dto.PreauthResult;


/**
 * 预授权完成接口
 * @author peiyu.yang
 * @since 2015年6月11日
 */
public interface PreauthCompService {
   
	/**
	 * 预授权申请
	 * @param preauthInfo
	 * @return
	 */
   PreauthResult preauthCompleted(PreauthInfo preauthInfo);
}
