package br.com.itads.empirico.application.ports.in;

import br.com.itads.empirico.application.core.domain.Asset;

public interface PortAsset {

	public Asset getAsset(String ticker );
	
}
