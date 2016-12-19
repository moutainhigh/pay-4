<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


	<!-- <div style="width: 95%;text-align: right;margin: 2px" ><a href="javascript:void(0)" onclick="downloadList()">下载查询结果</a></div> -->
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">日期</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">会员号</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">账户名称</font> </a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">支付服务名称</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">价格策略名称</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">计算方式</font></a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">手续费（元）</font></a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">失败原因</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">操作</font></a></td>			
						
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
				<td class="border_top_right4" align="center" nowrap>${dto.memberCode}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.acctName}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${dto.serviceName }</td>
				<td class="border_top_right4" align="center" nowrap>${dto.priceStrategyName }</td>
				<td class="border_top_right4" align="center" nowrap>${dto.calcuMethod }</td>
				<td class="border_top_right4" align="center" nowrap><fmt:formatNumber pattern="0.00">${dto.fee }</fmt:formatNumber></td>
				<td class="border_top_right4" align="center" nowrap>${dto.errorMsg }</td>
				<td class="border_top_right4" align="center" nowrap><a href="javascript:void(0)" onclick="chargeback('${dto.voucherCode}','${dto.dealType}')">扣费</a> </td>
			</tr>
		</c:forEach>
	</table>	
	<input type="hidden" id="totalCount" value="${page.totalCount }" />
 	<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>
	
<script type="text/javascript">
<!--
function chargeback(vcode,dtype){
	
	if(!confirm("确认要扣费吗？"))
		return ;
	
	var url = "${ctx}/if_poss/accoFeeFailed.do?method=chargeback";
	$.post(url,{"voucherCode":vcode,"dealType":dtype}
			,function (msg){
					if(msg=="S"){
						alert("扣费成功");
						indexQuery();
					}
					else{
						alert(msg);
					}
			}
		);
}

//-->
</script>
	



