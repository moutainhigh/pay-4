package com.pay.poss.memberrelation.controller;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件包装类
 */
public class RelationDataFileUploadBean {
	private MultipartFile file;

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

}
