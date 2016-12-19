<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21">
	<tr>
		<td class="border_top_right4" height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td class="border_top_right4" height="18">
			<div align="center">
				<font class="titletext">风 险 订 单 查 询</font>
			</div>
		</td>
	</tr>
	<tr>
		<td class="border_top_right4" height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">风险订单查询</h2>

<form action="findriskOrder.do" method="post" id="riskForm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4" align="right">商户订单号：</td>
			<td class="border_top_right4" align="left"><input type="text" name="merchantReferenceCode"
				value="${request.merchantReferenceCode}"></td>

	<!-- 		<td class="border_top_right4" align="right">网关订单号：</td>
			<td class="border_top_right4" align="left"><input type="text"></td> -->
			<td class="border_top_right4" align="right">交易创建时间：</td>
			<td class="border_top_right4" colspan="3"><c:if test="${not empty request.startDate}">
					<input class="Wdate" type="text" id="startDate" name="startDate"
						value='${request.startDate }' style="width: 150px;"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				</c:if> <c:if test="${empty request.startDate}">
					<input class="Wdate" type="text" id="startDate" name="startDate"
						value='<fmt:formatDate value="${startTime}" type="both"/>'
						style="width: 150px;"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				</c:if> 至&nbsp; <c:if test="${not empty request.endDate}">
					<input class="Wdate" type="text" id="endDate" name="endDate"
						value='${request.endDate }' style="width: 150px;"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				</c:if> <c:if test="${empty request.endDate}">
					<input class="Wdate" type="text" id="endDate" name="endDate"
						value='<fmt:formatDate value="${endTime}" type="both"/>'
						style="width: 150px;"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				</c:if> <input type="hidden" id="decision" value="${request.decision}">
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right">  风险结果：</td>
			<td class="border_top_right4" align="left">  
			<select name="decision"	id="selectId" style="width: 135px">
					<option value="">-请选择-</option>
					<option id="ACCEPT" value="ACCEPT">通过</option>
					<option id="REAUDIT" value="REAUDIT">审核</option>
					<option id="MONITOR" value="MONITOR">监控</option>
					<option id="REJECT" value="REJECT">拒绝</option>
			</select></td>
			<td class="border_top_right4" align="right"> 会员号：</td>
			<td class="border_top_right4" align="left"> <input type="text"
				name="merchantDefinedData1" value="${request.merchantDefinedData1}"></td>
		</tr>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="4">
				<input type="button" class="button2" onclick="findRiskOrder();" value="查询">
				<input type="button" class="button2" onclick="download()" value="下载"></td>
		</tr>
	</table>
	  <!-- <input type="hidden" name="method"
		value="findCybsResult"> <input type="button"
		onclick="findRiskOrder();" value="查询">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" onclick="download()" value="下载"> -->
</form>
<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<script type="text/javascript">
	/* function findRiskOrder() {
		$("#riskForm").submit();
	} */
	$(document).ready(function() {
		findRiskOrder();
	});

	function findRiskOrder(pageNo, totalCount) {
		//$('#infoLoadingDiv').dialog('open');
		var pars = $("#riskForm").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./findriskOrder.do?method=list",
			data : pars,
			success : function(result) {
				//	$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	function download() {
		var merchantReferenceCode = $("input[name='merchantReferenceCode']")
				.val();
		var startDate = $("input[name='startDate']").val();
		var endDate = $("input[name='endDate']").val();
		var decision = $("#selectId>option:selected").val();
		var merchantDefinedData1 = $("input[name='merchantDefinedData1']")
				.val();
		window.location.href = './findriskOrderDownload.do?method=cybsExcel&merchantReferenceCode='
				+ merchantReferenceCode
				+ '&startDate='
				+ startDate
				+ '&endDate='
				+ endDate
				+ '&decision='
				+ decision
				+ '&merchantDefinedData1=' + merchantDefinedData1;
	}
	$(function() {
		var decision = $("#decision").val();
		if (decision == "ACCEPT") {
			$("#ACCEPT").attr("selected", "selected");
		} else if (decision == "REJECT") {
			$("#REJECT").attr("selected", "selected");
		} else if (decision == "REAUDIT") {
			$("#REAUDIT").attr("selected", "selected");
		} else if (decision == "MONITOR") {
			$("#MONITOR").attr("selected", "selected");
		}
	});
</script>
