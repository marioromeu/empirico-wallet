package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Quote implements Serializable {
	
	private static final long serialVersionUID = 6424634013553603720L;

	/**
	 * Dia do ano usado para a cotação. Precisão máxima da aplicação.
	 */
	LocalDateTime localDateTime;
	
	/**
	 * Momento em que foi coletada a cotação.
	 */
	LocalDateTime dateTime;
	
	/**
	 * preço de fechamento do pregão
	 */
	Double closedPrice;

	/**
	 * Ativo cotado
	 */
	Asset asset;

	public Double getClosedPrice() {
		return closedPrice;
	}

	public Asset getAsset() {
		return asset;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

}