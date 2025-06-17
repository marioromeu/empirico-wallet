package br.com.itads.empirico.view.importer.trades.strategy.impl;

import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.view.importer.trades.strategy.GenericTradeProcess;

public class AdjustTradeProcessImpl extends GenericTradeProcess {

	public AdjustTradeProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
	}

}
