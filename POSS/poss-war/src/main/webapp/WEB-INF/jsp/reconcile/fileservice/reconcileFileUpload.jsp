<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs_new.jsp"%>
<script type="text/javascript">
	function checkFileUpload(){
		
		if("" == $("#bizCode").val()){
			alert("请您选择业务类型!");
			return false;
		}
		if("" == $("#orgCode").val()){
			alert("请您选择资金机构!");
			return false;
		}

		if("" == $("#startDate").val()){
			alert("请您选择开始日期!");
			return false;
		}
		
		if("" == $("#endDate").val()){
			alert("请您选择结束日期!");
			return false;
		}
		if($("input[type='checkbox']").is(':checked')==false){
			alert("请您至少选择一种结算币种!");
			return false;
		}
		
	    var fileName = $("input[name='orginalFile']").val();
		if ( fileName == '') {
			alert('请上传一个文件.');
			return false;
		} else {
			 submitUpload();
		}
	}


	function submitUpload(){
		//document.reconcileUploadForm.action = "queryReconcile.do?method=upload";
		document.reconcileUploadForm.action = "queryReconcile.do?method=upload_new";
	    document.reconcileUploadForm.submit();
	}
	
	function selectChannel(bizCode){
		var str = "<option value=''>--请选择渠道--</option>";
		if('' == bizCode){
			$("#orgCode").html(str);
			return;
		}
		var url = "queryReconcile.do?method=";
		if('FI' == bizCode){
			//url += "getFundinChannels";
			//add by davis.guo at 2016-08-11 begin
			url = "${ctx}/organization.do?method=queryOrgForCheckOrder";
			var displayForChannel = 1;//1为渠道，0为非渠道
			var pars = {"displayForChannel":displayForChannel,"htmlType":"json"};
			$.ajaxSetup({ cache: false });
			$.getJSON(url, pars, function(data) {
		        for(var i=0;i<data.length;i++){
		        	str += "<option value='" + data[i].orgCode + "'>" + data[i].orgName + "</option>";
		        }
		        $("#orgCode").html(str);//测试migs时，把这句注释掉
		    });
			//======================= end
		}else if('FO' == bizCode){
			url += "getFundoutChannels";
			$.getJSON(url, function(data) {
		        for(var i=0;i<data.length;i++){
		        	str += "<option value='" + data[i].bankId + "'>" + data[i].channelName + "</option>";
		        }
		        $("#orgCode").html(str);
		    });
		}
	}
	
	$(function(){
		$("#checkAll").click(function(){
			//alert($('#checkAll').is(':checked'));
			$("input[name='settlementCurrency']").each(function(){
				$(this).prop("checked",$('#checkAll').is(':checked'));
			});  
		});
	});
	
</script>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">导入银行对账文件</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">导入银行对账文件</h2>


<form method="post" name="reconcileUploadForm" id="reconcileUploadForm" action=""
     enctype="multipart/form-data">
  
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">选择业务:</td>
      <td class="border_top_right4">
          <select id="bizCode" name="bizCode" onchange="selectChannel(this.value)">
          	<option value="">--请选择--</option>
          	<option value="FI">入款</option>
          	<option value="FO">出款</option>
          </select>
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">选择银行:</td>
      <td class="border_top_right4">
          <select id="orgCode" name="orgCode">
          	<!-- <option value="">--请选择渠道--</option>
          	<option value="10080001">migs</option> -->
          </select>
      </td>
    </tr>
   <tr class="trForContent1">
      <td class="border_top_right4" align="right">对账日期:</td>
      <td class="border_top_right4">
        <input class="Wdate" type="text" id="startDate" name="startDate" value='${startDate}' onClick="WdatePicker();">-
       	<input class="Wdate" type="text" id="endDate" name="endDate" value='${endDate}' onClick="WdatePicker();">
      </td>
    </tr>
   <tr class="trForContent1">
      <td class="border_top_right4" align="right">结算币种:</td>
      <td class="border_top_right4">
      	<input type="checkbox" name="checkAll" id="checkAll"/>全选<br/>
        <c:forEach var="item" items="${currencyCodes}">
        	<c:if test="${item.code == 'CNY'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'USD'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'EUR'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'GBP'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'CHF'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'SEK'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'NOK'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'DKK'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'JPY'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'AUD'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'HKD'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'NZD'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        	<c:if test="${item.code == 'SGD'}">
        		<input type="checkbox" name="settlementCurrency" id="settlementCurrency" value="${item.code}"/>${item.desc }
        	</c:if>
        </c:forEach>
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="right">上传对账文件：</td>
      <td class="border_top_right4">
        <input type="file" name="orginalFile" id="orginalFile" size="50" multiple/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:red;">多文件上传请按Ctrl或Shift</font>
      </td>
    </tr>
    <tr class="trForContent1">
      <td class="border_top_right4" align="center" colspan="2" >
        
          <a class="s03" href="#" onclick="javascript:checkFileUpload();"><input class="button2" type="button" value="确定"></a>
      </td>
    </tr>
  </table>
  
  <c:if test="${not empty validDataInfo}"> 
  	<li style="color: red"><c:out value="${validDataInfo}" /> </li>
  </c:if>

</form>
