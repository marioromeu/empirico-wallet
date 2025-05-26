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
}