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
function deleteBusinessType(id){
	var url="blackWhiteListApprovalDelete.do?method=deleteBlackWhiteList&id="+id;
	location.href = url;
}
function updateBusinessType(id){
	var url="blackWhiteListApprovalUpdate.do?method=updateBlackWhiteListView&id="+id;
	parent.addMenu("修改名单",url);
}

function  approval(approvalFlg){
	var ids="0";
	$("input[id*='approval_']:checkbox:checked").each(function(){	
		var checked = $(this).attr("checked");
		if(checked){
			if(ids==="0"){
				ids=$(this).val();
			}else{
				ids+=","+$(this).val();
			}
		}
	});
	
	if(ids=="0"){
		alert("请选择要审核的记录");
		return false;
	}
	
	if(approvalFlg==1){
		if(!confirm("您确认要审核通过吗？")) {
			return false;
		}
	}else if(approvalFlg==0){
		if(!confirm("您确认要审核拒绝吗？")) {
			return false;
		}
	}
	

	
	var remark=$("#remark").val();
	var data ="ids="+ids+"&remark="+remark+"&approvalFlg="+approvalFlg+"&method=approval"
	var url="blackWhiteListApproval.do";
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		success: function(result) {
           alert(result);
           businessTypeSearch();
		}
	});
}

$(function(){
	$("#approval").click(function(){
		var checked = $(this).attr("checked");
		if(checked){
			$("input[id*='approval_']").each(function(){
				var disabled=$(this).attr("disabled");
				if(!disabled){
					$(this).attr("checked",true);
				}
			});
		}else{
			$("input[id*='approval_']").attr("checked",false);
		}
	});
});
</script>
</head>

<body>
<form>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
		<tr class="" align="center" valign="middle">
		    <th class=""  ><input type="checkbox" id="approval"></th>
		    <th class=""  >业务类型</th>
		    <th class=""  >分类</th>
		    <th class=""  >创建日期</th>
			<th class=""  >内容</th>
			<th class=""  >操作员</th>
			<th class=""  >操作类型</th>
			<th class=""  >操作备注</th>
			<th class=""  >状态</th>
		</tr>
	</thead>
	<c:forEach items="${page.result}" var="business" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>
	    <td class=""  align="center"  ><input type="checkbox" id="approval_${personalStatus.index}" 
	    value="${business.id}" <c:if test="${business.status==1||business.status==-1}">disabled="disabled"</c:if>></td>
	    <td class="" align="center" >
			<c:if test="${business.businessTypeId == 1}">卡号</c:if>
			<c:if test="${business.businessTypeId == 2}">邮箱</c:if>
			<c:if test="${business.businessTypeId == 3}">IP</c:if>
			<c:if test="${business.businessTypeId == 4}">国家</c:if>
			<c:if test="${business.businessTypeId == 5}">MAC地址</c:if>
			<c:if test="${business.businessTypeId == 6}">手机号码</c:if>
			<c:if test="${business.businessTypeId == 7}">证件号码</c:if>
			<c:if test="${business.businessTypeId == 8}">收货地址</c:if>
			<c:if test="${business.businessTypeId == 9}">账单地址</c:if>
			<c:if test="${business.businessTypeId == 10}">卡Bin段</c:if>
			<c:if test="${business.businessTypeId == 11}">IP地址段</c:if>
		</td>
		<td class="" align="center" >
			<c:if test="${business.partType == 1}">全匹配</c:if>
			<c:if test="${business.partType == 2}">部分匹配</c:if>
			<c:if test="${business.partType == 3}">包含</c:if>
			<c:if test="${business.partType == 4}">区段</c:if>
		</td>			
		<td class="" align="center" ><ddate:date0 value="${business.createDate}" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
		<td class="" align="center" >${business.content}&nbsp;</td>
		<td class="" align="center" >${business.operator}&nbsp;</td>
		<td class="" align="center" >
			<c:if test="${business.approvalType == 1}">添加黑名单</c:if>
			<c:if test="${business.approvalType == 2}">删除黑名单</c:if>
			<c:if test="${business.approvalType == 3}">修改黑名单</c:if>
		&nbsp;</td>
		<td class="" align="center" >${business.remark}&nbsp;</td>
		<td class="" align="center" >
			<c:if test="${business.status == -1}">审核拒绝</c:if>
			<c:if test="${business.status == 0}">待审核</c:if>
			<c:if test="${business.status == 1}">审核通过</c:if>
		&nbsp;
		</td>
		</tr>
	</c:forEach>
</table>
</form>
<li:pagination methodName="businessTypeSearch" pageBean="${page}" sytleName="black2"/>

<table border="0" cellspacing="0" style="width:100%">
   <tr>
   	<td width="50%" align="right">操作备注：</td>
       <td colspan="" align="left">
           <textarea rows="2" cols="30" name="remark" id="remark"></textarea>
       </td>
   </tr>

   <tr>
      <td align="right">
         <input type="button"  name="butSubmit" value="审核通过" class="button2" onclick="approval(1);">
      </td>
      <td align="left">
         <input type="button"  name="butSubmit" value="审核拒绝" class="button2" onclick="approval(0);">
      </td>
   </tr>
</table>

</body>

