package br.com.itads.empirico.util;

import br.com.itads.empirico.adapters.in.AssetAdapter;
import br.com.itads.empirico.adapters.in.RecommendationAdapter;
import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;

public class AdapterBuilder {

	private AdapterBuilder() {
	}
	
	public static AssetAdapter buildAssetAdapter() {
		return new AssetAdapter(ServiceBuilder.buildAssetService());
	}
	
	public static TradeAdapter buildTradeAdapter() {
		return new TradeAdapter(ServiceBuilder.buildTradeService());
	}
	
	public static WalletAdapter buildWalletAdapter() {
		return new WalletAdapter(ServiceBuilder.buildWalletService());
	}

	public static RecommendationAdapter buildRecommendationAdapter() {
		return new RecommendationAdapter(ServiceBuilder.buildRecommendationService());
	}
	
	
}
