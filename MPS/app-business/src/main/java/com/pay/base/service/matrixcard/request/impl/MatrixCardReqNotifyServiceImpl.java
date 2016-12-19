package com.pay.base.service.matrixcard.request.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.base.common.helper.matrixcard.DESSecurityUtil;
import com.pay.base.common.util.matrixcard.MatrixCardViewer;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.request.IMatrixCardReqNotifyService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.StringUtil;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public class MatrixCardReqNotifyServiceImpl implements
		IMatrixCardReqNotifyService {
	private IMessageDigest iMessageDigest;
	private IMatrixCardService matrixCardService;
	private CheckCodeService mailService;

	@Override
	public boolean sendReqAttachmentEmail(CheckCode checkCode,
			Date lastValidDate, List<String> recAddress, String subject,
			String url, long templateId, String imgId) {
		boolean flag = false;
		try {
			InputStream imgAttacheMent = printAttachment(imgId);
			String imgName = imgId.concat(".jpg");
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
			// this.mailService.sendMail(checkCode,dateFm.format(lastValidDate),
			// recAddress, subject, url, templateId, imgName, imgAttacheMent);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	// 是否使用通一通知
	private String useUnsp = "1";

	public InputStream printAttachment(String imgId) {

		Long mcId = parseMcId(imgId);
		if (mcId == null) {
			return null;
		}
		MatrixCardDto matrixCardDto = matrixCardService.findById(mcId);
		byte[] imageData = MatrixCardViewer.showUserCard(matrixCardDto);
		return new ByteArrayInputStream(imageData);
	}

	// 验证客户端传入的要求图片显示的加密串
	private Long parseMcId(String idEnc) {
		Long mcId = null;

		String idDec = StringUtil.null2String(DESSecurityUtil.decrypt(idEnc));
		String[] infos = StringUtil.split(idDec, "|");
		if (infos == null || infos.length != 2) {
			return null;
		}
		mcId = Long.valueOf(infos[0]);
		String mcSn = infos[1];
		MatrixCardDto mcDto = matrixCardService.findBySerialNo(mcSn);
		if (mcDto == null) {
			return null;
		}
		if (mcDto.getId().longValue() != mcId) {
			return null;
		}
		return mcId;
	}

	@Override
	public void sendReqSuccEmail(Long mcId, String mcSn, String sessionId,
			String serverName, String contextPath, String emailAddr) {
		// TODO 发送邮件等邮件发送可以发送附件出来后再做
		String fileUrl = "";
		try {
			// 做签名
			// String sign = MD5.md5Hex(mcId + "|" + mcSn);
			MatrixCardDto matrixCardDto = matrixCardService.findById(mcId);
			if (matrixCardDto == null) {
				// return null;
			}
			String sign = iMessageDigest.genMessageDigest(new String(mcId + "|"
					+ matrixCardDto.getSerialNo()).getBytes());
			fileUrl = "https://" + serverName + contextPath
					+ "/common/matrixcard/forUnspOnly/" + sign + mcId + ".jpg";
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (useUnsp.equals("1")) { // 使用统一通知

			// EmailNotifyRequest request = generateEmailNotifyRequest(fileUrl,
			// emailAddr);
			//
			// try {
			// NotifyClientResponse response =
			// unspWebServiceClient.notify(request);
			// String status = response.getStatus();
			// }
			// catch (Exception e) {
			// e.printStackTrace();
			// }

		} else { // 使用一般方式通知

			// sendEmail(mcId, mcSn, sessionId, serverName, contextPath,
			// emailAddr);
		}
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public CheckCodeService getMailService() {
		return mailService;
	}

	public void setMailService(CheckCodeService mailService) {
		this.mailService = mailService;
	}
}
