package com.pay.acc.service.cidverify.cid2bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-9-29 下午01:31:13 pay2bocom
 */
public class BocomManager {

	private final Log log = LogFactory.getLog(BocomManager.class);
	private String fileUrl;// 文件路径
	private String certtype;// 证件类型
	/**
	 * @param cardNo银行卡号
	 * @param custName名称
	 * @param certNo证件号
	 * @return true/false
	 * @throws Exception
	 */
	public boolean invokeBocom(String cardNo, String custName, String certNo)
			throws Exception {
		boolean bool = false;
		//BOCOMB2CClient bc = new BOCOMB2CClient();
		//int ret = bc.initialize(fileUrl);
		if (false) {
			log.error("initialize [bocom] error.");
		} else {
			log.info("initialize [bocom] done.invoke bocomm verifyCustId.");
			//BOCOMB2COPReply retObj = bc.verifyCustID(cardNo, null,certtype, certNo);
//			if(retObj != null){
//				if ("0".equals(retObj.getRetCode() + "")) {
//					bool = true;
//				}
//			}
		}
		log.info("invoke bocomm verifyCustId return  ["+bool+"]");
		return bool;
	}
	
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}
}
