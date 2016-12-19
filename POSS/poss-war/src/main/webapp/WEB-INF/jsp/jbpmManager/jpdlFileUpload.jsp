<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	function checkFileUpload(){		
	    var fileName = $("input[name='orginalFile']").val();
		if ( fileName == '') {
			alert('请上传一个文件.');
			return false;
		} else {
			  var i1=fileName.lastIndexOf("."); 
		      var suffix=fileName.substring(i1+1).toLowerCase();
			 if(suffix == 'xml'){
				   submitUpload();
			  }else{
			      alert("导入的文件格式不正确");      
			 	  $("input[name='orginalFile']").focus(); 
		     	  return false;
			  }
		      
		}
	}


	function submitUpload(){
		document.reconcileUploadForm.action = "workflow.process.do?method=workflowFileUpload";
	    document.reconcileUploadForm.submit();
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">工作流流程发布</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="reconcileUploadForm" id="reconcileUploadForm" action=""
     enctype="multipart/form-data">
  
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">上传工作流流程文件：</td>
      <td class="border_top_right4">
        <input type="file" name="orginalFile" size="50"/>
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="center" colspan="2" >
        <input type="button" name="btnUpload" value="流程发布" class="button4" onclick="javascript:checkFileUpload();"/>
      </td>
    </tr>
  </table>
  <br/>
  	<c:if test="${not empty resultFlag}">
	  <c:choose>
	  	<c:when test="${'failed' eq resultFlag}">
	  		<li style="color: red">
	  			${resultMsg}
	  			上传工作流流程文件名称:${uploadFile}
	  		</li>
	  	</c:when>
	  	<c:otherwise>
	  		<li style="color: red">${resultMsg}</li>
	  	</c:otherwise>
	  </c:choose>
	</c:if>
</form>
