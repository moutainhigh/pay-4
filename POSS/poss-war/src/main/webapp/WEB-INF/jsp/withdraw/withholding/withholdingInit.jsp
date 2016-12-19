<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		function query(pageNo,totalCount,pageSize) {
			$('#infoLoadingDiv').dialog('open');
			var parsForm = $("#typeSearchForm").serialize();
			var parsPage = "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/withholding.htm?method=queryWithholdingSummary",
				data: parsForm + parsPage,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
	</script>
</head>

<body>
	   <form id="typeSearchForm">
		<table class="border_all2">
			<tr class="trForContent1">
				<td class="textRight">开始时间：</td>
				<td class="textLeft"><input class="Wdate" type="text" name="startTime"  id="startTime"  value=''  onClick="WdatePicker()"></td>
				<td class="textRight">结束时间：</td>
				<td class="textLeft"><input class="Wdate" type="text" name="endTime"  id="endTime"  value='' onClick="WdatePicker()"></td>
				<td class="textRight">批次号：</td>
				<td class="textLeft"><input type="text" name="batchNo"  id="batchNo"  value=''  ></td>
				<td class="textRight">支付状态：</td>
				<td class="textLeft">
					<select name="status" id="status">
						<option value="">全部</option>
						<option value="1">处理中</option>
						<option value="2">处理完成</option>
					</select>
				</td>
				<td class="textRight">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
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

