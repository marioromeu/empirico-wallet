package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.enums.RecommendationTypeEnum;

public class Recommendation implements Serializable {
	
	private static final long serialVersionUID = 2495872470511378728L;

	/**
	 * ID único da recomendação
	 */
	UUID uuid;
	
	/**
	 * Data de abertura da recomendação
	 */
	LocalDateTime initDateTime;
	
	/**
	 * Descrição do racional da recomendação
	 */
	String description;
	
	/**
	 * Data de finalização da recomendação
	 */
	LocalDateTime endDateTime;
	
	/**
	 * Tipo da recomendação
	 */
	RecommendationTypeEnum recommendationTypeEnum;

	/**
	 * Tipo da recomendação
	 */
	Asset asset;
	
	/**
	 * Grau de recomendação, de 0 a 10, onde 10 é a recomendação mais forte
	 */
	private Double degree;
	
	/**
	 * Grau de recomendação, de 0 a 10, onde 10 é a recomendação mais forte
	 */
	private String link;	

	public Recommendation(
			UUID uuid, 
			LocalDateTime initDateTime, 
			String description, 
			LocalDateTime endDateTime,
			RecommendationTypeEnum recommendationTypeEnum,
			Asset asset,
			Double degree,
			String link) {
		super();
		this.uuid = uuid;
		this.initDateTime = initDateTime;
		this.description = description;
		this.endDateTime = endDateTime;
		this.recommendationTypeEnum = recommendationTypeEnum;
		this.asset = asset;
		this.degree = degree;
		this.link = link;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UUID getUuid() {
		return uuid;
	}

	public LocalDateTime getInitDateTime() {
		return initDateTime;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public RecommendationTypeEnum getRecommendationTypeEnum() {
		return recommendationTypeEnum;
	}
	
	public Asset getAsset() {
		return asset;
	}
	
	public Double getDegree() {
		return degree;
	}
	
	public String getLink() {
		return link;
	}
	
}