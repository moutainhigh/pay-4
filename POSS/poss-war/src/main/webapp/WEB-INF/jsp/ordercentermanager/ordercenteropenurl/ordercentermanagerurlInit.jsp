<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>

	<head>
	<title>订单查询</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function processAllQuery(pageNo,totalCount,pageSize) {
				if(!validate()){
					return false;
				}
				
				$('#infoLoadingDiv').dialog('open');
				$("#memberCode").val($.trim($("#memberCode").val()));
				$("#orderKy").val($.trim($("#orderKy").val()));
				$("#bankOrderKy").val($.trim($("#bankOrderKy").val()));
				$("#merchantOrderKy").val($.trim($("#merchantOrderKy").val()));
				var pars = $("#orderCenterForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/fo-ordercentermanager-list.htm?method=orderCenterManagerUrlList",
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

			function validate() {
				var memberCode = $.trim($("#memberCode").val());//付款方或收款方登录名
				var orderKy = $.trim($("#orderKy").val());//交易流水号
				var bankOrderKy = $.trim($("#bankOrderKy").val());//银行订单号
				var merchantOrderKy = $.trim($("#merchantOrderKy").val()); //商家订单号

				if(!s_isNumber(orderKy)){
					alert("交易流水号必须为数字!");
					return false;
				}
				if('' == memberCode　&& '' == orderKy && '' == bankOrderKy && '' == merchantOrderKy){
					alert("必须输入一个查询条件!");
					return false;
				}
				return true;
			}
			
			function selectChange(obj){
				if(1014 == obj.value || 1010 == obj.value){
					$("#orderSrcDiv").css("display", "block");
					$("#orderSrcDiv").html("<td align='right' class='border_top_right4'>原订单号：</td><td class='border_top_right4' colspan='3'><input type='text' name='orderSrc' id='orderSrc' style='width: 150px;' /></td>")
				}else{
					$("#orderSrcDiv").css("display", "none");
					$("#orderSrcDiv").html("");
				}
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
		
		<form id="orderCenterForm">
			<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">付款方/收款方登录名:</td>
					<td class="border_top_right4">
						<input type="text" name="memberCode" id="memberCode" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">订单类型：</td>
					<td class="border_top_right4">
						<li:select name="orderType" otherAttribute="onchange='selectChange(this)'" itemList="${orderStatus}" selected="${orderType}"/>
					</td>
				</tr>
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">交易流水号：</td>
					<td class="border_top_right4">
						<input type="text" name="orderKy" id="orderKy" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">订单状态：</td>
					<td class="border_top_right4">
						<li:select name="orderStatus" itemList="${tradeStatus}"/>
					</td>
				</tr>
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">银行订单号：</td>
					<td class="border_top_right4">
						<input type="text" name="bankOrderKy" id="bankOrderKy" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">商家订单号：</td>
					<td class="border_top_right4">
						<input type="text" name="merchantOrderKy" id="merchantOrderKy" style="width: 150px;" />
					</td>
				</tr>
				<tr class="trForContent1" id="orderSrcDiv" style="display: none;"></tr>
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">订单时间：</td>
					<td class="border_top_right4" colspan="3">
				      	<input class="Wdate" type="text" id="startTime" value='<fmt:formatDate value="${startTime}" type="both"/>' name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
						<input class="Wdate" type="text" id="endTime" value='<fmt:formatDate value="${endTime}" type="both"/>' name="endTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})">
			      	</td>
			     </tr>
			     <tr class="trForContent1">
					<td align="center" class="border_top_right4" colspan="4">
			      		<input type="button" onclick="processAllQuery();" name="submitBtn" value="查  询" class="button2">
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