<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">

	function add() {

		var channelItemCode = $("#channelItemCode").val();
		var currencyCode = $("#currencyCode").val();
		var orgMerchantCode = $("#orgMerchantCode").val();
		
		if('' == channelItemCode){
			alert("请选择渠道！");
			return;
		}
		
		if('' == currencyCode){
			alert("输入币种！");
			return;
		}
		window.location.href="${ctx}/channelItemRCurrency.do?method=addChannelCurrency&channelItemCode="+channelItemCode+"&currencyCode="+currencyCode;
	}
</script>

</head>

<body>

<h2 class="panel_title">渠道支持币种增加</h2>
<c:if test="${not empty msg}">
			<p>
				<font color="red">${msg}</font></p>
			</c:if>

<table class="border_all2" width="30%" border="0" cellspacing="0" cellpadding="1" align="center">
	<form id="testjop1" action="${ctx}/channelItemRCurrency.do?method=addChannelCurrency" method="post">
	<tr valign="top" class="trForContent1">
		<td align="center" nowrap class="border_top_right4">
		<table width="617" border="0" cellspacing="0" cellpadding="0">
			<tr height="6" class="trForContent1">
				<td align="right" class="border_top_right4">渠道名称</td>
				<td align="left" class="border_top_right4"><select id="channelItemCode" name="channelItemCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${channelItems}" varStatus="v">
							<c:choose> 
	   							<c:when test="${channel.code eq selectChannelItemCode}">
									<option value="${channel.code}" selected="selected">${channel.desc}</option>
								</c:when>
								<c:otherwise>
									<option value="${channel.code}" >${channel.desc}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select></td>
			</tr>
			<tr height="6" class="trForContent1">
				<td align="right" class="border_top_right4">支持币种</td>
				<td align="left" class="border_top_right4">
					<c:choose>
						<c:when test="${! empty inputCurreneyCode}">
							<input type="text" id="currencyCode" name="currencyCode" value="${inputCurreneyCode}">
						</c:when>
						<c:otherwise>
							<input type="text" id="currencyCode" name="currencyCode" value="">
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="2" align="center">
					<a class="s03" href="javascript:add()"><img	src="./images/add.jpg" border="0"> </a>
					<a class="s03" href="${ctx}/channelItemRCurrency.do"><img	src="./images/goback.jpg" border="0"> </a>
				</td>
			</tr>
	</form>
</table>
