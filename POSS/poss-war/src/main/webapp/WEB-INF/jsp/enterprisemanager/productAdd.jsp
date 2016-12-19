<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script type="text/javascript">

function closePage(url){	
	parent.closePage(url);
}

function submitFormAndAgain(){
	if(!validator(document.forms['productFormBean'])){
		return ;
	}
	$("#addAgain").val("1");
	$("#productFormBean").submit();
}	
function submitForm(){
	if(!validator(document.forms['productFormBean'])){
		return ;
	}
	$("#addAgain").val("0");
	$.post($("#productFormBean").attr("action"),$("#productFormBean").serialize(),function backfun(msg){
			alert(msg);
	});
	return false;
}


$(function(){
	
<c:choose>
 	<c:when test="${product!=null}">
	$("#id").val("${product.id}");
	$("#name").val("${product.name}");
	$("#description").val("${product.description}");
	$("#allowObject").val("${product.allowObject}");
	$("#immediacyPass").val("${product.immediacyPass}");
	$("#type").val("${product.type}");
	$("#productCode").val("${product.productCode}");
	$("#isDefault").val("${product.isDefault}");
	$("#activationMode").val("${product.activationMode}");
	$("#productType").val("<c:out value="${product.productType}" />");
	$("#productOrder").val("${product.productOrder==0?10:product.productOrder}");
</c:when>
	<c:otherwise>
	$("#allowObject").val("2");
	$("#immediacyPass").val("1");
	$("#type").val(2);
	$("#isDefault").val("0");
	$("#activationMode").val("1");
	$("#productOrder").val("10");
	</c:otherwise>
</c:choose>
		
		
	
});
</script>
</head>

<body>


<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">产 品 新 增</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">产品新增</h2>
<c:if test="${sign!=null}">
	<center>
		<font color="red">${sign}</font>
	</center>
</c:if>

<form id="productFormBean" name="productFormBean" action="productAdd.do" method="post"   >
	<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >产品名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="name" name="name" maxlength= "30" valid="required" errmsg="产品名称不能为空!"/>
				<font color="red">*</font>
			</td>
			</tr>
			
			<tr class="trForContent1">
			
			<td class="border_top_right4" align="right" >产品编号：</td>
			<td class="border_top_right4" align="left"  >
				<input  type="text" id= "productCode" name="productCode" maxlength="25" valid="required" errmsg="产品编号不能为空!" />
				<font color="red">*</font>
			</td>
			</tr><tr class="trForContent1">
			<td class="border_top_right4" align="right" >产品描述：</td>
			<td class="border_top_right4" align="left" >
				<input  type="text" id= "description" name="description" maxlength= "60">
			</td>
					
		</tr>		
			
			<tr class="trForContent1">
			<td class="border_top_right4" align="right" >产品激活方式：</td>
			<td class="border_top_right4" align="left"  >
				<select	id="activationMode" name="activationMode"  valid="required" errmsg="产品激活方式不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${activationModeEnum}" var="activationMode">
					<option value="${activationMode.code}">${activationMode.description}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
		</tr>				
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >产品适用对象：</td>
			<td class="border_top_right4" align="left" >
				<select id="allowObject" name="allowObject" valid="required" errmsg="适用对象不能为空!">	
					<option value="" selected>---请选择---</option>		
					<c:forEach items="${allowObjectEnum}" var="allowObject">
					<option value="${allowObject.code}">${allowObject.description}</option>
					</c:forEach>	
				</select>
				<font color="red">*</font>
			</td>
			</tr><tr class="trForContent1">
			<td class="border_top_right4" align="right" >产品状态：</td>
			<td class="border_top_right4" align="left" >
				<select id="immediacyPass" name="immediacyPass"  valid="required" errmsg="产品状态不能为空!">	
					<option value="" selected>---请选择---</option>		
					<c:forEach items="${productStatusEnum}" var="productStatus">
					<option value="${productStatus.code}">${productStatus.description}</option>
					</c:forEach>	
				</select>
				<font color="red">*</font>
				
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >产品类别：</td>
			<td class="border_top_right4" align="left" >
				<select	id="productType" name="productType"  valid="required" errmsg="产品类别不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${productTypeOrderEnum}" var="productType">
					<option value="${productType.code}">${productType.desc}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
				
				
			</td>
			</tr>
			
			<tr class="trForContent1">
			<td class="border_top_right4" align="right" >是否默认产品：</td>
			<td class="border_top_right4" align="left" >
				<select id="isDefault" name="isDefault"  valid="required" errmsg="是否默认产品不能为空!">	
					<option value="" selected>---请选择---</option>		
					<option value="1">是</option>
					<option value="0">否</option>		
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >在类别内排序：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="productOrder" name="productOrder" maxlength="3" size="5" />	
				<font color="red">*</font> 默认为10，值越小排序越前，请填10的倍数
			</td>
		</tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="center" colspan="2" >
			<input class="button2" id="submitButton" name="submitButton" type="button" value="保存" onclick="javascript:submitForm();">	
			<input class="button2" id="submitButton" name="submitButton" type="button" value="保存并继续新增" onclick="javascript:submitFormAndAgain();">	
			<input class="button2" id="closeButton" name="closeButton" type="button" value="关闭" onclick="javascript:closePage('productAdd.do');">			
			</td>
		</tr>
		
														
</table>
	<input id="addAgain" name="addAgain" type="hidden" >
	<input id="id" name="id" type="hidden" >
</form>


</body>

