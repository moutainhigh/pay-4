<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/xdevelop.net/taglibs/page" prefix="page"%>
<%@ page import="com.pay.SupplementLoadService"%>
<%@ page import="com.pay.RemedyItemDTO"%>
<%@ page import="java.util.*"%>
<p>
补单结果显示
</p>
<%	
	//获取查询参数
	RemedyItemDTO remedyItemDTO = (RemedyItemDTO)request.getAttribute("remedyItemDTO");
	
	SupplementLoadService supplementLoadServiceImpl = (SupplementLoadService)request.getAttribute("supplementLoadService");

%>
<page:pager total="${ remedyItemListLength }" defaultPageSize="10">
<table id="remedyList" title="确认补单列表" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
  	<tr>
    	<th align="center">银行订单号</th>
    	<th align="center">银行名称</th>
    	<th align="center">交易状态</th>
    	<th align="center">金额</th>
  	</tr>
  	</thead>
<%	
	List list = supplementLoadServiceImpl.queryRemedyItemListByRemedyId(remedyItemDTO,index.intValue()-1,pageSize.intValue());
	request.setAttribute("list",list);
%> 
	<tbody>
  	<c:forEach var="r" items="${ list }" varStatus="status">
  	<tr>
	    <td>${ r.bank_order_id }</td>
	    <td>${ r.org_code }</td>
	    <td>
	    	<c:if test="${ r.status eq '0' }">创建</c:if>
	    	<c:if test="${ r.status eq '1' }">已提交银行</c:if>
	    	<c:if test="${ r.status eq '2' }">支付成功</c:if>
	    	<c:if test="${ r.status eq '3' }">支付失败</c:if>
	    </td>
	    <td>${ r.amount }</td>
	</tr>
  	</c:forEach>
  	</tbody>
  	<tr><td style="text-align:center" colspan="4"><page:navigator type="button"/></td></tr>
</table>
</page:pager> 

<input type="button" id="back" name="back" value="返回重新上传" onclick="goonSupplement()" />

<style type="text/css">
	.even{ background:#FFF38F;}
	.odd{ background:FFFFEE;}
</style>

<script type="text/javascript">

	function submitRemedy() {
		document.mainFrom.action = "supplement.upload.do?method=updateRemedyStatusByRemedyId";
		document.mainFrom.submit();
	}

	function goonSupplement(){
		window.location.href = "supplement.upload.do?method=entryUpload";
	}

	$(document).ready( function() { loads(); } ); 
	function loads(){
		
		$("p").attr("style","text-align:left;");
		$("td").append("&nbsp;");
		$("button").attr("width","50");

		$("#remedyList tbody tr:odd").addClass("odd");
		$("#remedyList tbody tr:even").addClass("even");
	}
</script>