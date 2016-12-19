<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>

<script src="./js/common.js"></script>
<script src="./js/mainstyle1/body.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<style>
.search_box{ position:relative;}
.pop_con{ height:100px; padding:0; border:1px solid #999; background:#FFFCED; position:absolute; top:-113px; left:0; overflow:auto;}
.pop_con ul{margin:0;padding:0;}
.pop_con li{ padding:0 5px; list-style:none; height:20px; line-height:20px; overflow:hidden;}
.pop_con .hover{ background:#EADFBB}
</style>
<script language="javascript">
$(function(){
	var industryBoolean = true;
	var bizLicenceCodeBoolean = true;
	var govCodeBoolean = true;
	var taxCodeBoolean = true;
	var emailBoolean = true;
	var signDepartBoolean = true;
	$("#industry").focusout(function(){
		var industry = $.trim($("#industry").val());
		if('' != industry){
			var pars = "industry=" + industry;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantEditValidate.do?method=industry&memberCode=${merchantDto.memberCode}",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						alert("您输入MCC值不存在,请与管理员联系!");
						$("#submitForm").attr("disabled","true");
						industryBoolean = false;
					}else{
						industryBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean&&signDepartBoolean){
							$("#submitForm").removeAttr("disabled"); 
						}
						
					} 
				}
			});
		}
	});
	$("#bizLicenceCode").focusout(function(){
		var bizLicenceCode = $.trim($("#bizLicenceCode").val());
		if('' != bizLicenceCode){
			var pars = "bizLicenceCode=" + bizLicenceCode;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantEditValidate.do?method=bizLicenceCode&memberCode=${merchantDto.memberCode}",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						alert("商户营业执照号码已经存在!");
						$("#submitForm").attr("disabled","true");
						bizLicenceCodeBoolean = false;
					}else{
						bizLicenceCodeBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean&&signDepartBoolean){
							$("#submitForm").removeAttr("disabled"); 
						}
					} 
				}
			});
		}
	});
	$("#govCode").focusout(function(){
		var govCode = $.trim($("#govCode").val());
		if('' != govCode){
			var pars = "govCode=" + govCode;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantEditValidate.do?method=govCode&memberCode=${merchantDto.memberCode}",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						alert("商户机构代码证号码已经存在!");
						$("#submitForm").attr("disabled","true");
						govCodeBoolean = false;
					}else{
						govCodeBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean&&signDepartBoolean){
							$("#submitForm").removeAttr("disabled"); 
						}
					} 
				}
			});
		}
	});
	$("#taxCode").focusout(function(){
		var taxCode = $.trim($("#taxCode").val());
		if('' != taxCode){
			var pars = "taxCode=" + taxCode;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantEditValidate.do?method=taxCode&memberCode=${merchantDto.memberCode}",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						alert("商户税务登记证号码已经存在!");
						$("#submitForm").attr("disabled","true");
						taxCodeBoolean = false;
					}else{
						taxCodeBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean&&signDepartBoolean){
							$("#submitForm").removeAttr("disabled"); 
						} 
					} 
				}
			});
		}
	});
	//added by tianqing_wang 2010-12-30
	$("#signDepart").blur(function(){
		var signDepart = $.trim($("#signDepart").val());
		if('' != signDepart){
			var pars = "departmentName=" + signDepart;
			$.ajax({
				type: "POST",
				url: "${ctx}/signDepartSearch.do?method=signDepSearchAsyn",
				data: pars,
				success: function(result) {
					var info = result.split("|");
					var html="";
					for(i=1;i<info.length;i++){
						var infoValue = info[i];
						html+="<li><input type='radio' name='searchData' value='"+infoValue+"' onclick='javascript:check()' >"+infoValue+"</li>";
					}
					//当html有值时显示层
					if(html!=""){
						signDepartBoolean = true;
						document.getElementById("aaa").innerHTML = html;
						document.getElementById('resultListDiv').style.display = '';
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&signDepartBoolean){
							$("#submitForm").removeAttr("disabled"); 
						} 
					}else{
						alert("您输入的签约人部门不存在!");
						$("#submitForm").attr("disabled","true");
						signDepartBoolean = false;
					}
				}
			});
		}
	});
});
function changeProvince(father,son){
	var relationArray = new Array();
	<c:forEach items = "${relationList}" var = "relation" varStatus = "relationStatus">
		relationArray[${relationStatus.index}] = new dropDownListMode('${relation.fatherCode}','${relation.code}','${relation.name}');	
	</c:forEach>
	
	this.changeFatherSelect(father,son,relationArray,null);
}
function closePage(url){
	parent.closePage(url);
}

function check(){
	var searchData = document.getElementsByName("searchData");
	for(var i = 0;i<searchData.length;i++){
		if(searchData[i].checked==true){
			document.getElementById('signDepart').value = searchData[i].value;
	    }
	}
	document.getElementById('resultListDiv').style.display = "none";
}


function loadBranchBanks( defaultBankNo){
	var bankName = $("#bigBankName").val();
	var provinceName = $("#regionBank").find("option:selected").text();
	var cityName = $("#cityBank").find("option:selected").text();
	if(bankName && provinceName && cityName){
		var submitObj = {};
		submitObj.bankName = bankName;
		submitObj.provinceName = provinceName;
		submitObj.cityName = cityName;
		$("#branchBankId").load("${ctx}/getBranchBanksOptions.do",submitObj);	
	} 
}


function loadBranchBanks( defaultBankNo){
	var bankName = $("#bigBankName").val();
	var provinceName = $("#regionBank").find("option:selected").text();
	var cityName = $("#cityBank").find("option:selected").text();
	if(bankName && provinceName && cityName){
		var submitObj = {};
		submitObj.bankName = bankName;
		submitObj.provinceName = provinceName;
		submitObj.cityName = cityName;
		$("#branchBankId").load("${ctx}/getBranchBanksOptions.do",submitObj);	
	} 
}

var bigBankIds = [];
var bigBankNames = [];
$().ready(function() {
	<c:forEach items="${bankList}" var="bank" varStatus = "status">
		bigBankIds[${status.index}] = '${bank.bankId}';
		bigBankNames[${status.index}] = '${bank.bankName}';
	</c:forEach>
	$("#bigBankName").autocomplete(bigBankNames, {
		selectFirst:true,
		matchContains:true,
		autoFill:true,
		minChar:0
		});
	});

function getBankIdByBankName(bankName){
	for(i=0;i<bigBankNames.length;i++){
		if(bankName==bigBankNames[i]){
			return bigBankIds[i];
		}
	}
}			
		

</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商 户 信 息 编 辑</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<form id="merchantFormBean" name="merchantFormBean" action="merchantEditForEdit.do" method="post" onsubmit="return validator(this)">
<input type="hidden" id="memberCode" name="memberCode" value="${merchantDto.memberCode}">
<input type="hidden" id="merchantCode" name="merchantCode" value="${merchantDto.merchantCode}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户基本信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="zhName" name="zhName" maxlength= "32" valid="required" errmsg="商户中文名称不能为空!" value="${merchantDto.zhName}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="enName" name="enName" maxlength= "32"  value="${merchantDto.enName}">
			</td>
			<td class="border_top_right4" align="right" >商户主网站网址：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="website" name="website" maxlength= "64"  valid="required|isURL" errmsg="商户主网站网址不能为空!|商户主网站网址格式不正确" value="${merchantDto.website}">
				
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户类型：</td>
			<td class="border_top_right4" align="left" >
				<select id="enterpriseType" name="enterpriseType"  valid="required" errmsg="商户类型不能为空!">
					<option value="" >---请选择---</option>
					<c:forEach items="${merchantTypeEnum}" var="enterpriseType">
						<option value="${enterpriseType.code}"<c:if test="${enterpriseType.code == merchantDto.enterpriseType}"> selected="selected" </c:if>>${enterpriseType.description}</option>
					</c:forEach>
								
				</select>
			
			</td>
			<td class="border_top_right4" align="right" >商户等级：</td>
			<td class="border_top_right4" align="left" >
				<select id="serviceLevelCode" name="serviceLevelCode"  valid="required" errmsg="商户等级不能为空!">
					<option value="" selected>---请选择---</option>	
					<c:forEach items="${merchantLevelEnum}" var="merchantLevel">
					<option value="${merchantLevel.code}"<c:if test="${merchantLevel.code == merchantDto.serviceLevelCode}"> selected="selected" </c:if>>${merchantLevel.description}</option>
					</c:forEach>	
				</select>
					
			</td>
			<td class="border_top_right4" align="right" >MCC：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="industry" name="industry"  maxlength= "4" valid="required|isInt" errmsg="MCC不能为空!|MCC格式不正确!" value="${merchantDto.industry}">
				<font color="red">*</font>
			</td>
		</tr>
	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >所属国家：</td>
			<td class="border_top_right4" align="left" >
				
				<select id="nation" name="nation" 	 valid="required" errmsg="所属地区不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${nationEnum}" var="nation">
						<option value="${nation.code}"<c:if test="${nation.code == merchantDto.nation}"> selected="selected" </c:if>>${nation.description}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >所属地区：</td>
			<td class="border_top_right4" align="left" >
				<select id="region" name="region" 	onchange="changeProvince('region','city');" valid="required" errmsg="所属地区不能为空!">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${provinceList}" var="province">
					<option value="${province.provincecode}"<c:if test="${province.provincecode == merchantDto.region}"> selected="selected" </c:if>>${province.provincename}</option>
				</c:forEach>
			</select>
			<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >所属城市：</td>
			<td class="border_top_right4" align="left" >
				<select	id="city" name="city"  valid="required" errmsg="所属城市不能为空!">
					<option value="${merchantDto.city}" selected>${merchantDto.cityName}</option>
				</select>
				<font color="red">*</font>
			</td>
			
		</tr>	
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户营业执照号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="bizLicenceCode" name="bizLicenceCode"  maxlength= "15" valid="required|isInt" errmsg="商户营业执照号码不能为空!|商户营业执照号码只能为数字!"  value="${merchantDto.bizLicenceCode}">
				<font color="red">*必须唯一</font>
			</td>
			<td class="border_top_right4" align="right" >商户营业执照有效期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "expire" name="expire"  onClick="WdatePicker()"

				
					 valid="required" errmsg="商户营业执照有效期不能为空!" value='${fn:substring(merchantDto.expire, 0, 10)}' >
			</td>
			<td class="border_top_right4" align="right" >商户机构代码证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="govCode" name="govCode"  maxlength= "10" valid="required" errmsg="商户机构代码证号码不能为空!" value="${merchantDto.govCode}">
				<font color="red">*必须唯一</font>
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户税务登记证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="taxCode" name="taxCode"  maxlength= "15" valid="required" errmsg="商户税务登记证号码不能为空!" value="${merchantDto.taxCode}">
				<font color="red">*必须唯一</font>
			</td>
			<td class="border_top_right4" align="right" >商户风险等级：</td>
			<td class="border_top_right4" align="left" colspan="3">
				<select	id="riskLeveCode" name="riskLeveCode"  valid="required" errmsg="商户风险等级不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${riskLevelList}" var="riskLevel">
					<option value="${riskLevel.riskLevel}"<c:if test="${riskLevel.riskLevel == merchantDto.riskLeveCode}"> selected="selected" </c:if>>${riskLevel.levelName}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户联系信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称1：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName1"  maxlength="16"  name="webName1" value="${merchantDto.webName1}">
			</td>
			<td class="border_top_right4" align="right" >商户网站网址1：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr1" name="webAddr1"   maxlength="64" valid="isURL" errmsg="商户网站网址1格式不正确" value="${merchantDto.webAddr1}">
			</td>
			<td class="border_top_right4" align="right" >传真：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="fax" name="fax" valid="required|isPhone"   maxlength="16"  errmsg="传真不能为空!|传真格式不正确" value="${merchantDto.fax}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称2：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName2" name="webName2"   maxlength="16" value="${merchantDto.webName2}">
			</td>
			<td class="border_top_right4" align="right" >商户网站网址2：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr2" name="webAddr2"   maxlength="64" valid="isURL" errmsg="商户网站网址2格式不正确" value="${merchantDto.webAddr2}">
			</td>
			<td class="border_top_right4" align="right" >公司电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="tel" name="tel"   maxlength="16" valid="required" errmsg="公司电话不能为空!" value="${merchantDto.tel}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称3：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName3"   maxlength="16" name="webName3" value="${merchantDto.webName3}">
			</td>
			<td class="border_top_right4" align="right" >商户网站网址3：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr3" name="webAddr3"  maxlength="64"  valid="isURL" errmsg="商户网站网址3格式不正确" value="${merchantDto.webAddr3}">
			</td>
			<td class="border_top_right4" align="right" >公司邮编：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="zip" name="zip"   maxlength="6" valid="required|isPost" errmsg="公司邮编不能为空!|公司邮编格式不正确" value="${merchantDto.zip}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司法人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="legalName" name="legalName"   maxlength="8" valid="required" errmsg="公司法人姓名不能为空!" value="${merchantDto.legalName}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司法人联系电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="legalLink" name="legalLink"   maxlength="16"  valid="required|isPhoneAndMobile" errmsg="公司法人联系电话不能为空!|公司法人联系电话格式不正确" value="${merchantDto.legalLink}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司地址：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="address" name="address"   maxlength="32" valid="required" errmsg="公司地址不能为空!" value="${merchantDto.address}">
				
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="compayLinkerName" name="compayLinkerName"   maxlength="8" valid="required" errmsg="公司联系人姓名不能为空!" value="${merchantDto.compayLinkerName}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="compayLinkerTel" name="compayLinkerTel"   maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司联系人电话不能为空!|公司联系人电话格式不正确" value="${merchantDto.compayLinkerTel}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="financeName" name="financeName"   maxlength="8" valid="required" errmsg="公司财务联系人姓名不能为空!" value="${merchantDto.financeName}">
				<font color="red">*</font>
			</td>
		</tr>
		
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司技术联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="techName" name="techName"   maxlength="8" valid="required" errmsg="公司技术联系人姓名不能为空!" value="${merchantDto.techName}">
			
			</td>
			<td class="border_top_right4" align="right" >公司技术联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="techLink" name="techLink"   maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司技术联系电话不能为空!|公司技术联系电话格式不正确" value="${merchantDto.techLink}">
				
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="financeLink" name="financeLink"   maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司财务联系电话不能为空!|公司财务联系电话格式不正确" value="${merchantDto.financeLink}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司Email：</td>
			<td class="border_top_right4" align="left" colspan="5" >
				<input type="text" id="email" name="email"   maxlength="32" disabled valid="required|isEmail" errmsg="公司Email不能为空!|Email格式不对!" value="${merchantDto.email}">
				<font color="red">*</font>
				<font color="red">此邮件地址将作为激活商户后发送邮件地址,必须唯一</font>
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户合同信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >合同起始日期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()" value="${fn:substring(merchantDto.startDate, 0, 10)}">
				
			</td>
			<td class="border_top_right4" align="right" >合同结束日期</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" value="${fn:substring(merchantDto.endDate, 0, 10)}">	
				
			</td>
			<td class="border_top_right4" align="right" >开通费用：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="openFee"   maxlength="11" name="openFee" valid="isNumber" errmsg="开通费用格式不对!" value="${merchantDto.openFee}">
				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >实际合同起始日期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "factStartDate" name="factStartDate"  onClick="WdatePicker()" 
				valid="required" errmsg="实际合同起始日期不能为空!" value="${fn:substring(merchantDto.factStartDate, 0, 10)}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >实际合同结束日期</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "factEndDate" name="factEndDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'factStartDate\')}'})" 
				valid="required" errmsg="实际合同结束日期不能为空!"  value="${fn:substring(merchantDto.factEndDate, 0, 10)}">
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >年服务费：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="yearFee" name="yearFee"   maxlength="11" valid="isNumber" errmsg="年服务费格式不对!" value="${merchantDto.yearFee}">
				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >实际开通费用：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="factOpenFee" name="factOpenFee"   maxlength="11" valid="required|isNumber" errmsg="实际开通费用不能为空!|实际开通费用格式不对"  value="${merchantDto.factOpenFee}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >实际年服务费：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="factYearFee" name="factYearFee"  maxlength="11"  valid="required|isNumber" errmsg="实际年服务费不能为空!|实际年服务费格式不对" value="${merchantDto.factYearFee}"> 
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >应收保证金：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="assureFee" name="assureFee"  maxlength="11"  valid="required|isNumber" errmsg="应收保证金不能为空!|应收保证金格式不对" value="${merchantDto.assureFee}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >保证金说明：</td>
			<td class="border_top_right4" align="left" colspan="5">
				<input type="text" id="assureDesc" name="assureDesc"   maxlength="64" valid="required" errmsg="保证金说明不能为空!" value="${merchantDto.assureDesc}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户结算信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >开户行所属地区：</td>
			<td class="border_top_right4" align="left" >
				<select id="regionBank" name="regionBank" 	onchange="changeProvince('regionBank','cityBank');" valid="required" errmsg="所属地区不能为空!" value="${merchantDto.regionBank}">
				
				<c:forEach items="${provinceList}" var="province">
					<option value="${province.provincecode}" <c:if test="${province.provincecode == merchantDto.regionBank}"> selected="selected" </c:if>>${province.provincename}</option>
				</c:forEach>
			</select>
			<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >开户行所属城市：</td>
			<td class="border_top_right4" align="left" >
				<select	id="cityBank" name="cityBank"  valid="required" errmsg="所属城市不能为空!" value="${merchantDto.cityBank}">
					<option value="${merchantDto.cityBank}" selected>${merchantDto.cityBankName}</option>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >结算方式：</td>
			<td class="border_top_right4" align="left" >
				<select id="accountMode" name="accountMode"  valid="required" errmsg="结算方式不能为空!" value="${merchantDto.settlementCycle}">
					
					<c:forEach items="${liquidateModeEnum}" var="liquidateMode">
					<option value="${liquidateMode.code}"<c:if test="${liquidateMode.code == merchantDto.settlementCycle}"> selected="selected" </c:if>>${liquidateMode.description}</option>
					</c:forEach>
											
				</select>
				<font color="red">*</font>	
			</td>
			
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户结算银行名称：</td>
			<td class="border_top_right4" align="left" >
				<input	id="bigBankName" name="bigBankName"  valid="required" errmsg="商户结算银行名称不能为空!" onblur="loadBranchBanks()" style="width:220px" >
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >商户结算银行账户：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="bankAcct" name="bankAcct"   maxlength="32" valid="required|isInt" errmsg="商户结算银行账户不能为空!|商户结算银行账户只能为数字"  value="${merchantDto.bankAcct}">
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >商户结算账户名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="acctName" name="acctName"   maxlength="32" valid="required" errmsg="商户结算账户名称不能为空!" value="${merchantDto.acctName}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >结算银行支行名称：</td>
			<td class="border_top_right4" align="left" >	
				<select	id="branchBankId" name="branchBankId"  valid="required" errmsg="结算银行支行名称不能为空!" style="width:240px" >
				</select>			
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >商户结算银行地址：</td>
			<td class="border_top_right4" align="left" colspan="3">
				<input type="text" id="bankAddress" name="bankAddress"   maxlength="32" valid="required" errmsg="商户结算银行地址!" value="${merchantDto.bankAddress}">
				
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">签约人信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="signName" name="signName"   maxlength="8" valid="required" errmsg="签约人姓名不能为空!" value="${merchantDto.signName}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >签约人部门：</td>
			<td class="border_top_right4" align="left" >
				<div  class="search_box">
					<input type="text" id="signDepart" name="signDepart" valid="required" errmsg="签约人部门不能为空!" value="${merchantDto.signDepart}" ></input>&nbsp;<font color="red">*</font>
					<div id="wrap">
					 <div id="resultListDiv" class="pop_con" style="width:200px; top:-113px; left:0; display: none;">
							<ul id="aaa">
								
							</ul>
						 </div>
					
					</div>
				</div>
			</td>
			<td class="border_top_right4" align="right" >是否自动续约：</td>
			<td class="border_top_right4" align="left" >
				<select id="continueSign" name="continueSign" size="1" valid="required" errmsg="是否自动续约不能为空!">
						<option value="" >---请选择---</option>						
						<option value="0" <c:if test="${0 == merchantDto.continueSign}"> selected="selected" </c:if>>否</option>
						<option value="1"<c:if test="${1 == merchantDto.continueSign}"> selected="selected" </c:if>>是</option>				
											
				</select>
				<font color="red">*</font>
			</td>
		</tr>
</table>
<br></br>
<table>
	<tr>	
		<td  align="center">
		<c:if test="${isEditPerm==true}">
			<input id="submitForm" name="submitForm" type="submit" value="保存">
		</c:if>	
		
		<input type = "button"  onclick="javascript:closePage('merchantEditForEdit.do?memberCode=${merchantDto.memberCode}');" value="关闭">
				
		</td>
	</tr>
</table>
<input type="hidden" id="bankName" name="bankName"  />
<input type="hidden" id="bankId" name="bankId"  />
</form>


</body>

