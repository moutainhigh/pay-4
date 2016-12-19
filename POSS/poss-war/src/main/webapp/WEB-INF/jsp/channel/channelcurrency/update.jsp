<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function save() {
	 	var orgCode=$("#orgCode").val();
		var prdtCode=$("#prdtCode").val();
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
		if (!confirm("确认修改该渠道币种配置？")) {
			return;
		 }
		$("#frm").submit();
	}
	
	function selectedPrdt(){
		var payType=$("#payType").val();
		
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
			$("#PARTNER_STANDARD_DCC").removeAttr("disabled");
			$("#STANDARD_DCC").removeAttr("disabled");		
			$("#FORCED_DCC").removeAttr("disabled");		
			$("#cardCurrencyCodeFont").empty();
		if(payType == 'EDC'){
			$("#cardCurrencyCode").removeAttr("value");	
			$("#cardCurrencyCode").attr("disabled","disabled");	
			$("#PARTNER_DCC_PRDCT").attr("disabled","disabled");	
			$("#CUSTOM_HIDDEN_DCC").attr("disabled","disabled");	
			$("#CUSTOM_FORCED_DCC").attr("disabled","disabled");	
			$("#CUSTOM_STANDARD_DCC").attr("disabled","disabled");
			$("#PARTNER_STANDARD_DCC").attr("disabled","disabled");
			$("#STANDARD_DCC").attr("disabled","disabled");	
			$("#FORCED_DCC").attr("disabled","disabled");	
		}else if(payType == 'DCC'){
			$("#EDC").attr("disabled","disabled");	
			$("#PARTNER_DCC_PRDCT").attr("checked","checked");
			$("#CUSTOM_HIDDEN_DCC").attr("checked","checked");
			$("#CUSTOM_FORCED_DCC").attr("checked","checked");
			$("#CUSTOM_STANDARD_DCC").attr("checked","checked");
			$("#PARTNER_STANDARD_DCC").attr("checked","checked");
			$("#STANDARD_DCC").attr("checked","checked");
			$("#FORCED_DCC").attr("checked","checked");
			$("#cardCurrencyCodeTd").append("<font color='red' id='cardCurrencyCodeFont'>*</font>");
		}
		
	}
	

	$(function(){
				//当页面加载完成的时候，自动调用该方法
		        window.onload=function(){
		              //获得所要回显的值，此处为：100,1001,200,1400
		              var checkeds = $("#meidaHidden").val();
		              //拆分为字符串数组
		              var checkArray =checkeds.split(",");
		              //获得所有的复选框对象
		             var checkBoxAll = $("input[name='prdtCode']");
		             //获得所有复选框（新闻,微信,论坛，问答，博客，平媒）的value值，然后，用checkArray中的值和他们比较，如果有，则说明该复选框被选中
		             for(var i=0;i<checkArray.length;i++){
		                 //获取所有复选框对象的value属性，然后，用checkArray[i]和他们匹配，如果有，则说明他应被选中
		                 $.each(checkBoxAll,function(j,checkbox){
		                     //获取复选框的value属性
		                     var checkValue=$(checkbox).val();
		                     if(checkArray[i]==checkValue){
		                         $(checkbox).attr("checked",true);
		                    }
		                 })
		             }
				};
			$.ajax({
				type: "POST",
				url: "${ctx}/channelItemRCurrency.do?method=getChannelItemCurrencyCode&channelItemCode="+${map.orgCode},
				//data: pars,
				success: function(result) {
					var json=JSON.parse(result);
					for(var k in json){
						$("#channelCurrencyCode").append("<option>"+json[k].currencyCode+"</option>");				
					}
				}
			});	
	});
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

<h2 class="panel_title">渠道币种配置修改</h2>
<form action="${ctx}/channelCurrency.do?method=updateChannelCurrency"
	method="post" id="frm">
		<input type="hidden" name="id" value="${map.id }">
	<table class="border_all2" width="58%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">渠道：</td>
			<td class="border_top_right4">
				<select name="orgCode"	id="orgCode" onchange="getChannelRCurrency();">
					<c:forEach var="channel" items="${channelItems}">
						<option value="${channel.code}"<c:if test="${channel.code== map.orgCode}">selected</c:if>>${channel.desc}</option>  &nbsp;&nbsp; <font color="red">*</font>
					</c:forEach>
				</select> &nbsp;&nbsp; <font color="red">*</font>
			</td>
		</tr>
				<tr class="trForContent1">
			<td class=border_top_right4 align="right">支付类型：</td>
			<td class="border_top_right4">
					<select name="payType" id="payType"  style="width: 115px;height: 17px" onchange="selectedPrdt();" >
						<option value="EDC"  <c:if test="${map.payType ==  'EDC'}">selected</c:if>>EDC</option>
						<option value="DCC" <c:if test="${map.payType == 'DCC'}">selected</c:if>>DCC</option>
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
			<input type="checkbox" name="prdtCode" value="PARTNER_STANDARD_DCC " id="PARTNER_STANDARD_DCC"  >  自建纯标准DCC
			<p>	${map.prdtCode }
		  	<input type="hidden" value="${map.prdtCode }" id="meidaHidden">
			   &nbsp;&nbsp; <font color="red">*</font>
		<td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">交易币种：</td>
			<td class="border_top_right4">
			<select name="currencyCode" id="currencyCode"  style="width: 115px;height: 17px">
					<option <c:if test="${map.currencyCode =='GBP' }">selected</c:if>>GBP</option>
					<option <c:if test="${map.currencyCode =='USD' }">selected</c:if>>USD</option>
					<option <c:if test="${map.currencyCode =='EUR' }">selected</c:if>>EUR</option>
					<option <c:if test="${map.currencyCode =='ALL' }">selected</c:if>>ALL</option>
					<option <c:if test="${map.currencyCode =='AOA' }">selected</c:if>>AOA</option>
					<option <c:if test="${map.currencyCode =='ARS' }">selected</c:if>>ARS</option>
					<option <c:if test="${map.currencyCode =='AUD' }">selected</c:if>>AUD</option>
					<option <c:if test="${map.currencyCode =='BHD' }">selected</c:if>>BHD</option>
					<option <c:if test="${map.currencyCode =='BDT' }">selected</c:if>>BDT</option>
					<option <c:if test="${map.currencyCode =='BYR' }">selected</c:if>>BYR</option>
					<option <c:if test="${map.currencyCode =='BGN' }">selected</c:if>>BGN</option>
					<option <c:if test="${map.currencyCode =='BOB' }">selected</c:if>>BOB</option>
					<option <c:if test="${map.currencyCode =='BRL' }">selected</c:if>>BRL</option>
					<option <c:if test="${map.currencyCode =='BND' }">selected</c:if>>BND</option>
					<option <c:if test="${map.currencyCode =='CAD' }">selected</c:if>>CAD</option>
					<option <c:if test="${map.currencyCode =='CLP' }">selected</c:if>>CLP</option>
					<option <c:if test="${map.currencyCode =='CNY' }">selected</c:if>>CNY</option>
					<option <c:if test="${map.currencyCode =='COP' }">selected</c:if>>COP</option>
					<option <c:if test="${map.currencyCode =='CDF' }">selected</c:if>>CDF</option>
					<option <c:if test="${map.currencyCode =='CRC' }">selected</c:if>>CRC</option>
					<option <c:if test="${map.currencyCode =='HRK' }">selected</c:if>>HRK</option>
					<option <c:if test="${map.currencyCode =='CZK' }">selected</c:if>>CZK</option>
					<option <c:if test="${map.currencyCode =='DKK' }">selected</c:if>>DKK</option>
					<option <c:if test="${map.currencyCode =='EGP' }">selected</c:if>>EGP</option>
					<option <c:if test="${map.currencyCode =='FJD' }">selected</c:if>>FJD</option>
					<option <c:if test="${map.currencyCode =='HKD' }">selected</c:if>>HKD</option>
					<option <c:if test="${map.currencyCode =='HUF' }">selected</c:if>>HUF</option>
					<option <c:if test="${map.currencyCode =='ISK' }">selected</c:if>>ISK</option>
					<option <c:if test="${map.currencyCode =='INR' }">selected</c:if>>INR</option>
					<option <c:if test="${map.currencyCode =='IDR' }">selected</c:if>>IDR</option>
					<option <c:if test="${map.currencyCode =='ILS' }">selected</c:if>>ILS</option>
					<option <c:if test="${map.currencyCode =='JPY' }">selected</c:if>>JPY</option>
					<option <c:if test="${map.currencyCode =='KRW' }">selected</c:if>>KRW</option>
					<option <c:if test="${map.currencyCode =='KWD' }">selected</c:if>>KWD</option>
					<option <c:if test="${map.currencyCode =='LVL' }">selected</c:if>>LVL</option>
					<option <c:if test="${map.currencyCode =='LTL' }">selected</c:if>>LTL</option>
					<option <c:if test="${map.currencyCode =='MYR' }">selected</c:if>>MYR</option>
					<option <c:if test="${map.currencyCode =='MXN' }">selected</c:if>>MXN</option>
					<option <c:if test="${map.currencyCode =='NZD' }">selected</c:if>>NZD</option>
					<option <c:if test="${map.currencyCode =='NOK' }">selected</c:if>>NOK</option>
					<option <c:if test="${map.currencyCode =='PKR' }">selected</c:if>>PKR</option>
					<option <c:if test="${map.currencyCode =='PHP' }">selected</c:if>>PHP</option>
					<option <c:if test="${map.currencyCode =='PLN' }">selected</c:if>>PLN</option>
					<option <c:if test="${map.currencyCode =='RUB' }">selected</c:if>>RUB</option>
					<option <c:if test="${map.currencyCode =='SAR' }">selected</c:if>>SAR</option>
					<option <c:if test="${map.currencyCode =='SGD' }">selected</c:if>>SGD</option>
					<option <c:if test="${map.currencyCode =='ZAR' }">selected</c:if>>ZAR</option>
					<option <c:if test="${map.currencyCode =='SEK' }">selected</c:if>>SEK</option>
					<option <c:if test="${map.currencyCode =='CHF' }">selected</c:if>>CHF</option>
					<option <c:if test="${map.currencyCode =='THB' }">selected</c:if>>THB</option>
					<option <c:if test="${map.currencyCode =='TRY' }">selected</c:if>>TRY</option>
					<option <c:if test="${map.currencyCode =='AED' }">selected</c:if>>AED</option>
					<option <c:if test="${map.currencyCode =='TWD' }">selected</c:if>>TWD</option>
					<option <c:if test="${map.currencyCode =='UAH' }">selected</c:if>>UAH</option>
					<option <c:if test="${map.currencyCode =='MOP' }">selected</c:if>>MOP</option>
					<option <c:if test="${map.currencyCode =='*' }">selected</c:if>>*</option>
			</select> 
			   &nbsp;&nbsp; <font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">卡本币：</td>
			<td class="border_top_right4"  id="cardCurrencyCodeTd">
					<input name="cardCurrencyCode" id="cardCurrencyCode" style="width: 115px;height: 17px" value="${map.cardCurrencyCode }">
				  &nbsp;&nbsp; 
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">送渠道币种：</td>
			<td class="border_top_right4">
				<select name="channelCurrencyCode" id="channelCurrencyCode" style="width: 115px;height: 17px">
				<option>${map.channelCurrencyCode }</option>
			</select>  &nbsp;&nbsp; <font color="red">*</font>
			</td>
		</tr>

		<tr class="trForContent1">
			<td class="border_top_right4" align="center" colspan="4"><input id="btn" type="button"
				onclick="save();" class="button2" value="保存"> <input
				type="button" class="button2" onclick="window.history.go(-1);"
				value="返回"></td>
		</tr>
	</table>
</form>

