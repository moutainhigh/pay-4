<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/page.css">

 <form action="" method="post" name="hFrom">
 	<input type="hidden" name="fileKy"  />
 	<input type="hidden" name="batchNum"  />
 	<input type="hidden" name="bankCode"  />
  </form>

<form action="" method="post" name="manyForm">
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>
				选择
				<input type="checkbox" name="allCheck" id="allCheck"> 
			</th>
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
	<c:forEach items="${page.result}" var="batchFileInfo" varStatus="status">
		<tr>
			<td >
				<input type="checkbox" name="ifChecked" value="${status.count-1}"
					<c:if test="${batchFileInfo.notMatchCount > 0 || batchFileInfo.batchFileStatus != 4}">
						disabled="disabled"
					</c:if>
				>
				<input type="hidden" name="fileKy" value="${batchFileInfo.fileKy}">
			</td>
			<td ><c:out value="${status.count}" /></td>
			<td > ${batchFileInfo.batchNum} </td>
			<td > ${batchFileInfo.fileName} </td>
			<td > ${batchFileInfo.allCount} </td>
			<td > 
		 		<fmt:formatNumber value="${batchFileInfo.allAmountLong/1000}" pattern="#,##0.00"/>&nbsp;
		 	</td>
			<td > 
				<fmt:formatDate value="${batchFileInfo.generateTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td > 
				<fmt:formatDate value="${batchFileInfo.downloadTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td > 
				<fmt:formatDate value="${batchFileInfo.importTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td > 
				<fmt:formatDate value="${batchFileInfo.sureimportTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td > ${batchFileInfo.batchFileStatusDesc} </td>
			<td > 
				<a href="#" onclick="showDetail('${batchFileInfo.batchNum}','${batchFileInfo.bankCode}');">
					查看
				</a>
				<c:if test="${batchFileInfo.notMatchCount == 0 && batchFileInfo.batchFileStatus == 4}">
					<input type="button" onclick="confirmSingle('${batchFileInfo.fileKy}');" name="submitBtn" value="确认导入" class="button3">
				</c:if>
				<c:if test="${batchFileInfo.notMatchCount > 0 }">
					<input type="button" onclick="refuseImport('${batchFileInfo.fileKy}','${batchFileInfo.batchNum}','${batchFileInfo.bankCode}');" name="submitBtn" value="退回导入" class="button3">
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</form>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

<input type="button" onclick="confirmBatch();" name="submitBtn" value="批量确认导入" class="button3">
<script type="text/javascript">
$(function(){ 
    $("#allCheck").click(function(){ 
    	$(':checkbox[name=ifChecked]').each(function() {
        	if(!$(this).attr("disabled")) {
				this.checked = $("#allCheck").attr("checked");
        	}
		 });
    }); 
}); 

function showDetail(batchNum,bankCode) {
	$("input[name='batchNum']").val(batchNum);
	$("input[name='bankCode']").val(bankCode);
	document.hFrom.action = "refund.file.do?method=resultDetailInit";
	document.hFrom.submit();
}

function refuseImport(fileKy,batchNum,bankCode) {
	$("input[name='fileKy']").val(fileKy);
	$("input[name='batchNum']").val(batchNum);
	$("input[name='bankCode']").val(bankCode);
	if(confirm("您是否确定需要进行确认导入提交?")){
		document.hFrom.action = "refund.file.do?method=dropImportedFile";
		document.hFrom.submit();
	}
	return;
}

function confirmSingle(fileKy) {
	$("input[name='fileKy']").val(fileKy);
	if(confirm("您是否确定需要进行确认导入提交?")){
		document.hFrom.action = "refund.file.do?method=confirmImportedSingle";
		document.hFrom.submit();
	}
	return;
}

function confirmBatch() {
	if(0 == $(":checkbox[name=ifChecked]:checked").size()){
		alert("请您先选择需要确认的记录!");
		return false;
	}
	if(confirm("您是否确定需要进行批量确认导入提交?")){
		document.manyForm.action="refund.file.do?method=confirmImportedBatch";
		document.manyForm.submit();
	}
	return;
}
</script>