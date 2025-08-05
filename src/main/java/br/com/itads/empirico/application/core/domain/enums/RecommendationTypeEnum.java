package br.com.itads.empirico.application.core.domain.enums;

public enum RecommendationTypeEnum {

	COMPRA,
	NEUTRO,
	VENDA;
	
	public String getHtmlBackgroundTagColor() {
		switch (this) {
			case COMPRA:
				return " style=\"background-color:#aaffaa;\"";
			case NEUTRO:
				return " style=\"background-color:#aaaaff;\"";
			case VENDA:
				return " style=\"background-color:#ffaaaa;\"";
			default:
				return " style=\"background-color:#aaaaaa;\"";
		}
	}
	
	public String getHtmlSpanTagColor() {
		switch (this) {
			case COMPRA:
				return " style=\"color:#aaffaa;\"";
			case NEUTRO:
				return " style=\"color:#aaaaff;\"";
			case VENDA:
				return " style=\"color:#ffaaaa;\"";
			default:
				return " style=\"color:#aaaaaa;\"";
		}
	}
	
}
