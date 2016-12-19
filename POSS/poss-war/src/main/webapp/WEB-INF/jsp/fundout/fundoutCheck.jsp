<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">导入出款复核文件</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form name="fundoutUploadForm" id="fundoutUploadForm" action="uploadFundoutCheck.do"
    enctype="multipart/form-data"  method="post">
    <input name="method"  value="uploadFundoutCheck" type="hidden">
    <!-- add by davis.guo at 2016-08-29 begin -->
  	<input type="hidden" name="bussinessType" value="${importFileDTO.bussinessType }"/>
  	<input type="hidden" name="bankCode" value="${importFileDTO.bankCode }"/>
  	<input type="hidden" name="tradeType" value="${importFileDTO.tradeType }"/>
  	<input type="hidden" name="batchNum" value="${importFileDTO.batchNum }"/>
  	<input type="hidden" name="batchName" value="${importFileDTO.batchName }"/>
  	<input type="hidden" name="gFileKy" value="${importFileDTO.gFileKy }"/>
  	<!-- add end -->
  <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1"  align="center">	
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">导入出款复核文件：</td>
		<td class="border_top_right4">
        <input type="file" name="orginalFile" id="orginalFile" size="50"/>
      	</td>
      	<!-- add by davis.guo at 2016-08-29 begin -->
      	<td align="right" class="border_top_right4">批次号：</td>
		<td class="border_top_right4">
        	${importFileDTO.batchNum}
      	</td>
      	<td align="right" class="border_top_right4">文件记录：</td>
		<td class="border_top_right4">
        	${importFileDTO.gFileKy}
      	</td>
      	<!-- add end -->
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="6">
      <input type="button" name="submitBtn" value="确认" class="button2" onclick="subUploadFile();">
      </td>
    </tr>
  </table>
 </form>
 <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div> 
<script type="text/javascript">
	
	function subUploadFile(){
		var orginalFile=$("#orginalFile").val();
		if(orginalFile==""){
			alert("请选择需要复核的文件！！！");
			return;
		}
	       if(orginalFile.indexOf('.xls')!=-1 || orginalFile.indexOf('.txt')!=-1 ){
				$('#infoLoadingDiv').dialog('open');
				$("#fundoutUploadForm").submit();	
	       }else{
	    	   alert("文件格式不正确，请选择正确的解析文件(后缀名.xls或者.txt)！")
				return false ;	    	   
	       }
	}
</script>