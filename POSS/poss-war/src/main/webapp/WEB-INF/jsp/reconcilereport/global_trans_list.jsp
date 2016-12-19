<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/xdevelop.net/taglibs/page" prefix="page"%>
<%@ page import="com.pay.ReconcileReportService"%>
<%@ page import="com.pay.GlobalInfo"%>
<%@ page import="java.util.*"%>
<p>
行业卡交易列表
</p>

<%	
	//获取查询参数
	GlobalInfo globalInfo = (GlobalInfo)request.getAttribute("globalInfo");
	
	ReconcileReportService reconcileReportService = (ReconcileReportService)request.getAttribute("reconcileReportService");

%>
<page:pager total="${ listSize }" defaultPageSize="10">
<table id="tableList" title="行业卡交易列表" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
  	<tr>
  		<th align="center">卡号</th>
  		<!-- 
    	<th align="center">卡密</th>
    	 -->
    	<th align="center">卡种</th>
    	<th align="center">商家用户名</th>
    	<th align="center">订单交易号</th>
    	<!-- 
    	<th align="center">商户订单号</th>
    	 -->
    	<th align="center">交易开始时间</th>
    	<th align="center">交易结束时间</th>
    	<th align="center">交易类型</th>
    	<!-- <th align="center">交易状态</th> -->
    	<th align="center">交易金额</th>
    	<!-- <th align="center">错误原因</th> -->
    	<th align="center">渠道代码</th>
    	<th align="center">操作</th>
  	</tr>
  	</thead>
<%	
	List list = reconcileReportService.queryGlobalTransactionsList(globalInfo,index.intValue()-1,pageSize.intValue());
	request.setAttribute("list",list);
%> 
  	<tbody>
  	<c:forEach var="r" items="${ list }" varStatus="status">
  	<tr>
  		<td>${ r.card_no }</td>
  		<!-- 
  		<td>${ r.card_pass }</td>
  		 -->
  		<td>
  			<c:if test="${ r.card_type == '20' }">移动</c:if>
  			<c:if test="${ r.card_type == '21' }">电信</c:if>
  			<c:if test="${ r.card_type == '22' }">联通</c:if>
  			<c:if test="${ r.card_type == '23' }">骏网</c:if>
  			<c:if test="${ r.card_type != '20' && r.card_type != '21' && r.card_type != '22' && r.card_type != '23' }">未知</c:if>
  		</td>
  		<td>${ r.seller_name }</td>
  		<td>${ r.trade_order_info_id }</td>
  		<!-- 
  		<td>${ r.order_id }</td>
  		 -->
  		<td>
  			<fmt:formatDate value="${ r.startDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
  		</td>
  		<td>
  			<fmt:formatDate value="${ r.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
  		</td>
  		<td>
  		
  			
  			
  			<c:if test="${ r.charge_type == '0' }">充值</c:if>
  			<c:if test="${ r.charge_type == '1' }">支付</c:if>
  			<c:if test="${ r.charge_type != '0' && r.charge_type != '1' }">未知</c:if>
  		</td>
  		<!-- 
  		<td>
  			<c:if test="${ r.status == '0' }">未付款</c:if>
  			<c:if test="${ r.status == '1' }">交易关闭</c:if>
  			<c:if test="${ r.status == '2' }">已付款</c:if>
  			<c:if test="${ r.status == '3' }">交易完成</c:if>
  			<c:if test="${ r.status == '4' }">交易成功</c:if>
  			<c:if test="${ r.status == '9' }">超时</c:if>
  		</td>
  		 -->
  		<td>${ r.order_amount }</td>
  		<!-- <td>${ r.error_code }</td> -->
  		<td>${ r.alias }</td>
  		<td>
  			<a href="#" onclick="queryGlobalTransactionsDetails('${ r.trade_order_info_id }','${ r.error_code }','${ r.charge_order_status }','${ r.card_pass }','${ r.bank_order_id }')">查看明细</a>
  		</td>
  	</tr>
  	</c:forEach>
  	</tbody>
  	<tr>
  		<td style="text-align:center" colspan="13"><a href="#" onclick="entryGlobalTransactions()">返回查询首页</a>&nbsp;&nbsp;&nbsp;<page:navigator type="button"/></td>
  	</tr>
</table>
</page:pager> 


<style type="text/css">
	.even{ background:#FFF38F;}
	.odd{ background:FFFFEE;}
</style>

<script type="text/javascript">

	function queryGlobalTransactionsDetails(id,errer_code,charge_order_status,card_pass,bank_order_id){
		window.location.href = "reconcile.report.do?method=queryGlobalTransactionsDetails&trade_order_info_id="+id+"&errer_code="+errer_code+"&charge_order_status="+charge_order_status+"&card_pass="+card_pass+"&bank_order_id="+bank_order_id;
	}

	function entryGlobalTransactions(){
		window.location.href = "reconcile.report.do?method=entryGlobalTransactions";
	}

	$(document).ready( function() { loads(); } ); 
	function loads(){

		$("#tableList").tablesorter({
			 headers: {6: {sorter: false}}
		});  

		$("#tableList tbody tr:odd").addClass("odd");
		$("#tableList tbody tr:even").addClass("even");
		
		$("p").attr("style","text-align:center;");
	}
</script>
<style type="text/css">
	.focus{
		border:1px; solid #f00;
		background: #fcc;
	}
</style>