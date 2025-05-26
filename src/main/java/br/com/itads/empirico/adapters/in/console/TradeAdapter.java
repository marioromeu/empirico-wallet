package br.com.itads.empirico.adapters.in.console;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.application.core.service.TradeService;
import br.com.itads.empirico.application.ports.in.PortTrade;

public class TradeAdapter implements PortTrade {

	TradeService tradeService;
	
	public TradeAdapter(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@Override
	public void processTrade(TradeDTO trade) {
		tradeService.processTrade(trade.toDomain(), trade.getAssetTicker());
	}
	
}
