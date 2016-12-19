<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>

<script type="text/javascript" src="js/common.js"></script>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/page.css">
 
 <form action="reconcile.download.do?method=downloadFile" method="post" name="downloadForm">
 	<input type="hidden" id="fileFolderId" name="fileFolder"  />
 	<input type="hidden" id="fileNameId" name="fileName"   />
 	<input type="hidden"   name="fileKy"   />
 </form>
 
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>银行名称</th>
			<th>服务代码</th>
			<th>交易时间</th>
			<th>文件名称</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="reconcileFile" varStatus="status">
		<tr>
			<td ><c:out value="${status.count}" /></td>
			<td >${reconcileFile.bankCode}</td>
			<td >${reconcileFile.providerCode}</td>
			<td >
				<fmt:formatDate pattern='yyyy-MM-dd' value='${reconcileFile.busiTime}'/>
			</td>
			<td >${reconcileFile.fileName}</td>
			<td  colspan="3">
				<!-- <a href="#" onclick='downloadSubmit("${reconcileFile.providerCode}","${reconcileFile.fileName}");' >下载</a> -->
				<a href="#" onclick='deleteSubmit("${reconcileFile.fileKy}","<fmt:formatDate pattern='yyyy-MM-dd' value='${reconcileFile.uploadTime}'/>","<fmt:formatDate pattern='yyyy-MM-dd' value='${now}'/>");' >删除</a>
				<a href="#" onclick='detailSubmit("${reconcileFile.fileKy}");' >详情</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table> 
	
<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">

	function detailSubmit(fileKy){
		$("input[name='fileKy']").val(fileKy);
		document.downloadForm.action="reconcile.download.do?method=initDetail";
		document.downloadForm.submit();
	}
	
	function downloadSubmit(providerCode,fileName){
		$("#fileFolderId").val(providerCode);
		$("#fileNameId").val(fileName);
		document.downloadForm.action="reconcile.download.do?method=downloadFile&rnd=" + Math.random();
		document.downloadForm.submit();
	}
	function deleteSubmit(fileKy,uploadTime,now){
		if(uploadTime != now){
			alert("非当天数据,不能删除");
		}else{
			var truthBeTold = window.confirm("确定要删除吗?");
           	if (truthBeTold) {
           		$('#infoLoadingDiv').dialog('open');
        		var pars = $("#downloadForm").serialize() + "&fileKy=" + fileKy + "&fileName=&fileFolder=" ;
        		$.ajax({
        				type: "POST",
        				url: "reconcile.download.do?method=deleteFile",
        				data: pars,
        				success: function(result) {
        					$('#infoLoadingDiv').dialog('close');
            				if ( result && result.length > 0 ) {
            					alert ( result ) ;
            				}
        					submitByHref () ;
        				}
        			}) ;
           	}else{
           	  	return;
           	}
		}
	}

</script>