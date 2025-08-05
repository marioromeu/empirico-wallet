package br.com.itads.empirico.application.core.service.recommendation.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Asset;
import br.com.itads.empirico.application.core.domain.Recommendation;
import br.com.itads.empirico.application.core.domain.enums.AssetClassEnum;
import br.com.itads.empirico.application.core.domain.enums.RecommendationTypeEnum;
import br.com.itads.empirico.application.core.service.recommendation.IRecommendationStrategy;

public class MarioRecommendation implements IRecommendationStrategy {

	@Override
	public Recommendation buildRecommendationFor(String assetTicker, String userId, String walletId) {

		Recommendation recommendation = null;
		
		if (assetTicker.equals("OIBR3")) {		
			recommendation = new Recommendation(
					UUID.randomUUID(),
					LocalDateTime.now(),
					"Mario que inventou isso",
					LocalDateTime.now(),
					RecommendationTypeEnum.COMPRA,
					new Asset("OIBR3", "Oi Telecom", AssetClassEnum.ACOES_BR),
					2d,
					"http://www.google.com.br"
			);
		}
		
		return recommendation;
	}

}
