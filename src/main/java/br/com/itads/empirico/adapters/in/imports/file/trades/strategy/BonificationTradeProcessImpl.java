package br.com.itads.empirico.adapters.in.imports.file.trades.strategy;

import br.com.itads.empirico.adapters.in.console.TradeAdapter;
import br.com.itads.empirico.adapters.in.console.WalletAdapter;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.generic.GenericTradeProcess;

public class BonificationTradeProcessImpl extends GenericTradeProcess {

	public BonificationTradeProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
	}

}
