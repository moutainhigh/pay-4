<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(function(){
         $("#send").click(function(){
			var orderNo = $('#orderNo').val();
			if(orderNo.replace(/(^\s*)|(\s*$)/g,'') == "") {
				alert("请输入正确的网关订单流水号!");
                return false;
			}
			
            var val=$('input:radio[name="type"]:checked').val();
            if(val==null){
                alert("请选择通知内容!");
                return false;
            }
            else if(val == '1'){ // 人民币网关102开头的订单
				$("#resendForm").attr("action", "${ctx}/sendNotice.htm?method=search");
            } else if(val == '2'){// 商旅卡网关171开头的订单
				$("#resendForm").attr("action", "${ctx}/hnacardSendNotice.htm?method=search");
            }
			$("#resendForm").submit();
         });

		$("#cancle").click(function(){
			$("#resendForm").attr("action", "${ctx}/sendNotice.htm?method=init&type=0&orderNo=&searchType=0");
			$("#resendForm").submit();
		});

		 
     });
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">重发通知</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="resendForm" id="resendForm" action="${ctx}/sendNotice.htm?method=search">
  
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	 <tr class="trForContent1">
      <td class="border_top_right4" align="right" valign="top">订单来自：</td>
      <td class="border_top_right4">
        <input type="radio" id="order_content" name="type" value="1" <c:if test="${param.type == '1'}">checked</c:if>/>人民币网关
		<input type="radio" id="order_content" name="type" value="2" <c:if test="${param.type == '2'}">checked</c:if>/>商旅卡网关
      </td>
    </tr>
	<tr class="trForContent1">
	 <td class="border_top_right4" align="right" valign="top">订单类型：</td>
   <td class="border_top_right4"><select name='searchType'>
        <option value='1' name='searchType' <c:if test="${param.searchType == '1'}">selected</c:if>>商家订单号
        <option value='2' name='searchType' <c:if test="${param.searchType == '2'}">selected</c:if>>网关订单流水号
        <option value='3' name='searchType' <c:if test="${param.searchType == '3'}">selected</c:if>>支付订单流水号
		<option value='4' name='searchType' <c:if test="${param.searchType == '4'}">selected</c:if>>资金协议流水号
		<option value='5' name='searchType' <c:if test="${param.searchType == '5'}">selected</c:if>>银行订单流水号
	</select></td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="right" valign="top">订单号：</td>
      <td class="border_top_right4">
        <input type="text" id="orderNo" name="orderNo" size="20" value="<c:out value='${param.orderNo}'/>"/>
      </td>
    </tr>
	
    <tr class="trForContent1">
      <td class="border_top_right4" align="center" colspan="2" >
          <input type="button" name="submit1" id="send" value="提 交 查 询"/>
      </td>
    </tr>
  </table>

</form>
<div id="resultListDiv" class="listFence">
<c:if test = "${not (empty resultMap)}">
<table  width="100%" align="center" class="tableorder" border="1" cellSpacing="1" cellpadding="1">
<tr>
<th class="header">商家订单号</th>
<th class="header">收单商家名</th>
<th class="header">网关订单流水号</th>
<th class="header">支付订单流水号</th>
<th class="header">订单内容</th>
<th class="header">订单金额(元)</th>
<th class="header">支付方式</th>
<th class="header">渠道名称</th>
<th class="header">付款人</th>
<th class="header">支付状态</th>
<th class="header">订单状态</th>
<th class="header">收单时间</th>
<th class="header">支付完成时间</th>
</tr>
<c:if test = "${empty resultMap}">
<tr>
<td colspan="13">未查到该订单的成功付款信息</td>
</tr>
</c:if>
<tr>
<td><c:out value="${resultMap.orderId}"/></td>
<td><c:out value="${resultMap.payee}"/></td>
<td><c:out value="${resultMap.tradeOrderNo}"/></td>
<td><c:out value="${resultMap.paymentOrderNo}"/></td>
<td><c:out value="${resultMap.goodsName}"/></td>
<td><c:out value="${resultMap.orderAmount}"/></td>
<td><c:out value="${resultMap.paytype_cn}"/></td>
<td><c:out value="${resultMap.orgName}"/></td>
<td><c:out value="${resultMap.payer}"/></td>
<td><c:out value="${resultMap.paystatus_cn}"/></td>
<td><c:out value="${resultMap.orderstatus_cn}"/></td>
<td><c:out value="${resultMap.createDate}"/></td>
<td><c:out value="${resultMap.updateDate}"/></td>
</tr>
</table>
<form name="sendForm" id="sendForm" action=<c:if test="${param.type == '2'}">${ctx}/hnacardSendNotice.htm?method=onSubmit</c:if><c:if test="${param.type == '1'}">${ctx}/sendNotice.htm?method=onSubmit</c:if> method="post">
<input type="hidden" name="tradeOrderNo" value="<c:out value='${resultMap.tradeOrderNo}'/>">
<input type="hidden" name="sendType" value="1">
<input type="submit" value="确定重发">&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="cancle" value="放弃">
</form>
</c:if>
<c:if test = "${(empty resultMap)}">
<c:if test="${not (empty param.searchType)}">
未查到该订单的成功付款信息
</c:if>
</c:if>
</div>