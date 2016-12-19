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
				url: "${ctx}/rulemanage.do?method=updateRuleStatus",
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
				<th>目的 银行</th>     
				<th>出款银行</th>  
				<th>单笔最小出款金额</th>
				<th>单笔最大出款金额</th>      
				<th>交易类型</th>   
				<th>银行账户类型</th>  
				<th>优先级</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${rulelist}" var="rule">
			<tr>     
				<td>${rule.sequenceId}</td>     
				<td>${rule.toBankName}</td>  
				<td>${rule.withdrawBankName}</td>  
				<td>${rule.sinMinValue}</td>   
				<td>${rule.sinMaxValue}</td>
				<td><c:if test="${rule.withdrawType == 1}">对公</c:if><c:if test="${rule.withdrawType == 2}">对私</c:if></td>
				<td>${rule.businessName}</td>
				<td>${rule.priority}</td>
				<td>${rule.status}</td>
				<td><fmt:formatDate  value="${rule.creationDate}" type="Date" pattern="yyyy.MM.dd HH:mm:ss" /></td> 
				<td><fmt:formatDate  value="${rule.updateDate}" type="Date" pattern="yyyy.MM.dd HH:mm:ss" /></td>
				<td>
					<c:if test="${rule.status == 1}"><a href="javascript:updateStatus(${rule.sequenceId},0)">关闭</a></c:if>
					<c:if test="${rule.status == 0}"><a href="javascript:updateStatus(${rule.sequenceId},1)">开启</a></c:if>
					</td> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	
</body>

