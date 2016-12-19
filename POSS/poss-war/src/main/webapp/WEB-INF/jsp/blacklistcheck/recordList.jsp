<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/blacklistCheck.do?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}
function cheked(){
	 if($("#check").attr("checked")==true){
		 $("input[type='checkbox']").each(function(){
			 this.checked=true;
		 })
	 }else{
		 $("input[type='checkbox']").each(function(){
			 this.checked=false;
		 })
	 }
}
</script>
<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th class="item15" width="100px;">
				全选/全不选	<input type="checkbox" onclick="cheked()" id="check">
			</th>
			<th>会员号</th>
			<th>业务类型</th>
			<th>内容</th>
			<th>所有网关订单号</th>
			<th>状态</th>
			<th>创建时间</th>
			<th>更新时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result}" var="order" varStatus="index">
		<tr class="even" align="center">
			<td>
				<c:if test="${order.status=='0'}">
					<input type="checkbox" value=${order.id}>
				</c:if>
			</td>
			<td><span></span>${order.memberCode}<span></span></td>
			<td>
				<c:if test="${order.businessTypeId=='1'}">
					卡号
				</c:if>
				<c:if test="${order.businessTypeId=='2'}">
					邮箱
				</c:if>
				<c:if test="${order.businessTypeId=='3'}">
					IP
				</c:if>
				<c:if test="${order.businessTypeId=='8'}">
					收货地址
				</c:if>
				<c:if test="${order.businessTypeId=='9'}">
					账单地址
				</c:if>
			</td>
			<td>${order.content }</td>
			<td>${order.tradeOrderNos }</td>
			<td>
				<c:if test="${order.status=='0'}">
					未处理
				</c:if>
				<c:if test="${order.status=='1'}">
				已交付审核
				</c:if>
				<c:if test="${order.status=='2'}">
					已处理
				</c:if>
			</td>
			<td><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${order.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		</c:forEach>
		<!-- <tr>
			<td colspan="19" align="center"></td>
		</tr> -->
	</tbody>
</table>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
