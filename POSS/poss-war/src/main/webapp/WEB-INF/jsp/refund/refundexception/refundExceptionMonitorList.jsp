<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function query(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/refundOrder.do?method=refundExceptionMonitorList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}

function setRefundFailFunc(additional){
	$('#setRefundToFail').dialog({ 
		position:"top",
		width:400,
		modal:true, 	
		title:'置为退款失败', 
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	} );
	$("#refundOrderNoForFail").val(additional);
}
function submitSetRefundFail(){
	$("#setRefundToFail").dialog("close");
	var id = $("#refundOrderNoForFail").val();
	var additional = $("#failReason").val();
	sendRequest("setFail",id,additional);
}

function setRefundSuccessFunc(additional){
	$('#setRefundToSuccess').dialog({ 
		position:"top",
		width:400,
		modal:true, 	
		title:'置为退款成功', 
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	} );
	$("#refundOrderNoForSuccess").val(additional);
}
function submitSetRefundSuccess(){
	$("#setRefundToSuccess").dialog("close");
	var id = $("#refundOrderNoForSuccess").val();
	var additional = $("#refundChannelSeqNo").val();
	sendRequest("setSuccess",id,additional);
}
function refundExceptionHandle(operate,id){
	var tipMsg = "";
	if(operate=="reTry"){
		tipMsg = "确认重新提交渠道进行退款？";
		var r = confirm(tipMsg);
		if(!r)return;
	}else if(operate=="manual"){
		tipMsg = "确认将该笔退款置为人工处理吗？";
		var r = confirm(tipMsg);
		if(!r)return;
	}else if(operate=="queryStatus"){
		tipMsg = "确认将该笔发起退款查询吗？";
		var r = confirm(tipMsg);
		if(!r)return;
	}
	sendRequest(operate,id,null);
}
function sendRequest(operate,id,additional){
	var data={"operate":operate,"id":id};
	$('#infoLoadingDiv').dialog('open');
	if(additional)
		data={"operate":operate,"id":id,"additional":additional};
	$.ajax({
		type: "POST",
		url: "${ctx}/refundOrder.do?method=refundExceptionHandle",
		data: data,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$("#failReason").val("");
			$("#refundChannelSeqNo").val("");
			alert(result);
			query(parseInt($(".current").html()),parseInt($(".black2>span:first").html().split("：")[1]));
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
<form id="frm" name="manyForm" method="post" action="${ctx}/refundException.htm?method=relation">
<input type="hidden" name="ids" id="ids" value=""/>
<input type="hidden" name="uuid" value="${sessionScope.uuid}"/>
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th><input type="checkbox" onclick="cheked();" id="check" /></th>
			<th>会员号</th>
			<th>商户名称</th>
			<th>退款订单号</th>
			<th>商户订单号</th>
			<th>网关订单号</th>
			<th>渠道</th>
			<th>渠道订单号</th>
			<th>退款金额</th>
			<th>退款币种</th>
			<th>退款状态</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="tb"> 
	<c:forEach items="${list}" var="info" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
			<td><input type="checkbox" value="${info.refundOrderNo }"/></td>
			<td>${info.partnerId}</td>
			<td>${info.merchantName}</td>
			<td>${info.refundOrderNo}</td>
			<td>${info.orderId}</td>
			<td>${info.tradeOrderNo}</td>
			<td>${info.channelName}</td>
			<td>${info.channelOrderNo}</td>
			<td>${info.refundAmount}</td>
			<td>${info.currencyCode}</td>
			<td>
				<c:if test="${info.status==0}">创建</c:if>
				<c:if test="${info.status==1}">退款中</c:if>
				<c:if test="${info.status==3}">机构退款失败</c:if>
				<c:if test="${info.status==4}">机构退款超时</c:if>
				<c:if test="${info.status==5}">人工处理</c:if>
				<c:if test="${info.status==6}">退款失败</c:if>
			</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd" value="${info.createDate}" type="both"/></td>
			<td>
				<c:if test="${info.status==3}">
					<a href="#" onclick="refundExceptionHandle('reTry','${info.refundOrderNo}')">重新发起</a>&nbsp;&nbsp;
					<a href="#" onclick="refundExceptionHandle('manual','${info.refundOrderNo}')">人工处理</a>
				</c:if>
				<c:if test="${info.status==4}">
					<a href="#" onclick="refundExceptionHandle('queryStatus','${info.refundOrderNo}')">查询状态</a>&nbsp;&nbsp;
					<a href="#" onclick="refundExceptionHandle('manual','${info.refundOrderNo}')">人工处理</a>
				</c:if>
				<c:if test="${info.status==5}">
					<a href="#" onclick="setRefundSuccessFunc('${info.refundOrderNo}')">置为成功</a>&nbsp;&nbsp;
					<a href="#" onclick="setRefundFailFunc('${info.refundOrderNo}')">置为失败</a>
				</c:if>
				<c:if test="${info.status==0 ||info.status==1}">
					<a href="#" onclick="refundExceptionHandle('manual','${info.refundOrderNo}')">人工处理</a>&nbsp;&nbsp;
					<a href="#" onclick="setRefundFailFunc('${info.refundOrderNo}')">置为失败</a>
				</c:if>
				<c:if test="${info.status==6}">
					<a href="#" onclick="setRefundSuccessFunc('${info.refundOrderNo}')">置为成功</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
</form>
<div id="infoLoadingDiv" title="处理信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息处理中, 请稍候...
</div>