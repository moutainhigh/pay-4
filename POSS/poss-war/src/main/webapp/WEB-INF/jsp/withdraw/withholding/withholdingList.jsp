<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>批次号</th>
					<th>文件名</th>
					<th>批次状态</th>
					<th>总笔数</th>
					<th>总金额</th>
					<th>成功笔数</th>
					<th>成功金额</th>
					<th>失败笔数</th>
					<th>失败金额</th>
					<th>创建时间</th>
					<th>修改时间</th>
					<th>查看</th>
				</tr>
			</thead>
			<tbody id="tb">
				<c:forEach var="obj" items="${page.result}" varStatus="v"> 
					<tr>  
						<td>
							${obj.batchNum}
						</td>
						<td>
							<a href="${ctx}/withholding.htm?method=downloadFile&fileKy=${obj.fileKy}">${obj.fileNameDesc}</a>
						</td>
						<td>
							<c:if test="${obj.status == 2}">
								处理完成
							</c:if>
							<c:if test="${obj.status != 2}">
								处理中
							</c:if>
							
						</td>
						<td>
							${obj.totalCount}
						</td>
						<td>
							<fmt:formatNumber value="${obj.totalAmount * 0.001}" pattern="#,##0.00"/>
						</td>
						<td>
							${obj.successCount}
						</td>
						<td>
							<fmt:formatNumber value="${obj.successAmount * 0.001}" pattern="#,##0.00"/>
						</td>
						<td>
							${obj.failureCount}
						</td>
						<td>
							<fmt:formatNumber value="${obj.failureAmount * 0.001}" pattern="#,##0.00"/>
						</td>
						<td>
							<fmt:formatDate value="${obj.createDate}" type="date" />
						</td>
						<td>
							<fmt:formatDate value="${obj.updateDate}" type="date" />
						</td>
						<td >
							<a href="#" onclick="parent.addTabMenu('${obj.fileNameDesc}-详情','${ctx}/withholding.htm?method=queryWithholdingDetail&fileKy=${obj.fileKy}');">查看</a>
						</td>
					</tr>                   
				</c:forEach>                   
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>