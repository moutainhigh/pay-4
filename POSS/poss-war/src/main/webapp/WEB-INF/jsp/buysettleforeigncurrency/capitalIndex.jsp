<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">

		function position(){
				$('#positionDiv').dialog({ 
					position:["center","center"],
					width:550,
					height:256,
					modal:true, 	
					title:'调拨头寸申请', 
					overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
				});
		}
		function back(){
			$('#positionDiv').dialog("close");
		}
	function update(id,ele,currency){
			var precautiousLineAmount=$(ele).prev().val();
			var status ="";
			if($("#"+currency).attr("checked")){
				if (!confirm("是否需要开通"+currency+"的账户 ? ?")) {
					return;
				 }
					status = '1';
			}else {
				if (!confirm("是否需要关闭"+currency+"的账户 ? ?")) {
					return;
				 }
					status = '0';
			}
			window.location.href="${ctx}/capitalPoolManage.do?method=update&id="+id+"&precautiousLineAmount="+precautiousLineAmount+"&status="+status;
	}
	
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function decimals(obj){
		var markup=obj.value;//获取markup
		if(markup && markup.indexOf(".")==-1){
			var index;
			var zeroCount=0; 
			for(var i=0;i<markup.length;i++){
				if(markup.charAt(i)!="0"){
					index=i;
					break;
				}else{
					zeroCount++;	
				}
			}
			if(zeroCount==markup.length){
				$(obj).attr("value","");
			}else if(index){
				$(obj).attr("value",markup.substr(index));
			}
		}else if(markup && markup.indexOf(".")>-1){
			var left=markup.split(".")[0];
			var right=markup.split(".")[1];
			var zeroCount=0;  
			for(var i=0;i<left.length;i++){
				if(left.charAt(i)=="0"){
					zeroCount++;
				}else{
					break;
				}
			}
			if(zeroCount==left.length){
				left=left.substr(zeroCount-1);
				markup=left+"."+right;
				$(obj).attr("value",left+"."+right);
			}else if(zeroCount>0){
				left=left.substr(zeroCount);
				markup=left+"."+right;
				$(obj).attr("value",left+"."+right);
			}
			if(right.length>2){
				markup=markup.substring(0,markup.indexOf(".")+3);
				$(obj).attr("value",markup);
			}
		}
		reckonRate();
	}
	
	function reckonRate(){
		var callInAmount =$("#callInAmount").val();
		var callOutAmount =$("#callOutAmount").val();
		if(callInAmount != '' && callOutAmount != '' ){
			$("#rate").html(callOutAmount/callInAmount);			
		}
	}
	
	function addCurrency(ele,id){
		var currency=$(ele).val();
		$("#"+id).html(currency);
	}
	
	
	function validateConfig(){
		var callInAmount =$('#callInAmount').val();
		var callInCurrencyCode=$('#callInAccount')	.val();
		var callOutAmount=$('#callOutAmount').val();
		var callOutCurrencyCode=	$('#callOutAccount').val();
		if(callOutCurrencyCode == ''){
			alert("请选择多头寸账户！！！")
			return;
		}
		if(callOutAmount == ''){
			alert("请输入多头寸调出金额！！！")
			return;
		}
		if(callInCurrencyCode == ''){
			alert("请选择缺头寸账户！！！")
			return;
		}
		if(callInAmount == ''){
			alert("请输入缺头寸调出金额！！！")
			return;
		}
		var callInAccount=$('#callInAccount').find("option:selected").html();
		var callOutAccount=	$('#callOutAccount').find("option:selected").html();
		$("input[name='callInAccountDesc']").attr("value",callInAccount);
		$("input[name='callOutAccountDesc']").attr("value",callOutAccount);
		$("#positionForm").submit()	;
	}
</script>
<h2 class='panel_title'>资金池头寸管理</h2>
<h2><font color="red">${info}</font></h2>
<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		
	<c:forEach items="${result}" var="ca" >
			<tr class="trForContent1">
				<td class="border_top_right4"> 
				<input type="checkbox"   id="${ca.currencyCode}" <c:if test = "${ca.status== '1' }">checked="checked" </c:if>>   开通为购汇账户</td>
				<td class="border_top_right4">
					<c:choose>
							<c:when test="${ca.currencyCode=='CNY'}">人民币头寸账户</c:when>
							<c:when test="${ca.currencyCode=='USD'}">美元头寸账户</c:when>
							<c:when test="${ca.currencyCode=='EUR'}">欧元头寸账户</c:when>
							<c:when test="${ca.currencyCode=='GBP'}">英镑头寸账户</c:when>
							<c:when test="${ca.currencyCode=='HKD'}">港元头寸账户</c:when>
							<c:when test="${ca.currencyCode=='AUD'}">澳元头寸账户</c:when>
							<c:when test="${ca.currencyCode=='CAD'}">加元头寸账户</c:when>
							<c:when test="${ca.currencyCode=='JPY'}">日元头寸账户</c:when>
							<c:when test="${ca.currencyCode=='SGD'}">新加坡元头寸账户</c:when>
						</c:choose>	
				</td>
				<td class="border_top_right4">
						币种:  ${ca.currencyCode}		
				</td>
				<td  class="border_top_right4">
						可购汇金额:
				</td>
				<td  class="border_top_right4" >
					<c:if test="${ca.buyAmount <'0'}">
					<span style="color: red">${ca.buyAmount}</span>			
					</c:if>
					<c:if test="${ca.buyAmount>='0'}">
						<span>${ca.buyAmount}</span>			
					</c:if>
				</td>
				<td  class="border_top_right4">
						预警线金额:
				</td>
				<td class="border_top_right4">
						<input  type="text"  value="${ca.precautiousLineAmount/1000}" onkeyup="checkNum(this);" onblur="decimals(this)"><input  onclick="update('${ca.id}',this,'${ca.currencyCode}');"  type="button" value="修改">
				</td>
			</tr>
	</c:forEach>
	<tr class="trForContent1"> <td  class="border_top_right4" colspan="7"  align="center"><input  onclick="position();"  name=""  type="button" value="调拔头寸"></td></tr>
</table>

<div id="positionDiv" style="display: none" align="center" >
<form id="positionForm" name="positionForm"  method="post"  action="${ctx}/capitalPoolManage.do?method=addAudit">
<input name="callOutAccountDesc"  	type="hidden">
<input name="callInAccountDesc"   		type="hidden">

<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
				<td colspan="1" align="right"  class="border_top_right4">	
					多头寸账户：
				</td>	
				<td colspan="2" align="left"  class="border_top_right4">
					<select name="callOutAccount"  id = "callOutAccount"  onchange="addCurrency(this,'callOutCurrencyCode');">
							<option value="">----请选择----</option>
							<option value="CNY">人民币头寸账户</option>
							<option  value="USD">美元头寸账户</option>
							<option  value="EUR">欧元头寸账户</option>
							<option  value="GBP">英镑头寸账户</option>
							<option  value="HKD">港元头寸账户</option>
							<option  value="AUD">澳元头寸账户</option>
							<option  value="CAD">加元头寸账户</option>
							<option  value="JPY">日元头寸账户</option>
							<option   value="SGD">新加坡头寸账户</option>
					</select>
				</td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
		</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right"  class="border_top_right4">	
				多头寸账户调出：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
				 <input name="callOutAmount" id="callOutAmount" onkeyup="checkNum(this);" onblur="decimals(this)"><span id = "callOutCurrencyCode"></span>
			</td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
		</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
				 缺头寸账户：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
				<select name="callInAccount"  id = "callInAccount" onchange="addCurrency(this,'callInCurrencyCode');">
							<option value="">----请选择----</option>
							<option value="CNY">人民币头寸账户</option>
							<option  value="USD">美元头寸账户</option>
							<option  value="EUR">欧元头寸账户</option>
							<option  value="GBP">英镑头寸账户</option>
							<option  value="HKD">港元头寸账户</option>
							<option  value="AUD">澳元头寸账户</option>
							<option  value="CAD">加元头寸账户</option>
							<option  value="JPY">日元头寸账户</option>
							<option   value="SGD">新加坡头寸账户</option>
					</select>
			</td>
	</tr>
		<tr class="trForContent1">
			<td colspan="3" align="left" class="border_top_right4"></td>
		</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
			缺头寸账户调入：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
			 <input name="callInAmount" id="callInAmount" onkeyup="checkNum(this);" onblur="decimals(this)"><span id = "callInCurrencyCode"></span>
			</td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
	</tr>
	<tr class="trForContent1">
			<td colspan="1" align="right" class="border_top_right4">
			 调拨汇率：
			</td>
			<td colspan="2" align="left" class="border_top_right4">
					<span id = "rate"> </span>
			</td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="left" class="border_top_right4"></td>
	</tr>
	<tr class="trForContent1">
		<td colspan="3" align="center">
			<input type="button" value="确定" onclick="validateConfig();"> &nbsp;&nbsp;&nbsp;
			<input type="button" value="取消" onclick="back();">
		</td>		
	</tr>
</table>
</form>
</div>