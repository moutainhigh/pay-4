<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">商户结算汇率管理</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号</td>
	      <td><input type="text"  name="memberCode"></td>
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
	      <td align="right" class="border_top_right4" >卡组织</td>
	      <td class="border_top_right4">
	       <select name="cardOrg" id="cardOrg">
	                <option value="">请选择</option>
					<option value="VISA">VISA</option>
					<option value="MASTER">MASTER</option>
					<option value="JCB">JCB</option>
					<option value="AE">AE</option>
			   		<option value="DC">DC</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >状态</td>
	      <td class="border_top_right4">
	       <select name="status" id="status">
	                <option value="">请选择</option>
					<option value="1">生效</option>
					<option value="0">失效</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="10">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <!--<input type="button"  name="butSubmit" value="添  加" class="button2" onclick="toAdd();">  -->
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
		window.location.href="${ctx}/settlementRate/rate.do?method=toAdd";
	}
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/settlementRate/rate.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>