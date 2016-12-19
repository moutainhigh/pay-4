package com.pay.poss.memberrelation.service;

import java.io.InputStream;
import java.util.List;

import com.pay.poss.memberrelation.exception.VouchRelationValidateMessage;

public interface UploadTemplateDataService {

	public List<VouchRelationValidateMessage> uploadDate(InputStream inStream);

}
