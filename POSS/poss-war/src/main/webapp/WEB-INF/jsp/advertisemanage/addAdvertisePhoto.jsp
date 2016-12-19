<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">

function isRightDateFormat(date) {
	if(date==null||date.length==0){
		return true;
	}
	var isRight = date.match(new RegExp("^(\\d{4})([-]?)(\\d{1,2})([-]?)(\\d{1,2})$"));
	if (isRight== null) {
		return false;
	}
	return true;
}

$(function(){
	
	$("#submitBtn").click(function(){
		if($("#title").val() == ""){
			alert("标题不能为空!");
			return false;
		}
		if($("#parameters").val() == ""){
			alert("图片链接不能为空!");
			return false;
		}
		if($("#code").val() == ""){
			alert("查看模式不能为空!");
			return false;
		}

		if($("#starttimeStr").val() == ""){
			alert("开始时间不能为空!");
			return false;
		}
		
		if($("#endtimeStr").val() == ""){
			alert("结束时间不能为空!");
			return false;
		}
		if($("#starttimeStr").val() > $("#endtimeStr").val()){
			alert("开始时间必须在结束时间之前！");
			return false;
		}
	  	
		var filepath = $("#imgpath").val();
		if(filepath != ""){
			var extname = filepath.substring(filepath.lastIndexOf(".")+1,filepath.length);
			extname = extname.toLowerCase();
		    if(extname!= "bmp"&&extname!= "jpg"&&extname!= "gif"&&extname!= "jpeg"){
		     alert("只能上传bmp,jpg,gif格式的图片！");
		     return false;
		    }
		}
		
		$("#mainfrom").submit();
    });

});

</script>
<c:if test="${advMsg!=null}">
<script type="text/javascript">
alert("${advMsg}");
</script>
</c:if>
<h2 class="panel_title">新增首页图片</h2>
<form action="advertisePhotoManage.do?method=addAdvertisePhoto" method="post" name="mainfrom" id="mainfrom" enctype="multipart/form-data">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >标题：</td>
	      <td class="border_top_right4">
	      <input type="text" id="title"  name="title" maxlength= "30" style="width: 320px;"/>
	      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >位置：</td>
		      <td class="border_top_right4" >
		      	<input type="radio" name="targets" value = "3" checked>合作商户&nbsp;&nbsp;&nbsp;
		      </td>
		 </tr>
		 
		<tr class="trForContent1">
		      <td class=border_top_right4 align="right" >图片链接：</td>
		      <td class="border_top_right4" >
		      <input type="text" id="parameters"  name="parameters" maxlength= "30" style="width: 320px;"/>
		      </td>
		 </tr>
		 
		 
		 <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >查看模式：</td>
		      <td class="border_top_right4" >
		      <input type="radio" name="code" value = "_blank" checked>弹出&nbsp;&nbsp;&nbsp;
		      <input type="radio" name="code" value = "_self" >本页面&nbsp;&nbsp;&nbsp;
		      <input type="hidden" id="sort"  name="sort" maxlength= "30" value="1" style="width: 320px;"/>
		      </td>
		 </tr>
		 
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >图片路径：</td>
		      <td class="border_top_right4" >
		      	<input type="text" id="imgpath"  name="imgpath" maxlength= "200" style="width: 320px;"/>
		      </td>
	     </tr>
	      
		 
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >是否启用：</td>
		      <td class="border_top_right4" >
		         <select name="available">
					<option value="1" selected>有效</option>
					<option value="0">无效</option>
				</select>
		      </td>
	     </tr>
	     
	    <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >广告有效期：</td>
		      <td class="border_top_right4" >
		         <input class="Wdate" type="text" id= "starttimeStr" name="starttimeStr" value="${advertisementDto.starttime}" onClick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 156px;">
		         ~
		         <input class="Wdate" type="text" id= "endtimeStr" name="endtimeStr" value="${advertisementDto.endtime}" onClick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 156px;">
		      </td>
	     </tr>
	     
	     
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="left" colspan="2">
	      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      	 <input type="button" id="submitBtn" class="button2" value="新  增">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
</form>
 <script type="text/javascript">
	
	function goBack() {
		document.mainfrom.action="advertisePhotoManage.do?method=advertisePhotoList";
		document.mainfrom.submit();
	}

  </script>
 
 