package br.com.itads.empirico.adapters.in.imports.file.trades.strategy;

import br.com.itads.empirico.adapters.in.imports.file.dto.LineDTO;
import br.com.itads.empirico.application.core.domain.Wallet;

public interface TradeProcess {

	void process(Wallet wallet, LineDTO lineDTO, String categoria);
	
}
