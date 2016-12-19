<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">拒付审核</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/chargeBackAudit.do?method=audit" method="post" name="addFrom" id="addFrom">
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">网关交易号：</td>	
		<td class="border_top_right4" align="left" style="width: 100px;">
			${tradeOrderNo }
		</td>
		<td align="right" class="border_top_right4" style="width: 80px;">拒付金额：</td>
		<td class="border_top_right4" align="left" style="width: 100px;">
			${chargeBackAmount/1000}
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" style="width: 80px;">渠道原档号：</td>	
		<td class="border_top_right4" align="left">
			${oldRefNo }
		</td>
		<td class="border_top_right4" align="right">
			拒付日期：
		</td>
		<td class="border_top_right4" align="left">
			${cpdDate}
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" style="width: 80px;">渠道订单号：</td>	
		<td class="border_top_right4" align="left">
			${channelOrderId }
		</td>
		<td class="border_top_right4" align="right">
			交易金额：
		</td>
		<td class="border_top_right4" align="left">
			${tradeAmount/1000}
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" style="width: 80px;">类别：</td>	
		<td class="border_top_right4" align="left" colspan="3">
			<select name="cpType" id="cpType">
		      		<option value="1" <c:if test="${cpType == 1 }">selected </c:if>>内部调查单</option>
		      		<option value="2" <c:if test="${cpType == 2 }">selected </c:if>>银行调查单</option>
		      		<option value="3" <c:if test="${cpType == 3 }">selected </c:if>>拒付单</option>
	      	</select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">拒付申请原因：</td>	
		<td class="border_top_right4" align="left" colspan="3">
			${chargeBackMsg }
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">复核备注：</td>	
		<td class="border_top_right4" align="left" colspan="3">
			<textarea rows="3" cols="40" name="auditMsg" id="auditMsg"></textarea>
		</td>
	</tr>
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="4" align="center">
			<input type="button" name="butSubmit" value="审核通过" class="button2" onclick="audit('1');">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="butSubmit" value="审核拒绝" class="button2" onclick="audit('2');">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="butSubmit" value="返回" class="button2" onclick="goback();">
		</td>
	</tr>
	
</table>
</form>

<script>

function goback(){
	window.location.href="${ctx}/chargeBackQuery.do";
}

function audit(flag) {
	
	var cpType = $('#cpType').val();
	var auditMsg = $('#auditMsg').val();
	var oldCpType = ${cpType};
	if(oldCpType == 3 && cpType != 3){
		alert('拒付订单不能转换为其它类型');
		return;
	}
	
	window.location.href='${ctx}/chargeBackAudit.do?method=audit&orderId=${orderId}&cpType=' + cpType + "&status=" + flag + "&auditMsg=" + auditMsg;
	
}
</script>