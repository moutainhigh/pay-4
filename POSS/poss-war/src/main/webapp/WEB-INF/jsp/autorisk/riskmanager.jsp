<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<script type="text/javascript">
	$(function(){
		var m001 = "${m001}";
		var m001List = new Array();    
		m001List = m001.split(",");
		for(var i=0;i<m001List.length ;i++ ){    
			$("#m001_"+(i+1)).val($.trim(m001List[i]));
	    }
		
		var m002 = "${m002}";
		var m002List = new Array();    
		m002List = m002.split(",");
		for(var i=0;i<m002List.length ;i++ ){    
			$("#m002_"+(i+1)).val($.trim(m002List[i]));
	    }
		
		$("#m003_1").val("${m003}");
		$("#m004_1").val("${m004}");
		
		var p001 = "${p001}";
		var p001List = new Array();    
		p001List = p001.split(",");
		for(var i=0;i<p001List.length ;i++ ){    
			$("#p001_"+(i+1)).val($.trim(p001List[i]));
	    }
		
		$("#p002_1").val("${p002}");
	});
	</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">风控规则配置</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table> 
	
	<form method="post" action="${ctx}/fundout_risk_manager.htm?method=saveRisk">
	
	<table class="border_all2" width="75%" border="0" cellspacing="0" cellpadding="3" align="center">
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="2"><b>规则号         m001</b></td>
			<td class="border_top_right4" colspan="2"><font color="red">(针对新企业账户和新个人注册账户的审查，可设置多次出款触发审查)</font></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">历史第&nbsp;<input type="text" name="m001_1" id="m001_1" value=""/>次出款</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">历史第&nbsp;<input type="text" name="m001_2" id="m001_2" />次出款</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">历史第&nbsp;<input type="text" name="m001_3" id="m001_3" />次出款</td>
		</tr>
	
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="2"><b>规则号         m002</b></td>
			<td class="border_top_right4" colspan="2"><font color="red">(针对商户开始收款、收款量急剧增加、收款量急剧减小)</font></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4"><font color="red">以触发规则日期为T</font></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">T到T-&nbsp;<input type="text" name="m002_1" id="m002_1" />日内收款金额>=&nbsp;
			<input type="text" name="m002_2" id="m002_2" />元</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">T到T-&nbsp;<input type="text" name="m002_3" id="m002_3" />日内收款金额>=T-
			<input type="text" name="m002_4" id="m002_4" />到T-<input type="text" name="m002_5" id="m002_5" />日内收款总额的
			<input type="text" name="m002_6" id="m002_6" />%
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">T到T-&nbsp;<input type="text" name="m002_7" id="m002_7" />日内收款金额>=T-
			<input type="text" name="m002_8" id="m002_8" />到T-<input type="text" name="m002_9" id="m002_9" />日内收款总额的
			<input type="text" name="m002_10" id="m002_10" />%
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">T到T-&nbsp;<input type="text" name="m002_11" id="m002_11" />日内收款金额<=T-
			<input type="text" name="m002_12" id="m002_12" />到T-<input type="text" name="m002_13" id="m002_13" />日内收款总额的
			<input type="text" name="m002_14" id="m002_14" />%
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">T到T-&nbsp;<input type="text" name="m002_15" id="m002_15" />日内收款金额<=T-
			<input type="text" name="m002_16" id="m002_16" />到T-<input type="text" name="m002_17" id="m002_17" />日内收款总额的
			<input type="text" name="m002_18" id="m002_18" />%
			</td>
		</tr>
	
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="2"><b>规则号         m003</b></td>
			<td class="border_top_right4" colspan="2"><font color="red">(针对新出现的收款域名)</font></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">历史收款域名数量大于T-&nbsp;<input type="text" name="m003_1" id="m003_1" />
			日之前的所有收款域名数量
			</td>
		</tr>
		
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4"><b>规则号         m004</b></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">T到T-1内单笔收款最大金额>=<input type="text" name="m004_1" id="m004_1" />（元）
			</td>
		</tr>
	
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4"><b>规则号         p001</b></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">最近&nbsp;<input type="text" name="p001_1" id="p001_1" />日内发生
			<input type="text" name="p001_2" id="p001_2" />次及以上登录失败
			</td>
		</tr>
	
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4"><b>规则号         p002</b></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4" colspan="4">最近&nbsp;<input type="text" name="p002_1" id="p002_1" />
			日内登录的IP关联至其他账户，不考虑其关联账户在此IP的登录日期。
			</td>
		</tr>
	</table>		
	<br>
	<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
		<tr class="trbgcolor6" align="center">
			<td>
				<input type="hidden" name="batchAccounts" value="0"/>
				<input type="hidden" name="mcc" value="0"/>
			 	<input type="submit" name="Submit" id="enforceWithdrawBtn" value="保存">
			</td>
		</tr>
	</table>
	<c:if test="${not empty info}">
		<br>
		<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
			<tr class="trbgcolor6" align="center">
				<td>
					<li style="color: red;">${info}</li>
				</td>
			</tr>
		</table>
	</c:if>
	</form>
</body>
</html>