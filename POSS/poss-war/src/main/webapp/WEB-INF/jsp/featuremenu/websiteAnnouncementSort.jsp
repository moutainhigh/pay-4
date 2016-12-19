<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function moveUp(obj)
{
with (obj){
  if(selectedIndex==0){
    options[length]=new Option(options[0].text,options[0].value)
    options[0]=null
    selectedIndex=length-1
    }
  else if(selectedIndex>0) moveG(obj,-1)
  }
}

function moveDown(obj)
{
with (obj){
  if(selectedIndex==length-1){
    var otext=options[selectedIndex].text
    var ovalue=options[selectedIndex].value
    for(i=selectedIndex; i>0; i--){
      options[i].text=options[i-1].text
      options[i].value=options[i-1].value
      }
    options[i].text=otext
    options[i].value=ovalue
    selectedIndex=0
    }
  else if(selectedIndex>=0 && selectedIndex<length-1) moveG(obj,+1)
  }
}

function moveG(obj,offset)
{
with (obj){
  desIndex=selectedIndex+offset
  var otext=options[desIndex].text
  var ovalue=options[desIndex].value
  options[desIndex].text=options[selectedIndex].text
  options[desIndex].value=options[selectedIndex].value
  options[selectedIndex].text=otext
  options[selectedIndex].value=ovalue
  selectedIndex=desIndex
  }
}

function Form_Submit(formObj)
{
  var resourceIdstr="";
  for (i = 0 ; i < formObj.resourceIds.length; i ++)
  {
    //formObj.resourceIds.options[i].selected = true ;
    resourceIdstr=resourceIdstr+formObj.resourceIds.options[i].value+",";
  }
  
  formObj.resourceIdstr.value=resourceIdstr;
//  alert(formObj.resourceIdstr.value);
  
  return true ;
}

$(function(){
	   $("#sform").submit(function(){
		   var resourceIdstr="";
		   $("#menuIds option").each(function(){
			     resourceIdstr+=$(this).val()+",";
			   });
		   //alert(resourceIdstr);
		    $("#menuIdArry").val(resourceIdstr);
		    return true;
		  });

	  });
function closePage() {
	parent.closePage('websiteAnnouncement.do?method=announcementSortView');
}

</script>
</head>

<body>

<h2 class="panel_title">WEBSITE公告排序</h2>

<form id="sform" name="sform" method="post" action="websiteAnnouncement.do">
	<input type="hidden" name="method" value="doSorting"/>
	<input type="hidden" name="menuIdArry" id="menuIdArry"/>
	<input type="hidden" name="type" id="type" value="${type}"/>
	<input type="hidden" name="pId" id="pId"  value="${pId}" />
	<table cellpadding="0" cellspacing="0" width="700" border="1" style="margin:0 auto;">
		<tr class="trForContent1">
			<td class="border_top_right4">主题</td>
			<td class="border_top_right4">
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr class="trForContent1">
						<td class="border_top_right4" width="70%">
						   <select id="menuIds" name="menuIds" multiple size="18" style="width:360px;">
							   <c:forEach items="${announcemenList}" var="m">
							  	 <option value="${m.announcementId}">${m.subject}</option>
							   </c:forEach>
						   </select>
						</td>
						<td height="26" class="border_top_right4"valign="center" align="center">
				    		<input name="Button" type="button" class="button_2letter" value="∧" onclick="moveUp(this.form.menuIds)">
						      <br>
						      <br>
				      		<input name="Submit2" type="button" class="button_2letter" value="∨" onclick="moveDown(this.form.menuIds)">
				        </td>
					 </tr>
				 </table>
			 </td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" >&nbsp;</td>
			<td class="border_top_right4"  align="center" >
				<input type="submit" name="btnok" value="提交"/>
				<input type = "button"  onclick="javascript:closePage();" value="关闭">
			</td>
		</tr>
	</table>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

