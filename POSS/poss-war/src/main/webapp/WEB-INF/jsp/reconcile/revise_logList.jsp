<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>
 <form action="" method="post" name="mainfrom" id="resultForm">
 	<input type="hidden"   name="busiFlag"  value="${webQueryReconcileDTO.busiFlag }" />
 	<input type="hidden"   name="countFlag" value="${webQueryReconcileDTO.countFlag}"  />
 	<input type="hidden"   name="bankCode"  value="${webQueryReconcileDTO.bankCode}" />
 	<input type="hidden"  name="accountSeq" value="${webQueryReconcileDTO.accountSeq }"  />
 	<input type="hidden"   name="providerCode"  value="${webQueryReconcileDTO.providerCode}" />
 	<input type="hidden"   name="adjustStatus"  value="${webQueryReconcileDTO.adjustStatus}" />
 	<input type="hidden" name="startTime"  value='<fmt:formatDate value="${webQueryReconcileDTO.startTime}" type="date"/>'   >
	<input  type="hidden" name="endTime"   value='<fmt:formatDate value="${webQueryReconcileDTO.endTime}" type="date"/>' >
	 	
 </form>
 <form action="" method="post" name="fromapply">
 	<input type="hidden" id="acceptKy" name="acceptKy"   />
 	<input type="hidden" id="id" name="id"   />
 	<input type="hidden" id="id" name="actionSource"  value="log"  />
 	<input type="hidden"   name="busiFlag"  value="${webQueryReconcileDTO.busiFlag }" />
 </form>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
	<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
		<tr >
			<th>序号</th>
			<th>银行名称</th>
			<th>服务代码</th>
			<th>订单号</th>
			<th>交易金额</th>
			<th>交易日期</th>
			<th>错账方</th>
			<th>调账状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="reconcileAcceptMain" varStatus="status">
		<tr>
			<td ><c:out value="${status.count}" /></td>
			<td >${reconcileAcceptMain.bankCode}</td>
			<td >${reconcileAcceptMain.providerCode}</td>
			<td >${reconcileAcceptMain.accountSeq}</td>
			<td >
			<fmt:formatNumber value="${reconcileAcceptMain.accountAmount}" pattern="#,##0.00"/>
			</td>
			<td >
			<fmt:formatDate value="${reconcileAcceptMain.busiTime}" type="date"/>
			</td>
			<td >
				${reconcileAcceptMain.busiFlagDesc}
			</td>
			<td >
				${reconcileAcceptMain.adjustStatusDesc }
			</td>
			<td >
				<c:if test="${ empty reconcileAcceptMain.adjustType}">
					<a href="#" onclick="queryReconcileLog('${reconcileAcceptMain.id}','${reconcileAcceptMain.acceptKy}','${reconcileAcceptMain.busiFlag}');" >
						查看日志
					</a>
				</c:if>
				<c:if test="${not empty reconcileAcceptMain.adjustType and reconcileAcceptMain.adjustType == reconcileAcceptMain.busiFlag}">
					<a href="#" onclick="queryReconcileLog('${reconcileAcceptMain.id}','${reconcileAcceptMain.acceptKy}','${reconcileAcceptMain.busiFlag}');" >
						查看日志
					</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">
	function queryReconcileLog(id,acceptKy,adjustType){
		$("input[name=id]").val(id);
		$("input[name=acceptKy]").val(acceptKy);
		var url = "reconcile.reviseLog.do?method=queryReviseLogDetail&adjustType="+adjustType;
		document.fromapply.action=url;
		document.fromapply.submit();
	}

	function downloadExcel() {
		if("${page.totalCount}" > 0){
			document.mainfrom.action = "reconcile.reviseLog.do?method=downloadExcel&rnd=" + Math.random();
			document.mainfrom.submit();
		}else{
			alert("没有任何数据,无法提供下载");
		}
	}
</script>