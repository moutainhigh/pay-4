<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">黑名单批量导入</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">黑名单批量导入</h2>
<form action="${ctx}/blackListUpload.do?method=upload" method="post" id="mainfromId" enctype="multipart/form-data" onsubmit="return sub();">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >黑名单文件：</td>
	      <td class="border_top_right4">
	      	<input type="file" id="file" name="file">
	      	<input type="hidden" id="listType" value="${listType}" name="listType">
	      </td>
	      <td align="right" class="border_top_right4" ></td>
	      <td class="border_top_right4">

	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="submit"  value="导  入" class="button2">
	      <input type="button"  value="返  回" class="button2" onclick="closePage();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>

<c:if test="${not empty responseMsg}">
	<font color="red"><b>${responseMsg}</b></font>
</c:if>
	 
  <script type="text/javascript">
	function closePage() {
		window.location.href="${ctx}/blackWhiteListSearch.do?method=blackWhiteListSearchView";
	}
	
	function sub(){
		
		var file = $("#file").val();
		if(""== file){
			alert("请选择文件！");
			return false;
		}
		return true;
	}

  </script>