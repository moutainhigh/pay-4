<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="resultForm">
 	
 </form>
<body>

完全匹配失败列表
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr>     
				<th>序号</th>     
				<th>交易号</th>     
				<th>银行名称</th>     
				<th>充退金额（元）</th>     
				<th>会员姓名</th>
				<th>交易备注</th>
				<th>状态</th>
				<th>银行流水号</th>
				<th>失败原因</th>
			</tr> 
	
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="refundReconcileResult" varStatus="status">
		<tr >
			<td ><c:out value="${status.count}" /></td>
			<td >${refundReconcileResult.leftBackSeq}</td>
			<td >${refundReconcileResult.bankCode}</td>
			<td >${refundReconcileResult.leftAmount/1000 }</td>
			<td >${refundReconcileResult.rightMemberName }</td>
			<td >&nbsp;</td>
			<td >${refundReconcileResult.leftStatus }</td>
			<td >${refundReconcileResult.leftBankSeq }</td>
			<td >户名不符</td>
		</tr>
	</c:forEach>
	</tbody> 
</table>
<li:pagination methodName="queryMatchFail" pageBean="${page}" sytleName="black2"/>
</body>
<script type="text/javascript">

</script>
