<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function list(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/orderfilter/orderfilterRule.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function deleteData(id){
	if(!confirm("您确认要删除该条规则吗？")) {
		return ;
	}
	window.location.href="${ctx}/orderfilter/orderfilterRule.do?method=delete&id="+id;
}
</script>
<form action="" method="post" name="listFrom" id="enterpriseSearchFormBean">
<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
		<tr>
		    <th class="item15" align="center">会员号</th>
		    <th class="item10" align="center">时间范围</th>
		    <th class="item15" align="center">金额限制(CNY)</th>
			<th class="item15" align="center">IP地区</th>
			<th class="item15" align="center">发卡国</th>
			<th class="item10" align="center">卡本币</th>
			<th class="item10" align="center">卡种类</th>
			<th class="item10" align="center">创建时间</th>
			<th class="item30" align="center">操作员</th>
			<th class="item30" align="center">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${oderFilterRuleList}" var="oderFilterRule" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
		    <td><span></span>${oderFilterRule.memberCode}<span></span></td>
		    <td><span></span><ddate:date0 value="${oderFilterRule.startDate}" format="yyyy/MM/dd"/>--<ddate:date0 value="${oderFilterRule.endDate}" format="yyyy/MM/dd"/><span></span></td>
		    <td><span></span>${oderFilterRule.startAmount}--${oderFilterRule.endAmount}<span></span></td>
			<td>${oderFilterRule.ipCountryCode}</td>
			<td>${oderFilterRule.cardCountryCode}</td>
			<td>${oderFilterRule.cardCurrencyCode}</td>
			<td>${oderFilterRule.cardType}</td>
			<td><ddate:date0 value="${oderFilterRule.createDate}" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td align="left">${oderFilterRule.operator}</td>
			<td align="left">
			   <a href="#" onclick="deleteData(${oderFilterRule.id});" style="color: red">删除</a>
			</td>
		</tr>
		</c:forEach>
				<tr>
		</tr>
	</tbody>
</table>
<li:pagination methodName="list" pageBean="${page}" sytleName="black2"/>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>