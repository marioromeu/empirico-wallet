package br.com.itads.empirico.application.ports.in;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Recommendation;
import br.com.itads.empirico.application.core.domain.User;

public interface PortRecommendation {

	public void buildRecommendationFor(String assetTicker, String userId, String walletId);

	public Map<String, List<Recommendation>> getRecommendationsFor(String assetTicker, User user, UUID walletUuid);

}
