<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function save() {
		 	var orgCode=$("#orgCode").val();
			var prdtCode=document.getElementsByName("prdtCode"); 
			var currencyCode=$("#currencyCode").val();
			var cardCurrencyCode=$("#cardCurrencyCode").val();
			var channelCurrencyCode=$("#channelCurrencyCode").val();
			var payType=$("#payType").val();
			if(orgCode == ""){
				alert("请选择渠道名称!");
				return;
			}
			if(prdtCode == ""){
				alert("请选择产品类型！");
				return ;
			} 
			if(currencyCode == ""){
				alert("请选择交易币种！");
				return ;
			} 
			if(payType =='DCC'){
				if(cardCurrencyCode == ""){
					alert("请输入卡本币！");
					return ;
				} 
			}
			if(channelCurrencyCode == "" || channelCurrencyCode == null){
				alert("请选择送渠道币种！");
				return ;
			} 
			if(payType == ""){
				alert("请选择支付类型！");
				return ;
			} 
			  for(var i=0;i<prdtCode.length;i++){
			         if(prdtCode[i].value=="PARTNER_STANDARD_DCC"&&prdtCode[i].checked&&currencyCode!="CNY"){
			        	 alert("交易币种应选为CNY");
							return;
			         }
			  }
			if (!confirm("确认新增该渠道币种配置？")) {
				return;
			 }
		
		$("#frm").submit();
	}
	
	function selectedPrdt(){
		var payType=$("#payType").val();
		
		$("#EDC").removeAttr("checked");
		$("#PARTNER_DCC_PRDCT").removeAttr("checked");
		$("#CUSTOM_HIDDEN_DCC").removeAttr("checked");
		$("#CUSTOM_FORCED_DCC").removeAttr("checked");
		$("#CUSTOM_STANDARD_DCC").removeAttr("checked");
		$("#STANDARD_DCC").removeAttr("checked");
		$("#FORCED_DCC").removeAttr("checked");
		$("#PARTNER_STANDARD_DCC").removeAttr("checked");
		$("#cardCurrnecyCode").removeAttr("disabled");	
			$("#EDC").removeAttr("disabled");	
			$("#cardCurrencyCode").removeAttr("disabled");		
			$("#PARTNER_DCC_PRDCT").removeAttr("disabled");		
			$("#CUSTOM_HIDDEN_DCC").removeAttr("disabled");		
			$("#CUSTOM_FORCED_DCC").removeAttr("disabled");		
			$("#CUSTOM_STANDARD_DCC").removeAttr("disabled");		
			$("#STANDARD_DCC").removeAttr("disabled");		
			$("#FORCED_DCC").removeAttr("disabled");		
	//		$("#cardCurrencyCodeFont").empty();
		if(payType == 'EDC'){
			$("#cardCurrencyCode").attr("disabled","disabled");	
			$("#PARTNER_DCC_PRDCT").attr("disabled","disabled");	
			$("#CUSTOM_HIDDEN_DCC").attr("disabled","disabled");	
			$("#CUSTOM_FORCED_DCC").attr("disabled","disabled");	
			$("#CUSTOM_STANDARD_DCC").attr("disabled","disabled");	
			$("#STANDARD_DCC").attr("disabled","disabled");	
			$("#FORCED_DCC").attr("disabled","disabled");	
			$("#EDC").attr("checked","checked");
		}else if(payType == 'DCC'){
			$("#EDC").attr("disabled","disabled");	
			$("#PARTNER_DCC_PRDCT").attr("checked","checked");
			$("#CUSTOM_HIDDEN_DCC").attr("checked","checked");
			$("#CUSTOM_FORCED_DCC").attr("checked","checked");
			$("#CUSTOM_STANDARD_DCC").attr("checked","checked");
			$("#STANDARD_DCC").attr("checked","checked");
			$("#FORCED_DCC").attr("checked","checked");
			$("#PARTNER_STANDARD_DCC").attr("checked","checked");
			
		//	$("#cardCurrencyCodeTd").append("<font color='red' id='cardCurrencyCodeFont'>*</font>");
		}
		
	}
		function getChannelRCurrency(){
			var orgCode=$("#orgCode").val();
			$("#channelCurrencyCode").empty();
			$.ajax({
				type: "POST",
				url: "${ctx}/channelItemRCurrency.do?method=getChannelItemCurrencyCode&channelItemCode="+orgCode,
				//data: pars,
				success: function(result) {
					var json=JSON.parse(result);
					for(var k in json){
						$("#channelCurrencyCode").append("<option>"+json[k].currencyCode+"</option>");				
					}
				}
			});	
		}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">渠道币种配置新增</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/channelCurrency.do?method=addChannelCurrency"
	method="post" id="frm">
	<table class="border_all2" width="58%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">渠道名称：</td>
			<td class="border_top_right4"><select name="orgCode"
				id="orgCode" onchange="getChannelRCurrency()">
					<option value="">-----请选择-----</option>
					<c:forEach var="channel" items="${channelItems}">
						<option  value="${channel.code}">${channel.desc}</option>
					</c:forEach>
				</select>   &nbsp;&nbsp; <font color="red">*</font>
			</td>
		</tr>
			<tr class="trForContent1">
			<td class=border_top_right4 align="right">支付类型：</td>
			<td class="border_top_right4">
					<select name="payType" id="payType"  onchange="selectedPrdt();" >
						<option value="">-----请选择-----</option>
						<option value="EDC">EDC</option>
						<option value="DCC">DCC</option>
					</select> &nbsp;&nbsp; <font color="red">*</font>
				</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">产品类型：</td>
			<td class="border_top_right4">
			<input type="checkbox" name="prdtCode" value="EDC" id="EDC"> EDC <br>
		 	<input type="checkbox" name="prdtCode" value="PARTNER_DCC_PRDCT" id="PARTNER_DCC_PRDCT">    自建纯DCC
			<input type="checkbox" name="prdtCode" value="CUSTOM_HIDDEN_DCC" id="CUSTOM_HIDDEN_DCC"> 自建隐藏DCC
			<input type="checkbox" name="prdtCode" value="CUSTOM_FORCED_DCC" id="CUSTOM_FORCED_DCC"> 自建强制DCC 
			<input type="checkbox" name="prdtCode" value="CUSTOM_STANDARD_DCC" id="CUSTOM_STANDARD_DCC"> 自建标准DCC<br>
			<input type="checkbox" name="prdtCode" value="STANDARD_DCC" id="STANDARD_DCC">                               卡司标准DCC
			<input type="checkbox" name="prdtCode" value="FORCED_DCC " id="FORCED_DCC"  >                                     卡司强制DCC
			<input type="checkbox" name="prdtCode" value="PARTNER_STANDARD_DCC" id="PARTNER_STANDARD_DCC"  >             自建纯标准DCC
					&nbsp;&nbsp; <font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">交易币种：</td>
			<td class="border_top_right4">
			<select name="currencyCode" id="currencyCode" >
				<option value="">-----请选择-----</option>
					<option> GBP</option>
					<option> USD</option>
					<option> EUR</option>
					<option> ALL</option>
					<option> AOA</option>
					<option> ARS</option>
					<option> AUD</option>
					<option> BHD</option>
					<option> BDT</option>
					<option> BYR</option>
					<option> BGN</option>
					<option> BOB</option>
					<option> BRL</option>
					<option> BND</option>
					<option> CAD</option>
					<option> CLP</option>
					<option> CNY</option>
					<option> COP</option>
					<option> CDF</option>
					<option> CRC</option>
					<option> HRK</option>
					<option> CZK</option>
					<option> DKK</option>
					<option> EGP</option>
					<option> FJD</option>
					<option> HKD</option>
					<option> HUF</option>
					<option> ISK</option>
					<option> INR</option>
					<option> IDR</option>
					<option> ILS</option>
					<option> JPY</option>
					<option> KRW</option>
					<option> KWD</option>
					<option> LVL</option>
					<option> LTL</option>
					<option> MYR</option>
					<option> MXN</option>
					<option> NZD</option>
					<option> NOK</option>
					<option> PKR</option>
					<option> PHP</option>
					<option> PLN</option>
					<option> RUB</option>
					<option> SAR</option>
					<option> SGD</option>
					<option> ZAR</option>
					<option> SEK</option>
					<option> CHF</option>
					<option> THB</option>
					<option> TRY</option>
					<option> AED</option>
					<option> TWD</option>
					<option> UAH</option>
					<option> MOP</option>
					<option> *</option>
			</select> 
						&nbsp;&nbsp; <font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">卡本币：</td>
			<td class="border_top_right4" id="cardCurrencyCodeTd">
						<input type="text" name="cardCurrencyCode"  id="cardCurrencyCode"  > <font color='red' id='cardCurrencyCodeFont' >*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">送渠道币种：</td>
			<td class="border_top_right4">
				<select name="channelCurrencyCode" id="channelCurrencyCode" >
					<option value="">-----请选择-----</option>
			</select>  &nbsp;&nbsp; <font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="center" colspan="4">
			<input id="btn" type="button" onclick="save();" class="button2" value="保存"> 
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">&nbsp;&nbsp;&nbsp;
								<c:if test="${not empty msg}">
										<font color="red">${msg}</font>
								</c:if>
				</td>
		</tr>
	</table>
</form>