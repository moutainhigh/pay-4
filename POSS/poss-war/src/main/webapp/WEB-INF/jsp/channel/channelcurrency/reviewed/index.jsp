<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<%--引入用于格式化页面的CSS文件--%>
	<link rel="stylesheet" href="./css/main.css">
	<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

	<script language="javascript">
		<!--
		var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
		$(function(){
			query();
		});
		function query(pageNo) {
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#channelCurrencyForm").serialize() + "&pageNo=" + pageNo;
			$.ajax({
				type: "POST",
				url: "${ctx}/channelCurrencyReviewed.do?method=list",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}

		function reviewed(status,id,flag,channelCurrencyId){
			window.location.href="${ctx}/channelCurrencyReviewed.do?method=reviewed&status="+status+"&id="+id+"&flag="+flag+"&channelCurrencyId="+channelCurrencyId;
		}
		-->
	</script>
</head>

<body>
<h2 class="panel_title">送渠道币种配置审核</h2>
<c:if test="${not empty msg}">
	<font color="red">${msg}</font>
</c:if>
<form id="channelCurrencyForm" name="channelCurrencyForm"  action=""  method="post" enctype="multipart/form-data">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		   cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4" align="left">
				会员号：<input type="text" name="partnerId"/>
				</select>
			</td>
			<td class="border_top_right4" align="left">
				操作类型：<select id="flag" name="flag">
				<option value="" selected>---请选择---</option>
				<option value="1">新增配置</option>
				<option value="2">修改配置</option>
				<option value="3">删除配置</option>
			</select>
			</td>
			<td class="border_top_right4" align="left">
				审核状态：<select id="status" name="status">
				<option value="" selected>---请选择---</option>
				<option value="0">待审核</option>
				<option value="1">审核通过</option>
				<option value="2">审核拒绝</option>
			</select>
			</td>

		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" colspan="5" align="center">
				<a class="s03" href="javascript:query()">
				<input class="button2" type="button" value="查询"> </a>
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

