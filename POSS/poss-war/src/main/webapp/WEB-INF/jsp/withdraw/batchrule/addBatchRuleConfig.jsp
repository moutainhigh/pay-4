<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>新增批次规则</title>
		<script type="text/javascript">
			$(document).ready(function(){
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

				$("#submitBtn").click(function(){
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
					if(verificationInfo()){
						var url="${ctx}/batchRuleConfigController.do";
						var data="method=checkRuleConfigDesc&batchRuleDesc="+$("#batchRuleDesc").val();
						$.post(url,data,function(res){
							if(res!="yes"){
								alert("批次规则名称已存在，请重新输入");
							} else {
								$("#frm").submit();
							}
						});
					}
				});
			});

			function verificationInfo(){
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
				if(timeType=="0" && startTimePoint>=endTimePoint){
					alert("结束时间必须大于开始时间");
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
				if(effectDate>lostEffectDate){
					alert("失效日期必须大于生效日期");
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
		<form id="frm" action="${ctx}/batchRuleConfigController.do?method=create" method="post">
			<div style="text-align: left;position: absolute;left:50px;top:30px;">
			<h3>新增批次规则时间</h3><br>
			批次名称：<input type="text" name="batchRuleDesc" id="batchRuleDesc"/><br><br>
			<div id="chk">
			批次生产时间：
			<input type="checkbox" name="batchDate">周一
			<input type="checkbox" name="batchDate">周二
			<input type="checkbox" name="batchDate">周三
			<input type="checkbox" name="batchDate">周四
			<input type="checkbox" name="batchDate">周五
			<input type="checkbox" name="batchDate">周六
			<input type="checkbox" name="batchDate">周日
			</div>
			<br>
			<input type="hidden" name="weekDayList" id="weekDayList" value="1111111">
			批次生产时间类型：
			<select name="timeType" id="timeType">
				<option value="none" selected="selected">请选择</option>
				<option value="0">定时间隔</option>
				<option value="1">指定时间</option>
			</select>
			<br><br>
			<div id="dsjg" style="display: none;">
				定时间隔：
				从<input type="text" name="startTime" id="startTime" onfocus="WdatePicker({dateFmt:'H:00:00',vel:'startTimePoint'})" class="Wdate"/>
				 至<input type="text" name="endTime" id="endTime" onfocus="WdatePicker({dateFmt:'H:00:00',vel:'endTimePoint'})" class="Wdate"/>
				 每隔<input type="text" name="timeSpan" id="timeSpan">分钟执行一次<br><br>
				 <input type="hidden" name="startTimePoint" id="startTimePoint">
				 <input type="hidden" name="endTimePoint" id="endTimePoint">
			</div>
			<div id="zdsj" style="display: none;">
				指定时间：<input type="text" name="special" id="special" onfocus="WdatePicker({dateFmt:'H:mm:ss',vel:'specialPoint'})" class="Wdate"/>
				<br><br>
				<input type="hidden" name="specialPoint" id="specialPoint">
			</div>
			规则生效时间：<input type="text" name="effectDate1" id="effectDate1" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',vel:'effectDate'})" class="Wdate"/><br><br>
			规则失效时间：<input type="text" name="lostEffectDate1" id="lostEffectDate1" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',vel:'lostEffectDate'})" class="Wdate"/><br><br>
			<input type="hidden" name="effectDate" id="effectDate">
			<input type="hidden" name="lostEffectDate" id="lostEffectDate">
			批次文件接收人员Email：<input type="text" name="identity" id="identity" size="50">(注：多个Email间用分号隔开)<br><br>
			批次最大支持的交易笔数：<input type="text" name="maxOrderCounts" id="maxOrderCounts"><br><br>
			<!--
			批次的优先级：
			<select name="batchLevel">
				<c:forEach begin="1" end="20" step="1" var="i">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select>
			--><br><br>
			<input type="button" value="保存批次规则时间" id="submitBtn">
			</div>
		</form>
	</body>
</html>