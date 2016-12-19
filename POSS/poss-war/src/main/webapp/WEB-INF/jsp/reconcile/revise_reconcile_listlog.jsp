<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr> 
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">调账日志列表</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="reconcile.reviseReconcile.do?method=queryReviseReconcile" method="post" name="mainfrom">
<input type="hidden" name="actionSource" value="log" />
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td align="right" class="border_top_right4" colspan="1"  >选择交易日期：</td>
      <td class="border_top_right4" colspan="3" >
      	<input class="Wdate" type="text" name="startTime"  value='<fmt:formatDate value="${webQueryReconcileDTO.startTime}" type="date"/>'    onClick="WdatePicker()">
       	 ～
		<input class="Wdate" type="text" name="endTime"   value='<fmt:formatDate value="${webQueryReconcileDTO.endTime}" type="date"/>'  onClick="WdatePicker()">
      </td>
    </tr>
  
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >选择银行：</td>
      <td class="border_top_right4" >
        	<select name="bankCode" >
        		<option value="" selected="selected">所有</option>
        	     <option value="CCB" >建设银行</option>
         		 <option value="UnionPay" >银联</option>
        	</select>
      </td>
      
      <td class=border_top_right4 align="right" >服务代码：</td>
      <td class="border_top_right4"  align="left"  >
        	<select id="providerCodeId" name="providerCode" >
        	<option value="" selected="selected">所有</option>
        			<option value="001">建设银行B2B网关</option>
        			<option value="002">银联默认网关</option>
        	</select>
      </td>
      
    </tr>
    
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >调账状态：</td>
      <td class="border_top_right4" >
        	<select id="adjustStatusId" name="adjustStatus" >
        			<option value="2">调账申请</option>
        			<option value="3">调账审核通过</option>
        			<option value="4">调账审核失败</option>
        	</select>
      </td>
      
      <td class=border_top_right4 align="right" >错账方：</td>
      <td class="border_top_right4" align="left" >
        	<select id="busiFlagId" name="busiFlag" >
        			<option value="" selected="selected">所有</option>
        			<option value="200">银行</option>
        			<option value="210">系统</option>
        	</select>
      </td>
      
    </tr>
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" >银行订单号：</td>
      <td class="border_top_right4" >
	        <input type="text"  name="accountSeq" value="${webQueryReconcileDTO.accountSeq }"  />
      </td>
      
         <td align="center" class="border_top_right4" colspan="2">
     	 <a class="s03" href="#" onclick="javascript:submitByHref();"><img
			src="./images/query.jpg" border="0"> </a>
      </td>
    </tr>
  </table>
 </form>
 <form action="" method="post" name="fromapply">
 	<input type="hidden" id="acceptKy" name="acceptKy"   />
 	<input type="hidden" id="id" name="id"   />
 	<input type="hidden" id="id" name="actionSource"  value="log"  />
 </form>
<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1" > 
		<td class="border_top_right4"  colspan="9" align="right"><input id="download" type="button" onclick="downloadExcel();" value="下载EXCEL" class="button5" /></td>
	</tr>
	<tr class="trbgcolorForTitle" align="center" valign="middle" >
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">序号</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">银行名称</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">服务代码</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">银行订单号</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">交易金额</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">交易日期</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">错账方</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">调账状态</font></a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font color="#FFFFFF">操作</font></a></td>
	</tr>


	<c:forEach items="${page.result}" var="reconcileResultFunds" varStatus="status">
		<tr align="center" class="trForContent1"  valign="middle">
			<td class="border_top_right4"><c:out value="${status.count}" /></td>
			<td class="border_top_right4">${reconcileResultFunds.bankCode}</td>
			<td class="border_top_right4">${reconcileResultFunds.providerCode}</td>
			<td class="border_top_right4">${reconcileResultFunds.accountSeq}</td>
			<td class="border_top_right4">
				<fmt:formatNumber value="${reconcileResultFunds.accountAmount}" pattern="#,##0.00"/>
			</td>
			<td class="border_top_right4">
			<fmt:formatDate value="${reconcileResultFunds.busiTime}" type="date"/>
			</td>
			<td class="border_top_right4">
				<c:if test="${'200' eq reconcileResultFunds.busiFlag   }">银行</c:if>
				<c:if test="${'220' eq reconcileResultFunds.busiFlag}">
					<c:if test="${'200' eq webQueryReconcileDTO.busiFlag }">银行</c:if>
					<c:if test="${'210' eq webQueryReconcileDTO.busiFlag }">系统</c:if>
				</c:if>
				<c:if test="${'210' eq reconcileResultFunds.busiFlag }">系统</c:if>
				<c:if test="${'0' eq reconcileResultFunds.busiFlag}">所有</c:if>
			</td>
			<td class="border_top_right4">
				<c:if test="${'1' eq reconcileResultFunds.adjustStatus}">
						未调账
				</c:if>
				<c:if test="${'2' eq reconcileResultFunds.adjustStatus}">
						调账申请
				</c:if>
				<c:if test="${'3' eq reconcileResultFunds.adjustStatus}">
						调账审核通过
				</c:if>
				<c:if test="${'4' eq reconcileResultFunds.adjustStatus}">
					调账审核失败
				</c:if>
				</td>
			<td class="border_top_right4">
						<a href="#" onclick="queryReconcileLog('${reconcileResultFunds.id}','${reconcileResultFunds.acceptKy}','${reconcileResultFunds.adjustStatus}');" >
						查看日志</a>
					</td>
		</tr>
	</c:forEach>
</table>
<script type="text/javascript">
	function queryReconcileLog(id,acceptKy,adjustStatus){
		$("input[name=id]").val(id);
		$("input[name=acceptKy]").val(acceptKy);
		var url = "reconcile.reviseReconcile.do?method=queryReconcileAcceptMain";
		if("1" == adjustStatus){
			url = "reconcile.reviseReconcile.do?method=queryReviseReconcileById";
		}
		document.fromapply.action=url;
		document.fromapply.submit();
	}
	//默认填充下拉列表的值
	$(function(){
			<c:if test="${not empty webQueryReconcileDTO.bankCode}">
				$("#bankCodeId").val("${webQueryReconcileDTO.bankCode}");
			</c:if>
			<c:if test="${not empty webQueryReconcileDTO.providerCode}">
				$("#providerCodeId").val("${webQueryReconcileDTO.providerCode}");
			</c:if>
			<c:if test="${not empty webQueryReconcileDTO.adjustStatus}">
				$("#adjustStatusId").val("${webQueryReconcileDTO.adjustStatus}");
			</c:if>
			<c:if test="${not empty webQueryReconcileDTO.busiFlag}">
				$("#busiFlagId").val("${webQueryReconcileDTO.busiFlag}");
			</c:if>
		})
		function submitByHref(){
		document.mainfrom.submit();
	}


	function changeBankInfo(obj){
		if(obj.value == 'UnionPay'){
			$("#providerId").hide();
			$("#providerId").val('002');
		}else{
			$("#providerId").show();
			$("#providerId").val('001');
		}
	}
</script>