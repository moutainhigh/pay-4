<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">网关订单管理</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      <td align="right" class="border_top_right4" >商户订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="orderId" name="orderId">
	      </td>
	      <td align="right" class="border_top_right4" >状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="statusList">
	      		<option value="">全部</option>
	      		<option value='0'>未付款</option>
				<option value='6,20'>支付中</option>
				<option value='4,3'>支付成功</option>
				<option value='5'>支付失败</option>
				<option value='21'>预授权申请成功</option>
				<option value='22'>预授权申请已撤销</option>
				<option value='23'>预授权申请发生完成</option>
				<option value='24'>预授权申请失败</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >系统交易号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="tradeOrderNo" name="tradeOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >交易创建时间</td>
	      <td class="border_top_right4" colspan="3">
	      <input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
			<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      </td>
	     </tr>
	     <tr class="trForContent1">
	    	 <td align="right" class="border_top_right4" >渠道订单号：</td>
		      <td class="border_top_right4">
		      	<input type="text" id="channelOrderNo" name="channelOrderNo">
		      </td>
	    	 <td align="right" class="border_top_right4" >授权码：</td>
		      <td class="border_top_right4">
		      	<input type="text" id="authCode" name="authCode">
		      </td>
		       <td align="right" class="border_top_right4" >是否拒付：</td>
	      <td class="border_top_right4">
	      		<select name="chargeBack" id="chargeBack">
	      			<option value="">全部</option>
	      			<option value="1">是</option>
	      			<option value="2">否</option>
	      		</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="search();">
	        <input type="button"  name="butSubmit" value="下载" class="button2" onclick="download();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
<script type="text/javascript">

	$(document).ready(function(){
		search();
	});  
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/tradeOrderQuery.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	function download(){
		var pars = $("#mainfromId").serialize();
		window.location.href = 	"${ctx}/tradeOrderQueryDownload.do?method=downloadTrade&"+pars;
	}
  </script>