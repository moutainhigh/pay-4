<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
	table.tablesorter tbody .tr_bg1 td{ background-color:#fff;}
	table.tablesorter tbody .tr_bg2 td{ background-color:#DDECFB;}
</style>

<form action="" method="post" name="querymainfrom" id="querymainfrom">
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
					   
						<th>银行名称</th>
						<th>批次号</th>
						<th>订单号</th>
						<th>银行订单号</th>
						<th>金额</th>
						<th>交易状态</th>
						<th>日期</th>
						<th>申请人</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody> 

					   <c:forEach items="${requestScope.paramsList}" var="parammap">    
					<tr class="tr_bg1">
					
						<td>		
							${parammap.itemname}
						</td>
						<td>
							${parammap.batchNo}
						</td>
						<td>
							${parammap.sodepositprotocolno}
						</td>
						<td>
						<c:if test="${not (parammap.supplementRetrunNo eq 'null')}">
									 ${parammap.supplementRetrunNo}
						 </c:if>
						</td>
						<td>
							<fmt:formatNumber value="${parammap.depositamount*0.001}" pattern="#0.00"  />
						</td>
						<td>
						   <c:choose>
								<c:when test="${parammap.depositorderstatus eq '0'}">
									处理中
								</c:when>
								<c:when test="${parammap.depositorderstatus eq '1'}">
									成功
								</c:when>
								<c:when test="${parammap.depositorderstatus eq '2'}">
									失败
								</c:when>
							
							</c:choose>
						</td>
				        <td>
							${parammap.supplementCreateDate}
						</td>
                        <td>
							${parammap.operator}
						</td>
						<td>
							<c:choose>
								<c:when test="${parammap.supplementstatus eq '0'}">
									未审核
								</c:when>
								<c:when test="${parammap.supplementstatus eq '1'}">
									成功
								</c:when>
								<c:when test="${parammap.supplementstatus eq '2'}">
									失败
								</c:when>
								
							</c:choose>
						</td>
						<td>
							 <input type="button" class="button2" name="batchname" value="重新补单" onclick="batchsubmit('${parammap.sodepositprotocolno}','${parammap.supplementRetrunNo}','${parammap.operator}','${parammap.auditors}','${parammap.followno}','${parammap.batchNo}','${parammap.billType}');"/>
			    &nbsp;
				<input type="button" class="button2" name="warningname"  value="报 警" onclick="batchwarning('${parammap.sodepositprotocolno}');"/>
						</td>
						
				</tr>
               
			    <tr class="tr_bg2">
					<td> ${parammap.itemname} </td>
					<td>  </td>
					<td> ${parammap.dbdepositprotocolno} </td>
					<td> 
					     <c:if test="${not (parammap.depositRetrunNo eq 'null')}">
									 ${parammap.depositRetrunNo}
						 </c:if>
					</td>
					<td> <fmt:formatNumber value="${parammap.depositamount*0.001}" pattern="#0.00"  /> </td>
					<td>  <c:choose>
								<c:when test="${parammap.depositorderstatus eq '0'}">
									处理中
								</c:when>
								<c:when test="${parammap.depositorderstatus eq '1'}">
									成功
								</c:when>
								<c:when test="${parammap.depositorderstatus eq '2'}">
									失败
								</c:when>
							
							</c:choose> </td>
					<td> ${parammap.depositCreateDate} </td>
					<td colspan="5"></td>
				</tr>

			</c:forEach>
		</tbody>
		
	</table>

	<div >
			 <li:pagination methodName="querysearch" pageBean="${page}" sytleName="black2"/>
		
		</div>
	</form>  

 <script type="text/javascript">
	

    function batchsubmit(protocolno,retrunNo,operator,auditors,followno,batchNo,billType){
		//重新补单
		document.querymainfrom.action="batchQuery.htm?method=batchsubmit&protocolno="+protocolno+"&retrunNo="+retrunNo+"&operator="+operator+"&auditors="+auditors+"&followno="+followno+"&batchNo="+batchNo+"&billType="+billType;
		document.querymainfrom.submit();
	}
	function batchwarning(protocolno){
		//报警
		document.querymainfrom.action="batchQuery.htm?method=batchwarning&protocolno="+protocolno;
		document.querymainfrom.submit();
	}
    
</script>