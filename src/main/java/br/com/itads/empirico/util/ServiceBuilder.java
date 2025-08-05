package br.com.itads.empirico.util;

import br.com.itads.empirico.adapters.out.repository.file.AssetRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.DumpWalletRecommendationRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.PositionRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.RecommendationRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.TradeRepositoryImpl;
import br.com.itads.empirico.adapters.out.repository.file.WalletRepositoryImpl;
import br.com.itads.empirico.application.core.service.AssetService;
import br.com.itads.empirico.application.core.service.RecommendationService;
import br.com.itads.empirico.application.core.service.TradeService;
import br.com.itads.empirico.application.core.service.WalletService;

public class ServiceBuilder {

	private ServiceBuilder() {
	}
	
	public static AssetService buildAssetService() {
		return new AssetService(AssetRepositoryImpl.INSTANCE);
	}
	
	public static TradeService buildTradeService() {
		return new TradeService(PositionRepositoryImpl.INSTANCE, TradeRepositoryImpl.INSTANCE);
	}
	
	public static WalletService buildWalletService() {
		return new WalletService(
				WalletRepositoryImpl.INSTANCE, 
				PositionRepositoryImpl.INSTANCE,
				DumpWalletRecommendationRepositoryImpl.INSTANCE
		);
	}
	
	public static RecommendationService buildRecommendationService() {
		return new RecommendationService(
				DumpWalletRecommendationRepositoryImpl.INSTANCE,
				RecommendationRepositoryImpl.INSTANCE
		);
	}

}
