<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
 
<head>
﻿<title></title>
</head>


<script>


function $U(url){
	//alert(url);
	//
	window.location.href=url;
}

function $E(elemid) {
	return document.getElementById(elemid);
}

function isEmpty(fData)
{
    return ((fData==null) || (fData.length==0) )
} 
function isRightDateFormat(date) {
	if(date==null||date.length==0){
		return false;
	}
	var isRight = date.match(new RegExp("^(\\d{4})([-]?)(\\d{1,2})([-]?)(\\d{1,2})$"));
	if (isRight== null) {
		return false;
	}
	return true;
}
function isNull(s){
    if (s == null || s.length <= 0 ){
            return true;
    }
    return false;
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
	
		var fixedcharge = document.getElementById('fixedcharge').value;
		var chargerate = document.getElementById('chargerate').value;		
		var maxcharge = document.getElementById('maxcharge').value;
		var mincharge = document.getElementById('mincharge').value;
		var rangfrom = document.getElementById('rangfrom').value;
		var rangto = document.getElementById('rangto').value;
		var pricestrategytype = ${pricingStetagyDto.priceStrategyType} +0;
		var reservedCode = document.getElementById('reservedCode').value;
		/*
		var validdate = document.getElementById('validdate').value;
		var invaliddate = document.getElementById('invaliddate').value;
		var effectivefrom = document.getElementById('effectivefrom').value;
		var effectiveto = document.getElementById('effectiveto').value;
		*/
		//alert(pricestrategytype);
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
			/*
			if(pricestrategytype==7||pricestrategytype==9){
				if(fixedcharge+mincharge >=maxcharge){
					alert("fixcharge + mincharge must less than maxcharge");
					return false ;
				}
			}
			*/
		}
	
		 //下限
		if(pricestrategytype==3 || pricestrategytype==5||pricestrategytype==8||pricestrategytype==9){
			if(!isNumber(mincharge)){
				alert('下 限 不能为空!');
				return false ;
			}		
		}
		if(!isNull(rangfrom)){
			if(!isNumber(rangfrom)){
				alert("起始金额填写有误");
				return false;
			}
		}
		if(!isNull(rangto)){
			if(!isNumber(rangto)){
				alert("终止金额填写有误");
				return false;
			}
		}
		if(parseInt(rangfrom)>parseInt(rangto)){
			alert("起始金额不能大于终止金额");
			return false;
		}
		if(!isNull(reservedCode)){
			if(!isNumber(reservedCode)) {
				alert('对方会员号填写有误');
				return false;
			}
		}
		/*
		if(isNull(effectivefrom)){
			if(!isNull(effectiveto)){
				alert("起始时间结束时间填写有误");
				return false;
			}
		}else{
			if (!isRightDateFormat(effectivefrom)) {
		    	alert("开始日期格式错误！应为YYYY-MM-DD.");
		    	return false;
		    }
		    if(isNull(effectiveto)){
		    	if(validdate>effectivefrom){
			  		alert("开始时间需在生效日期和失效日期之间");
			  		return false;
		  		}
		  		if(!isNull(invaliddate)){
		  			alert("结束时间需在生效日期和失效日期之间");
			  		return false;
		  		}
		    }else{
			    if (!isRightDateFormat(effectiveto)) {
			    	alert("结束时间格式错误！应为YYYY-MM-DD.");
			    	return false;
			    }
			    if(effectivefrom>effectiveto){
			    	alert("开始时间需早于结束时间");
			  		return false;
			    }
		    	if(validdate>effectivefrom ){
			  		alert("开始时间需在生效日期和失效日期之间");
			  		return false;
		  		}
		  		if(!isNull(invaliddate)){
		  			if(invaliddate<effectiveto){
		  				alert("结束时间需在生效日期和失效日期之间");
		  				return false;
		  			}
		  		}
		    }
		}*/
	
	}catch(e){
		alert(e);
		return false ;
	}
	//alert(true);
	return true;
}


</script>


<body>

<form method="post" action="${ctx}/if_poss_datamanage/changePricingStrategyDetail.do?method=submit" name="addForm" id="addForm" onsubmit="return check()">
 
<h2 class="panel_title">管理支付服务 &gt;&gt;修改价格策略明细</h2>
  <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
  <tr class="trForContent1">
    <td class="border_top_right4" width="15%" height="27" align="right">支付服务代码：</td>
    <td class="border_top_right4" width="31%" align="left">
    	<input  type="text" name="paymentservicecode" value="${pricingStrategyCmd.paymentservicecode}" readonly/>
    </td>
    <td class="border_top_right4" width="18%" align="right">支付服务名称：</td>
    <td class="border_top_right4" width="36%" align="left">
	    <input  type="text" name="paymentservicename" value="${pricingStrategyCmd.paymentservicename}" readonly/>
    </td>
  </tr>
  
  <tr class="trForContent1">
    <td class="border_top_right4" height="27" colspan="4" align="right"></td>
  </tr>
  
  
  <tr class="trForContent1">
    <td class="border_top_right4" align="right">价格策略代码：</td>
    <td class="border_top_right4" align="left" >
    	<input  type="text"	    name="pricestrategycode"	    value="${pricingStrategyCmd.pricestrategycode}" readonly/>
    </td>
    <td class="border_top_right4" align="right" >价格策略名称：</td>
    <td class="border_top_right4" align="left" >
    	<input  type="text" id="pricestrategyname" name="pricestrategyname"	    value="${pricingStrategyCmd.pricestrategyname}" readonly/>
    </td>
  </tr>
  
   <tr class="trForContent1">
    <td class="border_top_right4" align="right">价格策略类型：</td>
    <td class="border_top_right4" align="left" >
    	${pricingStetagyDto.priceStrategyTypeDesc}
    </td>
      <td class="border_top_right4" align="right" >计算方式：</td>
      <td class="border_top_right4" align="left">${pricingStetagyDto.caculateMethodDesc}</td>
  </tr>
  
  

  <tr class="trForContent1">
    <td colspan="4" class="border_top_right4" align="center">
       		<a href="#" onClick="$U('${ctx}/if_poss_datamanage/prstgytemInit.do?priceStrategyCode=${pricingStetagyDto.priceStrategyCode}&pricestrategydetailcode=${pricingStrategyCmd.pricestrategydetailcode}')">选择价格策略</a> 
  </tr>
  <tr class="trForContent1">
    <td class="border_top_right4" align="right">固定费用：</td>
    <td class="border_top_right4" align="left" >
    <input id="fixedcharge" name="fixedcharge" value='${pricingStrategyCmd.fixedcharge}' readonly/>
     <font style="font-family:MS Gothic;font-size:30px"></font>(厘)<br>
    
    </td>
    
    <td class="border_top_right4" align="right" >费率：</td>
    <td class="border_top_right4" align="left" >
    <input  type="text" name="chargerate"	    id="chargerate"	    value="${pricingStrategyCmd.chargerate}" readonly/>
	    <font style="font-family:MS Gothic;font-size:30px"></font>(万分之一)</td>
  </tr>
  <tr class="trForContent1">
    <td class="border_top_right4" align="right">起始金额：</td>
    <td class="border_top_right4" align="left" >
    	<input id="rangfrom" name="rangfrom" value='${pricingStrategyCmd.rangfrom}'  />
     	<font style="font-family:MS Gothic;font-size:30px"></font>(厘) 阶梯费用需设置<br>
     </td>
    <td class="border_top_right4" align="right" >终止金额：</td>
    <td class="border_top_right4" align="left" >
    	<input id="rangto" name="rangto" value='${pricingStrategyCmd.rangto}'  />
     	<font style="font-family:MS Gothic;font-size:30px"></font>(厘)阶梯费用需设置<br>
      </td>
  </tr>
  <tr class="trForContent1">
    <td class="border_top_right4" align="right">上限：</td>
    <td class="border_top_right4" align="left" >
      <input name="maxcharge" value='${pricingStrategyCmd.maxcharge}' id="maxcharge" readonly/>
      <font style="font-family:MS Gothic;font-size:30px"></font>(厘)<br>
    </td>
    <td class="border_top_right4" align="right" >下限：</td>
    <td class="border_top_right4" align="left" >
    <input name="mincharge" value='${pricingStrategyCmd.mincharge}' id="mincharge" readonly/>
     <font style="font-family:MS Gothic;font-size:30px"></font>(厘)<br>
      </td>
  </tr>
  <!-- 
   <tr class="trForContent1">
	  	<td class="border_top_right4" align="right" >开始时间：</td>
				    <td class="border_top_right4" align="left" >	    
					    <input class="Wdate" type="text" id="effectivefrom" name="effectivefrom" value='${pricingStrategyCmd.effectivefrom}'  onClick="WdatePicker()">
					</td>
		<td class="border_top_right4" align="right"> 结束时间：</td>
				    <td class="border_top_right4" align="left" >
				   		<input class="Wdate" type="text" id="effectiveto" name="effectiveto" value='${pricingStrategyCmd.effectiveto}'  onClick="WdatePicker()">
					</td>
  	</tr>
  	 -->
    <c:if test="${paymentServiceDto.reservedCodeType == 1}" >
    <tr class="trForContent1">
  
  		<td class="border_top_right4" align="right">对方会员号：</td>
  		<td class="border_top_right4" align="left" >
    	<input id="reservedCode" name="reservedCode" value='${pricingStrategyCmd.reservedCode}'  />
    	</td>
  	</tr>
  	<tr class="trForContent1">
  	 	<td class="border_top_right4" align="right">&nbsp;</td>
  	 	<td class="border_top_right4" align="left" ><font style="font-family:MS Gothic;font-size:30px"></font>对方会员号是指针对该会员下的所有付款用户进行收集，若有请填写，若无不需要填写<br></td>
  	</tr>
  </c:if>

  
  
  <tr class="trForContent1">
    <td colspan="4" class="border_top_right4" align="center">
      <c:if test="${paymentServiceDto.reservedCodeType != 1}" >
  		<input type="hidden" name="reservedCode" id="reservedCode" value='${pricingStrategyCmd.reservedCode}'>

  </c:if>
    <input type="hidden"	    name="pricestrategydetailcode"	    value="${pricingStrategyCmd.pricestrategydetailcode}" />
	    <input type="hidden" name="pricestrategytype" value=${pricingStrategyCmd.pricestrategytype}>
		<input type="hidden" name="caculatemethod" value="${pricingStrategyCmd.caculatemethod}">
		<input type="hidden" name="validdate" id="validdate" value="${pricingStrategyCmd.validdate}">
		<input type="hidden" name="invaliddate" id="invaliddate"  value="${pricingStrategyCmd.invaliddate}">
		
		<input  type="submit"  value="确定" />
      	<input  type="button"  value="取消" onClick="window.location.href='${ctx}/if_poss_datamanage/managePricingStrategyDetail.do?priceStrategyCode=${pricingStrategyCmd.pricestrategycode}'  "/>
    </td>

  </tr>
</table>




</form>

<script>
<c:if test="${! empty error}" >
	alert('修改错误信息:'+'${error}');
</c:if>


var priceStrategyType = ${pricingStetagyDto.priceStrategyType} ;
//alert(priceStrategyType);

//alert(document.getElementById('pricestrategyname').readOnly );


var fixedchargeObj = document.getElementById('fixedcharge');
var chargerateObj = document.getElementById('chargerate');		
var maxchargeObj = document.getElementById('maxcharge');
var minchargeObj = document.getElementById('mincharge');

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
	fixedchargeObj.readOnly  =  false;
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
</script>








</body>
</html>