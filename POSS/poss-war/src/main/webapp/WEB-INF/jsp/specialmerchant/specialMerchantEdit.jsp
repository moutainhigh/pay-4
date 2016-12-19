<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<!-- jquery validate -->
<script src="${ctx}/js/jquery/plugins/validate/jquery.validate.min.js" type="text/javascript"></script>
<link href="${ctx}/js/jquery/plugins/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery/plugins/validate/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/plugins/validate/jquery.form.js" type="text/javascript"></script>

<script src="./js/common.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script type="text/javascript">

$(function(){
	
	$("input[type=text]").blur(function(){
		$(this).val($.trim(this.value));
	});
	
	$("span[id*=Tip]").attr("style","color:orange");
	$("#sp_merchant_name").focusout(function(){
		var sp_merchant_name = $.trim($("#sp_merchant_name").val());
		if('' != sp_merchant_name){
			var pars = "sp_merchant_name=" + sp_merchant_name + "&sp_merchant_id=" + ${spmerchant.sp_merchant_id}
			$.ajax({
				type: "POST",
				url: "${ctx}/specialMerchantNameValidate.do",
				data: pars,
				success: function(result) {
					if (result == 'false') {
						alert("您输入的商户名称已经存在，请与相关人员确认!");
						$("#submitForm").attr("disabled","true");
						industryBoolean = false;						
					}else{
						industryBoolean = true;
						$("#submitForm").removeAttr("disabled"); 
					} 
				}
			});
		}
	});
	
});

function processRet(){
		location.href = "${ctx}/specialMerchantInit.do";
}
function closePage() {
	var spMerchantId = document.getElementById('sp_merchant_id').value ;
	var url = "${ctx}/specialMerchantEdit.do?sp_merchant_id=" + spMerchantId;
	parent.closePage(url);
}
function changeProvince(father,son){
	var relationArray = new Array();
	<c:forEach items = "${relationList}" var = "relation" varStatus = "relationStatus">
		relationArray[${relationStatus.index}] = new dropDownListMode('${relation.fatherCode}','${relation.code}','${relation.name}');	
	</c:forEach>
	
	this.changeFatherSelect(father,son,relationArray,null);
}	
</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">特约商户信息编辑</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>



<form method="post" id="specialMerchantFrom" name="specialMerchantFrom" action="${ctx}/specialMerchantEditForResult.do" onsubmit="return(validator(this))" enctype="multipart/form-data">
<input type="hidden" name="sp_merchant_id" id="sp_merchant_id" value="${spmerchant.sp_merchant_id}" />
<table class="border_all2" width="600" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>商户名称</td>
		<td class=border_top_right4>
			<input type="text" name="sp_merchant_name" id="sp_merchant_name" valid="required" errmsg="商户名称不能为空!" value="${spmerchant.sp_merchant_name}"/>&nbsp;&nbsp;
			<font color="red">*</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>经营范围</td>
		<td class=border_top_right4>
			<!-- <input type="text" name="range_id" id="range_id" value="${spmerchant.range_id}"/> -->
			<select id="range_id" name="range_id" valid="required" errmsg="请选择经营范围!" >
				<option value="" selected>---请选择---</option>
				<c:forEach items="${rangIdList}" var="rangIdInfo">
					<option value="${rangIdInfo.enumCode}"<c:if test="${rangIdInfo.enumCode == spmerchant.range_id}"> selected="selected" </c:if>>${rangIdInfo.enumName}</option>
				</c:forEach>
			</select>
			<font color="red">*</font>
		</td>
		
	</tr>

	
	<tr class="trForContent1">
		<td class="border_top_right4" nowrap >所属地区：</td>
			<td class="border_top_right4" align="left" >
				<select id="region" name="region" 	onchange="changeProvince('region','city');" valid="required" errmsg="所属地区不能为空!">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${provinceList}" var="province">
					<%-- <option value="${province.provincecode}">${province.provincename}</option> --%>
					<option value="${province.provincecode}"<c:if test="${province.provincecode == cityinfo.provincecode}"> selected="selected" </c:if>>${province.provincename}</option>
				</c:forEach>
			</select>
			<font color="red">*</font>	
			</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" nowrap>所属城市：</td>
			<td class="border_top_right4" align="left" >
				<select	id="city" name="city"  valid="required" errmsg="所属城市不能为空!">
					<option value="" selected>---请选择---</option>
					<option value="${cityinfo.citycode}" selected>${cityinfo.cityname}</option>
				</select>
				<font color="red">*</font>
			</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户介绍：</td>
		<td class=border_top_right4>
			<textarea rows="3" cols="30" id="merchant_intro" name="merchant_intro" >${spmerchant.merchant_intro}</textarea>
		 </td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户地址：</td>
		<td class=border_top_right4>
			<textarea rows="2" cols="30" id="addr" name="addr" valid="required" errmsg="特约商户地址不能为空" >${spmerchant.addr}</textarea>
			<font color="red">*</font>
		 </td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户联系电话：</td>
		<td class=border_top_right4>
			<input type="text" name="tel" id="tel" maxlength="16" valid="required" errmsg="公司联系人电话不能为空!|公司联系人电话格式不正确" value="${spmerchant.tel}"/>&nbsp;&nbsp;
			<font color="red">*</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>网址：</td>
		<td class=border_top_right4>
			<input type="text" name="website_url" id="website_url" size="30" value="${spmerchant.website_url}" valid="isURL" errmsg="网址必须以http:开头"/>&nbsp;&nbsp;
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户LOGO图片：</td>
		<td class=border_top_right4>
			<c:if test="${not empty  spmerchant.sp_merchant_logo}">
				<img id="imgimg1" src="${spmerchant.sp_merchant_logo}" /><br>
			</c:if>
			<input type="file" size="40" id="imgSmall" name="imgSmall" ><font color="red">此图片为小图，尺寸必须大于或等于150*150的正方形图片</font><br>
			<input type="file" size="40" id="imgBig" name="imgBig" ><font color="red">此图为大图，图片宽度建议不超过650px,高度不限制</font>
		</td>
	</tr>
	<c:if test="${not empty info}">
				<li style="color: red;">${info }</li>
			</c:if>
</table>


<br>
<br>

<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td align="center" style="text-align: center">
		 	<input id="submitForm" name="submitForm" type="submit" value="保存" />			
		 	<input type="button" value="关闭" onclick="closePage()" />			
		</td>
	</tr>
</table>
</form>
</body>