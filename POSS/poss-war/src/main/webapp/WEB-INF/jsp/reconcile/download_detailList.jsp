<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>
 <form action="" method="post" name="downloadForm">
 <input type="hidden"   name="fileKy"  value="${fileKy}" />
 </form> 
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
	<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
	<tr>
		<th>序号 </th>
		<th>文件号 </th>
		<th>系统交易码 </th>
		<th>银行代码 </th>
		<th>服务代码 </th>
		<th>银行流水 </th>
		<th>银行交易金额 </th>
		<th>银行订单号 </th>
		
		<th>日期时间 </th>
	</tr>
	</thead>
	<tbody>
	
	<c:forEach items="${page.result}" var="reconcileImport" varStatus="status">
		<tr >
			<td ><c:out value="${status.count}" /></td>
			<td >${reconcileImport.fileKy}</td>
			<td ></td>
			<td >${reconcileImport.bankCode}</td>
			<td >${reconcileImport.providerCode}</td>
			<td >${reconcileImport.bankSeq}&nbsp;</td>
			<td >
				<fmt:formatNumber value="${reconcileImport.bankAmount}" type="currency" pattern="#,##0.00"/>
			</td>
			<td >${reconcileImport.accountSeq}</td>
			<td >
				<fmt:formatDate value="${reconcileImport.cutTime}" type="date"/>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<li:pagination methodName="queryDetail" pageBean="${page}" sytleName="black2"/>
<div align="center">	
	<a class="s03" href="#" onclick="javascript:history.go(-1);"><img
			src="./images/goback.jpg" border="0"> </a>
</div>
<script type="text/javascript">
function downloadExcel() {
	if("${page.totalCount}" > 0){
		document.downloadForm.action = "reconcile.download.do?method=downloadExcel&rnd=" + Math.random();
		document.downloadForm.submit();
	}else{
		alert("没有任何数据,无法提供下载");
	}
}
</script>