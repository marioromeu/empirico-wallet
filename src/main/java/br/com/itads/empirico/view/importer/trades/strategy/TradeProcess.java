package br.com.itads.empirico.view.importer.trades.strategy;

import br.com.itads.empirico.application.core.domain.Wallet;
import br.com.itads.empirico.view.importer.dto.LineDTO;

public interface TradeProcess {

	void process(Wallet wallet, LineDTO lineDTO, String categoria);
	
}
