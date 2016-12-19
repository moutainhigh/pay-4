<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">导入提现复核文件</h2>
 <form action="" method="post" enctype="multipart/form-data" name="reviewFoForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
  	<input type="hidden" name="bussinessType" value="${importFileDTO.bussinessType }"/>
  	<input type="hidden" name="bankCode" value="${importFileDTO.bankCode }"/>
  	<input type="hidden" name="tradeType" value="${importFileDTO.tradeType }"/>
  	<input type="hidden" name="batchName" value="${importFileDTO.batchName }"/>
  	<input type="hidden" name="gFileKy" value="${importFileDTO.gFileKy }"/>
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >批次号：</td>
      <td class="border_top_right4" >
	        <input type="text"  name="batchNum" value="${importFileDTO.batchNum }" />
      </td>
      <td class=border_top_right4 align="right" >银行：</td>
      <td class="border_top_right4" >
      		<li:select name="bankCode" defaultStyle="true" itemList="${withdrawBankList}" selected="${importFileDTO.bankCode}" disabled="true"/>
	  </td>
      
    </tr>
    
    <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >复核文件：</td>
      <td class="border_top_right4">
      	<input type="file" name="orginalFile" size="50" />
      </td>
         <td align="center" class="border_top_right4" colspan="2"  >
         <a href="#" onclick="importReviewFile();"> 
         <input class="button2" type="button" value="导入"></a>
         </td>
      </tr>
  </table>
  
  <c:if test="${not empty errorMsg }"> 
  	<li style="color: red"><c:out value="${errorMsg}" /> </li>
  </c:if>
 </form>
 <script type="text/javascript">
 
 	 function importReviewFile()
 	 {
 	 	var url ='fundout-withdraw-reviewfile.do?method=importReviewFile';
		document.reviewFoForm.action = url;
		document.reviewFoForm.submit();
     }
</script>