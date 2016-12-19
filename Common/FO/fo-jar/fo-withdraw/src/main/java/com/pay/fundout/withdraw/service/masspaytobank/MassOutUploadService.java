package com.pay.fundout.withdraw.service.masspaytobank;

import java.io.IOException;

import com.pay.fundout.withdraw.common.NotMatchTemplateException;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;



/**
 * 批量直付上传.
 * @company sys.com.
 * @author sean_yi
 * @version 1.0
 */
public interface MassOutUploadService {
		
	/**
	 * 解析上传的文件为DTO对象
	 * @param file
	 * @param maxSize
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 * @throws NotMatchTemplateException 
	 */
	public MassPaytobankImportBaseDTO buildUploadOrders(final byte[] file,int maxSize)
			throws IOException, NotMatchTemplateException;
	
	/**
	 * 验证批量付款到银行详细信息
	 * @param details
	 * @return
	 */
	public MassPaytobankImportBaseDTO validateMassOutDetails(MassPaytobankImportBaseDTO mass);
}
