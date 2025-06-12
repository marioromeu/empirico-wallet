package br.com.itads.empirico.adapters.in.console;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.application.core.domain.Position;
import br.com.itads.empirico.application.core.domain.Result;
import br.com.itads.empirico.application.core.service.TradeService;
import br.com.itads.empirico.application.ports.in.PortTrade;

public class TradeAdapter implements PortTrade {

	TradeService tradeService;
	
	public TradeAdapter(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@Override
	public Position processTrade(TradeDTO trade) {
		return tradeService.processTrade(trade.toDomain(), trade.getAssetTicker());
	}
	
	public Position processResult(Result result, String ticker) {
		return tradeService.processResult(result, ticker);
	}
	
}
