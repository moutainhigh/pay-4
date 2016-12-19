<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>

<head>
﻿<title></title>
</head>

<script>

/*网页转向*/
function $U(url){
	window.location.href=url;
}

</script>
<script>
var COACheckState = true;
function selectAll(rsId) {
	setCheckBoxValue(rsId,COACheckState);
	COACheckState = !COACheckState;
}


var COACheckState = true;
function selectAll(rsId) {
	setCheckBoxValue(rsId,COACheckState);
	COACheckState = !COACheckState;
}
function getCheckBoxValues(name){	
	var values = new Array();
	var cbs = document.getElementsByName(name);
	var i;   
	if (null == cbs) return values;	  
	if (null == cbs.length){
	  	if(cbs.checked) {
	  		values[values.length] = cbs.value;
	  	}
	  	return values;
	}	    
	var count = 0 ;  	
	for(i = 0; i<cbs.length; i++){
		if(cbs[i].checked){
			values[values.length] = cbs[i].value;
		}
	}
	return values;
}
function setCheckBoxValue(name,value){
	var cbs = document.getElementsByName(name);
	var i;
    if (null == cbs) return 0 ;
  	if (null == cbs.length){
  		cbs.checked = value;
  		return 0;
  	}
	for(i=0;i<cbs.length;i++){
  		cbs[i].checked = value;
  	}
  	return 0;
}

function deleteAll(url){
	var obj = getCheckBoxValues("rsId");
	if(obj.length == 0){
		alert("请选择需要删除的记录");
		return false;
	}
	if(!confirm('确定删除?')){return false;};
	var codes = '';
	for(var i=0; i<obj.length; i++) {
		codes = codes + '&code=' + obj[i];
	}
	//alert(url+codes);
	$U(url+codes);
}


var detailSize = 0 ;
function addDetail(url){
//	alert(detailSize);
	
//	if(detailSize>0){
//      alert('已存在明细,不允许在增加 !');
 //     return ;
//	}
	$U(url);
}



function userQuery(pageNo,totalCount) {
  	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#queryForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/if_poss_datamanage/managePricingStrategyDetail.do?method=query&status=${pricingStrategyDto.status}&pricingstrategycode=${pricingStrategyDto.priceStrategyCode}",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#paginationResult').html(result);
		}
	});
	//pricingstrategy/managepricingstrategydetailview.htm?method=query&status=${pricingStrategyDto.status}
}


$(document).ready(function(){userQuery();});


</script>

<body>
<form method="post" action="${ctx}/if_poss_datamanage/managePricingStrategyDetail.do?method=query&status=${pricingStrategyDto.status}&pricingstrategycode=${pricingStrategyDto.priceStrategyCode}" 
name="queryForm" id="queryForm">

<h2 class="panel_title">管理支付服务 &gt;&gt;设置价格策略明细</h2>
   <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
  <tr class="trForContent1">
    <td class="border_top_right4" width="15%" height="27" align="right">支付服务代码：</td>
    <td class="border_top_right4" width="31%" align="left">${paymentServiceDto.paymentservicecode}</td>
    <td class="border_top_right4" width="18%" align="right">支付服务名称：</td>
    <td class="border_top_right4" width="36%" align="left">${paymentServiceDto.paymentservicename}</td>
  </tr>
  
  <tr class="trForContent1">
    <td class="border_top_right4" align="right">价格策略类型：</td>
    <td class="border_top_right4" align="left" >${pricingStrategyDto.priceStrategyTypeDesc}</td>
    <td class="border_top_right4" align="right" >计算方式：</td>
    <td class="border_top_right4" align="left" >${pricingStrategyDto.caculateMethodDesc}</td>
  </tr>
  <tr class="trForContent1">
    <td class="border_top_right4" align="right">生效范围：</td>
    <td class="border_top_right4" align="left" >${pricingStrategyDto.effectiveOnDesc}</td>
    <td class="border_top_right4" align="right" >服务等级：</td>
    <td class="border_top_right4" align="left" >${pricingStrategyDto.serviceLevelDesc}</td>
  </tr>
 <tr class="trForContent1">
    <td class="border_top_right4" align="right">会员：</td>
    <td class="border_top_right4" align="left" >${pricingStrategyDto.memberCode}</td>
    <td class="border_top_right4" align="right" >生效日期：</td>
    <td class="border_top_right4" align="left" ><fmt:formatDate value="${pricingStrategyDto.validDate}" type="date" pattern="yyyy-MM-dd"/></td>
   
  </tr>
  <tr class="trForContent1">
    <td class="border_top_right4" align="right">失效日期：</td>
    <td class="border_top_right4" align="left" >
    	<c:set var="timelost"><fmt:formatDate value="${pricingStrategyDto.invalidDate}" pattern="yyyy-MM-dd" /></c:set>
    	<c:if test='${ timelost != "1900-01-01"}'><fmt:formatDate value="${pricingStrategyDto.invalidDate}" type="date" pattern="yyyy-MM-dd"/></c:if>
    
    		  
    </td>
    
	
    <td class="border_top_right4" align="right" >&nbsp;</td>
    
    <td class="border_top_right4" align="left" >
  	&nbsp;
						
    </td>
  </tr>
  
</table>

<div id="paginationResult"></div>


<div id="content_4">
    <div align="center"><input  type="hidden" name="pricestrategycode" value="${pricingStrategyDto.priceStrategyCode}"/>
      <input name="button2" class="button2" type="button"  value="增加" 
      onclick="addDetail('${ctx}/if_poss_datamanage/addPricingStrategyDetail.do?pricestrategycode=${pricingStrategyDto.priceStrategyCode}')"/>
      　
      <input name="button2" class="button2" type="button"  value="删除"
      onClick="deleteAll('${ctx}/if_poss_datamanage/managePricingStrategyDetail.do?method=deldetail')"/>   
     　  
     <input name="button22" class="button2" type="button"  value="取消" onClick="$U('${ctx}/if_poss_datamanage/managePricingStrategy.do?paymentservicecode=${paymentServiceDto.paymentservicecode}') "/>
    </div>
</div>

</form>


<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
  
  
<c:if test='${error != null}'>
  	<script>
		alert('${error}')
	</script>
</c:if>


</body>
</html>