<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.pay.poss.refund.common.constant.RefundConstants"%>

 <form action="" method="post" name="downloadForm">
 	<input type="hidden" name="fileType" value="<%=RefundConstants.FILE_TYPE_BANK %>"  />
 	<input type="hidden" name="fileKy"  />
 	<input type="hidden" name="batchNum"  />
 	<input type="hidden" name="filePath"  />
 	<input type="hidden" name="dlBankCount"  />
 	<input type="hidden" name="bankCode"  />
 </form>
 
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>批次号</th>
			<th>银行名称</th>
			<th>总笔数</th>
			<th>总金额（元）</th>
			<th>生成时间</th>
			<th>下载时间</th>
			<th>导入时间</th>
			<th>确认导入时间</th>
			<th>批次状态</th>
			<th>下载银行文件次数</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="batchFileInfo" varStatus="status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td ><c:out value="${status.count}" /></td>
			<td > ${batchFileInfo.batchNum} </td>
			<td > 
				<li:select name="bankCode1" itemList="${bankList}" selected="${batchFileInfo.bankCode}" showStyle="desc" />
			</td>
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
			<td > ${batchFileInfo.dlBankCount} </td>
			<td > 
				<a href="#" onclick="download('${batchFileInfo.fileKy}','${batchFileInfo.batchNum}','${batchFileInfo.filePath}','${batchFileInfo.dlBankCount}');">
						下载银行文件
					</a>
				<!--<a href="#" onclick="queryDetail('${batchFileInfo.fileKy}');">
					查看批次文件
				</a>-->
				<!--<c:if test="${batchFileInfo.batchFileStatus == 1 || batchFileInfo.batchFileStatus == 2 || batchFileInfo.batchFileStatus == 3}">
					<a href="#" onclick="download('${batchFileInfo.fileKy}','${batchFileInfo.batchNum}','${batchFileInfo.filePath}','${batchFileInfo.dlBankCount}');">
						下载银行文件
					</a>
				</c:if>-->
				<!--<c:if test="${batchFileInfo.batchFileStatus == 3}">
					<a href="#" onclick="upload('${batchFileInfo.fileKy}','${batchFileInfo.batchNum}','${batchFileInfo.bankCode}');">
						导入
					</a>
				</c:if>
				<c:if test="${batchFileInfo.batchFileStatus == 4}">
					<a href="#" onclick="drop('${batchFileInfo.fileKy}','${batchFileInfo.batchNum}','${batchFileInfo.bankCode}');">
						废除
					</a>
				</c:if>-->
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">
/*
function queryDetail(fileKy) {
	$("input[name='fileKy']").val(fileKy);
	document.downloadForm.action = "refund.file.do?method=initFileDetail";
	document.downloadForm.submit();
}
*/
function download(fileKy,batchNum,filePath,dlBankCount) {
	$("input[name='fileKy']").val(fileKy);
	$("input[name='batchNum']").val(batchNum);
	$("input[name='filePath']").val(filePath);
	$("input[name='dlBankCount']").val(dlBankCount);
	document.downloadForm.action = "refund.file.do?method=downloadBatchFile";
	document.downloadForm.submit();
}

function upload(fileKy,batchNum,bankCode) {
	$("input[name='fileKy']").val(fileKy);
	$("input[name='batchNum']").val(batchNum);
	$("input[name='bankCode']").val(bankCode);
	document.downloadForm.action = "refund.file.do?method=entryFileUploadPage";
	document.downloadForm.submit();
}

function drop(fileKy,batchNum,bankCode) {
	$("input[name='fileKy']").val(fileKy);
	$("input[name='batchNum']").val(batchNum);
	$("input[name='bankCode']").val(bankCode);
	document.downloadForm.action = "refund.file.do?method=dropImportedFile";
	document.downloadForm.submit();
}
</script>