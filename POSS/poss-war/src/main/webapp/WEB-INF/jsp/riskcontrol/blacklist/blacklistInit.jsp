<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<html>
<head>
	<title>黑名单管理</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function blackListQuery(pageNo,totalCount) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#blackListForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
				$.ajax({
					type: "POST",
					url: "${ctx}/rm-blackandwhite-blacklist.htm?method=searchBlackList",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			$(function(){blackListQuery();});

			function blackListAdd(){
				window.location = "${ctx}/rm-blackandwhite-blacklist.htm?method=blackListForm";
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
								<font class="titletext">黑名单维护</font>
							</div>
						</td>
					</tr>
					<tr>
						<td height="1" bgcolor="#000000"></td>
					</tr>
		</table>
	
		<form id="blackListForm">
			<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">黑名单ID:</td>
					<td class="border_top_right4">
						<input type="text" name="blackId" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">公司全称：</td>
					<td class="border_top_right4">
						<input type="text" name="businessName" style="width: 150px;" />
					</td>
					<td align="right" class="border_top_right4">真实姓名：</td>
					<td class="border_top_right4">
						<input type="text" name="trueName" style="width: 150px;" />
					</td>
					<td align="center" class="border_top_right4">
			      		<input type="button" onclick="blackListQuery();" name="submitBtn" value="查  询" class="button2">
			      		<input type="button" onclick="blackListAdd();" name="submitBtn" value="添加黑名单" class="button2">
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