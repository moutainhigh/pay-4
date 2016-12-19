<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>


<script type="text/javascript">
function dcclist(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#DCCForm").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "./dcc_configuration.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function detailsDCC(partnerId,partnerName,currencyCode,markUp){
		$("#markUp").attr("value",markUp);
		$("#partnerId").attr("value",partnerId);
		$("#partnerName").attr("value",partnerName);
		$("#currencyCode").attr("value",currencyCode);
		$("#method").attr("value","detailsDCC");
		$("#dccConf").submit();
}
function updateDCC(partnerId,partnerName,currencyCode,markUp,id){
		$("#markUp").attr("value",markUp);
		$("#partnerId").attr("value",partnerId);
		$("#partnerName").attr("value",partnerName);
		$("#currencyCode").attr("value",currencyCode);
		$("#id").attr("value",id);
		$("#method").attr("value","updateDCC");
		$("#dccConf").submit();
}
function deleteDCC(id){ 
	   if (confirm("确认要删除？")) {
		   window.location.href = "${ctx}/dcc_configuration.do?method=deleteDcc&id="+id;
       }	
}
</script>
		 <form id="dccConf" action="dcc_configuration.do" method="POST">
 			<input type="hidden" name="partnerId" id="partnerId">
 			<input type="hidden" name="partnerName" id="partnerName">
 			<input type="hidden" name="currencyCode" id="currencyCode">
 			<input type="hidden" name="markUp" id="markUp">
 			<input type="hidden" name="id" id="id">
 			<input type="hidden" name="method" id="method" >
 		</form>
<body>
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead>
		<tr class="">
			<th align="center" class="">会员号</th>
			<th align="center" class="">商户名称</th>
			<th align="center" class="">货币代码</th>
			<th align="center" class="">创建时间</th>
			<th align="center" class="">更新时间</th>
			<th align="center" class="">markup比率(%)</th>
			<th align="center" class="">操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="dcc" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td align="center" class="">${dcc.partnerId}</td>
			<td align="center" class="">${dcc.partnerName}</td>
			<td align="center" class="">${dcc.currencyCode}</td>
			<td align="center" class=""><fmt:formatDate value="${dcc.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
			<td align="center" class=""><fmt:formatDate value="${dcc.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
			<td align="center" class="">${dcc.markUp}</td>
			<td align="center" class="">
		<a onclick="deleteDCC('${dcc.id}');" href="#" >删除</a>	
	&nbsp; 
		<a onclick="updateDCC('${dcc.partnerId}','${dcc.partnerName}','${dcc.currencyCode}','${dcc.markUp}','${dcc.id}')"   href="#">修改</a>
		<a href="./dcc_configuration.do?method=bulkEditingDCC&partnerId=${dcc.partnerId}">批量修改</a>
			</td>
		</tr>
		</c:forEach>
		</tbody>
	</table><br><br><br><br>
	<table align="center">	<tr>
			<td colspan="13" align="center"><li:pagination methodName="dcclist" pageBean="${page}" sytleName="black2"/></td>
		</tr>
		</table>