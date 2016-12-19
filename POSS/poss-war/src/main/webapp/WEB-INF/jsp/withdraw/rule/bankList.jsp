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
				url: "${ctx}/rulemanage.do?method=updateBankStatus",
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
				<th>简称</th>    
				<th>状态</th>  
				<th>出款方式</th>   
				<th>出款业务</th>  
				<th>创建时间</th>
				<th>修改时间</th>
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${banklist}" var="bank">
			<tr>     
				<td>${bank.sequenceId}</td>     
				<td>${bank.bankName}</td>  
				<td>${bank.bankCode}</td>     
				<td>${bank.status}</td>
				<td>${bank.typeName}</td>
				<td>${bank.businessName}</td>
				<td><fmt:formatDate  value="${bank.creationDate}" type="Date" pattern="yyyy.MM.dd HH:mm:ss" /></td> 
				<td><fmt:formatDate  value="${bank.updateDate}" type="Date" pattern="yyyy.MM.dd HH:mm:ss" /></td>
				<td>
					<c:if test="${bank.status == 1}"><a href="javascript:updateStatus(${bank.sequenceId},0)">关闭</a></c:if>
					<c:if test="${bank.status == 0}"><a href="javascript:updateStatus(${bank.sequenceId},1)">开启</a></c:if>
					</td> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	
</body>

