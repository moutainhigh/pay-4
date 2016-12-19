/**
 *  File: FileProcessController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-13      Sunsea_Li      Changes
 *  
 *
 */
package com.pay.fo.controller.refund;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.common.util.MyControllerUtil;
import com.pay.poss.refund.model.BatchFileInfo;
import com.pay.poss.refund.model.RefundImportFile;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundReconcileResult;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.model.WebRefundUploadDTO;
import com.pay.poss.refund.service.FileProcessService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.JSonUtil;
import com.pay.util.SpringControllerUtils;

/**
 * 充退批次文件处理相关操作
 * 
 * @author Sunsea_Li
 * 
 */
public class FileProcessController extends RefundAbstractController {

	private FileProcessService refundFileService;

	public void setRefundFileService(FileProcessService refundFileService) {
		this.refundFileService = refundFileService;
	}

	/**
	 * 进入查询充退批次文件页面
	 */
	public ModelAndView entryRefundBatchPage(HttpServletRequest request,
			HttpServletResponse response) {
		String viewName = urlMap.get("batchInit");
		return new ModelAndView(viewName, RefundConstants.entryRefundPage());
	}

	/**
	 * 查询充退批次文件分页列表
	 * 
	 * @throws PossException
	 */
	public ModelAndView queryBatchList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossUntxException, PossException {
		String viewName = urlMap.get("batchList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		webQueryRefundDTO.setFileType(RefundConstants.FILE_TYPE_BATCH);
		Page<BatchFileInfo> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = refundFileService.queryBatchFileInfo(resultPage,
				webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 进入查询提交银行充退文件页面
	 */
	public ModelAndView entryRefundFilePage(HttpServletRequest request,
			HttpServletResponse response) {
		String viewName = urlMap.get("fileInit");
		return new ModelAndView(viewName, RefundConstants.entryRefundPage())
				.addObject("bankList", getBankListInf());
	}

	/**
	 * 查询提交银行充退文件分页列表
	 * 
	 * @throws PossException
	 */
	public ModelAndView queryFileList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossUntxException, PossException {
		String viewName = urlMap.get("fileList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		webQueryRefundDTO.setFileType(RefundConstants.FILE_TYPE_BANK);
		Page<BatchFileInfo> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = refundFileService.queryBatchFileInfo(resultPage,
				webQueryRefundDTO);
		model.put("bankList", getBankListInf());
		return new ModelAndView(viewName, model);
	}

	/**
	 * 进入充退文件详细信息页面
	 * 
	 * @throws ServletException
	 */
	public ModelAndView initFileDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("fileDetailInit");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 充退文件详细列表页面
	 * 
	 * @throws ServletException
	 */
	public ModelAndView queryFileDetailList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("fileDetailList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundOrderD> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		model.put("page", resultPage);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 下载批次文件动作
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * @throws PossException
	 */
	public ModelAndView downloadBatchFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			PossException {
		final BatchFileInfo batchFileInfo = new BatchFileInfo();
		bind(request, batchFileInfo, "batchFileInfo", "yyyy-MM-dd");
		String fileType = batchFileInfo.getFileType().toString();// 11:内部概要文件（批次汇总概要文件）；12:内部详细文件；21:外部概要文件（提交银行概要文件）；22:外部详细文件
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		try {
			// File file = new File(URLDecoder.decode("/支付/对账/银联111.xls",
			// "UTF-8"));//文件路径格式参考
			String filePath = CommonConfiguration
					.getStrProperties("batchFilePath")
					+ "/refund"
					+ batchFileInfo.getFilePath();
			File file = new File(URLDecoder.decode(filePath, "UTF-8"));
			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader(
					"Content-Disposition",
					"attachment; filename="
							+ new String((filePath.substring(
									filePath.lastIndexOf('/') + 1,
									filePath.indexOf('.'))).getBytes("UTF-8"),
									"ISO-8859-1")
							+ filePath.substring(filePath.indexOf('.')));
			response.setContentLength((int) file.length());
			byte[] buffer = new byte[2048];
			// 写缓冲区：
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = (-1);
			while ((n = input.read(buffer, 0, 2048)) > -1) {
				output.write(buffer, 0, n);
			}
			// 修改数据库状态为已下载，下载时间，修改下载次数
			batchFileInfo.setBatchFileStatus(new Integer(
					RefundConstants.FILE_STATUS_DOWNLOADED));
			batchFileInfo.setOldStatus(new Integer(
					RefundConstants.FILE_STATUS_CREATED));
			batchFileInfo.setDownloadTime(new Date());
			if (RefundConstants.FILE_TYPE_BATCH.equals(fileType)) {
				batchFileInfo
						.setDlBatchCount(batchFileInfo.getDlBatchCount() + 1);
			} else if (RefundConstants.FILE_TYPE_BANK.equals(fileType)) {
				batchFileInfo
						.setDlBankCount(batchFileInfo.getDlBankCount() + 1);
			}
			refundFileService.updateBatchFileInfo(batchFileInfo);
		} catch (FileNotFoundException e) {// 找不到对应的文件
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
				response.setStatus(HttpServletResponse.SC_OK);
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 进入导入充退银行结果文件页面
	 * 
	 * @throws ServletException
	 */
	public ModelAndView entryFileUploadPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("fileUpload");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 导入充退银行结果文件动作处理 返回到匹配结果信息页面
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView uploadBankFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("resultInit");
		final RefundImportFile refundImportFile = new RefundImportFile();
		bind(request, refundImportFile, "refundImportFile", "yyyy-MM-dd");

		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");

		final BatchFileInfo batchFileInfo = new BatchFileInfo();
		bind(request, batchFileInfo, "batchFileInfo", "yyyy-MM-dd");

		final WebRefundUploadDTO webRefundUploadDTO = new WebRefundUploadDTO();
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest
				.getFile("orginalFile");
		webRefundUploadDTO.setOrginalFile(orginalFile);
		webRefundUploadDTO.setRefundImportFile(refundImportFile);

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model = refundFileService.processUploadRdTx(webRefundUploadDTO);// 处理上传动作
		} catch (PossException e) {
			model.put("err", "系统异常：" + e.getMessage());
			viewName = urlMap.get("fileUpload");
			return new ModelAndView(viewName, model);
		}
		// 此处可先对model返回信息做相关异常或者错误处理再到下面步骤

		// 更新batch_file_info表状态为已导入
		batchFileInfo.setBatchFileStatus(new Integer(
				RefundConstants.FILE_STATUS_IMPORTED));
		batchFileInfo.setImportTime(new Date());
		refundFileService.updateBatchFileInfo(batchFileInfo);
		model.putAll(refundFileService.queryResultStatistics(webQueryRefundDTO));// 获得统计结果
		return new ModelAndView(viewName, model);
	}

	/**
	 * 充退结果信息列表展示 完全匹配交易成功
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView getResultMatchOkList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("resultMatchOk");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundReconcileResult> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = refundFileService
				.queryResultList(resultPage, webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 充退结果信息列表展示 完全匹配交易失败
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView getResultMatchFailList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("resultMatchFail");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundReconcileResult> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = refundFileService
				.queryResultList(resultPage, webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 充退结果信息列表展示 完全匹配交易进行中
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView getResultMatchProcessingList(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, PossException {
		String viewName = urlMap.get("resultMatchProcessing");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundReconcileResult> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = refundFileService
				.queryResultList(resultPage, webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 完全匹配交易进行中 的 成功失败记账处理
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView processSuccess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		// 记账处理成功
		refundFileService.processSucess(webQueryRefundDTO);
		sb.append("success");
		SpringControllerUtils.renderText(response, sb.toString());
		return null;
	}

	/**
	 * 充退结果信息列表展示 不匹配交易
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView getResultMatchDisMatchList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("resultDisMatch");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundReconcileResult> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = refundFileService
				.queryResultList(resultPage, webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 进入查询 充退导入结果 页面
	 */
	public ModelAndView entryImportedFilePage(HttpServletRequest request,
			HttpServletResponse response) {
		String viewName = urlMap.get("importedFileInit");
		return new ModelAndView(viewName, RefundConstants.entryRefundPage())
				.addObject("bankList", getBankListInf());
	}

	/**
	 * 查询 充退导入结果 分页列表
	 * 
	 * @throws PossException
	 */
	public ModelAndView queryImportedFileList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossUntxException, PossException {
		String viewName = urlMap.get("importedFileList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<BatchFileInfo> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = refundFileService.queryRefundImportFile(resultPage,
				webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}

	/**
	 * 进入 查看导入结果详情 初始页 返回到匹配结果信息页面
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView resultDetailInit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("resultInit");

		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");

		Map<String, Object> model = new HashMap<String, Object>();

		model.putAll(refundFileService.queryResultStatistics(webQueryRefundDTO));// 获得统计结果
		return new ModelAndView(viewName, model);
	}

	/**
	 * 将已导入的文件 废除 动作（相当于退回导入动作）
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView dropImportedFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("fileInit");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");

		refundFileService.dropImportedFile(webQueryRefundDTO);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return new ModelAndView(viewName, model).addObject("bankList",
				getBankListInf());
	}

	/**
	 * 确认导入动作 （多个文件一起）
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView confirmImportedBatch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("importedFileInit");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		MyControllerUtil.wrapFileKyList(request, webQueryRefundDTO);
		// 设置登陆用户
		webQueryRefundDTO.setUserId(SessionUserHolderUtil.getLoginId());
		refundFileService.confirmImportedBatch(webQueryRefundDTO);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return new ModelAndView(viewName, model).addObject("bankList",
				getBankListInf());
	}

	/**
	 * 确认导入动作 （单个文件）
	 * 
	 * @throws ServletException
	 * @throws PossException
	 */
	public ModelAndView confirmImportedSingle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossException {
		String viewName = urlMap.get("importedFileInit");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		List<String> fileKys = new ArrayList<String>();
		fileKys.add(webQueryRefundDTO.getFileKy());
		webQueryRefundDTO.setFileKys(fileKys);
		// 设置登陆用户
		webQueryRefundDTO.setUserId(SessionUserHolderUtil.getLoginId());

		refundFileService.confirmImportedBatch(webQueryRefundDTO);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return new ModelAndView(viewName, model).addObject("bankList",
				getBankListInf());
	}

	/**
	 * 废除批次文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws IOException
	 * @see
	 */
	public ModelAndView invalidBatchFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String batchNum = request.getParameter("batchNum1");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userId = SessionUserHolderUtil.getLoginId();
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("batchNum", batchNum);
		outMap.put("userId", userId);

		String jsonString = "";
		try {
			refundFileService.dropBatchInfoRdTx(outMap);
			jsonString = JSonUtil.toJSonString(outMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			outMap.put("infos", e.getMessage());
			jsonString = JSonUtil.toJSonString(outMap);
		}
		out.write(jsonString);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 重成批次
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author Henry.Zeng
	 * @throws IOException
	 * @see
	 */
	public ModelAndView regenerateBatchFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String batchNum = request.getParameter("batchNum1");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Map<String, Object> outMap = new HashMap<String, Object>(1);
		outMap.put("batchNum", batchNum);
		String jsonString = "";
		try {
			refundFileService.reBuildBatchInfo(outMap);
			jsonString = JSonUtil.toJSonString(outMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			outMap.put("infos", e.getMessage());
			jsonString = JSonUtil.toJSonString(outMap);
		}
		out.write(jsonString);
		out.flush();
		out.close();
		return null;
	}

}
