<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 
 <form action="" method="post" name="mainfrom" id="resultForm">
 	<input type="hidden"   name="busiFlag"  value="${webQueryReconcileDTO.busiFlag }" />
 	<input type="hidden"   name="countFlag" value="${webQueryReconcileDTO.countFlag}"  />
 	<input type="hidden"   name="bankCode"  value="${webQueryReconcileDTO.bankCode}" />
 	<input type="hidden"   name="providerCode"  value="${webQueryReconcileDTO.providerCode}" />
 	 	<input type="hidden" name="startTime" value='<fmt:formatDate value="${webQueryReconcileDTO.startTime}" type="date"/>'  >
	<input type="hidden" name="endTime"  value='<fmt:formatDate value="${webQueryReconcileDTO.endTime}" type="date"/>' >
 </form>

<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr class="trForContent1" > 
			<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
		</tr>
		<tr >
			<th >序号</th>
			<th >银行名称</th>
			<th >服务代码</th>
			<th >银行订单号</th>
			<th >系统交易金额</th>
			<th >银行交易金额</th>
			<th >交易日期</th>
		</tr>
	</thead>
	<tbody> 
			<c:forEach items="${page.result}" var="reconcileResultFunds" varStatus="status">
				<tr>
					<td ><c:out value="${status.count}" /></td>
					<td >${reconcileResultFunds.bankCode}</td>
					<td >${reconcileResultFunds.providerCode}</td>
					
					<td >${reconcileResultFunds.accountSeq}&nbsp;</td>
					<td ><fmt:formatNumber value="${reconcileResultFunds.accountAmount}" pattern="#,##0.00"  />&nbsp;</td>
					<td ><fmt:formatNumber value="${reconcileResultFunds.bankAmount}" pattern="#,##0.00"  />&nbsp;</td>
					<td >
					<fmt:formatDate value="${reconcileResultFunds.busiTime}" type="date"/>
					</td>
				</tr>
			</c:forEach>
	</tbody>
	
</table>
<li:pagination methodName="queryResultSummaryDetail" pageBean="${page}" sytleName="black2"/>
<div align="center">
	 <a class="s03" href="#" onclick="javascript:history.go(-1);"><img
			src="./images/goback.jpg" border="0"> </a>
      </div>
      
<c:set var="listSize" value="${fn:length(page.result)}"/>
<script type="text/javascript">

function downloadExcel() {
	if("${listSize}" > 0){
		document.mainfrom.action = "reconcile.resultsummary.do?method=downloadReconcileResultDetail&rnd=" + Math.random();
		document.mainfrom.submit();
	}else{
		alert("没有任何数据,无法提供下载");
	}
}
</script>
