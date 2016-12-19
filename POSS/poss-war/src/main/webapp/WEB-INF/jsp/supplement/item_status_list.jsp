<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/xdevelop.net/taglibs/page" prefix="page"%>
<%@ page import="com.pay.SupplementLoadService"%>
<%@ page import="com.pay.RemedyItemDTO"%>
<%@ page import="java.util.*"%>
<form action="" method="post" name="mainFrom" enctype="multipart/form-data">
 	<input type="hidden" id="remedy_id" name="remedy_id" value="${ remedyItemDTO.remedy_id }" />
</form>
<p>
补单总笔数：<c:out value="${ listSize + noRequiredCount }"></c:out>
<br/>
核对实际正确笔数：<c:out value="${ requiredCount }"></c:out>
<br/>
核对实际错误笔数：<c:out value="${ errerCount }"></c:out>
<br/>
核对无需补单笔数：<c:out value="${ noRequiredCount }"></c:out>
</p>

<%	
	//获取查询参数
	RemedyItemDTO remedyItemDTO = (RemedyItemDTO)request.getAttribute("remedyItemDTO");
	
	SupplementLoadService supplementLoadServiceImpl = (SupplementLoadService)request.getAttribute("supplementLoadService");

%>
<page:pager total="${ listSize }" defaultPageSize="10">
<table id="errer" title="核对实际错误笔数" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
  	<tr>
    	<th align="center">银行订单号</th>
    	<th align="center">银行名称</th>
    	<th align="center">交易状态</th>
    	<th align="center">金额</th>
    	<th align="center">结果</th>
  	</tr>
  	</thead>
<%	
	List list = supplementLoadServiceImpl.queryRemedyItemAndCheck(remedyItemDTO,index.intValue()-1,pageSize.intValue());
	request.setAttribute("list",list);
%> 
	<tbody>
  	<c:forEach var="r" items="${ list }" varStatus="status">
  	<tr>
	    <td>${ r.bank_order_id }</td>
	    <td>${ r.exRemedyItemDTO.org_code }</td>
	    <td>
	    	<c:if test="${ r.exRemedyItemDTO.status eq '0' }">创建</c:if>
	    	<c:if test="${ r.exRemedyItemDTO.status eq '1' }">已提交银行</c:if>
	    	<c:if test="${ r.exRemedyItemDTO.status eq '2' }">支付成功</c:if>
	    	<c:if test="${ r.exRemedyItemDTO.status eq '3' }">支付失败</c:if>
	    </td>
	    <td>${ r.amount }</td>
	    <td>
	    	<font color="red">
	    		<c:if test="${ r.errreason eq '2' }">日期格式错误</c:if>
		    	<c:if test="${ r.errreason eq '3' }">系统多笔重复记录</c:if>
		    	<c:if test="${ r.errreason eq '4' }">无符合条件的记录</c:if>
		    	<c:if test="${ r.errreason eq '5' }">交易未成功金额不符</c:if>
		    	<c:if test="${ r.errreason eq '6' }">订单状态为支付失败</c:if>
		    	<c:if test="${ r.errreason eq '9' }">交易已成功金额不符</c:if>
		    	<c:if test="${ r.errreason eq '10' }">其它错误</c:if>
	    	</font>
	    	<font color="green">
		    	<c:if test="${ r.errreason eq '8' }">无需补单</c:if>
	    	</font>
	    	<font color="blue">
		    	<c:if test="${ r.errreason eq '7' }">可以补单</c:if>
	    	</font>
	    </td>
	</tr>
  	</c:forEach>
  	</tbody>
  	<tr><td style="text-align:center" colspan="5"><page:navigator type='button'/></td></tr>
</table>
</page:pager> 

<input type="button" id="backBtn" name="backBtn" value="返回重新上传" onclick="goonSupplement()" />
<c:if test="${ requiredCount > 0 }">
	<input type="button" id="submitBtn" name="submitBtn" value="确认补单" onclick="submitRemedy()" />
</c:if>

<style type="text/css">
	.even{ background:#FFF38F;}
	.odd{ background:FFFFEE;}
</style>

<script type="text/javascript">

	function submitRemedy() {
		//alert(("#remedy_id").val())
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

		$("#errer tbody tr:odd").addClass("odd");
		$("#errer tbody tr:even").addClass("even");
	}
</script>