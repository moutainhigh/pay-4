<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script type="text/javascript">
function processBack(){
	window.location.href="${ctx}/blacklist.do"; 
}
</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">个人名单信息详情</font></div>
		</td>
	</tr>
</table>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">

		<tr>
	    	<td align="right">姓名:</td>
	      	<td>
	      		<label>${dto.xm}</label>
	     	</td>
	     	<td align="right">身份证号:</td>
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
	    	<td align="right">支付人:</td>
	      	<td>
	      		<label>${dto.zfr}</label>
	     	</td>
	     	<td align="right">负面信息事件编码:</td>
	      	<td >
	      		<label>${dto.hmdsj}</label>
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
	    	<td align="right">标记时间:</td>
	      	<td>
	      		<label><fmt:formatDate value="${dto.bjsj}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
	     	</td>
	     	<td align="right">状态:</td>
	      	<td>
	      		<c:if test="${dto.status==0}"><label>正常</label></c:if>
				<c:if test="${dto.status==2}"><label>新建待审核</label></c:if>
				<c:if test="${dto.status==3}"><label>修改待审核</label></c:if>
				<c:if test="${dto.status==4}"><label>删除待审核</label></c:if>
				<c:if test="${dto.status==5}"><label>上传待审核</label></c:if>
				<c:if test="${dto.status==1}"><label>审核拒绝</label></c:if>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">上传次数:</td>
	      	<td colspan="3">
	      		<label>${dto.uploadcount}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right" >负面信息备注:</td>
	      	<td colspan="3">
	      		<label>${dto.hmdsjbz}</label>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right" >审核备注:</td>
	      	<td colspan="3">
	      		<label>${dto.remark}</label>
	     	</td>
	    </tr>
</table>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr>
			<th>序号</th>
			<th>时间</th>
			<th>操作员</th>
			<th>维护内容</th>
		</tr> 
	</thead> 
	<tbody>
		<c:forEach items="${logDtoList}" var="logDto" varStatus="status"> 
		<tr>
			<td><c:out value="${status.index +1}"/></td>
			<td><fmt:formatDate value="${logDto.logTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${logDto.logOperator}</td>
			<td>${logDto.logContent}</td>
		</tr>
		</c:forEach>
	</tbody> 
</table>
<br>
<br>

<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td>
			<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="processBack()">
			<span class="ui-icon ui-icon-arrowreturnthick-1-w"></span>&nbsp;返&nbsp;&nbsp;回&nbsp;</a>
		</td>
	</tr>
</table>
</body>