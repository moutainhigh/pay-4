<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script src="./js/mainstyle1/body.js"></script>
<script language="javascript">

function changeProvince(relationList){
	var relationArray = new Array();
	<c:forEach items = "${relationList}" var = "relation" varStatus = "relationStatus">
		relationArray[${relationStatus.index}] = new dropDownListMode('${relation.fatherCode}','${relation.code}','${relation.name}');	
	</c:forEach>
	
	this.changeFatherSelect('region','city',relationArray,null);
}

function checkOk(){
	var emptyBankidL =  $(":input[name=bigBankId][value=0]").size();
	if(emptyBankidL>0){
		alert("审核不通过，开户行未选择，请到商户准入编辑中修改开户行");
		return false;
	}
	document.merchantFormBean.action="merchantEditForCheck.do?type=checkOk";
	document.merchantFormBean.submit();
}

</script>
</head>
<body>


<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商 户 信 息 审 核</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户信息审核</h2>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<form id="merchantFormBean" name="merchantFormBean"  method="post" target="hideIframe"> 
<input type="hidden" id="memberCode" name="memberCode" value="${merchantDto.memberCode}">
<input type="hidden" id="merchantCode" name="merchantCode" value="${merchantDto.merchantCode}">
<input type="hidden" id="merchantEmail" name="merchantEmail" value="${merchantDto.email}">
<input type="hidden" id="merchantName" name="merchantName" value="${merchantDto.zhName}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户基本信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >
				 ${merchantDto.zhName} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.enName} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户主网站网址：</td>
			<td class="border_top_right4" align="left" >
			${merchantDto.website} &nbsp;
				
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户类型：</td>
			<td class="border_top_right4" align="left" >
			<c:forEach items="${merchantTypeEnum}" var="merchantLevel">
						<c:if test="${merchantLevel.code == merchantDto.enterpriseType}">
							${merchantLevel.description}
						</c:if>
			</c:forEach>			
								&nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户等级：</td>
			<td class="border_top_right4" align="left" >
					<c:forEach items="${merchantLevelEnum}" var="merchantLevel">
						<c:if test="${merchantLevel.code == merchantDto.serviceLevelCode}"> 
							${merchantLevel.description} &nbsp;
						</c:if>
					</c:forEach>	
					
			</td>
			<td class="border_top_right4" align="right" >MCC：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.industry} &nbsp;
			</td>
		</tr>
	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >所属国家：</td>
			<td class="border_top_right4" align="left" >
				
					<c:forEach items="${nationEnum}" var="nation">
						<c:if test="${nation.code == merchantDto.nation}">
							${nation.description} &nbsp;
						</c:if>
					</c:forEach>
			</td>
			<td class="border_top_right4" align="right" >所属地区：</td>
			<td class="border_top_right4" align="left" >
				
				<c:forEach items="${provinceList}" var="province">
					<c:if test="${province.provincecode == merchantDto.region}"> 
						${province.provincename} &nbsp;
					</c:if>
				</c:forEach>
			</td>
			<td class="border_top_right4" align="right" >所属城市：</td>
			<td class="border_top_right4" align="left" >
					${merchantDto.cityName} &nbsp;
			</td>
			
		</tr>	
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户营业执照号码：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.bizLicenceCode} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户营业执照有效期：</td>
			<td class="border_top_right4" align="left" >
				${fn:substring(merchantDto.expire, 0, 10)} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户机构代码证号码：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.govCode} &nbsp;
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户税务登记证号码：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.taxCode} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户风险等级：</td>
			<td class="border_top_right4" align="left">
					
					<c:forEach items="${riskLevelList}" var="riskLevel">
					<c:if test="${riskLevel.riskLevel == merchantDto.riskLeveCode}"> 
						${riskLevel.levelName} &nbsp;
					</c:if>
					</c:forEach>
			</td>
			<td class="border_top_right4" align="right" >会员号等级：</td>
			<td class="border_top_right4" align="left">
					<c:if test='${merchantDto.merchantType == "2"}'> 
						普通会员 &nbsp;
					</c:if>
					<c:if test='${merchantDto.merchantType == "3"}'> 
						平台会员 &nbsp;
					</c:if>
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户联系信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称1：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.webName1} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户网站网址1：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.webAddr1} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >传真：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.fax} &nbsp;
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称2：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.webName2} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户网站网址2：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.webAddr2} &nbsp;
			</td> 
			<td class="border_top_right4" align="right" >公司电话：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.tel} &nbsp;
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称3：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.webName3} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >商户网站网址3：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.webAddr3} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >公司邮编：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.zip} &nbsp;
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司法人姓名：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.legalName} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >公司法人联系电话：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.legalLink} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >公司地址：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.address} &nbsp;
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.compayLinkerName} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >公司联系人电话：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.compayLinkerTel} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.financeName} &nbsp;
			</td>
		</tr>
		
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司技术联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.techName} &nbsp;
				
			</td>
			<td class="border_top_right4" align="right" >公司技术联系人电话：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.techLink} &nbsp;
				
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人电话：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.financeLink} &nbsp;
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司Email：</td>
			<td class="border_top_right4" align="left" colspan="5" >
				${merchantDto.email}
				<font color="red">(此邮件地址将作为激活商户后发送邮件地址)</font>
			</td>
		</tr>
		
		
		
		<tr class="trForContent2">
		<td class="border_top_right4" align="left" colspan="6">商户结算信息：</td></tr>
		<c:forEach items="${merchantList}" var="dto" varStatus="status">
		<tr class="trForContent1">
		<td class="border_top_right4" align="left" colspan="6">账户${status.count }</td></tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >开户行所属地区：</td>
				<td class="border_top_right4" align="left" >
					
					<c:forEach items="${provinceList}" var="province">
						 <c:if test="${province.provincecode == dto.regionBank}"> ${province.provincename} </c:if>
					</c:forEach>
				</td>
				<td class="border_top_right4" align="right" >开户行所属城市：</td>
				<td class="border_top_right4" align="left" >
						${dto.cityBankName} &nbsp;
				</td>
				<td class="border_top_right4" align="right" >结算方式：</td>
				<td class="border_top_right4" align="left" >
						
						<c:forEach items="${liquidateModeEnum}" var="liquidateMode">
							<c:if test="${liquidateMode.code == dto.settlementCycle}">${liquidateMode.description} </c:if>
						</c:forEach>
												
				</td>
				
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >商户结算银行名称：</td>
				<td class="border_top_right4" align="left" >
						<c:forEach items="${bankList}" var="bank"><c:if test="${bank.bankId == dto.bankId}">${bank.bankName}</c:if></c:forEach>
					<input type="hidden" name="bigBankId" value="${dto.bankId}" />&nbsp;
				</td>
				<td class="border_top_right4" align="right" >银行账户号：</td>
				<td class="border_top_right4" align="left" >
					${dto.bankAcct} &nbsp;
				</td>
				<td class="border_top_right4" align="right" >商户结算账户名称：</td>
				<td class="border_top_right4" align="left" >
					
`					${dto.acctName} &nbsp;
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >结算银行支行名称：</td>
				<td class="border_top_right4" align="left" >
					<input type="hidden" name="bigBankName" value="${dto.acctName}" />				
					${dto.bankName} &nbsp;
				</td>
				<td class="border_top_right4" align="right" >商户结算银行地址：</td>
				<td class="border_top_right4" align="left" colspan="3">
					${dto.bankAddress} &nbsp;
				</td>
			</tr>
			</c:forEach>
		
		
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">签约人信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人姓名：</td>
			<td class="border_top_right4" align="left" >
				${merchantDto.signName} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >签约人部门：</td>
			<td class="border_top_right4" align="left" >
			${merchantDto.signDepart} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >是否自动续约：</td>
			<td class="border_top_right4" align="left" >
						
						<c:if test="${0 == merchantDto.continueSign}">
						否
						</c:if>
						<c:if test="${1 == merchantDto.continueSign}">
						是
						</c:if>				
											
			</td>
		</tr>
			<tr class="trForContent1">
			<td class="border_top_right4" align="right" >联系信息：</td>
			<td class="border_top_right4" align="left">
				${merchantDto.marketLink} &nbsp;
			</td>
			<td class="border_top_right4" align="right" >签约人帐号 ：</td>
			<td class="border_top_right4" align="left"  colspan="3">
					${merchantDto.signLoginId} &nbsp;
			</td>
			
		</tr>
		<!-- <tr class="trForContent1">
			<td class="border_top_right4" align="right" >退回原因：</td>
			<td class="border_top_right4" align="left" colspan="5">
				<textArea id="goBackRemark" name="goBackRemark"></textArea>
				
			</td>
			
		</tr> -->
		<tr class="trForContent1">	
		<td colspan="6" class="border_top_right4"  align="center">	
		<c:if test="${isCheckedPerm==true}">
			<input type = "button"  onclick="javascript:checkOk();" value="审核通过">
		</c:if>	
		
								
		</td>
	</tr>
</table>
<%-- <br></br>
<table>
	<tr>	
		<td  align="center">	
		<c:if test="${isCheckedPerm==true}">
			<input type = "button"  onclick="javascript:checkOk();" value="审核通过">
		</c:if>	
		
								
		</td>
	</tr>
</table> --%>

</form>
<iframe name="hideIframe" style="display:none;"></iframe>

</body>

