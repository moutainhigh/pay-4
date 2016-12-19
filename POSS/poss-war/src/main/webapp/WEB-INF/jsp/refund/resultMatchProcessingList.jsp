<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
 <form action="" method="post" name="resultForm">
	<input type="hidden" name="batchNum" value="${webQueryRefundDTO.batchNum}">
	<input type="hidden" name="bankCode" value="${webQueryRefundDTO.bankCode}">
	<input type="hidden" name="busiFlag" value="">
 </form>
完全匹配进行中列表
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
				<th>银行备注</th>
				<th>操作</th>
			</tr> 
	
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="refundReconcileResult" varStatus="status">
		<tr>
			<td ><c:out value="${status.count}" /></td>
			<td >${refundReconcileResult.leftBackSeq}</td>
			<td >${refundReconcileResult.bankCode}</td>
			<td >${refundReconcileResult.leftAmount/1000 }</td>
			<td >${refundReconcileResult.rightMemberName }</td>
			<td >&nbsp;</td>
			<td >${refundReconcileResult.leftStatus }</td>
			<td >${refundReconcileResult.leftBankSeq }</td>
			<td >系统处理中</td>
			<td >
				<input type="button" value="成  功" class="button2" onclick="successOrFailProcessing(1,'${refundReconcileResult.rightSeqD}','${refundReconcileResult.seqId}');" />
				&nbsp;
				<input type="button" value="失  败" class="button2" onclick="successOrFailProcessing(2,'${refundReconcileResult.rightSeqD}','${refundReconcileResult.seqId}');" />
			</td>
		</tr>
	</c:forEach>
	</tbody> 
</table>
<li:pagination methodName="queryMatchProcessing" pageBean="${page}" sytleName="black2"/>
</body>
<script type="text/javascript">
	function successOrFailProcessing(refundStatus,refundSeq,seqId){
		var pars = "refundStatus=" + refundStatus+"&refundSeq=" + refundSeq+"&seqId=" +seqId;
		$('#infoLoadingDiv').dialog('open');
		$.ajax({
			type: "POST",
			url: "refund.file.do?method=processSuccess",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				if (result == 'success') {
					queryMatchProcessing();
				}else{
					alert("处理失败!");
				}
			}
		});
	}
</script>
