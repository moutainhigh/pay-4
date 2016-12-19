<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>

<h2 class="panel_title">退款状态批量处理查询</h2>

<form action="" method="post" id="mainfromId" name="mainfrom" 
		onkeydown="javascript:if(event.keyCode == 13){document.all.submitBtn.focus();document.all.submitBtn.click();}">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
     <tr class="trForContent1">
   	  	<td class=border_top_right4 align="center" colspan="4" >上传日期：
        	<input type="text" name="createTimeStart" id="createTimeStart" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'createTimeEnd\');}'})" class="Wdate"/>
      	至
        	<input type="text" name="createTimeEnd" id="createTimeEnd" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'createTimeStart\');}',maxDate:'%y-%M-%d'})" class="Wdate"/>
     	</td>
    </tr>
    
   <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="query();" name="submitBtn" value="查询" class="button2">
      </td>
    </tr>
  </table>
 </form>

<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">

	function query(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/refundOrder.do?method=queryRefundExceptionBatchList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

	$(document).ready(function(){
		query();
	});
</script>