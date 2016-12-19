<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
$(document).ready(function() {
	
});

</script>
<body>
	<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">水电煤，通讯账单销账处理提示</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
		
	<table class="inputTable" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
    	<tr >
      		
      		<td colspan="2" style="text-align: center">
        		错误信息
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2" >
			    <span style="color: red;font-size: 15px">${errorMsg }</span>	<a href="${ctx}/app/sdmtxOrder.do">返回</a>
      		</td>
    	</tr>
    
  </table>
		

</body>
</html>
