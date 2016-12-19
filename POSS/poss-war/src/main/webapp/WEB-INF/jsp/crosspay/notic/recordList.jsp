<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.hnapay.fundout.util.AmountUtils"%>
<%@ taglib prefix="li_new" uri="page"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>公告标题</th>
			<th>创建时间</th>
			<th>操作人</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:if test="${page.result != null}">
			<c:forEach items="${page.result}" var="dto" varStatus="index">
			<tr>
				<td>
					${dto.title}
				</td>
				<td>
					<fmt:formatDate value="${dto.createDate}" type="date"/>
				</td>
				<td>
					${dto.operator}
				</td>				
				<td>
					<a href="${ctx}/crosspay/noticinfo.do?method=toUpdate&id=${dto.id}">修改</a>&nbsp;
				</td>
				
			</c:forEach>
		
		</c:if>
		<c:if test="${page.result == null}">
			<tr>
			<td colspan="11" align="center">
				没有查询到相关数据!
			</td>
			</tr>
		</c:if>
	</tbody>
</table>

<li_new:page methodName="search" pageBean="${page}" sytleName="black2"/>
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