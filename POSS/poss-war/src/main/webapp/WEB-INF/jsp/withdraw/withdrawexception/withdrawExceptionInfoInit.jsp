<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">出款异常数据处理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainfromId" name="mainfrom" 
		onkeydown="javascript:if(event.keyCode == 13){document.all.submitBtn.focus();document.all.submitBtn.click();}">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
     <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >交易时间：</td>
      	<td class="border_top_right4" colspan="3">
        	<input type="text" name="startTime" id="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'endTime\');}'})" class="Wdate"/>
      	至
        	<input type="text" name="endTime" id="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\');}',maxDate:'%y-%M-%d'})" class="Wdate"/>
     	</td>
    </tr>
    <tr class="trForContent1">
      	<td align="right" class="border_top_right4">交易流水号：</td>
      	<td class="border_top_right4">
	    	<input type="text" name="businessSeq" style="width:120px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="20"/>   	
	    </td>
      	<td class=border_top_right4 align="right" >会员号：</td>
      	<td class="border_top_right4" >
      		<input type="text" name="memberNo" style="width:120px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="19"/>
      	</td>
    </tr>
    <tr class="trForContent1">
      	<td align="right" class="border_top_right4">交易类型：</td>
      	<td class="border_top_right4">
	    	<select name="businessType" style="width:120px;">
	    		<option value="">全部</option>
	    		<option value="0">提现</option>
	    		<!--<option value="1">批量出款</option>
	    		<option value="2">信用卡还款</option>
	    		--><option value="3">付款到银行</option>
	    		<option value="4">批量付款到银行</option>
	    	</select>   	
	    </td>
      	<td class=border_top_right4 align="right" >银行账号：</td>
      	<td class="border_top_right4" >
      		<input type="text" name="bankAcct" style="width:120px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="19"/>
      	</td>
    </tr>
   <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="query();" name="submitBtn" value="查  询" class="button2">
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
				url: "${ctx}/withdrawException.htm?method=list",
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