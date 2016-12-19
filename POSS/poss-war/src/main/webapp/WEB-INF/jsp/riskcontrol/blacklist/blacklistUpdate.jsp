<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<script type="text/javascript">
<!--
	$(function(){
		$("#processSave").click(function(){
			if(!validate())
				return ;
		
			var pars = $("#blackListModifyId").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/rm-blackandwhite-blacklist.htm?method=blacklistUpdate",
				data: pars,
				success: function(result) {
					if (result == 'success') {
						$('#closeBtn').click();
						//重新载入列表
						blackListQuery();
					} else {
						alert("修改失败!");
					}
				}
			});
		});

		function validate() {
			var business_name = $.trim($("#business_name").val());//公司全称
			var true_name = $.trim($("#true_name").val());//用户名称
			var business_id = $.trim($("#business_id").val());//商户ID
			var bank_account = $.trim($("#bank_account").val());//绑定银行账号

			if('' == business_name){
				alert("公司全称为必填项!");
				return false;
			}
			if('' == true_name){
				alert("用户名称为必填项!");
				return false;
			}
			if('' == business_id){
				alert("商户ID为必填项!");
				return false;
			}
			if('' == bank_account){
				alert("绑定银行账号为必填项!");
				return false;
			}
			if(!s_isNumber(business_id)){
				alert("商户ID必须为数字!");
				return false;
			}
			if(!s_isNumber(bank_account)){
				alert("绑定银行账号必须为数字!");
				return false;
			}
			return true;
		}
	});
//-->
</script>

<div align="center"><font class="titletext">修改黑名单</font></div>


<form method="post" id="blackListModifyId">

  <table>	
    <tr>
   	  	<td>公司全称：</td>
      	<td>
        	<input type="text"  name="business_name" id="business_name" value="${blacklist.business_name}"  />
        	<input type="hidden" name="black_id" value="${blacklist.black_id}"/>
      	</td>
    </tr>
    <tr>
   	  	<td>用户名称：</td>
      	<td>
        	<input type="text"  name="true_name" id="true_name" value="${blacklist.true_name}"  />
      	</td>
    </tr>
  	<tr>
   	  	<td>商户ID：</td>
      	<td>
        	<input type="text"  name="business_id" id="business_id" value="${blacklist.business_id}"  />
      	</td>
    </tr>
     <tr>
   	  	<td>绑定银行账号：</td>
      	<td>
        	<input type="text"  name="bank_account" id="bank_account" value="${blacklist.bank_account}"  />
      	</td>
    </tr>
    <tr>
      <td align="center" colspan="2">
      	<input type="button" class="button01" value="保 存" id="processSave"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="nyroModalClose button01" id="closeBtn" value="取 消" />
      </td>
    </tr>
  </table>
 </form>
 