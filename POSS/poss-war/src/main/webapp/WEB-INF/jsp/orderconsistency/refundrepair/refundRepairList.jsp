<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="listFrom">
 </form>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>序号</th>
						<th>订单号</th>
						<th>订单状态</th>
						<th>银行卡号</th>
						<th>收款人名称</th>
						<th>订单金额（元）</th>
						<th>费用（元）</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${list != null && fn:length(list) > 0}">
					<c:forEach items="${list}" var="dto" varStatus="index">
					<tr>
						<td>
							<c:out value="${index.count}" />
						</td>
						<td>${dto.sequenceId}</td>
						<td>交易失败	</td>
						<td>
							${dto.bankAcct}
						</td>
						<td>
							${dto.accountName}
						</td>
						<td>
							<fmt:formatNumber value="${dto.orderAmount*0.001}" pattern="#,##0.00" />
							
						</td>
						<td>
						<fmt:formatNumber value="${dto.fee*0.001}" pattern="#,##0.00" />
						</td>
						<td>
							<fmt:formatDate value="${dto.createTime}" type="both"/>
						</td>
						<td>
							<input type="button" onclick="repairRefundById('${dto.sequenceId}');"  id="submitBtn" class="button2" value="修 复">
						</td>
					</c:forEach>
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

	function repairRefundById(sequenceId){
		document.listFrom.action="refundrepair.htm?method=repair&sequenceId="+sequenceId;
		document.listFrom.submit();
	}
	
</script>