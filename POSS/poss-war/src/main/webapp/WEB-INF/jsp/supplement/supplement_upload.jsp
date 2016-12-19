<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<%  String path = request.getContextPath(); %>
<script type="text/javascript">
	function checkFileUpload(){
	    var fileName = $("input[name='orginalFile']").val();
		if ( fileName == '') {
			alert('请上传一个文件.');
			return false;
		} else {
			//获取上传文件格式
			var i1=fileName.lastIndexOf("."); 
			var suffix=fileName.substring(i1+1).toLowerCase();
			
			if( suffix != 'xls' ){
				alert("导入的文件格式不正确");      
				$("input[name='orginalFile']").focus(); 
				return false;
			}else{
				document.uploadOrDownloadForm.action = "supplement.upload.do?method=uploadFile";
				document.uploadOrDownloadForm.submit();
			} 
		}
	}

	function downloadExcel() {
		document.uploadOrDownloadForm.action = "supplement.download.do?method=downloadExcel";
		document.uploadOrDownloadForm.submit();
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">导入手工补单文件</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="uploadOrDownloadForm"  action="" enctype="multipart/form-data">

	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
    	<tr class="trForContent1">
      		<td class="border_top_right4"  >如果您还没有模板文件，请先下载</td>
      		<td class="border_top_right4">
        		<!-- <a href="#" onclick="downloadExcel();">手工补单模板</a> -->
        		<a href="<%= path %>/supplementmodel/supplementModel.xls">手工补单模板</a>
      		</td>
    	</tr>
   
    	<tr class="trForContent1">
      		<td class="border_top_right4" >上传补单文件：</td>
      		<td class="border_top_right4">
        		<input type="file" name="orginalFile" size="50" />
      		</td>
    	</tr>
    	
    	<tr class="trForContent1">
      		<td class="border_top_right4" align="center" colspan="2" >
          		<a class="s03" href="#" onclick="javascript:checkFileUpload();"><img src="./images/ok-Blue.jpg" border="0"> </a>
      		</td>
    	</tr>
  </table>
  
  <c:if test="${not empty msg }"> 
  	<li style="color: red"><c:out value="${msg}" /> </li>
  </c:if>

</form>
