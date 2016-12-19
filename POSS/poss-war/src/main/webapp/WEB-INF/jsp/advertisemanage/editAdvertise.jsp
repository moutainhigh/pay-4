<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
$(function(){
	
	$("#submitBtn").click(function(){
		if($("#title").val() == ""){
			alert("标题不能为空!");
			return false;
		}
		if($("#starttime").val() == ""){
			alert("开始时间不能为空!");
			return false;
		}
		if($("#endtime").val() == ""){
			alert("结束时间不能为空!");
			return false;
		}
		if($("#starttime").val() > $("#endtime").val()){
			alert("开始时间必须在结束时间之前！");
			return false;
		}
		//var filepath = $("#imgfile").val(); 
		//if(filepath != ""){
		//	var extname = filepath.substring(filepath.lastIndexOf(".")+1,filepath.length);
		//	extname = extname.toLowerCase();
		//    if(extname!= "bmp"&&extname!= "jpg"&&extname!= "gif"&&extname!= "jpeg"){
		//     alert("只能上传bmp,jpg,gif格式的图片！");
		//     return false;
		//    }
		//}
				
		$("#mainfrom").submit();
    });

});

function goBack(targets) {
	window.location="advertiseManage.do?method=advertiseList&targets="+targets;
	return false;
}
</script>
<c:if test="${advMsg!=null}">
<script type="text/javascript">
alert("${advMsg}");
</script>
</c:if>
<h2 class="panel_title">更新首页幻灯片广告</h2>
<form action="advertiseManage.do?method=updateAdvertise" method="post" name="mainfrom" id="mainfrom" enctype="multipart/form-data">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >标题：</td>
	      <td class="border_top_right4">
	      <input type="text" id="title"  name="title" maxlength= "30" value="${advertisementDto.title}" style="width: 320px;"/>
	      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >位置：</td>
		      <td class="border_top_right4" >
		      	<c:if test="${advertisementDto.targets==1}">个人主页幻灯片</c:if>
				<c:if test="${advertisementDto.targets==2}">企业主页幻灯片</c:if>
		      </td>
		 </tr><%-- 
		 <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >排序：</td>
		      <td class="border_top_right4" >
	      	 	<input type="radio" name="advertisementDto.sort" value = "1" checked>1&nbsp;&nbsp;&nbsp;
				<input type="radio" name="advertisementDto.sort" value = "2" >2&nbsp;&nbsp;&nbsp;
				<input type="radio" name="advertisementDto.sort" value = "3">3&nbsp;&nbsp;&nbsp;
		      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >广告类型：</td>
		      <td class="border_top_right4" >
		         <input type="text" id="advtype"  name="advertisementDto.advtype" maxlength= "30" value="${advertisementDto.advtype}" style="width: 320px;"/>
		      </td>
	     </tr>--%>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >图片路径：</td>
		      <td class="border_top_right4" >
		         <input type="text" id="imgpath"  name="imgpath" value="${advertisementDto.imgpath}"  style="width: 320px;" />
		      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >图片链接：</td>
		      <td class="border_top_right4" >
		      <input type="text" id="parameters"  name="parameters" maxlength= "150" value="${advertisementDto.parameters}" style="width: 320px;"/>
		      </td>
		 </tr>
		 <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >查看模式：</td>
		      <td class="border_top_right4" >
		      <input id="blank" type="radio" name="code" value = "_blank" <c:if test="${advertisementDto.code=='_blank'}"> checked </c:if> >弹出&nbsp;&nbsp;&nbsp;
		      <input id="self" type="radio" name="code" value = "_self" <c:if test="${advertisementDto.code=='_self'}"> checked </c:if>>本页面&nbsp;&nbsp;&nbsp;
		      </td>
		 </tr>
		 
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >&nbsp;</td>
		      <td class="border_top_right4" >
		         <img src="${advertisementDto.imgpath}" height="180" width="720" />
		      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >是否启用：</td>
		      <td class="border_top_right4" >
		         <select name="available">
					<option <c:if test="${advertisementDto.available==1}"> selected </c:if> value="1" >有效</option>
					<option <c:if test="${advertisementDto.available==0}"> selected </c:if>  value="0">无效</option>
				</select>
		      </td>
	     </tr>
	     <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >广告有效期：</td>
		      <td class="border_top_right4" >
		         <input class="Wdate" type="text" id= "starttimeStr" name="starttimeStr" value="${advertisementDto.starttimeStr}" onClick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 156px;">~<input class="Wdate" type="text" id= "endtimeStr" name="endtimeStr" value="${advertisementDto.endtimeStr}" onClick="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" style="width: 156px;">
		      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center" colspan="2">
	      	 <input type="button" id="submitBtn" class="button2" value="更  新">
	      	 <c:if test="${advertisementDto.targets==1}"><input type="button" onclick="goBack(1);"  class="button2" value="返  回"></c:if> 
	      	 <c:if test="${advertisementDto.targets==2}"><input type="button" onclick="goBack(2);"  class="button2" value="返  回"></c:if> 
	      </td>
	    </tr>
	  </table>
	  <input type="hidden" name="id" value="${advertisementDto.id}">
	  <input type="hidden" value="${advertisementDto.targets}" name="targets">
</form>

 