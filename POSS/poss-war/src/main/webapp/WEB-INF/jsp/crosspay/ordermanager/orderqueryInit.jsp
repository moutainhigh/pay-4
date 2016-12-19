<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>

	<head>
	<title>订单查询</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function orderQuery(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				$("#payer").val($.trim($("#payer").val()));
				$("#tradeOrderNo").val($.trim($("#tradeOrderNo").val()));
				$("#orderId").val($.trim($("#orderId").val()));
				//$("#merchantOrderKy").val($.trim($("#merchantOrderKy").val()));
				var pars = $("#queryParamForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/crosspay/orderQuery.do?method=list",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			function showUrl(menu,Url){
			    parent.addMenu(menu,Url);
			}
			function downLoad(){
				document.queryParamForm.action = "${ctx}/crosspay/orderQuery.do?method=excelDownload";
				document.queryParamForm.submit();
			}
		</script>
	</head>
	
	<body>	
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
				<tr>
					<td height="1" bgcolor="#000000"></td>
				</tr>
				<tr>
					<td height="18">
						<div align="center">
							<font class="titletext">订单查询</font>
						</div>
					</td>
				</tr>
				<tr>
					<td height="1" bgcolor="#000000"></td>
				</tr>
		</table>
		
		<form id="queryParamForm" name="queryParamForm"  method="post" action="">
			<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">会员号:</td>
					<td class="border_top_right4">
						<input type="text" name="payer" id="payer" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">卡类型：</td>
					<td class="border_top_right4">
						<select id="cardType" name="cardType">
							<option value="all" selected="selected">全部</option>
							<option value="1">VISA</option>
							<option value="2">MASTER</option>
							<option value="3">JCB</option>
						</select>
					</td>
				</tr>
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">交易号：</td>
					<td class="border_top_right4">
						<input type="text" name="tradeOrderNo" id="tradeOrderNo" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">订单状态：</td>
					<td class="border_top_right4">
						<select id="tradeStatus" name="tradeStatus">
							<option value="all" selected="selected">全部</option>
							<option value="1">进行中</option>
							<option value="2">成功</option>
							<option value="3">拒付</option>
							<option value="4">冻结</option>
							<option value="5">退款</option>
						</select>
						<%-- <li:select name="orderStatus" itemList="${tradeStatus}"/> --%>
					</td>
				</tr>
				<tr class="trForContent1" id="orderSrcDiv" style="display: none;"></tr>
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">订单时间：</td>
					<td class="border_top_right4">
				      	<input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
						<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
			      	</td>
			      	<td align="right" class="border_top_right4">商家订单号：</td>
					<td class="border_top_right4" >
						<input type="text" name="orderId" id="orderId" style="width: 150px;" />
					</td>
			     </tr>
			     <tr class="trForContent1">
					<td align="center" class="border_top_right4" colspan="4">
			      		<input type="button" onclick="orderQuery();" name="submitBtn" value="查  询" class="button2">
						<input type="button" onclick="downLoad()" id="downLoadBtn" value="下   载" style="display: none;" class="button2">
					</td>
				</tr>
			</table>
		</form>
	
        <div id="resultListDiv" class="listFence"></div>
		<div id="deleteRoleDiv" title="删除用户信息"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>