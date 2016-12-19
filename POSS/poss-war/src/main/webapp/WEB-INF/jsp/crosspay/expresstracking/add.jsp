<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">汇率导入</h2>
<form action="${ctx}/currency/rate.do?method=add" method="post" id="mainfromId" enctype="multipart/form-data" onsubmit="return sub();">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >汇率文件：</td>
	      <td class="border_top_right4">
	      	<input type="file" id="file" name="file">
	      </td>
	      <td align="right" class="border_top_right4" >目标币种：</td>
	      <td class="border_top_right4">
	     	<select name="targetCurrency" id="targetCurrency">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="currency" items="${currencys}" varStatus="v">
					<option value="${currency.code}">${currency.desc}</option>
				</c:forEach>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >兑换单位：</td>
	      <td class="border_top_right4">
	     	<input type="text" id="currencyUnit" name="currencyUnit">
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="submit"  value="添加" class="button2">
	      <input type="button"  value="返回" class="button2" onclick="toIndex();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
	 
  <script type="text/javascript">
  
	function toIndex(){
		window.location.href="${ctx}/currency/rate.do";
	}
	
	function sub(){
		
		var file = $("#file").val();
		if(""== file){
			alert("请选择文件！");
			return false;
		}
		
		var targetCurrency = $("#targetCurrency").val();
		if(''== targetCurrency){
			alert("请选择目标币种!");
			return false;
		}
		
		var currencyUnit = $("#currencyUnit").val();
		if(""== currencyUnit){
			alert("请输入兑换单位！");
			return false;
		}
		
		return true;
	}

  </script>