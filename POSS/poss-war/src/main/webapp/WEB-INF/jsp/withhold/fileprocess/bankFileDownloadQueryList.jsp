<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.pay.pe.AmountUtils"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>渠道代码</th>
			<th>批次号</th>
			<th>业务类型</th>
			<th>总行数</th>
			<th>成功行数</th>
			<th>失败行数</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:if test="${page != null && fn:length(page.result) > 0}">
			<c:forEach items="${page.result}" var="dto" varStatus="index">
			<tr>
				<td>
					${dto.bankCode}
				</td>
				<td>
					${dto.batchNo}
				</td>
				<td>
					<c:if test="${dto.type=='01'}">签约</c:if> 
					<c:if test="${dto.type=='02'}">代扣</c:if> 
				</td>
				<td>
					${dto.allSum}
				</td>
				<td>
					${dto.successSum}
				</td>
				<td>
					${dto.failSum}
				</td>
				<td>
					<a href="javascript:downloadFile('${dto.sequenceId}');">下载</a>&nbsp;
				</td>
			</c:forEach>
		
		</c:if>
		<c:if test="${page == null || fn:length(page.result) == 0}">
			<tr>
			<td colspan="11" align="center">
				没有查询到相关数据!
			</td>
			</tr>
		</c:if>
	</tbody>
</table>

<li:pagination methodName="bankfileDownloadQuery" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 

 function downloadFile(id){
	var url = "${ctx}/context_withhold_bankfile_download.htm?method=downloadFile&sequenceId="+id;
	window.open(url);
}
	
</script>