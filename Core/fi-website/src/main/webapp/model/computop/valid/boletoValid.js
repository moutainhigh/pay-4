$().ready(function(){
	 $("#Boleto_from").validate({
		 rules: {
			 taxNumber: {
		        required: true,
		        rangelength:[11,14],
		        digits:true
		      }
		    },
			errorPlacement : function(error, element) {
				if (element.attr("name") == "fname" || element.attr("name") == "lname")   
					error.insertAfter("#lastname");
				else    
					error.insertAfter(element);
			}
	 })
})
