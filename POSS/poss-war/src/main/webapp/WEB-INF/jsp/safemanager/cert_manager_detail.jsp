<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
<!--
	.inputTable th,.inputTable th{
		height: 30px;
		width:80px;
	}
	.centerTh th,.oddTd td,.eddTd td {
		text-align: center;
		height: 30px;
		width:80px;
	}
	.oddTd td {
		background-color: #ccc;
	}
	
	
-->
</style>
<script type="text/javascript">

</script>
<body>
	
	<table class="inputTable" width="700" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>	
		<th  >会员号</th>
		<td >
			${dto.memberCode}
		</td>
		<th  >商户号</th>
		<td >
			${dto.merchantCode}
		</td>
		<th  >商户名称</th>
		<td width="80">
			${dto.memberName}
		</td>
	</tr>
	<tr>	
		<th  >用户名</th>
		<td >
			${dto.loginName}&nbsp;
		</td>
		<th  >操作员标识</th>
		<td >
			${dto.operatorName}&nbsp;
		</td>
		<th  >类型</th>
		<td >
			${dto.isAdmin==1?"管理员":"操作员"}
		</td>
	</tr>
	<tr>	
		<th  >证件号码</th>
		<td >
			${dto.certCode}&nbsp;
		</td>
		<th  >绑定手机 </th>
		<td colspan="3">
			${dto.bindMobile}&nbsp;
		</td>
		
		
	</tr>
	
	<tr>	
		<th colspan="6" >证书信息</th>
	</tr>
	<tr class="centerTh">
		<th > 编号</th>
		<th > 使用地点</th>
		<th > 使用地点状态</th>
		<th colspan="2"> 创建时间 </th>
		<th > 操作 </th>
	</tr>
	
	<c:forEach var="useDto" items="${dto.operatorCertUseDtos}" varStatus="status">
		
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="oddTd">
	       </c:when>
	       <c:otherwise>
	             <tr class="eddTd">
	       </c:otherwise>
		</c:choose>
			<td>${status.count }</td>
			<td>${useDto.usePalce }</td>
			<td id="statusTd_${useDto.certManageId}">${useDto.status==1?"有效":"无效" }</td>
			<td colspan="2"><fmt:formatDate value="${useDto.createDate }"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td id="revTd_${useDto.certManageId}">
			<c:if test="${useDto.status==1}"><a href="javascript:void(0)" style="color:blue" onclick="disableUsePlace('${useDto.certManageId}','${useDto.usePalce}')">删除</a></c:if>
			&nbsp;
			</td>
		</tr>
	</c:forEach>
	
	
	
	</table>


</body>

