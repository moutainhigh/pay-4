<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!--<form action="fundout-withdraw-querybankfile.do" method="post" id="initForm"> -->
<form action="fundout-withdraw-querybatchfile.do?method=entryBatchFile" method="post" id="initForm">
<input type="hidden" name="batchNum" value="${batchFileInfo.batchNum}" />

<h2 class="panel_title">手工生成批次类型</h2>
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>批次号</th>
			<th>批次名称</th>
			<th>总比数</th>
			<th>总金额</th>
			<th>产生时间</th>
		</tr>
	</thead>
	<tbody>
	<tr class="trForContent1">
			<td >${batchFileInfo.batchNum}</td>
			<td >${batchFileInfo.batchName}</td>
			<td >${batchFileInfo.allCount} </td>
			<td><fmt:formatNumber value="${batchFileInfo.allAmount/1000}" pattern="#,##0.00"  /></td>
			<td > 
				<fmt:formatDate value="${batchFileInfo.generateTime}" type="both"/>
			</td>
		</tr>
	</tbody>		
		<tr class="trForContent2">
			<td colspan="5" align="center"><input type="submit" name="showBatch" class="button2" value="查看批次文件" /></td>
		</tr>
  </table>
</form>
