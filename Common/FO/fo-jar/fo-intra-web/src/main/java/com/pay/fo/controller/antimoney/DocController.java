package com.pay.fo.controller.antimoney;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.batchinfo.service.antimoney.DocService;
import com.pay.fundout.batchinfo.service.model.MessageObject;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * 反洗钱之可疑报告、补充报告总控制器
 * @author limeng
 *
 */
public class DocController extends AbstractBaseController{
	
	/*
	 * 可疑报告模板路径，为/opt/pay/config/antimoney/可疑交易报告模板.xlsx
	 */
	public static String SUSPICIOUS_DOC_TEMPLATE = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("config").append(File.separator).append("antimoney").append(File.separator).append("可疑交易报告模板.xlsx").toString(); 
	
	/*
	 * 补充报告模板路径，为/opt/pay/config/antimoney/补充交易报告模板.xlsx
	 */
	public static String SUPPLEMENT_DOC_TEMPLATE = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("config").append(File.separator).append("antimoney").append(File.separator).append("补充交易报告模板.xlsx").toString();
	
	/*
	 * 可疑报告、补充报告报文生成路径，为/opt/pay/config/antimoney/xmls
	 */
	public static String MESSAGE_GENERATE_PATH = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("config").append(File.separator).append("antimoney").append(File.separator).append("xmls").append(File.separator).toString();
	
	private DocService docService;
	
	public void setDocService(DocService docService) {
		this.docService = docService;
	}

	/**
	 * 初始化进入可疑报告操作页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView suspiciousDocInit(HttpServletRequest request, HttpServletResponse response){
		String viewName = urlMap.get("suspiciousDocInit");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 初始化进入补充报告操作页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView supplementDocInit(HttpServletRequest request, HttpServletResponse response){
		String viewName = urlMap.get("supplementDocInit");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 提交模板，进行解析，保存入库等操作
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView submit(HttpServletRequest request, HttpServletResponse response){
		String info = "操作成功";
		String flag = request.getParameter("flag");
		String type = request.getParameter("type");
		String batchNo = request.getParameter("batchNo");
		String seqNo = request.getParameter("seqNo");
		String orgCode = request.getParameter("orgCode");
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		final CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
		String viewName = "";
		if(flag.equals("suspicious")){
			viewName = urlMap.get("suspiciousDocInit");
		}else{
			viewName = urlMap.get("supplementDocInit");
		}
		// 解析模板
		try {
			XSSFWorkbook wbs = new XSSFWorkbook(file.getInputStream());
			XSSFSheet childSheet = wbs.getSheetAt(0);
			// 除去前两行标题部分
			logger.info("-----全部数据行数：" + (childSheet.getLastRowNum() - 1) + "-----");
			boolean firstStatus = false;
			if(childSheet.getLastRowNum() < 2){
				firstStatus = true;
				info="模板内容不能为空";
			}
			for (int j = 2; j <= childSheet.getLastRowNum(); j++) {
				XSSFRow row = childSheet.getRow(j);
				if (null != row) {
					for (int k = 0; k < row.getLastCellNum(); k++) {
						XSSFCell cell = row.getCell(k);
                        if(cell != null){
                        	if(cell.getCellType() != XSSFCell.CELL_TYPE_STRING && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK){
                        		info = "第" + (j + 1) + "行，第" + (k + 1) + "列数据格式不对，请确保为文本类型";
                        		firstStatus = true;
                        		break;
                        	}
                        }
					}
				}
				if(firstStatus == true){
					break;
				}
			}
			if(!firstStatus){
				logger.info("-----验证通过-----");
				// 进行解析
				parseTemplate(flag, orgCode, type, batchNo, seqNo, childSheet);
			}
		} catch (Exception e) {
			info = "操作失败";
			logger.error("操作失败", e);
		} 
		return new ModelAndView(viewName).addObject("info", info);
	}
	
	/**
	 * 解析模板，生成对应的xml报文文件
	 * @param flag
	 * @param childSheet
	 * @throws Exception
	 */
	private void parseTemplate(String flag, String orgCode, String type, String batchNo, String seqNo, XSSFSheet childSheet) throws Exception{
		String messageFlag = null;
		Document document = DocumentHelper.createDocument();
		// 记录可疑主体信息序号
		int j = 1;
		// 记录可疑交易信息序号
		int m = 1;
		if(flag.equals("suspicious")){
			messageFlag = "PS";
			// 可疑报告解析逻辑
			Element headElement = document.addElement("PSTR");
			Element basicInformationElement = headElement.addElement("RBIF");
			
			// 模板的第二行是正常有数据的开始行
			XSSFRow row = childSheet.getRow(2);
			if(null != row){
				// 说明是第一行数据，进行基本信息部分的组装
				
				// 报告机构名称
				String orgName = row.getCell(0).getStringCellValue();
				Element orgNameElement = basicInformationElement.addElement("RINM");
				orgNameElement.setText(orgName);
				
				// 报告机构所在地区编码
				String areaCode = row.getCell(1).getStringCellValue();
				Element areaCodeElement = basicInformationElement.addElement("FIRC");
				areaCodeElement.setText(areaCode);
				
				// 报告机构分支机构/网点代码
				String branchCode = row.getCell(2).getStringCellValue();
				Element branchCodeElement = basicInformationElement.addElement("FICE");
				branchCodeElement.setText(branchCode);
				
				// 报送次数标志
				String reportNo = row.getCell(3).getStringCellValue();
				Element reportNoElement = basicInformationElement.addElement("RFSG");
				reportNoElement.setText(reportNo);
				
				// 初次报送的可疑交易报文名称
				String messageName = row.getCell(4).getStringCellValue();
				Element messageNameElement = basicInformationElement.addElement("ORXN");
				messageNameElement.setText(messageName);
				
				// 可疑交易处理情况
				String dealSituation = row.getCell(5).getStringCellValue();
				Element dealSituationElement = basicInformationElement.addElement("SSTM");
				dealSituationElement.setText(dealSituation);
				
				// 可疑交易特征
				String tradeFeature = row.getCell(6).getStringCellValue();
				Element tradeFeatureElement = basicInformationElement.addElement("STCR");
				tradeFeatureElement.setText(tradeFeature);
				
				// 可疑交易描述
				String tradeDescription = row.getCell(7).getStringCellValue();
				Element tradeDescriptionElement = basicInformationElement.addElement("SSDS");
				tradeDescriptionElement.setText(tradeDescription);
				
				// 机构自定可疑交易标准编号
				String standedCode = row.getCell(8).getStringCellValue();
				Element standedCodeElement = basicInformationElement.addElement("UDSI");
				standedCodeElement.setText(standedCode);
				
				// 可疑主体数量
				String bodyNo = row.getCell(9).getStringCellValue();
				Element bodyNoElement = basicInformationElement.addElement("SCTN");
				bodyNoElement.setText(bodyNo);
				
				// 可疑交易数量
				String tradeNo = row.getCell(10).getStringCellValue();
				Element tradeNoElement = basicInformationElement.addElement("TTNM");
				tradeNoElement.setText(tradeNo);
				
				// 可疑主体信息部分
				// 可疑主体姓名/名称，用其作为判断是否还有下一条的条件
				Element mainInformationsElement = headElement.addElement("CTIFs");
				for(int i = 2; i <= childSheet.getLastRowNum(); i++){
					row = childSheet.getRow(i);
					String bodyName = null;
					if(row.getCell(11) != null){
						bodyName = row.getCell(11).getStringCellValue();
					}
					if(!(StringUtil.isEmpty(bodyName))){
						Element mainInformationElement = mainInformationsElement.addElement("CTIF");
						mainInformationElement.addAttribute("seqno", String.valueOf(j++));
						
						// 可疑主体姓名/名称
						Element bodyNameElement = mainInformationElement.addElement("CTNM");
						bodyNameElement.setText(bodyName);
						
						// 主体特约商户编号
						String merchantCode = row.getCell(12).getStringCellValue();
						Element merchantCodeElement = mainInformationElement.addElement("SMID");
						merchantCodeElement.setText(merchantCode);
						
						// 可疑主体身份证件/证明文件类型
						String idType = row.getCell(13).getStringCellValue();
						Element idTypeElement = mainInformationElement.addElement("CITP");
						idTypeElement.setText(idType);
						
						// 可疑主体身份证件/证明文件号码
						String idNo = row.getCell(14).getStringCellValue();
						Element idNoElement = mainInformationElement.addElement("CTID");
						idNoElement.setText(idNo);
						
						// 详情部分
						Element ccifElement = mainInformationElement.addElement("CCIF");
						
						// 详细住址
						String address = row.getCell(15).getStringCellValue();
						Element addressElement = ccifElement.addElement("CTAR");
						addressElement.setText(address);
						
						// 联系电话
						String mobile = row.getCell(16).getStringCellValue();
						Element mobileElement = ccifElement.addElement("CCTL");
						mobileElement.setText(mobile);
						
						// 电子邮件
						String email = row.getCell(17).getStringCellValue();
						Element emailElement = ccifElement.addElement("CEML");
						emailElement.setText(email);
						
						// 可疑主体的职业/行业类别
						String industryType = row.getCell(18).getStringCellValue();
						Element industryTypeElement = mainInformationElement.addElement("CTVC");
						industryTypeElement.setText(industryType);
						
						// 可疑主体的法定代表人姓名
						String behalfName = row.getCell(19).getStringCellValue();
						Element behalfNameElement = mainInformationElement.addElement("CRNM");
						behalfNameElement.setText(behalfName);
						
						// 可疑主体的法定代表人身份证件类型
						String behalfIdType = row.getCell(20).getStringCellValue();
						Element behalfIdTypeElement = mainInformationElement.addElement("CRIT");
						behalfIdTypeElement.setText(behalfIdType);
						
						// 可疑主体的法定代表人身份证件号码
						String behalfIdNo = row.getCell(21).getStringCellValue();
						Element behalfIdNoElement = mainInformationElement.addElement("CRID");
						behalfIdNoElement.setText(behalfIdNo);
					}
				}
				// 可疑交易信息部分
				Element tradeInformationsElement = headElement.addElement("STIFs");
				for(int i = 2; i <= childSheet.getLastRowNum(); i++){
					row = childSheet.getRow(i);
					String bodyName2 = null;
					if(row.getCell(22) != null){
						bodyName2 = row.getCell(22).getStringCellValue();
					}
					if(!(StringUtil.isEmpty(bodyName2))){
						Element tradeInformationElement = tradeInformationsElement.addElement("STIF");
						tradeInformationElement.addAttribute("seqno", String.valueOf(m++));
						
						// 可疑主体姓名/名称
						Element bodyName2Element = tradeInformationElement.addElement("CTNM");
						bodyName2Element.setText(bodyName2);
						
						// 可疑主体身份证件/证明文件类型
						String idType2 = row.getCell(23).getStringCellValue();
						Element idType2Element = tradeInformationElement.addElement("CITP");
						idType2Element.setText(idType2);
						
						// 可疑主体身份证件/证明文件号码
						String idNo2 = row.getCell(24).getStringCellValue();
						Element idNo2Element = tradeInformationElement.addElement("CTID");
						idNo2Element.setText(idNo2);
						
						// 可疑主体的银行账号种类
						String bodyBankAccType = row.getCell(25).getStringCellValue();
						Element bodyBankAccTypeElement = tradeInformationElement.addElement("CBAT");
						bodyBankAccTypeElement.setText(bodyBankAccType);
						
						// 可疑主体的银行账号
						String bodyBankAcc = row.getCell(26).getStringCellValue();
						Element bodyBankAccElement = tradeInformationElement.addElement("CBAC");
						bodyBankAccElement.setText(bodyBankAcc);
						
						// 可疑主体银行账号的开户银行名称
						String bodyBankAccBankName = row.getCell(27).getStringCellValue();
						Element bodyBankAccBankNameElement = tradeInformationElement.addElement("CABM");
						bodyBankAccBankNameElement.setText(bodyBankAccBankName);
						
						// 可疑主体的支付账户种类
						String bodyPayAccType = row.getCell(28).getStringCellValue();
						Element bodyPayAccTypeElement = tradeInformationElement.addElement("CTAT");
						bodyPayAccTypeElement.setText(bodyPayAccType);
						
						// 可疑主体的支付账户账号
						String bodyPayAcc = row.getCell(29).getStringCellValue();
						Element bodyPayAccElement = tradeInformationElement.addElement("CTAC");
						bodyPayAccElement.setText(bodyPayAcc);
						
						// 可疑主体所在支付机构的名称
						String bodyOrgName = row.getCell(30).getStringCellValue();
						Element bodyOrgNameElement = tradeInformationElement.addElement("CPIN");
						bodyOrgNameElement.setText(bodyOrgName);
						
						// 可疑主体所在支付机构的银行账号
						String bodyOrgBankAcc = row.getCell(31).getStringCellValue();
						Element bodyOrgBankAccElement = tradeInformationElement.addElement("CPBA");
						bodyOrgBankAccElement.setText(bodyOrgBankAcc);
						
						// 可疑主体所在支付机构的银行账号的开户银行名称
						String bodyOrgBankAccBankName = row.getCell(32).getStringCellValue();
						Element bodyOrgBankAccBankNameElement = tradeInformationElement.addElement("CPBN");
						bodyOrgBankAccBankNameElement.setText(bodyOrgBankAccBankName);
						
						// 可疑主体的交易IP地址
						String bodyTradeIp = row.getCell(33).getStringCellValue();
						Element bodyTradeIpElement = tradeInformationElement.addElement("CTIP");
						bodyTradeIpElement.setText(bodyTradeIp);
						
						// 交易时间
						String tradeTime = row.getCell(34).getStringCellValue();
						Element tradeTimeElement = tradeInformationElement.addElement("TSTM");
						tradeTimeElement.setText(tradeTime);
						
						// 货币资金转移方式
						String moveMode = row.getCell(35).getStringCellValue();
						Element moveModeElement = tradeInformationElement.addElement("CTTP");
						moveModeElement.setText(moveMode);
						
						// 资金收付标志
						String inOutFlag = row.getCell(36).getStringCellValue();
						Element inOutFlagElement = tradeInformationElement.addElement("TSDR");
						inOutFlagElement.setText(inOutFlag);
						
						// 资金用途
						String moneyWay = row.getCell(37).getStringCellValue();
						Element moneyWayElement = tradeInformationElement.addElement("CRPP");
						moneyWayElement.setText(moneyWay);
						
						// 交易币种
						String tradeCurrency = row.getCell(38).getStringCellValue();
						Element tradeCurrencyElement = tradeInformationElement.addElement("CRTP");
						tradeCurrencyElement.setText(tradeCurrency);
						
						// 交易金额
						String tradeSum = row.getCell(39).getStringCellValue();
						Element tradeSumElement = tradeInformationElement.addElement("CRAT");
						tradeSumElement.setText(tradeSum);
						
						// 交易对手姓名/名称
						String rivalName = row.getCell(40).getStringCellValue();
						Element rivalNameElement = tradeInformationElement.addElement("TCNM");
						rivalNameElement.setText(rivalName);
						
						// 交易对手特约商户编号
						String rivalMerchantCode = row.getCell(41).getStringCellValue();
						Element rivalMerchantCodeElement = tradeInformationElement.addElement("TSMI");
						rivalMerchantCodeElement.setText(rivalMerchantCode);
						
						// 交易对手证件/证明文件类型
						String rivalIdType = row.getCell(42).getStringCellValue();
						Element rivalIdTypeElement = tradeInformationElement.addElement("TCIT");
						rivalIdTypeElement.setText(rivalIdType);
						
						// 交易对手证件/证明文件号码
						String rivalIdNo = row.getCell(43).getStringCellValue();
						Element rivalIdNoElement = tradeInformationElement.addElement("TCID");
						rivalIdNoElement.setText(rivalIdNo);
						
						// 交易对手的银行账号种类
						String rivalBankAccType = row.getCell(44).getStringCellValue();
						Element rivalBankAccTypeElement = tradeInformationElement.addElement("TCAT");
						rivalBankAccTypeElement.setText(rivalBankAccType);
						
						// 交易对手的银行账号
						String rivalBankAcc = row.getCell(45).getStringCellValue();
						Element rivalBankAccElement = tradeInformationElement.addElement("TCBA");
						rivalBankAccElement.setText(rivalBankAcc);
						
						// 交易对手银行账号的开户银行名称
						String rivalBankAccBankName = row.getCell(46).getStringCellValue();
						Element rivalBankAccBankNameElement = tradeInformationElement.addElement("TCBN");
						rivalBankAccBankNameElement.setText(rivalBankAccBankName);
						
						// 交易对手的支付账户种类
						String rivalPayAccType = row.getCell(47).getStringCellValue();
						Element rivalPayAccTypeElement = tradeInformationElement.addElement("TCTT");
						rivalPayAccTypeElement.setText(rivalPayAccType);
						
						// 交易对手的支付账户号码
						String rivalPayAccNo = row.getCell(48).getStringCellValue();
						Element rivalPayAccNoElement = tradeInformationElement.addElement("TCTA");
						rivalPayAccNoElement.setText(rivalPayAccNo);
						
						// 交易对手所在支付机构的名称
						String rivalOrgName = row.getCell(49).getStringCellValue();
						Element rivalOrgNameElement = tradeInformationElement.addElement("TCPN");
						rivalOrgNameElement.setText(rivalOrgName);
						
						// 交易对手所在支付机构的银行账号
						String rivalOrgBankAcc = row.getCell(50).getStringCellValue();
						Element rivalOrgBankAccElement = tradeInformationElement.addElement("TCPA");
						rivalOrgBankAccElement.setText(rivalOrgBankAcc);
						
						// 交易对手所在支付机构银行账号的开户银行名称
						String rivalOrgBankAccBankName = row.getCell(51).getStringCellValue();
						Element rivalOrgBankAccBankNameElement = tradeInformationElement.addElement("TPBN");
						rivalOrgBankAccBankNameElement.setText(rivalOrgBankAccBankName);
						
						// 交易对手的交易IP地址
						String rivalTradeIp = row.getCell(52).getStringCellValue();
						Element rivalTradeIpElement = tradeInformationElement.addElement("TCIP");
						rivalTradeIpElement.setText(rivalTradeIp);
						
						// 交易商品名称
						String productName = row.getCell(53).getStringCellValue();
						Element productNameElement = tradeInformationElement.addElement("TMNM");
						productNameElement.setText(productName);
						
						// 银行与支付机构之间的业务交易编码
						String bankOrderId = row.getCell(54).getStringCellValue();
						Element bankOrderIdElement = tradeInformationElement.addElement("BPTC");
						bankOrderIdElement.setText(bankOrderId);
						
						// 支付机构与商户之间的业务交易编码
						String orderId = row.getCell(55).getStringCellValue();
						Element orderIdElement = tradeInformationElement.addElement("PMTC");
						orderIdElement.setText(orderId);
						
						// 业务标识号
						String businessNo = row.getCell(56).getStringCellValue();
						Element businessNoElement = tradeInformationElement.addElement("TICD");
						businessNoElement.setText(businessNo);
					}
				}
			}
		}else{
			messageFlag = "PC";
			// 补充报告解析逻辑
			Element headElement = document.addElement("PCTR");
			Element basicInformationElement = headElement.addElement("RBIF");
			XSSFRow row = childSheet.getRow(2);
			if(null != row){
				// 说明是第一行数据，进行基本信息部分的组装
				
				// 报告机构名称
				String orgName = row.getCell(0).getStringCellValue();
				Element orgNameElement = basicInformationElement.addElement("RINM");
				orgNameElement.setText(orgName);
				
				// 报告机构所在地区编码
				String areaCode = row.getCell(1).getStringCellValue();
				Element areaCodeElement = basicInformationElement.addElement("FIRC");
				areaCodeElement.setText(areaCode);
				
				// 补充信息通知编码
				String noticeCode = row.getCell(2).getStringCellValue();
				Element noticeCodeElement = basicInformationElement.addElement("CIMC");
				noticeCodeElement.setText(noticeCode);
				
				// 可疑主体数量
				String bodyNo = row.getCell(3).getStringCellValue();
				Element bodyNoElement = basicInformationElement.addElement("SCTN");
				bodyNoElement.setText(bodyNo);
				
				// 可疑交易数量
				String tradeNo = row.getCell(4).getStringCellValue();
				Element tradeNoElement = basicInformationElement.addElement("TTNM");
				tradeNoElement.setText(tradeNo);
					
				// 可疑主体信息部分
				// 可疑主体姓名/名称，用其作为判断是否还有下一条的条件
				Element mainInformationsElement = headElement.addElement("CTIFs");
				for(int i = 2; i <= childSheet.getLastRowNum(); i++){
					row = childSheet.getRow(i);
					String bodyName = null;
					if(row.getCell(5) != null){
						bodyName = row.getCell(5).getStringCellValue();
					}
					if(!(StringUtil.isEmpty(bodyName))){
						Element mainInformationElement = mainInformationsElement.addElement("CTIF");
						mainInformationElement.addAttribute("seqno", String.valueOf(j++));
						
						// 可疑主体姓名/名称
						Element bodyNameElement = mainInformationElement.addElement("CTNM");
						bodyNameElement.setText(bodyName);
						
						// 主体特约商户编号
						String merchantCode = row.getCell(6).getStringCellValue();
						Element merchantCodeElement = mainInformationElement.addElement("SMID");
						merchantCodeElement.setText(merchantCode);
						
						// 可疑主体身份证件/证明文件类型
						String idType = row.getCell(7).getStringCellValue();
						Element idTypeElement = mainInformationElement.addElement("CITP");
						idTypeElement.setText(idType);
						
						// 可疑主体身份证件/证明文件号码
						String idNo = row.getCell(8).getStringCellValue();
						Element idNoElement = mainInformationElement.addElement("CTID");
						idNoElement.setText(idNo);
						
						// 详情部分
						Element ccifElement = mainInformationElement.addElement("CCIF");
						
						// 详细住址
						String address = row.getCell(9).getStringCellValue();
						Element addressElement = ccifElement.addElement("CTAR");
						addressElement.setText(address);
						
						// 联系电话
						String mobile = row.getCell(10).getStringCellValue();
						Element mobileElement = ccifElement.addElement("CCTL");
						mobileElement.setText(mobile);
						
						// 电子邮件
						String email = row.getCell(11).getStringCellValue();
						Element emailElement = ccifElement.addElement("CEML");
						emailElement.setText(email);
						
						// 可疑主体的职业/行业类别
						String industryType = row.getCell(12).getStringCellValue();
						Element industryTypeElement = mainInformationElement.addElement("CTVC");
						industryTypeElement.setText(industryType);
						
						// 可疑主体的法定代表人姓名
						String behalfName = row.getCell(13).getStringCellValue();
						Element behalfNameElement = mainInformationElement.addElement("CRNM");
						behalfNameElement.setText(behalfName);
						
						// 可疑主体的法定代表人身份证件类型
						String behalfIdType = row.getCell(14).getStringCellValue();
						Element behalfIdTypeElement = mainInformationElement.addElement("CRIT");
						behalfIdTypeElement.setText(behalfIdType);
						
						// 可疑主体的法定代表人身份证件号码
						String behalfIdNo = row.getCell(15).getStringCellValue();
						Element behalfIdNoElement = mainInformationElement.addElement("CRID");
						behalfIdNoElement.setText(behalfIdNo);
					}
				}
				
				// 可疑交易信息部分
				Element tradeInformationsElement = headElement.addElement("STIFs");
				for(int i = 2; i <= childSheet.getLastRowNum(); i++){
					row = childSheet.getRow(i);
					String bodyName2 = null;
					if(row.getCell(16) != null){
						bodyName2 = row.getCell(16).getStringCellValue();
					}
					if(!(StringUtil.isEmpty(bodyName2))){
						Element tradeInformationElement = tradeInformationsElement.addElement("STIF");
						tradeInformationElement.addAttribute("seqno", String.valueOf(m++));
						
						// 可疑主体姓名/名称
						Element bodyName2Element = tradeInformationElement.addElement("CTNM");
						bodyName2Element.setText(bodyName2);
						
						// 可疑主体身份证件/证明文件类型
						String idType2 = row.getCell(17).getStringCellValue();
						Element idType2Element = tradeInformationElement.addElement("CITP");
						idType2Element.setText(idType2);
						
						// 可疑主体身份证件/证明文件号码
						String idNo2 = row.getCell(18).getStringCellValue();
						Element idNo2Element = tradeInformationElement.addElement("CTID");
						idNo2Element.setText(idNo2);
						
						// 可疑主体的银行账号种类
						String bodyBankAccType = row.getCell(19).getStringCellValue();
						Element bodyBankAccTypeElement = tradeInformationElement.addElement("CBAT");
						bodyBankAccTypeElement.setText(bodyBankAccType);
						
						// 可疑主体的银行账号
						String bodyBankAcc = row.getCell(20).getStringCellValue();
						Element bodyBankAccElement = tradeInformationElement.addElement("CBAC");
						bodyBankAccElement.setText(bodyBankAcc);
						
						// 可疑主体银行账号的开户银行名称
						String bodyBankAccBankName = row.getCell(21).getStringCellValue();
						Element bodyBankAccBankNameElement = tradeInformationElement.addElement("CABM");
						bodyBankAccBankNameElement.setText(bodyBankAccBankName);
						
						// 可疑主体的支付账户种类
						String bodyPayAccType = row.getCell(22).getStringCellValue();
						Element bodyPayAccTypeElement = tradeInformationElement.addElement("CTAT");
						bodyPayAccTypeElement.setText(bodyPayAccType);
						
						// 可疑主体的支付账户账号
						String bodyPayAcc = row.getCell(23).getStringCellValue();
						Element bodyPayAccElement = tradeInformationElement.addElement("CTAC");
						bodyPayAccElement.setText(bodyPayAcc);
						
						// 可疑主体所在支付机构的名称
						String bodyOrgName = row.getCell(24).getStringCellValue();
						Element bodyOrgNameElement = tradeInformationElement.addElement("CPIN");
						bodyOrgNameElement.setText(bodyOrgName);
						
						// 可疑主体所在支付机构的银行账号
						String bodyOrgBankAcc = row.getCell(25).getStringCellValue();
						Element bodyOrgBankAccElement = tradeInformationElement.addElement("CPBA");
						bodyOrgBankAccElement.setText(bodyOrgBankAcc);
						
						// 可疑主体所在支付机构的银行账号的开户银行名称
						String bodyOrgBankAccBankName = row.getCell(26).getStringCellValue();
						Element bodyOrgBankAccBankNameElement = tradeInformationElement.addElement("CPBN");
						bodyOrgBankAccBankNameElement.setText(bodyOrgBankAccBankName);
						
						// 可疑主体的交易IP地址
						String bodyTradeIp = row.getCell(27).getStringCellValue();
						Element bodyTradeIpElement = tradeInformationElement.addElement("CTIP");
						bodyTradeIpElement.setText(bodyTradeIp);
						
						// 交易时间
						String tradeTime = row.getCell(28).getStringCellValue();
						Element tradeTimeElement = tradeInformationElement.addElement("TSTM");
						tradeTimeElement.setText(tradeTime);
						
						// 货币资金转移方式
						String moveMode = row.getCell(29).getStringCellValue();
						Element moveModeElement = tradeInformationElement.addElement("CTTP");
						moveModeElement.setText(moveMode);
						
						// 资金收付标志
						String inOutFlag = row.getCell(30).getStringCellValue();
						Element inOutFlagElement = tradeInformationElement.addElement("TSDR");
						inOutFlagElement.setText(inOutFlag);
						
						// 资金用途
						String moneyWay = row.getCell(31).getStringCellValue();
						Element moneyWayElement = tradeInformationElement.addElement("CRPP");
						moneyWayElement.setText(moneyWay);
						
						// 交易币种
						String tradeCurrency = row.getCell(32).getStringCellValue();
						Element tradeCurrencyElement = tradeInformationElement.addElement("CRTP");
						tradeCurrencyElement.setText(tradeCurrency);
						
						// 交易金额
						String tradeSum = row.getCell(33).getStringCellValue();
						Element tradeSumElement = tradeInformationElement.addElement("CRAT");
						tradeSumElement.setText(tradeSum);
						
						// 交易对手姓名/名称
						String rivalName = row.getCell(34).getStringCellValue();
						Element rivalNameElement = tradeInformationElement.addElement("TCNM");
						rivalNameElement.setText(rivalName);
						
						// 交易对手特约商户编号
						String rivalMerchantCode = row.getCell(35).getStringCellValue();
						Element rivalMerchantCodeElement = tradeInformationElement.addElement("TSMI");
						rivalMerchantCodeElement.setText(rivalMerchantCode);
						
						// 交易对手证件/证明文件类型
						String rivalIdType = row.getCell(36).getStringCellValue();
						Element rivalIdTypeElement = tradeInformationElement.addElement("TCIT");
						rivalIdTypeElement.setText(rivalIdType);
						
						// 交易对手证件/证明文件号码
						String rivalIdNo = row.getCell(37).getStringCellValue();
						Element rivalIdNoElement = tradeInformationElement.addElement("TCID");
						rivalIdNoElement.setText(rivalIdNo);
						
						// 交易对手的银行账号种类
						String rivalBankAccType = row.getCell(38).getStringCellValue();
						Element rivalBankAccTypeElement = tradeInformationElement.addElement("TCAT");
						rivalBankAccTypeElement.setText(rivalBankAccType);
						
						// 交易对手的银行账号
						String rivalBankAcc = row.getCell(39).getStringCellValue();
						Element rivalBankAccElement = tradeInformationElement.addElement("TCBA");
						rivalBankAccElement.setText(rivalBankAcc);
						
						// 交易对手银行账号的开户银行名称
						String rivalBankAccBankName = row.getCell(40).getStringCellValue();
						Element rivalBankAccBankNameElement = tradeInformationElement.addElement("TCBN");
						rivalBankAccBankNameElement.setText(rivalBankAccBankName);
						
						// 交易对手的支付账户种类
						String rivalPayAccType = row.getCell(41).getStringCellValue();
						Element rivalPayAccTypeElement = tradeInformationElement.addElement("TCTT");
						rivalPayAccTypeElement.setText(rivalPayAccType);
						
						// 交易对手的支付账户号码
						String rivalPayAccNo = row.getCell(42).getStringCellValue();
						Element rivalPayAccNoElement = tradeInformationElement.addElement("TCTA");
						rivalPayAccNoElement.setText(rivalPayAccNo);
						
						// 交易对手所在支付机构的名称
						String rivalOrgName = row.getCell(43).getStringCellValue();
						Element rivalOrgNameElement = tradeInformationElement.addElement("TCPN");
						rivalOrgNameElement.setText(rivalOrgName);
						
						// 交易对手所在支付机构的银行账号
						String rivalOrgBankAcc = row.getCell(44).getStringCellValue();
						Element rivalOrgBankAccElement = tradeInformationElement.addElement("TCPA");
						rivalOrgBankAccElement.setText(rivalOrgBankAcc);
						
						// 交易对手所在支付机构银行账号的开户银行名称
						String rivalOrgBankAccBankName = row.getCell(45).getStringCellValue();
						Element rivalOrgBankAccBankNameElement = tradeInformationElement.addElement("TPBN");
						rivalOrgBankAccBankNameElement.setText(rivalOrgBankAccBankName);
						
						// 交易对手的交易IP地址
						String rivalTradeIp = row.getCell(46).getStringCellValue();
						Element rivalTradeIpElement = tradeInformationElement.addElement("TCIP");
						rivalTradeIpElement.setText(rivalTradeIp);
						
						// 交易商品名称
						String productName = row.getCell(47).getStringCellValue();
						Element productNameElement = tradeInformationElement.addElement("TMNM");
						productNameElement.setText(productName);
						
						// 银行与支付机构之间的业务交易编码
						String bankOrderId = row.getCell(48).getStringCellValue();
						Element bankOrderIdElement = tradeInformationElement.addElement("BPTC");
						bankOrderIdElement.setText(bankOrderId);
						
						// 支付机构与商户之间的业务交易编码
						String orderId = row.getCell(49).getStringCellValue();
						Element orderIdElement = tradeInformationElement.addElement("PMTC");
						orderIdElement.setText(orderId);
						
						// 业务标识号
						String businessNo = row.getCell(50).getStringCellValue();
						Element businessNoElement = tradeInformationElement.addElement("TICD");
						businessNoElement.setText(businessNo);
					}
				}
			}
		}
		
		String fileName = type + messageFlag + orgCode + "-" + DateUtil.formatDateTime("yyyyMMdd") + "-" + batchNo + "-" + seqNo + ".XML";
		String fullPath = MESSAGE_GENERATE_PATH + fileName;
		logger.info("-----generate xml name is: " + fileName + "-----");
		logger.info("-----full xml path is: " + fullPath + "-----");
		
		File file = new File(fullPath);
		if(file.exists()){
			logger.error("-----同名文件已存在，请确保文件名的唯一性-----");
			throw new Exception("同名文件已存在，请确保文件名的唯一性");
		}else{
			file.createNewFile();
		}
		
		//格式化输出xml文件，兼容ie的格式化输出
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("gb18030");
		//把xml文件从内存中写入文件
		XMLWriter writer = new XMLWriter(new FileWriter(fullPath), format);
		writer.write(document);
		writer.close();
		
		// 记录入库操作
		MessageObject message = new MessageObject();
		message.setName(fileName);
		message.setCategory(getCategoryIntValue(messageFlag));
		message.setType(getTypeIntValue(type));
		message.setBatchNo(batchNo);
		message.setSeqNo(seqNo);
		message.setSubmitDate(DateUtil.formatDateTime("yyyyMMdd"));
		message.setCreateDate(new Date());
		docService.createMessage(message);
	}
	
	/**
	 * 返回对应的数据库值
	 * @param category
	 * @return
	 */
	private int getCategoryIntValue(String category){
		if(category.equals("PS")){
			return 0;
		}else{
			return 1;
		}
	}
	
	/**
	 * 返回对应的数据库值
	 * @param type
	 * @return
	 */
	private int getTypeIntValue(String type){
		if(type.equals("N")){
			return 1;
		}else if(type.equals("C")){
			return 2;
		}else if(type.equals("R")){
			return 3;
		}else{
			return 4;
		}
	}
	
	/**
	 * 进入初始化报文查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initSearch(HttpServletRequest request, HttpServletResponse response){
		String viewName = urlMap.get("initSearch");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 查询报文
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response){
		MessageObject object = new MessageObject();
		String category = request.getParameter("category");
		String type = request.getParameter("type");
		String batchNo = request.getParameter("batchNo");
		String seqNo = request.getParameter("seqNo");
		String submitDate = request.getParameter("submitDate");
		System.out.println(submitDate.replaceAll("-", ""));
		if(!(StringUtil.isEmpty(category))){
			object.setCategory(Integer.valueOf(category));
		}
		if(!(StringUtil.isEmpty(type))){
			object.setType(Integer.valueOf(type));
		}
		if(!(StringUtil.isEmpty(batchNo))){
			object.setBatchNo(batchNo);
		}
		if(!(StringUtil.isEmpty(seqNo))){
			object.setSeqNo(seqNo);
		}
		if(!(StringUtil.isEmpty(submitDate))){
			object.setSubmitDate(submitDate.replaceAll("-", ""));
		}
		Page<MessageObject> page = PageUtils.getPage(request);
		page = this.docService.queryMessageFile(page, object);
		String viewName = urlMap.get("searchResult");
		return new ModelAndView(viewName).addObject("page", page);
	}
	
	/**
	 * 下载报文模板
	 * @return
	 */
	public ModelAndView downloadDocTemplate(HttpServletRequest request, HttpServletResponse response){
		String flag = request.getParameter("flag");
		String filePath = "";
		String fileName = "";
		if(flag.equals("suspicious")){
			filePath = SUSPICIOUS_DOC_TEMPLATE;
			fileName = "可疑交易报告模板.xlsx";
		}else{
			filePath = SUPPLEMENT_DOC_TEMPLATE;
			fileName = "补充交易报告模板.xlsx";
		}
		
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			inputStream = new FileInputStream(new File(filePath));
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
			response.setBufferSize(1024);

			os = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(b)) > 0) {
				os.write(b, 0, len);
				os.flush();
			}
			os.close();
			inputStream.close();
			return null;
		}catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			os = null;
			inputStream = null;
		}
	}
	
	/**
	 * 下载报文XML
	 * @return
	 */
	public ModelAndView downloadMessageXml(HttpServletRequest request, HttpServletResponse response){
		long id = Long.valueOf(request.getParameter("id"));
		MessageObject object = docService.findById(id);
		InputStream inputStream = null;
		OutputStream os = null;
		if(object == null){
			logger.error("-----要下载的目标文件不存在-----");
		}else{
			try {
				inputStream = new FileInputStream(new File(MESSAGE_GENERATE_PATH.concat(object.getName())));
				response.reset();
				response.setContentType("text/xml;charset=gb18030");
				response.setHeader("Content-disposition", "attachment; filename="+ object.getName());
				response.setBufferSize(1024);

				os = new BufferedOutputStream(response.getOutputStream());
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(b)) > 0) {
					os.write(b, 0, len);
					os.flush();
				}
				os.close();
				inputStream.close();
				
				return null;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			} finally {
				os = null;
				inputStream = null;
			}
		}
		return null;
	}
	
}
