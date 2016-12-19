<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="listFrom">
 </form>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>序号</th>
						<th>商户编号</th>
						<th>授权商户提交地址</th>
						<th>异步通知地址</th>
<!--						<th>商户公钥</th>-->
						<th>创建者</th>
						<th>创建时间</th>
						<th>修改者</th>
						<th>修改时间</th>
						<th>是否需异步通知</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					
						<c:if test="${list != null && fn:length(list) > 0}">
					<c:forEach items="${list}" var="dto" varStatus="index">
					<tr>
						<td>
							<c:out value="${index.count}" />
						</td>
						<td>
							${dto.merchantCode}
						</td>
						<td>
							${dto.authorizeAddress}
						</td>
						<td>
							${dto.notifyUrl}
						</td>
<!--						<td>-->
<!--							${dto.publicKey}-->
<!--						</td>-->
						<td>
							${dto.creator}
						</td>
						<td>
							<fmt:formatDate value="${dto.createDate}" type="both"/>
						</td>
						<td>
							${dto.updator}
						</td>
						<td>
							<fmt:formatDate value="${dto.updateDate}" type="both"/>
						</td>
						<td>
							<c:if test="${dto.notifyFlag == 0}">
								不需要
							</c:if>
							<c:if test="${dto.notifyFlag == 1}">
								需要
							</c:if>
						</td>
						<td>
							<c:if test="${dto.status == 0}">
								关闭
							</c:if>
							<c:if test="${dto.status == 1}">
								正常
							</c:if>
						</td>
						<td>
							<input type="button" onclick="searchMerchantConfigureForId('${dto.id}');"  id="submitBtn" class="button2" value="修 改">
						</td>
					</c:forEach>
				
				</c:if>
				<c:if test="${list == null || fn:length(list) == 0}">
					<tr>
					<td colspan="11" align="center">
						没有查询到相关数据!
					</td>
					</tr>
				</c:if>
			
		</tbody>
	</table>
	<li:pagination methodName="search" pageBean="${resultPage}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				10: {sorter: false}
			}
		});
	 }); 

	function searchMerchantConfigureForId(id){
		document.listFrom.action="context_fundout_merchantconfigure.controller.htm?method=initModify&id="+id;
		document.listFrom.submit();
	}
	
</script>