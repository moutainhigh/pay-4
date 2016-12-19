<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">网关订单退款申请</h2>
<form action="${ctx}/refundOrder.do?method=onSubmit" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	     	  	
	      <tr class="trForContent1">
	      <td width="50%" align="right" class="border_top_right4" >交易流水号：</td>
	      <td class="border_top_right4">
            <input type="textr" name="tradeOrderNo" value="${tradeOrderNo}"  readonly="readonly">
	      </td>
	     </tr>
	       <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" name="orderId" value="${orderId}" readonly="readonly">
	      </td>
	     </tr>
	       <tr class="trForContent1">
	     	 <td align="right" class="border_top_right4" >订单金额：</td>
	         <td class="border_top_right4">
	           <input type="text" name="orderAmount" value="${orderAmount}" readonly="readonly">
	         </td>
	     </tr>
	      <tr class="trForContent1">
	     	 <td align="right" class="border_top_right4" >会员号：</td>
	         <td class="border_top_right4">
	           <input type="text" name="partnerId" value="${partnerId}" readonly="readonly">
	         </td>
	     </tr>
	      <tr class="trForContent1">
	     	 <td align="right" class="border_top_right4" >可退金额：</td>
	         <td class="border_top_right4">
	           <input type="text" name="refundAbleAmount" value="${refundAmount}" readonly="readonly">
	         </td>
	     </tr>
	       <tr class="trForContent1">
			<td align="right" class="border_top_right4">退款金额</td>
			<td class="border_top_right4">
                <input type="text" name="refundAmount" >
			</td>
		</tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="2">
	      <input type="submit"  name="butSubmit" value="申请退款" class="button2" >
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if> 