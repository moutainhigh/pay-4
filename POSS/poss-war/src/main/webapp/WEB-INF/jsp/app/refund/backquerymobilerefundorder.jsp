<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<HTML>
<head>
<style type="text/css">
body{font-size: 12px; font-family: 宋体}
.textbox {border-right: #cccccc 1px solid; border-top: #cccccc 1px solid; border-left: #cccccc 1px solid; color: #333333; border-bottom: #cccccc 1px solid; height: 20px;widows:120px; background-color: #fdfdfd}
.button {border-right: #888888 1px solid; border-top: #f4f4f4 1px solid; border-left: #f4f4f4 1px solid; COLOR: #000000; padding-top: 2px; border-bottom: #888888 1px solid}
td{height: 20px;}
.td1{font-weight: bold;text-align: center;}
</style>
<script type="text/javascript">


function resetForm(){
	//清空文本框
	 $('input[type=text]').each(function(){
		 $(this).val(""); 
	  });
}
function   refund(chargeOrderId,chargeItemId){
	alert('chargeOrderId-->'+chargeOrderId);
	alert('chargeItemId-->'+chargeItemId);
$.ajax( {
	type : "post",
	url : "mobile_refund.do?method=getRefundResult&chargeOrderId="+chargeOrderId+"&chargeItemId="+chargeItemId,
	datatype : "json",
	success : function(data) {
		  alert(data);
          if(date == "true")
          {
              alert("退款成功");
          }
          if(data == "false")
          {
              alert("退款失败");
          }
}
});	
  mobile_query_form.submit();
	}		
</script>
</head>
<body>
     
<table cellSpacing=0 cellPadding=0 width="98%" border=0 style="margin-top: 20px;">
   <tbody>
     <tr>
       <td vAlign=top width="100%" bgColor=#ffffff>
       
     
          <table borderColor="#cccccc" cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
            <tbody>
                 <tr>
                     <td height=25>
                    <form action="mobile_query_refund.do?method=query" method="post"  name="mobile_query_form" id="mobile_query_form">
                       <table cellSpacing="0" cellPadding="2" border="0" align="center">
                         <tbody>
                              <tr >
                                 <td class="td1">充值手机号码</td>
                                 <td><input class="textbox"  type="text" name="mobileno" id="mobileno" value="${cmmand.mobileno}"/> </td>
                                 <td class="td1">充值金额</td>
                                 <td><input class="textbox" type="text" name="applyamount" id="applyamount" value="${cmmand.applyamount}"/> </td>
                                 <td class="td1">交易开始日期</td>
                                <td> <li:dateTime id="startcreatedate" width="120" value="${cmmand.startcreatedate}"/></td>
							 </tr>
							 <tr>
	                                 <td class="td1">交易结束日期</td>
                                 <td> <li:dateTime id="endcreatedate" width="120" value="${cmmand.endcreatedate}"/></td>						 
							     <td class="td1">订单号</td>
                                 <td><input class="textbox"  id="chargeOrderId" name="chargeOrderId" value="${cmmand.chargeOrderId}"/></td>
                                 <td class="td1">网关流水号</td>
                                 <td> <input class="textbox" name="tradeNo" id="tradeNo" value="${cmmand.tradeNo}"/></td>
                                 <td class="td1">是否退款</td>
                                 <td><select  id="status" name="status" style="width: 120px;">
                                       <c:if test="${cmmand.status!=111}">
                                       <option value="">未退款</option>
                                       </c:if>  
                                       <c:if test="${cmmand.status==111}">
                                       <option value="111">已退款</option>
                                       </c:if>                                        
                                     <option value="111">已退款</option>
                                     <option value="">未退款</option>
                                  </select> 
                                  </td>                                 
                                 <td align="center">
                                        <input class="button"  type="submit" value="查询 " />
                                        <input class="button"  type="button" value="清空 " onclick="resetForm();" />
                                  </td>
                                  
                              </tr> 
                                   </tbody>
                                   </table>
                           </form>
                   </td></tr>
          <tr>
          <td>
        
           
              <table id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
              <tbody>
              <tr  style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
                <td>充值手机号码</td>
                <td>需要退款金额</td>
                <td>充值总金额</td>
                <td>网关交易流水号</td>
                <td>订单号</td>
                <td>子订单号</td>
                <td>申请充值时间</td>
                <td>支付完成时间</td>
                <td>用户memberCode</td>
                <td>用户账号</td>
                <td>操作</td>
               </tr>
                 <c:forEach items="${backRefundmentRequestList}" var="refund">
                 <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td>${refund.mobileno}</td>
                   <td>${refund.refundAmount}</td>
                   <td>${refund.applyamount}</td>
                    <td>${refund.tradeNo}</td>
                   <td>${refund.chargeOrderId}</td>
                   <td>${refund.chargeItemId}</td>
                   <td>${refund.createdate}</td>
                   <td>${refund.receivedate}</td>
                    <td>${refund.memberCode}</td>
                    <td>${refund.memberAcctCode}</td>
                   <td>  
                      <c:if test="${refund.status!=111}">
                      <a href="${ctx}/mobile_refund.do?method=reRefundconfirm&mobileno=${refund.mobileno}&chargeOrderId=${refund.chargeOrderId}&chargeItemId=${refund.chargeItemId}" >退款</a>
                      </c:if>
                      <c:if test="${refund.status==111}"> 
                                                                     已退款
                      </c:if>                      
                   </td>
			     </tr>
              </c:forEach>
               <c:if test="${empty backRefundmentRequestList}">
                  <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td colspan="10" align="center">没有记录</td>
                 </tr>
              </c:if>
           </tbody>
           </table>
        
           </td>
           </tr>
     </tbody>
     </table>
     </tbody>
   </table>
</body>
</html>
