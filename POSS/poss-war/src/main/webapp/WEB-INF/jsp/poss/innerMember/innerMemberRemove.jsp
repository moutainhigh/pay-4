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

	<form action="${ctx}/report/innerMember.do?method=delete&id=${memberToDelete.sequenceId}" method="post" id="form1" name="form1">
		<table width="60%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr> 
				<td height="18">
					<div align="left">
						<font color="red">确认删除以下分子公司及其商户的信息吗？</font>
					</div>
				</td>
			</tr>
		</table>
	   
	  <table class="border_all2" width="60%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<th align="center" class="border_top_right4" width="65%">分子公司名称</th>
	      	<th align="center" class=border_top_right4>分子公司会员号</th>
		</tr>
		<tr>
	      	<td class="border_top_right4" align="left">
	      		${memberToDelete.memberName}
	      	</td>
	      	<td class="border_top_right4" align="left">
	      		${memberToDelete.memberCode}
	      	</td>
	    </tr>
	  </table>
	  <br>
	  <br>
	  <input type="submit" name="submitBtn" value="确认删除" class="button2">&nbsp;&nbsp;&nbsp;
	  <input type="button" onclick="javascript:processBack()" name="Submit2" value="返 回" class="button2">
	</form>
</body>
</html>
