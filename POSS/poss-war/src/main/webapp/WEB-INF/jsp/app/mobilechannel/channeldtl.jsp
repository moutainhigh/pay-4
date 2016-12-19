<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<style type="text/css">
.table{margin-top:20px;border-top-width: 0px; font-weight: normal; border-left-width: 0px; border-left-color: #cccccc; border-bottom-width: 0px; border-bottom-color: #cccccc; width: 60%; border-top-color: #cccccc; font-style: normal; background-color: #cccccc; border-right-width: 0px; text-decoration: none; border-right-color: #cccccc;}
.td1{font-weight: bold; font-style: normal; background-color: #eeeeee; text-decoration: none;text-align: right;}
.td2{font-weight: normal; font-style: normal; background-color: white; text-decoration: none;}
tr{height: 25px;}
.button {border-right: #888888 1px solid; border-top: #f4f4f4 1px solid; border-left: #f4f4f4 1px solid; COLOR: #000000; padding-top: 2px; border-bottom: #888888 1px solid}
</style>
<script type="text/javascript">
function closeTab(sqId){
	parent.closePage("${ctx}/mobileChannel.do?method=showDetail&id=" + sqId);
	
}

</script>


</head>
<body>
   <table class="table" cellSpacing="1" cellPadding="2" rules="all" border="0" >
            <tr>
                 <td class="td1">渠道编号</td>
                 <td class="td2">${channelDetail.code}</td>
            </tr>
            <tr>
                 <td class="td1">渠道名称</td>
                 <td class="td2">${channelDetail.name}</td>
            </tr>
            <tr>
                 <td class="td1">渠道折扣</td>
                 <td class="td2">${channelDetail.discount}</td>
            </tr>
            <tr>
                 <td class="td1">支持地区</td>
                 <td class="td2">${channelDetail.provName}</td>
            </tr>
            <tr>
                 <td class="td1">支持金额</td> 
                 <td class="td2">${channelDetail.amounts}</td>
              </tr>
              <tr>
                 <td class="td1">系统科目账户</td>
                 <td class="td2">${channelDetail.memberAccountCode}</td> 
            </tr>   
            <tr>
                 <td class="td1">渠道打开时间</td>
                 <td class="td2">${refund.startTime}</td>
            </tr>
            <tr>
                 <td class="td1">渠道关闭时间</td>
                 <td class="td2">${channelDetail.closeTime}</td>
            </tr>
            <tr>
                 <td class="td1">渠道状态</td>
                  <td class="td2">
                  <c:if test="${channelDetail.status eq 1}">打开</c:if>
                  <c:if test="${channelDetail.status eq 0}">关闭</c:if>
                  </td>
            </tr>
            <tr>
                 <td class="td1">充值类型</td>
                 <td class="td2">
                 <c:if test="${channelDetail.chargeType eq 0}">慢充</c:if>
                 <c:if test="${channelDetail.chargeType eq 1}">快充</c:if>
                 </td>
            </tr>
            <tr>
                 <td class="td1">运营商</td>
                 <td class="td2">
                 <c:if test="${channelDetail.bossType eq 0}">移动</c:if>
                 <c:if test="${channelDetail.bossType eq 1}">联通</c:if>
                 <c:if test="${channelDetail.bossType eq 2}">电信</c:if>
                 </td>
            </tr>
            <tr>
              <td colspan="2" align="center" style="border: 0px;" class="td2"><button class="button"  type="button" onclick="closeTab(${channelDetail.id})";">关闭</button></td>
            </tr>
         </table>
</body>
</html>
