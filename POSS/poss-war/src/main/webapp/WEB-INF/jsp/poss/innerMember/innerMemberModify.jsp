<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>分子公司信息维护</title>
<script type="text/javascript">
	function processBack(){
		location.href = "innerMember.do";
	}
	
	//页面validate
	$(document).ready(function(){
		// 不能包含全角字符
		jQuery.validator.addMethod("hasChn", function(value, element) {
		  return  (escape(value).indexOf("%u") < 0);    
		}, "不能包含中文字符"); 
		
		//聚焦第一个输入框
		$("#memberName").focus();
		$("#form1").validate({
			rules: { 
				memberName:{
					required:true,
					rangelength:[1,256],
					hasChn:false
		    	},
		    	memberCode:{
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
						<font class="titletext">分子公司信息维护</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>

	<form action="${ctx}/report/innerMember.do?method=update" method="post" id="form1" name="form1">
	  	<input type="hidden" name="id" value="${memberToUpdate.sequenceId}"/>
	  <table class="border_all2" width="40%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td align="right" class="border_top_right4">分子公司名称：</td>
	      	<td class="border_top_right4" width="65%">
	      	
		    	<input type="text" name="memberName" maxlength="128" size=30 value="${memberToUpdate.memberName}">
		    </td>
		</tr>
		<tr>
	      	<td class=border_top_right4 align="right" >分子公司会员号：</td>
	      	<td class="border_top_right4">
	        	<input type="text" name="memberCode" maxlength="11" size=30 value="${memberToUpdate.memberCode}" readonly=true style="color: gray;background-color: #ccc"> 
	      	</td>
	    </tr>
	  </table>
	  <br>
	  <br>
	  <input type="submit" name="submitBtn" value="确认修改" class="button2">&nbsp;&nbsp;&nbsp;
	  <input type="button" onclick="javascript:processBack()" name="Submit2" value="返 回" class="button2">
	</form>
</body>
</html>
