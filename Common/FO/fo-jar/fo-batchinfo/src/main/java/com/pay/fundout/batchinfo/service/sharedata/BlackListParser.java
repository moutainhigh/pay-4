package com.pay.fundout.batchinfo.service.sharedata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.pay.fundout.batchinfo.service.model.BlackList;

/**
 * 黑名单解析器
 * @author limeng
 *
 */
public class BlackListParser {
	
	/**
	 * 当目标xml文件为空时，第一次填充内容
	 * @param blackList 黑名单实体
	 * @param filePath 黑名单文件路径
	 * @return null表示解析成功，有返回则证明解析失败
	 */
	public static String createBlackListFile(BlackList blackList, String filePath){
		Document document = DocumentHelper.createDocument();
		Element rowsElement = document.addElement("ROWS");
		return commonOperation(blackList, filePath, document, rowsElement);
	}
	
	/**
	 * 当要写入的xml是已经被操作过的时，则进行如下规则
	 * @param blackList 黑名单实体
	 * @param file 黑名单文件
	 * @param filePath 黑名单文件路径
	 * @return null表示解析成功，有返回则证明解析失败
	 */
	public static String updateBlackListFile(BlackList blackList, File file, String filePath){
		SAXReader reader=new SAXReader();
		Document document;
		// 获取document对象
        try {
			document=reader.read(file);
		} catch (DocumentException e) {
			return "解析已存在黑名单文件失败";
		}
        // 找到根节点
        Element rowsElement = document.getRootElement();
        return commonOperation(blackList, filePath, document, rowsElement);
	}
	
	/**
	 * 新增和修改通用操作
	 * @param blackList 黑名单实体
	 * @param filePath 黑名单文件路径
	 * @param document xml对应的dom4j文档实体
	 * @param rowsElement 根元素
	 * @return null表示解析成功，有返回则证明解析失败
	 */
	public static String commonOperation(BlackList blackList, String filePath, Document document, Element rowsElement){
		Element rowElement = rowsElement.addElement("ROW");
		// 通用
			// 类型属性
			rowElement.addAttribute("HMDLX", blackList.getType());
			// 黑名单ID
			Element idElement = rowElement.addElement("HMDID");
			idElement.setText(blackList.getId());
			// 成员单位编码
			Element memberUnitCodeElement = rowElement.addElement("CYDWBM");
			memberUnitCodeElement.setText(blackList.getMemberUnitCode());
			// 公民身份证号码/法人代表公民身份号码
			Element identityCodeElement = rowElement.addElement("GMSFHM");
			identityCodeElement.setText(blackList.getIdentityCode());
			// 姓名/法人代表姓名
			Element nameElement = rowElement.addElement("XM");
			nameElement.setText(blackList.getName());
			// 发生地区
			Element occuredAreaElement = rowElement.addElement("FSDQ");
			occuredAreaElement.setText(blackList.getOccuredArea());
			// 录黑途径
			Element wayElement = rowElement.addElement("LHTJ");
			wayElement.setText(blackList.getWay());
			// 黑名单事件
			Element eventElement = rowElement.addElement("HMDSJ");
			eventElement.setText(blackList.getEvent());
			// 黑名单事件备注
			Element remarkElement = rowElement.addElement("HMDSJBZ");
			remarkElement.setText(blackList.getRemark());
			// 手机号码
			Element mobileElement = rowElement.addElement("SJHM");
			mobileElement.setText(blackList.getMobile());
			// 固定电话
			Element telephoneElement = rowElement.addElement("GDDH");
			telephoneElement.setText(blackList.getTelephone());
			// 银行卡号
			Element bankCodeElement = rowElement.addElement("YHKH");
			bankCodeElement.setText(blackList.getBankCode());
			// 开户行
			Element bankNameElement = rowElement.addElement("KHH");
			bankNameElement.setText(blackList.getBankName());
			// IP地址
			Element ipElement = rowElement.addElement("IP");
			ipElement.setText(blackList.getIp());
			// MAC地址
			Element macElement = rowElement.addElement("MAC");
			macElement.setText(blackList.getMac());
			// EMAIL
			Element emailElement = rowElement.addElement("EMAIL");
			emailElement.setText(blackList.getEmail());
			// 地址
			Element addressElement = rowElement.addElement("DZ");
			addressElement.setText(blackList.getAddress());
			// ICP编号
			Element tcpCodeElement = rowElement.addElement("ICP");
			tcpCodeElement.setText(blackList.getIcpCode());
			// ICP备案人
			Element tcpMemberElement = rowElement.addElement("ICPBAR");
			tcpMemberElement.setText(blackList.getIcpMember());
			// URL地址
			Element urlAddressElement = rowElement.addElement("URLDZ");
			urlAddressElement.setText(blackList.getUrlAddress());
			// URL跳转地址
			Element urlBranchAddressElement = rowElement.addElement("URLTZDZ");
			urlBranchAddressElement.setText(blackList.getUrlBranchAddress());
			// 支付人
			Element payerNameElement = rowElement.addElement("ZFR");
			payerNameElement.setText(blackList.getPayerName());
			// 标记为黑名单的时间
			Element markTimeElement = rowElement.addElement("BJSJ");
			markTimeElement.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(blackList.getMarkTime()));
			// 数据状态
			Element statusElement = rowElement.addElement("SJZT");
			statusElement.setText(blackList.getStatus());
			// 操作人
			Element operatorElement = rowElement.addElement("CZR");
			operatorElement.setText(blackList.getOperator());
		// 通用结束
		// 机构黑名单比个人多出三个输入项
		if(blackList.getType().equals("2")){
			// 机构名称
			Element orgNameElement = rowElement.addElement("JGMC");
			orgNameElement.setText(blackList.getOrgName());
			// 组织机构代码
			Element orgCodeElement = rowElement.addElement("ZZJGDM");
			orgCodeElement.setText(blackList.getOrgCode());
			// 营业执照编号
			Element businessCodeElement = rowElement.addElement("YYZZBH");
			businessCodeElement.setText(blackList.getBusinessCode());
		}
		try {
			//格式化输出xml文件，兼容ie的格式化输出
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			//把xml文件从内存中写入文件
			XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			return "生成黑名单失败";
		}
		return null;
	}
}
