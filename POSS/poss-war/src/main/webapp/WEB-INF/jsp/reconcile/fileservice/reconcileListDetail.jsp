<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">对账结果详情</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">对账结果详情</h2>
<form action="" method="post" id="queryForm">
	<table class="border_all2" width="60%" border="0" cellspacing="0"
		cellpadding="1" align="center">
			<input type="hidden" name="batchNo" value="${batchNo}">	
	    	<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" >对账状态：</td>
			<td class="border_top_right4" align="left" >
				<select id="status" name="status" required="required">
					<option value="" selected>--请选择--</option>
					<option value="0">未对账</option>
					<option value="1">对账成功</option>
					<option value="2">对账失败</option>
					<option value="4">对账成功记账失败</option>
				</select>
				
			</td>
			<td class="border_top_right4" align="center"><input type="button"  name="querySubmit" value="查 询" class="button2" onclick="javascript:queryDetail();"></td>
	      <td align="center" class="border_top_right4">
	      	<input type="button"  name="butSubmit" value="下载" class="button2" onclick="javascript:download();">
	      </td>
	      <td align="center" class="border_top_right4">
	      	<input type="button" calss="button2" value="返回" onclick="history.go(-1)">
	      </td>
	    </tr>
   </table>
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>对账批次号</th>
			<th>渠道订单号</th>
			<th>referNum</th>
			<th>上传时间</th>
			<th>操作员</th>
			<th>对账状态</th>
			<th>备注</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${batchDetails}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${dto.batchNoDetail}</td>				
				<td>${dto.channelOrderNo}</td>		
				<td>${dto.merchTranRef}</td>		
				<td><fmt:formatDate value="${dto.createDate}" type="both"/></td>				
				<td>${dto.operator}</td>				
				<td>
					<c:if test="${dto.status==0}">未对账</c:if>
					<c:if test="${dto.status==1}">对账成功</c:if>
					<c:if test="${dto.status==2}">对账失败</c:if>
					
					<!-- add by davis.guo 2016-08-26 begin-->
					<c:if test="${dto.status==4}">对账成功,记账失败</c:if>
					<c:if test="${dto.status==8}">需要补单</c:if>
					<!-- add by davis.guo 2016-08-26 end-->
				</td>				
				<td>${dto.remark}</td>				
			</tr>
		</c:forEach>
	</tbody>
</table>
			
 <script type="text/javascript">

	function queryDetail(){
		var pars = $("#queryForm").serialize();
		window.location.href = 	"${ctx}/reconcile.do?method=queryReconcileDetail&"+pars;
	}
	function download(){
		var pars = $("#queryForm").serialize();
		window.location.href = 	"${ctx}/reconcile.do?method=download&"+pars;
	}
</script>
