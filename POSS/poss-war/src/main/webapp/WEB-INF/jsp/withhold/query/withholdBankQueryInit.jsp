<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">银行代扣查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainfromId" name="mainfrom">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
		<td class="border_top_right4" align="right" >订单号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="orderId" name="orderId" value="${orderId}">
		</td>
		<td class="border_top_right4" align="right" >发送银行流水号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="bankOrderid" name="bankOrderid" value="${bankOrderid}"  maxlength= "32"></td>
		</td>
	</tr>
	<tr class="trForContent1"> 
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantId" name="merchantId" value="${merchantId}"  maxlength= "32">
		</td>
		<td class="border_top_right4" align="right" >代扣类型：</td>
		<td class="border_top_right4" align="left" >
			<select id="type" name="type">
				<option value="" selected >全部</option>	
					<option value="1" <c:if test="${type=='1'}">select</c:if> >支付平台账户</option>
					<option value="2" <c:if test="${type=='2'}">select</c:if> >借记卡</option>
					<option value="3" <c:if test="${type=='3'}">select</c:if> >贷记卡</option>
					<option value="4" <c:if test="${type=='4'}">select</c:if> >预付卡</option>
					<option value="5" <c:if test="${type=='5'}">select</c:if> >存折</option>
		    </select>
		</td>
	</tr>	
	<tr class="trForContent1"> 
		<td  class="border_top_right4" align="right" >处理状态：</td>
		<td  class="border_top_right4" align="left" >
			<select id="orderStatus" name="orderStatus">	
				<option value=" ">全部</option>
				<option value="1" <c:if test="${status=='1'}">select</c:if> >订单已受理</option>
				<option value="2" <c:if test="${status=='2'}">select</c:if> >等待审核</option>
				<option value="3" <c:if test="${status=='3'}">select</c:if> >审核成功</option>
				<option value="4" <c:if test="${status=='4'}">select</c:if> >审核失败</option>
				<option value="5" <c:if test="${status=='5'}">select</c:if> >已发送银行</option>
				<option value="6" <c:if test="${status=='6'}">select</c:if> >银行已受理</option>	
				<option value="7" <c:if test="${status=='7'}">select</c:if> >银行拒绝</option>
				<option value="8" <c:if test="${status=='8'}">select</c:if> >银行扣款成功</option>
				<option value="9" <c:if test="${status=='9'}">select</c:if> >银行扣款失败</option>	
				<option value="10" <c:if test="${status=='10'}">select</c:if> >订单处理成功</option>
				<option value="11" <c:if test="${status=='11'}">select</c:if> >订单处理失败</option>
				<option value="12" <c:if test="${status=='12'}">select</c:if> >已退款</option>		
		   </select>	
		</td>
		<td  class="border_top_right4" align="right" >起止时间：</td>
		<td  class="border_top_right4" align="left" >
			<input class="Wdate" type="text" id= "startTime" name="startTime"  value="${startTime}" 
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'whyGreen'})" readonly />
			～
			<input class="Wdate" type="text" id= "endTime" name="endTime"  value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'whyGreen',minDate:'#F{$dp.$D(\'startTime\')}'})">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<!--  <a class="s03" href="javascript:withholdBankQuery()"><img src="./images/query.jpg" border="0"></a> -->
			<input type="button" name="btnDownload" class="button2" value="检索" onclick="withholdBankQuery();"/>
			<input type="button" name="btnDownload" class="button2" value="下  载" onclick="downloadInfo();"/>
		</td>
	</tr>
   </table>
</form>


 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
  <script type="text/javascript">
	/* $(document).ready(function(){
		search();
	});  */
	
	function withholdBankQuery(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
		;
			$.ajax({
				type: "POST",
				url: "poss_withhold_bank_query.htm?method=queryList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	
	//下载复核信息
	function downloadInfo(){
		document.mainfrom.action = "poss_withhold_bank_query.htm?method=handleDownloadList";
		document.mainfrom.submit();
	}
  </script>