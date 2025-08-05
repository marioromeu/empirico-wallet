package br.com.itads.empirico.adapters.out.repository.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.DashboardAdapterDTO;
import br.com.itads.empirico.application.core.domain.Recommendation;
import br.com.itads.empirico.application.ports.out.repository.RecommendationRepository;

public class RecommendationRepositoryImpl extends FileRepository<Map<UUID, Recommendation>> implements RecommendationRepository {

	private Map<UUID, Recommendation> internalMap;
	public static final RecommendationRepositoryImpl INSTANCE = new RecommendationRepositoryImpl();
		
	private RecommendationRepositoryImpl() {
		super("Recommendation");
		internalMap = read();
		if (Objects.isNull( internalMap )) {
			internalMap = new HashMap<>();
		}
	}
	
	@Override
	public Recommendation saveOrUpdate(Recommendation recommendation) {
		internalMap.put(recommendation.getUuid(), recommendation);
		internalMap = write(internalMap);
		return recommendation;
	}

	@Override
	public Recommendation getBy(UUID uuid) {
		return internalMap.get( uuid );
	}

	@Override
	public List<Recommendation> getListOfRecommendationByAssetTicker(String ticker) {
		return internalMap.entrySet().stream()
				.filter(entry -> entry.getValue().getAsset().getTicker().equals(ticker))
				.map(Map.Entry::getValue)
				.toList();
	}

	@Override
	public List<Recommendation> getListOfRecommendationByAsset(DashboardAdapterDTO dto) {
		return internalMap.entrySet().stream()
			.filter(entry -> entry.getValue().getAsset().getTicker().equals(dto.dto().asset()))
			.map(Map.Entry::getValue)
			.toList();
	}

	@Override
	public void saveAll(List<Recommendation> recommendations) {
		for (Recommendation recommendation : recommendations) {
			saveOrUpdate(recommendation);
		}
		internalMap = write(internalMap);
	}

}
