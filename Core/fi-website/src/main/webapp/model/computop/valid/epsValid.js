$().ready(function(){
	 $("#EPS_from").validate({
		 rules: {
			 accName: {
		        required: true
		      },
		      IBAN:{
			    required: true,
			    maxlength:34
			  },
			  BIC:{
			    required: true,
			    maxlength:11
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
