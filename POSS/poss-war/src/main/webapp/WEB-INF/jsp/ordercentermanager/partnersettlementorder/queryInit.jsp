<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">清算订单查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      <td align="right" class="border_top_right4" >网关订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="tradeOrderNo" name="tradeOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >清算状态：</td>
	      <td class="border_top_right4">
	      	<select id="settlementFlg" name="settlementFlg">
	      		<option value="">请选择</option>
	      		<option value='0'>未清算</option>
				<option value='1'>清算成功</option>
				<option value='2'>清算失败</option>
				<option value='4'>已退款</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="orderId" name="orderId">
	      </td>
	      <td align="right" class="border_top_right4" >支付订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="paymentOrderNo" name="paymentOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >保证金清算状态：</td>
	      <td class="border_top_right4">
	      	<select id="assureSettlementFlg" name="assureSettlementFlg">
	      		<option value="">请选择</option>
	      		<option value='0'>未清算</option>
				<option value='1'>清算成功</option>
				<option value='2'>清算失败</option>
				<option value='3'>退款老</option>
				<option value='4'>退款新</option>
				<option value='5'>拒付</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
    	 <td align="right" class="border_top_right4" >清算订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="id" name="id">
	      </td>	    
	      <td align="right" class="border_top_right4" >清算时间：</td>
	      <td class="border_top_right4">
	      	<input class="Wdate" type="text" id="settleStart" value="${settleStart}" name="settleStart"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'settleEnd\')}'})">
				        	～
						<input class="Wdate" type="text" id="settleEnd" name="settleEnd" value="${settleEnd}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'settleStart\')}'})">
	      </td>
	      <td align="right" class="border_top_right4" >交易时间：</td>
	      <td class="border_top_right4" colspan="3">
	      <input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
						<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      </td>
	      
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  id="resettleBtn" name="butSubmit" value="清算失败重新清算" class="button2" onclick="orderReSettlement();">
	      <input type="button"  id="butSubmit" name="butSubmit" value="删除正在重新清算标记" class="button2" onclick="delResettleFlag();">
	      <input type="button"  id="resettleAssureBtn" name="butSubmit" value="保证金清算失败重新清算" class="button2" onclick="orderReAssureSettlement();">
	      <input type="button"  id="butSubmit" name="butSubmit" value="删除正在重新清算保证金标记" class="button2" onclick="delResettleAssureFlag();">
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

<div id="infoResettleDiv" title="正在进行中" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;正在重新清算, 请稍候...
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
			url: "${ctx}/partnerSettlementOrderQuery.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function orderReSettlement() {
		$('#infoResettleDiv').dialog({
				title:'正在进行中',
				width: 400,
				height:200,
				closed:false,
				cache:false,
				modal:true
		});
		$("#resettleBtn").attr({"disabled":"disabled"});
		$.ajax({
			type: "POST",
			url: "${ctx}/partnerSettlementOrderQuery.do?method=orderReSettlement",
			success: function(result) {
				$('#infoResettleDiv').dialog('close');
				$('#resultListDiv').html(result);
				$("#resettleBtn").removeAttr("disabled");
			},
			error:function(e){
				alert("出错了！" + e);
				$('#infoResettleDiv').dialog('close');
				$("#resettleBtn").removeAttr("disabled");
				
			}
		});
	}
	
	
	function delResettleFlag() {	
		$.ajax({
			type: "POST",
			url: "${ctx}/partnerSettlementOrderDelResettleFlag.do?method=delResettleFlag",
			success: function(result) {				
				alert(result);
				$("#resettleBtn").removeAttr("disabled");	
			},
			error:function(e){
				alert("出错了！" + e);
				$("#resettleBtn").removeAttr("disabled");				
			}
		});
	}
	
	function delResettleAssureFlag() {	
		$.ajax({
			type: "POST",
			url: "${ctx}/partnerSettlementOrderDelResettleAssureFlag.do?method=delResettleAssureFlag",
			success: function(result) {				
				alert(result);
				$("#resettleAssureBtn").removeAttr("disabled");	
			},
			error:function(e){
				alert("出错了！" + e);
				$("#resettleAssureBtn").removeAttr("disabled");				
			}
		});
	}
	
	function orderReAssureSettlement() {
		$('#infoResettleDiv').dialog({
			title:'正在进行中',
			width: 400,
			height:200,
			closed:false,
			cache:false,
			modal:true
		});
		$("#resettleAssureBtn").attr({"disabled":"disabled"});		
		$.ajax({
			type: "POST",
			url: "${ctx}/partnerSettlementOrderQuery.do?method=orderReAssureSettlement",
			success: function(result) {
				$('#infoResettleDiv').dialog('close');
				$('#resultListDiv').html(result);
				$("#resettleAssureBtn").removeAttr("disabled");
			},
		});
	}
	
	function download(){
		var pars = $("#mainfromId").serialize();
		window.location.href = 	"${ctx}/partnerSettlementOrderQueryDownload.do?method=downloadPartnerSettlementOrder&"+pars;
	}
  </script>