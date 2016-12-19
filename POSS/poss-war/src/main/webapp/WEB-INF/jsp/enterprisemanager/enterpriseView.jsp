<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>

<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="li" uri="poss"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${ctx}/css/page.css">

<link href="${ctx}/css/main.css" rel="stylesheet" type="text/css">
<!-- 自定义样式 -->
<!-- jquery js包 -->
<script  type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/js/jquery/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<!-- jquery 样式 及插件样式 -->

<!-- jquery validate -->
<script src="${ctx}/js/jquery/plugins/validate/jquery.validate.min.js" type="text/javascript"></script>
<link href="${ctx}/js/jquery/plugins/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery/plugins/validate/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/plugins/validate/jquery.form.js" type="text/javascript"></script>


<script src="./js/common.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>

<script type='text/javascript' src='./js/jquery/plugins/autocomplate/thickbox-compressed.js'></script>
<script type='text/javascript' src='./js/jquery/plugins/autocomplate/jquery.autocomplete.min.js'></script>
<link rel="stylesheet" type="text/css" href="./js/jquery/plugins/autocomplate/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="./js/jquery/plugins/autocomplate/thickbox.css" />

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
	$.ajax({
		type : "POST",
		url : "${ctx}/bdController.do?method=getBDName",
		dataType:"json",
		success : function(result) {
			$.each(result,function(i,item){ 
				$("#signLoginId").append("<option value="+item.name+">"+item.loginId+"</option>");	
			});
		}
	});
	$("input[type=text]").blur(function(){
		$(this).val($.trim(this.value));
	});
	var industryBoolean = true;
	var bizLicenceCodeBoolean = true;
	var govCodeBoolean = true;
	var taxCodeBoolean = true;
	var signDepartBoolean = true;
	var emailBoolean = true;
	$("span[id*=Tip]").attr("style","color:orange");
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
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&signDepartBoolean){
							$("#submitForm").removeAttr("disabled"); 
						}
						
					} 
				}
			});
		}
	});
	
	
	$("#bizLicenceCode").focusout(function(){
		var bizLicenceCode = $.trim($("#bizLicenceCode").val());
		var bizLicenceCodeOld = $.trim($("#bizLicenceCodeOld").val());
		if(bizLicenceCodeOld == bizLicenceCode){
			$("#bizLicenceCodeTip").text("未修改");
			return ;
		}
		if('' != bizLicenceCode ){
			var pars = "bizLicenceCode=" + bizLicenceCode;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantAddValidate.do?method=bizLicenceCode",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						//alert("商户营业执照号码已经存在!");
						//$("#submitForm").attr("disabled","true");
						//bizLicenceCodeBoolean = false;
						$("#bizLicenceCodeTip").text("已存在");
					}else{
						$("#bizLicenceCodeTip").text("");
						bizLicenceCodeBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean){
							$("#submitForm").removeAttr("disabled"); 
						}
					} 
				}
			});
		}
	});
	
	$("#govCode").focusout(function(){
		var govCode = $.trim($("#govCode").val());
		var govCodeOld = $.trim($("#govCodeOld").val());
		if(govCodeOld == govCode){
			$("#govCodeTip").text("未修改");
			return ;
		}
		if('' != govCode ){
			var pars = "govCode=" + govCode;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantAddValidate.do?method=govCode",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						
					//	alert("商户机构代码证号码已经存在!");
					//	$("#submitForm").attr("disabled","true");
					//	govCodeBoolean = false;
						$("#govCodeTip").text("已存在");
					
					}else{
						$("#govCodeTip").text("");
						govCodeBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean){
							$("#submitForm").removeAttr("disabled"); 
						}
					} 
				}
			});
		}
	});
	
	$("#taxCode").focusout(function(){
		var taxCode = $.trim($("#taxCode").val());
		var taxCodeOld = $.trim($("#taxCodeOld").val());
		if(taxCode == taxCodeOld){
			$("#taxCodeTip").text("未修改");
			return ;
		}
		if('' != taxCode  ){
			var pars = "taxCode=" + taxCode;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantAddValidate.do?method=taxCode",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						
					//	alert("商户税务登记证号码已经存在!");
					//	$("#submitForm").attr("disabled","true");
					//	taxCodeBoolean = false;
						$("#taxCodeTip").text("已存在");
					}else{
						$("#taxCodeTip").text("");
						taxCodeBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean){
							$("#submitForm").removeAttr("disabled"); 
						} 
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

function loadBranchBanks(loadIndex,defaultBankNo){
	var bankName = $("#bankId_"+loadIndex).find("option:selected").text();
	var provinceName = $("#regionBank_"+loadIndex).find("option:selected").text();
	var cityName = $("#cityBank_"+loadIndex).find("option:selected").text();
	//alert(bankName+" "+provinceName+" "+cityName);
	if(bankName && provinceName && cityName){
		var submitObj = {};
		submitObj.bankName = bankName;
		submitObj.provinceName = provinceName;
		submitObj.cityName = cityName;
		$("#branchBankId_"+loadIndex).load("${ctx}/getBranchBanksOptions.do?defaultBankNo="+defaultBankNo,submitObj,function (msg){
		});	
	} 
}

function setBrahchBankName(index){
	$("#bankName_"+index).val($("#branchBankId_"+index).find("option:selected").text());
}
setTimeout(function(){
	<c:forEach items="${merchantList}" var="dto" varStatus="status">
		loadBranchBanks("${status.count}","${dto.branchBankId}");
	</c:forEach>
},300);

function submitPrevProcess(){
	$(":input[name=branchBankId]").change();
	return true;	
}
function setAcctName(){
	$(":input[name=acctName]").val($("#zhName").val());
}

function getterSignName(){
  	var signName=$("#signLoginId").val();	
	$("#signName").attr("value",signName);
}


function submitMerchantForm(){
	var signName=$("#signLoginId option:selected").text();
	$("input[name='signLoginId']").attr("value",signName);
	$("#merchantFormBean").submit();
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
		<div align="center"><font class="titletext">商 户 信 息 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户信息</h2>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<c:if test="${not empty userRelation}">
<form id="merchantFormBean" name="merchantFormBean" action="enterpriseViewEdit.do" method="post" onsubmit="return (validator(this)&&submitPrevProcess())" target="hideIframe">
<input type="hidden" readonly="readonly" id="memberCode" name="memberCode" value="${merchantDto.memberCode}">
<input type="hidden" readonly="readonly" id="merchantCode" name="merchantCode" value="${merchantDto.merchantCode}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户基本信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >
				
				<input type="text" readonly="readonly" id="zhName" name="zhName" maxlength= "32" valid="required" errmsg="商户名称不能为空!" value="${merchantDto.zhName}" onblur="setAcctName()" />
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >商户简称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="enName" readonly="readonly" name="enName" maxlength= "32" valid="required" errmsg="商户简称不能为空!" value="${merchantDto.enName}">
			</td>
			<td class="border_top_right4" align="right" >商户主网站网址：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="website" name="website" readonly="readonly" maxlength= "64"  valid="required|isURL" errmsg="商户主网站网址不能为空!|商户主网站网址格式不正确" value="${merchantDto.website}">
				
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户类型：</td>
			<td class="border_top_right4" align="left" >
				<select id="enterpriseType" disabled="disabled" name="enterpriseType"  valid="required" errmsg="商户类型不能为空!">
					<option value=""  >---请选择---</option>
					<c:forEach items="${merchantTypeEnum}" var="enterpriseType">
						<option value="${enterpriseType.code}"<c:if test="${enterpriseType.code == merchantDto.enterpriseType}"> selected="selected" </c:if>>${enterpriseType.description}</option>
					</c:forEach>
								
				</select>
			
			</td>
			<td class="border_top_right4" align="right" >商户等级：</td>
			<td class="border_top_right4" align="left" >
				<select id="serviceLevelCode" disabled="disabled"  name="serviceLevelCode"  valid="required" errmsg="商户等级不能为空!">
					<option value="" selected>---请选择---</option>	
					<c:forEach items="${merchantLevelEnum}" var="merchantLevel">
					<option value="${merchantLevel.code}"<c:if test="${merchantLevel.code == merchantDto.serviceLevelCode}"> selected="selected" </c:if>>${merchantLevel.description}</option>
					</c:forEach>	
				</select>
					
			</td>
			<td class="border_top_right4" align="right" >MCC：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="industry" name="industry" readonly="readonly" maxlength= "4" valid="required|isInt" errmsg="MCC不能为空!|MCC格式不正确!" value="${merchantDto.industry}">
				<font color="red">*</font>
			</td>
		</tr>
	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >所属国家：</td>
			<td class="border_top_right4" align="left" >
				
				<select id="nation" name="nation" disabled="disabled"	 valid="required" errmsg="所属地区不能为空!">
					<option value=""  selected>---请选择---</option>
					<c:forEach items="${nationEnum}" var="nation">
						<option value="${nation.code}"<c:if test="${nation.code == merchantDto.nation}"> selected="selected" </c:if>>${nation.description}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >所属地区：</td>
			<td class="border_top_right4" align="left" >
				<select id="region" name="region" disabled="disabled"	onchange="changeProvince('region','city');" valid="required" errmsg="所属地区不能为空!">
				<option value=""  selected>---请选择---</option>
				<c:forEach items="${provinceList}" var="province">
					<option value="${province.provincecode}"<c:if test="${province.provincecode == merchantDto.region}"> selected="selected" </c:if>>${province.provincename}</option>
				</c:forEach>
			</select>
			<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right"  >所属城市：</td>
			<td class="border_top_right4" align="left" >
				<select	id="city" name="city"  valid="required" disabled="disabled" errmsg="所属城市不能为空!">
					<option value="${merchantDto.city}" selected>${merchantDto.cityName}</option>
				</select>
				<font color="red">*</font>
			</td>
			
		</tr>	
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户营业执照号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" readonly="readonly" id="bizLicenceCode" name="bizLicenceCode"  maxlength= "15" valid="required|isInt" errmsg="商户营业执照号码不能为空!|商户营业执照号码只能为数字!"  value="${merchantDto.bizLicenceCode}">
				<input type="hidden"  readonly="readonly" id="bizLicenceCodeOld" name="bizLicenceCodeOld"   value="${merchantDto.bizLicenceCode}">
				<font color="red">*</font><span id="bizLicenceCodeTip"></span>
			</td>
			<td class="border_top_right4" align="right" >商户营业执照有效期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" readonly="readonly" type="text" id= "expire" name="expire"  	
					 valid="required" errmsg="商户营业执照有效期不能为空!" value='${fn:substring(merchantDto.expire, 0, 10)}' >
			</td>
			<td class="border_top_right4" align="right" >商户机构代码证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" readonly="readonly" id="govCode" name="govCode"  maxlength= "10" valid="required" errmsg="商户机构代码证号码不能为空!" value="${merchantDto.govCode}">
				<input type="hidden" readonly="readonly" id="govCodeOld" name="govCodeOld"   value="${merchantDto.govCode}">
				<font color="red">*</font><span id="govCodeTip"></span>
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户税务登记证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="taxCode" name="taxCode" readonly="readonly" maxlength= "20" valid="required" errmsg="商户税务登记证号码不能为空!" value="${merchantDto.taxCode}">
				<input type="hidden" id="taxCodeOld" name="taxCodeOld" readonly="readonly"   value="${merchantDto.taxCode}">
				<font color="red">*</font><span id="taxCodeTip"></span>
			</td>
			<td class="border_top_right4" align="right" >商户风险等级：</td>
			<td class="border_top_right4" align="left">
				<select id="riskLeveCode" name="riskLeveCode" disabled="disabled"  valid="required" errmsg="商户风险等级不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${riskLevelList}" var="riskLevel">
						<option value="${riskLevel.riskLevel}"<c:if test="${riskLevel.riskLevel == merchantDto.riskLeveCode}"> selected="selected" </c:if>>${riskLevel.levelName}</option>
					</c:forEach>
				</select>
 				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >会员号等级：</td>
			<td class="border_top_right4" align="left">
				<select id="merchantType" name="merchantType" size="1" disabled="disabled"  valid="required" errmsg="会员号等级不能为空!">
						<option value="0">---请选择---</option>
						<option value="2" <c:if test='${merchantDto.merchantType == "2"}'> selected="selected" </c:if>>普通会员</option>
						<option value="3" <c:if test='${merchantDto.merchantType == "3"}'> selected="selected" </c:if>>平台会员</option>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td colspan="6">hiii</td>
		</tr>
		
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户联系信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称1：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName1"  readonly="readonly" maxlength="16"  name="webName1" value="${merchantDto.webName1}">
			</td>
			<td class="border_top_right4" align="right" >商户网站网址1：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr1" name="webAddr1"  readonly="readonly" maxlength="64" valid="isURL" errmsg="商户网站网址1格式不正确" value="${merchantDto.webAddr1}">
			</td>
			<td class="border_top_right4" align="right" >传真：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="fax" name="fax" valid="required|isPhone"  readonly="readonly"  maxlength="16"  errmsg="传真不能为空!|传真格式不正确" value="${merchantDto.fax}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称2：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName2" name="webName2" readonly="readonly"  maxlength="16" value="${merchantDto.webName2}">
			</td>
			<td class="border_top_right4" align="right" >商户网站网址2：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr2" name="webAddr2" readonly="readonly"  maxlength="64" valid="isURL" errmsg="商户网站网址2格式不正确" value="${merchantDto.webAddr2}">
			</td>
			<td class="border_top_right4" align="right" >公司电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="tel" name="tel"  readonly="readonly"  maxlength="16" valid="required" errmsg="公司电话不能为空!" value="${merchantDto.tel}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称3：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName3" readonly="readonly"  maxlength="16" name="webName3" value="${merchantDto.webName3}">
			</td>
			<td class="border_top_right4" align="right" >商户网站网址3：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr3" name="webAddr3" readonly="readonly"  maxlength="64"  valid="isURL" errmsg="商户网站网址3格式不正确" value="${merchantDto.webAddr3}">
			</td>
			<td class="border_top_right4" align="right" >公司邮编：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="zip" name="zip"   maxlength="6" readonly="readonly"  valid="required|isPost" errmsg="公司邮编不能为空!|公司邮编格式不正确" value="${merchantDto.zip}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司法人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="legalName" name="legalName" valid="required" readonly="readonly" errmsg="公司法人姓名不能为空!" value="${merchantDto.legalName}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司法人联系电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="legalLink" name="legalLink"   maxlength="16" readonly="readonly"  valid="required|isPhoneAndMobile" errmsg="公司法人联系电话不能为空!|公司法人联系电话格式不正确" value="${merchantDto.legalLink}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司地址：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="address" name="address"   maxlength="32" valid="required"  readonly="readonly" errmsg="公司地址不能为空!" value="${merchantDto.address}">
				
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="compayLinkerName" name="compayLinkerName"  readonly="readonly" valid="required" errmsg="公司联系人姓名不能为空!" value="${merchantDto.compayLinkerName}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="compayLinkerTel" name="compayLinkerTel" readonly="readonly"  maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司联系人电话不能为空!|公司联系人电话格式不正确" value="${merchantDto.compayLinkerTel}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="financeName" name="financeName"  readonly="readonly" valid="required" errmsg="公司财务联系人姓名不能为空!" value="${merchantDto.financeName}">
				<font color="red">*</font>
			</td>
		</tr>
		
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司技术联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="techName" name="techName"  readonly="readonly"  maxlength="8" valid="required" errmsg="公司技术联系人姓名不能为空!" value="${merchantDto.techName}">
			
			</td>
			<td class="border_top_right4" align="right" >公司技术联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="techLink" name="techLink" readonly="readonly"  maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司技术联系电话不能为空!|公司技术联系电话格式不正确" value="${merchantDto.techLink}">
				
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="financeLink" name="financeLink"  readonly="readonly" maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司财务联系电话不能为空!|公司财务联系电话格式不正确" value="${merchantDto.financeLink}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司Email：</td>
			<td class="border_top_right4" align="left" colspan="5" >
				<input type="text" id="email" name="email"    maxlength="32" readonly="readonly" valid="required|isEmail" errmsg="公司Email不能为空!|Email格式不对!" value="${merchantDto.email}" style="color:gray">
				<font color="red">*</font>
				<font color="red">此邮件地址将作为激活商户后发送邮件地址,必须唯一</font>
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户合同信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >合同起始日期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "startDate" readonly="readonly" name="startDate"   value="${fn:substring(merchantDto.startDate, 0, 10)}">
				
			</td>
			<td class="border_top_right4" align="right" >合同结束日期</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "endDate" name="endDate" readonly="readonly"  value="${fn:substring(merchantDto.endDate, 0, 10)}">	
				
			</td>
			<td class="border_top_right4" align="right" >开通费用：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="openFee"   maxlength="11" name="openFee" readonly="readonly" valid="isNumber" errmsg="开通费用格式不对!" value="${merchantDto.openFee}">
				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >实际合同起始日期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "factStartDate" readonly="readonly" name="factStartDate"  
				valid="required" errmsg="实际合同起始日期不能为空!" value="${fn:substring(merchantDto.factStartDate, 0, 10)}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >实际合同结束日期</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "factEndDate" readonly="readonly" name="factEndDate"   
				valid="required" errmsg="实际合同结束日期不能为空!"  value="${fn:substring(merchantDto.factEndDate, 0, 10)}">
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >年服务费：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="yearFee" name="yearFee"  readonly="readonly"  maxlength="11" valid="isNumber" errmsg="年服务费格式不对!" value="${merchantDto.yearFee}">
				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >实际开通费用：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="factOpenFee" name="factOpenFee" readonly="readonly"  maxlength="11" valid="required|isNumber" errmsg="实际开通费用不能为空!|实际开通费用格式不对"  value="${merchantDto.factOpenFee}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >实际年服务费：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="factYearFee" name="factYearFee" readonly="readonly"  maxlength="11"  valid="required|isNumber" errmsg="实际年服务费不能为空!|实际年服务费格式不对" value="${merchantDto.factYearFee}"> 
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >应收保证金：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="assureFee" name="assureFee"  readonly="readonly" maxlength="11"  valid="required|isNumber" errmsg="应收保证金不能为空!|应收保证金格式不对" value="${merchantDto.assureFee}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >保证金说明：</td>
			<td class="border_top_right4" align="left" colspan="5">
				<input type="text" id="assureDesc" name="assureDesc" readonly="readonly"  maxlength="64" valid="required" errmsg="保证金说明不能为空!" value="${merchantDto.assureDesc}">
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">签约人信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="signName" name="signName"  value="${merchantDto.signName}" readonly="readonly" maxlength="8" valid="required" errmsg="签约人姓名不能为空!" >
			</td>
		</tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人帐号 ：</td>
			<td class="border_top_right4" align="left"  colspan="1">
				<select onchange="getterSignName();" disabled="disabled" id="signLoginId" style="width:131px; height:20px;" valid="required" errmsg="签约人帐号不能为空">
					<option value="${merchantDto.signName}">${merchantDto.signLoginId}</option>
	    		</select>
	    	<font color="red">*</font>
	    	<input type="hidden" name="signLoginId">
			</td>
		</tr>

</table>
<br></br>
<table width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr>	
		<td  align="center" style="text-align: center">
			
			<!-- <input id="submitForm" name="submitForm" type="button"  onclick="javascript:history.go(-1);" value="返回" /> -->
			<!--<input type ="button"  onclick="javascript:closePage('enterpriseView.do?memberCode=${merchantDto.memberCode}');" value="关闭">-->
				
		</td>
	</tr>
</table>

</form>
</c:if>
<c:if test="${empty userRelation}">
<form id="merchantFormBean" name="merchantFormBean" action="enterpriseViewEdit.do" method="post" onsubmit="return (validator(this)&&submitPrevProcess())" target="hideIframe">
<input type="hidden" id="memberCode" name="memberCode" value="${merchantDto.memberCode}">
<input type="hidden" id="merchantCode" name="merchantCode" value="${merchantDto.merchantCode}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户基本信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >
				
				<input type="text" id="zhName" name="zhName" maxlength= "32" valid="required" errmsg="商户名称不能为空!" value="${merchantDto.zhName}" onblur="setAcctName()" />
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >商户简称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="enName" name="enName" maxlength= "32" valid="required" errmsg="商户简称不能为空!" value="${merchantDto.enName}">
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
				<input type="hidden" id="bizLicenceCodeOld" name="bizLicenceCodeOld"   value="${merchantDto.bizLicenceCode}">
				<font color="red">*</font><span id="bizLicenceCodeTip"></span>
			</td>
			<td class="border_top_right4" align="right" >商户营业执照有效期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "expire" name="expire"  onClick="WdatePicker()"

				
					 valid="required" errmsg="商户营业执照有效期不能为空!" value='${fn:substring(merchantDto.expire, 0, 10)}' >
			</td>
			<td class="border_top_right4" align="right" >商户机构代码证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="govCode" name="govCode"  maxlength= "10" valid="required" errmsg="商户机构代码证号码不能为空!" value="${merchantDto.govCode}">
				<input type="hidden" id="govCodeOld" name="govCodeOld"   value="${merchantDto.govCode}">
				<font color="red">*</font><span id="govCodeTip"></span>
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户税务登记证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="taxCode" name="taxCode"  maxlength= "20" valid="required" errmsg="商户税务登记证号码不能为空!" value="${merchantDto.taxCode}">
				<input type="hidden" id="taxCodeOld" name="taxCodeOld"   value="${merchantDto.taxCode}">
				<font color="red">*</font><span id="taxCodeTip"></span>
			</td>
			<td class="border_top_right4" align="right" >商户风险等级：</td>
			<td class="border_top_right4" align="left">
				<select	id="riskLeveCode" name="riskLeveCode"  valid="required" errmsg="商户风险等级不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${riskLevelList}" var="riskLevel">
					<option value="${riskLevel.riskLevel}"<c:if test="${riskLevel.riskLevel == merchantDto.riskLeveCode}"> selected="selected" </c:if>>${riskLevel.levelName}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >会员号等级：</td>
			<td class="border_top_right4" align="left">
				<select id="merchantType" name="merchantType" size="1"  disabled="disabled"  valid="required" errmsg="会员号等级不能为空!">
						<option value="0">---请选择---</option>
						<option value="2" <c:if test='${merchantDto.merchantType == "2"}'> selected="selected" </c:if>>普通会员</option>
						<option value="3" <c:if test='${merchantDto.merchantType == "3"}'> selected="selected" </c:if>>平台会员</option>
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
				<input type="text" id="legalName" name="legalName" valid="required" errmsg="公司法人姓名不能为空!" value="${merchantDto.legalName}">
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
				<input type="text" id="compayLinkerName" name="compayLinkerName" valid="required" errmsg="公司联系人姓名不能为空!" value="${merchantDto.compayLinkerName}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="compayLinkerTel" name="compayLinkerTel"   maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司联系人电话不能为空!|公司联系人电话格式不正确" value="${merchantDto.compayLinkerTel}">
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="financeName" name="financeName" valid="required" errmsg="公司财务联系人姓名不能为空!" value="${merchantDto.financeName}">
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
				<input type="text" id="email" name="email"   maxlength="32" readonly valid="required|isEmail" errmsg="公司Email不能为空!|Email格式不对!" value="${merchantDto.email}" style="color:gray">
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
			<td class="border_top_right4" align="right" >年服务费(必须为整数)：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="yearFee" name="yearFee"   maxlength="11" valid="isInt" errmsg="年服务费格式不对,必须为整数!" value="${merchantDto.yearFee}">
				
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
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">签约人信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="signName" name="signName" value="${merchantDto.signName}" readonly="readonly" maxlength="8" valid="required" errmsg="签约人姓名不能为空!" >
			</td>
			<td class="border_top_right4" colspan="4"></td>
		</tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人帐号 ：</td>
			<td class="border_top_right4" align="left"  colspan="1">
				<select onchange="getterSignName();"  id="signLoginId" style="width:131px; height:20px;" valid="required" errmsg="签约人帐号不能为空">
					<option value="${merchantDto.signName}">${merchantDto.signLoginId}</option>
	    		</select>
	    	<font color="red">*</font>
	    	<input type="hidden" name="signLoginId">
			</td>
			<td class="border_top_right4" colspan="4"></td>
		</tr>
		<tr class="trForContent1">
		<td colspan="6" class="border_top_right4" align="center" style="text-align: center">
			
			<input id="submitForm" class="button2" name="submitForm" type="button"  onclick="javascript:submitMerchantForm();" value="保存" />
			<!--<input type ="button"  onclick="javascript:closePage('enterpriseView.do?memberCode=${merchantDto.memberCode}');" value="关闭">-->
				
		</td>
	</tr>

</table>


</form>
</c:if>
<iframe name="hideIframe" style="display:none;"></iframe>
</body>

