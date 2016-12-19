package com.pay.base.service.paychain;

import org.springframework.web.multipart.MultipartFile;

import com.pay.base.dto.ResultDto;

public interface PayChainUploadService {

	public abstract ResultDto doUploadFileRnTx(MultipartFile file)
			throws Exception;

	public boolean  deletePic(Long picId);
}