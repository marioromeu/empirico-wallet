package br.com.itads.empirico.application.core.service.recommendation;

import java.util.ArrayList;
import java.util.List;

import br.com.itads.empirico.application.core.service.recommendation.impl.MarioRecommendation;

public class RecommendationStrategiesBuilder {

	List<IRecommendationStrategy> internalList = new ArrayList<>();
	
	public static RecommendationStrategiesBuilder INSTANCE = new RecommendationStrategiesBuilder();
	
	private RecommendationStrategiesBuilder() {
		internalList.add( new MarioRecommendation() );
	}

	public List<IRecommendationStrategy> getAllStrategies() {
		return internalList;
	}

}
