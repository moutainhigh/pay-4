<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	6: {sorter: false},
				 	7: {sorter: false}
				 }});      
		});
		function processSearch(){
			var userSearchForm = $("#userSearchForm");
			userSearchForm.submit();
		}
		function processEdit(id){
			location.href = "user.do?method=updateInit&id=" + id;
		}
		function processAdd(){
			location.href = "userAdd.do";
		}
	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>用户姓名</th>     
				<th>用户名</th>     
				<th>部门</th>     
				<th>职位</th>     
				<th>状态</th>
				<th>最后登录时间</th>
				<th colspan="2">操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="use"> 
			<tr>     
				<td>${use.userName}</td>     
				<td>${use.userCode}</td>     
				<td>${use.userOrgName}</td>     
				<td>${use.userDutyName}</td>     
				<td>${use.userStatusName}</td> 
				<td>${use.lastLoginTime}</td>
				<td><a href="javascript:processEdit('${use.userKy}')">编辑</a></td> 
				<!--<td><a href="${ctx}/user.do?method=updateInit&id=${use.userKy}" class="nyroModal">编辑</a></td>-->
				<td><a href="javascript:processDelete(deleteUser,'${use.userKy}','确认删 除姓名为：${use.userName} 的用户信息吗')">删除</a></td>  
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
</body>

