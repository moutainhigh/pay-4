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
			  var selectVal = $("select[name='bankCode']").val();
		      if(selectVal == 'CCB' && suffix == 'txt'){
		    	  submitUpload();
			   }else if(selectVal == 'UnionPay' && (suffix == 'xml' || suffix == 'xls') ){
				   submitUpload();
				}
			   else{
				   submitUpload();
				      //alert("导入的文件格式不正确");      
				 	  //$("input[name='orginalFile']").focus(); 
			     	  //return false;
				 }
		      
		}
	}


	function submitUpload(){
		document.upload.action = "refund.file.do?method=uploadBankFile";
	    document.upload.submit();
		 }
	
	function changeBankInfo(obj){
		if(obj.value == 'UnionPay'){
			$("#providerId").html('<input type="radio" name="providerCode" checked="checked" value="002" />银联默认网关');
		}else{
			//$("#providerId").show();
			$("#providerId").html('<input type="radio" name="providerCode" checked="checked" value="001" />建设银行B2C网关');
		}
	}

</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">导入银行充退结果文件</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="upload"  action=""
     enctype="multipart/form-data">
  <input type="hidden" name="fileKy"  value="${webQueryRefundDTO.fileKy }"/>
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >批次号：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="batchNum" value="${webQueryRefundDTO.batchNum }"  />
      	</td>
      	<td class=border_top_right4 align="right" >银行名称：</td>
       	<td class="border_top_right4" >
        	<input type="text"  name="bankCode" value="${webQueryRefundDTO.bankCode }"  />
     	</td>
    </tr>
   
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">导入结果文件：</td>
      <td class="border_top_right4">
        <input type="file" name="orginalFile" size="50" />
      </td>
      <td class="border_top_right4" colspan="2">
        &nbsp;
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="center" colspan="4" >
		  <input type="button" onclick="javascript:checkFileUpload();" name="rtnBtn" value="确  定" class="button2">
		  <input type="button" onclick="history.go(-1);" name="rtnBtn" value="返  回" class="button2">
      </td>
    </tr>
  </table>
  
  <c:if test="${not empty err }"> 
  	<li style="color: red"><c:out value="${err}" /> </li>
  </c:if>

</form>
