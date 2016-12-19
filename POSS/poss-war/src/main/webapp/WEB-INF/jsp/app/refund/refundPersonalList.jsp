<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	
</head>

   <table id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
              <tbody>
              <tr  style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
                <td>退款流水号</td>
                <td>订单流水号</td>
                <td>子订单流水号</td>
                <td>入款流水号</td>
                <td>交易名称</td>
                <td>出款帐户</td>
                <td>收款方</td>
                <td>订单状态</td>
                <td>子订单状态</td>
                <td>订单金额</td>
                <td>交易付款渠道</td>
                <td>收款银行</td>
                <td>付款银行流水号</td>
                <td>交易日期</td>
                <td>操作</td>
               </tr>
              <c:forEach items="${page.result}" var="refund">
                 <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                 <td>${refund.eiroId}</td>
                    <td>${refund.orderId}</td>
                   <td><a href="refoundPersonal.do?method=refundPersonalDetail&itemId=${refund.itemId}">${refund.itemId}</a></td>
                   <td>${refund.gateTradeNo}</td>
                   <td>${refund.billName}</td>
                   <td>${refund.loginName}</td>
                   <td>${refund.payeeAcctNo}</td>
                   <td>
	                   <c:if test="${refund.orderStatus eq 101}">初始化</c:if>
	                   <c:if test="${refund.orderStatus eq 111}">支付成功</c:if>
	                   <c:if test="${refund.orderStatus eq 112}">支付失败</c:if>
	                   <c:if test="${refund.orderStatus eq 113}">订单完成</c:if>
                   </td>
                   <td>
	                     <c:if test="${refund.itemStatus  eq 0}">初始化</c:if>
                         <c:if test="${refund.itemStatus  eq 1}">申请成功</c:if>
                         <c:if test="${refund.itemStatus  eq 2}">销帐成功</c:if>
                         <c:if test="${refund.itemStatus  eq 3}">销帐失败</c:if>
                   </td>
                   <td>${refund.billAmount}</td>
                   <td> <c:if test="${refund.payAcctType eq 0}">银行卡</c:if>
                   <c:if test="${refund.payAcctType eq 1}">系统</c:if>
                   </td>
                   <td>${refund.backName}</td>
                   <td>${refund.bankSerialNo}</td>
                   <td>${refund.creationDate}</td>
                   <td> 
                     <c:if test="${refund.eiroStatus != 111}">
                         <a href="refoundPersonal.do?method=refundPersonalConfirm&itemId=${refund.itemId}">未退款</a>
                      </c:if>
                      <c:if test="${refund.eiroStatus == 111}">
                                                                                  已退款
                      </c:if>
                   </td>
			     </tr>
              </c:forEach>
               <c:if test="${empty page}">
                  <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td colspan="16" align="center">没有记录</td>
              </tr>
              </c:if>
           </tbody>
           </table>
     <li:pagination methodName="refundPersonalBillsQuery" pageBean="${page}" sytleName="black2"/>


