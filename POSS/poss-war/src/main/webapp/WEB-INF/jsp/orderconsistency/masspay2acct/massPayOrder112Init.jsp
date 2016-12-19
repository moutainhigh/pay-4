<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<head>
	<script type="text/javascript">
		function search() {
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#SearchForm").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/masspayorder_backend_support.htm?method=searchMassPayOrder112",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
		$(function(){search();});
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
						<font class="titletext">批量付款子订单状态为112处理</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		
		<form id="SearchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td align="right" class="border_top_right4">总订单号:</td>
				<td class="border_top_right4">
					<input type="text" name="parentOrder" style="width: 150px;" />
				</td>
				<td align="right" class="border_top_right4">开始时间：</td>
				<td class="border_top_right4">
					<input class="Wdate" type="text" id="startDate"  name="startDate" value='<fmt:formatDate value="<%=new java.util.Date()%>"  pattern="yyyy-MM-dd"/>' onClick="WdatePicker()">
				</td>
				<td align="right" class="border_top_right4">结束时间：</td>
				<td class="border_top_right4">
					<input class="Wdate" type="text" id="endDate" name="endDate"  value='<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd"/>' onClick="WdatePicker()">
				</td>
				<td align="center" class="border_top_right4" colspan="4">
		      		<input type="button" onclick="search();" name="submitBtn" value="查  询" class="button2">
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

