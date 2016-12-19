<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.pay.pe.AmountUtils"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>商户号</th>
			<th>授权商户提交地址</th>
			<th>通知商户地址</th>
			<th style="width:300px;">密钥</th>
			<th>是否需异步通知</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:if test="${list != null}">
			<c:forEach items="${list}" var="dto" varStatus="index">
			<tr>
				<td>
					${dto.id}
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
				<td  style="width:300px;">
					${dto.publicKey}
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
					<a href="${ctx}/context_withhold_merchant.controller.htm?method=toUpdate&id=${dto.id}">更新</a>
				</td>
				
			</c:forEach>
		
		</c:if>
		<c:if test="${list == null}">
			<tr>
			<td colspan="11" align="center">
				没有查询到相关数据!
			</td>
			</tr>
		</c:if>
	</tbody>
</table>

<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 
	
</script>