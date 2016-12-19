<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>卡号</th>
			<th>有效期月</th>
			<th>有效期年</th>
			<th>CVV</th>
			<th>卡组织</th>
			<th>上传日期</th>
			<th>启用时间</th>
			<th>今日成功笔数</th>
			<th>今日未成功笔数</th>
			<th>本月成功笔数</th>
			<th>批次</th>
			<th>状态</th>
			<th>操作</th>
			<th>选择  <input type="checkbox"  id="check" onclick="allCheckbox();">全选/反选</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${cardInfos}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>
					<input type="hidden" name="id" value="${dto.id}">
						${dto.cardCode}
				</td>
				<td>
					${dto.effectiveMonth}
					
				</td>
				<td>
					${dto.effectiveYear}
				</td>
				<td>
					${dto.cvv }
				</td>
				<td>
					${dto.cardOrg}
				</td>
				<td>
				<fmt:formatDate value="${dto.createDate }" type="both"/> 
				</td>
				<td>
					<c:if test="${dto.status== 1}">
					<fmt:formatDate value="${dto.updateDate }" type="both"/> 
					</c:if>
					<c:if test="${dto.status== 2}">
					</c:if>
				</td>
				<td>
					${dto.daySuccessCount}
				</td>
				<td>
					${dto.failCount}
				</td>
				<td>
					${dto.monthsSuccessCount}
				</td>
				<td>
					${dto.batch}
				</td>
				<td>
					<c:if test="${dto.status== 1}">
					启用
					</c:if>
					<c:if test="${dto.status== 2}">
					暂不使用
					</c:if>
				</td>
				<td>
				<c:if test="${dto.status== 2}">
					<a  onclick="updateStatus(1,${dto.id});" href="#">启用 </a>
				</c:if>
				<c:if test="${dto.status== 1}">
					<a  onclick="updateStatus(2,${dto.id});" href="#">暂不使用</a>	
				</c:if>
				<a  onclick="deletecard(${dto.id});" href="#">删除 </a>
				</td>
				<td>
					<input type="checkbox" id="status" value="${dto.id}">
				</td> 
			</tr>
		</c:forEach>
	</tbody>
</table>

 <script type="text/javascript">
 
 function deletecard(id){
     if(confirm("确定删除吗")){
    	 window.location.href = "${ctx}/manualTransSub.do?method=deletecard&&id="+id+"&pageNo="+pageNo;
     }else{
    	
     }
}
 
 	function updateStatus(obj,id){
 		  var pageNo=$("#pageNo").val();
 		window.location.href = 	"${ctx}/manualTransSub.do?method=updateStatus&status="+obj+"&id="+id+"&pageNo="+pageNo;;
 	}
	function search(pageNo,totalCount,pageSize) {
		var pars = $("#mainfromId").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$("#pageNo").attr("value",pageNo);
		$.ajax({
			type: "POST",
			url: "${ctx}/manualTransSub.do?method=queryCardInfo",
			data: pars,
			success: function(result) {
				$('#resultListDiv').html(result);
			}
		});
	}
	function allCheckbox(){
			$(":checkbox").each(function(){
				if($(this).attr("checked")==true){
						$(this).removeAttr("checked");
				}else{
					$(this).attr("checked","checked");
				}
			});
		}
</script>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>