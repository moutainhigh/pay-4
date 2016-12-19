
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>
<head>
	<link rel="stylesheet" href="./css/main.css">
	<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="./css/main.css">
	<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
	<script src="./js/common.js"></script>
	<script type="text/javascript">
		/** 发通知的ajax */
		function processEdit(IDAndStatus){
			$('#infoLoadingDiv').dialog('open');			
			var pars = $("#renotifyResultFormBean").serialize() + "&IDAndStatus=" +IDAndStatus;
			$.ajax({
				type: "POST",
				url: "${ctx}/gotoFiNotify.do?method=processSendRenotify",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					alert(result);
				}
			});
		}
		
		/**控制复选框全选*/
		$("#checkAll").click(function(){
			$("[name=items]:checkbox").attr("checked",this.checked);
		});
		$("[name=items]:checkbox").click(function(){
			var flag = true;
			$("[name=items]:checkbox").each(function(){
				if(!this.checked)flag = false;
			});
			$("#checkAll").attr("checked",flag);
		});
		
	</script>
</head>


<body>
	<form id="renotifyResultFormBean" name="renotifyResultFormBean">
		<table class="border_all2" width="95%" border="0" cellspacing="0"
			cellpadding="1" align="center">
			<tr class="trbgcolorForTitle" align="center" valign="middle">
				<td class="border_right4"  nowrap><a class="s03">
					<font color="#FFFFFF">全选</font> </a>
					<input type="checkbox" id="checkAll" name="checkAll"> </td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">交易流水号</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">商家订单号</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">商户名</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">商品名称</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">数量</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">订单价格</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">订单状态</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">生成时间</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">交易处理时间</font> </a></td>
<!--				<td class="border_right4"  nowrap><a class="s03"><font-->
<!--					color="#FFFFFF">交易详情</font> </a></td>-->
				<td class="border_right4"  nowrap><a class="s03"><font
					color="#FFFFFF">通知</font> </a></td>
			</tr>
			<c:forEach items="${page.result}" var="renotifyOrder" varStatus = "renotifyOrderStatus">
				<c:choose>
					<c:when test="${renotifyOrderStatus.index%2==0}">
					      <tr class="trForContent1">
					</c:when>
					<c:otherwise>
					      <tr class="trForContent2">
					</c:otherwise>
				</c:choose>	
					<td class="border_top_right4" align="center" nowrap>
						<input type="checkbox" id="items" name="items" 
							   value="${renotifyOrder.tradeOrderInfoID}#${renotifyOrder.orderStatus}"> &nbsp;
					</td>
					<td class="border_top_right4" align="center" nowrap>${renotifyOrder.tradeOrderInfoID}&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap>${renotifyOrder.orderID}&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap>${renotifyOrder.sellerName}&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap>${renotifyOrder.goodsName}&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap>${renotifyOrder.goodsCount}&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap>
						<fmt:formatNumber value="${renotifyOrder.orderAmount * 0.001}" pattern="#,##0.00"/></td>
					<td class="border_top_right4" align="center" nowrap>
						<c:if test='${renotifyOrder.orderStatus == "0"}'>未付款	</c:if>
						<c:if test='${renotifyOrder.orderStatus == "1"}'>交易关闭 	</c:if>
						<c:if test='${renotifyOrder.orderStatus == "2"}'>已付款	</c:if>
						<c:if test='${renotifyOrder.orderStatus == "3"}'>交易完成含退款 	</c:if>
						<c:if test='${renotifyOrder.orderStatus == "4"}'>交易成功 	</c:if>
						<c:if test='${renotifyOrder.orderStatus == "5"}'>已冲正 	</c:if>
						&nbsp;
					</td>
					<td class="border_top_right4" align="center" nowrap>
						<fmt:formatDate value="${renotifyOrder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap>
						<fmt:formatDate value="${renotifyOrder.dealTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
<!--					<td class="border_top_right4" align="center" nowrap>${renotifyOrder.detailURL}&nbsp;</td>-->
					<td class="border_top_right4" align="center" nowrap>
						<c:if test='${renotifyOrder.orderStatus == "0"}'> 不可重发消息	</c:if>
						<c:if test='${renotifyOrder.orderStatus == "1"}'> 不可重发消息 	</c:if>
						<c:if test='${renotifyOrder.orderStatus == "5"}'> 不可重发消息 	</c:if>
						<a href="javascript:processEdit('${renotifyOrder.tradeOrderInfoID}#${renotifyOrder.orderStatus}')">
							<c:if test='${renotifyOrder.orderStatus == "2"}'> 重发买家已付款消息 	</c:if>
							<c:if test='${renotifyOrder.orderStatus == "3"}'> 重发确认退款消息 	</c:if>
							<c:if test='${renotifyOrder.orderStatus == "4"}'> 重发确认收货消息 	</c:if>
						</a>&nbsp;				
					</td>
				</tr>
			</c:forEach>
		</table>
		<br/>
		<a href="javascript:processEdit(null)">重发已勾选的消息</a>
	</form>
	
	<li:pagination methodName="fiRenotifyQuery" pageBean="${page}" sytleName="black2"/>	
	
</body>

