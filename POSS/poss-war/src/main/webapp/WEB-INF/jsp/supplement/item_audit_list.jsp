<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/xdevelop.net/taglibs/page" prefix="page"%>
<%@ page import="com.pay.SupplementLoadService"%>
<%@ page import="com.pay.RemedyItemDTO"%>
<%@ page import="java.util.*"%>
<p>
手工补单审核
</p>

<form action="" method="post" name="mainFrom">
 	<input type="hidden" id="remedy_id" name="remedy_id" value="" />
 	<table>
 		<tr>
 			<td>银行名称:</td>
 			<td>
 				<select id="org_code" name="org_code">
 					<option value="">-请选择-</option>
 					<c:forEach var="o" items="${ orgList }">
 						<option value="${ o.orgCode }">${ o.orgName }</option>
 					</c:forEach>
 				</select>
 			</td>
 			<td>银行订单号:</td>
 			<td><input type="text" id="bank_order_id" name="bank_order_id" value="${ remedyItemDTO.bank_order_id }" /></td>
 		</tr>
 		<tr>
 			<td>时间：</td>
 			<td>
 				<input class="Wdate" type="text" id="beginDate" name="beginDate" value="<fmt:formatDate value="${ remedyItemDTO.beginDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})"> -
 				<input class="Wdate" type="text" id="endDate" name="endDate" value="<fmt:formatDate value="${ remedyItemDTO.endDate }" pattern="yyyy-MM-dd"/>" onClick="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDate\')}'})">
 			</td>
 			<td>状态：</td>
 			<td>
 				<select id="remedy_status" name="remedy_status">
 					<option value="2">已提交</option>
 					<option value="3">已审核</option>
 					<option value="4">被撤销</option>
 				</select>
 			</td>
 		</tr>
 		<tr>
 			<td align="center" colspan="4">
 				<input type="button" id="btnQuery" name="btnQuery" value="查询" onclick="queryRemedyItem()"size="100" />
 			</td>
 		</tr>
 		
 	</table>

</form>
<%	
	//获取查询参数
	RemedyItemDTO remedyItemDTO = (RemedyItemDTO)request.getAttribute("remedyItemDTO");
	
SupplementLoadService supplementLoadServiceImpl = (SupplementLoadService)request.getAttribute("supplementLoadService");

%>
<page:pager total="${ listSize }" defaultPageSize="10">
<table id="tableList" title="手工补单审核" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
  	<tr>
  		<th align="center">
  			选项
  		</th>
    	<th align="center">银行订单号</th>
    	<th align="center">连接名称</th>
    	<th align="center">银行名称</th>
    	<th align="center">金额</th>
    	<th align="center">交易时间</th>
    	<th align="center">补单申请时间</th>
    	<th align="center">补单审核时间</th>
    	<th align="center">状态</th>
  	</tr>
  	</thead>
<%	
	List list = supplementLoadServiceImpl.queryRemedyItemListByRemedyId(remedyItemDTO,index.intValue()-1,pageSize.intValue());
	request.setAttribute("list",list);
%> 
  	<tbody>
  	<c:forEach var="r" items="${ list }" varStatus="status">
  	<tr>
  		<td>
  			<c:if test="${ r.remedy_status == '2' }"><input type="checkbox" name="items" value="${ r.remedy_order_id }" /></c:if>
  		</td>
	    <td>${ r.bank_order_id }</td>
	    <td>${ r.alias }</td>
	    <td>${ r.org_name }</td>
	    <td>${ r.amount }</td>
	    <td>
	    	<c:if test="${ r.order_date != null && r.order_date != '' }">
	    		<fmt:formatDate value="${ r.order_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
	    	</c:if>
	    </td>
	    <td>
	    	<c:if test="${ r.request_date != null && r.request_date != '' }">
	    		<fmt:formatDate value="${ r.request_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
	    	</c:if>
	    </td>
	    <td>
	    	<c:if test="${ r.audit_date != null && r.audit_date != '' }">
	    		<fmt:formatDate value="${ r.audit_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
	    	</c:if>
	    </td>
	    <td>
	    	<c:if test="${ r.remedy_status == '2' }">已提交</c:if>
	    	<c:if test="${ r.remedy_status == '3' }">已审核</c:if>
	    	<c:if test="${ r.remedy_status == '4' }">被撤销</c:if>
	    </td>
	</tr>
	
  	</c:forEach>
  	</tbody>
  	<tr>
  		<td>
  			<input type="checkbox" id="checkAll" />全选
  			<input type="button" id="checkRve" name="checkRve" value="取反" />
  		</td>
  		<td style="text-align:center" colspan="8">
  			<page:navigator type='button'/>
  		</td>
  	</tr>
</table>
 </page:pager> 
<input type="button" id="btnRevocat" name="btnRevocat" value="撤销" onclick="setValue('4')" />
<input type="button" id="btnAudit" name="btnAudit" value="审核" onclick="setValue('3')" />

<style type="text/css">
	.even{ background:#FFF38F;}
	.odd{ background:FFFFEE;}
</style>

<script type="text/javascript">

	/**撤销或者审核*/
	function setValue( status ){
		var ids = "";
		$("[name=items]:checkbox:checked").each(function(){
			ids += $(this).attr("value")+",";
		});
		if(ids.length < 2){
			alert("请先选择要处理的项");
			return false;
		}
		if(confirm("确定继续执行？")){
			window.location.href = "supplement.upload.do?method=processRemedyItem&ids="+ids+"&status="+status;
		}
	}

	function queryRemedyItem() {
		document.mainFrom.action = "supplement.upload.do?method=queryRemedyItemListByExample";
		document.mainFrom.submit();
	}

	$(document).ready( function() { loads(); } ); 
	function loads(){

		$("#tableList").tablesorter({
			 
		});  

		$("#tableList tbody tr:odd").addClass("odd");
		$("#tableList tbody tr:even").addClass("even");
		
		$("p").attr("style","text-align:center;");
		$("td").append("&nbsp;");
		$("button").attr("style","h:120px;");
		$("#org_code").attr("style","width:160px;");
		$("#remedy_status").attr("style","width:100px;");

		/**保留查询条件*/
		$("#org_code>option[value='${ remedyItemDTO.org_code }']").attr("selected","selected");
		$("#remedy_status>option[value='${ remedyItemDTO.remedy_status }']").attr("selected","selected");


	

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