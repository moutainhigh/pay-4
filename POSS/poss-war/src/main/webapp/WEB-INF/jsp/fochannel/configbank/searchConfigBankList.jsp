<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="listFrom">
 </form>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>序号</th>
						<th>配置名称</th>
						<th>目的银行</th>
						<th>出款渠道</th>
						<th>状态</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					
						<c:if test="${page != null && fn:length(page.result) > 0}">
					<c:forEach items="${page.result}" var="dto" varStatus="index">
					<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>
							<c:out value="${index.count}" />
						</td>
						<td>
							${dto.configName}
						</td>
						<td>
						<li:select name="targetBankId" selected="${dto.targetBankId}" showStyle="desc"  itemList="${targetBankList}"  />
						</td>
						<td>
							 <li:codetable fieldName="channelId" selectedValue="${dto.channelId}" style="desc" attachOption="true" codeTableId="fundOutChannelTable" />
						</td>
						<td>
							<li:select name="status" showStyle="desc" selected="${dto.status}" itemList="${statusList}"  />
						</td>
						<td>
							${dto.mark }
						</td>
						<td>
							<input type="button" onclick="search('${dto.configId}');"  id="submitBtn" class="button2" value="修改">
							<input type="button" onclick="del('${dto.configId}');"  id="submitBtn1" class="button2" value="删除">
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
	<li:pagination methodName="searchConfig" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 

	function search(configId){
		document.listFrom.action="context_fundout_configbank.controller.htm?method=initModify&configId="+configId;
		document.listFrom.submit();
	}

	function del(configId){
		if(confirm('确认删除吗？')){
			document.listFrom.action="context_fundout_configbank.controller.htm?method=del&configId="+configId;
			document.listFrom.submit();
			}
	}
	
</script>