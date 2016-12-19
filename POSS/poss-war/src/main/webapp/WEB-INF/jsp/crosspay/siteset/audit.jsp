<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">域名审核</h2>
	<form action="" id="checkStatusForm">
	<input name="id" id="id" type='hidden' value="${id }"/>
	
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" width="50%">商户号:</td>
			<td class="border_top_right4" ><input name="partnerId" id="partnerId" readonly="readonly" value="${partnerId}"/></td>
			</tr>
			<tr class="trForContent1"><td class="border_top_right4"  align="right">域     名:</td>
			<td class="border_top_right4" ><input name="siteId" id="siteId" value="${siteId}" readonly="readonly"/></td>
			</tr>
			<tr class="trForContent1">
			<td class="border_top_right4"  align="right">审     核:</td>
			<td class="border_top_right4" >
				<select id="status" name="status">
		      		<option value="">请选择</option>
		      		<option value='0' <c:if test="${status == '0'}">selected</c:if>>冻结</option>
					<option value='1' <c:if test="${status == '1'}">selected</c:if>>正常</option>
					<option value='3' <c:if test="${status == '3'}">selected</c:if>>审核未通过</option>
					<option value='4' <c:if test="${status == '4'}">selected</c:if>>已删除</option>
				<%-- 	<option value='5' <c:if test="${map.status == '5'}">selected</c:if>>系统审核未通过</option>
					<option value='6' <c:if test="${map.status == '6'}">selected</c:if>>系统审核通过</option> --%>
	      		</select>
	      	</td>
	      	</tr>
			<tr class="trForContent1">
			<td class="border_top_right4"  align="right" style="width: 200px;">网站品类:</td>
				<td class="border_top_right4" >
					<input name="category" id="category">
				</td>
			</tr>
	      	<tr class="trForContent1">
	      		<td class="border_top_right4"  align="right">是否送往Credorax:</td>
	      		<td class="border_top_right4" >
					<select id="sendCredorax" name="sendCredorax">
						<option value="N">否</option>
						<option value="Y">是</option>
					</select>
				</td>
	      	</tr>
			<tr class="trForContent1">
			<td class="border_top_right4"  align="right"> 备注:</td>
			<td class="border_top_right4" ><textarea cols="20" rows="6" id="remark" name="remark" ></textarea></td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4"  colspan="2" align="center">
					<input type="button"  value="确定"  onclick="checkStatus();">
					<input type="button" value="返回"  onclick="toIndex();">
				</td>
			</tr>
	</table>
	</form>
	
<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
	 
  <script type="text/javascript">
  
	function toIndex(){
		window.location.href="${ctx}/crosspay/sitesetAudit.do";
	}
	
	 function checkStatus() {
			
			var pars = $("#checkStatusForm").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/crosspay/sitesetAudit.do?method=updateSiteSetStatus",
				data: pars,
				success: function(result) {
					if(result == 1){
						alert("审核成功！");
						window.location.href="${ctx}/crosspay/sitesetAudit.do";
					}else{
						alert("审核失败!");
						window.location.href="${ctx}/crosspay/sitesetAudit.do";
					}
				}
			});
	}
  </script>