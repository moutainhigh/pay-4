$().ready(function(){
	 $("#Sofort_from").validate({
		 rules: {
			 accName: {
		        required: true
		      },
		      AccNumber: {
		    	  required: true,
		    	  maxlength:12,
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
