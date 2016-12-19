

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>

<head>
﻿<title></title>
</head>
<script>

/*网页转向*/
function $U(url){
	window.location.href=url;
}

function $E(elemid) {
	return document.getElementById(elemid);
}

function isEmpty(fData)
{
    return ((fData==null) || (fData.length==0) )
} 

function isNumber(sText){
	  if(isEmpty(sText)){
		  return false ;
	  }
	   var ValidChars = "0123456789.";
	   var IsNumber=true;
	   var Char;
	   for (i = 0; i < sText.length && IsNumber == true; i++){
	      Char = sText.charAt(i);
	      if (ValidChars.indexOf(Char) == -1){
	         IsNumber = false;
	      }
	   }
	   return IsNumber;  
}

function check() {
	try{
		var fixedcharge = document.getElementById('fixedCharge').value;
		var chargerate = document.getElementById('chargeRate').value;		
		var maxcharge = document.getElementById('maxCharge').value;
		var mincharge = document.getElementById('minCharge').value;
		var pricestrategytype = document.getElementById('priceStrategyType').value;
		//alert('fixedcharge='+fixedcharge+'   '+'chargerate='+chargerate+'   '+'maxcharge='+maxcharge+'   '+'mincharge='+mincharge+'   '+'pricestrategytype='+pricestrategytype+'   ');
		 //固定费用
		if(pricestrategytype==1 || pricestrategytype==6||pricestrategytype==7||pricestrategytype==9){
			if(!isNumber(fixedcharge)){
				alert('固定费用不能为空!');
				return false ;
			}
		}
		  //费率
		if(pricestrategytype!=1 ){
			if(!isNumber(chargerate)){
				alert('固定费率不能为空!');
				return false ;
				//			 Assert.notNull(pricingDetailDTO.getFixedCharge());
			}
		}
		 //上限
		if(pricestrategytype==4 || pricestrategytype==5||pricestrategytype==7||pricestrategytype==9){
			if(!isNumber(maxcharge)){			
				alert('上限 不能为空!');
				return false ;
			}
		}
		 //下限
		if(pricestrategytype==3 || pricestrategytype==5||pricestrategytype==8||pricestrategytype==9){
			if(!isNumber(mincharge)){			
				alert('下 限 不能为空!');
				return false ;
			}		
		}
	}catch(e){
		alert(e);
		return false ;
	}
	return true;
}

function processRet(){
	window.location.href = "${ctx}/if_poss_datamanage/prstgytemInit.do?priceStrategyCode=${priceStrategyCode}&pricestrategydetailcode=${pricestrategydetailcode}";
}
$(function(){
	jQuery.validator.addMethod("stgyNameLen", function(value,element,param){
		var length=this.getLength($.trim(value),element);
		return this.optional(element)||(length>=param[0]&&length<=param[1]);
	},"策略名称不能超过10个汉字");
	jQuery.validator.addMethod("onlyChn", function(value, element) {
		return  ((/^[\u4e00-\u9fa5]+$/.test(value)));
	}, "只能输入中文字符"); 
	//validate函数
	$("#addForm").validate({
		rules: { 
			templateName:{
				required:true,
				stgyNameLen:[1,10],
				onlyChn:true
			},
			fixedCharge:{
				number:true
			},
			chargeRate:{
				number:true
			},
			rangFrom:{
				number:true
			},
			rangTo:{
				number:true 
			},
			maxCharge:{
				number:true
			},
			minCharge:{
				number:true
			}
		}
	});
	$(".must").each(function(i){
		$(this).html("<span style='color:red'>*</span>"+$(this).html());
	});
	$("#priceStrategyType").change(function(){
		//var b = $("#priceStrategyType").find('option:selected').value();
		var priceStrategyType = document.getElementById('priceStrategyType').value;
		var fixedchargeObj = document.getElementById('fixedCharge');
		var chargerateObj = document.getElementById('chargeRate');		
		var maxchargeObj = document.getElementById('maxCharge');
		var minchargeObj = document.getElementById('minCharge');
		
		if(priceStrategyType==1){//固定费用
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = true ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = true ;
			chargerateObj.value = "";
			maxchargeObj.value = "";
			minchargeObj.value = "";
		}
		if(priceStrategyType==2){//费率
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = true ;	
			fixedchargeObj.value = "";
			maxchargeObj.value = "";
			minchargeObj.value = "";
		}
		if(priceStrategyType==3){//费率及下限
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = false ;
			fixedchargeObj.value = "";
			maxchargeObj.value = "";
		}
		if(priceStrategyType==4){//费率及上限
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = true ;
			fixedchargeObj.value = "";
			minchargeObj.value = "";
		}
		if(priceStrategyType==5){//费率及上下限
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = false ;
			fixedchargeObj.value = "";
		}
		if(priceStrategyType==6){//固定费用_费率
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = true ;
			maxchargeObj.value = "";
			minchargeObj.value = "";
		}
		if(priceStrategyType==7){//固定费用_费率及上限
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = true ;
			minchargeObj.value = "";
		}
		if(priceStrategyType==8){//固定费率_费率及下限
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = false ;
			maxchargeObj.value = "";
		}
		if(priceStrategyType==9){//固定费率_费率及上下限
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = false ;
		}
	});
	 var priceStrategyType = document.getElementById('priceStrategyType').value;
		var fixedchargeObj = document.getElementById('fixedCharge');
		var chargerateObj = document.getElementById('chargeRate');		
		var maxchargeObj = document.getElementById('maxCharge');
		var minchargeObj = document.getElementById('minCharge');
		
		if(priceStrategyType==1){//固定费用
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = true ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = true ;
		}
		if(priceStrategyType==2){//费率
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = true ;	
		}
		if(priceStrategyType==3){//费率及下限
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = false ;
		}
		if(priceStrategyType==4){//费率及上限
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = true ;
		}
		if(priceStrategyType==5){//费率及上下限
			fixedchargeObj.readOnly  = true ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = false ;
		}
		if(priceStrategyType==6){//固定费用_费率
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = true ;
		}
		if(priceStrategyType==7){//固定费用_费率及上限
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = true ;
		}
		if(priceStrategyType==8){//固定费率_费率及下限
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = true ;
			minchargeObj.readOnly  = false ;
		}
		if(priceStrategyType==9){//固定费率_费率及上下限
			fixedchargeObj.readOnly  = false ;
			chargerateObj.readOnly  = false ;
			maxchargeObj.readOnly  = false ;
			minchargeObj.readOnly  = false ;
		}
});
</script>

<body>
<form method="post" action="${ctx}/if_poss_datamanage/prstgytemAddResult.do?priceStrategyCode=${priceStrategyCode}&pricestrategydetailcode=${pricestrategydetailcode}" name="addForm" id="addForm" onsubmit="return check()">

	<h2 class="panel_title">新建价格策略配置模板</h2>
	  <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	  <tr class="trForContent1" >
	   <td class="border_top_right4" width="18%" align="right">策略名称：</td>
	   <td class="border_top_right4" align="left" >
	    	<input  type="text"	id="templateName" name="templateName" maxlength="20"/>
	    </td>
	    <td class="border_top_right4" width="18%" align="right"></td>
	    <td class="border_top_right4" width="18%" align="right"></td>
	    
	  </tr>
	  
	  <tr class="trForContent1" >
	   <td class="border_top_right4" align="right">价格策略类型：</td>
	   <td class="border_top_right4" align="left" >
			<select id="priceStrategyType" name="priceStrategyType"   >
				<c:forEach items="${pricingStrategyCmd.pricestrategytypeMap}" var="item">
					<option value="${item.key}" <c:if test="${item.key == pricingStrategyCmd.pricestrategytype}"> selected="selected" </c:if>>
						${item.value}</option>
				</c:forEach>
			</select>
		</td>
		<td class="border_top_right4" width="18%" align="right"></td>
		<td class="border_top_right4" width="18%" align="right"></td>
	  </tr>
	   
	  <tr class="trForContent1" >
	   <td class="border_top_right4" align="right">固定费用：</td>
	   <td class="border_top_right4" align="left" >
	    	<input id="fixedCharge" name="fixedCharge"  />
	    	 <font style="font-family:MS Gothic;font-size:30px"></font>(厘)<br>
	    </td>
	   <td class="border_top_right4" align="right" >费率：</td>
	   <td class="border_top_right4" align="left" > 
	      <input id="chargeRate" name="chargeRate"   />
		    <font style="font-family:MS Gothic;font-size:30px"></font>(万分之一)<br>
		  </td>
	  </tr>
	 <tr class="trForContent1" >
	   <td class="border_top_right4" align="right">起始金额：</td>
	   <td class="border_top_right4" align="left" >
	    	<input id="rangFrom" name="rangFrom"   />
	     	<font style="font-family:MS Gothic;font-size:30px"></font>(厘) 阶梯费用需设置<br>
	     </td>
	   <td class="border_top_right4" align="right" >终止金额：</td>
	   <td class="border_top_right4" align="left" >
	    	<input id="rangTo" name="rangTo"   />
	     	<font style="font-family:MS Gothic;font-size:30px"></font>(厘)阶梯费用需设置<br>
	      </td>
	  </tr>
	  <tr class="trForContent1" >
	   <td class="border_top_right4" align="right">上限：</td>
	   <td class="border_top_right4" align="left" >
	    <input id="maxCharge" name="maxCharge"   />
	     <font style="font-family:MS Gothic;font-size:30px"></font>(厘)<br>
	     </td>
	   <td class="border_top_right4" align="right" >下限：</td>
	   <td class="border_top_right4" align="left" >
	    <input id="minCharge" name="minCharge"   />
	     <font style="font-family:MS Gothic;font-size:30px"></font>(厘)<br>
	      </td>
	  </tr>
	
	  <tr class="trForContent1" >
	   <td class="border_top_right4" colspan="4" align="center">
	   <input type="submit"  value="确定" />
	    <input type="button"  value="取消" onClick="processRet()" /></td>
	  </tr>
	  
	</table>
	
</form>

<script>
<c:if test="${! empty error}" >
	alert('新增错误信息:'+'${error}');
</c:if>
</script>

</body>
</html>