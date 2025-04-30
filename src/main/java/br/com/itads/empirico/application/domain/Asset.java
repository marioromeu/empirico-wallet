package br.com.itads.empirico.application.domain;

import br.com.itads.empirico.application.domain.enums.AssetClassEnum;

public class Asset {
	
	private String ticker;
	private String description;
	private AssetClassEnum type;

	public Asset(
			String ticker,
			String description,
			AssetClassEnum type
			) {
		this.ticker = ticker;
		this.description = description;
		this.type = type;
	}

	public String getTicker() {
		return ticker;
	}

}