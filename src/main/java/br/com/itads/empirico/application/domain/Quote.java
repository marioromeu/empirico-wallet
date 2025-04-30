package br.com.itads.empirico.application.domain;

import java.time.LocalDateTime;

public class Quote {
	
	/**
	 * Dia do ano usado para a cotação. Precisão máxima da aplicação.
	 */
	Integer dayOfYear;
	
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
}