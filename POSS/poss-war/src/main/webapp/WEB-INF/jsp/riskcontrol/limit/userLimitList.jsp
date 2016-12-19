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
				 	9: {sorter: false},
			 		10: {sorter: false}
				 }});      
		});

		function processDelete(id,status) {
			if(confirm("确认删除?")){
				$('#infoLoadingDiv').dialog('open');
				var pars =  "id=" + id ;
				$.ajax({
					type: "POST",
					url: "${ctx}/rm_limit_userlimit.htm?method=userLimitDelete",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						if (result == 'success') {
							searchUserLimit();
						}else{
							alert("删除失败!");
						}
					}
				});
			}
		}
		function processEdit(id){
			location.href = "rm_limit_userlimit.htm?method=userLimitUpdateForm&id=" + id;
		}

	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>    
				<th>流水号</th>     
				<th>业务类型</th>     
				<th>用户等级</th>
				<th>单笔限额</th>     
				<th>每日累计</th>
				<th>每月累计</th>				     
				<th>每日次数</th>     
				<th>每月次数</th>
				<th>状态</th>
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="userlimit" varStatus = "personalStatus">
			<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${userlimit.sequenceId}</td>        
				<td>
				<li:codetable selectedValue="${userlimit.businessType}" style="desc" fieldName="businessType" attachOption="true" codeTableId="rmPersonBusiTypeTable" />
				</td>     
				<td><li:code2name itemList="${useLevelMapList}" selected="${userlimit.userLevel}"/></td>     
				<td><fmt:formatNumber value="${userlimit.singleLimit/1000}" pattern="#,##0.00"  /></td>     
				<td><fmt:formatNumber value="${userlimit.dayLimit/1000}" pattern="#,##0.00"  /></td>     
				<td><fmt:formatNumber value="${userlimit.monthLimit/1000}" pattern="#,##0.00"  /></td> 
				<td>${userlimit.dayTimes}</td>
				<td>${userlimit.monthTimes}</td>
				<td><li:code2name itemList="${statusList}" selected="${userlimit.status}"/></td>
				<!--<td><a href="javascript:processEdit('${userlimit.sequenceId}')">编辑</a></td>--> 
				<td><a href="${ctx}/rm_limit_userlimit.htm?method=userLimitUpdateForm&id=${userlimit.sequenceId}" class="nyroModal">编辑</a></td>
<!--				<td><a href="javascript:processDelete('${userlimit.sequenceId}')">删除</a></td>  -->
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="searchUserLimit" pageBean="${page}" sytleName="black2"/>
</body>

