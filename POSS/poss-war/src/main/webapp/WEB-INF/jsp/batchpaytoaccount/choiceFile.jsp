<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="generatorValidateCode.jsp" %>
<html>
	<head>
		<title>选择文件</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#payAccountName").val($("#payAccount").find("option").eq($("#payAccount")[0].selectedIndex).html());
				
				$("#payAccount").change(function(){
					var context=$(this).find("option").eq($(this)[0].selectedIndex).html();
					$("#payAccountName").val(context);
				});
				
				$("#btn").click(function(){
					if(checkInfo()){
						$("#frm").submit();
					}
				});
			});

			function checkInfo(){
				var uploadFile=$("#uploadFile").val();
				var vcode=$("#vcode").val();
				var validateCode=$("#validateCode").val();

				if(uploadFile.length>0){
					var fileName=uploadFile.substring(uploadFile.lastIndexOf("\\")+1);
					//var regx=/^*[.xls]$/;
					if(fileName.length>0 && fileName.length<5){
						alert("文件格式不正确");
						return false;
					}				
				} else {
					alert("请选择上传的文件");
					return false;
				}
				
				if(vcode.toLowerCase()!=validateCode.toLowerCase()){
					alert("验证码错误");
					return false;
				}
				$("#uploadFileName").val(uploadFile);
				return true;
			}
		</script>
	</head>
	
	<body>
		<div style="text-align: left;position: absolute;left:50px;top:50px;width: 60%;">
			<span style="width: 400px;font-size: 20px;">批量付款到系统账户</span>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="${ctx}/batchPayToAccountController.do?method=importFileList">查看批量付款记录</a>
			
			<hr color="gray" noshade="noshade">
			<span style="float: right;">
				<font color="red">1.上传批量文件</font>-->2.确认付款信息-->3.等待付款到帐
			</span><br>
			<hr color="gray" noshade="noshade"><br>
			
			如果您还没有“批量付款到系统模板”，请先下载 。   
			<a href="${ctx}/downloadFileController.do?method=downloadTemplateFile">批量付款到系统模板</a>
		</div>
		<form action="${ctx}/uploadBatchFileController.do" method="post" enctype="multipart/form" id="frm">
		<div style="text-align: left;position: absolute;left:50px;top:170px;">
			<span style="width:100px;">请选择付款账户：</span>
			<select name="payAccount" id="payAccount">
				<option value="1" selected="selected">人民币账户</option>
			</select>
			<input type="hidden" name="payAccountName" id="payAccountName"/>
			<br><br>
			<span style="width:100px;">请选择上传文件：</span>
			<input type="file" name="uploadFile" id="uploadFile"/><br><br>
			<input type="hidden" name="uploadFileName" id="uploadFileName" style="width: 150px;"/>
			<hr color="gray" noshade="noshade"><br>
			
			<span style="width:100px;">请输入验证码：</span>
			<input type="text" name="validateCode" id="validateCode" size="10"/>
			<img src="${ctx}/getValidateCode.do?code=${requestScope.validateCode}" style="position: relative;top: 5px;">
			<input type="hidden" name="vcode" id="vcode" value="${requestScope.validateCode}"/>
			<input type="button" value="下一步" id="btn"></input>
		</div>
		</form>
	</body>
</html>