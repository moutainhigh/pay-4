<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#queryBtn").click(function(){
			if($("#bankOrderId").val()==""){
				alert("请输入银行订单号");
				return;
			}
			var pars = $("#mainfromId").serialize();
			$.ajax({
				type: "POST",
				url: "withholdrepair.htm?method=queryOrder",
				data: pars,
				dataType: 'json',
				success: function(result) {
					showData(result);
				}
			});
		});

		//补单--成功
		$("#repairOrderSuccess").click(function(){
			$.ajax({
				type: "POST",
				url : "withholdrepair.htm?method=repairOrder&bankOrderId="
								+ $(this).attr("d-bankOrderId") + "&flag=1",
						dataType : 'json',
						success : function(result) {
							if(result == "1") {
								alert("补单完成");
								$("#queryBtn").click();
							}else {
								alert("补单失败");
							}
						}
			});
		});

		//补单--失败
		$("#repairOrderFail").click(function(){
			$.ajax({
				type: "POST",
				url : "withholdrepair.htm?method=repairOrder&bankOrderId="
								+ $(this).attr("d-bankOrderId") + "&flag=2",
						dataType : 'json',
						success : function(result) {
							if(result == "1") {
								alert("补单完成");
								$("#queryBtn").click();
							}else {
								alert("补单失败");
							}
						}
			});
		});
	});
	function showData(result) {
		var wStatusIndex = new Array("订单已受理", "等待审核", "审核成功",
				"审核失败", "已发送银行", "银行已受理", "银行拒绝", "银行扣款成功",
				"银行扣款失败", "订单处理成功", "订单处理失败", "已退款");
		//订单
		var order = result[0];
		if (order == null) {
			alert('无此订单');
			return;
		}
		$("#orderSeq").text(String(order.orderId));
		$("#strCreateDate").text(order.strCreateDate);
		$("#merchantName").text(order.merchantId);
		$("#orderStatus").text(wStatusIndex[parseInt(order.orderStatus) - 1]);
		$("#strPayerAmount").text(order.strPayerAmount);
		$("#payerBankname").text(order.payerBankname);
		$("#name").text(order.name);

		//展示结果
		$("#orderInfos").slideUp("fast").slideDown("fast");

		if (order.orderStatus == 7 || order.orderStatus == 8 || order.orderStatus == 9 || order.orderStatus == 10 || order.orderStatus == 11) {
			$("#repairOrderSuccess").attr("disabled", "disabled");
			$("#repairOrderFail").attr("disabled", "disabled");
		} else {
			$("#repairOrderSuccess").removeAttr("disabled");
			$("#repairOrderFail").removeAttr("disabled");
			$("#repairOrderSuccess").attr("d-bankOrderId", order.bankOrderid);
			$("#repairOrderFail").attr("d-bankOrderId", order.bankOrderid);
		}
	}
</script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">代扣补单</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId" name="mainfrom">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right">银行流水号：</td>
		      <td class="border_top_right4">
		      	<input type="text" id="bankOrderId" name="bankOrderId" value="" />
		      </td>
		 </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4"></td>
	      <td class="border_top_right4">
	      <input type="button" id="queryBtn"  name="queryBtn" value="查  询" class="button2" />
	      </td>
	    </tr>
	</table>
</form>

<div id="orderInfos" style="display:none;">
	<div id="originalOrder">
		<div class="titletext">订单信息</div>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead> 
				<tr>
					<th>订单流水号</th>
					<th>上传时间</th>
					<th>商户号</th>
					<th>订单状态</th> 
					<th>扣款金额</th>
					<th>扣款银行</th>
					<th>客户姓名</th>
				</tr> 
			</thead>
			<tbody>
				<tr>
					<td id="orderSeq"></td>
					<td id="strCreateDate"></td>
					<td id="merchantName"></td>
					<td id="orderStatus"></td>
					<td id="strPayerAmount"></td>
					<td id="payerBankname"></td>
					<td id="name"></td>
				</tr>
			</tbody> 
		</table>
	</div>
	<div id="newOrder">
		<button id="repairOrderSuccess" d-bankOrderId="" disabled="disabled">成  功</button>
		<button id="repairOrderFail" d-bankOrderId="" disabled="disabled">失  败</button>
	</div>
</div>