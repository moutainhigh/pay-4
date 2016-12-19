package com.pay.app.controller.base.login;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.app.base.api.annotation.PutAppLog;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.SafeControllerWrapper;
import com.pay.app.service.mail.MailService;
import com.pay.app.service.sms.SmsService;
import com.pay.app.validator.RegisterValidator;
import com.pay.app.validator.ValidatorDto;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.service.member.MemberService;
import com.pay.util.CheckUtil;

public class RegisterController extends MultiActionController {

	private static final Log logger = LogFactory
			.getLog(RegisterController.class);
	private static final String POST_METHOD_PREFIX = "POST";
	private String toRegister;
	private String toSupplementary;
	private String toSsoTimeOut;
	private String toIndex;
	private String toSecond;
	private final static int REGTYPE_EMAIL = 2;

	private final static int REGTYPE_MOBILE = 1;

	private MemberService baseMemberService;

	/**
	 * 邮件服务
	 */
	private MailService mailService;
	private SmsService smsService;
	private String mailUrlAction;

	// 平台过来用户标识
	private final static String RELATION = "relation";

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(toIndex);
	}

	public ModelAndView select(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(toSecond);
	}

	public void setMailUrlAction(String mailUrlAction) {
		this.mailUrlAction = mailUrlAction;
	}

	public ModelAndView third(HttpServletRequest request,
			HttpServletResponse response) {
		int regType = (request.getParameter("regType") == null && !CheckUtil
				.isNumber(request.getParameter("regType"))) ? 1 : Integer
				.parseInt(request.getParameter("regType"));
		MemberInfoDto memberRegisterBO = new MemberInfoDto();
		memberRegisterBO.setRegType(regType);
		return new ModelAndView(toRegister).addObject("criteria",
				memberRegisterBO);
	}

	public ModelAndView checkCode(HttpServletRequest request,
			HttpServletResponse response, MemberInfoDto memberRegisterBO) throws Exception {
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("code");
		if (randCode.equalsIgnoreCase(code)) {
			response.getWriter().println(1);
		}else {
			response.getWriter().println(0);
		}
		return null;
	}
	@PutAppLog(logType = CutsConstants.USER_LOG_REGISTERSUCCESS)
	public ModelAndView register(HttpServletRequest req,
			HttpServletResponse response, MemberInfoDto memberRegisterBO)
			throws Exception {
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				new String[] { "password", "passwordConfirm" });
		String msgStr = ErrorCodeEnum.SYSTEM_NO_POST.getMessage();

		String password = null == request.getParameter("password") ? ""
				: request.getParameter("password");
		String passwordConfirm = null == request
				.getParameter("passwordConfirm") ? "" : request
				.getParameter("passwordConfirm");
		String _target = this.getToRegister();

		String method = request.getMethod();
		if (POST_METHOD_PREFIX.equals(method)) {
			String randCode = request.getSession().getAttribute("rand") == null ? ""
					: request.getSession().getAttribute("rand").toString();
			request.getSession().removeAttribute("rand");
			String code = request.getParameter("randCode") == null ? ""
					: request.getParameter("randCode");
			String relation = request.getParameter("relation") == null ? ""
					: request.getParameter("relation");
			String loginName = null;
			if (RELATION.equals(relation)) { // 商城过来的用户注册后直接做关联
				String userId = request.getSession().getAttribute("userid") == null ? ""
						: request.getSession().getAttribute("userid")
								.toString();
				if (StringUtils.isBlank(userId)) {
					return new ModelAndView(toSsoTimeOut);
				}
				memberRegisterBO.setSsoUserId(userId);
				_target = this.getToSupplementary();
			}
			if (REGTYPE_EMAIL == memberRegisterBO.getRegType()) {
				loginName = request.getParameter("emailLoginName") == null ? ""
						: request.getParameter("emailLoginName");
				memberRegisterBO.setMobile(memberRegisterBO.getContact());
			} else {
				loginName = request.getParameter("mobileLoginName") == null ? ""
						: request.getParameter("mobileLoginName");
				memberRegisterBO.setEmail(memberRegisterBO.getContact());
			}
			memberRegisterBO.setLoginName(loginName);

			// 两次出入的密码不一致
			if (!password.equals(passwordConfirm)) {
				msgStr = ErrorCodeEnum.MEMBER_LOGINNAME_PASSWORD_DIFF
						.getMessage();
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("criteria", memberRegisterBO);
				paraMap.put("msgStr", msgStr);
				paraMap.put("register", memberRegisterBO);
				return new ModelAndView(_target, paraMap);
			}

			memberRegisterBO.setPassword(password);

			ValidatorDto vd = RegisterValidator.validate(memberRegisterBO);// 校验表单提交的内容
			if (vd.hasErrors()) {
				msgStr = vd.getError();
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("criteria", memberRegisterBO);
				paraMap.put("msgStr", msgStr);
				paraMap.put("register", memberRegisterBO);
				return new ModelAndView(_target, paraMap);
			}
			if ("".equals(randCode) || "".equals(code)
					|| !code.equalsIgnoreCase(randCode)) { // 校验验证码
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("criteria", memberRegisterBO);
				paraMap.put("inputName", "randCode");
				paraMap.put("msgStr",
						MessageConvertFactory.getMessage("randCode"));
				paraMap.put("register", memberRegisterBO);
				return new ModelAndView(_target, paraMap);
			} else if (!baseMemberService.checkLoginNameByRegister(loginName,
					memberRegisterBO.getRegType())) {// 校验用户名是否唯一
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("criteria", memberRegisterBO);
				paraMap.put("inputName", "loginName");
				paraMap.put("msgStr",
						MessageConvertFactory.getMessage("loginNameRepeat"));
				paraMap.put("register", memberRegisterBO);
				return new ModelAndView(_target, paraMap);
				// }else if(memberRegisterBO.getContact()!=null &&
				// !"".equals(memberRegisterBO.getContact()) &&
				// !baseMemberService.checkLoginNameByRegister(memberRegisterBO.getContact(),this.invokeRegType(memberRegisterBO.getRegType()))){
				// Map<String, Object> paraMap = new HashMap<String, Object>();
				// paraMap.put("criteria", memberRegisterBO);
				// paraMap.put("inputName", "contact");
				// paraMap.put("msgStr",
				// MessageConvertFactory.getMessage("contactRepeat"));
				// return new ModelAndView(toRegister, paraMap);
			}

			ResultDto resDto = new ResultDto();

			try {
				memberRegisterBO.setPassword(password);// 设置密码
				resDto = baseMemberService
						.doIndividualMemberRegisterRdTx(memberRegisterBO);
			} catch (Exception e) {
				resDto.setErrorNum(ErrorCodeEnum.SYSTEM_BUSY);
				logger.error(
						"baseMemberService.doIndividualMemberRegisterRdTx throws error",
						e);
			}

			msgStr = resDto.getErrorMsg();
			if (msgStr == null) {

				// 注册成功之后把memberCode和loginName放入SESSION以方便以后调用
				request.getSession().setAttribute("memberCode",
						resDto.getMemberCode());
				request.getSession().setAttribute("loginName", loginName);
				request.getSession().setAttribute("verifyName",
						memberRegisterBO.getRealName());
				// 确保注册成功只记录一条日志信息
				request.setAttribute("isRegisterSuccess", "Y");
				if (REGTYPE_EMAIL == memberRegisterBO.getRegType()) {
					return sendEmail(loginName, memberRegisterBO.getRealName(),
							resDto.getMemberCode());
					// return new ModelAndView("redirect:/reSendEmail.htm");
				} else {
					// return new ModelAndView("redirect:/reSendMessage.htm");
					return sendSms(loginName, memberRegisterBO.getRealName(),
							resDto.getMemberCode());
				}
			}

		} else {
			memberRegisterBO = new MemberInfoDto();
		}

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("criteria", memberRegisterBO);
		paraMap.put("msgStr", msgStr);
		return new ModelAndView(_target, paraMap);
		// return index(request,response).addObject("msgStr",msgStr);
	}

	public void checkLoginName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		int regType = request.getParameter("regType") == null ? 1 : Integer
				.parseInt(request.getParameter("regType"));
		String loginName = null;
		if (REGTYPE_EMAIL == regType) {
			loginName = request.getParameter("emailLoginName") == null ? ""
					: request.getParameter("emailLoginName");
		} else {
			loginName = request.getParameter("mobileLoginName") == null ? ""
					: request.getParameter("mobileLoginName");
		}

		PrintWriter out = null;
		out = response.getWriter();
		boolean flag = false;
		flag = baseMemberService.checkLoginNameByRegister(
				loginName.toLowerCase(), regType);
		out.print(flag);
		out.flush();
		out.close();
	}

	public void checkStatusLoginName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		int regType = request.getParameter("regType") == null ? 1 : Integer
				.parseInt(request.getParameter("regType"));
		String loginName = null;
		if (REGTYPE_EMAIL == regType) {
			loginName = request.getParameter("emailLoginName") == null ? ""
					: request.getParameter("emailLoginName");
		} else {
			loginName = request.getParameter("mobileLoginName") == null ? ""
					: request.getParameter("mobileLoginName");
		}

		PrintWriter out = null;
		out = response.getWriter();
		int flag = MemberStatusEnum.UNCREATE.getCode();
		flag = baseMemberService.checkLoginNameByRegister(loginName
				.toLowerCase());
		out.print(flag);
		out.flush();
		out.close();
	}

	public void checkContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		int regType = request.getParameter("regType") == null ? 1 : Integer
				.parseInt(request.getParameter("regType"));
		String loginName = request.getParameter("contact") == null ? ""
				: request.getParameter("contact");

		PrintWriter out = null;
		out = response.getWriter();
		boolean flag = false;
		flag = baseMemberService.checkLoginNameByRegister(
				loginName.toLowerCase(), regType);
		out.print(flag);
		out.flush();
		out.close();
	}

	private int invokeRegType(final int regType) {
		int rt = REGTYPE_MOBILE;
		if (REGTYPE_MOBILE == regType)
			rt = this.REGTYPE_EMAIL;
		else
			rt = this.REGTYPE_MOBILE;
		return rt;
	}

	private ModelAndView sendEmail(String email, String verifyName,
			String memberCode) {
		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);

		CheckCodeDto ck = new CheckCodeDto();
		ck.setMemberCode(Long.valueOf(memberCode));
		ck.setOrigin(CheckCodeOriginEnum.ACTIVE_REGISTER.getValue());
		ck.setEmail(email);

		if (email != null
				&& mailService.sendMail(verifyName, recAddress, ck,
						mailUrlAction, Resource.TEMPLATEID_ACTIVATION,
						Resource.MAIL_SUBJECT_ACTIVATION)) {

			return new ModelAndView("redirect:/reSendEmail.htm?method=toEmail");
		}
		return new ModelAndView("redirect:/reSendEmail.htm?method=toFail");
	}

	private ModelAndView sendSms(String mobile, String verifyName,
			String memberCode) {
		List<String> mobiles = new ArrayList<String>(1);
		mobiles.add(mobile);
		if (mobile != null
				&& !"".equals(mobile)
				&& smsService.sendSms(memberCode,
						CheckCodeOriginEnum.ACTIVE_REGISTER.getValue(),
						verifyName, mobiles, 2)) {
			return new ModelAndView(
					"redirect:/reSendMessage.htm?method=toMobile");
		}
		return new ModelAndView("redirect:/reSendMessage.htm?method=toFail");
	}

	public String getToSupplementary() {
		return toSupplementary;
	}

	public void setToSupplementary(String toSupplementary) {
		this.toSupplementary = toSupplementary;
	}

	public String getToRegister() {
		return toRegister;
	}

	public void setToRegister(String toRegister) {
		this.toRegister = toRegister;
	}

	public String getToSsoTimeOut() {
		return toSsoTimeOut;
	}

	public void setToSsoTimeOut(String toSsoTimeOut) {
		this.toSsoTimeOut = toSsoTimeOut;
	}

	public void setBaseMemberService(MemberService baseMemberService) {
		this.baseMemberService = baseMemberService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setToIndex(String toIndex) {
		this.toIndex = toIndex;
	}

	public void setToSecond(String toSecond) {
		this.toSecond = toSecond;
	}

}
