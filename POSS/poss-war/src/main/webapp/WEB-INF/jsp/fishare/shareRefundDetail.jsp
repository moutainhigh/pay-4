<%@ page contentType="text/html;charset=UTF-8" language="java"%> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">退款记录详细信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
</br>
</br>

<input type="hidden" name="tradeOrderNo" id="tradeOrderNo" value="" width="130px;"/>
<c:forEach items="${sharingOrder4QueryDTOList}" var="ts"> 
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td class=border_top_right4 align="right">支付平台交易号：</td>
       <td class="border_top_right4">
     		&nbsp ${ts.tradeOrderNo}
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易创建时间：</td>
       <td class="border_top_right4">
     		&nbsp <fmt:formatDate value="${ts.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易结束时间：</td>
       <td class="border_top_right4">
     		&nbsp <fmt:formatDate value="${ts.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易类型：</td>
       <td class="border_top_right4">
     		&nbsp 分账退款
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">商家订单号：</td>
       <td class="border_top_right4">
     		&nbsp ${ts.orderId}
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">付款方名称：</td>
       <td class="border_top_right4">
     		&nbsp ${ts.payerName}
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">退款金额：</td>
       <td class="border_top_right4">
     		&nbsp <fmt:formatNumber value="${ts.refundAmount*0.001}" pattern="#0.00"  />
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">手续费：</td>
       <td class="border_top_right4">
     		&nbsp <fmt:formatNumber value="${ts.fee*0.001}" pattern="#0.00"  />
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">实际交易金额：</td>
       <td class="border_top_right4">
     		&nbsp <fmt:formatNumber value="${ts.actualAmount*0.001}" pattern="#0.00"  />
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易状态：</td>
       <td class="border_top_right4">
     		&nbsp <c:choose>
												<c:when test="${ts.status eq '0'}">
													初始化
												</c:when>
												<c:when test="${ts.status eq '1'}">
													处理中
												</c:when>
												<c:when test="${ts.status eq '2'}">
													成功
												</c:when>
											    <c:when test="${ts.status eq '3'}">
													失败
												</c:when>
											</c:choose>
     
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">备注：</td>
       <td class="border_top_right4">
     		&nbsp ${ts.remark}
     
      </td>

    </tr>
    
   <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
       <input type="button" onclick="history.go(-1);" name="backbutton" value="返  回" class="button2">	  
			  
      </td>
    </tr>
    
  </table>
  </c:forEach>

