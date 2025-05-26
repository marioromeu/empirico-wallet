package br.com.itads.empirico.application.ports.out.repository;

import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Trade;

public interface TradeRepository extends CRUDRepository<Trade, UUID>{
	
}
