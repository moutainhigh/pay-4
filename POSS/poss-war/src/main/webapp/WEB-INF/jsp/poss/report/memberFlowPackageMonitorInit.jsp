<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>会员包量监控</title>
<script type="text/javascript">
		
		function mfpMonitorQuery(pageNo,totalCount,pageSize){
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/report/memberFlowPackageMonitor.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }

		$(document).ready(function(){mfpMonitorQuery();});
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
						<font class="titletext">会员包量流量监控</font>
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
      	<td class=border_top_right4 align="right" >会员编号：</td>
      	<td class="border_top_right4" >
        	<input type="text" name="memberCode" maxlength="11"> 
      	</td>
      	<td class=border_top_right4 align="right" >会员名称：</td>
      	<td class="border_top_right4" >
        	<input type="text" name="memberName" maxlength="11"> 
      	</td>
    </tr>
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">预警状态：</td>
    	<td class="border_top_right4">
	    	<select id="warnStatus" name="warnStatus" >
	    		<option value="">全部</option>
	    		<option value="0">未预警</option>
	    		<option value="1">预警</option>
	    	</select>
	    </td>
	    <td align="right" class="border_top_right4">网关状态：</td>
    	<td class="border_top_right4">
	    	<select id="gatewayStatus" name="gatewayStatus" >
	    		<option value="">全部</option>
	    		<option value="0">关闭</option>
	    		<option value="1">正常</option>
	    	</select>
	    </td>
	</tr>
    
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      	<input type="button" onclick="mfpMonitorQuery();" name="submitBtn" value="查  询" class="button2">&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
  </table>
  <c:if test="${not empty message}">
 	<div>
		<li style="color: red;">${message}</li>
	</div>
 </c:if>
 </form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>
