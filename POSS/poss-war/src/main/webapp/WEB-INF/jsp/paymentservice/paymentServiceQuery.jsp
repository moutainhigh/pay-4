<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
﻿<title>支付服务管理</title>

<script>
function deleteAll(url){
	var obj = getCheckBoxValues("rsId");
	if(obj.length == 0){
		alert("请选择需要删除的记录");
		return false;
	}
	if(!confirm('确定删除?')){return false;};
	var codes = '';
	for(var i=0;i<obj.length;i++) {
		codes = codes + '&code=' + obj[i];
	}
	
	$U(url+codes);
}
</script> 

<script>
function isNumber(sText){
	   var ValidChars = "0123456789.";
	   var IsNumber=true;
	   var Char;
	   for (i = 0; i < sText.length && IsNumber == true; i++){
	      Char = sText.charAt(i);
	      if (ValidChars.indexOf(Char) == -1){
	         IsNumber = false;
	      }
	   }
	   return IsNumber;  
	}




function deleteAll(url){
	var obj = getCheckBoxValues("rsId");
	if(obj.length == 0){
		alert("请选择需要删除的记录");
		return false;
	}
	if(!confirm('确定删除?')){return false;};
	var codes = '';
	for(var i=0;i<obj.length;i++) {
		codes = codes + '&code=' + obj[i];
	}
	
	$U(url+codes);
}
function clearText()  {
	document.getElementById("psCode").value = "";
	document.getElementById("psName").value = "";
}
function userQuery()  {
	var psCode = document.getElementById("psCode").value;
	if(!isNumber(psCode)) {
		alert('支付服务代码只能是数字');
		return false;
	}
	var pageNo = '' ;
	var totalCount= '' ;
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#queryForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/if_poss_datamanage/psmanage.do?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#paginationResult').html(result);
		}
	});
}

var paymentServiceCheckState = true;
function selectAll(rsId) {
	setCheckBoxValue(rsId,paymentServiceCheckState);
	paymentServiceCheckState = !paymentServiceCheckState;
}

/*网页转向*/
function $U(url){
	//alert(url);
	window.location.href=url;
}


$(document).ready(function(){userQuery();});

</script>
</head>
<body>
<h2 class="panel_title">管理支付服务组 &gt;&gt; 查询</h2>
<form method="post" action="${ctx}/if_poss_datamanage/psmanage.do?method=query" name="queryForm"  id="queryForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
    <tr class="trForContent1" >
     
      <td class="border_top_right4">
      
      
      <div align="center">支付服务代码：<strong>
          <input type="text" name="psCode" id=psCode  value='${queryps_psCode}' />
          　</strong>支付服务名称：<strong>
        <input type="text"  name="psName" id="psName" value='${queryps_psName}' />
        
        <input name="select" type="button"  onclick="userQuery();" value="查　询" />
        　
        <label>
         
	      <input type="button" onclick="clearText();" value="清　空" /><!-- <input type="reset" name="clear" value="清　空"> -->
        </label>
        </strong>
      </div>  
      </td>
    </tr>
  </table>
  </form>
  
  
<div align="center" id="paginationResult"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>


<p>&nbsp;</p>
  
  

</body>
</html>