<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>

	<style type="text/css"> 
		.tableMain { width:600px; margin:50px auto 0; }
		.tableMain, .tableMain th { border-collapse:collapse; border:1px solid #000; }
		.scrollTable { height:100px; overflow-x: hidden; overflow-y: auto; width: 100%; }
		.winbd033{ 
				border: 1px solid blue;
				border-top-width: 0px; 
				border-right-width: 0px; 
				order-bottom-width: 1px; 
				border-left-width: 1px; 
				line-height:20px;
				background-color:#FCFCFC;
				text-align:center;}
				 
		.winbd023{ border: 1px solid blue;
				border-top-width: 1px; 
				border-right-width: 0px; 
				border-bottom-width: 1px; 
				border-left-width: 	0px; 
				background-color:#D2E9FF;
				text-align:center;
				line-height:25px;}
	
	</style>

 <form action="" method="post" name="resultForm">
 	<input type="hidden"   name="busiFlag"   />
 	<input type="hidden"   name="countFlag"   />
 	<input type="hidden"   name="allCount"   /> 
 	<input type="hidden"   name="bankCode"   />
 	<input type="hidden"   name="providerCode"   />
 	<input type="hidden" name="startTime" value='<fmt:formatDate value="${webQueryReconcileDTO.startTime}" type="date"/>'  >
	<input type="hidden" name="endTime"  value='<fmt:formatDate value="${webQueryReconcileDTO.endTime}" type="date"/>' >
 </form>
<div style="left:15;position:absolute;z-index:1">
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
	 	<!-- 优化后 -->
		<tr>     
				<th>序号</th>     
				<th>银行名称</th>     
				<th>网关名称</th>     
				<th>匹配笔数</th>     
				<th>匹配金额</th>
				<th>未调账笔数</th>
				<th>未调账金额</th>
				<th>已调账笔数</th>
				<th>已调账金额</th>
				<th>记账状态</th>
				<th>操作</th>
			</tr> 
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="reconcileRsultSummary" varStatus="status">
		<!-- 优化后 -->
		<tr >
			<td ><c:out value="${status.count}" /></td><!-- ${reconcileRsultSummary.fileKy} -->
			<td >${reconcileRsultSummary.bankName}</td>
			<td >${reconcileRsultSummary.bankAlias}</td>
		<c:if test="${fn:length(reconcileRsultSummary.timeDisDesc)>0}">
			<!-- 匹配笔数 -->
			<td style='background-Color:yellow' style="cursor:hand;"  onclick="showDivMsg('${reconcileRsultSummary.timeDisDesc}')">${reconcileRsultSummary.completeMatchCount}</td>
			<!-- 匹配金额 -->
			<td style='background-Color:yellow' style="cursor:hand;"  onclick="showDivMsg('${reconcileRsultSummary.timeDisDesc}')"><fmt:formatNumber value="${reconcileRsultSummary.completeMatchAmount}" pattern="#,##0.00"/></td>
		</c:if>
		<c:if test="${fn:length(reconcileRsultSummary.timeDisDesc)==0}">
			<!-- 匹配笔数 -->
			<td>${reconcileRsultSummary.completeMatchCount}</td>
			<!-- 匹配金额 -->
			<td><fmt:formatNumber value="${reconcileRsultSummary.completeMatchAmount}" pattern="#,##0.00"/></td>
		</c:if>			
			<td >${reconcileRsultSummary.unAdjustMatchCount}</td>
			<td ><fmt:formatNumber value="${reconcileRsultSummary.unAdjustMatchAmount}" pattern="#,##0.00"  /></td>
			<td >${reconcileRsultSummary.adjustMatchCount}</td>
			<td ><fmt:formatNumber value="${reconcileRsultSummary.adjustMatchAmount}" pattern="#,##0.00"  /></td>
			<td>
				<c:if test="${reconcileRsultSummary.sKeepState == true}">
					<font color="blue">已记账</font>
				</c:if>
				<c:if test="${reconcileRsultSummary.sKeepState == false}">
					未记账
				</c:if>
			</td>
			<td>
				<c:if test="${reconcileRsultSummary.sKeep == true}">
					<input  class="button2"  type="button" value="记  账" onclick="this.disabled=true;regReconcileResult('${reconcileRsultSummary.fileKy}',this)"/>
				</c:if>
				&nbsp;
			</td>
		</tr>
	</c:forEach>
	</tbody> 
</table>


<li:pagination methodName="queryReconcileResult" pageBean="${page}" sytleName="black2"/>
</div>

 <div id="divMsg"  style="width:650px;display:none;z-index:9999;position:absolute;">
 	<table onDblClick='hideDivMsg()'  border='0' cellspacing='' cellpadding='0' style='cursor:hand;width:100%;word-break:break-all;'>
 		<tr>
 			<td class="winbd023" width="40px" align='center' style="border-left-width:1px;"><b>序号</b></td>
 			<td class="winbd023" width="60px" align='center'><b>挂账方</b></td>
 			<td class="winbd023" width="230px" align='center'><b>订单号</b></td>
 			<td class="winbd023" width="60px" align='center'><b>交易金额</b></td>
 			<td class="winbd023" width="130px" align='center'><b>系统交易完成时间</b></td>
 			<td class="winbd023" width="130px" align='center' style="border-right-width:1px;"><b>银行完成时间</b></td>
 		</tr>
 		<tr>
 			<td colspan="6">
 				<div id="divMsgIn" class="scrollTable" style="overflow-x:hidden;overflow-y:auto;width:100%;">
 				
 				</div>
 			</td>
 		</tr>
 	</table>
 	
 </div>
<script type="text/javascript">
	function showDivMsg ( msg ) {
		if ( !msg || msg.length == 0 ) {
			return ;
		}
		var divMsgObj = document.getElementById ("divMsg") ;
		var divMsgInObj = document.getElementById ("divMsgIn") ;
		divMsgInObj.innerHTML = "" ;
		
		var msgAlls = msg.split ( "," ) ;
		var sa = "style='background-Color:#FFE4CA' align='center' " ;
		var tableStr = "" ;
		tableStr += "<table  onDblClick='hideDivMsg()' border='0' cellspacing='0' cellpadding='0' style='cursor:hand;width:100%;word-break:break-all;'>" ;
		tableStr += "<tbody>" ;
		for ( var cnt=0 ; cnt< msgAlls.length ; cnt++ ) {
			var msgs = msgAlls[cnt].split ("=") ;
			tableStr += "<tr><td class='winbd033' style='width:40px;'>&nbsp;"+(cnt+1)+"</td>" ;
			tableStr += msgs[0] == '1' ? "<td class='winbd033' style='width:60px;'>银行</td>" : "<td class='winbd033' style='width:60px;'>系统</td>"  ;
			tableStr += "<td class='winbd033' style='width:230px;'>&nbsp;"+msgs[2]+"</td>";
			tableStr += "<td class='winbd033' style='width:60px;'>&nbsp;"+fmoney(msgs[1],2)+"</td>" ;
			tableStr += "<td class='winbd033' style='width:130px;'>&nbsp;"+msgs[3]+"</td>" ;
			tableStr += "<td class='winbd033' style='width:130px;border-right-width:1px;'>&nbsp;"+msgs[4]+"</td>" ;
			tableStr += "</tr>" ;
		}
		tableStr += "</tbody></table>" ;
		divMsgInObj.innerHTML = tableStr ;
		divMsgObj.style.display = "" ;
		divMsgObj.style.left = event.clientX -200 ;
		divMsgObj.style.top =  event.clientY ;
	}
	function hideDivMsg () {
		var divMsgObj = document.getElementById ("divMsg") ;
		var divMsgInObj = document.getElementById ("divMsgIn") ;
		divMsgInObj.innerHTML = "" ;
		divMsgObj.style.display = "none" ;
	}

	//格式化金额    
	function fmoney(s, n){   
	    n = n > 0 && n <= 20 ? n : 2;   
	    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
	    var l = s.split(".")[0].split("").reverse(),   
	    r = s.split(".")[1];   
	    t = "";   
	    for(i = 0; i < l.length; i ++ )   {   
	    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
	    }   
	    return t.split("").reverse().join("") + "." + r;   
	}   
	
	function queryResultSummaryDetail(busiFlag,allCount,countFlag,bankCode,providerCode){
		$("input[name='busiFlag']").val(busiFlag);
		$("input[name='countFlag']").val(countFlag);
		$("input[name='allCount']").val(allCount);
		$("input[name='bankCode']").val(bankCode);
		$("input[name='providerCode']").val(providerCode);
		if(allCount > 0){
			document.resultForm.action="reconcile.resultsummary.do?method=initDetail";
			document.resultForm.submit();
		}else{
			alert("没有记录");
			return false;
		}
	}
	
</script>
