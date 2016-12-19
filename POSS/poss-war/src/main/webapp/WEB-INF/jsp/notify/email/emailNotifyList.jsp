<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<center>
<table  align="center" style="width: 90%;margin-left:0px;" id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th class="item15"><input type="checkbox" onclick="cheked();" id="check" /></th>
			<th class="item15">会员号</th>
			<th class="item15">商户号</th>
			<th class="item15">商户名称</th>
			<th class="item15">登录名</th>
			<th class="item15">公司Email</th>
			<th class="item15">通知Email</th>
			<th class="item15">是否开通</th>
			<th class="item15">创建时间</th>
			<th class="item15">操作人</th>
			<th class="item10">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${orderEmailNotifys}" var="oen" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><c:if test="${oen.memberCode !='0' }"><input type="checkbox" value="${oen.id }"/></c:if></td>
				<td align="center">${oen.memberCode}</td>
				<td align="center">${oen.merchantCode}</td>
				<td align="center">${oen.merchantName}</td>
				<td align="center">${oen.loginName}</td>
				<td align="center">${oen.emailCompany }</td>
				<td align="center">${oen.emailNotify }</td>
				<td align="center">${oen.openFlag}</td>
				<td align="center">${oen.createTime }</td>
				<td align="center">${oen.operator }</td>
				<td align="center">
					<a href="javascript:update('${oen.id }','${oen.memberCode }','${oen.merchantCode }','${oen.merchantName }','${oen.loginName }','${oen.emailCompany }','${oen.emailNotify }','${oen.openFlag }','${oen.operator }')">修改</a> | 
					<a href="javascript:updateFlag('${oen.id }','${oen.openFlag }')">是否开通</a>	| 
					<a href="#" onclick="del(${oen.id})">删除</a>			
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="13" align="center"><input type="button" value="批量删除" onclick="bacthDelete() ;" /></td>
		</tr>
		
	</tbody>
	
</table>
</center>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">
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
	
	function bacthDelete(){
		var ids="";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					ids+=$(this).val()+",";							
				}
			};
		 })
		 if(ids==''){
			alert("请选择要删除项！！！");
			return ;	
		}
		if (!confirm("确认删除?")) {
			return;
		} 
		
		 $.ajax({
			type : "POST",
			url : "./orderEmailNotify.do?method=orderEmailNotifydelete",
			data : "ids="+ids,
			success : function(result) {
				search() ;
		}
	});
}
	
	
</script>