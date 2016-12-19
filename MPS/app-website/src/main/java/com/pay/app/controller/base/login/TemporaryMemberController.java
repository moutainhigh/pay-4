package com.pay.app.controller.base.login;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.app.base.api.common.enums.TokenEnum;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.SafeControllerWrapper;
import com.pay.app.service.mail.MailService;
import com.pay.app.service.sms.SmsService;
import com.pay.app.validator.UpdateValidator;
import com.pay.app.validator.ValidatorDto;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.member.MemberService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.CheckUtil;
import com.pay.util.DESUtil;
import com.pay.util.FormatDate;

public class TemporaryMemberController extends MultiActionController {

	private static final String loginOutUrl = "/outapp.htm";

	// 取得会员信息
	private String initMemberInfo;
	private String initTempMemberInfo;
	// 更新会员信息
	private String updateMemberInfo;
	// 激活补全成功
	private String activeTempSuccess;
	// 登录

	private MemberService baseMemberService;

	private CheckCodeService checkCodeService;

	private SmsService smsService;

	/**
	 * 邮件服务
	 */
	private MailService mailService;

	private AcctService acctService;

	// 填写激活手机号
	private String inputActiveMobile;
	// 填写激活E-mail
	private String inputActiveEmail;

	// 输入手机验证码
	private String activeMobileForTemporary;

	private String activeEmailForTemporary;

	// 非法请求跳转
	private String toresultviewwithoutlogin;

	private String repeatCommit;
	private IMessageDigest iMessageDigest;
	private String mailUrlTempAction;

	private static final String[] PASSWORDS = new String[] { "payPassword",
			"payPasswordConfirm", "loginPassword", "loginPasswordConfirm" };

	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMailUrlTempAction(String mailUrlTempAction) {
		this.mailUrlTempAction = mailUrlTempAction;
	}

	/**
	 * 临时会员激活第一步，填写手机号
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toActiveMember(HttpServletRequest request,
			HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		ModelAndView mv = new ModelAndView(inputActiveMobile);
		if (StringUtils.isNotBlank(loginName)
				&& CheckUtil.checkPhone(loginName)) {
			mv = new ModelAndView(inputActiveMobile);
		} else if (StringUtils.isNotBlank(loginName)
				&& CheckUtil.checkEmail(loginName)) {
			mv = new ModelAndView(inputActiveEmail);
		} else {
			return mv.addObject("loginName", loginName);
		}

		MemberDto member = baseMemberService.findMemberByLoginName(loginName);
		if (member != null) {
			loginName = member.getLoginName();
			if (member.getLoginType() == 1) {
				mv = new ModelAndView(inputActiveMobile);
			} else {
				mv = new ModelAndView(inputActiveEmail);
			}

			if (member.getStatus() != MemberStatusEnum.TEMPORARY.getCode()
					.intValue()) {
				// 如果不是临时会员
				mv.addObject("errMsg",
						ErrorCodeEnum.MEMBER_STATUS_NOT_TEMPORARY.getMessage());
			}
		} else {
			mv.addObject("errMsg",
					ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getMessage());
		}

		return mv.addObject("loginName", loginName);
	}

	/**
	 * 临时会员激活第二步，调转到填写手机验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toSendActiveMessage4Temporary(
			HttpServletRequest request, HttpServletResponse response) {

		// 验证手机号是否有效
		String mobile = request.getParameter("mobile");

		if (StringUtils.isBlank(mobile)) {
			String errMsg = MessageConvertFactory.getMessage("loginNameError1");
			return new ModelAndView(inputActiveMobile).addObject("errMsg",
					errMsg);
		}

		if (!CheckUtil.checkPhone(mobile)) {
			String errMsg = MessageConvertFactory
					.getMessage("mobileNumberInvalid");
			return new ModelAndView(inputActiveMobile).addObject("errMsg",
					errMsg);
		}

		// 验证会员状态
		MemberDto member = baseMemberService.findMemberByLoginName(mobile);

		if (member == null) {
			// 会员不存在
			String errMsg = ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getMessage();
			return new ModelAndView(inputActiveMobile).addObject("errMsg",
					errMsg);
		}

		if (member.getStatus() != MemberStatusEnum.TEMPORARY.getCode()
				.intValue()) {
			// 不是临时会员
			String errMsg = ErrorCodeEnum.MEMBER_STATUS_NOT_TEMPORARY
					.getMessage();
			return new ModelAndView(inputActiveMobile).addObject("errMsg",
					errMsg);
		}

		// 验证随机码是否正确
		String code = request.getParameter("code");
		String randCode = String.valueOf(request.getSession().getAttribute(
				"rand"));
		if (StringUtils.isBlank(code) || StringUtils.isBlank(randCode)
				|| !code.equalsIgnoreCase(randCode)) {
			// 验证码错误，调转到上一个页面
			String errMsg = MessageConvertFactory.getMessage("randCode");
			return new ModelAndView(inputActiveMobile).addObject("errMsg",
					errMsg).addObject("mobile", mobile);
		}

		// 发送短信到用户
		List<String> mobilelist = new ArrayList<String>();
		mobilelist.add(mobile);
		request.getSession().setAttribute("memberCode",
				String.valueOf(member.getMemberCode()));
		request.getSession().setAttribute("loginName", String.valueOf(mobile));

		// 更新以前发送得短信为已使用
		checkCodeService.updateCheckCodeStatus2Used(
				String.valueOf(member.getMemberCode()),
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_MOBILE.getValue());

		smsService.sendSms(String.valueOf(member.getMemberCode()),
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_MOBILE.getValue(),
				mobile, mobilelist, Resource.TEMPLATEID_FOR_TEMPORARY);
		CheckCodeDto checkCode = smsService.getByMemerCode(
				String.valueOf(member.getMemberCode()),
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_MOBILE);
		request.getSession().setAttribute("checkCode", checkCode);
		return new ModelAndView(activeMobileForTemporary).addObject("mobile",
				mobile);
	}

	/**
	 * 临时会员激活第二步，调转到填写邮箱验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toSendActiveEmail4Temporary(HttpServletRequest request,
			HttpServletResponse response) {

		// 验证手机号是否有效
		String email = request.getParameter("email");

		if (StringUtils.isBlank(email)) {
			String errMsg = ErrorCodeEnum.MEMBER_TEMP__EMAIL_EMPTY_ERROR
					.getMessage();
			return new ModelAndView(inputActiveEmail).addObject("errMsg",
					errMsg);
		}

		if (!CheckUtil.checkEmail(email)) {
			String errMsg = ErrorCodeEnum.MEMBER_TEMP__EMAIL_INVALID
					.getMessage();
			return new ModelAndView(inputActiveEmail).addObject("errMsg",
					errMsg);
		}

		// 验证会员状态
		MemberDto member = baseMemberService.findMemberByLoginName(email);

		if (member == null) {
			// 会员不存在
			String errMsg = ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getMessage();
			return new ModelAndView(inputActiveEmail).addObject("errMsg",
					errMsg);
		}

		if (member.getStatus() != MemberStatusEnum.TEMPORARY.getCode()
				.intValue()) {
			// 不是临时会员
			String errMsg = ErrorCodeEnum.MEMBER_STATUS_NOT_TEMPORARY
					.getMessage();
			return new ModelAndView(inputActiveEmail).addObject("errMsg",
					errMsg);
		}

		// 验证随机码是否正确
		String code = request.getParameter("code");
		String randCode = String.valueOf(request.getSession().getAttribute(
				"rand"));
		if (StringUtils.isBlank(code) || StringUtils.isBlank(randCode)
				|| !code.equalsIgnoreCase(randCode)) {
			// 验证码错误，调转到上一个页面
			String errMsg = MessageConvertFactory.getMessage("randCode");
			return new ModelAndView(inputActiveEmail).addObject("errMsg",
					errMsg).addObject("loginName", email);
		}

		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);
		request.getSession().setAttribute("memberCode",
				String.valueOf(member.getMemberCode()));
		request.getSession().setAttribute("loginName", String.valueOf(email));

		// 更新以前发送得短信为已使用
		checkCodeService.updateCheckCodeStatus2Used(
				String.valueOf(member.getMemberCode()),
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_EMAIL.getValue());

		CheckCodeDto ck = new CheckCodeDto();
		ck.setMemberCode(member.getMemberCode());
		ck.setOrigin(CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_EMAIL.getValue());
		ck.setEmail(email);

		mailService.sendMail(email, recAddress, ck, mailUrlTempAction,
				Resource.TEMPLATEID_ACTIVATION_FOR_TEMPORARY,
				Resource.MAIL_SUBJECT_ACTIVATION);

		CheckCodeDto checkCode = smsService.getByMemerCode(
				String.valueOf(member.getMemberCode()),
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_EMAIL);
		request.getSession().setAttribute("checkCode", checkCode);
		return new ModelAndView(activeEmailForTemporary).addObject("email",
				email);
	}

	/**
	 * 重发激活短信
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView reSendActiveMessage4Temporary(
			HttpServletRequest request, HttpServletResponse response) {

		// 验证手机号是否有效
		String mobile = request.getSession().getAttribute("loginName") == null ? null
				: String.valueOf(request.getSession().getAttribute("loginName"));
		String memberCode = String.valueOf(request.getSession().getAttribute(
				"memberCode"));
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(memberCode)) {
			// 如果取不到手机号或者会员号，则跳转到错误页面
			return new ModelAndView(toresultviewwithoutlogin).addObject(
					"returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
		}
		MemberDto member = baseMemberService.findMemberByLoginName(mobile);
		if (member == null || member.getStatus() == 1) {
			return new ModelAndView(repeatCommit).addObject("msgStr",
					MessageConvertFactory.getMessage("tempactiveReady"));
		}
		// 发送短信到用户
		List<String> mobilelist = new ArrayList<String>();
		mobilelist.add(mobile);
		// 更新以前发送得短信为已使用
		checkCodeService.updateCheckCodeStatus2Used(memberCode,
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_MOBILE.getValue());
		smsService.sendSms(memberCode,
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_MOBILE.getValue(),
				mobile, mobilelist, Resource.TEMPLATEID_FOR_TEMPORARY);

		CheckCodeDto checkCode = smsService.getByMemerCode(memberCode,
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_MOBILE);
		request.getSession().setAttribute("checkCode", checkCode);
		return new ModelAndView(activeMobileForTemporary).addObject("mobile",
				mobile);
	}

	public ModelAndView reSendActiveEmail4Temporary(HttpServletRequest request,
			HttpServletResponse response) {

		// 验证手机号是否有效
		String email = request.getSession().getAttribute("loginName") == null ? null
				: String.valueOf(request.getSession().getAttribute("loginName"));
		String memberCode = String.valueOf(request.getSession().getAttribute(
				"memberCode"));
		if (StringUtils.isBlank(email) || StringUtils.isBlank(memberCode)) {
			// 如果取不到手机号或者会员号，则跳转到错误页面
			return new ModelAndView(toresultviewwithoutlogin).addObject(
					"returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
		}
		MemberDto member = baseMemberService.findMemberByLoginName(email);
		if (member == null || member.getStatus() == 1) {
			return new ModelAndView(repeatCommit).addObject("msgStr",
					MessageConvertFactory.getMessage("tempactiveReady"));
		}
		String code = request.getParameter("code");
		String randCode = String.valueOf(request.getSession().getAttribute(
				"rand"));
		if (StringUtils.isBlank(code) || StringUtils.isBlank(randCode)
				|| !code.equalsIgnoreCase(randCode)) {
			// 验证码错误，调转到上一个页面
			String errMsg = MessageConvertFactory.getMessage("randCode");
			return new ModelAndView(activeEmailForTemporary).addObject(
					"errMsg", errMsg).addObject("email", email);
		}

		List<String> recAddress = new ArrayList<String>(1);
		recAddress.add(email);
		// 更新以前发送得短信为已使用
		checkCodeService.updateCheckCodeStatus2Used(memberCode,
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_EMAIL.getValue());
		CheckCodeDto ck = new CheckCodeDto();
		ck.setMemberCode(Long.valueOf(memberCode));
		ck.setOrigin(CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_EMAIL.getValue());
		ck.setEmail(email);

		mailService.sendMail(email, recAddress, ck, mailUrlTempAction,
				Resource.TEMPLATEID_ACTIVATION_FOR_TEMPORARY,
				Resource.MAIL_SUBJECT_ACTIVATION);
		CheckCodeDto checkCode = smsService.getByMemerCode(memberCode,
				CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_EMAIL);
		request.getSession().setAttribute("checkCode", checkCode);
		return new ModelAndView(activeEmailForTemporary).addObject("email",
				email);
	}

	/**
	 * 取得会员信息
	 */
	public ModelAndView initMemberInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		String loginName = (String) request.getSession().getAttribute(
				"loginName");

		if (request.getSession().getAttribute("memberCode") == null) {
			RequestDispatcher rd = request.getRequestDispatcher(loginOutUrl);
			rd.forward(request, response);
		}

		String checkCode = request.getParameter("checkCode");
		// 验证验证码是否正确

		if (!StringUtils.isBlank(checkCode)) {
			CheckCodeDto ck = checkCodeService.getByCheckCode(checkCode);
			if (ck == null) {
				String errMsg = MessageConvertFactory.getMessage("mobileEmpty");
				return new ModelAndView(activeMobileForTemporary).addObject(
						"mobile", loginName).addObject("errMsg", errMsg);
			}
			MemberDto member = baseMemberService
					.findMemberByLoginName(loginName);
			if (member == null || member.getStatus() == 1) {
				return new ModelAndView(repeatCommit).addObject("msgStr",
						MessageConvertFactory.getMessage("tempactiveReady"));
			}

			// 验证码已经使用
			if (CheckCodeService.USED == ck.getStatus()) {
				String errMsg = MessageConvertFactory.getMessage("activeError");
				return new ModelAndView(repeatCommit).addObject("mobile",
						loginName).addObject("msgStr", errMsg);
			}

			Integer minuttes = new Integer(AppConf.get(AppConf.sms_interval));
			long time = FormatDate
					.sceondOfTwoDate(ck.getCreateTime(), minuttes);
			if (time == 0) {
				// 如果验证码过期
				String errMsg = MessageConvertFactory.getMessage("mobileValid");
				return new ModelAndView(activeMobileForTemporary).addObject(
						"mobile", loginName).addObject("errMsg", errMsg);
			}

			if (!checkCode.equals(ck.getCheckCode())) {
				String errMsg = MessageConvertFactory.getMessage("mobileEmpty");
				return new ModelAndView(activeMobileForTemporary).addObject(
						"mobile", loginName).addObject("errMsg", errMsg);
			}

			// 增加验证码
			paraMap.put("checkCode", checkCode);
			paraMap.put("origin", ck.getOrigin());
		} else {
			String errMsg = MessageConvertFactory.getMessage("mobileEmpty");
			return new ModelAndView(activeMobileForTemporary).addObject(
					"mobile", loginName).addObject("errMsg", errMsg);
		}

		MemberCriteria memberCriteria = new MemberCriteria();
		memberCriteria.setMemberCode(memberCode);
		memberCriteria.setLoginName(loginName);
		// baseMemberService.queryMemberCriteriaByMemberCodeNsTx(new
		// Long(memberCode));
		if (MemberStatusEnum.NORMAL.getCode()
				.equals(memberCriteria.getStatus())) {
			return new ModelAndView("redirect:/app/accountInfo.htm");
		}
		String formToken = SessionHelper.setToken(TokenEnum.TEMPORARY_REG);
		logger.info("initMemberInfo formToken is ###############" + formToken
				+ "########################");
		paraMap.put("formToken", formToken);
		paraMap.put("criteria", memberCriteria);
		paraMap.put("loginName", loginName);
		return new ModelAndView(initMemberInfo, paraMap);
	}

	/**
	 * 取得会员信息
	 */
	public ModelAndView initTempMemberInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		String memberCode = "";
		String loginName = "";

		String checkCode = request.getParameter("code");
		// 验证验证码是否正确

		if (!StringUtils.isBlank(checkCode)) {
			CheckCodeDto ck = checkCodeService.getByCheckCode(checkCode);
			if (ck == null) {
				String errMsg = MessageConvertFactory.getMessage("emailEmpty");
				return new ModelAndView(repeatCommit).addObject("msgStr",
						errMsg);
			}
			memberCode = String.valueOf(ck.getMemberCode());
			loginName = ck.getEmail();
			request.getSession().setAttribute("memberCode", memberCode);
			request.getSession().setAttribute("loginName", loginName);
			MemberDto member = baseMemberService
					.findMemberByLoginName(loginName);
			if (member == null || member.getStatus() == 1) {
				return new ModelAndView(repeatCommit).addObject("msgStr",
						MessageConvertFactory.getMessage("tempactiveReady"));
			}
			// 验证码已经使用
			if (CheckCodeService.USED == ck.getStatus()) {
				String errMsg = MessageConvertFactory.getMessage("activeError");
				return new ModelAndView(repeatCommit).addObject("email",
						loginName).addObject("msgStr", errMsg);
			}

			Integer dayuttes = new Integer(AppConf.get(AppConf.mail_interval));
			// int day = FormatDate.daysOfTwo(ck.getCreateTime(), new Date());
			Calendar calendar = Calendar.getInstance();
			Date currentDate = calendar.getTime();
			int day = FormatDate.getIntervalDays(ck.getCreateTime(),
					currentDate);

			if (day > dayuttes) {
				// 如果验证码过期
				String errMsg = MessageConvertFactory.getMessage("emailValid");
				return new ModelAndView(activeEmailForTemporary).addObject(
						"email", loginName).addObject("errMsg", errMsg);
			}

			if (!checkCode.equals(ck.getCheckCode())) {
				String errMsg = MessageConvertFactory.getMessage("emailEmpty");
				return new ModelAndView(activeEmailForTemporary).addObject(
						"email", loginName).addObject("errMsg", errMsg);
			}

			paraMap.put("checkCode", checkCode);
			paraMap.put("origin", ck.getOrigin());
		} else {
			String errMsg = MessageConvertFactory.getMessage("emailEmpty");
			return new ModelAndView(activeEmailForTemporary).addObject("email",
					loginName).addObject("errMsg", errMsg);
		}

		MemberCriteria memberCriteria = new MemberCriteria();
		memberCriteria.setMemberCode(memberCode);
		memberCriteria.setLoginName(loginName);
		memberCriteria.setMemberType(2);
		// baseMemberService.queryMemberCriteriaByMemberCodeNsTx(new
		// Long(memberCode));
		String formToken = SessionHelper.setToken(TokenEnum.TEMPORARY_REG);
		paraMap.put("formToken", formToken);
		paraMap.put("criteria", memberCriteria);
		paraMap.put("loginName", loginName);
		logger.info("initTempMemberInfo formToken is ###############"
				+ formToken + "########################");
		return new ModelAndView(initTempMemberInfo, paraMap);
	}

	/**
	 * 更新会员信息
	 */
	public ModelAndView updateMemberInfo(HttpServletRequest req,
			HttpServletResponse response, MemberInfoDto criteria)
			throws Exception {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (!SessionHelper.validateToken(TokenEnum.TEMPORARY_REG,
				criteria.getFormToken())) {
			paraMap.put("msgStr",
					MessageConvertFactory.getMessage("tokenError"));
			return new ModelAndView(repeatCommit, paraMap);
		}
		SafeControllerWrapper request = new SafeControllerWrapper(req,
				PASSWORDS);
		String loginName = (String) request.getSession().getAttribute(
				"loginName");
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(memberCode)) {
			// 如果取不到手机号或者会员号，则跳转到错误页面
			return new ModelAndView(toresultviewwithoutlogin).addObject(
					"returninfo",
					MessageConvertFactory.getMessage("errorRequestMethod"));
		}
		MemberDto member = baseMemberService.findMemberByLoginName(loginName);
		if (member == null || member.getStatus() == 1) {
			return new ModelAndView(repeatCommit).addObject("msgStr",
					MessageConvertFactory.getMessage("tempactiveReady"));
		}
		// ddr modify 2013-1-24
		String checkCode = ServletRequestUtils.getStringParameter(request,
				"checkCode");
		String origin = ServletRequestUtils.getStringParameter(request,
				"origin", CheckCodeOriginEnum.ACTIVE_REGISTER.getValue());
		// 验证验证码是否是正确
		CheckCodeDto checkCodeDto = checkCodeService.getByCheckCodeAndOrigin(
				checkCode, CheckCodeOriginEnum.getCheckCodeOriginEnum(origin));

		if (checkCodeDto == null) {
			paraMap.put("msgStr", "验证链接无效！");
			return new ModelAndView(repeatCommit, paraMap);
		} else if (2 == checkCodeDto.getStatus()) {
			paraMap.put("msgStr", "验证链接已过期！");
			return new ModelAndView(repeatCommit, paraMap);
		}

		String msg = "";
		ValidatorDto vd = UpdateValidator.ValidatorTempDto(criteria);// 校验表单提交的内容
		// 验证支付密码
		String payPassword = request.getParameter("payPassword");
		String payPasswordConfirm = request.getParameter("payPasswordConfirm");
		String loginPassword = request.getParameter("loginPassword");
		String loginPasswordConfirm = request
				.getParameter("loginPasswordConfirm");
		ValidatorDto vdPay = UpdateValidator.validatePayPwd(payPassword,
				payPasswordConfirm);// 校验支付密码
		ValidatorDto vdLogin = UpdateValidator.validateLoginPwd(loginPassword,
				loginPasswordConfirm);// 校验登录密码

		paraMap.put("criteria", criteria);
		paraMap.put("loginName", loginName);

		String formToken = SessionHelper.setToken(TokenEnum.TEMPORARY_REG);
		paraMap.put("formToken", formToken);
		logger.info("updateMemberInfo formToken is ###############" + formToken
				+ "########################");
		// 验证码
		String randCode = request.getSession().getAttribute("rand") == null ? ""
				: request.getSession().getAttribute("rand").toString();
		request.getSession().removeAttribute("rand");
		String code = request.getParameter("randCode") == null ? "" : request
				.getParameter("randCode");
		if ("".equals(randCode) || "".equals(code)
				|| !code.equalsIgnoreCase(randCode)) { // 校验验证码
			paraMap.put("inputName", "randCode");
			paraMap.put("msgStr", MessageConvertFactory.getMessage("randCode"));
			paraMap.put("checkCode", checkCodeDto.getCheckCode());
			paraMap.put("origin", checkCodeDto.getOrigin());
			return new ModelAndView(initMemberInfo, paraMap);
		}

		if (vd.hasErrors()) {
			msg = vd.getError();
			paraMap.put("msgStr", msg);
			return new ModelAndView(initMemberInfo, paraMap);
		}

		if (vdLogin.hasErrors()) {
			msg = vdLogin.getError();
			paraMap.put("msgStr", msg);
			return new ModelAndView(initMemberInfo, paraMap);
		}

		if (vdPay.hasErrors()) {
			msg = vdPay.getError();
			paraMap.put("msgStr", msg);
			return new ModelAndView(initMemberInfo, paraMap);
		}

		criteria.setMemberCode(Long.valueOf(memberCode));

		Integer regType = criteria.getRegType();
		if (regType == null) {
			regType = Integer.valueOf(request.getParameter("regType"));
		}
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		if (regType.equals(1)) {
			checkCodeService.updateCheckCodeStatus2Used(memberCode,
					CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_MOBILE.getValue());
			memberInfoDto.setEmail(criteria.getContact());
		} else {
			checkCodeService.updateCheckCodeStatus2Used(memberCode,
					CheckCodeOriginEnum.ACTIVE_REGISTER_TEMP_EMAIL.getValue());
			memberInfoDto.setMobile(criteria.getContact());

		}

		String mLoginPassword = iMessageDigest.genMessageDigest(loginPassword
				.getBytes());

		memberInfoDto.setTel(criteria.getTel());
		memberInfoDto.setFax(criteria.getFax());
		memberInfoDto.setPassword(loginPassword);
		memberInfoDto.setQq(criteria.getQq());
		memberInfoDto.setMsn(criteria.getMsn());
		memberInfoDto.setAddr(criteria.getAddr());
		memberInfoDto.setMemberCode(Long.valueOf(criteria.getMemberCode()));
		memberInfoDto.setRealName(criteria.getRealName());
		memberInfoDto.setCity(criteria.getCity());
		memberInfoDto.setProvince(criteria.getProvince());
		memberInfoDto.setGreeting(criteria.getGreeting());
		memberInfoDto.setZip(criteria.getZip());
		memberInfoDto.setSecurityAnswer(criteria.getSecurityAnswer());
		memberInfoDto.setSecurityQuestion(criteria.getSecurityQuestion());
		memberInfoDto.setPassword(mLoginPassword);

		String certificateNo = null;
		if (StringUtils.isNotBlank(criteria.getCertificateNo())) {
			certificateNo = DESUtil.encrypt(criteria.getCertificateNo());
		}
		if (StringUtils.isNotBlank(certificateNo)) {
			memberInfoDto.setCertificateNo(certificateNo);
		} else {
			memberInfoDto.setCertificateNo(criteria.getCertificateNo());
		}
		memberInfoDto.setCertificateType(criteria.getCertificateType());

		memberInfoDto.setStatus(MemberStatusEnum.NORMAL.getCode());

		// 验证支付密码和登录密码是否一致 start
		if (payPassword.equals(loginPassword)) {
			paraMap.put("msgStr",
					MessageConvertFactory.getMessage("errorForSamePassword"));
			paraMap.put("classcss", "feedback warning");
			return new ModelAndView(initMemberInfo, paraMap);
		}
		ResultDto result;
		try {
			// 更新临时用户信息
			result = baseMemberService.doUpdateTempMemberInfoRnTx(
					memberInfoDto, new Long(memberCode), payPassword);
		} catch (Exception e) {
			msg = MessageConvertFactory.getMessage("updateFailed");
			paraMap.put("iconNum", "2");
			paraMap.put("msgStr", msg);
			return new ModelAndView(initMemberInfo, paraMap);
		}

		if (StringUtils.isBlank(result.getErrorCode())) { // 修改成功
			paraMap.put("loginName", loginName);
			checkCodeService.updateCheckStateByMemCode(
					Long.parseLong(memberCode), checkCode);// 更新手机验证码状态
			SessionHelper.clearToken(TokenEnum.TEMPORARY_REG);
			return new ModelAndView(activeTempSuccess, paraMap);
		}
		msg = result.getErrorMsg();
		paraMap.put("iconNum", "2");
		paraMap.put("msgStr", msg);
		return new ModelAndView(initMemberInfo, paraMap);
	}

	public String getUpdateMemberInfo() {
		return updateMemberInfo;
	}

	public void setUpdateMemberInfo(String updateMemberInfo) {
		this.updateMemberInfo = updateMemberInfo;
	}

	public String getInitMemberInfo() {
		return initMemberInfo;
	}

	public void setInitMemberInfo(String initMemberInfo) {
		this.initMemberInfo = initMemberInfo;
	}

	public MemberService getBaseMemberService() {
		return baseMemberService;
	}

	public void setBaseMemberService(MemberService baseMemberService) {
		this.baseMemberService = baseMemberService;
	}

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public String getActiveTempSuccess() {
		return activeTempSuccess;
	}

	public void setActiveTempSuccess(String activeTempSuccess) {
		this.activeTempSuccess = activeTempSuccess;
	}

	public void setInputActiveMobile(String inputActiveMobile) {
		this.inputActiveMobile = inputActiveMobile;
	}

	public void setActiveMobileForTemporary(String activeMobileForTemporary) {
		this.activeMobileForTemporary = activeMobileForTemporary;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setToresultviewwithoutlogin(String toresultviewwithoutlogin) {
		this.toresultviewwithoutlogin = toresultviewwithoutlogin;
	}

	public void setInputActiveEmail(String inputActiveEmail) {
		this.inputActiveEmail = inputActiveEmail;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setActiveEmailForTemporary(String activeEmailForTemporary) {
		this.activeEmailForTemporary = activeEmailForTemporary;
	}

	public void setInitTempMemberInfo(String initTempMemberInfo) {
		this.initTempMemberInfo = initTempMemberInfo;
	}

	public String getRepeatCommit() {
		return repeatCommit;
	}

	public void setRepeatCommit(String repeatCommit) {
		this.repeatCommit = repeatCommit;
	}

}
