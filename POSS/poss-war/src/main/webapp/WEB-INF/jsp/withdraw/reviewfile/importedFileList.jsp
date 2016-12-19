<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>


 <form action="" method="post" name="resultForm"  id="resultForm">
 	<input type="hidden" name="batchNum" id="batchNameId" />
 	<input type="hidden" name="fileKy" id="fileKyId" />
 	<input type="hidden" name="bankCode" id="bankCodeId" />
 	<input type="hidden" name="tradeType" id="tradeTypeId" />
 	<input type="hidden" name="batImportPrms" id="batImportPrms" value=""/>
 	
 	<!--<c:forEach items="${page.result}" var="dto" varStatus="status">
 		<c:if test="${dto.batchFileStatus == 4 and dto.sureCount == 0}">
			<input type="hidden" id="${dto.batchNum}_batch" name="${dto.batchNum}_batch" value="${dto.batchNum}"/>
			<input type="hidden" id="${dto.batchNum}_bank" name="${dto.batchNum}_bank" value="${dto.bankCode}"/>
		</c:if>
 	</c:forEach>
 	
 --></form>
 
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
				<tr>
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
						<input type="button" value="查  看" class="button2" onclick="showDetail('${dto.batchNum }','${dto.fileKy }','${dto.bankCode }','${dto.fileBusiType }');" />
						<c:if test="${dto.batchFileStatus == 9}">
							<input type="button" value="强制复核通过" class="button4" onclick="confirmImport('${dto.batchNum }','${dto.fileKy }');"  />
							<input type="button" value="退回导入" class="button2" onclick="refuseImport('${dto.batchNum }','${dto.fileKy }','${dto.bankCode }');"  />
						</c:if>
					</td>
				</tr>
		</c:forEach>
		</form>	
	</tbody>
</table>

<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/><!--

 <!--<input type="button" onclick="confirmBatch();" name="submitBtn" value="批量强制通过" class="button4">-->
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

function showDetail(batchNum,fileKy,bankCode,tradeType) {

	$("#batchNameId").val(batchNum);
	$("#bankCodeId").val(bankCode);
	$("#fileKyId").val(fileKy);
	$("#tradeTypeId").val(tradeType);
	document.resultForm.action = 'fundout-withdraw-reviewfile.do?method=initReview';
	document.resultForm.submit();
}

function confirmImport(batchNum,fileKy) {
	$("#batchNameId").val(batchNum);
	$("#fileKyId").val(fileKy);
	document.resultForm.action = 'fundout-withdraw-reviewfile.do?method=sureImport';
	document.resultForm.submit();
}

function refuseImport(batchNum,fileKy,bankCode) {
	if(confirm("您是否确定要退回复核文件导入?")){
		$("#batchNameId").val(batchNum);
		$("#fileKyId").val(fileKy);
		$("#bankCodeId").val(bankCode);
		document.resultForm.action = 'fundout-withdraw-reviewfile.do?method=refuseImport';
		document.resultForm.submit();
	}
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
	document.resultForm.action = 'fundout-withdraw-reviewfile.do?method=batchSureImport';
	document.resultForm.submit();
}
</script>