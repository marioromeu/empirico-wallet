package br.com.itads.empirico.application.ports.out.repository;

import java.util.List;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.DashboardAdapterDTO;
import br.com.itads.empirico.application.core.domain.Recommendation;

public interface RecommendationRepository extends CRUDRepository<Recommendation, UUID> {

	List<Recommendation> getListOfRecommendationByAssetTicker(String ticker);
	
	List<Recommendation> getListOfRecommendationByAsset(DashboardAdapterDTO dto);

	void saveAll(List<Recommendation> recommendations);

}
