package br.com.itads.empirico.adapters.in;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Recommendation;
import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.service.RecommendationService;
import br.com.itads.empirico.application.ports.in.PortRecommendation;

public class RecommendationAdapter implements PortRecommendation {

	private RecommendationService service;
	
	public RecommendationAdapter(RecommendationService service) {
		this.service = service;
	}
	
	@Override
	public void buildRecommendationFor(String assetTicker, String userId, String walletId) {
		service.buildRecommendationFor(assetTicker, userId, walletId);
		
	}

	@Override
	public Map<String, List<Recommendation>> getRecommendationsFor(String assetTicker, User user, UUID walletUuid) {
		Map<String, List<Recommendation>> map = new HashMap<>();
		map.put(assetTicker, service.getRecommendationOfAsset(assetTicker, walletUuid) );
		return map;
	}

}
