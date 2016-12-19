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
	if(document.appealBaseDataRelationFormBean.onsubmit()){
		var fatherDataCode = $.trim($("#fatherDataCode").val());
		var sonDataCode = $.trim($("#sonDataCode").val());
		if('' != fatherDataCode&&'' != sonDataCode){
			var pars = "fatherDataCode=" + fatherDataCode + "&sonDataCode=" + sonDataCode;
			$.ajax({
				type: "POST",
				url: "${ctx}/appealBaseDataRelationAddValidate.do",
				data: pars,
				success: function(result) {
					if (result == 'false') {
						alert("该数据关系已存在,不能重复录入 !");
						
									
					}else{						
						
						document.appealBaseDataRelationFormBean.submit();
						
					} 
				}
			});
		}
	
	}
}

</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">基 础 数 据 关 系 新 增</font></div>
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

<form id="appealBaseDataRelationFormBean" name="appealBaseDataRelationFormBean" action="appealBaseDataRelationAdd.do" method="post"   onsubmit="return validator(this)" >

<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >父类数据：</td>
			<td class="border_top_right4" align="left" >
				<select	id="fatherDataCode" name="fatherDataCode" valid="required" errmsg="父类数据不能为空!">
						<option value="" selected>---请选择---</option>
						<c:forEach items="${baseDataList}" var="baseData">
						<option value="${baseData.code}">${baseData.name}</option>
						</c:forEach>
				</select>
				
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >子类数据：</td>
			<td class="border_top_right4" align="left" >
				<select	id="sonDataCode" name="sonDataCode" valid="required" errmsg="子类数据不能为空!">
						<option value="" selected>---请选择---</option>
						<c:forEach items="${baseDataList}" var="baseData">
						<option value="${baseData.code}">${baseData.name}</option>
						</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >数据关系类型：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="relationType" name="relationType" maxlength= "2" valid="required|isEnglish" errmsg="数据类型不能为空!|数据类型只能为英文字母"/>
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
		 <input id="closeButton" name="closeButton" type="button" value="关闭" onclick="javascript:closePage('appealBaseDataRelationAdd.do');">			
		</td>
	</tr>
</table>

</form>


</body>

