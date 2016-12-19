package com.pay.app.controller.base.matrixcard;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.base.common.helper.matrixcard.DESSecurityUtil;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.change.MatrixCardChangeService;
import com.pay.util.StringUtil;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.controller.matrixcard.util.MatrixCardClientUtil;

/**
 * @author jim_chen
 * @version 2010-9-19
 */
public class MatrixCardChgController extends MultiActionController {
	// 旧卡验证界面
	String chgOldView;
	// 新卡验证界面
	String chgNewView;
	public void setMatrixCardService(IMatrixCardService matrixCardService) {
        this.matrixCardService = matrixCardService;
    }

    // 成功界面
	String succView;
	// 失败界面
	String failView;

	//长度为0
	private static final int zero = 0;
	
	private static final int len=3;
	
	private MatrixCardChangeService matrixCardChangeService;
	private IMatrixCardService  matrixCardService;

	/**
	 * 显示旧卡验证界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView step1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 启动事务
			OperatorInfo operatorInfo = MatrixCardClientUtil.getOperatorInfo(request);
			matrixCardChangeService.startChangeTrans(operatorInfo);
			Long memberCode=operatorInfo.getMemberCode();
			MatrixCardDto matrixCardDto=matrixCardService.findByBindMemberCode(memberCode);
			String serialId = matrixCardDto.getSerialNo();
			// 请求旧卡token
			MatrixCardVfyRequest mcVfyRequest = new MatrixCardVfyRequest();
	
			ResultDto resultDto = matrixCardChangeService.requestOldToken(mcVfyRequest, operatorInfo);
			if (null != resultDto && null == resultDto.getErrorCode()) {
				MatrixCardToken token = (MatrixCardToken) resultDto.getObject();
				return new ModelAndView(chgOldView).addObject("serialId",securityNo(serialId)).addObject("oserialNo",serialId)
				.addObject("otoken", token.getToken()).addObject("oxy0", token.getXy0()).addObject("oxy1", token.getXy1()).addObject("oxy2", token.getXy2());
			}
			else {
				return new ModelAndView(failView).addObject("message", resultDto.getErrorMsg()).addObject("messageExt", "");
			}

		}
		catch (Exception mcException) {
			return new ModelAndView(failView).addObject("message", mcException.getMessage()).addObject("messageExt", "");
		}
	}

    private static String securityNo(String serialNo){
        String serialNoDes="";
        
        int alen=serialNo.length();
      if(len>0){
          serialNoDes=serialNo.substring(0,len)+serialNo.substring(len, alen-len).replaceAll("\\w", "*")+serialNo.substring(alen-len, alen);
      }  
       return serialNoDes;
    }

	/**
	 * 显示新卡验证界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView step2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			OperatorInfo operatorInfo = MatrixCardClientUtil.getOperatorInfo(request);

			// 收集输入的参数
			String oserialNo = StringUtil.null2String(request.getParameter("osequenceId")).trim();
			String otoken = StringUtil.null2String(request.getParameter("otoken"));
			String ostrValue0 = StringUtil.null2String(request.getParameter("opos0")).trim();
			String ostrValue1 = StringUtil.null2String(request.getParameter("opos1")).trim();
			String ostrValue2 = StringUtil.null2String(request.getParameter("opos2")).trim();

			if (oserialNo.length() == zero || otoken.length() == zero || ostrValue0.length() == zero || ostrValue1.length() == zero || ostrValue2.length() == zero) {
				MatrixCardVfyRequest mcVfyRequest = new MatrixCardVfyRequest();
				ResultDto resultDto = matrixCardChangeService.requestOldToken(mcVfyRequest, operatorInfo);
				if (null != resultDto && null == resultDto.getErrorCode()) {
					MatrixCardToken token = (MatrixCardToken) resultDto.getObject();
					return new ModelAndView(chgOldView).addObject("otoken", token.getToken()).addObject("oxy0", token.getXy0()).addObject("oxy1", token.getXy1()).addObject("oxy2", token.getXy2())
					        .addObject("oserialNo", oserialNo).addObject("message", MessageConvertFactory.getMessage("matrixCardChgMsg"));
				}

			}

			// 对收集到得数据加密
			StringBuffer oldtoken = new StringBuffer();
			oldtoken.append(oserialNo + ";");
			oldtoken.append(otoken + ";");
			oldtoken.append(ostrValue0 + ";");
			oldtoken.append(ostrValue1 + ";");
			oldtoken.append(ostrValue2 + ";");
			String oldtokencode = DESSecurityUtil.encrypt(oldtoken.toString());

			// 请求新卡token
			MatrixCardVfyRequest mcVfyRequest = new MatrixCardVfyRequest();
			ResultDto resultDto = matrixCardChangeService.requestNewToken(mcVfyRequest, operatorInfo);
			if (null != resultDto && null == resultDto.getErrorCode()) {
				MatrixCardToken token = (MatrixCardToken) resultDto.getObject();
				return new ModelAndView(chgNewView).addObject("ntoken", token.getToken()).addObject("nxy0", token.getXy0()).addObject("nxy1", token.getXy1()).addObject("nxy2", token.getXy2())
				        .addObject("oldtokencode", oldtokencode);
			}
			else {
				return new ModelAndView(failView).addObject("message", resultDto.getErrorMsg()).addObject("messageExt", "").addObject("serialNo",StringUtil.null2String(request.getParameter("osequenceId")).trim());
			}

		}
		catch (Exception mcException) {
			return new ModelAndView(failView).addObject("serialNo",StringUtil.null2String(request.getParameter("osequenceId")).trim())
			.addObject("message", mcException.getMessage()).addObject("messageExt", "").addObject("backUrl", "matrixcard/chg.htm?method=step1");
		}
	}

	/**
	 * 换卡处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView change(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			OperatorInfo operatorInfo = MatrixCardClientUtil.getOperatorInfo(request);
			// 获取输入参数
			String oldtokencode = StringUtil.null2String(request.getParameter("oldtokencode"));
			String nserialNo = StringUtil.null2String(request.getParameter("nsequenceId")).trim();
			String ntoken = StringUtil.null2String(request.getParameter("ntoken"));
			String nstrValue0 = StringUtil.null2String(request.getParameter("npos0")).trim();
			String nstrValue1 = StringUtil.null2String(request.getParameter("npos1")).trim();
			String nstrValue2 = StringUtil.null2String(request.getParameter("npos2")).trim();

			if (nserialNo.length() == zero || ntoken.length() == zero || nstrValue0.length() == zero || nstrValue1.length() == zero || nstrValue2.length() == zero) {
				MatrixCardVfyRequest mcVfyRequest = new MatrixCardVfyRequest();
				ResultDto resultDto = matrixCardChangeService.requestNewToken(mcVfyRequest, operatorInfo);
				if (null != resultDto && null == resultDto.getErrorCode()) {
					MatrixCardToken token = (MatrixCardToken) resultDto.getObject();
					return new ModelAndView(chgNewView).addObject("ntoken", token.getToken()).addObject("nxy0", token.getXy0()).addObject("nxy1", token.getXy1()).addObject("nxy2", token.getXy2())
					        .addObject("serialNo", nserialNo).addObject("oldtokencode", oldtokencode).addObject("message", MessageConvertFactory.getMessage("matrixCardChgNewMsg"));
				}

			}

			// 旧卡token加密串解密
			String oldtokenDec = DESSecurityUtil.decrypt(oldtokencode);
			String[] olddatas = oldtokenDec.split(";");
			String oserialNo = olddatas[0];
			String otoken = olddatas[1];
			String ostrValue0 = olddatas[2];
			String ostrValue1 = olddatas[3];
			String ostrValue2 = olddatas[4];

			MatrixCardVfyDto oldMatrixCardVfyDto = new MatrixCardVfyDto();
			oldMatrixCardVfyDto.setSerialNo(oserialNo);
			oldMatrixCardVfyDto.setToken(otoken);
			oldMatrixCardVfyDto.setValue0(ostrValue0);
			oldMatrixCardVfyDto.setValue1(ostrValue1);
			oldMatrixCardVfyDto.setValue2(ostrValue2);

			oldMatrixCardVfyDto.setVfyDate(new Date());
			oldMatrixCardVfyDto.setVfyIp(operatorInfo.getIp());
			oldMatrixCardVfyDto.setVfyMemberCode(operatorInfo.getMemberCode());

			MatrixCardVfyDto newMatrixCardVfyDto = new MatrixCardVfyDto();
			newMatrixCardVfyDto.setSerialNo(nserialNo);
			newMatrixCardVfyDto.setToken(ntoken);
			newMatrixCardVfyDto.setValue0(nstrValue0);
			newMatrixCardVfyDto.setValue1(nstrValue1);
			newMatrixCardVfyDto.setValue2(nstrValue2);

			// 执行换卡业务
			ResultDto resultDto = matrixCardChangeService.change(operatorInfo.getU_id(), oldMatrixCardVfyDto, newMatrixCardVfyDto);
			if (null != resultDto && null == resultDto.getErrorCode()){
				return new ModelAndView(succView).addObject("serialNo",nserialNo);
			}else{
				//TODO 失败后的处理
				return new ModelAndView(failView).addObject("serialNo",nserialNo).addObject("message",resultDto.getErrorMsg());
			}
		}
		catch (Exception mcException) {
			return new ModelAndView(failView).addObject("serialNo",StringUtil.null2String(request.getParameter("nsequenceId")).trim())
			.addObject("message", mcException.getMessage()).addObject("messageExt", "").addObject("backUrl", "matrixcard/chg.htm?method=step1");
		}
	}
	

	public void setSuccView(String succView) {
		this.succView = succView;
	}

	public void setChgOldView(String chgOldView) {
		this.chgOldView = chgOldView;
	}

	public void setChgNewView(String chgNewView) {
		this.chgNewView = chgNewView;
	}

	public void setFailView(String failView) {
		this.failView = failView;
	}

	public void setMatrixCardChangeService(MatrixCardChangeService matrixCardChangeService) {
		this.matrixCardChangeService = matrixCardChangeService;
	}

	

}
