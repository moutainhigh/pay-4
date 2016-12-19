<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<title>银行开户行信息维护</title>
		<script type="text/javascript">

			function change(val){

				if("" == val){
					$('#city_div').html("<select id='city' name='city'><option value=''>请选择</option></select>");
				}

				$.ajax({
					type: "POST",
					url: "${ctx}/generateLucene.do?method=getCity&provinceId=" + val,
					data: null,
					success: function(result) {
						$('#city_div').html(result);
					}
				});
			}

			function add(){

				if(!confirm('确认添加？')){
					return;
				}
				var d = document.forms[0];
				var bankName = d.bankName.value;
				var province = d.province.value;
				var city = d.city.value;
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
				d.submit();
			}

			function query1(pageNo,totalCount,pageSize){
				var d = document.forms[0];
				var bankName = d.bankName.value;
				var province = d.province.value;
				var city = d.city.value;
				var bankNumber = d.bankNumber.value;
				var bankKaihu = d.bankKaihu.value;

				var parames = $("#luceneForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize; 
				$.ajax({
					type: "POST",
					url: "${ctx}/generateLucene.do?method=findByCondition",
					data: parames,
					success: function(result) {
						$('#resultDiv').html(result);
					}
				});
			}

			function del(id){
				if(confirm('确认删除？')){
					window.location.href='${ctx}/generateLucene.do?method=del&id=' + id;
				}
			}

			function toUpdate(id){
				window.location.href='${ctx}/generateLucene.do?method=toUpdate&id=' + id;
			}

			function clear(){
				var d = document.forms[0];
				d.bankName[0].selected = true;
				d.province[0].selected = true;
				d.city[0].selected = true;
				d.bankNumber.value = '';
				d.bankKaihu.value = '';
			}

			function reBuildIndex(id){
				window.location.href='${ctx}/rebuildLucene.do';
			}
			
		</script>
	</head>
<body>
<h2 class="panel_title">银行开户行信息维护</h2>
	<form id="luceneForm" method="post"  action="generateLucene.do?method=add">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td align="right" class="border_top_right4" class="textRight">银行名称：</td>
			   	<td class="border_top_right4" class="textLeft">
			   		<select id="bankName" name="bankName">
			   			<option value="">请选择</option>
				   		<c:if test="${bankList != null}">
							<c:forEach items="${bankList}" var="bank">			
				   				<option value="${bank.bankName}" <c:if test='${orderInfo != null} && ${orderInfo.bankName == bank.bankName }'>selected="selected"</c:if>>${bank.bankName}</option>
				   			</c:forEach>
				   		</c:if>
			   		</select>
			   	</td>
	   			<td align="right" class="border_top_right4" class="textRight">地区：</td>
			   	<td class="border_top_right4" class="textLeft">
					<select id="province" name="province" onchange="change(this.value)">
			   			<option value="">请选择</option>
				   		<c:if test="${provinceList != null}">
							<c:forEach items="${provinceList}" var="province">			
				   				<option value="${province.provincecode}" <c:if test='${orderInfo != null} && ${orderInfo.province == province.provincename }'>selected="selected"</c:if>>${province.provincename}</option>
				   			</c:forEach>
				   		</c:if>
			   		</select>
				</td>
		   </tr>
		   <tr class="trForContent1">
	   			<td align="right" class="border_top_right4" class="textRight"> 城市：</td>
			   	<td class="border_top_right4" class="textLeft">
			   		<div id="city_div">
			   			<select id="city" name="city">
							<option value="">请选择</option>
						</select>
			   		</div>
			   	</td>	
	   			<td align="right" class="border_top_right4" class="textRight"> 联行号：</td>
			   	<td class="border_top_right4" class="textLeft"><input type="text" id ="bankNumber" name="bankNumber" style="width: 150px;" value="<c:if test='${orderInfo != null}'>${orderInfo.bankNumber}</c:if>" /></td>
		   	</tr>
		    <tr class="trForContent1">
	   			<td align="right" class="border_top_right4" class="textRight"> 开户行：</td>
			   	<td class="border_top_right4" class="textLeft"><input size="80" type="text" id ="bankKaihu" name="bankKaihu" style="width: 150px;" value="<c:if test='${orderInfo != null}'>${orderInfo.bankKaihu}</c:if>"/></td>	
		   		<td align="right" class="border_top_right4" class="textRight">状态：</td>
		   		<td class="border_top_right4" class="textLeft">
		   			<input type="radio" name="flag" value="1" checked="checked"/>可用
		   			<input type="radio" name="flag" value="0"/>不可用
		   		</td>
		   	</tr>
		   	<tr class="trForContent1">
	   			<td align="right" class="border_top_right4" class="textRight">类别：</td>
			   	<td class="border_top_right4" class="textLeft">
		   			<input type="radio" name="type" value="1" checked="checked"/>普通
		   			<input type="radio" name="type" value="2"/>专用
		   		</td>
		   		<td class="border_top_right4" colspan="2" class="textRight"></td>
		   	</tr>
		   	<tr class="trForContent1">
			<td class="border_top_right4" class="submit" align="center" colspan="4">
				<input class="button2" type="button" onclick="add()" value="添加开户行"/>
				<input class="button2" type="button" onclick="clear()" value="清空"/>
				<input class="button2" type="button" onclick="query1()" value="查询开户行信息"/>
				<input class="button2" type="button" onclick="reBuildIndex()" value="重新生成联行号索引"/>
			</td>
			</tr>
		</table>
</form>
<div id="resultDiv"></div>
<p align="left">
<c:if test="${errorMsg != null}">
	<font color="red">${errorMsg}</font>
</c:if></p>
</body>
</html>