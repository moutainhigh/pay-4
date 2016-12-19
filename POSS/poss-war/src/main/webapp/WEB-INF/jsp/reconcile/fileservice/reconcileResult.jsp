<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

	function submitUpload(){
		document.reconcileUploadForm.action = "queryReconcile.do?method=upload";
	    document.reconcileUploadForm.submit();
	}
</script>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">导入银行对账文件结果</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
  <h2 class="panel_title">导入银行对账文件结果</h2>
  <table id="detailTable" width="85%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
	<!--<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
	-->
	<tr>
		<th width="10%">对账批次号</th>
		<th width="10%">渠道订单号</th>
		<th width="10%">上传时间</th>
		<th width="10%">操作员</th>
		<th width="10%">对账状态</th>
		<th width="5%">备注 </th>
	</tr>
	</thead>
	<tbody>
	
	<c:forEach items="${channelOrders}" var="reconcileRecord" varStatus="status">
		<c:if test="${reconcileRecord.reconciliationFlg == 2}">
			<tr >
				<td>${reconcileRecord.bacthNo}</td>
				<td >${reconcileRecord.channelOrderNo}</td>
				<td>
				<fmt:formatDate value="${reconcileRecord.bacthCreateDate}" type="both"/>
				</td>
				<td>${reconcileRecord.operator}</td>
				<td>
					<%-- <c:if test="${reconcileRecord.reconciliationFlg == 1}">对账成功</c:if> --%>
					<c:if test="${reconcileRecord.reconciliationFlg == 2}">对账失败</c:if>
				</td>
				<td>
					<c:if test="${not empty reconcileRecord.errorMsg }">${reconcileRecord.errorMsg}</c:if>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<tr>
		<td colspan="2">总笔数：<strong>${fileParseResult.totalRecord}</strong></td>
		<td colspan="2">失败笔数：<strong>${fileParseResult.totalRecord - successCount}</strong></td>
		<td colspan="2">成功笔数：<strong>${successCount}</strong></td>
	</tr>
	</tbody>
</table>
  
  <c:if test="${not empty validDataInfo}"> 
  	<li style="color: red"><c:out value="${validDataInfo}" /> </li>
  </c:if>
