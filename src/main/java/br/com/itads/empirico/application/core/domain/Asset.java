package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;

import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;

public class Asset implements Serializable {
	
	private static final long serialVersionUID = -124336061806215514L;
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

	public String getDescription() {
		return description;
	}

	public AssetClassEnum getType() {
		return type;
	}
	
}