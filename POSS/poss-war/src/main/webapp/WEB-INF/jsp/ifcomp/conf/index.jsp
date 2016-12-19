<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<head>
	<script type="text/javascript">
		function searchBusiness(pageNo,totalCount,pageSize) {
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#SearchForm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/if_comp/if_config_action.do?method=doQuery",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
		$("#SearchForm").click = function(){
			var url = "${ctx}/if_comp/if_config_action.do?method=gotoAdd&opp=add";
			parent.addMenu("新增配置信息",url);
		}
		/*$(function(){searchBusiness();});*/
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
						<font class="titletext">动 态 参 数 配 置 信 息</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		
		<form id="SearchForm">
		<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td align="right" class="border_top_right4">组GROUP：</td>
				<td class="border_top_right4">
					<input type="text" name="queryGroupCode" style="width: 200px;" />
				</td>
				<td align="right" class="border_top_right4">键KEY：</td>
				<td class="border_top_right4">
					<input type="text" name="queryKey" style="width: 200px;" />
				</td>
				<td align="right" class="border_top_right4">状态：</td>
				<td class="border_top_right4">
					<select name="queryState">
					    <option value="1" selected>有效</option>
						<option value="0">无效</option>
					</select>
				</td>
				<td align="center" class="border_top_right4" colspan="4">
		      		<input type="button" onclick="searchBusiness();" name="submitBtn" value="查  询" class="button2">
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

