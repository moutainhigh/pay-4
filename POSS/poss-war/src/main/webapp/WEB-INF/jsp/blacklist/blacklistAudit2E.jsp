<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<script type="text/javascript">
<!--
	$(function(){
		$("#processPass").click(function(){
			var pars = $("#blacklistAudit").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/blacklist.do?method=audit&status=0",
				data: pars,
				success: function(result) {
					if (result == 'success') {
						$('#closeBtn').click();
						//重新载入列表
						query();
					} else {
						alert("提交失败!");
					}
				}
			});
		});
		
		$("#processReject").click(function(){
			var pars = $("#blacklistAudit").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/blacklist.do?method=audit&status=1",
				data: pars,
				success: function(result) {
					if (result == 'success') {
						$('#closeBtn').click();
						//重新载入列表
						query();
					} else {
						alert("提交失败!");
					}
				}
			});
		});
	});
//-->
</script>

<div align="center"><font class="titletext">审核企业名单信息</font></div>
<form method="post" id="blacklistAudit">
<input type="hidden" name="hmdid" value="${dto.hmdid}"/>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
  		<tr>
	    	<td align="right">企业法人代表姓名:</td>
	      	<td>
	      		<label>${dto.xm}</label>
	     	</td>
	     	<td align="right">法人身份证号:</td>
	      	<td>
	      		<label>${dto.gmsfhm}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">发生地区编码:</td>
	      	<td>
	      		<label>${dto.fsdq}</label>
	     	</td>
	     	<td align="right">录黑途径编码:</td>
	      	<td>
	      		<label>${dto.lhtj}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">手机号码:</td>
	      	<td>
	      		<label>${dto.sjhm}</label>
	     	</td>
	     	<td align="right">固定电话:</td>
	      	<td>
	      		<label>${dto.gddh}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">银行卡号:</td>
	      	<td>
	      		<label>${dto.yhkh}</label>
	     	</td>
	     	<td align="right">开户行:</td>
	      	<td>
	      		<label>${dto.khh}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">机构名称:</td>
	      	<td>
	      		<label>${dto.jgmc}</label>
	     	</td>
	     	<td align="right">营业执照编号:</td>
	      	<td>
	      		<label>${dto.yyzzbh}</label>
	     	</td>
	    </tr>
	     <tr>
	    	<td align="right">IP地址:</td>
	      	<td>
	      		<label>${dto.ip}</label>
	     	</td>
	     	<td align="right">MAC地址:</td>
	      	<td>
	      		<label>${dto.mac}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">邮箱:</td>
	      	<td>
	      		<label>${dto.email}</label>
	     	</td>
	    	<td align="right">地址:</td>
	      	<td>
	      		<label>${dto.dz}</label>
	     	</td>
	    </tr>
	    <tr>
	     	<td align="right">ICP编号:</td>
	      	<td>
	      		<label>${dto.icp}</label>
	     	</td>
	     	<td align="right">ICP备案人:</td>
	      	<td>
	      		<label>${dto.icpbar}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">URL地址:</td>
	      	<td>
	      		<label>${dto.urldz}</label>
	     	</td>
	     	<td align="right">URL跳转地址:</td>
	      	<td>
	      		<label>${dto.urltzdz}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">标记时间:</td>
	      	<td>
	      		<label><fmt:formatDate value="${dto.bjsj}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
	     	</td>
	     	<td class="textRight">组织机构编号：</td>
			<td class="textLeft">
				<label>${dto.zzjgdm}</label>
			</td>
	    </tr>
	    <tr>
	    	<td class="textRight">名单类型：</td>
			<td class="textLeft">
				<c:if test="${dto.sjzt==1}">黑名单</c:if>
				<c:if test="${dto.sjzt==2}">灰名单</c:if>
			</td>
			<td class="textRight">业务类别：</td>
			<td class="textLeft">
				<c:if test="${dto.ywlb=='01'}">个人互联网支付</c:if>
				<c:if test="${dto.ywlb=='02'}">机构互联网支付</c:if>
				<c:if test="${dto.ywlb=='03'}">移动支付</c:if>
				<c:if test="${dto.ywlb=='04'}">POSS收单</c:if>
				<c:if test="${dto.ywlb=='05'}">预付费卡</c:if>
				<c:if test="${dto.ywlb=='06'}">语音支付</c:if>
				<c:if test="${dto.ywlb=='00'}">其他</c:if>
			</td>
	    </tr>
	    <tr>
	     	<td align="right">负面信息事件编码:</td>
	      	<td >
	      		<label>${dto.hmdsj}</label>
	     	</td>
	     	<td align="right">支付人:</td>
	      	<td>
	      		<label>${dto.zfr}</label>
	     	</td>
	    </tr>	
	    <tr>
	   	  	<td class="textRight">负面信息事件备注：</td>
	      	<td class="textLeft" colspan="3">
	      		<label>${dto.hmdsjbz}</label>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="right" >审核备注:</td>
	      	<td colspan="3">
	      		<input type="text" name="remark" id="remark" value="${dto.remark}" maxlength="100"/>
	     	</td>
	    </tr>
	    <tr>
	      <td align="center" colspan="4">
	      	<input type="button" value="审核通过" id="processPass"/>&nbsp;&nbsp;&nbsp;&nbsp;
	      	<input type="button" value="审核拒绝" id="processReject"/>&nbsp;&nbsp;&nbsp;&nbsp;
	      	<input type="button" value="取 消" class="nyroModalClose" id="closeBtn"  />
	      </td>
	    </tr>
  </table>
</form>
 