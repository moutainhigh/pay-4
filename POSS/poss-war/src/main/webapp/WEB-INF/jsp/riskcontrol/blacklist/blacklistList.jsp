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
			 $("#blackListTable").tablesorter({
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
					url: "${ctx}/rm-blackandwhite-blacklist.htm?method=blackListDelete",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						if (result == 'success') {
							blackListQuery();
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

	<table id="blackListTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>黑名单编号</th>     
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
			<c:forEach items="${page.result}" var="blackListBean" > 
			<tr>     
				<td>${blackListBean.black_id}</td>     
				<td>${blackListBean.business_id}</td>     
				<td>${blackListBean.business_name}</td>     
				<td>${blackListBean.true_name}</td>     
				<td>${blackListBean.bank_account}</td> 
				<td>${blackListBean.operator_id}</td>
				<td ><fmt:formatDate value="${blackListBean.create_date}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="rm-blackandwhite-blacklist.htm?method=blacklistUpdateForm&id=${blackListBean.black_id}"  class="nyroModal">编辑</a></td> 
				<td><a href="javascript:processDelete('${blackListBean.black_id}')">删除</a></td>  
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="blackListQuery" pageBean="${page}" sytleName="black2"/>
</body>

