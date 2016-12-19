<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.float{float:left;clear:right;}
</style>
<script type="text/javascript">

</script>
<div id="editChannelDiv" style="display: none">
	<form action="./channel_currency_management.do" id="channelCurrencyForm">
		<input type="hidden" name="method" value="editChannel"><br><br>
		<input type='hidden' id='orgCurrencyCode' name='orgCurrencyCode' value="${orgcodeQuery}">
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

<!-- <div id="infoLoadingDiv" title="加载信息" -->
<!-- 	style="display: none; width: 200px; padding-top: 30px; height: 70px;"> -->
<%-- 	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, --%>
<!-- 	请稍候... -->
<!-- </div> -->
<body>

<c:forEach items="${map}" var="m">

<table id="userTable" width="100%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">

	<thead>
	<tr>
	<th colspan="2">日期</th>
	<th colspan="7" align="center">
	<c:if test="${m.key==enddate}">
	${date}
	</c:if>
	<c:if test="${m.key!=enddate}">
	${m.key}
	</c:if>
	
	</th>
	</tr>
		<tr>
			<c:if test="${type=='0'}">
			<th >商户号</th>
			<th >商户名称</th>
			</c:if>
			<c:if test="${type=='1'}">
			<th >二级渠道号</th>
			</c:if>
			<th >渠道类别</th>
			<c:if test="${type=='3'}">
			<th >所属渠道</th>
			</c:if>
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
			<c:if test="${type=='0'}">
			<td>${dto.partnerId}</td>
			<td>${dto.partnerName}</td>
			</c:if>
			<c:if test="${type=='1'}">
			<td>${dto.merchantNo}</td>
			</c:if>
			<td>${dto.cardOrg}</td>
			
			<c:if test="${type=='3'}">
			<td>
					<c:choose>
					<c:when test="${dto.orgCode=='10076001'}">卡司</c:when>
					<c:when test="${dto.orgCode=='10079001'}">中银MOTO</c:when>
					<c:when test="${dto.orgCode=='10080001'}">中银</c:when>
					<c:when test="${dto.orgCode=='10003001'}">中国银行</c:when>
					<c:when test="${dto.orgCode=='10002001' }">农业银行</c:when>
					<c:when test="${dto.orgCode=='10075001'}">CREDORAX</c:when>
					<c:when test="${dto.orgCode=='10077001'}">Adyen</c:when>
					<c:when test="${dto.orgCode=='10077002'}">Belto</c:when>
					<c:when test="${dto.orgCode=='10077003'}">Cashu</c:when>
					<c:when test="${dto.orgCode=='10081016'}">前海万融</c:when>
					<c:otherwise>未知机构</c:otherwise>
					</c:choose>
			</td>
			</c:if>
				<td>${dto.bouncedCount}</td>
				<td>${dto.totalCount}</td>
				<td><fmt:formatNumber value="${dto.bouncedRate}"
						pattern="#,##0.00%" /></td>
				<td><fmt:formatNumber value="${dto.fraudAmount}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.totalAmount}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.fraudRate}"
						pattern="#,##0.00%" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</c:forEach>
</body>


<script type="text/javascript" language="javascript">

</script>