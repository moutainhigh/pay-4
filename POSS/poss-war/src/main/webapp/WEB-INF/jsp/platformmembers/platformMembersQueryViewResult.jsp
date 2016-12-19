<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>
<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>

<script type="text/javascript">
function submitfindByCriteriaForm(){
	$("#findByCriteriaForm").submit();
}
function updateMemberRelationStatusWithConfirm(element, srcStatus, targetStatus){
	if(targetStatus == "2") {
		$("span.dialog-message").html("你确定通过该审核请求吗？");
	} else if(targetStatus == "3") {
		$("span.dialog-message").html("你确定拒绝该审核请求吗？");
	} else if(targetStatus == "4") {
		$("span.dialog-message").html("你确定解除会员绑定吗？");
	}
	$("#sureToUpdateButton").attr("memberrelationid", $(element).attr("memberrelationid"));
	$("#sureToUpdateButton").attr("srcstatus", srcStatus);
	$("#sureToUpdateButton").attr("targetstatus", targetStatus);
	$('#dialog-confirm').dialog({
		position : "center",
		width : 350,
		modal : true,
		title : '更新审核状态',
		height : 150,
		overlay : {
			opacity : 0.5,
			background : "black",
			overflow : 'auto'
		}
	});
}

function updateMemberRelationStatus() {
	var memberRelationId = $("#sureToUpdateButton").attr("memberrelationid");
	var srcStatus = $("#sureToUpdateButton").attr("srcstatus");
	var targetStatus = $("#sureToUpdateButton").attr("targetstatus");
	 $.ajax({
         type:"POST",
         url:"${ctx}/platformMembersQuery.do?method=updatePlatformMembersStatus",
         data:{ id : memberRelationId, srcStatus : srcStatus, targetStatus : targetStatus },
         datatype: "json",
         success:function(data){
        	// alert("success: " + data.resultCode);
	        if(data != null && data.resultCode == "0000") {
				var eventNode = $("a[memberrelationid=" + memberRelationId + "]");
				var parent = $(eventNode).parent();
				$(eventNode).remove();
				if(targetStatus == "2") { //已审核
					$(parent).prev().empty();
					$(parent).prev().append("审核通过");
					$(parent).empty();
					$(parent).append('<a memberrelationid="${platformMember.id }"  onclick="updateMemberRelationStatusWithConfirm(this, 4);" href="#">解除绑定</a>');
				} else if(targetStatus == "3") { //已拒绝
					$(parent).prev().empty();
					$(parent).prev().append("审核拒绝");
					$(parent).append("-&nbsp;-");
				} else if(targetStatus == "4") { // 已解绑
					$(parent).prev().empty();
					$(parent).prev().append("绑定已解除");
					$(parent).append("-&nbsp;-");
				}
				$("#dialog-confirm").dialog('close');
			} else {
				$("span.dialog-message").html(data.resultMsg);
			}
	        
         }   ,
         complete: function(XMLHttpRequest, textStatus){
            //$("#dialog-confirm").dialog('close');
         },
         error: function(XMLHttpRequest, textStatus, errorThrown){
        	 //alert(XMLHttpRequest.status);
        	 //alert(XMLHttpRequest.readyState);
        	 //alert(textStatus);
         }
	 });
}

function checkNum(obj) {
	if (isNaN(obj.value)) {
		obj.value = "";
	}
}
function query(pageNo,totalCount,pageSize) {
	var pars = $("#findByCriteriaForm").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	var url = "${ctx}/platformMembersQuery.do?method=findByCriteria&" + pars;
	window.location.href = 	url;
}

function cancel() {
	$('#dialog-confirm').dialog('close');
}

</script>

</head>

<body>
<div id="dialog-confirm" title="加载信息" style="display: none">
	<br/><br/>
	<span class="dialog-message">你确定解绑该银行卡吗？</span>
	<br/><br/><br/>
		<input id="sureToUpdateButton" type="button" onclick="updateMemberRelationStatus();" value="确定">&nbsp;
		<input type="button" onclick="cancel();" value="取消">
		
</div>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">平台会员号绑定管理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="platformMembersQuery.do?method=findByCriteria" method="post" id="findByCriteriaForm" name="findByCriteriaForm">
  <table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">平台会员号：</td>
      	<td class="border_top_right4">
      	    <input type="text" onkeyup="checkNum(this);" id="fatherMemberCode" name="fatherMemberCode" value="${fatherMemberCode }"/>
      	</td>
      	<td class="border_top_right4" align="right" >会员号：</td>
      	<td class="border_top_right4" >
        	<input type="text" id="sonMemberCode" name="sonMemberCode" value="${sonMemberCode }"/>
      	</td>
      	<td class="border_top_right4" align="right" >审核状态：</td>
      	<td class="border_top_right4" >
        	<select name="status" id="status">
        		<option value="" >——请选择——</option>
        		<option value="1">待审核</option>
        		<option value="2">审核通过</option>
        		<option value="3">审核拒绝</option>
        		<option value="4">绑定已解除</option>
        	</select>
      	</td>
    </tr>
	<tr class="trForContent1">
      	<td align="center" class="border_top_right4" colspan="6">
		      <input type="hidden" name="method" value="manualTransSub">
		      <input type="button"  name="submitBtn" onclick="submitfindByCriteriaForm();" value="查 询" class="button2">&nbsp;
      	 </td>
      </tr>
    </tr>
  </table>
 </form>
<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			序号</th>
		<th class=""  >
			高级会员号</th>
		<th class=""  >
			会员号</th>
		<th class=""  >
			会员名称</th>
		<th class=""  >
			创建时间</th>
		<th class=""  >
			商户操作员</th>
		<th class=""  >
			审核状态</th>
		<th class=""  >
		操作</th>
	</tr></thead>
	<c:forEach items="${page.result}" var="platformMember" varStatus = "platformMemberStatus">
			<c:choose>
		       	<c:when test="${platformMemberStatus.index%2==0}">
		             <tr class="trForContent1">
		       </c:when>
		       <c:otherwise>
		             <tr class="trForContent2">
		       </c:otherwise>
			</c:choose>
			<td class="" align="center" >${platformMemberStatus.index}</td>
			<td class="" align="center" >${platformMember.fatherMemberCode}</td>
			<td class="" align="center" >${platformMember.sonMemberCode}</td>
			<td class="" align="center" >${platformMember.sonZhName}</td>
			<td class="" align="center" >${platformMember.createDate}</td>
			<td class="" align="center" >${platformMember.sonOperator}</td>
			<c:choose>
				<c:when test='${platformMember.status == "1"}'>
		             <td class="" align="center" >待审核</td>
		       </c:when>
		       <c:when test='${platformMember.status == "2"}'>
		             <td class="" align="center" >审核通过</td>
		       </c:when>
		       <c:when test='${platformMember.status == "3"}'>
		             <td class="" align="center" >审核拒绝</td>
		       </c:when>
		       <c:when test='${platformMember.status == "4"}'>
		             <td class="" align="center" >绑定已解除</td>
		       </c:when>
	       </c:choose>
			<td class="" align="center" >
				<c:choose>	
					<c:when test='${platformMember.status == "1"}'>
						<a  memberrelationid="${platformMember.id }"  onclick="updateMemberRelationStatusWithConfirm(this, 1, 3);" href="#">拒绝</a>
						&nbsp;
						<a  memberrelationid="${platformMember.id }"  onclick="updateMemberRelationStatusWithConfirm(this, 1, 2);" href="#">通过</a>
					</c:when>
					<c:when test='${platformMember.status == "2"}'>
						<a  memberrelationid="${platformMember.id }"  onclick="updateMemberRelationStatusWithConfirm(this, 2, 4);" href="#">解除绑定</a>
					</c:when>
					<c:otherwise>   
					    -&nbsp;-
					 </c:otherwise> 
			    </c:choose>	
			</td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
</body>
<script type="text/javascript">
	$("#status").val("${status}");
</script>

