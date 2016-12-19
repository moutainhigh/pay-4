<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<form action="" method="post" name="querymainfrom" id="querymainfrom">
 
<table id="sorterTable" class="tablesorter" border="0" width="80%"  cellpadding="0" align="center" cellspacing="1">
				<thead>
					<tr>
						<th>交易创建时间</th>
						<th>交易修改时间</th>
						<th>交易类型</th>
						<th>支付平台交易号</th> 
						<th>商家订单号</th>
						<th>订单金额</th>
						<th>交易状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody> 
					<c:forEach items="${paramsList}" var="par">
					<tr>
						
						<td>${par.strCreateDate}</td>
						<td>${par.strUpdateDate}</td>
						<td>${par.sharingType}</td>
						<td>${par.strTradeOrderNo}</td>
						<td>${par.orderId}</td>
						<td>${par.strOrderAmount}</td> 
						<td>${par.strStatus}</td>
						<td>
							<input type="button" class="button2" name="batchname" value="查看详情" onclick="querydetailInfo('${memberCode}','${par.sharingOrderNo}');"/>
			  
						</td>
						
				</tr>
		</c:forEach>
			
			
		
		
		</tbody>
	</table>
	</form>
	 <li:pagination methodName="querysearch" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 
   
    function querydetailInfo(memberCode,sharingOrderNo){
        var url = "sharePaymentDetailed.htm?method=queryPaymentDetailInfo&memberCode="+memberCode+"&sharingOrderNo="+sharingOrderNo;
    	parent.addMenu("付款记录详细信息",url);
		//document.querymainfrom.action="sharePaymentDetailed.htm?method=queryPaymentDetailInfo&memberCode="+memberCode+"&sharingOrderNo="+sharingOrderNo;
		//document.querymainfrom.submit();
	}
</script>