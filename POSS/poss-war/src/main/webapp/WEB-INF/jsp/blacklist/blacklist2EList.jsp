<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
					0: {sorter: false},
				 	10: {sorter: false}
				 }}); 

			 $(".tablesorter tbody tr").mouseover(function(){$(this).find("td").css({"background":"#cec"});})
			 .mouseout(function(){$(this).find("td").css({"background":"#fff"});}) ;         
		});
		function processEdit(id){
			location.href = "${ctx}/blacklist.do?method=updateBlacklist2EInit&hmdid=" + id;
		}
		function processView(id){
			location.href = "${ctx}/blacklist.do?method=blacklist2EDetail&hmdid=" + id;
		}
		function processDelete(id,name){
			var flag = confirm("确认删除法人代表姓名为: "+name+" 的机构名单吗？");
			if(flag){
				$('#infoLoadingDiv').dialog('open');
				$.ajax({
					type: "POST",
					url: "${ctx}/blacklist.do?method=deleteBlacklist&hmdid=" + id,
					data: "",
					success: function(result) {
						if(result=='success'){
							$('#infoLoadingDiv').dialog('close');
							query();
							$.fo.alert('删除成功！');
						}else{
							$('#infoLoadingDiv').dialog('close');
							$.fo.alert('删除失败！');
						}
					}
				});
			}
		}
		
		function processUpload(id,name){
			var flag = confirm("确认上传法人代表姓名为: "+name+" 的机构名单吗？");
			if(flag){
				$('#infoLoadingDiv').dialog('open');
				$.ajax({
					type: "POST",
					url: "${ctx}/blacklist.do?method=uploadBlacklist2E&hmdid=" + id,
					data: "",
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						query();
						$.fo.alert(result);
					}
				});
			}
		}
		
		function batchUpload(key,obj){
			btnDisabledSetTrue();
			if(0 == $("input[name=choose]:checked").size()){
				$.fo.alert('请您选择数据后再进行提交！');	
				btnDisabledSetFalse();
				return false;
			}
			
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#batchForm").serialize() + "&status=" + key ;
			$.ajax({
				type: "POST",
				url: "${ctx}/blacklist.do?method=batchUploadBlacklist2E",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					query();
					$.fo.alert(result);
				}
			});
		}
		
		function batchAudit(key,obj){
			btnDisabledSetTrue();
			if(0 == $("input[name=choose]:checked").size()){
				$.fo.alert('请您选择数据后再进行提交！');	
				btnDisabledSetFalse();
				return false;
			}
			var remark = $.trim($("#remark").val());
			if(!s_validateStrLength(remark,2,100)){
				$.fo.alert('批量操作备注最少2个字符,最大不超过50个汉字！');
				btnDisabledSetFalse();
				return false;
			}
			
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#batchForm").serialize() + "&status=" + key ;
			$.ajax({
				type: "POST",
				url: "${ctx}/blacklist.do?method=batchAudit",
				data: pars,
				success: function(result) {
					if (result == 'success') {
						$('#infoLoadingDiv').dialog('close');
						btnDisabledSetFalse();
						query();
						$.fo.alert('操作成功！');
					}else {
						$('#infoLoadingDiv').dialog('close');
						$.fo.alert('提交失败！');
						btnDisabledSetFalse();
					}
				}
			});
		}
		
		function btnDisabledSetTrue(){
			$("#btn1").attr("disabled",true);
			$("#btn2").attr("disabled",true);
			$("#btn3").attr("disabled",true);
		}

		function btnDisabledSetFalse(){
			$("#btn1").attr("disabled",false);
			$("#btn2").attr("disabled",false);
			$("#btn3").attr("disabled",false);
		}
		
		//id的全选或全部取消
		function selectAll() {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").attr("checked",true);
			} else {
				$("input[name='choose']").attr("checked",false);
			}
		}
		//取消一个选择单位，去掉全选框的勾
		function selectAllcheckBoxunchecked(obj){
		  if(!obj.checked){
			  $("#checkAll").attr("checked",false);
		  }
		}
	</script>
	
</head>
<body>
	<form id="batchForm">
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>
				<th>全选<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" /></th>
				<th>法人姓名</th>
				<th>法人身份证号</th>
				<th>手机号</th>
				<th>录黑途径编码</th>
				<th>银行卡号</th>
				<th>邮箱</th>
				<th>名单类型</th>
				<th>营业执照</th>
				<th>上传次数</th>
				<th>状态</th>
				<th colspan="2">操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="dto"> 
			<tr>
				<c:if test="${dto.status!=1}">
					<td><input type="checkbox" value="${dto.hmdid}" name="choose" onclick="selectAllcheckBoxunchecked(this);"/></td>
				</c:if>
				<c:if test="${dto.status==1}"><td></td></c:if>
				<td>${dto.xm}</td>
				<td>${dto.gmsfhm}</td>
				<td>${dto.sjhm}</td>
				<td>${dto.lhtj}</td>
				<td>${dto.yhkh}</td>
				<td>${dto.email}</td>
				<td>
					<c:if test="${dto.sjzt==1}">黑名单</c:if>
					<c:if test="${dto.sjzt==2}">灰名单</c:if>
				</td>
				<td>${dto.yyzzbh}</td>
				<td>${dto.uploadcount}</td>
				<td>
					<c:if test="${dto.status==0}">正常</c:if>
					<c:if test="${dto.status==2}">新建待审核</c:if>
					<c:if test="${dto.status==3}">修改待审核</c:if>
					<c:if test="${dto.status==4}">删除待审核</c:if>
					<c:if test="${dto.status==5}">上传待审核</c:if>
					<c:if test="${dto.status==1}">审核拒绝</c:if>
				</td>
				<td>
					<c:if test="${dto.status==0}">
						<a href="javascript:processEdit('${dto.hmdid}')">修改&nbsp;&nbsp;</a>
						<a href="javascript:processDelete('${dto.hmdid}','${dto.xm}')">删除&nbsp;&nbsp;</a>
						<a href="javascript:processUpload('${dto.hmdid}','${dto.xm}')">上传&nbsp;&nbsp;</a>
					</c:if>
					<c:if test="${dto.status==2 || dto.status==3 || dto.status==4 || dto.status==5}">
						<a href="${ctx}/blacklist.do?method=auditInit2E&hmdid=${dto.hmdid}"  class="nyroModal">审核</a>
					</c:if>
					<a href="javascript:processView('${dto.hmdid}')">详情&nbsp;&nbsp;</a>
				</td>
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	批量操作备注：<textarea rows="4" cols="45" name="remark" id="remark"></textarea>
	</form>
	<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	<table align="center">
		<tr>
			<td align="center">
				<input type="button" onclick="batchAudit(0)" name="submitBtn" id="btn1" value="批量审核通过" class="button4">
				<input type="button" onclick="batchAudit(1)" name="submitBtn" id="btn2" value="批量审核拒绝" class="button4">
				<input type="button" onclick="batchUpload(3)" name="submitBtn" id="btn3" value="批量上传" class="button4">
			</td>
		</tr>
	</table>
</body>

