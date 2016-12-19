<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">机构汇率导入</h2>
<form action="${ctx}/orgCurrency/rate.do?method=add" method="post" id="mainfromId" enctype="multipart/form-data" onsubmit="return sub();">
	<table class="border_all2" width="90%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td align="right" class="border_top_right4" >机构：</td>
	      <td class="border_top_right4">
	      	<select name="orgCode" id="orgCode">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="org" items="${orgList}" varStatus="v">
					<option value="${org.orgCode}">${org.name}</option>
				</c:forEach>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >汇率文件：</td>
	      <td class="border_top_right4">
	      	<input type="file" id="file" name="file">
	      </td>
	      <td align="right" class="border_top_right4" >目标币种：</td>
	      <td class="border_top_right4">
	     	<select name="targetCurrency" id="targetCurrency">
	      		<option value="CNY" selected>人民币</option>
	      		<option value="EUR" selected>欧元</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >兑换单位：</td>
	      <td class="border_top_right4">
	     	<input type="text" id="currencyUnit" name="currencyUnit" value="100" readonly="readonly">
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type = "button"  onclick="download()" value="模版下载">
	      <input type="submit"  value="添  加" class="button2">
	      <input type="button"  value="返  回" class="button2" onclick="toIndex();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
	 
<script type="text/javascript">

function download(){
	window.location.href='${ctx}/orgCurrency/rate.do?method=downloadTemplate';
}
  
	function toIndex(){
		window.location.href="${ctx}/orgCurrency/rate.do";
	}
	
	function sub(){
		var orgCode = $('#orgCode').val();
		if(""== orgCode){
			alert("请选择机构！");
			return false;
		}
		
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