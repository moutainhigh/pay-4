<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>查询批次充退数据</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function refundDataQuery(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#processresultSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/refund.processresult.do?method=processresultList",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			$(function(){refundDataQuery();});
		</script>
	</head>

	<body>
		<h2 class="panel_title">查询批次充退数据</h2>
		
		<form id="processresultSearchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0"
				cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class=border_top_right4 align="right" >批次号：</td>
				<td class="border_top_right4" align="left">
					<input type="text" name="BATCH_NUM" style="width: 150px;" />
				</td>
				<td class=border_top_right4 align="right">银行名称：</td>
				<td class="border_top_right4" align="left">
					<li:select name="RECHARGE_BANK" defaultStyle="true" itemList="${bankList}"/>
				</td>
				
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">充退流水号：</td>
				<td class="border_top_right4" align="left">
					<input type="text" name="ORDER_KY" style="width: 150px;" />
				</td>
				<td class=border_top_right4 align="right">充值流水号：</td>
				<td class="border_top_right4" align="left">
					<input type="text" name="RECHARGE_ORDER_SEQ" style="width: 150px;" />
				</td>				
				
			</tr>
			<tr class="trForContent1">
			<td class=border_top_right4 align="center" colspan="4">
					<input type="button" onclick="refundDataQuery();" name="submitBtn" value="查 询" class="button2"/>
				</td>
			</tr>
		</table>
		</form>
		
		<div id="resultListDiv" class="listFence"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
		
