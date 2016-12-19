<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">渠道返回查询</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      <td align="right" class="border_top_right4" >渠道订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="channelOrderNo" name="channelOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="status">
	      		<option value="">请选择</option>
	      		<option value='0'>进行中</option>
				<option value='1'>交易成功</option>
				<option value='2'>交易失败</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="orderId" name="orderId">
	      </td>
	      <td align="right" class="border_top_right4" >银行机构：</td>
	      <td class="border_top_right4" align="left">
					<select id="orgCode" name="orgCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${paymentChannelItems}" varStatus="v">
							<option value="${channel.orgCode}">${channel.name}</option>
						</c:forEach>
					</select>	
		  </td>
	      <td align="right" class="border_top_right4" >交易时间</td>
	      <td class="border_top_right4">
	      <input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
						<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      </td>
	     </tr>
	     <tr class="trForContent1">
	     	<td align="right" class="border_top_right4">授权码：</td>
	     	<td class="border_top_right4"><input type="text" name="authorisation" id="authorisation"/></td>
	     	<td align="right" class="border_top_right4">网关订单号：</td>
	     	<td class="border_top_right4">
	     		<input type="text" name="tradeOrderNo" id="tradeOrderNo"/>
	     	</td>
	     	<td align="right" class="border_top_right4">返回码：</td>
	     	<td class="border_top_right4">
	     		<input type="text" name="errorCode" id="errorCode"/>
	     	</td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="search();">
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

	/* $(document).ready(function(){
		search();
	});  */
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/channelCodeQuery.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>