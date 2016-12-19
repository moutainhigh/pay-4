<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class='panel_title'>域名审核</h2>
	<form action="" id="checkStatusForm">
	<input name="id" id="id" type='hidden' value="${siteIds}"/>
	<input name="partnerId" id="partnerId" type='hidden' value="${map.partnerId}"/>
	<input name="siteId" id="siteId" type='hidden' value="${map.siteId}"/>
	<input name="status" id="status" type='hidden' value="${map.status}"/>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" width="50%">本次批量操作数量:</td>
			<td class="border_top_right4" >
				<input name="batchCount" id="batchCount" readonly="readonly" value="${batchCount}"/></td>
			</tr>
			<tr class="trForContent1">
			<td class="border_top_right4"  align="right">审     核:</td>
			<td class="border_top_right4" >
				<select id="status" name="status1">
						<option value="">请选择</option>
		      			<option value='0'>冻结</option>
						<option value='1'>正常</option>
				<!-- 		<option value='2'>待审核</option> -->
						<option value='3'>审核未通过</option>
						<option value='4'>已删除</option>
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
					<select id="sendCredorax" name="sendCredorax1">
						<option value="N">否</option>
						<option value="Y">是</option>
					</select>
				</td>
	      	</tr>
			<tr class="trForContent1">
			<td class="border_top_right4"  align="right"> 备注:</td>
			<td class="border_top_right4" ><textarea cols="20" rows="6" id="remark" name="remark"></textarea></td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4"  colspan="2" align="center">
					<input type="button"  value="确  定"  onclick="reviewedStatus();">
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
			var pars = $("#checkStatusForm").serialize();
		window.location.href="${ctx}/crosspay/sitesetAudit.do?"+pars;
	}
	
	 function reviewedStatus() {
			 var pars = $("#checkStatusForm").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/crosspay/sitesetAudit.do?method=updateBacthSiteSetStatus",
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