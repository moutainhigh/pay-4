<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">人工交易提交基本配置</h2>
<form action="manualTransSub.do" method="post" id="mainfromId" name="mainfrom">
  <input type="hidden" name="method" value="saveArtificialTradeBaseConf"> 
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center" >	
	<tr class="trForContent1">
		<td class=border_top_right4 align="right" >币种：</td>
      		<td class="border_top_right4" >
        	<select  name="currencyCode" id="currencyCode" style="width:132px; height:20px;">
	      			<option value="">--请选择--</option>
	      			<option value="USD">美元</option>
	      			<option value="EUR">欧元</option>
	      			<option value="CNY">人民币</option>
	      	</select>
      	</td>
      </tr>
      <tr class="trForContent1">
      	<td align="right" class="border_top_right4">提交金额：</td>
      	<td class="border_top_right4">
	      	<input type="text" name="AmountMin" id="AmountMin"  onkeyup="checkNum(this);" onblur="decimals(this)" >
	        	～
			<input type="text" name="AmountMax" id="AmountMax" onkeyup="checkNum(this);" onblur="decimals(this)" >
      	</td>
      	</tr>
   <!-- 	<tr class="trForContent1">
      	<td class=border_top_right4 align="right" >卡片每日提交交易限次：</td>
      		<td class="border_top_right4" >
      		<input type="text" name="tradingTimes" id="tradingTimes" onkeyup="checkNum(this);">
      	  	</td>
   	 </tr> -->
   	<tr class="trForContent1">
      	<td class=border_top_right4 align="right" >单次交易间隔时长(s)：</td>
      	  <td class="border_top_right4" >
	      	<input type="text" name="sleepStartTime" onkeyup="checkNum(this);">
	        	～
			<input type="text" name="sleepEndTime" onkeyup="checkNum(this);" >
      	 </td>
   	 </tr>
   	 	<tr class="trForContent1">
      	<td class=border_top_right4 align="center" colspan="2" ><input type="button" onclick="basicConfSub();" value="保 存"> &nbsp;&nbsp;&nbsp;
		<input type="button" onclick="back();" value="返 回"></td>
      	</tr>
 </table>
 </form>
<script type="text/javascript">
	function back(){
		window.location.href = 	"${ctx}/manualTransSub.do?method=index"; 		
	}
	function checkNum(obj) {
	//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function decimals(obj){
		var markup=obj.value;//获取markup
	 	var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	    if(!exp.test(markup)){
	    	alert('请输入正确的金额！');
	    	return;
	  	}
	}
	function basicConfSub(){
		var	currencyCode=$("#currencyCode").val();
		if(currencyCode==""){
			alert("请选择币种");
			return;
		}
		var AmountMin=$("#AmountMin").val();
		//alert(AmountMin)
		if(AmountMin==""){
			alert("请输入最小金额！");
			return;
		}
		var AmountMax=$("#AmountMax").val();
		if(AmountMax==""){
			alert("请输入最大金额！");
			return;
		}	
		if(AmountMax<AmountMin){
			alert("最大的金额不能小于最小金额！");
			return;
		}
		var tradingTimes=$("#tradingTimes").val();
		if(tradingTimes==""){
			alert("请输入卡片每日成功交易限次！");
			return;
		}	
		var estimatedTime=$("#estimatedTime").val();
		if(estimatedTime==''){
			alert("请输入交易时长！！");
			return;
		}
		$("#mainfromId").submit();
	}
</script>