<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li" uri="poss"%>
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
					2: {sorter: false},
				 	3: {sorter: false},
			 		4: {sorter: false}
				 }});      
		});
	</script>
</head>

<body>
	<table  id="userTable" width="100%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th style="width:20%">分组</th>     
				<th style="width:30%">键 (KEY)</th> 
				<th style="width:30%">值 (VALUE)</th>
				<th style="width:8%">状态</th>    
				<th style="width:12%">操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="conf">
			<tr>     
				<td>${conf.groupCode}</td>     
				<td>${conf.key}</td>
				<td>${conf.value}</td>
				<td><c:if test="${conf.state eq '1'}">有效</c:if><c:if test="${conf.state == '0'}">无效</c:if></td>
				<td>
				<a href="${ctx}/if_comp/if_config_action.do?method=gotoDetail&confId=${conf.id}&opp=view" class="nyroModal">查看</a>
				<c:if test="${conf.state eq '1'}">
				<a href="${ctx}/if_comp/if_config_action.do?method=gotoDetail&confId=${conf.id}&opp=modify" class="nyroModal">更新</a>
				<a href="${ctx}/if_comp/if_config_action.do?method=gotoDetail&confId=${conf.id}&opp=flush" class="nyroModal">刷新缓存</a>
				</c:if>
				</td> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	
	<li:pagination methodName="searchBusiness" pageBean="${page}" sytleName="black2"/>
	
</body>

