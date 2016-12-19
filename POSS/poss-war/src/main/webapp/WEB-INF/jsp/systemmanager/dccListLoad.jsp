<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>


<script type="text/javascript">
function upload() {
var uploadFlag = $("#uploadFlag").val();
var type = $("#type").val();
	if(uploadFlag!="true"){
			alert("文件错误不能上传！！");
			return false;
	}
	$("#dccConf").submit();
	//document.dccConf.submit();
} 
function back() {
	window.location.replace("./dcc_configuration.do?method=index");
}

</script>
				
		 <form id="dccConf" action="dcc_configuration.do?method=updateDCCConfigurationLoad" method="post">
		 <input type="hidden" name="uploadFlag" id="uploadFlag" value="${uploadFlag}" />
		 <input type="hidden" name="type" id="type" value="${type}" />
		 <table align="center" class="inputTable" style="width: 60%">
			<tr>
			<td><input type="button" value="确认上传" onclick="upload();"
				class="button2"></td>
			<td><input type="button" value="返回" onclick="return back();"
				class="button2"></td>
			</tr>
	</table>
 		</form>
<body>
	<table width="70%" border="1" cellspacing="1" cellpadding="0"
		align="center">
		<tr class="trForContent1">
			<td align="center" class="border_top_right4">会员号</td>
			<td align="center" class="border_top_right4">商户名称</td>
			<td align="center" class="border_top_right4">货币代码</td>
			<td align="center" class="border_top_right4">原markup比率(%)</td>
			<td align="center" class="border_top_right4">新markup比率(%)</td>
			<td align="center" class="border_top_right4">异常描述</td>
		</tr>
		<tbody>
		<input type="hidden" name="uploadFlag" id="uploadFlag" value="${uploadFlag}" />
		<input type="hidden" name="type" id="type" value="${type}" />
		<c:forEach items="${list}" var="dcc" >
		<tr class="trForContent1">
			<td align="center" class="border_top_right4">${dcc.partnerId}</td>
			<td align="center" class="border_top_right4">${dcc.partnerName}</td>
			<td align="center" class="border_top_right4">${dcc.currencyCode}</td>
			<td align="center" class="border_top_right4">${dcc.markUp}</td>
			<td align="center" class="border_top_right4">${dcc.markUpNew}</td>
			<td align="center" class="border_top_right4"><font color=red >${dcc.remark}</font></td>
		</c:forEach>
		</tbody>
	</table><br><br><br><br>
	<table align="center">	<tr>
			<td colspan="13" align="center"><li:pagination methodName="dcclist" pageBean="${page}" sytleName="black2"/></td>
		</tr>
		</table>