<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">修改银行协议</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="context_withhold_bankprotocol.controller.htm?method=modify" method="post" name="mainfrom">
	<input name="status" type="hidden" value="5" />
	<input name="bankCode" type="hidden" value="${dto.bankCode}" />
	<input name="bankName" type="hidden" value="${dto.bankName}" />
	<input name="sequenceId" type="hidden" value="${dto.sequenceId}" />
	
	
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		<tr class="trForContent1">
	      <td align="right" class="border_top_right4" >出款银行：</td>
	      <td class="border_top_right4">
	      	${dto.bankName }
	      </td>
	      <td class=border_top_right4 align="right" >授权业务类型：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="authorizeType" value="${dto.authorizeType}"  />
		      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >禁止的业务：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="unauthorized" value="${dto.unauthorized}"  />
		      </td>
		      <td class=border_top_right4 align="right" >是否需签约：0-不需要，1-需要：</td>
		      <td class="border_top_right4" >
		          <select name="needContract" >
        			<option value="0">不需要</option>
        			<option value="1" selected="selected">需要</option>
        		  </select>
		      </td>
		 </tr>
		 <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >生效日期：</td>
		      <td class="border_top_right4" >
		          <input class="Wdate" type="text" name="effectiveDate" id="effectiveDate" value='<fmt:formatDate value="${dto.effectiveDate}" type="both"/>' 
		          	onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
		      </td>
		      <td class=border_top_right4 align="right" >失效日期：</td>
		      <td class="border_top_right4" >
		          <input class="Wdate" type="text" name="expirationDate" id="expirationDate" value='<fmt:formatDate value="${dto.expirationDate}" type="both"/>' 
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"" />
		      </td>
		 </tr>
		  <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >签约日期：</td>
		      <td class="border_top_right4" >
		          <input class="Wdate" type="text" name="contractTime" id="contractTime" value='<fmt:formatDate value="${dto.contractTime}" type="both"/>'  
		          	onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
		      </td>
		      <td class=border_top_right4 align="right" >单笔下限：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="minimumLimit" id="minimumLimit" value='<fmt:formatNumber value="${dto.minimumLimit/1000}" pattern="#,##0.00"/>'  />
		      </td>
		 </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >单笔上限：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="maxLimit" id="maxLimit" value='<fmt:formatNumber value="${dto.maxLimit/1000}" pattern="#,##0.00"/>'  />
		      </td>
		      <td class=border_top_right4 align="right" >单日限额：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="dayLimit" id="dayLimit" value='<fmt:formatNumber value="${dto.dayLimit/1000}" pattern="#,##0.00"/>'  />
		      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >单日限次：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="dayTimes" id="dayTimes" value="${dto.dayTimes}"  />
		      </td>
		      <td class=border_top_right4 align="right" >单月限额：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="monthLimit" id="monthLimit" value='<fmt:formatNumber value="${dto.monthLimit/1000}" pattern="#,##0.00"/>'  />
		      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >单月限次：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="monthTimes" id="monthTimes"  value="${dto.monthTimes}"  />
		      </td>
		      <td class=border_top_right4 align="right" >信用额度：</td>
		      <td class="border_top_right4" >
		          <input type="text"  name="creditLimit" id="creditLimit" value='<fmt:formatNumber value="${dto.creditLimit/1000}" pattern="#,##0.00"/>'  />
		      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="right" colspan="2">
	      	 <input type="submit" id="submitBtn" class="button2" value="确  认">
	      </td>
	      <td class=border_top_right4 align="left" colspan="2">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	 </table>
	  
</form>

 <script type="text/javascript">
 	$(function(){
		$("#submitBtn").click(function(){
			var effectiveDate = $("#effectiveDate").val();
			if(isNull(effectiveDate)){
				alert("请选择生效日期");
				return false;
			}
			var expirationDate = $("#expirationDate").val();
			if(isNull(expirationDate)){
				alert("请选择失效日期");
				return false;
			}
			var contractTime = $("#contractTime").val();
			if(isNull(contractTime)){
				alert("请选择签约日期");
				return false;
			}
			
			var minimumLimit = $("#minimumLimit").val();
			if(isNull(minimumLimit)){
				alert("请输入单笔下限");
				return false;
			}
			if(!validAmount(minimumLimit)){
				alert("单笔下限金额格式不合法,请更改后再进行提交!($###.##)");
				return false;
			}
			
			var maxLimit = $("#maxLimit").val();
			if(isNull(maxLimit)){
				alert("请输入单笔上限");
				return false;
			}
			if(!validAmount(maxLimit)){
				alert("单笔上限金额格式不合法,请更改后再进行提交!($###.##)");
				return false;
			}
			
			var dayLimit = $("#dayLimit").val();
			if(isNull(dayLimit)){
				alert("请输入单日限额");
				return false;
			}
			if(!validAmount(dayLimit)){
				alert("单日限额金额格式不合法,请更改后再进行提交!($###.##)");
				return false;
			}
			
			var dayTimes = $("#dayTimes").val();
			if(isNull(dayTimes)){
				alert("请输入单日限次");
				return false;
			}
			if(!validNumber(dayTimes)){
				alert("单日限次格式不合法,请更改后再进行提交!(正整数)");
				return false;
			}
			
			var monthLimit = $("#monthLimit").val();
			if(isNull(monthLimit)){
				alert("请输入单月限额");
				return false;
			}
			if(!validAmount(monthLimit)){
				alert("单月限额金额格式不合法,请更改后再进行提交!($###.##)");
				return false;
			}
			
			var monthTimes = $("#monthTimes").val();
			if(isNull(monthTimes)){
				alert("请输入单月限额");
				return false;
			}
			if(!validNumber(monthTimes)){
				alert("单月限次格式不合法,请更改后再进行提交!(正整数)");
				return false;
			}
		});
	});
 
	function goBack() {
		document.mainfrom.action="context_withhold_bankprotocol.controller.htm?method=modifyQueryInit";
		document.mainfrom.submit();
	}
	
	function isNull(objVal){
		if(null == objVal || 0 == objVal.length || objVal == ''){
			return true;
		}
		return false;
	}
	
	//验证金额的合法性
	function validAmount(objVal){
		var reg = /^[0-9]+(.[0-9]{1,2})?$/g;
		if(!reg.test(objVal)){
			return false;
		}
		var reg2 = /^[0]+(.[0]{1,2})?$/g;
		if(reg2.test(objVal)){
			alert("金额必须大于0");
			return false;
		}
		return true;
	}
	
	//验证数字的合法性
	function validNumber(objVal){
		var reg = /^[1-9]{1}+([0-9]{1,5})?$/g;
		if(!reg.test(objVal)){
			return false;
		}
		return true;
	}
  </script>
 