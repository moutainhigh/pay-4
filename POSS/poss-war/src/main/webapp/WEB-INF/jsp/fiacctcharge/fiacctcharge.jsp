<%@ page contentType="text/html;charset=UTF-8" language="java"%> 
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">手工补单申请</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="supplement.htm?method=onSubmit" method="post" name="mainfrom">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >协议流水号：</td>
	      <td class="border_top_right4">
	     		 ${depositProtocolNo}
	      		<input type="hidden" name="depositProtocolNo" value="${depositProtocolNo}" />
				<input type="hidden" name="paymentCategory" id="paymentCategory" value="${paymentCategory}" />
	      </td>
	     </tr>
		  <c:if test="${paymentCategory == '11'}">
			  <tr class="trForContent1">
			  <td align="right" class="border_top_right4" >卡面金额：</td>
			  <td class="border_top_right4">
			  <input type="text" id="realAmt" name="realAmt" value="0"  /> 根据充值卡面值填写。
			  </td>
			 </tr>
		 </c:if>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >银行流水号：</td>
	      <td class="border_top_right4">
	      		<input type="text" name="returnNo" value="" /> 
	      </td>
	     </tr>
	     
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="right">
	      	 <input type="button" id="submitBtn" class="button2" value="确  认">
	      </td>
	      <td class=border_top_right4 align="left">
	      	 <input type="button" onclick="history.go(-1);"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
	  <script type="text/javascript">
	  jQuery(function($) {
		$('#realAmt').focus(function(){
			//金额格式化
			$('#realAmt').autoNumeric();
		});

		$("#submitBtn").click(function(){
			var paymentCategory = $("#paymentCategory").val();
			//神州行卡
			if(paymentCategory == '11'){
				//去掉格式化
				var realAmt = $.fn.autoNumeric.Strip('realAmt');
				if(realAmt == '' || realAmt<=0){
					alert("请输入正确的卡面金额");
					return false;
			    }
				$('#realAmt').val(realAmt);
		    }
			document.mainfrom.action="supplement.htm?method=onSubmit";
			document.mainfrom.submit();
		});
	}); 

	</script>
</form>

