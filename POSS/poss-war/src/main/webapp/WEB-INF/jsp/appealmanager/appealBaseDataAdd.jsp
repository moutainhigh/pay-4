<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script language="javascript">

function closePage(url){	
	parent.closePage(url);
}
function submitForm(){
	if(document.appealBeaseDataFormBean.onsubmit()){
		document.appealBeaseDataFormBean.submit();
	
	}
}
$(function(){
$("#code").focusout(function(){	
	var baseDataCode = $.trim($("#code").val());
	
	if('' != baseDataCode){
		var pars = "baseDataCode=" + baseDataCode;
		$.ajax({
			type: "POST",
			url: "${ctx}/appealBaseDataAddValidate.do",
			data: pars,
			success: function(result) {
				if (result == 'false') {
					alert("您输入数据标识已存在,不能重复录入 !");
					$("#submitButton").attr("disabled","true");	
								
				}else{						
					
					$("#submitButton").removeAttr("disabled"); 
				
					
				} 
			}
		});
	}
});
});
</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">基 础 数 据 新 增</font></div>
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

<form id="appealBeaseDataFormBean" name="appealBeaseDataFormBean" action="appealBaseDataAdd.do" method="post"   onsubmit="return validator(this)" >

<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >数据标识：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="code" name="code" maxlength= "3" valid="required|isInt" errmsg="数据标识不能为空!|数据标识只能为数字"/>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >数据名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="name" name="name" maxlength= "16" valid="required" errmsg="数据名称不能为空!"/>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >数据类型：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="type" name="type" maxlength= "2" valid="required|isEnglish" errmsg="数据类型不能为空!|数据类型只能为英文字母"/>
				<font color="red">*</font>
			</td>
		</tr>				
		
		
		
</table>
<br></br>
<table>
	<tr>	
		<td  align="center">	
			<input id="submitButton" name="submitButton" type="button" value="保存" onclick="javascript:submitForm();">			
		</td>
		<td  align="center">	
		 <input id="closeButton" name="closeButton" type="button" value="关闭" onclick="javascript:closePage('appealBaseDataAdd.do');">			
		</td>
	</tr>
</table>

</form>


</body>

