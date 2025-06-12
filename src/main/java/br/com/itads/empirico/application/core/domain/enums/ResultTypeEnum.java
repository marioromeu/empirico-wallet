package br.com.itads.empirico.application.core.domain.enums;

public enum ResultTypeEnum {

	JCP, DIVIDENDO, RENDIMENTO, ALUGUEL, CORRETAGEM, COME_COTAS;
	
	public static ResultTypeEnum getResultCategory(String resultCategory) {
		switch (resultCategory) {
		case "Dividendo": {
			return ResultTypeEnum.DIVIDENDO; 
		}
		case "Juros": {
			return ResultTypeEnum.JCP; 
		}
		case "Rendimento": {
			return ResultTypeEnum.RENDIMENTO; 
		}
		case "Aluguel": {
			return ResultTypeEnum.ALUGUEL; 
		}
		case "Corretagem": {
			return ResultTypeEnum.CORRETAGEM; 
		}
		case "ComeCotas": {
			return ResultTypeEnum.COME_COTAS; 
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + resultCategory);
		}

	}   
	
}
