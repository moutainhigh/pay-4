$().ready(function(){
	 $("#POLI_from").validate({
		 rules: {
			 accName: {
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
