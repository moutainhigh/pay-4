$().ready(function(){
	 $("#QIWI_from").validate({
		 rules: {
			 accName: {
		        required: true
		      },
		      mobileNumber_show: {
		    	  required: true,
		    	  maxlength:11,
		    	  minlength:8
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
