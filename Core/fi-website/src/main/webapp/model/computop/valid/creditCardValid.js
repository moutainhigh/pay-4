$().ready(function(){
	 $("#CreditCard_from").validate({
		 rules: {
			 cardHolderNumber: {
		        required: true,
		        rangelength:[16,19],
		        digits:true
		      },
		      securityCode: {
		    	  required: true,
		    	  rangelength:[3,4],
		    	  digits:true
		      },
		      cardHolderFirstName: {
		    	  required: true
		      },
		      cardHolderLastName: {
		    	  required: true
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
