package br.com.itads.empirico.application.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Trade {

	/**
	 * ID da operação
	 */
	private UUID uuid;
	
	/**
	 * Descrição do trade
	 */
	private String description;
	
	/**
	 * Momento em que o trade aconteceu
	 */
	private LocalDateTime datetime;
	
	/**
	 * quantidade de ativos transacionados
	 */
	private Double quantity;
	
	/**
	 * Preço da cotação do ativo no momento da operação
	 */
	private Double price;
	
	/**
	 * Ativo transacionado
	 */
	private Asset asset;

	public Trade(
			String description,
			LocalDateTime datetime,
			Double quantity,
			Double price,
			Asset asset) {

		this.description = description;
		this.datetime = datetime;
		this.quantity = quantity;
		this.price = price;
		this.asset = asset;
		
	}

	public Double getTotalTradeValue() {
		return quantity * price;
	}

	public Asset getAsset() {
		return asset;
	}

	public UUID getUuid() {
		return uuid;
	}


	public Double getQuantity() {
		return quantity;
	}	
	
}