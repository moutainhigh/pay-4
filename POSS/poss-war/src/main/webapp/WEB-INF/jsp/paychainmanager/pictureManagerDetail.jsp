<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>

<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>

<script language="javascript">
function closePage() {
	var us="picturePayChainManager.do?method=queryDetail";
	parent.closePage(us);
}

function updatePictureStatus(url){
	
	var pars = $("#pictureManagerForm").serialize() ;
	
	var pictureStatus=$("input[type=radio]:checked").val()  ;
	if(pictureStatus == undefined){
		alert("请选择审核方式");
		return ;
	}
	
	var remark=$("#remark").val();
	if(remark.length>80){
		alert("备注的长度不能超过50个汉字");
		return ;
	}
  if(pictureStatus==1){
		if(!confirm("确定审核通过吗?")){
			return;
		}
	}else{
		if(!confirm("确定审核拒绝吗?")){
			return;
		}
	}
	
	$.post("${ctx}/picturePayChainManager.do?method=updatePictureStatus",pars,
			function cbk(result){
			if(result=='1'){
				alert("审核操作成功");
			}else{
				alert("审核操作失败");
			}
			closePage() ;
	});
		
}


</script>
</head>

<body>
<form id="pictureManagerForm" name="pictureManagerForm">

<!-- <input type="hidden" name="payChainNumber" id="payChainNumber" value="${chainDto.payChainNumber}" /> -->
	<input type="hidden" name="pictureOwnerId" id="pictureOwnerId" value="${pictureDto.pictureOwnerId}" />
		<input type="hidden" name="updatedBy" id="updatedBy" value="${pictureDto.updatedBy}" />	
		

<table class="border_all2" width="80%" border="0" cellspacing="0"cellpadding="1" align="center">
	<c:forEach items="${PictureDtoList}" var="dto" varStatus = "dtoStatus">
     <tr class="trForContent2">
  				<td class="border_top_right4" align="center" nowrap>支付链图片${dtoStatus.index+1} :  <img src="${ picturePath}${dto.pictureUrl}"/>;</td>
	</tr>		
	</c:forEach>
</table>

<table class="border_all2" width="80%" border="0" cellspacing="0"cellpadding="1" align="center">	


	<tr class="trForContent2">
		<td class="" align="right" >审核状态：</font></td>
		<td class="" align="left" >
			<c:if test="${pictureDto.pictureStatus==0}">
				未审核
			</c:if>
		 <c:if test="${pictureDto.pictureStatus==1}">
				审核通过
			</c:if>
			<c:if test="${pictureDto.pictureStatus==2}">
				审核拒绝
			</c:if>
		</td>
	</tr>
	<c:if test="${pictureDto.pictureStatus==0}">
		<tr class="trForContent2">
			<td   align="right" >审核操作：</td>
			<td  align="left">
				<input type="radio" name="pictureStatus" value="1">审核通过
				<input type="radio" name="pictureStatus" value="2">审核拒绝
			</td>
		</tr>
	</c:if>
	<tr class="trForContent2">
		<td class="" align="right" >操作员：</td>
		<td class="" align="left" >
			${pictureDto.updatedBy}
		</td>
	</tr>	
	
	<tr class="trForContent2">
			<td class="" align="right" >备注：</td>
			<td class="" align="left" >
				<textarea rows="7" cols="30" id="remark" name="remark" value="" >${pictureDto.remark}</textarea>
			</td>
	</tr>
	
	<tr class="trForContent2">
		<td colspan="2" align="center">
		<c:if test="${pictureDto.pictureStatus==0}">
			<input type = "button"  onclick="javascript:updatePictureStatus();" value="确认">
		</c:if>
			<input type = "button"  onclick="javascript:closePage();" value="关闭">
		</td>
	</tr>
	</table>
</form>

</body>

