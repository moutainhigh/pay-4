package com.pay.app.controller.external;

import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.LaMemberQueryService;
import com.pay.acc.service.member.dto.LaResultDto;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.common.util.WebUrlUtil;
import com.pay.app.controller.common.SecuritySubstanceUtil;
import com.pay.util.BeanUtil;
import com.pay.util.CharsetTypeEnum;
import com.pay.util.StringUtil;

public class AcctBalanceQueryController extends MultiActionController{
	
	
	private LaMemberQueryService laMemberQueryService;
	String keysLinked = "memberCode,loginName";
	private static String merchantKey="merchantKey";

	private Log log = LogFactory.getLog(getClass());

	/**
	* @Title: getMerchantKey
	* @Description: 从缓存中得到商户号的公钥,公钥的key为merchantKey+memberCode组成
	* @param @param memberCode
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	private String getMerchantKey(String memberCode){
		if(!StringUtil.isEmpty(memberCode)){
			return 	MessageConvertFactory.getProperties(merchantKey+memberCode);
		}
		return "";
	}



	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long start = System.currentTimeMillis();
		String memberCode=request.getParameter("memberCode");
		String loginName =request.getParameter("loginName");
		LaResultDto resultDto=null;
		resultDto=validateSingnData(request);
		//验签成功
		if(null==resultDto){
			 resultDto=this.laMemberQueryService.doQueryMemberByRelation(memberCode, loginName);
		}		
		String rsaUrl=genRSASign(resultDto);
		resultDto.setSignMsg(rsaUrl);
		String json=BeanUtil.bean2JSON(resultDto);
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		logger.info(json);
		pw.write(json);
		pw.close();
		long end = System.currentTimeMillis();
		logger.debug("执行时长：" + (end - start));	

		
		return null;
	}
	
	/**验证签名
	 * @param request
	 * @return
	 */
	public LaResultDto validateSingnData(HttpServletRequest request) {
		
		LaResultDto resultDto=new LaResultDto ();
		String signMsg =ServletRequestUtils.getStringParameter(request, "signMsg","") ;
		String memberCode=ServletRequestUtils.getStringParameter(request, "memberCode","") ;
		
		 if(StringUtil.isEmpty(getMerchantKey(memberCode))){
				resultDto.setErrorCode(ErrorExceptionEnum.ACC_SINGN_DATA_ERROR.getErrorCode());
				resultDto.setErrorMsg(ErrorExceptionEnum.ACC_SINGN_DATA_ERROR.getMessage());
				return resultDto;		
		 }
		LinkedHashMap<String, String> paramMap = new  LinkedHashMap<String, String>();
		String[] keys = keysLinked.split(",");
		
		for(String k:keys){
			paramMap.put(k, ServletRequestUtils.getStringParameter(request, k,""));
		}
		String signUrl = WebUrlUtil.mapToUrl(paramMap);
		log.info("signUrl=" + signUrl+"   ||   signMsg="+ signMsg);
		try {
			boolean securityFlag=SecuritySubstanceUtil.verifySignatureByRSA(signUrl, signMsg, CharsetTypeEnum.UTF8, getMerchantKey(memberCode));
			log.info("验签结果  ：  "+securityFlag);
			if(securityFlag){
				return null;
			}
		} catch (Exception e) {
			log.error("验签失败 :  "+e);
		}
		resultDto.setErrorCode(ErrorExceptionEnum.LA_SINGN_DATA_ERROR.getErrorCode());
		resultDto.setErrorMsg(ErrorExceptionEnum.LA_SINGN_DATA_ERROR.getMessage());

		return resultDto;		
	}
	
	
	public String  genRSASign(LaResultDto resultDto){
		LinkedHashMap<String, 		String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("resultFlag", 	new Boolean(resultDto.isResultFlag()).toString());
		paramMap.put("loginName", 	resultDto.getLoginName());
		paramMap.put("balance",resultDto.getBalance());
		paramMap.put("errorCode",resultDto.getErrorCode());
		paramMap.put("errorMsg",resultDto.getErrorMsg());
		String signUrl = WebUrlUtil.mapToUrl(paramMap);
		String reaUrl=null;
		try {
			reaUrl=SecuritySubstanceUtil.genSignByRSA(signUrl, CharsetTypeEnum.UTF8);
		} catch (Exception e) {
			log.error("加签失败 ： "+e);
		}
		return reaUrl;
		
	}
	
	public void setLaMemberQueryService(LaMemberQueryService laMemberQueryService) {
		this.laMemberQueryService = laMemberQueryService;
	}	
}
