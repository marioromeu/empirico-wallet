package br.com.itads.empirico.adapters.in.imports.file.trades.strategy;

import br.com.itads.empirico.adapters.in.console.TradeAdapter;
import br.com.itads.empirico.adapters.in.console.WalletAdapter;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.generic.GenericTradeProcess;

public class SeparateTradeProcessImpl extends GenericTradeProcess {

	public SeparateTradeProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
	}

}
