<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<form action="" method="post" name="querymainfrom" id="querymainfrom">

<table id="sorterTable" class="tablesorter" border="0" width="80%"  cellpadding="0" align="center" cellspacing="1">
				<thead>
					<tr>
						<th>商户会员号</th>
						<th>商户名称</th>
						<th>交易时间</th>
						<th>交易类型</th>
						<th>支付平台交易号</th>
						<th>商家订单号</th>
						<th>购买方订单金额</th>
						<th>付款方名称</th>
						<th>退款金额</th>
						<th>手续费</th>
						<th>交易状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody> 
					<c:forEach items="${paramsList}" var="par">
					<tr>
						<td>
							${par.partnerId}
						</td>
						<td>
							名称
						</td>
						<td>
							<fmt:formatDate value="${par.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							分账退款
						</td>
						<td>
							${par.tradeOrderNo}
						</td>
						<td>
							${par.orderId}
						</td>
						<td>
						  <fmt:formatNumber value="${par.orderAmount*0.001}" pattern="#0.00"  />
						</td>
						<td>
							${par.payerName}
						</td>
						<td>
							<fmt:formatNumber value="${par.refundAmount*0.001}" pattern="#0.00"  />
						</td>
						<td>
							<fmt:formatNumber value="${par.fee*0.001}" pattern="#0.00"  />
						</td>
						<td>

							<c:choose>
												<c:when test="${par.status eq '0'}">
													初始化
												</c:when>
												<c:when test="${par.status eq '1'}">
													处理中
												</c:when>
												<c:when test="${par.status eq '2'}">
													成功
												</c:when>
											    <c:when test="${par.status eq '3'}">
													失败
												</c:when>
											</c:choose>
						</td>
						<td>
							<input type="submit" style="display:none;" />
							<input type="button" class="button2" name="reundName" value="查看详情" onclick="refundInfo('${par.sharingRefundNo}');"/>
						</td>
						
				</tr>
		</c:forEach>
			
		
		
		</tbody>
	</table>
	 </form>
	  <li:pagination methodName="querysearch" pageBean="${page}" sytleName="black2"/>
 <SCRIPT LANGUAGE="JavaScript">
 
	  function refundInfo(sharingRefundNo){

		document.querymainfrom.action="sharePaymentDetailed.htm?method=queryRefundDetailInfo&sharingRefundNo="+sharingRefundNo;
		document.querymainfrom.submit();
	}
  
 </SCRIPT>