<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<title>添加申请记账</title>
</head>
<script type="text/javascript">
	function submitInfo() {
		var pars = $("#userSearchForm").serialize() ;
		$.ajax({
			type: "POST",
			url: "${ctx}/manualbooking/vouchDatailInit.do?method=addInfoMessage",
			data: pars,
			success: function(result) {
			}
		});
	}
</script>
<body>
	<div>
    <div><table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">添加申请记账</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
	</div>
	<form id="userSearchForm">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">			
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="25%">账号：</td>
			<td class="border_top_right4" align="left" width="25%">
				<input type="text" name="vouchCode" style="width: 130px;" />
			</td>
			<td class="border_top_right4" align="right" width="25%">账号名称：</td>
			<td class="border_top_right4" align="left" width="25%">
				<input type="text" name="accountName" style="width: 130px;" />			
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="25%">借贷：</td>
			<td class="border_top_right4" align="left" width="25%">
				 <input type="radio" value="1"  name="crdr" checked="checked"/>借<input type="radio" value="2" name="crdr" />贷
			</td>
			<td class="border_top_right4" align="right" width="25%">金额：</td>
			<td class="border_top_right4" align="left" width="25%">			
				<input type="text" name="amount" style="width: 130px;" />元(人民币)									
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="25%">摘要：</td>
			<td class="border_top_right4" align="left" width="25%">
				<textarea name="remark" rows="3" cols="24"></textarea>
			</td>
			<td class="border_top_right4" align="right" width="25%">联系电话：</td>
			<td class="border_top_right4" align="left" width="25%">			
				<input type="text" id="phone" name="phone">									
			</td>
		</tr>
				
		<tr class="trForContent1">	
			<td class="border_top_right4" align="center" colspan="4">					
			  <input type="button" value="确  定" onclick="submitInfo()"/>
			  <input type="button" value="返  回" onclick="javascript:history.back()"/>
			</td>
		</tr>
</table>
</form>
	</div>
</body>
</html>