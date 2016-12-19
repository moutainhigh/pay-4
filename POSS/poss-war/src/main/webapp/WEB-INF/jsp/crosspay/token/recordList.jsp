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
function forCheck(url){
	parent.addMenu("商户编辑",url);
}
</script>

<script type="text/javascript">
function submitTokenQuery(pageNo,totalCount,pageSize){
	$("#tokenQuerySubmit").submit();
}
function tokenCardQuery(pageNo,totalCount,pageSize){
	var criteria = $("#tokenQuerySubmit").serialize();
	window.location.href = 	"${ctx}/tokenpay.do?method=list&pageNo="+pageNo+"&totalCount="+totalCount+"&pageSize="+pageSize+"&"+criteria;
}
function unbindWithConfirm(element){
	$("#sureToUnbindButton").attr("token", $(element).attr("token"));
	$('#dialog-confirm').dialog({
		position : "center",
		width : 350,
		modal : true,
		title : '解绑卡',
		height : 150,
		overlay : {
			opacity : 0.5,
			background : "black",
			overflow : 'auto'
		}    
	});
}

function unbind() {
	var token = $("#sureToUnbindButton").attr("token");
	 $.ajax({
         type:"POST",
         url:"${ctx}/tokenpay.do?method=unbindCard",
         data:{ token:token },
         datatype: "json",
         success:function(data){
	        if(data != null && data.responseCode == "0000") {
				var eventNode = $("a[token=" + token + "]");
				var parent = $(eventNode).parent();
				$(eventNode).remove();
				$(parent).append("已解绑");
				
				$(parent).prev().prev().prev().empty();
				$(parent).prev().prev().prev().append("已解绑");
			}
	        $("#dialog-confirm").dialog('close');
         }   ,
         complete: function(XMLHttpRequest, textStatus){
            $("#dialog-confirm").dialog('close');
         },
         error: function(XMLHttpRequest, textStatus, errorThrown){
        	 //alert(XMLHttpRequest.status);
        	 //alert(XMLHttpRequest.readyState);
        	 //alert(textStatus);
         }
	 });
}

function checkNum(obj) {
	//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
$(document).ready(function() {
	query();
});
function query(pageNo,totalCount,pageSize) {
	var pars = $("#mainfrom").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	//alert(pars);
	$.ajax({
		type: "POST",
		url: "${ctx}/tokenpay.do?method=queryManualTran",
		data: pars,
		success: function(result) {
			$('#resultListDiv').html(result);
		}
	});
}

function query(pageNo, totalCount, pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#initForm").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type : "POST",
		url : "bounced-register.do?method=batchRegisterQuery",
		data : pars,
		success : function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}

function cancel() {
	$('#dialog-confirm').dialog('close');
}

function submitTokenQuery(){
	$("#tokenQuerySubmit").submit();
}

</script>

</head>

<body>
<div id="dialog-confirm" title="加载信息" style="display: none">
	<br/><br/>
	你确定解绑该银行卡吗？
	<br/><br/><br/>
		<input id="sureToUnbindButton" type="button" onclick="unbind();" value="确定">&nbsp;
		<input type="button" onclick="cancel();" value="取消">
		
</div>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">Token支付查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">Token支付</h2>

<form action="tokenpay.do?method=list" method="post" id="tokenQuerySubmit" name="mainfrom">
  <table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">会员号：</td>
      	<td class="border_top_right4">
      	      	<input type="text" onkeyup="checkNum(this);" id="partnerId" name="partnerId"/>
      	</td>
      	<!-- <td class=border_top_right4 align="right" >Token：</td>
      	<td class="border_top_right4" >
        	<input type="text" onkeyup="checkNum(this);" id="tokenID" name="tokenID" />
      	</td> -->
      	<td class=border_top_right4 align="right" >用户ID：</td>
      	<td class="border_top_right4" >
        	<input type="text" id="registerUserId" name="registerUserId" />
      	</td>
      	<td class=border_top_right4 align="right" >使用状态：</td>
      	<td class="border_top_right4" >
        	<select name="status" id="status">
        		<option value="">——请选择——</option>
        		<option value="0">已解绑</option>
        		<option value="1">使用中</option>
        	</select>
      	</td>
      	<tr class="trForContent1">
      	<td class="border_top_right4"  align="right">绑卡日期：</td>
      	<td class="border_top_right4" colspan="3">
			<input class="Wdate" type="text" id= "beginTime" name="beginTime"  onClick="WdatePicker()">~
			<input class="Wdate" type="text" id= "endTime" name="endTime"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		</td>
		<td class="border_top_right4"  align="right">卡号：</td>
      	<td class="border_top_right4">
      		<input type="text" id="cardNo" name="cardNo" />
		</td>
	</tr>
	<tr class="trForContent1">
      	<td align="center" class="border_top_right4" colspan="6">
		      <input type="hidden" name="method" value="manualTransSub">
		      <input type="button"  name="submitBtn" onclick="submitTokenQuery();" value="查 询" class="button2">&nbsp;&nbsp;
      	 </td>
      </tr>
    </tr>
  </table>
 </form>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			会员号</th>
		<th class=""  >
			商户名称</th>
		<th class=""  >
			用户ID</th>
		<th class=""  >
			F卡号</th>
		<th class=""  >
			使用状态</th>
		<th class=""  >
			绑卡时间</th>
		<th class=""  >
			解绑时间</th>
		<th class=""  >
		操作</th>
	</tr>
	</thead>
	<c:forEach items="${result}" var="tokenCard" varStatus = "tokenCardStatus">
		<c:choose>
	       	<c:when test="${tokenCardStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
			<td class="" align="center" >${tokenCard.partnerId}</td>
			<td class="" align="left" >${tokenCard.zhName}</td>
			<td class="" align="center" >${tokenCard.registerUserId}</td>
			<td class="" align="center" >${tokenCard.cardHolderNumber}</td>
			<c:choose>
			<c:when test='${tokenCard.status == "1"}'>
	             <td class="" align="center" >使用中</td>
	       </c:when>
	       <c:otherwise>
	             <td class="" align="center" >已解绑</td>
	       </c:otherwise>
	       </c:choose>	
			<td class="" align="center" >${tokenCard.createDate}</td>
			<c:choose>	
			<c:when test='${tokenCard.status == "1"}'>
				<td class="" align="center" ></td>
			</c:when>
			<c:otherwise>
	             <td class="" align="center" >${tokenCard.updateDate}</td>
	       </c:otherwise>
	       </c:choose>	
			<td class="" align="center" >
			<c:choose>	
				<c:when test='${tokenCard.status == "1"}'>
					<a  cardNo="${tokenCard.cardHolderNumber }" token="${tokenCard.token }"  registerUserId="${tokenCard.registerUserId }"  onclick="unbindWithConfirm(this);" href="#">解绑</a>
				</c:when>
				<c:otherwise>
		                         已解绑
		       </c:otherwise>
		       </c:choose>	
			</td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="tokenCardQuery" pageBean="${page}" sytleName="black2"/>
</body>
<script type="text/javascript">
	$("#status").val("${criteria.status}");
</script>

