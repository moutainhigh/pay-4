<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>复核手工处理充退数据</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function checkRefundBatchQuery(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#checkprocessresultSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/refund.processresult.do?method=checkprocessresultList",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			//$(function(){checkRefundBatchQuery();});
		</script>
	</head>

	<body>
		<h2 class="panel_title">复核手工处理充退数据</h2>
		
		<form id="checkprocessresultSearchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0"
				cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">批次号：</td>
				<td class="border_top_right4" align="left">
					<input type="text" name="BATCH_NUM" style="width: 150px;" />
				</td>
				<td class=border_top_right4 align="right">状态：</td>
				<td class="border_top_right4" align="left">
					<select name="ORDER_STATUS" style="width: 150px;">
						<option value="" selected="selected">全部</option>
						<option value="101">处理中</option>
						<option value="102">成功</option>
						<option value="103">失败</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">银行名称：</td>
				<td class="border_top_right4" align="left" colspan="3">
					<li:select name="RECHARGE_BANK" defaultStyle="true" itemList="${bankList}"/>
				</td>
				
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="center" colspan="4">
					<input type="button" onclick="checkRefundBatchQuery();" name="submitBtn" value="查 询" class="button2"/>
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
		
