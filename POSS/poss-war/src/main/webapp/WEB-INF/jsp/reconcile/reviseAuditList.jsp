<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>

 <form action="" method="post" name="mainfrom" id="resultForm">
 	<input type="hidden" name="adjustStatus" value="${webQueryReconcileDTO.adjustStatus}"/>
 	<input type="hidden"   name="accountSeq"  value="${webQueryReconcileDTO.accountSeq}" />
 	<input type="hidden"   name="busiFlag"  value="${webQueryReconcileDTO.busiFlag }" />
 	<input type="hidden"   name="countFlag" value="${webQueryReconcileDTO.countFlag}"  />
 	<input type="hidden"   name="bankCode"  value="${webQueryReconcileDTO.bankCode}" />
 	<input type="hidden"   name="providerCode"  value="${webQueryReconcileDTO.providerCode}" />
 	<input type="hidden" name="startTime"  value='<fmt:formatDate value="${webQueryReconcileDTO.startTime}" type="date"/>'   >
	<input  type="hidden" name="endTime"   value='<fmt:formatDate value="${webQueryReconcileDTO.endTime}" type="date"/>' >
 </form> 
   <form action="" method="post" name="fromaudit">
 	<input type="hidden" id="id" name="id"   />
 	 <input type="hidden" name="actionSource" value="audit" />
 	 <input type="hidden" name="acceptKy"  value="" />
 	 <input type="hidden" name="mark"  value="" />
 	 <input  type="hidden" name="busiFlag"   value='${webQueryReconcileDTO.busiFlag }' >
 </form>
 <body>
<table id="reviseAudit" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
	<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="10" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
 
		<tr>
			<!--<th> &nbsp; </th>
			--><th> 序号</th>
			<th> 银行名称</th>
			<th> 服务代码</th>
			<th> 订单号</th>
			<th> 交易金额</th>
			<th> 交易日期</th>
			<th> 错账方</th>
			<th> 调账状态</th>
			<th> 操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result}" var="reconcileAcceptMain" varStatus="status">
			<tr><!--
				<!--<td><input type="checkbox" value="${reconcileAcceptMain.id}" name="choose" onclick="selectAllcheckBoxunchecked(this);" /> </td>
				<input type="checkbox" style="display: none" checked="checked"  name="acceptKyArray" value="${reconcileAcceptMain.acceptKy }" />
				--><td ><c:out value="${status.count}" /></td>
				<td >${reconcileAcceptMain.bankCode}</td>
				<td >${reconcileAcceptMain.providerCode}</td>
				<td >${reconcileAcceptMain.accountSeq}</td>
				<td >
				<fmt:formatNumber value="${reconcileAcceptMain.accountAmount}" pattern="#,##0.00"/>
				</td>
				<td >
				<fmt:formatDate value="${reconcileAcceptMain.busiTime}" type="date"/>
				</td>
				<td >
					${reconcileAcceptMain.busiFlagDesc}	
				</td>
				<td >${reconcileAcceptMain.adjustStatusDesc}</td>
				<td >
				<c:if test="${reconcileAcceptMain.adjustStatus == '3' or reconcileAcceptMain.adjustStatus == '4'}" >
					<c:if test="${not empty reconcileAcceptMain.adjustType and reconcileAcceptMain.adjustType == reconcileAcceptMain.busiFlag}">
						<a href="#" name="qrAuditDetail" onclick="queryReconcileAuditDetail('${reconcileAcceptMain.id}','${reconcileAcceptMain.acceptKy }','${reconcileAcceptMain.busiFlag}');" >
							查看
						</a>
					</c:if>
					<c:if test="${empty reconcileAcceptMain.adjustType}">
						<a href="#" name="qrAuditDetail" onclick="queryReconcileAuditDetail('${reconcileAcceptMain.id}','${reconcileAcceptMain.acceptKy }','${reconcileAcceptMain.busiFlag}');" >
							查看
						</a>
					</c:if>
				</c:if>
					
				<c:if test="${ not empty reconcileAcceptMain.adjustType and reconcileAcceptMain.adjustStatus == '2'}">
					<c:if test="${reconcileAcceptMain.adjustType == reconcileAcceptMain.busiFlag}">
						<a href="#" name="qrAuditDetail" onclick="queryReconcileAuditDetail('${reconcileAcceptMain.id}','${reconcileAcceptMain.acceptKy }','${reconcileAcceptMain.busiFlag}');" >
							审核
						</a>
					</c:if>
					<c:if test="${reconcileAcceptMain.adjustType != reconcileAcceptMain.busiFlag}">
						<!-- <a href="#" name="qrAuditDetail" onclick="queryReconcileAuditDetail('${reconcileAcceptMain.id}','${reconcileAcceptMain.acceptKy }');" >
							查看
						</a> -->
					</c:if>					
				</c:if>
				
				<c:if test="${ empty reconcileAcceptMain.adjustType and reconcileAcceptMain.adjustStatus == '2'}">
					<a href="#" name="qrAuditDetail" onclick="queryReconcileAuditDetail('${reconcileAcceptMain.id}','${reconcileAcceptMain.acceptKy}','${reconcileAcceptMain.busiFlag}');" >
						审核
					</a>
				</c:if>		
				</td>
				
					
			</tr>
		</c:forEach>
	</tbody> 
	<!--<c:if test="${not empty page.result}">
	<tr>
		<td class="border_top_right4" colspan="6"><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选</td>
		<td class="border_top_right4" colspan="2">  <input type="button" onclick="haderReconcileAudit('success');" class="button4" name="submitQuery" value="批量审核通过"></td>
		<td class="border_top_right4" colspan="2"> <input type="button" onclick="haderReconcileAudit('failure');" class="button4" name="submitQuery" value="批量审核失败"></td>
	</tr>
	</c:if>
--></table>
<li:pagination methodName="queryReviseReconcileAudit" pageBean="${page}" sytleName="black2"/>
</body>

<script type="text/javascript">

	function downloadExcel() {
		if("${page.totalCount}" > 0 ){
			document.mainfrom.action = "reconcile.reviseAudit.do?method=downloadExcel&rnd=" + Math.random();
			document.mainfrom.submit();
		}else{
			alert("没有任何数据,无法提供下载");
		}
	}
	
	function queryReconcileAuditDetail(obj,acceptKy,adjustType){
		$("#id").val(obj);
		$("input[name='acceptKy']").val(acceptKy);
		//document.fromaudit.action="reconcile.reviseReconcile.do?method=queryReconcileAcceptMain";
		document.fromaudit.action="reconcile.reviseAudit.do?method=queryReviseAuditDetail&adjustType=" + adjustType ;
		document.fromaudit.submit();
	}


	function haderReconcileAudit(obj){

		$("input[name='mark']").val(obj);

		var id = "";
		$("input[name='choose']").each(function(){
			if(this.checked == true){
					id+=this.value+",";
				}
		});
		var acceptKy = "";
		$("input[name='acceptKyArray']").each(function(){
			if(this.checked == true){
				acceptKy+=this.value+",";
				}
		});
		$("#id").val(id);
		$("input[name='acceptKy']").val(acceptKy);
		document.fromaudit.action="reconcile.reviseReconcile.do?method=handerReconcileAudit";
		document.fromaudit.submit();

		
	}

		//id的全选或全部取消
		function selectAll() {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").each(function(){
					this.checked = true;
				});
			} else {
				$("input[name='choose']").each(function() {
					this.checked = false;
				});
			}
		}
		//取消一个选择单位，去掉全选框的勾
		function selectAllcheckBoxunchecked(obj){
		  if(!obj.checked){
			  $("#checkAll").attr("checked",false);
			  }
		}
		
</script>