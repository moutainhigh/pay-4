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

$(function(){
	var listType= $("#listType").val();
	if(listType=='2'){
		$("#showMsg").html("你确认删除黑名单吗");
	}
	if(listType=='1'){
		$("#showMsg").html("你确认删除白名单吗");
	}
});
function deleteBusinessType(id){
	shouWindow();
    $("#deleteId").val(id);
}
function updateBusinessType(id){
	var listType=$("#listType").val();
	var url="blackWhiteListUpdate.do";
	var tabName="黑名单修改";
	if(listType==1){
		url="whiteListUpdate.do";
		tabName="白名单修改";
	}
	url+="?method=updateBlackWhiteListView&id="+id+"&listType="+listType;
	parent.addMenu(tabName,url);
}

function deleteBlackWhiteList(){
	var id=$("#deleteId").val();
	var remark=$("#deleteRemark").val();
	var data="method=deleteBlackWhiteList&id="+id+"&remark="+remark;
	var url="blackWhiteListDelete.do";
	var listType=$("#listType").val();
	
	if(listType==1){
		url='whiteListDelete.do';
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		success: function(result) {
		   closeFee();
           alert(result);
		}
	});
}

function updateStatus(id,status){
	var data="method=updateBlackWhiteList&id="+id+"&status="+status;
	var url="blackWhiteListUpdateStatus.do";
	var listType=$("#listType").val();
	
	if(listType==1){
		url='whiteListUpdateStatus.do';
	}
	if(status==0){
		if(!confirm("您确认要停用该条名单吗？")) {
			return ;
		}
	}
	if(status==1){
		if(!confirm("您确认要启用该条名单吗？")) {
			return ;
		}
	}
	
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

function shouWindow(){
	$("#saveOrUpdate").val("add");
	$('#addLogDiv').dialog({ 
			position:["center","center"],
			width:300,
			height:200,
			modal:true, 	
			title:'黑名单删除确认', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
}

function closeFee(){
	$("#addLogDiv").dialog("close");  //关闭Dialog
}
</script>
</head>

<body>
<form>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	
	<thead>
	<tr class="" align="center" valign="middle">
	    <th class=""  >业务类型</th>
	    <th class=""  >分类</th>
	    <th class=""  >创建日期</th>
		<th class=""  >内容</th>
		<th class=""  >操作员</th>
		<th class=""  >有效性标志</th>
		<th class=""  >操作</th>

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
		<td class="" align="center" ><ddate:date0 value="${business.createDate}" format="yyyy-MM-dd hh:mm:ss"/>&nbsp;</td>
		<td class="" align="center" >${business.content}&nbsp;</td>
		<td class="" align="center" >${business.operator}&nbsp;</td>
		<td class="" align="center" >
			<c:if test="${business.status==1}">启用</c:if>
			<c:if test="${business.status==0}">停用</c:if>
		&nbsp;
		</td>
		
		<td class="" align="center" >
			<a href="javascript:void(0);" onclick="updateBusinessType('${business.id}');">&nbsp;修改&nbsp;</a>
			<a href="javascript:void(0);" onclick="deleteBusinessType('${business.id}');">&nbsp;删除&nbsp;</a>
		</td>
		</tr>
	</c:forEach>
</table>
</form>
<li:pagination methodName="businessTypeSearch" pageBean="${page}" sytleName="black2"/>
<div id="addLogDiv" name="addLogDiv" align="center" style="display: none">
	<table border="0" style="width: 100%">
	    <tr>
			<td colspan="2" align="center" id="showMsg">                                   

			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			</td>
		</tr>
		<tr>
			<td align="center" >
			   操作备注
			</td>
			<td align="left" >
			  <textarea rows="2" cols="20" name="remark" id="deleteRemark"></textarea>
			</td>
		</tr>
		<tr>
			<td align="right">
				<input type="button" value="确定" onclick="deleteBlackWhiteList()" />
			</td>
			<td align="center">
				<input type="button" value="取消" onclick="closeFee()" />
			</td>
		</tr>
	</table>
	<input type="hidden" id="deleteId" name="id">
</div>
</body>

