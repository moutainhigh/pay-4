<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<style type="text/css">
body{font-size: 12px; font-family: 宋体}
.textbox {border-right: #cccccc 1px solid; border-top: #cccccc 1px solid; border-left: #cccccc 1px solid; color: #333333; border-bottom: #cccccc 1px solid; height: 20px;widows:120px; background-color: #fdfdfd}
.button {border-right: #888888 1px solid; border-top: #f4f4f4 1px solid; border-left: #f4f4f4 1px solid; COLOR: #000000; padding-top: 2px; border-bottom: #888888 1px solid}
td{height: 20px;}
.td1{font-weight: bold;text-align: center;}
</style>
<script type="text/javascript">
	$(function(){
		$("#upt").click(function(){
			var ratios=$(":input[name=ratio]");
			var channelCodes=$(":input[name=channelCode]");
			var ratioStr="";
			var channelCodeStr="";
			var sumratio=0;
			for (var i=0;i<ratios.length;i++){
				sumratio+=parseFloat(ratios[i].value);
				ratioStr=ratioStr+ratios[i].value+",";
				channelCodeStr=channelCodeStr+channelCodes[i].value+",";
			}
			  var c=channelCodeStr.split(",");
			  c=c.sort();
			  for(var i = 0; i<c.length;i++){
				 if(c[i]==c[i+1]){
					alert("充值渠道选择重复");
					return ;
				  }
			  }	
			if(sumratio!=100){
				alert("充值渠道比例不正确");
				return ;
			}
			$.ajax({
				type: "POST",
				url: "${ctx}/mobileChannelRule.do?method=updateRatio",
				data: "ruleId="+$("#ruleId").val()+"&channelCode="+channelCodeStr+"&ratio="+ratioStr+"&mobileProvinces="+$("#province").val()+"&bossType="+$("#bossType").val(),
				success: function(result) {
					if(result!=""){
						alert("更新失败");
						return ;
					}
					alert("更新成功");
				}
			});
		});
	});
</script>
</head>
<table id=grid
	style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc"
	cellSpacing=1 cellPadding=2 rules=all border=0>
	<tbody>
		<tr
			style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
			<td>地区</td>
			<td>运营商</td>
			<td>直充渠道</td>
			<td>比例</td>
		</tr>
		<c:forEach items="${ruleList}" var="channelRule">
			<tr
				style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
				<td width="15%"><select name="mobileProvinces" id="province"
					onchange="javascript:this.disabled=true;" disabled="disabled">
					<c:forEach items="${prolist}" var="Pro">
						<c:if test="${channelRule.mobileProvinces eq '默认'}">
							<option value="0" selected="selected">默认</option>						
						</c:if>
						<c:choose>
							<c:when test="${Pro.provincename==channelRule.mobileProvinces}">
								<option value="${Pro.provincecode}" selected="selected">${Pro.provincename}</option>
							</c:when>
							<c:otherwise>
								<option value="${Pro.provincecode}">${Pro.provincename}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select></td>
				<td><select name="bossType"
					onchange="javascript:this.disabled=true;" disabled="disabled" id="bossType">
					<c:if test="${channelRule.bossType eq '默认'}">
						<option value="" selected="selected">默认</option>
						<option value="0">移动</option>
						<option value="1">联通</option>
						<option value="2">电信</option>
					</c:if>
					<c:if test="${channelRule.bossType eq 0}">
						<option value="">默认</option>
						<option value="0" selected="selected">移动</option>
						<option value="1">联通</option>
						<option value="2">电信</option>
					</c:if>
					<c:if test="${channelRule.bossType eq 1}">
						<option value="">默认</option>
						<option value="0">移动</option>
						<option value="1" selected="selected">联通</option>
						<option value="2">电信</option>
					</c:if>
					<c:if test="${channelRule.bossType eq 2}">
						<option value="">默认</option>
						<option value="0">移动</option>
						<option value="1">联通</option>
						<option value="2" selected="selected">电信</option>
					</c:if>
				</select></td>
				<td><c:forEach items="${channelRule.channelInfos}"
					var="channelListInfos">
					<select name="channelCode">
						<c:forEach items="${channelMap}" var="channelMap">
							<c:choose>
								<c:when test="${channelMap.key==channelListInfos.channelCode}">
									<option value="${channelMap.key}" selected="selected">${channelMap.value}</option>
								</c:when>
								<c:otherwise>
									<option value="${channelMap.key}">${channelMap.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					<br />
				</c:forEach></td>
				<td><c:forEach items="${channelRule.channelInfos}"
					var="channelList">
					<input type="text" name="ratio" value="${channelList.ratio*100}" />%<br />
				</c:forEach></td>
			</tr>
			<input type="hidden" id="ruleId" value="${channelRule.ruleId }"/>
		</c:forEach>
		<tr>
			<td><input type="button" id="upt" value="保存" class="button"/><input type="button" id="close" value="关闭" class="button"/></td>
		</tr>
	</tbody>
</table>