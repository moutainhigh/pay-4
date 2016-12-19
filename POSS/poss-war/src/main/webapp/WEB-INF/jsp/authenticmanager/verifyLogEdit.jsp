<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>


<script src="./js/mainstyle1/body.js"></script>


<script language="javascript">
function submitForm(){
	document.getElementById('submitButton').disabled='disabled';
	document.verifyLogFormBean.submit();
}

function closePage(url){
	parent.closePage(url);
}
</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">实 名 认 证 信 息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<form id="verifyLogFormBean" name="verifyLogFormBean" action="verifyLogEdit.do" method="post">
<input type="hidden" id="verifyId" name="verifyId" value="${verifyLogDto.verifyId}">
<input type="hidden" id="memberCode" name="memberCode" value="${verifyLogDto.memberCode}">
<input type="hidden" id="type" name="type" value="${type}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >会员号：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${verifyLogDto.memberCode}
			</td>
			<td class="border_top_right4" align="right" >登录名：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${verifyLogDto.loginName}
			</td>
			
			<td class="border_top_right4" align="right" >认证状态：</td>
			<td class="border_top_right4" align="left" >&nbsp;	
						
					<c:forEach items="${verifyLogStatusEnum}" var="verifyLogStatus">
					<c:if test="${verifyLogStatus.code == verifyLogDto.verifyStatus}"> 
						${verifyLogStatus.description}
					</c:if>
					</c:forEach>
				
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >身份证号码：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${verifyLogDto.cardId}			
			</td>
			<td class="border_top_right4" align="right" >姓名：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${verifyLogDto.memberName}
			</td>
			<td class="border_top_right4" align="right" >申请时间：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${verifyLogDto.createDate}				
			</td>
		</tr>
		
		<tr class="trForContent1">
			<c:if test="${type=='edit'}">
			
			<td class="border_top_right4" align="right" >人工操作：</td>
			<td class="border_top_right4" align="left" colspan="5">&nbsp;
				<select id="policeMessage" name="policeMessage">					
					<option value="1" selected>成功</option>
					<option value="2">失败</option>
				</select>			
			</td>
			</c:if>	
			
		</tr>	
		
		
</table>
<br></br>
<table>
	<tr>	
		<td  align="center">
		<c:if test="${type=='edit'}">
			
			<input type="button" id="submitButton" name="submitButton" value="提交" onclick="javascript:submitForm();" >
			
		</c:if>	
		<c:choose>
				<c:when test="${edited == 'edited'}">
					<input type = "button"  onclick="javascript:closePage('verifyLogEdit.do?verifyId=${verifyLogDto.verifyId}&type=edit');" value="关闭">
				</c:when>
				<c:otherwise>
					<c:choose>
					<c:when test="${isView=='true'}">
						<input type = "button"  onclick="javascript:closePage('verifyLogEdit.do?verifyId=${verifyLogDto.verifyId}&type=edit');" value="关闭">
					</c:when>
					<c:otherwise>
						<input type = "button"  onclick="javascript:closePage('verifyLogEdit.do?verifyId=${verifyLogDto.verifyId}&type=${type}');" value="关闭">
					</c:otherwise>
					</c:choose>
					
				</c:otherwise>
		</c:choose>
		
		</td>
	</tr>
</table>

</form>


</body>

