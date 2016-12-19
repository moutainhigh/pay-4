<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="listFrom">
 </form>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>充值订单流水号</th>
						<th>充值金额</th>
						<th>资金机构代码</th>
						<th>客户账户号</th>
						<th>客户账户类型</th>
						<th>手续费</th>
						<th>状态</th>
						<th>创建时间</th>
						<th>支付订单流水号</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody> 
					<c:if test="${depositOrder != null }">
					<tr>
						
						<td>
							${depositOrder.depositOrderNo}
						</td>
						<td>
							${amount}
						</td>
						<td>
							${depositOrder.orgCode}
						</td>
						<td>
							${depositOrder.customer}
						</td>
						<td>
							${depositOrder.customerType}
						</td>
						<td>
							${fee}
						</td>
						<td>
							<c:choose>
								<c:when test="${depositOrder.status eq '1'}">
									成功
								</c:when>
								<c:when test="${depositOrder.status eq '2'}">
									失败
								</c:when>
								<c:otherwise>
									处理中
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<fmt:formatDate value="${depositOrder.createDate }" type="both"/>
						</td>
						<td>
							${depositOrder.paymentOrderNo}
						</td>
						<td>
						<c:choose>
								<c:when test="${depositOrder.status eq '1'}">
									
								</c:when>
								<c:otherwise>
									<input type="button" onclick="searchModeForId('${depositProtocolNo}');"  id="submitBtn" class="button2" value="申  请">
								</c:otherwise>
							</c:choose>
						</td>
				</tr>
			
		</c:if>
			<c:if test="${depositOrder == null }">
					<tr>
					<td colspan="11" align="center"> 
						没有查询到相关数据!
					</td>
					</tr>
			</c:if>
		</tbody>
	</table>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				8: {sorter: false}
			}
		});
	 }); 

	function searchModeForId(modeId){
		document.listFrom.action="supplement.htm?method=initModify&depositProtocolNo="+modeId;
		document.listFrom.submit();
	}
	
</script>