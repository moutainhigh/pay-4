<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<script src="./js/common.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script language="javascript">

function closePage(url){	
	parent.closePage(url);
}
function appealPictureInfo(url){
	parent.addMenu("申诉图片查看",url);
}
function vaildateAppealState(appealId,status){
	//并发验证
	
	var pars = "appealState=dispense" ;
	$.ajax({
		type: "POST",
		url: "${ctx}/isAppealStateValidate.do?appealId="+appealId,
		data: pars,
		success: function(result) {
			if (result == 'true') {							
				operatorAppeal(status);
			}else{
				alert("此申诉已不在待分发节点,不能重复操作!");	
				return;								
			}
		}
	});
}

function operatorAppeal(status){
	
	//分发
	if(status=='2'){
		if(document.getElementById('appealTypeCode').value==''){
			alert("申诉类型不能为空!");
			return ;
		}
		if(document.getElementById('appealLevelCode').value==''){
			alert("申诉级别不能为空!");
			return ;
		}
		/**
		if(document.getElementById('appealDeptCode').value==''){
			alert("申诉部门不能为空!");
			return ;
		}**/
		if(document.getElementById('businessTypeCode').value==''){
			alert("业务类型不能为空!");
			return ;
		}
		/**
		if(document.getElementById('productBigTypeCode').value==''){
			alert("产品大类不能为空!");
			return ;
		}
		if(document.getElementById('productLittleTypeCode').value==''){
			alert("产品小类不能为空!");
			return ;
		}**/
		document.getElementById('appealStatusCode').value="2";		
	//补充要素	
	}else if(status=='4'){
		if(document.getElementById('remark').value==''){
			alert("处理意见不能为空!");
			return ;
		}
		document.getElementById('appealStatusCode').value="4";		
	//直接处理
	}else if(status=='3'){
		if(document.getElementById('appealTypeCode').value==''){
			alert("申诉类型不能为空!");
			return ;
		}
		if(document.getElementById('appealLevelCode').value==''){
			alert("申诉级别不能为空!");
			return ;
		}
		/***
		if(document.getElementById('appealDeptCode').value==''){
			alert("申诉部门不能为空!");
			return ;
		}***/
		if(document.getElementById('businessTypeCode').value==''){
			alert("业务类型不能为空!");
			return ;
		}
		/**
		if(document.getElementById('productBigTypeCode').value==''){
			alert("产品大类不能为空!");
			return ;
		}
		if(document.getElementById('productLittleTypeCode').value==''){
			alert("产品小类不能为空!");
			return ;
		}**/
		if(document.getElementById('remark').value==''){
			alert("处理意见不能为空!");
			return ;
		}
		document.getElementById('appealStatusCode').value="3";
	//撤销
	}else if(status=='5'){
		if(document.getElementById('remark').value==''){
			alert("处理意见不能为空!");
			return ;
		}
		document.getElementById('appealStatusCode').value="5";
	}else{
		return;
	}
	if(document.getElementById('remark').value!=''){
		var str = document.getElementById('remark').value; 

		if (str.length>1000){ 
			alert("处理意见不能超过1000个字符!");
			return ;
		} 
		
	}	
	document.appealFormBean.submit();
	alert("操作成功!");
	
}
function changeFather(father,son,allSon){
	var relationArray = new Array();
	<c:forEach items = "${relationList}" var = "relation" varStatus = "relationStatus">
		relationArray[${relationStatus.index}] = new dropDownListMode('${relation.fatherDataCode}','${relation.sonDataCode}','${relation.sonName}');	
	</c:forEach>
	var sonArray = new Array();
	if(allSon == 'productBigTypeList'){
		
		<c:forEach items = "${productBigTypeList}" var = "baseData" varStatus = "baseDataStatus">
		sonArray[${baseDataStatus.index}] = new dropDownListMode('','${baseData.code}','${baseData.name}');	
		</c:forEach>
	}
	if(allSon == 'productLittleTypeList'){
		
		<c:forEach items = "${productLittleTypeList}" var = "baseData" varStatus = "baseDataStatus">
		sonArray[${baseDataStatus.index}] = new dropDownListMode('','${baseData.code}','${baseData.name}');	
		</c:forEach>
	}
	
	
	this.appealChangeFatherSelect(father,son,relationArray,sonArray);
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
		<div align="center"><font class="titletext">申 诉 分 发</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form id="appealFormBean" name="appealFormBean"  action="appealInfoForDispense.do" method="post"  >
<input type="hidden" id="appealId" name="appealId" value="${appealDto.appealId}"></input>
<input type="hidden" id="appealCode" name="appealCode" value="${appealDto.appealCode}"></input>
<input type="hidden" id="appealStatusCode" name="appealStatusCode"></input>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
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
							<td class="border_top_right4" align="center" nowrap>
								<textArea id="HistoryRemark" name="HistoryRemark"  rows="3" cols="60" style="BORDER-BOTTOM:   0px   solid;   BORDER-LEFT:   0px   solid;   BORDER-RIGHT:   0px   solid;   BORDER-TOP:   0px   solid;background:#DDF0FE;overflow:auto;
								SCROLLBAR-HIGHLIGHT-COLOR:   #DDF0FE;   /*滚动条及箭头的亮边色*/   
      							SCROLLBAR-SHADOW-COLOR:   #DDF0FE  ;       /*滚动条及箭头的暗边色*/   
						     	SCROLLBAR-3DLIGHT-COLOR:   #DDF0FE ;      /*滚动条及箭头的3D亮边色*/   
						      	SCROLLBAR-TRACK-COLOR:   #DDF0FE  ;         /*滚动条底色*/   
						      	SCROLLBAR-DARKSHADOW-COLOR:#DDF0FE;   /*滚动条及箭头的暗边阴影色*/   
						      	SCROLLBAR-BASE-COLOR:   #DDF0FE;             /*滚动条的基本颜色*/   
						      	SCROLLBAR-ARROW-COLOR:   #9BBBF4;           /*箭头的颜色*/   
						      	SCROLLBAR-FACE-COLOR:   #DDF0FE;   
								TEXT-ALIGN: center;                /*文字居中*/
								">${appealHistory.remark}</textArea>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>申诉类型：</td>
			<td class="border_top_right4" align="left" >
				<select	id="appealTypeCode" name="appealTypeCode" >
					<option value="" selected>---请选择---</option>
						<c:forEach items="${appealTypeList}" var="appealType">
						<option value="${appealType.code}">${appealType.name}</option>
					</c:forEach>
				</select>
				
			</td>
			<td class="border_top_right4" align="right" nowrap>申诉级别：</td>
			<td class="border_top_right4" align="left" >
				<select	id="appealLevelCode" name="appealLevelCode" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${appealLevelList}" var="appealLevel">
					<option value="${appealLevel.code}">${appealLevel.name}</option>
					</c:forEach>
				</select>
				
			</td>			
		</tr>	
		<tr class="trForContent1">
		<!-- 			<td class="border_top_right4" align="right" nowrap>申诉部门：</td>
			<td class="border_top_right4" align="left" >
				<select	id="appealDeptCode" name="appealDeptCode"  >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${appealDeptList}" var="appealDept">
					<option value="${appealDept.code}">${appealDept.name}</option>
					</c:forEach>
				</select>
					
			</td> -->
			
			<td class="border_top_right4" align="right" nowrap>业务类型：</td>
			<td class="border_top_right4" align="left" colspan="3">
				<select	id="businessTypeCode" name="businessTypeCode"  >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${businessTypeList}" var="businessType">
					<option value="${businessType.code}">${businessType.name}</option>
					</c:forEach>
				</select>
					
			</td>			
		</tr>
		<!-- 
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>业务大类：</td>
			<td class="border_top_right4" align="left" >
				<select	id="productBigTypeCode" name="productBigTypeCode"  onchange="changeFather('productBigTypeCode','productLittleTypeCode','productLittleTypeList');" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${productBigTypeList}" var="productBigType" >
					<option value="${productBigType.code}">${productBigType.name}</option>
					</c:forEach>
				</select>
			
			</td>
			<td class="border_top_right4" align="right" nowrap>业务小类：</td>
			<td class="border_top_right4" align="left" >
				<select	id="productLittleTypeCode" name="productLittleTypeCode" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${productLittleTypeList}" var="productLittleType">
					<option value="${productLittleType.code}">${productLittleType.name}</option>
					</c:forEach>
				</select>				
			</td>			
		</tr> -->
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >处理意见：</td>
			<td class="border_top_right4" align="left" colspan="3">
				<textArea id="remark" name="remark" rows="6" cols="100"></textArea>
			</td>
			
		</tr>
</table>
<br></br>
<table>
	<tr><!-- 	
		<td  align="center">	
			<input id="button1" name="button1" type="button" value="分发" onclick="javascript:vaildateAppealState('${appealDto.appealId}','2');">			
		</td> -->
		<td  align="center">	
			<input id="button2" name="button2" type="button" value="补充要素" onclick="javascript:vaildateAppealState('${appealDto.appealId}','4');">		
		</td>
		<td  align="center">	
			<input id="button3" name="button3" type="button" value="直接处理" onclick="javascript:vaildateAppealState('${appealDto.appealId}','3');">				
		</td>
		<td  align="center">	
			<input id="button4" name="button4" type="button" value="撤销" onclick="javascript:vaildateAppealState('${appealDto.appealId}','5');">			
		</td>
		<td  align="center">	
		 	<input id="button5" name="button5" type="button" value="关闭" onclick="javascript:closePage('appealListForDispense.do');">			
		</td>
	</tr>
</table>

</form>


</body>

