<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">运单审核</h2>
<form action="" id="checkStatusForm">
	<input name="id" id="id" type='hidden'/>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4" width="50%" align="right">交 易 号:</td>
			<td class="border_top_right4" width=""><input name="tradeOrderNo" id="tradeOrderNo" readonly value="${tradeOrderNo }"/>
				<input name="orderId" id ="orderId" type="hidden" readonly value="${orderId}">
			</td>
		</tr>
		<tr class="trForContent1"><td class="border_top_right4" align="right">运 单 号:</td>
			<td><input name="trackingNo" id="trackingNo" readonly value="${trackingNo }"/></td>
			</tr>
			<tr class="trForContent1">
			<td class="border_top_right4" align="right">快 递 公 司:</td>
			<td>
			<input name="expressCom" id="expressCom" readonly value="${expressCom }"/>
	      	</td>
	      	</tr>
	     <tr class="trForContent1">
	      	<td class="border_top_right4" align="right">运单查询网址:</td>
	      	<td>
	      	<input name="queryUrl" id="queryUrl" readonly value="${queryUrl }"/>
	      	</td>
	    </tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right">审 核 原 因:</td>
			<td><textarea cols="30" rows="6" id="remark" name="remark"></textarea></td>
		</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" colspan="2" align="center">
					<input type="button"  value="审核通过"  onclick="checkStatus(2);">
					<input type="button"  value="审核不通过"  onclick="checkStatus(3);">
					<input type="button" value="返回"  onclick="javascript:window.location.href='${ctx}/expresstracking.do';">
				</td>
			</tr>
	</table>
	
	</form>
<script type="text/javascript">
function checkStatus(status) {
	
	var pars = $("#checkStatusForm").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/expresstracking.do?method=checkExpress&status=" + status,
		data: pars,
		success: function(result) {
			window.location.href='${ctx}/expresstracking.do';
		}
	});
}

</script>