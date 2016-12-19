<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	a:hover{text-decoration:underline;}
</style>

<h2 class="panel_title">补单申请</h2>
<form action="${ctx}/orderCompletionReq.do?method=doUpload" method="post" id="mainfromId" name="uploadReqForm" enctype="multipart/form-data">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >银行机构：</td>
	      <td class="border_top_right4">
	      	<select id="orgCode" name="orgCode">
				<option value="" selected>---请选择---</option>
				<c:forEach var="channel" items="${paymentChannelItems}" varStatus="v">
					<option value="${channel.orgCode}">${channel.name}</option>
				</c:forEach>
			</select>
	      </td>
	      <td></td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >上传补单请求：</td>
	      <td class="border_top_right4"><input type="file" name="file" id="file" /></td>
	      <td align="left" class="border_top_right4"><a href="${ctx }/orderCompletionReq.do?method=downloadTempFile&filename=ordercompletion_template.xls" style=":hover">下载补单模板文件</a></td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="3">
	      	<input type="submit" id="butSubmit" name="butSubmit" value="上传" class="button2" onclick="javascript:return retCheck();">
	      </td>
	    </tr>
   </table>
</form>

<script type="text/javascript">
	function retCheck(){
		var orgCodeVal = document.uploadReqForm.orgCode.value ;
		if(null == orgCodeVal || "" == orgCodeVal){
			alert("请选择银行机构！") ;
			return false ;
		}
		var fileName = document.uploadReqForm.file.value ;
		if(null != fileName && "" != fileName){
			var suffixName = fileName.substring(fileName.lastIndexOf(".")) ;
			if(suffixName != ".xls"){
				alert("文件格式不正确，请选择.xls格式文件上传！") ;
				return false ;
			}
		}else{
			alert("请选择文件！") ;
			return false ;
		}
		return true ;
	}
</script>
