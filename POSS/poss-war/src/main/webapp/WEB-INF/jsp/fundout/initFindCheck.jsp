<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<h2 class="panel_title">查询出款复核结果</h2>
<form action="" method="post" id="mainfromId" name="mainfrom" >
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	 <tr class="trForContent1">
      	<td align="right" class="border_top_right4">批次号：</td>
      	<td class="border_top_right4">
	    	<input type="text" name="batchNo" style="width:120px;" />   	
	    </td>
      	<td class=border_top_right4 align="right" >操作员：</td>
      	<td class="border_top_right4" >
      		<input type="text" name="operator" style="width:120px;"/>
      	</td>
    </tr>
     <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >上传日期：</td>
      	<td class="border_top_right4" colspan="3">
        	<input class="Wdate" type="text" id="startTime"  name="startTime"  onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'})">
      	至
        	<input class="Wdate" type="text" id="endTime" name="endTime" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})">
     	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      	<input type="button" name="submitBtn" onclick="submitByHref();" value="查  询" class="button2">
      </td>
    </tr>
  </table>
 </form>
 <div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">
function submitByHref(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "./findFundoutCheck.do?method=queryFundoutCheck",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
}

</script>
 