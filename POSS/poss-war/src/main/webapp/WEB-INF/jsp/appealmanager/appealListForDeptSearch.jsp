<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<script src="./js/common.js"></script>
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";



function appealQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#appealTaskSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/appealListForDeptSearch.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
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

<table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">部 门 申 诉 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

<form id="appealTaskSearchFormBean" name="appealTaskSearchFormBean">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >申诉号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="appealCode" name="appealCode">
		</td>
		<td class="border_top_right4" align="right" >客户姓名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="customerName" name="customerName">
		</td>
		<td class="border_top_right4" align="right" >申诉主体：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="appealBody" name="appealBody">
	    </td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >来电号码：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="callPhone" name="callPhone">
		</td>
		<td class="border_top_right4" align="right" >联系号码：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="linkPhone" name="linkPhone">
		</td>
		
		<td class="border_top_right4" align="right" >联系邮箱：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="linkEmail" name="linkEmail">
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
			<td class="border_top_right4" align="left" colspan="3">
				<select	id="appealLevelCode" name="appealLevelCode" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${appealLevelList}" var="appealLevel">
					<option value="${appealLevel.code}">${appealLevel.name}</option>
					</c:forEach>
				</select>
				
			</td>		
		
		
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >业务类型：</td>
		<td class="border_top_right4" align="left" >
			<select	id="businessTypeCode" name="businessTypeCode" onchange="changeFather('businessTypeCode','productBigTypeCode','productBigTypeList');">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${businessTypeList}" var="businessType">
					<option value="${businessType.code}">${businessType.name}</option>
					</c:forEach>
				</select>
		</td>
		<td class="border_top_right4" align="right" >业务大类：</td>
		<td class="border_top_right4" align="left" >
			<select	id="productBigTypeCode" name="productBigTypeCode" onchange="changeFather('productBigTypeCode','productLittleTypeCode','productLittleTypeList');">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${productBigTypeList}" var="productBigType">
					<option value="${productBigType.code}">${productBigType.name}</option>
					</c:forEach>
				</select>
		</td>
		
		<td class="border_top_right4" align="right" >业务小类：</td>
		<td class="border_top_right4" align="left" >
			<select	id="productLittleTypeCode" name="productLittleTypeCode" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${productLittleTypeList}" var="productLittleType">
					<option value="${productLittleType.code}">${productLittleType.name}</option>
					</c:forEach>
				</select>
		</td>
	</tr>
	<tr class="trForContent1">	
		<td class="border_top_right4" align="right" nowrap>来源：</td>
			<td class="border_top_right4" align="left" >
				<select	id="appealSourceCode" name="appealSourceCode" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${appealSourceList}" var="appealSource">
					<option value="${appealSource.code}">${appealSource.name}</option>
					</c:forEach>
				</select>	
									
		</td>	
		<td class="border_top_right4" align="right" >申诉状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="appealStatusCode" name="appealStatusCode" size="1">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${appealStatusList}" var="appealStatus">
				<option value="${appealStatus.code}">${appealStatus.name}</option>
				</c:forEach>
			</select>
		</td>		
		<td  class="border_top_right4"  align="right">受理时间：</td>
		<td  class="border_top_right4" align="left" >
			<input class="Wdate" type="text" id="startTime" name="startTime"  onClick="WdatePicker()">
				到
			<input class="Wdate" type="text" id="endTime" name="endTime"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})">
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" href="javascript:appealQuery()">
			<img src="./images/query.jpg" border="0"></a></td>
	</tr>

</table>
</form>
<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

</body>

