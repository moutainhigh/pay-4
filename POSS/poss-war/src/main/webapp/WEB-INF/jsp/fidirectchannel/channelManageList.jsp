<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="listFrom">
 </form>
<table id="sorterTable" class="tablesorter" border="0" width="80%"  cellpadding="0" align="center" cellspacing="1">
				<thead>
					<tr>
						<th>ID</th>
						<th>别名</th>
						<th>名称</th>
						<th>描述</th>
						<th>状态</th>
						<th>借代卡类型</th>
						<th>机构代码</th>
						<th>服务地址</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody> 
					 <c:forEach items="${paymentChannelItemList}" var="pay">    
					<tr>
						<td>
							${pay.id}
						</td>
						<td>
							${pay.alias}
						</td>
						<td>
							${pay.itemName}
						</td>
						<td>
							${pay.description}
						</td>
						<td>
							<c:choose>
								<c:when test="${pay.status eq '1'}">
									已打开
								</c:when>
								<c:otherwise>
									已关闭
								</c:otherwise>
							</c:choose>
							
						</td>
						<td>
							${pay.debitType}
						</td>
						<td>
							${pay.orgcode}
						</td>
						<td>
							${pay.serviceUrl}
						</td>
						<td>
							<fmt:formatDate value="${pay.createDate}" type="date"/>
						</td>
						<td>
							<c:choose>
								<c:when test="${pay.status eq '1'}">
									<a href="javascript:channelClose('${pay.id}','${id}','${alias}','${orgcode}')">关闭</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:channelOpen('${pay.id}','${id}','${alias}','${orgcode}')">打开</a>
								</c:otherwise>
							</c:choose>
						</td>
				</tr>
				</c:forEach>
			
		
		
		</tbody>
	</table>
 <script type="text/javascript">
	 
   function channelClose(payid,id,alias,orgcode) {
		if (confirm("确定关闭资金渠道？")) {
			location.href = "channelconfig.htm?method=updatechannelManage&payid="+payid+"&statusKy="+1+"&alias="+alias+"&orgcode="+orgcode+"&id="+id;
		}
	}
   function channelOpen(payid,id,alias,orgcode) {
		if (confirm("确定打开资金渠道？")) {
			location.href = "channelconfig.htm?method=updatechannelManage&payid="+payid+"&statusKy="+0+"&alias="+alias+"&orgcode="+orgcode+"&id="+id;
		}
	}
</script>