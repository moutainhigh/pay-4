package com.pay.app.controller.base.email;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.member.dto.IndividualInfoDto;
import com.pay.acc.member.service.IndividualInfoService;

/**
 * 会员激活邮箱
 * 
 * 
 */
public class ActiveEmailController extends MultiActionController {

	private final Log log = LogFactory.getLog(ActiveEmailController.class);
	private CheckCodeService checkCodeService;
	private IndividualInfoService individualInfoService;

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		
		response.setCharacterEncoding("utf-8");
		
		String checkCode = request.getParameter("code") == null ? "" : request
				.getParameter("code");

		CheckCodeDto checkCodeDto = checkCodeService.getByCheckCodeAndOrigin(
				checkCode, CheckCodeOriginEnum.ACTIVE_EMAIL);

		if (null != checkCodeDto) {

			IndividualInfoDto individualInfoDto = individualInfoService
					.queryIndividualInfoByMemberCode(checkCodeDto
							.getMemberCode());
			individualInfoDto.setEmail(checkCodeDto.getEmail());
			individualInfoService
					.updateIndividualInfoByMemberCode(individualInfoDto);
			try {
				response.getWriter().print("激活邮箱成功！");
			} catch (IOException e) {
				log.error("active email error:", e);
			}
			return null;
		} else {
			try {
				response.getWriter().print("非法请求！");
			} catch (IOException e) {
				log.error("active email error:", e);
			}
		}

		return null;
	}
}
