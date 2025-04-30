package br.com.itads.empirico.application.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.com.itads.empirico.application.domain.Asset;
import br.com.itads.empirico.application.domain.Position;
import br.com.itads.empirico.application.domain.Result;
import br.com.itads.empirico.application.domain.Trade;
import br.com.itads.empirico.application.repository.PositionRepository;
import br.com.itads.empirico.application.repository.TradeRepository;
import br.com.itads.empirico.mock.MockBuilderForTestsJunit;

class TradeServiceTest {

	PositionRepository positionRepository = mock(PositionRepository.class);	
	TradeRepository tradeRepository = mock(TradeRepository.class);
	
	TradeService tradeService;
	
	
	TradeServiceTest() {
		tradeService = new TradeService(positionRepository, tradeRepository);
	}
	
	/**
	 * RF01 – Cadastrar operações sobre um ativo :
	 *	1.1-COMPRA
	 *	1.2-VENDA		
	 * @param trade
	 */
	@Test
	void RF01_11_E_12_processTrade_with_position_ok() {
		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade trade = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		UUID uuid = UUID.randomUUID();		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition());		
		Position position = tradeService.processTrade(trade, uuid);		
		assertNotNull(position);		
	}
	
	/*
	 * TODO vale a pena subir uma exceção de posição não encontrada ?
	 * Pois a ideia eh subir criar uma posição se for a primeira transacao
	 */
	@Test
	void RF01_11_E_12_processTrade_with_position_not_ok() {
		Asset bitcoin = MockBuilderForTestsJunit.buildAssetBTC();
		Trade trade = MockBuilderForTestsJunit.buildBuyTrade(bitcoin);
		UUID uuid = UUID.randomUUID();		
		when(positionRepository.getBy(any())).thenReturn(null);		
		Position position = tradeService.processTrade(trade, uuid);		
		assertNull(position);		
	}	

	/**
 	 * RF01 – Cadastrar operações sobre um ativo :
	 *	1.3-LUCRO
	 * @param result
	 * @param uuid
	 */
	void RF01_13_processResult_with_position_ok() {
		Result result = MockBuilderForTestsJunit.buildProfitResult();
		UUID uuid = UUID.randomUUID();		
		when(positionRepository.getBy(any())).thenReturn(MockBuilderForTestsJunit.buildPosition());		
		Position position = tradeService.processResult(result, uuid);		
		assertNotNull(position);
	}

}
