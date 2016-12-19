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
		document.upload.action = "fundout-withdraw-importwdresult.do?method=importWdResult";
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

<h2 class="panel_title">导入银行提现结果文件</h2>

<form method="post" name="upload"  action=""
     enctype="multipart/form-data">
  
	<input type="hidden"  name="bankCode" value="${dto.bankCode }"  />
	<input type="hidden"  name="tradeType" value="${dto.tradeType }"  />
	<input type="hidden"  name="gFileKy" value="${dto.gFileKy }"  />
	<input type="hidden"  name="bussinessType" value="${dto.bussinessType }"  />
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >批次号：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="batchNum" value="${dto.batchNum }"  />
        	
      	</td>
      	<td class=border_top_right4 align="right" >批次名称：</td>
       	<td class="border_top_right4" >
        	<input type="text"  name="batchName" value="${dto.batchName }"  />
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
        
          <a class="s03" href="#" onclick="javascript:checkFileUpload();"><input class="button2" type="button" value="确定"></a>
      </td>
    </tr>
  </table>
  
  <c:if test="${not empty errorMsg }"> 
  	<li style="color: red"><c:out value="${errorMsg}" /> </li>
  </c:if>

</form>
