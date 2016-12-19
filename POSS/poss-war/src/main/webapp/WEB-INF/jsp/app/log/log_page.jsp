<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
		$(document).ready(function(){

			 $(".tablesorter tbody tr").mouseover(function(){$(this).find("td").css({"background":"#cec"});} )
			 .mouseout(function(){$(this).find("td").css({"background":"#fff"});} ) ;

			       
			 $("#userTable").tablesorter({
				 headers: {
				 	6: {sorter: false},
				 	7: {sorter: false}
				 }});   

			
		});
	
	
	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				
				<th>日志号</th>  
				<th>操作人</th>        
				<th>用户IP</th>     
				<th>操作的时间</th>     
				<th>URL</th>
				<th>操作名称</th>
				<th>详情</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="obj"> 
			<tr>     
				<td>${obj.sequenceId}</td>   
				<td>${obj.loginUser}</td>      
				<td>${obj.userIp}</td>     
				<td>
				<fmt:formatDate value="${obj.creationDate}" pattern="yyyy/MM/dd HH:mm:ss"/>
				</td>     
				<td>${obj.actionUrl}</td> 
				<td>${obj.operationName}</td>
				<td>${obj.details}</td>
				
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
</body>

