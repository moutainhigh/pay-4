<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>


 <form id="queryDetail" name="queryDetail" method="post" action="">
			<input id="busiFlagId" name="busiFlag" type="hidden" value="" />
			<input id="allCountId" name="allCount" type="hidden" value="" />
			<input id="countFlagId" name="countFlag" type="hidden" value="" />
			<input id="withdrawBankCodeId" name="withdrawBankId" type="hidden" value="" />
			<input type="hidden" name="startDate" value='<fmt:formatDate value="${startDate}" type="date"/>'  >
			<input type="hidden" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date"/>' >
</form>			
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
					<thead>
						<tr>
							<th>序号</th>
							<th>对账出款银行</th>
							<th>银行笔数</th>
							<th>系统笔数</th>
							<th>银行总金额</th>
							<th>系统总金额</th>
							<th>银行多帐</th>
							<th>银行少帐</th>
							<th>对账成功</th>
							<th>差异帐</th>
							<th>对账成功总金额</th>
						</tr>
					</thead>
					<tbody>
						
							<c:if test="${page != null && fn:length(page.result) > 0}">
								<c:forEach items="${page.result}" var="reconcile" varStatus="index">
								<tr>
									<td>
										<c:out value="${index.count}" />
									</td>
									<td>
										<li:codetable fieldName="orgCode" selectedValue="${reconcile.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
									</td>
									<td>
									<a href="#" onclick="queryResultDetail('0',1,${reconcile.allBankCount},'${reconcile.withdrawBankId}');" >
										${reconcile.allBankCount}</a>
									</td>
									<td>
									<a href="#" onclick="queryResultDetail('0',2,${reconcile.allAccountCount},'${reconcile.withdrawBankId}');" >
										${reconcile.allAccountCount}
										</a>
									</td>
									<td>
									<fmt:formatNumber value="${reconcile.allBankAmount*0.001}" pattern="#,##0.00"  />
										
									</td>
									<td>
										<fmt:formatNumber value="${reconcile.allAccountAmount*0.001}" pattern="#,##0.00"  />
									</td>
									<td>
									<a href="#" onclick="queryResultDetail(200,'',${reconcile.bankManyCount},'${reconcile.withdrawBankId}');" >
										${reconcile.bankManyCount}
										</a>
									</td>
									<td>
									<a href="#" onclick="queryResultDetail(210,'',${reconcile.sysManyCount},'${reconcile.withdrawBankId}');" >
										${reconcile.sysManyCount}
									</a>
									</td>
									<td>
									<a href="#" onclick="queryResultDetail(100,'',${reconcile.completeMatchCount},'${reconcile.withdrawBankId}');" >
										${reconcile.completeMatchCount}
										</a>
									</td>
									<td>
									<a href="#" onclick="queryResultDetail(220,'',${reconcile.completeNoMatchCount},'${reconcile.withdrawBankId}');" >
										${reconcile.completeNoMatchCount}
										</a>
									</td>
									<td>
										<fmt:formatNumber value="${reconcile.completeMatchAmount*0.001}" pattern="#,##0.00"  />
									</td>
										</tr>
								</c:forEach>
							
							</c:if>
							<c:if test="${page == null || fn:length(page.result) == 0}">
								<tr>
								<td colspan="11" align="center">
									没有查询到相关数据!
								</td>
								</tr>
							</c:if>
						
					</tbody>
				</table>
				<li:pagination methodName="applyReconcileQuery" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">
	function queryResultDetail(busiFlag,countFlag,allCount,withdrawBankId){
			$("#busiFlagId").val(busiFlag);
			$("#countFlagId").val(countFlag);
			$("#withdrawBankCodeId").val(withdrawBankId);
			$("#allCountId").val(allCount);
			if( allCount > 0){
				document.queryDetail.action="fo-rc-queryreconcile.do?method=initDetail";
				document.queryDetail.submit();
			}else{
				alert("没有记录");
				return false;
			}
	}
</script>				