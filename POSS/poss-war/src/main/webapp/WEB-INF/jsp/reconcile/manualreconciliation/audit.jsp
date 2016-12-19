<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		//审核通过
		function processPass(){
			document.auditFrom.action = "fo_rc_foreconcileauditcontroller.do?method=passSubmit";
			document.auditFrom.submit();
		}
		//审核驳回
		function processReject(){
			document.auditFrom.action = "fo_rc_foreconcileauditcontroller.do?method=rejectSubmit";
			document.auditFrom.submit();
		}
		//返回
		function processBack(){
			location.href = "fo_rc_foreconcileauditcontroller.do?method=queryauditInit";
		}
	</script>
</head>

<body>
	<form method="post" id="auditFrom" name="auditFrom">
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">申请调账</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<br>
	
	<table class="border_all2" width="55%" border="0" cellspacing="0" cellpadding="3" align="center">
		<tr class="trbgcolor10">
			<td class="border_top_right4">银行名称:</td>
			<td class="border_top_right4">
				<input type="hidden" name="RESULT_ID" value="${rc.resultId}"/>
				<input type="hidden" name="APPLY_ID" value="${rc.applyId}"/>
				<li:codetable selectedValue="${rc.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">出款业务:</td>
			<td class="border_top_right4">
			<li:codetable fieldName="" selectedValue="${rc.withdrawBusiType}" style="desc" attachOption="true" codeTableId="fundOutBusinessTable" />
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">对账状态:</td>
			<td class="border_top_right4"><li:code2name itemList="${busiFlagList}" selected="${rc.busiFlag}"/></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">交易号:</td>
			<td class="border_top_right4"><input type="hidden" name="TRADE_SEQ" value="${rc.transactionNumber}"/>${rc.transactionNumber}</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">交易金额:</td>
			<td class="border_top_right4"><fmt:formatNumber value="${rc.tradeAmount*0.001}" pattern="#,##0.00"  /></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">交易时间:</td>
			<td class="border_top_right4"><fmt:formatDate value="${rc.tradeTime}" type="both"/></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">银行处理日期:</td>
			<td class="border_top_right4"><fmt:formatDate value="${rc.bankProcessDate}" type="date"/></td>
		</tr>
			<tr class="trbgcolor10">
			<td class="border_top_right4">调账理由:</td>
			<td class="border_top_right4">${rc.reason}
			&nbsp;
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">审核理由:</td>
			<td class="border_top_right4"><textArea id="reason" maxlength="200" name="reason" cols="40" rows="5"></textArea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" name="subBtn" value="审核通过" onclick="processPass();" class="button4"/>
				<input type="button" name="subBtn" value="审核驳回" onclick="processReject();" class="button4"/>
				<input type="button" name="backBtn" value="返回" onclick="processBack();" class="button4"/>
			</td>
		</tr>
	</table>
	</form>
</body>

