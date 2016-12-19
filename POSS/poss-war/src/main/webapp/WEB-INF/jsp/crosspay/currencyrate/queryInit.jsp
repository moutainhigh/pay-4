<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">基本汇率管理</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >币种：</td>
	      <td class="border_top_right4">
	      	<select name="currency" id="currency">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="currency" items="${currencys}" varStatus="v">
					<option value="${currency.code}">${currency.desc}</option>
				</c:forEach>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >目标币种：</td>
	      <td class="border_top_right4">
	      	<select name="targetCurrency" id="targetCurrency">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="currency" items="${currencys}" varStatus="v">
					<option value="${currency.code}">${currency.desc}</option>
				</c:forEach>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >状态</td>
	      <td>
	       <select name="status" id="status">
	                <option value="">请选择</option>
					<option value="1">生效</option>
					<option value="0">失效</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >生效周期</td>
	      <td class="border_top_right4">
	      	<input class="Wdate" type="text" id="effectDate" value="${effectDate}" name="effectDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'expireDate\')}'})">
				        	～
						<input class="Wdate" type="text" id="expireDate" name="expireDate" value="${expireDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'effectDate\')}'})">
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit" value="添  加" class="button2" onclick="toAdd();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseCode && responseCode == '0000'}">
	<font color="red"><b>操作成功！</b></font>	
</c:if>

<c:if test="${not empty responseCode && responseCode != '0000'}">
	<font color="red"><b>${responseDesc }</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
<script type="text/javascript">

	/* $(document).ready(function(){
		search();
	});  */
	
	function toAdd(){
		window.location.href="${ctx}/currency/rate.do?method=toAdd";
	}
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/currency/rate.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>