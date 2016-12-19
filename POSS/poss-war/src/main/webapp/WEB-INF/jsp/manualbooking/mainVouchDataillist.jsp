<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter();      
		});

		
	</script>
</head>
<body>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>账号</th>     
				<th>账号名称</th>     
				<th>借/贷</th>     
				<th>金额</th>
				<th>备注</th>  
			</tr> 
		</thead> 
		<tbody> 
		<c:forEach items="${page.result}" var="list">
							
							<tr>     
								<td>${list.accountCode}</td>    
								<td>${list.accountName}</td>    
								<td>
									<c:choose>
										<c:when test="${'1' eq list.crdr}">
											借				
										</c:when>
										<c:when test="${'2' eq list.crdr}">
											贷			
										</c:when>				
				                    </c:choose>
								</td>        
								<td>${list.amount}</td>
								<td>${list.remark}</td>     
							</tr>
					</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
</body>
</html>