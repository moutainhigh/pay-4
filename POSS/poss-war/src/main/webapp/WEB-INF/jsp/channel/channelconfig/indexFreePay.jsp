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
		function query(pageNo, totalCount) {
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#paymentChannelItemFormBean").serialize()+ "&pageNo=" + pageNo
					+ "&totalCount=" + totalCount; ;
			$.ajax({
				type: "POST",
				url: "${ctx}/channelConfig.htm?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
		function del(id) {
			var pars = "id=" + id;
			var url="${ctx}/channelConfig.htm?method=findConnect";
			var url1="${ctx}/channelConfig.htm?method=pollOutFreePool";
			$.post(url,pars,function(res){
				if(1==res){
					if(confirm("该二级商户号已被关键，确认删除?")){
						$.post(url1,pars,function(res){
							if(1==res){
								alert('操作成功');
								query();
							}else{
								alert(res);
							}
						});
					}
				}else{
					$.post(url1,pars,function(res){
						if(1==res){
							alert('操作成功');
							query();
						}else{
							alert(res);
						}
					});
				}
			});



		}
		-->
	</script>
</head>

<body>
<h2 class="panel_title">通道二级商户号管理</h2>
<form id="paymentChannelItemFormBean" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		   cellpadding="1" align="center">

		<tr class="trForContent1">
			<td class="border_top_right4" align="left">
				渠道名称：<select id="orgCode" name="orgCode">
				<option value="" selected>---请选择---</option>
				<c:forEach var="channel" items="${channelItems}" varStatus="v">
					<option value="${channel.code}">${channel.desc}</option>
				</c:forEach>
			</select>
			</td>
			<td class="border_top_right4" align="left" >
				机构商户号：<input type="text" id="orgMerchantCode" name="orgMerchantCode" value="${orgMerchantCode}">
			</td>
			<td align="left" class="border_top_right4">
				币种：
				<select id="currencyCode" name="currencyCode">
					<option value="">--请选择--</option>
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v">
						<option value="${curCode.code}">${curCode.desc}</option>
					</c:forEach>
				</select>
				Credorax下必填
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="left" >
				MCC：<input type="text" id="mcc" name="mcc" value="">
			</td>
			<td class="border_top_right4" align="left">
				状态：<select id="status" name="status" size="1">
				<option value="" selected>---请选择---</option>
				<option value="1" >启用</option>
				<option value="0" >禁用</option>
			</select>
			</td>
			<td class="border_top_right4" align="left" >
				<input type="hidden" value="1" name="aucrFlag"/>
			</td>
		</tr>

		<tr class="trForContent1">
			<td class="border_top_right4" colspan="3" align="center">
				
				<input type="button" class="button2" onclick="query()" value="检索"/>
				<input type="button" class="button2" value="返回" onclick="window.history.go(-1);">
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

