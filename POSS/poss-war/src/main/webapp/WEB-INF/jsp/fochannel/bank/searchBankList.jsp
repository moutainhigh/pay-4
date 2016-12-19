<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="listFrom">
 </form>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>序号</th>
						<th>ID</th>
						<th>出款银行</th>
						<th>状态</th>
						<th>创建时间</th>
						<th>修改时间</th>
						<th>备注</th>
						<th>操作员</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					
						<c:if test="${page != null && fn:length(page.result) > 0}">
					<c:forEach items="${page.result}" var="dto" varStatus="index">
					<tr>
						<td>
							<c:out value="${index.count}" />
						</td>
						<td>
							${dto.bankId}
						</td>
						<td>
							${dto.bankName}
						</td>
						<td>
							<li:select name="status" showStyle="desc" selected="${dto.status}" itemList="${statusList}"  />
						</td>
						<td>
							<fmt:formatDate value="${dto.createDate }" type="both"/>
						</td>
						<td>
							<fmt:formatDate value="${dto.updateDate }" type="both"/>
						</td>
						<td>
							${dto.mark }
						</td>
						<td>
							${dto.operator }
						</td>
						<td>
							<input type="button" onclick="searchBankForId('${dto.bankId}');"  id="submitBtn" class="button2" value="修 改">
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
	<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				8: {sorter: false}
			}
		});
	 }); 

	function searchBankForId(bankId){
		document.listFrom.action="context_fundout_fobank.controller.htm?method=initModify&bankId="+bankId;
		document.listFrom.submit();
	}
	
</script>