<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function productQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#productSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/productListForMenu.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
</head>

<body>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">产 品 信 息 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">产品信息查询</h2>

<form id="productSearchFormBean" name="productSearchFormBean" >
<input type = "hidden" id="productId" name="productId">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >产品名称：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="productName" name="productName"></td>
		<td class="border_top_right4" align="right" >产品适用类型</td>
		<td class="border_top_right4" align="left">
			<select id="allowObject" name="allowObject" size="1">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${allowObjectEnum}" var="allowObject">
				<option value="${allowObject.code}">${allowObject.description}</option>
				</c:forEach>
			</select>
		</td>
		<td class="border_top_right4" align="right" >是否默认产品</td>
		<td class="border_top_right4" align="left">
			<select id="isDefault" name="isDefault" size="1">
				<option value="" selected>---请选择---</option>		
					<option value="1">是</option>
					<option value="0">否</option>	
			</select>
		</td>			
				
	</tr>
	<tr class="trForContent1">
	<td class="border_top_right4" align="right" >产品类别</td>
		<td class="border_top_right4" align="left" colspan="10">
					<input type="radio" name="productType" value="" checked="checked" />全部
					<c:forEach items="${productTypeOrderEnum}" var="productType">
						<input type="radio" name="productType" value="${productType.code}" />${productType.desc} 
					</c:forEach>
		</td>		
		
	</tr>
	
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="10" align="center"><a
			class="s03" href="javascript:productQuery()">
			<input class="button2" type="button" value="查询"></a>
		</td>
	</tr>

</table>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
		<div id="delCheckDiv"  style="display: none;">
		请输入删除的密码：<input type="password" id="checkPassword" name="checkPassword"  value="" />
		</div>
</body>

