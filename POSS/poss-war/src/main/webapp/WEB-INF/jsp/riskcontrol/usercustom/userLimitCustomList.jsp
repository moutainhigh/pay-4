<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/rmtaglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%> 
<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	11: {sorter: false},
				 	12: {sorter: false}
				 }});      
		});

		function processDelete(id) {
			if(confirm("确认删除?")){
				$('#infoLoadingDiv').dialog('open');
				var pars =  "id=" + id ;
				$.ajax({
					type: "POST",
					url: "${ctx}/rm_limit_businesslimitcustom.htm?method=businessLimitCustomDelete",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						if (result == 'success') {
							searchBusinessLimitCustom();
						}else{
							alert("删除失败!");
						}
					}
				});
			}
		}
		
		function processEdit(id){
			location.href = "rm_limit_businesslimitcustom.htm?method=businessLimitCustomUpdateForm&id=" + id;
		}

	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>    
				<th>流水号</th>
				<th>会员号</th>     
				<th>业务类型</th>     
<!--				<th>商户名称</th>-->

				<th>单笔限额</th>     
				<th>每日累计</th>
				<th>每月累计</th>				     
				<th>每日次数</th>     
				<th>每月次数</th>

				<th>状态</th>
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="businesslimitcustom" varStatus = "personalStatus">
			<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose> 
				<td>${businesslimitcustom.sequenceId}</td>      
				<td>${businesslimitcustom.memberCode}</td>
				<td>
				<li:codetable selectedValue="${businesslimitcustom.businessType}" style="desc" fieldName="businessType" attachOption="true" codeTableId="rmPersonBusiTypeTable" />
				</td>  
 
				<td><fmt:formatNumber value="${businesslimitcustom.singleLimit/1000}" pattern="#,##0.00"  /></td> 
				<td><fmt:formatNumber value="${businesslimitcustom.dayLimit/1000}" pattern="#,##0.00"  /></td>     
				<td><fmt:formatNumber value="${businesslimitcustom.monthLimit/1000}" pattern="#,##0.00"  /></td> 
				<td>${businesslimitcustom.dayTimes}</td>
				<td>${businesslimitcustom.monthTimes}</td>

				<td><li:code2name itemList="${statusList}" selected="${businesslimitcustom.status}"/></td>

				<td><a href="${ctx}/rm_limit_userlimitcustom.htm?method=businessLimitCustomUpdateForm&id=${businesslimitcustom.sequenceId}" class="nyroModal">编辑</a></td> 
<!--				<td><a href="javascript:processDelete('${businesslimitcustom.sequenceId}')">删除</a></td>  -->
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="searchBusinessLimitCustom" pageBean="${page}" sytleName="black2"/>
</body>

