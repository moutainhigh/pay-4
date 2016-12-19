<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">手工调账申请操作<!-- 银行多 --></font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="reconcile.reviseApply.do?method=handerReviseApply" method="post" name="mainfrom">
	<input type="hidden" name="id" value="${ reconcileResultFunds.id}" />
	<input type="hidden" name="busiFlag" value="${ reconcileResultFunds.busiFlag}" />
	<input type="hidden" name="adjustType" value="${adjustType}" />
	
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >银行名称：</td>
	      <td class="border_top_right4">
	      	<input type="hidden" name="bankCode" value="${ reconcileResultFunds.bankCode}" />
	      	${ reconcileResultFunds.bankCode}
	      </td>
	    </tr>
	  
	     <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >服务代码：</td>
	      <td class="border_top_right4" >
	      <input type="hidden" name="providerCode" value="${ reconcileResultFunds.providerCode}" />
	        	${reconcileResultFunds.providerCode}
	      </td>
	      </tr>
	      
	      <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >交易日期：</td>
	      <td class="border_top_right4" >
	      <input type="hidden" name="cutTime" value="<fmt:formatDate value="${reconcileResultFunds.cutTime}" type="date"/>" />
	        <fmt:formatDate value="${reconcileResultFunds.busiTime}" type="date"/>
	      </td>
	    </tr>
	        <tr  class="trForContent1">
	        <td class=border_top_right4 align="right" >错账方：</td>
	      	<td class="border_top_right4" align="left" >
				银行
	      	</td>
	      </tr>
	    <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >银行方订单号：</td>
	      <td class="border_top_right4" >
	        	 <input type="text" name="accountSeq" readonly value="${reconcileResultFunds.bankSeq }" />
	      </td>
	    </tr>  
	    <tr  class="trForContent1">
	    
	      <td class=border_top_right4 align="right" >银行方交易金额：</td>
	      <td class="border_top_right4" >
	        	 <input type="text" name="applyAmount"   readonly  value="<fmt:formatNumber value="${reconcileResultFunds.bankAmount }" pattern="#,##0.00"  />" />
	      </td>
	      
	    </tr>
	    <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >调账原因：</td>
	      <td class="border_top_right4" >
	      	<select id="applyCause" name="applyCause" >
	      		<option value="1">错账</option>
	      		<option value="2">单边账</option>
	      		<option value="3">其他</option>
       		</select>
	      </td>
	    </tr>	    
	   
	    <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >调账理由：</td>
	      <td class="border_top_right4" >
	        	 	<textarea name="applyReason" rows="5" cols="20"></textarea>
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="center" colspan="2">
	         <a class="s03" href="#" onclick="javascript:submitByHref();"><img
				src="./images/ok-Blue.jpg" border="0"> </a>
				 <a class="s03" href="#" onclick="javascript:history.go(-1);"><img
				src="./images/goback.jpg" border="0"> </a>
	      </td>
	    </tr>
	  </table>
<c:if test="${'220' eq reconcileResultFunds.busiFlag}"><!-- 
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >银行名称：</td>
	      <td class="border_top_right4">
	      	<input type="hidden" name="bankCode" value="${ reconcileResultFunds.bankCode}" />
	      	${ reconcileResultFunds.bankCode}
	      </td>
	      <td class=border_top_right4 align="right" >服务代码：</td>
	      <td class="border_top_right4" >
	      <input type="hidden" name="providerCode" value="${ reconcileResultFunds.providerCode}" />
	        	${reconcileResultFunds.providerCode}
	      </td>
	      </tr>
	      
	      <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >交易日期：</td>
	      <td class="border_top_right4" >
	      <input type="hidden" name="cutTime" value="<fmt:formatDate value="${reconcileResultFunds.busiTime}" type="date"/>" />
	        <fmt:formatDate value="${reconcileResultFunds.busiTime}" type="date"/>
	      </td>
	        <td class=border_top_right4 align="right" >错账方：</td>
	      <td class="border_top_right4" align="left" >
	        	<c:if test="${'200' eq reconcileResultFunds.busiFlag   }">银行</c:if>
				<c:if test="${'210' eq reconcileResultFunds.busiFlag }">系统</c:if>
				<c:if test="${'220' eq reconcileResultFunds.busiFlag }">金额不匹配</c:if>
				<c:if test="${'0' eq reconcileResultFunds.busiFlag}">所有</c:if>
	      </td>
	      </tr>
	    <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >银行方订单号：</td>
	      <td class="border_top_right4" >
	        	 <input type="text" name="bankSeq" readonly value="${reconcileResultFunds.bankSeq }" />
	      </td>
	       <td class=border_top_right4 align="right" >系统订单号：</td>
		      <td class="border_top_right4" >
		        	 <input type="text" name="accountSeq" readonly value="${reconcileResultFunds.accountSeq }" />
		      </td>
	    </tr>  
	    <tr  class="trForContent1">
	    
	      <td class=border_top_right4 align="right" >银行方交易金额：</td>
	      <td class="border_top_right4" >
	        	 <input type="text" name="bankAmount"  readonly value='<fmt:formatNumber value="${reconcileResultFunds.bankAmount}" pattern="#,##0.00"/>'  />
	      </td>
	       <td class=border_top_right4 align="right" >系统交易金额：</td>
		      <td class="border_top_right4" >
		        	 <input type="text" name="accountAmount" readonly  value='<fmt:formatNumber value="${reconcileResultFunds.accountAmount}" pattern="#,##0.00" />'  />
		      </td>
	    </tr>
	     <tr  class="trForContent1">
	      <td class=border_top_right4 align="right" >调账原因：</td>
	      <td class="border_top_right4" colspan="8">
	      	<select id="applyCause" name="applyCause" >
	      		<option value="0">错账</option>
	      		<option value="1">单边账</option>
	      		<option value="2">其他</option>
       		</select>
	      </td>
	    </tr>	    
	   
	    <tr  class="trForContent1">
	      <td class=border_top_right4 align="center" >调账理由：</td>
	      <td class="border_top_right4" align="left"  >
	        	 	<textarea name="applyReason" rows="5" cols="20"></textarea>
	      </td>
	      <td class=border_top_right4 align="center" colspan="2" >&nbsp;</td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="center" colspan="6" >
	         <a class="s03" href="#" onclick="javascript:submitRevise();"><img
				src="./images/ok-Blue.jpg" border="0"> </a>
				 <a class="s03" href="#" onclick="javascript:history.go(-1);"><img
				src="./images/goback.jpg" border="0"> </a>
	      </td>
	    </tr>
	  </table> -->
</c:if>
</form>
 <script type="text/javascript">

	function submitRevise(){
		var bankAmount = $("input[name='bankAmount']").val();
		var accountAmount = $("input[name='accountAmount']").val();
		var applyReason = $("textarea[name='applyReason']").val();
		if(applyReason==''){
			alert("请输入申请理由");
			return;
		}
		document.mainfrom.submit();
	}
 
	function submitByHref(){
		var applyReason = $("textarea[name='applyReason']").val();
		if(applyReason==''){
			alert("请输入申请理由");
			return;
		}
		var truthBeTold = window.confirm("确定要提交吗?");
       	if (truthBeTold) {
			document.mainfrom.submit();
       	}else{
           	return ;
       	}	
	}

</script>
 