<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table id="detailTable" width="85%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
	<!--<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
	-->
	<tr>
		<th>序号 </th>
		<th>出款银行 </th>
		<th>交易号 </th>
		<th>银行流水 </th>
		<th>银行交易金额 </th>
		<th>交易时间 </th>		
		<th>交易状态 </th>
	</tr>
	</thead>
	<tbody>
	
	<c:forEach items="${page.result}" var="reconcileRecord" varStatus="status">
		<tr >
			<td ><c:out value="${status.count}" /></td>
			<td >
				<li:codetable  selectedValue="${reconcileRecord.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
			</td>
			<td >${reconcileRecord.tradeSeq}</td>
			<td >${reconcileRecord.bankSeq}</td>
			<td >
				<fmt:formatNumber value="${reconcileRecord.bankAmount*0.001}"  pattern="#,##0.00"/>
			</td>
			<td >
				<fmt:formatDate pattern='yyyy-MM-dd' value='${reconcileRecord.tradeTime}'/>
			</td>
			<td >
				<c:choose>
					<c:when test="${'1' eq reconcileRecord.busiStatus}">
						成功
					</c:when>
					<c:when test="${'2' eq reconcileRecord.busiStatus}">
						失败
					</c:when>
					<c:when test="${'3' eq reconcileRecord.busiStatus}">
						处理中
					</c:when>
					<c:otherwise>
						${reconcileRecord.busiStatus}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>
<form action="" id="detailInfoFormId">
	<input type="hidden" name="fileId" id="fileId" value="${fileId }" />
	<input type="hidden" name="busiDate" id="busiDate" value="${busiDate }" />
	<input type="hidden" name="orgCode" id="orgeCode" value="${orgCode }" />
</form>
<div align="center">	
	<c:if test="${'5' eq status}">
		<input type="button" name="reconcileInfo" value="对账"  onclick="singleReconcile();" class="button2"/>
	</c:if>
	<input type="button" name="goBackBtn" value="返回" onclick="goBack();" class="button2"/>
</div>

<script type="text/javascript">
	function singleReconcile(){
		document.all.reconcileInfo.disabled = true;
		var params = $("#detailInfoFormId").serialize();
		$('#infoLoadingDiv').dialog('open');
		$.ajax({
			type: "POST",
			url: "fo_rc_queryreconcile.do?method=singleReconcileInfo",
			data: params,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				if("success" != result){
					alert("对账失败!");
				}else{
					alert("对账成功!");
					history.go(-1);
				}
			}
		});
	}
	
	
	function goBack() {
			document.detailInfoFormId.action = 'fo_rc_queryreconcile.do?method=initQueryReconcileFileUploadInfo';
			document.detailInfoFormId.submit();
	}
	
	function downloadExcel() {
		if("${page.totalCount}" > 0){
			document.downloadForm.action = "reconcile.download.do?method=downloadExcel";
			document.downloadForm.submit();
		}else{
			alert("没有任何数据,无法提供下载");
		}
	}
</script>