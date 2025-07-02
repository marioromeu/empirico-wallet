package br.com.itads.empirico.view.importer.trades.strategy.enums;

public enum TradeProcessEnum {

	COMPRA,
	DIVIDENDO,
	JUROS,
	CISAO,
	BONIFICACAO,
	RENDIMENTO,
	GRUPAMENTO,
	INCORPORACAO,
	ATUALIZACAO,
	DESDOBRAMENTO,
	VENDA,
	RESTITUICAO,
	AJUSTE,
	AMORTIZACAO,
	TRANSFERENCIA,
	IPO;	
	
	public static String clean(String value) {
		if (value == null) {
			return null;
		}
		value = value
				.replace("ç", "c")
				.replace("á", "a")
				.replace("ã", "a")
				.replace("â", "a")
				.replace("é", "e")
				.replace("ê", "e")
				.replace("í", "i")
				.replace("ó", "o")
				.replace("õ", "o")
				.replace("ô", "o")
				.replace("ú", "u")
				.replace("ü", "u");
		return value.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
	}

}