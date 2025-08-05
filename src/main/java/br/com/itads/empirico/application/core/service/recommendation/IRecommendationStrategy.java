package br.com.itads.empirico.application.core.service.recommendation;

import br.com.itads.empirico.application.core.domain.Recommendation;

public interface IRecommendationStrategy {

	Recommendation buildRecommendationFor(String assetTicker, String userId, String walletId);
	
}
