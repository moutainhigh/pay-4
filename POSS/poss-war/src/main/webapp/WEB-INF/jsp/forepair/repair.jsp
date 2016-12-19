<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#queryBtn").click(function(){
			if($("#type").val()==""){
				alert("请选择订单类型");
				return;
			}
			if($("#orderId").val()==""){
				alert("请输入订单流水");
				return;
			}
			var pars = $("#mainfromId").serialize();
			$.ajax({
				type: "POST",
				url: "forepair.htm?method=queryOrder",
				data: pars,
				dataType: 'json',
				success: function(result) {
					showData(result);
				}
			});
		});
		//补单
		$("#repairOrder").click(function(){
			$.ajax({
				type: "POST",
				url : "forepair.htm?method=repairOrder&orderId="
								+ $(this).attr("d-orderId") + "&type="
								+ $(this).attr("d-type") + "&workOrderId=" + $(this).attr("d-worderId"),
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
	var typeIndex = new Array("0:提现", "1:付款到账户", "2:批付到账户", "3:付款到银行",
				"4:批付到银行");
		var wStatusIndex = new Array("0：工单初始", "1：审核通过", "2：审核拒绝", "3：审核滞留",
				"4：复核通过", "5：复核拒绝", "6：清算拒绝", "7：工单生成批次成功 ", "8：人工初审成功 ",
				"9：人工初审失败 ", "10：工单失败 ", "11：完成 ", "12：工单确认成功", "13：直连申请成功 ",
				"14：直连失败", "15:直联审核确认失败");
		//订单
		var order = result[0];
		var worder = result[1];
		var corder = result[2];
		if (order == null) {
			alert('无此订单');
			return;
		}
		$("#sequenceId").text(String(order.orderId));
		$("#orderType").text(typeIndex[parseInt(order.orderType)]);
		$("#orderSmallType").text(getSmallType(order.orderSmallType));
		$("#orderAmount").text(
				(parseFloat(order.orderAmount) * 0.001).toFixed(2));
		$("#orderStatus").text(getStatus(order.orderStatus));
		$("#payerMemberCode").text(order.payerMemberCode);
		$("#payeeBankAcctCode").text(order.payeeBankAcctCode);
		$("#payeeOpeningBankName").text(order.payeeOpeningBankName);
		$("#createDate").text(formatDate(order.createDate));
		//工单
		$("#status").text(wStatusIndex[parseInt(worder.status)]);
		//渠道
		if (corder != null) {
			$("#CorderId").text(corder.orderId);
			$("#CtradeOrderId").text(corder.tradeOrderId);
			$("#CbankSequenceId").text(corder.bankSequenceId);
			$("#Camount").text((parseFloat(corder.amount) * 0.001).toFixed(2));
			$("#CorderStatus").text(getStatus(corder.orderStatus));
			$("#CcreateDate").text(formatDate(corder.createDate));
			$("#channel").show();
		} else {
			$("#channel").hide();
		}

		//展示结果
		$("#orderInfos").slideUp("fast").slideDown("fast");

		if (order.orderStatus == 101 && corder.orderStatus == 111) {
			$("#repairOrder").removeAttr("disabled");
			$("#repairOrder").attr("d-orderId", order.orderId);
			$("#repairOrder").attr("d-type", $("#type").val());
		} else {
			$("#repairOrder").attr("disabled", "disabled");
		}
	}
	function formatDate(date) {
		date = new Date(parseFloat(date));
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		var time = year + '-' + month + '-' + day + ' '
				+ date.toLocaleTimeString();
		return time;
	}
	
	function getStatus(status) {
		var r = status;
		if(status=="100") {
			r="100：初始状态";
		}else if(status=="101") {
			r="101：处理中";
		}else if(status=="102") {
			r="102：申请失败";
		}else if(status=="111") {
			r="111：处理成功";
		}else if(status=="112") {
			r="112：处理失败";
		}else if(status=="113") {
			r="113：已退票";
		}
		return r;
	}
	function getSmallType(status) {
		var r = status;
		if(status=="001") {
			r="001-普通提现";
		}else if(status=="002") {
			r="002-委托提现";
		}else if(status=="003") {
			r="003-强制提现";
		}else if(status=="004") {
			r="004-BSP提现";
		}else if(status=="101") {
			r="101-付款到账户";
		}else if(status=="102") {
			r="102-账户代扣";
		}else if(status=="103") {
			r="103-API批付到账户";
		}else if(status=="104") {
			r="104-API付款到账户";
		}else if(status=="201") {
			r="201-批付到账户";
		}else if(status=="301") {
			r="301-付款到银行";
		}else if(status=="302") {
			r="302-API付款到银行";
		}else if(status=="401") {
			r="401-批付到银行";
		}else if(status=="402") {
			r="402-自动批付到银行";
		}else if(status=="403") {
			r="403-批付到银行";
		}else if(status=="501") {
			r="501-资金调拨";
		}
		return r;
	}
</script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">出款补单</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId" name="mainfrom">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >出款业务：</td>
	      <td class="border_top_right4">
	      	<select name="type" id="type">
	      		<option value="">请选择</option>
	      		<option value="1" selected="selected">银企直联</option>
	      	</select>
	      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >订单号：</td>
		      <td class="border_top_right4" >
		      	<input type="text" id="orderId" name="orderId" value=""/>
		      </td>
		 </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4"></td>
	      <td class="border_top_right4">
	      <input type="button" id="queryBtn"  name="queryBtn" value="查  询" class="button2">
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
					<th>订单类型</th>
					<th>子订单类型</th>
					<th>订单金额（元）</th> 
					<th>订单状态</th>
					<th>工单状态</th>
					<th>付款会员号</th>
					<th>收款银行账号</th>
					<th>收款银行</th>
					<th>订单时间</th>
				</tr> 
			</thead>
			<tbody>
				<tr>
					<td id="sequenceId"></td>
					<td id="orderType"></td>
					<td id="orderSmallType"></td>
					<td id="orderAmount"></td>
					<td id="orderStatus"></td>
					<td id="status"></td>
					<td id="payerMemberCode"></td>
					<td id="payeeBankAcctCode"></td>
					<td id="payeeOpeningBankName"></td>
					<td id="createDate"></td>
				</tr>
			</tbody> 
		</table>
		<div id="channel">
			<div class="titletext">银企直联订单</div>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead> 
				<tr>
					<th>交易流水号</th>
					<th>订单流水号</th>
					<th>银行流水号</th>
					<th>订单金额（元）</th> 
					<th>订单状态</th>
					<th>订单时间</th>
				</tr> 
			</thead>
			<tbody>
				<tr>
					<td id="CorderId"></td>
					<td id="CtradeOrderId"></td>
					<td id="CbankSequenceId"></td>
					<td id="Camount"></td>
					<td id="CorderStatus"></td>
					<td id="CcreateDate"></td>
				</tr>
			</tbody> 
		</table>
		</div>
	</div>
	<div id="newOrder">
		<button id="repairOrder" d-orderId="" d-type="" d-worderId="" disabled="disabled">补  单</button>
	</div>
</div>