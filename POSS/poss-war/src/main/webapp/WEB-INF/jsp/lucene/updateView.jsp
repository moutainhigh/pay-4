<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<title>银行开户行信息维护</title>
		<script type="text/javascript">

			function change(val){

				$.ajax({
					type: "POST",
					url: "${ctx}/generateLucene.do?method=getCity&provinceId=" + val,
					data: null,
					success: function(result) {
						$('#city_div').html(result);
					}
				});
			}

			function submit1(){
				var d = document.forms[0];
				var bankName = d.bankName.value;
				var province = d.province.value;
				var city = d.city.value;
				var bankNumber = d.bankNumber.value;
				var bankKaihu = d.bankKaihu.value;
				if(bankName == ''){
					alert('请选择银行！');
					return;
				}
				if(province == ''){
					alert('请选择地区！');
					return;
				}
				if(city == ''){
					alert('请选择城市！');
					return;
				}

				if(bankNumber == ''){
					alert('联行号不能为空！');
					return;
				}

				if(bankKaihu == ''){
					alert('开户行不能为空！');
					return;
				}
				d.submit();
			}

			function back(){
				window.location.href='${ctx}/generateLucene.do';
			}
			
		</script>
	</head>
<body>
<h2 class="panel_title">银行开户行信息维护</h2>
	<form id="luceneForm" method="post"  action="generateLucene.do?method=update">
		<input type="hidden" name="id" value="${result.id}"/>
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" class="textRight">银行名称：</td>
			   	<td class="border_top_right4" class="textLeft">
			   		<select id="bankName" name="bankName">
				   		<c:if test="${bankList != null}">
							<c:forEach items="${bankList}" var="bank">			
				   				<option value="${bank.bankName}" <c:if test='${result.bankName == bank.bankName}'>selected="selected"</c:if>>${bank.bankName}</option>
				   			</c:forEach>
				   		</c:if>
			   		</select>
			   	</td>
	   			<td class="border_top_right4" class="textRight">地区：</td>
			   	<td class="border_top_right4" class="textLeft">
					<select id="province" name="province" onchange="change(this.value)">
				   		<c:if test="${provinceList != null}">
							<c:forEach items="${provinceList}" var="province">			
				   				<option value="${province.provincecode}" <c:if test='${result.province == province.provincename}'>selected="selected"</c:if>>${province.provincename}</option>
				   			</c:forEach>
				   		</c:if>
			   		</select>
				</td>
		   </tr>
		   <tr class="trForContent1">
	   			<td class="border_top_right4" class="textRight"> 城市：</td>
			   	<td class="border_top_right4" class="textLeft">
			   		<div id="city_div">
			   			<select id="city" name="city">
							<option value="${result.city}">${result.city}</option>
						</select>
			   		</div>
			   	</td>	
	   			<td class="border_top_right4" class="textRight"> 联行号：</td>
			   	<td class="border_top_right4" class="textLeft"><input type="text" id ="bankNumber" name="bankNumber" style="width: 150px;" value="<c:if test='${result != null}'>${result.bankNumber}</c:if>" /></td>
		   	</tr>
		    <tr class="trForContent1">
	   			<td class="border_top_right4" class="textRight"> 开户行：</td>
			   	<td class="border_top_right4" class="textLeft"><input type="text" size="70" id ="bankKaihu" name="bankKaihu" style="width: 150px;" value="<c:if test='${result != null}'>${result.bankKaihu}</c:if>"/></td>	
		   		<td class="border_top_right4" class="textRight">状态：</td>
		   		<td class="border_top_right4" class="textLeft">
		   			<c:if test="${result.flag == 1}">
			   			<input type="radio" name="flag" value="1" checked="checked"/>可用
			   			<input type="radio" name="flag" value="0"/>不可用
		   			</c:if>
		   			<c:if test="${result.flag == 0}">
			   			<input type="radio" name="flag" value="1"/>可用
			   			<input type="radio" name="flag" value="0" checked="checked"/>不可用
		   			</c:if>
		   		</td>
		   	</tr>
		   	<tr class="trForContent1">
				<td class="border_top_right4" class="textRight">类别：</td>
		   		<td class="border_top_right4" class="textLeft">
		   			<c:if test="${result.type == 1}">
			   			<input type="radio" name="type" value="1" checked="checked"/>普通
			   			<input type="radio" name="type" value="2"/>专用
		   			</c:if>
		   			<c:if test="${result.type == 2}">
			   			<input type="radio" name="type" value="1"/>普通
			   			<input type="radio" name="type" value="2" checked="checked"/>专用
		   			</c:if>
		   		</td>
		   		<td colspan="2" class="border_top_right4" class="textRight"></td>
		   	</tr>
		   	<tr class="trForContent1">
			<td class="border_top_right4" class="submit" align="center" colspan="4">
				<input type="button" class="button2" onclick="submit1()" value="确定修改"/>
				<input type="button" class="button2" onclick="back()" value="返回"/>
			</td>
			</tr>
		</table>
</form>

</body>
</html>