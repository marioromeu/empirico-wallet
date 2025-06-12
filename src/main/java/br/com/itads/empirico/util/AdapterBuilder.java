package br.com.itads.empirico.util;

import br.com.itads.empirico.adapters.in.console.AssetAdapter;
import br.com.itads.empirico.adapters.in.console.TradeAdapter;
import br.com.itads.empirico.adapters.in.console.WalletAdapter;

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

}
