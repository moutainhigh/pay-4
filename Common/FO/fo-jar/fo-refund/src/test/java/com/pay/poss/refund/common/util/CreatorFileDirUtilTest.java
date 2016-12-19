package com.pay.poss.refund.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.refund.common.util.CreatorFileDirUtil;
import com.pay.poss.refund.model.RefundBatchDTO;


public class CreatorFileDirUtilTest {
	private static Map<String,String> fileInfo = new HashMap<String,String>();
	
	public static void init() {
		fileInfo = new HashMap<String, String>();
		fileInfo.put("BATCH_FILE_PATH", "D:/");
		fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls");
		fileInfo.put("BATCH_NUM", "SYS_0");
		fileInfo.put("BATCH_TASK_TYPE", "200001");

	}
	
	
	public static void main(String[] args){
		init();
		List<RefundBatchDTO> list = new ArrayList<RefundBatchDTO>();
		RefundBatchDTO refundBatchDTO = new RefundBatchDTO();
		refundBatchDTO.setMemberCode("sfdsfsdfsdf");
		list.add(refundBatchDTO);
		CreatorFileDirUtil.writeFile(list, fileInfo);
	}
}
