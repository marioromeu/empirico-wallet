package br.com.itads.empirico.view.importer.trades.strategy.impl;

import br.com.itads.empirico.adapters.in.TradeAdapter;
import br.com.itads.empirico.adapters.in.WalletAdapter;
import br.com.itads.empirico.view.importer.trades.strategy.GenericResultProcess;

public class RestitutionTradeProcessImpl extends GenericResultProcess {

	public RestitutionTradeProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
	}

}
