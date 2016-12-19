<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function list(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/exceptionCardMgr.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

</script>
<form action="" method="post" name="listFrom" id="enterpriseSearchFormBean">
<table style="width: 99%;margin-left:8px;table-layout:fixed;word-break:break-all"  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
		    <th class="item15" align="center">会员号</th>
		    <th class="item10" align="center">时间</th>
		    <th class="item15" align="center">异常卡笔数</th>
			<th class="item15" align="center">失败订单笔数</th>
			<th class="item15" align="center">异常卡比例</th>
			<th class="item10" align="center">前七日分时比例</th>
			<th class="item10" align="center">状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="exceptionCard" items="${list }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			    <td>${exceptionCard.memberCode }</td>
			    <td>${exceptionCard.timeZone }</td>
			    <td>${exceptionCard.exceptionCardCount }</td>
				<td>${exceptionCard.failOrderCount }</td>
				<td>${exceptionCard.exceptionCardPercent }</td>
				<td>${exceptionCard.aweekAgoTimeZonePercent }</td>
				<td>
					<%-- <c:choose>
						<c:when test="${exceptionCard.status == 'N'}">
							正常
						</c:when>
						<c:when test="${exceptionCard.status == 'E'}">
							预警
						</c:when>
					</c:choose> --%>
					<font <c:if test="${exceptionCard.status == '预警'}">style="color:red;"</c:if>>${exceptionCard.status}</font>
					
				</td>
			</tr>
		</c:forEach>
		<%-- <tr>
			<td colspan="7" align="center"><li:pagination methodName="list" pageBean="${page}" sytleName="black2"/></td>
		</tr> --%>
	</tbody>
</table>
	<div>
		日总计：会员号：${memberCode } 时间：${time }
		异常卡笔数：${dayCount1.aweekExceptionCardCount }
		失败订单数：${dayCount1.aweekFailOrderCount }
		异常卡比例：${dayCount1.percent }
		前七日比例：${dayCount7.percent }
	</div>
</form>
<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>