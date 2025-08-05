package br.com.itads.empirico.application.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.DashboardAdapterDTO;
import br.com.itads.empirico.application.core.domain.Recommendation;
import br.com.itads.empirico.application.core.service.recommendation.IRecommendationStrategy;
import br.com.itads.empirico.application.core.service.recommendation.RecommendationStrategiesBuilder;
import br.com.itads.empirico.application.ports.out.repository.DumpWalletRecommendationRepository;
import br.com.itads.empirico.application.ports.out.repository.RecommendationRepository;

public class RecommendationService {

	DumpWalletRecommendationRepository dumpWalletRecommendationRepository;
	RecommendationRepository recommendationRepository;

	public RecommendationService(
			DumpWalletRecommendationRepository dumpWalletRecommendationRepository,
			RecommendationRepository recommendationRepository) {
		super();
		this.dumpWalletRecommendationRepository = dumpWalletRecommendationRepository;
		this.recommendationRepository = recommendationRepository;
	}
	
	public List<Recommendation> getRecommendationOfAsset(String ticker, UUID walletUuid) {		
		DashboardAdapterDTO dto = 
				dumpWalletRecommendationRepository.getByAssetTicker(ticker, walletUuid);		
		List<Recommendation> listOfRecommendation = 
				recommendationRepository.getListOfRecommendationByAsset( dto );
		return listOfRecommendation;
	}

	/**
	 * 
	 * @param assetTicker
	 * @param userId
	 * @param walletId
	 */
	public void buildRecommendationFor(String assetTicker, String userId, String walletId) {

		List<IRecommendationStrategy> strategies = RecommendationStrategiesBuilder.INSTANCE.getAllStrategies();
		List<Recommendation> recommendations = new ArrayList<>();

		for (IRecommendationStrategy s : strategies) {

			Recommendation recommendation = s.buildRecommendationFor(assetTicker, userId, walletId);

			if (Objects.nonNull(recommendation)) {
				recommendations.add( recommendation );
			}

		}

		recommendationRepository.saveAll(recommendations);

	}
	
}