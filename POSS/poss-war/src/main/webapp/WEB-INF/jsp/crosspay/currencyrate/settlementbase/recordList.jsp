<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function list(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}//settlementBaseRate/rate.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<table  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
		    <th class="item15">ID</th>
			<th class="item15">货币代码</th>
			<th class="item15">货币名称</th>
			<th class="item10">交易单位</th>
			<th class="item10">汇率(1外币兑换人民币)</th><!-- 修改字段名称 2016年5月4日 11:25:49--> 
			<th class="item10">汇率(100外币兑换人民币)</th>
			<th class="item10">目标币种</th>
			<th class="item10">目标币种名称</th>
			<th class="item10">状态</th>
			<th class="item10">生效时间</th>
			<th class="item10">失效时间</th>
			<th class="item10">创建时间</th>
			<th class="item30">操作人</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${rateList}" var="rate" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
		    <td><span></span>${rate.id}<span></span></td>
			<td><span></span>${rate.currency}<span></span></td>
			<td>
				<c:forEach var="currency" items="${currencys}" varStatus="v">
					<c:if test="${currency.code == rate.currency}">${currency.desc}</c:if>
				</c:forEach>
			</td>
			<td>
				${rate.currencyUnit }				
			</td>
			<td>
				${rate.exchangeRate }				
			</td>
			<td>
				${rate.reverseExchangeRate }				
			</td>
			<td><span></span>${rate.targetCurrency}<span></span></td>
			<td>
				<c:forEach var="currency" items="${currencys}" varStatus="v">
					<c:if test="${currency.code == rate.targetCurrency}">${currency.desc}</c:if>
				</c:forEach>
			</td>
			<td>
			   <c:choose>
			         <c:when test="${rate.status=='1'}">
			                                             生效
			         </c:when>
			         <c:when test="${rate.status=='0'}">
			                                             失效
			         </c:when>
			   </c:choose>
			</td>
			<td><date:date value="${rate.effectDate}"/></td>
			<td><date:date value="${rate.expireDate}"/></td>
			<td><date:date value="${rate.createDate}"/></td>
			<td align="left">${rate.operator}</td>
			
		</tr>
		</c:forEach>
		
	</tbody>
</table>
<li:pagination methodName="list" pageBean="${page}" sytleName="black2"/>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>