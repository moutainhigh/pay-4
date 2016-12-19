<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>

	<head>
	<title>订单查询</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function processAllQuery(pageNo,totalCount,pageSize) {
				
				var memberCode = $.trim($("#memberCode").val());
				var orderType = $("#orderType").val();
	
				if('-' == orderType){
					alert('请选择订单类型！');
					return;
				}
				
				$('#infoLoadingDiv').dialog('open');
				$("#memberCode").val($.trim($("#memberCode").val()));
				$("#orderKy").val($.trim($("#orderKy").val()));
				$("#bankOrderKy").val($.trim($("#bankOrderKy").val()));
				$("#merchantOrderKy").val($.trim($("#merchantOrderKy").val()));
				var pars = $("#orderCenterForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/fo-ordercentermanager-list.htm?method=processOrderCenter",
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
			
			function selectChange(obj){
				var comValue = obj.value;
				if(1014 == obj.value || 1010 == obj.value){
					$("#orderSrcDiv").css("display", "block");
					$("#orderSrcDiv").html("<td align='right' class='border_top_right4'>原订单号：</td><td class='border_top_right4' colspan='3'><input type='text' name='orderSrc' id='orderSrc' style='width: 150px;' /></td>")
				}else{
					$("#orderSrcDiv").css("display", "none");
					$("#orderSrcDiv").html("");
				}
				if('BATCH2ACCT' == comValue || 'BATCH2BANK' == comValue) {
					$("[data-sign='fo-batch']").show();
				}else {
					$("[data-sign='fo-batch']").hide();
				}
				
				if ('WITHDRAW' == comValue || 'CHARGE_REFUND' == comValue
						|| 'PAY_ACCT' == comValue || 'BATCH2ACCT' == comValue
						|| 'BATCH2BANK' == comValue
						|| 'FUNDOUT_REFUND' == comValue
						|| 'PAY2BANK' == comValue) {
					$("[data-sign='fo-single']").show();
				} else {
					$("[data-sign='fo-single']").hide();
				}
			
				if ('WITHDRAW' == comValue || 'BATCH2BANK' == comValue || 'PAY2BANK' == comValue){
						$("#downLoadBtn").show();
						//$("#bankCorporateExpressDiv").css("display", "block");
						
				}else{
					//$("#bankCorporateExpressDiv").css("display", "none");
					$("#downLoadBtn").hide();
				}
			
				if ('ACCT_CHARGE' == comValue || 'GATEWAY_PAYMENT' == comValue || 'GATEWAY_REFUND' == comValue){
					$("#bankOrderKyTitle").html("充值协议号");
				}else{
					$("#bankOrderKyTitle").html("银行订单号");
				}
			}
			
			function downLoad(){
				document.orderCenterForm.action = "fo-ordercentermanager-list.htm?method=exportExcel";
				document.orderCenterForm.submit();
			}
			
			$(function(){
				var orderType = $("#orderType").val();
				if ('ACCT_CHARGE' == orderType || 'GATEWAY_PAYMENT' == orderType || 'GATEWAY_REFUND' == orderType){
					$("#bankOrderKyTitle").html("充值协议号");
				}else{
					$("#bankOrderKyTitle").html("银行订单号");
				}
			});
		</script>
	</head>
	
	<body>	
	
		<h2 class="panel_title">订单查询</h2>
		<form id="orderCenterForm" name="orderCenterForm"  method="post" action="">
			<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">会员号:</td>
					<td class="border_top_right4">
						<input type="text" name="memberCode" id="memberCode" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">订单类型：</td>
					<td class="border_top_right4">
						<li:select name="orderType" otherAttribute="onchange='selectChange(this)'" itemList="${orderStatus}" selected="${orderType}"/>
					</td>
				</tr>
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">系统交易号：</td>
					<td class="border_top_right4">
						<input type="text" name="orderKy" id="orderKy" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">订单状态：</td>
					<td class="border_top_right4">
						<li:select name="orderStatus" itemList="${tradeStatus}"/>
					</td>
				</tr>
				<tr class="trForContent1">
					<td align="right" class="border_top_right4" id="bankOrderKyTitle">银行订单号：</td>
					<td class="border_top_right4">
						<input type="text" name="bankOrderKy" id="bankOrderKy" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">商家订单号：</td>
					<td class="border_top_right4">
						<input type="text" name="merchantOrderKy" id="merchantOrderKy" style="width: 150px;" />
					</td>
				</tr>
				<tr class="trForContent1" data-sign="fo-batch" style="display: none;">
					<td align="right" class="border_top_right4">业务批次号：</td>
					<td class="border_top_right4">
						<input type="text" name="batchNo" id="batchNo" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">总订单号：</td>
					<td class="border_top_right4">
						<input type="text" name="primaryOrderNo" id="primaryOrderNo" style="width: 150px;" />
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
			      	<td align="right" class="border_top_right4"><div data-sign="fo-single" style="display: none;">会员登陆标志：</div></td>
					<td class="border_top_right4" >
						<div data-sign="fo-single" style="display: none;"><input type="text" name="loginName" id="loginName" style="width: 150px;" /></div>
					</td>
			     </tr>
			     <tr class="trForContent1">
			     	<td align="right" class='border_top_right4'>出款渠道：</td>
					<td class='border_top_right4' colspan="3">
						<select name="channelId" id="channelId">
							<option value="">全部</option>
							<c:forEach items="${fundoutChannelList}" var="s">
								<c:if test="${s.modeCode == 0}">
									<option value="${s.code}">${s.channelName}</option>
								</c:if>
								<c:if test="${s.modeCode != 0}">
									<option value="">${s.channelName}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
			     </tr>
			     <tr class="trForContent1">
					<td align="center" class="border_top_right4" colspan="4">
			      		<input type="button" onclick="processAllQuery();" name="submitBtn" value="查询" class="button2">
						<input type="button" onclick="downLoad()" id="downLoadBtn" value="下载" style="display: none;" class="button2">
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