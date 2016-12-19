<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
﻿<title></title>
</head>

<script>

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



function userQuery(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#queryForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/if_poss_datamanage/managePricingStrategy.do?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#paginationResult').html(result);
		}
	});
}


/*网页转向*/
function $U(url){
	window.location.href=url;
}


$(document).ready(function(){userQuery();});




</script>


<body>

<h2 class="panel_title">管理价格策略 &gt;&gt; 查询</h2>
<form method="post" action="${ctx}/if_poss_datamanage/managePricingStrategy.do?method=query" name="queryForm" id="queryForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
   
    <tr class="trForContent1">
      <td class="border_top_right4">
      
      
      <div align="center">支付服务代码：
    	   <strong>
    	   			${paymentServiceDto.paymentservicecode}
        			<input type="hidden" name="paymentservicecode" value="${paymentServiceDto.paymentservicecode}"    />
      	  　</strong>
        	  
        	  支付服务名称：
        	<strong>
        			${paymentServiceDto.paymentservicename}
          			<input type="hidden"  name="paymentservicename" value="${paymentServiceDto.paymentservicename}"   /> 
          </strong>
      </div>
      
		  	

      
      </td>
      
    </tr>
  </table>
  </form>
  
  
  <div id="paginationResult"></div>
  
  
  <div align="center">
    <input type="button"  value="新建" onclick="$U('${ctx}/if_poss_datamanage/addPricingStrategy.do?paymentservicecode=${paymentServiceDto.paymentservicecode}')" />
    　
    <input type="button" value="删除" onclick=" deleteAll('${ctx}/if_poss_datamanage/managePricingStrategy.do?method=delete') "/>
   　  
    <input type="button"  value="返回" onClick="$U('${ctx}/if_poss_datamanage/psmanage.do')" />
  </div>
  
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