<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<body>

	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>	
		<th>会员号</th>
		<td >
			${dto.memberCode}
		</td>
	</tr>
	<tr>	
		<th>登录名</th>
		<td >
			${dto.loginName}
		</td>
	</tr>
	<tr>
	    <th>账户号</th>
	    <td>
	         ${dto.acctCode }
	    </td>
	</tr>
	<tr>
	    <th>会员类型</th>
	    <c:choose>
	       <c:when test="${dto.memberType==1}">
	             <td>
	                                   个人会员
	             </td>
	       </c:when>
	       <c:otherwise>
	              <td>
	                                     企业会员
	             </td>
	       </c:otherwise>
		</c:choose>
	    
	</tr>
	<tr>	
		<th >可用金额</th>
		<td >
		<fmt:formatNumber value="${dto.balance*0.001}"  type="currency" />
		</td>
	</tr>
	<tr>	
		<th >冻结金额</th>
		<td >
		<fmt:formatNumber value="${dto.frozenAmount*0.001}"  type="currency" />
		</td>
	</tr>
	<tr>	
		<th >可解冻金额</th>
		<td >
		<fmt:formatNumber value="${isfrozenAmount*0.001}"  type="currency" />
		</td>
	</tr>
	<tr>	
		<th >最后账户更新时间</th>
		<td >
			<fmt:formatDate value="${dto.lastBlanceUpdateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
		
	</table>

</body>

