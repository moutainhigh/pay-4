<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(document).ready(
		function(){
			indexQuery();
		}
);

function indexQuery(pageNo,totalCount,pageSize) {
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/cardtypeManager.do?method=searchSpEnumInfo",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function addSpEnumInfo(){
	$("#enumNameA").val("");
	$("#value1A").val("");
	$("#value2A").val("");
	$('#addInfoDiv').dialog( 
			{ 
				modal:true, 		
			autoOpen:true,	
			height:200, 
			width:500, 
			title:'添加预付卡卡种信息', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 
				if(!checkDescLength()){
					alert("描述输入字数不能超过40个字");
					return ;
				}
				if(checkEnumType()){
					
					$.post("${ctx}/cardtypeManager.do?method=addSpEnumInfo",{enumName:$("#enumNameA").val(),value1:$("#value1A").val(),value2:$("#value2A").val()}
						,function (msg){
								if(msg=="S"){
									indexQuery();
									$('#addInfoDiv').dialog("close");
								}
								else{
									alert(msg);
								}
						}
					);
			   }
			}, 
			'取消':function(){ 
				$("#addInfoDiv").dialog("close");
			} 
			} 
} );}


function updateSpEnumInfoDetail(enumId,enumName,enumCode,value1,value2){
	$("#enumNameB").val(enumName);
	$("#enumCodeBDiv").text(enumCode);
	$("#value1B").val(value1);
	$("#value2B").val(value2);
	$('#updateInfoDiv').dialog( 
			{ 
				modal:true, 		
			autoOpen:true,	
			height:170, 
			width:500, 
			title:'更新预付卡卡种信息', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 
				if(!checkDescLengthB()){
					alert("描述输入字数不能超过40个字");
					return ;
				}
				if(checkEnumTypeB()){
					
					$.post("${ctx}/cardtypeManager.do?method=updateSpEnumInfo",{id:enumId,enumName:$("#enumNameB").val(),value1:$("#value1B").val(),value2:$("#value2B").val()}
						,function (msg){
								if(msg=="S"){
									indexQuery();
									$('#updateInfoDiv').dialog("close");
								}
								else{
									alert(msg);
								}
						}
					);
			   }
			}, 
			'取消':function(){ 
				$("#updateInfoDiv").dialog("close");
			} 
			} 
} );}

function showSpEnumInfoDetail(id){
	$('#detailDiv').load("${ctx}/cardtypeManager.do?method=getSpEnumInfoDetail",{id:id},function(msg){
		});	
		$('#detailDiv').dialog( 
				{ 
				width:500,	
				modal:true, 	
				title:'预付卡卡种详细页面', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }								
		} );
}


function delSpEnumInfo(enumId,enumName,enumCode,value1,value2){
	$("#enumId").val(enumId);
	$("#enumNameDiv").text(enumName);
	$("#enumCodeDiv").text(enumCode);
	$("#value1Div").text(value1);
	$("#value2Div").text(value2);
	$('#delInfoDiv').dialog( 
			{ 
				modal:true, 		
			autoOpen:true,	
			height:170, 
			width:500, 
			title:'预付卡卡种信息', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 	
					$.post("${ctx}/cardtypeManager.do?method=delSpEnumInfo",{id:$("#enumId").val()}
						,function (msg){
								if(msg=="S"){
									indexQuery();
									$('#delInfoDiv').dialog("close");
								}
								else{
									alert(msg);
								}
						}
					);
			}, 
			'取消':function(){ 
				$("#delInfoDiv").dialog("close");
			} 
			} 
} );}

function checkEnumType(){
	var enumName = $("#enumNameA").val();
	if(enumName==""){
		alert("名称不能为空");
		return false;
	}
	return true;
}

function checkEnumTypeB(){
	var enumName = $("#enumNameB").val();
	if(enumName==""){
		alert("名称不能为空");
		return false;
	}
	return true;
}

function checkDescLength(){
	var desc1 = $("#value1A").val();
	var desc2 = $("#value2A").val();
	if(desc1==null){
		desc1 = "";
	}else{
		desc1 = desc1.replace("\n", " ");
		
	}
	$("#value1A").val(desc1);
	
	if(desc2==null){
		desc2 = "";
	}else{
		desc2 = desc2.replace("\n", " ");
	}
	$("#value2A").val(desc2);
	
	if(desc1.length>40){
		$("#value1A").css({color:'red'});
		return false;
	}
	$("#value1A").css({color:''});
	
	if(desc2.length>40){
		$("#value2A").css({color:'red'});
		return false;
	}
	$("#value2A").css({color:''});
	
	return true;
}

function checkDescLengthB(){
	var desc1 = $("#value1B").val();
	var desc2 = $("#value2B").val();
	
	if(desc1==null){
		desc1 = "";
	}else{
		desc1 = desc1.replace("\n", " ");
		
	}
	$("#value1B").val(desc1);
	
	if(desc2==null){
		desc2 = "";
	}else{
		desc2 = desc2.replace("\n", " ");
	}
	$("#value2B").val(desc2);
	
	if(desc1.length>40){
		$("#value1B").css({color:'red'});
		return false;
	}
	$("#value1B").css({color:''});
	
	if(desc2.length>40){
		$("#value2B").css({color:'red'});
		return false;
	}
	$("#value2B").css({color:''});
	
	return true;
}

</script>
</head>

<body>

<table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">预 付 卡 卡 种 维 护</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

	<form id="searchFormBean">
		<table class="searchTermTable" class="inputTable" style="width:80%" >
			<tr>
				<th >名称：<input type="text" id="enumName" name="enumName" style="width: 150px;" maxlength="50" /></th>
				<th >代码：<input type="text" id="enumCode" name="enumCode" style="width: 150px;" maxlength="20"/></th>			
			</tr>
			<tr ><td height="10"></td>
			</tr>
			<tr>
					<td  colspan="4" style="text-align: center;  ">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="indexQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="addSpEnumInfo()" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加卡种&nbsp;
					</a>
				</td>
			</tr>
		</table>
		</form>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>

</c:if>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<div id="addInfoDiv" style="display: none">
	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>
		<th><span class="must">*</span>名称</th>
		<td>
			<input type="text" name="enumNameA" id="enumNameA" 　 />
		</td>
	</tr>
	<tr>
		<th>备用一</th>
		<td >
			<textarea name="value1A" id="value1A"  onkeyup="checkDescLength()" style="width:250px;height: 40px"/></textarea>
		</td>
	</tr>
	<tr>
		<th>备用二</th>
		<td >
			<textarea name="value2A" id="value2A"  onkeyup="checkDescLength()" style="width:250px;height: 40px"/></textarea>
		</td>
	</tr>
	</table>
</div>

<div id="updateInfoDiv" style="display: none">
	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>
		<th><span class="must">*</span>名称</th>
		<td>
			<input type="text" name="enumNameB" id="enumNameB" 　 />
		</td>
	</tr>
	<tr>
		<th >代码</th>
		<td >
			<div id="enumCodeBDiv" style="font-size: 16px"></div>
		</td>
	</tr>
	<tr>
		<th>备用一</th>
		<td >
			<textarea name="value1B" id="value1B"  onkeyup="checkDescLengthB()" style="width:250px;height: 40px"/></textarea>
		</td>
	</tr>
	<tr>
		<th>备用二</th>
		<td >
			<textarea name="value2B" id="value2B"  onkeyup="checkDescLengthB()" style="width:250px;height: 40px"/></textarea>
		</td>
	</tr>
	</table>
</div>


<div id="delInfoDiv" style="display: none">
	<table class="inputTable" width="300" border="0" cellspacing="0" cellpadding="3" align="center">
	<input type="hidden" name="enumId" id="enumId"  />
	<tr>
		<th width="100">名称</th>
		<td width="200">
			<div id="enumNameDiv" style="font-size: 16px"></div>
		</td>
	</tr>
	<tr>
		<th >代码</th>
		<td >
			<div id="enumCodeDiv" style="font-size: 16px"></div>
		</td>
	</tr>
	<tr>
		<th >类型</th>
		<td >
			<div id="enumTypeDiv" style="width:100%;word-break : break-all" >预付卡</div>
		</td>
	</tr>
	<tr>
		<th >备用一</th>
		<td >
			<div id="value1Div" style="width:100%;word-break : break-all"></div>
		</td>
	</tr>
	<tr>
		<th >备用二</th>
		<td >
			<div id="value2Div" style="width:100%;word-break : break-all"></div>
		</td>
	</tr>
	</table>
</div>

<div id="detailDiv" style="display: none;width: 500px;height: 500px ">
	
</div>

</body>

