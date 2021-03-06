<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	a:hover{text-decoration:underline;}
</style>
<h2 class="panel_title">补单审核</h2>

<form action="${ctx }/orderCompletionReq.do?method=doApplyData" method="post" id="mainfromId">

	<div style="text-align:left;padding-left:20px;">
		<font>银行机构：&nbsp;&nbsp;&nbsp;&nbsp;
			<c:forEach var="channel" items="${paymentChannelItems }" varStatus="v">
				<label id="${channel.orgCode }" style="display:none;">${channel.name }</label>
			</c:forEach>
		</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font>审核结果：&nbsp;&nbsp;&nbsp;&nbsp;
			<c:choose>
				<c:when test="${orderFillBatchDto.auditStatus == 1 }">
					审核通过
				</c:when>
				<c:when test="${orderFillBatchDto.auditStatus == 2 }">
					审核拒绝
				</c:when>
			</c:choose>
		</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font>备注：&nbsp;&nbsp;&nbsp;&nbsp;${orderFillBatchDto.remark }</font>
		
		<p>补单成功笔数：&nbsp;&nbsp;${fn:length(passSuccessList) }笔</p>
	</div>
	
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
		<thead>
			<tr>
				<th>渠道订单号</th>
				<th>机构端流水号</th>
				<th>支付金额</th>
				<th>支付币种</th>
				<th>授权码</th>
			</tr>
		</thead>
		<tbody>
			
			<c:forEach var="passSuccess" items="${passSuccessList }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><span></span>${passSuccess.channelOrderNo }<span></span></td>
				<td>
					${passSuccess.returnNo }
				</td>
				<td>${passSuccess.amount }</td>
				<td>${passSuccess.currencyCode }</td>
				<td>${passSuccess.authorization }</td>
			</tr>
			</c:forEach>
			
		</tbody>
		
	</table>
	
	<br/>
	<div style="text-align:left;padding-left:20px;">
		<p>补单失败笔数：&nbsp;&nbsp;${fn:length(passFailList) }笔</p>
	</div>
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
		<thead>
			<tr>
				<th>渠道订单号</th>
				<th>机构端流水号</th>
				<th>支付金额</th>
				<th>支付币种</th>
				<th>授权码</th>
				<th>失败原因</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="passFail" items="${passFailList }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><span></span>${passFail.channelOrderNo }<span></span></td>
				<td>
					${passFail.returnNo }
				</td>
				<td>${passFail.amount }</td>
				<td>${passFail.currencyCode }</td>
				<td>${passFail.authorization }</td>
				<td>${passFail.failReason }</td>
			</tr>
			</c:forEach>
		</tbody>
		
	</table>
	<input type="button"  name="butSubmit" value="返  回" class="button2" onclick="javascript:window.history.go(-1);">
</form>
<script type="text/javascript">
	$(function(){
		$("#" + '${orgCode}').removeAttr("style") ;
	}) ;
</script>