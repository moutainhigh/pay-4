<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script language="javascript">

function closePage(url){	
	parent.closePage(url);
}
function submitForm(){
	if(document.getElementById('appealSourceCode').value!=''){
		if(document.getElementById('appealSourceCode').value=='101'){
			
			if(document.getElementById('callPhone').value==''||document.getElementById('linkPhone').value==''){
				
				alert("申诉来源为客服时,来电号码和联系电话不能为空!");
				return ;
			}
		}
		if(document.getElementById('appealSourceCode').value=='102'){
			if(document.getElementById('linkEmail').value==''){
				
				alert("申诉来源为邮箱时,联系邮箱不能为空!");
				return ;
			}
		}
		if(document.getElementById('appealBody').value!=''){
			var str = document.getElementById('appealBody').value; 

			if (str.length>1000){ 
				alert("申诉主体不能超过1000个字符!");
				return ;
			} 

			
			
			
		}					
		if(document.getElementById('remark').value!=''){
			var str = document.getElementById('remark').value; 

			if (str.length>1000){ 
				alert("解决描述不能超过1000个字符!");
				return ;
			} 
			
		}		
	}
	
	if(document.appealFormBean.onsubmit()){
		//alert("document.appealFormBean.submit");
		document.appealFormBean.submit();		
	}
}
var pic1 = true;
var pic2 = true;
var pic3 = true;
var pic4 = true;
var pic5 = true;
var pic6 = true;
function checkFileChange(obj) {   
	//验证格式                    
    var file = document.getElementById(obj).value;
    if(file!=null&&file!=''){
	    
	    var exp = /.\.jpg|.\.gif|.\.png|.\.bmp/i;   
	    if (!exp.test(file)) {       
	       alert("图片格式只能为*.jpg,*.gif,*.png,*.bmp");  
	       document.getElementById('submitButton').disabled=true;
	       if(obj=='picture1'){
	    	   pic1 = false;
	       }else if(obj=='picture2'){
	    	   pic2 = false;
	       }else if(obj=='picture3'){
	    	   pic3 = false;
	       }else if(obj=='picture4'){
	    	   pic4 = false;
	       }else if(obj=='picture5'){
	    	   pic5 = false;
	       }else if(obj=='picture6'){
	    	   pic6 = false;
	       }
	    }else{
	    	 if(obj=='picture1'){
	      	   pic1 = true;
	         }else if(obj=='picture2'){
	      	   pic2 = true;
	         }else if(obj=='picture3'){
	      	   pic3 = true;
	         }else if(obj=='picture4'){
	      	   pic4 = true;
	         }else if(obj=='picture5'){
	      	   pic5 = true;
	         }else if(obj=='picture6'){
	      	   pic6 = true;
	         } 
	         if(pic1&&pic2&&pic3&&pic4&&pic5&&pic6){
	        	 document.getElementById('submitButton').disabled=false;
	         }
	    }   
    }   
}   




	
</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">申 诉 受 理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<form id="appealFormBean" name="appealFormBean" action="appealAdd.do" method="post"   onsubmit="return validator(this)" enctype="multipart/form-data">

<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">

		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >客户姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="customerName" name="customerName" maxlength= "16" valid="required" errmsg="客户姓名不能为空!"/>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >发生时间：</td>
			<td class="border_top_right4" align="left" >
				<input class="Wdate" type="text" id= "occurTime" name="occurTime"  onClick="WdatePicker({maxDate:'%y-%M-%d'})">
			</td>
		</tr>				
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >是否需要回复：</td>
			<td class="border_top_right4" align="left" >
				<select id="isNeedReply" name="isNeedReply" valid="required" errmsg="是否需要回复不能为空!">	
					<option value="" selected>---请选择---</option>		
					<option value="1">是</option>
					<option value="0">否</option>		
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >是否紧急：</td>
			<td class="border_top_right4" align="left" >
				<select id="isUrgency" name="isUrgency"  valid="required" errmsg="是否紧急不能为空!">	
					<option value="" selected>---请选择---</option>		
					<option value="1">是</option>
					<option value="0">否</option>		
				</select>
				<font color="red">*</font>
				
			</td>
		</tr>	
		<tr class="trForContent1">
			
			<td class="border_top_right4" align="right" >来源：</td>
			<td class="border_top_right4" align="left" >
				<select	id="appealSourceCode" name="appealSourceCode"  valid="required" errmsg="来源不能为空!">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${appealSourceList}" var="appealSource">
					<option value="${appealSource.code}">${appealSource.name}</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >联系邮箱：</td>
			<td class="border_top_right4" align="left" colspan="3" >
				<input type="text" id="linkEmail" name="linkEmail"  maxlength= "64" valid="isEmail" errmsg="邮箱输入格式不正确!" >
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >来电号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="callPhone" name="callPhone" maxlength= "32" valid="isPhoneAndMobile" errmsg="来电号码输入格式不正确!"/>
			</td>
			<td class="border_top_right4" align="right" >联系电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="linkPhone" name="linkPhone" maxlength= "32" valid="isPhoneAndMobile" errmsg="联系电话输入格式不正确!"/>
			</td>			
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >申诉主体：</td>
			<td class="border_top_right4" align="left" colspan="3">				
				<textArea id="appealBody" name="appealBody" rows="6" cols="100" valid="required" errmsg="申诉主体不能为空!"></textArea>
				<font color="red">*</font>
			</td>
			
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >解决描述：</td>
			<td class="border_top_right4" align="left" colspan="3">
				<textArea id="remark" name="remark" rows="6" cols="100"></textArea>
			</td>
			
		</tr>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="4"> <font color="red">图片资料仅支持*.jpg,*.gif,*.png,*.bmp格式且不能大于2M</font></td></tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >图片资料1：</td>
			<td class="border_top_right4" align="left" >
				<input name="picture1" id="picture1" type="file" onchange="Javascript:checkFileChange('picture1');"/>  
			</td>
			<td class="border_top_right4" align="right" >图片资料2：</td>
			<td class="border_top_right4" align="left" >
				<input name="picture2" id="picture2" type="file" onchange="Javascript:checkFileChange('picture2');"/>  
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >图片资料3：</td>
			<td class="border_top_right4" align="left" >
				<input name="picture3" id="picture3" type="file" onchange="Javascript:checkFileChange('picture3');"/>  
			</td>
			<td class="border_top_right4" align="right" >图片资料4：</td>
			<td class="border_top_right4" align="left" >
				<input name="picture4" id="picture4" type="file" onchange="Javascript:checkFileChange('picture4');"/>  
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >图片资料5：</td>
			<td class="border_top_right4" align="left" >
				<input name="picture5" id="picture5" type="file" onchange="Javascript:checkFileChange('picture5');"/>  
			</td>
			<td class="border_top_right4" align="right" >图片资料6：</td>
			<td class="border_top_right4" align="left" >
				<input name="picture6" id="picture6" type="file" onchange="Javascript:checkFileChange('picture6');"/>  
			</td>
		</tr>				
		
		
</table>
<br></br>
<table>
	<tr>	
		<td  align="center">	
			<input id="submitButton" name="submitButton" type="button" value="保存" onclick="javascript:submitForm();">			
		</td>
		<td  align="center">	
		 <input id="closeButton" name="closeButton" type="button" value="关闭" onclick="javascript:closePage('appealAdd.do');">			
		</td>
	</tr>
</table>

</form>


</body>

