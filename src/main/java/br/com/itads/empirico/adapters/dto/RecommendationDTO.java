package br.com.itads.empirico.adapters.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.enums.RecommendationTypeEnum;

public record RecommendationDTO(
	UUID uuid, 
	LocalDateTime initDateTime, 
	String description, 
	LocalDateTime endDateTime,
	RecommendationTypeEnum recommendationTypeEnum,
	Asset asset,
	Double degree,
	String link) {
		
}