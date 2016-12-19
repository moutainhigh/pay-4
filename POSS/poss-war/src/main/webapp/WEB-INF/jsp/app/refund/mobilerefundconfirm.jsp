<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">


</head>
<body>
<form action="mobile_refund.do?method=getRefundResult" method="post"  name="mobile_refund_form">
       <table class="table" cellSpacing="1" cellPadding="2" rules="all" border="0" > 
            <tr>
             <td class="td1">充值号码:</td>
             <td class="td2"><input type="text" name="mobileno" id="mobileno" value="${cmmand.mobileno}"   readonly></td>
            </tr>
            <tr> 
             <td class="td1">充值订单号:</td>
             <td  class="td2"><input type="text" name="chargeOrderId" id="chargeOrderId" value="${cmmand.chargeOrderId}" readonly></td>
            </tr>
            <tr> 
             <td class="td1">充值订单号:</td>
             <td  class="td2"><input type="text" name="chargeItemId" id="chargeItemId" value="${cmmand.chargeItemId}" readonly></td>
            </tr>            
            <tr> 
            <td class="td1">退款理由:</td>
             <td  class="td2"><textarea name="remarks" cols="40" rows="5"></textarea></td>
            </tr> 
            <tr> 
             <td  align="center"> 
               <button class="btn" type="submit">确认退款</button>
               </td><td>
                <button class="btn" type="button" onclick="window.location.href=('${ctx}/mobile_query_refund.do');">返回</button>
             </td>
           </tr>
     </table>

</form>

</body>
</html>