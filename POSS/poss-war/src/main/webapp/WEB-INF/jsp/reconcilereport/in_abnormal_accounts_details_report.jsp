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

function queryIn_abnormal_accounts_details_Excel(){
	var channels = "<c:out value='${ channels }' />";
	var start = "<c:out value='${ start }' />";
	var end = "<c:out value='${ end }' />";
	var reason = "<c:out value='${ reason }' />";

	if(channels == ''){
		alert("系统异常，请检查生成Excel连接！");
		return false;
	}

	//document.downloadForm.action = "reconcile.report.do?method=downloadInAbnormalAccountsDetailsExcel";
	//document.downloadForm.submit();
	
	window.location.href = "reconcile.report.do?method=downloadInAbnormalAccountsDetailsExcel&channels="+ channels +"&startDate="+ start +"&endDate="+ end +"&reason="+ reason+"&dateTemp="+new Date();
}

function entryInAbnormalAccountsDetails(){
	window.location.href = "reconcile.report.do?method=entryInAbnormalAccountsDetails";
}
</script>

<br/>
<form id="downloadForm" name="downloadForm" method="post">
	<input id="channels" name="channels" type="hidden" value="${ channels }">
	<input id="startDate" name="startDate" type="hidden" value="${ start }">
	<input id="endDate" name="endDate" type="hidden" value="${ end }">
	<input id="reason" name="reason" type="hidden" value="${ reason }">
</form>
<table border="0" style="width:70%;">
  <tr>
    <td width="80%"><h1 align="center">入款挂账明细表</h1></td>
    <td width="20%" align="left">
    	<a href="javascript:queryIn_abnormal_accounts_details_Excel()" >
    		<font color="blue" size="3">导出excel</font>
    	</a>
    </td>
    <td><input type="button" id="selectBtn" name="selectBtn" value="返回入款挂账查询首页" onclick="entryInAbnormalAccountsDetails()" /></td>
  </tr>
</table>
<hr/>
<br/>


<table id="table1" class="tablesorter" style="width:90%;" border="0" cellspacing="0" cellpadding="1" align="center">
	<thead> 
	<tr>
		<th align="center">组织代码</th>
		<th align="center">挂账主体</th>
		<th align="center">交易号</th>
		<th align="center">银行订单号</th>
		<th align="center">银行金额</th>
		<th align="center">系统金额</th>
		<th align="center">业务类型</th>
		<th align="center">订单状态</th>
		<th align="center">挂账原因</th>
	</tr>
	</thead> 
	<tbody> 
	<c:forEach var="o" items="${ list }">
		<tr>
			<td>${ o.BANK_CHANNEL }</td>
			<td>${ o.SYSNAME }</td>
			<td>${ o.SETTLE_DEPOSIT_ID }</td>
			<td>${ o.BANK_ORDER_ID }</td>
			<td>${ o.BANK_AMOUNT }</td>
			<td>${ o.ACCOUNT_AMOUNT }</td>
			<td>${ o.BUSI_TYPE }</td>
			<td>
				<c:if test="${ o.STATUS == '0' }">创建</c:if>
				<c:if test="${ o.STATUS == '1' }">已提交银行</c:if>
				<c:if test="${ o.STATUS == '2' }">支付成功</c:if>
				<c:if test="${ o.STATUS == '3' }">支付失败</c:if>
				<c:if test="${ o.STATUS == '4' }">已冲正</c:if>
			</td>
			<td>
				<c:if test="${ o.ADJUST_STATUS == '3' }">
					<!-- 
					<c:if test="${ o.FLAG == '200' || o.FLAG == '210' }">单边账</c:if>
					<c:if test="${ o.FLAG == '220' }">错账</c:if>
					 -->
					<c:if test="${ o.APPLY_CAUSE == '1' }">错账</c:if>
					<c:if test="${ o.APPLY_CAUSE == '2' }">单边账</c:if>
					<c:if test="${ o.APPLY_CAUSE == '3' }">其它</c:if>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody> 
</table>