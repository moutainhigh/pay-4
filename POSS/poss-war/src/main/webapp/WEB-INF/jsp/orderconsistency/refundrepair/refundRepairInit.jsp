<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">出款退款失败修复初始页</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainFormId" name="mainForm"
		onkeydown="javascript:if(event.keyCode == 13){document.all.submitBtn.focus();document.all.submitBtn.click();}">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">选择交易日期：</td>
      	<td class="border_top_right4" colspan="3">
	      	<input class="Wdate" type="text" id="startTime"  name="startDate" value='<fmt:formatDate value="${workorderRepairDTO.startTime}" type="date"/>' onClick="WdatePicker()">
	        	～
			<input class="Wdate" type="text" id="endTime" name="endDate"  value='<fmt:formatDate value="${workorderRepairDTO.endTime}" type="date"/>' onClick="WdatePicker()">
      	</td>
    </tr>
    <tr class="trForContent1">
    	<td class=border_top_right4 align="right" >交易号：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="sequenceId" value=""  />
      	</td>
   	  	<td colspan="2" ></td>
      	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="submitByHref();" name="submitBtn" value="查询" class="button2">
      </td>
    </tr>
  </table>
 </form>
<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">
	showMsg();
	function showMsg(){
		var errorMsg = '${message}';
		errorMsg = '<font color="red"><b>'+errorMsg+'</b></font>';
		$('#resultListDiv').html(errorMsg);
	}
	
	function submitByHref() {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainFormId").serialize();
			$.ajax({
				type: "POST",
				url: "refundrepair.htm?method=repairList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>