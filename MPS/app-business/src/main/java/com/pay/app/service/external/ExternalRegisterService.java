/**
 * 
 */
package com.pay.app.service.external;

import com.pay.app.dto.ExternalRegisterDto;
import com.pay.app.dto.ExternalRegisterResultDto;

/**
 * 提供
 * @author 戴德荣
 *
 */
public interface ExternalRegisterService {

	/**
	 * 处理注册的过程
	 * @param inputDto
	 * @return ExternalRegisterResultDto
	 */
	public ExternalRegisterResultDto processRegisterRdTx(ExternalRegisterDto inputDto);
}
