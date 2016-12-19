<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(document).ready(function(){merchantQuery();});
function merchantQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#prstgytemListFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize + "&priceStrategyCode=${priceStrategyCode}" +"&pricestrategydetailcode=${pricestrategydetailcode}";
	
	$.ajax({
		type: "POST",
		url: "${ctx}/if_poss_datamanage/prstgytemList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function processAdd(id){
	location.href = "${ctx}/if_poss_datamanage/prstgytemAdd.do?priceStrategyCode=${priceStrategyCode}&pricestrategydetailcode=${pricestrategydetailcode}";
}
function processRet(){
	if(isEmpty(${pricestrategydetailcode})){//add
		location.href = "${ctx}/if_poss_datamanage/addPricingStrategyDetail.do?pricestrategycode=${priceStrategyCode}";
	}else{//edit
		location.href = "${ctx}/if_poss_datamanage/changePricingStrategyDetail.do?pricestrategydetailcode=${pricestrategydetailcode}";
	}
}
function isEmpty(fData)
{
    return ((fData==null) || (fData.length==0) )
} 
</script>
</head>
<body>

<h2 class="panel_title">价 格 策 略 模 板&gt;&gt;查詢 </h2>

<form id="prstgytemListFormBean" name="prstgytemListFormBean" >
<!-- 
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="left" >模板名稱：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="templateName" name="templateName"></td>
		<td align="right" class="border_top_right4" >
		      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="validFrom();">
		</td>
	</tr>
</table>
 -->
 
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<input name="button2"  class="button2" type="button"  value="添加策略" onclick="processAdd()" />
		<input name="button2"  class="button2" type="button"  value="返 回" onclick="processRet()" />
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif"/>  信息加载中, 请稍候...
		</div>
	</form>
</body>

