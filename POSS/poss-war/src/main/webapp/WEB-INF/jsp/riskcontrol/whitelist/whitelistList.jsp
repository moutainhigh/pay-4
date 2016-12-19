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
			 $("#whiteListTable").tablesorter({
				 headers: {
				 	7: {sorter: false},
				 	8: {sorter: false}
				 }});      
		});

		function processDelete(id) {
			if(confirm("确认删除?")){
				$('#infoLoadingDiv').dialog('open');
				var pars =  "id=" + id ;
				$.ajax({
					type: "POST",
					url: "${ctx}/rm-blackandwhite-whitelist.htm?method=whitelistDelete",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						if (result == 'success') {
							whiteListQuery();
						}else{
							alert("删除失败!");
						}
					}
				});
			}
		}
		
	</script>
</head>

<body>

	<table id="whiteListTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>白名单编号</th>     
				<th>商户代码</th>     
				<th>公司全称</th>     
				<th>用户名</th>     
				<th>银行账号</th>
				<th>操作员ID</th>
				<th>更新时间</th>
				<th colspan="2">操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="whiteListBean" > 
			<tr>     
				<td>${whiteListBean.white_id}</td>     
				<td>${whiteListBean.business_id}</td>     
				<td>${whiteListBean.business_name}</td>     
				<td>${whiteListBean.true_name}</td>     
				<td>${whiteListBean.bank_account}</td> 
				<td>${whiteListBean.operator_id}</td>
				<td ><fmt:formatDate value="${whiteListBean.create_date}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="rm-blackandwhite-whitelist.htm?method=whitelistUpdateForm&id=${whiteListBean.white_id}"   class="nyroModal">编辑</a></td> 
				<td><a href="javascript:processDelete('${whiteListBean.white_id}')">删除</a></td>  
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="whiteListQuery" pageBean="${page}" sytleName="black2"/>
</body>

