<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/inc/header.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/17 0017
  Time: 上午 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>财务数据下载</title>
    <script>
        function changeMonthAndDate(m){
            if(m.length == 1){
                return "0" + m;
            }else{
                return m;
            }
        }



        $(document).ready(function(){
            var date1 = new Date();
            var month1 = date1.getMonth()+1 + "";
            month1 = changeMonthAndDate(month1);
            var day1 = date1.getDate() + "";
            day1 = changeMonthAndDate(day1);
            $('#endDate').val(date1.getFullYear()+"-"+month1+"-"+day1);

            var date2 = new Date(date1);

            date2.setDate(date1.getDate() - 30);
            var month2 = date2.getMonth()+1 + "";
            month2 = changeMonthAndDate(month2);
            var day2 = date2.getDate() + "";
            day2 = changeMonthAndDate(day2);
            $('#startDate').val(date2.getFullYear()+"-"+month2+"-"+day2);

        });
        function downloadClearingData(){
            var downloadCheckbox = document.getElementsByName('downloadData');
            var value = "";
            for(var i = 0; i < downloadCheckbox.length; i++){
                if(downloadCheckbox[i].checked)
                    value = value + downloadCheckbox[i].value + "|";
            }
            if(value == ""){
                alert("请选择要下载的数据!");
                return;
            }

            var params = {};
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var status = $("#status").val();
            if(isNullStr(startDate) || isNullStr(endDate)){
                alert("日期不能为空");
                return;
            }
            if(!isNullStr(startDate)){
                params.startDate = startDate;
            }
            if(!isNullStr(endDate)){
                params.endDate = endDate;
            }
            if(!isNullStr(startDate) && !isNullStr(endDate)){
                var s1 = new Date(startDate.replace(/-/g, "/"));
                var s2 = new Date(endDate.replace(/-/g, "/"));
                if(s2 < s1){
                    alert("结束时间不能小于开始时间");
                    return;
                }
                var days = s2.getTime() - s1.getTime();
                var time = parseInt(days / (1000 * 60 * 60 * 24));
                if(time > 30){
                    alert("查询时间区间不能超过30天");
                    return;
                }

            }

            if(!isNullStr(status)){
                params.status = status;
            }

            params.downloadType = value;



            var flag = confirm("确定要下载吗？");
                if (flag) {
                   // alert(params);
                    downloadClearing(path + "/riskAndClearingDownload/downloadData.do", params);

                }


        }

        function checkboxchange(){
            var allCheckFlg = $("#allcheck").attr("checked");
            if(allCheckFlg==true){
                $("[name='downloadData']").attr("checked",'true');
            }else{
                $("[name='downloadData']").removeAttr("checked");
            }
        }
    </script>
</head>
<body>
<h2 class="panel_title">财务数据查询</h2>
<table class="border_all2" width="80%" border="0" cellspacing="0"
       cellpadding="1" align="center">
    <tr class="trForContent1">
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value = '1'/>已清算订单表
        </td>
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value='2'/>拒付订单表
        </td>
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value='3'/>每日清算保证金表
        </td>
    </tr>
    <tr class="trForContent1">
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value='4'/>未清算订单表
        </td>
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value='5'/>退款订单表
        </td>
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value='6'/>中银MIGS退款订单
        </td>
    </tr>
    <tr class="trForContent1">
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value="7"/>归还保证金表
        </td>
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value="8"/>每日交易数据表
        </td>
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value="9"/>详细信息表
        </td>
    </tr>
    <tr class="trForContent1">
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value="10"/>风控手续费表
        </td>
        <td class="border_top_right4">
            <input type="checkbox" name="downloadData" value="11"/>每日清算基本户表
        </td>
        <td class="border_top_right4">

        </td>
    </tr>
    <tr class="trForContent1">

        <td colspan="2" class="border_top_right4">
            时间:<input type="text"  value="" name="startDate" id="startDate" class="Wdate" style="width: 150px;" onClick="WdatePicker()"/> ~
            <input type="text"  value="" name="endDate" id="endDate" class="Wdate" style="width: 150px;" onClick="WdatePicker()"/>
        </td>
        <td class="border_top_right4">
            状态:
            <select id="status">
                <option value="">--请选择--</option>
                <option value="1">退款中(已发请求给银行)</option>
                <option value="2">退款成功</option>
                <option value="3">退款失败</option>
                <option value="4">机构退款超时</option>
                <option value="5">人工处理</option>
                <option value="6">人工置为失败</option>
            </select>


        </td>
    </tr>
    <tr class="trForContent1">
        <td class="border_top_right4" colspan="3" align="center">
            <input type="button" value="下载" onclick="downloadClearingData();"/>
        </td>
    </tr>
</table>
</body>
</html>
