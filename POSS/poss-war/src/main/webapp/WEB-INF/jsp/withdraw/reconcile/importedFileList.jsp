<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>


 <form action="" method="post" name="resultForm"  id="resultForm">
 	<input type="hidden" name="batchNum" id="batchNameId" />
 	<input type="hidden" name="bankCode" id="bankCodeId" />
 	<input type="hidden" name="tradeType" id="tradeTypeId" />
 	<input type="hidden" name="gFileKy" id="gFileKyId" />
 	<input type="hidden" name="batImportPrms" id="batImportPrms" value=""/>
 	<c:forEach items="${page.result}" var="dto" varStatus="status">
 		<c:if test="${dto.batchFileStatus == 4 and dto.sureCount == 0}">
			<input type="hidden" id="${dto.batchNum}_batch" name="${dto.batchNum}_batch" value="${dto.batchNum}"/>
			<input type="hidden" id="${dto.batchNum}_bank" name="${dto.batchNum}_bank" value="${dto.bankCode}"/>
		</c:if>
 	</c:forEach>
 	
 </form>
 
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选</th>
			<th>序号</th>
			<th>批次号</th>
			<th>批次名称</th>
			<th>总笔数</th>
			<th>总金额（元）</th>
			<th>产生时间</th>
			<th>下载时间</th>
			<th>导入时间</th>
			<th>确认导入时间</th>
			<th>批次状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<form name="form" id="form">
		<c:forEach items="${page.result}" var="dto" varStatus="status">
				<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
					<td >
						<c:if test="${dto.batchFileStatus == 4 and dto.sureCount == 0}">
							<input type="checkbox" name="batchId" id="batchId" value="${dto.batchNum}" onclick="selectAllcheckBoxunchecked(this);" /> 
						</c:if>
					</td>
					<td ><c:out value="${status.count}" /></td>
					<td >${dto.batchNum}</td>
					<td >${dto.ruleName}</td>
					<td >${dto.allCount}&nbsp;</td>
					<td ><fmt:formatNumber value="${dto.allAmount*0.001}" pattern="#,##0.00"  />&nbsp;</td>
					<td >
					<fmt:formatDate value="${dto.generateTime}" type="date"/>&nbsp;
					</td>
					<td >
					<fmt:formatDate value="${dto.downloadTime}" type="date"/>&nbsp;
					</td>
					<td >
					<fmt:formatDate value="${dto.importTime}" type="date"/>&nbsp;
					</td>
					<td >
					<fmt:formatDate value="${dto.sureImportTime}" type="date"/>&nbsp;
					</td>
					<td >${dto.batchFileStatusDesc}</td>
					<td>
						<input type="button" value="查  看" class="button2" onclick="showDetail('${dto.batchNum }','${dto.bankCode }','${dto.fileBusiType }');" />
						<c:if test="${dto.batchFileStatus == 4}">
							<input type="button" value="确认导入" class="button2" onclick="confirmImport('${dto.batchFileStatus}','${dto.sureCount }','${dto.batchNum }','${dto.bankCode }','${dto.fileKy }');"  />
						</c:if>
						<c:if test="${dto.batchFileStatus == 4}">
							<input type="button" value="退回导入" class="button2" onclick="refuseImport('${dto.batchNum }','${dto.bankCode }','${dto.fileKy }','${dto.fileBusiType }');"  />
						</c:if>
					</td>
				</tr>
		</c:forEach>
		</form>	
	</tbody>
</table>

<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
<c:if test="${dto.batchFileStatus == 4 and dto.sureCount == 0}">
 	<input type="button" onclick="confirmBatch();" name="submitBtn" value="批量确认导入" class="button4">
</c:if>

<script type="text/javascript">

//id的全选或全部取消
	function selectAll() {
		if($("#checkAll").attr("checked")){
			$("input[name='batchId']").each(function(){
				this.checked = true;
			});
		} else {
			$("input[name='batchId']").each(function() {
				this.checked = false;
			});
		}
	}
	//取消一个选择单位，去掉全选框的勾
	function selectAllcheckBoxunchecked(obj){
	  if(!obj.checked){
		  $("#checkAll").attr("checked",false);
		  }
	}

function showDetail(batchNum,bankCode,tradeType) {

	$("#batchNameId").val(batchNum);
	$("#bankCodeId").val(bankCode);
	$("#tradeTypeId").val(tradeType);
	document.resultForm.action = 'fundout-withdraw-importwdresult.do?method=initResult';
	document.resultForm.submit();
}

function confirmImport(status,sureCount,batchNum,bankCode,gFileKy) {
	if(status != 4 ){
		alert("该批次不处于已导入,无法确认导入");
		return false;
	}
	if(sureCount > 0 ){
		alert("该批次存在未匹配的交易,无法确认导入,请查看相关信息或者退回导入..");
		return false;
	}
	$("#batchNameId").val(batchNum);
	$("#bankCodeId").val(bankCode);
	$("#gFileKyId").val(gFileKy);
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#resultForm").serialize();
	$.ajax({
		type: "POST",
		url: "fundout-withdraw-importwdresult.do?method=sureImport",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			if(result == 'success'){
				alert("文件确认成功，具体处理结果请到“订单中心”进行查询！");
				query();
			}else{
				alert(result);
			}
		}
	});
}

function refuseImport(batchNum,bankCode,gFileKy,tradeType) {
	$("#batchNameId").val(batchNum);
	$("#bankCodeId").val(bankCode);
	$("#gFileKyId").val(gFileKy);
	$("#tradeTypeId").val(tradeType);
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#resultForm").serialize();
	$.ajax({
		type: "POST",
		url: "fundout-withdraw-importwdresult.do?method=refuseImport",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			if(result == 'success'){
				alert("退回导入动作处理成功！");
				query();
			}else{
				alert(result);
			}
		}
	});
}

function checkSelected(){
	num = 0;
	for(var i=0;i <form.elements.length;i++){ 
		var element = form.elements[i];
	     if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
	 	      num++; 
	      } 
	}
	return num;
}

function confirmBatch() {
	var num = checkSelected();
	if(num<=0){
		alert("请选择数据");
		return false;
	}

	var batchImportParaStr="";
	for(var i=0;i <form.elements.length;i++){
		  var element = form.elements[i];
	      if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
	    	  var batchNum = document.getElementById(element.value+"_batch").value;
           	  var bankCode = document.getElementById(element.value+"_bank").value;
           	  var temStr = batchNum + "::" + bankCode;
			  if(batchImportParaStr == "")
				  batchImportParaStr = temStr;
			  else
				  batchImportParaStr += "##" + temStr;
	      } 
	}
	document.getElementById("batImportPrms").value = batchImportParaStr;
	document.resultForm.action = 'fundout-withdraw-importwdresult.do?method=batchSureImport';
	document.resultForm.submit();
}
</script>