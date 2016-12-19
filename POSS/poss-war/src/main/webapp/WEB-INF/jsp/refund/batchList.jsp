<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

 <form action="" method="post" name="downloadForm" id="listForm" >
 	<input type="hidden" name="fileType" value="12"  />
 	<input type="hidden" name="fileKy"  />
 	<input type="hidden" name="batchNum"  />
 	<input type="hidden" name="filePath"  />
 	<input type="hidden" name="dlBatchCount"  />
 </form>
 
 <p id="showmsg" style="display: none"></p>
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>批次号</th>
			<th>批次名称</th>
			<th>总笔数</th>
			<th>总金额（元）</th>
			<th>产生时间</th>
			<th>批次文件状态</th>
			<th>下载批次文件次数</th>
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
			<td > ${batchFileInfo.fileName} </td>
			<td > ${batchFileInfo.allCount} </td>
			<td >
				<fmt:formatNumber value="${batchFileInfo.allAmountLong/1000}" pattern="#,##0.00"/>&nbsp;
			</td>
			<td > 
				<fmt:formatDate value="${batchFileInfo.generateTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td > ${batchFileInfo.batchFileStatusDesc} </td>
			<td > ${batchFileInfo.dlBatchCount} </td>
			<td > 
				<a href="javascript:download('${batchFileInfo.fileKy}','${batchFileInfo.batchNum}','${batchFileInfo.filePath}','${batchFileInfo.dlBatchCount}')" >
						下载批次汇总文件
					</a>
				<!--<c:if test="${batchFileInfo.dlBatchCount < 1 && 6 ne batchFileInfo.batchFileStatus }">
					<a href="#" onclick="download('${batchFileInfo.fileKy}','${batchFileInfo.batchNum}','${batchFileInfo.filePath}','${batchFileInfo.dlBatchCount}');">
						下载批次汇总文件
					</a>
				</c:if>	-->	
				<c:if test="${0 eq batchFileInfo.dropStatus && 3 ne batchFileInfo.status}">
					<a href="#" onclick="drop('${batchFileInfo.batchNum}');">
						废除
					</a>
				</c:if>
				<c:if test="${batchFileInfo.status == 3}">
					<a href="#" onclick="rebuild('${batchFileInfo.batchNum}');">
						重支付平台成
					</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">
function download(fileKy,batchNum,filePath,dlBatchCount) {
	$("input[name='fileKy']").val(fileKy);
	$("input[name='batchNum']").val(batchNum);
	$("input[name='filePath']").val(filePath);
	$("input[name='dlBatchCount']").val(dlBatchCount);
	document.downloadForm.action = "refund.file.do?method=downloadBatchFile";
	document.downloadForm.submit();
}

//废除批次
function drop(batchNum) {
	if(!confirm("确定要废除批次?")){
		return;
	}
	var pars = $("#listForm").serialize()+ "&batchNum1=" + batchNum ;
	$.ajax({
		type: "POST",
		url: "refund.file.do?method=invalidBatchFile",
		data: pars,
		success: function(transport) {
	          var result = eval('('+transport+')');
	          $("#showmsg").html('<li style="color: red ">'+result.infos+' </li>');
	          $("#showmsg").show();
	          window.setTimeout("submitByHref()", 900);
	          }       
	            // alert("网络延时! 加载数据失败!");
		});
}

//重支付平台成批次
function rebuild(batchNum) {
	if(!confirm("确定要重支付平台成批次?")){
		return;
	}
	var pars = $("#listForm").serialize()+ "&batchNum1=" + batchNum ;
	$.ajax({
		type: "POST",
		url: "refund.file.do?method=regenerateBatchFile",
		data: pars,
		success: function(transport) {
	          var result = eval('('+transport+')');
	          $("#showmsg").html('<li style="color: red ">'+result.infos+' </li>');
	          $("#showmsg").show();
	          window.setTimeout("submitByHref()", 900);
	          }
		});
}
</script>