<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
 
<head>
﻿<title></title>
</head>
<script>
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

/*网页转向*/
function $U(url){
	window.location.href=url;
}

function check() {
	var pricestrategyname = document.getElementById('pricestrategyname').value;
	var effectiveon = document.getElementById('effectiveon').value;

	var pricestrategytype = document.getElementById('pricestrategytype').value;
	var caculatemethod = document.getElementById('caculatemethod').value;
	
	var servicelevelcode = document.getElementById('servicelevelcode').value;
	var membercode = document.getElementById('membercode').value;
	
	var validdate = document.getElementById('validdate').value;
	
	var invaliddate = document.getElementById('invaliddate').value;

	try {
	//	alert(effectiveon+'=   '+isNull(effectiveon));
	} catch(err){
	alert(err);
	alert(err.description) // Prints "'y' is undefined".
	} 
	
	
	//alert('pricestrategytype='+pricestrategytype+'  '+'caculatemethod='+caculatemethod+'  '+'effectiveon='+effectiveon+'  '+'servicelevelcode='+servicelevelcode+'  ');

	if(isNull(pricestrategyname)){
	      alert('名字不能为空');
	      return false;
	}
	
	if(isNull(effectiveon)){
	      alert('作用类型不能为空');
	      return false;
	}

	if(isNull(validdate)){
	      alert('起始生效 日期 不能为空');
	      return false;
	}
	
	if(!isNull(invaliddate)){
	    	if (!isRightDateFormat(validdate)) {
	    		alert("开始日期格式错误！应为YYYY-MM-DD.");
	    		return false;
	    	}
	    	
	    	if (!isRightDateFormat(invaliddate)) {
	    		alert("结束日期格式错误！应为YYYY-MM-DD.");
	    		return false;
	    	}
	    	
	    	if (validdate.length!=0&&invaliddate!=0&&isRightDateFormat(validdate)&&isRightDateFormat(invaliddate)) {
	    		if(validdate>invaliddate){
	  			alert("起始时间不应晚于结束时间");
	  			return false;
	  			}
			}
	     // return false;
	}
	
	 
	  if (!isNull(effectiveon)){
	
	  	if (effectiveon == '2') {
	  		if (isNull(servicelevelcode)) {
	  			 alert('为会员服务等级类型，服务等级不能为空');
	  		      return false;
	  		}
	  	}
	  	if (effectiveon == '3') {
	  		if (isNull(membercode)){
	  			 alert('为会员类型，会员不能为空');
	  		      return false;
	  			}
	  	}
	  	if (effectiveon == '1') {
	  		if (!isNull(membercode) || !isNull(servicelevelcode) ){
	  			  alert('全局类型， 会员，服务等级必须为空');
	  		      return false;
	  			}
	  	}
	  }
	  return true;
}


function effectiveonChange(value) {
	  if('1' == value) {
		 document.getElementById('servicelevelcode').disabled=true;
		 document.getElementById('membercode').disabled=true;
	  } 
	  if('2' == value) {
		  	 document.getElementById('servicelevelcode').disabled=false;
			 document.getElementById('membercode').disabled=true;
	  } 
	  if('3' == value) {
		  document.getElementById('servicelevelcode').disabled=true;
			 document.getElementById('membercode').disabled=false;
	  }
}

function changeWdate(obj){
	var caculatemethod = document.getElementById("caculatemethod").value;
	var myDateFmt='yyyy-MM-01';
	if(caculatemethod == '4'){
		if(obj.name =='validdate'){
			WdatePicker({dateFmt:myDateFmt,minDate:'%y-#{%M+1}-%d'});
		}else{
			WdatePicker({dateFmt:myDateFmt,minDate:'#F{$dp.$D(\'validdate\',{M:1})}'});
		}
	}
	else{
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	}
}
</script>



	<body>
		<form method="post" action="${ctx}/if_poss_datamanage/changePricingStrategy.do?method=submit" name="addForm" id="addForm" onsubmit="return check()">
			<h2 class="panel_title">管理支付服务 &gt;&gt;设置价格策略</h2>
				<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
					<tr class="trForContent1">
						<td class="border_top_right4"  width="15%" height="27" align="right">
							<strong>价格策略代码：</strong>
						</td>
						<td class="border_top_right4"  width="31%" align="left">
							<input   id="pricestrategycode" name="pricestrategycode" value="${pricingStrategyCmd.pricestrategycode}" readonly/>
							
						</td>
						<td class="border_top_right4"  width="18%" align="right">
							价格策略名称：						
						</td>
						<td class="border_top_right4"  width="36%" align="left">
							<input   id="pricestrategyname" name="pricestrategyname" value="${pricingStrategyCmd.pricestrategyname}" />
							
						</td>
					</tr>

					<tr class="trForContent1">
						<td class="border_top_right4"  align="right">价格策略类型：</td>
						<td class="border_top_right4"  align="left" >
								<select id="pricestrategytype" name="pricestrategytype"   >
									<c:forEach items="${pricingStrategyCmd.pricestrategytypeMap}" var="item">
										<option value="${item.key}" <c:if test="${item.key == pricingStrategyCmd.pricestrategytype}"> selected="selected" </c:if>>
									${item.value}</option>
									</c:forEach>
								</select>
						 </td>
						<td class="border_top_right4"  align="right" >
							计算方式：
						</td>
						<td class="border_top_right4"  align="left" >
								<select id="caculatemethod" name="caculatemethod"   >
								<c:forEach items="${pricingStrategyCmd.caculatemethodMap}" var="item">									
									<option value="${item.key}" <c:if test="${item.key == pricingStrategyCmd.caculatemethod}"> selected="selected" </c:if>>
								${item.value}</option>
								</c:forEach>
								</select>	
		 				</td>
					</tr>
					
					<tr class="trForContent1">
						<td class="border_top_right4"  align="right">
							生效范围：
						</td>
						<td class="border_top_right4"  align="left" >
								<select id="effectiveon" name="effectiveon" onchange="effectiveonChange(this.value)">
								<c:forEach items="${pricingStrategyCmd.effectiveonMap}" var="item">									
									<option value="${item.key}" <c:if test="${item.key == pricingStrategyCmd.effectiveon}"> selected="selected" </c:if>>
								${item.value}</option>
								</c:forEach>
								</select>	
						</td>
						
						<td class="border_top_right4"  align="right" >
							服务等级：
						</td>
						<td class="border_top_right4"  align="left" >
							<select id="servicelevelcode" name="servicelevelcode" >
								<option value="" >--</option>
								<c:forEach items="${pricingStrategyCmd.servicelevelcodeMap}" var="item">									
									<option value="${item.key}" <c:if test="${item.key == pricingStrategyCmd.servicelevelcode}"> selected="selected" </c:if>>
								${item.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr class="trForContent1">
						<td class="border_top_right4"  align="right">
							<strong>会员号：</strong>
						</td>
						<td class="border_top_right4"  align="left" >							
								<input type="text" id="membercode" name="membercode" value="${pricingStrategyCmd.membercode}"/>
						</td>
						
						<td class="border_top_right4"  align="right" >
							生效日期：
						</td>
						<td class="border_top_right4"  align="left" >
								<input class="Wdate" type="text" id="validdate" name="validdate" value='${pricingStrategyCmd.validdate}'  onfocus="changeWdate(addForm.validdate)">
						</td>
					</tr>
					
					
					
					<tr class="trForContent1">
					<td class="border_top_right4"  align="right"> 失效日期：</td>
					<td class="border_top_right4"  align="left" >
							<input class="Wdate" type="text" id="invaliddate" name="invaliddate" value='${pricingStrategyCmd.invaliddate}'  onfocus="changeWdate(addForm.invaliddate)">
					</td>
					
						<td class="border_top_right4"  align="right" >
							&nbsp;
						</td>
						<td class="border_top_right4"  align="left" >
							&nbsp;
							
		  
							
							
							<input type="hidden" name="paymentservicecode" value="${pricingStrategyCmd.paymentservicecode}"/>
							<input type="hidden" name="status" value="${pricingStrategyCmd.status}" />
							<input type="hidden" name="pricestrategyseq" value="${pricingStrategyCmd.pricestrategyseq}"/>
						</td>
					</tr>

					<tr class="trForContent1">
						<td colspan="4" class="border_top_right4"  align="center"><input type="hidden" name="paymentServiceType" value="${pricingStrategyCmd.paymentServiceType}"/>
					<input name="button2" type="submit" class="ncss" value="确定" />
					<input name="button2" type="button" class="ncss" value="取消" onClick="$U('${ctx}/if_poss_datamanage/managePricingStrategy.do?paymentservicecode=${pricingStrategyCmd.paymentservicecode}')" />
						</td>
					</tr>
				</table>
				
	

		</form>
		
<script>
<c:if test="${! empty error}" >
	alert('修改错误信息:'+'${error}');
</c:if>

<c:if test="${! empty pricingStrategyCmd.effectiveon}" >	
	effectiveonChange('${pricingStrategyCmd.effectiveon}');
</c:if>
</script>





	</body>
</html>
