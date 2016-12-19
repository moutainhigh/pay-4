package com.pay.pe.manualbooking.controller;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件包装类
 */
public class VouchDataFileUploadBean {
	private MultipartFile file;

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

}
