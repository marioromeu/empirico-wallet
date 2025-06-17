package br.com.itads.empirico.view.importer.trades.strategy.impl;

import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.view.importer.trades.strategy.GenericResultProcess;

public class YieldResultProcessImpl extends GenericResultProcess {

	public YieldResultProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
	}

}
