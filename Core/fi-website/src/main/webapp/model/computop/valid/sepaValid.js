$().ready(function(){
	 $("#SEPA_from").validate({
		 rules: {
			 accName: {
		        required: true
		      },
		      IBAN: {
		    	  required: true,
		    	  maxlength:34
		      },
		      bankName: {
		    	  required: true,
		      },
		      AccNumber: {
		    	  required: true,
		    	  maxlength:34
		      },
		      accEmail:{
		    	  required: true,
		    	  email:true
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
