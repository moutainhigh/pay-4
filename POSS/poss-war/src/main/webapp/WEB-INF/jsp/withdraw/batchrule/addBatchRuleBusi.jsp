<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>添加批次规则业务</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#ruleConfigId").change(function(){
					if($(this).val()=="-1"){
						$("#channelDiv").css("display","none");
					} else {
						var url="${ctx}/batchRuleConfigController.do";
						var data="method=getChannelList&ruleConfigId="+$(this).val();
						$.post(url,data,function(res){
							$("#channelDiv").css("display","");
							if(res!="no"){
								$("#channelChk").html(res);
								$("#submitBtn").removeAttr("disabled");
							} else {
								$("#channelChk").html("当前批次规则已关联所有的渠道");
								$("#submitBtn").attr("disabled","disabled");
							}
						});
					}
				});
				
				$("#allChannel").click(function(){
					if($(this).attr("checked")==true){
						$("#channelChk input").each(function(index){
							$(this).attr("checked",true);
						});
					}else{
						$("#channelChk input").each(function(index){
							$(this).attr("checked",false);
						});
					}
				});
				
				$("#submitBtn").click(function(){
					if(verificationInfo()){
						$("#frm").submit();
					}
				});
			});

			function verificationInfo(){
				var ruleConfigId=$("#ruleConfigId").val();
				if(ruleConfigId=="-1"){
					alert("请选择一个批次规则名称");
					return false;
				}

				var channelChoice=0;
				$("#channelChk input").each(function(index){
					if($(this).attr("checked")==true){
						channelChoice++;
					}
				});
				if(channelChoice==0){
					alert("请选择一个或多个业务类型");
					return false;
				}
				
				return true;
			}
		</script>
	</head>
	
	<body>
		<form id="frm" action="${ctx}/batchRuleConfigController.do?method=createBusi" method="post">
		<div style="text-align: left;position: absolute;left:50px;top:30px;">
			<h3>新增批次规则业务</h3><br>
			批次规则名称：
			<select name="ruleConfigId" id="ruleConfigId">
				<option value="-1">请选择</option>
				<c:forEach var="ruleConfig" items="${ruleConfigList}">
					<option value="${ruleConfig.sequenceId}">${ruleConfig.batchRuleDesc}</option>
				</c:forEach>
			</select>
			<br><br>
			<div id="channelDiv" style="display: none;">
				选择所有渠道：
				<input type="checkbox" name="allChannel" id="allChannel" value="3"><span style="color:red;">所有出批次的渠道</span><br><br>
				出款渠道:<br></br>
				<div id="channelChk">
				</div>
				<br><br>
				<input type="button" value="新增批次规则业务" id="submitBtn"></input><br>
			</div>
		</div>
		</form>
	</body>
</html>