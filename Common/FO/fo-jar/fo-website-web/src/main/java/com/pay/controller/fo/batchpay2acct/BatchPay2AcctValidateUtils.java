/**
 * 
 */
package com.pay.controller.fo.batchpay2acct;

import javax.servlet.http.HttpServletRequest;

import com.pay.util.StringUtil;


/**
 * @author NEW
 *
 */
public class BatchPay2AcctValidateUtils {
	
	public static String validateInputInfo(BatchPay2AcctCommand command){
		
		//上传付款文件
		if(StringUtil.isNull(command.getPaymentFile())){
			return "请上传有效的付款文件";
		}
		
		//业务批次号
		if(StringUtil.isNull(command.getBusinessBatchNo())){
			return "请输业务批次号";
		}

		return null;
	}
	
	
	public static boolean validateToken(HttpServletRequest request){
		String token = StringUtil.null2String(request.getParameter("token"));
		String token_batchpay2acct = null;
		token_batchpay2acct = StringUtil.null2String(request.getSession().getAttribute("token_batchpay2acct"));
		request.getSession().removeAttribute("token_batchpay2acct");
		if(StringUtil.isNull(token)||(StringUtil.isNull(token_batchpay2acct)||!token.equalsIgnoreCase(token_batchpay2acct))){
			return false;
		}
		return true;
	}
	public static boolean vertifyReason(String vertifyCause) {
		int strc = 0;
		for (int i = 0; i < vertifyCause.length(); i++) {
			if (vertifyCause.toCharArray()[i] > 255) {
				strc += 2;
			} else {
				strc++;
			}
		}
		if (strc > 100) {
			return false;
		}
		return true;
	}

}
