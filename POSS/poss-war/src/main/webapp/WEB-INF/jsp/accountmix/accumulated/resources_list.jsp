<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>



	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">序列</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">订单类型</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">支付方式</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">交易类型</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">支付服务代码</font></a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">作用方</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">创建时间</font></a></td>	
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">修改时间</font></a></td>	
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">创建人</font></a></td>	
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">修改人</font></a></td>	
			<td class="border_right4" nowrap ><a class="s03"><font
				color="#FFFFFF">操作</font></a></td>				
		</tr>
		<c:forEach items="${page.result}" var="dto" varStatus = "status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
	
		</c:choose>
				<td class="border_top_right4" align="center" nowrap>${dto.id}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.orderType}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.dealCode}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.dealType}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.paymentServiceCode }</td>	
				<td class="border_top_right4" align="center" nowrap>${dto.takeOn==1?"付款方":"<font color='red'>收款方" }</td>	
				<td class="border_top_right4" align="center" nowrap> <fmt:formatDate value="${dto.createDate }" pattern="yyyy/MM/dd HH:mm:ss"  /></td>	
				<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${dto.updateDate }" pattern="yyyy/MM/dd HH:mm:ss"  /></td>	
				<td class="border_top_right4" align="center" nowrap>${dto.createdBy }</td>
				<td class="border_top_right4" align="center" nowrap>${dto.modifiedBy }</td>
					<td class="border_top_right4" align="center" nowrap>
						<a href="javascript:void(0)" onclick="modifyRes('${dto.id}')">修改<a/>
						<a href="javascript:void(0)" onclick="copyAddRes('${dto.id}')">复制新增<a/>
						<a href="javascript:void(0)" onclick="removeRes('${dto.id}')">删除<a/>
					</td>						
			</tr>
		</c:forEach>
	</table>	
	<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>



