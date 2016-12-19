package com.pay.app.controller.base.matrixcard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.base.common.helper.matrixcard.DESSecurityUtil;
import com.pay.base.common.util.matrixcard.MatrixCardViewer;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.request.IMatrixCardReqService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;

/**
 * 1.为统一通知服务提供口令卡下载(ip限制) 2.口令卡的查看（卡的id加密） 3.口令卡tag支持
 * 
 * @author jim_chen
 * 
 */
public class MatrixCardViewController extends MultiActionController {

	private IMatrixCardReqService matrixCardReqService;
	private IMatrixCardService matrixCardService;
	private IMessageDigest iMessageDigest;

	// 只允许特定ip访问
	// private Map<String,String> allowIp = new HashMap<String,String>();
	private List<String> allowIp = new ArrayList<String>();

	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = StringUtil.null2String(request.getParameter("method"));

		if (!"".equals(method)) {
			return super.handleRequestInternal(request, response);
		}

		// 是否为tag提供支持
		String uri = request.getRequestURI();
		if (uri.startsWith(request.getContextPath() + "/common/matrixcard/view.htm")) {
			return tagSupport(request, response);
		}
		// 为统一通知提供下载
		return createAttachMent(request, response);

	}

	/**
	 * 为统一通知服务生成附件;
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ModelAndView createAttachMent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String clientIp = WebUtil.getClientIP(request);

		// 检查ip限制， 只能由统一通知服务器访问
		if (clientIp == null || !allowIp.contains(clientIp)) {
			return null;
		}

		// 是否准备下载图片
		String uri = request.getRequestURI();
		String prefix = request.getContextPath() + "/common/matrixcard/forUnspOnly/";
		if (uri.startsWith(prefix)) {
			int endPos = uri.lastIndexOf(".");

			String sign = uri.substring(prefix.length(), prefix.length() + 32);
			Long mcId = Long.valueOf(uri.substring(prefix.length() + 32, endPos));

			// 验证签名
			MatrixCardDto matrixCardDto = matrixCardService.findById(mcId);
			if (matrixCardDto == null) {
				return null;
			}
			// 检验生成的图片名称，与现在生成的名称是否一至
			String signSrc = iMessageDigest.genMessageDigest(new String(mcId + "|" + matrixCardDto.getSerialNo()).getBytes());
			if (!signSrc.equalsIgnoreCase(sign)) {
				return null;
			}
			String sessionId = request.getSession().getId();

			response.setHeader("Content-disposition", "attachment; filename=" + sign + mcId + ".jpg");

			return download(mcId, sessionId, request, response);
		}

		return null;
	}

	private ModelAndView download(Long mcId, String sessionId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (StringUtil.isNull(mcId)) {
			return null;
		}

		byte[] imageData = matrixCardReqService.download(mcId + "", sessionId);

		printImg(imageData, request, response);

		return null;
	}

	// 下载图片
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long mcId = parseMcId(request.getParameter("id"));
		if (mcId == null) {
			return null;
		}
		String sessionId = request.getSession().getId();
		String signSrc = iMessageDigest.genMessageDigest(sessionId.getBytes());

		// response.setHeader("Content-disposition", "attachment; filename=" + signSrc + ".jpg");

		String fileSrc = signSrc.concat(".jpg");
		String fileName = URLEncoder.encode(fileSrc, "UTF-8");
		if (fileName.length() > 150) {// 解决IE 6.0 bug
			fileName = new String(fileSrc.getBytes("GBK"), "ISO-8859-1");
		}
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");

		return download(mcId, sessionId, request, response);
	}

	// 为servlet提供显示方法
	public ModelAndView tagSupport(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		String type = StringUtil.null2String(request.getParameter("type"));
		String pos = StringUtil.null2String(request.getParameter("pos"));
		String imgId = StringUtil.null2String(request.getParameter("imgId"));

		byte[] imageData = null;
		if (type.equalsIgnoreCase("demo")) {
			imageData = MatrixCardViewer.showDemo();
		}
		else if (type.equalsIgnoreCase("pos")) {
			String[] setPos = pos.split(",");
			imageData = MatrixCardViewer.showPosition(StringUtil.null2String(setPos[0]), StringUtil.null2String(setPos[1]), StringUtil.null2String(setPos[2]));
		}
		else if (type.equalsIgnoreCase("display")) {
			Long mcId = parseMcId(imgId);
			if (mcId == null) {
				return null;
			}

			MatrixCardDto matrixCardDto = matrixCardService.findById(mcId);

			// if ("1".equals(MatrixCardReqNotifyServiceImpl.getUseUnsp())) {
			//
			// Calendar calCreationDate = Calendar.getInstance();
			// calCreationDate.setTime(matrixCardDto.getCreationDate());
			//
			// Calendar calValidateDate = Calendar.getInstance();
			// calValidateDate.set(2010, 1, 1);
			//
			// // 2010.2.1以后的申请的口令卡，如果状态为绑定状态，则不能查看和下载
			// if (calCreationDate.after(calValidateDate) && matrixCardDto.getStatus() != MatrixCardStatus.CREATE.getValue()) {
			// return null;
			// }
			//
			// }
			imageData = MatrixCardViewer.showUserCard(matrixCardDto);
		}
		if (null != imageData) {
			printImg(imageData, request, response);
		}

		return null;
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

	private void printImg(byte[] imageData, HttpServletRequest request, HttpServletResponse response) {

		ByteArrayOutputStream baos = null;
		ByteArrayInputStream in = null;
		ServletOutputStream out = null;

		try {
			byte[] buff = new byte[4096];

			baos = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(imageData);
			while (-1 != in.read(buff)) {
				baos.write(buff);
			}
			in.close();
			in = null;

			// 设置下载数据类型
			response.setContentType("image/jpeg");

			// 设置下载文件长度
			response.setContentLength(baos.size());
			// 将byte流中的数据写入输出流中
			out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
			out.close();
			baos.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != baos) {
					baos.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void setAllowIpStr(String allowIpCfg) {
		if (null == allowIpCfg || allowIpCfg.length() <= 0) {
			return;
		}
		allowIp.clear();
		String[] ipstrArr = allowIpCfg.split(",");
		for (int i = 0; i < ipstrArr.length; i++) {
			allowIp.add(ipstrArr[i]);
		}
	}

	public void setMatrixCardReqService(IMatrixCardReqService matrixCardReqService) {
		this.matrixCardReqService = matrixCardReqService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

}
