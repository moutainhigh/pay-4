<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class='panel_title'>域名添加</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      <td align="right" class="border_top_right4" >域名地址：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="siteId" name="siteId">
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >备注：</td>
	      <td class="border_top_right4" colspan="3">
	      	<textarea rows="3" cols="20" name="remark"></textarea>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="button"  name="butSubmit" value="添  加" class="button2" onclick="add();">
	      <input type="button"  name="butSubmit" value="返  回" class="button2" onclick="toIndex();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
	 
  <script type="text/javascript">
  
	function toIndex(){
		window.location.href="${ctx}/crosspay/siteset.do";
	}
	
	function add(pageNo,totalCount,pageSize) {
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/crosspay/siteset.do?method=add",
			data: pars,
			success: function(result) {
				if(result == 1){
					alert('添加成功！');
				}else{
					alert(result);
				}
			}
		});
	}
  </script>