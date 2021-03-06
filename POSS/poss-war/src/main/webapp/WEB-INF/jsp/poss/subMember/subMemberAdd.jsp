<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>商户信息维护</title>
<script type="text/javascript">
	function processBack(){
		location.href = "subMember.do?id=${innerMemberAddTo.sequenceId}";
	}
	//页面validate
	$(document).ready(function(){
		// 不能包含全角字符
		jQuery.validator.addMethod("hasChn", function(value, element) {
		  return  (escape(value).indexOf("%u") < 0);    
		}, "不能包含中文字符"); 
		
		//聚焦第一个输入框
		$("#subMemberName").focus();
		$("#form1").validate({
			rules: { 
				subMemberName:{
					required:true,
					rangelength:[1,256],
					hasChn:false
		    	},
		    	subMemberCode:{
					required:true,
					rangelength:[11,11],
					number:true
				}
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
					<div align="center">
						<font class="titletext">商户信息维护</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>

	<form action="${ctx}/report/subMember.do?method=add" method="post" id="form1" name="form1">
		<input type="hidden" name="id" value="${innerMemberAddTo.sequenceId}"/>
	  <table class="border_all2" width="40%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td align="right" class="border_top_right4">分子公司名称：</td>
	      	<td class="border_top_right4" width="65%">
		    	<input type="text" name="innerMemberName" maxlength="128" size=30 readonly=true style="color: gray;background-color: #ccc" value="${innerMemberAddTo.memberName}">
		    </td>
		</tr>
	    <tr class="trForContent1">
	    	<td align="right" class="border_top_right4">分子公司会员号：</td>
	      	<td class="border_top_right4" width="65%">
		    	<input type="text" name="innerMemberCode" maxlength="60" size=30 readonly=true style="color: gray;background-color: #ccc" value="${innerMemberAddTo.memberCode}">
		    </td>
		</tr>
	    <tr class="trForContent1">
	    	<td align="right" class="border_top_right4">商户名称：</td>
	      	<td class="border_top_right4" width="65%">
		    	<input type="text" name="subMemberName" maxlength="60" size=30 value="${dto.memberName}">
		    </td>
		</tr>
		<tr>
	      	<td class=border_top_right4 align="right" >商户会员号：</td>
	      	<td class="border_top_right4">
	        	<input type="text" name="subMemberCode" maxlength="11" size=30 value="${dto.memberCode}"> 
	      	</td>
	    </tr>
	  </table>
	 <c:if test="${not empty message}">
	 	<div>
			<li style="color: red;">${message}</li>
		</div>
	 </c:if>
	  <br>
	  <br>
	  <input type="submit" name="submitBtn" value="保 存" class="button2">
	  <input type="button" onclick="javascript:processBack()" name="Submit2" value="返 回" class="button2">
	</form>
</body>
</html>
