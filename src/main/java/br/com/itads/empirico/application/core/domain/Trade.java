package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class Trade implements Serializable {

	private static final long serialVersionUID = 1694203218650842338L;

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
	private BigDecimal quantity;
	
	/**
	 * Preço da cotação do ativo no momento da operação
	 */
	private BigDecimal price;
	
	/**
	 * Ativo transacionado
	 */
	private Asset asset;

	public Trade(
			UUID uuid,
			String description,
			LocalDateTime datetime,
			BigDecimal quantity,
			BigDecimal price,
			Asset asset) {

		this.uuid = uuid;
		this.description = description;
		this.datetime = datetime;
		this.quantity = quantity;
		this.price = price;
		this.asset = asset;
		
	}

	public BigDecimal getTotalTradeValue() {
		return quantity.multiply( price );
	}

	public Asset getAsset() {
		return asset;
	}

	public UUID getUuid() {
		return uuid;
	}


	public BigDecimal getQuantity() {
		return quantity;
	}	
	
}