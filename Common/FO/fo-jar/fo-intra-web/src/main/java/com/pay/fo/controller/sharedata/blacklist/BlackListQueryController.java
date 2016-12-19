package com.pay.fo.controller.sharedata.blacklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.batchinfo.service.model.BlackList;
import com.pay.fundout.batchinfo.service.model.BlackListDTO;
import com.pay.poss.base.controller.AbstractBaseController;

public class BlackListQueryController extends AbstractBaseController {

	/*
	 * 预警核查授权证书地址
	 */
	public static final String WARNING_LICENCE_ADDRESS = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("config").append(File.separator).append("sharedata").append(File.separator).append("授权文件_支付联盟——_zfhhzfhh36197_3418.txt").toString();
	
	/*
	 * 总数查询授权证书地址
	 */
	public static final String TOTAL_LICENCE_ADDRESS = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("config").append(File.separator).append("sharedata").append(File.separator).append("授权文件_支付联盟——_zfhhzfhh36197_3419.txt").toString();

	/*
	 * 明细查询授权证书地址
	 */
	public static final String DETAIL_LICENCE_ADDRESS = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("config").append(File.separator).append("sharedata").append(File.separator).append("授权文件_支付联盟——_zfhhzfhh36197_3420.txt").toString();
	
	/*
	 * 调用的webservice目标地址
	 */
	public static final String TARGET_ADDRESS = "https://api.nciic.com.cn/nciic_ws/services/NciicServices";
	
	public static final String WARNING_QUERY = "黑名单核查";
	
	public static final String TOTAL_QUERY = "记录数查询";
	
	public static final String DETAIL_QUERY = "黑名单查询";
	
	private BlackList blackList = null;
	
	/**
	 * 初始化查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("initSearch");
		return new ModelAndView(viewName);
	}

	/**
	 * 像黑名单共享中心查询相关黑名单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		blackList = null;
		String viewName = urlMap.get("searchResult");
		final BlackListDTO dto = new BlackListDTO();
		bind(request, dto, "dto", null);
		String condition = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS><INFO><SBM>" 
			+ dto.getSbm() + "</SBM></INFO><ROW><HMDLX>黑名单类型</HMDLX><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM><JGMC>机构名称</JGMC><YYZZBH>机构营业执照编号</YYZZBH><SJHM>手机号码</SJHM><YHKH>银行卡号</YHKH><EMAIL>邮箱地址</EMAIL><URLDZ>URL地址</URLDZ><URLTZDZ>URL跳转地址</URLTZDZ></ROW>"
			+ "<ROW FSD=\"" + dto.getOccuredArea() + "\" YWLX=\"" + dto.getBusinessType() + "\">"
			+ "<HMDLX>" + dto.getType() + "</HMDLX><GMSFHM>" + dto.getIdentityCode() + "</GMSFHM><XM>" + dto.getName() + "</XM><JGMC>" 
			+ dto.getOrgName() + "</JGMC><YYZZBH>" + dto.getBusinessCode() + "</YYZZBH><SJHM>" + dto.getMobile() + "</SJHM><YHKH>"
			+ dto.getBankCode() + "</YHKH><EMAIL>" + dto.getEmail() + "</EMAIL><URLDZ>" + dto.getUrlAddress() + "</URLDZ><URLTZDZ>" + dto.getUrlBranchAddress() + "</URLTZDZ></ROW></ROWS>";
		log.info("condition is " + condition);
		Service service = new Service();
		String content = null;
		String address = null;
		try {
			if(dto.getBusinessType().equals(WARNING_QUERY)){
				address = WARNING_LICENCE_ADDRESS;
			}else if(dto.getBusinessType().equals(TOTAL_QUERY)){
				address = TOTAL_LICENCE_ADDRESS;
			}else{
				address = DETAIL_LICENCE_ADDRESS;
			}
			BufferedReader in = new BufferedReader(new FileReader(address));
			content = in.readLine();
		} catch (FileNotFoundException e) {
			log.error("授权证书不存在", e);
		}catch (IOException e) {
			log.error("读取授权证书出错", e);
		}
		String result = "";
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(TARGET_ADDRESS));
			call.setOperationName("nciicCombineSearch");
			String wsResult = (String) call.invoke(new Object[] {content, condition});
			log.info("wsResult is " + wsResult);
			result = handleResult(wsResult, dto.getBusinessType());
		} catch (Exception e) {
			log.error("调用webserivice出错", e);
		}
		// 页面上面如果blackList为空，那么就显示result信息，否则任务是详细查询，展示黑名单的各对应项的值
		return new ModelAndView(viewName).addObject("result", result).addObject("blackList", blackList);
	}
	
	/**
	 * 处理数据共享中心返回的信息
	 * @param businessType
	 * @param blackList
	 * @return
	 * @throws DocumentException 
	 * @throws DocumentException 
	 */
	private String handleResult(String wsResult, String businessType) throws DocumentException{
		// 下面注释掉的这句纯粹是为了测试用
		// wsResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS><ROW no=\"1\"><INPUT><hmdlx>2</hmdlx><gmsfhm>110101198001012526</gmsfhm><xm>张三</xm><jgmc></jgmc><yyzzbh></yyzzbh><sjhm></sjhm><yhkh></yhkh><email></email><urldz></urldz><urltzdz></urltzdz></INPUT><OUTPUT><ITEM><errormesage>服务结果:Y</errormesage></ITEM><ITEM><errormesagecol></errormesagecol></ITEM></OUTPUT></ROW><ROW no=\"2\"><INPUT><hmdlx>1</hmdlx><gmsfhm>110101198001012528</gmsfhm><xm></xm><jgmc></jgmc><yyzzbh></yyzzbh><sjhm>13911116666</sjhm><yhkh></yhkh><email></email><urldz></urldz><urltzdz></urltzdz></INPUT><OUTPUT><ITEM><errormesage>服务结果:N</errormesage></ITEM><ITEM><errormesagecol></errormesagecol></ITEM></OUTPUT></ROW></ROWS>";
		// wsResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS><ROW no=\"1\"><INPUT><hmdlx>2</hmdlx><gmsfhm>110101198001012528</gmsfhm><xm></xm><jgmc></jgmc><yyzzbh></yyzzbh><sjhm></sjhm><yhkh></yhkh><email></email><urldz></urldz><urltzdz></urltzdz></INPUT><OUTPUT><ITEM><errormesage>服务结果:没有符合条件的记录</errormesage></ITEM><ITEM><errormesagecol></errormesagecol></ITEM></OUTPUT></ROW></ROWS>";
//		wsResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS><ROW no=\"1\"><INPUT>"
// + "<hmdlx>1</hmdlx><gmsfhm>110101198101012511</gmsfhm><xm></xm><jgmc></jgmc><yyzzbh></yyzzbh><sjhm></sjhm><yhkh></yhkh><email></email><urldz></urldz><urltzdz></urltzdz></INPUT><OUTPUT>"
// + "<ITEM><result_hmdid>100390000055</result_hmdid></ITEM><ITEM><result_hmdlx>2</result_hmdlx></ITEM>"
// + "<ITEM><result_cydwbm>1005</result_cydwbm></ITEM><ITEM><result_lhtj>16</result_lhtj></ITEM><ITEM><result_hmdsj>105</result_hmdsj></ITEM>"
// + "<ITEM><result_bjsj>2012-05-31 00:00:00.0</result_bjsj></ITEM><ITEM><result_sjzt>1</result_sjzt></ITEM><ITEM><result_hmdsjbz>由银行投诉存在22233</result_hmdsjbz></ITEM>"
// + "<ITEM><result_gmsfhm>110101198101012511</result_gmsfhm></ITEM><ITEM><result_xm>d</result_xm></ITEM><ITEM><result_jgmc>gu</result_jgmc></ITEM>"
// + "<ITEM><result_yyzzbh>5533</result_yyzzbh></ITEM><ITEM><result_icp>苏B2-200977</result_icp></ITEM><ITEM><result_icpbar>s</result_icpbar></ITEM>"
// + "<ITEM><result_zfr>t</result_zfr></ITEM><ITEM><result_fsdq>1200</result_fsdq></ITEM><ITEM><result_sjhm>13800010024</result_sjhm></ITEM>"
// + "<ITEM><result_gddh>010-66665544#020-7755555555</result_gddh></ITEM><ITEM><result_yhkh>6225880122565639#5555555555555</result_yhkh></ITEM>"
// + "<ITEM><result_khh>中国建设银行北京王府</result_khh></ITEM><ITEM><result_email>zhangsan@sohu.com</result_email></ITEM>"
// + "<ITEM><result_urldz>https://wwwffff</result_urldz></ITEM><ITEM><result_urltzdz>http://wggggww.he11o.com.cn111/</result_urltzdz></ITEM>"
// + "<ITEM><result_dz>h</result_dz></ITEM><ITEM><result_ip>202.113.5.66#211.888</result_ip></ITEM>"
// + "<ITEM><result_mac>00-1C-BF-DD-EE-77</result_mac></ITEM><ITEM><result_czr></result_czr></ITEM></OUTPUT></ROW></ROWS>";
		
		boolean isError = false;
		Document document;
		String rootElementName = "";
		document = DocumentHelper.parseText(wsResult);
		rootElementName = document.getRootElement().getName();
		StringBuffer sb = new StringBuffer();
		List list;
		if(rootElementName.equalsIgnoreCase("RESPONSE")){
			isError = true;
		}
		if(!isError){
			// 返回正常xml，则按照正常结果进行解析
			if(businessType.equals(WARNING_QUERY)){
				// 预警核查
				list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/errormesage");
				System.out.println(((Element)list.get(0)).getText());
				return ((Element)list.get(0)).getText();
			}else if(businessType.equals(TOTAL_QUERY)){
				// 记录数查询
				list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/errormesage");
				System.out.println(((Element)list.get(0)).getText());
				return ((Element)list.get(0)).getText();
			}else if(businessType.equals(DETAIL_QUERY)){
				// 详细信息查询
				list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/errormesage");
				if(list != null && list.size() > 0){
					System.out.println(((Element)list.get(0)).getText());
					return ((Element)list.get(0)).getText();
				}else{
					blackList = new BlackList();
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_hmdid");
					String id = ((Element)list.get(0)).getText();
					blackList.setId(id);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_hmdlx");
					String type = ((Element)list.get(0)).getText();
					blackList.setType(type);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_cydwbm");
					String memberUnitCode = ((Element)list.get(0)).getText();
					blackList.setMemberUnitCode(memberUnitCode);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_lhtj");
					String way = ((Element)list.get(0)).getText();
					blackList.setWay(way);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_hmdsj");
					String event = ((Element)list.get(0)).getText();
					blackList.setEvent(event);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_bjsj");
					String markTimeStr = ((Element)list.get(0)).getText();
					blackList.setMarkTimeStr(markTimeStr);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_sjzt");
					String status = ((Element)list.get(0)).getText();
					blackList.setStatus(status);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_hmdsjbz");
					String remark = ((Element)list.get(0)).getText();
					blackList.setRemark(remark);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_gmsfhm");
					String identityCode = ((Element)list.get(0)).getText();
					blackList.setIdentityCode(identityCode);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_xm");
					String name = ((Element)list.get(0)).getText();
					blackList.setName(name);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_jgmc");
					String orgName = ((Element)list.get(0)).getText();
					blackList.setOrgName(orgName);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_yyzzbh");
					String businessCode = ((Element)list.get(0)).getText();
					blackList.setBusinessCode(businessCode);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_icp");
					String icpCode = ((Element)list.get(0)).getText();
					blackList.setIcpCode(icpCode);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_icpbar");
					String icpMember = ((Element)list.get(0)).getText();
					blackList.setIcpMember(icpMember);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_zfr");
					String payerName = ((Element)list.get(0)).getText();
					blackList.setPayerName(payerName);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_fsdq");
					String occuredArea = ((Element)list.get(0)).getText();
					blackList.setOccuredArea(occuredArea);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_sjhm");
					String mobile = ((Element)list.get(0)).getText();
					blackList.setMobile(mobile);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_gddh");
					String telephone = ((Element)list.get(0)).getText();
					blackList.setTelephone(telephone);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_yhkh");
					String bankCode = ((Element)list.get(0)).getText();
					blackList.setBankCode(bankCode);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_khh");
					String bankName = ((Element)list.get(0)).getText();
					blackList.setBankName(bankName);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_email");
					String email = ((Element)list.get(0)).getText();
					blackList.setEmail(email);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_urldz");
					String urlAddress = ((Element)list.get(0)).getText();
					blackList.setUrlAddress(urlAddress);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_urltzdz");
					String urlBranchAddress = ((Element)list.get(0)).getText();
					blackList.setUrlBranchAddress(urlBranchAddress);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_dz");
					String address = ((Element)list.get(0)).getText();
					blackList.setAddress(address);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_ip");
					String ip = ((Element)list.get(0)).getText();
					blackList.setIp(ip);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_mac");
					String mac = ((Element)list.get(0)).getText();
					blackList.setMac(mac);
					list = document.selectNodes("/ROWS/ROW/OUTPUT/ITEM/result_czr");
					String operator = ((Element)list.get(0)).getText();
					blackList.setOperator(operator);
				}
			}
		}else{
			// 返回错误码，则将错误码和错误信息显示
			list = document.selectNodes("/RESPONSE/ROWS/ROW/ErrorCode");
			sb.append("查询出错，错误码：" + ((Element)list.get(0)).getText() + "，错误码描述：");
			list = document.selectNodes("/RESPONSE/ROWS/ROW/ErrorMsg");
			sb.append(((Element)list.get(0)).getText());
			System.out.println(sb.toString());
			return sb.toString();
		}
		return null;
	}
	
	public static void main(String[] args) throws DocumentException{
//		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS><ROW no=\"1\"><INPUT><hmdlx>2</hmdlx><gmsfhm>110101198001012526</gmsfhm><xm>张三</xm><jgmc></jgmc><yyzzbh></yyzzbh><sjhm></sjhm><yhkh></yhkh><email></email><urldz></urldz><urltzdz></urltzdz></INPUT><OUTPUT><ITEM><errormesage>服务结果:Y</errormesage></ITEM><ITEM><errormesagecol></errormesagecol></ITEM></OUTPUT></ROW><ROW no=\"2\"><INPUT><hmdlx>1</hmdlx><gmsfhm>110101198001012528</gmsfhm><xm></xm><jgmc></jgmc><yyzzbh></yyzzbh><sjhm>13911116666</sjhm><yhkh></yhkh><email></email><urldz></urldz><urltzdz></urltzdz></INPUT><OUTPUT><ITEM><errormesage>服务结果:N</errormesage></ITEM><ITEM><errormesagecol></errormesagecol></ITEM></OUTPUT></ROW></ROWS>";
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"<RESPONSE errorcode=\"-80\" code=\"0\" countrows=\"1\"><ROWS><ROW><ErrorCode>-80</ErrorCode><ErrorMsg>授权文件格式错误</ErrorMsg></ROW></ROWS></RESPONSE>";
		Document document = DocumentHelper.parseText(str);
		List list = document.selectNodes("/RESPONSE/ROWS/ROW/ErrorMsg");
		System.out.println(list.size());
	}
}
