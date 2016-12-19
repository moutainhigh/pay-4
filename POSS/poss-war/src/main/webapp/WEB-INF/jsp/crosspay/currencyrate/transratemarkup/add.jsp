<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">商户交易汇率Markup新增</h2>
<form action="${ctx}/transRateMarkup/markup.do?method=add" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="memberCode" name="memberCode" value="${markup.memberCode}"> <span style="color:red">*</span>
	      	<input type="hidden" id="id" name="id" value="${markup.id}">
	      </td>
	      <td align="right" class="border_top_right4" >Markup：</td>
	      <td class="border_top_right4">
	     	<input type="text" id="markup" name="markup" value="${markup.markup}">% <span style="color:red">*</span>
	      </td>
	      <td align="right" class="border_top_right4" >优先值：</td>
	      <td class="border_top_right4">
	     	<input type="text" id="priority" name="priority" value="${markup.priority}"><span style="color:red">*</span>
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">交易币种：</td>
	      <td class="border_top_right4" colspan="5">
	        <input type="hidden"  id="currency000"  value="${markup.currency}">
	        <input type="checkbox"  id="currency" ><span style="color: red;font-weight: bold;">全选</span>
	      	<c:forEach var="currency" items="${currencys}" varStatus="v">
					<input type="checkbox"  id="currency_${currency.code}" name="currency" value="${currency.code}">${currency.code}
			</c:forEach>
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">目标币种：</td>
	      <td class="border_top_right4" colspan="5">
	         <input type="hidden"  id="targetCurrency000"  value="${markup.targetCurrency}">
	         <input type="checkbox"  id="targetCurrency" ><span style="color: red;font-weight: bold;">全选</span>
	      	 <c:forEach var="currency" items="${currencys}" varStatus="v">
					<input type="checkbox"  id="targetCurrency_${currency.code}" name="targetCurrency" value="${currency.code}">${currency.code}
			</c:forEach>
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">卡组织：</td>
	      <td class="border_top_right4" colspan="5">
	        <input type="hidden"  id="cardOrg000" value="${markup.cardOrg}">
	      	<input type="checkbox"  id="cardOrg_VISA" name="cardOrg" value="VISA">VISA
	      	<input type="checkbox" id="cardOrg_MASTER" name="cardOrg" value="MASTER">MASTER
	      	<input type="checkbox" id="cardOrg_JCB" name="cardOrg" value="JCB">JCB
	      	<input type="checkbox" id="cardOrg_AE" name="cardOrg" value="AE">AE
	      	<input type="checkbox" id="cardOrg_DC" name="cardOrg" value="DC">DC
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">卡属国：</td>
	      <td class="border_top_right4" colspan="5">
	        <input type="hidden"  id="cardCountry000"  value="${markup.cardCountry}">
	      	<input type="checkbox"  id="cardCountry_CHN" name="cardCountry" value="CHN">中国 CHN
	      	<input type="checkbox" id="cardCountry_USA" name="cardCountry" value="USA">美国 USA
	      	<input type="checkbox" id="cardCountry_CAN" name="cardCountry" value="CAN">加拿大 CAN
	      	<input type="checkbox" id="cardCountry_AUS" name="cardCountry" value="AUS">澳大利亚 AUS
	      	<input type="checkbox" id="cardCountry_JPN" name="cardCountry" value="JPN">日本 JPN
	      	<input type="checkbox"  id="cardCountry_MNP" name="cardCountry" value="MNP">挪威 MNP
	      	<input type="checkbox" id="cardCountry_SWE" name="cardCountry" value="SWE">瑞典 SWE
	      	<input type="checkbox" id="cardCountry_GBR" name="cardCountry" value="GBR">英国 GBR
	      	<input type="checkbox" id="cardCountry_EUR" name="cardCountry" value="EUR">欧盟 EUR
	      	<input type="checkbox" id="cardCountry_OOO" name="cardCountry" value="OOO">其他地区 OOO
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">卡本币：</td>
	      <td class="border_top_right4" colspan="5">
	        <input type="checkbox"  id="cardCurrencyCode" ><span style="color: red;font-weight: bold;">全选</span>
	        <input type="hidden"  id="cardCurrencyCode000"  value="${markup.cardCurrencyCode}">
	      	<c:forEach var="currency" items="${currencys}" varStatus="v">
					<input type="checkbox"  id="cardCurrencyCode_${currency.code}" name="cardCurrencyCode" value="${currency.code}">${currency.code}
			</c:forEach>
	      </td>
	     </tr>
	     	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">交易金额范围：</td>
	      <td class="border_top_right4" colspan="2">
	      	<input type="text" id="startAmount" name="startAmount" value="${markup.startAmount}">--
	     	<input type="text" id="endAmount" name="endAmount" value="${markup.endAmount}"> <span style="color:red">*</span>(USD)
	      </td>
	      <td align="right" class="border_top_right4" >时间段：</td>
	      <td class="border_top_right4" colspan="2">
	     	<input type="text" id="startPoint" name="startPoint" value="${markup.startPoint}">--
	     	<input type="text" id="endPoint" name="endPoint" value="${markup.endPoint}"><span style="color:red">*</span>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="submit"  value="添  加" class="button2">
	      <input type="button"  value="返  回" class="button2" onclick="toIndex();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseDesc}">
	<font color="red"><b>${responseDesc}</b></font>
</c:if>
  <script type="text/javascript">  
	function toIndex(){
		window.location.href="${ctx}/transRateMarkup/markup.do";
	}
	
	$(function(){
		var currencys = $("#currency000").val();
		if(currencys.length>0){
			var array = currencys.split(",");
			for(var i=0;i<array.length;i++){
				$("#currency_"+array[i]).attr("checked",true);
			}
		}
		
		var targetCurrencys = $("#targetCurrency000").val();
		if(targetCurrencys.length>0){
			var array = targetCurrencys.split(",");
			for(var i=0;i<array.length;i++){
				$("#targetCurrency_"+array[i]).attr("checked",true);
			}
		}
		
		var cardOrgs = $("#cardOrg000").val();
		if(cardOrgs.length>0){
			var array = cardOrgs.split(",");
			for(var i=0;i<array.length;i++){
				$("#cardOrg_"+array[i]).attr("checked",true);
			}
		}
		
		var cardCountrys = $("#cardCountry000").val();
		if(cardCountrys.length>0){
			var array = cardCountrys.split(",");
			for(var i=0;i<array.length;i++){
				$("#cardCountry_"+array[i]).attr("checked",true);
			}
		}
		
		var cardCurrencyCodes = $("#cardCurrencyCode000").val();
		if(cardCurrencyCodes.length>0){
			var array = cardCurrencyCodes.split(",");
			for(var i=0;i<array.length;i++){
				$("#cardCurrencyCode_"+array[i]).attr("checked",true);
			}
		}
		
		
		$("#currency").click(function(){
			var checked = $(this).attr("checked");
			if(checked){
				$("input[id*='currency_']").attr("checked",true);
			}else{
				$("input[id*='currency_']").attr("checked",false);
			}
		});
		
		$("#targetCurrency").click(function(){
			var checked = $(this).attr("checked");
			if(checked){
				$("input[id*='targetCurrency_']").attr("checked",true);
			}else{
				$("input[id*='targetCurrency_']").attr("checked",false);
			}
		});
		
		$("#cardCurrencyCode").click(function(){
			var checked = $(this).attr("checked");
			if(checked){
				$("input[id*='cardCurrencyCode_']").attr("checked",true);
			}else{
				$("input[id*='cardCurrencyCode_']").attr("checked",false);
			}
		});
	});

  </script>