package br.com.itads.empirico.application.core.domain.enums;

public enum ResultTypeEnum {

	JCP, DIVIDENDO, RENDIMENTO, ALUGUEL, CORRETAGEM, COME_COTAS, RESTITUICAO;
	
	public static ResultTypeEnum getResultCategory(String resultCategory) {
		switch (resultCategory) {
		case "DIVIDENDO": {
			return ResultTypeEnum.DIVIDENDO; 
		}
		case "JUROS": {
			return ResultTypeEnum.JCP; 
		}
		case "RENDIMENTO": {
			return ResultTypeEnum.RENDIMENTO; 
		}
		case "ALUGUEL": {
			return ResultTypeEnum.ALUGUEL; 
		}
		case "CORRETAGEM": {
			return ResultTypeEnum.CORRETAGEM; 
		}
		case "COME_COTAS": {
			return ResultTypeEnum.COME_COTAS; 
		}
		case "RESTITUICAO": {
			return ResultTypeEnum.RESTITUICAO; 
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + resultCategory);
		}

	}   
	
}
