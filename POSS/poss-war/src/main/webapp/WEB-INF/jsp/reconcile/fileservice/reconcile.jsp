<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 
<%@ taglib prefix="li" uri="fi" %>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查询对账</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">查询对账</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td class="border_top_right4" align="right" >对账批次号：</td>
			<td class="border_top_right4" align="left" >
			<input	type="text" id="" name="batchNo"></td>
			<td align="right" class="border_top_right4" >操作员：</td>
		      <td class="border_top_right4">
		      	<input type="text" name="operator">
		      </td>
		        <td align="right" class="border_top_right4" >上传时间</td>
	    	  <td class="border_top_right4" colspan="3">
	    	  <input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
					        	～
			<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="search();" >
	 <!--      <input type="button"  name="butSubmit" value="下  载" class="button2" onclick="download();"> -->
	      </td>
	    </tr>
   </table>
</form>
	<div id="resultListDiv" class="listFence"></div>
   	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;<span id="loadMsgSpan">信息加载中,请稍候...</span>
</div>  
<script type="text/javascript">
$(document).ready(function(){search();}); 

function search(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
		$.ajax({
			type: "POST",
			url: "reconcile.do?method=queryReconcile",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
}

</script>