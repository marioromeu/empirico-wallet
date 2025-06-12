package br.com.itads.empirico.application.ports.in;

import br.com.itads.empirico.adapters.dto.TradeDTO;
import br.com.itads.empirico.application.core.domain.Position;

public interface PortTrade {

	public Position processTrade(TradeDTO trade);
	
}
