<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>

<table id="reviseAudit" class="tablesorter" border="0" cellpadding="0"
	cellspacing="1">
	<thead>
		<tr class="trForContent1">
			<td class="border_top_right4" colspan="10" align="right"><input
				id="download" type="button" onclick="downloadExcel()" value="下载EXCEL" class="button5" /></td>
		</tr>
	</thead>
</table>
<div style="width: 100%; height: 100%; overflow-x: auto; overflow-y: auto;">
<table border='0' cellspacing='0' cellpadding='0' style="width: 800px;">
	<tr>
		<td rowspan="2" class="winbd02" style="width: 80px"><b>时间\银行</b></td>
		<td colspan="6" class="winbd02" style="border-right-width:1px;"><b>${query_bank_input_s_report_list[0].orgName}</b></td>
	</tr>
	<tr>
		<td class="winbd02" style="border-bottom-width: 1px;"><b>总笔数</b></td>
		<td class="winbd02" style="border-bottom-width: 1px;"><b>总金额</b></td>
		<td class="winbd02" style="border-bottom-width: 1px;"><b>完成笔数</b></td>
		<td class="winbd02" style="border-bottom-width: 1px;"><b>完成金额</b></td>
		<td class="winbd02" style="border-bottom-width: 1px;"><b>退款笔数</b></td>
		<td class="winbd02" style="border-bottom-width: 1px;border-right-width:1px;"><b>退款金额</b></td>
	</tr>
	<c:forEach items="${query_bank_input_s_report_list}" var="reportInfo" varStatus="status">
		<tr>
			<td class="winbd02" style="border-right-width: 1px;">&nbsp;<b>${reportInfo.fmtDate}</b></td>
			<td class="winbd03">&nbsp;${reportInfo.allCount}</td>
			<td class="winbd03">&nbsp;<fmt:formatNumber value="${reportInfo.allAmount}" pattern="#,##0.00"/></td>
			<td class="winbd03">&nbsp;${reportInfo.fulfilCount}</td>
			<td class="winbd03">&nbsp;<fmt:formatNumber value="${reportInfo.fulfilAmount}" pattern="#,##0.00"/></td>
			<td class="winbd03">&nbsp;${reportInfo.backCount}</td>
			<td class="winbd03" style="border-right-width:1px;">&nbsp;<fmt:formatNumber value="${reportInfo.backAmount}" pattern="#,##0.00"/></td>
		</tr>
	</c:forEach>
	<tr>
		<td class="winbd02" style="border-right-width:1px;border-bottom-width:1px;">&nbsp;<b>合计</b></td>
		<td class="winbd04">&nbsp;${query_bank_input_s_report_all.allCount}</td>
		<td class="winbd04">&nbsp;<fmt:formatNumber value="${query_bank_input_s_report_all.allAmount}" pattern="#,##0.00"/></td>
		<td class="winbd04">&nbsp;${query_bank_input_s_report_all.fulfilCount}</td>
		<td class="winbd04">&nbsp;<fmt:formatNumber value="${query_bank_input_s_report_all.fulfilAmount}" pattern="#,##0.00"/></td>
		<td class="winbd04">&nbsp;${query_bank_input_s_report_all.backCount}</td>
		<td class="winbd04" style="border-right-width:1px;">&nbsp;<fmt:formatNumber value="${query_bank_input_s_report_all.backAmount}" pattern="#,##0.00"/></td>
	</tr>
</table>
</div>

<c:set var="listSize" value="${fn:length(query_bank_input_s_report_list)}"/>
<script type="text/javascript">
	function downloadExcel() {
		if ("${listSize}" > 0) {
			document.mainfrom.action = "fi.bankInSReport.do?method=downLoadReport&rnd="
					+ Math.random();
			document.mainfrom.submit();
		} else {
			alert("没有任何数据,无法提供下载");
		}
	}
</script>
