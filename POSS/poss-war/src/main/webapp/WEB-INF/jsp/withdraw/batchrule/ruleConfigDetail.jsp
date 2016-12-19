<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批次详情</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#btn").click(function(){
					window.location="${ctx}/batchRuleConfigController.do?method=query";
				});
			});
		</script>
	</head>
	
	<body>
	<div style="text-align: left;position: absolute;left:50px;top:30px;">
		<h3>批次详情</h3><br>
		<h5>批次规则</h5>
		<hr noshade="noshade" color="black" width="70%"><br>
		批次名称：${ruleConfig.batchRuleDesc }<br><br>
		批次产生周期：${timeConfig.weekDayListStr}<br><br>
		批次产生时间：<br><br>
		<span style="padding-left: 40px;">
			<c:if test="${timeConfig.timeType==0}">
				定时间隔：从${timeConfig.startTimePoint} 至  ${timeConfig.endTimePoint}   
				每隔  ${timeConfig.timeSpan} 分钟执行一次
			</c:if>
			<c:if test="${timeConfig.timeType==1}">
				指定时间：${timeConfig.specialPoint}
			</c:if>
		</span>	
		<br><br>	
		规则生效时间：${ruleConfig.effectDateStr}<br><br>
		规则失效时间：${ruleConfig.lostEffectDateStr }<br><br>
		批次文件接收人员：${operators}<br><br>
		批次最大支持的交易笔数：${ruleConfig.maxOrderCounts}笔<br>
		<br>
		<input style="width: 100px;" id="btn" type="button" value="返回"/><br><br>
	</div>
	</body>
</html>