<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<title>提现退款维护</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			function withdrawbackFind(id) {
				var sd = document.getElementById("mcc").value;
				location.href="riskconctrol.mccLimitation.do?method=findById&mcc=" + sd;
			}
		</script>
	</head>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">提现退款维护</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
</table>
<form id="withdrawbackForm" method="post"  action="fundout.withdraw.refund.order.do?method=search">
		<table class="searchTermTable">
			<tr>
				<td class="textRight"> 交易流水号：</td>
			   	<td class="textLeft"><input type="text" id ="sequenceId"   name="sequenceId" style="width: 150px;" /></td>
	   			<td class="textRight"> 交易时间：</td>
			   	<td class="textLeft"><input type="text" id ="createTime"   name="createTime" style="width: 150px;" /></td>
		   </tr>
		   <tr>
	   			<td class="textRight"> 银行账户：</td>
			   	<td class="textLeft"><input type="text" id ="bankAcct"   name="bankAcct" style="width: 150px;" /></td>	
	   			<td class="textRight"> 状态：</td>
			   	<td class="textLeft">
			   		<select name="status">
			   			<option value="1">处理中</option>
			   			<option value="2">处理成功</option>
			   			<option value="3">处理失败</option>
			   		</select>
                 </td>
		   	</tr>
		   	<tr>
			<td class="submit" align="center"   >
			&nbsp;查&nbsp;&nbsp;询
				</td>
			</tr>
		</table>
</form>
        <div id="resultListDiv" class="listFence"></div>
		<div id="deleteRoleDiv" title="删除用户信息"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>