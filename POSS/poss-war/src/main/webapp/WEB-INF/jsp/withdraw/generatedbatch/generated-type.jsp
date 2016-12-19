<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">手工生成批次类型</h2>
 <form action="" name="generateForm" method="post" id="genForm">
 <input type="hidden" name="sequenceIdList" value="${sequenceIdList }" />
 <input type="hidden" name="busiType" value="${busiType}" />
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	 <tr class="trForContent1">
      <td width="50%" align="right" class="border_top_right4"  >出款渠道：</td>
      <td align="left" class="border_top_right4">
      	<li:select name="withdrawBankCode" defaultStyle="true" itemList="${fundoutChannelList}"  />
      </td>
    </tr>	
    <tr class="trForContent1">
      <td align="right" class="border_top_right4"  >手工批次名称：</td>
      <td align="left" class="border_top_right4">
        <input type="text"  id="batchNameId" name="batchName"  />
      </td>
    </tr>
   
   <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >是否发送：</td>
      <td align="left" class="border_top_right4">
        <select name="isSend">
       		<option value="0">是</option>
       		<option value="1" selected="selected">否</option>
       	</select>
      </td>
    </tr>
     <tr class="trForContent1">
	     <td align="center" class="border_top_right4" colspan="4">
	     	 <input type="button" onclick="validateFrom();" name="submitBtn" value="生成批次" class="button2">
	      </td>
    </tr>
  </table>
 </form>
 
 <c:if test="${not empty errorMsg}">
 	<li style="color: red">${errorMsg }</li>
 </c:if>
<script type="text/javascript">
	function validateFrom(){

		var batchName = $("#batchNameId").val();
		var bankCode = $("select[name='withdrawBankCode']").val();
		
		if("" == bankCode || null == bankCode){
				alert("请选择出款银行");
				return false;
		}
		if("" == batchName || null == batchName){
			alert("请输入批次名称");
			return false;
		}
		document.generateForm.action = 'fundout-withdraw-generatebatch.do?method=generateBatchFile';
		document.generateForm.submit();
	}

</script>
 
 