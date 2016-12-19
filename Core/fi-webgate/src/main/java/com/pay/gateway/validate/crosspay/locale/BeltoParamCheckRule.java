package com.pay.gateway.validate.crosspay.locale;

import org.springframework.util.StringUtils;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.rule.MessageRule;

public class BeltoParamCheckRule extends MessageRule {

	private String messageEn;

	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayGatewayRequest onlineRequestDTO = (CrosspayGatewayRequest) validateBean;
		CrosspayGatewayResponse onlineResponseDTO = onlineRequestDTO
				.getGatewayResponseDTO();

		String tradeType = onlineRequestDTO.getTradeType();
		String language = onlineRequestDTO.getLanguage();
		if (tradeType.equals(TradeTypeEnum.LOCALE_BOLETO.getCode())) {
			String birthDate = onlineRequestDTO.getBirthDate();
			String document = onlineRequestDTO.getDocument();
			String streetNumber = onlineRequestDTO.getStreetNumber();
			String name=onlineRequestDTO.getBillName();
			String email=onlineRequestDTO.getBillEmail();
			String address=onlineRequestDTO.getBillAddress();
			String zipcode=onlineRequestDTO.getBillPostalCode();
			String city=onlineRequestDTO.getBillCity();
			String phoneNumber=onlineRequestDTO.getBillPhoneNumber();
			String state=onlineRequestDTO.getBillState();
			String countryCode=onlineRequestDTO.getBillCountryCode();
			
			if(StringUtils.isEmpty(name)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(email)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(address)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(zipcode)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(city)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(phoneNumber)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(state)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(countryCode)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			
			if(StringUtils.isEmpty(birthDate)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			if(StringUtils.isEmpty(document)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
			if(StringUtils.isEmpty(streetNumber)){
				if("cn".equals(language))
					onlineResponseDTO.setResultMsg(getMessage());
				else
					onlineResponseDTO.setResultMsg(getMessageEn());
				return false;
			}
		}
		return true;
	}

}
