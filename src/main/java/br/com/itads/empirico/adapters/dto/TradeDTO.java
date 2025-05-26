package br.com.itads.empirico.adapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Trade;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;

public class TradeDTO {

	private UUID uuid;
	private String description;
	private LocalDateTime datetime;
	private BigDecimal quantity;
	private BigDecimal price;
	private String assetTicker;
	private String assetDescription;
	private String assetClass;
	
	public TradeDTO(
		UUID uuid,
		String description,
		LocalDateTime datetime,
		BigDecimal quantity,
		BigDecimal price,
		String assetTicker,
		String assetDescription,
		String assetClass) {
		
		this.uuid = uuid;
		this.description = description;
		this.datetime = datetime;
		this.quantity = quantity;
		this.price = price;
		this.assetTicker = assetTicker;
		this.assetDescription = assetDescription;
		this.assetClass = assetClass;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getAssetTicker() {
		return assetTicker;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public String getAssetClass() {
		return assetClass;
	}

	public Trade toDomain() {		
		Asset asset = new Asset(assetTicker, assetDescription, AssetClassEnum.valueOf(assetClass));		
		return new Trade(uuid, description, datetime, quantity, price, asset);
	}
	
}
