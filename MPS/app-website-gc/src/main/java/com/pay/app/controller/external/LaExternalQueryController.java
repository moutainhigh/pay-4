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
import com.pay.app.common.util.WebUrlUtil;
import com.pay.app.controller.common.SecuritySubstanceUtil;
import com.pay.util.BeanUtil;
import com.pay.util.CharsetTypeEnum;



/** 
* @ClassName: LaExternalQueryController 
* @Description: 利安余额查询接口 
* @author cf
* @date 2011-9-26 下午12:01:46 
*  
*/

public class LaExternalQueryController extends MultiActionController{
	
	
	private LaMemberQueryService laMemberQueryService;
	String keysLinked = "memberCode,login_Name";
	//利安给的公钥
	private String publicKey;

	private Log log = LogFactory.getLog(getClass());


	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long start = System.currentTimeMillis();
		String memberCode=request.getParameter("memberCode");
		String login_Name =request.getParameter("login_Name");
		LaResultDto resultDto=null;
		resultDto=validateSingnData(request);
		//验签成功
		if(null==resultDto){
			 resultDto=this.laMemberQueryService.doQueryMemberByRelation(memberCode, login_Name);
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
	
		LinkedHashMap<String, String> paramMap = new  LinkedHashMap<String, String>();
		String[] keys = keysLinked.split(",");
		
		for(String k:keys){
			paramMap.put(k, ServletRequestUtils.getStringParameter(request, k,""));
		}
		String signUrl = WebUrlUtil.mapToUrl(paramMap);
		log.info("signUrl=" + signUrl+"   ||   signMsg="+ signMsg);
		try {
			boolean securityFlag=SecuritySubstanceUtil.verifySignatureByRSA(signUrl, signMsg, CharsetTypeEnum.UTF8, publicKey);
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
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}
