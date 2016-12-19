<%@ page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>上传文件格式错误</title>
	</head>
	
	<body>
		<span style="width: 400px;font-size: 20px;">批量付款到系统账户</span>
		<hr color="gray" noshade="noshade">
		<br>
		${error}，请确认后重新上传。<br><br>
		5秒钟后自动返回...&nbsp;&nbsp;&nbsp;
		<script type="text/javascript">
			var cnt=1;
			function test(){
				if(++cnt==5){
					location.href="toUploadFile.do";
				}
			}
			setTimeout("test()","1000");
		</script>
		<a href="${ctx}/toUploadFile.do">立即返回</a> 
		<hr color="black" noshade="noshade" width="0">
		<br><br>
		您可能需要：
		<br><br>
		<a href="${ctx}/toUploadFile.do">继续批量付款</a> | 
		<a href="">查看批量付款记录</a> | 
		<a href="">返回企业账户首页</a>
	</body>
</html>