<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script language="javascript">

function closePage(url){	
	parent.closePage(url);
}
function appealPictureInfo(url){
	parent.addMenu("申诉图片查看",url);
}
</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">申 诉 信 息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form id="appealFormBean" name="appealFormBean"  action="appealInfoForFinish.do" method="post"  >
<input type="hidden" id="appealId" name="appealId" value="${appealDto.appealId}"></input>
<input type="hidden" id="appealStatusCode" name="appealStatusCode"></input>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>申诉号：</td>
			<td class="border_top_right4" align="left" >
				&nbsp;&nbsp;${appealDto.appealCode}
			</td>
			<td class="border_top_right4" align="right" nowrap>申诉状态：</td>
			<td class="border_top_right4" align="left" >
				&nbsp;&nbsp;
				<c:forEach items="${appealStatusList}" var="appealStatus">
						<c:if test="${appealStatus.code == appealDto.appealStatusCode}">
							${appealStatus.name}
						</c:if>
				</c:forEach>
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>客户姓名：</td>
			<td class="border_top_right4" align="left" >
				&nbsp;&nbsp;${appealDto.customerName}
			</td>
			<td class="border_top_right4" align="right" nowrap>发生时间：</td>
			<td class="border_top_right4" align="left" >
				&nbsp;&nbsp;${fn:substring(appealDto.occurTime, 0, 10)}
			</td>
		</tr>				
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>是否需要回复：</td>
			<td class="border_top_right4" align="left" >
				
				<c:choose>
	       			<c:when test="${appealDto.isNeedReply==1}">
	             		&nbsp;&nbsp;是
	       			</c:when>
	       			<c:when test="${appealDto.isNeedReply==0}">
	             		&nbsp;&nbsp;否
	       			</c:when>
	       			<c:otherwise>
	             		&nbsp;&nbsp;&nbsp;
	       			</c:otherwise>
				</c:choose>						
			</td>
			<td class="border_top_right4" align="right" nowrap>是否紧急：</td>
			<td class="border_top_right4" align="left" >
				<c:choose>
	       			<c:when test="${appealDto.isUrgency==1}">
	             		&nbsp;&nbsp;是
	       			</c:when>
	       			<c:when test="${appealDto.isUrgency==0}">
	             		&nbsp;&nbsp;否
	       			</c:when>
	       			<c:otherwise>
	             		&nbsp;&nbsp;&nbsp;
	       			</c:otherwise>
				</c:choose>										
			</td>
		</tr>	
		<tr class="trForContent1">
			
			<td class="border_top_right4" align="right" nowrap>来源：</td>
			<td class="border_top_right4" align="left" >&nbsp;&nbsp;
				<c:forEach items="${appealSourceList}" var="appealSource">
						<c:if test="${appealSource.code == appealDto.appealSourceCode}">
							${appealSource.name}
						</c:if>
				</c:forEach>
									
			</td>
			<td class="border_top_right4" align="right" nowrap>邮箱：</td>
			<td class="border_top_right4" align="left" colspan="3" >
				&nbsp;&nbsp;${appealDto.linkEmail }	
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>来电号码：</td>
			<td class="border_top_right4" align="left" >
				&nbsp;&nbsp;${appealDto.callPhone }		
			</td>
			<td class="border_top_right4" align="right" nowrap>联系电话：</td>
			<td class="border_top_right4" align="left" >
				&nbsp;&nbsp;${appealDto.linkPhone }	
			</td>			
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>申诉主体：</td>
			<td class="border_top_right4" align="left" colspan="3">				
				<textArea id="appealBody" name="appealBody" rows="6" cols="100" readonly="readonly">${appealDto.formatAppealBody}</textArea>
				<a href="javascript:appealPictureInfo('appealPictureInfo.do?appealCode=${appealDto.appealCode}');">&nbsp;&nbsp;查看图片</a>
			</td>
			
			
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="center" nowrap colspan="4">申诉处理历史</td>			
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap colspan="4">
				<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="1" align="center">
					
					<tr class="trbgcolorForTitle" align="center" valign="middle">
						<td class="border_right4"  nowrap width="15%"><a class="s03"><font color="#FFFFFF">操作时间</font> </a></td>
						<td class="border_right4"  nowrap width="15%"><a class="s03"><font color="#FFFFFF">操作员</font> </a></td>
						<td class="border_right4"  nowrap width="15%"><a class="s03"><font color="#FFFFFF">部门</font> </a></td>
						<td class="border_right4"  nowrap width="15%"><a class="s03"><font color="#FFFFFF">状态</font> </a></td>
						<td class="border_right4"  nowrap width="60%"><a class="s03" ><font color="#FFFFFF">处理意见</font> </a></td>
					</tr>
					<c:forEach items="${appealHistoryList}" var="appealHistory">
						<tr class="trForContent1">
							<td class="border_top_right4" align="center" nowrap >&nbsp;${appealHistory.createTime }&nbsp;</td>
							<td class="border_top_right4" align="center" nowrap >&nbsp;
								<c:forEach items="${userBaseDataList}" var="user">								
									<c:if test ="${appealHistory.operatorId == user.code}">
										${user.name}
									</c:if>
								</c:forEach>&nbsp;
							</td>
							<td class="border_top_right4" align="center" nowrap >&nbsp;
								<c:forEach items="${appealDeptList}" var="appealDept">								
									<c:if test ="${appealHistory.operatorDeptCode == appealDept.code}">
										${appealDept.name}
									</c:if>
								</c:forEach>&nbsp;
							</td>
							<td class="border_top_right4" align="center" nowrap >&nbsp;
								<c:forEach items="${appealStatusList}" var="appealStatus">								
									<c:if test ="${appealHistory.appealStatusCode == appealStatus.code}">
										${appealStatus.name}
									</c:if>
								</c:forEach>
							&nbsp;</td>
							<td class="border_top_right4" align="center"  >
								<div  style="width:100%;word-break : break-all ">
										&nbsp;${appealHistory.remark }&nbsp;
									</div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>申诉类型：</td>
			<td class="border_top_right4" align="left" >&nbsp;&nbsp;			
				<c:forEach items="${appealTypeList}" var="appealType">
						<c:if test="${appealType.code == appealDto.appealTypeCode}">
							${appealType.name}
						</c:if>
				</c:forEach>
			</td>
			<td class="border_top_right4" align="right" nowrap>申诉级别：</td>
			<td class="border_top_right4" align="left" >&nbsp;&nbsp;
				<c:forEach items="${appealLevelList}" var="appealLevel">
						<c:if test="${appealLevel.code == appealDto.appealLevelCode}">
							${appealLevel.name}
						</c:if>
				</c:forEach>
				
				
			</td>			
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>申诉部门：</td>
			<td class="border_top_right4" align="left" >	&nbsp;&nbsp;		
				<c:forEach items="${appealDeptList}" var="appealDept">
						<c:if test="${appealDept.code == appealDto.appealDeptCode}">
							${appealDept.name}
						</c:if>
				</c:forEach>	
			</td>
			<td class="border_top_right4" align="right" nowrap>业务类型：</td>
			<td class="border_top_right4" align="left" >&nbsp;&nbsp;
			
				<c:forEach items="${businessTypeList}" var="businessType">
						<c:if test="${businessType.code == appealDto.businessTypeCode}">
							${businessType.name}
						</c:if>
				</c:forEach>		
			</td>			
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>业务大类：</td>
			<td class="border_top_right4" align="left" >&nbsp;&nbsp;				
				<c:forEach items="${productBigTypeList}" var="productBigType">
						<c:if test="${productBigType.code == appealDto.productBigTypeCode}">
							${productBigType.name}
						</c:if>
				</c:forEach>
			</td>
			<td class="border_top_right4" align="right" nowrap>业务小类：</td>
			<td class="border_top_right4" align="left" >	&nbsp;&nbsp;	
				<c:forEach items="${productLittleTypeList}" var="productLittleType">
						<c:if test="${productLittleType.code == appealDto.productLittleTypeCode}">
							${productLittleType.name}
						</c:if>
				</c:forEach>				
			</td>			
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >是否我司责任：</td>
			<td class="border_top_right4" align="left" >
				<c:choose>
	       			<c:when test="${appealDto.isSelfDuty==1}">
	             		&nbsp;&nbsp;是
	       			</c:when>
	       			<c:when test="${appealDto.isSelfDuty==0}">
	             		&nbsp;&nbsp;否
	       			</c:when>
	       			<c:otherwise>
	             		&nbsp;&nbsp;&nbsp;
	       			</c:otherwise>
				</c:choose>		
							
			</td>
			<td class="border_top_right4" align="right" >客户满意度：</td>
			<td class="border_top_right4" align="left" >&nbsp;&nbsp;
				<c:forEach items="${customerReplyList}" var="customerReply">
						<c:if test="${customerReply.code == appealDto.customerReplyCode}">
							${customerReply.name}
						</c:if>
				</c:forEach>
							
			</td>
		</tr>
</table>
<br></br>
<table>
	<tr>	 
		<td  align="center">	
		 	<input id="button5" name="button5" type="button" value="关闭" onclick="javascript:closePage('appealInfoForAdminSearch.do?appealId=${appealDto.appealId}');">			
		</td>
	</tr>
</table>

</form>


</body>

