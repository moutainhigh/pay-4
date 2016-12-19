<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function recordList(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/tradeOrderQuery.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<form action="" method="post" name="listFrom">
</form>
<center>
<table  align="center" id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>

			<th class="item15">系统交易号</th>
			<th class="item15">会员号</th>
			<th class="item15">商户订单号</th>
			<th class="item15">渠道订单号</th>
			<th class="item15">订单类型</th>
			<th class="item10">状态</th>
			<th class="item15">订单金额</th>
			<th class="item15">可退金额</th>
			<th class="item15">交易币种</th>
			<th class="item10">是否退款</th>
			<th class="item10">是否拒付</th>
			<th class="item10">授权码</th>
			<th class="item10">渠道支付结果</th>
			<th class="item10">渠道返回原因</th>
			<th class="item10">创建时间</th>
			<th class="item10">完成时间</th>
		<!-- 	<th class="item30">备注</th> -->
			<th class="item30">商户返回码</th>
			<th class="item30">返回消息</th>
			<th class="item10">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="order" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
			<td><span></span>${order.tradeOrderNo}<span></span></td>
			<td>${order.partnerId}</td>
			<td>${order.orderId}</td>
			<td>${order.channelOrderNo}</td>
			<td>
				<c:if test="${order.tradeType=='1000'}">
					信用卡支付
				</c:if>
				<c:if test="${order.tradeType=='1001'}">
					信用卡支付
				</c:if>
				<c:if test="${order.tradeType=='1002'}">
					收银台信用卡支付
				</c:if>
				<c:if test="${order.tradeType=='2000'}">
					预授权申请
				</c:if>
				<c:if test="${order.tradeType=='2001'}">
					收银台预授权申请
				</c:if>
				<c:if test="${order.tradeType=='2100'}">
					预授权完成
				</c:if>
				<c:if test="${order.tradeType=='4000'}">
					本地化交易
				</c:if>
				<c:if test="${order.tradeType=='4001'}">
					本地化交易
				</c:if>
				<c:if test="${order.tradeType=='4002'}">
					本地化交易
				</c:if>
				<c:if test="${order.tradeType=='5000'}">
					循环代扣
				</c:if>
				<c:if test="${order.tradeType=='3000'}">
					3D支付
				</c:if>
				<c:if test="${order.tradeType=='6000'}">
					VCC交易
				</c:if>
				<c:if test="${order.tradeType=='7001'}">
					信用卡绑定
				</c:if>
				<c:if test="${order.tradeType=='7002'}">
					信用卡解绑
				</c:if>
				<c:if test="${order.tradeType=='7000'}">
					token支付
				</c:if>
				<c:if test="${order.tradeType=='7200'}">
					token预授权
				</c:if>
				<c:if test="${order.tradeType=='7004'}">
					Token创建及消费
				</c:if>
				<c:if test="${order.tradeType=='7005'}">
					收银台Token创建及消费
				</c:if>
				<c:if test="${order.tradeType=='7204'}">
					Token创建及预授权
				</c:if>
				<c:if test="${order.tradeType=='7205'}">
					收银台Token创建及预授权
				</c:if>
			</td>
			<td>
				<c:if test="${order.status=='0'}">
					未付款
				</c:if>
				<c:if test="${order.status=='1'}">
					支付失败
				</c:if>
				<c:if test="${order.status=='2'}">
					交易已付款
				</c:if>
				<c:if test="${order.status=='3'}">
					支付成功
				</c:if>
				<c:if test="${order.status=='4'}">
					支付成功
				</c:if>					
				<c:if test="${order.status=='5'}">
					支付失败
				</c:if>		
				<c:if test="${order.status=='20'}">
					支付中
				</c:if>
				<c:if test="${order.status=='21'}">
					预授权申请成功
				</c:if>
				<c:if test="${order.status=='22'}">
					预授权申请已撤销
				</c:if>
				<c:if test="${order.status=='23'}">
					预授权申请发生完成
				</c:if>
				<c:if test="${order.status=='24'}">
					预授权申请失败
				</c:if>
			</td>
			<td> <fmt:formatNumber type="number"   value="${order.orderAmount/1000.00}" />  </td>
			<td> <fmt:formatNumber type="number"   value="${order.refundAmount/1000.00}" />  </td>
			<td>${order.currencyCode}</td>
			<td>
			   	<c:if test="${order.status=='3'}">
					<c:if test="${order.refundAmount>0}">
				                   部分退款
			       </c:if>
			       <c:if test="${order.refundAmount==0}">
				                全额退款
			        </c:if>
				</c:if>
				<c:if test="${order.status!='3'}">
				            否
				</c:if>
			</td>
			<td>	
				<c:if test="${order.chargeBack == '1'}">
				            是
				</c:if>
				<c:if test="${order.chargeBack == '2'}">
				            否
				</c:if>
					<%-- &nbsp;${order.doingBouncedAmount/1000} --%></td>
			<td>${order.authCode }</td>
			<td>
				<c:if test="${order.channelStatus == '1'}">
				         成功
				</c:if>
				<c:if test="${order.channelStatus == '2'}">
				            失败
				</c:if>
				<c:if test="${order.channelStatus == '0'}">
				           处理中
				</c:if>
			</td><!-- 状态 0:处理中;1:成功;2:失败 -->
			<td>${order.errorMsg}</td>
			<td><date:date value="${order.createDate}"/></td>
			<td><date:date value="${order.completeDate}"/></td>
		<%-- 	<td align="left">${order.goodsName}</td> --%>
			<td align="left">${order.respCode}</td>
			<td align="left">${order.respMsg}</td>
			<td><!-- <a href="javaScript:;" onclick="viewDetail()">详情</a> -->
	<a href="./tradeOrderQuery.do?method=tradeOrderDetails&partnerId=${order.partnerId}&orderId=${order.orderId}&tradeOrderNo=${order.tradeOrderNo}">详情</a> 
			</td>
		</tr>
		</c:forEach>
		
	</tbody>
	
</table>
</center>
<table align="center">
<tr>
			
			<td colspan="18" align="center">
			金额总计：  ${countAmount.result} CNY<li:pagination methodName="recordList" pageBean="${page}" sytleName="black2"/>
			</td>
		</tr>
</table>