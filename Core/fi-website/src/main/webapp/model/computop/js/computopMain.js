$(function(){
	init();
})
function getData(){
	var data=$("#orgCodeAndUrl").val(); 
	data=$.parseJSON(data);
	return data;
}
function getDataName(){
	var data=$("#orgCodeAndUrlName").val();
	data=$.parseJSON(data);
	return data;
}
function init(){
	var length=0;
	var data=getData();
	var dataName=getDataName();
	var labVal="";
	var index1="";
	$.each(data,function(index,val){
		length++;
		if(length==1){
			index1=index;
			labVal=val.replace("b_","");
			if(index=="10077002"){
				labVal=val;
			}
		}
	})
	if(length==1){
		show_form(labVal,index1);
	}else{
		initIcon(data,dataName);
	}
}
function initIcon(data,dataName){
	var context="";
	var test="";
	var m_index=0;
	$.each(data,function(index,val){
		var labVal=val.replace("b_","");
		var m_val=val.substring(0,2);
		context="<div class='small-12  medium-6  large-3 columns end' style='text-align: center;'>" +
		"<a  class='th'>" +
		"<img alt='' src='"+basePath+"/static/icon/smillIcon/"+val+".png' onclick=img_click('"+labVal+"','"+index+"');> " +
		"</a>" +
		"<label style='font-weight: bold;height: 50px'>"+dataName[index]+"</label>" +
		"</div>";
		if(m_val=="b_"&&val!="b_CreditCard"){
			$("#iconContext").append(context);
		}
		if(index=="10077002"){
			labVal=val.replace("m_","");
			context="<div class='small-12  medium-6  large-3 columns end' style='text-align: center;'>" +
			"<a  class='th'>" +
			"<img alt='' src='"+basePath+"/static/icon/smillIcon/"+val+".png' onclick=img_click('"+val+"','"+index+"');> " +
			"</a>" +
			"<label style='font-weight: bold;height: 50px'>"+dataName[index]+"</label>" +
			"</div>";
			$("#iconContext").append(context);
		}
		
		if(m_index<1){
			if(val=="b_CreditCard"){
				labVal=val.replace("b_","");
				m_index++;
				context="<div class='small-12  medium-6  large-3 columns end' style='text-align: center;'>" +
				"<a  class='th'>" +
				"<img alt='' src='"+basePath+"/static/icon/smillIcon/"+val+".png' onclick=img_click('"+labVal+"','0000');> " +
				"</a>" +
				"<label style='font-weight: bold;height: 50px'>"+dataName[index]+"</label>" +
				"</div>";
				$("#iconContext").prepend(context);
			}
		}
	})
}

var all_labVal="";
var orgCode="";
function show_form(labVal,index){
	all_labVal=labVal;
	orgCode=index;
	$("#iconContext").hide();
	$("#headTip").hide();
	if(index == '10077003'){
		$("#content").hide();
		$("#order_form").submit();
		return;
	}
	
	$("#headTip_one").show();
	$("."+labVal+"_fromContext").slideDown();
	$("#pay").removeAttr("disabled");
	if(labVal=="PagDebitCard"){
		createPagDebitCardCountry("pagDebitCard_from_select");
	}
	if(labVal=="SEPA"){
		createPagDebitCardCountry("sepa_from_select");
	}
}
function img_click(labVal,index){
	all_labVal=labVal;
	orgCode=index;
	if(index == '10077003'){
		$("#order_form").submit();
		return;
	}
	$("#iconContext").hide();
	$("."+labVal+"_fromContext").slideDown();
	$("#headTip").html("Please fill in the information");
	$("#Cancel").removeAttr("disabled"); 
	$("#pay").removeAttr("disabled");
	if(labVal=="PagDebitCard"){
		createPagDebitCardCountry("pagDebitCard_from_select");
	}
	if(labVal=="SEPA"){
		createPagDebitCardCountry("sepa_from_select");
	}

}
function Cancel_click(){
	$("#iconContext").slideDown();
	$("."+all_labVal+"_fromContext").hide();
	$("#headTip").html("Select Payment Methods");
	$("#Cancel").attr("disabled","disabled"); 
	$("#pay").attr("disabled","disabled"); 
	var validator=$("#"+all_labVal+"_from").validate();
	validator.resetForm();
	$("#"+all_labVal+"_from")[0].reset();
}
$("#order_form").submit(function(){
	if(all_labVal=="QIWI"){
		qiwiOp();
	}
	if(orgCode == "10077003"){
		$("#orgCode").val(orgCode);
		return true;
	}
	if($("#"+all_labVal+"_from").valid()){
		var payment_form = $("#"+all_labVal+"_from").form2json();
		$("#PaymentText").val(JSON.stringify(payment_form));
		$("#orgCode").val(orgCode);	
	}else{
		return false;		
	}
})
function qiwiOp(){
	var accCountryCode=$("#qiwi_accCountryCode").find("option:selected").text().replace("+","");
	var mobileNumber=accCountryCode+$("#mobileNumber").val();;
	$("#countryMobileNumber").val(mobileNumber);
}
function createPagDebitCardCountry(from){
	$("#"+from+"").find("option").remove();
	 $.ajax({
			url : basePath+'/static/json/CountryJson',
			type : 'POST',
			dataType : 'json',
			traditional:true,
		}).done(
				function(data) {
					$.each((data),function(index,val){  
							$("#"+from+"").append("<option value="+val.number+">"+val.countrysimpl+"</option>");
					})
		}).fail(function() {
				console.log("error");
		});
}
