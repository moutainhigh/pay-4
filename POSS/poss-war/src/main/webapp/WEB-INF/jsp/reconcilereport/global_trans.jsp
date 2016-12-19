<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

	function queryGlobalTransactionsList(){

		/** 数值字符串  */
		var patrn = /[0-9]$/;  

		var trade_order_info_id = $("#trade_order_info_id").val();
		if ( trade_order_info_id != "" && trade_order_info_id != null && !patrn.exec(trade_order_info_id)){
			alert("订单交易号必须由数字组成！");
			return false;
		}

		var seller_id = $("#seller_id").val();
		if ( seller_id != "" && seller_id != null && !patrn.exec(seller_id)){
			alert("商家会员号必须由数字组成！");
			return false;
		}


		
		document.form1.action = "reconcile.report.do?method=queryGlobalTransactionsList";
		document.form1.submit();
	}

	
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">行业卡交易查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="form1"  action="">

	<table width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr>
			<td align="right" width="40%">时间：</td>
			<td>
				<input class="Wdate" type="text" id="startDate" name="startDate" value="${ startDate }" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})"> -
 				<input class="Wdate" type="text" id="endDate" name="endDate" value="${ endDate }" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})">
			</td>
		</tr>
		
		<tr>
			<td align="right">交易类型：</td>
			<td>
				<select id="charge_type" name="charge_type" style="width:132px;">
					<option value="">--请选择--</option>
					<option value="0">充值</option>
					<option value="1">支付</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">卡号：</td>
			<td>
				<input type="text" id="card_no" name="card_no" value="${ card_no }" />
			</td>
		</tr>
		<tr>
			<td align="right">卡密：</td>
			<td>
				<input type="text" id="card_pass" name="card_pass" value="${ card_pass }" />
			</td>
		</tr>
		<tr>
			<td align="right">商户订单号：</td>
			<td>
				<input type="text" id="order_id" name="order_id" value="${ order_id }" />
			</td>
		</tr>
		<tr>
			<td align="right">订单交易号：</td>
			<td>
				<input type="text" id="trade_order_info_id" name="trade_order_info_id" value="${ trade_order_info_id }" />
			</td>
		</tr>
		<tr>
			<td align="right">商家会员号：</td>
			<td>
				<input type="text" id="seller_id" name="seller_id" value="${ seller_id }" />
			</td>
		</tr>
		<tr>
			<td align="right">渠道订单号：</td>
			<td>
				<input type="text" id="bank_order_id" name="bank_order_id" value="${ bank_order_id }" />
			</td>
		</tr>
		<tr>
			<td align="right">交易渠道：</td>
			<td>
				<select id="trans_channel" name="trans_channel" style="width:132px;" >
					<option value="">--请选择--</option>
					<c:forEach var="c" items="${ cardRuleList }">
						<option value="${ c.ID }">${ c.CARD_NAME }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">交易状态：</td>
			<td>
				<select id="transStatus" name="transStatus" style="width:132px;" >
					<option value="">--请选择--</option>
					<option value="1">成功</option>
					<option value="2">失败</option>
					<option value="3">进行中</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				
			</td>
			<td>
				<input type="button" id="selectBtn" name="selectBtn" value="查 询" style="width:60px;" onclick="queryGlobalTransactionsList()">
			</td>
		</tr>
	</table>
  
	<c:if test="${not empty msg }"> 
		<li style="color: red"><c:out value="${msg}" /> </li>
	</c:if>

</form>
