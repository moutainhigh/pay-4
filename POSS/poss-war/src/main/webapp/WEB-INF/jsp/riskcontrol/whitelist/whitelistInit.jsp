<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<html>
<head>
	<title>白名单管理</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function whiteListQuery(pageNo,totalCount) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#whiteListForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
				$.ajax({
					type: "POST",
					url: "${ctx}/rm-blackandwhite-whitelist.htm?method=searchWhiteList",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			$(function(){whiteListQuery();});

			function whiteListAdd(){
				window.location = "${ctx}/rm-blackandwhite-whitelist.htm?method=whitelistForm";
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
							<font class="titletext">白名单维护</font>
						</div>
					</td>
				</tr>
				<tr>
					<td height="1" bgcolor="#000000"></td>
				</tr>
	</table>
	
	<form id="whiteListForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td align="right" class="border_top_right4">白名单ID:</td>
				<td class="border_top_right4">
					<input type="text" name="whiteId" style="width: 150px;" />
				</td>
				<td align="right" class="border_top_right4">公司全称：</td>
				<td class="border_top_right4">
					<input type="text" name="businessName" style="width: 150px;" />
				</td>
				<td align="right" class="border_top_right4">真实姓名：</td>
				<td class="border_top_right4">
					<input type="text" name="trueName" style="width: 150px;" />
				</td>
				<td class="textRight">
					<input type="button" onclick="whiteListQuery();" name="submitBtn" value="查  询" class="button2">
			      	<input type="button" onclick="whiteListAdd();" name="submitBtn" value="添加白名单" class="button2">
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