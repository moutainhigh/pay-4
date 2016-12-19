<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/page.css">

 <form action="reconcile.download.do?method=downloadFile" method="post" name="downloadForm">
 	<input type="hidden" id="fileId" name="fileId"/>
 	<input type="hidden" id="fileNameId" name="fileName"/>
 	<input type="hidden" id="busiDate" name="busiDate"/>
 	<input type="hidden" id="withdrawBankId" name="withdrawBankId"/>
 	<input type="hidden" id="uploadDate" name="uploadDate"/>
 	<input type="hidden" id="status" name="status"/>
 </form>
 
<table id="detailTable" width="85%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th><input type="checkbox" name="allReconcileInfos" id="allReconcileInfos" onclick="checkAll(this);"/>选择</th>
			<th>出款银行</th>
			<th>业务日期</th>
			<th>上传时间</th>
			<th>文件名称</th>
			<th>状态</th>
			<th>操作员</th>
			<th>操作备注</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="reconcileFile" varStatus="status">
		<tr>
			<td >
				<input type="checkbox" name="reconcileInfo" 
					value="${reconcileFile.fileId},<fmt:formatDate pattern='yyyy-MM-dd' value='${reconcileFile.busiDate}'/>,${reconcileFile.withdrawBankId},${reconcileFile.status}"/>
			</td>
			<td >
			<li:codetable selectedValue="${reconcileFile.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
			</td>
			<td >
				<fmt:formatDate pattern='yyyy-MM-dd' value='${reconcileFile.busiDate}'/>
			</td>
			<td >
				<fmt:formatDate pattern='yyyy-MM-dd' value='${reconcileFile.uploadDate}'/>
			</td>
			<td >${reconcileFile.fileName}</td>			
			<td >
				<c:choose>
					<c:when test="${1 eq reconcileFile.status}">
						上传成功
					</c:when>
					<c:when test="${2 eq reconcileFile.status}">
						上传失败
					</c:when>
					<c:when test="${3 eq reconcileFile.status}">
						文件解析成功
					</c:when>
					<c:when test="${4 eq reconcileFile.status}">
						文件解析失败
					</c:when>
					<c:when test="${5 eq reconcileFile.status}">
						文件数据导入成功
					</c:when>
					<c:when test="${6 eq reconcileFile.status}">
						文件数据导入失败
					</c:when>
					<c:when test="${7 eq reconcileFile.status}">
						对账成功
					</c:when>
					<c:when test="${8 eq reconcileFile.status}">
						对账失败
					</c:when>
					<c:when test="${9 eq reconcileFile.status}">
						已废除
					</c:when>
					<c:otherwise>
						${reconcileFile.status}
					</c:otherwise>
				</c:choose>
			</td>
			<td>${reconcileFile.operator}</td>
			<td>${reconcileFile.errorTips}</td>
			<td  colspan="3">
				<c:if test="${2 ne reconcileFile.status && 9 ne reconcileFile.status}">
					<a href="#" onclick="downloadSubmit('${reconcileFile.fileId}','${reconcileFile.fileName}','<fmt:formatDate pattern="yyyyMMdd" value="${reconcileFile.uploadDate}"/>');" >下载</a>
				</c:if>
				<c:if test="${'Y' eq reconcileFile.currFlag && 9 ne reconcileFile.status}">
					<a href="#" onclick="revokeSubmit(this,'${reconcileFile.fileId}');" >废除</a>
				</c:if>
				<c:if test="${4 lt reconcileFile.status && 9 ne reconcileFile.status && 6 ne reconcileFile.status}">
					<a href="#" onclick="detailSubmit('${reconcileFile.fileId}','${reconcileFile.withdrawBankId}','<fmt:formatDate pattern="yyyy-MM-dd" value="${reconcileFile.busiDate}"/>','${reconcileFile.status}');" >详情</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>
<br/>
<center>
   <input type="button" onclick="batchReconcile();" class="button4" value="批量对账"/>
</center>
<script type="text/javascript">

	function batchReconcile(pageNo,totalCount){
		if($("input[name='reconcileInfo']:checked").size() == 0){
			alert("请您先选择对账记录再进行提交!");
			return false;
		}

		var tempCh = "";
		var flag = true;
		$("input[name='reconcileInfo']:checked").each(function(index){
			array = this.value.split(",");
			if(5 != array[3]){
				alert("请选择对账文件状态为【文件数据导入成功】的记录!");
				flag = false;
				return false;
			}
		});
		if(!flag){
			return false;
		}
		
		if(confirm("您是否确定需要进行批量对账提交?")){
			//组装参数
			var params = "";
			var size = $("input[name='reconcileInfo']:checked").size();
			$("input[name='reconcileInfo']:checked").each(function(index){
					if(index < (size - 1)){
						params += this.value + "##";
					}else{
						params += this.value;
					}
				});
		}

		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount +
					"&fileIds=" + params;
		$.ajax({
			type: "POST",
			url: "fo_rc_queryreconcile.do?method=batchReconcileInfo",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function detailSubmit(fileId,orgCode,busiDate,status){
		$("#fileId").val(fileId);
		$("#busiDate").val(busiDate);
		$("#withdrawBankId").val(orgCode);
		$("#status").val(status);
		document.downloadForm.action="fo_rc_queryreconcile.do?method=initQueryUploadFileDetail";
		document.downloadForm.submit();
	}
	
	function downloadSubmit(fileId,fileName,uploadDate){
		$("#fileId").val(fileId);
		$("#fileNameId").val(fileName);
		$("#uploadDate").val(uploadDate);
		document.downloadForm.action="fo_rc_queryreconcile.do?method=download";
		document.downloadForm.submit();
	}
	function revokeSubmit(obj,fileId){
		if(confirm("您是否确定要废除此文件导入的信息?")){
			var params = "fileId=" + fileId;
			$('#infoLoadingDiv').dialog('open');
			$.ajax({
				type: "POST",
				url: "fo_rc_queryreconcile.do?method=revokeUploadFile",
				data: params,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					if("success" != result){
						alert(result);
					}else{
						obj.parentElement.parentElement.cells[5].innerText = "已废除";
						alert("文件已废除!");
					}
				}
			});
		}else{
			return false;
		}
	}

	function checkAll(obj){
		$("input[name='reconcileInfo']").each(function(){
            $(this).attr('checked',obj.checked);
        });
	}
</script>