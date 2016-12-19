<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>日历查询</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

			function query(pageNo, totalCount, pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#frm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/fundout_calendar.htm?method=search",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});		
			}

			$(document).ready(function(){
				query();

				$("#btn").click(function(){
					query();
				});
			});
		</script>
	</head>
	
	<table width="25%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
			<div align="center"><font class="titletext">日历查询</font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	
	<form id="frm" method="post" action="#">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">
					日期：
				</td>
				<td class=border_top_right4>
					<input class="Wdate" type="text" id="dateStr" name="dateStr" style="width: 150px;" onclick="WdatePicker()" />
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">
					周几：
				</td>
				<td class=border_top_right4>
					<select name="cdateWeekdy" id="cdateWeekdy">
						<option value="">不选</option>
						<option value="1">一</option>
						<option value="2">二</option>
						<option value="3">三</option>
						<option value="4">四</option>
						<option value="5">五</option>
						<option value="6">六</option>
						<option value="7">七</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">
					是否假日：
				</td>
				<td class=border_top_right4>
					<select name="cdateHolidy" id="cdateHolidy">
						<option value="">不选</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 colspan="2" align="center">
					<input style="width: 100px;" type="button" value="查询" id="btn"/>
				</td>
			</tr>
		</table>
	</form>
	<div id="resultListDiv" class="listFence"></div>
	<div id="deleteRoleDiv" title="删除用户信息"></div>
	<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
		请稍候...
	</div>
</html>