$().ready(function(){
	 $("#m_Boleto_from").validate({
		 rules: {
			 cpf: {
		        required: true,
		        maxlength:18
		      },
		      firstName: {
		    	  required: true
		      },
		      lastName: {
		    	  required: true
		      },
		      email: {
		    	  required: true,
		    	  email:true
		      },
		      telephone: {
		    	  required: true
		      },
		      birthDate: {
		    	  required: true
		      },
		      zipCode: {
		    	  required: true
		      },
		      city:{
			    required: true,
			  },
			  address:{
			    required: true,
			  },
			  streetNumber:{
				  required: true,
			  },
			  state:{
				  required: true,
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
