<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function queryOrderListById(){

		if($("#id").val() == ""){
			return false;
		}
		
		document.queryOrder.action = "reversed.query.queryOrderListById.do";
		document.queryOrder.submit();
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">输入订单交易号（冲正）</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form method="post" name="queryOrder"  action="">

	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
    	<tr class="trForContent1">
      		<td class="border_top_right4">请输入订单交易号</td>
      		<td class="border_top_right4">
      		</td>
    	</tr>
   
    	<tr class="trForContent1">
      		<td class="border_top_right4" >订单交易号：</td>
      		<td class="border_top_right4">
        		<input type="text" id="id" name="id" size="50" />
      		</td>
    	</tr>
    	
    	<tr class="trForContent1">
      		<td class="border_top_right4" align="center" colspan="2" >
          		<a class="s03" href="#" onclick="javascript:queryOrderListById();"><img src="./images/ok-Blue.jpg" border="0"> </a>
      		</td>
    	</tr>
  </table>
  
  <c:if test="${not empty msg }"> 
  	<li style="color: red"><c:out value="${msg}" /> </li>
  </c:if>

</form>
