<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function id2Text(obj){
		var text = obj.options[obj.selectedIndex].text;
		$("input[name='bankName']").val(text);
	}

	$(function(){
		$("#submitBtn").click(function(){
			var bankId = $("#bankId").val();
			if(bankId == ''){
				alert("请选择银行");
				return false;
			}
		});
	});

	function bankQuery(){
		window.location.href='${ctx}/context_fundout_fobank.controller.htm?method=initSearch';
	}	
	
</script>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">新增出款银行</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="context_fundout_fobank.controller.htm?method=addFundoutBankInfo" method="post" name="mainfrom">
<li:token formId="addBankFrom" />
	<input name="bankName" type="hidden" value="" />
	 <table class="border_all2" width="45%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >出款银行：</td>
	      <td class="border_top_right4">
	      	<li:select name="bankId" defaultStyle="true" itemList="${bankInfoList}" otherAttribute="onchange=id2Text(this);" />
	      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >状态：</td>
		      <td class="border_top_right4" >
		      	<li:select name="status" itemList="${statusList}" />
		      </td>
		 </tr>
		 <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >备注：</td>
		      <td class="border_top_right4" >
		        	 	<textarea name="mark" rows="5" cols="20"></textarea>
		      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center" colspan="2">
	      	 <input id="btn_back" type="button" class="button2" value="返回" onClick="bankQuery()">
	      	 <input type="submit" id="submitBtn" class="button2" value="新  增">
	      </td>
	    </tr>
	    	  <c:if test="${not empty info}">
				<li style="color: red;">${info }</li>
			</c:if>
	  </table>
</form>

 