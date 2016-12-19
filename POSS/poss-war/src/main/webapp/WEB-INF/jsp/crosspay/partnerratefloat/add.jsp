<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">商户交易汇率浮动新增</h2>
<form action="${ctx}/partnerRateFloat.do?method=add" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" width="100">会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId" value="${respMap.partnerId}"> <span style="color:red">*</span>
	      </td>
	      <td align="right" class="border_top_right4" >浮动起始点：</td>
	      <td class="border_top_right4">
	     	<input type="text" id="markup" name="startPoint" value="${respMap.startPoint}">‰ <span style="color:red">*</span>
	      </td>
	      <td align="right" class="border_top_right4" >浮动截止点：</td>
	      <td class="border_top_right4">
	     	<input type="text" id="priority" name="endPoint" value="${respMap.endPoint}">‰<span style="color:red">*</span>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="6">
	      <input type="submit"  value="添  加" class="button2">
	      <input type="button"  value="返  回" class="button2" onclick="toIndex();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseDesc}">
	<font color="red"><b>${responseDesc}</b></font>
</c:if>
  <script type="text/javascript">
  
	function toIndex(){
		window.location.href="${ctx}/partnerRateFloat.do";
	}
	
	$(function(){
		var url0="${ctx}/partnerRateFloat.do?method=checkData&";
		
        $("#partnerId").blur(function(){
        	var val = $(this).val();
        	if(val.length>0){
        		var url_=url0+"memberCode="+$(this).val()
        	    var id_="partnerId";
        	}
        });
        
	});

	function ajaxRequest(url_,id_){
    	$.ajax({
    		type: "POST",
    		url: url_,
    		success: function(result) {
                alert(result);
                if(result.length>0){
               	 $("#"+id_).val('');
               }
    		}
    	});
	}
  </script>