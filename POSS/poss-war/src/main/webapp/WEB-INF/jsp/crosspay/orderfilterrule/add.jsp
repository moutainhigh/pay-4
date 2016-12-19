<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商户订单过滤规则新增</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户订单过滤规则新增</h2>
<form action="${ctx}/orderfilter/orderfilterRule.do?method=add" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="memberCode" name="memberCode" value="${orderFilterRule.memberCode}"><span style="color:red">*</span>
	      	<input type="hidden" id="id" name="id" value="${orderFilterRule.id}">
	      </td>
          <td align="right" class="border_top_right4" >时间范围：</td>
	      <td class="border_top_right4">
	      	  <input class="Wdate" type="text" id="startDate" name="startDate"  
	      	   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${oderFilterRule.startTime}">至
	      	   <input class="Wdate" type="text" id="endDate" name="endDate" value="${oderFilterRule.endTime}"  
	      	   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	      </td>
	      <td align="right" class="border_top_right4" width="100">金额限制：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="startAmount" name="startAmount" value="${orderFilterRule.startAmount}">--
	     	<input type="text" id="endAmount" name="endAmount" value="${orderFilterRule.endAmount}">(CNY)
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">IP地区：</td>
	      <td class="border_top_right4">
               <input type="text" id="ipCountryCode" name="ipCountryCode" value="${orderFilterRule.ipCountryCode}">(国家三字码)
	      </td>
	      <td align="right" class="border_top_right4" width="100">卡类型：</td>
	      <td class="border_top_right4">
             <input type="text" id="cardType" name="cardType" value="${orderFilterRule.cardType}">
	      </td>
	      <td align="right" class="border_top_right4" width="100">发卡国：</td>
	      <td class="border_top_right4">
              <input type="text" id="cardCountryCode" name="cardCountryCode" value="${orderFilterRule.cardCountryCode}">(国家三字码)
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">卡本币：</td>
	      <td class="border_top_right4" colspan="5">
	         <input type="hidden"  id="cardCurrency000"  value="${orderFilterRule.cardCurrencyCode}">
	      	 <c:forEach var="currency" items="${currencys}" varStatus="v">
					<input type="radio"  id="cardCurrency_${currency.code}" name="cardCurrencyCode" value="${currency.code}">${currency.code}
			</c:forEach>
			<a href="#" onclick="currencyCancel()" style="color: red">取消</a>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="button"  value="添  加" class="button2" onclick="addCure();">
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
		window.location.href="${ctx}/orderfilter/orderfilterRule.do";
	}
	
	
	function addCure() {
		if(!confirm("您确认要添加该条规则吗？")) {
			return ;
		}
		
		$("#mainfromId").submit();
	} 
	
	$(function(){
		
		var url0="${ctx}/orderfilter/orderfilterRule.do?method=checkData&";
		
        $("#memberCode").blur(function(){
        	var val = $(this).val();
        	if(val.length>0){
        		var url_=url0+"memberCode="+$(this).val()
        	    var id_="memberCode";
        	}
        });
        
        $("#ipCountryCode").blur(function(){
        	var val = $(this).val();
        	if(val.length>0){
        		var url_=url0+"ipCountryCode="+$(this).val();
        		var id_="ipCountryCode";
        		ajaxRequest(url_,id_);
        	}
        });
        
        $("#cardType").blur(function(){
        	var val = $(this).val();
        	if(val.length>0){
        	   var url_=url0+"cardType="+$(this).val();
        	   var id_="cardType";
        	   ajaxRequest(url_,id_);
        	}
        });
        
        $("#cardCountryCode").blur(function(){
        	var val = $(this).val();
        	if(val.length>0){
        		var url_=url0+"cardCountryCode="+$(this).val();
        		var id_="cardCountryCode";
        		ajaxRequest(url_,id_);
        	}
        });
	});
	
	function ajaxRequest(url_,id_){
    	$.ajax({
    		type: "POST",
    		url: url_,
    		success: function(result) {
                alert(result);
                if(result.length>0){
               	 $("#"+id_).val('');
               }
    		}
    	});
	}
    
	function currencyCancel(){
		$("input[id*='cardCurrency_']").attr("checked",false);
	}
  </script>