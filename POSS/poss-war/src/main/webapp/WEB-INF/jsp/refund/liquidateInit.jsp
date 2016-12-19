<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>

<h2 class="panel_title">查询充退复核数据</h2>
<form action="" method="post" id="mainfromId" name="mainfrom" 
		onkeydown="javascript:if(event.keyCode == 13){document.all.submitBtn.focus();document.all.submitBtn.click();}">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
     <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >交易日期：</td>
      	<td class="border_top_right4" colspan="3">
        	<input class="Wdate" type="text" id="startTime"  name="startTime" 
        		value='<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>' 
        		onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'})">
      	至
        	<input class="Wdate" type="text" id="endTime" name="endTime" 
        		value='<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>' 
        		onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})">
     	</td>
    </tr>
    <tr class="trForContent1">
      	<td align="right" class="border_top_right4">交易流水号：</td>
      	<td class="border_top_right4">
	    	<input type="text" name="refundSeq" style="width:120px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="19"/>   	
	    </td>
      	<td class=border_top_right4 align="right" >充值流水号：</td>
      	<td class="border_top_right4" >
      		<input type="text" name="rechargeSeq" style="width:120px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="19"/>
      	</td>
    </tr>
    <tr class="trForContent1">
      	<td class=border_top_right4 align="right" >银行名称：</td>
      	<td class="border_top_right4" >
        	<li:select name="bankCode" defaultStyle="true" itemList="${bankList}"/>
      	</td>
      	<td class=border_top_right4 align="right" >复核状态：</td>
      	<td class="border_top_right4" >
        	<select name="auditStatus"  style="width:120px;">
        			<option value="4,5">全部</option>
        			<option value="4">通过</option>
        			<option value="5">拒绝</option>
        	</select>
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

	function submitByHref(pageNo,totalCount,pageSize) {
		var strDate1 = $("#startTime").val();
		var strDate2 = $("#endTime").val();

		if(!validDate(strDate1,strDate2)){
			alert("开始日期不能大于结束日期!");
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "refund.liquidate.do?method=queryRefundLiquidateList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
</script>