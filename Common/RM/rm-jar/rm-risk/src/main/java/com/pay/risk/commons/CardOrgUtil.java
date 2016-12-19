package com.pay.risk.commons;

public class CardOrgUtil {
	
	public static String getCardType(String cardNo){
		int cardLen = cardNo.length();
		
		if(cardLen == 16){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=400000 && subCard <=499999){
				return "VISA";
			}
			if(subCard>=510000 && subCard <=559999){
				return "MASTER";
			}
			if((subCard>=352800 && subCard <=358999)||(subCard>=213100 && subCard <=213199)
					||(subCard>=180000 && subCard <=180099)){
				return "JCB";
			}
		}
		if(cardLen == 14){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=300000 && subCard <=305999){
				return "DC";
			}
			if(subCard>=309500 && subCard <=309599){
				return "DC";
			}
			if(subCard>=360000 && subCard <=369999){
				return "DC";
			}
			if(subCard>=380000 && subCard <=399999){
				return "DC";
			}
		}
		if(cardLen == 15){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=340000 && subCard <=349999){
				return "AE";
			}
			if(subCard>=370000 && subCard <=379999){
				return "AE";
			}
		}
		return null;
	}

}
