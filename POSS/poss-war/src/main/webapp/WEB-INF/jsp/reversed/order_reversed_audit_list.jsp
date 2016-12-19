<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/xdevelop.net/taglibs/page" prefix="page"%>
<%@ page import="com.pay.OrderReversedService"%>
<%@ page import="com.pay.Reversed"%>
<%@ page import="java.util.*"%>
<p>
冲正审核
</p>

<form action="" method="post" name="mainFrom">
	<table border="1" width="80%">
		<tr>
			<td>系统交易号：<input type="text" id="src_order_serial_no" name="src_order_serial_no" value="${ reversed.src_order_serial_no }" /></td>
			<td>状态：
				<select id="status" name="status">
					<option value="">请选择</option>
					<option value="0">待审核</option>
					<option value="1">已冲正</option>
 					<option value="2">已催讨</option>
 					<option value="3">冲正失败</option>
 					<option value="4">驳回申请</option>
 					<option value="5">冲正异常</option>
 				</select>
			</td>
		</tr>
		<tr>
			<td>
				交易时间：
				<input class="Wdate" type="text" id="beginDate" name="beginDate" value="<fmt:formatDate value="${ reversed.beginDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})"> -
 				<input class="Wdate" type="text" id="endDate" name="endDate" value="<fmt:formatDate value="${ reversed.endDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDate\')}'})">
			</td>
			<td>
				<input type="button" id="selectBtn" name="selectBtn" value="查询" onclick="queryReversedList()" />
			</td>
		</tr>
	</table>

</form>
<%	
	//获取查询参数
	Reversed reversed = (Reversed)request.getAttribute("reversed");
	
	OrderReversedService orderReversedService = (OrderReversedService)request.getAttribute("orderReversedService");

%>

<div align="left">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="checkbox" id="checkAll" />全选
	<input type="button" id="checkRve" name="checkRve" value="取反" />
	<input type="button" id="btnAudit" name="btnAudit" value="通过选中交易" onclick="setValue('1')" />
	<input type="button" id="btnRevocat" name="btnRevocat" value="拒绝选中交易" onclick="setValue('4')" />
</div>


<page:pager total="${ size }" defaultPageSize="10">
<table id="tableList" title="手工补单审核" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
  	<tr>
  		<th align="center">选项</th>
    	<th align="center">交易类型</th>
    	<th align="center">交易金额</th>
    	<th align="center">原始交易订单号</th>
    	<th align="center">原始记账凭证号</th>
    	<th align="center">红冲记账凭证号</th>
    	<th align="center">冲正状态</th>
    	<th align="center">冲正发起时间</th>
    	<th align="center">冲正处理时间</th>
    	<th align="center">操作</th>
  	</tr>
  	</thead>
<%	
	List list = orderReversedService.queryReversedList(reversed,index.intValue()-1,pageSize.intValue());
	request.setAttribute("list",list);
%> 
  	<tbody>
  	<c:forEach var="r" items="${ list }" varStatus="status">
  	<tr>
  		<td><c:if test="${ r.status == '0' || r.status == '5' }"><input type="checkbox" name="items" value="${ r.id }" /></c:if></td>
	    <td>${ r.deal_type }</td>
	    <td>${ r.amount }</td>
	    <td>${ r.src_order_serial_no }</td>
	    <td>${ r.src_pe_serial_no }</td>
	    <td>${ r.new_pe_red_serial_no }</td>
	    <td>
	    	<c:if test="${ r.status == '0' }">待审核</c:if>
	    	<c:if test="${ r.status == '1' }">已冲正</c:if>
	    	<c:if test="${ r.status == '2' }">催讨中</c:if>
	    	<c:if test="${ r.status == '3' }">冲正失败</c:if>
	    	<c:if test="${ r.status == '4' }">驳回冲正</c:if>
	    	<c:if test="${ r.status == '5' }">冲正异常</c:if>
	    </td>
	    <td>
	    	<c:if test="${ r.create_time != null && r.create_time != '' }">
	    		<fmt:formatDate value="${ r.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
	    	</c:if>
	    </td>
	    <td>
	    	<c:if test="${ r.update_time != null && r.update_time != '' }">
	    		<fmt:formatDate value="${ r.update_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
	    	</c:if>
	    </td>
	    <td>
	    	<c:if test="${ r.status == '0' || r.status == '2' || r.status == '5' }">
	    		<input type="button" id="passBtn" name="passBtn" value="通过" onclick="setValueFunc('${ r.id }','1')" />
	    		<input type="button" id="refuseBtn" name="refuseBtn" value="拒绝" onclick="setValueFunc('${ r.id }','4')" />
	    	</c:if>
	    	<a href="#" onclick="queryOrderDetailsById('${ r.id }','${ r.src_order_serial_no }','${ r.deal_type }','${ r.payeeMemberCode }','${ r.payerMemberCode }')">详情</a>
	    </td>
	</tr>
	
  	</c:forEach>
  	</tbody>
  	<tr>
  		<td style="text-align:center" colspan="12">
  			<page:navigator type='button'/>
  		</td>
  	</tr>
</table>
</page:pager> 

<style type="text/css">
	.even{ background:#FFF38F;}
	.odd{ background:FFFFEE;}
</style>

<script type="text/javascript">

	/**撤销或者审核*/
	function setValue( status ){

		//var audit_desc = window.showModalDialog('reversed.do?method=entryReversedAuditDesc',null,'dialogWidth=300px;dialogHeight=200px;dialogTop=300px;dialogLeft=450px');
		var audit_desc = window.showModalDialog('reversed.entryReversedAuditDesc.do',null,'dialogWidth=300px;dialogHeight=200px;dialogTop=300px;dialogLeft=450px');
		if(!audit_desc || audit_desc == ''){
			return;
		}
		var ids = "";
		$("[name=items]:checkbox:checked").each(function(){
			ids += $(this).attr("value")+",";
		});
		if(ids.length < 2){
			alert("请先选择要处理的项");
			return false;
		}
		if(confirm("确定继续执行？")){
			//window.location.href = "reversed.do?method=processReversedItem&ids="+ids+"&status="+status+"&audit_desc="+encodeURIComponent(encodeURIComponent(audit_desc));
			window.location.href = "reversed.processReversedItem.do?ids="+ids+"&status="+status+"&audit_desc="+encodeURIComponent(encodeURIComponent(audit_desc));
		}
	}

	/**单独审核或者拒绝*/
	function setValueFunc(ids,status){
		//var audit_desc = window.showModalDialog('reversed.do?method=entryReversedAuditDesc',null,'dialogWidth=300px;dialogHeight=200px;dialogTop=300px;dialogLeft=450px');
		var audit_desc = window.showModalDialog('reversed.entryReversedAuditDesc.do',null,'dialogWidth=300px;dialogHeight=200px;dialogTop=300px;dialogLeft=450px');
		if(!audit_desc || audit_desc == ''){
			return;
		}

		if(confirm("确定继续执行？")){
			//window.location.href = "reversed.do?method=processReversedItem&ids="+ids+"&status="+status+"&audit_desc="+encodeURIComponent(encodeURIComponent(audit_desc));
			window.location.href = "reversed.processReversedItem.do?ids="+ids+"&status="+status+"&audit_desc="+encodeURIComponent(encodeURIComponent(audit_desc));
		}
	}

	/**查看详情*/
	function queryOrderDetailsById(id,src_order_serial_no,type,payeeMemberCode,payerMemberCode){
		//window.location.href = "reversed.do?method=queryOrderDetailsById&orderKy="+id+"&orderType="+type;
		window.location.href = "reversed.queryOrderDetailsById.do?id="+id+"&orderKy="+src_order_serial_no+"&orderType="+type+"&payeeMemberCode="+payeeMemberCode+"&payerMemberCode="+payerMemberCode;
	}

	/**查询冲正列表*/
	function queryReversedList(){
		//document.mainFrom.action = "reversed.do?method=queryReversedList";
		document.mainFrom.action = "reversed.queryReversedList.do";
		document.mainFrom.submit();
	}

	$(document).ready( function() { loads(); } ); 
	function loads(){

		$("#tableList tbody tr:odd").addClass("odd");
		$("#tableList tbody tr:even").addClass("even");
		
		$("p").attr("style","text-align:center;");
		$("td").append("&nbsp;");
		$("button").attr("style","h:120px;");
		$("#org_code").attr("style","width:160px;");
		$("#remedy_status").attr("style","width:100px;");

		/**保留查询条件*/
		$("#status>option[value='${ reversed.status }']").attr("selected","selected");


	

		/**控制复选框全选*/
		$("#checkAll").click(function(){
			$("[name=items]:checkbox").attr("checked",this.checked);
		});
		$("[name=items]:checkbox").click(function(){
			var flag = true;
			$("[name=items]:checkbox").each(function(){
				if(!this.checked)flag = false;
			});
			$("#checkAll").attr("checked",flag);
		});

		/**复选框取反*/
		$("#checkRve").click(function (){
			$("[name=items]:checkbox").each(function (){
				this.checked = !this.checked;
			});
		});
		
	}
</script>
<style type="text/css">
	.focus{
		border:1px; solid #f00;
		background: #fcc;
	}
</style>