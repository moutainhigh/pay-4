<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>全员奖励销售报表</title>
<script type="text/javascript">

		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}

		function validateQuery(){
			var accTime = document.getElementById("startDate").value;
			
			if(isEmpty(accTime)){
				alert("交易结算时间不能 为空");
				return false;
			}
			
			return true;
		}
		
		function userQuery(){
			if(!validateQuery()){
				return false;
			}
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/if_poss_query/performanceReport.do?method=query",
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
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">报表查询统计</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>

<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">交易结算时间：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startDate"  name="startDate" readonly onClick="WdatePicker({dateFmt:'yyyy-MM'})">
      	</td>
      	<td class=border_top_right4 align="center" colspan="2">
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
