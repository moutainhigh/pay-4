<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		//提交
		function processSubmit(){
			document.applyFrom.action = "fo_rc_foreconcileapplycontroller.do?method=applySubmit";
			document.applyFrom.submit();
		}
		//返回
		function processBack(){
			location.href = "fo_rc_foreconcileapplycontroller.do?method=queryapplyInit";
		}
	</script>
</head>

<body>
	<form id="applyFrom" name="applyFrom" method="post">
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">查看详细</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<br>
	
	<table class="border_all2" width="55%" border="0" cellspacing="0" cellpadding="3" align="center">
		<input type="hidden" name="RESULT_ID" value="${rc.resultId}"/>
		<tr class="trbgcolor10">
			<td class="border_top_right4">银行名称:</td>
			<td class="border_top_right4">
				<li:codetable selectedValue="${rc.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">出款业务:</td>
			<td class="border_top_right4">
				<li:codetable selectedValue="${rc.withdrawBusiType}" style="desc" attachOption="true" codeTableId="fundOutBusinessTable" />
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
		<tr>
			<td colspan="2" align="center">
				<input type="button" name="backBtn"  value="返  回" onclick="processBack();" class="button2"/>
			</td>
		</tr>
	</table>
	</form>
</body>

