<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.div-inline{ display:inline}
.float{float:left;clear:right;}
</style>
<script type="text/javascript">

</script>
<div id="editChannelDiv" style="display: none">
	<form action="./channel_currency_management.do" id="channelCurrencyForm">
		<input type="hidden" name="method" value="editChannel"><br><br>
		<input type='hidden' id='orgCurrencyCode' name='orgCurrencyCode'>
		<table  id="editChannelTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" >


		</table>
		<br> <br>
		<br>
		<br>
		<input type="button" onclick="javascript:save(this);" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="javascript:cancel1();" value="返回">&nbsp;&nbsp;&nbsp; 
	</form>
</div>
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<body>

<c:forEach items="${map}" var="m">
 
<table id="userTable" width="100%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">

	<thead>
	<tr>
	<th colspan="2">日期</th>
	<th colspan="7" align="center">${m.key}</th>
	</tr>
		<tr>
			<th >商户号</th>
			<th >商户名称</th>
			<th >拒付笔数</th>
			<th >总订单数</th>
			<th >拒付率</th>
			<th >欺诈金额</th>
			<th >总交易额</th>
			<th >欺诈金额比例</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${m.value}" var="dto" varStatus="status">
			<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${dto.partnerId}</td>
				<td>${dto.partnerName}</td>
				<td>${dto.bouncedCount}</td>
				<td>${dto.totalCount}</td>
				<td>${dto.bouncedRate}</td>
				<td>${dto.fraudAmount}</td>
				<td>${dto.totalAmount}</td>
				<td>${dto.fraudRate}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</c:forEach>

</body>


<script type="text/javascript" language="javascript">

</script>