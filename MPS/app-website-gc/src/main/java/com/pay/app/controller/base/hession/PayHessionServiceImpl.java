package com.pay.app.controller.base.hession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.caucho.hessian.server.HessianServlet;
import com.pay.acc.service.cidverify.IdCardVerifyService;
import com.pay.app.hesssion.dto.CidVerifyDto;
import com.pay.app.hesssion.dto.CidVerifyResultDto;
import com.pay.app.hesssion.service.PayHessionService;
import com.pay.inf.exception.AppException;
import com.pay.util.JSonUtil;


/**
 * payHessionService 
 * 对外hession接口
 * @author ddr
 *
 */
public class PayHessionServiceImpl extends HessianServlet  implements PayHessionService{
 
	protected static final Log log = LogFactory.getLog(PayHessionServiceImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 144711620680124088L;
	//公安网验证实名认证接口服务
	private IdCardVerifyService idCardVerifyService;
	
	public void setIdCardVerifyService(IdCardVerifyService idCardVerifyService) {
		this.idCardVerifyService = idCardVerifyService;
	}
	@Override
	public String sayHello() {
		log.info("----------hession-----------------");
		return "hello----";
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.pay.app.hesssion.service.payHessionService#cidVerify(java.lang.String)
	 */
	public String cidVerify(String jsonString)  {
	
		log.info("传来的json串:"+jsonString);
		CidVerifyDto dto = JSonUtil.toObject(jsonString, CidVerifyDto.class);
		//查询系统中是否有认证过信息
		CidVerifyResultDto resultDto  = null;
		try{
			 boolean  isVerify  = idCardVerifyService.isCidVerified(dto.getNo(), dto.getName());
			  resultDto = new CidVerifyResultDto(isVerify);
		 }catch (AppException e) {
			 e.printStackTrace();
			 resultDto = new CidVerifyResultDto(CidVerifyResultDto.EXCEPTION,"调用公安网失败，暂时得不到认证信息");
		}
		String json = JSonUtil.toJSonString(resultDto);;
		log.info("返回验证结果json串:"+json);
		return json;
	}


	

	
	
	
	
}
