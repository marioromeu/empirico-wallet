package br.com.itads.empirico.application.ports.in;

import br.com.itads.empirico.adapters.dto.TradeDTO;

public interface PortTrade {

	public void processTrade(TradeDTO trade);
	
}
