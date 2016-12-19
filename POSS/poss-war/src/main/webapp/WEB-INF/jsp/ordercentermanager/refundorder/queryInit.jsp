<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">退款订单管理</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      <td align="right" class="border_top_right4" >网关交易号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="tradeOrderNo" name="tradeOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >支付订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="paymentOrderNo" name="paymentOrderNo">
	      </td>	      
	      <td align="right" class="border_top_right4" >状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="status">
	      		<option value="">请选择</option> <!-- 添加退款订单状态的查询条件 delin.dong 2016年4月21日16:17:51 -->
	      		<option value='0'>进行中</option>
	      		<option value='1'>退款中(已发请求给银行)</option>
				<option value='2'>退款成功</option>
				<option value='3'>机构退款失败</option>
				<option value='4'>机构退款超时</option>
				<option value='5'>人工处理</option>
				<option value='6'>人工处理失败</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >退款交易号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="refundOrderNo" name="refundOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >商户退款订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerRefundOrderId" name="partnerRefundOrderId">
	      </td>
	      <td align="right" class="border_top_right4" >交易时间</td>
	      <td class="border_top_right4" colspan="3">
	      <input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
						<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      </td>
	
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >完成时间</td>
	      <td class="border_top_right4">
	      <input class="Wdate" type="text" id="startTime" value="${compStartTime}" name="compStartTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
	        	～
		  <input class="Wdate" type="text" id="endTime" name="compEndTime" value="${compEndTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      </td>  
	      <td align="right" class="border_top_right4" >CTV退款单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="ctvReturnNo" name="ctvReturnNo">
	      </td>
	      <td align="right" class="border_top_right4" >对账：</td>	
	      <td class="border_top_right4">
	      	<select id="reconciliationFlg" name="reconciliationFlg">
	      		<option value="">请选择</option>
	      		<option value='0'>未对账</option>
	      		<option value='1'>已对账</option>
				<option value='2'>对账失败</option>
				<option value='3'>调单</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >银行渠道：</td>
	      <td class="border_top_right4" >
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
              </select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
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

	/* $(document).ready(function(){
		search();
	});  */
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/refundOrderQuery.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function download(){
		var pars = $("#mainfromId").serialize();
		window.location.href = 	"${ctx}/refundOrderDownload.do?method=downloadRefundOrder&"+pars;
	}
  </script>