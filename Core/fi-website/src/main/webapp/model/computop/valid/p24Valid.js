$().ready(function(){
	 $("#P24_from").validate({
		 rules: {
			 accName: {
		        required: true
		      },
		      accEmail: {
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
