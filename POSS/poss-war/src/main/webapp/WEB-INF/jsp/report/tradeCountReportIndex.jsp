<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>交易统计</title>
<script type="text/javascript">

		function userQuery(){
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/tradeReport/tradeCount.do?method=queryTradeCount",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }
        
</script>
</head>

<body>
<h2 class="panel_title">交易报表统计</h2>
<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">交易日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value="${dateStr}"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value="${dateStr}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
      	</td>
    	<%-- <td align="right" class="border_top_right4">会员号：</td>
      	<td class="border_top_right4">
	      	<input  type="text" id="partnerId" name="partnerId" value="${partnerId}">
      	</td> --%>
      	<td class=border_top_right4 align="center" colspan="3">
      		 <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
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
