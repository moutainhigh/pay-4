<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="listFrom">
 </form>
<table id="sorterTable" class="tablesorter" border="0" width="80%"  cellpadding="0" align="center" cellspacing="1">
				<thead>
					<tr>
						<th>直连流水号</th>
						<th>商户ID</th>
						<th>渠道别名</th>
						<th>渠道号</th>
						<th>操作员 </th>
						<th>状态</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody> 
					 <c:forEach items="${directChannelConfigList}" var="direct">    
					<tr>
						<td>
							${direct.directChannelConfigNo}
						</td>
						<td>
							${direct.partnerId}
						</td>
						<td>
							${direct.channelAlias}
						</td>
						<td>
							${direct.paymentChannelItemNo}
						</td>
						<td>
							${direct.operator}
						</td>
						<td>
							<c:choose>
								<c:when test="${direct.status eq '1'}">
									已打开
								</c:when>
								<c:otherwise>
									已关闭
								</c:otherwise>
							</c:choose>
							
						</td>
						<td>
							<fmt:formatDate value="${direct.createDate}" type="date"/>
						</td>
						<td>
							<c:choose>
								<c:when test="${direct.status eq '1'}">
									<a href="javascript:directClose('${direct.directChannelConfigNo}','1','${partnerId}','${channelAlias}','${paymentChannelItemNo}')">关闭</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:directOpen('${direct.directChannelConfigNo}','0','${partnerId}','${channelAlias}','${paymentChannelItemNo}')">打开</a>
								</c:otherwise>
							</c:choose>
						</td>
				</tr>
				</c:forEach>
			
		
		
		</tbody>
	</table>
 <script type="text/javascript">
	 
   function directClose(directChannelConfigNo,statusKy,partnerId,channelAlias,paymentChannelItemNo) {
		if (confirm("确定关闭直连渠道？")) {
			location.href = "ficonfig.htm?method=updateConfigManage&statusKy="+statusKy+"&partnerId="+partnerId+"&channelAlias="+channelAlias+"&paymentChannelItemNo="+paymentChannelItemNo+"&directChannelConfigNo="+directChannelConfigNo;
		}
	}
   function directOpen(directChannelConfigNo,statusKy,partnerId,channelAlias,paymentChannelItemNo) {
		if (confirm("确定打开直连渠道？")) {
			location.href = "ficonfig.htm?method=updateConfigManage&statusKy="+statusKy+"&partnerId="+partnerId+"&channelAlias="+channelAlias+"&paymentChannelItemNo="+paymentChannelItemNo+"&directChannelConfigNo="+directChannelConfigNo;
		}
	}
</script>