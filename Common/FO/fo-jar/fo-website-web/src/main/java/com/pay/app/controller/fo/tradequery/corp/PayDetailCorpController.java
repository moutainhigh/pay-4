/**
 *  File: PayDetailPersonalController
 *  Description:个人单笔记录查询
 *  Date        Author      Changes
 *  2011-4-14   Sandy		create
 *  
 */
package com.pay.app.controller.fo.tradequery.corp;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.controller.fo.tradequery.BaseTradeQueryController;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.base.PayToAcctOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.util.Trans2RMB;
import com.pay.tradequery.service.PayDetailService;
import com.pay.util.StringUtil;

/**
 * @Description:企业付款记录查询
 * @author Sandy
 */
@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
public class PayDetailCorpController extends BaseTradeQueryController {

	private PayDetailService payDetailService;
	
	private PayToAcctOrderService payToAcctOrderService;

	/**
	 * 批量付款请求基本信息服务类
	 */
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;

	/**
	 * 批量付款订单服务类
	 */
	private BatchPaymentOrderService batchPaymentOrderService;

	/**
	 * 出款订单服务类
	 */
	private FundoutOrderService fundoutOrderService;

	/**
	 * 会员查询服务类
	 */
	private MemberQueryFacadeService memberQueryFacadeService;

	/**
	 * 默认页
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_DETAIL")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return queryPayDetail(request, response);
	}

	/**
	 * 付款记录集合查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_DETAIL")
	public ModelAndView queryPayDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView payDetailView = new ModelAndView(payDetailCorp);
		if (null == request.getParameter("subType") || "".equals(request.getParameter("subType"))) {
			payDetailView.addObject("startTime", getDate(-2));
			payDetailView.addObject("endTime", getDate(+1));
			return payDetailView;
		}
		int pager_offset = 1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset = Integer.parseInt(request.getParameter("pager_offset"));
		}
		Map<String, Object> map = validatQueryPara(request);
		String export = request.getParameter("export");
		Page<Map<String, Object>> payDetailPage = payDetailService.queryPayDetailList(map, pager_offset, pageSize);
		if (StringUtils.isNotBlank(export)) {
			if("1".equals(export)){
				Page<Map<String, Object>> dateList = payDetailService.queryPayDetailList(map, 1, payDetailPage.getTotalCount());
				return exportResult(request, response, dateList.getResult(), exportPayDetailCorp);
			}else if("2".equals(export)){
				Page<Map<String, Object>> dateList = payDetailService.queryPayDetailList(map, 1, payDetailPage.getTotalCount());
				return exportCsvResult(request, response, dateList.getResult(), exportPayDetailCorpCsv);
			}
			
		}
		PageUtil pageUtil = new PageUtil(pager_offset, pageSize, payDetailPage.getTotalCount());
		payDetailView.addObject("payDetailList", payDetailPage.getResult());
		payDetailView.addObject("pu", pageUtil);

		// 页面返回查询条件
		payDetailView.addObject("startTime", map.get("startTime"));
		payDetailView.addObject("endTime", map.get("endTime"));
		payDetailView.addObject("payStatus", map.get("payStatus"));
		payDetailView.addObject("payChannel", map.get("payChannel"));
		return payDetailView;
	}

	/**
	 * 单笔查询入参验证
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> validatQueryPara(HttpServletRequest request) {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		// 获取入参
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String payStatus = request.getParameter("payStatus");
		String payChannel = request.getParameter("payChannel");
		if (!StringUtils.isBlank(startTime)) {
			map.put("startTime", getStartTime(startTime));
		}
		if (!StringUtils.isBlank(endTime)) {
			map.put("endTime", getEndTime(endTime));
		}
		if (!StringUtils.isBlank(payStatus)) {
			map.put("payStatus", payStatus);
		}
		if (!StringUtils.isBlank(payChannel)) {
			map.put("payChannel", payChannel);
			if ("1".equals(payChannel)) {
				map.put("payType", payChannel);
			} else {
				map.put("payType", "other");// 此处表示所有银行卡的付款.(后期可能会有易卡支付,逻辑需要变动 )
			}
		}
		return map;
	}

	/**
	 * 批量付款记录集合查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_BATCH_DETAIL")
	public ModelAndView queryPayBatchDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView payDetailView = new ModelAndView(payBatchDetailCorp);
		if (null == request.getParameter("subType") || "".equals(request.getParameter("subType"))) {
			payDetailView.addObject("startTime", getDate(-2));
			payDetailView.addObject("endTime", getDate(+1));
			return payDetailView;
		}
		int pager_offset = 1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset = Integer.parseInt(request.getParameter("pager_offset"));
		}
		Map<String, Object> map = validatBatchQueryPara(request);
		String export = request.getParameter("export");
		Page<Map<String, Object>> payDetailPage = payDetailService.queryBatchPayDetailList(map, pager_offset, pageSize);
		if (StringUtils.isNotBlank(export)) {
			if("1".equals(export)){
				Page<Map<String, Object>> dateList = payDetailService.queryBatchPayDetailList(map, 1, payDetailPage.getTotalCount());
				return exportResult(request, response, dateList.getResult(), exportPayBatchDetailCorp);
			}else if("2".equals(export)){
				Page<Map<String, Object>> dateList = payDetailService.queryBatchPayDetailList(map, 1, payDetailPage.getTotalCount());
				return exportCsvResult(request, response, dateList.getResult(), exportPayBatchDetailCorpCsv);
			}
		}
		PageUtil pageUtil = new PageUtil(pager_offset, pageSize, payDetailPage.getTotalCount());
		payDetailView.addObject("payDetailList", payDetailPage.getResult());
		payDetailView.addObject("pu", pageUtil);

		// 页面返回查询条件
		payDetailView.addObject("startTime", map.get("startTime"));
		payDetailView.addObject("endTime", map.get("endTime"));
		payDetailView.addObject("payStatus", request.getParameter("payStatus"));
		payDetailView.addObject("serialNo", map.get("serialNo"));
		payDetailView.addObject("reCheck", map.get("reCheck"));
		payDetailView.addObject("payChannel", map.get("payChannel"));
		return payDetailView;
	}

	/**
	 * 批量付款记录查询入参验证
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> validatBatchQueryPara(HttpServletRequest request) {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		// 获取入参
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String payStatus = request.getParameter("payStatus");
		String serialNo = request.getParameter("serialNo");
		String reCheck = request.getParameter("reCheck");
		String payChannel = request.getParameter("payChannel");
		if (!StringUtils.isBlank(startTime)) {
			map.put("startTime", validateDate(startTime));
		}
		if (!StringUtils.isBlank(endTime)) {
			map.put("endTime", validateDate(endTime));
		}
		if (StringUtils.isNotEmpty(payStatus)) {
			map.put("payStatus", payStatus);
		}
		if (!StringUtils.isBlank(serialNo)) {
			map.put("serialNo", serialNo);
		}
		if (!StringUtils.isBlank(reCheck)) {
			map.put("reCheck", reCheck);
		}
		if (!StringUtils.isBlank(payChannel)) {
			map.put("payChannel", payChannel);
		}
		return map;
	}

	/**
	 * 单条付款记录详情查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_DETAIL_SINGLE")
	public ModelAndView querySinglePayDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(singlePayDetailCorp);
		LoginSession loginSession = SessionHelper.getLoginSession();
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = loginSession.getMemberCode();
		String sequenceId = request.getParameter("serialNo");
		String channel = request.getParameter("channel");
		String busiType = request.getParameter("busiType");
		String refund = request.getParameter("refund");
		String flow = "0";
		String flag = request.getParameter("flag");
		if (StringUtils.isNotBlank(flag)) {
			flow = "1";
		}
		if (!StringUtils.isNumeric(sequenceId)) {
			return singleView;
		}
		if (StringUtils.isNotBlank((channel))) {
			map.put("channel", channel);
		}
		if (StringUtils.isNotBlank((busiType))) {
			map.put("busiType", busiType);
		}
		map.put("memberCode", memberCode);
		map.put("sequenceId", sequenceId);
		map.put("flag", flow);
		// 查询
		Map<String, Object> resultMap = payDetailService.querySinglePayDetail(map);
		if (null == resultMap) {
			return singleView;
		}
		if (loginSession.getLoginName().equals(resultMap.get("payyBankAccNo"))) {// 收入
			flow = "1";
			map.put("flag", flow);
			resultMap = payDetailService.querySinglePayDetail(map);
		}

		singleView.addObject("map", resultMap);
		singleView.addObject("channel", channel);
		if ("1".equals(refund)) {
			singleView.addObject("refund", refund);
		}
		singleView.addObject("flag", flow);
		singleView.addObject("memberCode", memberCode);
		return singleView;
	}

	/**
	 * 单笔付款到银行付款凭证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_DETAIL_SINGLE")
	public ModelAndView queryPayToBankCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView singleView = new ModelAndView(payToBankCertificate);
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		String businessBatchNo = StringUtil.null2String(request.getParameter("businessBatchNo"));

		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(new Long(memberCode));
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String sequenceId = request.getParameter("serialNo");
		String channel = request.getParameter("channel");

		if (!StringUtils.isNumeric(sequenceId)) {
			return new ModelAndView(singlePayDetailCorp);
		}
		if (StringUtils.isNotBlank((channel))) {
			map.put("channel", channel);
		}
		map.put("memberCode", memberCode);
		map.put("sequenceId", sequenceId);

		Map<String, Object> resultMap = payDetailService.queryPayToBankCertificate(map);
		if (null == resultMap) {
			return new ModelAndView(singlePayDetailCorp);
		}
		String amount = String.valueOf(resultMap.get("amount"));
		String fee = String.valueOf(resultMap.get("fee"));
		String fundoutBankcode = String.valueOf(resultMap.get("fundoutBankcode"));

		Long amountL = StringUtils.isNotEmpty(amount) ? Long.valueOf(amount) : 0;
		Long feeL = StringUtils.isNotEmpty(fee) ? Long.valueOf(fee) : 0;
		Long foAmountL = amountL + feeL;

		BigDecimal amountBig = new BigDecimal(amountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal feeBig = new BigDecimal(feeL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal foAmountBig = new BigDecimal(foAmountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);

		String amoutStr = Trans2RMB.processNum(amountBig.toString());
		String feeStr = Trans2RMB.processNum(feeBig.toString());
		String foAmountStr = Trans2RMB.processNum(foAmountBig.toString());

		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class<?> gclass = loader.parseClass(new File("/opt/pay/config/poss/BankInfo.groovy"));
		GroovyObject groovyObject = (GroovyObject) gclass.newInstance();
		String bankNo = (String) groovyObject.invokeMethod("getBankNo", fundoutBankcode);
		String bankAddress = (String) groovyObject.invokeMethod("getBankAddress", fundoutBankcode);

		String payeeBankNo = String.valueOf(resultMap.get("payeeLoginName"));
		singleView.addObject("map", resultMap);
		singleView.addObject("channel", channel);
		singleView.addObject("memberCode", memberCode);
		singleView.addObject("memberInfo", memberInfo);
		singleView.addObject("amoutStr", amoutStr);
		singleView.addObject("feeStr", feeStr);
		singleView.addObject("foAmountStr", foAmountStr);
		singleView.addObject("foAmountL", foAmountL);
		singleView.addObject("bankNo", bankNo);
		singleView.addObject("bankAddress", bankAddress);
		singleView.addObject("payeeBankNo", payeeBankNo);
		singleView.addObject("businessBatchNo", businessBatchNo);
		return singleView;
	}

	/**
	 * 下载单笔付款到银行付款凭证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_DETAIL_SINGLE")
	public ModelAndView downloadPayToBankCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();

		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(new Long(memberCode));
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String sequenceId = request.getParameter("serialNo");
		String channel = request.getParameter("channel");
		String businessBatchNo = StringUtil.null2String(request.getParameter("businessBatchNo"));

		if (StringUtils.isNotBlank((channel))) {
			map.put("channel", channel);
		}
		map.put("memberCode", memberCode);
		map.put("sequenceId", sequenceId);

		Map<String, Object> resultMap = payDetailService.queryPayToBankCertificate(map);
		if (null == resultMap) {
			return new ModelAndView(singlePayDetailCorp);
		}
		
		/* 金额小写转大写begin */
		String amount = String.valueOf(resultMap.get("amount"));
		String fee = String.valueOf(resultMap.get("fee"));
		String fundoutBankcode = String.valueOf(resultMap.get("fundoutBankcode"));

		Long amountL = StringUtils.isNotEmpty(amount) ? Long.valueOf(amount) : 0;
		Long feeL = StringUtils.isNotEmpty(fee) ? Long.valueOf(fee) : 0;
		Long foAmountL = amountL + feeL;

		BigDecimal amountBig = new BigDecimal(amountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal feeBig = new BigDecimal(feeL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal foAmountBig = new BigDecimal(foAmountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);

		String amoutStr = Trans2RMB.processNum(amountBig.toString());
		String feeStr = Trans2RMB.processNum(feeBig.toString());
		String foAmountStr = Trans2RMB.processNum(foAmountBig.toString());
		/* 金额小写转大写end */

		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class<?> gclass = loader.parseClass(new File("/opt/pay/config/poss/BankInfo.groovy"));
		GroovyObject groovyObject = (GroovyObject) gclass.newInstance();
		String bankNo = (String) groovyObject.invokeMethod("getBankNo", fundoutBankcode);
		String bankAddress = (String) groovyObject.invokeMethod("getBankAddress", fundoutBankcode);

		String payeeBankNo = String.valueOf(resultMap.get("payeeLoginName"));

		try {
			File _file = new File("/opt/pay/config/poss/payToBank.jpg");
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);

			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			g.setColor(Color.black);
			g.setFont(new Font("宋体", 20, 12));

			g.drawString(businessBatchNo, 112, 150);// 商家批次号
			g.drawString(String.valueOf(resultMap.get("sequenceId")), 583, 150); // 交易号

			// 付款方名称
			String memberName = StringUtil.null2String(memberInfo.getMemberName());
			if (null != memberName && memberName.length() > 26) {
				String oneLine = memberName.substring(0, 26);
				g.drawString(oneLine, 189, 235); // 名称
				String twoLine = memberName.substring(26, memberName.length());
				g.drawString(twoLine, 189, 235 + 20); // 名称
			} else {
				g.drawString(memberName, 189, 245); // 名称
			}

			g.drawString(memberInfo.getLoginName(), 189, 286);// 支付账户
			g.drawString("启赟金融信息服务有限公司", 189, 330);// 协议代付方户名
			g.drawString(bankAddress, 189, 374);// 协议代付方开户行
			g.drawString(bankNo, 189, 418); // 协议代付方账号
			g.drawString(amoutStr, 189, 462);// 代付金额(大写)
			g.drawString(feeStr, 189, 504);// 费用(大写)
			g.drawString(foAmountStr, 189, 548);// 出账金额(大写)
			if (StringUtils.isNotEmpty(businessBatchNo)) {
				g.drawString("批量付款到银行卡", 189, 590);// 交易类型
			} else {
				g.drawString("付款到银行卡", 189, 590);// 交易类型
			}

			// 收款方名称
			String payeeName = StringUtil.null2String(resultMap.get("payeeName"));
			if (null != payeeName && payeeName.length() > 22) {
				String oneLine = payeeName.substring(0, 22);
				g.drawString(oneLine, 689, 235); // 名称
				String twoLine = payeeName.substring(22, payeeName.length());
				g.drawString(twoLine, 689, 235 + 20); // 名称
			} else {
				g.drawString(payeeName, 689, 245); // 名称
			}

			g.drawString(payeeBankNo, 689, 287); // 银行账号

			// 开户行
			String bankBranch = StringUtil.null2String(resultMap.get("bankBranch"));
			if (null != bankBranch && bankBranch.length() > 22) {
				String oneLine = bankBranch.substring(0, 22);
				g.drawString(oneLine, 689, 315); // 名称
				String twoLine = bankBranch.substring(22, bankBranch.length());
				g.drawString(twoLine, 689, 315 + 20); // 名称
			} else {
				g.drawString(bankBranch, 689, 330); // 开户行
			}

			g.drawString(amountBig.toString() + "元", 689, 462); // 代付金额
			g.drawString(feeBig.toString() + "元", 689, 504); // 费用
			g.drawString(foAmountBig.toString() + "元", 689, 548);// 出账金额
			g.drawString(String.valueOf(resultMap.get("createTime")), 689, 590);// 付款时间

			// 备注
			String remark = StringUtil.null2String(resultMap.get("remark"));
			if (null != remark && remark.length() > 60) {
				String oneLine = remark.substring(0, 60);
				g.drawString(oneLine, 62, 621); // 名称
				String twoLine = remark.substring(60, remark.length());
				g.drawString(twoLine, 62, 637); // 名称
			} else {
				g.drawString(remark, 62, 631);// 备注
			}

			g.dispose();

			try {
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response.setContentType("image/x-png");
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("付款凭证.jpg", "UTF8"));
				OutputStream out = response.getOutputStream();
				ImageIO.write(image, "png", out);
				out.flush();
				out.close();
				return null;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 批量付款到银行付款凭证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_BATCH_DETAIL_SINGLE")
	public ModelAndView queryPayToBankBatchCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView singleView = new ModelAndView(payToBankBatchCertificate);
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();

		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(new Long(memberCode));
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		String channel = request.getParameter("channel");

		if (!StringUtils.isNumeric(serialNo)) {
			return singleView;
		}
		if (StringUtils.isNotBlank((channel))) {
			map.put("channel", channel);
		}
		map.put("memberCode", memberCode);
		map.put("serialNo", serialNo);

		Map<String, Object> resultMap = payDetailService.querySinglePayBatchDetail(map);
		if (null == resultMap) {
			return new ModelAndView(singlePayDetailCorp);
		}

		String amount = String.valueOf(resultMap.get("successAmount"));
		String fee = String.valueOf(resultMap.get("fee"));

		Long amountL = StringUtils.isNotEmpty(amount) ? Long.valueOf(amount) : 0;
		Long feeL = StringUtils.isNotEmpty(fee) ? Long.valueOf(fee) : 0;
		Long foAmountL = (amountL <= 0) == true ? 0 : amountL + feeL;

		BigDecimal amountBig = new BigDecimal(amountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal feeBig = new BigDecimal(feeL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal foAmountBig = new BigDecimal(foAmountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);

		String amoutStr = Trans2RMB.processNum(amountBig.toString());
		String feeStr = Trans2RMB.processNum(feeBig.toString());
		String foAmountStr = Trans2RMB.processNum(foAmountBig.toString());

		singleView.addObject("map", resultMap);
		singleView.addObject("channel", channel);
		singleView.addObject("memberCode", memberCode);
		singleView.addObject("memberInfo", memberInfo);
		singleView.addObject("amoutStr", amoutStr);
		singleView.addObject("feeStr", feeStr);
		singleView.addObject("foAmountStr", foAmountStr);
		singleView.addObject("foAmountL", foAmountL);
		return singleView;
	}

	/**
	 * 下载批量付款到银行付款凭证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_DETAIL_SINGLE")
	public ModelAndView downloadPayToBankBatchCertificate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();

		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(new Long(memberCode));
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		String channel = request.getParameter("channel");

		if (StringUtils.isNotBlank((channel))) {
			map.put("channel", channel);
		}
		map.put("memberCode", memberCode);
		map.put("serialNo", serialNo);

		Map<String, Object> resultMap = payDetailService.querySinglePayBatchDetail(map);
		if (null == resultMap) {
			return new ModelAndView(singlePayDetailCorp);
		}

		String amount = String.valueOf(resultMap.get("successAmount"));
		String fee = String.valueOf(resultMap.get("fee"));

		Long amountL = StringUtils.isNotEmpty(amount) ? Long.valueOf(amount) : 0;
		Long feeL = StringUtils.isNotEmpty(fee) ? Long.valueOf(fee) : 0;
		Long foAmountL = (amountL <= 0) == true ? 0 : amountL + feeL;

		BigDecimal amountBig = new BigDecimal(amountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal feeBig = new BigDecimal(feeL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
		BigDecimal foAmountBig = new BigDecimal(foAmountL).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);

		String amoutStr = Trans2RMB.processNum(amountBig.toString());
		String feeStr = Trans2RMB.processNum(feeBig.toString());
		String foAmountStr = Trans2RMB.processNum(foAmountBig.toString());

		try {

			File _file = new File("/opt/pay/config/poss/batchPayToBank.jpg");
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);

			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			g.setColor(Color.black);
			g.setFont(new Font("宋体", 20, 12));

			g.drawString(String.valueOf(resultMap.get("batchNum")), 112, 150);// 商家批次号
			g.drawString(String.valueOf(resultMap.get("orderId")), 583, 150); // 交易号

			// 付款方名称
			String memberName = StringUtil.null2String(memberInfo.getMemberName());
			if (null != memberName && memberName.length() > 26) {
				String oneLine = memberName.substring(0, 26);
				g.drawString(oneLine, 189, 235); // 名称
				String twoLine = memberName.substring(26, memberName.length());
				g.drawString(twoLine, 189, 235 + 20); // 名称
			} else {
				g.drawString(memberName, 189, 245); // 名称
			}

			g.drawString(memberInfo.getLoginName(), 189, 286);// 支付账户
			g.drawString("海南信息技术有限公司", 189, 330);// 协议代付方户名

			g.drawString(amoutStr, 189, 462);// 代付金额(大写)
			g.drawString(feeStr, 189, 504);// 费用(大写)
			g.drawString(foAmountStr, 189, 548);// 出账金额(大写)
			g.drawString("批量付款到银行卡", 189, 590);// 交易类型

			g.drawString(amountBig.toString() + "元", 689, 462); // 代付金额
			g.drawString(feeBig.toString() + "元", 689, 504); // 费用
			g.drawString(foAmountBig.toString() + "元", 689, 548);// 出账金额
			g.drawString(String.valueOf(resultMap.get("createTime")), 689, 590);// 付款时间

			// 备注
			String remark = StringUtil.null2String(resultMap.get("auditRemark"));
			if (null != remark && remark.length() > 60) {
				String oneLine = remark.substring(0, 60);
				g.drawString(oneLine, 62, 621); // 名称
				String twoLine = remark.substring(60, remark.length());
				g.drawString(twoLine, 62, 637); // 名称
			} else {
				g.drawString(remark, 62, 631);// 备注
			}

			g.dispose();

			try {
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response.setContentType("image/x-png");
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("批量付款凭证.jpg", "UTF8"));
				OutputStream out = response.getOutputStream();
				ImageIO.write(image, "png", out);
				out.flush();
				out.close();
				return null;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 批量付款记录详情查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_BATCH_DETAIL_SINGLE")
	public ModelAndView querySinglePayBatchDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(singlePayBatchDetailCorp);
		LoginSession loginSession = SessionHelper.getLoginSession();
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = loginSession.getMemberCode();
		String serialNo = request.getParameter("serialNo");
		String uploadSeq = request.getParameter("uploadSeq");
		String channel = request.getParameter("channel");
		if (StringUtils.isBlank(serialNo) && StringUtils.isBlank(uploadSeq)) {
			return singleView;
		}
		if (StringUtils.isNotBlank((channel))) {
			map.put("channel", channel);
		}
		map.put("memberCode", memberCode);
		map.put("serialNo", serialNo);
		map.put("uploadSeq", uploadSeq);
		// 查询
		Map<String, Object> resultMap = payDetailService.querySinglePayBatchDetail(map);
		singleView.addObject("map", resultMap);
		singleView.addObject("channel", channel);
		return singleView;
	}

	/**
	 * 查询批量付款到银行/账户详细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_PAY_BATCH_DETAIL_SINGLE")
	public ModelAndView queryPayToBankBatchDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView singleView = new ModelAndView(payToBankBatchDetail);
		LoginSession loginSession = SessionHelper.getLoginSession();
		Long memberCode = Long.valueOf(loginSession.getMemberCode());
		
		int pager_offset = 1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset = Integer.parseInt(request.getParameter("pager_offset"));
		}

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String sequenceId = request.getParameter("sequenceId");
			String channel = request.getParameter("channel");
			String businessBatchNo = StringUtil.null2String(request.getParameter("businessBatchNo"));
			Long requestSeq = Long.valueOf(sequenceId);

			BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(requestSeq);

			if (reqInfo == null || !reqInfo.getPayerMemberCode().equals(memberCode)) {
				throw new RuntimeException();
			}

			Page<FundoutOrderDTO> payDetailPage = null;
			Page<OrderInfoDTO> payDetailAcctPage = null;
			int orderType = OrderType.BATCHPAY2BANK.getValue();
			if (String.valueOf(OrderType.BATCHPAY2ACCT.getValue()).equals(channel)) {
				orderType = OrderType.BATCHPAY2ACCT.getValue();
				singleView = new ModelAndView(payToAcctBatchDetail);
			}
			BatchPaymentOrderDTO order = batchPaymentOrderService.getOrder(memberCode, orderType, businessBatchNo);
			PageUtil pageUtil = null;
			if (order != null && order.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()) {
				map.put("orderId", order.getOrderId());
				map.put("memberCode", memberCode);
				singleView.addObject("orderId", order.getOrderId());
				if (orderType == OrderType.BATCHPAY2BANK.getValue()) {
					payDetailPage = fundoutOrderService.queryPayToBankBatchDetail(map, pager_offset, pageSize);
					pageUtil = new PageUtil(pager_offset, pageSize, payDetailPage.getTotalCount());
					singleView.addObject("payDetailList", payDetailPage.getResult());
				}else if (orderType ==OrderType.BATCHPAY2ACCT.getValue()) {
					payDetailAcctPage = fundoutOrderService.queryPayToAcctBatchDetail(map, pager_offset, pageSize);
					pageUtil = new PageUtil(pager_offset, pageSize, payDetailAcctPage.getTotalCount());
					singleView.addObject("payDetailList", payDetailAcctPage.getResult());
				}
			}
			singleView.addObject("pu", pageUtil);
			singleView.addObject("businessBatchNo", businessBatchNo);
			singleView.addObject("sequenceId", sequenceId);
			singleView.addObject("channel", channel);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return singleView;
	}

	/**
	 * 导出查询结果(excel)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportResult(HttpServletRequest request, HttpServletResponse response, Object dateList, String view) throws Exception {
		setResonseHeader(request, response);
		return new ModelAndView(view).addObject("incomeDetailList", dateList);
	}
	
	/**
	 * 导出查询结果(csv)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportCsvResult(HttpServletRequest request, HttpServletResponse response, Object dateList, String view) throws Exception {
		setCsvResonseHeader(request, response);
		return new ModelAndView(view).addObject("incomeDetailList", dateList);
	}

	/**
	 * BSP提现列表查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//操作员权限控更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW_SUMMAARY_BSP")
	public ModelAndView queryWithdrawSummaryFromBsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView bspWithdrawListView = new ModelAndView(bspWithdrawListCorp);
		if (null == request.getParameter("subType") || "".equals(request.getParameter("subType"))) {
			bspWithdrawListView.addObject("startTime", getDate2(-1));
			bspWithdrawListView.addObject("endTime", getDate2(0));
			return bspWithdrawListView;
		}
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		String recheckStatus = request.getParameter("recheckStatus");

		bspWithdrawListView.addObject("startTime", startTime);
		bspWithdrawListView.addObject("endTime", endTime);
		bspWithdrawListView.addObject("status", status);
		bspWithdrawListView.addObject("recheckStatus", recheckStatus);
		LoginSession loginSession = SessionHelper.getLoginSession();
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = loginSession.getMemberCode();
		map.put("startTime", getEndTime(startTime));
		map.put("endTime", getEndTime(endTime));
		map.put("status", status);
		map.put("recheckStatus", recheckStatus);
		map.put("memberCode", memberCode);

		int pager_offset = getPagerOffset(request);
		Page<Map<String, Object>> page = payDetailService.queryWithdrawSummaryFromBsp(map, pager_offset, pageSize);
		List<Map<String, Object>> dataList = page.getResult();
		PageUtil pageUtil = new PageUtil(pager_offset, pageSize, page.getTotalCount());
		bspWithdrawListView.addObject("dataList", dataList);
		bspWithdrawListView.addObject("totalAmount", totalAmount(dataList));
		bspWithdrawListView.addObject("pu", pageUtil);
		return bspWithdrawListView;
	}

	private Long totalAmount(List<Map<String, Object>> dataList) {
		Long totalAmount = 0L;
		if (null != dataList && !dataList.isEmpty()) {
			for (Map<String, Object> map : dataList) {
				BigDecimal amount = (BigDecimal) map.get("amount");
				if (null != amount) {
					totalAmount += amount.longValue();
				}
			}
		}
		return totalAmount;
	}

	/**
	 * BSP提现详情查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	// @OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW_DETAIL_BSP")
	public ModelAndView queryWithdrawDetailFromBsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(bspWithdrawDetailCorp);
		String sequenceId = request.getParameter("sequenceId");
		LoginSession loginSession = SessionHelper.getLoginSession();
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = loginSession.getMemberCode();
		if (StringUtils.isBlank(sequenceId)) {
			return singleView;
		}
		map.put("sequenceId", sequenceId);
		map.put("memberCode", memberCode);
		Map<String, Object> resultMap = payDetailService.queryWithdrawDetailFromBsp(map);
		singleView.addObject("map", resultMap);
		return singleView;
	}

	/**
	 * 资金调拨详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	// @OperatorPermission(operatPermission =
	// "OPERATOR_FO_FUNDALLOT_DETAIL_BSP")
	public ModelAndView queryFundAllotDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView singleView = new ModelAndView(fundAllotDetailCorp);
		String sequenceId = request.getParameter("sequenceId");
		LoginSession loginSession = SessionHelper.getLoginSession();
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = loginSession.getMemberCode();
		if (StringUtils.isBlank(sequenceId)) {
			return singleView;
		}
		map.put("sequenceId", sequenceId);
		map.put("memberCode", memberCode);
		Map<String, Object> resultMap = payDetailService.queryFundAllotDetail(map);
		singleView.addObject("map", resultMap);
		return singleView;
	}

	/** set **/
	public void setPayDetailService(PayDetailService payDetailService) {
		this.payDetailService = payDetailService;
	}

	public void setMemberQueryFacadeService(MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}

	public void setBatchPaymentReqBaseInfoService(BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

	public void setBatchPaymentOrderService(BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}

	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}
	
	/**
	 * @param payToAcctOrderService the payToAcctOrderService to set
	 */
	public void setPayToAcctOrderService(
			PayToAcctOrderService payToAcctOrderService) {
		this.payToAcctOrderService = payToAcctOrderService;
	}

}
