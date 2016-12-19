<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>

<style type="text/css">
	.even{ background:#FFF38F;}
	.odd{ background:FFFFEE;}
</style>

<script type="text/javascript">

$(document).ready( function() { loads(); } ); 
function loads(){
	$("#table1 tbody tr:odd").addClass("odd");
	$("#table1 tbody tr:even").addClass("even");
}

	function queryChannel_reconcile_report_Excel(){
		var channels = "<c:out value='${ channels }' />";
		var start = "<c:out value='${ start }' />";
		var end = "<c:out value='${ end }' />";

		if(channels == ''){
			alert("系统异常，请检查生成Excel连接！");
			return false;
		}
		window.location.href = "reconcile.report.do?method=downloadChannelReconcileReportExcel&channels="+ channels +"&startDate="+ start +"&endDate="+ end+"&dateTemp="+new Date();

	}

	function entryChannelReconcileReport(){
		window.location.href = "reconcile.report.do?method=entryChannelReconcileReport";
	}
</script>

<br/>
<table border="0" style="width:70%;">
  <tr>
    <td width="60%"><h1 align="right">网关对账汇总表</h1></td>
    <td width="20%" align="left"><font color="red">（退款暂不启用）</font></td>
    <td width="20%" align="left">
    	<a href="#" onclick="queryChannel_reconcile_report_Excel()">
    		<font color="blue" size="3">导出excel</font>
    	</a>
    </td>
    <td><input type="button" id="selectBtn" name="selectBtn" value="返回网关对账查询首页" onclick="entryChannelReconcileReport()" /></td>
  </tr>
</table>
<hr/>
<br/>


<table id="table1" class="tablesorter" style="width:90%;" border="0" cellspacing="0" cellpadding="1" align="center">
	<thead> 
	<tr>
		<th align="center">网关</th>
		<th colspan="5">&nbsp;</th>
		<th align="center">起止日期</th>
		<th colspan="6" align="center">${ start } ~~~ ${ end }</th>
	</tr>
	<tr>
		<th rowspan="2" align="center">网关名称</th>
		<th colspan="3" align="center" width="21%">入款交易笔数</th>
		<th colspan="3" align="center" width="21%">入款交易金额</th>
		<th colspan="3" align="center" width="23%">退款交易笔数</th>
		<th colspan="3" align="center" width="23%">退款交易金额</th>
	</tr>
	<tr>
		<th align="center">网关</th>
		<th align="center">系统</th>
		<th align="center">挂账</th>
		<th align="center">网关</th>
		<th align="center">系统</th>
		<th align="center">挂账</th>
		<th align="center">网关退款</th>
		<th align="center">单边账退款</th>
		<th align="center">当日撤回</th>
		<th align="center">网关退款</th>
		<th align="center">单边账退款</th>
		<th align="center">当日撤回</th>
	</tr>
	</thead> 
	<tbody> 
	<c:forEach var="r" items="${ list }">
		<tr>
			<td align="center">${ r.channelName }</td>
			<td align="center">${ r.channelCount }</td>
			<td align="center">${ r.sysCount }</td>
			<td align="center">${ r.suppendCount }</td>
			<td align="center">${ r.channelSum }</td>
			<td align="center">${ r.sysSum }</td>
			<td align="center">${ r.suppendSum }</td>
			<td align="center">-</td>
			<td align="center">-</td>
			<td align="center">-</td>
			<td align="center">-</td>
			<td align="center">-</td>
			<td align="center">-</td>
		</tr>
	</c:forEach>
	</tbody> 
</table>