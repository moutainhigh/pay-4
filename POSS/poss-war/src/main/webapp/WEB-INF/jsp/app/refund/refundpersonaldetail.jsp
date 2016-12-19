<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<style type="text/css">
.table{margin-top:20px;border-top-width: 0px; font-weight: normal; border-left-width: 0px; border-left-color: #cccccc; border-bottom-width: 0px; border-bottom-color: #cccccc; width: 60%; border-top-color: #cccccc; font-style: normal; background-color: #cccccc; border-right-width: 0px; text-decoration: none; border-right-color: #cccccc;}
.td1{font-weight: bold; font-style: normal; background-color: #eeeeee; text-decoration: none;text-align: right;}
.td2{font-weight: normal; font-style: normal; background-color: white; text-decoration: none;}
tr{height: 25px;}
.button {border-right: #888888 1px solid; border-top: #f4f4f4 1px solid; border-left: #f4f4f4 1px solid; COLOR: #000000; padding-top: 2px; border-bottom: #888888 1px solid}
</style>
</head>
<body>
   <table class="table" cellSpacing="1" cellPadding="2" rules="all" border="0" >
            <tr>
                 <td class="td1">退款流水号</td>
                 <td class="td2">${refund.eiroId}</td>
            </tr>
            <tr>
                 <td class="td1">订单流水号</td>
                 <td class="td2">${refund.orderId}</td>
            </tr>
            <tr>
                 <td class="td1">交易名称</td>
                 <td class="td2">${refund.billName}</td>
            </tr>
            <tr>
                 <td class="td1">出款帐户</td>
                 <td class="td2">${refund.loginName}</td>
            </tr>
            <tr>
                 <td class="td1">收款方</td>
                 <td class="td2">${refund.payeeAcctNo}</td>
            </tr>
            <tr>
                 <td class="td1">订单状态</td> 
                 <td class="td2">
	                   <c:if test="${refund.orderStatus eq 101}">初始化</c:if>
	                   <c:if test="${refund.orderStatus eq 111}">支付成功</c:if>
	                   <c:if test="${refund.orderStatus eq 112}">支付失败</c:if>
	                   <c:if test="${refund.orderStatus eq 113}">订单完成</c:if>              
                 </td>
              </tr>
              <tr>
                 <td class="td1">子订单状态</td>
                 <td class="td2">
	                     <c:if test="${refund.itemStatus  eq 0}">初始化</c:if>
                         <c:if test="${refund.itemStatus  eq 1}">申请成功</c:if>
                         <c:if test="${refund.itemStatus  eq 2}">销帐成功</c:if>
                         <c:if test="${refund.itemStatus  eq 3}">销帐失败</c:if>
	             </td> 
            </tr>   
            <tr>
                 <td class="td1">订单金额(元)</td>
                 <td class="td2">${refund.billAmount}</td>
            </tr>
            <tr>
                 <td class="td1">交易付款渠道</td>
                 <td class="td2"><c:if test="${refund.payAcctType eq 0}">银行卡</c:if>
                       <c:if test="${refund.payAcctType eq 1}">系统</c:if>
                 </td>
            </tr>
            <tr>
                 <td class="td1">收款银行</td>
                  <td class="td2">${refund.backName}</td>
            </tr>
            <tr>
                 <td class="td1">银行流水号</td>
                 <td class="td2">${refund.bankSerialno}</td>
            </tr>
            <tr>
                 <td class="td1">交易日期</td>
                 <td class="td2">${refund.creationDate}</td>
            </tr>
            <tr>
              <td colspan="2" align="center" style="border: 0px;" class="td2"><button class="button"  type="button" onclick="javascript:history.go(-1)">返回</button></td>
            </tr>
         </table>
</body>
</html>
