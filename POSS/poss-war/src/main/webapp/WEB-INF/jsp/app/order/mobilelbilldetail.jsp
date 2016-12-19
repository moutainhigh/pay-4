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
                <td class="td1">充值渠道</td>
                <td class="td2">${mobileCharge.channelName}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">订单编号</td>
				 <td class="td2">${mobileCharge.orderId}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">子订单编号</td>
                 <td class="td2">${mobileCharge.itemId}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">订单状态</td>
				<td class="td2">
                 <c:if test="${mobileCharge.orderStatus eq 101}">初始</c:if>
                 <c:if test="${mobileCharge.orderStatus eq 111}">支付成功</c:if>
                 <c:if test="${mobileCharge.orderStatus eq 112}">支付失败</c:if>
                 <c:if test="${mobileCharge.orderStatus eq 113}">充值完成</c:if>  
                 </td>
            </tr>
            <tr>
				<td class="td1">号码</td>
				 <td class="td2">${mobileCharge.mobileNo}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">省份</td>
                <td class="td2">${mobileCharge.provinceName}(${mobileCharge.provinceCode})&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">折扣率</td>
                <td class="td2">${mobileCharge.discount}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">折扣金额(元)</td>
				<td  class="td2">${mobileCharge.amount}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">申请充值金额(元)</td>
                <td class="td2">${mobileCharge.applyAmount}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">实充金额(元)</td>
				 <td class="td2">${mobileCharge.acceptedAmount}&nbsp;</td>
            </tr>
            <tr>
				<td class="td1">充值状态</td>
				<td class="td2">
                   <c:if test="${mobileCharge.itemStatus eq 0}">初始</c:if>
                   <c:if test="${mobileCharge.itemStatus eq 1}">充值中</c:if>
                   <c:if test="${mobileCharge.itemStatus eq 2}">充值成功</c:if>
                   <c:if test="${mobileCharge.itemStatus eq 3}">充值部分完成</c:if>
                   <c:if test="${mobileCharge.itemStatus eq 4}">充值失败</c:if>
                 </td>
            </tr>
            <tr>
				<td class="td1">提交时间</td>
				<td class="td2">${mobileCharge.requestDate}</td>
            </tr>
            <tr>
				<td class="td1">完成时间</td>
				<td class="td2">${mobileCharge.completeDate}</td>
            </tr>
            <tr>
				<td class="td1">支付时间</td>
				<td class="td2">${mobileCharge.receiveDate}</td>
            </tr>
            <tr>
              <td colspan="2" align="center" style="border: 0px;" class="td2"><button class="btn" type="button" onclick="javascript:window.close();">关闭窗口</button></td>
            </tr>
         </table>
</body>
</html>
