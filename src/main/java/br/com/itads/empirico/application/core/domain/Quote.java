package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Quote implements Serializable {
	
	private static final long serialVersionUID = 6424634013553603720L;
	
	/**
	 * Momento em que foi coletada a cotação.
	 */
	LocalDateTime localDateTime;

	/**
	 * preço de fechamento do pregão
	 */
	BigDecimal closedPrice;

	/**
	 * Ativo cotado
	 */
	Asset asset;
	
	public Quote(LocalDateTime localDateTime, BigDecimal closedPrice, Asset asset) {
		super();
		this.localDateTime = localDateTime;
		this.closedPrice = closedPrice;
		this.asset = asset;
	}

	public BigDecimal getClosedPrice() {
		return closedPrice;
	}

	public Asset getAsset() {
		return asset;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

}