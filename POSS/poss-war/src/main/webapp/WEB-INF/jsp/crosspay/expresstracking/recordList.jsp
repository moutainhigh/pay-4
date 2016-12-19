<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function search(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
	+ "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/expresstracking.do?method=list",
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

<table width="900px"  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th class="item15" width="4%;">
			选择/反选	<input type="checkbox" onclick="cheked()" id="check">
			</th>
			<th class="item15" width="">交易号</th>
			<th class="item15" width="">会员号</th>
			<th class="item15" width="">网站</th>
			<th class="item15" width="">订单号</th>
			<!-- <th class="item10" width="10%">交易网站</th> -->
			<th class="item10" width="">运单号</th>
			<th class="item10" width="">快递公司</th>
			<th class="item30" width="">运单查询网站</th>
			<th class="item10" width="">运单上传时间</th>
			<th class="item30" width="" nowrap="nowrap">运单状态</th>
			<th class="item10" width="">是否妥投</th>
			<th class="item30" width="">备注</th>
			<th class="item10" width="">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="express" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td>
			<c:if test="${express.status=='1'}">
			<input type="checkbox" value="${express.id}"> 
			</c:if>
			</td>
			<td>${express.tradeOrderNo}</td>
			<td>${express.partnerId}</td>
			<td>${express.siteId}</td>
			<td>${express.orderId}</td>
		<%-- 	<td>${express.tradeUrl}</td> --%>
		    <td>${express.trackingNo}</td>
		    <td>${express.expressCom}</td>
		    <td>${express.queryUrl}</td>
		    <td><%-- <fmt:formatDate value='${e}'/> --%>
		    <date:date value="${express.uploadeDate}"/>
		    </td>
			<td>
				<!--1：待审核2：审核通过  3：审核未通过--> 
				<c:if test="${express.status=='0'}">
					未上传运单号
				</c:if>
				<c:if test="${express.status=='1'}">
					待审核
				</c:if>
				<c:if test="${express.status=='2'}">
					审核通过
				</c:if>
				<c:if test="${express.status=='3'}">
					审核未通过
				</c:if>				
			</td>
			<td>
				<!--0：未妥投2：已妥投--> 
				<c:if test="${express.completeFlg=='0'}">
				未妥投
				</c:if>
				<c:if test="${express.completeFlg=='1'}">
					已妥投
				</c:if>			
			</td>
			<td align="left">${express.remark}</td>
			<td>
				<c:if test="${express.status=='1'}">
				<a href="javascript:toAudit('${express.tradeOrderNo}','${express.trackingNo}','${express.expressCom}','${express.queryUrl}','${express.orderId}');">审核</a>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>