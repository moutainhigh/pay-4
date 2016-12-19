<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<HTML>
<head>
<style type="text/css">
body{font-size: 12px; font-family: 宋体}
.textbox {border-right: #cccccc 1px solid; border-top: #cccccc 1px solid; border-left: #cccccc 1px solid; color: #333333; border-bottom: #cccccc 1px solid; height: 20px;widows:120px; background-color: #fdfdfd}
.button {border-right: #888888 1px solid; border-top: #f4f4f4 1px solid; border-left: #f4f4f4 1px solid; COLOR: #000000; padding-top: 2px; border-bottom: #888888 1px solid}
td{height: 20px;}
.td1{font-weight: bold;text-align: center;}
</style>
<script type="text/javascript">
$(function(){
	$("#saveRule").click(function(){
		var ratios=$(":input[name=ratio]");
		var channelCodes=$(":input[name=channelCode]");
		var ratioStr="";
		var channelCodeStr="";
		var sumratio=0;
		for (var i=0;i<ratios.length;i++){
			sumratio+=parseFloat(ratios[i].value);
			ratioStr=ratioStr+ratios[i].value+",";
			channelCodeStr=channelCodeStr+channelCodes[i].value+",";
		}	
		  var c=channelCodeStr.split(",");
		  c=c.sort();
		  for(var i = 0; i<c.length;i++){
			 if(c[i]==c[i+1]){
				alert("充值渠道选择重复");
				return ;
			  }
		  }
		if(sumratio!=100){
			alert("充值渠道比例不正确");
			return;
		}
		if(($("#province").val()=='0' && $("#prodIsp").val()!='')||($("#province").val()!='0' && $("#prodIsp").val()=='')){
			alert("地区或运营商选择有误");
			return;
		}

  	  var datas = "mobileProvinces="+$("#province").val()+"&bossType="+$("#prodIsp").val()+"&channelCode="+channelCodeStr+"&ratio="+ratioStr;
	  $.ajax({
			type: "POST",
			url: "${ctx}/mobileChannelRule.do?method=create",
			data: datas,
			success: function(s) {
				if(s!=""){
					alert("产品添加失败(该规则可能已存在)");
					return ;
				}
				alert("产品添加成功");
				resetForm();
			}
		});
	});

	$("#province").change(function(){
		if($("#province").val()=='0'){
			$("#default").attr("selected","selected");
		}
		if($("#province").val()!='0'&&$("#prodIsp").val()==''){
			$("#channelTd").html("");
		}else{
			  $("select[name=channelCode]").html("");
			  $.getJSON(
					  "${ctx}/mobileChannelRule.do?method=channelList&bossType="+$("#prodIsp").val()+"&mobileProvinces="+$("#province").val(),
					  function(data){
						  $("#channelTd").html("");
						  for(var i=0;i<data.length;i++){
								$("#channelTd").append('<td class="td1">充值渠道</td><td><select name="channelCode"></select></td>');
								$("#channelTd").append('<td class="td1">比例</td><td><input type="text" name="ratio"/>%</td>');
						  	}
						  for(var i=0;i<data.length;i++){
								$("select[name=channelCode]").append('<option value='+data[i].code+'>'+data[i].name+'</option>');
							}
					}
				);
		}
	});
	$("#prodIsp").change(function(){
		if($("#prodIsp").val()==''){
			$("#init").attr("selected","selected");
		}
			  $("select[name=channelCode]").html("");
			  $.getJSON(
					  "${ctx}/mobileChannelRule.do?method=channelList&bossType="+$("#prodIsp").val()+"&mobileProvinces="+$("#province").val(),
					  function(data){
						  $("#channelTd").html("");
						  for(var i=0;i<data.length;i++){
								$("#channelTd").append('<td class="td1">充值渠道</td><td><select name="channelCode"></select></td>');
								$("#channelTd").append('<td class="td1">比例</td><td><input type="text" name="ratio"/>%</td>');
						  	}
						  for(var i=0;i<data.length;i++){
								$("select[name=channelCode]").append('<option value='+data[i].code+'>'+data[i].name+'</option>');
							}
					}
				);
	});
});

function closeTab(){
	parent.closePage("${ctx}/mobileChannelRule.do?method=create");
	
}

function resetForm(){
	//清空文本框
	 $('input[type=text]').each(function(){
		 $(this).val(""); 
	  });
	}
$(document).ready(function(){
	  $.getJSON(
			  "${ctx}/mobileChannelRule.do?method=channelList",
			  function(data){
				  for(var i=0;i<data.length;i++){
						$("#channelTd").append('<td class="td1">充值渠道</td><td><select name="channelCode"></select></td>');
						$("#channelTd").append('<td class="td1">比例</td><td><input type="text" name="ratio"/>%</td>');
				  	}
				  for(var i=0;i<data.length;i++){
						$("select[name=channelCode]").append('<option value='+data[i].code+'>'+data[i].name+'</option>');
					}
			}
		);
	  $.getJSON(
			  "${ctx}/mobileChannelRule.do?method=provinceList",
			  function(data_p){
				  for(var i=0;i<data_p.length;i++){
						$("#province").append('<option value='+data_p[i].code+'>'+data_p[i].name+'</option>');
					}
			}
		);
});
</script>
</head>
<body>
     
<table cellSpacing=0 cellPadding=0 width="98%" border=0 style="margin-top: 20px;">
   <tbody>
     <tr>
       <td vAlign=top width="100%" bgColor=#ffffff>
       
     
          <table borderColor="#cccccc" cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
            <tbody>
                 <tr>
                     <td height=25>
                     <form action="" name="command" id="command">
                       <table cellSpacing="0" cellPadding="2" border="0" align="center">
                         <tbody>
                              <tr >
                         		 <td class="td1">支持地区</td>
                                 <td>
                                 	<select id="province" name="mobileProvinces">
										<option id="init" value="0">默认</option>
									</select>
                                 </td>
							 </tr>
							 <tr>
							     <td class="td1">运营商</td>
                                 <td> <select id="prodIsp" name="bossType">
                                 		<option value="" id="default">默认</option>
                                 		<option value="0">移动</option>
										<option value="1">联通</option>
										<option value="2">电信</option>
									</select>
								</td>
							 </tr>
							 <tr id="channelTd">
							 	
							 </tr>							 
							 <tr>
							       <td align="center">
                                        <input class="button"  type="button" value="保存 " id="saveRule"/>
                                   </td>
                                   <td align="center">
                                        <input class="button"  type="button" value="清空 " onclick="resetForm();" />
                                  </td>
                                  <td align="center">
                                        <input class="button"  type="button" value="返回 " onclick="closeTab();" />
                                  </td>
                                  
                              </tr> 
                                   </tbody>
                                   </table>
                           </form>
                   </td></tr>
     </tbody>
     </table>
     </tbody>
   </table>
</body>
</html>