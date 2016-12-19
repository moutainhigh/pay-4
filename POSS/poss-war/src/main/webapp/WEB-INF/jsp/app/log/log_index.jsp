<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>日志查询</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function query(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
				$.ajax({
					type: "GET",
					url: "${ctx}/app/querySearch.do",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			$(document).ready(function(){query();});
			
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
						<font class="titletext">日志维 护</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		
		<form id="userSearchForm">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">操作人：</td>
				<td class="textLeft"><input type="text" name="loginUser" style="width: 150px;"  /></td>
				<td class="textRight">操作类型：</td>
				<td class="textLeft">
				<select name="operationName">
					<option value="">--选择--</option>
					<c:forEach var="name" items="${operationNames}" >
						<option value="${name }">${name }</option>
					</c:forEach>
				</select>
				
				</td>
				<td class="textRight">开始时间：</td>
				<td class="textLeft"><input type="text" name="begin" style="width: 150px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
				<td class="textRight">结束时间：</td>
				<td class="textLeft">
					<input type="text" name="end" style="width: 150px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
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
		<div id="deleteRoleDiv" title="删除用户信息"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
		
