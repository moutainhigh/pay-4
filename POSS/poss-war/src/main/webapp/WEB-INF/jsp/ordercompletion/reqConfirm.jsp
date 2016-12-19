<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	a:hover{text-decoration:underline;}
</style>

<h2 class="panel_title">补单申请</h2>
<form action="${ctx }/orderCompletionReq.do?method=doApplyData" method="post" id="mainfromId">
	<input type="hidden" id="applyDataA" name=applyDataA value="${applyDataA }" />
	<input type="hidden" id="orgCode" name="orgCode" value="${orgCode }" />
	<input type="hidden" id="fileName" name="fileName" value="${fileName }"/>
	<div style="text-align:center;">
		<p>银行机构：&nbsp;&nbsp;&nbsp;&nbsp;
			<c:forEach var="channel" items="${paymentChannelItems }" varStatus="v">
				<label id="${channel.orgCode }" style="display:none;">${channel.name }</label>
			</c:forEach>
		</p>
		<p>上传成功记录：&nbsp;&nbsp;${fn:length(applyDataS) }笔</p>
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
			
			<c:forEach var="orderS" items="${applyDataS }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><span></span>${orderS.channelOrderNo }<span></span></td>
				<td>
					${orderS.returnNo }
				</td>
				<td>${orderS.amount }</td>
				<td>${orderS.currencyCode }</td>
				<td>${orderS.authorization }</td>
			</tr>
			</c:forEach>
			
		</tbody>
		
	</table>
	<br/>
	<div style="text-align:center;">
		<p>上传失败记录：&nbsp;&nbsp;${fn:length(applyDataF) }笔</p>
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
			<c:forEach var="orderF" items="${applyDataF }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><span></span>${orderF.channelOrderNo }<span></span></td>
				<td>
					${orderF.returnNo }
				</td>
				<td>${orderF.amount }</td>
				<td>${orderF.currencyCode }</td>
				<td>${orderF.authorization }</td>
				<td>${orderF.failReason }</td>
			</tr>
			</c:forEach>
		</tbody>
		
	</table>
	<input type="submit"  name="butSubmit" value="提交补单申请" class="button2" onclick="">
	<input type="button"  name="butSubmit" value="取  消" class="button2" onclick="javascript:window.location.href = '${ctx}/orderCompletionReq.do'">
</form>
<script type="text/javascript">
	$(function(){
		$("#applyDataA").val(encodeURIComponent('${applyDataA}')) ;
		$("#" + '${orgCode}').removeAttr("style") ;
	}) ;
</script>
