<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<p>
行业卡交易明细
</p>

<table id="tableList" style="width:90%;" title="行业卡交易明细" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<td width="25%" align="right">外部（商户）订单号：</td>
		<td width="25%" align="left">${ tradeOrderInfo.order_id }</td>
		<td width="25%" align="right">外部（商户）订单金额：</td>
		<td width="25%" align="left">${ tradeOrderInfo.order_amount }￥</td>
	</tr>
	<tr>
		<td align="right">卡密：</td>
		<td align="left">${ card_pass }</td>
		<td align="right">商户订单号： </td>
		<td align="left">${ tradeOrderInfo.order_id }</td>
	</tr>
	<tr>
		<td align="right">外部（商户）订单时间： </td>
		<td align="left"><fmt:formatDate value="${ tradeOrderInfo.order_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td align="right">交易状态：</td>
		<td align="left">
			<!-- 
			<c:if test="${ tradeOrderInfo.status == '0' }">未付款</c:if>
			<c:if test="${ tradeOrderInfo.status == '1' }">交易关闭</c:if>
			<c:if test="${ tradeOrderInfo.status == '2' }">已付款</c:if>
			<c:if test="${ tradeOrderInfo.status == '3' }">交易完成（含退款）</c:if>
			<c:if test="${ tradeOrderInfo.status == '4' }">交易成功</c:if>
			 -->
			<c:if test="${ charge_order_status == '0' }">初始化</c:if>
			<c:if test="${ charge_order_status == '1' }">交易成功</c:if>
			<c:if test="${ charge_order_status == '2' }">交易失败</c:if>
			<c:if test="${ charge_order_status == '3' }">交易失败，卡消费成功，选择面额>真是面额</c:if>
			<c:if test="${ charge_order_status == '4' }">交易成功，卡消费成功，选择面额&lt;真实面额</c:if>
			<c:if test="${ charge_order_status == '9' }">超时</c:if>
		</td>
	</tr>
	<tr>
		<td align="right">买家的系统支付账户姓名（账户别名）：</td>
		<td align="left">${ tradeOrderInfo.buyer_name }</td>
		<td align="right">卖家的系统支付账户姓名（账户别名）： </td>
		<td align="left">${ tradeOrderInfo.seller_name }</td>
	</tr>
	<tr>
		<td align="right">商品名称：</td>
		<td align="left">${ tradeOrderInfo.goods_name }</td>
		<td align="right">商品数量：</td>
		<td align="left">${ tradeOrderInfo.goods_count }</td>
		
	</tr>
	<tr>
		<td align="right">物流方式：</td>
		<td align="left">
			<c:if test="${ tradeOrderInfo.shipping_type == '1' }">平邮</c:if>
			<c:if test="${ tradeOrderInfo.shipping_type == '2' }">快递</c:if>
			<c:if test="${ tradeOrderInfo.shipping_type == '3' }">EMS</c:if>
		</td>
		<td align="right">创建时间：</td>
		<td align="left"><fmt:formatDate value="${ tradeOrderInfo.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<td align="right">交易类型：</td>
		<td align="left">
			<c:if test="${ tradeOrderInfo.trade_type == '0001' }">商品购买</c:if>
			<c:if test="${ tradeOrderInfo.trade_type == '0002' }">服务购买</c:if>
			<c:if test="${ tradeOrderInfo.trade_type == '0003' }">网络拍卖</c:if>
			<c:if test="${ tradeOrderInfo.trade_type == '0004' }">捐赠 目前只用于商品购买</c:if>
		</td>
		<td align="right">付款帐户类型：</td>
		<td align="left">
			<c:if test="${ tradeOrderInfo.acct_type == '10' }">人民币</c:if>
			<c:if test="${ tradeOrderInfo.acct_type == '20' }">移动</c:if>
			<c:if test="${ tradeOrderInfo.acct_type == '21' }">电信</c:if>
			<c:if test="${ tradeOrderInfo.acct_type == '22' }">联通</c:if>
			<c:if test="${ tradeOrderInfo.acct_type == '23' }">骏网</c:if>
		</td>
	</tr>
	<tr>
		<td align="right">错误原因：</td>
		<td align="left">${ errer_code }</td>
		<td align="right">收货地址：</td>
		<td align="left">${ tradeOrderInfo.receive_addr }</td>
	</tr>
	<tr>
		<td align="right">渠道订单号：</td>
		<td align="left">${ bank_order_id }</td>
		<td align="right"></td>
		<td align="left"></td>
	</tr>
</table>

<p align="center"><a href="#" onclick="entryGlobalTransactions()">返回查询首页</a></p>

<script type="text/javascript">

	function entryGlobalTransactions(){
		window.location.href = "reconcile.report.do?method=entryGlobalTransactions";
	}

	$(document).ready( function() { loads(); } ); 
	function loads(){

		$("#tableList").tablesorter({
			 headers: {6: {sorter: false}}
		});  

		$("#tableList tr:odd").addClass("odd");
		$("#tableList tr:even").addClass("even");
	}
</script>