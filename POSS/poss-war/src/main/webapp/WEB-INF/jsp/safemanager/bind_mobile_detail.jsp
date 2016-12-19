<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
<!--
	.inputTable th,.inputTable th{
		height: 30px;
		width:80px;
	}
	
-->
</style>
<body>
	
	<table class="inputTable" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
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
		<th  >操作员姓名</th>
		<td >
			${dto.operatorFullName}&nbsp;
		</td>
	</tr>
	<tr>	
		<th  >证件号码</th>
		<td >
			${dto.certCode}&nbsp;
		</td>
		<th  >部门</th>
		<td >
			${dto.department}&nbsp;
		</td>
		<th  >类型</th>
		<td >
			${dto.isAdmin==1?"管理员":"操作员"}
		</td>
	</tr>
	<tr>	
		<th  >email</th>
		<td >
			${dto.operatorEmail}&nbsp;
		</td>
		<th  >电话</th>
		<td >
			${dto.operatorPhone} &nbsp;
		</td>
		<th  >手机</th>
		<td >
			${dto.bindMobile} &nbsp;
		</td>
	</tr>
	<tr>	
		<th  >状态</th>
		<td >
		${dto.statusMsg}
		</td>
		<th  >创建时间</th>
		<td >
		${dto.createDate}
		</td>
		<th  >&nbsp;</th>
		<td >
			&nbsp;
		</td>
	</tr>
	
	
	</table>


</body>

