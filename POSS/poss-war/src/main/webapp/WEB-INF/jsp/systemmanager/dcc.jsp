<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">企 业 DCC 配置</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br>
<br>
<br> -->
<h2 class="panel_title">企业DCC配置</h2>
<h2>${info}</h2>
<form action="dcc_configuration.do" enctype="multipart/form-data" method="post" id="DCCForm" name="DCCForm">

	<!-- <input name="method" value="list" type="hidden"> -->

	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4" >会员号：</td>
			<td class="border_top_right4"><input type="text" name="partnerId" value="${dccConfig.partnerId}"
				 maxlength="50" onkeyup="checkNum(this);"
				onblur="decimals(this)" /></td>
			<td align="right" class="border_top_right4">商户名称：</td>
			<td class="border_top_right4"><input type="text" name="partnerName" value="${dccConfig.partnerName}"
				 maxlength="20" /></td>
			<td align="right" class="border_top_right4">币种代码：</td>
			<td class="border_top_right4"><input type="text" name="currencyCode" value="${dccConfig.currencyCode}"
				 maxlength="20" /></td>
			</tr>
			<tr class="trForContent1">
			<td align="center" valign="middle" class="border_top_right4" >
			<input type="button" value="查   询"  onclick="dcclist();"
				class="button2">
			</td>
			<td class="border_top_right4" colspan="5">	
				<a href="./dcc_configuration.do?method=saveDCC">新增企业DCC配置</a>
				<a href="./dcc_configuration.do?method=configurationDCC">设置企业DCC默认配置</a>
				</td>
			</tr>
		<tr class="trForContent1">
      
      <td align="center" valign="middle" class="border_top_right4" >
      <input type="file"  name="file_trackinguploade" id="file_trackinguploade" accept=".xls"  value="点击上传">
      </td>
      <td class="border_top_right4" colspan="5"><a href="./dcc_configuration.do?method=downLoadFile&fileName=Dcc_Template.xls" id="url_template" name="url_template" style="text-decoration:underline;">企业DCC配置上传模板下载</a></td>
      </tr>
      <tr class="trForContent1">
	     	<td align="center" class="border_top_right4" colspan="6"><input type="button" value="批量新增" onclick="return uploadeAdd(this);"
				class="button2">
				<input type="button" value="批量修改" onclick="return uploadeUpdate(this);"
				class="button2"></td>
     </tr>
		</table>
	</form>
	<div id="resultListDiv" class="listFence">
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead>
		<tr class="">
			<th align="center" class="">会员号</th>
			<th align="center" class="">商户名称</th>
			<th align="center" class="">货币代码</th>
			<th align="center" class="">原markup比率(%)</th>
			<th align="center" class="">新markup比率(%)</th>
			<th align="center" class="">异常描述</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="dcc" >
		<tr class="">
			<td align="center" class="">${dcc.partnerId}</td>
			<td align="center" class="">${dcc.partnerName}</td>
			<td align="center" class="">${dcc.currencyCode}</td>
			<td align="center" class="">${dcc.markUp}</td>
			<td align="center" class="">${dcc.markUpNew}</td>
			<td align="center" class="">${dcc.remark}</td>
		</c:forEach>
		</tbody>
	</table><br><br><br><br>
	</div>
	

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
<script type="text/javascript">
function uploadeAdd(){
var file = $("#file_trackinguploade").val();
	
	if("" == file || null == file){
			alert("请选择上传文件！");
			return false;
	}
	document.DCCForm.action="dcc_configuration.do?method=submitUploade&type=add";
	document.DCCForm.submit();
}
function uploadeUpdate(){
var file = $("#file_trackinguploade").val();
	
	if("" == file || null == file){
			alert("请选择上传文件！");
			return false;
	}
	document.DCCForm.action="dcc_configuration.do?method=submitUploade&type=update";
	document.DCCForm.submit();
}
function back() {
	window.location.replace("./dcc_configuration.do?method=index");
}	
</script>	 
<script type="text/javascript">
	 $(document).ready(function(){
		 dcclist();
		 <c:if test="${not empty errormsg }">
		 			alert('${errormsg}') ;
		 </c:if>
	 });
	 
	function dcclist(pageNo, totalCount) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#DCCForm").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./dcc_configuration.do?method=list",
			data : pars,
			async:false,
			success : function(result) {
		$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}

	/* 	function findDCC() {
	 $("#DCCForm").submit();
	 } */
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function decimals(obj) {
		var markup = obj.value;//获取markup
		if (markup && markup.indexOf(".") == -1) {
			var index;
			var zeroCount = 0;
			for (var i = 0; i < markup.length; i++) {
				if (markup.charAt(i) != "0") {
					index = i;
					break;
				} else {
					zeroCount++;
				}
			}
			if (zeroCount == markup.length) {
				$("table tr td input[name=markup]").attr("value", "");
			} else if (index) {
				$("table tr td input[name=markup]").attr("value",
						markup.substr(index));
			}
		} else if (markup && markup.indexOf(".") > -1) {
			var left = markup.split(".")[0];
			var right = markup.split(".")[1];
			var zeroCount = 0;
			for (var i = 0; i < left.length; i++) {
				if (left.charAt(i) == "0") {
					zeroCount++;
				} else {
					break;
				}
			}
			if (zeroCount == left.length) {
				left = left.substr(zeroCount - 1);
				markup = left + "." + right;
				$("table tr td input[name=markup]").attr("value",
						left + "." + right);
			} else if (zeroCount > 0) {
				left = left.substr(zeroCount);
				markup = left + "." + right;
				$("table tr td input[name=markup]").attr("value",
						left + "." + right);
			}
			if (right.length > 2) {
				markup = markup.substring(0, markup.indexOf(".") + 3);
				$("table tr td input[name=markup]").attr("value", markup);
			}
		}
	}
</script>	
