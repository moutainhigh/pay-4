<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function checkFileUpload(){
		
	    var fileName = $("input[name='getwayFile']").val();
		var num=fileName.lastIndexOf("\\")+1;
		fileName=fileName.substring(num,fileName.length);
		if ( fileName == '') {
			alert('请上传一个文件.');
			return false;
		} else {
			if(fileName.indexOf(".txt")>-1){
			  submitUpload(fileName);
		    }else{
               alert('请上传.txt文件');
			   return false;
			}	
		}
	}


	function submitUpload(fileName){
		document.gatewayUploadForm.action = "batchAnalyse.htm?method=indexInfo&getwayFile="+fileName;
	    document.gatewayUploadForm.submit();
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">网关文件校验</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="gatewayUploadForm" id="gatewayUploadForm" action=""
     enctype="multipart/form-data">
  
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
   
 
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">导入网关文件：</td>
      <td class="border_top_right4">
        <input type="file" id="getwayFile" name="getwayFile" size="50"/>
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="center" colspan="2" >
        
          <a class="s03" href="#" onclick="javascript:checkFileUpload();"><img
			src="./images/ok-Blue.jpg" border="0"> </a>
      </td>
    </tr>
  </table>

</form>
<div id="resultListDiv" class="listFence"></div>