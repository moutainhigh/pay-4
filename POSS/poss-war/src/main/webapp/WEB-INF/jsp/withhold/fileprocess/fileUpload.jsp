<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">导入代扣、签约文件</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
 <form action="" method="post" enctype="multipart/form-data" name="reviewFoForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
       <td class=border_top_right4 align="right" >文件类型</td>
	      <td class="border_top_right4" >
	          <select name="type" >
      			<option value="01" selected="selected">签约回盘文件</option>
      			<option value="02">代扣回盘文件</option>
      		  </select>
	    </td>
      <td class=border_top_right4 align="right" >银行：</td>
      <td class="border_top_right4" >
      	<li:select name="bankCode" defaultStyle="true" itemList="${bankInfoList}" />
	  </td>
      
    </tr>
    
    <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >选择文件：</td>
      <td class="border_top_right4">
      	<input type="file" name="orginalFile" size="50" />
      </td>
         <td align="right" class="border_top_right4" colspan="2"  >
         <img src="images/01.gif"  onclick="importFile();"></img>
         </td>
      </tr>
  </table>
  
  <c:if test="${not empty errorMsg }"> 
  	<li style="color: red"><c:out value="${errorMsg}" /> </li>
  </c:if>
 </form>
 
 <script type="text/javascript">
 	 function importFile()
 	 {
 	 	var url ='context_withhold_fileprocess.controller.htm?method=importWhFile';
		document.reviewFoForm.action = url;
		document.reviewFoForm.submit();
     }
</script>