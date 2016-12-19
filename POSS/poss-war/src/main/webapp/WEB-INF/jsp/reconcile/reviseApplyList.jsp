<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 

 <form action="" method="post" name="e_mainfrom" id="resultForm">
 <input type="hidden" name="startTime"  value='<fmt:formatDate value="${webQueryReconcileDTO.startTime}" type="date"/>'   >
		<input  type="hidden" name="endTime"   value='<fmt:formatDate value="${webQueryReconcileDTO.endTime}" type="date"/>' >
		<input  type="hidden" name="bankCode"   value='${webQueryReconcileDTO.bankCode }' >
		<input  type="hidden" name="providerCode"   value='${webQueryReconcileDTO.providerCode }' >
		<input  type="hidden" name="adjustStatus"   value='${webQueryReconcileDTO.adjustStatus }' >
		<input  type="hidden" name="accountSeq"   value='${webQueryReconcileDTO.accountSeq }' >
		<input  type="hidden" name="busiFlag"   value='${webQueryReconcileDTO.busiFlag }' >
 </form>
 <form action="" method="post" name="fromapply">
 	<input type="hidden" id="bankCode" name="bankCode"   />
 	<input type="hidden" id="providerCode" name="providerCode"   />
 	<input type="hidden" id="accountSeq" name="accountSeq"   />
 	<input type="hidden" id="id" name="id"   />
 	<input type="hidden" id="statusId" name="adjustStatus"   />
 	<!--  <input  type="hidden" name="busiFlag"   value='${webQueryReconcileDTO.busiFlag }'/>-->
 	<input  type="hidden" id="acceptKy" name="acceptKy"  />
 </form>
 <body>
<table id="reviseAudit" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
<thead> 
	<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="10" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
		<tr>
		
			<th> 序号</th>
			<th> 银行名称</th>
			<th> 服务代码</th>
			<th> 订单号</th>
			<th> 交易金额</th>
			<th> 交易日期</th>
			<th> 错账方</th>
			<th> 调账状态</th>
			<th> 操作</th>
		</tr>
	</thead>
	<tbody>
			<c:forEach items="${page.result}" var="reconcileResultFunds" varStatus="status">
				<tr>
					<td ><c:out value="${status.count}" /></td>
					<td >${reconcileResultFunds.bankCode}</td>
					<td >${reconcileResultFunds.providerCode}</td>
					<td >${reconcileResultFunds.accountSeq}&nbsp;</td>
					<td ><fmt:formatNumber value="${reconcileResultFunds.accountAmount}" pattern="#,##0.00"/>&nbsp;</td>
					<td >
					<fmt:formatDate value="${reconcileResultFunds.busiTime}" type="date"/>
					</td>
					<td >
						 ${reconcileResultFunds.busiFlagDesc} 
					</td>
					
						<c:if test="${'1' eq reconcileResultFunds.adjustStatus}">
							<td >
								未调账
							</td>
							<td >
								<a href="#" onclick="queryReconcileApply('${reconcileResultFunds.bankCode}','${reconcileResultFunds.providerCode}','${reconcileResultFunds.accountSeq}',0,'${reconcileResultFunds.id}','','${reconcileResultFunds.busiFlag}');" >
								调账申请</a>
							</td>
						</c:if>
						<c:if test="${'2' eq reconcileResultFunds.adjustStatus}">
							<td >
								待审核
							</td>
							<td >
								<a href="#" onclick="queryReconcileApply('${reconcileResultFunds.bankCode}','${reconcileResultFunds.providerCode}','${reconcileResultFunds.accountSeq}',1,'${reconcileResultFunds.id}','${reconcileResultFunds.acceptKy}');" >
								查看</a>
							</td>
						</c:if>
						<c:if test="${'3' eq reconcileResultFunds.adjustStatus}">
							<td >
								审核通过
							</td>
							<td >
							<c:if test="${not empty reconcileResultFunds.adjustType and reconcileResultFunds.adjustType == reconcileResultFunds.busiFlag}">
								<a href="#" onclick="queryReconcileApply('${reconcileResultFunds.bankCode}','${reconcileResultFunds.providerCode}','${reconcileResultFunds.accountSeq}',1,'${reconcileResultFunds.id}','${reconcileResultFunds.acceptKy}');" >
								查看</a>
							</c:if>
							<c:if test="${empty reconcileResultFunds.adjustType}">
								<a href="#" onclick="queryReconcileApply('${reconcileResultFunds.bankCode}','${reconcileResultFunds.providerCode}','${reconcileResultFunds.accountSeq}',1,'${reconcileResultFunds.id}','${reconcileResultFunds.acceptKy}');" >
									查看
								</a>
							</c:if>
					
	
							</td>
						</c:if>
						<c:if test="${'4' eq reconcileResultFunds.adjustStatus}">
							<td >
							审核退回
							</td>
							<td >
							<a href="#" onclick="queryReconcileApply('${reconcileResultFunds.bankCode}','${reconcileResultFunds.providerCode}','${reconcileResultFunds.accountSeq}',0,'${reconcileResultFunds.id}','','${reconcileResultFunds.busiFlag}');" >
								调账申请</a>
							</td>
						</c:if>
				</tr>
			</c:forEach>
	
	</tbody>
</table>
<li:pagination methodName="queryReviseReconcileAudit" pageBean="${page}" sytleName="black2"/>
<script type="text/javascript">

	function queryReconcileApply(bankCode,providerCode,accountSeq,isShow,id,acceptKy,busiFlag){
		$("#bankCode").val(bankCode);
		$("#providerCode").val(providerCode);
		$("#accountSeq").val(accountSeq);
		$("#id").val(id);
		if(1 == isShow){
			$("#acceptKy").val(acceptKy);
			//document.fromapply.action="reconcile.reviseReconcile.do?method=queryReconcileAcceptMain";
			document.fromapply.action="reconcile.reviseApply.do?method=queryReviseApplyDetail";
			document.fromapply.submit();
		}else{
			$("#apply").val("true");
			//document.fromapply.action="reconcile.reviseReconcile.do?method=queryReviseReconcileById";
			document.fromapply.action="reconcile.reviseApply.do?method=queryReviseApplyDetailById&busiFlag="+busiFlag;
			document.fromapply.submit();
		}
		
	}
	function downloadExcel() {
		if("${page.totalCount}" > 0 ){
			document.e_mainfrom.action = "reconcile.reviseApply.do?method=downloadExcel&rnd=" + Math.random();
			document.e_mainfrom.submit();
		}else{
			alert("没有任何数据,无法提供下载");
		}
	}

</script>