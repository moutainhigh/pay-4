<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>修改批次规则</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$(document).ready(function(){
					$("#btn").click(function(){
						window.location="${ctx}/batchRuleConfigController.do?method=query";
					});
				});

				$("#timeType").change(function(){
					var timeType=$("#timeType").val();
					if(timeType=="0"){
						$("#dsjg").css("display","");
						$("#zdsj").css("display","none");
					} else if(timeType=="1"){
						$("#dsjg").css("display","none");
						$("#zdsj").css("display","");
					} else {
						$("#dsjg").css("display","none");
						$("#zdsj").css("display","none");
					}
				});

				$("#toBankPage").click(function(){
					location.href="${ctx}/batchRuleConfigController.do?method=toAddBank";
				});

				$("#updateBtn").click(function(){
					var weekDayList="";
					$("#chk input").each(function(index){
						if($(this).attr("checked")){
							weekDayList+="1";
						} else {
							weekDayList+="0";	
						}
					});
					if(weekDayList!="0000000"){
						weekDayList=weekDayList.substring(6)+weekDayList.substring(0,6);
						$("#weekDayList").val(weekDayList);
					}
					if(checkRuleInfo()){
						var url="${ctx}/batchRuleConfigController.do";
						var data="method=checkRuleConfigDesc&ruleConfigId=${ruleConfig.sequenceId}&batchRuleDesc="+$("#batchRuleDesc").val();
						$.post(url,data,function(res){
							if(res!="yes"){
								alert("批次规则名称已存在，请重新输入");
							} else {
								$("#frm").submit();
							}
						});
					}
				});

				$("#choiceBatchRule").change(function(){
					if($(this).val()!="none"){
						$("#batchRuleId").val($(this).val());
						var context=$(this).find("option").eq($(this)[0].selectedIndex).html();
						$("#batchRuleDesc").val(context.substring(context.indexOf("-")+1));
					}else{
						$("#batchRuleId").val("");
						$("#batchRuleDesc").val("");
					}
				});
				
				$("#allBank").click(function(){
					if($(this).attr("checked")==true){
						$("#bankChk input").each(function(index){
							if($(this).attr("type")=="checkbox"){
								$(this).attr("checked",true);
							}
						});
					}else{
						$("#bankChk input").each(function(index){
							if($(this).attr("type")=="checkbox"){
								$(this).attr("checked",false);
							}
						});
					}
				});
			});

			function checkRuleInfo(){
				var batchRuleDesc=$("#batchRuleDesc").val();
				var timeType=$("#timeType").val();
				var timeSpan=$("#timeSpan").val();
				var identity=$("#identity").val();
				var startTimePoint=$("#startTimePoint").val();
				var endTimePoint=$("#endTimePoint").val();
				var specialPoint=$("#specialPoint").val();
				var effectDate=$("#effectDate").val();
				var lostEffectDate=$("#lostEffectDate").val();
				var maxOrderCounts=$("#maxOrderCounts").val();
				if(batchRuleDesc.length<1 || batchRuleDesc.length>100){
					alert("批次名称长度必须在1-100之间");
					return false;
				}
				if($("input[name='batchDate']:checked").size() == 0){
					alert("请您选择批次产生时间");
					return false;
				}
				if(timeType=="none"){
					alert("请选择一个生产时间类型");
					return false;
				} else if(timeType=="0" && (startTimePoint.length==0 || endTimePoint.length==0)){
					alert("请选择开始和结束时间");
					return false;
				} else if(timeType=="1" && specialPoint.length==0){
					alert("请选择指定时间");
					return false;
				}
				if(timeType=="0" && startTimePoint>endTimePoint){
					alert("开始时间不能大于结束时间");
					return false;
				}
				var reg= /^\d+$/;
				if(timeType=="0" && !reg.test(timeSpan)){
					alert("时间间隔必须为整数");
					return false;
				} 
				if(timeType=="0" && reg.test(timeSpan)){
					if(parseInt(timeSpan)<=0){
						alert("时间间隔必须为大于零的整数");
						return false;
					}
					if(parseInt(timeSpan)>60 && parseInt(timeSpan)%60!=0){
						alert("时间间隔必须为小于60的整数或60的倍数");
						return false;
					}
				}
				if(effectDate.length==0 || lostEffectDate.length==0){
					alert("请选择生效和失效时间");
					return false;
				}
				var date=new Date();
				var year=date.getYear();
				var month=date.getMonth()+1;
				var day=date.getDate();
				var hour=date.getHours();
				var minute=date.getMinutes();
				var second=date.getSeconds();
				var dateStr=year+"-"+((month<10)?"0"+month:month)+"-"+((day<10)?"0"+day:day)+" "
						+((hour<10)?"0"+hour:hour)+":"+((minute<10)?"0"+minute:minute)+":"+((second<10)?"0"+second:second);
				if(effectDate<dateStr){
					alert("生效时间必须大于当前时间");
					return false;
				}
				if($.trim(identity).length<1){
					alert("请输入接收人员的Email，以分号隔开");
					return false;
				} else {
					var emails=$.trim(identity).split(";");
					var reg=/^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$/;
					for(var i=0;i<emails.length;i++){
						if(!reg.test(emails[i])){
							alert("请输入规范的Email格式");
							return false;
						}
					}
				}
				var reg= /^\d+$/;
				if(maxOrderCounts.length!=0 && !reg.test(maxOrderCounts)){
					alert("交易笔数必须为数字");
					return false;
				}
				return true;
			}
		</script>
	</head>
	
	<body>
		<form id="frm" action="${ctx}/batchRuleConfigController.do?method=updateRuleConfig" method="post">
			<div style="text-align: left;position: absolute;left:50px;top:30px;">
				<input type="hidden" name="sequenceId" value="${ruleConfig.sequenceId}"/>
				<input type="hidden" name="timeConfigId" value="${timeConfig.sequenceId}"/>
				<h3>修改批次规则</h3><br>
				<h5>批次规则</h5>
				<hr noshade="noshade" color="black" width="70%"><br>
				批次名称：<input type="text" name="batchRuleDesc" id="batchRuleDesc" value="${ruleConfig.batchRuleDesc}"/><br><br>
				<div id="chk">
				批次生产时间：
				<input type="checkbox" name="batchDate" ${fn:substring(timeConfig.weekDayList,1,2)=="1"?"checked":""}>周一
				<input type="checkbox" name="batchDate" ${fn:substring(timeConfig.weekDayList,2,3)=="1"?"checked":""}>周二
				<input type="checkbox" name="batchDate" ${fn:substring(timeConfig.weekDayList,3,4)=="1"?"checked":""}>周三
				<input type="checkbox" name="batchDate" ${fn:substring(timeConfig.weekDayList,4,5)=="1"?"checked":""}>周四
				<input type="checkbox" name="batchDate" ${fn:substring(timeConfig.weekDayList,5,6)=="1"?"checked":""}>周五
				<input type="checkbox" name="batchDate" ${fn:substring(timeConfig.weekDayList,6,7)=="1"?"checked":""}>周六
				<input type="checkbox" name="batchDate" ${fn:substring(timeConfig.weekDayList,0,1)=="1"?"checked":""}>周日
				</div>
				<br>
				<input type="hidden" name="weekDayList" id="weekDayList" value="1111111">
				批次生产时间类型：
				<select name="timeType" id="timeType">
					<c:if test="${timeConfig.timeType==0}">
						<option value="0" selected="selected">定时间隔</option>
						<option value="1">指定时间</option>
					</c:if>
					<c:if test="${timeConfig.timeType==1}">
						<option value="0">定时间隔</option>
						<option value="1" selected="selected">指定时间</option>
					</c:if>
				</select>
				<br><br>
				<c:if test="${timeConfig.timeType==0}">
					<div id="dsjg" style="padding-left: 40px;">
						定时间隔：
						从<input type="text" name="startTime" id="startTime" value="${timeConfig.startTimePoint}" onfocus="WdatePicker({dateFmt:'H:00:00',vel:'startTimePoint'})" class="Wdate"/>
						 至<input type="text" name="endTime" id="endTime" value="${timeConfig.endTimePoint}" onfocus="WdatePicker({dateFmt:'H:00:00',vel:'endTimePoint'})" class="Wdate"/>
						 每隔<input type="text" name="timeSpan" id="timeSpan" value="${timeConfig.timeSpan}">分钟执行一次<br><br>
						 <input type="hidden" name="startTimePoint" id="startTimePoint" value="${timeConfig.startTimePoint}">
						 <input type="hidden" name="endTimePoint" id="endTimePoint" value="${timeConfig.endTimePoint}">
					</div>
					<div id="zdsj" style="padding-left: 40px;display: none;">
						指定时间：<input type="text" name="special" id="special" onfocus="WdatePicker({dateFmt:'H:mm:ss',vel:'specialPoint'})" class="Wdate"/>
						<br><br>
						<input type="hidden" name="specialPoint" id="specialPoint">
					</div>
				</c:if>
				<c:if test="${timeConfig.timeType==1}">
					<div id="dsjg" style="padding-left: 40px; display: none;">
						定时间隔：
						从<input type="text" name="startTime" id="startTime" onfocus="WdatePicker({dateFmt:'H:00:00',vel:'startTimePoint'})" class="Wdate"/>
						 至<input type="text" name="endTime" id="endTime" onfocus="WdatePicker({dateFmt:'H:00:00',vel:'endTimePoint'})" class="Wdate"/>
						 每隔<input type="text" name="timeSpan" id="timeSpan">分钟执行一次<br><br>
						 <input type="hidden" name="startTimePoint" id="startTimePoint">
						 <input type="hidden" name="endTimePoint" id="endTimePoint">
					</div>
					<div id="zdsj" style="padding-left: 40px;">
						指定时间：<input type="text" name="special" id="special" value="${timeConfig.specialPoint}" onfocus="WdatePicker({dateFmt:'H:mm:ss',vel:'specialPoint'})" class="Wdate"/>
						<br><br>
						<input type="hidden" name="specialPoint" id="specialPoint" value="${timeConfig.specialPoint}">
					</div>
				</c:if>
				规则生效时间：<input type="text" name="effectDate1" id="effectDate1" value="${ruleConfig.effectDateStr}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',vel:'effectDate'})" class="Wdate"/><br><br>
				规则失效时间：<input type="text" name="lostEffectDate1" id="lostEffectDate1" value="${ruleConfig.lostEffectDateStr}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',vel:'lostEffectDate'})" class="Wdate"/><br><br>
				<input type="hidden" name="effectDate" id="effectDate" value="${ruleConfig.effectDateStr}">
				<input type="hidden" name="lostEffectDate" id="lostEffectDate" value="${ruleConfig.lostEffectDateStr}">
				批次文件接收人员Email：<input type="text" name="identity" id="identity" size="50" value="${operators}">(注：多个Email间用分号隔开)<br><br>
				批次最大支持的交易笔数：<input type="text" name="maxOrderCounts" id="maxOrderCounts" value="${ruleConfig.maxOrderCounts}"><br><br>
				<!--
				批次的优先级：
				<select name="batchLevel">
					<c:forEach begin="1" end="20" step="1" var="i">
						<c:if test="${ruleConfig.batchLevel==i}">
							<option value="${i}" selected="selected">${i}</option>
						</c:if>
						<c:if test="${ruleConfig.batchLevel!=i}">
							<option value="${i}">${i}</option>
						</c:if>
					</c:forEach>
				</select>
				--><br>
				<br>
				<input style="width: 100px;" type="button" value="修改" id="updateBtn"></input>&nbsp;&nbsp;&nbsp;&nbsp;
				<input style="width: 100px;" id="btn" type="button" value="返回"/><br><br>
			</div>
		</form>
	</body>
</html>