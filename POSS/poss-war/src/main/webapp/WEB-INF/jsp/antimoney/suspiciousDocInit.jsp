<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>可疑报告报文管理</title>
		<script type="text/javascript">
			function download(){
				location.href="${ctx}/context_fundout_antimoney.controller.htm?method=downloadDocTemplate&flag=" + $("#flag").val();
			}

			function submitTask(){
				if(validate()){
					document.forms[0].submit();
				}
			}

			function validate(){
				var orgCode = $("#orgCode").val();
				var type = $("#type").val();
				var batchNo = $("#batchNo").val();
				var seqNo = $("#seqNo").val();
				var file = $("#file").val();
				if($.trim(orgCode).length < 1 || $.trim(orgCode).length != 14){
					alert("报告机构编码不能为空并且必须是14位");
					$("#orgCode").focus();
					return false;
				}
				if($.trim(type).length < 1){
					alert("请选择报文类型");
					$("#type").focus();
					return false;
				}
				if($.trim(batchNo).length < 1 || $.trim(batchNo).length != 4){
					alert("当日报送批次不能为空并且必须是4位");
					$("#batchNo").focus();
					return false;
				}
				if($.trim(seqNo).length < 1 || $.trim(seqNo).length != 4){
					alert("文件在该批次中的编号不能为空并且必须是4位");
					$("#seqNo").focus();
					return false;
				}
				if($.trim(file).length < 1){
					alert("请上传文件");
					$("#file").focus();
					return false;
				}
				return true;
			}
		</script>
		<style>
			<!--
				a:link{color:red;}
				a:visited{color:red;}
				a:active{color:red;}
				a:hover{color:red;}
			-->
		</style>
	</head>
	
	<body>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center"><font class="titletext">可疑报告报文管理</font></div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="10">
				</td>
			</tr>
			<tr>
				<td height="18">
					<div align="center"><font style="color: red; font-size: 15px;"><a style="cursor:hand;" onclick="download();">可疑报告报文模板下载</a></font></div>
				</td>
			</tr>
		</table>
		<form action="${ctx}/context_fundout_antimoney.controller.htm?method=submit" method="post" id="frm1" name="frm1" enctype="multipart/form-data">
			<input type="hidden" name="flag" id="flag" value="suspicious" />
			<table class="border_all2" width="60%" border="0" cellspacing="0" cellpadding="1" align="center">
				<tr class="trForContent1">
					<td class=border_top_right4 align="right">报告机构编码：</td>
					<td class="border_top_right4" colspan="3">
						<input type="text" name="orgCode" id="orgCode" />（请按照规则录入14位编码）
					</td>
				</tr>
				<tr class="trForContent1">
					<td class=border_top_right4 align="right">报文类型：</td>
					<td class="border_top_right4" colspan="3">
						<select name="type" id="type">
							<option value="">--请选择--</option>
							<option value="N">普通报文</option>
							<option value="R">重发报文</option>
							<option value="C">纠错报文</option>
						</select>
					</td>
				</tr>
				<tr class="trForContent1">
					<td class=border_top_right4 align="right">当日报送批次：</td>
					<td class="border_top_right4" colspan="3">
						<input type="text" name="batchNo" id="batchNo" />（数字，四位，不足四位请自行左侧补0）
					</td>
				</tr>
				<tr class="trForContent1">
					<td class=border_top_right4 align="right">文件在该批次中的编号：</td>
					<td class="border_top_right4" colspan="3">
						<input type="text" name="seqNo" id="seqNo" />（数字，四位，不足四位请自行左侧补0）
					</td>
				</tr>
				<tr class="trForContent1">
					<td class=border_top_right4 align="right">上传文件：</td>
					<td class="border_top_right4" colspan="3">
						<input type="file" name="file" id="file" />
					</td>
				</tr>
				<tr class="trForContent1">
					<td class="border_top_right4" align="center" colspan="4">
						<input id="btn2" type="button" class="button2" value="提交" onclick="submitTask();" />
					</td>
				</tr>
				<tr class="trForContent1">
					<td class="border_top_right4" colspan="4">
						<br />
						&nbsp;&nbsp;&nbsp;&nbsp;操作说明：请先下载可疑报告模板（普通报文、重发报文、纠错报文共享同一个模板：页面中的红字链接，模板支持最低版本为2007，请保证所有单元格的数据类型都为文本格式），然后按照反洗钱中心给出的约定依次在模板对应位置录入对应的数据，选择报文类型、录入当日报送批次号、文件在批次中的编号以及上传填充数据后的模板后点击提交。文件名生成规则：报文类型和交易报告类型标识＋报告机构编码＋报送日期＋当日报送批次＋文件在该批次中的编号.XML，请确保报文文件名的唯一性，否则会生成失败，成功后请到报文查询下载管理菜单中进行查询下载操作。
					</td>
				</tr>
				<c:if test="${not empty info}">
					<li style="color: red;" id="msg">${info}</li>
				</c:if>
			</table>
		</form>
	</body>
</html>