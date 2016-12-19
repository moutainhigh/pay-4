package com.pay.fundout.withdraw.service.masspaytobank;

import java.util.Map;

import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;
import com.pay.inf.exception.AppException;
import com.pay.rm.facade.dto.RCLimitResultDTO;


/**
 * 批量直付.
 * @company sys.com.
 * @author sean_yi
 * @version 1.0
 */
public interface MassPaytoBankService {
		
	/**
	 * 判断付款账户状态
	 * 
	 * @param memberCode
	 * @param accountType
	 * @param validAmount
	 * @return
	 */
	public String validateAccountInfo(Long memberCode, Integer accountType, Long amount);
	/**
	 * 得到支付密码
	 * 
	 * @param memberCode
	 * @param accountType
	 * @return
	 */
	public boolean validatePayPwd(Long memberCode, Integer accountType,String payPwd);
	
	/**
	 * 保存上传文件基本信息和详细信息
	 * @param baseinfo
	 * @return
	 * @throws AppException 
	 */
	public void saveUploadInfoRdTx(MassPaytobankImportBaseDTO baseinfo) throws AppException;
	
	/**
	 * 确认付款保存工单信息，工作流信息
	 * @param base
	 * @throws AppException 
	 */
	void confirmApplicationRdTx(MassPaytobankImportBaseDTO base) throws AppException;
	
	/**
	 * 创建批量付款到银行明细订单
	 * @param order
	 */
	void createMassPaytobankDetailOrder(long massOrderSeq);
	
	
	
	/**
	 * 付款到银行算费
	 * @param paymentAmount
	 * @param isPayerPayFee
	 * @param memberCode
	 * @return
	 */
	Long caculateFee(Long paymentAmount,Integer isPayerPayFee,String memberCode);
	
	/**
	 * 通知poss后台处理
	 * @param massOrderSeq
	 */
	void notify(Long massOrderSeq);
	
	/**
	 * 发送邮件
	 * @param param 邮件参数
	 * @param subject 邮件主题
	 * @param templateId 邮件模板ID
	 */
	void sendEmail(Map<String,String> param,String subject,long templateId);
	
	
	/**
	 * 获取会员风控规则
	 * @param memberCode
	 * @return
	 */
	RCLimitResultDTO getRCLimitAmount(Long memberCode);
	
	/**
	 * 获取消息内容
	 * @param msgCode
	 * @param pageCode
	 * @param scenarioId
	 * @return
	 */
	String getMessage(String msgCode,String pageCode,String scenarioId);
	
	/**
	 * 获取验证消息
	 * @param msgCode
	 * @return
	 */
	String getMessage(String msgCode);
	/**
	 * 拒绝批量付款到银行订单
	 * @param massOrderSeq
	 * @param remark
	 * @param operatorId
	 * @throws AppException 
	 */
	void rejectMassPaytobankOrder(Long massOrderSeq,String remark,String operatorId) throws AppException;
	
	/**
	 *  更新工单状态
	 * @param massOrderSeq
	 * @param operatorId
	 * @param op 1 审核通过  2审核拒绝
	 * @param remark 备注
	 * @throws AppException
	 */
	public void updateWorkOrderRdTx(long massOrderSeq,String operatorId,int op,String remark) throws AppException;
	
	/**
	 * 处理完成的批量付款到银行订单系信息
	 */
	void processCompleteMassPaytobankOrder();
	/**
	 * 同意批量付款到银行订单
	 * @param base
	 * @param operatorId
	 * @return
	 * @throws AppException
	 */
	boolean passMassPaytoBankOrderRdTx(MassPaytobankImportBaseDTO base,
			String operator) throws AppException;
}
