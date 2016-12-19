<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<html>
<head>
	<title>编辑业务类型</title>
	<link rel="stylesheet" href="./css/main.css" />
	<style type="text/css">
	.read{
		color:'gray';
		background-color: '#ccc';
	}
	</style>
	<script type="text/javascript">
	$("#processSave").click(function(){
		if(!validate())
			return ;

		var pars = $("#businessForm").serialize();
		
		$.ajax({
			type: "POST",
			url: "${ctx}/if_comp/if_config_action.do?method=doSave",
			data: pars,
			success: function(result) {
				if (result == 'success') {
					$('#closeBtn').click();
					//操作成功,重新载入列表
					searchBusiness();
				} else {
					alert("操作失败!");
				}
			}
		});
	});
	
	$("#processFlush").click(function(){

		var pars = $("#businessForm").serialize();
		
		$.ajax({
			type: "POST",
			url: "${ctx}/if_comp/if_config_action.do?method=doFlush",
			data: pars,
			success: function(result) {
				if (result == 'success') {
					$('#closeBtn').click();
				} else {
					alert("操作失败!");
				}
			}
		});
	});
	
	function validate() {
		//业务名称
		var key = $.trim($("#key").val());
		var value = $.trim($("#value").val())
		if(key == ''){
			alert("键为必填项！");
			return false;
		}
		if(value == ''){
			alert("值为必填项！")
			return false;
		}
		return true;
	}
	</script>
</head>

<body>
<div>
	<div>
	<c:choose>
       <c:when test="${opp eq 'add'}">
             <span>新增动态配置</span>
       </c:when>
       <c:when test="${opp eq 'modify'}">
             <span>更新动态配置</span>
       </c:when>
       <c:when test="${opp eq 'flush'}">
             <span>刷新缓存</span>
       </c:when>
       <c:otherwise>
             <span>查看动态配置</span>
       </c:otherwise>
	</c:choose>
	</div>
	<form id="businessForm" method="post">
		<input type="hidden"  id="confId" name="confId" value="${conf.id}"/>
		<table class="inputTable" width="500" border="0" cellspacing="0" cellpadding="3" >
			<tr>
				<td sytle="width:30%">分组编码</td>
				<td sytle="width:70%">
				<c:choose>
			       <c:when test="${empty conf.id}">
			            <input style="width:90%" type="text" name="groupCode" id="groupCode" value="${conf.groupCode }" />
			       </c:when>
			       <c:otherwise>
			            <input style="width:90%" type="text" name="groupCode" id="groupCode" value="${conf.groupCode }" class="read" readonly="readonly" />
			       </c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr >
				<td ><span style='color:red'>*</span>键</td>
				<td>
				<c:choose>
			       <c:when test="${empty conf.id}">
			           <input style="width:90%" type="text" name="key" id="key" value="${conf.key }"/>
			       </c:when>
			       <c:otherwise>
			           <input style="width:90%" type="text" name="key" id="key" value="${conf.key }" class="read" readonly="readonly"/>
			       </c:otherwise>
				</c:choose>
				</td>
				</tr>
			<tr >
				<td ><span style='color:red'>*</span>值</td>
				<td>
					<input style="width:90%" type="text" name="value" id="value" value="${conf.value }"/>
				 </td>
			</tr>
			<tr >
				<td >缓存中的值</td>
				<td>
					<span>${cacheValue}</span>
				 </td>
			</tr>
			<tr >
				<td >生效时间</td>
				<td >
					<span><fmt:formatDate value="${conf.validDate}"  pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
				</td>
			</tr>
			<tr >
				<td>失效时间</td>
				<td >
					<span><fmt:formatDate value="${conf.invalidDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
				</td>
			</tr>
			<tr >
				<td>状态</td>
				<td >
				<c:choose>
			       <c:when test="${conf.state eq 0}">
			             <span>无效</span>
			       </c:when>
			       <c:otherwise>
			             <span>有效</span>
			       </c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>
		
		<div style="padding-top: 10px;">
		<c:if test="${opp eq 'modify' }">
			<input type="button" class="button01" value="保 存" id="processSave"/>&nbsp;&nbsp;&nbsp;&nbsp;
			
		</c:if>
		<c:if test="${opp eq 'flush' }">
			<input type="button" class="button01" value="刷新缓存" id="processFlush"/>&nbsp;&nbsp;&nbsp;&nbsp;
			
		</c:if>
			<input type="button" class="nyroModalClose button01" id="closeBtn" value="关 闭" />
		</div>
	</form>
</div>
</body>
</html>