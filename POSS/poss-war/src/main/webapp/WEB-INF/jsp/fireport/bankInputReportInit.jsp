<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 
<%@ taglib prefix="li" uri="fi" %>

	<script type="text/javascript" src="js/common.js"></script>
	<body>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center"><font class="titletext">银行入款统计报表</font></div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>

		<form action="" method="post" name="mainfrom" id="mainfromId">
  			<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
    			<tr class="trForContent1">
      				<td align="right" class="border_top_right4">选择交易日期：</td>
      				<td class="border_top_right4">
      					<input class="Wdate" type="text" name="startTime"  id="startTime"  value='<fmt:formatDate value="${webQueryFIReportDTO.startTime}" type="date"/>'  onClick="WdatePicker()">
        					～
						<input class="Wdate" type="text" name="endTime"  id="endTime"  value='<fmt:formatDate value="${webQueryFIReportDTO.endTime}" type="date"/>' onClick="WdatePicker()">
      				</td>
    			</tr>
    			<tr class="trForContent1">
      				<td align="center" class="border_top_right4" colspan="2">
      					<input type="button" onclick="queryList();" name="submitBtn" value="检  索" class="button2">
      				</td>
   				</tr>
  			</table>
 		</form>
 
		<div id="resultListDiv" class="listFence" style="width:100%;height:55%;"></div>
   		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;<span id="loadMsgSpan">信息加载中,请稍候...</span>
		</div>  
	</body>

 	<script type="text/javascript">

		$(document).ready(function(){}); 

 		function queryList () {
 			var start =  $('#startTime').val() ;
 			var end = $('#endTime').val() ;
 			if ( !validDate ( start ,end )) {
 				alert ( "开始日期不能大于结束日期" ) ;
 				return false ;
 			}
 			$('#infoLoadingDiv').dialog('open');
 			var pars = $("#mainfromId").serialize();
 				$.ajax({
 					type: "POST",
 					url: "fi.bankInReport.do?method=queryList",
 					data: pars,
 					success: function(result) {
 						$('#infoLoadingDiv').dialog('close');
 						$('#resultListDiv').html(result);
 					}
 				});
 		}
	
	</script>
 
 