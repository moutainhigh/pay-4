<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<meta. http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta. name="Keywords" content="" />
<meta. name="Description" content="" />
<title></title>
<script language="javascript" type="text/javascript">
	function goHead(){    
	  	document.location = "${appContext}/manualbooking/misController.do?menthod=index";  	
	}
	function Trim(str){  
	    return str.replace(/(^\s*)|(\s*$)/g, "");  
	}
	function do_Submit() {
		var flag;
		var jcrdr_num=0;     //借的数目
		var dcrdr_num=0;     //贷的数目
		if($("#objectManageForm input:checked")){
			   flag=$("#objectManageForm input:checked").val();
		}
		 var _accountCode=document.getElementsByName("accountCode").length;
		   if(_accountCode==0){
			   alert("账户:不能为空");
			   return false;
			}else{
				for (var i=0;i<_accountCode;i++){
				    if(Trim(document.getElementsByName("accountCode")[i].value)==null ||Trim(document.getElementsByName("accountCode")[i].value)==""){
				     	 alert("账户:不能为空");
		                 return false;
				    }
				}
			}
		 var _amount=document.getElementsByName("amount").length;
		   if(_amount==0){
			   alert("账户:不能为空");
			   return false;
			}else{
				for (var i=0;i<_accountCode;i++){
				    if(Trim(document.getElementsByName("amount")[i].value)==null ||Trim(document.getElementsByName("amount")[i].value)==""){
				     	 alert("金额:不能为空");
		                 return false;
				    }
				}
			}
		if(flag==1){
			var account_Code=document.getElementsByName("accountCode").length;
			if(account_Code % 2==1){
				alert("借贷不平衡");
			}else{
				if (account_Code>0){
					for (var i=0;i<account_Code;i++){
					     	 if(document.getElementsByName("crdr")[i].value==1){
					     		 jcrdr_num++;
						     }else if(document.getElementsByName("crdr")[i].value==2){
						    	 dcrdr_num++;
						     }
					}
				}
		    }
			
		}
	//	las_number = Number(jamount_num) + Number(damount_num);
		if(jcrdr_num!=dcrdr_num){
			alert("借贷不平衡");
			return false;
		}
		var pars = $("#objectManageForm").serialize();
		$.ajax({
			type: "POST",
			url: "${appContext}/manualbooking/vouchDatailInit.do?method=InsertBothInfo",
			data: pars,
			success: function(result) {
				if (result == 'true') {
					alert("提交成功");
					goHead();
				}else{
					alert("提交失败");
			    } 
			}
		});
	}

	
	function add(){
	var otr = document.getElementById("tab").insertRow(-1);
	var checkTd=document.createElement("td");
	checkTd.innerHTML = '<input type="checkbox"  onclick="ccolor()" name="checkItem" ><input name="de" type="button" class="button" onClick="del();" value="删 除" />';
	var otd1 = document.createElement("td");
	otd1.innerHTML = '<input type="text"  name="accountCode" maxlength="30" value=""/>';
	var otd2 = document.createElement("td");
	otd2.innerHTML = '<select  name="crdr" maxlength="30" onchange=\"type_choose(this)\" ><option value=\"1\" >借</option><option value=\"2\">贷</option></select>';
	var otd3 = document.createElement("td");
	otd3.innerHTML = '<input type="text"  name="amount" maxlength="30" value=""/>';
	var otd4 = document.createElement("td");
	otd4.innerHTML = '<input type="text"  name="_remark"  maxlength="30" value=""/>';
	
	otr.appendChild(checkTd);
	otr.appendChild(otd1); 
	otr.appendChild(otd2); 
	otr.appendChild(otd3); 
	otr.appendChild(otd4); 
	}
	function type_choose(obj){
	}
	
	function ccolor()
	{
		
	   var c1 = document.getElementsByName('checkItem');
	   for(var i=0; i<c1.length; i++)
	   if(c1[i].checked)
	   {
	    //c1[i].parentNode.parentNode.className="checkBg";
	    //c1[i].parentNode.nextSibling.firstChild.className="checkTxt";
	   // c1[i].parentNode.nextSibling.nextSibling.firstChild.className="checkTxt";
	   }
	   else { 
		   c1[i].parentNode.parentNode.className="";
	   c1[i].parentNode.nextSibling.firstChild.className="";
	    c1[i].parentNode.nextSibling.nextSibling.firstChild.className="";}
	}
	function del(){
		var c = document.getElementsByName('checkItem');
		var idArray = new Array();
		for(var i=0; i<c.length; i++)
		if(c[i].checked)
		idArray.push(i);
	   var rowIndex;
	   var nextDiff =0;
	   for(j=0;j< idArray.length;j++)
		{
		    rowIndex = idArray[j]+1-nextDiff++;
		    document.getElementById("tab").deleteRow(rowIndex);
		}
	}
</script>
</head>

<body>
<form id="objectManageForm" name="objectManageForm">
<input type="hidden" name="hiid" id="hiid" value="" />
<table width=600 height=230 border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="31%" height="40" align=right >验证借贷平衡：</td>
		<td width="69%" align=left >
			 <input type="radio" value="1" id="yzcrdr" name="yzcrdr" checked="checked"/>是<input type="radio" value="2" id="yzcrdr" name="yzcrdr" />否
		</td>
	</tr>
	<tr>
		<td width="31%" height="40" align=right >创建日期：</td>
		<td width="69%" align=left ><input type="text"
			id="createDate" name="createDate" readonly="readonly" value="<fmt:formatDate value="${curdate}" type="date"/>" /></td>
	</tr>
	<tr>
		<td width="31%" height="40" align=right >创建人：</td>
		<td width="69%" align=left ><input type="text"
			id="creator" name="creator" value="${creator}" readonly="readonly"/></td>
	</tr>
	<tr>
		<td width="31%" height="40" align=right >状态：</td>
		<td width="69%" align=left ><input type="text"
			id="status" name="status" value="未复核" readonly="readonly"/></td>
	</tr>
	<tr>
		<td width="31%" height="40" align=right >备注：</td>
		<td width="69%" align=left ><textarea type="text" rows="3" cols="46" id="remark" name="remark" ></textarea></td>
		</td>
	</tr>
	<tr>
		<td width="31%" height="40" align=right >vouchDatailInit：</td>
		<td width="69%" align=left ><input
			name="addv_btn" id="addv_btn" type="button" class="button"
			onClick="add();" value=" 增 加 " /> <input name="del_btn" id="del_btn"
			type="button" class="button" onClick="del();" value=" 删 除 " />
		<table id="tab" class="tablesorter" border="1" cellpadding="0"
			cellspacing="1" align="center" width="420">
			<tr>
				<td></td>
				<td>账户</td>
				<td>借贷</td>
				<td>金额</td>
				<td>摘要</td>
			</tr>

			<tr>
				<td><input type="checkbox" onclick="ccolor()" name="checkItem"><input
					name="de" type="button" class="button" onClick="del();" value="删 除" /></td>
				<td><input type="text" name="accountCode" maxlength="30" value="" /></td>
				<td>
				    <select name="crdr" maxlength="30" onchange="type_choose(this)">
						<option value="1" >借</option>
						<option value="2" >贷</option>
				    </select>
				</td>
				<td><input type="text" name="amount" maxlength="30" value="" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
				<td><input type="text" name="_remark" maxlength="30" value="" /></td>
				
			</tr>
		
		</table>
		</td>
	</tr>
	<tr>
		<td height="33" align=right >&nbsp;&nbsp;</td>
		<td align=left >
		 <input type="button" value="提 交 "
			onClick="do_Submit()" />&nbsp;&nbsp; &nbsp;&nbsp;
		<input type="button" value="返 回  " onClick="goHead()" /></td>
	</tr>
</table>

</form>
</body>
</html>
