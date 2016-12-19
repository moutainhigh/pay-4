<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" id="listForm"  name="resultForm">
 	<input type="hidden" name="fileType" />
 	<input type="hidden" id="batchNumId" name="batchNum" />
 	<input type="hidden" name="gFileKy" id="gFileKyId" />
 </form>

<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th >序号</th>
			<th >批次号</th>
			<th>出款流水号</th>
			<th >批次规则名称</th>
			<th >总笔数</th>
			<th >总金额</th>
			<th >产生时间</th>
			<th >批次文件状态</th>
			<th >处理状态</th>
			<th >操作</th>
		</tr>
	</thead>
	<tbody> 
			<c:forEach items="${page.result}" var="dto" varStatus="status">
					<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
					<td ><c:out value="${status.count}" /></td>
					<td >${dto.batchNum}</td>
					<td>${dto.tradeSeq}</td>
					<td >${dto.ruleName}</td>
					<td >${dto.allCount}&nbsp;</td>
					<td ><fmt:formatNumber value="${dto.allAmount*0.001}" pattern="#,##0.00"  />&nbsp;</td>
					<td >
					<fmt:formatDate value="${dto.generateTime}" type="both"/>
					</td>
					<td id="batchStatus" >${dto.batchFileStatusDesc}</td>
					<td>
						<c:if test="${dto.finStatus == 4}">
							未处理
						</c:if>
						<c:if test="${dto.finStatus == 12}">
							处理成功
						</c:if>
						<c:if test="${dto.finStatus == 10}">
							处理失败
						</c:if>
						<c:if test="${dto.finStatus == 7}">
							处理中
						</c:if>
						<c:if test="${dto.finStatus == 8}">
							处理中
						</c:if>
						<c:if test="${dto.finStatus == 9}">
							处理中												
						</c:if>
					</td>
					<td>
						<input type="button" value="批次文件" class="button2" onclick="downloadBatchFile('12','${dto.batchNum }','${dto.fileKy }');" />
						<input type="button" value="批次业务汇款单" class="button2" onclick="downloadBatchFile('11','${dto.batchNum }','${dto.fileKy }');" />
						<!--<c:if test="${2 == dto.batchFileStatus}">
						</c:if>
						<c:if test="${3 == dto.batchFileStatus}">
							<input type="button" value="批次业务汇款单" class="button2" onclick="downloadBatchFile('11','${dto.batchNum }');" />
						</c:if>-->
					</td>
				</tr>
			</c:forEach>
	</tbody>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
<!--<p align="center"><input type="button" onclick="backQuery();" name="submitBtn" value="返    回" class="button2"></p>
-->
<script type="text/javascript">
			function valInputText(fileType,batchNum){
		$("input[name='fileType']").val(fileType);	
		$("#batchNumId").val(batchNum);
	}
	
	//下载批次文件
	function downloadBatchFile(fileType,batchNum,gFileKy){
		valInputText(fileType, batchNum);
		$("#gFileKyId").val(gFileKy);
		document.resultForm.action='fundout-withdraw-downloadfile.do?method=downloadFile';
		document.resultForm.submit();
	}
	function back(){
		window.location="${ctx}/fundout-withdraw-generatebatch.do"
	}
</script>
