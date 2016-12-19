<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="detailForm" id="mainfromId">
 	<input type="hidden"   name="busiFlag"  value="${busiFlag }" />
 	<input type="hidden"   name="countFlag" value="${countFlag}"  />
 	<input type="hidden"   name="withdrawBankId"  value="${withdrawBankId }" />
 	<input type="hidden" name="startDate" value='<fmt:formatDate value="${startDate}" type="date"/>'  >
	<input type="hidden" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date"/>' >
 </form>
 <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" > 
	<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">总笔数:</font></a></td>
	<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF"><font color="red">${allCount}</font>笔</font></a></td>
	<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">总金额:</font></a></td>
	<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF"><font color="red"><fmt:formatNumber value="${allAmount*0.001}" pattern="#,##0.00"  /></font>元</font></a></td>
	</tr>
</table>
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr class="trForContent1" > 
			<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
		</tr>
		<tr >
			<th >序号</th>
			<th >银行名称</th>
			<th >出款业务</th>
			<th >交易号</th>
			<th >交易流水号</th>
			<th >交易金额</th>
			<th >交易状态</th>
			<th >交易日期</th>
		</tr>
	</thead>
	<tbody> 
			<c:forEach items="${page.result}" var="dto" varStatus="status">
				<tr>
					<td ><c:out value="${status.count}" /></td>
					<td >
					<li:codetable selectedValue="${dto.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable" />
					</td>
					<td >
					<li:codetable selectedValue="${dto.withdrawBusiType}" style="desc" attachOption="true" codeTableId="fundOutBusinessTable" />
					</td>
					
					<td >${dto.resultId}&nbsp;</td>
					<td >
							${dto.tradeSeq }
					&nbsp;</td>
					<td >
							<fmt:formatNumber value="${dto.tradeAmount*0.001}" pattern="#,##0.00"  />&nbsp;
					&nbsp;</td>
					<td >
						成功
					</td>
					<td >
						<fmt:formatDate value="${dto.busiDate}" type="date"/>
					</td>
				</tr>
			</c:forEach>
	</tbody>
	
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
<div align="center">
	 <a class="s03" href="#" onclick="backInit();"><img
			src="./images/goback.jpg" border="0"> </a>
      </div>
      
<c:set var="listSize" value="${fn:length(page.result)}"/>
<script type="text/javascript">

function downloadExcel() {
	if("${listSize}" > 0){
		document.detailForm.action = 'fo_rc_downloadexcelcontroller.do?method=downloadResultDetail2Excel';
		document.detailForm.submit();
	}else{
		alert("没有任何数据,无法提供下载");
	}
}

function backInit(pageNo,totalCount,pageSize) {
	document.detailForm.action = 'fo-rc-queryreconcile.do';
	document.detailForm.submit();
}
</script>
