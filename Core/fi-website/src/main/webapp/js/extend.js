//点击登录按钮事件 自定义
 
 Zepto(function($){
	 
	 function RetRndNum(n) {
        var strRnd = "";
        for (var intI = 0; intI < n; intI++) {
            strRnd += Math.floor(Math.random() * 10);
        }
        return strRnd;
     }
	 /*
	  *index.html
	  */
	 var index_form=function(){

	 var innerhtml = '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>本产品的风险等级可能超出您的风险承受能力，您是否确定购买？</p></div><div id="" class="foot"><a href="purchase_amount.html" class="submit button_a">我愿意承担风险，确定购买</a><a href="javascript:" class="submit button_b close" style="margin-top:14px;">暂不购买</a></div>';
	 openNew(innerhtml,"240px","auto",false);
	 }
	 formAction(index_form,"indexform");
     /*
	  *set_password.html
	  */

	 var password_form=function(){
	 /*var innerhtml = '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>购买本产品年龄必须在18-60周岁之间，您的年龄不符合要求。</p></div><div id="" class="foot"><a href="purchase_amount.html" class="submit button_a">我知道了</a><a href="confirm_info.html" class="submit button_b" style="margin-top:14px;">使用其它账号登录</a></div>';
	 openNew(innerhtml,"240px","auto",false);*/
     window.location.href="register_success.html";
	 return false;
	 }
	 formAction(password_form,"setpassword");


	 var perfect_form=function(){
	 var innerhtml2 = '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>您输入的手机号码有误，请重新输入。</p></div><div id="" class="foot"><a href="javascript:" class="submit button_a">确定</a></div>';
	 openNew(innerhtml2,"240px","auto",false);
	 return false;
	 }
	 formAction(perfect_form,"perfectscene2");
	 /*
	  *sign_up_in.html
	  */
	 var signupin_form=function(){
     window.location.href="login.html";
	 /*var innerhtml = '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>您输入的手机号码有误，请重新输入。</p></div><div id="" class="foot"><a href="javascript:" class="submit button_a">确定</a></div>';
	 openNew(innerhtml,"240px","auto",false);*/
	 return false;
	 }
	 formAction(signupin_form,"signupin");


	 var auth_form=function(){
	 var innerhtml4 = '<div class="title" ><span class="">请选择实名认证方式</span></div><div class="menu"><ul class="media" ><li style="" onclick="window.location.href=\'perfect_scene1.html\'"><div id="" class="icontitle icon_auth1"></div><p>手动录入</p><p>输入姓名和身份证号码进行实名认证</p></li><li><div id="" class="icontitle icon_auth2"></div><p>拍照识别</p><p>拍摄身份证照片，自动识别信息进行实名认证</p></li></ul></div>';
	 openNew(innerhtml4,"240px","auto",true);
	 return false;
	 }
	 formAction(auth_form,"auth");
     var authmode_form=function(){
	 
	 return false;
	 }
	 formAction(authmode_form,"autmodeform");

	 var purchase_form=function(){
	 var innerhtml= '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>您购买的金额超过20万，需要上传身份证照片。</p></div><div id="" class="foot"><div class="row" style="margin-bottom:0px;margin-left:-3px;margin-right:-3px;"><div class="col-xs-6" style="padding-left:3px;padding-right:3px;"><a href="javascript:" class="submit button_b close" >取&nbsp;&nbsp;消</a></div><div class="col-xs-6" style="padding-left:3px;padding-right:3px;"><a href="personal_info.html" class="submit button_a" >确&nbsp;&nbsp;定</a></div></div></div>';
	 openNew(innerhtml,"240px","auto",false);
	  
	  return false;
	 }
	 formAction(purchase_form,"purchase");
      /*
	  *personal_info.html
	 
	 var personal_form=function(){
	 
     var innerhtml = '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>您的年龄超过60岁，不能购买本产品，感谢您对我们的关注。</p></div><div id="" class="foot"><a href="javascript:"  class="submit button_a close">我知道了</a><a href="confirm_info.html" class="submit button_b" style="margin-top:14px;">使用其它账号登录</a></div>';
	 
	 openNew(innerhtml,"240px","auto",false);
	 
	 //window.location.href="confirm_info.html";
	 return false;
	 }
	 formAction(personal_form,"personal"); */
	 /*
	  *personal_info_filled.html
	  
	   var personal_filled_form=function(){
	 
     var innerhtml = '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>您的年龄超过60岁，不能购买本产品，感谢您对我们的关注。</p></div><div id="" class="foot"><a href="personal_info.html" class="submit button_a">我知道了</a><a href="confirm_info.html" class="submit button_b" style="margin-top:14px;">使用其它账号登录</a></div>';
	 openNew(innerhtml,"240px","auto",false);
	 //window.location.href="confirm_info.html";
	 return false;
	 }
	 formAction(personal_filled_form,"personal_filled");
     */
	 var confirm_form=function(){
	 var innerhtml = '<div class="canvasbox zhang"><img src="img/buy_icon_verify.png" /><p>正在进行核保，请稍候···</p></div>';
	 openNew(innerhtml,"270px","auto",false,"windowbg");
     setTimeout(function(){closeNew();window.location.href="commut_finish.html";},5000); 
	 return false;
	 }
	 formAction(confirm_form,"confirm");

     /*
	  *risk_ass.html(评估)
	  */
	 var risk_form=function(){
	  var count=0;
	 var ass={};
	 //var strnid = "note_" + RetRndNum(3);
	 var strnid = "note_ass";
	 $("#risk.form input[type='radio']").each(function(){
		if($(this).prop("checked")){
		count+=parseInt($(this).val());
	    
		}
	 });
     if(count<=20){
	  ass.type="保守型";
	  ass.level="I";

	 }else if(count>=21&&count<=45){
	  ass.type="稳健型";
	  ass.level="I、II";
	 }else if(count>=46&&count<=70){
	  ass.type="平衡型";
	  ass.level="I、II、III";
	 }else if(count>=71&&count<=85){
	  ass.type="成长型";
	  ass.level="I、II、III、IV";
	 }else if(count>=86&&count<=100){
	  ass.type="进取型";
	  ass.level="I、II、III、IV、V";
	 }
     var myDate = new Date();
	 ass.datetime=myDate.getMonth()+"月"+myDate.getDate()+"日"+myDate.toLocaleTimeString();
	 var jsonotedata = JSON.stringify(ass);
     rttophtml5mobi.utils.setParam(strnid, jsonotedata);
	 window.location.href="assess.html";
     
	 return false;
	 }
	
	 formAction(risk_form,"risk");
	 /*
	  *assess.html
	  */
	 var assess_form=function(){
	  var innerhtml = '<div id="" class="head"><p>提示</p></div><div id="" class="body"><p>本产品的风险等级可能超出您的风险承受能力，您是否确定购买？</p></div><div id="" class="foot"><a href="purchase_amount.html" class="submit button_a">我愿意承担风险，确定购买</a><a href="javascript:" class="submit button_b" style="margin-top:14px;">暂不购买</a></div>';
	 openNew(innerhtml,"240px","auto",false);
	 
	 }
     formAction(assess_form,"assess");
	 var addbank_form=function(){
	
	
	 }
	 formAction(addbank_form,"addbank");
	 
	 var perfect_s1_form=function(){
	
	 window.location.href="index.html";
	 }
	 formAction(perfect_s1_form,"perfect_s1");
	 /*
	  *login.html
	  */
	 var psw_form=function(){
	 
	 window.location.href="register.html";
	 }
	 formAction(psw_form,"psw");
	 var register_form=function(){
	 
	 window.location.href="set_password.html";register
	 }
	 formAction(register_form,"register");
     var regseccess_form=function(){
	 
	 window.location.href="perfect_scene1.html";
	 }
	 formAction(regseccess_form,"regseccess");
     var atone_form=function(){
	 
	 
	 }
	 formAction(atone_form,"atone");
	
    function autoHeight(){
	var thisFun;
	thisFun = arguments.callee;
    if (window.innerWidth){
	thisFun.nowWidth = window.innerWidth;
	thisFun.nowHeight = window.innerHeight;
    }else{
	thisFun.nowWidth = document.documentElement.innerWidth;
	thisFun.nowHeight = document.documentElement.innerHeight;
    }
	return thisFun;
    }
    

	 $("#index_a").bind('tap',function(){
	    window.location.href="hot_product.html";
	 });
	  $("#index_b").bind('tap',function(){
	     window.location.href="hot_product.html";
	 });
	  $("#index_c").bind('tap',function(){
	     window.location.href="hot_product.html";
	 });
	  $("#index_d").bind('tap',function(){
	     window.location.href="hot_product.html";
	 });
	  $("#index_e").bind('tap',function(){
	     window.location.href="hot_product.html";
	 });
	  $("#index_f").bind('tap',function(){
	     window.location.href="hot_product.html";
	 });
	  var isedit=false;
	  $("#cards_edit").bind("click",function(){
        if(!isedit){
	    $('.cardlist .cardon').animate({
        opacity: 1,
        marginLeft:'-112px'
        }, 500,
        'ease-out');
		$('.cardlist .cardoff').animate({
        opacity: 1,
        marginLeft:'-112px'
        }, 500,
        'ease-out');
	    $('.icon_del_card').animate({
        opacity: 1
        }, 700,
        'ease-out');
        $("#cards_edit").text("完成");
		}else{
		$('.cardlist .cardon').animate({
        opacity: 1,
        marginLeft:
        '-150px'
        }, 500,
        'ease-out');
		$('.cardlist .cardoff').animate({
        opacity: 1,
        marginLeft:
        '-150px'
        }, 500,
        'ease-out');
	    $('.icon_del_card').animate({
        opacity: 0
        }, 700,
        'ease-out');
        $("#cards_edit").text("编辑"); 
		}
		isedit=!isedit;
	  
	  });
	  /*$(document).ajaxStart(function() {
		loading();
	  }).ajaxStop(function() {
		closeLoading();
	  });
	  $(document).ajaxStop(function() {
		closeLoading();
	  });*/
	  function onBridgeReady() {
		WeixinJSBridge.call('hideOptionMenu');
	  }
	if (typeof WeixinJSBridge == "undefined") {
		if (document.addEventListener) {
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		} else if (document.attachEvent) {
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		}
	} else {
		onBridgeReady();
	}
 })
 