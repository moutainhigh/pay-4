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


<script type='text/javascript' src='./js/jquery/plugins/autocomplate/thickbox-compressed.js'></script>
<script type='text/javascript' src='./js/jquery/plugins/autocomplate/jquery.autocomplete.min.js'></script>
<link rel="stylesheet" type="text/css" href="./js/jquery/plugins/autocomplate/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="./js/jquery/plugins/autocomplate/thickbox.css" />
<script src="./js/common.js"></script>
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
	var emailBoolean = true;
	var signDepartBoolean = true;
	$("span[id*=Tip]").attr("style","color:orange");
	$("#industry").focusout(function(){
		var industry = $.trim($("#industry").val());
		if('' != industry){
			var pars = "industry=" + industry;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantAddValidate.do?method=industry",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						alert("您输入MCC值不存在,请与管理员联系!");
						$("#submitForm").attr("disabled","true");
						industryBoolean = false;						
					}else{
						industryBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean){
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
		if('' != govCode){
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
		if('' != taxCode){
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
	
	$("#email").focusout(function(){
		var email = $.trim($("#email").val());
		if('' != email){
			var pars = "email=" + email;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantAddValidate.do?method=email",
				data: pars,
				success: function(result) {
					if (result == 'true') {
						alert("公司email已经存在!");
						$("#submitForm").attr("disabled","true");
						emailBoolean = false;
					}else{
						emailBoolean = true;
						if(industryBoolean&&bizLicenceCodeBoolean&&govCodeBoolean&&taxCodeBoolean&&emailBoolean){
							$("#submitForm").removeAttr("disabled"); 
						}
					} 
				}
			});
		}
	});

});

function getSignName(){
  	var signName=$("#signLoginId").val();	
	$("#signName").attr("value",signName);
}


function changeProvince(father,son){
	var relationArray = new Array();
	<c:forEach items = "${relationList}" var = "relation" varStatus = "relationStatus">
		relationArray[${relationStatus.index}] = new dropDownListMode('${relation.fatherCode}','${relation.code}','${relation.name}');	
	</c:forEach>
	
	this.changeFatherSelect(father,son,relationArray,null);
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

var bigBankIds = [];
var bigBankNames = [];
var clickCount = 0;
$().ready(function() {
	<c:forEach items="${bankList}" var="bank" varStatus = "status">
		bigBankIds[${status.index}] = '${bank.bankId}';
		bigBankNames[${status.index}] = '${bank.bankName}';
	</c:forEach>
	$("#bigBankName").autocomplete(bigBankNames, {
		selectFirst:true,
		matchContains:true,
		minChars:0,
		max:99,
		mustMatch:true
		});

	
	});

function getBankIdByBankName(bankName){
	for(i=0;i<bigBankNames.length;i++){
		if(bankName==bigBankNames[i]){
			return bigBankIds[i];
		}
	}
}			
function submitF(){
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
		<div align="center"><font class="titletext">商 户 新 增</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户新增</h2>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<form id="merchantFormBean" name="merchantFormBean" action="merchantAdd.do" method="post"   onsubmit="return validator(this)" target="hideIframe">

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center" style="margin:0 auto;">	
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户基本信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="zhName" name="zhName" maxlength= "32" valid="required" errmsg="商户名称不能为空!" onblur="setAcctName()" />
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >商户简称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="enName" name="enName" maxlength= "32" valid="required" errmsg="商户简称不能为空!" />
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >商户主网站网址：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="website" name="website" maxlength= "64"  value="http://www.pay.com"  valid="required|isURL" errmsg="商户主网站网址不能为空!|商户主网站网址格式不正确" >
				
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户类型：</td>
			<td class="border_top_right4" align="left" >
				<select id="enterpriseType" name="enterpriseType"  valid="required" errmsg="商户类型不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${merchantTypeEnum}" var="merchantType">
						<option value="${merchantType.code}">${merchantType.description}</option>
					</c:forEach>			
				</select>
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >商户等级：</td>
			<td class="border_top_right4" align="left" >
				<select id="serviceLevelCode" name="serviceLevelCode"  valid="required" errmsg="商户等级不能为空!">
					<option value="" selected>---请选择---</option>	
					<c:forEach items="${merchantLevelEnum}" var="merchantLevel">
					
					<option value="${merchantLevel.code}"<c:if test="${merchantLevel.code==1}">selected  </c:if>>${merchantLevel.description}</option>
					</c:forEach>	
				</select>
			</td>
			<td class="border_top_right4" align="right" >MCC：</td>
			<td class="border_top_right4" align="left" >
				<input type="text"  name="industry"  maxlength= "4"  ><!-- id="industry"valid="required|isInt" errmsg="MCC不能为空!|MCC格式不正确!" -->
				<font color="red">*</font>
			</td>
		</tr>
	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >所属国家：</td>
			<td class="border_top_right4" align="left" >
				
				<select id="nation" name="nation" 	 valid="required" errmsg="所属国家不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${nationEnum}" var="nation">
					<option value="${nation.code}">${nation.description}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >所属地区：</td>
			<td class="border_top_right4" align="left" >
				<select id="region" name="region" 	onchange="changeProvince('region','city');" valid="required" errmsg="所属地区不能为空!">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${provinceList}" var="province">
					<option value="${province.provincecode}">${province.provincename}</option>
				</c:forEach>
			</select>
			<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >所属城市：</td>
			<td class="border_top_right4" align="left" >
				<select	id="city" name="city"  valid="required" errmsg="所属城市不能为空!">
					<option value="" selected>---请选择---</option>
				</select>
				<font color="red">*</font>
			</td>
			
		</tr>	
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户营业执照号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="bizLicenceCode" name="bizLicenceCode"  maxlength= "15" valid="required|isInt" errmsg="商户营业执照号码不能为空!|商户营业执照号码只能为数字!" >
				<font color="red">*</font><span id="bizLicenceCodeTip"></span>
			</td>
			<td class="border_top_right4" align="right" >商户营业执照有效期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "expire" name="expire"  onClick="WdatePicker()"
					 valid="required" errmsg="商户营业执照有效期不能为空!" >
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >商户机构代码证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="govCode" name="govCode"  maxlength= "10" valid="required" errmsg="商户机构代码证号码不能为空!" >
				<font color="red">*</font><span id="govCodeTip"></span>
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户税务登记证号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="taxCode" name="taxCode"  maxlength= "20" valid="required" errmsg="商户税务登记证号码不能为空!" >
				<font color="red">*</font><span id="taxCodeTip"></span>
			</td>
			<td class="border_top_right4" align="right" >商户风险等级：</td>
			<td class="border_top_right4" align="left">
				<select	id="riskLeveCode" name="riskLeveCode"  valid="required" errmsg="商户风险等级不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${riskLevelList}" var="riskLevel">
					<option value="${riskLevel.riskLevel}">${riskLevel.levelName}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >商户类别：</td>
			<td class="border_top_right4" align="left">
				<select	id="merchantCodePrefix" name="merchantCodePrefix"  valid="required" errmsg="商户类别不能为空!">
					<option value="" selected>---请选择---</option>
					<option value="500" selected>iPayLinks</option>
					<option value="800" selected>GCPayment</option>
				</select>
				<font color="red">*</font>
			</td>
			</tr>
			<tr class="trForContent1">
			<td class="border_top_right4" align="right" >是否垫资：</td>
			<td class="border_top_right4" align="left">
				<select id="allowAdvanceMoney" name="allowAdvanceMoney" size="1" valid="required" errmsg="是否垫资不能为空!">
						<option value="0" selected>否</option>
						<option value="1" >是</option>				
				</select>
				<font color="red">*</font>
			</td>
			
			<td class="border_top_right4" align="right" >会员号等级：</td>
			<td class="border_top_right4" align="left" colspan="3">
				<select id="merchantType" name="merchantType" size="1" valid="required" errmsg="会员号等级不能为空!">
						<option value="0">---请选择---</option>
						<option value="2">普通会员</option>
						<option value="3">平台会员</option>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
				
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户联系信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称1：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName1"  maxlength="16"  name="webName1" >
			</td>
			<td class="border_top_right4" align="right" >商户网站网址1：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr1" name="webAddr1"   maxlength="64" valid="isURL" errmsg="商户网站网址1格式不正确" >	
			</td>
			<td class="border_top_right4" align="right" >传真：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="fax" name="fax" valid="required|isPhone"   maxlength="16"  errmsg="传真不能为空!|传真格式不正确" >
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称2：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName2" name="webName2"   maxlength="16" >
			</td>
			<td class="border_top_right4" align="right" >商户网站网址2：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr2" name="webAddr2"   maxlength="64" valid="isURL" errmsg="商户网站网址2格式不正确" >	
			</td>
			<td class="border_top_right4" align="right" >公司电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="tel" name="tel"   maxlength="16" valid="required" errmsg="公司电话不能为空!" >
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户网站名称3：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webName3"   maxlength="16" name="webName3" >
			</td>
			<td class="border_top_right4" align="right" >商户网站网址3：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="webAddr3" name="webAddr3"  maxlength="64"  valid="isURL" errmsg="商户网站网址3格式不正确" >	
			</td>
			<td class="border_top_right4" align="right" >公司邮编：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="zip" name="zip"   maxlength="6" value="000000" valid="required|isPost" errmsg="公司邮编不能为空!|公司邮编格式不正确" >
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司法人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="legalName" name="legalName" valid="required" errmsg="公司法人姓名不能为空!" >	
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司法人联系电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="legalLink" name="legalLink"   maxlength="16"  valid="required|isPhoneAndMobile" errmsg="公司法人联系电话不能为空!|公司法人联系电话格式不正确" >
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司地址：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="address" name="address"   maxlength="32" valid="required" errmsg="公司地址不能为空!" >
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="compayLinkerName" name="compayLinkerName" valid="required" errmsg="公司联系人姓名不能为空!" >	
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="compayLinkerTel" name="compayLinkerTel"   maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司联系人电话不能为空!|公司联系人电话格式不正确" >
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="financeName" name="financeName" valid="required" errmsg="公司财务联系人姓名不能为空!" >	
				<font color="red">*</font>
			</td>
		</tr>
		
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司技术联系人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="techName" name="techName" value="默认姓名" valid="required" errmsg="公司技术联系人姓名不能为空!" >
				
			</td>
			<td class="border_top_right4" align="right" >公司技术联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="techLink" name="techLink"   maxlength="16" value="87654321" valid="required|isPhoneAndMobile" errmsg="公司技术联系电话不能为空!|公司技术联系电话格式不正确" >
				
			</td>
			<td class="border_top_right4" align="right" >公司财务联系人电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="financeLink" name="financeLink"   maxlength="16" valid="required|isPhoneAndMobile" errmsg="公司财务联系电话不能为空!|公司财务联系电话格式不正确" >
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" align="right" >公司Email：</td>
			<td class="border_top_right4" align="left" colspan="5" >
				<input type="text" id="email" name="email"   maxlength="32" valid="required|isEmail" errmsg="公司Email不能为空!|Email格式不对!" >
				<font color="red">*</font>
				<font color="red">此邮件地址将作为激活商户后发送邮件地址,必须唯一</font>
			</td>
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">商户合同信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >合同起始日期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()">
			</td>
			<td class="border_top_right4" align="right" >合同结束日期</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">	
			</td>
			<td class="border_top_right4" align="right" >开通费用：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="openFee"   maxlength="11" name="openFee" valid="isNumber" errmsg="开通费用格式不对!">
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >实际合同起始日期：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "factStartDate" name="factStartDate"  onClick="WdatePicker()" 
				valid="required" errmsg="实际合同起始日期不能为空!" >
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >实际合同结束日期</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "factEndDate" name="factEndDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'factStartDate\')}'})" 
				valid="required" errmsg="实际合同结束日期不能为空!" >
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >年服务费(必须为整数)：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="yearFee" name="yearFee"   maxlength="11" valid="isInt" errmsg="年服务费格式不对，必须为整数!">
				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >实际开通费用：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="factOpenFee" name="factOpenFee"   maxlength="11" valid="required|isNumber" errmsg="实际开通费用不能为空!|实际开通费用格式不对" >
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >实际年服务费：</td>
			<td class="border_top_right4" align="left" > 
				<input type="text" id="factYearFee" name="factYearFee"  maxlength="11"  valid="required|isNumber" errmsg="实际年服务费不能为空!|实际年服务费格式不对" > 
				<font color="red">*</font>	
			</td>
			<td class="border_top_right4" align="right" >应收保证金：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="assureFee" name="assureFee"  maxlength="11"  valid="required|isNumber" errmsg="应收保证金不能为空!|应收保证金格式不对" >
				<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >保证金说明：</td>
			<td class="border_top_right4" align="left" colspan="5">
				<input type="text" id="assureDesc" name="assureDesc"   maxlength="64" valid="required" errmsg="保证金说明不能为空!" >
				<font color="red">*</font>
			</td>
		</tr>
		
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="6">签约人信息：</td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="signName" name="signName"  readonly="readonly" maxlength="8" valid="required" errmsg="签约人姓名不能为空!" >
			</td>
			<td colspan="4" class="border_top_right4"></td>
		</tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >签约人帐号 ：</td>
			<td class="border_top_right4" align="left"  colspan="1">
				<select  onchange="getSignName();"  id="signLoginId" style="width:131px; height:20px;" valid="required" errmsg="签约人帐号不能为空">
					<option value="">---请选择---</option>
	    		</select>
	    	<font color="red">*</font>
	    	<input type="hidden" name="signLoginId">
			</td>
			<td colspan="4" class="border_top_right4"></td>
		</tr>
		<tr class="trForContent1">	
		<td colspan="6"  class="border_top_right4" align="center">
		 <input id="submitForm" class="button2" name="submitForm" type="button"  onclick="javascript:submitF();" value="保存" >			
		 <input type="button" class="button2" value="重置" onclick="javascript:document.merchantFormBean.reset()">			
		</td>
	</tr>
		
		
</table>

<input type="hidden" id="bankName" name="bankName"  />
<input type="hidden" id="bankId" name="bankId"  />

</form>
<iframe name="hideIframe" style="display:none;" ></iframe>

</body>

