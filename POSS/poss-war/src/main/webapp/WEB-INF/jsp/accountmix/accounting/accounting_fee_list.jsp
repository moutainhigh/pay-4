<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


	<div style="width: 95%;text-align: right;margin: 2px" ><a href="javascript:void(0)" onclick="downloadList()">下载查询结果</a></div>
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">日期</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">会员类型</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">会员号</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">账户名称</font> </a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">交易类型</font></a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">订单金额（元）</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">手续费（元）</font></a></td>	
						
		</tr>
		<c:forEach items="${page.result}" var="dto" varStatus = "status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
	
		</c:choose>
				<td class="border_top_right4" align="center" nowrap> <fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td class="border_top_right4" align="center" nowrap>${dto.userTypeMsg}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.memberCode}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.acctName}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.dealTypeMsg }</td>	
				<td class="border_top_right4" align="center" nowrap> <fmt:formatNumber pattern="0.00">${dto.orderAmount }</fmt:formatNumber> </td>
				<td class="border_top_right4" align="center" nowrap><fmt:formatNumber pattern="0.00">${dto.fee }</fmt:formatNumber></td>
			</tr>
		</c:forEach>
	</table>	
	<input type="hidden" id="totalCount" value="${page.totalCount }" />
 	<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>
	
<script type="text/javascript">
<!--
function downloadList(){
	var totalCount = $("#totalCount").val();
	if(totalCount>5000){
		alert("记录数最多不能超过5000，请缩小范围再下载!");
		return ;
	}
	if( totalCount==0){
		alert("无记录，无需下载!");
		return ;
	}
	var par = $("#searchFormBean").serialize();
	var url = "${ctx}/if_poss/accountingFee.do?method=downloadFeeList&"+par;
	window.open(url);
}

//-->
</script>
	



