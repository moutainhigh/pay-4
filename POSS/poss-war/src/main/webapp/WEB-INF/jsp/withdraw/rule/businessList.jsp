<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	1: {sorter: true}
				 }});      
		});

		function updateStatus(id,status) {
			
			$('#infoLoadingDiv').dialog('open');
			var pars =  "id=" + id + "&status=" + status;
			$.ajax({
				type: "POST",
				url: "${ctx}/rulemanage.do?method=updateBusinessStatus",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>ID</th>     
				<th>名称</th>     
				<th>状态</th>  
				<th>创建时间</th>
				<th>修改时间</th>
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${businesslist}" var="business">
			<tr>     
				<td>${business.sequenceId}</td>     
				<td>${business.businessName}</td>     
				<td>${business.status}</td>    
				<td><fmt:formatDate  value="${business.creationDate}" type="Date" pattern="yyyy.MM.dd HH:mm:ss" /></td> 
				<td><fmt:formatDate  value="${business.updateDate}" type="Date" pattern="yyyy.MM.dd HH:mm:ss" /></td>
				<td>
					<c:if test="${business.status == 1}"><a href="javascript:updateStatus(${business.sequenceId},0)">关闭</a></c:if>
					<c:if test="${business.status == 0}"><a href="javascript:updateStatus(${business.sequenceId},1)">开启</a></c:if>
					</td> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	
</body>

