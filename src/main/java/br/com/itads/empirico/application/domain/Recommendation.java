package br.com.itads.empirico.application.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.application.domain.enums.RecommendationTypeEnum;

public class Recommendation {
	
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