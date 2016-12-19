<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr align="center">
			<th class="item15" width="100px;">
			选择/反选	<input type="checkbox" onclick="cheked()" id="check">
			</th>
			<th class="item15" width="100px;">会员号</th>
			<th class="item15" width="100px;">域名</th>
			<th class="item10" width="70px;">网站状态</th>
			<th class="item10" width="130px;">是否送往Credorax</th>
			<th class="item10" width="80px;">创建时间</th>
			<th class="item10" width="80px;">网站品类</th>
			<th class="item10" width="80px;">操作员</th>
			<th class="item30" width="50px;">备注</th>
			<th class="item20" width="100px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${websiteConfigs}" var="site" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td><input type="checkbox" value="${site.id}"> </td>
			<td>${site.partnerId}</td>
			<td align="left"><a href="http://${site.url}">http://${site.url}</a></td>
			<td>
				<!--0：冻结1：正常2：待审核3：审核未通过4：已删除  --> 
				<c:if test="${site.status=='0'}">
					冻结	
				</c:if>
				<c:if test="${site.status=='1'}">
					正常
				</c:if>
				<c:if test="${site.status=='2'}">
					待审核
				</c:if>
				<c:if test="${site.status=='3'}">
					审核未通过
				</c:if>
				<c:if test="${site.status=='4'}">
					已删除	
				</c:if>	
				<c:if test="${site.status=='5'}">
					系统审核未通过	
				</c:if>					
				<c:if test="${site.status=='6'}">
					系统审核通过	
				</c:if>				
			</td>
			<td>
				<c:choose>
					<c:when test="${site.sendCredorax=='Y'}">
						是
					</c:when>
					<c:otherwise>
						否
					</c:otherwise>
				</c:choose>
			</td>
			<td><date:date value="${site.createDate}"/></td>
			<td  >${site.category}</td>
			<td>${site.operator}</td>
			<td align="left">${site.remark}</td>
			<td>
				<a href="javascript:toAudit('${site.id}','${site.partnerId}','${site.url}','${site.status}');">审核</a>
				&nbsp;
				<a href="javascript:del('${site.id}');">删除</a>
			</td>
		</tr>
		</c:forEach>
			<tr class="trForContent1">
				<td colspan="10" align="center"><input type="button"	value="批量操作" onclick="bacthReviewed();" /></td>
			</tr>
	</tbody>
</table>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
		<%-- <li_new:page methodName="search" pageBean="${page}" sytleName="black2"/> --%>

 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 
		function bacthReviewed(){
				var siteIds="";
				 $("input[type='checkbox']").each(function(){
					 if(this.checked){
						if($(this).val()!='on'){
							siteIds+=$(this).val()+",";							
						}
					};
				 })
				if(siteIds==''){
					alert("请选择需要审核的域名！");
					return;
				}
				 var pars = $("#mainfromId").serialize();
				 window.location.href="${ctx}/crosspay/sitesetAudit.do?method=bacthReviewed&siteIds="+siteIds+"&"+pars;
		}
		
		function search(pageNo,totalCount,pageSize) {
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#mainfromId").serialize()+  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
			$.ajax({
				type: "POST",
				url: "${ctx}/crosspay/sitesetAudit.do?method=list",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
		
		function cheked(){
			 if($("#check").attr("checked")==true){
				 $("input[type='checkbox']").each(function(){
					 this.checked=true;
				 })
			 }else{
				 $("input[type='checkbox']").each(function(){
					 this.checked=false;
				 })
			 }
		}
</script>