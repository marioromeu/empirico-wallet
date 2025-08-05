package br.com.itads.empirico.adapters.out.repository.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.DashboardAdapterDTO;
import br.com.itads.empirico.adapters.dto.WalletDashboardAdapterDTO;
import br.com.itads.empirico.application.ports.out.repository.DumpWalletRecommendationRepository;

public class DumpWalletRecommendationRepositoryImpl extends FileRepository<Map<UUID, WalletDashboardAdapterDTO>> implements DumpWalletRecommendationRepository {

	private Map<UUID, WalletDashboardAdapterDTO> internalMap;
	public static final DumpWalletRecommendationRepositoryImpl INSTANCE = new DumpWalletRecommendationRepositoryImpl();
		
	private DumpWalletRecommendationRepositoryImpl() {
		super("DumpWalletRecommendation");
		internalMap = read();
		if (Objects.isNull( internalMap )) {
			internalMap = new HashMap<>();
		}
	}
	
	@Override
	public WalletDashboardAdapterDTO saveOrUpdate(WalletDashboardAdapterDTO recommendation) {
		internalMap.put(recommendation.walletUuid(), recommendation);
		internalMap = write(internalMap);
		return recommendation;
	}

	@Override
	public WalletDashboardAdapterDTO getBy(UUID uuid) {
		return internalMap.get( uuid );
	}

	@Override
	public DashboardAdapterDTO getByAssetTicker(String ticker, UUID walletUuid) {
		WalletDashboardAdapterDTO dto = internalMap.get(walletUuid);
		return dto.dto().stream().filter(dash -> {
			return dash.dto().asset().equals(ticker);
		}).findFirst().orElse(null);
		
	}

}
