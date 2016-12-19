<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>用户角色分配</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function userQuery(pageNo,totalCount) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
				$.ajax({
					type: "POST",
					url: "${ctx}/roleForUser.do?method=search",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			$(document).ready(function(){userQuery();});
		</script>
	</head>

	<body>
		<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr class="trForContent1">
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr class="trForContent1">
				<td height="18">
					<div align="center">
						<font class="titletext">用户角色分配</font>
					</div>
				</td>
			</tr>
			<tr class="trForContent1">
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table> -->
		<h2 class="panel_title">用户角色分配</h2>
		
		<form id="userSearchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="3" align="center">
			<tr class="trForContent1">
				<td align="right" class="border_top_right4">用户名：</td>
				<td class="border_top_right4"><input type="text" name="userId" style="width: 150px;" /></td>
				<td align="right" class="border_top_right4">用户姓名：</td>
				<td class="border_top_right4"><input type="text" name="userName" style="width: 150px;" /></td>
				<td align="right" class="border_top_right4">状态：</td>
				<td class="border_top_right4">
					<select name="status">
						<option value="1">激活</option>
						<option value="2">全部</option>
						<option value="0">禁用</option>
					</select>
				</td>
				</tr>
				<tr class="trForContent1">
				<td class="border_top_right4" align="center" colspan="6">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="userQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>
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
		
