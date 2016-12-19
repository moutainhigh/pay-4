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

		function processDelete(id) {
			if(confirm("确认删除?")){
				$('#infoLoadingDiv').dialog('open');
				var pars =  "id=" + id ;
				$.ajax({
					type: "POST",
					url: "${ctx}/rm_limit_syslimit.htm?method=sysLimitDelete",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						if (result == 'success') {
							searchSysLimit();
						}else{
							alert("删除失败!");
						}
					}
				});
			}
		}
		function processEdit(id){
			location.href = "rm_limit_syslimit.htm?method=sysLimitUpdateForm&id=" + id;
		}

	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>    
				<th>流水号</th>     
				<th>直营业务</th>     
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
			<c:forEach items="${page.result}" var="syslimit">
			<tr> 
				<td>${syslimit.sequenceId}</td>        
				<td><li:code2name itemList="${rcBusiness}" selected="${syslimit.sysBusiness}"/></td>
				<td><li:code2name itemList="${useLevelMapList}" selected="${syslimit.userLevel}"/></td>     
				<td><fmt:formatNumber value="${syslimit.singleLimit/1000}" pattern="#,##0.00"/></td>     
				<td><fmt:formatNumber value="${syslimit.dayLimit/1000}" pattern="#,##0.00"/></td>     
				<td><fmt:formatNumber value="${syslimit.monthLimit/1000}" pattern="#,##0.00"/></td> 
				<td>${syslimit.dayTimes}</td>
				<td>${syslimit.monthTimes}</td>
				<td><li:code2name itemList="${statusList}" selected="${syslimit.status}"/></td>
				<!--<td><a href="javascript:processEdit('${syslimit.sequenceId}')">编辑</a></td>--> 
				<td><a href="${ctx}/rm_limit_syslimit.htm?method=sysLimitUpdateForm&id=${syslimit.sequenceId}" class="nyroModal">编辑</a></td>
			<!--<td><a href="javascript:processDelete('${syslimit.sequenceId}')">删除</a></td>  -->
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="searchSysLimit" pageBean="${page}" sytleName="black2"/>
</body>

