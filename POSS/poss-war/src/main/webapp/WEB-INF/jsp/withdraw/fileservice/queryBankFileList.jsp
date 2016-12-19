<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post"  name="resultForm">
 	<input type="hidden" name="fileType"  />
 	<input type="hidden" name="channelCode" />
 	<input type="hidden" name="bankCode"  id="bankCodeId"/>
 	<input type="hidden" name="tradeType"  id="tradeTypeId"/>
 	<input type="hidden" name="batchNum"  id="batchNumId"/>
 	<input type="hidden" name="batchName" id="ruleNameId"/>
 	<input type="hidden" name="gFileKy" id="gFileKyId" />
 	<input type="hidden" name="bussinessType" id="bussinessTypeId" />
 </form>

<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th nowrap="nowrap">序号</th>
			<th nowrap="nowrap">批次号</th>
			<th nowrap="nowrap">批次规则名称</th>
			<th nowrap="nowrap">银行名称</th>
			<th nowrap="nowrap">总笔数</th>
			<th nowrap="nowrap">总金额（元）</th>
			<th nowrap="nowrap">对公对私标志</th>
			<th nowrap="nowrap">生成时间</th>
			<th nowrap="nowrap">下载时间</th>
			<th nowrap="nowrap">复核时间</th>
			<th nowrap="nowrap">导入时间</th>
			<th nowrap="nowrap">确认导入时间</th>
			<th nowrap="nowrap">批次状态</th>
			<th nowrap="nowrap" >操作</th>
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
					<td nowrap="nowrap"><c:out value="${status.count}" /></td>
					<td nowrap="nowrap">${dto.batchNum}</td>
					<td nowrap="nowrap">${dto.ruleName}</td>
					<td nowrap="nowrap"><li:select name="bankCode" itemList="${withdrawBankList}" selected="${dto.bankCode}" showStyle="desc" /> </td>
					<td nowrap="nowrap">${dto.allCount}&nbsp;</td>
					<td nowrap="nowrap"><fmt:formatNumber value="${dto.allAmount*0.001}" pattern="#,##0.00"  />&nbsp;</td>
					<td nowrap="nowrap">
					${dto.fileBusiTypeStr }
					</td>
					<td nowrap="nowrap">
					<fmt:formatDate value="${dto.generateTime}" type="both"/>&nbsp;
					</td>
					<td nowrap="nowrap">
					<fmt:formatDate value="${dto.downloadTime}" type="both"/>&nbsp;
					</td>
					<td nowrap="nowrap">
					<fmt:formatDate value="${dto.foreviewTime}" type="both"/>&nbsp;
					</td>
					<td nowrap="nowrap">
					<fmt:formatDate value="${dto.importTime}" type="both"/>&nbsp;
					</td>
					<td nowrap="nowrap">
					<fmt:formatDate value="${dto.sureImportTime}" type="both"/>&nbsp;
					</td>
					<td nowrap="nowrap">${dto.batchFileStatusDesc}</td>
					<td nowrap="nowrap">
					<!-- flag:0代表复核,结果文件都不存在; 1代表只有复核文件; 2代表只有结果文件;3复核,结果文件都存在。
					batchFileStatus:1,'文件未生成';2,'文件已生成';3,'已下载';4,'已导入';5,'已确认导入';6,'批次文件已废除';8,'已复核';9,'已复核导入'。 -->
					<!-- 当“银行文件”已经下载，并且“复核文件”存在时，才显示“复核”按钮。 -->
					<%-- <c:if test="${3 == dto.batchFileStatus && (1 == dto.flag || 3==dto.flag)}"> --%>
					<c:if test="${3 == dto.batchFileStatus}"><!-- 因custom_batch_flow(定制批次流程表)数据为空，上面判断条件中flag为空，所以无法显示“复合”按键。 -->
						<input onclick="openReviewPage('${dto.batchNum}','${dto.ruleName}','${dto.bankCode}','${dto.fileBusiType }','${dto.fileKy }','FC');" class="button2" type="button" value="复核">
						<%-- <img src="images/fuhe.gif" onclick="openReviewPage('${dto.batchNum}','${dto.ruleName}','${dto.bankCode}','${dto.fileBusiType }','${dto.fileKy }','FC');"></img> --%>
					</c:if>
					<!-- 1、当“银行文件”已经下载时，显示“导入”按钮；2、当“银行文件”已经下载，并且有“结果”文件时，显示“导入”按钮；3、“复核”和“结果”文件都存在，并且“已复核”时，显示“导入”按钮。 -->
					<%-- <c:if test="${(8 == dto.batchFileStatus && 3==dto.flag) || (3 == dto.batchFileStatus && 2 == dto.flag) || (3 == dto.batchFileStatus)}"> --%>
					<c:if test="${(8 == dto.batchFileStatus) || (3 == dto.batchFileStatus)}">
						<input onclick="importBankFile('${dto.batchNum}','${dto.ruleName}','${dto.bankCode}','${dto.fileBusiType }','${dto.fileKy }','FR');" class="button2" type="button" value="导入">
						<%-- <img src="images/01.gif" onclick="importBankFile('${dto.batchNum}','${dto.ruleName}','${dto.bankCode}','${dto.fileBusiType }','${dto.fileKy }','FR');"></img> --%>
					</c:if>
					<input onclick="downloadBankFile('${dto.bankCode}','${dto.channelCode }','22','${dto.batchNum }','${dto.fileKy }','${dto.fileBusiType }');" class="button2" type="button" value="银行文件">
					<input onclick="downloadBankFile('${dto.bankCode}','${dto.channelCode }','21','${dto.batchNum }','${dto.fileKy }','${dto.fileBusiType }');" class="button2" type="button" value="银行业务汇款单">
					<%-- <img src="images/02.gif" onclick="downloadBankFile('${dto.bankCode}','${dto.channelCode }','22','${dto.batchNum }','${dto.fileKy }','${dto.fileBusiType }');"></img>
					<img src="images/03.gif" onclick="downloadBankFile('${dto.bankCode}','${dto.channelCode }','21','${dto.batchNum }','${dto.fileKy }','${dto.fileBusiType }');"></img> --%>
						<!--<c:if test="${2 == dto.batchFileStatus}">
							
							<input type="button" value="银行文件" class="button2" onclick="downloadBankFile('${dto.channelCode }','22','${dto.batchNum }');" />
							<input type="button" value="银行业务汇款单" class="button2" onclick="downloadBankFile('${dto.channelCode }','21','${dto.batchNum }');" />
						</c:if>
						<c:if test="${3 == dto.batchFileStatus}">
							<img src="images/fuhe.gif" onclick="openReviewPage('${dto.bankCode }','${dto.fileBusiType }','${dto.batchNum }','22','${dto.channelCode }');"></img>
							<img src="images/03.gif" onclick="downloadBankFile('${dto.channelCode }','21','${dto.batchNum }');"></img>
							<c:if test="${4 == dto.batchFileStatus or 5 == dto.batchFileStatus }">
							<img src="images/03.gif" onclick="downloadBankFile('${dto.channelCode }','21','${dto.batchNum }');"></img>
						</c:if>
						</c:if>-->
					</td>
				</tr>
			</c:forEach>
	</tbody>
	
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
<!--<p align="center"><input type="button" onclick="backQuery();" name="submitBtn" value="返    回" class="button2"></p>
--><script type="text/javascript">

	function valInputText(channelCode,fileType,batchNum){
		$("input[name='channelCode']").val(channelCode);
		$("input[name='fileType']").val(fileType);
		$("#batchNumId").val(batchNum);
	}
	
	//下载批次文件
	function downloadBankFile(bankCode,channelCode,fileType,batchNum,gFileKy,fileBusiType){
		valInputText(channelCode,fileType, batchNum);
		$("#bankCodeId").val(bankCode);
		$("#gFileKyId").val(gFileKy);
		$("#bussinessTypeId").val(fileBusiType);
		document.resultForm.action='fundout-withdraw-downloadfile.do?method=downloadFile';
		document.resultForm.submit();
	} 
	//导入银行文件
	function importBankFile(batchNum,ruleName,bankCode,tradeType,gFileKy,bussinessType){
		$("#batchNumId").val(batchNum);
		$("#ruleNameId").val(ruleName);
		$("#bankCodeId").val(bankCode);
		$("#tradeTypeId").val(tradeType);
		$("#gFileKyId").val(gFileKy);
		$("#bussinessTypeId").val(bussinessType);
		var url ='fundout-withdraw-importwdresult.do?method=initImport';
		//url = 'uploadFundoutCheck.do?method=index';//modified by davis.guo at 2016-08-12。原流程没错，by davis.guo at 2016-08-26
		document.resultForm.action = url;
		document.resultForm.submit();
	}

	function back(){
		window.location="${ctx}/fundout-withdraw-generatebatch.do";
	}

	 function openReviewPage(batchNum,ruleName,bankCode,tradeType,gFileKy,bussinessType)
     {	
		 $("#batchNumId").val(batchNum);
		 $("#ruleNameId").val(ruleName);
		 $("#bankCodeId").val(bankCode);
		 $("#tradeTypeId").val(tradeType);
		 $("#gFileKyId").val(gFileKy);
		 $("#bussinessTypeId").val(bussinessType);
	     var url = "fundout-withdraw-reviewfile.do?method=initImport";
	     url = 'uploadFundoutCheck.do?method=index';//原有复核功能不可用，直接转到“导入出款复核文件”页面。modified by davis.guo at 2016-08-29。注：记得删除“导入出款复核文件”菜单项，同时修改菜单项 “导入银行结果文件”为“查询银行结果文件”
	     document.resultForm.action = url;
		 document.resultForm.submit();
     }

	
</script>
