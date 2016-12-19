<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查询充退审核数据</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">查询充退审核数据</h2>

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
      	<td align="right" class="border_top_right4">会员号：</td>
      	<td class="border_top_right4">
	    	<input type="text" name="memberNo" style="width:120px;" maxlength="36"/>   	
	    </td>
      	<td class=border_top_right4 align="right" >会员类型：</td>
      	<td class="border_top_right4" >
        	<select name="memberType" style="width:120px;">
        			<option value="">全部</option>
        			<option value="1">个人</option>
        			<option value="2">企业</option>
        	</select>
      	</td>
    </tr>
  	<tr class="trForContent1">
      	<td class=border_top_right4 align="right" >账户类型：</td>
      	<td class="border_top_right4" >
        	<select id="accountType" name="accountType">
				<option value="" selected>---请选择---</option>
				<c:forEach var="acctType" items="${acctTypes}" varStatus="v">
					<option value="${acctType.code}">${acctType.displayName}</option>
				</c:forEach>
			</select>
      	</td>
      	<td class=border_top_right4 align="right" >审核状态：</td>
      	<td class="border_top_right4" >
        	<select name="auditStatus"  style="width:120px;">
        			<option value="1,2">全部</option>
        			<option value="1">审核通过</option>
        			<option value="2">审核失败</option>
        	</select>
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
      	<td class=border_top_right4 align="right" >银行渠道：</td>
      	<td class="border_top_right4">
        	<%-- <li:select name="bankCode" defaultStyle="true" itemList="${bankList}"/> --%>
		 <select name="bankCode" defaultStyle="true" > 
					<option value="">---请选择---</option>
					<option value="0000000">测试通道</option>
        			<option value="10076001">中银卡司</option>
        			<option value="10079001">中银MOTO</option>
        			<option value="10080001">中银MIGS</option>
        			<option value="10075001">CREDORAX</option>
        			<option value="10003001">中国银行</option>
        			<option value="10002001">农业银行</option>
        			<option value="10078001">农行CTV</option>
        			<option value="10077001">Adyen</option>
        			<option value="10077002">Belto</option>
        			<option value="10077003">Cashu</option>
        			
					<option value="10081001">CTBoleto</option>
					<option value="10081002">CTSafetyPay</option>
					<!-- <option value="10081003">CTDirectDebitsSSL</option> -->
					<option value="10081004">CTSofortBanking</option>
					<option value="10081005">CTEPS</option>
					<option value="10081006">CTGiropay</option>
					<option value="10081007">CTPagBrailDebitCard</option>
					<option value="10081008">CTPagBrasilOTF</option>
					<option value="10081009">CTPoli</option>
					<option value="10081010">CTPrzelewy24</option>
					<option value="10081011">CTQIWI</option>
					<option value="10081012">CTSEPA</option>
					<option value="10081013">CTTeleingreso</option>
					<option value="10081014">CTTrustPay</option>
					<option value="10081015">CTiDeal</option>
					<option value="10081016">前海万融</option>
		 </select>
      	</td>
      	<td class=border_top_right4 align="right" >清结算是否废除：</td>
      	<td class="border_top_right4" >
        	<select name="liquidateFlag"  style="width:120px;">
        			<option value="2">是</option>
        			<option value="0" selected="selected">否</option>
        	</select>
      	</td>
      	</tr>
    <tr class="trForContent1">
      	<td class=border_top_right4 align="right" >业务类型：</td>
      	<td class="border_top_right4" colspan="3">
        	<select name="busiType" style="width:120px;">
        		<option value="">全部</option>
        		<option value="">充退</option>
        	</select>
      	</td>
    </tr>
   <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="submitByHref();" name="submitBtn" value="查询" class="button2">
      <input type="button" name="btnDownload" class="button2" value="下载" onclick="downloadReAuditInfo();"/>
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
				url: "refund.reaudit.do?method=queryRefundReAuditList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	//下载复核信息
	function downloadReAuditInfo(){
		document.mainfrom.action = "refund.reaudit.download.do?method=handleDownloadReAuditInfo";
		document.mainfrom.submit();
	}
</script>