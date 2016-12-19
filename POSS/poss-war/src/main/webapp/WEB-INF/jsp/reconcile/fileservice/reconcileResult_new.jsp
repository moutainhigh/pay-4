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
  

  <table id="detailTable" align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<!--<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
	-->
	<tr class="">
		<th >文件名</th>
		<th >条目数</th>
		<th >对账结果码</th>
		<th>提示消息</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="message" items="${messageList }">
		<tr class="trForContent1">
			<td colspan="1"><strong>${message.orginalFileName}</strong></td>
			<td colspan="1">总笔数：<strong>${message.fileParseResult.totalRecord}</strong></td>
			<td colspan="1">结果码：<strong>${message.responseCode}</strong></td>
			<td colspan="1">提示消息：<strong>${message.responseDesc}</strong></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
  
  <c:if test="${not empty validDataInfo}"> 
  	<li style="color: red"><c:out value="${validDataInfo}" /> </li>
  </c:if>
