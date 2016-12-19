<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.pay.poss.SessionUserHolderUtil"%>

<head>
	
</head>

   <table id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
              <tbody>
              <tr  style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
                    <td>账单名字</td>
                    <td>出款方</td>
                    <td>交易日期</td>
                    <%
						if(SessionUserHolderUtil.getSessionUserHolder() !=null)
						{
							%>
							  <td>订单流水号</td>  
						<% } %>
	                <td>子订单流水号</td>
	                <td>入款流水号</td>
	                  <%
						if(SessionUserHolderUtil.getSessionUserHolder() !=null)
						{
					%>
	                       <td>出款流水号</td>
	                <% } %>
	                <td>条形码</td>
	                <td>收款方</td>
	                <td>订单状态</td>
	                <td>子订单状态</td>
                    <td>订单产生时间</td>
                    <td>订单完成时间</td>
	                <td>订单金额</td>
	                <td>付款渠道</td>
	                <td>收款银行</td>
	                <td>付款银行流水号</td>
               </tr>
              <c:forEach items="${page.result}" var="itemOrder">
                 <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                       <td>${itemOrder.billName}</td>
                       <td >${itemOrder.loginName}</td>
                       <td>${itemOrder.creationDate}</td>
                          <%
						  if(SessionUserHolderUtil.getSessionUserHolder() !=null)
						   {
					    %>
                          <td>${itemOrder.orderId}</td>
                        <% } %>
                       <td><a href="personalBill.do?method=personalBillsDetail&itemId=${itemOrder.itemId}" >${itemOrder.itemId}</a></td>
                       <td>${itemOrder.gatewayTradeNo}</td>
                         <%
						  if(SessionUserHolderUtil.getSessionUserHolder() !=null)
						   {
					    %>
                           <td>${itemOrder.channelSeqId}</td>
                         <% } %>
                       <td>${itemOrder.billBarCode}</td>
                       <td>${itemOrder.payeeAcctNo}</td>
                       <td>
                         <c:if test="${itemOrder.orderStatus eq 101}">初始化</c:if>
                         <c:if test="${itemOrder.orderStatus eq 111}">支付成功</c:if>
                         <c:if test="${itemOrder.orderStatus eq 112}">支付失败</c:if>
                         <c:if test="${itemOrder.orderStatus eq 113}">订单完成</c:if>
                       </td>
                       <td>
                         <c:if test="${itemOrder.status eq 0}">初始化</c:if>
                         <c:if test="${itemOrder.status eq 1}">申请成功</c:if>
                         <c:if test="${itemOrder.status eq 2}">销帐成功</c:if>
                         <c:if test="${itemOrder.status eq 3}">销帐失败</c:if>  
                       </td>
                       <td>${itemOrder.creationDate}</td>
                       <td>${itemOrder.complateDate}</td>
                       <td>${itemOrder.amount}</td>
                       <td>
                        <c:if test="${itemOrder.payeeAcctType eq 0}">银行卡</c:if>
                        <c:if test="${itemOrder.payeeAcctType eq 1}">系统</c:if>
                       </td>
                       <td>${itemOrder.backName}</td>
                       <td>${itemOrder.bankSerialno}</td>
			     </tr>
              </c:forEach>
               <c:if test="${empty page}">
                  <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td colspan="16" align="center">没有记录</td>
              </tr>
              </c:if>
           </tbody>
           </table>
<li:pagination methodName="personalBillsQuery" pageBean="${page}" sytleName="black2"/>

