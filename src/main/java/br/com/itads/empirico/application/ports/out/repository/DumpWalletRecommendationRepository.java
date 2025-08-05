package br.com.itads.empirico.application.ports.out.repository;
import java.util.UUID;

import br.com.itads.empirico.adapters.dto.DashboardAdapterDTO;
import br.com.itads.empirico.adapters.dto.WalletDashboardAdapterDTO;

public interface DumpWalletRecommendationRepository extends CRUDRepository<WalletDashboardAdapterDTO, UUID> {

	DashboardAdapterDTO getByAssetTicker(String ticker, UUID walletUuid);

}
