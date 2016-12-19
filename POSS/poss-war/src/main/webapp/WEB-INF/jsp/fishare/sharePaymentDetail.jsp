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
		<div align="center"><font class="titletext">付款记录详细信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
</br>
</br>
<form  method="post" id="mainfromId" name="mainfrom">
<input type="hidden" name="tradeOrderNo" id="tradeOrderNo" value="" width="130px;"/>

  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td class=border_top_right4 align="right">支付平台交易号：</td>
       <td class="border_top_right4">
     		&nbsp ${sharingOrder.strTradeOrderNo}
      </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易创建时间：</td>
       <td class="border_top_right4">&nbsp ${sharingOrder.strCreateDate} </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易结束时间：</td>
       <td class="border_top_right4">&nbsp ${sharingOrder.strUpdateDate}</td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易类型：</td>
       <td class="border_top_right4">&nbsp 分账付款 </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">商家订单号：</td>
       <td class="border_top_right4"> &nbsp ${sharingOrder.orderId}</td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">订单金额：</td>
       <td class="border_top_right4">&nbsp ${sharingOrder.strOrderAmount}</td>

    </tr>
	 
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">交易状态：</td>
       <td class="border_top_right4">&nbsp ${sharingOrder.strStatus} </td>

    </tr>
	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">备注：</td>
       <td class="border_top_right4">
     		&nbsp ${sharingOrder.remark}
      </td>

    </tr>

	 <tr class="trForContent1">
      <td class=border_top_right4 align="right">子菜单：</td>
       <td class="border_top_right4">
     		<table class="table sub_table" border="1">
					<thead>
						<tr>
							<th>付款方名称</th>
							<th>付款方登录名</th>
							<th>收款方名称</th>
							<th>收款方登录名</th>
							<th>分账金额</th>
							<th>分账手续费</th>
							<th>交易状态</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${sharingOrderDetailDTOList}" var="sodd"> 
				
						<tr>
							<td>${sodd.payerName}</td>
							<td>${sodd.payerLoginName}</td>
							<td>${sodd.payeeName}</td>
							<td>${sodd.payeeLoginName}</td>
							<td><fmt:formatNumber value="${sodd.sharingAmount*0.001}" pattern="#0.00"  /></td>
							<td><fmt:formatNumber value="${sodd.payeeFee*0.001}" pattern="#0.00"  /></td>
							<td> <c:choose>
								<c:when test="${sodd.status eq '0'}">
									处理中
								</c:when>
								<c:when test="${sodd.status eq '1'}">
									处理成功
								</c:when>
								<c:when test="${sodd.status eq '2'}">
									处理失败
								</c:when>
							</c:choose>	
							</td>
						</tr>
						
					 </c:forEach>
					</tbody>
				</table>
     
      </td>

    </tr>
    
    
  </table>

 </form>