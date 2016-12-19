package com.pay.app.controller.base.matrixcard;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.bind.MatrixCardBindService;
import com.pay.util.StringUtil;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.controller.matrixcard.util.MatrixCardClientUtil;

public class MatrixCardBindController extends MultiActionController {

	private IMatrixCardService matrixCardService;
	private MatrixCardBindService matrixCardBindService;
	
	// 引导使用界面
	String introView;
	// 绑定界面
	String bindView;  
	// 成功界面
	String succView;
	// 失败界面
	String failView;

	//长度为0
	private static final int zero=0;
	
	//个人type
	private static final int INDIVIDUALTYPE=3;
	
	/**
	 * 判断链接的有效性
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView  checkCodeView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			 LoginSession loginSession = SessionHelper.getLoginSession();
			 String memberCode = loginSession.getMemberCode();
			 String checkCode = request.getParameter("checkCode");
			 ResultDto rsDto = matrixCardBindService.validateEmail(Long.valueOf(memberCode),checkCode);
			 if(null != rsDto && null == rsDto.getErrorCode()) {
				 return showBindView(request,response);
			 }else{
				 return new ModelAndView(failView).addObject("errMsg",rsDto.getErrorMsg());
			 }
		}catch (Exception mcException) {
			 return new ModelAndView(failView).addObject("errMsg",mcException.getMessage());
		}
	}
	
	/**
	 * 显示绑定页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showBindView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			LoginSession loginSession = SessionHelper.getLoginSession();
			if(loginSession.getScaleType()!= INDIVIDUALTYPE){
				String url = "redirect:/error.htm?method=individual";
				return new ModelAndView(url);
			}
			OperatorInfo operatorInfo = MatrixCardClientUtil.getOperatorInfo(request);

			// 判断该会员是否已有绑定卡，有则抛出异常提示
			if (matrixCardService.isBindByMemberCode(operatorInfo.getMemberCode())) {
				return new ModelAndView(failView).addObject("errMsg",MessageConvertFactory.getMessage("matrixCardisBind"));
			}

			// 绑定验证请求
			MatrixCardVfyRequest mcVfyRequest = new MatrixCardVfyRequest();
			ResultDto rsDto = matrixCardBindService.processRequest(mcVfyRequest, operatorInfo);
			if (null != rsDto && null == rsDto.getErrorCode()) {
				MatrixCardToken token = (MatrixCardToken) rsDto.getObject();
				return new ModelAndView(bindView).addObject("requestDate", mcVfyRequest.getRequestDate().getTime()).addObject("token", token.getToken()).addObject("xy0", token.getXy0()).addObject(
				        "xy1", token.getXy1()).addObject("xy2", token.getXy2());
			}
			else {
				return new ModelAndView(failView).addObject("errMsg",rsDto.getErrorMsg());
			}
			// MatrixCardToken token = matrixCardBindService.processRequest(mcVfyRequest, operatorInfo);
		}
		catch (Exception mcException) {
			return new ModelAndView(failView).addObject("errMsg",mcException.getMessage());
			//.addObject("message", mcException.getMessage()).addObject("messageExt", mcException.getErrorEnum().getMessage()).addObject("backUrl","matrixcard/mgrindex.htm");
		}
	}

	/**
	 * 绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView doBind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			OperatorInfo operatorInfo = MatrixCardClientUtil.getOperatorInfo(request);
			// 判断该会员是否已有绑定卡，有则抛出异常提示
			if (matrixCardService.isBindByMemberCode(operatorInfo.getMemberCode())) {
				return new ModelAndView(failView).addObject("errMsg",MessageConvertFactory.getMessage("matrixCardisBind"));
			}

			// 收集提交的数据
			String serialNo = StringUtil.null2String(request.getParameter("sequenceId")).trim();
			String token = StringUtil.null2String(request.getParameter("token"));
			String strValue0 = StringUtil.null2String(request.getParameter("pos0"));
			String strValue1 = StringUtil.null2String(request.getParameter("pos1"));
			String strValue2 = StringUtil.null2String(request.getParameter("pos2"));

			if (serialNo.length() == zero || token.length() == zero || strValue0.length() == zero || strValue1.length() == zero || strValue2.length() == zero) {
				return new ModelAndView(failView).addObject("errMsg",MessageConvertFactory.getMessage("matrixCardInput"));
			}

			MatrixCardVfyDto matrixCardVfyDto = new MatrixCardVfyDto();
			matrixCardVfyDto.setVfyIp(operatorInfo.getIp());
			matrixCardVfyDto.setVfyUid(operatorInfo.getU_id());
			matrixCardVfyDto.setVfyMemberCode(operatorInfo.getMemberCode());
			matrixCardVfyDto.setVfyDate(new Date());
			matrixCardVfyDto.setVfyType(MatrixCardVfyType.NEWCARD_VFY.getValue());

			matrixCardVfyDto.setSerialNo(serialNo);
			matrixCardVfyDto.setToken(token);
			matrixCardVfyDto.setValue0(strValue0);
			matrixCardVfyDto.setValue1(strValue1);
			matrixCardVfyDto.setValue2(strValue2);

			ResultDto rs = matrixCardBindService.bind(matrixCardVfyDto);
			//失败跳转到失败的页面 
			if (null == rs || null != rs.getErrorCode()) {
				return new ModelAndView(failView).addObject("errMsg",rs.getErrorMsg()).addObject("serialNo",serialNo).addObject("backUrl", "${rc.contextPath}/app/matrixCardBind.htm?method=showBindView");
			}
		}
		catch (Exception mcException) {
			return new ModelAndView(failView).addObject("errMsg", mcException.getMessage()).addObject("serialNo","").addObject("backUrl", "${rc.contextPath}/app/matrixCardBind.htm?method=showBindView");
		}
		LoginSession loginSession =SessionHelper.getLoginSession();
		loginSession.setSecurityLvl(SecurityLvlEnum.MAXTRIX.getValue());
		SessionHelper.setLoginSession(loginSession);
		//跳转到成功页面
		return new ModelAndView(succView).addObject("serialNo",StringUtil.null2String(request.getParameter("sequenceId").trim()));
	}

	/***********set 方法***********/
	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public void setMatrixCardBindService(MatrixCardBindService matrixCardBindService) {
		this.matrixCardBindService = matrixCardBindService;
	}

	public void setBindView(String bindView) {
		this.bindView = bindView;
	}

	public void setSuccView(String succView) {
		this.succView = succView;
	}

	public void setFailView(String failView) {
		this.failView = failView;
	}
	
}
