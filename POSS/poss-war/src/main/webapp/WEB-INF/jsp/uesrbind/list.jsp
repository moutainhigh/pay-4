<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<body>
<script type="text/javascript">
$(function(){
	$("[data]").click(function(){
		var dta = $(this).attr('data');
		$.post('${ctx}/userbindinfo.htm?method=update&id='+$(this).attr('d1')+'&status='+dta,function(data){
				query();
		});
	});
});
</script>
<table id="withdrawTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>编号</th>     
				<th>手机号码</th>     
				<th>姓名</th>  
				<th>身份证号</th>     
				<th>银行卡号</th>     
				<th>绑定时间</th>     
			    <th>状态</th>   
			    <th>操作</th> 
			</tr> 
		</thead> 
	<tbody>	
		<c:forEach items="${page.result}"  var="order">
			<tr>     
			    <td>${order.id}</td>
			    <td>${order.mobile}</td>
			    <td>${order.name}</td>
			    <td>${order.idNo}</td>
			    <td>${order.bankNo}</td>
			    <td><fmt:formatDate value="${order.createTime}" type="both"/></td>
			    <td>
			       <c:if test="${order.status==1}">正常</c:if>
			       <c:if test="${order.status==0}">撤销</c:if>
			   </td>
			   <td> 
			   		<c:if test="${order.status==0}">
			   			<span data="1" d1='${order.id}'>启用绑定</span>
			   		</c:if>
			   		<c:if test="${order.status==1}">
			   			<span data="0" d1='${order.id}'>撤销绑定</span>
		   			</c:if>
			   </td>
			</tr>
		</c:forEach>
	</tbody> 
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
</body>

