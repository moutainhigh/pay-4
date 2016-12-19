<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function list(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/crosspay/siteset.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th class="item15" width="100px;">会员号</th>
			<th class="item15" width="100px;">域名</th>
			<th class="item10" width="40px;">网站状态</th>
			<th class="item10" width="40px;">操作员</th>
			<th class="item10" width="80px;">创建时间</th>
			<th class="item30" width="50px;">备注</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${websiteConfigs}" var="site" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td>${site.partnerId}</td>
			<td><a href="http://${site.url}">http://${site.url}</a></td>
			<td>
				<!--0：冻结1：正常2：待审核3：审核未通过4：已删除  --> 
				<c:if test="${site.status=='0'}">
					冻结	
				</c:if>
				<c:if test="${site.status=='1'}">
					正常
				</c:if>
				<c:if test="${site.status=='2'}">
					待审核
				</c:if>
				<c:if test="${site.status=='3'}">
					审核未通过
				</c:if>
				<c:if test="${site.status=='4'}">
					已删除	
				</c:if>					
				<c:if test="${site.status=='5'}">
					系统审核未通过	
				</c:if>					
				<c:if test="${site.status=='6'}">
					系统审核通过	
				</c:if>					
			</td>
			<td>${site.operator}</td>
			<td><date:date value="${site.createDate}"/></td>
			<td align="left">${site.remark}</td>
		</tr>
		</c:forEach>
	
	</tbody>
</table>
<li:pagination methodName="list" pageBean="${page}" sytleName="black2"/>

 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 
</script>