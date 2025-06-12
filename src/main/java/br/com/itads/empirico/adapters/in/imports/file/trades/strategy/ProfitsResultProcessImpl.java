package br.com.itads.empirico.adapters.in.imports.file.trades.strategy;

import br.com.itads.empirico.adapters.in.console.TradeAdapter;
import br.com.itads.empirico.adapters.in.console.WalletAdapter;
import br.com.itads.empirico.adapters.in.imports.file.trades.strategy.generic.GenericResultProcess;

public class ProfitsResultProcessImpl extends GenericResultProcess {

	public ProfitsResultProcessImpl(TradeAdapter tradeAdapter, WalletAdapter walletAdapter) {
		super(tradeAdapter, walletAdapter);
	}

}
