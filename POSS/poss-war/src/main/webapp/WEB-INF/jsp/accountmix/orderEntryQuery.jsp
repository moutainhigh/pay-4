<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>订单分录查询</title>
<script type="text/javascript">

		function isEmpty(fData)
		{
		    return ((fData==null) || (fData.length==0) )
		} 
	
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
	
		function userQuery(pageNo,totalCount,pageSize){
			var orderId = document.getElementById("orderId").value;
			if(isEmpty(orderId)){
				alert("订单号不能为空.");
				return false;
			}
			if(!isNumber(orderId)){
				alert("订单号只能为数字.");
				return false;
			}
            //alert('功能未实现');
            //return ;
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/if_poss_query/orderEntryQuery.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});	       
        }
		        
</script>

</head>
<form id="process" action="">
<input type = 'hidden'  name="orderSeqId" value="" />
<input type = 'hidden'  name="rsIds" id="rsIds" value="" />
</form>

<body>
<h2 class="panel_title">订单分录查询</h2>

<form method="POST" id="form1" name="form1" >
<input type="hidden" name="code" value="">
<input type="hidden" name="isRequestDate" value="1"/>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
   <tr class="trForContent1">
   <td align="center" class="border_top_right4" >订单号：
   <input type="text" name="orderId" class="button2" id="orderId" value=""/>
   <input type="button" name="query" class="button2" id="query"  value="查询" onClick="userQuery()"/>
   </td>
  </tr>

</table>
  </td>
</table>
</p>
</form>


<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>
