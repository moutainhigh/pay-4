<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
		<title>选择文件</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#btn").click(function(){
					if(checkInfo()){
						if(confirm("确定上传文件吗？"))	{
							$("#frm").submit();
						}
					}
				});
				$("#closebtn").click(function(){
					window.close();
				});
			});

			function checkInfo(){
				var uploadFile=$("#uploadFile").val();
				if(uploadFile.length>0){
					var fileName=uploadFile.substring(uploadFile.lastIndexOf("\\")+1);
					//alert(fileName);
					if(fileName.length>0 && fileName.length<4){
						alert("文件格式不正确");
						return false;
					}				
				} else {
					alert("请选择上传的文件");
					return false;
				}
				
				return true;
			}

		</script>
	</head>
	<body>
		<form action="${ctx}/app/batchmobilecharge.do?method=uploadBatch" method="post" enctype="multipart/form-data" id="frm">
			<table class="searchTermTable">
				<tr>
					<td class="textRight">请选择上传文件：</td>
					<td class="textLeft"><input type="file" name="uploadFile" id="uploadFile"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="上传文件" id="btn">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="关闭" id="closebtn"></input></td>
				</tr>
			</table>
		</form>
		 <table class="searchTermTable">
		 <c:forEach items="${errorlist}" var="errormsg">
	      <tr>
	          <td><font color="red">${errormsg}</font></td>
	      </tr>
	    </c:forEach>  
		</table>
	</body>
</html>