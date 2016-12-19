/**
 * 
 */
package com.pay.fi.chain.service;

import org.springframework.web.multipart.MultipartFile;

import com.pay.fi.chain.dto.ResultDto;

/**
 * @author PengJiangbo
 *
 */
public interface PayLinkUploadService {
	
	public abstract ResultDto doUploadFileRnTx(MultipartFile file, Long memberCode)
			throws Exception;

	public boolean  deletePic(Long picId);
	
}
