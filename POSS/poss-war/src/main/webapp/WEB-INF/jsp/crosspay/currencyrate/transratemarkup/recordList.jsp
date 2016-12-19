<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function list(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/transRateMarkup/markup.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<form action="" method="post" name="listFrom" id="enterpriseSearchFormBean">
<table style="width: 100%;table-layout:fixed;word-break:break-all"  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
		    <th class="item15" style="width: 40px">ID</th>
		    <th class="item10" style="width: 40px">优先级</th>
		    <th class="item15" style="width: 80px">会员号</th>
			<th style="width:150px; white-space:nowrap">交易币种</th>
			<th style="width:150px; white-space:nowrap">目标币种</th>
			<th class="item10">Markup</th>
			<th class="item10">卡组织</th>
            <th class="item10">交易起始金额</th>
            <th class="item10">交易截止金额</th>
            <th class="item10">卡属国</th>
            <th class="item10" style="width:150px; white-space:nowrap">卡本币</th>
			<th class="item10" style="width: 40px">状态</th>
			<th class="item10">生效时间点</th>
			<th class="item10">失效时间点</th>
			<th class="item10" style="width: 130px">创建时间</th>
			<th class="item30">操作员</th>
			<th class="item30">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${markupList}" var="markup" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
		    <td><span></span>${markup.id}<span></span></td>
		    <td><span></span>${markup.priority}<span></span></td>
		    <td><span></span>${markup.memberCode}<span></span></td>
			<td width="100"><p style="word-break:break-word;">${markup.currency}</p></td>
			<td><span></span>${markup.targetCurrency}<span></span></td>
			<td>
				${markup.markup}				
			</td>
			<td>
				${markup.cardOrg}				
			</td>
			<td>
				${markup.startAmount}				
			</td>
            <td>
				${markup.endAmount}				
			</td>
			<td>
				${markup.cardCountry}				
			</td>
            <td>
				${markup.cardCurrencyCode}				
			</td>
			<td>
			   <c:choose>
			         <c:when test="${markup.status=='1'}">
			                                             生效
			         </c:when>
			         <c:when test="${markup.status=='0'}">
			                                             失效
			         </c:when>
			   </c:choose>
			</td>
			<td>${markup.startPoint}</td>
			<td>${markup.endPoint}</td>
			<td><date:date value="${markup.createDate}"/></td>
			<td align="left">${markup.operator}</td>
			<td align="left">
			    <c:choose>
			         <c:when test="${markup.status=='0'}">
			            <a href="${ctx}/transRateMarkup/markup.do?method=updateStatus&id=${markup.id}&status=1&memberCode=${markup.memberCode}&priority=${markup.priority}">生效</a>
			         </c:when>
			         <c:when test="${markup.status=='1'}">
			            <a href="${ctx}/transRateMarkup/markup.do?method=updateStatus&id=${markup.id}&status=0&memberCode=${markup.memberCode}&priority=${markup.priority}">失效</a>
			         </c:when>
			   </c:choose>
			   <a href="${ctx}/transRateMarkup/markup.do?method=edit&id=${markup.id}">编辑</a>
			</td>
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