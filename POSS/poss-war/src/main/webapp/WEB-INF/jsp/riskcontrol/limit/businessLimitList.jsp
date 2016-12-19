<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/rmtaglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<head> 
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	11: {sorter: false},
				 	12: {sorter: false}
				 }});      
		});

		function processDelete(id) {
			if(confirm("确认删除?")){
				$('#infoLoadingDiv').dialog('open');
				var pars =  "id=" + id ;
				$.ajax({
					type: "POST",
					url: "${ctx}/rm_limit_businesslimit.htm?method=businessLimitDelete",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						if (result == 'success') {
							searchBusinessLimit();
						}else{
							alert("删除失败!");
						}
					}
				});
			}
		}
		function processEdit(id){
			location.href = "rm_limit_businesslimit.htm?method=businessLimitUpdateForm&id=" + id;
		}

	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>    
				<th>流水号</th>     
				<th>业务类型</th>     
				<th>风险等级</th>
				<th>MCC</th>
				<th>单笔限额</th>     
				<th>每日累计</th>
				<th>每月累计</th>				     
				<th>每日次数</th>     
				<th>每月次数</th>
				<!-- <th>转出账户数</th>-->
				<th>行业限额相关</th>
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="businesslimit" varStatus = "personalStatus">
			<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${businesslimit.sequenceId}</td>
				<td>
				<li:codetable selectedValue="${businesslimit.businessType}" style="desc" fieldName="businessType" attachOption="true" codeTableId="rmEnterpriseBusiTypeTable" />
				</td>
				<td><li:code2name itemList="${riskLevelMapList}" selected="${businesslimit.riskLevel}"/></td>        
				<td>${businesslimit.mcc}</td> 
				<td><c:if test="${businesslimit.status == 1}"><fmt:formatNumber value="${businesslimit.singleLimit/1000}" pattern="#,##0.00"/></c:if>
				<c:if test="${businesslimit.status == 0}">${businesslimit.singleLimit}倍</c:if>
				</td> 
				<td><c:if test="${businesslimit.status == 1}"><fmt:formatNumber value="${businesslimit.dayLimit/1000}" pattern="#,##0.00"/></c:if>
				<c:if test="${businesslimit.status == 0}">${businesslimit.dayLimit}倍</c:if>
				</td>  
				<td><c:if test="${businesslimit.status == 1}"><fmt:formatNumber value="${businesslimit.monthLimit/1000}" pattern="#,##0.00"/></c:if>
				<c:if test="${businesslimit.status == 0}">${businesslimit.monthLimit}倍</c:if>
				</td>     
				<td>${businesslimit.dayTimes}</td>
				<td>${businesslimit.monthTimes}</td>
				<!-- <td>${businesslimit.batchAccounts}</td>-->
				<td><c:if test="${businesslimit.status == 1}">不相关</c:if><c:if test="${businesslimit.status == 0}">相关</c:if></td>
				<!--<td><a href="javascript:processEdit('${businesslimit.sequenceId}')">编辑</a></td>--> 
				<td><a href="${ctx}/rm_limit_businesslimit.htm?method=businessLimitUpdateForm&id=${businesslimit.sequenceId}" class="nyroModal">编辑</a></td>
<!--				<td><a href="javascript:processDelete('${businesslimit.sequenceId}')">删除</a></td>  -->
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="searchBusinessLimit" pageBean="${page}" sytleName="black2"/>
</body>

