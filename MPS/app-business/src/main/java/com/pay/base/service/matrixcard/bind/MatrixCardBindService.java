package com.pay.base.service.matrixcard.bind;

import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;

/**
 * @author jim_chen
 * @version 
 * 2010-9-17 
 */
public interface MatrixCardBindService {

	/**
	 * 生成绑定显示的图片
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return
	 */
	ResultDto processRequest(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo);
	
	/**
	 * 绑定
	 * @param u_id
	 * @param matrixCardVfyDto
	 * @return Object:boolean
	 */
	ResultDto bind(MatrixCardVfyDto matrixCardVfyDto);
	
	/**验证点击的链接地址是否发送的EMAIL地址
	 * @param memberCode
	 * @param checkCode
	 * @return
	 */
	ResultDto validateEmail(Long memberCode,String checkCode);

}
