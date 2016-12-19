<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/17 0017
  Time: 上午 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/inc/header.jsp"%>
<html>
<head>
    <title>风控数据下载</title>
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

        function downloadTemplate(type){
            var params = {};
            params.downloadType = type;
            downloadClearing(path + "/riskAndClearingDownload/downloadData.do", params);
        }

        function checkboxchange(){
            var allCheckFlg = $("#allcheck").attr("checked");
            if(allCheckFlg==true){
                $("[name='downloadData']").attr("checked",'true');
            }else{
                $("[name='downloadData']").removeAttr("checked");
            }
        }

        function uploadAndDownload(type){
            var inputFile = document.createElement('input');
            inputFile.setAttribute('id', 'fileToUpload');
            inputFile.setAttribute('name', 'fileToUpload');
            inputFile.setAttribute('type', 'file');
            inputFile.setAttribute("style", 'visibility:hidden');
            //inputFile.setAttribute("accept", 'image/!*,text/!*');
            var old = document.getElementById("fileToUpload");
            if (old) {
                document.body.replaceChild(inputFile, old);
            } else {
                document.body.appendChild(inputFile);
            }
            inputFile.click();
            inputFile.onchange = function () {
                $.ajaxFileUpload({
                    url: path + "/riskAndClearingDownload/uploadAndDownload.do",
                    secureuri: false,
                    async: false,
                    data: {type: type},
                    fileElementId: 'fileToUpload',
                    dataType: 'text',
                    success: function (data, status) {
                        var result = data.replace(/<.*?>/ig,"");
                        if(result == 0){
                            alert("文件上传失败");
                        }
                        if(result == 3){
                            alert("未查出数据");
                        }
                    }
                });
            };
        }

    </script>
</head>
<body>
    <h2 class="panel_title">风控数据查询</h2>
    <table class="border_all2" width="80%" border="0" cellspacing="0"
           cellpadding="1" align="center">
        <tr class="trForContent1">
            <td class="border_top_right4">
                <input type="checkbox" name="downloadData" value = '12'/>每日交易量变化报表
            </td>
            <td class="border_top_right4">
                <input type="checkbox" name="downloadData" value='13'/>T-1日交易明细报表
            </td>
            <td class="border_top_right4">
                <input type="checkbox" name="downloadData" value='14'/>渠道二级商户号日报
            </td>
        </tr>
        <tr class="trForContent1">
            <td class="border_top_right4">
                <input type="checkbox" name="downloadData" value='15'/>商户运营交易监控表
            </td>
            <td class="border_top_right4">
                <input type="checkbox" name="downloadData" value='16'/>商户交易数据汇总表
            </td>
            <td class="border_top_right4">
                <input type="checkbox" name="downloadData" value='17'/>102开头的网关订单表
            </td>
        </tr>
        <tr class="trForContent1">
            <td colspan="2" class="border_top_right4">
                时间:<input type="text"  value="" name="startDate" id="startDate" class="Wdate" style="width: 150px;" onClick="WdatePicker()"/> ~
                <input type="text"  value="" name="endDate" id="endDate" class="Wdate" style="width: 150px;" onClick="WdatePicker()"/>
            </td>
            <td class="border_top_right4" align="center">
                <input type="button" value="下载" onclick="downloadClearingData();"/>
            </td>
        </tr>

        <tr class="trForContent1">
            <td class="border_top_right4" colspan="3"></td>
        </tr>
        <tr class="trForContent1">
            <th colspan="3">风控订单原交易查询</th>
        </tr>
        <tr class="trForContent1">
            <td class="border_top_right4" colspan="3">
                <input type="button" value="下载模板" onclick="downloadTemplate(19);"/>
                <input type="button" value="选择文件" onclick="uploadAndDownload(1);"/>
            </td>
        </tr>

        <tr class="trForContent1">
            <td class="border_top_right4" colspan="3"></td>
        </tr>
        <tr class="trForContent1">
            <th colspan="3">拒付交易数据下载</th>
        </tr>
        <tr class="trForContent1">
            <td class="border_top_right4" colspan="3">
                <input type="button" value="下载模板" onclick="downloadTemplate(18);"/>
                <input type="button" value="选择文件" onclick="uploadAndDownload(2);"/>
            </td>
        </tr>
    </table>


</body>
</html>
